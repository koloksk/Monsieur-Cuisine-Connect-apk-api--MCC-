package com.google.zxing.qrcode.encoder;

import com.google.zxing.WriterException;
import com.google.zxing.common.BitArray;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;

/* loaded from: classes.dex */
public final class Encoder {
    public static final int[] a = {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 36, -1, -1, -1, 37, 38, -1, -1, -1, -1, 39, 40, -1, 41, 42, 43, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 44, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, -1, -1, -1, -1, -1};

    public static int a(Mode mode, BitArray bitArray, BitArray bitArray2, Version version) {
        return bitArray2.getSize() + mode.getCharacterCountBits(version) + bitArray.getSize();
    }

    public static Mode chooseMode(String str) {
        return a(str, (String) null);
    }

    public static QRCode encode(String str, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        return encode(str, errorCorrectionLevel, null);
    }

    public static int a(int i) {
        int[] iArr = a;
        if (i < iArr.length) {
            return iArr[i];
        }
        return -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:260:0x00af A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x009f A[LOOP:0: B:23:0x0071->B:37:0x009f, LOOP_END] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.zxing.qrcode.encoder.QRCode encode(java.lang.String r22, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel r23, java.util.Map<com.google.zxing.EncodeHintType, ?> r24) throws com.google.zxing.WriterException, java.io.UnsupportedEncodingException {
        /*
            Method dump skipped, instructions count: 1362
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.encoder.Encoder.encode(java.lang.String, com.google.zxing.qrcode.decoder.ErrorCorrectionLevel, java.util.Map):com.google.zxing.qrcode.encoder.QRCode");
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.zxing.qrcode.decoder.Mode a(java.lang.String r6, java.lang.String r7) throws java.io.UnsupportedEncodingException {
        /*
            java.lang.String r0 = "Shift_JIS"
            boolean r7 = r0.equals(r7)
            r1 = 1
            r2 = 0
            if (r7 == 0) goto L37
            byte[] r7 = r6.getBytes(r0)     // Catch: java.io.UnsupportedEncodingException -> L31
            int r0 = r7.length
            int r3 = r0 % 2
            if (r3 == 0) goto L14
            goto L31
        L14:
            r3 = r2
        L15:
            if (r3 >= r0) goto L2f
            r4 = r7[r3]
            r4 = r4 & 255(0xff, float:3.57E-43)
            r5 = 129(0x81, float:1.81E-43)
            if (r4 < r5) goto L23
            r5 = 159(0x9f, float:2.23E-43)
            if (r4 <= r5) goto L2c
        L23:
            r5 = 224(0xe0, float:3.14E-43)
            if (r4 < r5) goto L31
            r5 = 235(0xeb, float:3.3E-43)
            if (r4 <= r5) goto L2c
            goto L31
        L2c:
            int r3 = r3 + 2
            goto L15
        L2f:
            r7 = r1
            goto L32
        L31:
            r7 = r2
        L32:
            if (r7 == 0) goto L37
            com.google.zxing.qrcode.decoder.Mode r6 = com.google.zxing.qrcode.decoder.Mode.KANJI
            return r6
        L37:
            r7 = r2
            r0 = r7
        L39:
            int r3 = r6.length()
            if (r2 >= r3) goto L5b
            char r3 = r6.charAt(r2)
            r4 = 48
            if (r3 < r4) goto L4d
            r4 = 57
            if (r3 > r4) goto L4d
            r0 = r1
            goto L55
        L4d:
            int r7 = a(r3)
            r3 = -1
            if (r7 == r3) goto L58
            r7 = r1
        L55:
            int r2 = r2 + 1
            goto L39
        L58:
            com.google.zxing.qrcode.decoder.Mode r6 = com.google.zxing.qrcode.decoder.Mode.BYTE
            return r6
        L5b:
            if (r7 == 0) goto L60
            com.google.zxing.qrcode.decoder.Mode r6 = com.google.zxing.qrcode.decoder.Mode.ALPHANUMERIC
            return r6
        L60:
            if (r0 == 0) goto L65
            com.google.zxing.qrcode.decoder.Mode r6 = com.google.zxing.qrcode.decoder.Mode.NUMERIC
            return r6
        L65:
            com.google.zxing.qrcode.decoder.Mode r6 = com.google.zxing.qrcode.decoder.Mode.BYTE
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.qrcode.encoder.Encoder.a(java.lang.String, java.lang.String):com.google.zxing.qrcode.decoder.Mode");
    }

    public static Version a(int i, ErrorCorrectionLevel errorCorrectionLevel) throws WriterException {
        for (int i2 = 1; i2 <= 40; i2++) {
            Version versionForNumber = Version.getVersionForNumber(i2);
            if (a(i, versionForNumber, errorCorrectionLevel)) {
                return versionForNumber;
            }
        }
        throw new WriterException("Data too big");
    }

    public static boolean a(int i, Version version, ErrorCorrectionLevel errorCorrectionLevel) {
        return version.getTotalCodewords() - version.getECBlocksForLevel(errorCorrectionLevel).getTotalECCodewords() >= (i + 7) / 8;
    }
}
