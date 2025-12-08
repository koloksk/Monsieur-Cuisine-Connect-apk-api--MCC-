package com.google.gson;

import com.google.gson.internal.C$Gson$Preconditions;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.TreeTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import defpackage.h9;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes.dex */
public final class GsonBuilder {
    public Excluder a;
    public LongSerializationPolicy b;
    public FieldNamingStrategy c;
    public final Map<Type, InstanceCreator<?>> d;
    public final List<TypeAdapterFactory> e;
    public final List<TypeAdapterFactory> f;
    public boolean g;
    public String h;
    public int i;
    public int j;
    public boolean k;
    public boolean l;
    public boolean m;
    public boolean n;
    public boolean o;
    public boolean p;

    public GsonBuilder() {
        this.a = Excluder.DEFAULT;
        this.b = LongSerializationPolicy.DEFAULT;
        this.c = FieldNamingPolicy.IDENTITY;
        this.d = new HashMap();
        this.e = new ArrayList();
        this.f = new ArrayList();
        this.g = false;
        this.i = 2;
        this.j = 2;
        this.k = false;
        this.l = false;
        this.m = true;
        this.n = false;
        this.o = false;
        this.p = false;
    }

    public GsonBuilder addDeserializationExclusionStrategy(ExclusionStrategy exclusionStrategy) {
        this.a = this.a.withExclusionStrategy(exclusionStrategy, false, true);
        return this;
    }

    public GsonBuilder addSerializationExclusionStrategy(ExclusionStrategy exclusionStrategy) {
        this.a = this.a.withExclusionStrategy(exclusionStrategy, true, false);
        return this;
    }

    public Gson create() {
        h9 h9Var;
        h9 h9Var2;
        h9 h9Var3;
        ArrayList arrayList = new ArrayList(this.f.size() + this.e.size() + 3);
        arrayList.addAll(this.e);
        Collections.reverse(arrayList);
        ArrayList arrayList2 = new ArrayList(this.f);
        Collections.reverse(arrayList2);
        arrayList.addAll(arrayList2);
        String str = this.h;
        int i = this.i;
        int i2 = this.j;
        if (str == null || "".equals(str.trim())) {
            if (i != 2 && i2 != 2) {
                h9 h9Var4 = new h9(Date.class, i, i2);
                h9 h9Var5 = new h9(Timestamp.class, i, i2);
                h9 h9Var6 = new h9(java.sql.Date.class, i, i2);
                h9Var = h9Var4;
                h9Var2 = h9Var5;
                h9Var3 = h9Var6;
            }
            return new Gson(this.a, this.c, this.d, this.g, this.k, this.o, this.m, this.n, this.p, this.l, this.b, this.h, this.i, this.j, this.e, this.f, arrayList);
        }
        h9Var = new h9(Date.class, str);
        h9Var2 = new h9(Timestamp.class, str);
        h9Var3 = new h9(java.sql.Date.class, str);
        arrayList.add(TypeAdapters.newFactory(Date.class, h9Var));
        arrayList.add(TypeAdapters.newFactory(Timestamp.class, h9Var2));
        arrayList.add(TypeAdapters.newFactory(java.sql.Date.class, h9Var3));
        return new Gson(this.a, this.c, this.d, this.g, this.k, this.o, this.m, this.n, this.p, this.l, this.b, this.h, this.i, this.j, this.e, this.f, arrayList);
    }

    public GsonBuilder disableHtmlEscaping() {
        this.m = false;
        return this;
    }

    public GsonBuilder disableInnerClassSerialization() {
        this.a = this.a.disableInnerClassSerialization();
        return this;
    }

    public GsonBuilder enableComplexMapKeySerialization() {
        this.k = true;
        return this;
    }

    public GsonBuilder excludeFieldsWithModifiers(int... iArr) {
        this.a = this.a.withModifiers(iArr);
        return this;
    }

    public GsonBuilder excludeFieldsWithoutExposeAnnotation() {
        this.a = this.a.excludeFieldsWithoutExposeAnnotation();
        return this;
    }

    public GsonBuilder generateNonExecutableJson() {
        this.o = true;
        return this;
    }

