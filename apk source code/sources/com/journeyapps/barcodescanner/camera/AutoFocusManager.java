package com.journeyapps.barcodescanner.camera;

import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;
import java.util.Collection;

/* loaded from: classes.dex */
public final class AutoFocusManager {
    public static final Collection<String> i;
    public boolean a;
    public boolean b;
    public final boolean c;
    public final Camera d;
    public int f = 1;
    public final Handler.Callback g = new a();
    public final Camera.AutoFocusCallback h = new b();
    public Handler e = new Handler(this.g);

    public class a implements Handler.Callback {
        public a() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            AutoFocusManager autoFocusManager = AutoFocusManager.this;
            if (i != autoFocusManager.f) {
                return false;
            }
            autoFocusManager.b();
            return true;
        }
    }

    public class b implements Camera.AutoFocusCallback {

        public class a implements Runnable {
            public a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                AutoFocusManager autoFocusManager = AutoFocusManager.this;
                autoFocusManager.b = false;
                autoFocusManager.a();
            }
        }

        public b() {
        }

        @Override // android.hardware.Camera.AutoFocusCallback
        public void onAutoFocus(boolean z, Camera camera) {
            AutoFocusManager.this.e.post(new a());
        }
    }

    static {
        ArrayList arrayList = new ArrayList(2);
        i = arrayList;
        arrayList.add("auto");
        i.add("macro");
    }

    public AutoFocusManager(Camera camera, CameraSettings cameraSettings) {
        this.d = camera;
        String focusMode = camera.getParameters().getFocusMode();
        this.c = cameraSettings.isAutoFocusEnabled() && i.contains(focusMode);
        Log.i("AutoFocusManager", "Current focus mode '" + focusMode + "'; use auto focus? " + this.c);
        start();
    }

    public final synchronized void a() {
        if (!this.a && !this.e.hasMessages(this.f)) {
            this.e.sendMessageDelayed(this.e.obtainMessage(this.f), 2000L);
        }
    }

    public final void b() {
        if (!this.c || this.a || this.b) {
            return;
        }
        try {
            this.d.autoFocus(this.h);
            this.b = true;
        } catch (RuntimeException e) {
            Log.w("AutoFocusManager", "Unexpected exception while focusing", e);
            a();
        }
    }

    public void start() {
        this.a = false;
        b();
    }

    public void stop() {
        this.a = true;
        this.b = false;
        this.e.removeMessages(this.f);
        if (this.c) {
            try {
                this.d.cancelAutoFocus();
            } catch (RuntimeException e) {
                Log.w("AutoFocusManager", "Unexpected exception while cancelling focusing", e);
            }
        }
    }
}
