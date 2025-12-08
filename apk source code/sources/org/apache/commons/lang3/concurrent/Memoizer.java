package org.apache.commons.lang3.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/* loaded from: classes.dex */
public class Memoizer<I, O> implements Computable<I, O> {
    public final ConcurrentMap<I, Future<O>> a;
    public final Computable<I, O> b;
    public final boolean c;

    public class a implements Callable<O> {
        public final /* synthetic */ Object a;

        public a(Object obj) {
            this.a = obj;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public O call() throws InterruptedException {
            return (O) Memoizer.this.b.compute(this.a);
        }
    }

    public Memoizer(Computable<I, O> computable) {
        this(computable, false);
    }

    @Override // org.apache.commons.lang3.concurrent.Computable
    public O compute(I i) throws InterruptedException {
        FutureTask futureTask;
        while (true) {
            Future<O> futurePutIfAbsent = this.a.get(i);
            if (futurePutIfAbsent == null && (futurePutIfAbsent = this.a.putIfAbsent(i, (futureTask = new FutureTask(new a(i))))) == null) {
                futureTask.run();
                futurePutIfAbsent = futureTask;
            }
            try {
                continue;
                return futurePutIfAbsent.get();
            } catch (CancellationException unused) {
                this.a.remove(i, futurePutIfAbsent);
            } catch (ExecutionException e) {
                if (this.c) {
                    this.a.remove(i, futurePutIfAbsent);
                }
                Throwable cause = e.getCause();
                if (cause instanceof RuntimeException) {
                    throw ((RuntimeException) cause);
                }
                if (cause instanceof Error) {
                    throw ((Error) cause);
                }
                throw new IllegalStateException("Unchecked exception", cause);
            }
        }
    }

    public Memoizer(Computable<I, O> computable, boolean z) {
        this.a = new ConcurrentHashMap();
        this.b = computable;
        this.c = z;
    }
}
