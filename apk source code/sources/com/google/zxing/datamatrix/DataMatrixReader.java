package com.google.zxing.datamatrix;

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
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.datamatrix.decoder.Decoder;
import com.google.zxing.datamatrix.detector.Detector;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class DataMatrixReader implements Reader {
    public static final ResultPoint[] b = new ResultPoint[0];
    public final Decoder a = new Decoder();

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return decode(binaryBitmap, null);
    }

    @Override // com.google.zxing.Reader
    public void reset() {
    }

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        ResultPoint[] points;
        DecoderResult decoderResultDecode;
        if (map == null || !map.containsKey(DecodeHintType.PURE_BARCODE)) {
            DetectorResult detectorResultDetect = new Detector(binaryBitmap.getBlackMatrix()).detect();
            DecoderResult decoderResultDecode2 = this.a.decode(detectorResultDetect.getBits());
            points = detectorResultDetect.getPoints();
            decoderResultDecode = decoderResultDecode2;
        } else {
            BitMatrix blackMatrix = binaryBitmap.getBlackMatrix();
            int[] topLeftOnBit = blackMatrix.getTopLeftOnBit();
            int[] bottomRightOnBit = blackMatrix.getBottomRightOnBit();
            if (topLeftOnBit == null || bottomRightOnBit == null) {
                throw NotFoundException.getNotFoundInstance();
            }
            int width = blackMatrix.getWidth();
            int i = topLeftOnBit[0];
            int i2 = topLeftOnBit[1];
            while (i < width && blackMatrix.get(i, i2)) {
                i++;
            }
            if (i == width) {
                throw NotFoundException.getNotFoundInstance();
            }
            int i3 = i - topLeftOnBit[0];
            if (i3 == 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            int i4 = topLeftOnBit[1];
            int i5 = bottomRightOnBit[1];
            int i6 = topLeftOnBit[0];
            int i7 = ((bottomRightOnBit[0] - i6) + 1) / i3;
            int i8 = ((i5 - i4) + 1) / i3;
            if (i7 <= 0 || i8 <= 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            int i9 = i3 / 2;
            int i10 = i4 + i9;
            int i11 = i6 + i9;
            BitMatrix bitMatrix = new BitMatrix(i7, i8);
            for (int i12 = 0; i12 < i8; i12++) {
                int i13 = (i12 * i3) + i10;
                for (int i14 = 0; i14 < i7; i14++) {
                    if (blackMatrix.get((i14 * i3) + i11, i13)) {
                        bitMatrix.set(i14, i12);
                    }
                }
            }
            decoderResultDecode = this.a.decode(bitMatrix);
            points = b;
        }
        Result result = new Result(decoderResultDecode.getText(), decoderResultDecode.getRawBytes(), points, BarcodeFormat.DATA_MATRIX);
        List<byte[]> byteSegments = decoderResultDecode.getByteSegments();
        if (byteSegments != null) {
            result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
        }
        String eCLevel = decoderResultDecode.getECLevel();
        if (eCLevel != null) {
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, eCLevel);
        }
        return result;
    }
}
