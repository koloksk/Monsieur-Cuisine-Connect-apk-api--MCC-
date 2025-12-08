package defpackage;

import com.google.zxing.FormatException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import org.apache.commons.lang3.CharEncoding;

/* loaded from: classes.dex */
public final class cc {
    public static final char[] a = ";<>@[\\]_`~!\r\t,:\n-.$/\"|*()?{}'".toCharArray();
    public static final char[] b = "0123456789&\r\t,:#-.$/+%*=^".toCharArray();
    public static final Charset c = Charset.forName(CharEncoding.ISO_8859_1);
    public static final BigInteger[] d;

    public enum a {
        ALPHA,
        LOWER,
        MIXED,
        PUNCT,
        ALPHA_SHIFT,
        PUNCT_SHIFT
    }

    static {
        BigInteger[] bigIntegerArr = new BigInteger[16];
        d = bigIntegerArr;
        bigIntegerArr[0] = BigInteger.ONE;
        BigInteger bigIntegerValueOf = BigInteger.valueOf(900L);
        d[1] = bigIntegerValueOf;
        int i = 2;
        while (true) {
            BigInteger[] bigIntegerArr2 = d;
            if (i >= bigIntegerArr2.length) {
                return;
            }
            bigIntegerArr2[i] = bigIntegerArr2[i - 1].multiply(bigIntegerValueOf);
            i++;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:65:0x011b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.zxing.common.DecoderResult a(int[] r27, java.lang.String r28) throws com.google.zxing.FormatException {
        /*
            Method dump skipped, instructions count: 630
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.cc.a(int[], java.lang.String):com.google.zxing.common.DecoderResult");
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:13:0x003a. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:14:0x003d. Please report as an issue. */
    public static int a(int[] iArr, int i, StringBuilder sb) {
        a aVar;
        int i2;
        char c2;
        char c3;
        int i3 = 1;
        int[] iArr2 = new int[(iArr[0] - i) << 1];
        int[] iArr3 = new int[(iArr[0] - i) << 1];
        int i4 = i;
        boolean z = false;
        int i5 = 0;
        while (i4 < iArr[0] && !z) {
            int i6 = i4 + 1;
            int i7 = iArr[i4];
            if (i7 < 900) {
                iArr2[i5] = i7 / 30;
                iArr2[i5 + 1] = i7 % 30;
                i5 += 2;
            } else if (i7 != 913) {
                if (i7 != 928) {
                    switch (i7) {
                        case 900:
                            iArr2[i5] = 900;
                            i5++;
                            break;
                        case 901:
                        case 902:
                            break;
                        default:
                            switch (i7) {
                            }
                    }
                }
                i4 = i6 - 1;
                z = true;
            } else {
                iArr2[i5] = 913;
                i4 = i6 + 1;
                iArr3[i5] = iArr[i6];
                i5++;
            }
            i4 = i6;
        }
        a aVar2 = a.ALPHA;
        int i8 = 0;
        a aVar3 = aVar2;
        while (i8 < i5) {
            int i9 = iArr2[i8];
            int iOrdinal = aVar2.ordinal();
            char c4 = ' ';
            if (iOrdinal != 0) {
                if (iOrdinal != i3) {
                    if (iOrdinal == 2) {
                        if (i9 < 25) {
                            c4 = b[i9];
                        } else {
                            if (i9 == 25) {
                                aVar2 = a.PUNCT;
                            } else if (i9 != 26) {
                                if (i9 == 27) {
                                    aVar2 = a.LOWER;
                                } else if (i9 == 28) {
                                    aVar2 = a.ALPHA;
                                } else if (i9 == 29) {
                                    aVar = a.PUNCT_SHIFT;
                                    aVar3 = aVar2;
                                    aVar2 = aVar;
                                } else if (i9 == 913) {
                                    sb.append((char) iArr3[i8]);
                                } else if (i9 == 900) {
                                    aVar2 = a.ALPHA;
                                }
                            }
                            c2 = 0;
                        }
                        c2 = c4;
                    } else if (iOrdinal != 3) {
                        if (iOrdinal != 4) {
                            if (iOrdinal == 5) {
                                if (i9 < 29) {
                                    c3 = a[i9];
                                    c4 = c3;
                                    aVar2 = aVar3;
                                } else if (i9 == 29) {
                                    aVar2 = a.ALPHA;
                                } else {
                                    if (i9 == 913) {
                                        sb.append((char) iArr3[i8]);
                                    } else if (i9 == 900) {
                                        aVar2 = a.ALPHA;
                                    }
                                    c4 = 0;
                                    aVar2 = aVar3;
                                }
                            }
                            c2 = 0;
                        } else if (i9 < 26) {
                            c3 = (char) (i9 + 65);
                            c4 = c3;
                            aVar2 = aVar3;
                        } else {
                            if (i9 != 26) {
                                if (i9 == 900) {
                                    aVar2 = a.ALPHA;
                                    c2 = 0;
                                }
                                c4 = 0;
                            }
                            aVar2 = aVar3;
                        }
                        c2 = c4;
                    } else if (i9 < 29) {
                        c4 = a[i9];
                        c2 = c4;
                    } else {
                        if (i9 == 29) {
                            aVar2 = a.ALPHA;
                        } else if (i9 == 913) {
                            sb.append((char) iArr3[i8]);
                        } else if (i9 == 900) {
                            aVar2 = a.ALPHA;
                        }
                        c2 = 0;
                    }
                } else if (i9 < 26) {
                    i2 = i9 + 97;
                    c2 = (char) i2;
                } else {
                    if (i9 != 26) {
                        if (i9 == 27) {
                            aVar = a.ALPHA_SHIFT;
                        } else {
                            if (i9 == 28) {
                                aVar2 = a.MIXED;
                            } else if (i9 == 29) {
                                aVar = a.PUNCT_SHIFT;
                            } else if (i9 == 913) {
                                sb.append((char) iArr3[i8]);
                            } else if (i9 == 900) {
                                aVar2 = a.ALPHA;
                            }
                            c2 = 0;
                        }
                        aVar3 = aVar2;
                        aVar2 = aVar;
                        c2 = 0;
                    }
                    c2 = c4;
                }
            } else if (i9 < 26) {
                i2 = i9 + 65;
                c2 = (char) i2;
            } else {
                if (i9 != 26) {
                    if (i9 == 27) {
                        aVar2 = a.LOWER;
                    } else if (i9 == 28) {
                        aVar2 = a.MIXED;
                    } else if (i9 == 29) {
                        aVar = a.PUNCT_SHIFT;
                        aVar3 = aVar2;
                        aVar2 = aVar;
                    } else if (i9 == 913) {
                        sb.append((char) iArr3[i8]);
                    } else if (i9 == 900) {
                        aVar2 = a.ALPHA;
                    }
                    c2 = 0;
                }
                c2 = c4;
            }
            if (c2 != 0) {
                sb.append(c2);
            }
            i8++;
            i3 = 1;
        }
        return i4;
    }

    public static String a(int[] iArr, int i) throws FormatException {
        BigInteger bigIntegerAdd = BigInteger.ZERO;
        for (int i2 = 0; i2 < i; i2++) {
            bigIntegerAdd = bigIntegerAdd.add(d[(i - i2) - 1].multiply(BigInteger.valueOf(iArr[i2])));
        }
        String string = bigIntegerAdd.toString();
        if (string.charAt(0) == '1') {
            return string.substring(1);
        }
        throw FormatException.getFormatInstance();
    }
}
