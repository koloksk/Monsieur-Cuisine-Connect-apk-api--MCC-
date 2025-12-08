package io.reactivex.internal.subscriptions;

import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.util.BackpressureHelper;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public class SubscriptionArbiter extends AtomicInteger implements Subscription {
    public static final long serialVersionUID = -2189523197179400958L;
    public Subscription a;
    public long b;
    public final AtomicReference<Subscription> c = new AtomicReference<>();
    public final AtomicLong d = new AtomicLong();
    public final AtomicLong e = new AtomicLong();
    public final boolean f;
    public volatile boolean g;
    public boolean unbounded;

    public SubscriptionArbiter(boolean z) {
        this.f = z;
    }

    public final void a() {
        if (getAndIncrement() != 0) {
            return;
        }
        b();
    }

    public final void b() {
        int iAddAndGet = 1;
        Subscription subscription = null;
        long jAddCap = 0;
        do {
            Subscription andSet = this.c.get();
            if (andSet != null) {
                andSet = this.c.getAndSet(null);
            }
            long andSet2 = this.d.get();
            if (andSet2 != 0) {
                andSet2 = this.d.getAndSet(0L);
            }
            long andSet3 = this.e.get();
            if (andSet3 != 0) {
                andSet3 = this.e.getAndSet(0L);
            }
            Subscription subscription2 = this.a;
            if (this.g) {
                if (subscription2 != null) {
                    subscription2.cancel();
                    this.a = null;
                }
                if (andSet != null) {
                    andSet.cancel();
                }
            } else {
                long jAddCap2 = this.b;
                if (jAddCap2 != Long.MAX_VALUE) {
                    jAddCap2 = BackpressureHelper.addCap(jAddCap2, andSet2);
                    if (jAddCap2 != Long.MAX_VALUE) {
                        jAddCap2 -= andSet3;
                        if (jAddCap2 < 0) {
                            SubscriptionHelper.reportMoreProduced(jAddCap2);
                            jAddCap2 = 0;
                        }
                    }
                    this.b = jAddCap2;
                }
                if (andSet != null) {
                    if (subscription2 != null && this.f) {
                        subscription2.cancel();
                    }
                    this.a = andSet;
                    if (jAddCap2 != 0) {
                        jAddCap = BackpressureHelper.addCap(jAddCap, jAddCap2);
                        subscription = andSet;
                    }
                } else if (subscription2 != null && andSet2 != 0) {
                    jAddCap = BackpressureHelper.addCap(jAddCap, andSet2);
                    subscription = subscription2;
                }
            }
            iAddAndGet = addAndGet(-iAddAndGet);
        } while (iAddAndGet != 0);
        if (jAddCap != 0) {
            subscription.request(jAddCap);
        }
    }

    public void cancel() {
        if (this.g) {
            return;
        }
        this.g = true;
        a();
    }

    public final boolean isCancelled() {
        return this.g;
    }

    public final boolean isUnbounded() {
        return this.unbounded;
    }

    public final void produced(long j) {
        if (this.unbounded) {
            return;
        }
        if (get() != 0 || !compareAndSet(0, 1)) {
            BackpressureHelper.add(this.e, j);
            a();
            return;
        }
        long j2 = this.b;
        if (j2 != Long.MAX_VALUE) {
            long j3 = j2 - j;
            if (j3 < 0) {
                SubscriptionHelper.reportMoreProduced(j3);
                j3 = 0;
            }
            this.b = j3;
        }
        if (decrementAndGet() == 0) {
            return;
        }
        b();
    }

    @Override // org.reactivestreams.Subscription
    public final void request(long j) {
        if (!SubscriptionHelper.validate(j) || this.unbounded) {
            return;
        }
        if (get() != 0 || !compareAndSet(0, 1)) {
            BackpressureHelper.add(this.d, j);
            a();
            return;
        }
        long j2 = this.b;
        if (j2 != Long.MAX_VALUE) {
            long jAddCap = BackpressureHelper.addCap(j2, j);
            this.b = jAddCap;
            if (jAddCap == Long.MAX_VALUE) {
                this.unbounded = true;
            }
        }
        Subscription subscription = this.a;
        if (decrementAndGet() != 0) {
            b();
        }
        if (subscription != null) {
            subscription.request(j);
        }
    }

    public final void setSubscription(Subscription subscription) {
        if (this.g) {
            subscription.cancel();
            return;
        }
        ObjectHelper.requireNonNull(subscription, "s is null");
        if (get() != 0 || !compareAndSet(0, 1)) {
            Subscription andSet = this.c.getAndSet(subscription);
            if (andSet != null && this.f) {
                andSet.cancel();
            }
            a();
            return;
        }
        Subscription subscription2 = this.a;
        if (subscription2 != null && this.f) {
            subscription2.cancel();
        }
        this.a = subscription;
        long j = this.b;
        if (decrementAndGet() != 0) {
            b();
        }
        if (j != 0) {
            subscription.request(j);
        }
    }
}
