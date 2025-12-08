package defpackage;

import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitSource;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.StringUtils;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.google.zxing.qrcode.decoder.Mode;
import com.google.zxing.qrcode.decoder.Version;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public final class oc {
    public static final char[] a = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ $%*+-./:".toCharArray();

    public static DecoderResult a(byte[] bArr, Version version, ErrorCorrectionLevel errorCorrectionLevel, Map<DecodeHintType, ?> map) throws FormatException {
        int bits;
        BitSource bitSource = new BitSource(bArr);
        StringBuilder sb = new StringBuilder(50);
        int i = 1;
        ArrayList arrayList = new ArrayList(1);
        int bits2 = -1;
        int bits3 = -1;
        CharacterSetECI characterSetECIByValue = null;
        boolean z = false;
        while (true) {
            try {
                Mode modeForBits = bitSource.available() < 4 ? Mode.TERMINATOR : Mode.forBits(bitSource.readBits(4));
                if (modeForBits != Mode.TERMINATOR) {
                    if (modeForBits == Mode.FNC1_FIRST_POSITION || modeForBits == Mode.FNC1_SECOND_POSITION) {
                        z = true;
                    } else if (modeForBits == Mode.STRUCTURED_APPEND) {
                        if (bitSource.available() < 16) {
                            throw FormatException.getFormatInstance();
                        }
                        bits2 = bitSource.readBits(8);
                        bits3 = bitSource.readBits(8);
                    } else if (modeForBits == Mode.ECI) {
                        int bits4 = bitSource.readBits(8);
                        if ((bits4 & 128) == 0) {
                            bits = bits4 & 127;
                        } else if ((bits4 & 192) == 128) {
                            bits = ((bits4 & 63) << 8) | bitSource.readBits(8);
                        } else {
                            if ((bits4 & 224) != 192) {
                                throw FormatException.getFormatInstance();
                            }
                            bits = bitSource.readBits(16) | ((bits4 & 31) << 16);
                        }
                        characterSetECIByValue = CharacterSetECI.getCharacterSetECIByValue(bits);
                        if (characterSetECIByValue == null) {
                            throw FormatException.getFormatInstance();
                        }
                    } else if (modeForBits == Mode.HANZI) {
                        int bits5 = bitSource.readBits(4);
                        int bits6 = bitSource.readBits(modeForBits.getCharacterCountBits(version));
                        if (bits5 == i) {
                            a(bitSource, sb, bits6);
                        }
                    } else {
                        int bits7 = bitSource.readBits(modeForBits.getCharacterCountBits(version));
                        if (modeForBits == Mode.NUMERIC) {
                            c(bitSource, sb, bits7);
                        } else if (modeForBits == Mode.ALPHANUMERIC) {
                            a(bitSource, sb, bits7, z);
                        } else if (modeForBits == Mode.BYTE) {
                            if ((bits7 << 3) > bitSource.available()) {
                                throw FormatException.getFormatInstance();
                            }
                            byte[] bArr2 = new byte[bits7];
                            for (int i2 = 0; i2 < bits7; i2++) {
                                bArr2[i2] = (byte) bitSource.readBits(8);
                            }
                            try {
                                sb.append(new String(bArr2, characterSetECIByValue == null ? StringUtils.guessEncoding(bArr2, map) : characterSetECIByValue.name()));
                                arrayList.add(bArr2);
                            } catch (UnsupportedEncodingException unused) {
                                throw FormatException.getFormatInstance();
                            }
                        } else {
                            if (modeForBits != Mode.KANJI) {
                                throw FormatException.getFormatInstance();
                            }
                            b(bitSource, sb, bits7);
                        }
                    }
                }
                if (modeForBits == Mode.TERMINATOR) {
                    return new DecoderResult(bArr, sb.toString(), arrayList.isEmpty() ? null : arrayList, errorCorrectionLevel == null ? null : errorCorrectionLevel.toString(), bits2, bits3);
                }
                i = 1;
            } catch (IllegalArgumentException unused2) {
                throw FormatException.getFormatInstance();
            }
        }
    }

    public static void b(BitSource bitSource, StringBuilder sb, int i) throws FormatException {
        if (i * 13 > bitSource.available()) {
            throw FormatException.getFormatInstance();
        }
        byte[] bArr = new byte[i * 2];
        int i2 = 0;
        while (i > 0) {
            int bits = bitSource.readBits(13);
            int i3 = (bits % 192) | ((bits / 192) << 8);
            int i4 = i3 + (i3 < 7936 ? 33088 : 49472);
            bArr[i2] = (byte) (i4 >> 8);
            bArr[i2 + 1] = (byte) i4;
            i2 += 2;
            i--;
        }
        try {
            sb.append(new String(bArr, StringUtils.SHIFT_JIS));
        } catch (UnsupportedEncodingException unused) {
            throw FormatException.getFormatInstance();
        }
    }

    public static void c(BitSource bitSource, StringBuilder sb, int i) throws FormatException {
        while (i >= 3) {
            if (bitSource.available() < 10) {
                throw FormatException.getFormatInstance();
            }
            int bits = bitSource.readBits(10);
            if (bits >= 1000) {
                throw FormatException.getFormatInstance();
            }
            sb.append(a(bits / 100));
            sb.append(a((bits / 10) % 10));
            sb.append(a(bits % 10));
            i -= 3;
        }
        if (i == 2) {
            if (bitSource.available() < 7) {
                throw FormatException.getFormatInstance();
            }
            int bits2 = bitSource.readBits(7);
            if (bits2 >= 100) {
                throw FormatException.getFormatInstance();
            }
            sb.append(a(bits2 / 10));
            sb.append(a(bits2 % 10));
            return;
        }
        if (i == 1) {
            if (bitSource.available() < 4) {
                throw FormatException.getFormatInstance();
            }
            int bits3 = bitSource.readBits(4);
            if (bits3 >= 10) {
                throw FormatException.getFormatInstance();
            }
            sb.append(a(bits3));
        }
    }

    public static void a(BitSource bitSource, StringBuilder sb, int i) throws FormatException {
        if (i * 13 <= bitSource.available()) {
            byte[] bArr = new byte[i * 2];
            int i2 = 0;
            while (i > 0) {
                int bits = bitSource.readBits(13);
                int i3 = (bits % 96) | ((bits / 96) << 8);
                int i4 = i3 + (i3 < 959 ? 41377 : 42657);
                bArr[i2] = (byte) (i4 >> 8);
                bArr[i2 + 1] = (byte) i4;
                i2 += 2;
                i--;
            }
            try {
                sb.append(new String(bArr, StringUtils.GB2312));
                return;
            } catch (UnsupportedEncodingException unused) {
                throw FormatException.getFormatInstance();
            }
        }
        throw FormatException.getFormatInstance();
    }

    public static char a(int i) throws FormatException {
        char[] cArr = a;
        if (i < cArr.length) {
            return cArr[i];
        }
        throw FormatException.getFormatInstance();
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x006a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void a(com.google.zxing.common.BitSource r3, java.lang.StringBuilder r4, int r5, boolean r6) throws com.google.zxing.FormatException {
        /*
            int r0 = r4.length()
        L4:
            r1 = 1
            if (r5 <= r1) goto L2d
            int r1 = r3.available()
            r2 = 11
            if (r1 < r2) goto L28
            int r1 = r3.readBits(r2)
            int r2 = r1 / 45
            char r2 = a(r2)
            r4.append(r2)
            int r1 = r1 % 45
            char r1 = a(r1)
            r4.append(r1)
            int r5 = r5 + (-2)
            goto L4
        L28:
            com.google.zxing.FormatException r3 = com.google.zxing.FormatException.getFormatInstance()
            throw r3
        L2d:
            if (r5 != r1) goto L47
            int r5 = r3.available()
            r2 = 6
            if (r5 < r2) goto L42
            int r3 = r3.readBits(r2)
            char r3 = a(r3)
            r4.append(r3)
            goto L47
        L42:
            com.google.zxing.FormatException r3 = com.google.zxing.FormatException.getFormatInstance()
            throw r3
        L47:
            if (r6 == 0) goto L72
        L49:
            int r3 = r4.length()
            if (r0 >= r3) goto L72
            char r3 = r4.charAt(r0)
            r5 = 37
            if (r3 != r5) goto L6f
            int r3 = r4.length()
            int r3 = r3 - r1
            if (r0 >= r3) goto L6a
            int r3 = r0 + 1
            char r6 = r4.charAt(r3)
            if (r6 != r5) goto L6a
            r4.deleteCharAt(r3)
            goto L6f
        L6a:
            r3 = 29
            r4.setCharAt(r0, r3)
        L6f:
            int r0 = r0 + 1
            goto L49
        L72:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.oc.a(com.google.zxing.common.BitSource, java.lang.StringBuilder, int, boolean):void");
    }
}
