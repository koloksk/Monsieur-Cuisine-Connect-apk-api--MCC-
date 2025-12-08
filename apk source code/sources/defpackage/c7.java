package defpackage;

import android.os.AsyncTask;
import android.support.v7.util.ThreadUtil$BackgroundCallback;
import android.support.v7.util.TileList;
import android.util.Log;
import defpackage.d7;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: Add missing generic type declarations: [T] */
/* loaded from: classes.dex */
public class c7<T> implements ThreadUtil$BackgroundCallback<T> {
    public final d7.a a = new d7.a();
    public final Executor b = AsyncTask.THREAD_POOL_EXECUTOR;
    public AtomicBoolean c = new AtomicBoolean(false);
    public Runnable d = new a();
    public final /* synthetic */ ThreadUtil$BackgroundCallback e;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            while (true) {
                d7.b bVarA = c7.this.a.a();
                if (bVarA == null) {
                    c7.this.c.set(false);
                    return;
                }
                int i = bVarA.b;
                if (i == 1) {
                    c7.this.a.a(1);
                    c7.this.e.refresh(bVarA.c);
                } else if (i == 2) {
                    c7.this.a.a(2);
                    c7.this.a.a(3);
                    c7.this.e.updateRange(bVarA.c, bVarA.d, bVarA.e, bVarA.f, bVarA.g);
                } else if (i == 3) {
                    c7.this.e.loadTile(bVarA.c, bVarA.d);
                } else if (i != 4) {
                    StringBuilder sbA = g9.a("Unsupported message, what=");
                    sbA.append(bVarA.b);
                    Log.e("ThreadUtil", sbA.toString());
                } else {
                    c7.this.e.recycleTile((TileList.Tile) bVarA.h);
                }
            }
        }
    }

    public c7(d7 d7Var, ThreadUtil$BackgroundCallback threadUtil$BackgroundCallback) {
        this.e = threadUtil$BackgroundCallback;
    }

    public final void a(d7.b bVar) {
        this.a.a(bVar);
        if (this.c.compareAndSet(false, true)) {
            this.b.execute(this.d);
        }
    }

    public final void b(d7.b bVar) {
        this.a.b(bVar);
        if (this.c.compareAndSet(false, true)) {
            this.b.execute(this.d);
        }
    }

    @Override // android.support.v7.util.ThreadUtil$BackgroundCallback
    public void loadTile(int i, int i2) {
        a(d7.b.a(3, i, i2));
    }

    @Override // android.support.v7.util.ThreadUtil$BackgroundCallback
    public void recycleTile(TileList.Tile<T> tile) {
        a(d7.b.a(4, 0, tile));
    }

    @Override // android.support.v7.util.ThreadUtil$BackgroundCallback
    public void refresh(int i) {
        b(d7.b.a(1, i, (Object) null));
    }

    @Override // android.support.v7.util.ThreadUtil$BackgroundCallback
    public void updateRange(int i, int i2, int i3, int i4, int i5) {
        b(d7.b.a(2, i, i2, i3, i4, i5, null));
    }
}
