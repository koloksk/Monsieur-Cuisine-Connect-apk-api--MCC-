package com.google.zxing.qrcode.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.reedsolomon.GenericGF;
import com.google.zxing.common.reedsolomon.ReedSolomonDecoder;
import com.google.zxing.common.reedsolomon.ReedSolomonException;
import com.google.zxing.qrcode.decoder.Version;
import defpackage.lc;
import defpackage.mc;
import defpackage.nc;
import defpackage.oc;
import defpackage.pc;
import java.util.Map;

/* loaded from: classes.dex */
public final class Decoder {
    public final ReedSolomonDecoder a = new ReedSolomonDecoder(GenericGF.QR_CODE_FIELD_256);

    public final DecoderResult a(lc lcVar, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
        int i;
        BitMatrix bitMatrix;
        Version versionC = lcVar.c();
        ErrorCorrectionLevel errorCorrectionLevel = lcVar.b().a;
        pc pcVarB = lcVar.b();
        Version versionC2 = lcVar.c();
        nc ncVar = nc.values()[pcVarB.b];
        int height = lcVar.a.getHeight();
        ncVar.a(lcVar.a, height);
        int dimensionForVersion = versionC2.getDimensionForVersion();
        BitMatrix bitMatrix2 = new BitMatrix(dimensionForVersion);
        int i2 = 0;
        bitMatrix2.setRegion(0, 0, 9, 9);
        int i3 = dimensionForVersion - 8;
        bitMatrix2.setRegion(i3, 0, 8, 9);
        bitMatrix2.setRegion(0, i3, 9, 8);
        int length = versionC2.b.length;
        for (int i4 = 0; i4 < length; i4++) {
            int i5 = versionC2.b[i4] - 2;
            for (int i6 = 0; i6 < length; i6++) {
                if ((i4 != 0 || (i6 != 0 && i6 != length - 1)) && (i4 != length - 1 || i6 != 0)) {
                    bitMatrix2.setRegion(versionC2.b[i6] - 2, i5, 5, 5);
                }
            }
        }
        int i7 = dimensionForVersion - 17;
        int i8 = 6;
        bitMatrix2.setRegion(6, 9, 1, i7);
        bitMatrix2.setRegion(9, 6, i7, 1);
        if (versionC2.a > 6) {
            int i9 = dimensionForVersion - 11;
            bitMatrix2.setRegion(i9, 0, 3, 6);
            bitMatrix2.setRegion(0, i9, 6, 3);
        }
        int totalCodewords = versionC2.getTotalCodewords();
        byte[] bArr = new byte[totalCodewords];
        int i10 = height - 1;
        int i11 = 0;
        int i12 = 0;
        int i13 = 0;
        int i14 = i10;
        boolean z = true;
        while (i14 > 0) {
            if (i14 == i8) {
                i14--;
            }
            int i15 = i2;
            while (i15 < height) {
                int i16 = z ? i10 - i15 : i15;
                while (i2 < 2) {
                    int i17 = i14 - i2;
                    if (bitMatrix2.get(i17, i16)) {
                        i = height;
                        bitMatrix = bitMatrix2;
                    } else {
                        i = height;
                        int i18 = i12 + 1;
                        int i19 = i13 << 1;
                        bitMatrix = bitMatrix2;
                        int i20 = lcVar.a.get(i17, i16) ? i19 | 1 : i19;
                        if (i18 == 8) {
                            bArr[i11] = (byte) i20;
                            i11++;
                            i12 = 0;
                            i13 = 0;
                        } else {
                            i12 = i18;
                            i13 = i20;
                        }
                    }
                    i2++;
                    height = i;
                    bitMatrix2 = bitMatrix;
                }
                i15++;
                i2 = 0;
            }
            z = !z;
            i14 -= 2;
            i2 = 0;
            i8 = 6;
        }
        if (i11 != versionC2.getTotalCodewords()) {
            throw FormatException.getFormatInstance();
        }
        if (totalCodewords != versionC.getTotalCodewords()) {
            throw new IllegalArgumentException();
        }
        Version.ECBlocks eCBlocksForLevel = versionC.getECBlocksForLevel(errorCorrectionLevel);
        Version.ECB[] eCBlocks = eCBlocksForLevel.getECBlocks();
        int count = 0;
        for (Version.ECB ecb : eCBlocks) {
            count += ecb.getCount();
        }
        mc[] mcVarArr = new mc[count];
        int i21 = 0;
        for (Version.ECB ecb2 : eCBlocks) {
            int i22 = 0;
            while (i22 < ecb2.getCount()) {
                int dataCodewords = ecb2.getDataCodewords();
                mcVarArr[i21] = new mc(dataCodewords, new byte[eCBlocksForLevel.getECCodewordsPerBlock() + dataCodewords]);
                i22++;
                i21++;
            }
        }
        int length2 = mcVarArr[0].b.length;
        int i23 = count - 1;
        while (i23 >= 0 && mcVarArr[i23].b.length != length2) {
            i23--;
        }
        int i24 = i23 + 1;
        int eCCodewordsPerBlock = length2 - eCBlocksForLevel.getECCodewordsPerBlock();
        int i25 = 0;
        for (int i26 = 0; i26 < eCCodewordsPerBlock; i26++) {
            int i27 = 0;
            while (i27 < i21) {
                mcVarArr[i27].b[i26] = bArr[i25];
                i27++;
                i25++;
            }
        }
        int i28 = i24;
        while (i28 < i21) {
            mcVarArr[i28].b[eCCodewordsPerBlock] = bArr[i25];
            i28++;
            i25++;
        }
        int length3 = mcVarArr[0].b.length;
        while (eCCodewordsPerBlock < length3) {
            int i29 = 0;
            while (i29 < i21) {
                mcVarArr[i29].b[i29 < i24 ? eCCodewordsPerBlock : eCCodewordsPerBlock + 1] = bArr[i25];
                i29++;
                i25++;
            }
            eCCodewordsPerBlock++;
        }
        int i30 = 0;
        for (int i31 = 0; i31 < count; i31++) {
            i30 += mcVarArr[i31].a;
        }
        byte[] bArr2 = new byte[i30];
        int i32 = 0;
        for (int i33 = 0; i33 < count; i33++) {
            mc mcVar = mcVarArr[i33];
            byte[] bArr3 = mcVar.b;
            int i34 = mcVar.a;
            int length4 = bArr3.length;
            int[] iArr = new int[length4];
            for (int i35 = 0; i35 < length4; i35++) {
                iArr[i35] = bArr3[i35] & 255;
            }
            try {
                this.a.decode(iArr, bArr3.length - i34);
                for (int i36 = 0; i36 < i34; i36++) {
                    bArr3[i36] = (byte) iArr[i36];
                }
                int i37 = 0;
                while (i37 < i34) {
                    bArr2[i32] = bArr3[i37];
                    i37++;
                    i32++;
                }
            } catch (ReedSolomonException unused) {
                throw ChecksumException.getChecksumInstance();
            }
        }
        return oc.a(bArr2, versionC, errorCorrectionLevel, map);
    }

