package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.util.ThreadUtil$MainThreadCallback;
import android.support.v7.util.TileList;
import android.util.Log;
import defpackage.d7;

/* JADX INFO: Add missing generic type declarations: [T] */
/* loaded from: classes.dex */
public class b7<T> implements ThreadUtil$MainThreadCallback<T> {
    public final d7.a a = new d7.a();
    public final Handler b = new Handler(Looper.getMainLooper());
    public Runnable c = new a();
    public final /* synthetic */ ThreadUtil$MainThreadCallback d;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            d7.b bVarA = b7.this.a.a();
            while (bVarA != null) {
                int i = bVarA.b;
                if (i == 1) {
                    b7.this.d.updateItemCount(bVarA.c, bVarA.d);
                } else if (i == 2) {
                    b7.this.d.addTile(bVarA.c, (TileList.Tile) bVarA.h);
                } else if (i != 3) {
                    StringBuilder sbA = g9.a("Unsupported message, what=");
                    sbA.append(bVarA.b);
                    Log.e("ThreadUtil", sbA.toString());
                } else {
                    b7.this.d.removeTile(bVarA.c, bVarA.d);
                }
                bVarA = b7.this.a.a();
            }
        }
    }

    public b7(d7 d7Var, ThreadUtil$MainThreadCallback threadUtil$MainThreadCallback) {
        this.d = threadUtil$MainThreadCallback;
    }

    public final void a(d7.b bVar) {
        this.a.a(bVar);
        this.b.post(this.c);
    }

    @Override // android.support.v7.util.ThreadUtil$MainThreadCallback
    public void addTile(int i, TileList.Tile<T> tile) {
        a(d7.b.a(2, i, tile));
    }

    @Override // android.support.v7.util.ThreadUtil$MainThreadCallback
    public void removeTile(int i, int i2) {
        a(d7.b.a(3, i, i2));
    }

    @Override // android.support.v7.util.ThreadUtil$MainThreadCallback
    public void updateItemCount(int i, int i2) {
        a(d7.b.a(1, i, i2));
    }
}
