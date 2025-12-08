package defpackage;

import android.support.annotation.RequiresApi;
import android.support.transition.TransitionValues;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.util.SparseArray;
import android.view.View;

@RequiresApi(14)
/* loaded from: classes.dex */
public class p3 {
    public final ArrayMap<View, TransitionValues> a = new ArrayMap<>();
    public final SparseArray<View> b = new SparseArray<>();
    public final LongSparseArray<View> c = new LongSparseArray<>();
    public final ArrayMap<String, View> d = new ArrayMap<>();
}
