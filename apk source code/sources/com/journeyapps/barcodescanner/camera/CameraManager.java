package com.journeyapps.barcodescanner.camera;

import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.Log;
import android.view.SurfaceHolder;
import com.google.zxing.client.android.AmbientLightManager;
import com.google.zxing.client.android.camera.CameraConfigurationUtils;
import com.google.zxing.client.android.camera.open.OpenCameraInterface;
import com.journeyapps.barcodescanner.Size;
import com.journeyapps.barcodescanner.SourceData;
import defpackage.g9;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public final class CameraManager {
    public Camera a;
    public Camera.CameraInfo b;
    public AutoFocusManager c;
    public AmbientLightManager d;
    public boolean e;
    public String f;
    public DisplayConfiguration h;
    public Size i;
    public Size j;
    public Context l;
    public CameraSettings g = new CameraSettings();
    public int k = -1;
    public final a m = new a();

    public final class a implements Camera.PreviewCallback {
        public PreviewCallback a;
        public Size b;

        public a() {
        }

        @Override // android.hardware.Camera.PreviewCallback
        public void onPreviewFrame(byte[] bArr, Camera camera) {
            Size size = this.b;
            PreviewCallback previewCallback = this.a;
            if (size == null || previewCallback == null) {
                Log.d("CameraManager", "Got preview callback, but no handler or resolution available");
                if (previewCallback != null) {
                    previewCallback.onPreviewError(new Exception("No resolution available"));
                    return;
                }
                return;
            }
            try {
                if (bArr == null) {
                    throw new NullPointerException("No preview data received");
                }
                previewCallback.onPreview(new SourceData(bArr, size.width, size.height, camera.getParameters().getPreviewFormat(), CameraManager.this.getCameraRotation()));
            } catch (RuntimeException e) {
                Log.e("CameraManager", "Camera preview failed", e);
                previewCallback.onPreviewError(e);
            }
        }
    }

    public CameraManager(Context context) {
        this.l = context;
    }

    public final void a(boolean z) {
        Camera.Parameters parameters = this.a.getParameters();
        String str = this.f;
        if (str == null) {
            this.f = parameters.flatten();
        } else {
            parameters.unflatten(str);
        }
        if (parameters == null) {
            Log.w("CameraManager", "Device error: no camera parameters are available. Proceeding without configuration.");
            return;
        }
        StringBuilder sbA = g9.a("Initial camera parameters: ");
        sbA.append(parameters.flatten());
        Log.i("CameraManager", sbA.toString());
        if (z) {
            Log.w("CameraManager", "In camera config safe mode -- most settings will not be honored");
        }
        CameraConfigurationUtils.setFocus(parameters, this.g.getFocusMode(), z);
        if (!z) {
            CameraConfigurationUtils.setTorch(parameters, false);
            if (this.g.isScanInverted()) {
                CameraConfigurationUtils.setInvertColor(parameters);
            }
            if (this.g.isBarcodeSceneModeEnabled()) {
                CameraConfigurationUtils.setBarcodeSceneMode(parameters);
            }
            if (this.g.isMeteringEnabled()) {
                CameraConfigurationUtils.setVideoStabilization(parameters);
                CameraConfigurationUtils.setFocusArea(parameters);
                CameraConfigurationUtils.setMetering(parameters);
            }
        }
        List<Camera.Size> supportedPreviewSizes = parameters.getSupportedPreviewSizes();
        ArrayList arrayList = new ArrayList();
        if (supportedPreviewSizes == null) {
            Camera.Size previewSize = parameters.getPreviewSize();
            if (previewSize != null) {
                arrayList.add(new Size(previewSize.width, previewSize.height));
            }
        } else {
            for (Camera.Size size : supportedPreviewSizes) {
                arrayList.add(new Size(size.width, size.height));
            }
        }
        if (arrayList.size() == 0) {
            this.i = null;
        } else {
            Size bestPreviewSize = this.h.getBestPreviewSize(arrayList, isCameraRotated());
            this.i = bestPreviewSize;
            parameters.setPreviewSize(bestPreviewSize.width, bestPreviewSize.height);
        }
        if (Build.DEVICE.equals("glass-1")) {
            CameraConfigurationUtils.setBestPreviewFPS(parameters);
        }
        StringBuilder sbA2 = g9.a("Final camera parameters: ");
        sbA2.append(parameters.flatten());
        Log.i("CameraManager", sbA2.toString());
        this.a.setParameters(parameters);
    }

    public void close() {
        Camera camera = this.a;
        if (camera != null) {
            camera.release();
            this.a = null;
        }
    }

    public void configure() {
        if (this.a == null) {
            throw new RuntimeException("Camera not open");
        }
        try {
            int iA = a();
            this.k = iA;
            this.a.setDisplayOrientation(iA);
        } catch (Exception unused) {
            Log.w("CameraManager", "Failed to set rotation.");
        }
        try {
            a(false);
        } catch (Exception unused2) {
            try {
                a(true);
            } catch (Exception unused3) {
                Log.w("CameraManager", "Camera rejected even safe-mode parameters! No configuration");
            }
        }
        Camera.Size previewSize = this.a.getParameters().getPreviewSize();
        if (previewSize == null) {
            this.j = this.i;
        } else {
            this.j = new Size(previewSize.width, previewSize.height);
        }
        this.m.b = this.j;
    }

    public Camera getCamera() {
        return this.a;
    }

    public int getCameraRotation() {
        return this.k;
    }

    public CameraSettings getCameraSettings() {
        return this.g;
    }

    public DisplayConfiguration getDisplayConfiguration() {
        return this.h;
    }

    public Size getNaturalPreviewSize() {
        return this.j;
    }

    public Size getPreviewSize() {
        if (this.j == null) {
            return null;
        }
        return isCameraRotated() ? this.j.rotate() : this.j;
    }

    public boolean isCameraRotated() {
        int i = this.k;
        if (i != -1) {
            return i % 180 != 0;
        }
        throw new IllegalStateException("Rotation not calculated yet. Call configure() first.");
    }

    public boolean isOpen() {
        return this.a != null;
    }

    public boolean isTorchOn() {
        String flashMode;
        Camera.Parameters parameters = this.a.getParameters();
        if (parameters == null || (flashMode = parameters.getFlashMode()) == null) {
            return false;
        }
        return "on".equals(flashMode) || "torch".equals(flashMode);
    }

    public void open() {
        Camera cameraOpen = OpenCameraInterface.open(this.g.getRequestedCameraId());
        this.a = cameraOpen;
        if (cameraOpen == null) {
            throw new RuntimeException("Failed to open camera");
        }
        int cameraId = OpenCameraInterface.getCameraId(this.g.getRequestedCameraId());
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        this.b = cameraInfo;
        Camera.getCameraInfo(cameraId, cameraInfo);
    }

    public void requestPreviewFrame(PreviewCallback previewCallback) {
        Camera camera = this.a;
        if (camera == null || !this.e) {
            return;
        }
        a aVar = this.m;
        aVar.a = previewCallback;
        camera.setOneShotPreviewCallback(aVar);
    }

    public void setCameraSettings(CameraSettings cameraSettings) {
        this.g = cameraSettings;
    }

    public void setDisplayConfiguration(DisplayConfiguration displayConfiguration) {
        this.h = displayConfiguration;
    }

    public void setPreviewDisplay(SurfaceHolder surfaceHolder) throws IOException {
        setPreviewDisplay(new CameraSurface(surfaceHolder));
    }

    public void setTorch(boolean z) {
        if (this.a != null) {
            try {
                if (z != isTorchOn()) {
                    if (this.c != null) {
                        this.c.stop();
                    }
                    Camera.Parameters parameters = this.a.getParameters();
                    CameraConfigurationUtils.setTorch(parameters, z);
                    if (this.g.isExposureEnabled()) {
                        CameraConfigurationUtils.setBestExposure(parameters, z);
                    }
                    this.a.setParameters(parameters);
                    if (this.c != null) {
                        this.c.start();
                    }
                }
            } catch (RuntimeException e) {
                Log.e("CameraManager", "Failed to set torch", e);
            }
        }
    }

    public void startPreview() {
        Camera camera = this.a;
        if (camera == null || this.e) {
            return;
        }
        camera.startPreview();
        this.e = true;
        this.c = new AutoFocusManager(this.a, this.g);
        AmbientLightManager ambientLightManager = new AmbientLightManager(this.l, this, this.g);
        this.d = ambientLightManager;
        ambientLightManager.start();
    }

    public void stopPreview() {
        AutoFocusManager autoFocusManager = this.c;
        if (autoFocusManager != null) {
            autoFocusManager.stop();
            this.c = null;
        }
        AmbientLightManager ambientLightManager = this.d;
        if (ambientLightManager != null) {
            ambientLightManager.stop();
            this.d = null;
        }
        Camera camera = this.a;
        if (camera == null || !this.e) {
            return;
        }
        camera.stopPreview();
        this.m.a = null;
        this.e = false;
    }

    public void setPreviewDisplay(CameraSurface cameraSurface) throws IOException {
        cameraSurface.setPreview(this.a);
    }

    public final int a() {
        int i;
        int rotation = this.h.getRotation();
        int i2 = 0;
        if (rotation != 0) {
            if (rotation == 1) {
                i2 = 90;
            } else if (rotation == 2) {
                i2 = 180;
            } else if (rotation == 3) {
                i2 = 270;
            }
        }
        Camera.CameraInfo cameraInfo = this.b;
        if (cameraInfo.facing == 1) {
            i = (360 - ((cameraInfo.orientation + i2) % 360)) % 360;
        } else {
            i = ((cameraInfo.orientation - i2) + 360) % 360;
        }
        Log.i("CameraManager", "Camera Display Orientation: " + i);
        return i;
    }
}
