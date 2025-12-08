package com.google.zxing.pdf417;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.ChecksumException;
import com.google.zxing.DecodeHintType;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.pdf417.decoder.PDF417ScanningDecoder;
import com.google.zxing.pdf417.detector.Detector;
import com.google.zxing.pdf417.detector.PDF417DetectorResult;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public final class PDF417Reader implements Reader, MultipleBarcodeReader {
    public static Result[] a(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map, boolean z) throws NotFoundException, ChecksumException, FormatException {
        ArrayList arrayList = new ArrayList();
        PDF417DetectorResult pDF417DetectorResultDetect = Detector.detect(binaryBitmap, map, z);
        for (ResultPoint[] resultPointArr : pDF417DetectorResultDetect.getPoints()) {
            DecoderResult decoderResultDecode = PDF417ScanningDecoder.decode(pDF417DetectorResultDetect.getBits(), resultPointArr[4], resultPointArr[5], resultPointArr[6], resultPointArr[7], Math.min(Math.min(b(resultPointArr[0], resultPointArr[4]), (b(resultPointArr[6], resultPointArr[2]) * 17) / 18), Math.min(b(resultPointArr[1], resultPointArr[5]), (b(resultPointArr[7], resultPointArr[3]) * 17) / 18)), Math.max(Math.max(a(resultPointArr[0], resultPointArr[4]), (a(resultPointArr[6], resultPointArr[2]) * 17) / 18), Math.max(a(resultPointArr[1], resultPointArr[5]), (a(resultPointArr[7], resultPointArr[3]) * 17) / 18)));
            Result result = new Result(decoderResultDecode.getText(), decoderResultDecode.getRawBytes(), resultPointArr, BarcodeFormat.PDF_417);
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, decoderResultDecode.getECLevel());
            PDF417ResultMetadata pDF417ResultMetadata = (PDF417ResultMetadata) decoderResultDecode.getOther();
            if (pDF417ResultMetadata != null) {
                result.putMetadata(ResultMetadataType.PDF417_EXTRA_METADATA, pDF417ResultMetadata);
            }
            arrayList.add(result);
        }
        return (Result[]) arrayList.toArray(new Result[arrayList.size()]);
    }

    public static int b(ResultPoint resultPoint, ResultPoint resultPoint2) {
        if (resultPoint == null || resultPoint2 == null) {
            return Integer.MAX_VALUE;
        }
        return (int) Math.abs(resultPoint.getX() - resultPoint2.getX());
    }

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return decode(binaryBitmap, null);
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return decodeMultiple(binaryBitmap, null);
    }

    @Override // com.google.zxing.Reader
    public void reset() {
    }

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        Result[] resultArrA = a(binaryBitmap, map, false);
        if (resultArrA == null || resultArrA.length == 0 || resultArrA[0] == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        return resultArrA[0];
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        try {
            return a(binaryBitmap, map, true);
        } catch (ChecksumException | FormatException unused) {
            throw NotFoundException.getNotFoundInstance();
        }
    }

    public static int a(ResultPoint resultPoint, ResultPoint resultPoint2) {
        if (resultPoint == null || resultPoint2 == null) {
            return 0;
        }
        return (int) Math.abs(resultPoint.getX() - resultPoint2.getX());
    }
}
