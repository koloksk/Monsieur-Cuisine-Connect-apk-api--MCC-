package defpackage;

import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.Version;

/* loaded from: classes.dex */
public final class lc {
    public final BitMatrix a;
    public Version b;
    public pc c;
    public boolean d;

    public lc(BitMatrix bitMatrix) throws FormatException {
        int height = bitMatrix.getHeight();
        if (height < 21 || (height & 3) != 1) {
            throw FormatException.getFormatInstance();
        }
        this.a = bitMatrix;
    }

    public final int a(int i, int i2, int i3) {
        return this.d ? this.a.get(i2, i) : this.a.get(i, i2) ? (i3 << 1) | 1 : i3 << 1;
    }

    public pc b() throws FormatException {
        pc pcVar = this.c;
        if (pcVar != null) {
            return pcVar;
        }
        int iA = 0;
        int iA2 = 0;
        for (int i = 0; i < 6; i++) {
            iA2 = a(i, 8, iA2);
        }
        int iA3 = a(8, 7, a(8, 8, a(7, 8, iA2)));
        for (int i2 = 5; i2 >= 0; i2--) {
            iA3 = a(8, i2, iA3);
        }
        int height = this.a.getHeight();
        int i3 = height - 7;
        for (int i4 = height - 1; i4 >= i3; i4--) {
            iA = a(8, i4, iA);
        }
        for (int i5 = height - 8; i5 < height; i5++) {
            iA = a(i5, 8, iA);
        }
        pc pcVarA = pc.a(iA3, iA);
        if (pcVarA == null) {
            pcVarA = pc.a(iA3 ^ 21522, iA ^ 21522);
        }
        this.c = pcVarA;
        if (pcVarA != null) {
            return pcVarA;
        }
        throw FormatException.getFormatInstance();
    }

    public Version c() throws FormatException {
        Version version = this.b;
        if (version != null) {
            return version;
        }
        int height = this.a.getHeight();
        int i = (height - 17) / 4;
        if (i <= 6) {
            return Version.getVersionForNumber(i);
        }
        int i2 = height - 11;
        int iA = 0;
        int iA2 = 0;
        for (int i3 = 5; i3 >= 0; i3--) {
            for (int i4 = height - 9; i4 >= i2; i4--) {
                iA2 = a(i4, i3, iA2);
            }
        }
        Version versionA = Version.a(iA2);
        if (versionA != null && versionA.getDimensionForVersion() == height) {
            this.b = versionA;
            return versionA;
        }
        for (int i5 = 5; i5 >= 0; i5--) {
            for (int i6 = height - 9; i6 >= i2; i6--) {
                iA = a(i5, i6, iA);
            }
        }
        Version versionA2 = Version.a(iA);
        if (versionA2 == null || versionA2.getDimensionForVersion() != height) {
            throw FormatException.getFormatInstance();
        }
        this.b = versionA2;
        return versionA2;
    }

    public void d() {
        if (this.c == null) {
            return;
        }
        nc.values()[this.c.b].a(this.a, this.a.getHeight());
    }

    public void a() {
        int i = 0;
        while (i < this.a.getWidth()) {
            int i2 = i + 1;
            for (int i3 = i2; i3 < this.a.getHeight(); i3++) {
                if (this.a.get(i, i3) != this.a.get(i3, i)) {
                    this.a.flip(i3, i);
                    this.a.flip(i, i3);
                }
            }
            i = i2;
        }
    }
}
