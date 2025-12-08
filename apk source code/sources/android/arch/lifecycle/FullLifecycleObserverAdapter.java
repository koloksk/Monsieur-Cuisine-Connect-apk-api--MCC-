package android.arch.lifecycle;

import android.arch.lifecycle.Lifecycle;

/* loaded from: classes.dex */
public class FullLifecycleObserverAdapter implements GenericLifecycleObserver {
    public final FullLifecycleObserver a;

    public FullLifecycleObserverAdapter(FullLifecycleObserver fullLifecycleObserver) {
        this.a = fullLifecycleObserver;
    }

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        switch (event) {
            case ON_CREATE:
                this.a.e(lifecycleOwner);
                return;
            case ON_START:
                this.a.b(lifecycleOwner);
                return;
            case ON_RESUME:
                this.a.f(lifecycleOwner);
                return;
            case ON_PAUSE:
                this.a.c(lifecycleOwner);
                return;
            case ON_STOP:
                this.a.a(lifecycleOwner);
                return;
            case ON_DESTROY:
                this.a.d(lifecycleOwner);
                return;
            case ON_ANY:
                throw new IllegalArgumentException("ON_ANY must not been send by anybody");
            default:
                return;
        }
    }
}
