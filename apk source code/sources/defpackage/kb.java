package defpackage;

import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* loaded from: classes.dex */
public final class kb extends ob {
    public final String c;
    public final String d;

    public kb(BitArray bitArray, String str, String str2) {
        super(bitArray);
        this.c = str2;
        this.d = str;
    }

    @Override // defpackage.ob
    public int a(int i) {
        return i % 100000;
    }

    @Override // defpackage.ob
    public void b(StringBuilder sb, int i) {
        sb.append('(');
        sb.append(this.d);
        sb.append(i / 100000);
        sb.append(')');
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder
    public String parseInformation() throws NotFoundException {
        if (getInformation().getSize() != 84) {
            throw NotFoundException.getNotFoundInstance();
        }
        StringBuilder sb = new StringBuilder();
        a(sb, 8);
        b(sb, 48, 20);
        int iA = xb.a(getGeneralDecoder().a, 68, 16);
        if (iA != 38400) {
            sb.append('(');
            sb.append(this.c);
            sb.append(')');
            int i = iA % 32;
            int i2 = iA / 32;
            int i3 = (i2 % 12) + 1;
            int i4 = i2 / 12;
            if (i4 / 10 == 0) {
                sb.append('0');
            }
            sb.append(i4);
            if (i3 / 10 == 0) {
                sb.append('0');
            }
            sb.append(i3);
            if (i / 10 == 0) {
                sb.append('0');
            }
            sb.append(i);
        }
        return sb.toString();
    }
}
