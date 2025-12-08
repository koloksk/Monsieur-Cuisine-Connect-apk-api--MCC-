package android.support.v7.util;

import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.support.v7.util.TileList;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import defpackage.b7;
import defpackage.c7;
import defpackage.d7;
import defpackage.g9;

/* loaded from: classes.dex */
public class AsyncListUtil<T> {
    public final Class<T> a;
    public final int b;
    public final DataCallback<T> c;
    public final ViewCallback d;
    public final TileList<T> e;
    public final ThreadUtil$MainThreadCallback<T> f;
    public final ThreadUtil$BackgroundCallback<T> g;
    public boolean k;
    public final int[] h = new int[2];
    public final int[] i = new int[2];
    public final int[] j = new int[2];
    public int l = 0;
    public int m = 0;
    public int n = 0;
    public int o = 0;
    public final SparseIntArray p = new SparseIntArray();
    public final ThreadUtil$MainThreadCallback<T> q = new a();
    public final ThreadUtil$BackgroundCallback<T> r = new b();

    public static abstract class DataCallback<T> {
        @WorkerThread
        public abstract void fillData(T[] tArr, int i, int i2);

        @WorkerThread
        public int getMaxCachedTiles() {
            return 10;
        }

        @WorkerThread
        public void recycleData(T[] tArr, int i) {
        }

        @WorkerThread
        public abstract int refreshData();
    }

    public static abstract class ViewCallback {
        public static final int HINT_SCROLL_ASC = 2;
        public static final int HINT_SCROLL_DESC = 1;
        public static final int HINT_SCROLL_NONE = 0;

        @UiThread
        public void extendRangeInto(int[] iArr, int[] iArr2, int i) {
            int i2 = (iArr[1] - iArr[0]) + 1;
            int i3 = i2 / 2;
            iArr2[0] = iArr[0] - (i == 1 ? i2 : i3);
            int i4 = iArr[1];
            if (i != 2) {
                i2 = i3;
            }
            iArr2[1] = i4 + i2;
        }

        @UiThread
        public abstract void getItemRangeInto(int[] iArr);

        @UiThread
        public abstract void onDataRefresh();

        @UiThread
        public abstract void onItemLoaded(int i);
    }

    public class a implements ThreadUtil$MainThreadCallback<T> {
        public a() {
        }

        @Override // android.support.v7.util.ThreadUtil$MainThreadCallback
        public void addTile(int i, TileList.Tile<T> tile) {
            TileList.Tile<T> tile2;
            int i2 = 0;
            if (!(i == AsyncListUtil.this.o)) {
                AsyncListUtil.this.g.recycleTile(tile);
                return;
            }
            TileList<T> tileList = AsyncListUtil.this.e;
            int iIndexOfKey = tileList.b.indexOfKey(tile.mStartPosition);
            if (iIndexOfKey < 0) {
                tileList.b.put(tile.mStartPosition, tile);
                tile2 = null;
            } else {
                TileList.Tile<T> tileValueAt = tileList.b.valueAt(iIndexOfKey);
                tileList.b.setValueAt(iIndexOfKey, tile);
                if (tileList.c == tileValueAt) {
                    tileList.c = tile;
                }
                tile2 = tileValueAt;
            }
            if (tile2 != null) {
                StringBuilder sbA = g9.a("duplicate tile @");
                sbA.append(tile2.mStartPosition);
                Log.e("AsyncListUtil", sbA.toString());
                AsyncListUtil.this.g.recycleTile(tile2);
            }
            int i3 = tile.mStartPosition + tile.mItemCount;
            while (i2 < AsyncListUtil.this.p.size()) {
                int iKeyAt = AsyncListUtil.this.p.keyAt(i2);
                if (tile.mStartPosition > iKeyAt || iKeyAt >= i3) {
                    i2++;
                } else {
                    AsyncListUtil.this.p.removeAt(i2);
                    AsyncListUtil.this.d.onItemLoaded(iKeyAt);
                }
            }
        }

        @Override // android.support.v7.util.ThreadUtil$MainThreadCallback
        public void removeTile(int i, int i2) {
            if (i == AsyncListUtil.this.o) {
                TileList<T> tileList = AsyncListUtil.this.e;
                TileList.Tile<T> tile = tileList.b.get(i2);
                if (tileList.c == tile) {
                    tileList.c = null;
                }
                tileList.b.delete(i2);
                if (tile != null) {
                    AsyncListUtil.this.g.recycleTile(tile);
                    return;
                }
                Log.e("AsyncListUtil", "tile not found @" + i2);
            }
        }

