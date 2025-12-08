package com.google.zxing.qrcode;

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
import com.google.zxing.qrcode.decoder.Decoder;
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
import com.google.zxing.qrcode.detector.Detector;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public class QRCodeReader implements Reader {
    public static final ResultPoint[] b = new ResultPoint[0];
    public final Decoder a = new Decoder();

    @Override // com.google.zxing.Reader
    public Result decode(BinaryBitmap binaryBitmap) throws NotFoundException, ChecksumException, FormatException {
        return decode(binaryBitmap, null);
    }

    public final Decoder getDecoder() {
        return this.a;
    }

    @Override // com.google.zxing.Reader
    public void reset() {
    }

    @Override // com.google.zxing.Reader
    public final Result decode(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException, ChecksumException, FormatException {
        ResultPoint[] points;
        DecoderResult decoderResultDecode;
        if (map == null || !map.containsKey(DecodeHintType.PURE_BARCODE)) {
            DetectorResult detectorResultDetect = new Detector(binaryBitmap.getBlackMatrix()).detect(map);
            DecoderResult decoderResultDecode2 = this.a.decode(detectorResultDetect.getBits(), map);
            points = detectorResultDetect.getPoints();
            decoderResultDecode = decoderResultDecode2;
        } else {
            BitMatrix blackMatrix = binaryBitmap.getBlackMatrix();
            int[] topLeftOnBit = blackMatrix.getTopLeftOnBit();
            int[] bottomRightOnBit = blackMatrix.getBottomRightOnBit();
            if (topLeftOnBit == null || bottomRightOnBit == null) {
                throw NotFoundException.getNotFoundInstance();
            }
            int height = blackMatrix.getHeight();
            int width = blackMatrix.getWidth();
            int i = topLeftOnBit[0];
            int i2 = topLeftOnBit[1];
            int i3 = 0;
            boolean z = true;
            while (i < width && i2 < height) {
                if (z != blackMatrix.get(i, i2)) {
                    i3++;
                    if (i3 == 5) {
                        break;
                    }
                    z = !z;
                }
                i++;
                i2++;
            }
            if (i == width || i2 == height) {
                throw NotFoundException.getNotFoundInstance();
            }
            float f = (i - topLeftOnBit[0]) / 7.0f;
            int i4 = topLeftOnBit[1];
            int i5 = bottomRightOnBit[1];
            int i6 = topLeftOnBit[0];
            int i7 = bottomRightOnBit[0];
            if (i6 >= i7 || i4 >= i5) {
                throw NotFoundException.getNotFoundInstance();
            }
            int i8 = i5 - i4;
            if (i8 != i7 - i6 && (i7 = i6 + i8) >= blackMatrix.getWidth()) {
                throw NotFoundException.getNotFoundInstance();
            }
            int iRound = Math.round(((i7 - i6) + 1) / f);
            int iRound2 = Math.round((i8 + 1) / f);
            if (iRound <= 0 || iRound2 <= 0) {
                throw NotFoundException.getNotFoundInstance();
            }
            if (iRound2 != iRound) {
                throw NotFoundException.getNotFoundInstance();
            }
            int i9 = (int) (f / 2.0f);
            int i10 = i4 + i9;
            int i11 = i6 + i9;
            int i12 = (((int) ((iRound - 1) * f)) + i11) - i7;
            if (i12 > 0) {
                if (i12 > i9) {
                    throw NotFoundException.getNotFoundInstance();
                }
                i11 -= i12;
            }
            int i13 = (((int) ((iRound2 - 1) * f)) + i10) - i5;
            if (i13 > 0) {
                if (i13 > i9) {
                    throw NotFoundException.getNotFoundInstance();
                }
                i10 -= i13;
            }
            BitMatrix bitMatrix = new BitMatrix(iRound, iRound2);
            for (int i14 = 0; i14 < iRound2; i14++) {
                int i15 = ((int) (i14 * f)) + i10;
                for (int i16 = 0; i16 < iRound; i16++) {
                    if (blackMatrix.get(((int) (i16 * f)) + i11, i15)) {
                        bitMatrix.set(i16, i14);
                    }
                }
            }
            decoderResultDecode = this.a.decode(bitMatrix, map);
            points = b;
        }
        if (decoderResultDecode.getOther() instanceof QRCodeDecoderMetaData) {
            ((QRCodeDecoderMetaData) decoderResultDecode.getOther()).applyMirroredCorrection(points);
        }
        Result result = new Result(decoderResultDecode.getText(), decoderResultDecode.getRawBytes(), points, BarcodeFormat.QR_CODE);
        List<byte[]> byteSegments = decoderResultDecode.getByteSegments();
        if (byteSegments != null) {
            result.putMetadata(ResultMetadataType.BYTE_SEGMENTS, byteSegments);
        }
        String eCLevel = decoderResultDecode.getECLevel();
        if (eCLevel != null) {
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, eCLevel);
        }
        if (decoderResultDecode.hasStructuredAppend()) {
            result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE, Integer.valueOf(decoderResultDecode.getStructuredAppendSequenceNumber()));
            result.putMetadata(ResultMetadataType.STRUCTURED_APPEND_PARITY, Integer.valueOf(decoderResultDecode.getStructuredAppendParity()));
        }
        return result;
    }
}
