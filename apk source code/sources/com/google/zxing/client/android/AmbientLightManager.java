package com.google.zxing.client.android;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import com.journeyapps.barcodescanner.camera.CameraManager;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import defpackage.ha;

/* loaded from: classes.dex */
public final class AmbientLightManager implements SensorEventListener {
    public CameraManager a;
    public CameraSettings b;
    public Sensor c;
    public Context d;
    public Handler e = new Handler();

    public AmbientLightManager(Context context, CameraManager cameraManager, CameraSettings cameraSettings) {
        this.d = context;
        this.a = cameraManager;
        this.b = cameraSettings;
    }

    @Override // android.hardware.SensorEventListener
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override // android.hardware.SensorEventListener
    public void onSensorChanged(SensorEvent sensorEvent) {
        float f = sensorEvent.values[0];
        if (this.a != null) {
            if (f <= 45.0f) {
                this.e.post(new ha(this, true));
            } else if (f >= 450.0f) {
                this.e.post(new ha(this, false));
            }
        }
    }

    public void start() {
        if (this.b.isAutoTorchEnabled()) {
            SensorManager sensorManager = (SensorManager) this.d.getSystemService("sensor");
            Sensor defaultSensor = sensorManager.getDefaultSensor(5);
            this.c = defaultSensor;
            if (defaultSensor != null) {
                sensorManager.registerListener(this, defaultSensor, 3);
            }
        }
    }

    public void stop() {
        if (this.c != null) {
            ((SensorManager) this.d.getSystemService("sensor")).unregisterListener(this);
            this.c = null;
        }
    }
}
