package defpackage;

import com.google.zxing.common.BitArray;

/* loaded from: classes.dex */
public abstract class ob extends nb {
    public ob(BitArray bitArray) {
        super(bitArray);
    }

    public abstract int a(int i);

    public abstract void b(StringBuilder sb, int i);

    public final void b(StringBuilder sb, int i, int i2) {
        int iA = xb.a(getGeneralDecoder().a, i, i2);
        b(sb, iA);
        int iA2 = a(iA);
        int i3 = 100000;
        for (int i4 = 0; i4 < 5; i4++) {
            if (iA2 / i3 == 0) {
                sb.append('0');
            }
            i3 /= 10;
        }
        sb.append(iA2);
    }
}
