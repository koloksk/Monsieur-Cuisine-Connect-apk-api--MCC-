package com.google.zxing.oned;

import de.silpion.mc2.BuildConfig;

/* loaded from: classes.dex */
public final class Code39Reader extends OneDReader {
    public static final int[] e;
    public static final int f;
    public final boolean a;
    public final boolean b;
    public final StringBuilder c;
    public final int[] d;

    static {
        int[] iArr = {52, 289, 97, 352, 49, 304, 112, 37, BuildConfig.VERSION_CODE, 100, 265, 73, 328, 25, 280, 88, 13, 268, 76, 28, 259, 67, 322, 19, 274, 82, 7, 262, 70, 22, 385, 193, 448, 145, 400, 208, 133, 388, 196, 148, 168, 162, 138, 42};
        e = iArr;
        f = iArr[39];
    }

    public Code39Reader() {
        this(false);
    }

    public static int a(int[] iArr) {
        int length = iArr.length;
        int i = 0;
        while (true) {
            int i2 = Integer.MAX_VALUE;
            for (int i3 : iArr) {
                if (i3 < i2 && i3 > i) {
                    i2 = i3;
                }
            }
            int i4 = 0;
            int i5 = 0;
            int i6 = 0;
            for (int i7 = 0; i7 < length; i7++) {
                int i8 = iArr[i7];
                if (i8 > i2) {
                    i5 |= 1 << ((length - 1) - i7);
                    i4++;
                    i6 += i8;
                }
            }
            if (i4 == 3) {
                for (int i9 = 0; i9 < length && i4 > 0; i9++) {
                    int i10 = iArr[i9];
                    if (i10 > i2) {
                        i4--;
                        if ((i10 << 1) >= i6) {
                            return -1;
                        }
                    }
                }
                return i5;
            }
            if (i4 <= 3) {
                return -1;
            }
            i = i2;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0175, code lost:
    
        r8 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x01a2, code lost:
    
        return new com.google.zxing.Result(r1, null, new com.google.zxing.ResultPoint[]{new com.google.zxing.ResultPoint((r5[1] + r5[0]) / 2.0f, r8), new com.google.zxing.ResultPoint((r10 / 2.0f) + r6, r8)}, com.google.zxing.BarcodeFormat.CODE_39);
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x01a7, code lost:
    
        throw com.google.zxing.NotFoundException.getNotFoundInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x006d, code lost:
    
        r8 = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. *$/+%".charAt(r9);
        r4.append(r8);
        r9 = r2.length;
        r10 = r3;
        r11 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0079, code lost:
    
        if (r10 >= r9) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x007b, code lost:
    
        r11 = r11 + r2[r10];
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0081, code lost:
    
        r9 = r18.getNextSet(r11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0087, code lost:
    
        if (r8 != '*') goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0089, code lost:
    
        r4.setLength(r4.length() - r12);
        r1 = r2.length;
        r8 = r3;
        r10 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0094, code lost:
    
        if (r8 >= r1) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0096, code lost:
    
        r10 = r10 + r2[r8];
        r8 = r8 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x009c, code lost:
    
        r1 = (r9 - r6) - r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x009f, code lost:
    
        if (r9 == r7) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a2, code lost:
    
        if ((r1 << r12) < r10) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00a9, code lost:
    
        throw com.google.zxing.NotFoundException.getNotFoundInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ae, code lost:
    
        if (r16.a == false) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x00b0, code lost:
    
        r1 = r4.length() - r12;
        r7 = r3;
        r8 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00b9, code lost:
    
        if (r7 >= r1) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00bb, code lost:
    
        r8 = r8 + "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".indexOf(r16.c.charAt(r7));
        r7 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d2, code lost:
    
        if (r4.charAt(r1) != "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%".charAt(r8 % 43)) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00d4, code lost:
    
        r4.setLength(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00dc, code lost:
    
        throw com.google.zxing.ChecksumException.getChecksumInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00e1, code lost:
    
        if (r4.length() == 0) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00e5, code lost:
    
        if (r16.b == false) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00e7, code lost:
    
        r1 = r4.length();
        r7 = new java.lang.StringBuilder(r1);
        r8 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00f1, code lost:
    
        if (r8 >= r1) goto L134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x00f3, code lost:
    
        r9 = r4.charAt(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00fd, code lost:
    
        if (r9 == '+') goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00ff, code lost:
    
        if (r9 == '$') goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0101, code lost:
    
        if (r9 == '%') goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0103, code lost:
    
        if (r9 != '/') goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0106, code lost:
    
        r7.append(r9);
        r3 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x010b, code lost:
    
        r8 = r8 + 1;
        r15 = r4.charAt(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0115, code lost:
    
        if (r9 == '$') goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0117, code lost:
    
        if (r9 == '%') goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0119, code lost:
    
        if (r9 == '+') goto L74;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x011b, code lost:
    
        if (r9 == '/') goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x011d, code lost:
    
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x011f, code lost:
    
        if (r15 < 'A') goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0123, code lost:
    
        if (r15 > 'O') goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0125, code lost:
    
        r15 = r15 - ' ';
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0128, code lost:
    
        if (r15 != 'Z') goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x012a, code lost:
    
        r3 = ':';
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0131, code lost:
    
        throw com.google.zxing.FormatException.getFormatInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0132, code lost:
    
        if (r15 < 'A') goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0134, code lost:
    
        if (r15 > 'Z') goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0136, code lost:
    
        r15 = r15 + ' ';
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x013d, code lost:
    
        throw com.google.zxing.FormatException.getFormatInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x013e, code lost:
    
        if (r15 < 'A') goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0142, code lost:
    
        if (r15 > 'E') goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0144, code lost:
    
        r15 = r15 - '&';
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0149, code lost:
    
        if (r15 < 'F') goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x014d, code lost:
    
        if (r15 > 'W') goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x014f, code lost:
    
        r15 = r15 - 11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0156, code lost:
    
        throw com.google.zxing.FormatException.getFormatInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0157, code lost:
    
        if (r15 < 'A') goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0159, code lost:
    
        if (r15 > 'Z') goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x015b, code lost:
    
        r15 = r15 - '@';
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x015d, code lost:
    
        r3 = (char) r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x015e, code lost:
    
        r7.append(r3);
        r3 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0162, code lost:
    
        r8 = r8 + r3;
        r12 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x016b, code lost:
    
        throw com.google.zxing.FormatException.getFormatInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x016c, code lost:
    
        r1 = r7.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0171, code lost:
    
        r1 = r4.toString();
     */
    @Override // com.google.zxing.oned.OneDReader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.zxing.Result decodeRow(int r17, com.google.zxing.common.BitArray r18, java.util.Map<com.google.zxing.DecodeHintType, ?> r19) throws com.google.zxing.NotFoundException, com.google.zxing.ChecksumException, com.google.zxing.FormatException {
        /*
            Method dump skipped, instructions count: 489
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.Code39Reader.decodeRow(int, com.google.zxing.common.BitArray, java.util.Map):com.google.zxing.Result");
    }

    public Code39Reader(boolean z) {
        this(z, false);
    }

    public Code39Reader(boolean z, boolean z2) {
        this.a = z;
        this.b = z2;
        this.c = new StringBuilder(20);
        this.d = new int[9];
    }
}
