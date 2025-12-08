package defpackage;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;

/* loaded from: classes.dex */
public final class mb extends nb {
    public mb(BitArray bitArray) {
        super(bitArray);
    }

    @Override // com.google.zxing.oned.rss.expanded.decoders.AbstractExpandedDecoder
    public String parseInformation() throws NotFoundException, FormatException {
        StringBuilder sbA = g9.a("(01)");
        int length = sbA.length();
        sbA.append(getGeneralDecoder().a(4, 4));
        a(sbA, 8, length);
        return getGeneralDecoder().a(sbA, 48);
    }
}
