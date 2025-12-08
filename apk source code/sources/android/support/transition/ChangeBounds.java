package android.support.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import defpackage.a4;
import defpackage.c2;
import defpackage.f3;
import defpackage.i3;
import defpackage.l3;
import defpackage.m3;
import defpackage.u3;
import defpackage.y3;
import defpackage.z3;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/* loaded from: classes.dex */
public class ChangeBounds extends Transition {
    public static final String[] L = {"android:changeBounds:bounds", "android:changeBounds:clip", "android:changeBounds:parent", "android:changeBounds:windowX", "android:changeBounds:windowY"};
    public static final Property<Drawable, PointF> M = new b(PointF.class, "boundsOrigin");
    public static final Property<k, PointF> N = new c(PointF.class, "topLeft");
    public static final Property<k, PointF> O = new d(PointF.class, "bottomRight");
    public static final Property<View, PointF> P = new e(PointF.class, "bottomRight");
    public static final Property<View, PointF> Q = new f(PointF.class, "topLeft");
    public static final Property<View, PointF> R = new g(PointF.class, "position");
    public static l3 S = new l3();
    public int[] I;
    public boolean J;
    public boolean K;

    public class a extends AnimatorListenerAdapter {
        public final /* synthetic */ ViewGroup a;
        public final /* synthetic */ BitmapDrawable b;
        public final /* synthetic */ View c;
        public final /* synthetic */ float d;

        public a(ChangeBounds changeBounds, ViewGroup viewGroup, BitmapDrawable bitmapDrawable, View view2, float f) {
            this.a = viewGroup;
            this.b = bitmapDrawable;
            this.c = view2;
            this.d = f;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            z3 z3VarA = a4.a(this.a);
            ((y3) z3VarA).a.remove(this.b);
            a4.a(this.c, this.d);
        }
    }

    public static class b extends Property<Drawable, PointF> {
        public Rect a;

        public b(Class cls, String str) {
            super(cls, str);
            this.a = new Rect();
        }

        @Override // android.util.Property
        public PointF get(Drawable drawable) {
            drawable.copyBounds(this.a);
            Rect rect = this.a;
            return new PointF(rect.left, rect.top);
        }

        @Override // android.util.Property
        public void set(Drawable drawable, PointF pointF) {
            Drawable drawable2 = drawable;
            PointF pointF2 = pointF;
            drawable2.copyBounds(this.a);
            this.a.offsetTo(Math.round(pointF2.x), Math.round(pointF2.y));
            drawable2.setBounds(this.a);
        }
    }

    public static class c extends Property<k, PointF> {
        public c(Class cls, String str) {
            super(cls, str);
        }

        @Override // android.util.Property
        public PointF get(k kVar) {
            return null;
        }

