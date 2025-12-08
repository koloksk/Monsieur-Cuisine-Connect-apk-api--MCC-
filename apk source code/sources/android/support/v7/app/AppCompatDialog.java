package android.support.v7.app;

import android.R;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v7.view.ActionMode;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import defpackage.o6;

/* loaded from: classes.dex */
public class AppCompatDialog extends Dialog implements AppCompatCallback {
    public AppCompatDelegate a;

    public AppCompatDialog(Context context) {
        this(context, 0);
    }

    @Override // android.app.Dialog
    public void addContentView(View view2, ViewGroup.LayoutParams layoutParams) {
        AppCompatDelegateImplV9 appCompatDelegateImplV9 = (AppCompatDelegateImplV9) getDelegate();
        appCompatDelegateImplV9.e();
        ((ViewGroup) appCompatDelegateImplV9.B.findViewById(R.id.content)).addView(view2, layoutParams);
        appCompatDelegateImplV9.e.onContentChanged();
    }

    @Override // android.app.Dialog
    @Nullable
    public <T extends View> T findViewById(@IdRes int i) {
        AppCompatDelegateImplV9 appCompatDelegateImplV9 = (AppCompatDelegateImplV9) getDelegate();
        appCompatDelegateImplV9.e();
        return (T) appCompatDelegateImplV9.d.findViewById(i);
    }

    public AppCompatDelegate getDelegate() {
        if (this.a == null) {
            this.a = AppCompatDelegate.create(this, this);
        }
        return this.a;
    }

    public ActionBar getSupportActionBar() {
        o6 o6Var = (o6) getDelegate();
        o6Var.c();
        return o6Var.h;
    }

    @Override // android.app.Dialog
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void invalidateOptionsMenu() {
        getDelegate().invalidateOptionsMenu();
    }

    @Override // android.app.Dialog
    public void onCreate(Bundle bundle) {
        getDelegate().installViewFactory();
        super.onCreate(bundle);
        getDelegate().onCreate(bundle);
    }

    @Override // android.app.Dialog
    public void onStop() {
        super.onStop();
        getDelegate().onStop();
    }

    @Override // android.support.v7.app.AppCompatCallback
    public void onSupportActionModeFinished(ActionMode actionMode) {
    }

    @Override // android.support.v7.app.AppCompatCallback
    public void onSupportActionModeStarted(ActionMode actionMode) {
    }

    @Override // android.support.v7.app.AppCompatCallback
    @Nullable
    public ActionMode onWindowStartingSupportActionMode(ActionMode.Callback callback) {
        return null;
    }

    @Override // android.app.Dialog
    public void setContentView(@LayoutRes int i) {
        getDelegate().setContentView(i);
    }

    @Override // android.app.Dialog
    public void setTitle(CharSequence charSequence) {
        super.setTitle(charSequence);
        o6 o6Var = (o6) getDelegate();
        o6Var.o = charSequence;
        o6Var.a(charSequence);
    }

    public boolean supportRequestWindowFeature(int i) {
        return getDelegate().requestWindowFeature(i);
    }

    public AppCompatDialog(Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
    }

    @Override // android.app.Dialog
    public void setContentView(View view2) {
        getDelegate().setContentView(view2);
    }

    public AppCompatDialog(Context context, int i) {
        if (i == 0) {
            TypedValue typedValue = new TypedValue();
            context.getTheme().resolveAttribute(android.support.v7.appcompat.R.attr.dialogTheme, typedValue, true);
            i = typedValue.resourceId;
        }
        super(context, i);
        getDelegate().onCreate(null);
        getDelegate().applyDayNight();
    }

    @Override // android.app.Dialog
    public void setContentView(View view2, ViewGroup.LayoutParams layoutParams) {
        getDelegate().setContentView(view2, layoutParams);
    }

    @Override // android.app.Dialog
    public void setTitle(int i) {
        super.setTitle(i);
        AppCompatDelegate delegate = getDelegate();
        String string = getContext().getString(i);
        o6 o6Var = (o6) delegate;
        o6Var.o = string;
        o6Var.a(string);
    }
}
