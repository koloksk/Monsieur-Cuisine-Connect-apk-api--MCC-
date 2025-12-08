package android.arch.lifecycle;

import android.arch.lifecycle.Lifecycle;
import android.support.annotation.RestrictTo;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class SingleGeneratedAdapterObserver implements GenericLifecycleObserver {
    public final GeneratedAdapter a;

    public SingleGeneratedAdapterObserver(GeneratedAdapter generatedAdapter) {
        this.a = generatedAdapter;
    }

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        this.a.callMethods(lifecycleOwner, event, false, null);
        this.a.callMethods(lifecycleOwner, event, true, null);
    }
}
