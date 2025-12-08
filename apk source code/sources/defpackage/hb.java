package defpackage;

import com.google.zxing.common.BitArray;

/* loaded from: classes.dex */
public final class hb extends lb {
    public hb(BitArray bitArray) {
        super(bitArray);
    }

    @Override // defpackage.ob
    public int a(int i) {
        return i < 10000 ? i : i - 10000;
    }

    @Override // defpackage.ob
    public void b(StringBuilder sb, int i) {
        if (i < 10000) {
            sb.append("(3202)");
        } else {
            sb.append("(3203)");
        }
    }
}
