package android.support.v7.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import defpackage.p8;
import defpackage.w7;
import defpackage.x7;
import defpackage.y7;

/* loaded from: classes.dex */
public class CardView extends FrameLayout {
    public static final int[] h = {R.attr.colorBackground};
    public static final y7 i = new w7();
    public boolean a;
    public boolean b;
    public int c;
    public int d;
    public final Rect e;
    public final Rect f;
    public final x7 g;

    public class a implements x7 {
        public Drawable a;

        public a() {
        }

        public boolean a() {
            return CardView.this.getPreventCornerOverlap();
        }

        public void a(int i, int i2, int i3, int i4) {
            CardView.this.f.set(i, i2, i3, i4);
            CardView cardView = CardView.this;
            Rect rect = cardView.e;
            CardView.super.setPadding(i + rect.left, i2 + rect.top, i3 + rect.right, i4 + rect.bottom);
        }
    }

    public CardView(@NonNull Context context) {
        this(context, null);
    }

    @NonNull
    public ColorStateList getCardBackgroundColor() {
        return ((w7) i).a(this.g).h;
    }

    public float getCardElevation() {
        return CardView.this.getElevation();
    }

    public int getContentPaddingBottom() {
        return this.e.bottom;
    }

    public int getContentPaddingLeft() {
        return this.e.left;
    }

    public int getContentPaddingRight() {
        return this.e.right;
    }

    public int getContentPaddingTop() {
        return this.e.top;
    }

    public float getMaxCardElevation() {
        return ((w7) i).b(this.g);
    }

    public boolean getPreventCornerOverlap() {
        return this.b;
    }

    public float getRadius() {
        return ((w7) i).c(this.g);
    }

    public boolean getUseCompatPadding() {
        return this.a;
    }

    @Override // android.widget.FrameLayout, android.view.View
    public void onMeasure(int i2, int i3) {
        super.onMeasure(i2, i3);
    }

    public void setCardBackgroundColor(@ColorInt int i2) {
        y7 y7Var = i;
        x7 x7Var = this.g;
        ColorStateList colorStateListValueOf = ColorStateList.valueOf(i2);
        p8 p8VarA = ((w7) y7Var).a(x7Var);
        p8VarA.a(colorStateListValueOf);
        p8VarA.invalidateSelf();
    }

    public void setCardElevation(float f) {
        CardView.this.setElevation(f);
    }

    public void setContentPadding(int i2, int i3, int i4, int i5) {
        this.e.set(i2, i3, i4, i5);
        ((w7) i).d(this.g);
    }

    public void setMaxCardElevation(float f) {
        ((w7) i).a(this.g, f);
    }

    @Override // android.view.View
    public void setMinimumHeight(int i2) {
        this.d = i2;
        super.setMinimumHeight(i2);
    }

    @Override // android.view.View
    public void setMinimumWidth(int i2) {
        this.c = i2;
        super.setMinimumWidth(i2);
    }

    @Override // android.view.View
    public void setPadding(int i2, int i3, int i4, int i5) {
    }

    @Override // android.view.View
    public void setPaddingRelative(int i2, int i3, int i4, int i5) {
    }

    public void setPreventCornerOverlap(boolean z) {
        if (z != this.b) {
            this.b = z;
            y7 y7Var = i;
            x7 x7Var = this.g;
            w7 w7Var = (w7) y7Var;
            w7Var.a(x7Var, w7Var.a(x7Var).e);
        }
    }

    public void setRadius(float f) {
        p8 p8VarA = ((w7) i).a(this.g);
        if (f == p8VarA.a) {
            return;
        }
        p8VarA.a = f;
        p8VarA.a((Rect) null);
        p8VarA.invalidateSelf();
    }

    public void setUseCompatPadding(boolean z) {
        if (this.a != z) {
            this.a = z;
            y7 y7Var = i;
            x7 x7Var = this.g;
            w7 w7Var = (w7) y7Var;
            w7Var.a(x7Var, w7Var.a(x7Var).e);
        }
    }

