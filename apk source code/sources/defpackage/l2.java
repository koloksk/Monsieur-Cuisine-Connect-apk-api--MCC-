package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class l2 {
    public static l2 e;
    public final Object a = new Object();
    public final Handler b = new Handler(Looper.getMainLooper(), new a());
    public c c;
    public c d;

    public class a implements Handler.Callback {
        public a() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            if (message.what != 0) {
                return false;
            }
            l2.this.a((c) message.obj);
            return true;
        }
    }

    public interface b {
        void a(int i);

        void show();
    }

    public static class c {
        public final WeakReference<b> a;
        public int b;
        public boolean c;

        public c(int i, b bVar) {
            this.a = new WeakReference<>(bVar);
            this.b = i;
        }
    }

    public static l2 b() {
        if (e == null) {
            e = new l2();
        }
        return e;
    }

    public void a(int i, b bVar) {
        synchronized (this.a) {
            if (c(bVar)) {
                this.c.b = i;
                this.b.removeCallbacksAndMessages(this.c);
                b(this.c);
                return;
            }
            if (d(bVar)) {
                this.d.b = i;
            } else {
                this.d = new c(i, bVar);
            }
            if (this.c == null || !a(this.c, 4)) {
                this.c = null;
                a();
            }
        }
    }

    public final boolean c(b bVar) {
        c cVar = this.c;
        if (cVar != null) {
            if (bVar != null && cVar.a.get() == bVar) {
                return true;
            }
        }
        return false;
    }

    public final boolean d(b bVar) {
        c cVar = this.d;
        if (cVar != null) {
            if (bVar != null && cVar.a.get() == bVar) {
                return true;
            }
        }
        return false;
    }

    public void e(b bVar) {
        synchronized (this.a) {
            if (c(bVar)) {
                this.c = null;
                if (this.d != null) {
                    a();
                }
            }
        }
    }

    public void f(b bVar) {
        synchronized (this.a) {
            if (c(bVar)) {
                b(this.c);
            }
        }
    }

    public void g(b bVar) {
        synchronized (this.a) {
            if (c(bVar) && !this.c.c) {
                this.c.c = true;
                this.b.removeCallbacksAndMessages(this.c);
            }
        }
    }

    public void h(b bVar) {
        synchronized (this.a) {
            if (c(bVar) && this.c.c) {
                this.c.c = false;
                b(this.c);
            }
        }
    }

    public boolean b(b bVar) {
        boolean z;
        synchronized (this.a) {
            z = c(bVar) || d(bVar);
        }
        return z;
    }

    public final void b(c cVar) {
        int i = cVar.b;
        if (i == -2) {
            return;
        }
        if (i <= 0) {
            i = i == -1 ? 1500 : 2750;
        }
        this.b.removeCallbacksAndMessages(cVar);
        Handler handler = this.b;
        handler.sendMessageDelayed(Message.obtain(handler, 0, cVar), i);
    }

    public void a(b bVar, int i) {
        synchronized (this.a) {
            if (c(bVar)) {
                a(this.c, i);
            } else if (d(bVar)) {
                a(this.d, i);
            }
        }
    }

    public boolean a(b bVar) {
        boolean zC;
        synchronized (this.a) {
            zC = c(bVar);
        }
        return zC;
    }

    public final void a() {
        c cVar = this.d;
        if (cVar != null) {
            this.c = cVar;
            this.d = null;
            b bVar = cVar.a.get();
            if (bVar != null) {
                bVar.show();
            } else {
                this.c = null;
            }
        }
    }

    public final boolean a(c cVar, int i) {
        b bVar = cVar.a.get();
        if (bVar == null) {
            return false;
        }
        this.b.removeCallbacksAndMessages(cVar);
        bVar.a(i);
        return true;
    }

    public void a(c cVar) {
        synchronized (this.a) {
            if (this.c == cVar || this.d == cVar) {
                a(cVar, 2);
            }
        }
    }
}
