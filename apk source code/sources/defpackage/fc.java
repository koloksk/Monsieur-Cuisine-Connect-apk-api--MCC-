package defpackage;

/* loaded from: classes.dex */
public final class fc extends ec {
    public final boolean c;

    public fc(ac acVar, boolean z) {
        super(acVar);
        this.c = z;
    }

    public yb a() {
        bc[] bcVarArr = this.b;
        zb zbVar = new zb();
        zb zbVar2 = new zb();
        zb zbVar3 = new zb();
        zb zbVar4 = new zb();
        for (bc bcVar : bcVarArr) {
            if (bcVar != null) {
                bcVar.b();
                int i = bcVar.d % 30;
                int i2 = bcVar.e;
                if (!this.c) {
                    i2 += 2;
                }
                int i3 = i2 % 3;
                if (i3 == 0) {
                    zbVar2.a((i * 3) + 1);
                } else if (i3 == 1) {
                    zbVar4.a(i / 3);
                    zbVar3.a(i % 3);
                } else if (i3 == 2) {
                    zbVar.a(i + 1);
                }
            }
        }
        if (zbVar.a().length == 0 || zbVar2.a().length == 0 || zbVar3.a().length == 0 || zbVar4.a().length == 0 || zbVar.a()[0] <= 0 || zbVar2.a()[0] + zbVar3.a()[0] < 3 || zbVar2.a()[0] + zbVar3.a()[0] > 90) {
            return null;
        }
        yb ybVar = new yb(zbVar.a()[0], zbVar2.a()[0], zbVar3.a()[0], zbVar4.a()[0]);
        a(bcVarArr, ybVar);
        return ybVar;
    }

    @Override // defpackage.ec
    public String toString() {
        return "IsLeft: " + this.c + '\n' + super.toString();
    }

    public final void a(bc[] bcVarArr, yb ybVar) {
        for (int i = 0; i < bcVarArr.length; i++) {
            bc bcVar = bcVarArr[i];
            if (bcVarArr[i] != null) {
                int i2 = bcVar.d % 30;
                int i3 = bcVar.e;
                if (i3 > ybVar.e) {
                    bcVarArr[i] = null;
                } else {
                    if (!this.c) {
                        i3 += 2;
                    }
                    int i4 = i3 % 3;
                    if (i4 != 0) {
                        if (i4 != 1) {
                            if (i4 == 2 && i2 + 1 != ybVar.a) {
                                bcVarArr[i] = null;
                            }
                        } else if (i2 / 3 != ybVar.b || i2 % 3 != ybVar.d) {
                            bcVarArr[i] = null;
                        }
                    } else if ((i2 * 3) + 1 != ybVar.c) {
                        bcVarArr[i] = null;
                    }
                }
            }
        }
    }
}
