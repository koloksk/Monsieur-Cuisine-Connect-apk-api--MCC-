package com.journeyapps.barcodescanner;

import android.content.Context;
import android.view.OrientationEventListener;
import android.view.WindowManager;

/* loaded from: classes.dex */
public class RotationListener {
    public int a;
    public WindowManager b;
    public OrientationEventListener c;
    public RotationCallback d;

    public class a extends OrientationEventListener {
        public a(Context context, int i) {
            super(context, i);
        }

        @Override // android.view.OrientationEventListener
        public void onOrientationChanged(int i) {
            RotationListener rotationListener = RotationListener.this;
            WindowManager windowManager = rotationListener.b;
            RotationCallback rotationCallback = rotationListener.d;
            if (windowManager == null || rotationCallback == null) {
                return;
            }
            int rotation = windowManager.getDefaultDisplay().getRotation();
            RotationListener rotationListener2 = RotationListener.this;
            if (rotation != rotationListener2.a) {
                rotationListener2.a = rotation;
                rotationCallback.onRotationChanged(rotation);
            }
        }
    }

    public void listen(Context context, RotationCallback rotationCallback) {
        stop();
        Context applicationContext = context.getApplicationContext();
        this.d = rotationCallback;
        this.b = (WindowManager) applicationContext.getSystemService("window");
        a aVar = new a(applicationContext, 3);
        this.c = aVar;
        aVar.enable();
        this.a = this.b.getDefaultDisplay().getRotation();
    }

    public void stop() {
        OrientationEventListener orientationEventListener = this.c;
        if (orientationEventListener != null) {
            orientationEventListener.disable();
        }
        this.c = null;
        this.b = null;
        this.d = null;
    }
}
