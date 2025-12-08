package com.journeyapps.barcodescanner;

import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import com.google.zxing.LuminanceSource;
import com.google.zxing.Result;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.camera.CameraInstance;
import com.journeyapps.barcodescanner.camera.PreviewCallback;
import defpackage.g9;

/* loaded from: classes.dex */
public class DecoderThread {
    public CameraInstance a;
    public HandlerThread b;
    public Handler c;
    public Decoder d;
    public Handler e;
    public Rect f;
    public boolean g = false;
    public final Object h = new Object();
    public final Handler.Callback i = new a();
    public final PreviewCallback j = new b();

    public class a implements Handler.Callback {
        public a() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i != R.id.zxing_decode) {
                if (i != R.id.zxing_preview_failed) {
                    return true;
                }
                DecoderThread.this.a();
                return true;
            }
            DecoderThread decoderThread = DecoderThread.this;
            SourceData sourceData = (SourceData) message.obj;
            if (decoderThread == null) {
                throw null;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            sourceData.setCropRect(decoderThread.f);
            LuminanceSource luminanceSourceCreateSource = decoderThread.createSource(sourceData);
            Result resultDecode = luminanceSourceCreateSource != null ? decoderThread.d.decode(luminanceSourceCreateSource) : null;
            if (resultDecode != null) {
                long jCurrentTimeMillis2 = System.currentTimeMillis();
                StringBuilder sbA = g9.a("Found barcode in ");
                sbA.append(jCurrentTimeMillis2 - jCurrentTimeMillis);
                sbA.append(" ms");
                Log.d("DecoderThread", sbA.toString());
                if (decoderThread.e != null) {
                    Message messageObtain = Message.obtain(decoderThread.e, R.id.zxing_decode_succeeded, new BarcodeResult(resultDecode, sourceData));
                    messageObtain.setData(new Bundle());
                    messageObtain.sendToTarget();
                }
            } else {
                Handler handler = decoderThread.e;
                if (handler != null) {
                    Message.obtain(handler, R.id.zxing_decode_failed).sendToTarget();
                }
            }
            if (decoderThread.e != null) {
                Message.obtain(decoderThread.e, R.id.zxing_possible_result_points, decoderThread.d.getPossibleResultPoints()).sendToTarget();
            }
            decoderThread.a();
            return true;
        }
    }

    public class b implements PreviewCallback {
        public b() {
        }

        @Override // com.journeyapps.barcodescanner.camera.PreviewCallback
        public void onPreview(SourceData sourceData) {
            synchronized (DecoderThread.this.h) {
                if (DecoderThread.this.g) {
                    DecoderThread.this.c.obtainMessage(R.id.zxing_decode, sourceData).sendToTarget();
                }
            }
        }

        @Override // com.journeyapps.barcodescanner.camera.PreviewCallback
        public void onPreviewError(Exception exc) {
            synchronized (DecoderThread.this.h) {
                if (DecoderThread.this.g) {
                    DecoderThread.this.c.obtainMessage(R.id.zxing_preview_failed).sendToTarget();
                }
            }
        }
    }

    public DecoderThread(CameraInstance cameraInstance, Decoder decoder, Handler handler) {
        Util.validateMainThread();
        this.a = cameraInstance;
        this.d = decoder;
        this.e = handler;
    }

    public final void a() {
        if (this.a.isOpen()) {
            this.a.requestPreview(this.j);
        }
    }

    public LuminanceSource createSource(SourceData sourceData) {
        if (this.f == null) {
            return null;
        }
        return sourceData.createSource();
    }

    public Rect getCropRect() {
        return this.f;
    }

    public Decoder getDecoder() {
        return this.d;
    }

    public void setCropRect(Rect rect) {
        this.f = rect;
    }

    public void setDecoder(Decoder decoder) {
        this.d = decoder;
    }

    public void start() {
        Util.validateMainThread();
        HandlerThread handlerThread = new HandlerThread("DecoderThread");
        this.b = handlerThread;
        handlerThread.start();
        this.c = new Handler(this.b.getLooper(), this.i);
        this.g = true;
        a();
    }

    public void stop() {
        Util.validateMainThread();
        synchronized (this.h) {
            this.g = false;
            this.c.removeCallbacksAndMessages(null);
            this.b.quit();
        }
    }
}
