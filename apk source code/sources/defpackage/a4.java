package defpackage;

import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.util.Property;
import android.view.View;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class a4 {
    public static Field b;
    public static boolean c;
    public static final g4 a = new f4();
    public static final Property<View, Float> d = new a(Float.class, "translationAlpha");
    public static final Property<View, Rect> e = new b(Rect.class, "clipBounds");

    public static class a extends Property<View, Float> {
        public a(Class cls, String str) {
            super(cls, str);
        }

        @Override // android.util.Property
        public Float get(View view2) {
            return Float.valueOf(a4.b(view2));
        }

        @Override // android.util.Property
        public void set(View view2, Float f) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            a4.a(view2, f.floatValue());
        }
    }

    public static class b extends Property<View, Rect> {
        public b(Class cls, String str) {
            super(cls, str);
        }

        @Override // android.util.Property
        public Rect get(View view2) {
            return ViewCompat.getClipBounds(view2);
        }

        @Override // android.util.Property
        public void set(View view2, Rect rect) {
            ViewCompat.setClipBounds(view2, rect);
        }
    }

    public static z3 a(@NonNull View view2) {
        return new y3(view2);
    }

    public static float b(@NonNull View view2) {
        return ((d4) a).a(view2);
    }

    public static i4 c(@NonNull View view2) {
        return new h4(view2);
    }

    public static void d(@NonNull View view2) {
    }

    public static void a(@NonNull View view2, int i, int i2, int i3, int i4) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (!f4.l) {
            try {
                Method declaredMethod = View.class.getDeclaredMethod("setLeftTopRightBottom", Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE);
                f4.k = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("ViewUtilsApi22", "Failed to retrieve setLeftTopRightBottom method", e2);
            }
            f4.l = true;
        }
        Method method = f4.k;
        if (method != null) {
            try {
                method.invoke(view2, Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4));
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e3) {
                throw new RuntimeException(e3.getCause());
            }
        }
    }

    public static void a(@NonNull View view2, float f) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (!d4.b) {
            try {
                Method declaredMethod = View.class.getDeclaredMethod("setTransitionAlpha", Float.TYPE);
                d4.a = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("ViewUtilsApi19", "Failed to retrieve setTransitionAlpha method", e2);
            }
            d4.b = true;
        }
        Method method = d4.a;
        if (method != null) {
            try {
                method.invoke(view2, Float.valueOf(f));
                return;
            } catch (IllegalAccessException unused) {
                return;
            } catch (InvocationTargetException e3) {
                throw new RuntimeException(e3.getCause());
            }
        }
        view2.setAlpha(f);
    }

    public static void a(@NonNull View view2, @Nullable Matrix matrix) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (!e4.j) {
            try {
                Method declaredMethod = View.class.getDeclaredMethod("setAnimationMatrix", Matrix.class);
                e4.i = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException e2) {
                Log.i("ViewUtilsApi21", "Failed to retrieve setAnimationMatrix method", e2);
            }
            e4.j = true;
        }
        Method method = e4.i;
        if (method != null) {
            try {
                method.invoke(view2, matrix);
            } catch (IllegalAccessException e3) {
                throw new RuntimeException(e3.getCause());
            } catch (InvocationTargetException unused) {
            }
        }
    }

    public static void a(@NonNull View view2, int i) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        if (!c) {
            try {
                Field declaredField = View.class.getDeclaredField("mViewFlags");
                b = declaredField;
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException unused) {
                Log.i("ViewUtils", "fetchViewFlagsField: ");
            }
            c = true;
        }
        Field field = b;
        if (field != null) {
            try {
                b.setInt(view2, i | (field.getInt(view2) & (-13)));
            } catch (IllegalAccessException unused2) {
            }
        }
    }
}
