package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* loaded from: classes.dex */
public final class Pools {

    public interface Pool<T> {
        @Nullable
        T acquire();

        boolean release(@NonNull T t);
    }

    public static class SimplePool<T> implements Pool<T> {
        public final Object[] a;
        public int b;

        public SimplePool(int i) {
            if (i <= 0) {
                throw new IllegalArgumentException("The max pool size must be > 0");
            }
            this.a = new Object[i];
        }

        @Override // android.support.v4.util.Pools.Pool
        public T acquire() {
            int i = this.b;
            if (i <= 0) {
                return null;
            }
            int i2 = i - 1;
            Object[] objArr = this.a;
            T t = (T) objArr[i2];
            objArr[i2] = null;
            this.b = i - 1;
            return t;
        }

        @Override // android.support.v4.util.Pools.Pool
        public boolean release(@NonNull T t) {
            boolean z;
            int i = 0;
            while (true) {
                if (i >= this.b) {
                    z = false;
                    break;
                }
                if (this.a[i] == t) {
                    z = true;
                    break;
                }
                i++;
            }
            if (z) {
                throw new IllegalStateException("Already in the pool!");
            }
            int i2 = this.b;
            Object[] objArr = this.a;
            if (i2 >= objArr.length) {
                return false;
            }
            objArr[i2] = t;
            this.b = i2 + 1;
            return true;
        }
    }

    public static class SynchronizedPool<T> extends SimplePool<T> {
        public final Object c;

        public SynchronizedPool(int i) {
            super(i);
            this.c = new Object();
        }

        @Override // android.support.v4.util.Pools.SimplePool, android.support.v4.util.Pools.Pool
        public T acquire() {
            T t;
            synchronized (this.c) {
                t = (T) super.acquire();
            }
            return t;
        }

        @Override // android.support.v4.util.Pools.SimplePool, android.support.v4.util.Pools.Pool
        public boolean release(@NonNull T t) {
            boolean zRelease;
            synchronized (this.c) {
                zRelease = super.release(t);
            }
            return zRelease;
        }
    }
}
