package defpackage;

import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.internal.ObjectConstructor;

/* JADX INFO: Add missing generic type declarations: [T] */
/* loaded from: classes.dex */
public class q9<T> implements ObjectConstructor<T> {
    public q9(ConstructorConstructor constructorConstructor) {
    }

    @Override // com.google.gson.internal.ObjectConstructor
    public T construct() {
        return (T) new LinkedTreeMap();
    }
}
