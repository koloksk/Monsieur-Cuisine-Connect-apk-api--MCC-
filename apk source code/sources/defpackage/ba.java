package defpackage;

import com.google.gson.internal.reflect.ReflectionAccessor;
import java.lang.reflect.AccessibleObject;

/* loaded from: classes.dex */
public final class ba extends ReflectionAccessor {
    @Override // com.google.gson.internal.reflect.ReflectionAccessor
    public void makeAccessible(AccessibleObject accessibleObject) throws SecurityException {
        accessibleObject.setAccessible(true);
    }
}
