package android.databinding;

import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class CallbackRegistry<C, T, A> implements Cloneable {
    public List<C> a = new ArrayList();
    public long b = 0;
    public long[] c;
    public int d;
    public final NotifierCallback<C, T, A> e;

    public static abstract class NotifierCallback<C, T, A> {
        public abstract void onNotifyCallback(C c, T t, int i, A a);
    }

    public CallbackRegistry(NotifierCallback<C, T, A> notifierCallback) {
        this.e = notifierCallback;
    }

    public final void a(T t, int i, A a, int i2) {
        if (i2 < 0) {
            a(t, i, a, 0, Math.min(64, this.a.size()), this.b);
            return;
        }
        long j = this.c[i2];
        int i3 = (i2 + 1) * 64;
        int iMin = Math.min(this.a.size(), i3 + 64);
        a(t, i, a, i2 - 1);
        a(t, i, a, i3, iMin, j);
    }

    public synchronized void add(C c) {
        if (c == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        int iLastIndexOf = this.a.lastIndexOf(c);
        if (iLastIndexOf < 0 || a(iLastIndexOf)) {
            this.a.add(c);
        }
    }

    public final void b(int i) {
        if (i < 64) {
            this.b = (1 << i) | this.b;
            return;
        }
        int i2 = (i / 64) - 1;
        long[] jArr = this.c;
        if (jArr == null) {
            this.c = new long[this.a.size() / 64];
        } else if (jArr.length <= i2) {
            long[] jArr2 = new long[this.a.size() / 64];
            long[] jArr3 = this.c;
            System.arraycopy(jArr3, 0, jArr2, 0, jArr3.length);
            this.c = jArr2;
        }
        long j = 1 << (i % 64);
        long[] jArr4 = this.c;
        jArr4[i2] = j | jArr4[i2];
    }

    public synchronized void clear() {
        if (this.d == 0) {
            this.a.clear();
        } else if (!this.a.isEmpty()) {
            for (int size = this.a.size() - 1; size >= 0; size--) {
                b(size);
            }
        }
    }

    public synchronized ArrayList<C> copyCallbacks() {
        ArrayList<C> arrayList;
        arrayList = new ArrayList<>(this.a.size());
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            if (!a(i)) {
                arrayList.add(this.a.get(i));
            }
        }
        return arrayList;
    }

    public synchronized boolean isEmpty() {
        if (this.a.isEmpty()) {
            return true;
        }
        if (this.d == 0) {
            return false;
        }
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            if (!a(i)) {
                return false;
            }
        }
        return true;
    }

    public synchronized void notifyCallbacks(T t, int i, A a) {
        this.d++;
        int size = this.a.size();
        long[] jArr = this.c;
        int length = -1;
        if (jArr != null) {
            length = (-1) + jArr.length;
        }
        a(t, i, a, length);
        a(t, i, a, (length + 2) * 64, size, 0L);
        int i2 = this.d - 1;
        this.d = i2;
        if (i2 == 0) {
            if (this.c != null) {
                for (int length2 = this.c.length - 1; length2 >= 0; length2--) {
                    long j = this.c[length2];
                    if (j != 0) {
                        a((length2 + 1) * 64, j);
                        this.c[length2] = 0;
                    }
                }
            }
            if (this.b != 0) {
                a(0, this.b);
                this.b = 0L;
            }
        }
    }

    public synchronized void remove(C c) {
        if (this.d == 0) {
            this.a.remove(c);
        } else {
            int iLastIndexOf = this.a.lastIndexOf(c);
            if (iLastIndexOf >= 0) {
                b(iLastIndexOf);
            }
        }
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public synchronized CallbackRegistry<C, T, A> m1clone() {
        CallbackRegistry<C, T, A> callbackRegistry;
        CloneNotSupportedException e;
        try {
            callbackRegistry = (CallbackRegistry) super.clone();
        } catch (CloneNotSupportedException e2) {
            callbackRegistry = null;
            e = e2;
        }
        try {
            callbackRegistry.b = 0L;
            callbackRegistry.c = null;
            callbackRegistry.d = 0;
            callbackRegistry.a = new ArrayList();
            int size = this.a.size();
            for (int i = 0; i < size; i++) {
                if (!a(i)) {
                    callbackRegistry.a.add(this.a.get(i));
                }
            }
        } catch (CloneNotSupportedException e3) {
            e = e3;
            e.printStackTrace();
            return callbackRegistry;
        }
        return callbackRegistry;
    }

    public synchronized void copyCallbacks(List<C> list) {
        list.clear();
        int size = this.a.size();
        for (int i = 0; i < size; i++) {
            if (!a(i)) {
                list.add(this.a.get(i));
            }
        }
    }

    public final void a(T t, int i, A a, int i2, int i3, long j) {
        long j2 = 1;
        while (i2 < i3) {
            if ((j & j2) == 0) {
                this.e.onNotifyCallback(this.a.get(i2), t, i, a);
            }
            j2 <<= 1;
            i2++;
        }
    }

    public final boolean a(int i) {
        int i2;
        if (i < 64) {
            return ((1 << i) & this.b) != 0;
        }
        long[] jArr = this.c;
        if (jArr != null && (i2 = (i / 64) - 1) < jArr.length) {
            return ((1 << (i % 64)) & jArr[i2]) != 0;
        }
        return false;
    }

    public final void a(int i, long j) {
        long j2 = Long.MIN_VALUE;
        for (int i2 = (i + 64) - 1; i2 >= i; i2--) {
            if ((j & j2) != 0) {
                this.a.remove(i2);
            }
            j2 >>>= 1;
        }
    }
}
