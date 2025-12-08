package defpackage;

import com.google.zxing.FormatException;

/* loaded from: classes.dex */
public final class ub extends vb {
    public final int b;
    public final int c;

    public ub(int i, int i2, int i3) throws FormatException {
        super(i);
        if (i2 < 0 || i2 > 10 || i3 < 0 || i3 > 10) {
            throw FormatException.getFormatInstance();
        }
        this.b = i2;
        this.c = i3;
    }
}