        @Override // android.support.v7.util.ThreadUtil$MainThreadCallback
        public void updateItemCount(int i, int i2) {
            if (i == AsyncListUtil.this.o) {
                AsyncListUtil asyncListUtil = AsyncListUtil.this;
                asyncListUtil.m = i2;
                asyncListUtil.d.onDataRefresh();
                AsyncListUtil asyncListUtil2 = AsyncListUtil.this;
                asyncListUtil2.n = asyncListUtil2.o;
                for (int i3 = 0; i3 < AsyncListUtil.this.e.b.size(); i3++) {
                    AsyncListUtil asyncListUtil3 = AsyncListUtil.this;
                    asyncListUtil3.g.recycleTile(asyncListUtil3.e.b.valueAt(i3));
                }
                AsyncListUtil.this.e.b.clear();
                AsyncListUtil asyncListUtil4 = AsyncListUtil.this;
                asyncListUtil4.k = false;
                asyncListUtil4.a();
            }
        }
    }

    public class b implements ThreadUtil$BackgroundCallback<T> {
        public TileList.Tile<T> a;
        public final SparseBooleanArray b = new SparseBooleanArray();
        public int c;
        public int d;
        public int e;
        public int f;

        public b() {
        }

        public final void a(int i, int i2, int i3, boolean z) {
            int i4 = i;
            while (i4 <= i2) {
                AsyncListUtil.this.g.loadTile(z ? (i2 + i) - i4 : i4, i3);
                i4 += AsyncListUtil.this.b;
            }
        }

        @Override // android.support.v7.util.ThreadUtil$BackgroundCallback
        public void loadTile(int i, int i2) {
            if (this.b.get(i)) {
                return;
            }
            TileList.Tile<T> tile = this.a;
            if (tile != null) {
                this.a = tile.a;
            } else {
                AsyncListUtil asyncListUtil = AsyncListUtil.this;
                tile = new TileList.Tile<>(asyncListUtil.a, asyncListUtil.b);
            }
            tile.mStartPosition = i;
            int iMin = Math.min(AsyncListUtil.this.b, this.d - i);
            tile.mItemCount = iMin;
            AsyncListUtil.this.c.fillData(tile.mItems, tile.mStartPosition, iMin);
            int maxCachedTiles = AsyncListUtil.this.c.getMaxCachedTiles();
            while (this.b.size() >= maxCachedTiles) {
                int iKeyAt = this.b.keyAt(0);
                SparseBooleanArray sparseBooleanArray = this.b;
                int iKeyAt2 = sparseBooleanArray.keyAt(sparseBooleanArray.size() - 1);
                int i3 = this.e - iKeyAt;
                int i4 = iKeyAt2 - this.f;
                if (i3 > 0 && (i3 >= i4 || i2 == 2)) {
                    this.b.delete(iKeyAt);
                    AsyncListUtil.this.f.removeTile(this.c, iKeyAt);
                } else {
                    if (i4 <= 0 || (i3 >= i4 && i2 != 1)) {
                        break;
                    }
                    this.b.delete(iKeyAt2);
                    AsyncListUtil.this.f.removeTile(this.c, iKeyAt2);
                }
            }
            this.b.put(tile.mStartPosition, true);
            AsyncListUtil.this.f.addTile(this.c, tile);
        }

        @Override // android.support.v7.util.ThreadUtil$BackgroundCallback
        public void recycleTile(TileList.Tile<T> tile) {
            AsyncListUtil.this.c.recycleData(tile.mItems, tile.mItemCount);
            tile.a = this.a;
            this.a = tile;
        }

        @Override // android.support.v7.util.ThreadUtil$BackgroundCallback
        public void refresh(int i) {
            this.c = i;
            this.b.clear();
            int iRefreshData = AsyncListUtil.this.c.refreshData();
            this.d = iRefreshData;
            AsyncListUtil.this.f.updateItemCount(this.c, iRefreshData);
        }

        @Override // android.support.v7.util.ThreadUtil$BackgroundCallback
        public void updateRange(int i, int i2, int i3, int i4, int i5) {
            if (i > i2) {
                return;
            }
            int i6 = AsyncListUtil.this.b;
            int i7 = i - (i % i6);
            int i8 = i2 - (i2 % i6);
            int i9 = i3 - (i3 % i6);
            this.e = i9;
            int i10 = i4 - (i4 % i6);
            this.f = i10;
            if (i5 == 1) {
                a(i9, i8, i5, true);
                a(i8 + AsyncListUtil.this.b, this.f, i5, false);
            } else {
                a(i7, i10, i5, false);
                a(this.e, i7 - AsyncListUtil.this.b, i5, true);
            }
        }
    }

