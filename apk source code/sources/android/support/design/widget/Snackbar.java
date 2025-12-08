package android.support.design.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StringRes;
import android.support.design.internal.SnackbarContentLayout;
import android.support.design.widget.BaseTransientBottomBar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/* loaded from: classes.dex */
public final class Snackbar extends BaseTransientBottomBar<Snackbar> {
    public static final int LENGTH_INDEFINITE = -2;
    public static final int LENGTH_LONG = 0;
    public static final int LENGTH_SHORT = -1;

    @Nullable
    public BaseTransientBottomBar.BaseCallback<Snackbar> j;

    public static class Callback extends BaseTransientBottomBar.BaseCallback<Snackbar> {
        public static final int DISMISS_EVENT_ACTION = 1;
        public static final int DISMISS_EVENT_CONSECUTIVE = 4;
        public static final int DISMISS_EVENT_MANUAL = 3;
        public static final int DISMISS_EVENT_SWIPE = 0;
        public static final int DISMISS_EVENT_TIMEOUT = 2;

        @Override // android.support.design.widget.BaseTransientBottomBar.BaseCallback
        public void onDismissed(Snackbar snackbar, int i) {
        }

        @Override // android.support.design.widget.BaseTransientBottomBar.BaseCallback
        public void onShown(Snackbar snackbar) {
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final class SnackbarLayout extends BaseTransientBottomBar.i {
        public SnackbarLayout(Context context) {
            super(context);
        }

        @Override // android.widget.FrameLayout, android.view.View
        public void onMeasure(int i, int i2) {
            super.onMeasure(i, i2);
            int childCount = getChildCount();
            int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight();
            for (int i3 = 0; i3 < childCount; i3++) {
                View childAt = getChildAt(i3);
                if (childAt.getLayoutParams().width == -1) {
                    childAt.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, 1073741824), View.MeasureSpec.makeMeasureSpec(childAt.getMeasuredHeight(), 1073741824));
                }
            }
        }

        public SnackbarLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }
    }

    public class a implements View.OnClickListener {
        public final /* synthetic */ View.OnClickListener a;

        public a(View.OnClickListener onClickListener) {
            this.a = onClickListener;
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view2) {
            this.a.onClick(view2);
            Snackbar.this.a(1);
        }
    }

    public Snackbar(ViewGroup viewGroup, View view2, BaseTransientBottomBar.ContentViewCallback contentViewCallback) {
        super(viewGroup, view2, contentViewCallback);
    }

    @NonNull
    public static Snackbar make(@NonNull View view2, @StringRes int i, int i2) {
        return make(view2, view2.getResources().getText(i), i2);
    }

    @NonNull
    public Snackbar setAction(@StringRes int i, View.OnClickListener onClickListener) {
        return setAction(getContext().getText(i), onClickListener);
    }

    @NonNull
    public Snackbar setActionTextColor(ColorStateList colorStateList) {
        ((SnackbarContentLayout) this.c.getChildAt(0)).getActionView().setTextColor(colorStateList);
        return this;
    }

    @NonNull
    @Deprecated
    public Snackbar setCallback(Callback callback) {
        BaseTransientBottomBar.BaseCallback<Snackbar> baseCallback = this.j;
        if (baseCallback != null) {
            removeCallback(baseCallback);
        }
        if (callback != null) {
            addCallback(callback);
        }
        this.j = callback;
        return this;
    }

    @NonNull
    public Snackbar setText(@NonNull CharSequence charSequence) {
        ((SnackbarContentLayout) this.c.getChildAt(0)).getMessageView().setText(charSequence);
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x001e  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x002c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x002a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:? A[LOOP:0: B:3:0x0002->B:30:?, LOOP_END, SYNTHETIC] */
    @android.support.annotation.NonNull
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.support.design.widget.Snackbar make(@android.support.annotation.NonNull android.view.View r3, @android.support.annotation.NonNull java.lang.CharSequence r4, int r5) {
        /*
            r0 = 0
            r1 = r0
        L2:
            boolean r2 = r3 instanceof android.support.design.widget.CoordinatorLayout
            if (r2 == 0) goto L9
            android.view.ViewGroup r3 = (android.view.ViewGroup) r3
            goto L2d
        L9:
            boolean r2 = r3 instanceof android.widget.FrameLayout
            if (r2 == 0) goto L1c
            int r1 = r3.getId()
            r2 = 16908290(0x1020002, float:2.3877235E-38)
            if (r1 != r2) goto L19
            android.view.ViewGroup r3 = (android.view.ViewGroup) r3
            goto L2d
        L19:
            r1 = r3
            android.view.ViewGroup r1 = (android.view.ViewGroup) r1
        L1c:
            if (r3 == 0) goto L2a
            android.view.ViewParent r3 = r3.getParent()
            boolean r2 = r3 instanceof android.view.View
            if (r2 == 0) goto L29
            android.view.View r3 = (android.view.View) r3
            goto L2a
        L29:
            r3 = r0
        L2a:
            if (r3 != 0) goto L2
            r3 = r1
        L2d:
            if (r3 == 0) goto L4c
            android.content.Context r0 = r3.getContext()
            android.view.LayoutInflater r0 = android.view.LayoutInflater.from(r0)
            int r1 = android.support.design.R.layout.design_layout_snackbar_include
            r2 = 0
            android.view.View r0 = r0.inflate(r1, r3, r2)
            android.support.design.internal.SnackbarContentLayout r0 = (android.support.design.internal.SnackbarContentLayout) r0
            android.support.design.widget.Snackbar r1 = new android.support.design.widget.Snackbar
            r1.<init>(r3, r0, r0)
            r1.setText(r4)
            r1.setDuration(r5)
            return r1
        L4c:
            java.lang.IllegalArgumentException r3 = new java.lang.IllegalArgumentException
            java.lang.String r4 = "No suitable parent found from the given view. Please provide a valid view."
            r3.<init>(r4)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.Snackbar.make(android.view.View, java.lang.CharSequence, int):android.support.design.widget.Snackbar");
    }

    @NonNull
    public Snackbar setAction(CharSequence charSequence, View.OnClickListener onClickListener) {
        Button actionView = ((SnackbarContentLayout) this.c.getChildAt(0)).getActionView();
        if (TextUtils.isEmpty(charSequence) || onClickListener == null) {
            actionView.setVisibility(8);
            actionView.setOnClickListener(null);
        } else {
            actionView.setVisibility(0);
            actionView.setText(charSequence);
            actionView.setOnClickListener(new a(onClickListener));
        }
        return this;
    }

    @NonNull
    public Snackbar setActionTextColor(@ColorInt int i) {
        ((SnackbarContentLayout) this.c.getChildAt(0)).getActionView().setTextColor(i);
        return this;
    }

    @NonNull
    public Snackbar setText(@StringRes int i) {
        return setText(getContext().getText(i));
    }
}
