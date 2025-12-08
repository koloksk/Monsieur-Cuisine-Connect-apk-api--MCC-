package android.support.design.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.StyleRes;
import android.support.annotation.VisibleForTesting;
import android.support.design.R;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.Space;
import android.support.v4.widget.TextViewCompat;
import android.support.v4.widget.ViewGroupUtils;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.AppCompatDrawableManager;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.WithHint;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import defpackage.a2;
import defpackage.c2;
import defpackage.g9;
import defpackage.n2;
import defpackage.o2;
import defpackage.p2;
import defpackage.q1;

/* loaded from: classes.dex */
public class TextInputLayout extends LinearLayout implements WithHint {
    public Drawable A;
    public Drawable B;
    public ColorStateList C;
    public boolean D;
    public PorterDuff.Mode E;
    public boolean F;
    public ColorStateList G;
    public ColorStateList H;
    public boolean I;
    public final a2 J;
    public boolean K;
    public ValueAnimator L;
    public boolean M;
    public boolean N;
    public final FrameLayout a;
    public EditText b;
    public CharSequence c;
    public boolean d;
    public CharSequence e;
    public Paint f;
    public final Rect g;
    public LinearLayout h;
    public int i;
    public Typeface j;
    public boolean k;
    public TextView l;
    public int m;
    public boolean n;
    public CharSequence o;
    public boolean p;
    public TextView q;
    public int r;
    public int s;
    public int t;
    public boolean u;
    public boolean v;
    public Drawable w;
    public CharSequence x;
    public CheckableImageButton y;
    public boolean z;

    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        public CharSequence b;
        public boolean c;

