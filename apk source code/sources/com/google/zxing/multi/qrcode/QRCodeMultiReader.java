package com.google.zxing.multi.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.NotFoundException;
import com.google.zxing.ReaderException;
import com.google.zxing.Result;
import com.google.zxing.ResultMetadataType;
import com.google.zxing.ResultPoint;
import com.google.zxing.common.DecoderResult;
import com.google.zxing.common.DetectorResult;
import com.google.zxing.multi.MultipleBarcodeReader;
import com.google.zxing.multi.qrcode.detector.MultiDetector;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.decoder.QRCodeDecoderMetaData;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class QRCodeMultiReader extends QRCodeReader implements MultipleBarcodeReader {
    public static final Result[] c = new Result[0];
    public static final ResultPoint[] d = new ResultPoint[0];

    public static final class b implements Serializable, Comparator<Result> {
        public /* synthetic */ b(a aVar) {
        }

        @Override // java.util.Comparator
        public int compare(Result result, Result result2) {
            int iIntValue = ((Integer) result.getResultMetadata().get(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)).intValue();
            int iIntValue2 = ((Integer) result2.getResultMetadata().get(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)).intValue();
            if (iIntValue < iIntValue2) {
                return -1;
            }
            return iIntValue > iIntValue2 ? 1 : 0;
        }
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap) throws NotFoundException {
        return decodeMultiple(binaryBitmap, null);
    }

    @Override // com.google.zxing.multi.MultipleBarcodeReader
    public Result[] decodeMultiple(BinaryBitmap binaryBitmap, Map<DecodeHintType, ?> map) throws NotFoundException {
        boolean z;
        ArrayList arrayList = new ArrayList();
        for (DetectorResult detectorResult : new MultiDetector(binaryBitmap.getBlackMatrix()).detectMulti(map)) {
            try {
                DecoderResult decoderResultDecode = getDecoder().decode(detectorResult.getBits(), map);
                ResultPoint[] points = detectorResult.getPoints();
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
                arrayList.add(result);
            } catch (ReaderException unused) {
            }
        }
        if (arrayList.isEmpty()) {
            return c;
        }
        Iterator it = arrayList.iterator();
        while (true) {
            if (!it.hasNext()) {
                z = false;
                break;
            }
            if (((Result) it.next()).getResultMetadata().containsKey(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)) {
                z = true;
                break;
            }
        }
        if (z) {
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                Result result2 = (Result) it2.next();
                arrayList2.add(result2);
                if (result2.getResultMetadata().containsKey(ResultMetadataType.STRUCTURED_APPEND_SEQUENCE)) {
                    arrayList3.add(result2);
                }
            }
            Collections.sort(arrayList3, new b(null));
            StringBuilder sb = new StringBuilder();
            Iterator it3 = arrayList3.iterator();
            int length = 0;
            int length2 = 0;
            while (it3.hasNext()) {
                Result result3 = (Result) it3.next();
                sb.append(result3.getText());
                length += result3.getRawBytes().length;
                if (result3.getResultMetadata().containsKey(ResultMetadataType.BYTE_SEGMENTS)) {
                    Iterator it4 = ((Iterable) result3.getResultMetadata().get(ResultMetadataType.BYTE_SEGMENTS)).iterator();
                    while (it4.hasNext()) {
                        length2 += ((byte[]) it4.next()).length;
                    }
                }
            }
            byte[] bArr = new byte[length];
            byte[] bArr2 = new byte[length2];
            Iterator it5 = arrayList3.iterator();
            int length3 = 0;
            int length4 = 0;
            while (it5.hasNext()) {
                Result result4 = (Result) it5.next();
                System.arraycopy(result4.getRawBytes(), 0, bArr, length3, result4.getRawBytes().length);
                length3 += result4.getRawBytes().length;
                if (result4.getResultMetadata().containsKey(ResultMetadataType.BYTE_SEGMENTS)) {
                    for (byte[] bArr3 : (Iterable) result4.getResultMetadata().get(ResultMetadataType.BYTE_SEGMENTS)) {
                        System.arraycopy(bArr3, 0, bArr2, length4, bArr3.length);
                        length4 += bArr3.length;
                    }
                }
            }
            Result result5 = new Result(sb.toString(), bArr, d, BarcodeFormat.QR_CODE);
            if (length2 > 0) {
                ArrayList arrayList4 = new ArrayList();
                arrayList4.add(bArr2);
                result5.putMetadata(ResultMetadataType.BYTE_SEGMENTS, arrayList4);
            }
            arrayList2.add(result5);
            arrayList = arrayList2;
        }
        return (Result[]) arrayList.toArray(new Result[arrayList.size()]);
    }
}
