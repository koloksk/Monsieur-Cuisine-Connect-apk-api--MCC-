package defpackage;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.AutoSizeableTextView;
import android.support.v7.appcompat.R;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.TintTypedArray;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.widget.TextView;
import java.lang.ref.WeakReference;
import java.util.Arrays;

@RequiresApi(9)
/* loaded from: classes.dex */
public class t7 {
    public final TextView a;
    public u8 b;
    public u8 c;
    public u8 d;
    public u8 e;

    @NonNull
    public final v7 f;
    public int g = 0;
    public Typeface h;
    public boolean i;

    public class a extends ResourcesCompat.FontCallback {
        public final /* synthetic */ WeakReference a;

        public a(WeakReference weakReference) {
            this.a = weakReference;
        }

        @Override // android.support.v4.content.res.ResourcesCompat.FontCallback
        public void onFontRetrievalFailed(int i) {
        }

        @Override // android.support.v4.content.res.ResourcesCompat.FontCallback
        public void onFontRetrieved(@NonNull Typeface typeface) {
            t7 t7Var = t7.this;
            WeakReference weakReference = this.a;
            if (t7Var.i) {
                t7Var.h = typeface;
                TextView textView = (TextView) weakReference.get();
                if (textView != null) {
                    textView.setTypeface(typeface, t7Var.g);
                }
            }
        }
    }

    public t7(TextView textView) {
        this.a = textView;
        this.f = new v7(this.a);
    }

