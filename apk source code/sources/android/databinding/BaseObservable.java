package android.databinding;

import android.databinding.Observable;
import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public class BaseObservable implements Observable {
    public transient PropertyChangeRegistry a;

    @Override // android.databinding.Observable
    public void addOnPropertyChangedCallback(@NonNull Observable.OnPropertyChangedCallback onPropertyChangedCallback) {
        synchronized (this) {
            if (this.a == null) {
                this.a = new PropertyChangeRegistry();
            }
        }
        this.a.add(onPropertyChangedCallback);
    }

    public void notifyChange() {
        synchronized (this) {
            if (this.a == null) {
                return;
            }
            this.a.notifyCallbacks(this, 0, null);
        }
    }

    public void notifyPropertyChanged(int i) {
        synchronized (this) {
            if (this.a == null) {
                return;
            }
            this.a.notifyCallbacks(this, i, null);
        }
    }

    @Override // android.databinding.Observable
    public void removeOnPropertyChangedCallback(@NonNull Observable.OnPropertyChangedCallback onPropertyChangedCallback) {
        synchronized (this) {
            if (this.a == null) {
                return;
            }
            this.a.remove(onPropertyChangedCallback);
        }
    }
}
