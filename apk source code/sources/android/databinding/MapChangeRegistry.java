package android.databinding;

import android.databinding.CallbackRegistry;
import android.databinding.ObservableMap;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/* loaded from: classes.dex */
public class MapChangeRegistry extends CallbackRegistry<ObservableMap.OnMapChangedCallback, ObservableMap, Object> {
    public static CallbackRegistry.NotifierCallback<ObservableMap.OnMapChangedCallback, ObservableMap, Object> f = new a();

    public static class a extends CallbackRegistry.NotifierCallback<ObservableMap.OnMapChangedCallback, ObservableMap, Object> {
        @Override // android.databinding.CallbackRegistry.NotifierCallback
        public void onNotifyCallback(ObservableMap.OnMapChangedCallback onMapChangedCallback, ObservableMap observableMap, int i, Object obj) {
            ObservableMap observableMap2 = observableMap;
            ViewDataBinding.n nVar = (ViewDataBinding.n) onMapChangedCallback;
            ViewDataBinding viewDataBindingA = nVar.a.a();
            if (viewDataBindingA != null) {
                ViewDataBinding.m<ObservableMap> mVar = nVar.a;
                if (observableMap2 != mVar.c) {
                    return;
                }
                ViewDataBinding.a(viewDataBindingA, mVar.b, observableMap2, 0);
            }
        }
    }

    public MapChangeRegistry() {
        super(f);
    }

    public void notifyChange(@NonNull ObservableMap observableMap, @Nullable Object obj) {
        notifyCallbacks(observableMap, 0, obj);
    }
}
