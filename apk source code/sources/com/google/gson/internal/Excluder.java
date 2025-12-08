package com.google.gson.internal;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;
import com.google.gson.annotations.Until;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class Excluder implements TypeAdapterFactory, Cloneable {
    public static final Excluder DEFAULT = new Excluder();
    public boolean d;
    public double a = -1.0d;
    public int b = 136;
    public boolean c = true;
    public List<ExclusionStrategy> e = Collections.emptyList();
    public List<ExclusionStrategy> f = Collections.emptyList();

    /* JADX INFO: Add missing generic type declarations: [T] */
    public class a<T> extends TypeAdapter<T> {
        public TypeAdapter<T> a;
        public final /* synthetic */ boolean b;
        public final /* synthetic */ boolean c;
        public final /* synthetic */ Gson d;
        public final /* synthetic */ TypeToken e;

        public a(boolean z, boolean z2, Gson gson, TypeToken typeToken) {
            this.b = z;
            this.c = z2;
            this.d = gson;
            this.e = typeToken;
        }

        @Override // com.google.gson.TypeAdapter
        public T read(JsonReader jsonReader) throws IOException {
            if (this.b) {
                jsonReader.skipValue();
                return null;
            }
            TypeAdapter<T> delegateAdapter = this.a;
            if (delegateAdapter == null) {
                delegateAdapter = this.d.getDelegateAdapter(Excluder.this, this.e);
                this.a = delegateAdapter;
            }
            return delegateAdapter.read(jsonReader);
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, T t) throws IOException {
            if (this.c) {
                jsonWriter.nullValue();
                return;
            }
            TypeAdapter<T> delegateAdapter = this.a;
            if (delegateAdapter == null) {
                delegateAdapter = this.d.getDelegateAdapter(Excluder.this, this.e);
                this.a = delegateAdapter;
            }
            delegateAdapter.write(jsonWriter, t);
        }
    }

    public final boolean a(Class<?> cls) {
        if (this.a == -1.0d || a((Since) cls.getAnnotation(Since.class), (Until) cls.getAnnotation(Until.class))) {
            return (!this.c && c(cls)) || b(cls);
        }
        return true;
    }

    public final boolean b(Class<?> cls) {
        return !Enum.class.isAssignableFrom(cls) && (cls.isAnonymousClass() || cls.isLocalClass());
    }

    public final boolean c(Class<?> cls) {
        if (cls.isMemberClass()) {
            if (!((cls.getModifiers() & 8) != 0)) {
                return true;
            }
        }
        return false;
    }

    @Override // com.google.gson.TypeAdapterFactory
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<? super T> rawType = typeToken.getRawType();
        boolean zA = a(rawType);
        boolean z = zA || a((Class<?>) rawType, true);
        boolean z2 = zA || a((Class<?>) rawType, false);
        if (z || z2) {
            return new a(z2, z, gson, typeToken);
        }
        return null;
    }

    public Excluder disableInnerClassSerialization() {
        Excluder excluderM7clone = m7clone();
        excluderM7clone.c = false;
        return excluderM7clone;
    }

    public boolean excludeClass(Class<?> cls, boolean z) {
        return a(cls) || a(cls, z);
    }

    public boolean excludeField(Field field, boolean z) {
        Expose expose;
        if ((this.b & field.getModifiers()) != 0) {
            return true;
        }
        if ((this.a != -1.0d && !a((Since) field.getAnnotation(Since.class), (Until) field.getAnnotation(Until.class))) || field.isSynthetic()) {
            return true;
        }
        if (this.d && ((expose = (Expose) field.getAnnotation(Expose.class)) == null || (!z ? expose.deserialize() : expose.serialize()))) {
            return true;
        }
        if ((!this.c && c(field.getType())) || b(field.getType())) {
            return true;
        }
        List<ExclusionStrategy> list = z ? this.e : this.f;
        if (list.isEmpty()) {
            return false;
        }
        FieldAttributes fieldAttributes = new FieldAttributes(field);
        Iterator<ExclusionStrategy> it = list.iterator();
        while (it.hasNext()) {
            if (it.next().shouldSkipField(fieldAttributes)) {
                return true;
            }
        }
        return false;
    }

    public Excluder excludeFieldsWithoutExposeAnnotation() {
        Excluder excluderM7clone = m7clone();
        excluderM7clone.d = true;
        return excluderM7clone;
    }

    public Excluder withExclusionStrategy(ExclusionStrategy exclusionStrategy, boolean z, boolean z2) {
        Excluder excluderM7clone = m7clone();
        if (z) {
            ArrayList arrayList = new ArrayList(this.e);
            excluderM7clone.e = arrayList;
            arrayList.add(exclusionStrategy);
        }
        if (z2) {
            ArrayList arrayList2 = new ArrayList(this.f);
            excluderM7clone.f = arrayList2;
            arrayList2.add(exclusionStrategy);
        }
        return excluderM7clone;
    }

    public Excluder withModifiers(int... iArr) {
        Excluder excluderM7clone = m7clone();
        excluderM7clone.b = 0;
        for (int i : iArr) {
            excluderM7clone.b = i | excluderM7clone.b;
        }
        return excluderM7clone;
    }

    public Excluder withVersion(double d) {
        Excluder excluderM7clone = m7clone();
        excluderM7clone.a = d;
        return excluderM7clone;
    }

    /* renamed from: clone, reason: merged with bridge method [inline-methods] */
    public Excluder m7clone() {
        try {
            return (Excluder) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public final boolean a(Class<?> cls, boolean z) {
        Iterator<ExclusionStrategy> it = (z ? this.e : this.f).iterator();
        while (it.hasNext()) {
            if (it.next().shouldSkipClass(cls)) {
                return true;
            }
        }
        return false;
    }

    public final boolean a(Since since, Until until) {
        if (since == null || since.value() <= this.a) {
            return until == null || (until.value() > this.a ? 1 : (until.value() == this.a ? 0 : -1)) > 0;
        }
        return false;
    }
}
