package io.reactivex.internal.util;

import defpackage.g9;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.io.Serializable;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public enum NotificationLite {
    COMPLETE;

    public static final class a implements Serializable {
        public static final long serialVersionUID = -7482590109178395495L;
        public final Disposable a;

        public a(Disposable disposable) {
            this.a = disposable;
        }

        public String toString() {
            StringBuilder sbA = g9.a("NotificationLite.Disposable[");
            sbA.append(this.a);
            sbA.append("]");
            return sbA.toString();
        }
    }

    public static final class b implements Serializable {
        public static final long serialVersionUID = -8759979445933046293L;
        public final Throwable a;

        public b(Throwable th) {
            this.a = th;
        }

        public boolean equals(Object obj) {
            if (obj instanceof b) {
                return ObjectHelper.equals(this.a, ((b) obj).a);
            }
            return false;
        }

        public int hashCode() {
            return this.a.hashCode();
        }

        public String toString() {
            StringBuilder sbA = g9.a("NotificationLite.Error[");
            sbA.append(this.a);
            sbA.append("]");
            return sbA.toString();
        }
    }

    public static final class c implements Serializable {
        public static final long serialVersionUID = -1322257508628817540L;
        public final Subscription a;

        public c(Subscription subscription) {
            this.a = subscription;
        }

        public String toString() {
            StringBuilder sbA = g9.a("NotificationLite.Subscription[");
            sbA.append(this.a);
            sbA.append("]");
            return sbA.toString();
        }
    }

    public static <T> boolean accept(Object obj, Subscriber<? super T> subscriber) {
        if (obj == COMPLETE) {
            subscriber.onComplete();
            return true;
        }
        if (obj instanceof b) {
            subscriber.onError(((b) obj).a);
            return true;
        }
        subscriber.onNext(obj);
        return false;
    }

    public static <T> boolean acceptFull(Object obj, Subscriber<? super T> subscriber) {
        if (obj == COMPLETE) {
            subscriber.onComplete();
            return true;
        }
        if (obj instanceof b) {
            subscriber.onError(((b) obj).a);
            return true;
        }
        if (obj instanceof c) {
            subscriber.onSubscribe(((c) obj).a);
            return false;
        }
        subscriber.onNext(obj);
        return false;
    }

    public static Object complete() {
        return COMPLETE;
    }

    public static Object disposable(Disposable disposable) {
        return new a(disposable);
    }

    public static Object error(Throwable th) {
        return new b(th);
    }

    public static Disposable getDisposable(Object obj) {
        return ((a) obj).a;
    }

    public static Throwable getError(Object obj) {
        return ((b) obj).a;
    }

    public static Subscription getSubscription(Object obj) {
        return ((c) obj).a;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static <T> T getValue(Object obj) {
        return obj;
    }

    public static boolean isComplete(Object obj) {
        return obj == COMPLETE;
    }

    public static boolean isDisposable(Object obj) {
        return obj instanceof a;
    }

    public static boolean isError(Object obj) {
        return obj instanceof b;
    }

    public static boolean isSubscription(Object obj) {
        return obj instanceof c;
    }

    public static <T> Object next(T t) {
        return t;
    }

    public static Object subscription(Subscription subscription) {
        return new c(subscription);
    }

    @Override // java.lang.Enum
    public String toString() {
        return "NotificationLite.Complete";
    }

    public static <T> boolean accept(Object obj, Observer<? super T> observer) {
        if (obj == COMPLETE) {
            observer.onComplete();
            return true;
        }
        if (obj instanceof b) {
            observer.onError(((b) obj).a);
            return true;
        }
        observer.onNext(obj);
        return false;
    }

    public static <T> boolean acceptFull(Object obj, Observer<? super T> observer) {
        if (obj == COMPLETE) {
            observer.onComplete();
            return true;
        }
        if (obj instanceof b) {
            observer.onError(((b) obj).a);
            return true;
        }
        if (obj instanceof a) {
            observer.onSubscribe(((a) obj).a);
            return false;
        }
        observer.onNext(obj);
        return false;
    }
}
