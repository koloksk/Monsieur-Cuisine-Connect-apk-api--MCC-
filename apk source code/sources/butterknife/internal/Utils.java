package butterknife.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.DimenRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.UiThread;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.TypedValue;
import android.view.View;
import defpackage.f9;
import defpackage.g9;
import java.lang.reflect.Array;
import java.util.List;

/* loaded from: classes.dex */
public final class Utils {
    public static final TypedValue a = new TypedValue();

    public Utils() {
        throw new AssertionError("No instances.");
    }

    public static <T> T[] a(T[] tArr) {
        int length = tArr.length;
        int i = 0;
        for (T t : tArr) {
            if (t != null) {
                tArr[i] = t;
                i++;
            }
        }
        if (i == length) {
            return tArr;
        }
        T[] tArr2 = (T[]) ((Object[]) Array.newInstance(tArr.getClass().getComponentType(), i));
        System.arraycopy(tArr, 0, tArr2, 0, i);
        return tArr2;
    }

    @SafeVarargs
    public static <T> T[] arrayOf(T... tArr) {
        return (T[]) a(tArr);
    }

    public static <T> T castParam(Object obj, String str, int i, String str2, int i2, Class<T> cls) {
        try {
            return cls.cast(obj);
        } catch (ClassCastException e) {
            StringBuilder sbA = g9.a("Parameter #");
            sbA.append(i + 1);
            sbA.append(" of method '");
            sbA.append(str);
            sbA.append("' was of the wrong type for parameter #");
            sbA.append(i2 + 1);
            sbA.append(" of method '");
            sbA.append(str2);
            sbA.append("'. See cause for more info.");
            throw new IllegalStateException(sbA.toString(), e);
        }
    }

    public static <T> T castView(View view2, @IdRes int i, String str, Class<T> cls) {
        try {
            return cls.cast(view2);
        } catch (ClassCastException e) {
            throw new IllegalStateException("View '" + a(view2, i) + "' with ID " + i + " for " + str + " was of the wrong type. See cause for more info.", e);
        }
    }

    public static <T> T findOptionalViewAsType(View view2, @IdRes int i, String str, Class<T> cls) {
        return (T) castView(view2.findViewById(i), i, str, cls);
    }

    public static View findRequiredView(View view2, @IdRes int i, String str) {
        View viewFindViewById = view2.findViewById(i);
        if (viewFindViewById != null) {
            return viewFindViewById;
        }
        throw new IllegalStateException("Required view '" + a(view2, i) + "' with ID " + i + " for " + str + " was not found. If this view is optional add '@Nullable' (fields) or '@Optional' (methods) annotation.");
    }

    public static <T> T findRequiredViewAsType(View view2, @IdRes int i, String str, Class<T> cls) {
        return (T) castView(findRequiredView(view2, i, str), i, str, cls);
    }

    @UiThread
    public static float getFloat(Context context, @DimenRes int i) throws Resources.NotFoundException {
        TypedValue typedValue = a;
        context.getResources().getValue(i, typedValue, true);
        if (typedValue.type == 4) {
            return typedValue.getFloat();
        }
        StringBuilder sbA = g9.a("Resource ID #0x");
        sbA.append(Integer.toHexString(i));
        sbA.append(" type #0x");
        sbA.append(Integer.toHexString(typedValue.type));
        sbA.append(" is not valid");
        throw new Resources.NotFoundException(sbA.toString());
    }

    @UiThread
    public static Drawable getTintedDrawable(Context context, @DrawableRes int i, @AttrRes int i2) {
        if (context.getTheme().resolveAttribute(i2, a, true)) {
            Drawable drawableWrap = DrawableCompat.wrap(ContextCompat.getDrawable(context, i).mutate());
            DrawableCompat.setTint(drawableWrap, ContextCompat.getColor(context, a.resourceId));
            return drawableWrap;
        }
        StringBuilder sbA = g9.a("Required tint color attribute with name ");
        sbA.append(context.getResources().getResourceEntryName(i2));
        sbA.append(" and attribute ID ");
        sbA.append(i2);
        sbA.append(" was not found.");
        throw new Resources.NotFoundException(sbA.toString());
    }

    @SafeVarargs
    public static <T> List<T> listOf(T... tArr) {
        return new f9(a(tArr));
    }

    public static String a(View view2, @IdRes int i) {
        return view2.isInEditMode() ? "<unavailable while editing>" : view2.getContext().getResources().getResourceEntryName(i);
    }
}
