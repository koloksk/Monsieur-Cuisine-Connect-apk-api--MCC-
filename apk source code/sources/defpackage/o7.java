package defpackage;

import android.support.v4.util.Pools;
import android.support.v7.widget.RecyclerView;
import defpackage.l8;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class o7 implements l8.a {
    public final a d;
    public Pools.Pool<b> a = new Pools.SimplePool(30);
    public final ArrayList<b> b = new ArrayList<>();
    public final ArrayList<b> c = new ArrayList<>();
    public int g = 0;
    public final boolean e = false;
    public final l8 f = new l8(this);

    public interface a {
    }

    public static class b {
        public int a;
        public int b;
        public Object c;
        public int d;

        public b(int i, int i2, int i3, Object obj) {
            this.a = i;
            this.b = i2;
            this.d = i3;
            this.c = obj;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || b.class != obj.getClass()) {
                return false;
            }
            b bVar = (b) obj;
            int i = this.a;
            if (i != bVar.a) {
                return false;
            }
            if (i == 8 && Math.abs(this.d - this.b) == 1 && this.d == bVar.b && this.b == bVar.d) {
                return true;
            }
            if (this.d != bVar.d || this.b != bVar.b) {
                return false;
            }
            Object obj2 = this.c;
            if (obj2 != null) {
                if (!obj2.equals(bVar.c)) {
                    return false;
                }
            } else if (bVar.c != null) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((this.a * 31) + this.b) * 31) + this.d;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append("[");
            int i = this.a;
            sb.append(i != 1 ? i != 2 ? i != 4 ? i != 8 ? "??" : "mv" : "up" : "rm" : "add");
            sb.append(",s:");
            sb.append(this.b);
            sb.append("c:");
            sb.append(this.d);
            sb.append(",p:");
            sb.append(this.c);
            sb.append("]");
            return sb.toString();
        }
    }

    public o7(a aVar) {
        this.d = aVar;
    }

    public void a() {
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            ((n8) this.d).a(this.c.get(i));
        }
        a(this.c);
        this.g = 0;
    }

    public final int b(int i, int i2) {
        for (int size = this.c.size() - 1; size >= 0; size--) {
            b bVar = this.c.get(size);
            int i3 = bVar.a;
            if (i3 == 8) {
                int i4 = bVar.b;
                int i5 = bVar.d;
                if (i4 >= i5) {
                    i5 = i4;
                    i4 = i5;
                }
                if (i < i4 || i > i5) {
                    int i6 = bVar.b;
                    if (i < i6) {
                        if (i2 == 1) {
                            bVar.b = i6 + 1;
                            bVar.d++;
                        } else if (i2 == 2) {
                            bVar.b = i6 - 1;
                            bVar.d--;
                        }
                    }
                } else {
                    int i7 = bVar.b;
                    if (i4 == i7) {
                        if (i2 == 1) {
                            bVar.d++;
                        } else if (i2 == 2) {
                            bVar.d--;
                        }
                        i++;
                    } else {
                        if (i2 == 1) {
                            bVar.b = i7 + 1;
                        } else if (i2 == 2) {
                            bVar.b = i7 - 1;
                        }
                        i--;
                    }
                }
            } else {
                int i8 = bVar.b;
                if (i8 <= i) {
                    if (i3 == 1) {
                        i -= bVar.d;
                    } else if (i3 == 2) {
                        i += bVar.d;
                    }
                } else if (i2 == 1) {
                    bVar.b = i8 + 1;
                } else if (i2 == 2) {
                    bVar.b = i8 - 1;
                }
            }
        }
        for (int size2 = this.c.size() - 1; size2 >= 0; size2--) {
            b bVar2 = this.c.get(size2);
            if (bVar2.a == 8) {
                int i9 = bVar2.d;
                if (i9 == bVar2.b || i9 < 0) {
                    this.c.remove(size2);
                    if (!this.e) {
                        bVar2.c = null;
                        this.a.release(bVar2);
                    }
                }
            } else if (bVar2.d <= 0) {
                this.c.remove(size2);
                if (!this.e) {
                    bVar2.c = null;
                    this.a.release(bVar2);
                }
            }
        }
        return i;
    }

    public boolean c() {
        return this.b.size() > 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:197:0x00ac A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0142 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x012b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:206:0x00db A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:219:0x0009 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a7  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x00e0  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0110  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void d() {
        /*
            Method dump skipped, instructions count: 727
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.o7.d():void");
    }

    public void c(b bVar) {
        if (this.e) {
            return;
        }
        bVar.c = null;
        this.a.release(bVar);
    }

    public final void a(b bVar) {
        int i;
        int i2 = bVar.a;
        if (i2 != 1 && i2 != 8) {
            int iB = b(bVar.b, i2);
            int i3 = bVar.b;
            int i4 = bVar.a;
            if (i4 == 2) {
                i = 0;
            } else {
                if (i4 != 4) {
                    throw new IllegalArgumentException("op should be remove or update." + bVar);
                }
                i = 1;
            }
            int i5 = 1;
            for (int i6 = 1; i6 < bVar.d; i6++) {
                int iB2 = b((i * i6) + bVar.b, bVar.a);
                int i7 = bVar.a;
                if (i7 == 2 ? iB2 == iB : i7 == 4 && iB2 == iB + 1) {
                    i5++;
                } else {
                    b bVarA = a(bVar.a, iB, i5, bVar.c);
                    a(bVarA, i3);
                    if (!this.e) {
                        bVarA.c = null;
                        this.a.release(bVarA);
                    }
                    if (bVar.a == 4) {
                        i3 += i5;
                    }
                    i5 = 1;
                    iB = iB2;
                }
            }
            Object obj = bVar.c;
            if (!this.e) {
                bVar.c = null;
                this.a.release(bVar);
            }
            if (i5 > 0) {
                b bVarA2 = a(bVar.a, iB, i5, obj);
                a(bVarA2, i3);
                if (this.e) {
                    return;
                }
                bVarA2.c = null;
                this.a.release(bVarA2);
                return;
            }
            return;
        }
        throw new IllegalArgumentException("should not dispatch add or move for pre layout");
    }

    public void a(b bVar, int i) {
        ((n8) this.d).a(bVar);
        int i2 = bVar.a;
        if (i2 != 2) {
            if (i2 == 4) {
                ((n8) this.d).a(i, bVar.d, bVar.c);
                return;
            }
            throw new IllegalArgumentException("only remove and update ops can be dispatched in first pass");
        }
        a aVar = this.d;
        int i3 = bVar.d;
        n8 n8Var = (n8) aVar;
        n8Var.a.a(i, i3, true);
        RecyclerView recyclerView = n8Var.a;
        recyclerView.k0 = true;
        recyclerView.h0.d += i3;
    }

    public final void b(b bVar) {
        this.c.add(bVar);
        int i = bVar.a;
        if (i == 1) {
            ((n8) this.d).a(bVar.b, bVar.d);
            return;
        }
        if (i == 2) {
            n8 n8Var = (n8) this.d;
            n8Var.a.a(bVar.b, bVar.d, false);
            n8Var.a.k0 = true;
            return;
        }
        if (i == 4) {
            ((n8) this.d).a(bVar.b, bVar.d, bVar.c);
        } else if (i == 8) {
            ((n8) this.d).b(bVar.b, bVar.d);
        } else {
            throw new IllegalArgumentException("Unknown update op type for " + bVar);
        }
    }

    public final boolean a(int i) {
        int size = this.c.size();
        for (int i2 = 0; i2 < size; i2++) {
            b bVar = this.c.get(i2);
            int i3 = bVar.a;
            if (i3 == 8) {
                if (a(bVar.d, i2 + 1) == i) {
                    return true;
                }
            } else if (i3 == 1) {
                int i4 = bVar.b;
                int i5 = bVar.d + i4;
                while (i4 < i5) {
                    if (a(i4, i2 + 1) == i) {
                        return true;
                    }
                    i4++;
                }
            } else {
                continue;
            }
        }
        return false;
    }

    public void b() {
        a();
        int size = this.b.size();
        for (int i = 0; i < size; i++) {
            b bVar = this.b.get(i);
            int i2 = bVar.a;
            if (i2 == 1) {
                ((n8) this.d).a(bVar);
                ((n8) this.d).a(bVar.b, bVar.d);
            } else if (i2 == 2) {
                ((n8) this.d).a(bVar);
                a aVar = this.d;
                int i3 = bVar.b;
                int i4 = bVar.d;
                n8 n8Var = (n8) aVar;
                n8Var.a.a(i3, i4, true);
                RecyclerView recyclerView = n8Var.a;
                recyclerView.k0 = true;
                recyclerView.h0.d += i4;
            } else if (i2 == 4) {
                ((n8) this.d).a(bVar);
                ((n8) this.d).a(bVar.b, bVar.d, bVar.c);
            } else if (i2 == 8) {
                ((n8) this.d).a(bVar);
                ((n8) this.d).b(bVar.b, bVar.d);
            }
        }
        a(this.b);
        this.g = 0;
    }

    public int a(int i, int i2) {
        int size = this.c.size();
        while (i2 < size) {
            b bVar = this.c.get(i2);
            int i3 = bVar.a;
            if (i3 == 8) {
                int i4 = bVar.b;
                if (i4 == i) {
                    i = bVar.d;
                } else {
                    if (i4 < i) {
                        i--;
                    }
                    if (bVar.d <= i) {
                        i++;
                    }
                }
            } else {
                int i5 = bVar.b;
                if (i5 > i) {
                    continue;
                } else if (i3 == 2) {
                    int i6 = bVar.d;
                    if (i < i5 + i6) {
                        return -1;
                    }
                    i -= i6;
                } else if (i3 == 1) {
                    i += bVar.d;
                }
            }
            i2++;
        }
        return i;
    }

    public b a(int i, int i2, int i3, Object obj) {
        b bVarAcquire = this.a.acquire();
        if (bVarAcquire == null) {
            return new b(i, i2, i3, obj);
        }
        bVarAcquire.a = i;
        bVarAcquire.b = i2;
        bVarAcquire.d = i3;
        bVarAcquire.c = obj;
        return bVarAcquire;
    }

    public void a(List<b> list) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            c(list.get(i));
        }
        list.clear();
    }
}
