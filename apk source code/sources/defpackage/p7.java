package defpackage;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;

/* loaded from: classes.dex */
public class p7 {
    public final View a;
    public u8 d;
    public u8 e;
    public u8 f;
    public int c = -1;
    public final AppCompatDrawableManager b = AppCompatDrawableManager.get();

    public p7(View view2) {
        this.a = view2;
    }

    public void a(AttributeSet attributeSet, int i) {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.a.getContext(), attributeSet, R.styleable.ViewBackgroundHelper, i, 0);
        try {
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_android_background)) {
                this.c = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.ViewBackgroundHelper_android_background, -1);
                ColorStateList colorStateListB = this.b.b(this.a.getContext(), this.c);
                if (colorStateListB != null) {
                    a(colorStateListB);
                }
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_backgroundTint)) {
                ViewCompat.setBackgroundTintList(this.a, tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.ViewBackgroundHelper_backgroundTint));
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.ViewBackgroundHelper_backgroundTintMode)) {
                ViewCompat.setBackgroundTintMode(this.a, DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.getInt(R.styleable.ViewBackgroundHelper_backgroundTintMode, -1), null));
            }
        } finally {
            tintTypedArrayObtainStyledAttributes.recycle();
        }
    }

    public void b(ColorStateList colorStateList) {
        if (this.e == null) {
            this.e = new u8();
        }
        u8 u8Var = this.e;
        u8Var.a = colorStateList;
        u8Var.d = true;
        a();
    }

    public PorterDuff.Mode c() {
        u8 u8Var = this.e;
        if (u8Var != null) {
            return u8Var.b;
        }
        return null;
    }

    public void d() {
        this.c = -1;
        a((ColorStateList) null);
        a();
    }

    public ColorStateList b() {
        u8 u8Var = this.e;
        if (u8Var != null) {
            return u8Var.a;
        }
        return null;
    }

    public void a(int i) {
        this.c = i;
        AppCompatDrawableManager appCompatDrawableManager = this.b;
        a(appCompatDrawableManager != null ? appCompatDrawableManager.b(this.a.getContext(), i) : null);
        a();
    }

    public void a(PorterDuff.Mode mode) {
        if (this.e == null) {
            this.e = new u8();
        }
        u8 u8Var = this.e;
        u8Var.b = mode;
        u8Var.c = true;
        a();
    }

    public void a() {
        Drawable background = this.a.getBackground();
        if (background != null) {
            boolean z = true;
            if (this.d != null) {
                if (this.f == null) {
                    this.f = new u8();
                }
                u8 u8Var = this.f;
                u8Var.a = null;
                u8Var.d = false;
                u8Var.b = null;
                u8Var.c = false;
                ColorStateList backgroundTintList = ViewCompat.getBackgroundTintList(this.a);
                if (backgroundTintList != null) {
                    u8Var.d = true;
                    u8Var.a = backgroundTintList;
                }
                PorterDuff.Mode backgroundTintMode = ViewCompat.getBackgroundTintMode(this.a);
                if (backgroundTintMode != null) {
                    u8Var.c = true;
                    u8Var.b = backgroundTintMode;
                }
                if (u8Var.d || u8Var.c) {
                    AppCompatDrawableManager.a(background, u8Var, this.a.getDrawableState());
                } else {
                    z = false;
                }
                if (z) {
                    return;
                }
            }
            u8 u8Var2 = this.e;
            if (u8Var2 != null) {
                AppCompatDrawableManager.a(background, u8Var2, this.a.getDrawableState());
                return;
            }
            u8 u8Var3 = this.d;
            if (u8Var3 != null) {
                AppCompatDrawableManager.a(background, u8Var3, this.a.getDrawableState());
            }
        }
    }

    public void a(ColorStateList colorStateList) {
        if (colorStateList != null) {
            if (this.d == null) {
                this.d = new u8();
            }
            u8 u8Var = this.d;
            u8Var.a = colorStateList;
            u8Var.d = true;
        } else {
            this.d = null;
        }
        a();
    }
}
