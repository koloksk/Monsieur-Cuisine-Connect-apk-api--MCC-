package com.google.gson.internal;

import com.google.gson.InstanceCreator;
import com.google.gson.internal.reflect.ReflectionAccessor;
import com.google.gson.reflect.TypeToken;
import defpackage.n9;
import defpackage.o9;
import defpackage.p9;
import defpackage.q9;
import defpackage.r9;
import defpackage.s9;
import defpackage.t9;
import defpackage.u9;
import defpackage.v9;
import defpackage.w9;
import defpackage.x9;
import defpackage.y9;
import java.lang.reflect.Constructor;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.EnumSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentNavigableMap;

/* loaded from: classes.dex */
public final class ConstructorConstructor {
    public final Map<Type, InstanceCreator<?>> a;
    public final ReflectionAccessor b = ReflectionAccessor.getInstance();

    /* JADX INFO: Add missing generic type declarations: [T] */
    public class a<T> implements ObjectConstructor<T> {
        public final /* synthetic */ InstanceCreator a;
        public final /* synthetic */ Type b;

        public a(ConstructorConstructor constructorConstructor, InstanceCreator instanceCreator, Type type) {
            this.a = instanceCreator;
            this.b = type;
        }

        @Override // com.google.gson.internal.ObjectConstructor
        public T construct() {
            return (T) this.a.createInstance(this.b);
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    public class b<T> implements ObjectConstructor<T> {
        public final /* synthetic */ InstanceCreator a;
        public final /* synthetic */ Type b;

        public b(ConstructorConstructor constructorConstructor, InstanceCreator instanceCreator, Type type) {
            this.a = instanceCreator;
            this.b = type;
        }

        @Override // com.google.gson.internal.ObjectConstructor
        public T construct() {
            return (T) this.a.createInstance(this.b);
        }
    }

    public ConstructorConstructor(Map<Type, InstanceCreator<?>> map) {
        this.a = map;
    }

    public <T> ObjectConstructor<T> get(TypeToken<T> typeToken) throws NoSuchMethodException, SecurityException {
        s9 s9Var;
        Type type = typeToken.getType();
        Class<? super T> rawType = typeToken.getRawType();
        InstanceCreator<?> instanceCreator = this.a.get(type);
        if (instanceCreator != null) {
            return new a(this, instanceCreator, type);
        }
        InstanceCreator<?> instanceCreator2 = this.a.get(rawType);
        if (instanceCreator2 != null) {
            return new b(this, instanceCreator2, type);
        }
        ObjectConstructor<T> y9Var = null;
        try {
            Constructor<? super T> declaredConstructor = rawType.getDeclaredConstructor(new Class[0]);
            if (!declaredConstructor.isAccessible()) {
                this.b.makeAccessible(declaredConstructor);
            }
            s9Var = new s9(this, declaredConstructor);
        } catch (NoSuchMethodException unused) {
            s9Var = null;
        }
        if (s9Var != null) {
            return s9Var;
        }
        if (Collection.class.isAssignableFrom(rawType)) {
            y9Var = SortedSet.class.isAssignableFrom(rawType) ? new t9<>(this) : EnumSet.class.isAssignableFrom(rawType) ? new u9<>(this, type) : Set.class.isAssignableFrom(rawType) ? new v9<>(this) : Queue.class.isAssignableFrom(rawType) ? new w9<>(this) : new x9<>(this);
        } else if (Map.class.isAssignableFrom(rawType)) {
            y9Var = ConcurrentNavigableMap.class.isAssignableFrom(rawType) ? new y9<>(this) : ConcurrentMap.class.isAssignableFrom(rawType) ? new n9<>(this) : SortedMap.class.isAssignableFrom(rawType) ? new o9<>(this) : (!(type instanceof ParameterizedType) || String.class.isAssignableFrom(TypeToken.get(((ParameterizedType) type).getActualTypeArguments()[0]).getRawType())) ? new q9<>(this) : new p9<>(this);
        }
        return y9Var != null ? y9Var : new r9(this, rawType, type);
    }

    public String toString() {
        return this.a.toString();
    }
}
