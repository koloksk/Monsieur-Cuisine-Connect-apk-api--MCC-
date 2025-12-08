package android.databinding;

import android.R;
import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public class DataBindingUtil {
    public static DataBinderMapper a = new DataBinderMapperImpl();
    public static DataBindingComponent b = null;

    public static <T extends ViewDataBinding> T a(DataBindingComponent dataBindingComponent, View view2, int i) {
        return (T) a.getDataBinder(dataBindingComponent, view2, i);
    }

    @Nullable
    public static <T extends ViewDataBinding> T bind(@NonNull View view2) {
        return (T) bind(view2, b);
    }

    @Nullable
    public static String convertBrIdToString(int i) {
        return a.convertBrIdToString(i);
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0038  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x004c A[SYNTHETIC] */
    @android.support.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static <T extends android.databinding.ViewDataBinding> T findBinding(@android.support.annotation.NonNull android.view.View r9) {
        /*
        L0:
            r0 = 0
            if (r9 == 0) goto L5a
            android.databinding.ViewDataBinding r1 = android.databinding.ViewDataBinding.a(r9)
            if (r1 == 0) goto La
            return r1
        La:
            java.lang.Object r1 = r9.getTag()
            boolean r2 = r1 instanceof java.lang.String
            if (r2 == 0) goto L4d
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String r2 = "layout"
            boolean r2 = r1.startsWith(r2)
            if (r2 == 0) goto L4d
            java.lang.String r2 = "_0"
            boolean r2 = r1.endsWith(r2)
            if (r2 == 0) goto L4d
            r2 = 6
            char r2 = r1.charAt(r2)
            r3 = 7
            r4 = 47
            int r3 = r1.indexOf(r4, r3)
            r5 = 1
            r6 = -1
            r7 = 0
            if (r2 != r4) goto L3b
            if (r3 != r6) goto L38
            goto L39
        L38:
            r5 = r7
        L39:
            r7 = r5
            goto L4a
        L3b:
            r8 = 45
            if (r2 != r8) goto L4a
            if (r3 == r6) goto L4a
            int r3 = r3 + 1
            int r1 = r1.indexOf(r4, r3)
            if (r1 != r6) goto L38
            goto L39
        L4a:
            if (r7 == 0) goto L4d
            return r0
        L4d:
            android.view.ViewParent r9 = r9.getParent()
            boolean r1 = r9 instanceof android.view.View
            if (r1 == 0) goto L58
            android.view.View r9 = (android.view.View) r9
            goto L0
        L58:
            r9 = r0
            goto L0
        L5a:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.databinding.DataBindingUtil.findBinding(android.view.View):android.databinding.ViewDataBinding");
    }

    @Nullable
    public static <T extends ViewDataBinding> T getBinding(@NonNull View view2) {
        return (T) ViewDataBinding.a(view2);
    }

    @Nullable
    public static DataBindingComponent getDefaultComponent() {
        return b;
    }

    public static <T extends ViewDataBinding> T inflate(@NonNull LayoutInflater layoutInflater, int i, @Nullable ViewGroup viewGroup, boolean z) {
        return (T) inflate(layoutInflater, i, viewGroup, z, b);
    }

    public static <T extends ViewDataBinding> T setContentView(@NonNull Activity activity2, int i) {
        return (T) setContentView(activity2, i, b);
    }

    public static void setDefaultComponent(@Nullable DataBindingComponent dataBindingComponent) {
        b = dataBindingComponent;
    }

    public static <T extends ViewDataBinding> T a(DataBindingComponent dataBindingComponent, ViewGroup viewGroup, int i, int i2) {
        int childCount = viewGroup.getChildCount();
        int i3 = childCount - i;
        if (i3 == 1) {
            return (T) a(dataBindingComponent, viewGroup.getChildAt(childCount - 1), i2);
        }
        View[] viewArr = new View[i3];
        for (int i4 = 0; i4 < i3; i4++) {
            viewArr[i4] = viewGroup.getChildAt(i4 + i);
        }
        return (T) a.getDataBinder(dataBindingComponent, viewArr, i2);
    }

    @Nullable
    public static <T extends ViewDataBinding> T bind(@NonNull View view2, DataBindingComponent dataBindingComponent) {
        T t = (T) getBinding(view2);
        if (t != null) {
            return t;
        }
        Object tag = view2.getTag();
        if (!(tag instanceof String)) {
            throw new IllegalArgumentException("View is not a binding layout");
        }
        int layoutId = a.getLayoutId((String) tag);
        if (layoutId != 0) {
            return (T) a.getDataBinder(dataBindingComponent, view2, layoutId);
        }
        throw new IllegalArgumentException("View is not a binding layout. Tag: " + tag);
    }

    public static <T extends ViewDataBinding> T inflate(@NonNull LayoutInflater layoutInflater, int i, @Nullable ViewGroup viewGroup, boolean z, @Nullable DataBindingComponent dataBindingComponent) {
        boolean z2 = viewGroup != null && z;
        return z2 ? (T) a(dataBindingComponent, viewGroup, z2 ? viewGroup.getChildCount() : 0, i) : (T) a(dataBindingComponent, layoutInflater.inflate(i, viewGroup, z), i);
    }

    public static <T extends ViewDataBinding> T setContentView(@NonNull Activity activity2, int i, @Nullable DataBindingComponent dataBindingComponent) {
        activity2.setContentView(i);
        return (T) a(dataBindingComponent, (ViewGroup) activity2.getWindow().getDecorView().findViewById(R.id.content), 0, i);
    }
}
