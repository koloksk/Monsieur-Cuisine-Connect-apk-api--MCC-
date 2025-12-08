package com.google.zxing.multi;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class GenericMultipleBarcodeReader implements MultipleBarcodeReader {
    public final Reader a;

    public GenericMultipleBarcodeReader(Reader reader) {
        this.a = reader;
    }

    public final void a(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, List<Result> list, int i, int i2, int i3) {
        boolean z;
        float f;
        float f2;
        int i4;
        int i5;
        Result result;
        if (i3 > 4) {
            return;
        }
        try {
            Result resultDecode = this.a.decode(binaryBitmap, map);
            Iterator<Result> it = list.iterator();
            while (true) {
                if (it.hasNext()) {
                    if (it.next().getText().equals(resultDecode.getText())) {
                        z = true;
                        break;
                    }
                } else {
                    z = false;
                    break;
                }
            }
            if (!z) {
                ResultPoint[] resultPoints = resultDecode.getResultPoints();
                if (resultPoints == null) {
                    result = resultDecode;
                } else {
                    ResultPoint[] resultPointArr = new ResultPoint[resultPoints.length];
                    for (int i6 = 0; i6 < resultPoints.length; i6++) {
                        ResultPoint resultPoint = resultPoints[i6];
                        if (resultPoint != null) {
                            resultPointArr[i6] = new ResultPoint(resultPoint.getX() + i, resultPoint.getY() + i2);
                        }
                    }
                    result = new Result(resultDecode.getText(), resultDecode.getRawBytes(), resultDecode.getNumBits(), resultPointArr, resultDecode.getBarcodeFormat(), resultDecode.getTimestamp());
                    result.putAllMetadata(resultDecode.getResultMetadata());
                }
                list.add(result);
            }
            ResultPoint[] resultPoints2 = resultDecode.getResultPoints();
            if (resultPoints2 == null || resultPoints2.length == 0) {
                return;
            }
            int width = binaryBitmap.getWidth();
            int height = binaryBitmap.getHeight();
            float f3 = width;
            float f4 = 0.0f;
            float f5 = height;
            float f6 = 0.0f;
            for (ResultPoint resultPoint2 : resultPoints2) {
                if (resultPoint2 != null) {
                    float x = resultPoint2.getX();
                    float y = resultPoint2.getY();
                    if (x < f3) {
                        f3 = x;
                    }
                    if (y < f5) {
                        f5 = y;
                    }
                    if (x > f6) {
                        f6 = x;
                    }
                    if (y > f4) {
                        f4 = y;
                    }
                }
            }
            if (f3 > 100.0f) {
                f = f6;
                f2 = f5;
                i4 = height;
                i5 = width;
                a(binaryBitmap.crop(0, 0, (int) f3, height), map, list, i, i2, i3 + 1);
            } else {
                f = f6;
                f2 = f5;
                i4 = height;
                i5 = width;
            }
            if (f2 > 100.0f) {
                a(binaryBitmap.crop(0, 0, i5, (int) f2), map, list, i, i2, i3 + 1);
            }
            float f7 = f;
            if (f7 < i5 - 100) {
                int i7 = (int) f7;
                a(binaryBitmap.crop(i7, 0, i5 - i7, i4), map, list, i + i7, i2, i3 + 1);
            }
            if (f4 < i4 - 100) {
                int i8 = (int) f4;
                a(binaryBitmap.crop(0, i8, i5, i4 - i8), map, list, i, i2 + i8, i3 + 1);
            }
        } catch (ReaderException unused) {
        }
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return decodeMultiple(binaryBitmap, null);
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        ArrayList arrayList = new ArrayList();
        a(binaryBitmap, map, arrayList, 0, 0, 0);
        if (arrayList.isEmpty()) {
            throw NotFoundException.getNotFoundInstance();
        }
        return (Result[]) arrayList.toArray(new Result[arrayList.size()]);
    }
}
