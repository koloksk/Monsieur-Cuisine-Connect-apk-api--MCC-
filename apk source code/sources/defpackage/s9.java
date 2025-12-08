package defpackage;

import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/* JADX INFO: Add missing generic type declarations: [T] */
/* loaded from: classes.dex */
public class s9<T> implements ObjectConstructor<T> {
    public final /* synthetic */ Constructor a;

    public s9(ConstructorConstructor constructorConstructor, Constructor constructor) {
        this.a = constructor;
    }

    @Override // com.google.gson.internal.ObjectConstructor
    public T construct() {
        try {
            return (T) this.a.newInstance(null);
        } catch (IllegalAccessException e) {
            throw new AssertionError(e);
        } catch (InstantiationException e2) {
            StringBuilder sbA = g9.a("Failed to invoke ");
            sbA.append(this.a);
            sbA.append(" with no args");
            throw new RuntimeException(sbA.toString(), e2);
        } catch (InvocationTargetException e3) {
            StringBuilder sbA2 = g9.a("Failed to invoke ");
            sbA2.append(this.a);
            sbA2.append(" with no args");
            throw new RuntimeException(sbA2.toString(), e3.getTargetException());
        }
    }
}
