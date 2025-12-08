package com.journeyapps.barcodescanner;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import com.google.zxing.DecodeHintType;
import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.R;
import java.util.HashMap;
import java.util.List;

/* loaded from: classes.dex */
public class BarcodeView extends CameraPreview {
    public b B;
    public BarcodeCallback C;
    public DecoderThread D;
    public DecoderFactory E;
    public Handler F;
    public final Handler.Callback G;

    public class a implements Handler.Callback {
        public a() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            BarcodeView barcodeView;
            BarcodeCallback barcodeCallback;
            int i = message.what;
            if (i == R.id.zxing_decode_succeeded) {
                BarcodeResult barcodeResult = (BarcodeResult) message.obj;
                if (barcodeResult != null && (barcodeCallback = (barcodeView = BarcodeView.this).C) != null && barcodeView.B != b.NONE) {
                    barcodeCallback.barcodeResult(barcodeResult);
                    BarcodeView barcodeView2 = BarcodeView.this;
                    if (barcodeView2.B == b.SINGLE) {
                        barcodeView2.stopDecoding();
                    }
                }
                return true;
            }
            if (i == R.id.zxing_decode_failed) {
                return true;
            }
            if (i != R.id.zxing_possible_result_points) {
                return false;
            }
            List<ResultPoint> list = (List) message.obj;
            BarcodeView barcodeView3 = BarcodeView.this;
            BarcodeCallback barcodeCallback2 = barcodeView3.C;
            if (barcodeCallback2 != null && barcodeView3.B != b.NONE) {
                barcodeCallback2.possibleResultPoints(list);
            }
            return true;
        }
    }

    public enum b {
        NONE,
        SINGLE,
        CONTINUOUS
    }

    public BarcodeView(Context context) {
        super(context);
        this.B = b.NONE;
        this.C = null;
        this.G = new a();
        c();
    }

    public final Decoder b() {
        if (this.E == null) {
            this.E = createDefaultDecoderFactory();
        }
        DecoderResultPointCallback decoderResultPointCallback = new DecoderResultPointCallback();
        HashMap map = new HashMap();
        map.put(DecodeHintType.NEED_RESULT_POINT_CALLBACK, decoderResultPointCallback);
        Decoder decoderCreateDecoder = this.E.createDecoder(map);
        decoderResultPointCallback.setDecoder(decoderCreateDecoder);
        return decoderCreateDecoder;
    }

    public final void c() {
        this.E = new DefaultDecoderFactory();
        this.F = new Handler(this.G);
    }

    public DecoderFactory createDefaultDecoderFactory() {
        return new DefaultDecoderFactory();
    }

    public final void d() {
        e();
        if (this.B == b.NONE || !isPreviewActive()) {
            return;
        }
        DecoderThread decoderThread = new DecoderThread(getCameraInstance(), b(), this.F);
        this.D = decoderThread;
        decoderThread.setCropRect(getPreviewFramingRect());
        this.D.start();
    }

    public void decodeContinuous(BarcodeCallback barcodeCallback) {
        this.B = b.CONTINUOUS;
        this.C = barcodeCallback;
        d();
    }

    public void decodeSingle(BarcodeCallback barcodeCallback) {
        this.B = b.SINGLE;
        this.C = barcodeCallback;
        d();
    }

    public final void e() {
        DecoderThread decoderThread = this.D;
        if (decoderThread != null) {
            decoderThread.stop();
            this.D = null;
        }
    }

    public DecoderFactory getDecoderFactory() {
        return this.E;
    }

    @Override // com.journeyapps.barcodescanner.CameraPreview
    public void pause() {
        e();
        super.pause();
    }

    @Override // com.journeyapps.barcodescanner.CameraPreview
    public void previewStarted() {
        super.previewStarted();
        d();
    }

    public void setDecoderFactory(DecoderFactory decoderFactory) {
        Util.validateMainThread();
        this.E = decoderFactory;
        DecoderThread decoderThread = this.D;
        if (decoderThread != null) {
            decoderThread.setDecoder(b());
        }
    }

    public void stopDecoding() {
        this.B = b.NONE;
        this.C = null;
        e();
    }

    public BarcodeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.B = b.NONE;
        this.C = null;
        this.G = new a();
        c();
    }

    public BarcodeView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.B = b.NONE;
        this.C = null;
        this.G = new a();
        c();
    }
}
