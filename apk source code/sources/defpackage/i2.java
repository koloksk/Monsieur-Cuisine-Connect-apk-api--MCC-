package defpackage;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.InsetDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.VisibilityAwareImageButton;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.Property;
import android.view.View;
import java.util.ArrayList;

@RequiresApi(21)
/* loaded from: classes.dex */
public class i2 extends h2 {
    public InsetDrawable t;

    public static class a extends GradientDrawable {
        @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
        public boolean isStateful() {
            return true;
        }
    }

    public i2(VisibilityAwareImageButton visibilityAwareImageButton, k2 k2Var) {
        super(visibilityAwareImageButton, k2Var);
    }

    @Override // defpackage.h2
    public void a(ColorStateList colorStateList, PorterDuff.Mode mode, int i, int i2) {
        Drawable layerDrawable;
        Drawable drawableWrap = DrawableCompat.wrap(a());
        this.e = drawableWrap;
        DrawableCompat.setTintList(drawableWrap, colorStateList);
        if (mode != null) {
            DrawableCompat.setTintMode(this.e, mode);
        }
        if (i2 > 0) {
            this.g = a(i2, colorStateList);
            layerDrawable = new LayerDrawable(new Drawable[]{this.g, this.e});
        } else {
            this.g = null;
            layerDrawable = this.e;
        }
        RippleDrawable rippleDrawable = new RippleDrawable(ColorStateList.valueOf(i), layerDrawable, null);
        this.f = rippleDrawable;
        this.h = rippleDrawable;
        super/*android.widget.ImageButton*/.setBackgroundDrawable(rippleDrawable);
    }

    @Override // defpackage.h2
    public void a(int[] iArr) {
    }

    @Override // defpackage.h2
    public float b() {
        return this.k.getElevation();
    }

    @Override // defpackage.h2
    public void c() {
    }

    @Override // defpackage.h2
    public y1 d() {
        return new z1();
    }

    @Override // defpackage.h2
    public GradientDrawable e() {
        return new a();
    }

    @Override // defpackage.h2
    public void f() {
        i();
    }

    @Override // defpackage.h2
    public boolean g() {
        return false;
    }

    @Override // defpackage.h2
    public void b(Rect rect) {
        k2 k2Var = this.l;
        if (!FloatingActionButton.this.j) {
            super/*android.widget.ImageButton*/.setBackgroundDrawable(this.f);
        } else {
            InsetDrawable insetDrawable = new InsetDrawable(this.f, rect.left, rect.top, rect.right, rect.bottom);
            this.t = insetDrawable;
            super/*android.widget.ImageButton*/.setBackgroundDrawable(insetDrawable);
        }
    }

    @Override // defpackage.h2
    public void a(int i) {
        Drawable drawable = this.f;
        if (drawable instanceof RippleDrawable) {
            ((RippleDrawable) drawable).setColor(ColorStateList.valueOf(i));
        } else if (drawable != null) {
            DrawableCompat.setTintList(drawable, h2.b(i));
        }
    }

    @Override // defpackage.h2
    public void a(float f, float f2) {
        StateListAnimator stateListAnimator = new StateListAnimator();
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(ObjectAnimator.ofFloat(this.k, "elevation", f).setDuration(0L)).with(ObjectAnimator.ofFloat(this.k, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, f2).setDuration(100L));
        animatorSet.setInterpolator(h2.o);
        stateListAnimator.addState(h2.p, animatorSet);
        AnimatorSet animatorSet2 = new AnimatorSet();
        animatorSet2.play(ObjectAnimator.ofFloat(this.k, "elevation", f).setDuration(0L)).with(ObjectAnimator.ofFloat(this.k, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, f2).setDuration(100L));
        animatorSet2.setInterpolator(h2.o);
        stateListAnimator.addState(h2.q, animatorSet2);
        AnimatorSet animatorSet3 = new AnimatorSet();
        ArrayList arrayList = new ArrayList();
        arrayList.add(ObjectAnimator.ofFloat(this.k, "elevation", f).setDuration(0L));
        if (Build.VERSION.SDK_INT <= 24) {
            VisibilityAwareImageButton visibilityAwareImageButton = this.k;
            arrayList.add(ObjectAnimator.ofFloat(visibilityAwareImageButton, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, visibilityAwareImageButton.getTranslationZ()).setDuration(100L));
        }
        arrayList.add(ObjectAnimator.ofFloat(this.k, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, 0.0f).setDuration(100L));
        animatorSet3.playSequentially((Animator[]) arrayList.toArray(new ObjectAnimator[0]));
        animatorSet3.setInterpolator(h2.o);
        stateListAnimator.addState(h2.r, animatorSet3);
        AnimatorSet animatorSet4 = new AnimatorSet();
        animatorSet4.play(ObjectAnimator.ofFloat(this.k, "elevation", 0.0f).setDuration(0L)).with(ObjectAnimator.ofFloat(this.k, (Property<VisibilityAwareImageButton, Float>) View.TRANSLATION_Z, 0.0f).setDuration(0L));
        animatorSet4.setInterpolator(h2.o);
        stateListAnimator.addState(h2.s, animatorSet4);
        this.k.setStateListAnimator(stateListAnimator);
        if (FloatingActionButton.this.j) {
            i();
        }
    }

    @Override // defpackage.h2
    public void a(Rect rect) {
        if (FloatingActionButton.this.j) {
            float sizeDimension = r0.getSizeDimension() / 2.0f;
            float elevation = this.k.getElevation() + this.j;
            int iCeil = (int) Math.ceil(j2.a(elevation, sizeDimension, false));
            int iCeil2 = (int) Math.ceil(j2.b(elevation, sizeDimension, false));
            rect.set(iCeil, iCeil2, iCeil, iCeil2);
            return;
        }
        rect.set(0, 0, 0, 0);
    }
}
