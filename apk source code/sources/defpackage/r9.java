package defpackage;

import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.UnsafeAllocator;
import java.lang.reflect.Type;

/* JADX INFO: Add missing generic type declarations: [T] */
/* loaded from: classes.dex */
public class r9<T> implements ObjectConstructor<T> {
    public final UnsafeAllocator a = UnsafeAllocator.create();
    public final /* synthetic */ Class b;
    public final /* synthetic */ Type c;

    public r9(ConstructorConstructor constructorConstructor, Class cls, Type type) {
        this.b = cls;
        this.c = type;
    }

    @Override // com.google.gson.internal.ObjectConstructor
    public T construct() {
        try {
            return (T) this.a.newInstance(this.b);
        } catch (Exception e) {
            StringBuilder sbA = g9.a("Unable to invoke no-args constructor for ");
            sbA.append(this.c);
            sbA.append(". Registering an InstanceCreator with Gson for this type may fix this problem.");
            throw new RuntimeException(sbA.toString(), e);
        }
    }
}
