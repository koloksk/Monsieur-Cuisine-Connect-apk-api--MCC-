package defpackage;

import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import java.util.LinkedHashMap;

/* JADX INFO: Add missing generic type declarations: [T] */
/* loaded from: classes.dex */
public class p9<T> implements ObjectConstructor<T> {
    public p9(ConstructorConstructor constructorConstructor) {
    }

    @Override // com.google.gson.internal.ObjectConstructor
    public T construct() {
        return (T) new LinkedHashMap();
    }
}
