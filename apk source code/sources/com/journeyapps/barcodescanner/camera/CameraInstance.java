package com.journeyapps.barcodescanner.camera;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceHolder;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.Util;
import defpackage.uc;

/* loaded from: classes.dex */
public class CameraInstance {
    public uc a;
    public CameraSurface b;
    public CameraManager c;
    public Handler d;
    public DisplayConfiguration e;
    public boolean f = false;
    public boolean g = true;
    public CameraSettings h = new CameraSettings();
    public Runnable i = new c();
    public Runnable j = new d();
    public Runnable k = new e();
    public Runnable l = new f();

    public class a implements Runnable {
        public final /* synthetic */ boolean a;

        public a(boolean z) {
            this.a = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            CameraInstance.this.c.setTorch(this.a);
        }
    }

    public class b implements Runnable {
        public final /* synthetic */ PreviewCallback a;

        public b(PreviewCallback previewCallback) {
            this.a = previewCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            CameraInstance.this.c.requestPreviewFrame(this.a);
        }
    }

    public class c implements Runnable {
        public c() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                Log.d("CameraInstance", "Opening camera");
                CameraInstance.this.c.open();
            } catch (Exception e) {
                CameraInstance.a(CameraInstance.this, e);
                Log.e("CameraInstance", "Failed to open camera", e);
            }
        }
    }

    public class d implements Runnable {
        public d() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                Log.d("CameraInstance", "Configuring camera");
                CameraInstance.this.c.configure();
                if (CameraInstance.this.d != null) {
                    CameraInstance.this.d.obtainMessage(R.id.zxing_prewiew_size_ready, CameraInstance.this.c.getPreviewSize()).sendToTarget();
                }
            } catch (Exception e) {
                CameraInstance.a(CameraInstance.this, e);
                Log.e("CameraInstance", "Failed to configure camera", e);
            }
        }
    }

    public class e implements Runnable {
        public e() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                Log.d("CameraInstance", "Starting preview");
                CameraInstance.this.c.setPreviewDisplay(CameraInstance.this.b);
                CameraInstance.this.c.startPreview();
            } catch (Exception e) {
                CameraInstance.a(CameraInstance.this, e);
                Log.e("CameraInstance", "Failed to start preview", e);
            }
        }
    }

    public class f implements Runnable {
        public f() {
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                Log.d("CameraInstance", "Closing camera");
                CameraInstance.this.c.stopPreview();
                CameraInstance.this.c.close();
            } catch (Exception e) {
                Log.e("CameraInstance", "Failed to close camera", e);
            }
            CameraInstance cameraInstance = CameraInstance.this;
            cameraInstance.g = true;
            cameraInstance.d.sendEmptyMessage(R.id.zxing_camera_closed);
            CameraInstance.this.a.b();
        }
    }

    public CameraInstance(Context context) {
        Util.validateMainThread();
        if (uc.e == null) {
            uc.e = new uc();
        }
        this.a = uc.e;
        CameraManager cameraManager = new CameraManager(context);
        this.c = cameraManager;
        cameraManager.setCameraSettings(this.h);
    }

    public final void a() {
        if (!this.f) {
            throw new IllegalStateException("CameraInstance is not open");
        }
    }

    public void close() {
        Util.validateMainThread();
        if (this.f) {
            this.a.a(this.l);
        } else {
            this.g = true;
        }
        this.f = false;
    }

    public void configureCamera() {
        Util.validateMainThread();
        a();
        this.a.a(this.j);
    }

    public CameraManager getCameraManager() {
        return this.c;
    }

    public int getCameraRotation() {
        return this.c.getCameraRotation();
    }

    public CameraSettings getCameraSettings() {
        return this.h;
    }

    public uc getCameraThread() {
        return this.a;
    }

    public DisplayConfiguration getDisplayConfiguration() {
        return this.e;
    }

    public CameraSurface getSurface() {
        return this.b;
    }

    public boolean isCameraClosed() {
        return this.g;
    }

    public boolean isOpen() {
        return this.f;
    }

    public void open() {
        Util.validateMainThread();
        this.f = true;
        this.g = false;
        this.a.b(this.i);
    }

    public void requestPreview(PreviewCallback previewCallback) {
        a();
        this.a.a(new b(previewCallback));
    }

    public void setCameraSettings(CameraSettings cameraSettings) {
        if (this.f) {
            return;
        }
        this.h = cameraSettings;
        this.c.setCameraSettings(cameraSettings);
    }

    public void setDisplayConfiguration(DisplayConfiguration displayConfiguration) {
        this.e = displayConfiguration;
        this.c.setDisplayConfiguration(displayConfiguration);
    }

    public void setReadyHandler(Handler handler) {
        this.d = handler;
    }

    public void setSurface(CameraSurface cameraSurface) {
        this.b = cameraSurface;
    }

    public void setSurfaceHolder(SurfaceHolder surfaceHolder) {
        setSurface(new CameraSurface(surfaceHolder));
    }

    public void setTorch(boolean z) {
        Util.validateMainThread();
        if (this.f) {
            this.a.a(new a(z));
        }
    }

    public void startPreview() {
        Util.validateMainThread();
        a();
        this.a.a(this.k);
    }

    public static /* synthetic */ void a(CameraInstance cameraInstance, Exception exc) {
        Handler handler = cameraInstance.d;
        if (handler != null) {
            handler.obtainMessage(R.id.zxing_camera_error, exc).sendToTarget();
        }
    }

    public CameraInstance(CameraManager cameraManager) {
        Util.validateMainThread();
        this.c = cameraManager;
    }
}
