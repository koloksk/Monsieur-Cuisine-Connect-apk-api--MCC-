package defpackage;

import android.os.Handler;
import android.os.HandlerThread;

/* loaded from: classes.dex */
public class uc {
    public static uc e;
    public Handler a;
    public HandlerThread b;
    public int c = 0;
    public final Object d = new Object();

    public void a(Runnable runnable) {
        synchronized (this.d) {
            a();
            this.a.post(runnable);
        }
    }

    public void b() {
        synchronized (this.d) {
            int i = this.c - 1;
            this.c = i;
            if (i == 0) {
                c();
            }
        }
    }

    public final void c() {
        synchronized (this.d) {
            this.b.quit();
            this.b = null;
            this.a = null;
        }
    }

    public final void a() {
        synchronized (this.d) {
            if (this.a == null) {
                if (this.c > 0) {
                    HandlerThread handlerThread = new HandlerThread("CameraThread");
                    this.b = handlerThread;
                    handlerThread.start();
                    this.a = new Handler(this.b.getLooper());
                } else {
                    throw new IllegalStateException("CameraThread is not open");
                }
            }
        }
    }

    public void b(Runnable runnable) {
        synchronized (this.d) {
            this.c++;
            a(runnable);
        }
    }
}
