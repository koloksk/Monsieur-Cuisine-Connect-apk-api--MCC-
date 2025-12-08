package com.google.zxing.aztec.decoder;

import android.support.media.ExifInterface;
import com.google.zxing.FormatException;
import com.google.zxing.aztec.AztecDetectorResult;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import java.util.Arrays;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class Decoder {
    public static final String[] b = {"CTRL_PS", StringUtils.SPACE, ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "B", "C", "D", ExifInterface.LONGITUDE_EAST, "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", ExifInterface.LATITUDE_SOUTH, ExifInterface.GPS_DIRECTION_TRUE, "U", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, ExifInterface.LONGITUDE_WEST, "X", "Y", "Z", "CTRL_LL", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
    public static final String[] c = {"CTRL_PS", StringUtils.SPACE, "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "CTRL_US", "CTRL_ML", "CTRL_DL", "CTRL_BS"};
    public static final String[] d = {"CTRL_PS", StringUtils.SPACE, "\u0001", "\u0002", "\u0003", "\u0004", "\u0005", "\u0006", "\u0007", "\b", "\t", StringUtils.LF, "\u000b", "\f", StringUtils.CR, "\u001b", "\u001c", "\u001d", "\u001e", "\u001f", "@", "\\", "^", "_", "`", "|", "~", "\u007f", "CTRL_LL", "CTRL_UL", "CTRL_PL", "CTRL_BS"};
    public static final String[] e = {"", StringUtils.CR, "\r\n", ". ", ", ", ": ", "!", "\"", "#", "$", "%", "&", "'", "(", ")", "*", "+", ",", "-", ".", "/", ":", ";", "<", "=", ">", "?", "[", "]", "{", "}", "CTRL_UL"};
    public static final String[] f = {"CTRL_PS", StringUtils.SPACE, "0", "1", ExifInterface.GPS_MEASUREMENT_2D, ExifInterface.GPS_MEASUREMENT_3D, "4", "5", "6", "7", "8", "9", ",", ".", "CTRL_UL", "CTRL_US"};
    public AztecDetectorResult a;

    public enum a {
        UPPER,
        LOWER,
        MIXED,
        DIGIT,
        PUNCT,
        BINARY
    }

    public static String a(boolean[] zArr) {
        String str;
        int length = zArr.length;
        a aVar = a.UPPER;
        StringBuilder sb = new StringBuilder(20);
        a aVar2 = aVar;
        int i = 0;
        while (i < length) {
            if (aVar != a.BINARY) {
                int i2 = aVar == a.DIGIT ? 4 : 5;
                if (length - i < i2) {
                    break;
                }
                int iA = a(zArr, i, i2);
                i += i2;
                int iOrdinal = aVar.ordinal();
                if (iOrdinal == 0) {
                    str = b[iA];
                } else if (iOrdinal == 1) {
                    str = c[iA];
                } else if (iOrdinal == 2) {
                    str = d[iA];
                } else if (iOrdinal == 3) {
                    str = f[iA];
                } else {
                    if (iOrdinal != 4) {
                        throw new IllegalStateException("Bad table");
                    }
                    str = e[iA];
                }
                if (str.startsWith("CTRL_")) {
                    char cCharAt = str.charAt(5);
                    aVar2 = cCharAt != 'B' ? cCharAt != 'D' ? cCharAt != 'P' ? cCharAt != 'L' ? cCharAt != 'M' ? a.UPPER : a.MIXED : a.LOWER : a.PUNCT : a.DIGIT : a.BINARY;
                    if (str.charAt(6) != 'L') {
                        a aVar3 = aVar2;
                        aVar2 = aVar;
                        aVar = aVar3;
                    }
                } else {
                    sb.append(str);
                }
                aVar = aVar2;
            } else {
                if (length - i < 5) {
                    break;
                }
                int iA2 = a(zArr, i, 5);
                i += 5;
                if (iA2 == 0) {
                    if (length - i < 11) {
                        break;
                    }
                    iA2 = a(zArr, i, 11) + 31;
                    i += 11;
                }
                int i3 = 0;
                while (true) {
                    if (i3 >= iA2) {
                        break;
                    }
                    if (length - i < 8) {
                        i = length;
                        break;
                    }
                    sb.append((char) a(zArr, i, 8));
                    i += 8;
                    i3++;
                }
                aVar = aVar2;
            }
        }
        return sb.toString();
    }

    public static String highLevelDecode(boolean[] zArr) {
        return a(zArr);
    }

    public DecoderResult decode(AztecDetectorResult aztecDetectorResult) throws FormatException {
        int i;
        GenericGF genericGF;
        this.a = aztecDetectorResult;
        BitMatrix bits = aztecDetectorResult.getBits();
        boolean zIsCompact = this.a.isCompact();
        int nbLayers = this.a.getNbLayers();
        int i2 = (zIsCompact ? 11 : 14) + (nbLayers << 2);
        int[] iArr = new int[i2];
        int i3 = ((zIsCompact ? 88 : 112) + (nbLayers << 4)) * nbLayers;
        boolean[] zArr = new boolean[i3];
        int i4 = 2;
        if (zIsCompact) {
            for (int i5 = 0; i5 < i2; i5++) {
                iArr[i5] = i5;
            }
        } else {
            int i6 = i2 / 2;
            int i7 = ((((i6 - 1) / 15) * 2) + (i2 + 1)) / 2;
            for (int i8 = 0; i8 < i6; i8++) {
                iArr[(i6 - i8) - 1] = (i7 - r14) - 1;
                iArr[i6 + i8] = (i8 / 15) + i8 + i7 + 1;
            }
        }
        int i9 = 0;
        int i10 = 0;
        while (true) {
            if (i9 >= nbLayers) {
                break;
            }
            int i11 = ((nbLayers - i9) << i4) + (zIsCompact ? 9 : 12);
            int i12 = i9 << 1;
            int i13 = (i2 - 1) - i12;
            int i14 = 0;
            while (i14 < i11) {
                int i15 = i14 << 1;
                int i16 = 0;
                while (i16 < i4) {
                    int i17 = i12 + i16;
                    int i18 = i12 + i14;
                    zArr[i10 + i15 + i16] = bits.get(iArr[i17], iArr[i18]);
                    int i19 = i13 - i16;
                    zArr[(i11 * 2) + i10 + i15 + i16] = bits.get(iArr[i18], iArr[i19]);
                    int i20 = i13 - i14;
                    zArr[(i11 * 4) + i10 + i15 + i16] = bits.get(iArr[i19], iArr[i20]);
                    zArr[(i11 * 6) + i10 + i15 + i16] = bits.get(iArr[i20], iArr[i17]);
                    i16++;
                    i2 = i2;
                    nbLayers = nbLayers;
                    zIsCompact = zIsCompact;
                    i4 = 2;
                }
                i14++;
                i4 = 2;
            }
            i10 += i11 << 3;
            i9++;
            zIsCompact = zIsCompact;
            i4 = 2;
        }
        if (this.a.getNbLayers() <= 2) {
            genericGF = GenericGF.AZTEC_DATA_6;
            i = 6;
        } else if (this.a.getNbLayers() <= 8) {
            genericGF = GenericGF.AZTEC_DATA_8;
            i = 8;
        } else if (this.a.getNbLayers() <= 22) {
            i = 10;
            genericGF = GenericGF.AZTEC_DATA_10;
        } else {
            genericGF = GenericGF.AZTEC_DATA_12;
        }
        int nbDatablocks = this.a.getNbDatablocks();
        int i21 = i3 / i;
        if (i21 < nbDatablocks) {
            throw FormatException.getFormatInstance();
        }
        int i22 = i3 % i;
        int[] iArr2 = new int[i21];
        int i23 = 0;
        while (i23 < i21) {
            iArr2[i23] = a(zArr, i22, i);
            i23++;
            i22 += i;
        }
        try {
            new ReedSolomonDecoder(genericGF).decode(iArr2, i21 - nbDatablocks);
            int i24 = 1;
            int i25 = (1 << i) - 1;
            int i26 = 0;
            int i27 = 0;
            while (i26 < nbDatablocks) {
                int i28 = iArr2[i26];
                if (i28 == 0 || i28 == i25) {
                    throw FormatException.getFormatInstance();
                }
                if (i28 == i24 || i28 == i25 - 1) {
                    i27++;
                }
                i26++;
                i24 = 1;
            }
            int i29 = (nbDatablocks * i) - i27;
            boolean[] zArr2 = new boolean[i29];
            int i30 = 0;
            for (int i31 = 0; i31 < nbDatablocks; i31++) {
                int i32 = iArr2[i31];
                int i33 = 1;
                if (i32 == 1 || i32 == i25 - 1) {
                    Arrays.fill(zArr2, i30, (i30 + i) - 1, i32 > 1);
                    i30 = (i - 1) + i30;
                } else {
                    int i34 = i - 1;
                    while (i34 >= 0) {
                        int i35 = i30 + 1;
                        zArr2[i30] = ((i33 << i34) & i32) != 0;
                        i34--;
                        i30 = i35;
                        i33 = 1;
                    }
                }
            }
            int i36 = (i29 + 7) / 8;
            byte[] bArr = new byte[i36];
            for (int i37 = 0; i37 < i36; i37++) {
                int i38 = i37 << 3;
                int i39 = i29 - i38;
                bArr[i37] = (byte) (i39 >= 8 ? a(zArr2, i38, 8) : a(zArr2, i38, i39) << (8 - i39));
            }
            DecoderResult decoderResult = new DecoderResult(bArr, a(zArr2), null, null);
            decoderResult.setNumBits(i29);
            return decoderResult;
        } catch (ReedSolomonException e2) {
            throw FormatException.getFormatInstance(e2);
        }
    }

    public static int a(boolean[] zArr, int i, int i2) {
        int i3 = 0;
        for (int i4 = i; i4 < i + i2; i4++) {
            i3 <<= 1;
            if (zArr[i4]) {
                i3 |= 1;
            }
        }
        return i3;
    }
}
