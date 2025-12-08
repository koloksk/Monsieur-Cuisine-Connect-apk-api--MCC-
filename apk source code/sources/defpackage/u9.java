package defpackage;

import com.google.gson.JsonIOException;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.EnumSet;

/* JADX INFO: Add missing generic type declarations: [T] */
/* loaded from: classes.dex */
public class u9<T> implements ObjectConstructor<T> {
    public final /* synthetic */ Type a;

    public u9(ConstructorConstructor constructorConstructor, Type type) {
        this.a = type;
    }

    @Override // com.google.gson.internal.ObjectConstructor
    public T construct() {
        Type type = this.a;
        if (!(type instanceof ParameterizedType)) {
            StringBuilder sbA = g9.a("Invalid EnumSet type: ");
            sbA.append(this.a.toString());
            throw new JsonIOException(sbA.toString());
        }
        Type type2 = ((ParameterizedType) type).getActualTypeArguments()[0];
        if (type2 instanceof Class) {
            return (T) EnumSet.noneOf((Class) type2);
        }
        StringBuilder sbA2 = g9.a("Invalid EnumSet type: ");
        sbA2.append(this.a.toString());
        throw new JsonIOException(sbA2.toString());
    }
}
