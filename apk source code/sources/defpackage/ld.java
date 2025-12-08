package defpackage;

import com.squareup.picasso.Picasso;
import defpackage.qd;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class ld extends ThreadPoolExecutor {

    public static final class a extends FutureTask<xc> implements Comparable<a> {
        public final xc a;

        public a(xc xcVar) {
            super(xcVar, null);
            this.a = xcVar;
        }

        @Override // java.lang.Comparable
        public int compareTo(a aVar) {
            xc xcVar = this.a;
            Picasso.Priority priority = xcVar.s;
            xc xcVar2 = aVar.a;
            Picasso.Priority priority2 = xcVar2.s;
            return priority == priority2 ? xcVar.a - xcVar2.a : priority2.ordinal() - priority.ordinal();
        }
    }

    public ld() {
        super(3, 3, 0L, TimeUnit.MILLISECONDS, new PriorityBlockingQueue(), new qd.c());
    }

    @Override // java.util.concurrent.AbstractExecutorService, java.util.concurrent.ExecutorService
    public Future<?> submit(Runnable runnable) {
        a aVar = new a((xc) runnable);
        execute(aVar);
        return aVar;
    }
}
