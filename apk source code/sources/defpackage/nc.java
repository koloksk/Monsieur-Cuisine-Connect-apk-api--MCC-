package defpackage;

import com.google.zxing.common.BitMatrix;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public abstract class nc {
    public static final nc a = new a("DATA_MASK_000", 0);
    public static final nc b = new nc("DATA_MASK_001", 1) { // from class: nc.b
        {
            a aVar = null;
        }

        @Override // defpackage.nc
        public boolean a(int i2, int i3) {
            return (i2 & 1) == 0;
        }
    };
    public static final nc c = new nc("DATA_MASK_010", 2) { // from class: nc.c
        {
            a aVar = null;
        }

        @Override // defpackage.nc
        public boolean a(int i2, int i3) {
            return i3 % 3 == 0;
        }
    };
    public static final nc d = new nc("DATA_MASK_011", 3) { // from class: nc.d
        {
            a aVar = null;
        }

        @Override // defpackage.nc
        public boolean a(int i2, int i3) {
            return (i2 + i3) % 3 == 0;
        }
    };
    public static final nc e = new nc("DATA_MASK_100", 4) { // from class: nc.e
        {
            a aVar = null;
        }

        @Override // defpackage.nc
        public boolean a(int i2, int i3) {
            return (((i3 / 3) + (i2 / 2)) & 1) == 0;
        }
    };
    public static final nc f = new nc("DATA_MASK_101", 5) { // from class: nc.f
        {
            a aVar = null;
        }

        @Override // defpackage.nc
        public boolean a(int i2, int i3) {
            return (i2 * i3) % 6 == 0;
        }
    };
    public static final nc g = new nc("DATA_MASK_110", 6) { // from class: nc.g
        {
            a aVar = null;
        }

        @Override // defpackage.nc
        public boolean a(int i2, int i3) {
            return (i2 * i3) % 6 < 3;
        }
    };
    public static final nc h;
    public static final /* synthetic */ nc[] i;

    public enum a extends nc {
        public a(String str, int i) {
            super(str, i, null);
        }

        @Override // defpackage.nc
        public boolean a(int i, int i2) {
            return ((i + i2) & 1) == 0;
        }
    }

    static {
        nc ncVar = new nc("DATA_MASK_111", 7) { // from class: nc.h
            {
                a aVar = null;
            }

            @Override // defpackage.nc
            public boolean a(int i2, int i3) {
                return ((((i2 * i3) % 3) + (i2 + i3)) & 1) == 0;
            }
        };
        h = ncVar;
        i = new nc[]{a, b, c, d, e, f, g, ncVar};
    }

    public /* synthetic */ nc(String str, int i2, a aVar) {
    }

    public static nc valueOf(String str) {
        return (nc) Enum.valueOf(nc.class, str);
    }

    public static nc[] values() {
        return (nc[]) i.clone();
    }

    public final void a(BitMatrix bitMatrix, int i2) {
        for (int i3 = 0; i3 < i2; i3++) {
            for (int i4 = 0; i4 < i2; i4++) {
                if (a(i3, i4)) {
                    bitMatrix.flip(i4, i3);
                }
            }
        }
    }

    public abstract boolean a(int i2, int i3);
}
