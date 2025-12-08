package com.google.zxing.maxicode;

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
import com.google.zxing.maxicode.decoder.Decoder;
import java.util.Map;

/* loaded from: classes.dex */
public final class MaxiCodeReader implements Reader {
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
        if (map == null || !map.containsKey(DecodeHintType.PURE_BARCODE)) {
            throw NotFoundException.getNotFoundInstance();
        }
        BitMatrix blackMatrix = binaryBitmap.getBlackMatrix();
        int[] enclosingRectangle = blackMatrix.getEnclosingRectangle();
        if (enclosingRectangle == null) {
            throw NotFoundException.getNotFoundInstance();
        }
        int i = enclosingRectangle[0];
        int i2 = enclosingRectangle[1];
        int i3 = enclosingRectangle[2];
        int i4 = enclosingRectangle[3];
        BitMatrix bitMatrix = new BitMatrix(30, 33);
        for (int i5 = 0; i5 < 33; i5++) {
            int i6 = (((i4 / 2) + (i5 * i4)) / 33) + i2;
            for (int i7 = 0; i7 < 30; i7++) {
                if (blackMatrix.get((((((i5 & 1) * i3) / 2) + ((i3 / 2) + (i7 * i3))) / 30) + i, i6)) {
                    bitMatrix.set(i7, i5);
                }
            }
        }
        DecoderResult decoderResultDecode = this.a.decode(bitMatrix, map);
        Result result = new Result(decoderResultDecode.getText(), decoderResultDecode.getRawBytes(), b, BarcodeFormat.MAXICODE);
        String eCLevel = decoderResultDecode.getECLevel();
        if (eCLevel != null) {
            result.putMetadata(ResultMetadataType.ERROR_CORRECTION_LEVEL, eCLevel);
        }
        return result;
    }
}
