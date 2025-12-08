package android.support.v7.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import defpackage.g9;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;

/* loaded from: classes.dex */
public class SortedList<T> {
    public static final int INVALID_POSITION = -1;
    public T[] a;
    public T[] b;
    public int c;
    public int d;
    public int e;
    public Callback f;
    public BatchedCallback g;
    public int h;
    public final Class<T> i;

    public static class BatchedCallback<T2> extends Callback<T2> {
        public final Callback<T2> a;
        public final BatchingListUpdateCallback b;

        public BatchedCallback(Callback<T2> callback) {
            this.a = callback;
            this.b = new BatchingListUpdateCallback(callback);
        }

        @Override // android.support.v7.util.SortedList.Callback
        public boolean areContentsTheSame(T2 t2, T2 t22) {
            return this.a.areContentsTheSame(t2, t22);
        }

        @Override // android.support.v7.util.SortedList.Callback
        public boolean areItemsTheSame(T2 t2, T2 t22) {
            return this.a.areItemsTheSame(t2, t22);
        }

        @Override // android.support.v7.util.SortedList.Callback, java.util.Comparator
        public int compare(T2 t2, T2 t22) {
            return this.a.compare(t2, t22);
        }

        public void dispatchLastEvent() {
            this.b.dispatchLastEvent();
        }

        @Override // android.support.v7.util.SortedList.Callback
        @Nullable
        public Object getChangePayload(T2 t2, T2 t22) {
            return this.a.getChangePayload(t2, t22);
        }

        @Override // android.support.v7.util.SortedList.Callback
        public void onChanged(int i, int i2) {
            this.b.onChanged(i, i2, null);
        }

        @Override // android.support.v7.util.ListUpdateCallback
        public void onInserted(int i, int i2) {
            this.b.onInserted(i, i2);
        }

        @Override // android.support.v7.util.ListUpdateCallback
        public void onMoved(int i, int i2) {
            this.b.onMoved(i, i2);
        }

        @Override // android.support.v7.util.ListUpdateCallback
        public void onRemoved(int i, int i2) {
            this.b.onRemoved(i, i2);
        }

        @Override // android.support.v7.util.SortedList.Callback, android.support.v7.util.ListUpdateCallback
        public void onChanged(int i, int i2, Object obj) {
            this.b.onChanged(i, i2, obj);
        }
    }

    public static abstract class Callback<T2> implements Comparator<T2>, ListUpdateCallback {
        public abstract boolean areContentsTheSame(T2 t2, T2 t22);

        public abstract boolean areItemsTheSame(T2 t2, T2 t22);

        @Override // java.util.Comparator
        public abstract int compare(T2 t2, T2 t22);

        @Nullable
        public Object getChangePayload(T2 t2, T2 t22) {
            return null;
        }

        public abstract void onChanged(int i, int i2);

        public void onChanged(int i, int i2, Object obj) {
            onChanged(i, i2);
        }
    }

    public SortedList(Class<T> cls, Callback<T> callback) {
        this(cls, callback, 10);
    }

