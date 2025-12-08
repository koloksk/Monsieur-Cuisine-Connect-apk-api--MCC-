package android.databinding;

import android.databinding.CallbackRegistry;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.v4.util.Pools;

/* loaded from: classes.dex */
public class ListChangeRegistry extends CallbackRegistry<ObservableList.OnListChangedCallback, ObservableList, b> {
    public static final Pools.SynchronizedPool<b> f = new Pools.SynchronizedPool<>(10);
    public static final CallbackRegistry.NotifierCallback<ObservableList.OnListChangedCallback, ObservableList, b> g = new a();

    public static class a extends CallbackRegistry.NotifierCallback<ObservableList.OnListChangedCallback, ObservableList, b> {
        @Override // android.databinding.CallbackRegistry.NotifierCallback
        public void onNotifyCallback(ObservableList.OnListChangedCallback onListChangedCallback, ObservableList observableList, int i, b bVar) {
            ObservableList.OnListChangedCallback onListChangedCallback2 = onListChangedCallback;
            ObservableList observableList2 = observableList;
            b bVar2 = bVar;
            if (i == 1) {
                onListChangedCallback2.onItemRangeChanged(observableList2, bVar2.a, bVar2.b);
                return;
            }
            if (i == 2) {
                onListChangedCallback2.onItemRangeInserted(observableList2, bVar2.a, bVar2.b);
                return;
            }
            if (i == 3) {
                onListChangedCallback2.onItemRangeMoved(observableList2, bVar2.a, bVar2.c, bVar2.b);
            } else if (i != 4) {
                onListChangedCallback2.onChanged(observableList2);
            } else {
                onListChangedCallback2.onItemRangeRemoved(observableList2, bVar2.a, bVar2.b);
            }
        }
    }

    public static class b {
        public int a;
        public int b;
        public int c;
    }

    public ListChangeRegistry() {
        super(g);
    }

    public static b a(int i, int i2, int i3) {
        b bVarAcquire = f.acquire();
        if (bVarAcquire == null) {
            bVarAcquire = new b();
        }
        bVarAcquire.a = i;
        bVarAcquire.c = i2;
        bVarAcquire.b = i3;
        return bVarAcquire;
    }

    public void notifyChanged(@NonNull ObservableList observableList) {
        notifyCallbacks(observableList, 0, (b) null);
    }

    public void notifyInserted(@NonNull ObservableList observableList, int i, int i2) {
        notifyCallbacks(observableList, 2, a(i, 0, i2));
    }

    public void notifyMoved(@NonNull ObservableList observableList, int i, int i2, int i3) {
        notifyCallbacks(observableList, 3, a(i, i2, i3));
    }

    public void notifyRemoved(@NonNull ObservableList observableList, int i, int i2) {
        notifyCallbacks(observableList, 4, a(i, 0, i2));
    }

    @Override // android.databinding.CallbackRegistry
    public synchronized void notifyCallbacks(@NonNull ObservableList observableList, int i, b bVar) {
        super.notifyCallbacks((ListChangeRegistry) observableList, i, (int) bVar);
        if (bVar != null) {
            f.release(bVar);
        }
    }

    public void notifyChanged(@NonNull ObservableList observableList, int i, int i2) {
        notifyCallbacks(observableList, 1, a(i, 0, i2));
    }
}
