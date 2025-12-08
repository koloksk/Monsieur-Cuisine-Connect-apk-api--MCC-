package android.support.v7.util;

import android.util.SparseArray;
import java.lang.reflect.Array;

/* loaded from: classes.dex */
public class TileList<T> {
    public final int a;
    public final SparseArray<Tile<T>> b = new SparseArray<>(10);
    public Tile<T> c;

    public static class Tile<T> {
        public Tile<T> a;
        public int mItemCount;
        public final T[] mItems;
        public int mStartPosition;

        public Tile(Class<T> cls, int i) {
            this.mItems = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i));
        }
    }

    public TileList(int i) {
        this.a = i;
    }
}
