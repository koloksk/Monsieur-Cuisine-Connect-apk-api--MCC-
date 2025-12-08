package com.google.zxing.datamatrix.decoder;

import com.google.zxing.FormatException;
import model.Presets;

/* loaded from: classes.dex */
public final class Version {
    public static final Version[] h;
    public final int a;
    public final int b;
    public final int c;
    public final int d;
    public final int e;
    public final c f;
    public final int g;

    public static final class b {
        public final int a;
        public final int b;

        public /* synthetic */ b(int i, int i2, a aVar) {
            this.a = i;
            this.b = i2;
        }
    }

    static {
        int i = 1;
        a aVar = null;
        int i2 = 5;
        Version version = new Version(3, 14, 14, 12, 12, new c(10, new b(i, 8, aVar), aVar));
        int i3 = 2;
        int i4 = 12;
        int i5 = 18;
        int i6 = 14;
        int i7 = 36;
        b bVar = new b(i, 44, aVar);
        b bVar2 = new b(i, 86, aVar);
        int i8 = 42;
        int i9 = 114;
        int i10 = 48;
        int i11 = 144;
        int i12 = 56;
        int i13 = 174;
        b bVar3 = new b(i, i13, aVar);
        h = new Version[]{new Version(1, 10, 10, 8, 8, new c(i2, new b(i, 3, aVar), aVar)), new Version(2, 12, 12, 10, 10, new c(7, new b(i, i2, aVar), aVar)), version, new Version(4, 16, 16, 14, 14, new c(i4, new b(i, i4, aVar), aVar)), new Version(5, 18, 18, 16, 16, new c(i6, new b(i, i5, aVar), aVar)), new Version(6, 20, 20, 18, 18, new c(i5, new b(i, 22, aVar), aVar)), new Version(7, 22, 22, 20, 20, new c(20, new b(i, 30, aVar), aVar)), new Version(8, 24, 24, 22, 22, new c(24, new b(i, i7, aVar), aVar)), new Version(9, 26, 26, 24, 24, new c(28, bVar, aVar)), new Version(10, 32, 32, 14, 14, new c(i7, new b(i, 62, aVar), aVar)), new Version(11, 36, 36, 16, 16, new c(i8, bVar2, aVar)), new Version(12, 40, 40, 18, 18, new c(i10, new b(i, i9, aVar), aVar)), new Version(13, 44, 44, 20, 20, new c(i12, new b(i, i11, aVar), aVar)), new Version(14, 48, 48, 22, 22, new c(68, bVar3, aVar)), new Version(15, 52, 52, 24, 24, new c(i8, new b(i3, 102, aVar), aVar)), new Version(16, 64, 64, 14, 14, new c(i12, new b(i3, 140, aVar), aVar)), new Version(17, 72, 72, 16, 16, new c(i7, new b(4, 92, aVar), aVar)), new Version(18, 80, 80, 18, 18, new c(i10, new b(4, i9, aVar), aVar)), new Version(19, 88, 88, 20, 20, new c(i12, new b(4, i11, aVar), aVar)), new Version(20, 96, 96, 22, 22, new c(68, new b(4, i13, aVar), aVar)), new Version(21, 104, 104, 24, 24, new c(i12, new b(6, 136, aVar), aVar)), new Version(22, Presets.KNEADING_MAX_TIME, Presets.KNEADING_MAX_TIME, 18, 18, new c(68, new b(6, 175, aVar), aVar)), new Version(23, 132, 132, 20, 20, new c(62, new b(8, 163, aVar), aVar)), new Version(24, 144, 144, 22, 22, new c(62, new b(8, 156, aVar), new b(i3, 155, aVar), aVar)), new Version(25, 8, 18, 6, 16, new c(7, new b(1, 5, aVar), aVar)), new Version(26, 8, 32, 6, 14, new c(11, new b(1, 10, aVar), aVar)), new Version(27, 12, 26, 10, 24, new c(i6, new b(1, 16, aVar), aVar)), new Version(28, 12, 36, 10, 16, new c(i5, new b(1, 22, aVar), aVar)), new Version(29, 16, 36, 14, 16, new c(24, new b(1, 32, aVar), aVar)), new Version(30, 16, 48, 14, 22, new c(28, new b(1, 49, aVar), aVar))};
    }

    public Version(int i, int i2, int i3, int i4, int i5, c cVar) {
        this.a = i;
        this.b = i2;
        this.c = i3;
        this.d = i4;
        this.e = i5;
        this.f = cVar;
        int i6 = cVar.a;
        int i7 = 0;
        for (b bVar : cVar.b) {
            i7 += (bVar.b + i6) * bVar.a;
        }
        this.g = i7;
    }

    public static Version getVersionForDimensions(int i, int i2) throws FormatException {
        if ((i & 1) != 0 || (i2 & 1) != 0) {
            throw FormatException.getFormatInstance();
        }
        for (Version version : h) {
            if (version.b == i && version.c == i2) {
                return version;
            }
        }
        throw FormatException.getFormatInstance();
    }

    public int getDataRegionSizeColumns() {
        return this.e;
    }

    public int getDataRegionSizeRows() {
        return this.d;
    }

    public int getSymbolSizeColumns() {
        return this.c;
    }

    public int getSymbolSizeRows() {
        return this.b;
    }

    public int getTotalCodewords() {
        return this.g;
    }

    public int getVersionNumber() {
        return this.a;
    }

    public String toString() {
        return String.valueOf(this.a);
    }

    public static final class c {
        public final int a;
        public final b[] b;

        public /* synthetic */ c(int i, b bVar, a aVar) {
            this.a = i;
            this.b = new b[]{bVar};
        }

        public /* synthetic */ c(int i, b bVar, b bVar2, a aVar) {
            this.a = i;
            this.b = new b[]{bVar, bVar2};
        }
    }
}
