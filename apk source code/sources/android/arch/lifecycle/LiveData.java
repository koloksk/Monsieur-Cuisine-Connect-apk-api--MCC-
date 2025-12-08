package android.arch.lifecycle;

import android.arch.core.executor.ArchTaskExecutor;
import android.arch.core.internal.SafeIterableMap;
import android.arch.lifecycle.Lifecycle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes.dex */
public abstract class LiveData<T> {
    public static final Object NOT_SET = new Object();
    public static final int START_VERSION = -1;
    public boolean mDispatchInvalidated;
    public boolean mDispatchingValue;
    public final Object mDataLock = new Object();
    public SafeIterableMap<Observer<T>, LiveData<T>.c> mObservers = new SafeIterableMap<>();
    public int mActiveCount = 0;
    public volatile Object mData = NOT_SET;
    public volatile Object mPendingData = NOT_SET;
    public int mVersion = -1;
    public final Runnable mPostValueRunnable = new a();

    public class LifecycleBoundObserver extends LiveData<T>.c implements GenericLifecycleObserver {

        @NonNull
        public final LifecycleOwner e;

        public LifecycleBoundObserver(@NonNull LifecycleOwner lifecycleOwner, Observer<T> observer) {
            super(observer);
            this.e = lifecycleOwner;
        }

        @Override // android.arch.lifecycle.LiveData.c
        public void a() {
            this.e.getLifecycle().removeObserver(this);
        }

        @Override // android.arch.lifecycle.LiveData.c
        public boolean b() {
            return this.e.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED);
        }

        @Override // android.arch.lifecycle.LiveData.c
        public boolean g(LifecycleOwner lifecycleOwner) {
            return this.e == lifecycleOwner;
        }

        @Override // android.arch.lifecycle.GenericLifecycleObserver
        public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            if (this.e.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
                LiveData.this.removeObserver(this.a);
            } else {
                a(this.e.getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.STARTED));
            }
        }
    }

    public class a implements Runnable {
        public a() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.lang.Runnable
        public void run() {
            Object obj;
            synchronized (LiveData.this.mDataLock) {
                obj = LiveData.this.mPendingData;
                LiveData.this.mPendingData = LiveData.NOT_SET;
            }
            LiveData.this.setValue(obj);
        }
    }

    public class b extends LiveData<T>.c {
        public b(LiveData liveData, Observer<T> observer) {
            super(observer);
        }

        @Override // android.arch.lifecycle.LiveData.c
        public boolean b() {
            return true;
        }
    }

    public abstract class c {
        public final Observer<T> a;
        public boolean b;
        public int c = -1;

        public c(Observer<T> observer) {
            this.a = observer;
        }

        public void a() {
        }

        public void a(boolean z) {
            if (z == this.b) {
                return;
            }
            this.b = z;
            boolean z2 = LiveData.this.mActiveCount == 0;
            LiveData.this.mActiveCount += this.b ? 1 : -1;
            if (z2 && this.b) {
                LiveData.this.onActive();
            }
            if (LiveData.this.mActiveCount == 0 && !this.b) {
                LiveData.this.onInactive();
            }
            if (this.b) {
                LiveData.this.dispatchingValue(this);
            }
        }

        public abstract boolean b();

        public boolean g(LifecycleOwner lifecycleOwner) {
            return false;
        }
    }

    public static void assertMainThread(String str) {
        if (ArchTaskExecutor.getInstance().isMainThread()) {
            return;
        }
        throw new IllegalStateException("Cannot invoke " + str + " on a background thread");
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void considerNotify(LiveData<T>.c cVar) {
        if (cVar.b) {
            if (!cVar.b()) {
                cVar.a(false);
                return;
            }
            int i = cVar.c;
            int i2 = this.mVersion;
            if (i >= i2) {
                return;
            }
            cVar.c = i2;
            cVar.a.onChanged(this.mData);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dispatchingValue(@Nullable LiveData<T>.c cVar) {
        if (this.mDispatchingValue) {
            this.mDispatchInvalidated = true;
            return;
        }
        this.mDispatchingValue = true;
        do {
            this.mDispatchInvalidated = false;
            if (cVar != null) {
                considerNotify(cVar);
                cVar = null;
            } else {
                SafeIterableMap<Observer<T>, LiveData<T>.c>.e eVarIteratorWithAdditions = this.mObservers.iteratorWithAdditions();
                while (eVarIteratorWithAdditions.hasNext()) {
                    considerNotify((c) eVarIteratorWithAdditions.next().getValue());
                    if (this.mDispatchInvalidated) {
                        break;
                    }
                }
            }
        } while (this.mDispatchInvalidated);
        this.mDispatchingValue = false;
    }

    @Nullable
    public T getValue() {
        T t = (T) this.mData;
        if (t != NOT_SET) {
            return t;
        }
        return null;
    }

    public int getVersion() {
        return this.mVersion;
    }

    public boolean hasActiveObservers() {
        return this.mActiveCount > 0;
    }

    public boolean hasObservers() {
        return this.mObservers.size() > 0;
    }

    @MainThread
    public void observe(@NonNull LifecycleOwner lifecycleOwner, @NonNull Observer<T> observer) {
        if (lifecycleOwner.getLifecycle().getCurrentState() == Lifecycle.State.DESTROYED) {
            return;
        }
        LifecycleBoundObserver lifecycleBoundObserver = new LifecycleBoundObserver(lifecycleOwner, observer);
        LiveData<T>.c cVarPutIfAbsent = this.mObservers.putIfAbsent(observer, lifecycleBoundObserver);
        if (cVarPutIfAbsent != null && !cVarPutIfAbsent.g(lifecycleOwner)) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (cVarPutIfAbsent != null) {
            return;
        }
        lifecycleOwner.getLifecycle().addObserver(lifecycleBoundObserver);
    }

    @MainThread
    public void observeForever(@NonNull Observer<T> observer) {
        b bVar = new b(this, observer);
        LiveData<T>.c cVarPutIfAbsent = this.mObservers.putIfAbsent(observer, bVar);
        if (cVarPutIfAbsent != null && (cVarPutIfAbsent instanceof LifecycleBoundObserver)) {
            throw new IllegalArgumentException("Cannot add the same observer with different lifecycles");
        }
        if (cVarPutIfAbsent != null) {
            return;
        }
        bVar.a(true);
    }

    public void onActive() {
    }

    public void onInactive() {
    }

    public void postValue(T t) {
        boolean z;
        synchronized (this.mDataLock) {
            z = this.mPendingData == NOT_SET;
            this.mPendingData = t;
        }
        if (z) {
            ArchTaskExecutor.getInstance().postToMainThread(this.mPostValueRunnable);
        }
    }

    @MainThread
    public void removeObserver(@NonNull Observer<T> observer) {
        assertMainThread("removeObserver");
        LiveData<T>.c cVarRemove = this.mObservers.remove(observer);
        if (cVarRemove == null) {
            return;
        }
        cVarRemove.a();
        cVarRemove.a(false);
    }

    @MainThread
    public void removeObservers(@NonNull LifecycleOwner lifecycleOwner) {
        assertMainThread("removeObservers");
        Iterator<Map.Entry<Observer<T>, LiveData<T>.c>> it = this.mObservers.iterator();
        while (it.hasNext()) {
            Map.Entry<Observer<T>, LiveData<T>.c> next = it.next();
            if (next.getValue().g(lifecycleOwner)) {
                removeObserver(next.getKey());
            }
        }
    }

    @MainThread
    public void setValue(T t) {
        assertMainThread("setValue");
        this.mVersion++;
        this.mData = t;
        dispatchingValue(null);
    }
}
