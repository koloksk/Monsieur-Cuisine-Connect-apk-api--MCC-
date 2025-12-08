package com.google.zxing.aztec;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Writer;
import com.google.zxing.common.BitMatrix;
import java.nio.charset.Charset;
import org.apache.commons.lang3.CharEncoding;

/* loaded from: classes.dex */
public final class AztecWriter implements Writer {
    public static final Charset a = Charset.forName(CharEncoding.ISO_8859_1);

    @Override // com.google.zxing.Writer
    public BitMatrix encode(String str, BarcodeFormat barcodeFormat, int i, int i2) {
        return encode(str, barcodeFormat, i, i2, null);
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x004a A[PHI: r0 r2
  0x004a: PHI (r0v1 java.nio.charset.Charset) = (r0v0 java.nio.charset.Charset), (r0v5 java.nio.charset.Charset) binds: [B:3:0x0005, B:11:0x0039] A[DONT_GENERATE, DONT_INLINE]
  0x004a: PHI (r2v1 int) = (r2v0 int), (r2v7 int) binds: [B:3:0x0005, B:11:0x0039] A[DONT_GENERATE, DONT_INLINE]] */
    @Override // com.google.zxing.Writer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.zxing.common.BitMatrix encode(java.lang.String r8, com.google.zxing.BarcodeFormat r9, int r10, int r11, java.util.Map<com.google.zxing.EncodeHintType, ?> r12) throws java.lang.NumberFormatException {
        /*
            r7 = this;
            java.nio.charset.Charset r0 = com.google.zxing.aztec.AztecWriter.a
            r1 = 0
            r2 = 33
            if (r12 == 0) goto L4a
            com.google.zxing.EncodeHintType r3 = com.google.zxing.EncodeHintType.CHARACTER_SET
            boolean r3 = r12.containsKey(r3)
            if (r3 == 0) goto L1d
            com.google.zxing.EncodeHintType r0 = com.google.zxing.EncodeHintType.CHARACTER_SET
            java.lang.Object r0 = r12.get(r0)
            java.lang.String r0 = r0.toString()
            java.nio.charset.Charset r0 = java.nio.charset.Charset.forName(r0)
        L1d:
            com.google.zxing.EncodeHintType r3 = com.google.zxing.EncodeHintType.ERROR_CORRECTION
            boolean r3 = r12.containsKey(r3)
            if (r3 == 0) goto L33
            com.google.zxing.EncodeHintType r2 = com.google.zxing.EncodeHintType.ERROR_CORRECTION
            java.lang.Object r2 = r12.get(r2)
            java.lang.String r2 = r2.toString()
            int r2 = java.lang.Integer.parseInt(r2)
        L33:
            com.google.zxing.EncodeHintType r3 = com.google.zxing.EncodeHintType.AZTEC_LAYERS
            boolean r3 = r12.containsKey(r3)
            if (r3 == 0) goto L4a
            com.google.zxing.EncodeHintType r3 = com.google.zxing.EncodeHintType.AZTEC_LAYERS
            java.lang.Object r12 = r12.get(r3)
            java.lang.String r12 = r12.toString()
            int r12 = java.lang.Integer.parseInt(r12)
            goto L4b
        L4a:
            r12 = r1
        L4b:
            com.google.zxing.BarcodeFormat r3 = com.google.zxing.BarcodeFormat.AZTEC
            if (r9 != r3) goto La5
            byte[] r8 = r8.getBytes(r0)
            com.google.zxing.aztec.encoder.AztecCode r8 = com.google.zxing.aztec.encoder.Encoder.encode(r8, r2, r12)
            com.google.zxing.common.BitMatrix r8 = r8.getMatrix()
            if (r8 == 0) goto L9f
            int r9 = r8.getWidth()
            int r12 = r8.getHeight()
            int r10 = java.lang.Math.max(r10, r9)
            int r11 = java.lang.Math.max(r11, r12)
            int r0 = r10 / r9
            int r2 = r11 / r12
            int r0 = java.lang.Math.min(r0, r2)
            int r2 = r9 * r0
            int r2 = r10 - r2
            int r2 = r2 / 2
            int r3 = r12 * r0
            int r3 = r11 - r3
            int r3 = r3 / 2
            com.google.zxing.common.BitMatrix r4 = new com.google.zxing.common.BitMatrix
            r4.<init>(r10, r11)
            r10 = r1
        L87:
            if (r10 >= r12) goto L9e
            r11 = r1
            r5 = r2
        L8b:
            if (r11 >= r9) goto L9a
            boolean r6 = r8.get(r11, r10)
            if (r6 == 0) goto L96
            r4.setRegion(r5, r3, r0, r0)
        L96:
            int r11 = r11 + 1
            int r5 = r5 + r0
            goto L8b
        L9a:
            int r10 = r10 + 1
            int r3 = r3 + r0
            goto L87
        L9e:
            return r4
        L9f:
            java.lang.IllegalStateException r8 = new java.lang.IllegalStateException
            r8.<init>()
            throw r8
        La5:
            java.lang.IllegalArgumentException r8 = new java.lang.IllegalArgumentException
            java.lang.StringBuilder r10 = new java.lang.StringBuilder
            java.lang.String r11 = "Can only encode AZTEC, but got "
            r10.<init>(r11)
            r10.append(r9)
            java.lang.String r9 = r10.toString()
            r8.<init>(r9)
            throw r8
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.aztec.AztecWriter.encode(java.lang.String, com.google.zxing.BarcodeFormat, int, int, java.util.Map):com.google.zxing.common.BitMatrix");
    }
}
