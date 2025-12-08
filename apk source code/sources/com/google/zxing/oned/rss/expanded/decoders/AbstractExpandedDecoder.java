package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.common.BitArray;
import defpackage.gb;
import defpackage.hb;
import defpackage.ib;
import defpackage.jb;
import defpackage.kb;
import defpackage.mb;
import defpackage.pb;
import defpackage.xb;

/* loaded from: classes.dex */
public abstract class AbstractExpandedDecoder {
    public final BitArray a;
    public final xb b;

    public AbstractExpandedDecoder(BitArray bitArray) {
        this.a = bitArray;
        this.b = new xb(bitArray);
    }

    public static AbstractExpandedDecoder createDecoder(BitArray bitArray) {
        if (bitArray.get(1)) {
            return new mb(bitArray);
        }
        if (!bitArray.get(2)) {
            return new pb(bitArray);
        }
        int iA = xb.a(bitArray, 1, 4);
        if (iA == 4) {
            return new gb(bitArray);
        }
        if (iA == 5) {
            return new hb(bitArray);
        }
        int iA2 = xb.a(bitArray, 1, 5);
        if (iA2 == 12) {
            return new ib(bitArray);
        }
        if (iA2 == 13) {
            return new jb(bitArray);
        }
        switch (xb.a(bitArray, 1, 7)) {
            case 56:
                return new kb(bitArray, "310", "11");
            case 57:
                return new kb(bitArray, "320", "11");
            case 58:
                return new kb(bitArray, "310", "13");
            case 59:
                return new kb(bitArray, "320", "13");
            case 60:
                return new kb(bitArray, "310", "15");
            case 61:
                return new kb(bitArray, "320", "15");
            case 62:
                return new kb(bitArray, "310", "17");
            case 63:
                return new kb(bitArray, "320", "17");
            default:
                throw new IllegalStateException("unknown decoder: " + bitArray);
        }
    }

    public final xb getGeneralDecoder() {
        return this.b;
    }

    public final BitArray getInformation() {
        return this.a;
    }

    public abstract String parseInformation() throws NotFoundException, FormatException;
}
