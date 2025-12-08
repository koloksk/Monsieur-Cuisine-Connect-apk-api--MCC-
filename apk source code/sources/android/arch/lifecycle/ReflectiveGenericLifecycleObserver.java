package android.arch.lifecycle;

import android.arch.lifecycle.Lifecycle;
import defpackage.n1;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public class ReflectiveGenericLifecycleObserver implements GenericLifecycleObserver {
    public final Object a;
    public final n1.a b;

    public ReflectiveGenericLifecycleObserver(Object obj) {
        this.a = obj;
        this.b = n1.c.a(obj.getClass());
    }

    @Override // android.arch.lifecycle.GenericLifecycleObserver
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        n1.a aVar = this.b;
        Object obj = this.a;
        n1.a.a(aVar.a.get(event), lifecycleOwner, event, obj);
        n1.a.a(aVar.a.get(Lifecycle.Event.ON_ANY), lifecycleOwner, event, obj);
    }
}