        @Override // android.util.Property
        public void set(k kVar, PointF pointF) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            k kVar2 = kVar;
            PointF pointF2 = pointF;
            if (kVar2 == null) {
                throw null;
            }
            kVar2.a = Math.round(pointF2.x);
            int iRound = Math.round(pointF2.y);
            kVar2.b = iRound;
            int i = kVar2.f + 1;
            kVar2.f = i;
            if (i == kVar2.g) {
                a4.a(kVar2.e, kVar2.a, iRound, kVar2.c, kVar2.d);
                kVar2.f = 0;
                kVar2.g = 0;
            }
        }
    }

    public static class d extends Property<k, PointF> {
        public d(Class cls, String str) {
            super(cls, str);
        }

        @Override // android.util.Property
        public PointF get(k kVar) {
            return null;
        }

        @Override // android.util.Property
        public void set(k kVar, PointF pointF) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            k kVar2 = kVar;
            PointF pointF2 = pointF;
            if (kVar2 == null) {
                throw null;
            }
            kVar2.c = Math.round(pointF2.x);
            int iRound = Math.round(pointF2.y);
            kVar2.d = iRound;
            int i = kVar2.g + 1;
            kVar2.g = i;
            if (kVar2.f == i) {
                a4.a(kVar2.e, kVar2.a, kVar2.b, kVar2.c, iRound);
                kVar2.f = 0;
                kVar2.g = 0;
            }
        }
    }

    public static class e extends Property<View, PointF> {
        public e(Class cls, String str) {
            super(cls, str);
        }

        @Override // android.util.Property
        public PointF get(View view2) {
            return null;
        }

        @Override // android.util.Property
        public void set(View view2, PointF pointF) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            View view3 = view2;
            PointF pointF2 = pointF;
            a4.a(view3, view3.getLeft(), view3.getTop(), Math.round(pointF2.x), Math.round(pointF2.y));
        }
    }

    public static class f extends Property<View, PointF> {
        public f(Class cls, String str) {
            super(cls, str);
        }

        @Override // android.util.Property
        public PointF get(View view2) {
            return null;
        }

        @Override // android.util.Property
        public void set(View view2, PointF pointF) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            View view3 = view2;
            PointF pointF2 = pointF;
            a4.a(view3, Math.round(pointF2.x), Math.round(pointF2.y), view3.getRight(), view3.getBottom());
        }
    }

    public static class g extends Property<View, PointF> {
        public g(Class cls, String str) {
            super(cls, str);
        }

        @Override // android.util.Property
        public PointF get(View view2) {
            return null;
        }

        @Override // android.util.Property
        public void set(View view2, PointF pointF) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            View view3 = view2;
            PointF pointF2 = pointF;
            int iRound = Math.round(pointF2.x);
            int iRound2 = Math.round(pointF2.y);
            a4.a(view3, iRound, iRound2, view3.getWidth() + iRound, view3.getHeight() + iRound2);
        }
    }

    public class h extends AnimatorListenerAdapter {
        public final /* synthetic */ k a;
        public k mViewBounds;

        public h(ChangeBounds changeBounds, k kVar) {
            this.a = kVar;
            this.mViewBounds = this.a;
        }
    }

    public class i extends AnimatorListenerAdapter {
        public boolean a;
        public final /* synthetic */ View b;
        public final /* synthetic */ Rect c;
        public final /* synthetic */ int d;
        public final /* synthetic */ int e;
        public final /* synthetic */ int f;
        public final /* synthetic */ int g;

        public i(ChangeBounds changeBounds, View view2, Rect rect, int i, int i2, int i3, int i4) {
            this.b = view2;
            this.c = rect;
            this.d = i;
            this.e = i2;
            this.f = i3;
            this.g = i4;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            this.a = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (this.a) {
                return;
            }
            ViewCompat.setClipBounds(this.b, this.c);
            a4.a(this.b, this.d, this.e, this.f, this.g);
        }
    }

    public class j extends TransitionListenerAdapter {
        public boolean a = false;
        public final /* synthetic */ ViewGroup b;

        public j(ChangeBounds changeBounds, ViewGroup viewGroup) {
            this.b = viewGroup;
        }

        @Override // android.support.transition.TransitionListenerAdapter, android.support.transition.Transition.TransitionListener
        public void onTransitionCancel(@NonNull Transition transition) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            u3.a(this.b, false);
            this.a = true;
        }

        @Override // android.support.transition.TransitionListenerAdapter, android.support.transition.Transition.TransitionListener
        public void onTransitionEnd(@NonNull Transition transition) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (!this.a) {
                u3.a(this.b, false);
            }
            transition.removeListener(this);
        }

        @Override // android.support.transition.TransitionListenerAdapter, android.support.transition.Transition.TransitionListener
        public void onTransitionPause(@NonNull Transition transition) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            u3.a(this.b, false);
        }

        @Override // android.support.transition.TransitionListenerAdapter, android.support.transition.Transition.TransitionListener
        public void onTransitionResume(@NonNull Transition transition) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            u3.a(this.b, true);
        }
    }

    public static class k {
        public int a;
        public int b;
        public int c;
        public int d;
        public View e;
        public int f;
        public int g;

        public k(View view2) {
            this.e = view2;
        }
    }

    public ChangeBounds() {
        this.I = new int[2];
        this.J = false;
        this.K = false;
    }

    public final void b(TransitionValues transitionValues) {
        View view2 = transitionValues.f0view;
        if (!ViewCompat.isLaidOut(view2) && view2.getWidth() == 0 && view2.getHeight() == 0) {
            return;
        }
        transitionValues.values.put("android:changeBounds:bounds", new Rect(view2.getLeft(), view2.getTop(), view2.getRight(), view2.getBottom()));
        transitionValues.values.put("android:changeBounds:parent", transitionValues.f0view.getParent());
        if (this.K) {
            transitionValues.f0view.getLocationInWindow(this.I);
            transitionValues.values.put("android:changeBounds:windowX", Integer.valueOf(this.I[0]));
            transitionValues.values.put("android:changeBounds:windowY", Integer.valueOf(this.I[1]));
        }
        if (this.J) {
            transitionValues.values.put("android:changeBounds:clip", ViewCompat.getClipBounds(view2));
        }
    }

    @Override // android.support.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    @Override // android.support.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) {
        b(transitionValues);
    }

    @Override // android.support.transition.Transition
    @Nullable
    public Animator createAnimator(@NonNull ViewGroup viewGroup, @Nullable TransitionValues transitionValues, @Nullable TransitionValues transitionValues2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        int i2;
        View view2;
        int i3;
        Rect rect;
        ObjectAnimator objectAnimator;
        Animator animatorA;
        TransitionValues transitionValuesB;
        if (transitionValues == null || transitionValues2 == null) {
            return null;
        }
        Map<String, Object> map = transitionValues.values;
        Map<String, Object> map2 = transitionValues2.values;
        ViewGroup viewGroup2 = (ViewGroup) map.get("android:changeBounds:parent");
        ViewGroup viewGroup3 = (ViewGroup) map2.get("android:changeBounds:parent");
        if (viewGroup2 == null || viewGroup3 == null) {
            return null;
        }
        View view3 = transitionValues2.f0view;
        if (!(!this.K || ((transitionValuesB = b(viewGroup2, true)) != null ? viewGroup3 == transitionValuesB.f0view : viewGroup2 == viewGroup3))) {
            int iIntValue = ((Integer) transitionValues.values.get("android:changeBounds:windowX")).intValue();
            int iIntValue2 = ((Integer) transitionValues.values.get("android:changeBounds:windowY")).intValue();
            int iIntValue3 = ((Integer) transitionValues2.values.get("android:changeBounds:windowX")).intValue();
            int iIntValue4 = ((Integer) transitionValues2.values.get("android:changeBounds:windowY")).intValue();
            if (iIntValue == iIntValue3 && iIntValue2 == iIntValue4) {
                return null;
            }
            viewGroup.getLocationInWindow(this.I);
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(view3.getWidth(), view3.getHeight(), Bitmap.Config.ARGB_8888);
            view3.draw(new Canvas(bitmapCreateBitmap));
            BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmapCreateBitmap);
            float fB = a4.b(view3);
            a4.a(view3, 0.0f);
            viewGroup.getOverlay().add(bitmapDrawable);
            PathMotion pathMotion = getPathMotion();
            int[] iArr = this.I;
            ObjectAnimator objectAnimatorOfPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(bitmapDrawable, i3.a(M, pathMotion.getPath(iIntValue - iArr[0], iIntValue2 - iArr[1], iIntValue3 - iArr[0], iIntValue4 - iArr[1])));
            objectAnimatorOfPropertyValuesHolder.addListener(new a(this, viewGroup, bitmapDrawable, view3, fB));
            return objectAnimatorOfPropertyValuesHolder;
        }
        Rect rect2 = (Rect) transitionValues.values.get("android:changeBounds:bounds");
        Rect rect3 = (Rect) transitionValues2.values.get("android:changeBounds:bounds");
        int i4 = rect2.left;
        int i5 = rect3.left;
        int i6 = rect2.top;
        int i7 = rect3.top;
        int i8 = rect2.right;
        int i9 = rect3.right;
        int i10 = rect2.bottom;
        int i11 = rect3.bottom;
        int i12 = i8 - i4;
        int i13 = i10 - i6;
        int i14 = i9 - i5;
        int i15 = i11 - i7;
        Rect rect4 = (Rect) transitionValues.values.get("android:changeBounds:clip");
        Rect rect5 = (Rect) transitionValues2.values.get("android:changeBounds:clip");
        if ((i12 == 0 || i13 == 0) && (i14 == 0 || i15 == 0)) {
            i2 = 0;
        } else {
            i2 = (i4 == i5 && i6 == i7) ? 0 : 1;
            if (i8 != i9 || i10 != i11) {
                i2++;
            }
        }
        if ((rect4 != null && !rect4.equals(rect5)) || (rect4 == null && rect5 != null)) {
            i2++;
        }
        if (i2 <= 0) {
            return null;
        }
        if (this.J) {
            view2 = view3;
            a4.a(view2, i4, i6, Math.max(i12, i14) + i4, Math.max(i13, i15) + i6);
            ObjectAnimator objectAnimatorA = (i4 == i5 && i6 == i7) ? null : f3.a(view2, R, getPathMotion().getPath(i4, i6, i5, i7));
            if (rect4 == null) {
                i3 = 0;
                rect = new Rect(0, 0, i12, i13);
            } else {
                i3 = 0;
                rect = rect4;
            }
            Rect rect6 = rect5 == null ? new Rect(i3, i3, i14, i15) : rect5;
            if (rect.equals(rect6)) {
                objectAnimator = null;
            } else {
                ViewCompat.setClipBounds(view2, rect);
                l3 l3Var = S;
                Object[] objArr = new Object[2];
                objArr[i3] = rect;
                objArr[1] = rect6;
                ObjectAnimator objectAnimatorOfObject = ObjectAnimator.ofObject(view2, "clipBounds", l3Var, objArr);
                objectAnimatorOfObject.addListener(new i(this, view2, rect5, i5, i7, i9, i11));
                objectAnimator = objectAnimatorOfObject;
            }
            animatorA = c2.a(objectAnimatorA, objectAnimator);
        } else {
            view2 = view3;
            a4.a(view2, i4, i6, i8, i10);
            if (i2 != 2) {
                animatorA = (i4 == i5 && i6 == i7) ? f3.a(view2, P, getPathMotion().getPath(i8, i10, i9, i11)) : f3.a(view2, Q, getPathMotion().getPath(i4, i6, i5, i7));
            } else if (i12 == i14 && i13 == i15) {
                animatorA = f3.a(view2, R, getPathMotion().getPath(i4, i6, i5, i7));
            } else {
                k kVar = new k(view2);
                ObjectAnimator objectAnimatorA2 = f3.a(kVar, N, getPathMotion().getPath(i4, i6, i5, i7));
                ObjectAnimator objectAnimatorA3 = f3.a(kVar, O, getPathMotion().getPath(i8, i10, i9, i11));
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playTogether(objectAnimatorA2, objectAnimatorA3);
                animatorSet.addListener(new h(this, kVar));
                animatorA = animatorSet;
            }
        }
        if (view2.getParent() instanceof ViewGroup) {
            ViewGroup viewGroup4 = (ViewGroup) view2.getParent();
            u3.a(viewGroup4, true);
            addListener(new j(this, viewGroup4));
        }
        return animatorA;
    }

    public boolean getResizeClip() {
        return this.J;
    }

    @Override // android.support.transition.Transition
    @Nullable
    public String[] getTransitionProperties() {
        return L;
    }

    public void setResizeClip(boolean z) {
        this.J = z;
    }

    public ChangeBounds(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.I = new int[2];
        this.J = false;
        this.K = false;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, m3.d);
        boolean namedBoolean = TypedArrayUtils.getNamedBoolean(typedArrayObtainStyledAttributes, (XmlResourceParser) attributeSet, "resizeClip", 0, false);
        typedArrayObtainStyledAttributes.recycle();
        setResizeClip(namedBoolean);
    }
}