    public GsonBuilder registerTypeAdapter(Type type, Object obj) {
        boolean z = obj instanceof JsonSerializer;
        C$Gson$Preconditions.checkArgument(z || (obj instanceof JsonDeserializer) || (obj instanceof InstanceCreator) || (obj instanceof TypeAdapter));
        if (obj instanceof InstanceCreator) {
            this.d.put(type, (InstanceCreator) obj);
        }
        if (z || (obj instanceof JsonDeserializer)) {
            this.e.add(TreeTypeAdapter.newFactoryWithMatchRawType(TypeToken.get(type), obj));
        }
        if (obj instanceof TypeAdapter) {
            this.e.add(TypeAdapters.newFactory(TypeToken.get(type), (TypeAdapter) obj));
        }
        return this;
    }

    public GsonBuilder registerTypeAdapterFactory(TypeAdapterFactory typeAdapterFactory) {
        this.e.add(typeAdapterFactory);
        return this;
    }

    public GsonBuilder registerTypeHierarchyAdapter(Class<?> cls, Object obj) {
        boolean z = obj instanceof JsonSerializer;
        C$Gson$Preconditions.checkArgument(z || (obj instanceof JsonDeserializer) || (obj instanceof TypeAdapter));
        if ((obj instanceof JsonDeserializer) || z) {
            this.f.add(TreeTypeAdapter.newTypeHierarchyFactory(cls, obj));
        }
        if (obj instanceof TypeAdapter) {
            this.e.add(TypeAdapters.newTypeHierarchyFactory(cls, (TypeAdapter) obj));
        }
        return this;
    }

    public GsonBuilder serializeNulls() {
        this.g = true;
        return this;
    }

    public GsonBuilder serializeSpecialFloatingPointValues() {
        this.l = true;
        return this;
    }

    public GsonBuilder setDateFormat(String str) {
        this.h = str;
        return this;
    }

    public GsonBuilder setExclusionStrategies(ExclusionStrategy... exclusionStrategyArr) {
        for (ExclusionStrategy exclusionStrategy : exclusionStrategyArr) {
            this.a = this.a.withExclusionStrategy(exclusionStrategy, true, true);
        }
        return this;
    }

    public GsonBuilder setFieldNamingPolicy(FieldNamingPolicy fieldNamingPolicy) {
        this.c = fieldNamingPolicy;
        return this;
    }

    public GsonBuilder setFieldNamingStrategy(FieldNamingStrategy fieldNamingStrategy) {
        this.c = fieldNamingStrategy;
        return this;
    }

    public GsonBuilder setLenient() {
        this.p = true;
        return this;
    }

    public GsonBuilder setLongSerializationPolicy(LongSerializationPolicy longSerializationPolicy) {
        this.b = longSerializationPolicy;
        return this;
    }

    public GsonBuilder setPrettyPrinting() {
        this.n = true;
        return this;
    }

    public GsonBuilder setVersion(double d) {
        this.a = this.a.withVersion(d);
        return this;
    }

    public GsonBuilder setDateFormat(int i) {
        this.i = i;
        this.h = null;
        return this;
    }

    public GsonBuilder setDateFormat(int i, int i2) {
        this.i = i;
        this.j = i2;
        this.h = null;
        return this;
    }

    public GsonBuilder(Gson gson) {
        this.a = Excluder.DEFAULT;
        this.b = LongSerializationPolicy.DEFAULT;
        this.c = FieldNamingPolicy.IDENTITY;
        this.d = new HashMap();
        this.e = new ArrayList();
        this.f = new ArrayList();
        this.g = false;
        this.i = 2;
        this.j = 2;
        this.k = false;
        this.l = false;
        this.m = true;
        this.n = false;
        this.o = false;
        this.p = false;
        this.a = gson.f;
        this.c = gson.g;
        this.d.putAll(gson.h);
        this.g = gson.i;
        this.k = gson.j;
        this.o = gson.k;
        this.m = gson.l;
        this.n = gson.m;
        this.p = gson.n;
        this.l = gson.o;
        this.b = gson.s;
        this.h = gson.p;
        this.i = gson.q;
        this.j = gson.r;
        this.e.addAll(gson.t);
        this.f.addAll(gson.u);
    }
}
