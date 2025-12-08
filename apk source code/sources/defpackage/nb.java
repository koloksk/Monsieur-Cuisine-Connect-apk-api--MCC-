package defpackage;

import com.google.zxing.common.BitArray;
import com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder;

/* loaded from: classes.dex */
public abstract class nb extends AbstractExpandedDecoder {
    public nb(BitArray bitArray) {
        super(bitArray);
    }

    public final void a(StringBuilder sb, int i) {
        sb.append("(01)");
        int length = sb.length();
        sb.append('9');
        a(sb, i, length);
    }

    public final void a(StringBuilder sb, int i, int i2) {
        for (int i3 = 0; i3 < 4; i3++) {
            int iA = getGeneralDecoder().a((i3 * 10) + i, 10);
            if (iA / 100 == 0) {
                sb.append('0');
            }
            if (iA / 10 == 0) {
                sb.append('0');
            }
            sb.append(iA);
        }
        int i4 = 0;
        for (int i5 = 0; i5 < 13; i5++) {
            int iCharAt = sb.charAt(i5 + i2) - '0';
            if ((i5 & 1) == 0) {
                iCharAt *= 3;
            }
            i4 += iCharAt;
        }
        int i6 = 10 - (i4 % 10);
        sb.append(i6 != 10 ? i6 : 0);
    }
}
