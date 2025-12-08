package defpackage;

/* loaded from: classes.dex */
public final class ra implements sa {
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0051, code lost:
    
        com.google.zxing.datamatrix.encoder.HighLevelEncoder.a(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0054, code lost:
    
        throw null;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007b  */
    @Override // defpackage.sa
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(defpackage.ta r10) {
        /*
            r9 = this;
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
        L5:
            boolean r1 = r10.d()
            r2 = 1
            r3 = 0
            r4 = 4
            r5 = 0
            if (r1 == 0) goto L55
            char r1 = r10.b()
            r6 = 32
            if (r1 < r6) goto L1f
            r6 = 63
            if (r1 > r6) goto L1f
            r0.append(r1)
            goto L2d
        L1f:
            r6 = 64
            if (r1 < r6) goto L51
            r6 = 94
            if (r1 > r6) goto L51
            int r1 = r1 + (-64)
            char r1 = (char) r1
            r0.append(r1)
        L2d:
            int r1 = r10.f
            int r1 = r1 + r2
            r10.f = r1
            int r1 = r0.length()
            if (r1 < r4) goto L5
            java.lang.String r1 = a(r0, r3)
            java.lang.StringBuilder r6 = r10.e
            r6.append(r1)
            r0.delete(r3, r4)
            java.lang.String r1 = r10.a
            int r6 = r10.f
            int r1 = com.google.zxing.datamatrix.encoder.HighLevelEncoder.a(r1, r6, r4)
            if (r1 == r4) goto L5
            r10.g = r3
            goto L55
        L51:
            com.google.zxing.datamatrix.encoder.HighLevelEncoder.a(r1)
            throw r5
        L55:
            r1 = 31
            r0.append(r1)
            int r1 = r0.length()     // Catch: java.lang.Throwable -> Lcc
            if (r1 != 0) goto L61
            goto Lc1
        L61:
            r6 = 2
            if (r1 != r2) goto L7b
            r10.e()     // Catch: java.lang.Throwable -> Lcc
            com.google.zxing.datamatrix.encoder.SymbolInfo r7 = r10.h     // Catch: java.lang.Throwable -> Lcc
            int r7 = r7.getDataCapacity()     // Catch: java.lang.Throwable -> Lcc
            int r8 = r10.a()     // Catch: java.lang.Throwable -> Lcc
            int r7 = r7 - r8
            int r8 = r10.c()     // Catch: java.lang.Throwable -> Lcc
            if (r8 != 0) goto L7b
            if (r7 > r6) goto L7b
            goto Lc1
        L7b:
            if (r1 > r4) goto Lc4
            int r1 = r1 - r2
            java.lang.String r0 = a(r0, r3)     // Catch: java.lang.Throwable -> Lcc
            boolean r4 = r10.d()     // Catch: java.lang.Throwable -> Lcc
            r4 = r4 ^ r2
            if (r4 == 0) goto L8c
            if (r1 > r6) goto L8c
            goto L8d
        L8c:
            r2 = r3
        L8d:
            if (r1 > r6) goto Lb2
            int r4 = r10.a()     // Catch: java.lang.Throwable -> Lcc
            int r4 = r4 + r1
            r10.a(r4)     // Catch: java.lang.Throwable -> Lcc
            com.google.zxing.datamatrix.encoder.SymbolInfo r4 = r10.h     // Catch: java.lang.Throwable -> Lcc
            int r4 = r4.getDataCapacity()     // Catch: java.lang.Throwable -> Lcc
            int r6 = r10.a()     // Catch: java.lang.Throwable -> Lcc
            int r4 = r4 - r6
            r6 = 3
            if (r4 < r6) goto Lb2
            int r2 = r10.a()     // Catch: java.lang.Throwable -> Lcc
            int r4 = r0.length()     // Catch: java.lang.Throwable -> Lcc
            int r2 = r2 + r4
            r10.a(r2)     // Catch: java.lang.Throwable -> Lcc
            r2 = r3
        Lb2:
            if (r2 == 0) goto Lbc
            r10.h = r5     // Catch: java.lang.Throwable -> Lcc
            int r0 = r10.f     // Catch: java.lang.Throwable -> Lcc
            int r0 = r0 - r1
            r10.f = r0     // Catch: java.lang.Throwable -> Lcc
            goto Lc1
        Lbc:
            java.lang.StringBuilder r1 = r10.e     // Catch: java.lang.Throwable -> Lcc
            r1.append(r0)     // Catch: java.lang.Throwable -> Lcc
        Lc1:
            r10.g = r3
            return
        Lc4:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException     // Catch: java.lang.Throwable -> Lcc
            java.lang.String r1 = "Count must not exceed 4"
            r0.<init>(r1)     // Catch: java.lang.Throwable -> Lcc
            throw r0     // Catch: java.lang.Throwable -> Lcc
        Lcc:
            r0 = move-exception
            r10.g = r3
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.ra.a(ta):void");
    }

    public static String a(CharSequence charSequence, int i) {
        int length = charSequence.length() - i;
        if (length != 0) {
            int iCharAt = (charSequence.charAt(i) << 18) + ((length >= 2 ? charSequence.charAt(i + 1) : (char) 0) << '\f') + ((length >= 3 ? charSequence.charAt(i + 2) : (char) 0) << 6) + (length >= 4 ? charSequence.charAt(i + 3) : (char) 0);
            char c = (char) ((iCharAt >> 16) & 255);
            char c2 = (char) ((iCharAt >> 8) & 255);
            char c3 = (char) (iCharAt & 255);
            StringBuilder sb = new StringBuilder(3);
            sb.append(c);
            if (length >= 2) {
                sb.append(c2);
            }
            if (length >= 3) {
                sb.append(c3);
            }
            return sb.toString();
        }
        throw new IllegalStateException("StringBuilder must not be empty");
    }
}