    public final void a(T[] tArr) {
        if (tArr.length < 1) {
            return;
        }
        int iC = c(tArr);
        int i = 0;
        if (this.h == 0) {
            this.a = tArr;
            this.h = iC;
            this.f.onInserted(0, iC);
            return;
        }
        boolean z = !(this.f instanceof BatchedCallback);
        if (z) {
            beginBatchedUpdates();
        }
        this.b = this.a;
        this.c = 0;
        int i2 = this.h;
        this.d = i2;
        this.a = (T[]) ((Object[]) Array.newInstance((Class<?>) this.i, i2 + iC + 10));
        this.e = 0;
        while (true) {
            if (this.c >= this.d && i >= iC) {
                break;
            }
            int i3 = this.c;
            int i4 = this.d;
            if (i3 == i4) {
                int i5 = iC - i;
                System.arraycopy(tArr, i, this.a, this.e, i5);
                int i6 = this.e + i5;
                this.e = i6;
                this.h += i5;
                this.f.onInserted(i6 - i5, i5);
                break;
            }
            if (i == iC) {
                int i7 = i4 - i3;
                System.arraycopy(this.b, i3, this.a, this.e, i7);
                this.e += i7;
                break;
            }
            T t = this.b[i3];
            T t2 = tArr[i];
            int iCompare = this.f.compare(t, t2);
            if (iCompare > 0) {
                T[] tArr2 = this.a;
                int i8 = this.e;
                int i9 = i8 + 1;
                this.e = i9;
                tArr2[i8] = t2;
                this.h++;
                i++;
                this.f.onInserted(i9 - 1, 1);
            } else if (iCompare == 0 && this.f.areItemsTheSame(t, t2)) {
                T[] tArr3 = this.a;
                int i10 = this.e;
                this.e = i10 + 1;
                tArr3[i10] = t2;
                i++;
                this.c++;
                if (!this.f.areContentsTheSame(t, t2)) {
                    Callback callback = this.f;
                    callback.onChanged(this.e - 1, 1, callback.getChangePayload(t, t2));
                }
            } else {
                T[] tArr4 = this.a;
                int i11 = this.e;
                this.e = i11 + 1;
                tArr4[i11] = t;
                this.c++;
            }
        }
        this.b = null;
        if (z) {
            endBatchedUpdates();
        }
    }

