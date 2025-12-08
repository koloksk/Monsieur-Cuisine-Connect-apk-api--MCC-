package android.support.transition;

import android.animation.Animator;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import defpackage.c2;
import defpackage.m3;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class Slide extends Visibility {
    public static final TimeInterpolator M = new DecelerateInterpolator();
    public static final TimeInterpolator N = new AccelerateInterpolator();
    public static final g O = new a();
    public static final g P = new b();
    public static final g Q = new c();
    public static final g R = new d();
    public static final g S = new e();
    public static final g T = new f();
    public g K;
    public int L;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface GravityFlag {
    }

    public static class a extends h {
        public a() {
            super(null);
        }

        @Override // android.support.transition.Slide.g
        public float b(ViewGroup viewGroup, View view2) {
            return view2.getTranslationX() - viewGroup.getWidth();
        }
    }

    public static class b extends h {
        public b() {
            super(null);
        }

        @Override // android.support.transition.Slide.g
        public float b(ViewGroup viewGroup, View view2) {
            return ViewCompat.getLayoutDirection(viewGroup) == 1 ? view2.getTranslationX() + viewGroup.getWidth() : view2.getTranslationX() - viewGroup.getWidth();
        }
    }

    public static class c extends i {
        public c() {
            super(null);
        }

        @Override // android.support.transition.Slide.g
        public float a(ViewGroup viewGroup, View view2) {
            return view2.getTranslationY() - viewGroup.getHeight();
        }
    }

    public static class d extends h {
        public d() {
            super(null);
        }

        @Override // android.support.transition.Slide.g
        public float b(ViewGroup viewGroup, View view2) {
            return view2.getTranslationX() + viewGroup.getWidth();
        }
    }

    public static class e extends h {
        public e() {
            super(null);
        }

        @Override // android.support.transition.Slide.g
        public float b(ViewGroup viewGroup, View view2) {
            return ViewCompat.getLayoutDirection(viewGroup) == 1 ? view2.getTranslationX() - viewGroup.getWidth() : view2.getTranslationX() + viewGroup.getWidth();
        }
    }

    public static class f extends i {
        public f() {
            super(null);
        }

        @Override // android.support.transition.Slide.g
        public float a(ViewGroup viewGroup, View view2) {
            return view2.getTranslationY() + viewGroup.getHeight();
        }
    }

    public interface g {
        float a(ViewGroup viewGroup, View view2);

        float b(ViewGroup viewGroup, View view2);
    }

    public static abstract class h implements g {
        public /* synthetic */ h(a aVar) {
        }

        @Override // android.support.transition.Slide.g
        public float a(ViewGroup viewGroup, View view2) {
            return view2.getTranslationY();
        }
    }

    public static abstract class i implements g {
        public /* synthetic */ i(a aVar) {
        }

        @Override // android.support.transition.Slide.g
        public float b(ViewGroup viewGroup, View view2) {
            return view2.getTranslationX();
        }
    }

    public Slide() {
        this.K = T;
        this.L = 80;
        setSlideEdge(80);
    }

    @Override // android.support.transition.Visibility, android.support.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        super.captureEndValues(transitionValues);
        int[] iArr = new int[2];
        transitionValues.f0view.getLocationOnScreen(iArr);
        transitionValues.values.put("android:slide:screenPosition", iArr);
    }

    @Override // android.support.transition.Visibility, android.support.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        super.captureStartValues(transitionValues);
        int[] iArr = new int[2];
        transitionValues.f0view.getLocationOnScreen(iArr);
        transitionValues.values.put("android:slide:screenPosition", iArr);
    }

    public int getSlideEdge() {
        return this.L;
    }

    @Override // android.support.transition.Visibility
    public Animator onAppear(ViewGroup viewGroup, View view2, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues2 == null) {
            return null;
        }
        int[] iArr = (int[]) transitionValues2.values.get("android:slide:screenPosition");
        float translationX = view2.getTranslationX();
        float translationY = view2.getTranslationY();
        return c2.a(view2, transitionValues2, iArr[0], iArr[1], this.K.b(viewGroup, view2), this.K.a(viewGroup, view2), translationX, translationY, M);
    }

    @Override // android.support.transition.Visibility
    public Animator onDisappear(ViewGroup viewGroup, View view2, TransitionValues transitionValues, TransitionValues transitionValues2) {
        if (transitionValues == null) {
            return null;
        }
        int[] iArr = (int[]) transitionValues.values.get("android:slide:screenPosition");
        return c2.a(view2, transitionValues, iArr[0], iArr[1], view2.getTranslationX(), view2.getTranslationY(), this.K.b(viewGroup, view2), this.K.a(viewGroup, view2), N);
    }

    public void setSlideEdge(int i2) {
        if (i2 == 3) {
            this.K = O;
        } else if (i2 == 5) {
            this.K = R;
        } else if (i2 == 48) {
            this.K = Q;
        } else if (i2 == 80) {
            this.K = T;
        } else if (i2 == 8388611) {
            this.K = P;
        } else {
            if (i2 != 8388613) {
                throw new IllegalArgumentException("Invalid slide direction");
            }
            this.K = S;
        }
        this.L = i2;
        SidePropagation sidePropagation = new SidePropagation();
        sidePropagation.setSide(i2);
        setPropagation(sidePropagation);
    }

    public Slide(int i2) {
        this.K = T;
        this.L = 80;
        setSlideEdge(i2);
    }

    public Slide(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.K = T;
        this.L = 80;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, m3.h);
        int namedInt = TypedArrayUtils.getNamedInt(typedArrayObtainStyledAttributes, (XmlPullParser) attributeSet, "slideEdge", 0, 80);
        typedArrayObtainStyledAttributes.recycle();
        setSlideEdge(namedInt);
    }
}
