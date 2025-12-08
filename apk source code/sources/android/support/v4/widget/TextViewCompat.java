package android.support.v4.widget;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.v4.os.BuildCompat;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public final class TextViewCompat {
    public static final int AUTO_SIZE_TEXT_TYPE_NONE = 0;
    public static final int AUTO_SIZE_TEXT_TYPE_UNIFORM = 1;
    public static final g a;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface AutoSizeTextType {
    }

    @RequiresApi(16)
    public static class a extends g {
        @Override // android.support.v4.widget.TextViewCompat.g
        public int g(TextView textView) {
            return textView.getMaxLines();
        }

        @Override // android.support.v4.widget.TextViewCompat.g
        public int h(TextView textView) {
            return textView.getMinLines();
        }
    }

    @RequiresApi(17)
    public static class b extends a {
    }

    @RequiresApi(18)
    public static class c extends b {
        @Override // android.support.v4.widget.TextViewCompat.g
        public void a(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawablesRelative(drawable, drawable2, drawable3, drawable4);
        }

        @Override // android.support.v4.widget.TextViewCompat.g
        public void b(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(drawable, drawable2, drawable3, drawable4);
        }

        @Override // android.support.v4.widget.TextViewCompat.g
        public Drawable[] f(@NonNull TextView textView) {
            return textView.getCompoundDrawablesRelative();
        }

        @Override // android.support.v4.widget.TextViewCompat.g
        public void b(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
            textView.setCompoundDrawablesRelativeWithIntrinsicBounds(i, i2, i3, i4);
        }
    }

    @RequiresApi(23)
    public static class d extends c {
        @Override // android.support.v4.widget.TextViewCompat.g
        public void b(@NonNull TextView textView, @StyleRes int i) {
            textView.setTextAppearance(i);
        }
    }

    @RequiresApi(26)
    public static class e extends d {

        public class a implements ActionMode.Callback {
            public Class a;
            public Method b;
            public boolean c;
            public boolean d = false;
            public final /* synthetic */ ActionMode.Callback e;
            public final /* synthetic */ TextView f;

            public a(e eVar, ActionMode.Callback callback, TextView textView) {
                this.e = callback;
                this.f = textView;
            }

            @Override // android.view.ActionMode.Callback
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                return this.e.onActionItemClicked(actionMode, menuItem);
            }

            @Override // android.view.ActionMode.Callback
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                return this.e.onCreateActionMode(actionMode, menu);
            }

            @Override // android.view.ActionMode.Callback
            public void onDestroyActionMode(ActionMode actionMode) {
                this.e.onDestroyActionMode(actionMode);
            }

            /* JADX WARN: Removed duplicated region for block: B:42:0x00ce  */
            @Override // android.view.ActionMode.Callback
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public boolean onPrepareActionMode(android.view.ActionMode r13, android.view.Menu r14) throws java.lang.IllegalAccessException, java.lang.ClassNotFoundException, java.lang.IllegalArgumentException, java.lang.reflect.InvocationTargetException {
                /*
                    Method dump skipped, instructions count: 304
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: android.support.v4.widget.TextViewCompat.e.a.onPrepareActionMode(android.view.ActionMode, android.view.Menu):boolean");
            }
        }

        @Override // android.support.v4.widget.TextViewCompat.g
        public void a(TextView textView, ActionMode.Callback callback) {
            int i = Build.VERSION.SDK_INT;
            if (i == 26 || i == 27) {
                textView.setCustomSelectionActionModeCallback(new a(this, callback, textView));
            } else {
                textView.setCustomSelectionActionModeCallback(callback);
            }
        }
    }

    @RequiresApi(27)
    public static class f extends e {
        @Override // android.support.v4.widget.TextViewCompat.g
        public void a(TextView textView, int i) {
            textView.setAutoSizeTextTypeWithDefaults(i);
        }

        @Override // android.support.v4.widget.TextViewCompat.g
        public int b(TextView textView) {
            return textView.getAutoSizeMinTextSize();
        }

        @Override // android.support.v4.widget.TextViewCompat.g
        public int c(TextView textView) {
            return textView.getAutoSizeStepGranularity();
        }

        @Override // android.support.v4.widget.TextViewCompat.g
        public int[] d(TextView textView) {
            return textView.getAutoSizeTextAvailableSizes();
        }

        @Override // android.support.v4.widget.TextViewCompat.g
        public int e(TextView textView) {
            return textView.getAutoSizeTextType();
        }

        @Override // android.support.v4.widget.TextViewCompat.g
        public void a(TextView textView, int i, int i2, int i3, int i4) throws IllegalArgumentException {
            textView.setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
        }

        @Override // android.support.v4.widget.TextViewCompat.g
        public void a(TextView textView, @NonNull int[] iArr, int i) throws IllegalArgumentException {
            textView.setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
        }

        @Override // android.support.v4.widget.TextViewCompat.g
        public int a(TextView textView) {
            return textView.getAutoSizeMaxTextSize();
        }
    }

    static {
        if (BuildCompat.isAtLeastOMR1()) {
            a = new f();
        } else if (Build.VERSION.SDK_INT >= 26) {
            a = new e();
        } else {
            a = new d();
        }
    }

    public static int getAutoSizeMaxTextSize(@NonNull TextView textView) {
        return a.a(textView);
    }

    public static int getAutoSizeMinTextSize(@NonNull TextView textView) {
        return a.b(textView);
    }

    public static int getAutoSizeStepGranularity(@NonNull TextView textView) {
        return a.c(textView);
    }

    @NonNull
    public static int[] getAutoSizeTextAvailableSizes(@NonNull TextView textView) {
        return a.d(textView);
    }

    public static int getAutoSizeTextType(@NonNull TextView textView) {
        return a.e(textView);
    }

    @NonNull
    public static Drawable[] getCompoundDrawablesRelative(@NonNull TextView textView) {
        return a.f(textView);
    }

    public static int getMaxLines(@NonNull TextView textView) {
        return a.g(textView);
    }

    public static int getMinLines(@NonNull TextView textView) {
        return a.h(textView);
    }

    public static void setAutoSizeTextTypeUniformWithConfiguration(@NonNull TextView textView, int i, int i2, int i3, int i4) throws IllegalArgumentException {
        a.a(textView, i, i2, i3, i4);
    }

    public static void setAutoSizeTextTypeUniformWithPresetSizes(@NonNull TextView textView, @NonNull int[] iArr, int i) throws IllegalArgumentException {
        a.a(textView, iArr, i);
    }

    public static void setAutoSizeTextTypeWithDefaults(@NonNull TextView textView, int i) {
        a.a(textView, i);
    }

    public static void setCompoundDrawablesRelative(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        a.a(textView, drawable, drawable2, drawable3, drawable4);
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
        a.b(textView, drawable, drawable2, drawable3, drawable4);
    }

    public static void setCustomSelectionActionModeCallback(@NonNull TextView textView, @NonNull ActionMode.Callback callback) {
        a.a(textView, callback);
    }

    public static void setTextAppearance(@NonNull TextView textView, @StyleRes int i) {
        a.b(textView, i);
    }

    public static class g {
        /* JADX WARN: Multi-variable type inference failed */
        public void a(TextView textView, int i) {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeWithDefaults(i);
            }
        }

        public void a(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            throw null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int b(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeMinTextSize();
            }
            return -1;
        }

        public void b(TextView textView, @StyleRes int i) {
            throw null;
        }

        public void b(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
            throw null;
        }

        public void b(@NonNull TextView textView, @Nullable Drawable drawable, @Nullable Drawable drawable2, @Nullable Drawable drawable3, @Nullable Drawable drawable4) {
            throw null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int c(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeStepGranularity();
            }
            return -1;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int[] d(TextView textView) {
            return textView instanceof AutoSizeableTextView ? ((AutoSizeableTextView) textView).getAutoSizeTextAvailableSizes() : new int[0];
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int e(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeTextType();
            }
            return 0;
        }

        public Drawable[] f(@NonNull TextView textView) {
            throw null;
        }

        public int g(TextView textView) {
            throw null;
        }

        public int h(TextView textView) {
            throw null;
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void a(TextView textView, int i, int i2, int i3, int i4) throws IllegalArgumentException {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithConfiguration(i, i2, i3, i4);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public void a(TextView textView, @NonNull int[] iArr, int i) throws IllegalArgumentException {
            if (textView instanceof AutoSizeableTextView) {
                ((AutoSizeableTextView) textView).setAutoSizeTextTypeUniformWithPresetSizes(iArr, i);
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public int a(TextView textView) {
            if (textView instanceof AutoSizeableTextView) {
                return ((AutoSizeableTextView) textView).getAutoSizeMaxTextSize();
            }
            return -1;
        }

        public void a(TextView textView, ActionMode.Callback callback) {
            textView.setCustomSelectionActionModeCallback(callback);
        }
    }

    public static void setCompoundDrawablesRelativeWithIntrinsicBounds(@NonNull TextView textView, @DrawableRes int i, @DrawableRes int i2, @DrawableRes int i3, @DrawableRes int i4) {
        a.b(textView, i, i2, i3, i4);
    }
}
