package com.journeyapps.barcodescanner;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.ResultPoint;
import com.google.zxing.ResultPointCallback;
import com.google.zxing.common.HybridBinarizer;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class Decoder implements ResultPointCallback {
    public Reader a;
    public List<ResultPoint> b = new ArrayList();

    public Decoder(Reader reader) {
        this.a = reader;
    }

    public Result decode(LuminanceSource luminanceSource) {
        return decode(toBitmap(luminanceSource));
    }

    @Override // com.google.zxing.ResultPointCallback
    public void foundPossibleResultPoint(ResultPoint resultPoint) {
        this.b.add(resultPoint);
    }

    public List<ResultPoint> getPossibleResultPoints() {
        return new ArrayList(this.b);
    }

    public Reader getReader() {
        return this.a;
    }

    public BinaryBitmap toBitmap(LuminanceSource luminanceSource) {
        return new BinaryBitmap(new HybridBinarizer(luminanceSource));
    }

    public Result decode(BinaryBitmap binaryBitmap) {
        Result resultDecodeWithState;
        this.b.clear();
        try {
            resultDecodeWithState = this.a instanceof MultiFormatReader ? ((MultiFormatReader) this.a).decodeWithState(binaryBitmap) : this.a.decode(binaryBitmap);
        } catch (Exception unused) {
            resultDecodeWithState = null;
        } catch (Throwable th) {
            this.a.reset();
            throw th;
        }
        this.a.reset();
        return resultDecodeWithState;
    }
}
