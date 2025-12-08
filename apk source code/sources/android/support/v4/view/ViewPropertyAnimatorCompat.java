package android.support.v4.view;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.animation.Interpolator;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public final class ViewPropertyAnimatorCompat {
    public WeakReference<View> a;
    public Runnable b = null;
    public Runnable c = null;
    public int d = -1;

    public class a extends AnimatorListenerAdapter {
        public final /* synthetic */ ViewPropertyAnimatorListener a;
        public final /* synthetic */ View b;

        public a(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, ViewPropertyAnimatorListener viewPropertyAnimatorListener, View view2) {
            this.a = viewPropertyAnimatorListener;
            this.b = view2;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            this.a.onAnimationCancel(this.b);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            this.a.onAnimationEnd(this.b);
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
            this.a.onAnimationStart(this.b);
        }
    }

    public class b implements ValueAnimator.AnimatorUpdateListener {
        public final /* synthetic */ ViewPropertyAnimatorUpdateListener a;
        public final /* synthetic */ View b;

        public b(ViewPropertyAnimatorCompat viewPropertyAnimatorCompat, ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener, View view2) {
            this.a = viewPropertyAnimatorUpdateListener;
            this.b = view2;
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            this.a.onAnimationUpdate(this.b);
        }
    }

    public ViewPropertyAnimatorCompat(View view2) {
        this.a = new WeakReference<>(view2);
    }

    public final void a(View view2, ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        if (viewPropertyAnimatorListener != null) {
            view2.animate().setListener(new a(this, viewPropertyAnimatorListener, view2));
        } else {
            view2.animate().setListener(null);
        }
    }

    public ViewPropertyAnimatorCompat alpha(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().alpha(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat alphaBy(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().alphaBy(f);
        }
        return this;
    }

    public void cancel() {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().cancel();
        }
    }

    public long getDuration() {
        View view2 = this.a.get();
        if (view2 != null) {
            return view2.animate().getDuration();
        }
        return 0L;
    }

    public Interpolator getInterpolator() {
        View view2 = this.a.get();
        if (view2 != null) {
            return (Interpolator) view2.animate().getInterpolator();
        }
        return null;
    }

    public long getStartDelay() {
        View view2 = this.a.get();
        if (view2 != null) {
            return view2.animate().getStartDelay();
        }
        return 0L;
    }

    public ViewPropertyAnimatorCompat rotation(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().rotation(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationBy(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().rotationBy(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationX(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().rotationX(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationXBy(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().rotationXBy(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationY(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().rotationY(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat rotationYBy(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().rotationYBy(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleX(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().scaleX(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleXBy(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().scaleXBy(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleY(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().scaleY(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat scaleYBy(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().scaleYBy(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setDuration(long j) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().setDuration(j);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setInterpolator(Interpolator interpolator) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().setInterpolator(interpolator);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setListener(ViewPropertyAnimatorListener viewPropertyAnimatorListener) {
        View view2 = this.a.get();
        if (view2 != null) {
            a(view2, viewPropertyAnimatorListener);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setStartDelay(long j) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().setStartDelay(j);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat setUpdateListener(ViewPropertyAnimatorUpdateListener viewPropertyAnimatorUpdateListener) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().setUpdateListener(viewPropertyAnimatorUpdateListener != null ? new b(this, viewPropertyAnimatorUpdateListener, view2) : null);
        }
        return this;
    }

    public void start() {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().start();
        }
    }

    public ViewPropertyAnimatorCompat translationX(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().translationX(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationXBy(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().translationXBy(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationY(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().translationY(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationYBy(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().translationYBy(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationZ(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().translationZ(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat translationZBy(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().translationZBy(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat withEndAction(Runnable runnable) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().withEndAction(runnable);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat withLayer() {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().withLayer();
        }
        return this;
    }

    public ViewPropertyAnimatorCompat withStartAction(Runnable runnable) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().withStartAction(runnable);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat x(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().x(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat xBy(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().xBy(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat y(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().y(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat yBy(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().yBy(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat z(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().z(f);
        }
        return this;
    }

    public ViewPropertyAnimatorCompat zBy(float f) {
        View view2 = this.a.get();
        if (view2 != null) {
            view2.animate().zBy(f);
        }
        return this;
    }
}
