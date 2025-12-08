package android.support.transition;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.annotation.NonNull;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import defpackage.a3;
import defpackage.a4;
import defpackage.e4;
import defpackage.m3;
import java.lang.reflect.InvocationTargetException;
import org.xmlpull.v1.XmlPullParser;

/* loaded from: classes.dex */
public class ChangeTransform extends Transition {
    public static final String[] L = {"android:changeTransform:matrix", "android:changeTransform:transforms", "android:changeTransform:parentMatrix"};
    public static final Property<d, float[]> M = new a(float[].class, "nonTranslations");
    public static final Property<d, PointF> N = new b(PointF.class, "translations");
    public static final boolean O = true;
    public boolean I;
    public boolean J;
    public Matrix K;

    public static class a extends Property<d, float[]> {
        public a(Class cls, String str) {
            super(cls, str);
        }

        @Override // android.util.Property
        public float[] get(d dVar) {
            return null;
        }

        @Override // android.util.Property
        public void set(d dVar, float[] fArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            d dVar2 = dVar;
            float[] fArr2 = fArr;
            System.arraycopy(fArr2, 0, dVar2.c, 0, fArr2.length);
            dVar2.a();
        }
    }

    public static class b extends Property<d, PointF> {
        public b(Class cls, String str) {
            super(cls, str);
        }

        @Override // android.util.Property
        public PointF get(d dVar) {
            return null;
        }

