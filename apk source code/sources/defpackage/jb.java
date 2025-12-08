package defpackage;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* loaded from: classes.dex */
public final class jb extends nb {
    public jb(BitArray bitArray) {
        super(bitArray);
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder
    public String parseInformation() throws NotFoundException, FormatException {
        if (getInformation().getSize() < 48) {
            throw NotFoundException.getNotFoundInstance();
        }
        StringBuilder sb = new StringBuilder();
        a(sb, 8);
        int iA = getGeneralDecoder().a(48, 2);
        sb.append("(393");
        sb.append(iA);
        sb.append(')');
        int iA2 = getGeneralDecoder().a(50, 10);
        if (iA2 / 100 == 0) {
            sb.append('0');
        }
        if (iA2 / 10 == 0) {
            sb.append('0');
        }
        sb.append(iA2);
        sb.append(getGeneralDecoder().a(60, (String) null).b);
        return sb.toString();
    }
}
