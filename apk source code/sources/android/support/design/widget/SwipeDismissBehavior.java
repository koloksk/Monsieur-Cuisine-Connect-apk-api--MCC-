package android.support.design.widget;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewParent;

/* loaded from: classes.dex */
public class SwipeDismissBehavior<V extends View> extends CoordinatorLayout.Behavior<V> {
    public static final int STATE_DRAGGING = 1;
    public static final int STATE_IDLE = 0;
    public static final int STATE_SETTLING = 2;
    public static final int SWIPE_DIRECTION_ANY = 2;
    public static final int SWIPE_DIRECTION_END_TO_START = 1;
    public static final int SWIPE_DIRECTION_START_TO_END = 0;
    public ViewDragHelper a;
    public OnDismissListener b;
    public boolean c;
    public boolean e;
    public float d = 0.0f;
    public int f = 2;
    public float g = 0.5f;
    public float h = 0.0f;
    public float i = 0.5f;
    public final ViewDragHelper.Callback j = new a();

    public interface OnDismissListener {
        void onDismiss(View view2);

        void onDragStateChanged(int i);
    }

    public class a extends ViewDragHelper.Callback {
        public int a;
        public int b = -1;

        public a() {
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public int clampViewPositionHorizontal(View view2, int i, int i2) {
            int width;
            int width2;
            int width3;
            boolean z = ViewCompat.getLayoutDirection(view2) == 1;
            int i3 = SwipeDismissBehavior.this.f;
            if (i3 == 0) {
                if (z) {
                    width = this.a - view2.getWidth();
                    width2 = this.a;
                } else {
                    width = this.a;
                    width3 = view2.getWidth();
                    width2 = width3 + width;
                }
            } else if (i3 != 1) {
                width = this.a - view2.getWidth();
                width2 = view2.getWidth() + this.a;
            } else if (z) {
                width = this.a;
                width3 = view2.getWidth();
                width2 = width3 + width;
            } else {
                width = this.a - view2.getWidth();
                width2 = this.a;
            }
            return Math.min(Math.max(width, i), width2);
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public int clampViewPositionVertical(View view2, int i, int i2) {
            return view2.getTop();
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public int getViewHorizontalDragRange(View view2) {
            return view2.getWidth();
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onViewCaptured(View view2, int i) {
            this.b = i;
            this.a = view2.getLeft();
            ViewParent parent = view2.getParent();
            if (parent != null) {
                parent.requestDisallowInterceptTouchEvent(true);
            }
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onViewDragStateChanged(int i) {
            OnDismissListener onDismissListener = SwipeDismissBehavior.this.b;
            if (onDismissListener != null) {
                onDismissListener.onDragStateChanged(i);
            }
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public void onViewPositionChanged(View view2, int i, int i2, int i3, int i4) {
            float width = (view2.getWidth() * SwipeDismissBehavior.this.h) + this.a;
            float width2 = (view2.getWidth() * SwipeDismissBehavior.this.i) + this.a;
            float f = i;
            if (f <= width) {
                view2.setAlpha(1.0f);
            } else if (f >= width2) {
                view2.setAlpha(0.0f);
            } else {
                view2.setAlpha(SwipeDismissBehavior.a(0.0f, 1.0f - ((f - width) / (width2 - width)), 1.0f));
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x002a  */
        /* JADX WARN: Removed duplicated region for block: B:18:0x002c  */
        @Override // android.support.v4.widget.ViewDragHelper.Callback
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void onViewReleased(android.view.View r8, float r9, float r10) {
            /*
                r7 = this;
                r10 = -1
                r7.b = r10
                int r10 = r8.getWidth()
                r0 = 0
                int r1 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
                r2 = 1
                r3 = 0
                if (r1 == 0) goto L3a
                int r4 = android.support.v4.view.ViewCompat.getLayoutDirection(r8)
                if (r4 != r2) goto L16
                r4 = r2
                goto L17
            L16:
                r4 = r3
            L17:
                android.support.design.widget.SwipeDismissBehavior r5 = android.support.design.widget.SwipeDismissBehavior.this
                int r5 = r5.f
                r6 = 2
                if (r5 != r6) goto L1f
                goto L2a
            L1f:
                if (r5 != 0) goto L2e
                if (r4 == 0) goto L28
                int r9 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
                if (r9 >= 0) goto L2c
                goto L2a
            L28:
                if (r1 <= 0) goto L2c
            L2a:
                r9 = r2
                goto L56
            L2c:
                r9 = r3
                goto L56
            L2e:
                if (r5 != r2) goto L2c
                if (r4 == 0) goto L35
                if (r1 <= 0) goto L2c
                goto L39
            L35:
                int r9 = (r9 > r0 ? 1 : (r9 == r0 ? 0 : -1))
                if (r9 >= 0) goto L2c
            L39:
                goto L2a
            L3a:
                int r9 = r8.getLeft()
                int r0 = r7.a
                int r9 = r9 - r0
                int r0 = r8.getWidth()
                float r0 = (float) r0
                android.support.design.widget.SwipeDismissBehavior r1 = android.support.design.widget.SwipeDismissBehavior.this
                float r1 = r1.g
                float r0 = r0 * r1
                int r0 = java.lang.Math.round(r0)
                int r9 = java.lang.Math.abs(r9)
                if (r9 < r0) goto L2c
                goto L2a
            L56:
                if (r9 == 0) goto L64
                int r9 = r8.getLeft()
                int r0 = r7.a
                if (r9 >= r0) goto L62
                int r0 = r0 - r10
                goto L67
            L62:
                int r0 = r0 + r10
                goto L67
            L64:
                int r0 = r7.a
                r2 = r3
            L67:
                android.support.design.widget.SwipeDismissBehavior r9 = android.support.design.widget.SwipeDismissBehavior.this
                android.support.v4.widget.ViewDragHelper r9 = r9.a
                int r10 = r8.getTop()
                boolean r9 = r9.settleCapturedViewAt(r0, r10)
                if (r9 == 0) goto L80
                android.support.design.widget.SwipeDismissBehavior$b r9 = new android.support.design.widget.SwipeDismissBehavior$b
                android.support.design.widget.SwipeDismissBehavior r10 = android.support.design.widget.SwipeDismissBehavior.this
                r9.<init>(r8, r2)
                android.support.v4.view.ViewCompat.postOnAnimation(r8, r9)
                goto L8b
            L80:
                if (r2 == 0) goto L8b
                android.support.design.widget.SwipeDismissBehavior r9 = android.support.design.widget.SwipeDismissBehavior.this
                android.support.design.widget.SwipeDismissBehavior$OnDismissListener r9 = r9.b
                if (r9 == 0) goto L8b
                r9.onDismiss(r8)
            L8b:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.SwipeDismissBehavior.a.onViewReleased(android.view.View, float, float):void");
        }

        @Override // android.support.v4.widget.ViewDragHelper.Callback
        public boolean tryCaptureView(View view2, int i) {
            return this.b == -1 && SwipeDismissBehavior.this.canSwipeDismissView(view2);
        }
    }

    public class b implements Runnable {
        public final View a;
        public final boolean b;

        public b(View view2, boolean z) {
            this.a = view2;
            this.b = z;
        }

        @Override // java.lang.Runnable
        public void run() {
            OnDismissListener onDismissListener;
            ViewDragHelper viewDragHelper = SwipeDismissBehavior.this.a;
            if (viewDragHelper != null && viewDragHelper.continueSettling(true)) {
                ViewCompat.postOnAnimation(this.a, this);
            } else {
                if (!this.b || (onDismissListener = SwipeDismissBehavior.this.b) == null) {
                    return;
                }
                onDismissListener.onDismiss(this.a);
            }
        }
    }

    public static float a(float f, float f2, float f3) {
        return Math.min(Math.max(f, f2), f3);
    }

    public boolean canSwipeDismissView(@NonNull View view2) {
        return true;
    }

    public int getDragState() {
        ViewDragHelper viewDragHelper = this.a;
        if (viewDragHelper != null) {
            return viewDragHelper.getViewDragState();
        }
        return 0;
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        boolean zIsPointInChildBounds = this.c;
        int actionMasked = motionEvent.getActionMasked();
        if (actionMasked == 0) {
            zIsPointInChildBounds = coordinatorLayout.isPointInChildBounds(v, (int) motionEvent.getX(), (int) motionEvent.getY());
            this.c = zIsPointInChildBounds;
        } else if (actionMasked == 1 || actionMasked == 3) {
            this.c = false;
        }
        if (!zIsPointInChildBounds) {
            return false;
        }
        if (this.a == null) {
            this.a = this.e ? ViewDragHelper.create(coordinatorLayout, this.d, this.j) : ViewDragHelper.create(coordinatorLayout, this.j);
        }
        return this.a.shouldInterceptTouchEvent(motionEvent);
    }

    @Override // android.support.design.widget.CoordinatorLayout.Behavior
    public boolean onTouchEvent(CoordinatorLayout coordinatorLayout, V v, MotionEvent motionEvent) {
        ViewDragHelper viewDragHelper = this.a;
        if (viewDragHelper == null) {
            return false;
        }
        viewDragHelper.processTouchEvent(motionEvent);
        return true;
    }

    public void setDragDismissDistance(float f) {
        this.g = a(0.0f, f, 1.0f);
    }

    public void setEndAlphaSwipeDistance(float f) {
        this.i = a(0.0f, f, 1.0f);
    }

    public void setListener(OnDismissListener onDismissListener) {
        this.b = onDismissListener;
    }

    public void setSensitivity(float f) {
        this.d = f;
        this.e = true;
    }

    public void setStartAlphaSwipeDistance(float f) {
        this.h = a(0.0f, f, 1.0f);
    }

    public void setSwipeDirection(int i) {
        this.f = i;
    }
}
