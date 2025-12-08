package defpackage;

import java.io.Serializable;
import java.util.Iterator;
import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public final class jo implements Iterable<Character>, Serializable {
    public static final long serialVersionUID = 8270183163158333422L;
    public final char a;
    public final char b;
    public final boolean c;
    public transient String d;

    public static class b implements Iterator<Character> {
        public char a;
        public final jo b;
        public boolean c;

        public /* synthetic */ b(jo joVar, a aVar) {
            this.b = joVar;
            this.c = true;
            if (!joVar.c) {
                this.a = joVar.a;
                return;
            }
            if (joVar.a != 0) {
                this.a = (char) 0;
                return;
            }
            char c = joVar.b;
            if (c == 65535) {
                this.c = false;
            } else {
                this.a = (char) (c + 1);
            }
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            return this.c;
        }

        @Override // java.util.Iterator
        public Character next() {
            if (!this.c) {
                throw new NoSuchElementException();
            }
            char c = this.a;
            jo joVar = this.b;
            if (joVar.c) {
                if (c == 65535) {
                    this.c = false;
                } else {
                    int i = c + 1;
                    if (i == joVar.a) {
                        char c2 = joVar.b;
                        if (c2 == 65535) {
                            this.c = false;
                        } else {
                            this.a = (char) (c2 + 1);
                        }
                    } else {
                        this.a = (char) i;
                    }
                }
            } else if (c < joVar.b) {
                this.a = (char) (c + 1);
            } else {
                this.c = false;
            }
            return Character.valueOf(c);
        }

        @Override // java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public jo(char c, char c2, boolean z) {
        if (c > c2) {
            c2 = c;
            c = c2;
        }
        this.a = c;
        this.b = c2;
        this.c = z;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof jo)) {
            return false;
        }
        jo joVar = (jo) obj;
        return this.a == joVar.a && this.b == joVar.b && this.c == joVar.c;
    }

    public int hashCode() {
        return (this.b * 7) + this.a + 'S' + (this.c ? 1 : 0);
    }

    @Override // java.lang.Iterable
    public Iterator<Character> iterator() {
        return new b(this, null);
    }

    public String toString() {
        if (this.d == null) {
            StringBuilder sb = new StringBuilder(4);
            if (this.c) {
                sb.append('^');
            }
            sb.append(this.a);
            if (this.a != this.b) {
                sb.append('-');
                sb.append(this.b);
            }
            this.d = sb.toString();
        }
        return this.d;
    }
}