    @SuppressLint({"NewApi"})
    public void a(AttributeSet attributeSet, int i) {
        boolean z;
        boolean z2;
        int resourceId;
        Context context = this.a.getContext();
        AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.get();
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.AppCompatTextHelper, i, 0);
        int resourceId2 = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_textAppearance, -1);
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableLeft)) {
            this.b = a(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableLeft, 0));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableTop)) {
            this.c = a(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableTop, 0));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableRight)) {
            this.d = a(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableRight, 0));
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextHelper_android_drawableBottom)) {
            this.e = a(context, appCompatDrawableManager, tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextHelper_android_drawableBottom, 0));
        }
        tintTypedArrayObtainStyledAttributes.recycle();
        boolean z3 = this.a.getTransformationMethod() instanceof PasswordTransformationMethod;
        if (resourceId2 != -1) {
            TintTypedArray tintTypedArrayObtainStyledAttributes2 = TintTypedArray.obtainStyledAttributes(context, resourceId2, R.styleable.TextAppearance);
            if (z3 || !tintTypedArrayObtainStyledAttributes2.hasValue(R.styleable.TextAppearance_textAllCaps)) {
                z = false;
                z2 = false;
            } else {
                z = tintTypedArrayObtainStyledAttributes2.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
                z2 = true;
            }
            a(context, tintTypedArrayObtainStyledAttributes2);
            tintTypedArrayObtainStyledAttributes2.recycle();
        } else {
            z = false;
            z2 = false;
        }
        TintTypedArray tintTypedArrayObtainStyledAttributes3 = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.TextAppearance, i, 0);
        if (!z3 && tintTypedArrayObtainStyledAttributes3.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            z = tintTypedArrayObtainStyledAttributes3.getBoolean(R.styleable.TextAppearance_textAllCaps, false);
            z2 = true;
        }
        a(context, tintTypedArrayObtainStyledAttributes3);
        tintTypedArrayObtainStyledAttributes3.recycle();
        if (!z3 && z2) {
            this.a.setAllCaps(z);
        }
        Typeface typeface = this.h;
        if (typeface != null) {
            this.a.setTypeface(typeface, this.g);
        }
        v7 v7Var = this.f;
        TypedArray typedArrayObtainStyledAttributes = v7Var.j.obtainStyledAttributes(attributeSet, R.styleable.AppCompatTextView, i, 0);
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizeTextType)) {
            v7Var.a = typedArrayObtainStyledAttributes.getInt(R.styleable.AppCompatTextView_autoSizeTextType, 0);
        }
        float dimension = typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizeStepGranularity) ? typedArrayObtainStyledAttributes.getDimension(R.styleable.AppCompatTextView_autoSizeStepGranularity, -1.0f) : -1.0f;
        float dimension2 = typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizeMinTextSize) ? typedArrayObtainStyledAttributes.getDimension(R.styleable.AppCompatTextView_autoSizeMinTextSize, -1.0f) : -1.0f;
        float dimension3 = typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizeMaxTextSize) ? typedArrayObtainStyledAttributes.getDimension(R.styleable.AppCompatTextView_autoSizeMaxTextSize, -1.0f) : -1.0f;
        if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppCompatTextView_autoSizePresetSizes) && (resourceId = typedArrayObtainStyledAttributes.getResourceId(R.styleable.AppCompatTextView_autoSizePresetSizes, 0)) > 0) {
            TypedArray typedArrayObtainTypedArray = typedArrayObtainStyledAttributes.getResources().obtainTypedArray(resourceId);
            int length = typedArrayObtainTypedArray.length();
            int[] iArr = new int[length];
            if (length > 0) {
                for (int i2 = 0; i2 < length; i2++) {
                    iArr[i2] = typedArrayObtainTypedArray.getDimensionPixelSize(i2, -1);
                }
                v7Var.f = v7Var.a(iArr);
                v7Var.c();
            }
            typedArrayObtainTypedArray.recycle();
        }
        typedArrayObtainStyledAttributes.recycle();
        if (!v7Var.d()) {
            v7Var.a = 0;
        } else if (v7Var.a == 1) {
            if (!v7Var.g) {
                DisplayMetrics displayMetrics = v7Var.j.getResources().getDisplayMetrics();
                if (dimension2 == -1.0f) {
                    dimension2 = TypedValue.applyDimension(2, 12.0f, displayMetrics);
                }
                if (dimension3 == -1.0f) {
                    dimension3 = TypedValue.applyDimension(2, 112.0f, displayMetrics);
                }
                if (dimension == -1.0f) {
                    dimension = 1.0f;
                }
                v7Var.a(dimension2, dimension3, dimension);
            }
            v7Var.b();
        }
        if (AutoSizeableTextView.PLATFORM_SUPPORTS_AUTOSIZE) {
            v7 v7Var2 = this.f;
            if (v7Var2.a != 0) {
                int[] iArr2 = v7Var2.f;
                if (iArr2.length > 0) {
                    if (this.a.getAutoSizeStepGranularity() != -1.0f) {
                        this.a.setAutoSizeTextTypeUniformWithConfiguration(Math.round(this.f.d), Math.round(this.f.e), Math.round(this.f.c), 0);
                    } else {
                        this.a.setAutoSizeTextTypeUniformWithPresetSizes(iArr2, 0);
                    }
                }
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean b() {
        v7 v7Var = this.f;
        return v7Var.d() && v7Var.a != 0;
    }

    public final void a(Context context, TintTypedArray tintTypedArray) {
        String string;
        this.g = tintTypedArray.getInt(R.styleable.TextAppearance_android_textStyle, this.g);
        if (!tintTypedArray.hasValue(R.styleable.TextAppearance_android_fontFamily) && !tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily)) {
            if (tintTypedArray.hasValue(R.styleable.TextAppearance_android_typeface)) {
                this.i = false;
                int i = tintTypedArray.getInt(R.styleable.TextAppearance_android_typeface, 1);
                if (i == 1) {
                    this.h = Typeface.SANS_SERIF;
                    return;
                } else if (i == 2) {
                    this.h = Typeface.SERIF;
                    return;
                } else {
                    if (i != 3) {
                        return;
                    }
                    this.h = Typeface.MONOSPACE;
                    return;
                }
            }
            return;
        }
        this.h = null;
        int i2 = tintTypedArray.hasValue(R.styleable.TextAppearance_fontFamily) ? R.styleable.TextAppearance_fontFamily : R.styleable.TextAppearance_android_fontFamily;
        if (!context.isRestricted()) {
            try {
                Typeface font = tintTypedArray.getFont(i2, this.g, new a(new WeakReference(this.a)));
                this.h = font;
                this.i = font == null;
            } catch (Resources.NotFoundException | UnsupportedOperationException unused) {
            }
        }
        if (this.h != null || (string = tintTypedArray.getString(i2)) == null) {
            return;
        }
        this.h = Typeface.create(string, this.g);
    }

    public void a(Context context, int i) {
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, i, R.styleable.TextAppearance);
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextAppearance_textAllCaps)) {
            this.a.setAllCaps(tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.TextAppearance_textAllCaps, false));
        }
        a(context, tintTypedArrayObtainStyledAttributes);
        tintTypedArrayObtainStyledAttributes.recycle();
        Typeface typeface = this.h;
        if (typeface != null) {
            this.a.setTypeface(typeface, this.g);
        }
    }

    public void a() {
        if (this.b == null && this.c == null && this.d == null && this.e == null) {
            return;
        }
        Drawable[] compoundDrawables = this.a.getCompoundDrawables();
        a(compoundDrawables[0], this.b);
        a(compoundDrawables[1], this.c);
        a(compoundDrawables[2], this.d);
        a(compoundDrawables[3], this.e);
    }

    public final void a(Drawable drawable, u8 u8Var) {
        if (drawable == null || u8Var == null) {
            return;
        }
        AppCompatDrawableManager.a(drawable, u8Var, this.a.getDrawableState());
    }

    public static u8 a(Context context, AppCompatDrawableManager appCompatDrawableManager, int i) {
        ColorStateList colorStateListB = appCompatDrawableManager.b(context, i);
        if (colorStateListB == null) {
            return null;
        }
        u8 u8Var = new u8();
        u8Var.d = true;
        u8Var.a = colorStateListB;
        return u8Var;
    }

    public void a(int i) {
        v7 v7Var = this.f;
        if (v7Var.d()) {
            if (i == 0) {
                v7Var.a = 0;
                v7Var.d = -1.0f;
                v7Var.e = -1.0f;
                v7Var.c = -1.0f;
                v7Var.f = new int[0];
                v7Var.b = false;
                return;
            }
            if (i == 1) {
                DisplayMetrics displayMetrics = v7Var.j.getResources().getDisplayMetrics();
                v7Var.a(TypedValue.applyDimension(2, 12.0f, displayMetrics), TypedValue.applyDimension(2, 112.0f, displayMetrics), 1.0f);
                if (v7Var.b()) {
                    v7Var.a();
                    return;
                }
                return;
            }
            throw new IllegalArgumentException(g9.b("Unknown auto-size text type: ", i));
        }
    }

    public void a(int i, int i2, int i3, int i4) throws IllegalArgumentException {
        v7 v7Var = this.f;
        if (v7Var.d()) {
            DisplayMetrics displayMetrics = v7Var.j.getResources().getDisplayMetrics();
            v7Var.a(TypedValue.applyDimension(i4, i, displayMetrics), TypedValue.applyDimension(i4, i2, displayMetrics), TypedValue.applyDimension(i4, i3, displayMetrics));
            if (v7Var.b()) {
                v7Var.a();
            }
        }
    }

    public void a(@NonNull int[] iArr, int i) throws IllegalArgumentException {
        v7 v7Var = this.f;
        if (v7Var.d()) {
            int length = iArr.length;
            if (length > 0) {
                int[] iArrCopyOf = new int[length];
                if (i == 0) {
                    iArrCopyOf = Arrays.copyOf(iArr, length);
                } else {
                    DisplayMetrics displayMetrics = v7Var.j.getResources().getDisplayMetrics();
                    for (int i2 = 0; i2 < length; i2++) {
                        iArrCopyOf[i2] = Math.round(TypedValue.applyDimension(i, iArr[i2], displayMetrics));
                    }
                }
                v7Var.f = v7Var.a(iArrCopyOf);
                if (!v7Var.c()) {
                    StringBuilder sbA = g9.a("None of the preset sizes is valid: ");
                    sbA.append(Arrays.toString(iArr));
                    throw new IllegalArgumentException(sbA.toString());
                }
            } else {
                v7Var.g = false;
            }
            if (v7Var.b()) {
                v7Var.a();
            }
        }
    }
}
