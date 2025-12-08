package android.databinding.adapters;

import android.util.SparseArray;
import android.view.View;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public class ListenerUtil {
    public static final SparseArray<WeakHashMap<View, WeakReference<?>>> a = new SparseArray<>();

    public static <T> T getListener(View view2, int i) {
        return (T) view2.getTag(i);
    }

    public static <T> T trackListener(View view2, T t, int i) {
        T t2 = (T) view2.getTag(i);
        view2.setTag(i, t);
        return t2;
    }
}