        public static class a implements Parcelable.ClassLoaderCreator<SavedState> {
            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder sbA = g9.a("TextInputLayout.SavedState{");
            sbA.append(Integer.toHexString(System.identityHashCode(this)));
            sbA.append(" error=");
            sbA.append((Object) this.b);
            sbA.append("}");
            return sbA.toString();
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            TextUtils.writeToParcel(this.b, parcel, i);
            parcel.writeInt(this.c ? 1 : 0);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.b = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.c = parcel.readInt() == 1;
        }
    }

    public class a implements TextWatcher {
        public a() {
        }

        @Override // android.text.TextWatcher
        public void afterTextChanged(Editable editable) {
            TextInputLayout.this.a(!r0.N, false);
            TextInputLayout textInputLayout = TextInputLayout.this;
            if (textInputLayout.p) {
                textInputLayout.a(editable.length());
            }
        }

        @Override // android.text.TextWatcher
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }

        @Override // android.text.TextWatcher
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
        }
    }

    public class b implements View.OnClickListener {
        public b() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view2) {
            TextInputLayout.this.a(false);
        }
    }

    public class c implements ValueAnimator.AnimatorUpdateListener {
        public c() {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            TextInputLayout.this.J.c(((Float) valueAnimator.getAnimatedValue()).floatValue());
        }
    }

    public class d extends AccessibilityDelegateCompat {
        public d() {
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
            super.onInitializeAccessibilityEvent(view2, accessibilityEvent);
            accessibilityEvent.setClassName(TextInputLayout.class.getSimpleName());
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
            accessibilityNodeInfoCompat.setClassName(TextInputLayout.class.getSimpleName());
            CharSequence charSequence = TextInputLayout.this.J.v;
            if (!TextUtils.isEmpty(charSequence)) {
                accessibilityNodeInfoCompat.setText(charSequence);
            }
            EditText editText = TextInputLayout.this.b;
            if (editText != null) {
                accessibilityNodeInfoCompat.setLabelFor(editText);
            }
            TextView textView = TextInputLayout.this.l;
            CharSequence text = textView != null ? textView.getText() : null;
            if (TextUtils.isEmpty(text)) {
                return;
            }
            accessibilityNodeInfoCompat.setContentInvalid(true);
            accessibilityNodeInfoCompat.setError(text);
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public void onPopulateAccessibilityEvent(View view2, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent(view2, accessibilityEvent);
            CharSequence charSequence = TextInputLayout.this.J.v;
            if (TextUtils.isEmpty(charSequence)) {
                return;
            }
            accessibilityEvent.getText().add(charSequence);
        }
    }

    public TextInputLayout(Context context) {
        this(context, null);
    }

    private void setEditText(EditText editText) {
        if (this.b != null) {
            throw new IllegalArgumentException("We already have an EditText, can only have one");
        }
        if (!(editText instanceof TextInputEditText)) {
            Log.i("TextInputLayout", "EditText added is not a TextInputEditText. Please switch to using that class instead.");
        }
        this.b = editText;
        if (!c()) {
            a2 a2Var = this.J;
            Typeface typeface = this.b.getTypeface();
            a2Var.t = typeface;
            a2Var.s = typeface;
            a2Var.c();
        }
        a2 a2Var2 = this.J;
        float textSize = this.b.getTextSize();
        if (a2Var2.i != textSize) {
            a2Var2.i = textSize;
            a2Var2.c();
        }
        int gravity = this.b.getGravity();
        this.J.c((gravity & (-113)) | 48);
        this.J.e(gravity);
        this.b.addTextChangedListener(new a());
        if (this.G == null) {
            this.G = this.b.getHintTextColors();
        }
        if (this.d && TextUtils.isEmpty(this.e)) {
            CharSequence hint = this.b.getHint();
            this.c = hint;
            setHint(hint);
            this.b.setHint((CharSequence) null);
        }
        if (this.q != null) {
            a(this.b.getText().length());
        }
        if (this.h != null) {
            a();
        }
        f();
        a(false, true);
    }

    private void setHintInternal(CharSequence charSequence) {
        this.e = charSequence;
        this.J.a(charSequence);
    }

    @Override // android.view.ViewGroup
    public void addView(View view2, int i, ViewGroup.LayoutParams layoutParams) {
        if (!(view2 instanceof EditText)) {
            super.addView(view2, i, layoutParams);
            return;
        }
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
        layoutParams2.gravity = (layoutParams2.gravity & (-113)) | 16;
        this.a.addView(view2, layoutParams2);
        this.a.setLayoutParams(layoutParams);
        e();
        setEditText((EditText) view2);
    }

    public final void b() {
        if (this.w != null) {
            if (this.D || this.F) {
                Drawable drawableMutate = DrawableCompat.wrap(this.w).mutate();
                this.w = drawableMutate;
                if (this.D) {
                    DrawableCompat.setTintList(drawableMutate, this.C);
                }
                if (this.F) {
                    DrawableCompat.setTintMode(this.w, this.E);
                }
                CheckableImageButton checkableImageButton = this.y;
                if (checkableImageButton != null) {
                    Drawable drawable = checkableImageButton.getDrawable();
                    Drawable drawable2 = this.w;
                    if (drawable != drawable2) {
                        this.y.setImageDrawable(drawable2);
                    }
                }
            }
        }
    }

    public final boolean c() {
        EditText editText = this.b;
        return editText != null && (editText.getTransformationMethod() instanceof PasswordTransformationMethod);
    }

    public final void d() {
        Drawable background;
        TextView textView;
        TextView textView2;
        EditText editText = this.b;
        if (editText == null || (background = editText.getBackground()) == null) {
            return;
        }
        if (DrawableUtils.canSafelyMutateDrawable(background)) {
            background = background.mutate();
        }
        if (this.n && (textView2 = this.l) != null) {
            background.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(textView2.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
        } else if (this.u && (textView = this.q) != null) {
            background.setColorFilter(AppCompatDrawableManager.getPorterDuffColorFilter(textView.getCurrentTextColor(), PorterDuff.Mode.SRC_IN));
        } else {
            DrawableCompat.clearColorFilter(background);
            this.b.refreshDrawableState();
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int i) {
        EditText editText;
        if (this.c == null || (editText = this.b) == null) {
            super.dispatchProvideAutofillStructure(viewStructure, i);
            return;
        }
        CharSequence hint = editText.getHint();
        this.b.setHint(this.c);
        try {
            super.dispatchProvideAutofillStructure(viewStructure, i);
        } finally {
            this.b.setHint(hint);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        this.N = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.N = false;
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (this.d) {
            this.J.a(canvas);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void drawableStateChanged() {
        if (this.M) {
            return;
        }
        this.M = true;
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        a(ViewCompat.isLaidOut(this) && isEnabled(), false);
        d();
        a2 a2Var = this.J;
        if (a2Var != null ? a2Var.a(drawableState) | false : false) {
            invalidate();
        }
        this.M = false;
    }

    public final void e() {
        int i;
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.a.getLayoutParams();
        if (this.d) {
            if (this.f == null) {
                this.f = new Paint();
            }
            Paint paint = this.f;
            Typeface typeface = this.J.s;
            if (typeface == null) {
                typeface = Typeface.DEFAULT;
            }
            paint.setTypeface(typeface);
            this.f.setTextSize(this.J.j);
            i = (int) (-this.f.ascent());
        } else {
            i = 0;
        }
        if (i != layoutParams.topMargin) {
            layoutParams.topMargin = i;
            this.a.requestLayout();
        }
    }

    public final void f() {
        if (this.b == null) {
            return;
        }
        if (!(this.v && (c() || this.z))) {
            CheckableImageButton checkableImageButton = this.y;
            if (checkableImageButton != null && checkableImageButton.getVisibility() == 0) {
                this.y.setVisibility(8);
            }
            if (this.A != null) {
                Drawable[] compoundDrawablesRelative = TextViewCompat.getCompoundDrawablesRelative(this.b);
                if (compoundDrawablesRelative[2] == this.A) {
                    TextViewCompat.setCompoundDrawablesRelative(this.b, compoundDrawablesRelative[0], compoundDrawablesRelative[1], this.B, compoundDrawablesRelative[3]);
                    this.A = null;
                    return;
                }
                return;
            }
            return;
        }
        if (this.y == null) {
            CheckableImageButton checkableImageButton2 = (CheckableImageButton) LayoutInflater.from(getContext()).inflate(R.layout.design_text_input_password_icon, (ViewGroup) this.a, false);
            this.y = checkableImageButton2;
            checkableImageButton2.setImageDrawable(this.w);
            this.y.setContentDescription(this.x);
            this.a.addView(this.y);
            this.y.setOnClickListener(new b());
        }
        EditText editText = this.b;
        if (editText != null && ViewCompat.getMinimumHeight(editText) <= 0) {
            this.b.setMinimumHeight(ViewCompat.getMinimumHeight(this.y));
        }
        this.y.setVisibility(0);
        this.y.setChecked(this.z);
        if (this.A == null) {
            this.A = new ColorDrawable();
        }
        this.A.setBounds(0, 0, this.y.getMeasuredWidth(), 1);
        Drawable[] compoundDrawablesRelative2 = TextViewCompat.getCompoundDrawablesRelative(this.b);
        if (compoundDrawablesRelative2[2] != this.A) {
            this.B = compoundDrawablesRelative2[2];
        }
        TextViewCompat.setCompoundDrawablesRelative(this.b, compoundDrawablesRelative2[0], compoundDrawablesRelative2[1], this.A, compoundDrawablesRelative2[3]);
        this.y.setPadding(this.b.getPaddingLeft(), this.b.getPaddingTop(), this.b.getPaddingRight(), this.b.getPaddingBottom());
    }

    public int getCounterMaxLength() {
        return this.r;
    }

    @Nullable
    public EditText getEditText() {
        return this.b;
    }

    @Nullable
    public CharSequence getError() {
        if (this.k) {
            return this.o;
        }
        return null;
    }

    @Override // android.support.v7.widget.WithHint
    @Nullable
    public CharSequence getHint() {
        if (this.d) {
            return this.e;
        }
        return null;
    }

    @Nullable
    public CharSequence getPasswordVisibilityToggleContentDescription() {
        return this.x;
    }

    @Nullable
    public Drawable getPasswordVisibilityToggleDrawable() {
        return this.w;
    }

    @NonNull
    public Typeface getTypeface() {
        return this.j;
    }

    public boolean isCounterEnabled() {
        return this.p;
    }

    public boolean isErrorEnabled() {
        return this.k;
    }

    public boolean isHintAnimationEnabled() {
        return this.K;
    }

    public boolean isHintEnabled() {
        return this.d;
    }

    public boolean isPasswordVisibilityToggleEnabled() {
        return this.v;
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        EditText editText;
        super.onLayout(z, i, i2, i3, i4);
        if (!this.d || (editText = this.b) == null) {
            return;
        }
        Rect rect = this.g;
        ViewGroupUtils.getDescendantRect(this, editText, rect);
        int compoundPaddingLeft = this.b.getCompoundPaddingLeft() + rect.left;
        int compoundPaddingRight = rect.right - this.b.getCompoundPaddingRight();
        a2 a2Var = this.J;
        int compoundPaddingTop = this.b.getCompoundPaddingTop() + rect.top;
        int compoundPaddingBottom = rect.bottom - this.b.getCompoundPaddingBottom();
        if (!a2.a(a2Var.d, compoundPaddingLeft, compoundPaddingTop, compoundPaddingRight, compoundPaddingBottom)) {
            a2Var.d.set(compoundPaddingLeft, compoundPaddingTop, compoundPaddingRight, compoundPaddingBottom);
            a2Var.G = true;
            a2Var.b();
        }
        a2 a2Var2 = this.J;
        int paddingTop = getPaddingTop();
        int paddingBottom = (i4 - i2) - getPaddingBottom();
        if (!a2.a(a2Var2.e, compoundPaddingLeft, paddingTop, compoundPaddingRight, paddingBottom)) {
            a2Var2.e.set(compoundPaddingLeft, paddingTop, compoundPaddingRight, paddingBottom);
            a2Var2.G = true;
            a2Var2.b();
        }
        this.J.c();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        f();
        super.onMeasure(i, i2);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setError(savedState.b);
        if (savedState.c) {
            a(true);
        }
        requestLayout();
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (this.n) {
            savedState.b = getError();
        }
        savedState.c = this.z;
        return savedState;
    }

    public void setCounterEnabled(boolean z) {
        if (this.p != z) {
            if (z) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
                this.q = appCompatTextView;
                appCompatTextView.setId(R.id.textinput_counter);
                Typeface typeface = this.j;
                if (typeface != null) {
                    this.q.setTypeface(typeface);
                }
                this.q.setMaxLines(1);
                try {
                    TextViewCompat.setTextAppearance(this.q, this.s);
                } catch (Exception unused) {
                    TextViewCompat.setTextAppearance(this.q, android.support.v7.appcompat.R.style.TextAppearance_AppCompat_Caption);
                    this.q.setTextColor(ContextCompat.getColor(getContext(), android.support.v7.appcompat.R.color.error_color_material));
                }
                a(this.q, -1);
                EditText editText = this.b;
                if (editText == null) {
                    a(0);
                } else {
                    a(editText.getText().length());
                }
            } else {
                a(this.q);
                this.q = null;
            }
            this.p = z;
        }
    }

    public void setCounterMaxLength(int i) {
        if (this.r != i) {
            if (i > 0) {
                this.r = i;
            } else {
                this.r = -1;
            }
            if (this.p) {
                EditText editText = this.b;
                a(editText == null ? 0 : editText.getText().length());
            }
        }
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        a((ViewGroup) this, z);
        super.setEnabled(z);
    }

    public void setError(@Nullable CharSequence charSequence) {
        TextView textView;
        boolean z = ViewCompat.isLaidOut(this) && isEnabled() && ((textView = this.l) == null || !TextUtils.equals(textView.getText(), charSequence));
        this.o = charSequence;
        if (!this.k) {
            if (TextUtils.isEmpty(charSequence)) {
                return;
            } else {
                setErrorEnabled(true);
            }
        }
        this.n = true ^ TextUtils.isEmpty(charSequence);
        this.l.animate().cancel();
        if (this.n) {
            this.l.setText(charSequence);
            this.l.setVisibility(0);
            if (z) {
                if (this.l.getAlpha() == 1.0f) {
                    this.l.setAlpha(0.0f);
                }
                this.l.animate().alpha(1.0f).setDuration(200L).setInterpolator(q1.d).setListener(new n2(this)).start();
            } else {
                this.l.setAlpha(1.0f);
            }
        } else if (this.l.getVisibility() == 0) {
            if (z) {
                this.l.animate().alpha(0.0f).setDuration(200L).setInterpolator(q1.c).setListener(new o2(this, charSequence)).start();
            } else {
                this.l.setText(charSequence);
                this.l.setVisibility(4);
            }
        }
        d();
        a(z, false);
    }

    public void setErrorEnabled(boolean z) {
        if (this.k != z) {
            TextView textView = this.l;
            if (textView != null) {
                textView.animate().cancel();
            }
            if (z) {
                AppCompatTextView appCompatTextView = new AppCompatTextView(getContext());
                this.l = appCompatTextView;
                appCompatTextView.setId(R.id.textinput_error);
                Typeface typeface = this.j;
                if (typeface != null) {
                    this.l.setTypeface(typeface);
                }
                try {
                    TextViewCompat.setTextAppearance(this.l, this.m);
                } catch (Exception unused) {
                }
                boolean z2 = this.l.getTextColors().getDefaultColor() == -65281;
                if (z2) {
                    TextViewCompat.setTextAppearance(this.l, android.support.v7.appcompat.R.style.TextAppearance_AppCompat_Caption);
                    this.l.setTextColor(ContextCompat.getColor(getContext(), android.support.v7.appcompat.R.color.error_color_material));
                }
                this.l.setVisibility(4);
                ViewCompat.setAccessibilityLiveRegion(this.l, 1);
                a(this.l, 0);
            } else {
                this.n = false;
                d();
                a(this.l);
                this.l = null;
            }
            this.k = z;
        }
    }

    public void setErrorTextAppearance(@StyleRes int i) {
        this.m = i;
        TextView textView = this.l;
        if (textView != null) {
            TextViewCompat.setTextAppearance(textView, i);
        }
    }

    public void setHint(@Nullable CharSequence charSequence) {
        if (this.d) {
            setHintInternal(charSequence);
            sendAccessibilityEvent(2048);
        }
    }

    public void setHintAnimationEnabled(boolean z) {
        this.K = z;
    }

    public void setHintEnabled(boolean z) {
        if (z != this.d) {
            this.d = z;
            CharSequence hint = this.b.getHint();
            if (!this.d) {
                if (!TextUtils.isEmpty(this.e) && TextUtils.isEmpty(hint)) {
                    this.b.setHint(this.e);
                }
                setHintInternal(null);
            } else if (!TextUtils.isEmpty(hint)) {
                if (TextUtils.isEmpty(this.e)) {
                    setHint(hint);
                }
                this.b.setHint((CharSequence) null);
            }
            if (this.b != null) {
                e();
            }
        }
    }

    public void setHintTextAppearance(@StyleRes int i) {
        this.J.b(i);
        this.H = this.J.l;
        if (this.b != null) {
            a(false, false);
            e();
        }
    }

    public void setPasswordVisibilityToggleContentDescription(@StringRes int i) {
        setPasswordVisibilityToggleContentDescription(i != 0 ? getResources().getText(i) : null);
    }

    public void setPasswordVisibilityToggleDrawable(@DrawableRes int i) {
        setPasswordVisibilityToggleDrawable(i != 0 ? AppCompatResources.getDrawable(getContext(), i) : null);
    }

    public void setPasswordVisibilityToggleEnabled(boolean z) {
        EditText editText;
        if (this.v != z) {
            this.v = z;
            if (!z && this.z && (editText = this.b) != null) {
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
            this.z = false;
            f();
        }
    }

    public void setPasswordVisibilityToggleTintList(@Nullable ColorStateList colorStateList) {
        this.C = colorStateList;
        this.D = true;
        b();
    }

    public void setPasswordVisibilityToggleTintMode(@Nullable PorterDuff.Mode mode) {
        this.E = mode;
        this.F = true;
        b();
    }

    public void setTypeface(@Nullable Typeface typeface) {
        Typeface typeface2 = this.j;
        if ((typeface2 == null || typeface2.equals(typeface)) && (this.j != null || typeface == null)) {
            return;
        }
        this.j = typeface;
        a2 a2Var = this.J;
        a2Var.t = typeface;
        a2Var.s = typeface;
        a2Var.c();
        TextView textView = this.q;
        if (textView != null) {
            textView.setTypeface(typeface);
        }
        TextView textView2 = this.l;
        if (textView2 != null) {
            textView2.setTypeface(typeface);
        }
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public void a(boolean z, boolean z2) {
        boolean z3;
        ColorStateList colorStateList;
        TextView textView;
        boolean zIsEnabled = isEnabled();
        EditText editText = this.b;
        boolean z4 = (editText == null || TextUtils.isEmpty(editText.getText())) ? false : true;
        int[] drawableState = getDrawableState();
        int length = drawableState.length;
        int i = 0;
        while (true) {
            if (i >= length) {
                z3 = false;
                break;
            } else {
                if (drawableState[i] == 16842908) {
                    z3 = true;
                    break;
                }
                i++;
            }
        }
        boolean z5 = !TextUtils.isEmpty(getError());
        ColorStateList colorStateList2 = this.G;
        if (colorStateList2 != null) {
            a2 a2Var = this.J;
            if (a2Var.k != colorStateList2) {
                a2Var.k = colorStateList2;
                a2Var.c();
            }
        }
        if (zIsEnabled && this.u && (textView = this.q) != null) {
            this.J.a(textView.getTextColors());
        } else if (zIsEnabled && z3 && (colorStateList = this.H) != null) {
            a2 a2Var2 = this.J;
            if (a2Var2.l != colorStateList) {
                a2Var2.l = colorStateList;
                a2Var2.c();
            }
        } else {
            ColorStateList colorStateList3 = this.G;
            if (colorStateList3 != null) {
                a2 a2Var3 = this.J;
                if (a2Var3.l != colorStateList3) {
                    a2Var3.l = colorStateList3;
                    a2Var3.c();
                }
            }
        }
        if (z4 || (isEnabled() && (z3 || z5))) {
            if (z2 || this.I) {
                ValueAnimator valueAnimator = this.L;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    this.L.cancel();
                }
                if (z && this.K) {
                    a(1.0f);
                } else {
                    this.J.c(1.0f);
                }
                this.I = false;
                return;
            }
            return;
        }
        if (z2 || !this.I) {
            ValueAnimator valueAnimator2 = this.L;
            if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                this.L.cancel();
            }
            if (z && this.K) {
                a(0.0f);
            } else {
                this.J.c(0.0f);
            }
            this.I = true;
        }
    }

    public TextInputLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet);
        this.g = new Rect();
        this.J = new a2(this);
        p2.a(context);
        setOrientation(1);
        setWillNotDraw(false);
        setAddStatesFromChildren(true);
        FrameLayout frameLayout = new FrameLayout(context);
        this.a = frameLayout;
        frameLayout.setAddStatesFromChildren(true);
        addView(this.a);
        a2 a2Var = this.J;
        a2Var.J = q1.b;
        a2Var.c();
        a2 a2Var2 = this.J;
        a2Var2.I = new AccelerateInterpolator();
        a2Var2.c();
        this.J.c(8388659);
        TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.TextInputLayout, i, R.style.Widget_Design_TextInputLayout);
        this.d = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.TextInputLayout_hintEnabled, true);
        setHint(tintTypedArrayObtainStyledAttributes.getText(R.styleable.TextInputLayout_android_hint));
        this.K = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.TextInputLayout_hintAnimationEnabled, true);
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextInputLayout_android_textColorHint)) {
            ColorStateList colorStateList = tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.TextInputLayout_android_textColorHint);
            this.H = colorStateList;
            this.G = colorStateList;
        }
        if (tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, -1) != -1) {
            setHintTextAppearance(tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.TextInputLayout_hintTextAppearance, 0));
        }
        this.m = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.TextInputLayout_errorTextAppearance, 0);
        boolean z = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.TextInputLayout_errorEnabled, false);
        boolean z2 = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.TextInputLayout_counterEnabled, false);
        setCounterMaxLength(tintTypedArrayObtainStyledAttributes.getInt(R.styleable.TextInputLayout_counterMaxLength, -1));
        this.s = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.TextInputLayout_counterTextAppearance, 0);
        this.t = tintTypedArrayObtainStyledAttributes.getResourceId(R.styleable.TextInputLayout_counterOverflowTextAppearance, 0);
        this.v = tintTypedArrayObtainStyledAttributes.getBoolean(R.styleable.TextInputLayout_passwordToggleEnabled, false);
        this.w = tintTypedArrayObtainStyledAttributes.getDrawable(R.styleable.TextInputLayout_passwordToggleDrawable);
        this.x = tintTypedArrayObtainStyledAttributes.getText(R.styleable.TextInputLayout_passwordToggleContentDescription);
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextInputLayout_passwordToggleTint)) {
            this.D = true;
            this.C = tintTypedArrayObtainStyledAttributes.getColorStateList(R.styleable.TextInputLayout_passwordToggleTint);
        }
        if (tintTypedArrayObtainStyledAttributes.hasValue(R.styleable.TextInputLayout_passwordToggleTintMode)) {
            this.F = true;
            this.E = c2.a(tintTypedArrayObtainStyledAttributes.getInt(R.styleable.TextInputLayout_passwordToggleTintMode, -1), (PorterDuff.Mode) null);
        }
        tintTypedArrayObtainStyledAttributes.recycle();
        setErrorEnabled(z);
        setCounterEnabled(z2);
        b();
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        ViewCompat.setAccessibilityDelegate(this, new d());
    }

    public void setPasswordVisibilityToggleContentDescription(@Nullable CharSequence charSequence) {
        this.x = charSequence;
        CheckableImageButton checkableImageButton = this.y;
        if (checkableImageButton != null) {
            checkableImageButton.setContentDescription(charSequence);
        }
    }

    public void setPasswordVisibilityToggleDrawable(@Nullable Drawable drawable) {
        this.w = drawable;
        CheckableImageButton checkableImageButton = this.y;
        if (checkableImageButton != null) {
            checkableImageButton.setImageDrawable(drawable);
        }
    }

    public final void a(TextView textView, int i) {
        if (this.h == null) {
            LinearLayout linearLayout = new LinearLayout(getContext());
            this.h = linearLayout;
            linearLayout.setOrientation(0);
            addView(this.h, -1, -2);
            this.h.addView(new Space(getContext()), new LinearLayout.LayoutParams(0, 0, 1.0f));
            if (this.b != null) {
                a();
            }
        }
        this.h.setVisibility(0);
        this.h.addView(textView, i);
        this.i++;
    }

    public final void a() {
        ViewCompat.setPaddingRelative(this.h, ViewCompat.getPaddingStart(this.b), 0, ViewCompat.getPaddingEnd(this.b), this.b.getPaddingBottom());
    }

    public final void a(TextView textView) {
        LinearLayout linearLayout = this.h;
        if (linearLayout != null) {
            linearLayout.removeView(textView);
            int i = this.i - 1;
            this.i = i;
            if (i == 0) {
                this.h.setVisibility(8);
            }
        }
    }

    public static void a(ViewGroup viewGroup, boolean z) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setEnabled(z);
            if (childAt instanceof ViewGroup) {
                a((ViewGroup) childAt, z);
            }
        }
    }

    public void a(int i) {
        boolean z = this.u;
        int i2 = this.r;
        if (i2 == -1) {
            this.q.setText(String.valueOf(i));
            this.u = false;
        } else {
            boolean z2 = i > i2;
            this.u = z2;
            if (z != z2) {
                TextViewCompat.setTextAppearance(this.q, z2 ? this.t : this.s);
            }
            this.q.setText(getContext().getString(R.string.character_counter_pattern, Integer.valueOf(i), Integer.valueOf(this.r)));
        }
        if (this.b == null || z == this.u) {
            return;
        }
        a(false, false);
        d();
    }

    public final void a(boolean z) {
        if (this.v) {
            int selectionEnd = this.b.getSelectionEnd();
            if (c()) {
                this.b.setTransformationMethod(null);
                this.z = true;
            } else {
                this.b.setTransformationMethod(PasswordTransformationMethod.getInstance());
                this.z = false;
            }
            this.y.setChecked(this.z);
            if (z) {
                this.y.jumpDrawablesToCurrentState();
            }
            this.b.setSelection(selectionEnd);
        }
    }

    @VisibleForTesting
    public void a(float f) {
        if (this.J.c == f) {
            return;
        }
        if (this.L == null) {
            ValueAnimator valueAnimator = new ValueAnimator();
            this.L = valueAnimator;
            valueAnimator.setInterpolator(q1.a);
            this.L.setDuration(200L);
            this.L.addUpdateListener(new c());
        }
        this.L.setFloatValues(this.J.c, f);
        this.L.start();
    }
}
