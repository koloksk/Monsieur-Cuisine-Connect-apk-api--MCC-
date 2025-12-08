package com.google.zxing.pdf417.decoder;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.pdf417.decoder.ec.ErrorCorrection;
import defpackage.ac;
import defpackage.bc;
import defpackage.cc;
import defpackage.dc;
import defpackage.fc;
import defpackage.yb;
import defpackage.zb;
import java.util.Formatter;

/* loaded from: classes.dex */
public final class PDF417ScanningDecoder {
    public static final ErrorCorrection a = new ErrorCorrection();

    public static ac a(fc fcVar) throws NotFoundException {
        int[] iArr;
        int i;
        ResultPoint resultPoint;
        ResultPoint resultPoint2;
        ResultPoint resultPoint3;
        ResultPoint resultPoint4;
        if (fcVar == null) {
            return null;
        }
        yb ybVarA = fcVar.a();
        if (ybVarA == null) {
            iArr = null;
        } else {
            ac acVar = fcVar.a;
            int iB = fcVar.b((int) (fcVar.c ? acVar.c : acVar.e).getY());
            bc[] bcVarArr = fcVar.b;
            int i2 = 0;
            int i3 = -1;
            int iMax = 1;
            for (int iB2 = fcVar.b((int) (fcVar.c ? acVar.b : acVar.d).getY()); iB2 < iB; iB2++) {
                if (bcVarArr[iB2] != null) {
                    bc bcVar = bcVarArr[iB2];
                    bcVar.b();
                    int i4 = bcVar.e;
                    int i5 = i4 - i3;
                    if (i5 == 0) {
                        i2++;
                    } else if (i5 == 1) {
                        iMax = Math.max(iMax, i2);
                        i3 = bcVar.e;
                        i2 = 1;
                    } else if (i4 >= ybVarA.e) {
                        bcVarArr[iB2] = null;
                    } else {
                        i2 = 1;
                        i3 = i4;
                    }
                }
            }
            int i6 = ybVarA.e;
            iArr = new int[i6];
            for (bc bcVar2 : fcVar.b) {
                if (bcVar2 != null && (i = bcVar2.e) < i6) {
                    iArr[i] = iArr[i] + 1;
                }
            }
        }
        if (iArr == null) {
            return null;
        }
        int iMax2 = -1;
        for (int i7 : iArr) {
            iMax2 = Math.max(iMax2, i7);
        }
        int i8 = 0;
        for (int i9 : iArr) {
            i8 += iMax2 - i9;
            if (i9 > 0) {
                break;
            }
        }
        bc[] bcVarArr2 = fcVar.b;
        for (int i10 = 0; i8 > 0 && bcVarArr2[i10] == null; i10++) {
            i8--;
        }
        int i11 = 0;
        for (int length = iArr.length - 1; length >= 0; length--) {
            i11 += iMax2 - iArr[length];
            if (iArr[length] > 0) {
                break;
            }
        }
        for (int length2 = bcVarArr2.length - 1; i11 > 0 && bcVarArr2[length2] == null; length2--) {
            i11--;
        }
        ac acVar2 = fcVar.a;
        boolean z = fcVar.c;
        ResultPoint resultPoint5 = acVar2.b;
        ResultPoint resultPoint6 = acVar2.c;
        ResultPoint resultPoint7 = acVar2.d;
        ResultPoint resultPoint8 = acVar2.e;
        if (i8 <= 0) {
            resultPoint = resultPoint5;
            resultPoint2 = resultPoint7;
        } else {
            ResultPoint resultPoint9 = z ? resultPoint5 : resultPoint7;
            ResultPoint resultPoint10 = new ResultPoint(resultPoint9.getX(), ((int) resultPoint9.getY()) - i8 >= 0 ? r10 : 0);
            if (z) {
                resultPoint5 = resultPoint10;
                resultPoint = resultPoint5;
                resultPoint2 = resultPoint7;
            } else {
                resultPoint = resultPoint5;
                resultPoint2 = resultPoint10;
            }
        }
        if (i11 <= 0) {
            resultPoint3 = resultPoint6;
            resultPoint4 = resultPoint8;
        } else {
            ResultPoint resultPoint11 = z ? acVar2.c : acVar2.e;
            int y = ((int) resultPoint11.getY()) + i11;
            if (y >= acVar2.a.getHeight()) {
                y = acVar2.a.getHeight() - 1;
            }
            ResultPoint resultPoint12 = new ResultPoint(resultPoint11.getX(), y);
            if (z) {
                resultPoint6 = resultPoint12;
                resultPoint3 = resultPoint6;
                resultPoint4 = resultPoint8;
            } else {
                resultPoint4 = resultPoint12;
                resultPoint3 = resultPoint6;
            }
        }
        acVar2.a();
        return new ac(acVar2.a, resultPoint, resultPoint3, resultPoint2, resultPoint4);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:117:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:119:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01da  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x01db A[PHI: r4
  0x01db: PHI (r4v38 int) = (r4v37 int), (r4v40 int) binds: [B:123:0x01da, B:121:0x01ba] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0065  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x006d  */
    /* JADX WARN: Removed duplicated region for block: B:320:0x00b6 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x006f  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x009b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.google.zxing.common.DecoderResult decode(com.google.zxing.common.BitMatrix r20, com.google.zxing.ResultPoint r21, com.google.zxing.ResultPoint r22, com.google.zxing.ResultPoint r23, com.google.zxing.ResultPoint r24, int r25, int r26) throws com.google.zxing.NotFoundException, com.google.zxing.ChecksumException, com.google.zxing.FormatException {
        /*
            Method dump skipped, instructions count: 1297
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.PDF417ScanningDecoder.decode(com.google.zxing.common.BitMatrix, com.google.zxing.ResultPoint, com.google.zxing.ResultPoint, com.google.zxing.ResultPoint, com.google.zxing.ResultPoint, int, int):com.google.zxing.common.DecoderResult");
    }

    public static String toString(zb[][] zbVarArr) {
        Formatter formatter = new Formatter();
        for (int i = 0; i < zbVarArr.length; i++) {
            formatter.format("Row %2d: ", Integer.valueOf(i));
            for (int i2 = 0; i2 < zbVarArr[i].length; i2++) {
                zb zbVar = zbVarArr[i][i2];
                if (zbVar.a().length == 0) {
                    formatter.format("        ", null);
                } else {
                    formatter.format("%4d(%2d)", Integer.valueOf(zbVar.a()[0]), zbVar.a.get(Integer.valueOf(zbVar.a()[0])));
                }
            }
            formatter.format("%n", new Object[0]);
        }
        String string = formatter.toString();
        formatter.close();
        return string;
    }

    public static fc a(BitMatrix bitMatrix, ac acVar, ResultPoint resultPoint, boolean z, int i, int i2) {
        int i3;
        fc fcVar = new fc(acVar, z);
        int i4 = 0;
        while (i4 < 2) {
            int i5 = i4 == 0 ? 1 : -1;
            int x = (int) resultPoint.getX();
            for (int y = (int) resultPoint.getY(); y <= acVar.i && y >= acVar.h; y += i5) {
                bc bcVarA = a(bitMatrix, 0, bitMatrix.getWidth(), z, x, y, i, i2);
                if (bcVarA != null) {
                    fcVar.b[y - fcVar.a.h] = bcVarA;
                    if (z) {
                        i3 = bcVarA.a;
                    } else {
                        i3 = bcVarA.b;
                    }
                    x = i3;
                }
            }
            i4++;
        }
        return fcVar;
    }

    public static boolean a(dc dcVar, int i) {
        return i >= 0 && i <= dcVar.d + 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:74:0x0032, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0032, code lost:
    
        continue;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0032, code lost:
    
        continue;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x001f  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x004c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static defpackage.bc a(com.google.zxing.common.BitMatrix r17, int r18, int r19, boolean r20, int r21, int r22, int r23, int r24) {
        /*
            Method dump skipped, instructions count: 219
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.pdf417.decoder.PDF417ScanningDecoder.a(com.google.zxing.common.BitMatrix, int, int, boolean, int, int, int, int):bc");
    }

    public static DecoderResult a(int[] iArr, int i, int[] iArr2) throws ChecksumException, FormatException {
        if (iArr.length != 0) {
            int i2 = 1 << (i + 1);
            if ((iArr2 == null || iArr2.length <= (i2 / 2) + 3) && i2 >= 0 && i2 <= 512) {
                int iDecode = a.decode(iArr, i2, iArr2);
                if (iArr.length >= 4) {
                    int i3 = iArr[0];
                    if (i3 <= iArr.length) {
                        if (i3 == 0) {
                            if (i2 < iArr.length) {
                                iArr[0] = iArr.length - i2;
                            } else {
                                throw FormatException.getFormatInstance();
                            }
                        }
                        DecoderResult decoderResultA = cc.a(iArr, String.valueOf(i));
                        decoderResultA.setErrorsCorrected(Integer.valueOf(iDecode));
                        decoderResultA.setErasures(Integer.valueOf(iArr2.length));
                        return decoderResultA;
                    }
                    throw FormatException.getFormatInstance();
                }
                throw FormatException.getFormatInstance();
            }
            throw ChecksumException.getChecksumInstance();
        }
        throw FormatException.getFormatInstance();
    }
}
