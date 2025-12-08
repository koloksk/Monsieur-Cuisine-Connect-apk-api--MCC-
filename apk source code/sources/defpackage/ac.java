package defpackage;

import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;

/* loaded from: classes.dex */
public final class ac {
    public BitMatrix a;
    public ResultPoint b;
    public ResultPoint c;
    public ResultPoint d;
    public ResultPoint e;
    public int f;
    public int g;
    public int h;
    public int i;

    public ac(BitMatrix bitMatrix, ResultPoint resultPoint, ResultPoint resultPoint2, ResultPoint resultPoint3, ResultPoint resultPoint4) throws NotFoundException {
        if ((resultPoint == null && resultPoint3 == null) || ((resultPoint2 == null && resultPoint4 == null) || ((resultPoint != null && resultPoint2 == null) || (resultPoint3 != null && resultPoint4 == null)))) {
            throw NotFoundException.getNotFoundInstance();
        }
        this.a = bitMatrix;
        this.b = resultPoint;
        this.c = resultPoint2;
        this.d = resultPoint3;
        this.e = resultPoint4;
        a();
    }

    public final void a() {
        if (this.b == null) {
            this.b = new ResultPoint(0.0f, this.d.getY());
            this.c = new ResultPoint(0.0f, this.e.getY());
        } else if (this.d == null) {
            this.d = new ResultPoint(this.a.getWidth() - 1, this.b.getY());
            this.e = new ResultPoint(this.a.getWidth() - 1, this.c.getY());
        }
        this.f = (int) Math.min(this.b.getX(), this.c.getX());
        this.g = (int) Math.max(this.d.getX(), this.e.getX());
        this.h = (int) Math.min(this.b.getY(), this.d.getY());
        this.i = (int) Math.max(this.c.getY(), this.e.getY());
    }

    public ac(ac acVar) {
        BitMatrix bitMatrix = acVar.a;
        ResultPoint resultPoint = acVar.b;
        ResultPoint resultPoint2 = acVar.c;
        ResultPoint resultPoint3 = acVar.d;
        ResultPoint resultPoint4 = acVar.e;
        this.a = bitMatrix;
        this.b = resultPoint;
        this.c = resultPoint2;
        this.d = resultPoint3;
        this.e = resultPoint4;
        a();
    }
}