    public DecoderResult decode(boolean[][] zArr) throws ChecksumException, FormatException {
        return decode(zArr, (Map<DecodeHintType, ?>) null);
    }

    public DecoderResult decode(boolean[][] zArr, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
        int length = zArr.length;
        BitMatrix bitMatrix = new BitMatrix(length);
        for (int i = 0; i < length; i++) {
            for (int i2 = 0; i2 < length; i2++) {
                if (zArr[i][i2]) {
                    bitMatrix.set(i2, i);
                }
            }
        }
        return decode(bitMatrix, map);
    }

    public DecoderResult decode(BitMatrix bitMatrix) throws ChecksumException, FormatException {
        return decode(bitMatrix, (Map<DecodeHintType, ?>) null);
    }

    public DecoderResult decode(BitMatrix bitMatrix, Map<DecodeHintType, ?> map) throws ChecksumException, FormatException {
        ChecksumException checksumException;
        lc lcVar = new lc(bitMatrix);
        try {
            return a(lcVar, map);
        } catch (ChecksumException e) {
            checksumException = e;
            e = null;
            try {
                lcVar.d();
                lcVar.b = null;
                lcVar.c = null;
                lcVar.d = true;
                lcVar.c();
                lcVar.b();
                lcVar.a();
                DecoderResult decoderResultA = a(lcVar, map);
                decoderResultA.setOther(new QRCodeDecoderMetaData(true));
                return decoderResultA;
            } catch (ChecksumException | FormatException e2) {
                if (e != null) {
                    throw e;
                }
                if (checksumException != null) {
                    throw checksumException;
                }
                throw e2;
            }
        } catch (FormatException e3) {
            e = e3;
            checksumException = null;
            lcVar.d();
            lcVar.b = null;
            lcVar.c = null;
            lcVar.d = true;
            lcVar.c();
            lcVar.b();
            lcVar.a();
            DecoderResult decoderResultA2 = a(lcVar, map);
            decoderResultA2.setOther(new QRCodeDecoderMetaData(true));
            return decoderResultA2;
        }
    }
}
