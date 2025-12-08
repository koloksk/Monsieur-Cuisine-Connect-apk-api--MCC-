package defpackage;

import com.google.zxing.common.BitArray;

/* loaded from: classes.dex */
public final class ea extends ga {
    public final short c;
    public final short d;

    public ea(ga gaVar, int i, int i2) {
        super(gaVar);
        this.c = (short) i;
        this.d = (short) i2;
    }

    @Override // defpackage.ga
    public void a(BitArray bitArray, byte[] bArr) {
        bitArray.appendBits(this.c, this.d);
    }

    public String toString() {
        short s = this.c;
        short s2 = this.d;
        return "<" + Integer.toBinaryString((s & ((1 << s2) - 1)) | (1 << s2) | (1 << this.d)).substring(1) + '>';
    }
}