    public CardView(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, android.support.v7.cardview.R.attr.cardViewStyle);
    }

    public CardView(@NonNull Context context, @Nullable AttributeSet attributeSet, int i2) throws Resources.NotFoundException {
        int color;
        ColorStateList colorStateListValueOf;
        super(context, attributeSet, i2);
        this.e = new Rect();
        this.f = new Rect();
        this.g = new a();
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.support.v7.cardview.R.styleable.CardView, i2, android.support.v7.cardview.R.style.CardView);
        if (typedArrayObtainStyledAttributes.hasValue(android.support.v7.cardview.R.styleable.CardView_cardBackgroundColor)) {
            colorStateListValueOf = typedArrayObtainStyledAttributes.getColorStateList(android.support.v7.cardview.R.styleable.CardView_cardBackgroundColor);
        } else {
            TypedArray typedArrayObtainStyledAttributes2 = getContext().obtainStyledAttributes(h);
            int color2 = typedArrayObtainStyledAttributes2.getColor(0, 0);
            typedArrayObtainStyledAttributes2.recycle();
            float[] fArr = new float[3];
            Color.colorToHSV(color2, fArr);
            if (fArr[2] > 0.5f) {
                color = getResources().getColor(android.support.v7.cardview.R.color.cardview_light_background);
            } else {
                color = getResources().getColor(android.support.v7.cardview.R.color.cardview_dark_background);
            }
            colorStateListValueOf = ColorStateList.valueOf(color);
        }
        float dimension = typedArrayObtainStyledAttributes.getDimension(android.support.v7.cardview.R.styleable.CardView_cardCornerRadius, 0.0f);
        float dimension2 = typedArrayObtainStyledAttributes.getDimension(android.support.v7.cardview.R.styleable.CardView_cardElevation, 0.0f);
        float dimension3 = typedArrayObtainStyledAttributes.getDimension(android.support.v7.cardview.R.styleable.CardView_cardMaxElevation, 0.0f);
        this.a = typedArrayObtainStyledAttributes.getBoolean(android.support.v7.cardview.R.styleable.CardView_cardUseCompatPadding, false);
        this.b = typedArrayObtainStyledAttributes.getBoolean(android.support.v7.cardview.R.styleable.CardView_cardPreventCornerOverlap, true);
        int dimensionPixelSize = typedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.cardview.R.styleable.CardView_contentPadding, 0);
        this.e.left = typedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.cardview.R.styleable.CardView_contentPaddingLeft, dimensionPixelSize);
        this.e.top = typedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.cardview.R.styleable.CardView_contentPaddingTop, dimensionPixelSize);
        this.e.right = typedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.cardview.R.styleable.CardView_contentPaddingRight, dimensionPixelSize);
        this.e.bottom = typedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.cardview.R.styleable.CardView_contentPaddingBottom, dimensionPixelSize);
        dimension3 = dimension2 > dimension3 ? dimension2 : dimension3;
        this.c = typedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.cardview.R.styleable.CardView_android_minWidth, 0);
        this.d = typedArrayObtainStyledAttributes.getDimensionPixelSize(android.support.v7.cardview.R.styleable.CardView_android_minHeight, 0);
        typedArrayObtainStyledAttributes.recycle();
        y7 y7Var = i;
        x7 x7Var = this.g;
        p8 p8Var = new p8(colorStateListValueOf, dimension);
        a aVar = (a) x7Var;
        aVar.a = p8Var;
        CardView.this.setBackgroundDrawable(p8Var);
        CardView cardView = CardView.this;
        cardView.setClipToOutline(true);
        cardView.setElevation(dimension2);
        ((w7) y7Var).a(x7Var, dimension3);
    }

    public void setCardBackgroundColor(@Nullable ColorStateList colorStateList) {
        p8 p8VarA = ((w7) i).a(this.g);
        p8VarA.a(colorStateList);
        p8VarA.invalidateSelf();
    }
}
