package com.google.zxing.qrcode.decoder;

/* loaded from: classes.dex */
public enum ErrorCorrectionLevel {
    L(1),
    M(0),
    Q(3),
    H(2);

    public static final ErrorCorrectionLevel[] b;
    public final int a;

    static {
        ErrorCorrectionLevel errorCorrectionLevel = H;
        ErrorCorrectionLevel errorCorrectionLevel2 = L;
        b = new ErrorCorrectionLevel[]{M, errorCorrectionLevel2, errorCorrectionLevel, Q};
    }

    ErrorCorrectionLevel(int i) {
        this.a = i;
    }

    public static ErrorCorrectionLevel forBits(int i) {
        if (i >= 0) {
            ErrorCorrectionLevel[] errorCorrectionLevelArr = b;
            if (i < errorCorrectionLevelArr.length) {
                return errorCorrectionLevelArr[i];
            }
        }
        throw new IllegalArgumentException();
    }

    public int getBits() {
        return this.a;
    }
}
