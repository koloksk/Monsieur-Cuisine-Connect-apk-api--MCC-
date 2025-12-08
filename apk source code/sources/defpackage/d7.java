package defpackage;

/* JADX WARN: Unexpected interfaces in signature: [java.lang.Object<T>] */
/* loaded from: classes.dex */
public class d7<T> {

    public static class a {
        public b a;

        public synchronized b a() {
            if (this.a == null) {
                return null;
            }
            b bVar = this.a;
            this.a = this.a.a;
            return bVar;
        }

        public synchronized void b(b bVar) {
            bVar.a = this.a;
            this.a = bVar;
        }

        public synchronized void a(b bVar) {
            if (this.a == null) {
                this.a = bVar;
                return;
            }
            b bVar2 = this.a;
            while (bVar2.a != null) {
                bVar2 = bVar2.a;
            }
            bVar2.a = bVar;
        }

        public synchronized void a(int i) {
            while (this.a != null && this.a.b == i) {
                b bVar = this.a;
                this.a = this.a.a;
                bVar.a();
            }
            if (this.a != null) {
                b bVar2 = this.a;
                b bVar3 = bVar2.a;
                while (bVar3 != null) {
                    b bVar4 = bVar3.a;
                    if (bVar3.b == i) {
                        bVar2.a = bVar4;
                        bVar3.a();
                    } else {
                        bVar2 = bVar3;
                    }
                    bVar3 = bVar4;
                }
            }
        }
    }

    public static class b {
        public static b i;
        public static final Object j = new Object();
        public b a;
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;
        public Object h;

        public void a() {
            this.a = null;
            this.g = 0;
            this.f = 0;
            this.e = 0;
            this.d = 0;
            this.c = 0;
            this.b = 0;
            this.h = null;
            synchronized (j) {
                if (i != null) {
                    this.a = i;
                }
                i = this;
            }
        }

        public static b a(int i2, int i3, int i4, int i5, int i6, int i7, Object obj) {
            b bVar;
            synchronized (j) {
                if (i == null) {
                    bVar = new b();
                } else {
                    bVar = i;
                    i = i.a;
                    bVar.a = null;
                }
                bVar.b = i2;
                bVar.c = i3;
                bVar.d = i4;
                bVar.e = i5;
                bVar.f = i6;
                bVar.g = i7;
                bVar.h = obj;
            }
            return bVar;
        }

        public static b a(int i2, int i3, int i4) {
            return a(i2, i3, i4, 0, 0, 0, null);
        }

        public static b a(int i2, int i3, Object obj) {
            return a(i2, i3, 0, 0, 0, 0, obj);
        }
    }
}
