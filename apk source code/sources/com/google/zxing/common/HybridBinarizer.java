package com.google.zxing.common;

import com.google.zxing.Binarizer;
import com.google.zxing.LuminanceSource;
import com.google.zxing.NotFoundException;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public final class HybridBinarizer extends GlobalHistogramBinarizer {
    public BitMatrix e;

    public HybridBinarizer(LuminanceSource luminanceSource) {
        super(luminanceSource);
    }

    @Override // com.google.zxing.common.GlobalHistogramBinarizer, com.google.zxing.Binarizer
    public Binarizer createBinarizer(LuminanceSource luminanceSource) {
        return new HybridBinarizer(luminanceSource);
    }

    @Override // com.google.zxing.common.GlobalHistogramBinarizer, com.google.zxing.Binarizer
    public BitMatrix getBlackMatrix() throws NotFoundException {
        int i;
        int i2;
        BitMatrix bitMatrix = this.e;
        if (bitMatrix != null) {
            return bitMatrix;
        }
        LuminanceSource luminanceSource = getLuminanceSource();
        int width = luminanceSource.getWidth();
        int height = luminanceSource.getHeight();
        if (width < 40 || height < 40) {
            this.e = super.getBlackMatrix();
        } else {
            byte[] matrix = luminanceSource.getMatrix();
            int i3 = width >> 3;
            if ((width & 7) != 0) {
                i3++;
            }
            int i4 = height >> 3;
            if ((height & 7) != 0) {
                i4++;
            }
            boolean z = true;
            int i5 = 0;
            int[][] iArr = (int[][]) Array.newInstance((Class<?>) int.class, i4, i3);
            int i6 = 0;
            while (true) {
                int i7 = 8;
                if (i6 >= i4) {
                    break;
                }
                int i8 = i6 << 3;
                int i9 = height - 8;
                if (i8 > i9) {
                    i8 = i9;
                }
                int i10 = i5;
                while (i10 < i3) {
                    int i11 = i10 << 3;
                    int i12 = width - 8;
                    if (i11 > i12) {
                        i11 = i12;
                    }
                    int i13 = (i8 * width) + i11;
                    int i14 = 255;
                    int i15 = 0;
                    int i16 = 0;
                    int i17 = 0;
                    while (i15 < i7) {
                        int i18 = i17;
                        int i19 = 0;
                        while (i19 < i7) {
                            int i20 = i13;
                            int i21 = matrix[i13 + i19] & 255;
                            i16 += i21;
                            if (i21 < i14) {
                                i14 = i21;
                            }
                            if (i21 > i18) {
                                i18 = i21;
                            }
                            i19++;
                            i13 = i20;
                            i7 = 8;
                        }
                        int i22 = i13;
                        if (i18 - i14 > 24) {
                            i2 = i22;
                            while (true) {
                                i15++;
                                i2 += width;
                                if (i15 >= 8) {
                                    break;
                                }
                                int i23 = 0;
                                for (int i24 = 8; i23 < i24; i24 = 8) {
                                    i16 += matrix[i2 + i23] & 255;
                                    i23++;
                                    i14 = i14;
                                }
                            }
                            i = i14;
                        } else {
                            i = i14;
                            i2 = i22;
                        }
                        i15++;
                        i13 = i2 + width;
                        i14 = i;
                        i7 = 8;
                        i17 = i18;
                    }
                    int i25 = i16 >> 6;
                    if (i17 - i14 <= 24) {
                        i25 = i14 / 2;
                        if (i6 > 0 && i10 > 0) {
                            int i26 = i6 - 1;
                            int i27 = i10 - 1;
                            int i28 = (((iArr[i6][i27] * 2) + iArr[i26][i10]) + iArr[i26][i27]) / 4;
                            if (i14 < i28) {
                                i25 = i28;
                            }
                        }
                    }
                    iArr[i6][i10] = i25;
                    i10++;
                    z = true;
                    i7 = 8;
                }
                i6++;
                i5 = 0;
            }
            BitMatrix bitMatrix2 = new BitMatrix(width, height);
            for (int i29 = 0; i29 < i4; i29++) {
                int i30 = i29 << 3;
                int i31 = height - 8;
                if (i30 > i31) {
                    i30 = i31;
                }
                int i32 = 0;
                while (i32 < i3) {
                    int i33 = i32 << 3;
                    int i34 = width - 8;
                    if (i33 > i34) {
                        i33 = i34;
                    }
                    int i35 = i3 - 3;
                    int i36 = i32 < 2 ? 2 : i32 > i35 ? i35 : i32;
                    int i37 = i4 - 3;
                    if (i29 < 2) {
                        i37 = 2;
                    } else if (i29 <= i37) {
                        i37 = i29;
                    }
                    int i38 = -2;
                    int i39 = 0;
                    for (int i40 = 2; i38 <= i40; i40 = 2) {
                        int[] iArr2 = iArr[i37 + i38];
                        i39 = iArr2[i36 - 2] + iArr2[i36 - 1] + iArr2[i36] + iArr2[i36 + 1] + iArr2[i36 + 2] + i39;
                        i38++;
                    }
                    int i41 = i39 / 25;
                    int i42 = (i30 * width) + i33;
                    int i43 = 0;
                    while (true) {
                        if (i43 < 8) {
                            int i44 = height;
                            int i45 = 0;
                            for (int i46 = 8; i45 < i46; i46 = 8) {
                                byte[] bArr = matrix;
                                if ((matrix[i42 + i45] & 255) <= i41) {
                                    bitMatrix2.set(i33 + i45, i30 + i43);
                                }
                                i45++;
                                matrix = bArr;
                            }
                            i43++;
                            i42 += width;
                            height = i44;
                        }
                    }
                    i32++;
                }
            }
            this.e = bitMatrix2;
        }
        return this.e;
    }
}
