package android.support.v4.view;

import android.content.Context;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;

/* loaded from: classes.dex */
public final class GestureDetectorCompat {
    public final a a;

    public interface a {
        void a(GestureDetector.OnDoubleTapListener onDoubleTapListener);

        void a(boolean z);

        boolean a();

        boolean a(MotionEvent motionEvent);
    }

    public static class b implements a {
        public final GestureDetector a;

        public b(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
            this.a = new GestureDetector(context, onGestureListener, handler);
        }

        @Override // android.support.v4.view.GestureDetectorCompat.a
        public boolean a() {
            return this.a.isLongpressEnabled();
        }

        @Override // android.support.v4.view.GestureDetectorCompat.a
        public boolean a(MotionEvent motionEvent) {
            return this.a.onTouchEvent(motionEvent);
        }

        @Override // android.support.v4.view.GestureDetectorCompat.a
        public void a(boolean z) {
            this.a.setIsLongpressEnabled(z);
        }

        @Override // android.support.v4.view.GestureDetectorCompat.a
        public void a(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
            this.a.setOnDoubleTapListener(onDoubleTapListener);
        }
    }

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener) {
        this(context, onGestureListener, null);
    }

    public boolean isLongpressEnabled() {
        return this.a.a();
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.a.a(motionEvent);
    }

    public void setIsLongpressEnabled(boolean z) {
        this.a.a(z);
    }

    public void setOnDoubleTapListener(GestureDetector.OnDoubleTapListener onDoubleTapListener) {
        this.a.a(onDoubleTapListener);
    }

    public GestureDetectorCompat(Context context, GestureDetector.OnGestureListener onGestureListener, Handler handler) {
        this.a = new b(context, onGestureListener, handler);
    }
}
