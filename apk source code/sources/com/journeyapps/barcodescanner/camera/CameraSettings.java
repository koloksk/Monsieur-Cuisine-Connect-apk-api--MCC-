package com.journeyapps.barcodescanner.camera;

/* loaded from: classes.dex */
public class CameraSettings {
    public int a = -1;
    public boolean b = false;
    public boolean c = false;
    public boolean d = false;
    public boolean e = true;
    public boolean f = false;
    public boolean g = false;
    public boolean h = false;
    public FocusMode i = FocusMode.AUTO;

    public enum FocusMode {
        AUTO,
        CONTINUOUS,
        INFINITY,
        MACRO
    }

    public FocusMode getFocusMode() {
        return this.i;
    }

    public int getRequestedCameraId() {
        return this.a;
    }

    public boolean isAutoFocusEnabled() {
        return this.e;
    }

    public boolean isAutoTorchEnabled() {
        return this.h;
    }

    public boolean isBarcodeSceneModeEnabled() {
        return this.c;
    }

    public boolean isContinuousFocusEnabled() {
        return this.f;
    }

    public boolean isExposureEnabled() {
        return this.g;
    }

    public boolean isMeteringEnabled() {
        return this.d;
    }

    public boolean isScanInverted() {
        return this.b;
    }

    public void setAutoFocusEnabled(boolean z) {
        this.e = z;
        if (z && this.f) {
            this.i = FocusMode.CONTINUOUS;
        } else if (z) {
            this.i = FocusMode.AUTO;
        } else {
            this.i = null;
        }
    }

    public void setAutoTorchEnabled(boolean z) {
        this.h = z;
    }

    public void setBarcodeSceneModeEnabled(boolean z) {
        this.c = z;
    }

    public void setContinuousFocusEnabled(boolean z) {
        this.f = z;
        if (z) {
            this.i = FocusMode.CONTINUOUS;
        } else if (this.e) {
            this.i = FocusMode.AUTO;
        } else {
            this.i = null;
        }
    }

    public void setExposureEnabled(boolean z) {
        this.g = z;
    }

    public void setFocusMode(FocusMode focusMode) {
        this.i = focusMode;
    }

    public void setMeteringEnabled(boolean z) {
        this.d = z;
    }

    public void setRequestedCameraId(int i) {
        this.a = i;
    }

    public void setScanInverted(boolean z) {
        this.b = z;
    }
}
