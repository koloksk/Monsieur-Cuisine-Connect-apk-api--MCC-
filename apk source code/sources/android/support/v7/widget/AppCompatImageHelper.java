package android.support.v7.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.v4.widget.ImageViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.widget.ImageView;
import defpackage.u8;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class AppCompatImageHelper {
    public final ImageView a;
    public u8 b;

    public AppCompatImageHelper(ImageView imageView) {
        this.a = imageView;
    }

    public void a(ColorStateList colorStateList) {
        if (this.b == null) {
            this.b = new u8();
        }
        u8 u8Var = this.b;
        u8Var.a = colorStateList;
        u8Var.d = true;
        a();
    }

    public void loadFromAttributes(AttributeSet attributeSet, int i) {
        int resourceId;
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(this.a.getContext(), attributeSet, R.styleable.AppCompatImageView, i, 0);
        try {
            Drawable drawable = this.a.getDrawable();
            if (drawable == null && (resourceId = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatImageView_srcCompat, -1)) != -1 && (drawable = AppCompatResources.getDrawable(this.a.getContext(), resourceId)) != null) {
                this.a.setImageDrawable(drawable);
            }
            if (drawable != null) {
                DrawableUtils.a(drawable);
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatImageView_tint)) {
                ImageViewCompat.setImageTintList(this.a, tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.AppCompatImageView_tint));
            }
            if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatImageView_tintMode)) {
                ImageViewCompat.setImageTintMode(this.a, DrawableUtils.parseTintMode(tintTypedArrayObtainStyledAttributes.getInt(R.styleable.AppCompatImageView_tintMode, -1), null));
            }
        } finally {
            tintTypedArrayObtainStyledAttributes.recycle();
        }
    }

    public void setImageResource(int i) {
        if (i != 0) {
            Drawable drawable = AppCompatResources.getDrawable(this.a.getContext(), i);
            if (drawable != null) {
                DrawableUtils.a(drawable);
            }
            this.a.setImageDrawable(drawable);
        } else {
            this.a.setImageDrawable(null);
        }
        a();
    }

    public void a(PorterDuff.Mode mode) {
        if (this.b == null) {
            this.b = new u8();
        }
        u8 u8Var = this.b;
        u8Var.b = mode;
        u8Var.c = true;
        a();
    }

    public void a() {
        u8 u8Var;
        Drawable drawable = this.a.getDrawable();
        if (drawable != null) {
            DrawableUtils.a(drawable);
        }
        if (drawable == null || (u8Var = this.b) == null) {
            return;
        }
        AppCompatDrawableManager.a(drawable, u8Var, this.a.getDrawableState());
    }
}
