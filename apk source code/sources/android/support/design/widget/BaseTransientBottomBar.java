package android.support.design.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.design.R;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityManager;
import android.widget.FrameLayout;
import defpackage.l2;
import defpackage.p2;
import defpackage.q1;
import defpackage.s1;
import defpackage.t1;
import defpackage.u1;
import defpackage.v1;
import defpackage.w1;
import defpackage.x1;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BaseTransientBottomBar<B extends BaseTransientBottomBar<B>> {
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;
    public static final Handler i = new Handler(Looper.getMainLooper(), new a());
    public final ViewGroup a;
    public final Context b;
    public final i c;
    public final ContentViewCallback d;
    public int e;
    public List<BaseCallback<B>> f;
    public final AccessibilityManager g;
    public final l2.b h = new c();

    public static abstract class BaseCallback<B> {
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_TIMEOUT = 2;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public @interface DismissEvent {
        }

        public void onDismissed(B b, int i) {
        }

        public void onShown(B b) {
        }
    }

    public interface ContentViewCallback {
        void animateContentIn(int i, int i2);

        void animateContentOut(int i, int i2);
    }

    @IntRange(from = 1)
    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface Duration {
    }

    public static class a implements Handler.Callback {
        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            int i = message.what;
            if (i != 0) {
                if (i != 1) {
                    return false;
                }
                BaseTransientBottomBar baseTransientBottomBar = (BaseTransientBottomBar) message.obj;
                int i2 = message.arg1;
                if (baseTransientBottomBar.c() && baseTransientBottomBar.c.getVisibility() == 0) {
                    ValueAnimator valueAnimator = new ValueAnimator();
                    valueAnimator.setIntValues(0, baseTransientBottomBar.c.getHeight());
                    valueAnimator.setInterpolator(q1.b);
                    valueAnimator.setDuration(250L);
                    valueAnimator.addListener(new s1(baseTransientBottomBar, i2));
                    valueAnimator.addUpdateListener(new t1(baseTransientBottomBar));
                    valueAnimator.start();
                } else {
                    baseTransientBottomBar.b(i2);
                }
                return true;
            }
            BaseTransientBottomBar baseTransientBottomBar2 = (BaseTransientBottomBar) message.obj;
            if (baseTransientBottomBar2.c.getParent() == null) {
                ViewGroup.LayoutParams layoutParams = baseTransientBottomBar2.c.getLayoutParams();
                if (layoutParams instanceof CoordinatorLayout.LayoutParams) {
                    CoordinatorLayout.LayoutParams layoutParams2 = (CoordinatorLayout.LayoutParams) layoutParams;
                    f fVar = new f();
                    fVar.setStartAlphaSwipeDistance(0.1f);
                    fVar.setEndAlphaSwipeDistance(0.6f);
                    fVar.setSwipeDirection(0);
                    fVar.setListener(new u1(baseTransientBottomBar2));
                    layoutParams2.setBehavior(fVar);
                    layoutParams2.insetEdge = 80;
                }
                baseTransientBottomBar2.a.addView(baseTransientBottomBar2.c);
            }
            baseTransientBottomBar2.c.setOnAttachStateChangeListener(new w1(baseTransientBottomBar2));
            if (!ViewCompat.isLaidOut(baseTransientBottomBar2.c)) {
                baseTransientBottomBar2.c.setOnLayoutChangeListener(new x1(baseTransientBottomBar2));
            } else if (baseTransientBottomBar2.c()) {
                baseTransientBottomBar2.a();
            } else {
                baseTransientBottomBar2.b();
            }
            return true;
        }
    }

    public class b implements OnApplyWindowInsetsListener {
        public b(BaseTransientBottomBar baseTransientBottomBar) {
        }

        @Override // android.support.v4.view.OnApplyWindowInsetsListener
        public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
            view2.setPadding(view2.getPaddingLeft(), view2.getPaddingTop(), view2.getPaddingRight(), windowInsetsCompat.getSystemWindowInsetBottom());
            return windowInsetsCompat;
        }
    }

    public class c implements l2.b {
        public c() {
        }

        @Override // l2.b
        public void a(int i) {
            Handler handler = BaseTransientBottomBar.i;
            handler.sendMessage(handler.obtainMessage(1, i, 0, BaseTransientBottomBar.this));
        }

        @Override // l2.b
        public void show() {
            Handler handler = BaseTransientBottomBar.i;
            handler.sendMessage(handler.obtainMessage(0, BaseTransientBottomBar.this));
        }
    }

    public class d extends AnimatorListenerAdapter {
        public d() {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            BaseTransientBottomBar.this.b();
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            BaseTransientBottomBar.this.d.animateContentIn(70, 180);
        }
    }

    public class e implements ValueAnimator.AnimatorUpdateListener {
        public int a;
        public final /* synthetic */ int b;

        public e(int i) {
            this.b = i;
            this.a = this.b;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            int iIntValue = ((Integer) valueAnimator.getAnimatedValue()).intValue();
            BaseTransientBottomBar.d();
            BaseTransientBottomBar.this.c.setTranslationY(iIntValue);
            this.a = iIntValue;
        }
    }

    public final class f extends SwipeDismissBehavior<i> {
        public f() {
        }

        @Override // android.support.design.widget.SwipeDismissBehavior
        public boolean canSwipeDismissView(View view2) {
            return view2 instanceof i;
        }

        @Override // android.support.design.widget.SwipeDismissBehavior, android.support.design.widget.CoordinatorLayout.Behavior
        public boolean onInterceptTouchEvent(CoordinatorLayout coordinatorLayout, View view2, MotionEvent motionEvent) {
            i iVar = (i) view2;
            int actionMasked = motionEvent.getActionMasked();
            if (actionMasked != 0) {
                if (actionMasked == 1 || actionMasked == 3) {
                    l2.b().h(BaseTransientBottomBar.this.h);
                }
            } else if (coordinatorLayout.isPointInChildBounds(iVar, (int) motionEvent.getX(), (int) motionEvent.getY())) {
                l2.b().g(BaseTransientBottomBar.this.h);
            }
            return super.onInterceptTouchEvent(coordinatorLayout, iVar, motionEvent);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface g {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public interface h {
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class i extends FrameLayout {
        public h a;
        public g b;

        public i(Context context) {
            this(context, null);
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onAttachedToWindow() {
            super.onAttachedToWindow();
            g gVar = this.b;
            if (gVar != null && ((w1) gVar) == null) {
                throw null;
            }
            ViewCompat.requestApplyInsets(this);
        }

        @Override // android.view.ViewGroup, android.view.View
        public void onDetachedFromWindow() {
            super.onDetachedFromWindow();
            g gVar = this.b;
            if (gVar != null) {
                w1 w1Var = (w1) gVar;
                if (w1Var.a.isShownOrQueued()) {
                    BaseTransientBottomBar.i.post(new v1(w1Var));
                }
            }
        }

        @Override // android.widget.FrameLayout, android.view.ViewGroup, android.view.View
        public void onLayout(boolean z, int i, int i2, int i3, int i4) {
            super.onLayout(z, i, i2, i3, i4);
            h hVar = this.a;
            if (hVar != null) {
                x1 x1Var = (x1) hVar;
                x1Var.a.c.setOnLayoutChangeListener(null);
                if (x1Var.a.c()) {
                    x1Var.a.a();
                } else {
                    x1Var.a.b();
                }
            }
        }

        public void setOnAttachStateChangeListener(g gVar) {
            this.b = gVar;
        }

        public void setOnLayoutChangeListener(h hVar) {
            this.a = hVar;
        }

        public i(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.SnackbarLayout);
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.SnackbarLayout_elevation)) {
                ViewCompat.setElevation(this, typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.SnackbarLayout_elevation, 0));
            }
            typedArrayObtainStyledAttributes.recycle();
            setClickable(true);
        }
    }

    public BaseTransientBottomBar(@NonNull ViewGroup viewGroup, @NonNull View view2, @NonNull ContentViewCallback contentViewCallback) {
        if (viewGroup == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null parent");
        }
        if (view2 == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null content");
        }
        if (contentViewCallback == null) {
            throw new IllegalArgumentException("Transient bottom bar must have non-null callback");
        }
        this.a = viewGroup;
        this.d = contentViewCallback;
        Context context = viewGroup.getContext();
        this.b = context;
        p2.a(context);
        i iVar = (i) LayoutInflater.from(this.b).inflate(R.layout.design_layout_snackbar, this.a, false);
        this.c = iVar;
        iVar.addView(view2);
        ViewCompat.setAccessibilityLiveRegion(this.c, 1);
        ViewCompat.setImportantForAccessibility(this.c, 1);
        ViewCompat.setFitsSystemWindows(this.c, true);
        ViewCompat.setOnApplyWindowInsetsListener(this.c, new b(this));
        this.g = (AccessibilityManager) this.b.getSystemService("accessibility");
    }

    public static /* synthetic */ boolean d() {
        return false;
    }

    public void a(int i2) {
        l2.b().a(this.h, i2);
    }

    @NonNull
    public B addCallback(@NonNull BaseCallback<B> baseCallback) {
        if (baseCallback == null) {
            return this;
        }
        if (this.f == null) {
            this.f = new ArrayList();
        }
        this.f.add(baseCallback);
        return this;
    }

    public void b() {
        l2.b().f(this.h);
        List<BaseCallback<B>> list = this.f;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.f.get(size).onShown(this);
            }
        }
    }

    public boolean c() {
        return !this.g.isEnabled();
    }

    public void dismiss() {
        a(3);
    }

    @NonNull
    public Context getContext() {
        return this.b;
    }

    public int getDuration() {
        return this.e;
    }

    @NonNull
    public View getView() {
        return this.c;
    }

    public boolean isShown() {
        return l2.b().a(this.h);
    }

    public boolean isShownOrQueued() {
        return l2.b().b(this.h);
    }

    @NonNull
    public B removeCallback(@NonNull BaseCallback<B> baseCallback) {
        List<BaseCallback<B>> list;
        if (baseCallback == null || (list = this.f) == null) {
            return this;
        }
        list.remove(baseCallback);
        return this;
    }

    @NonNull
    public B setDuration(int i2) {
        this.e = i2;
        return this;
    }

    public void show() {
        l2.b().a(this.e, this.h);
    }

    public void a() {
        int height = this.c.getHeight();
        this.c.setTranslationY(height);
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(height, 0);
        valueAnimator.setInterpolator(q1.b);
        valueAnimator.setDuration(250L);
        valueAnimator.addListener(new d());
        valueAnimator.addUpdateListener(new e(height));
        valueAnimator.start();
    }

    public void b(int i2) {
        l2.b().e(this.h);
        List<BaseCallback<B>> list = this.f;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.f.get(size).onDismissed(this, i2);
            }
        }
        ViewParent parent = this.c.getParent();
        if (parent instanceof ViewGroup) {
            ((ViewGroup) parent).removeView(this.c);
        }
    }
}
