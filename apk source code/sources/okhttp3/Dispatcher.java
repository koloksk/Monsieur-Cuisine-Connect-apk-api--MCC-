package okhttp3;

import defpackage.an;
import defpackage.g9;
import java.io.InterruptedIOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.internal.Util;

/* loaded from: classes.dex */
public final class Dispatcher {

    @Nullable
    public Runnable c;

    @Nullable
    public ExecutorService d;
    public int a = 64;
    public int b = 5;
    public final Deque<an.a> e = new ArrayDeque();
    public final Deque<an.a> f = new ArrayDeque();
    public final Deque<an> g = new ArrayDeque();

    public Dispatcher(ExecutorService executorService) {
        this.d = executorService;
    }

    public void a(an.a aVar) {
        an.a next;
        synchronized (this) {
            this.e.add(aVar);
            if (!an.this.d) {
                String strA = aVar.a();
                Iterator<an.a> it = this.f.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        Iterator<an.a> it2 = this.e.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                next = null;
                                break;
                            } else {
                                next = it2.next();
                                if (next.a().equals(strA)) {
                                    break;
                                }
                            }
                        }
                    } else {
                        next = it.next();
                        if (next.a().equals(strA)) {
                            break;
                        }
                    }
                }
                if (next != null) {
                    aVar.b = next.b;
                }
            }
        }
        a();
    }

    public void b(an.a aVar) {
        aVar.b.decrementAndGet();
        a(this.f, aVar);
    }

    public synchronized void cancelAll() {
        Iterator<an.a> it = this.e.iterator();
        while (it.hasNext()) {
            an.this.b.cancel();
        }
        Iterator<an.a> it2 = this.f.iterator();
        while (it2.hasNext()) {
            an.this.b.cancel();
        }
        Iterator<an> it3 = this.g.iterator();
        while (it3.hasNext()) {
            it3.next().b.cancel();
        }
    }

    public synchronized ExecutorService executorService() {
        if (this.d == null) {
            this.d = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue(), Util.threadFactory("OkHttp Dispatcher", false));
        }
        return this.d;
    }

    public synchronized int getMaxRequests() {
        return this.a;
    }

    public synchronized int getMaxRequestsPerHost() {
        return this.b;
    }

    public synchronized List<Call> queuedCalls() {
        ArrayList arrayList;
        arrayList = new ArrayList();
        Iterator<an.a> it = this.e.iterator();
        while (it.hasNext()) {
            arrayList.add(an.this);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public synchronized int queuedCallsCount() {
        return this.e.size();
    }

    public synchronized List<Call> runningCalls() {
        ArrayList arrayList;
        arrayList = new ArrayList();
        arrayList.addAll(this.g);
        Iterator<an.a> it = this.f.iterator();
        while (it.hasNext()) {
            arrayList.add(an.this);
        }
        return Collections.unmodifiableList(arrayList);
    }

    public synchronized int runningCallsCount() {
        return this.f.size() + this.g.size();
    }

    public synchronized void setIdleCallback(@Nullable Runnable runnable) {
        this.c = runnable;
    }

    public void setMaxRequests(int i) {
        if (i < 1) {
            throw new IllegalArgumentException(g9.b("max < 1: ", i));
        }
        synchronized (this) {
            this.a = i;
        }
        a();
    }

    public void setMaxRequestsPerHost(int i) {
        if (i < 1) {
            throw new IllegalArgumentException(g9.b("max < 1: ", i));
        }
        synchronized (this) {
            this.b = i;
        }
        a();
    }

    public Dispatcher() {
    }

    public final boolean a() {
        int i;
        boolean z;
        ArrayList arrayList = new ArrayList();
        synchronized (this) {
            Iterator<an.a> it = this.e.iterator();
            while (it.hasNext()) {
                an.a next = it.next();
                if (this.f.size() >= this.a) {
                    break;
                }
                if (next.b.get() < this.b) {
                    it.remove();
                    next.b.incrementAndGet();
                    arrayList.add(next);
                    this.f.add(next);
                }
            }
            z = runningCallsCount() > 0;
        }
        int size = arrayList.size();
        for (i = 0; i < size; i++) {
            an.a aVar = (an.a) arrayList.get(i);
            ExecutorService executorService = executorService();
            if (aVar != null) {
                try {
                    try {
                        executorService.execute(aVar);
                    } catch (RejectedExecutionException e) {
                        InterruptedIOException interruptedIOException = new InterruptedIOException("executor rejected");
                        interruptedIOException.initCause(e);
                        an.this.b.noMoreExchanges(interruptedIOException);
                        aVar.a.onFailure(an.this, interruptedIOException);
                        an.this.a.dispatcher().b(aVar);
                    }
                } catch (Throwable th) {
                    an.this.a.dispatcher().b(aVar);
                    throw th;
                }
            } else {
                throw null;
            }
        }
        return z;
    }

    public synchronized void a(an anVar) {
        this.g.add(anVar);
    }

    public final <T> void a(Deque<T> deque, T t) {
        Runnable runnable;
        synchronized (this) {
            if (deque.remove(t)) {
                runnable = this.c;
            } else {
                throw new AssertionError("Call wasn't in-flight!");
            }
        }
        if (a() || runnable == null) {
            return;
        }
        runnable.run();
    }
}
