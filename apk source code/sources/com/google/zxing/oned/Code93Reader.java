package com.google.zxing.oned;

import com.google.zxing.ChecksumException;
import de.silpion.mc2.BuildConfig;
import okhttp3.internal.http.StatusLine;
import sound.SoundLength;

/* loaded from: classes.dex */
public final class Code93Reader extends OneDReader {
    public static final char[] c = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".toCharArray();
    public static final int[] d;
    public static final int e;
    public final StringBuilder a = new StringBuilder(20);
    public final int[] b = new int[6];

    static {
        int[] iArr = {276, 328, 324, 322, 296, BuildConfig.VERSION_CODE, 290, 336, 274, 266, 424, 420, 418, 404, 402, 394, 360, 356, 354, StatusLine.HTTP_PERM_REDIRECT, 282, 344, 332, 326, SoundLength.SMALL_THRESHOLD, 278, 436, 434, 428, 422, 406, 410, 364, 358, 310, 314, 302, 468, 466, 458, 366, 374, 430, 294, 474, 470, 306, 350};
        d = iArr;
        e = iArr[47];
    }

    public static int a(int[] iArr) {
        int i = 0;
        for (int i2 : iArr) {
            i += i2;
        }
        int length = iArr.length;
        int i3 = 0;
        for (int i4 = 0; i4 < length; i4++) {
            int iRound = Math.round((iArr[i4] * 9.0f) / i);
            if (iRound <= 0 || iRound > 4) {
                return -1;
            }
            if ((i4 & 1) == 0) {
                for (int i5 = 0; i5 < iRound; i5++) {
                    i3 = (i3 << 1) | 1;
                }
            } else {
                i3 <<= iRound;
            }
        }
        return i3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0065, code lost:
    
        r8 = com.google.zxing.oned.Code93Reader.c[r9];
        r7.append(r8);
        r9 = r6.length;
        r10 = 0;
        r12 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x006f, code lost:
    
        if (r10 >= r9) goto L116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0071, code lost:
    
        r12 = r12 + r6[r10];
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0077, code lost:
    
        r9 = r18.getNextSet(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x007d, code lost:
    
        if (r8 != '*') goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x007f, code lost:
    
        r7.deleteCharAt(r7.length() - 1);
        r8 = r6.length;
        r10 = 0;
        r12 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x008a, code lost:
    
        if (r10 >= r8) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x008c, code lost:
    
        r12 = r12 + r6[r10];
        r10 = r10 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0092, code lost:
    
        if (r9 == r5) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0098, code lost:
    
        if (r18.get(r9) == false) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x009e, code lost:
    
        if (r7.length() < 2) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00a0, code lost:
    
        r1 = r7.length();
        a(r7, r1 - 2, 20);
        a(r7, r1 - 1, 15);
        r7.setLength(r7.length() - 2);
        r1 = r7.length();
        r5 = new java.lang.StringBuilder(r1);
        r6 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00c4, code lost:
    
        if (r6 >= r1) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00c6, code lost:
    
        r8 = r7.charAt(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00cc, code lost:
    
        if (r8 < 'a') goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00d0, code lost:
    
        if (r8 > 'd') goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00d4, code lost:
    
        if (r6 >= (r1 - 1)) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x00d6, code lost:
    
        r6 = r6 + 1;
        r9 = r7.charAt(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00e2, code lost:
    
        switch(r8) {
            case 97: goto L79;
            case 98: goto L55;
            case 99: goto L48;
            case 100: goto L43;
            default: goto L42;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00e5, code lost:
    
        r8 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00e8, code lost:
    
        if (r9 < 'A') goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00ea, code lost:
    
        if (r9 > 'Z') goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00ec, code lost:
    
        r9 = r9 + ' ';
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00f3, code lost:
    
        throw com.google.zxing.FormatException.getFormatInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00f4, code lost:
    
        if (r9 < 'A') goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00f6, code lost:
    
        if (r9 > 'O') goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00f8, code lost:
    
        r9 = r9 - ' ';
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00fb, code lost:
    
        if (r9 != 'Z') goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00fd, code lost:
    
        r8 = ':';
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0104, code lost:
    
        throw com.google.zxing.FormatException.getFormatInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0105, code lost:
    
        if (r9 < 'A') goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0109, code lost:
    
        if (r9 > 'E') goto L59;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x010b, code lost:
    
        r9 = r9 - '&';
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0110, code lost:
    
        if (r9 < 'F') goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0114, code lost:
    
        if (r9 > 'J') goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0116, code lost:
    
        r9 = r9 - 11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x011b, code lost:
    
        if (r9 < 'K') goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x011d, code lost:
    
        if (r9 > 'O') goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x011f, code lost:
    
        r9 = r9 + 16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0124, code lost:
    
        if (r9 < 'P') goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0128, code lost:
    
        if (r9 > 'S') goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x012a, code lost:
    
        r9 = r9 + '+';
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x012f, code lost:
    
        if (r9 < 'T') goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0131, code lost:
    
        if (r9 > 'Z') goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0133, code lost:
    
        r8 = 127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x013a, code lost:
    
        throw com.google.zxing.FormatException.getFormatInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x013b, code lost:
    
        if (r9 < 'A') goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x013d, code lost:
    
        if (r9 > 'Z') goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x013f, code lost:
    
        r9 = r9 - '@';
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0141, code lost:
    
        r8 = (char) r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0147, code lost:
    
        throw com.google.zxing.FormatException.getFormatInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0148, code lost:
    
        r5.append(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0150, code lost:
    
        throw com.google.zxing.FormatException.getFormatInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0151, code lost:
    
        r5.append(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0154, code lost:
    
        r6 = r6 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0157, code lost:
    
        r9 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0183, code lost:
    
        return new com.google.zxing.Result(r5.toString(), null, new com.google.zxing.ResultPoint[]{new com.google.zxing.ResultPoint((r2[1] + r2[0]) / 2.0f, r9), new com.google.zxing.ResultPoint((r12 / 2.0f) + r4, r9)}, com.google.zxing.BarcodeFormat.CODE_93);
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0188, code lost:
    
        throw com.google.zxing.NotFoundException.getNotFoundInstance();
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x018d, code lost:
    
        throw com.google.zxing.NotFoundException.getNotFoundInstance();
     */
    @Override // com.google.zxing.oned.OneDReader
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.zxing.Result decodeRow(int r17, com.google.zxing.common.BitArray r18, java.util.Map<com.google.zxing.DecodeHintType, ?> r19) throws com.google.zxing.NotFoundException, com.google.zxing.ChecksumException, com.google.zxing.FormatException {
        /*
            Method dump skipped, instructions count: 468
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.oned.Code93Reader.decodeRow(int, com.google.zxing.common.BitArray, java.util.Map):com.google.zxing.Result");
    }

    public static void a(CharSequence charSequence, int i, int i2) throws ChecksumException {
        int iIndexOf = 0;
        int i3 = 1;
        for (int i4 = i - 1; i4 >= 0; i4--) {
            iIndexOf += "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ-. $/+%abcd*".indexOf(charSequence.charAt(i4)) * i3;
            i3++;
            if (i3 > i2) {
                i3 = 1;
            }
        }
        if (charSequence.charAt(i) != c[iIndexOf % 47]) {
            throw ChecksumException.getChecksumInstance();
        }
    }
}