    public AsyncListUtil(Class<T> cls, int i, DataCallback<T> dataCallback, ViewCallback viewCallback) {
        this.a = cls;
        this.b = i;
        this.c = dataCallback;
        this.d = viewCallback;
        this.e = new TileList<>(i);
        d7 d7Var = new d7();
        this.f = new b7(d7Var, this.q);
        this.g = new c7(d7Var, this.r);
        refresh();
    }

    public void a() {
        this.d.getItemRangeInto(this.h);
        int[] iArr = this.h;
        if (iArr[0] > iArr[1] || iArr[0] < 0 || iArr[1] >= this.m) {
            return;
        }
        if (this.k) {
            int i = iArr[0];
            int[] iArr2 = this.i;
            if (i > iArr2[1] || iArr2[0] > iArr[1]) {
                this.l = 0;
            } else if (iArr[0] < iArr2[0]) {
                this.l = 1;
            } else if (iArr[0] > iArr2[0]) {
                this.l = 2;
            }
        } else {
            this.l = 0;
        }
        int[] iArr3 = this.i;
        int[] iArr4 = this.h;
        iArr3[0] = iArr4[0];
        iArr3[1] = iArr4[1];
        this.d.extendRangeInto(iArr4, this.j, this.l);
        int[] iArr5 = this.j;
        iArr5[0] = Math.min(this.h[0], Math.max(iArr5[0], 0));
        int[] iArr6 = this.j;
        iArr6[1] = Math.max(this.h[1], Math.min(iArr6[1], this.m - 1));
        ThreadUtil$BackgroundCallback<T> threadUtil$BackgroundCallback = this.g;
        int[] iArr7 = this.h;
        int i2 = iArr7[0];
        int i3 = iArr7[1];
        int[] iArr8 = this.j;
        threadUtil$BackgroundCallback.updateRange(i2, i3, iArr8[0], iArr8[1], this.l);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x001c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public T getItem(int r6) {
        /*
            r5 = this;
            if (r6 < 0) goto L52
            int r0 = r5.m
            if (r6 >= r0) goto L52
            android.support.v7.util.TileList<T> r0 = r5.e
            android.support.v7.util.TileList$Tile<T> r1 = r0.c
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L1c
            int r4 = r1.mStartPosition
            if (r4 > r6) goto L19
            int r1 = r1.mItemCount
            int r4 = r4 + r1
            if (r6 >= r4) goto L19
            r1 = r2
            goto L1a
        L19:
            r1 = r3
        L1a:
            if (r1 != 0) goto L36
        L1c:
            int r1 = r0.a
            int r1 = r6 % r1
            int r1 = r6 - r1
            android.util.SparseArray<android.support.v7.util.TileList$Tile<T>> r4 = r0.b
            int r1 = r4.indexOfKey(r1)
            if (r1 >= 0) goto L2c
            r0 = 0
            goto L40
        L2c:
            android.util.SparseArray<android.support.v7.util.TileList$Tile<T>> r4 = r0.b
            java.lang.Object r1 = r4.valueAt(r1)
            android.support.v7.util.TileList$Tile r1 = (android.support.v7.util.TileList.Tile) r1
            r0.c = r1
        L36:
            android.support.v7.util.TileList$Tile<T> r0 = r0.c
            T[] r1 = r0.mItems
            int r0 = r0.mStartPosition
            int r0 = r6 - r0
            r0 = r1[r0]
        L40:
            if (r0 != 0) goto L51
            int r1 = r5.o
            int r4 = r5.n
            if (r1 == r4) goto L49
            goto L4a
        L49:
            r2 = r3
        L4a:
            if (r2 != 0) goto L51
            android.util.SparseIntArray r1 = r5.p
            r1.put(r6, r3)
        L51:
            return r0
        L52:
            java.lang.IndexOutOfBoundsException r0 = new java.lang.IndexOutOfBoundsException
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            r1.append(r6)
            java.lang.String r6 = " is not within 0 and "
            r1.append(r6)
            int r6 = r5.m
            r1.append(r6)
            java.lang.String r6 = r1.toString()
            r0.<init>(r6)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.util.AsyncListUtil.getItem(int):java.lang.Object");
    }

    public int getItemCount() {
        return this.m;
    }

    public void onRangeChanged() {
        if (this.o != this.n) {
            return;
        }
        a();
        this.k = true;
    }

    public void refresh() {
        this.p.clear();
        ThreadUtil$BackgroundCallback<T> threadUtil$BackgroundCallback = this.g;
        int i = this.o + 1;
        this.o = i;
        threadUtil$BackgroundCallback.refresh(i);
    }
}