    public int add(T t) {
        b();
        return a((SortedList<T>) t, true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void addAll(T[] tArr, boolean z) {
        b();
        if (tArr.length == 0) {
            return;
        }
        if (z) {
            a((Object[]) tArr);
            return;
        }
        Object[] objArr = (Object[]) Array.newInstance((Class<?>) this.i, tArr.length);
        System.arraycopy(tArr, 0, objArr, 0, tArr.length);
        a(objArr);
    }

    public final void b(@NonNull T[] tArr) {
        boolean z = !(this.f instanceof BatchedCallback);
        if (z) {
            beginBatchedUpdates();
        }
        this.c = 0;
        this.d = this.h;
        this.b = this.a;
        this.e = 0;
        int iC = c(tArr);
        this.a = (T[]) ((Object[]) Array.newInstance((Class<?>) this.i, iC));
        while (true) {
            if (this.e >= iC && this.c >= this.d) {
                break;
            }
            int i = this.c;
            int i2 = this.d;
            if (i >= i2) {
                int i3 = this.e;
                int i4 = iC - i3;
                System.arraycopy(tArr, i3, this.a, i3, i4);
                this.e += i4;
                this.h += i4;
                this.f.onInserted(i3, i4);
                break;
            }
            int i5 = this.e;
            if (i5 >= iC) {
                int i6 = i2 - i;
                this.h -= i6;
                this.f.onRemoved(i5, i6);
                break;
            }
            T t = this.b[i];
            T t2 = tArr[i5];
            int iCompare = this.f.compare(t, t2);
            if (iCompare < 0) {
                a();
            } else if (iCompare > 0) {
                a((SortedList<T>) t2);
            } else if (this.f.areItemsTheSame(t, t2)) {
                T[] tArr2 = this.a;
                int i7 = this.e;
                tArr2[i7] = t2;
                this.c++;
                this.e = i7 + 1;
                if (!this.f.areContentsTheSame(t, t2)) {
                    Callback callback = this.f;
                    callback.onChanged(this.e - 1, 1, callback.getChangePayload(t, t2));
                }
            } else {
                a();
                a((SortedList<T>) t2);
            }
        }
        this.b = null;
        if (z) {
            endBatchedUpdates();
        }
    }

    public void beginBatchedUpdates() {
        b();
        Callback callback = this.f;
        if (callback instanceof BatchedCallback) {
            return;
        }
        if (this.g == null) {
            this.g = new BatchedCallback(callback);
        }
        this.f = this.g;
    }

    public final int c(@NonNull T[] tArr) {
        if (tArr.length == 0) {
            return 0;
        }
        Arrays.sort(tArr, this.f);
        int i = 0;
        int i2 = 1;
        for (int i3 = 1; i3 < tArr.length; i3++) {
            T t = tArr[i3];
            if (this.f.compare(tArr[i], t) == 0) {
                int i4 = i;
                while (true) {
                    if (i4 >= i2) {
                        i4 = -1;
                        break;
                    }
                    if (this.f.areItemsTheSame(tArr[i4], t)) {
                        break;
                    }
                    i4++;
                }
                if (i4 != -1) {
                    tArr[i4] = t;
                } else {
                    if (i2 != i3) {
                        tArr[i2] = t;
                    }
                    i2++;
                }
            } else {
                if (i2 != i3) {
                    tArr[i2] = t;
                }
                i = i2;
                i2++;
            }
        }
        return i2;
    }

    public void clear() {
        b();
        int i = this.h;
        if (i == 0) {
            return;
        }
        Arrays.fill(this.a, 0, i, (Object) null);
        this.h = 0;
        this.f.onRemoved(0, i);
    }

    public void endBatchedUpdates() {
        b();
        Callback callback = this.f;
        if (callback instanceof BatchedCallback) {
            ((BatchedCallback) callback).dispatchLastEvent();
        }
        Callback callback2 = this.f;
        BatchedCallback batchedCallback = this.g;
        if (callback2 == batchedCallback) {
            this.f = batchedCallback.a;
        }
    }

    public T get(int i) throws IndexOutOfBoundsException {
        int i2;
        if (i < this.h && i >= 0) {
            T[] tArr = this.b;
            return (tArr == null || i < (i2 = this.e)) ? this.a[i] : tArr[(i - i2) + this.c];
        }
        StringBuilder sbA = g9.a("Asked to get item at ", i, " but size is ");
        sbA.append(this.h);
        throw new IndexOutOfBoundsException(sbA.toString());
    }

    public int indexOf(T t) {
        if (this.b == null) {
            return a(t, this.a, 0, this.h, 4);
        }
        int iA = a(t, this.a, 0, this.e, 4);
        if (iA != -1) {
            return iA;
        }
        int iA2 = a(t, this.b, this.c, this.d, 4);
        if (iA2 != -1) {
            return (iA2 - this.c) + this.e;
        }
        return -1;
    }

    public void recalculatePositionOfItemAt(int i) throws IndexOutOfBoundsException {
        b();
        T t = get(i);
        a(i, false);
        int iA = a((SortedList<T>) t, false);
        if (i != iA) {
            this.f.onMoved(i, iA);
        }
    }

    public boolean remove(T t) {
        b();
        int iA = a(t, this.a, 0, this.h, 2);
        if (iA == -1) {
            return false;
        }
        a(iA, true);
        return true;
    }

    public T removeItemAt(int i) throws IndexOutOfBoundsException {
        b();
        T t = get(i);
        a(i, true);
        return t;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void replaceAll(@NonNull T[] tArr, boolean z) {
        b();
        if (z) {
            b(tArr);
            return;
        }
        Object[] objArr = (Object[]) Array.newInstance((Class<?>) this.i, tArr.length);
        System.arraycopy(tArr, 0, objArr, 0, tArr.length);
        b(objArr);
    }

    public int size() {
        return this.h;
    }

    public void updateItemAt(int i, T t) throws IndexOutOfBoundsException {
        b();
        T t2 = get(i);
        boolean z = t2 == t || !this.f.areContentsTheSame(t2, t);
        if (t2 != t && this.f.compare(t2, t) == 0) {
            this.a[i] = t;
            if (z) {
                Callback callback = this.f;
                callback.onChanged(i, 1, callback.getChangePayload(t2, t));
                return;
            }
            return;
        }
        if (z) {
            Callback callback2 = this.f;
            callback2.onChanged(i, 1, callback2.getChangePayload(t2, t));
        }
        a(i, false);
        int iA = a((SortedList<T>) t, false);
        if (i != iA) {
            this.f.onMoved(i, iA);
        }
    }

    public SortedList(Class<T> cls, Callback<T> callback, int i) {
        this.i = cls;
        this.a = (T[]) ((Object[]) Array.newInstance((Class<?>) cls, i));
        this.f = callback;
        this.h = 0;
    }

    public void replaceAll(@NonNull T... tArr) {
        replaceAll(tArr, false);
    }

    public void addAll(T... tArr) {
        addAll(tArr, false);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void replaceAll(@NonNull Collection<T> collection) {
        replaceAll(collection.toArray((Object[]) Array.newInstance((Class<?>) this.i, collection.size())), true);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void addAll(Collection<T> collection) {
        addAll(collection.toArray((Object[]) Array.newInstance((Class<?>) this.i, collection.size())), true);
    }

    public final void b() {
        if (this.b != null) {
            throw new IllegalStateException("Data cannot be mutated in the middle of a batch update operation such as addAll or replaceAll.");
        }
    }

    public final void a(T t) {
        T[] tArr = this.a;
        int i = this.e;
        tArr[i] = t;
        int i2 = i + 1;
        this.e = i2;
        this.h++;
        this.f.onInserted(i2 - 1, 1);
    }

    public final void a() {
        this.h--;
        this.c++;
        this.f.onRemoved(this.e, 1);
    }

    public final int a(T t, boolean z) {
        int iA = a(t, this.a, 0, this.h, 1);
        if (iA == -1) {
            iA = 0;
        } else if (iA < this.h) {
            T t2 = this.a[iA];
            if (this.f.areItemsTheSame(t2, t)) {
                if (this.f.areContentsTheSame(t2, t)) {
                    this.a[iA] = t;
                    return iA;
                }
                this.a[iA] = t;
                Callback callback = this.f;
                callback.onChanged(iA, 1, callback.getChangePayload(t2, t));
                return iA;
            }
        }
        int i = this.h;
        if (iA <= i) {
            T[] tArr = this.a;
            if (i == tArr.length) {
                T[] tArr2 = (T[]) ((Object[]) Array.newInstance((Class<?>) this.i, tArr.length + 10));
                System.arraycopy(this.a, 0, tArr2, 0, iA);
                tArr2[iA] = t;
                System.arraycopy(this.a, iA, tArr2, iA + 1, this.h - iA);
                this.a = tArr2;
            } else {
                System.arraycopy(tArr, iA, tArr, iA + 1, i - iA);
                this.a[iA] = t;
            }
            this.h++;
            if (z) {
                this.f.onInserted(iA, 1);
            }
            return iA;
        }
        StringBuilder sbA = g9.a("cannot add item to ", iA, " because size is ");
        sbA.append(this.h);
        throw new IndexOutOfBoundsException(sbA.toString());
    }

    public final void a(int i, boolean z) {
        T[] tArr = this.a;
        System.arraycopy(tArr, i + 1, tArr, i, (this.h - i) - 1);
        int i2 = this.h - 1;
        this.h = i2;
        this.a[i2] = null;
        if (z) {
            this.f.onRemoved(i, 1);
        }
    }

    public final int a(T t, T[] tArr, int i, int i2, int i3) {
        T t2;
        while (i < i2) {
            int i4 = (i + i2) / 2;
            T t3 = tArr[i4];
            int iCompare = this.f.compare(t3, t);
            if (iCompare < 0) {
                i = i4 + 1;
            } else {
                if (iCompare == 0) {
                    if (this.f.areItemsTheSame(t3, t)) {
                        return i4;
                    }
                    int i5 = i4 - 1;
                    while (i5 >= i) {
                        T t4 = this.a[i5];
                        if (this.f.compare(t4, t) != 0) {
                            break;
                        }
                        if (this.f.areItemsTheSame(t4, t)) {
                            break;
                        }
                        i5--;
                    }
                    i5 = i4;
                    do {
                        i5++;
                        if (i5 < i2) {
                            t2 = this.a[i5];
                            if (this.f.compare(t2, t) != 0) {
                            }
                        }
                        i5 = -1;
                        break;
                    } while (!this.f.areItemsTheSame(t2, t));
                    return (i3 == 1 && i5 == -1) ? i4 : i5;
                }
                i2 = i4;
            }
        }
        if (i3 == 1) {
            return i;
        }
        return -1;
    }
}
