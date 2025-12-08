package defpackage;

import java.util.Formatter;

/* loaded from: classes.dex */
public class ec {
    public final ac a;
    public final bc[] b;

    public ec(ac acVar) {
        this.a = new ac(acVar);
        this.b = new bc[(acVar.i - acVar.h) + 1];
    }

    public final bc a(int i) {
        bc bcVar;
        bc bcVar2;
        bc bcVar3 = this.b[i - this.a.h];
        if (bcVar3 != null) {
            return bcVar3;
        }
        for (int i2 = 1; i2 < 5; i2++) {
            int i3 = (i - this.a.h) - i2;
            if (i3 >= 0 && (bcVar2 = this.b[i3]) != null) {
                return bcVar2;
            }
            int i4 = (i - this.a.h) + i2;
            bc[] bcVarArr = this.b;
            if (i4 < bcVarArr.length && (bcVar = bcVarArr[i4]) != null) {
                return bcVar;
            }
        }
        return null;
    }

    public final int b(int i) {
        return i - this.a.h;
    }

    public String toString() {
        Formatter formatter = new Formatter();
        int i = 0;
        for (bc bcVar : this.b) {
            if (bcVar == null) {
                formatter.format("%3d:    |   %n", Integer.valueOf(i));
                i++;
            } else {
                formatter.format("%3d: %3d|%3d%n", Integer.valueOf(i), Integer.valueOf(bcVar.e), Integer.valueOf(bcVar.d));
                i++;
            }
        }
        String string = formatter.toString();
        formatter.close();
        return string;
    }
}
