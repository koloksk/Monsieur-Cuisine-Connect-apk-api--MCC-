package com.journeyapps.barcodescanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.ViewGroup;
import android.view.WindowManager;
import com.google.zxing.client.android.R;
import com.journeyapps.barcodescanner.camera.CameraInstance;
import com.journeyapps.barcodescanner.camera.CameraSettings;
import com.journeyapps.barcodescanner.camera.CameraSurface;
import com.journeyapps.barcodescanner.camera.CenterCropStrategy;
import com.journeyapps.barcodescanner.camera.DisplayConfiguration;
import com.journeyapps.barcodescanner.camera.FitCenterStrategy;
import com.journeyapps.barcodescanner.camera.FitXYStrategy;
import com.journeyapps.barcodescanner.camera.PreviewScalingStrategy;
import defpackage.tc;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class CameraPreview extends ViewGroup {
    public static final String A = CameraPreview.class.getSimpleName();
    public CameraInstance a;
    public WindowManager b;
    public Handler c;
    public boolean d;
    public SurfaceView e;
    public TextureView f;
    public boolean g;
    public RotationListener h;
    public int i;
    public List<StateListener> j;
    public DisplayConfiguration k;
    public CameraSettings l;
    public Size m;
    public Size n;
    public Rect o;
    public Size p;
    public Rect q;
    public Rect r;
    public Size s;
    public double t;
    public PreviewScalingStrategy u;
    public boolean v;
    public final SurfaceHolder.Callback w;
    public final Handler.Callback x;
    public RotationCallback y;
    public final StateListener z;

    public interface StateListener {
        void cameraClosed();

        void cameraError(Exception exc);

        void previewSized();

        void previewStarted();

        void previewStopped();
    }

    public class a implements SurfaceHolder.Callback {
        public a() {
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
            if (surfaceHolder == null) {
                Log.e(CameraPreview.A, "*** WARNING *** surfaceChanged() gave us a null surface!");
                return;
            }
            CameraPreview.this.p = new Size(i2, i3);
            CameraPreview.this.a();
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
        }

        @Override // android.view.SurfaceHolder.Callback
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            CameraPreview.this.p = null;
        }
    }

    public class b implements Handler.Callback {
        public b() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            DisplayConfiguration displayConfiguration;
            int i = message.what;
            if (i != R.id.zxing_prewiew_size_ready) {
                if (i == R.id.zxing_camera_error) {
                    Exception exc = (Exception) message.obj;
                    if (CameraPreview.this.isActive()) {
                        CameraPreview.this.pause();
                        CameraPreview.this.z.cameraError(exc);
                    }
                } else if (i == R.id.zxing_camera_closed) {
                    CameraPreview.this.z.cameraClosed();
                }
                return false;
            }
            CameraPreview cameraPreview = CameraPreview.this;
            Size size = (Size) message.obj;
            cameraPreview.n = size;
            Size size2 = cameraPreview.m;
            if (size2 == null) {
                return true;
            }
            if (size == null || (displayConfiguration = cameraPreview.k) == null) {
                cameraPreview.r = null;
                cameraPreview.q = null;
                cameraPreview.o = null;
                throw new IllegalStateException("containerSize or previewSize is not set yet");
            }
            int i2 = size.width;
            int i3 = size.height;
            int i4 = size2.width;
            int i5 = size2.height;
            cameraPreview.o = displayConfiguration.scalePreview(size);
            cameraPreview.q = cameraPreview.calculateFramingRect(new Rect(0, 0, i4, i5), cameraPreview.o);
            Rect rect = new Rect(cameraPreview.q);
            Rect rect2 = cameraPreview.o;
            rect.offset(-rect2.left, -rect2.top);
            Rect rect3 = new Rect((rect.left * i2) / cameraPreview.o.width(), (rect.top * i3) / cameraPreview.o.height(), (rect.right * i2) / cameraPreview.o.width(), (rect.bottom * i3) / cameraPreview.o.height());
            cameraPreview.r = rect3;
            if (rect3.width() <= 0 || cameraPreview.r.height() <= 0) {
                cameraPreview.r = null;
                cameraPreview.q = null;
                Log.w(CameraPreview.A, "Preview frame is too small");
            } else {
                cameraPreview.z.previewSized();
            }
            cameraPreview.requestLayout();
            cameraPreview.a();
            return true;
        }
    }

    public class c implements RotationCallback {

        public class a implements Runnable {
            public a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                CameraPreview.a(CameraPreview.this);
            }
        }

        public c() {
        }

        @Override // com.journeyapps.barcodescanner.RotationCallback
        public void onRotationChanged(int i) {
            CameraPreview.this.c.postDelayed(new a(), 250L);
        }
    }

    public class d implements StateListener {
        public d() {
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void cameraClosed() {
            Iterator<StateListener> it = CameraPreview.this.j.iterator();
            while (it.hasNext()) {
                it.next().cameraClosed();
            }
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void cameraError(Exception exc) {
            Iterator<StateListener> it = CameraPreview.this.j.iterator();
            while (it.hasNext()) {
                it.next().cameraError(exc);
            }
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewSized() {
            Iterator<StateListener> it = CameraPreview.this.j.iterator();
            while (it.hasNext()) {
                it.next().previewSized();
            }
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewStarted() {
            Iterator<StateListener> it = CameraPreview.this.j.iterator();
            while (it.hasNext()) {
                it.next().previewStarted();
            }
        }

        @Override // com.journeyapps.barcodescanner.CameraPreview.StateListener
        public void previewStopped() {
            Iterator<StateListener> it = CameraPreview.this.j.iterator();
            while (it.hasNext()) {
                it.next().previewStopped();
            }
        }
    }

    public CameraPreview(Context context) {
        super(context);
        this.d = false;
        this.g = false;
        this.i = -1;
        this.j = new ArrayList();
        this.l = new CameraSettings();
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = 0.1d;
        this.u = null;
        this.v = false;
        this.w = new a();
        this.x = new b();
        this.y = new c();
        this.z = new d();
        a(context, null);
    }

    private int getDisplayRotation() {
        return this.b.getDefaultDisplay().getRotation();
    }

    public final void a(Context context, AttributeSet attributeSet) {
        if (getBackground() == null) {
            setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        }
        initializeAttributes(attributeSet);
        this.b = (WindowManager) context.getSystemService("window");
        this.c = new Handler(this.x);
        this.h = new RotationListener();
    }

    public void addStateListener(StateListener stateListener) {
        this.j.add(stateListener);
    }

    public Rect calculateFramingRect(Rect rect, Rect rect2) {
        Rect rect3 = new Rect(rect);
        rect3.intersect(rect2);
        if (this.s != null) {
            rect3.inset(Math.max(0, (rect3.width() - this.s.width) / 2), Math.max(0, (rect3.height() - this.s.height) / 2));
            return rect3;
        }
        int iMin = (int) Math.min(rect3.width() * this.t, rect3.height() * this.t);
        rect3.inset(iMin, iMin);
        if (rect3.height() > rect3.width()) {
            rect3.inset(0, (rect3.height() - rect3.width()) / 2);
        }
        return rect3;
    }

    public Matrix calculateTextureTransform(Size size, Size size2) {
        float f;
        float f2 = size.width / size.height;
        float f3 = size2.width / size2.height;
        float f4 = 1.0f;
        if (f2 < f3) {
            float f5 = f3 / f2;
            f = 1.0f;
            f4 = f5;
        } else {
            f = f2 / f3;
        }
        Matrix matrix = new Matrix();
        matrix.setScale(f4, f);
        int i = size.width;
        int i2 = size.height;
        matrix.postTranslate((i - (i * f4)) / 2.0f, (i2 - (i2 * f)) / 2.0f);
        return matrix;
    }

    public CameraInstance createCameraInstance() {
        CameraInstance cameraInstance = new CameraInstance(getContext());
        cameraInstance.setCameraSettings(this.l);
        return cameraInstance;
    }

    public CameraInstance getCameraInstance() {
        return this.a;
    }

    public CameraSettings getCameraSettings() {
        return this.l;
    }

    public Rect getFramingRect() {
        return this.q;
    }

    public Size getFramingRectSize() {
        return this.s;
    }

    public double getMarginFraction() {
        return this.t;
    }

    public Rect getPreviewFramingRect() {
        return this.r;
    }

    public PreviewScalingStrategy getPreviewScalingStrategy() {
        PreviewScalingStrategy previewScalingStrategy = this.u;
        return previewScalingStrategy != null ? previewScalingStrategy : this.f != null ? new CenterCropStrategy() : new FitCenterStrategy();
    }

    public void initializeAttributes(AttributeSet attributeSet) {
        TypedArray typedArrayObtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R.styleable.zxing_camera_preview);
        int dimension = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.zxing_camera_preview_zxing_framing_rect_width, -1.0f);
        int dimension2 = (int) typedArrayObtainStyledAttributes.getDimension(R.styleable.zxing_camera_preview_zxing_framing_rect_height, -1.0f);
        if (dimension > 0 && dimension2 > 0) {
            this.s = new Size(dimension, dimension2);
        }
        this.d = typedArrayObtainStyledAttributes.getBoolean(R.styleable.zxing_camera_preview_zxing_use_texture_view, true);
        int integer = typedArrayObtainStyledAttributes.getInteger(R.styleable.zxing_camera_preview_zxing_preview_scaling_strategy, -1);
        if (integer == 1) {
            this.u = new CenterCropStrategy();
        } else if (integer == 2) {
            this.u = new FitCenterStrategy();
        } else if (integer == 3) {
            this.u = new FitXYStrategy();
        }
        typedArrayObtainStyledAttributes.recycle();
    }

    public boolean isActive() {
        return this.a != null;
    }

    public boolean isCameraClosed() {
        CameraInstance cameraInstance = this.a;
        return cameraInstance == null || cameraInstance.isCameraClosed();
    }

    public boolean isPreviewActive() {
        return this.g;
    }

    public boolean isUseTextureView() {
        return this.d;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.d) {
            TextureView textureView = new TextureView(getContext());
            this.f = textureView;
            textureView.setSurfaceTextureListener(new tc(this));
            addView(this.f);
            return;
        }
        SurfaceView surfaceView = new SurfaceView(getContext());
        this.e = surfaceView;
        surfaceView.getHolder().addCallback(this.w);
        addView(this.e);
    }

    @Override // android.view.ViewGroup, android.view.View
    @SuppressLint({"DrawAllocation"})
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        Size size = new Size(i3 - i, i4 - i2);
        this.m = size;
        CameraInstance cameraInstance = this.a;
        if (cameraInstance != null && cameraInstance.getDisplayConfiguration() == null) {
            DisplayConfiguration displayConfiguration = new DisplayConfiguration(getDisplayRotation(), size);
            this.k = displayConfiguration;
            displayConfiguration.setPreviewScalingStrategy(getPreviewScalingStrategy());
            this.a.setDisplayConfiguration(this.k);
            this.a.configureCamera();
            boolean z2 = this.v;
            if (z2) {
                this.a.setTorch(z2);
            }
        }
        SurfaceView surfaceView = this.e;
        if (surfaceView == null) {
            TextureView textureView = this.f;
            if (textureView != null) {
                textureView.layout(0, 0, getWidth(), getHeight());
                return;
            }
            return;
        }
        Rect rect = this.o;
        if (rect == null) {
            surfaceView.layout(0, 0, getWidth(), getHeight());
        } else {
            surfaceView.layout(rect.left, rect.top, rect.right, rect.bottom);
        }
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof Bundle)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        Bundle bundle = (Bundle) parcelable;
        super.onRestoreInstanceState(bundle.getParcelable("super"));
        setTorch(bundle.getBoolean("torch"));
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState();
        Bundle bundle = new Bundle();
        bundle.putParcelable("super", parcelableOnSaveInstanceState);
        bundle.putBoolean("torch", this.v);
        return bundle;
    }

    public void pause() {
        TextureView textureView;
        SurfaceView surfaceView;
        Util.validateMainThread();
        Log.d(A, "pause()");
        this.i = -1;
        CameraInstance cameraInstance = this.a;
        if (cameraInstance != null) {
            cameraInstance.close();
            this.a = null;
            this.g = false;
        } else {
            this.c.sendEmptyMessage(R.id.zxing_camera_closed);
        }
        if (this.p == null && (surfaceView = this.e) != null) {
            surfaceView.getHolder().removeCallback(this.w);
        }
        if (this.p == null && (textureView = this.f) != null) {
            textureView.setSurfaceTextureListener(null);
        }
        this.m = null;
        this.n = null;
        this.r = null;
        this.h.stop();
        this.z.previewStopped();
    }

    public void pauseAndWait() throws InterruptedException {
        CameraInstance cameraInstance = getCameraInstance();
        pause();
        long jNanoTime = System.nanoTime();
        while (cameraInstance != null && !cameraInstance.isCameraClosed() && System.nanoTime() - jNanoTime <= 2000000000) {
            try {
                Thread.sleep(1L);
            } catch (InterruptedException unused) {
                return;
            }
        }
    }

    public void previewStarted() {
    }

    public void resume() {
        Util.validateMainThread();
        Log.d(A, "resume()");
        if (this.a != null) {
            Log.w(A, "initCamera called twice");
        } else {
            CameraInstance cameraInstanceCreateCameraInstance = createCameraInstance();
            this.a = cameraInstanceCreateCameraInstance;
            cameraInstanceCreateCameraInstance.setReadyHandler(this.c);
            this.a.open();
            this.i = getDisplayRotation();
        }
        if (this.p != null) {
            a();
        } else {
            SurfaceView surfaceView = this.e;
            if (surfaceView != null) {
                surfaceView.getHolder().addCallback(this.w);
            } else {
                TextureView textureView = this.f;
                if (textureView != null) {
                    if (textureView.isAvailable()) {
                        new tc(this).onSurfaceTextureAvailable(this.f.getSurfaceTexture(), this.f.getWidth(), this.f.getHeight());
                    } else {
                        this.f.setSurfaceTextureListener(new tc(this));
                    }
                }
            }
        }
        requestLayout();
        this.h.listen(getContext(), this.y);
    }

    public void setCameraSettings(CameraSettings cameraSettings) {
        this.l = cameraSettings;
    }

    public void setFramingRectSize(Size size) {
        this.s = size;
    }

    public void setMarginFraction(double d2) {
        if (d2 >= 0.5d) {
            throw new IllegalArgumentException("The margin fraction must be less than 0.5");
        }
        this.t = d2;
    }

    public void setPreviewScalingStrategy(PreviewScalingStrategy previewScalingStrategy) {
        this.u = previewScalingStrategy;
    }

    public void setTorch(boolean z) {
        this.v = z;
        CameraInstance cameraInstance = this.a;
        if (cameraInstance != null) {
            cameraInstance.setTorch(z);
        }
    }

    public void setUseTextureView(boolean z) {
        this.d = z;
    }

    public static /* synthetic */ void a(CameraPreview cameraPreview) {
        if (!cameraPreview.isActive() || cameraPreview.getDisplayRotation() == cameraPreview.i) {
            return;
        }
        cameraPreview.pause();
        cameraPreview.resume();
    }

    public final void a() {
        Rect rect;
        Size size = this.p;
        if (size == null || this.n == null || (rect = this.o) == null) {
            return;
        }
        if (this.e != null && size.equals(new Size(rect.width(), this.o.height()))) {
            a(new CameraSurface(this.e.getHolder()));
            return;
        }
        TextureView textureView = this.f;
        if (textureView == null || textureView.getSurfaceTexture() == null) {
            return;
        }
        if (this.n != null) {
            this.f.setTransform(calculateTextureTransform(new Size(this.f.getWidth(), this.f.getHeight()), this.n));
        }
        a(new CameraSurface(this.f.getSurfaceTexture()));
    }

    public CameraPreview(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.d = false;
        this.g = false;
        this.i = -1;
        this.j = new ArrayList();
        this.l = new CameraSettings();
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = 0.1d;
        this.u = null;
        this.v = false;
        this.w = new a();
        this.x = new b();
        this.y = new c();
        this.z = new d();
        a(context, attributeSet);
    }

    public final void a(CameraSurface cameraSurface) {
        if (this.g || this.a == null) {
            return;
        }
        Log.i(A, "Starting preview");
        this.a.setSurface(cameraSurface);
        this.a.startPreview();
        this.g = true;
        previewStarted();
        this.z.previewStarted();
    }

    public CameraPreview(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.d = false;
        this.g = false;
        this.i = -1;
        this.j = new ArrayList();
        this.l = new CameraSettings();
        this.q = null;
        this.r = null;
        this.s = null;
        this.t = 0.1d;
        this.u = null;
        this.v = false;
        this.w = new a();
        this.x = new b();
        this.y = new c();
        this.z = new d();
        a(context, attributeSet);
    }
}
