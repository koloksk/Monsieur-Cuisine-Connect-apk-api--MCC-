package defpackage;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* loaded from: classes.dex */
public final class ib extends nb {
    public ib(BitArray bitArray) {
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
        sb.append("(392");
        sb.append(iA);
        sb.append(')');
        sb.append(getGeneralDecoder().a(50, (String) null).b);
        return sb.toString();
    }
}
