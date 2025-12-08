package defpackage;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.RestrictTo;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityManager;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class x8 implements View.OnLongClickListener, View.OnHoverListener, View.OnAttachStateChangeListener {
    public static x8 i;
    public static x8 j;
    public final View a;
    public final CharSequence b;
    public final Runnable c = new a();
    public final Runnable d = new b();
    public int e;
    public int f;
    public y8 g;
    public boolean h;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() throws Resources.NotFoundException {
            x8.this.a(false);
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            x8.this.a();
        }
    }

    public x8(View view2, CharSequence charSequence) {
        this.a = view2;
        this.b = charSequence;
        view2.setOnLongClickListener(this);
        this.a.setOnHoverListener(this);
    }

    public final void a(boolean z) throws Resources.NotFoundException {
        int height;
        int i2;
        long j2;
        int longPressTimeout;
        long j3;
        if (ViewCompat.isAttachedToWindow(this.a)) {
            a((x8) null);
            x8 x8Var = j;
            if (x8Var != null) {
                x8Var.a();
            }
            j = this;
            this.h = z;
            y8 y8Var = new y8(this.a.getContext());
            this.g = y8Var;
            View view2 = this.a;
            int width = this.e;
            int i3 = this.f;
            boolean z2 = this.h;
            CharSequence charSequence = this.b;
            if (y8Var.b.getParent() != null) {
                y8Var.a();
            }
            y8Var.c.setText(charSequence);
            WindowManager.LayoutParams layoutParams = y8Var.d;
            layoutParams.token = view2.getApplicationWindowToken();
            int dimensionPixelOffset = y8Var.a.getResources().getDimensionPixelOffset(R.dimen.tooltip_precise_anchor_threshold);
            if (view2.getWidth() < dimensionPixelOffset) {
                width = view2.getWidth() / 2;
            }
            if (view2.getHeight() >= dimensionPixelOffset) {
                int dimensionPixelOffset2 = y8Var.a.getResources().getDimensionPixelOffset(R.dimen.tooltip_precise_anchor_extra_offset);
                height = i3 + dimensionPixelOffset2;
                i2 = i3 - dimensionPixelOffset2;
            } else {
                height = view2.getHeight();
                i2 = 0;
            }
            layoutParams.gravity = 49;
            int dimensionPixelOffset3 = y8Var.a.getResources().getDimensionPixelOffset(z2 ? R.dimen.tooltip_y_offset_touch : R.dimen.tooltip_y_offset_non_touch);
            View rootView = view2.getRootView();
            ViewGroup.LayoutParams layoutParams2 = rootView.getLayoutParams();
            if (!(layoutParams2 instanceof WindowManager.LayoutParams) || ((WindowManager.LayoutParams) layoutParams2).type != 2) {
                Context context = view2.getContext();
                while (true) {
                    if (!(context instanceof ContextWrapper)) {
                        break;
                    }
                    if (context instanceof Activity) {
                        rootView = ((Activity) context).getWindow().getDecorView();
                        break;
                    }
                    context = ((ContextWrapper) context).getBaseContext();
                }
            }
            if (rootView == null) {
                Log.e("TooltipPopup", "Cannot find app view");
            } else {
                rootView.getWindowVisibleDisplayFrame(y8Var.e);
                Rect rect = y8Var.e;
                if (rect.left < 0 && rect.top < 0) {
                    Resources resources = y8Var.a.getResources();
                    int identifier = resources.getIdentifier("status_bar_height", "dimen", "android");
                    int dimensionPixelSize = identifier != 0 ? resources.getDimensionPixelSize(identifier) : 0;
                    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                    y8Var.e.set(0, dimensionPixelSize, displayMetrics.widthPixels, displayMetrics.heightPixels);
                }
                rootView.getLocationOnScreen(y8Var.g);
                view2.getLocationOnScreen(y8Var.f);
                int[] iArr = y8Var.f;
                int i4 = iArr[0];
                int[] iArr2 = y8Var.g;
                iArr[0] = i4 - iArr2[0];
                iArr[1] = iArr[1] - iArr2[1];
                layoutParams.x = (iArr[0] + width) - (rootView.getWidth() / 2);
                int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
                y8Var.b.measure(iMakeMeasureSpec, iMakeMeasureSpec);
                int measuredHeight = y8Var.b.getMeasuredHeight();
                int[] iArr3 = y8Var.f;
                int i5 = ((iArr3[1] + i2) - dimensionPixelOffset3) - measuredHeight;
                int i6 = iArr3[1] + height + dimensionPixelOffset3;
                if (z2) {
                    if (i5 >= 0) {
                        layoutParams.y = i5;
                    } else {
                        layoutParams.y = i6;
                    }
                } else if (measuredHeight + i6 <= y8Var.e.height()) {
                    layoutParams.y = i6;
                } else {
                    layoutParams.y = i5;
                }
            }
            ((WindowManager) y8Var.a.getSystemService("window")).addView(y8Var.b, y8Var.d);
            this.a.addOnAttachStateChangeListener(this);
            if (this.h) {
                j3 = 2500;
            } else {
                if ((ViewCompat.getWindowSystemUiVisibility(this.a) & 1) == 1) {
                    j2 = 3000;
                    longPressTimeout = ViewConfiguration.getLongPressTimeout();
                } else {
                    j2 = 15000;
                    longPressTimeout = ViewConfiguration.getLongPressTimeout();
                }
                j3 = j2 - longPressTimeout;
            }
            this.a.removeCallbacks(this.d);
            this.a.postDelayed(this.d, j3);
        }
    }

    @Override // android.view.View.OnHoverListener
    public boolean onHover(View view2, MotionEvent motionEvent) {
        if (this.g != null && this.h) {
            return false;
        }
        AccessibilityManager accessibilityManager = (AccessibilityManager) this.a.getContext().getSystemService("accessibility");
        if (accessibilityManager.isEnabled() && accessibilityManager.isTouchExplorationEnabled()) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action != 7) {
            if (action == 10) {
                a();
            }
        } else if (this.a.isEnabled() && this.g == null) {
            this.e = (int) motionEvent.getX();
            this.f = (int) motionEvent.getY();
            a(this);
        }
        return false;
    }

    @Override // android.view.View.OnLongClickListener
    public boolean onLongClick(View view2) throws Resources.NotFoundException {
        this.e = view2.getWidth() / 2;
        this.f = view2.getHeight() / 2;
        a(true);
        return true;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view2) {
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view2) {
        a();
    }

    public final void a() {
        if (j == this) {
            j = null;
            y8 y8Var = this.g;
            if (y8Var != null) {
                y8Var.a();
                this.g = null;
                this.a.removeOnAttachStateChangeListener(this);
            } else {
                Log.e("TooltipCompatHandler", "sActiveHandler.mPopup == null");
            }
        }
        if (i == this) {
            a((x8) null);
        }
        this.a.removeCallbacks(this.d);
    }

    public static void a(x8 x8Var) {
        x8 x8Var2 = i;
        if (x8Var2 != null) {
            x8Var2.a.removeCallbacks(x8Var2.c);
        }
        i = x8Var;
        if (x8Var != null) {
            x8Var.a.postDelayed(x8Var.c, ViewConfiguration.getLongPressTimeout());
        }
    }
}