        @Override // android.util.Property
        public void set(d dVar, PointF pointF) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            d dVar2 = dVar;
            PointF pointF2 = pointF;
            if (dVar2 == null) {
                throw null;
            }
            dVar2.d = pointF2.x;
            dVar2.e = pointF2.y;
            dVar2.a();
        }
    }

    public static class c extends TransitionListenerAdapter {
        public View a;
        public GhostViewImpl b;

        public c(View view2, GhostViewImpl ghostViewImpl) {
            this.a = view2;
            this.b = ghostViewImpl;
        }

        @Override // android.support.transition.TransitionListenerAdapter, android.support.transition.Transition.TransitionListener
        public void onTransitionEnd(@NonNull Transition transition) {
            transition.removeListener(this);
            a3.a.removeGhost(this.a);
            this.a.setTag(R.id.transition_transform, null);
            this.a.setTag(R.id.parent_matrix, null);
        }

        @Override // android.support.transition.TransitionListenerAdapter, android.support.transition.Transition.TransitionListener
        public void onTransitionPause(@NonNull Transition transition) {
            this.b.setVisibility(4);
        }

        @Override // android.support.transition.TransitionListenerAdapter, android.support.transition.Transition.TransitionListener
        public void onTransitionResume(@NonNull Transition transition) {
            this.b.setVisibility(0);
        }
    }

    public static class d {
        public final Matrix a = new Matrix();
        public final View b;
        public final float[] c;
        public float d;
        public float e;

        public d(View view2, float[] fArr) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            this.b = view2;
            float[] fArr2 = (float[]) fArr.clone();
            this.c = fArr2;
            this.d = fArr2[2];
            this.e = fArr2[5];
            a();
        }

        public final void a() throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            float[] fArr = this.c;
            fArr[2] = this.d;
            fArr[5] = this.e;
            this.a.setValues(fArr);
            a4.a(this.b, this.a);
        }
    }

    public static class e {
        public final float a;
        public final float b;
        public final float c;
        public final float d;
        public final float e;
        public final float f;
        public final float g;
        public final float h;

        public e(View view2) {
            this.a = view2.getTranslationX();
            this.b = view2.getTranslationY();
            this.c = ViewCompat.getTranslationZ(view2);
            this.d = view2.getScaleX();
            this.e = view2.getScaleY();
            this.f = view2.getRotationX();
            this.g = view2.getRotationY();
            this.h = view2.getRotation();
        }

        public void a(View view2) {
            ChangeTransform.a(view2, this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof e)) {
                return false;
            }
            e eVar = (e) obj;
            return eVar.a == this.a && eVar.b == this.b && eVar.c == this.c && eVar.d == this.d && eVar.e == this.e && eVar.f == this.f && eVar.g == this.g && eVar.h == this.h;
        }

        public int hashCode() {
            float f = this.a;
            int iFloatToIntBits = (f != 0.0f ? Float.floatToIntBits(f) : 0) * 31;
            float f2 = this.b;
            int iFloatToIntBits2 = (iFloatToIntBits + (f2 != 0.0f ? Float.floatToIntBits(f2) : 0)) * 31;
            float f3 = this.c;
            int iFloatToIntBits3 = (iFloatToIntBits2 + (f3 != 0.0f ? Float.floatToIntBits(f3) : 0)) * 31;
            float f4 = this.d;
            int iFloatToIntBits4 = (iFloatToIntBits3 + (f4 != 0.0f ? Float.floatToIntBits(f4) : 0)) * 31;
            float f5 = this.e;
            int iFloatToIntBits5 = (iFloatToIntBits4 + (f5 != 0.0f ? Float.floatToIntBits(f5) : 0)) * 31;
            float f6 = this.f;
            int iFloatToIntBits6 = (iFloatToIntBits5 + (f6 != 0.0f ? Float.floatToIntBits(f6) : 0)) * 31;
            float f7 = this.g;
            int iFloatToIntBits7 = (iFloatToIntBits6 + (f7 != 0.0f ? Float.floatToIntBits(f7) : 0)) * 31;
            float f8 = this.h;
            return iFloatToIntBits7 + (f8 != 0.0f ? Float.floatToIntBits(f8) : 0);
        }
    }

    public ChangeTransform() {
        this.I = true;
        this.J = true;
        this.K = new Matrix();
    }

    public static /* synthetic */ void a(View view2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8) {
        view2.setTranslationX(f);
        view2.setTranslationY(f2);
        ViewCompat.setTranslationZ(view2, f3);
        view2.setScaleX(f4);
        view2.setScaleY(f5);
        view2.setRotationX(f6);
        view2.setRotationY(f7);
        view2.setRotation(f8);
    }

    public final void b(TransitionValues transitionValues) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        View view2 = transitionValues.f0view;
        if (view2.getVisibility() == 8) {
            return;
        }
        transitionValues.values.put("android:changeTransform:parent", view2.getParent());
        transitionValues.values.put("android:changeTransform:transforms", new e(view2));
        Matrix matrix = view2.getMatrix();
        transitionValues.values.put("android:changeTransform:matrix", (matrix == null || matrix.isIdentity()) ? null : new Matrix(matrix));
        if (this.J) {
            Matrix matrix2 = new Matrix();
            ((e4) a4.a).a((ViewGroup) view2.getParent(), matrix2);
            matrix2.preTranslate(-r2.getScrollX(), -r2.getScrollY());
            transitionValues.values.put("android:changeTransform:parentMatrix", matrix2);
            transitionValues.values.put("android:changeTransform:intermediateMatrix", view2.getTag(R.id.transition_transform));
            transitionValues.values.put("android:changeTransform:intermediateParentMatrix", view2.getTag(R.id.parent_matrix));
        }
    }

    @Override // android.support.transition.Transition
    public void captureEndValues(@NonNull TransitionValues transitionValues) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        b(transitionValues);
    }

    @Override // android.support.transition.Transition
    public void captureStartValues(@NonNull TransitionValues transitionValues) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        b(transitionValues);
        if (O) {
            return;
        }
        ((ViewGroup) transitionValues.f0view.getParent()).startViewTransition(transitionValues.f0view);
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0051, code lost:
    
        r1 = true;
     */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x007a  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0081  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00d0  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00df  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0157 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x01b0  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x01b6  */
    @Override // android.support.transition.Transition
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.animation.Animator createAnimator(@android.support.annotation.NonNull android.view.ViewGroup r22, android.support.transition.TransitionValues r23, android.support.transition.TransitionValues r24) throws java.lang.IllegalAccessException, java.lang.NoSuchMethodException, java.lang.SecurityException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
        /*
            Method dump skipped, instructions count: 446
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.transition.ChangeTransform.createAnimator(android.view.ViewGroup, android.support.transition.TransitionValues, android.support.transition.TransitionValues):android.animation.Animator");
    }

    public boolean getReparent() {
        return this.J;
    }

    public boolean getReparentWithOverlay() {
        return this.I;
    }

    @Override // android.support.transition.Transition
    public String[] getTransitionProperties() {
        return L;
    }

    public void setReparent(boolean z) {
        this.J = z;
    }

    public void setReparentWithOverlay(boolean z) {
        this.I = z;
    }

    public ChangeTransform(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.I = true;
        this.J = true;
        this.K = new Matrix();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, m3.g);
        XmlPullParser xmlPullParser = (XmlPullParser) attributeSet;
        this.I = TypedArrayUtils.getNamedBoolean(typedArrayObtainStyledAttributes, xmlPullParser, "reparentWithOverlay", 1, true);
        this.J = TypedArrayUtils.getNamedBoolean(typedArrayObtainStyledAttributes, xmlPullParser, "reparent", 0, true);
        typedArrayObtainStyledAttributes.recycle();
    }

    public static void b(View view2) {
        view2.setTranslationX(0.0f);
        view2.setTranslationY(0.0f);
        ViewCompat.setTranslationZ(view2, 0.0f);
        view2.setScaleX(1.0f);
        view2.setScaleY(1.0f);
        view2.setRotationX(0.0f);
        view2.setRotationY(0.0f);
        view2.setRotation(0.0f);
    }
}
