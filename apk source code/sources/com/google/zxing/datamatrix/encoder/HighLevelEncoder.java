package com.google.zxing.datamatrix.encoder;

import com.google.zxing.Dimension;
import defpackage.na;
import defpackage.oa;
import defpackage.pa;
import defpackage.ra;
import defpackage.sa;
import defpackage.ta;
import defpackage.ua;
import defpackage.va;
import java.util.Arrays;

/* loaded from: classes.dex */
public final class HighLevelEncoder {
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0207, code lost:
    
        return 5;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int a(java.lang.CharSequence r19, int r20, int r21) {
        /*
            Method dump skipped, instructions count: 552
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.datamatrix.encoder.HighLevelEncoder.a(java.lang.CharSequence, int, int):int");
    }

    public static boolean b(char c) {
        return c >= '0' && c <= '9';
    }

    public static boolean c(char c) {
        return c >= 128 && c <= 255;
    }

    public static boolean d(char c) {
        if (e(c) || c == ' ') {
            return true;
        }
        if (c < '0' || c > '9') {
            return c >= 'A' && c <= 'Z';
        }
        return true;
    }

    public static int determineConsecutiveDigitCount(CharSequence charSequence, int i) {
        int length = charSequence.length();
        int i2 = 0;
        if (i < length) {
            char cCharAt = charSequence.charAt(i);
            while (b(cCharAt) && i < length) {
                i2++;
                i++;
                if (i < length) {
                    cCharAt = charSequence.charAt(i);
                }
            }
        }
        return i2;
    }

    public static boolean e(char c) {
        return c == '\r' || c == '*' || c == '>';
    }

    public static String encodeHighLevel(String str) {
        return encodeHighLevel(str, SymbolShapeHint.FORCE_NONE, null, null);
    }

    public static String encodeHighLevel(String str, SymbolShapeHint symbolShapeHint, Dimension dimension, Dimension dimension2) {
        int i = 0;
        sa[] saVarArr = {new na(), new pa(), new ua(), new va(), new ra(), new oa()};
        ta taVar = new ta(str);
        taVar.b = symbolShapeHint;
        taVar.c = dimension;
        taVar.d = dimension2;
        if (str.startsWith("[)>\u001e05\u001d") && str.endsWith("\u001e\u0004")) {
            taVar.e.append((char) 236);
            taVar.i = 2;
            taVar.f += 7;
        } else if (str.startsWith("[)>\u001e06\u001d") && str.endsWith("\u001e\u0004")) {
            taVar.e.append((char) 237);
            taVar.i = 2;
            taVar.f += 7;
        }
        while (taVar.d()) {
            saVarArr[i].a(taVar);
            int i2 = taVar.g;
            if (i2 >= 0) {
                taVar.g = -1;
                i = i2;
            }
        }
        int iA = taVar.a();
        taVar.e();
        int dataCapacity = taVar.h.getDataCapacity();
        if (iA < dataCapacity && i != 0 && i != 5) {
            taVar.e.append((char) 254);
        }
        StringBuilder sb = taVar.e;
        if (sb.length() < dataCapacity) {
            sb.append((char) 129);
        }
        while (sb.length() < dataCapacity) {
            int length = (((sb.length() + 1) * 149) % 253) + 1 + 129;
            if (length > 254) {
                length -= 254;
            }
            sb.append((char) length);
        }
        return taVar.e.toString();
    }

    public static int a(float[] fArr, int[] iArr, int i, byte[] bArr) {
        Arrays.fill(bArr, (byte) 0);
        for (int i2 = 0; i2 < 6; i2++) {
            iArr[i2] = (int) Math.ceil(fArr[i2]);
            int i3 = iArr[i2];
            if (i > i3) {
                Arrays.fill(bArr, (byte) 0);
                i = i3;
            }
            if (i == i3) {
                bArr[i2] = (byte) (bArr[i2] + 1);
            }
        }
        return i;
    }

    public static void a(char c) {
        String hexString = Integer.toHexString(c);
        throw new IllegalArgumentException("Illegal character: " + c + " (0x" + ("0000".substring(0, 4 - hexString.length()) + hexString) + ')');
    }
}
