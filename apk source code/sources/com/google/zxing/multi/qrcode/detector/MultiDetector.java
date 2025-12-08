package com.google.zxing.multi.qrcode.detector;

import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.qrcode.detector.Detector;
import com.google.zxing.qrcode.detector.FinderPattern;
import com.google.zxing.qrcode.detector.FinderPatternFinder;
import com.google.zxing.qrcode.detector.FinderPatternInfo;
import defpackage.ya;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class MultiDetector extends Detector {
    public static final DetectorResult[] c = new DetectorResult[0];

    public MultiDetector(BitMatrix bitMatrix) {
        super(bitMatrix);
    }

    public DetectorResult[] detectMulti(Map<DecodeHintType, ?> map) throws NotFoundException {
        char c2;
        FinderPattern[][] finderPatternArr;
        char c3;
        char c4;
        ya yaVar = new ya(getImage(), map == null ? null : (ResultPointCallback) map.get(DecodeHintType.NEED_RESULT_POINT_CALLBACK));
        char c5 = 0;
        boolean z = map != null && map.containsKey(DecodeHintType.TRY_HARDER);
        boolean z2 = map != null && map.containsKey(DecodeHintType.PURE_BARCODE);
        BitMatrix image = yaVar.getImage();
        int height = image.getHeight();
        int width = image.getWidth();
        int i = (int) ((height / 228.0f) * 3.0f);
        int i2 = 3;
        if (i < 3 || z) {
            i = 3;
        }
        int[] iArr = new int[5];
        int i3 = i - 1;
        while (true) {
            c2 = 2;
            if (i3 >= height) {
                break;
            }
            iArr[0] = 0;
            iArr[1] = 0;
            iArr[2] = 0;
            iArr[3] = 0;
            iArr[4] = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < width; i5++) {
                if (image.get(i5, i3)) {
                    if ((i4 & 1) == 1) {
                        i4++;
                    }
                    iArr[i4] = iArr[i4] + 1;
                } else if ((i4 & 1) != 0) {
                    iArr[i4] = iArr[i4] + 1;
                } else if (i4 != 4) {
                    i4++;
                    iArr[i4] = iArr[i4] + 1;
                } else if (FinderPatternFinder.foundPatternCross(iArr) && yaVar.handlePossibleCenter(iArr, i3, i5, z2)) {
                    iArr[0] = 0;
                    iArr[1] = 0;
                    iArr[2] = 0;
                    iArr[3] = 0;
                    iArr[4] = 0;
                    i4 = 0;
                } else {
                    iArr[0] = iArr[2];
                    iArr[1] = iArr[3];
                    iArr[2] = iArr[4];
                    iArr[3] = 1;
                    iArr[4] = 0;
                    i4 = 3;
                }
            }
            if (FinderPatternFinder.foundPatternCross(iArr)) {
                yaVar.handlePossibleCenter(iArr, i3, width, z2);
            }
            i3 += i;
        }
        List<FinderPattern> possibleCenters = yaVar.getPossibleCenters();
        int size = possibleCenters.size();
        if (size < 3) {
            throw NotFoundException.getNotFoundInstance();
        }
        if (size == 3) {
            finderPatternArr = new FinderPattern[][]{new FinderPattern[]{possibleCenters.get(0), possibleCenters.get(1), possibleCenters.get(2)}};
        } else {
            Collections.sort(possibleCenters, new ya.b(null));
            ArrayList arrayList = new ArrayList();
            int i6 = 0;
            while (i6 < size - 2) {
                FinderPattern finderPattern = possibleCenters.get(i6);
                if (finderPattern != null) {
                    int i7 = i6 + 1;
                    while (i7 < size - 1) {
                        FinderPattern finderPattern2 = possibleCenters.get(i7);
                        if (finderPattern2 != null) {
                            float estimatedModuleSize = (finderPattern.getEstimatedModuleSize() - finderPattern2.getEstimatedModuleSize()) / Math.min(finderPattern.getEstimatedModuleSize(), finderPattern2.getEstimatedModuleSize());
                            if (Math.abs(finderPattern.getEstimatedModuleSize() - finderPattern2.getEstimatedModuleSize()) <= 0.5f || estimatedModuleSize < 0.05f) {
                                int i8 = i7 + 1;
                                while (i8 < size) {
                                    FinderPattern finderPattern3 = possibleCenters.get(i8);
                                    if (finderPattern3 != null) {
                                        float estimatedModuleSize2 = (finderPattern2.getEstimatedModuleSize() - finderPattern3.getEstimatedModuleSize()) / Math.min(finderPattern2.getEstimatedModuleSize(), finderPattern3.getEstimatedModuleSize());
                                        if (Math.abs(finderPattern2.getEstimatedModuleSize() - finderPattern3.getEstimatedModuleSize()) > 0.5f && estimatedModuleSize2 >= 0.05f) {
                                            c3 = 2;
                                            break;
                                        }
                                        FinderPattern[] finderPatternArr2 = new FinderPattern[i2];
                                        finderPatternArr2[c5] = finderPattern;
                                        finderPatternArr2[1] = finderPattern2;
                                        c4 = 2;
                                        finderPatternArr2[2] = finderPattern3;
                                        ResultPoint.orderBestPatterns(finderPatternArr2);
                                        FinderPatternInfo finderPatternInfo = new FinderPatternInfo(finderPatternArr2);
                                        float fDistance = ResultPoint.distance(finderPatternInfo.getTopLeft(), finderPatternInfo.getBottomLeft());
                                        float fDistance2 = ResultPoint.distance(finderPatternInfo.getTopRight(), finderPatternInfo.getBottomLeft());
                                        float fDistance3 = ResultPoint.distance(finderPatternInfo.getTopLeft(), finderPatternInfo.getTopRight());
                                        float estimatedModuleSize3 = (fDistance + fDistance3) / (finderPattern.getEstimatedModuleSize() * 2.0f);
                                        if (estimatedModuleSize3 <= 180.0f && estimatedModuleSize3 >= 9.0f && Math.abs((fDistance - fDistance3) / Math.min(fDistance, fDistance3)) < 0.1f) {
                                            float fSqrt = (float) Math.sqrt((fDistance3 * fDistance3) + (fDistance * fDistance));
                                            if (Math.abs((fDistance2 - fSqrt) / Math.min(fDistance2, fSqrt)) < 0.1f) {
                                                arrayList.add(finderPatternArr2);
                                            }
                                        }
                                    } else {
                                        c4 = c2;
                                    }
                                    i8++;
                                    c2 = c4;
                                    c5 = 0;
                                    i2 = 3;
                                }
                                c3 = c2;
                            }
                        } else {
                            c3 = c2;
                        }
                        i7++;
                        c2 = c3;
                        c5 = 0;
                        i2 = 3;
                    }
                }
                i6++;
                c2 = c2;
                c5 = 0;
                i2 = 3;
            }
            if (arrayList.isEmpty()) {
                throw NotFoundException.getNotFoundInstance();
            }
            finderPatternArr = (FinderPattern[][]) arrayList.toArray(new FinderPattern[arrayList.size()][]);
        }
        ArrayList arrayList2 = new ArrayList();
        for (FinderPattern[] finderPatternArr3 : finderPatternArr) {
            ResultPoint.orderBestPatterns(finderPatternArr3);
            arrayList2.add(new FinderPatternInfo(finderPatternArr3));
        }
        FinderPatternInfo[] finderPatternInfoArr = arrayList2.isEmpty() ? ya.f : (FinderPatternInfo[]) arrayList2.toArray(new FinderPatternInfo[arrayList2.size()]);
        if (finderPatternInfoArr.length == 0) {
            throw NotFoundException.getNotFoundInstance();
        }
        ArrayList arrayList3 = new ArrayList();
        for (FinderPatternInfo finderPatternInfo2 : finderPatternInfoArr) {
            try {
                arrayList3.add(processFinderPatternInfo(finderPatternInfo2));
            } catch (ReaderException unused) {
            }
        }
        return arrayList3.isEmpty() ? c : (DetectorResult[]) arrayList3.toArray(new DetectorResult[arrayList3.size()]);
    }
}
