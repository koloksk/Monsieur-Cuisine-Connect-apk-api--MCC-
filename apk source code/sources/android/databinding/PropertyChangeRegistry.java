package android.databinding;

import android.databinding.CallbackRegistry;
import android.databinding.Observable;
import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public class PropertyChangeRegistry extends CallbackRegistry<Observable.OnPropertyChangedCallback, Observable, Void> {
    public static final CallbackRegistry.NotifierCallback<Observable.OnPropertyChangedCallback, Observable, Void> f = new a();

    public static class a extends CallbackRegistry.NotifierCallback<Observable.OnPropertyChangedCallback, Observable, Void> {
        @Override // android.databinding.CallbackRegistry.NotifierCallback
        public void onNotifyCallback(Observable.OnPropertyChangedCallback onPropertyChangedCallback, Observable observable, int i, Void r4) {
            onPropertyChangedCallback.onPropertyChanged(observable, i);
        }
    }

    public PropertyChangeRegistry() {
        super(f);
    }

    public void notifyChange(@NonNull Observable observable, int i) {
        notifyCallbacks(observable, i, null);
    }
}
