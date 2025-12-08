package defpackage;

import com.google.zxing.WriterException;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.pdf417.encoder.Compaction;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.util.Arrays;
import org.apache.commons.lang3.CharEncoding;

/* loaded from: classes.dex */
public final class kc {
    public static final byte[] a = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 38, 13, 9, 44, 58, 35, 45, 46, 36, 47, 43, 37, 42, 61, 94, 0, 32, 0, 0, 0};
    public static final byte[] b = {59, 60, 62, 64, 91, 92, 93, 95, 96, 126, 33, 13, 9, 44, 58, 10, 45, 46, 36, 47, 34, 124, 42, 40, 41, 63, 123, 125, 39, 0};
    public static final byte[] c = new byte[128];
    public static final byte[] d = new byte[128];
    public static final Charset e = Charset.forName(CharEncoding.ISO_8859_1);

    static {
        Arrays.fill(c, (byte) -1);
        int i = 0;
        int i2 = 0;
        while (true) {
            byte[] bArr = a;
            if (i2 >= bArr.length) {
                break;
            }
            byte b2 = bArr[i2];
            if (b2 > 0) {
                c[b2] = (byte) i2;
            }
            i2++;
        }
        Arrays.fill(d, (byte) -1);
        while (true) {
            byte[] bArr2 = b;
            if (i >= bArr2.length) {
                return;
            }
            byte b3 = bArr2[i];
            if (b3 > 0) {
                d[b3] = (byte) i;
            }
            i++;
        }
    }

    public static String a(String str, Compaction compaction, Charset charset) throws WriterException {
        CharacterSetECI characterSetECIByName;
        int i;
        int i2;
        Charset charset2 = charset;
        StringBuilder sb = new StringBuilder(str.length());
        if (charset2 == null) {
            charset2 = e;
        } else if (!e.equals(charset2) && (characterSetECIByName = CharacterSetECI.getCharacterSetECIByName(charset.name())) != null) {
            int value = characterSetECIByName.getValue();
            if (value >= 0 && value < 900) {
                sb.append((char) 927);
                sb.append((char) value);
            } else if (value < 810900) {
                sb.append((char) 926);
                sb.append((char) ((value / 900) - 1));
                sb.append((char) (value % 900));
            } else {
                if (value >= 811800) {
                    throw new WriterException(g9.a("ECI number not in valid range from 0..811799, but was ", value));
                }
                sb.append((char) 925);
                sb.append((char) (810900 - value));
            }
        }
        int length = str.length();
        if (compaction == Compaction.TEXT) {
            a(str, 0, length, sb, 0);
        } else if (compaction == Compaction.BYTE) {
            byte[] bytes = str.getBytes(charset2);
            a(bytes, 0, bytes.length, 1, sb);
        } else {
            char c2 = 902;
            if (compaction == Compaction.NUMERIC) {
                sb.append((char) 902);
                a(str, 0, length, sb);
            } else {
                int i3 = 0;
                int iA = 0;
                int i4 = 0;
                while (i3 < length) {
                    int length2 = str.length();
                    if (i3 < length2) {
                        char cCharAt = str.charAt(i3);
                        int i5 = i3;
                        i = 0;
                        while (c(cCharAt) && i5 < length2) {
                            i++;
                            i5++;
                            if (i5 < length2) {
                                cCharAt = str.charAt(i5);
                            }
                        }
                    } else {
                        i = 0;
                    }
                    if (i >= 13) {
                        sb.append(c2);
                        i4 = 2;
                        a(str, i3, i, sb);
                        i3 += i;
                        iA = 0;
                    } else {
                        int length3 = str.length();
                        int i6 = i3;
                        while (i6 < length3) {
                            char cCharAt2 = str.charAt(i6);
                            int i7 = 0;
                            while (i7 < 13 && c(cCharAt2) && i6 < length3) {
                                i7++;
                                i6++;
                                if (i6 < length3) {
                                    cCharAt2 = str.charAt(i6);
                                }
                            }
                            if (i7 < 13) {
                                if (i7 <= 0) {
                                    char cCharAt3 = str.charAt(i6);
                                    if (!(cCharAt3 == '\t' || cCharAt3 == '\n' || cCharAt3 == '\r' || (cCharAt3 >= ' ' && cCharAt3 <= '~'))) {
                                        break;
                                    }
                                    i6++;
                                }
                            } else {
                                i2 = (i6 - i3) - i7;
                                break;
                            }
                        }
                        i2 = i6 - i3;
                        if (i2 >= 5 || i == length) {
                            if (i4 != 0) {
                                sb.append((char) 900);
                                iA = 0;
                                i4 = 0;
                            }
                            iA = a(str, i3, i2, sb, iA);
                            i3 += i2;
                        } else {
                            CharsetEncoder charsetEncoderNewEncoder = charset2.newEncoder();
                            int length4 = str.length();
                            int i8 = i3;
                            while (i8 < length4) {
                                char cCharAt4 = str.charAt(i8);
                                int i9 = 0;
                                while (i9 < 13 && c(cCharAt4)) {
                                    i9++;
                                    int i10 = i8 + i9;
                                    if (i10 >= length4) {
                                        break;
                                    }
                                    cCharAt4 = str.charAt(i10);
                                }
                                if (i9 >= 13) {
                                    break;
                                }
                                char cCharAt5 = str.charAt(i8);
                                if (!charsetEncoderNewEncoder.canEncode(cCharAt5)) {
                                    throw new WriterException("Non-encodable character detected: " + cCharAt5 + " (Unicode: " + ((int) cCharAt5) + ')');
                                }
                                i8++;
                            }
                            int i11 = i8 - i3;
                            if (i11 == 0) {
                                i11 = 1;
                            }
                            int i12 = i11 + i3;
                            byte[] bytes2 = str.substring(i3, i12).getBytes(charset2);
                            if (bytes2.length == 1 && i4 == 0) {
                                a(bytes2, 0, 1, 0, sb);
                            } else {
                                a(bytes2, 0, bytes2.length, i4, sb);
                                iA = 0;
                                i4 = 1;
                            }
                            i3 = i12;
                        }
                        c2 = 902;
                    }
                }
            }
        }
        return sb.toString();
    }

    public static boolean a(char c2) {
        if (c2 != ' ') {
            return c2 >= 'a' && c2 <= 'z';
        }
        return true;
    }

    public static boolean b(char c2) {
        if (c2 != ' ') {
            return c2 >= 'A' && c2 <= 'Z';
        }
        return true;
    }

    public static boolean c(char c2) {
        return c2 >= '0' && c2 <= '9';
    }

    public static boolean d(char c2) {
        return c[c2] != -1;
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x0011 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x0101 A[EDGE_INSN: B:83:0x0101->B:62:0x0101 BREAK  A[LOOP:0: B:3:0x0011->B:100:0x0011], SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int a(java.lang.CharSequence r16, int r17, int r18, java.lang.StringBuilder r19, int r20) {
        /*
            Method dump skipped, instructions count: 304
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.kc.a(java.lang.CharSequence, int, int, java.lang.StringBuilder, int):int");
    }

    public static void a(byte[] bArr, int i, int i2, int i3, StringBuilder sb) {
        int i4;
        if (i2 == 1 && i3 == 0) {
            sb.append((char) 913);
        } else if (i2 % 6 == 0) {
            sb.append((char) 924);
        } else {
            sb.append((char) 901);
        }
        if (i2 >= 6) {
            char[] cArr = new char[5];
            i4 = i;
            while ((i + i2) - i4 >= 6) {
                long j = 0;
                for (int i5 = 0; i5 < 6; i5++) {
                    j = (j << 8) + (bArr[i4 + i5] & 255);
                }
                for (int i6 = 0; i6 < 5; i6++) {
                    cArr[i6] = (char) (j % 900);
                    j /= 900;
                }
                for (int i7 = 4; i7 >= 0; i7--) {
                    sb.append(cArr[i7]);
                }
                i4 += 6;
            }
        } else {
            i4 = i;
        }
        while (i4 < i + i2) {
            sb.append((char) (bArr[i4] & 255));
            i4++;
        }
    }

    public static void a(String str, int i, int i2, StringBuilder sb) {
        StringBuilder sb2 = new StringBuilder((i2 / 3) + 1);
        BigInteger bigIntegerValueOf = BigInteger.valueOf(900L);
        BigInteger bigIntegerValueOf2 = BigInteger.valueOf(0L);
        int i3 = 0;
        while (i3 < i2) {
            sb2.setLength(0);
            int iMin = Math.min(44, i2 - i3);
            StringBuilder sb3 = new StringBuilder("1");
            int i4 = i + i3;
            sb3.append(str.substring(i4, i4 + iMin));
            BigInteger bigInteger = new BigInteger(sb3.toString());
            do {
                sb2.append((char) bigInteger.mod(bigIntegerValueOf).intValue());
                bigInteger = bigInteger.divide(bigIntegerValueOf);
            } while (!bigInteger.equals(bigIntegerValueOf2));
            for (int length = sb2.length() - 1; length >= 0; length--) {
                sb.append(sb2.charAt(length));
            }
            i3 += iMin;
        }
    }
}
