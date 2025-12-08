package butterknife;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.support.annotation.CheckResult;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.UiThread;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.util.Property;
import android.view.View;
import defpackage.g9;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class ButterKnife {
    public static boolean a = false;

    @VisibleForTesting
    public static final Map<Class<?>, Constructor<? extends Unbinder>> b = new LinkedHashMap();

    public interface Action<T extends View> {
        @UiThread
        void apply(@NonNull T t, int i);
    }

    public interface Setter<T extends View, V> {
        @UiThread
        void set(@NonNull T t, V v, int i);
    }

    public ButterKnife() {
        throw new AssertionError("No instances.");
    }

    public static Unbinder a(@NonNull Object obj, @NonNull View view2) throws NoSuchMethodException, SecurityException {
        Class<?> cls = obj.getClass();
        if (a) {
            StringBuilder sbA = g9.a("Looking up binding for ");
            sbA.append(cls.getName());
            Log.d("ButterKnife", sbA.toString());
        }
        Constructor<? extends Unbinder> constructorA = a(cls);
        if (constructorA == null) {
            return Unbinder.EMPTY;
        }
        try {
            return constructorA.newInstance(obj, view2);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to invoke " + constructorA, e);
        } catch (InstantiationException e2) {
            throw new RuntimeException("Unable to invoke " + constructorA, e2);
        } catch (InvocationTargetException e3) {
            Throwable cause = e3.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unable to create binding instance.", cause);
        }
    }

    @UiThread
    @SafeVarargs
    public static <T extends View> void apply(@NonNull List<T> list, @NonNull Action<? super T>... actionArr) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            for (Action<? super T> action : actionArr) {
                action.apply(list.get(i), i);
            }
        }
    }

    @UiThread
    @NonNull
    public static Unbinder bind(@NonNull Activity activity2) {
        return a(activity2, activity2.getWindow().getDecorView());
    }

    @CheckResult
    @Deprecated
    public static <T extends View> T findById(@NonNull View view2, @IdRes int i) {
        return (T) view2.findViewById(i);
    }

    public static void setDebug(boolean z) {
        a = z;
    }

    @CheckResult
    @Deprecated
    public static <T extends View> T findById(@NonNull Activity activity2, @IdRes int i) {
        return (T) activity2.findViewById(i);
    }

    @UiThread
    @NonNull
    public static Unbinder bind(@NonNull View view2) {
        return a(view2, view2);
    }

    @CheckResult
    @Deprecated
    public static <T extends View> T findById(@NonNull Dialog dialog, @IdRes int i) {
        return (T) dialog.findViewById(i);
    }

    @UiThread
    @SafeVarargs
    public static <T extends View> void apply(@NonNull T[] tArr, @NonNull Action<? super T>... actionArr) {
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            for (Action<? super T> action : actionArr) {
                action.apply(tArr[i], i);
            }
        }
    }

    @UiThread
    @NonNull
    public static Unbinder bind(@NonNull Dialog dialog) {
        return a(dialog, dialog.getWindow().getDecorView());
    }

    @UiThread
    @NonNull
    public static Unbinder bind(@NonNull Object obj, @NonNull Activity activity2) {
        return a(obj, activity2.getWindow().getDecorView());
    }

    @UiThread
    public static <T extends View> void apply(@NonNull List<T> list, @NonNull Action<? super T> action) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            action.apply(list.get(i), i);
        }
    }

    @UiThread
    @NonNull
    public static Unbinder bind(@NonNull Object obj, @NonNull View view2) {
        return a(obj, view2);
    }

    @UiThread
    public static <T extends View> void apply(@NonNull T[] tArr, @NonNull Action<? super T> action) {
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            action.apply(tArr[i], i);
        }
    }

    @UiThread
    @NonNull
    public static Unbinder bind(@NonNull Object obj, @NonNull Dialog dialog) {
        return a(obj, dialog.getWindow().getDecorView());
    }

    @UiThread
    @SafeVarargs
    public static <T extends View> void apply(@NonNull T t, @NonNull Action<? super T>... actionArr) {
        for (Action<? super T> action : actionArr) {
            action.apply(t, 0);
        }
    }

    @UiThread
    public static <T extends View> void apply(@NonNull T t, @NonNull Action<? super T> action) {
        action.apply(t, 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    @UiThread
    @CheckResult
    @Nullable
    public static Constructor<? extends Unbinder> a(Class<?> cls) throws NoSuchMethodException, SecurityException {
        Constructor<? extends Unbinder> constructorA;
        Constructor<? extends Unbinder> constructor = b.get(cls);
        if (constructor != null) {
            if (a) {
                Log.d("ButterKnife", "HIT: Cached in binding map.");
            }
            return constructor;
        }
        String name = cls.getName();
        if (!name.startsWith("android.") && !name.startsWith("java.")) {
            try {
                constructorA = cls.getClassLoader().loadClass(name + "_ViewBinding").getConstructor(cls, View.class);
                if (a) {
                    Log.d("ButterKnife", "HIT: Loaded binding class and constructor.");
                }
            } catch (ClassNotFoundException unused) {
                if (a) {
                    StringBuilder sbA = g9.a("Not found. Trying superclass ");
                    sbA.append(cls.getSuperclass().getName());
                    Log.d("ButterKnife", sbA.toString());
                }
                constructorA = a(cls.getSuperclass());
            } catch (NoSuchMethodException e) {
                throw new RuntimeException(g9.b("Unable to find binding constructor for ", name), e);
            }
            b.put(cls, constructorA);
            return constructorA;
        }
        if (!a) {
            return null;
        }
        Log.d("ButterKnife", "MISS: Reached framework class. Abandoning search.");
        return null;
    }

    @UiThread
    public static <T extends View, V> void apply(@NonNull List<T> list, @NonNull Setter<? super T, V> setter, V v) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            setter.set(list.get(i), v, i);
        }
    }

    @UiThread
    public static <T extends View, V> void apply(@NonNull T[] tArr, @NonNull Setter<? super T, V> setter, V v) {
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            setter.set(tArr[i], v, i);
        }
    }

    @UiThread
    public static <T extends View, V> void apply(@NonNull T t, @NonNull Setter<? super T, V> setter, V v) {
        setter.set(t, v, 0);
    }

    @UiThread
    @RequiresApi(14)
    @TargetApi(14)
    public static <T extends View, V> void apply(@NonNull List<T> list, @NonNull Property<? super T, V> property, V v) {
        int size = list.size();
        for (int i = 0; i < size; i++) {
            property.set(list.get(i), v);
        }
    }

    @UiThread
    @RequiresApi(14)
    @TargetApi(14)
    public static <T extends View, V> void apply(@NonNull T[] tArr, @NonNull Property<? super T, V> property, V v) {
        for (T t : tArr) {
            property.set(t, v);
        }
    }

    @UiThread
    @RequiresApi(14)
    @TargetApi(14)
    public static <T extends View, V> void apply(@NonNull T t, @NonNull Property<? super T, V> property, V v) {
        property.set(t, v);
    }
}
