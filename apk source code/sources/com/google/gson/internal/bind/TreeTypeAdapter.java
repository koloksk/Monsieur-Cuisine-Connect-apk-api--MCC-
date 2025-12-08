package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.C$Gson$Preconditions;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;

/* loaded from: classes.dex */
public final class TreeTypeAdapter<T> extends TypeAdapter<T> {
    public final JsonSerializer<T> a;
    public final JsonDeserializer<T> b;
    public final Gson c;
    public final TypeToken<T> d;
    public final TypeAdapterFactory e;
    public final TreeTypeAdapter<T>.b f = new b(null);
    public TypeAdapter<T> g;

    public final class b implements JsonSerializationContext, JsonDeserializationContext {
        public /* synthetic */ b(a aVar) {
        }

        @Override // com.google.gson.JsonDeserializationContext
        public <R> R deserialize(JsonElement jsonElement, Type type) throws JsonParseException {
            return (R) TreeTypeAdapter.this.c.fromJson(jsonElement, type);
        }

        @Override // com.google.gson.JsonSerializationContext
        public JsonElement serialize(Object obj) {
            return TreeTypeAdapter.this.c.toJsonTree(obj);
        }

        @Override // com.google.gson.JsonSerializationContext
        public JsonElement serialize(Object obj, Type type) {
            return TreeTypeAdapter.this.c.toJsonTree(obj, type);
        }
    }

    public static final class c implements TypeAdapterFactory {
        public final TypeToken<?> a;
        public final boolean b;
        public final Class<?> c;
        public final JsonSerializer<?> d;
        public final JsonDeserializer<?> e;

        public c(Object obj, TypeToken<?> typeToken, boolean z, Class<?> cls) {
            this.d = obj instanceof JsonSerializer ? (JsonSerializer) obj : null;
            JsonDeserializer<?> jsonDeserializer = obj instanceof JsonDeserializer ? (JsonDeserializer) obj : null;
            this.e = jsonDeserializer;
            C$Gson$Preconditions.checkArgument((this.d == null && jsonDeserializer == null) ? false : true);
            this.a = typeToken;
            this.b = z;
            this.c = cls;
        }

        @Override // com.google.gson.TypeAdapterFactory
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            TypeToken<?> typeToken2 = this.a;
            if (typeToken2 != null ? typeToken2.equals(typeToken) || (this.b && this.a.getType() == typeToken.getRawType()) : this.c.isAssignableFrom(typeToken.getRawType())) {
                return new TreeTypeAdapter(this.d, this.e, gson, typeToken, this);
            }
            return null;
        }
    }

    public TreeTypeAdapter(JsonSerializer<T> jsonSerializer, JsonDeserializer<T> jsonDeserializer, Gson gson, TypeToken<T> typeToken, TypeAdapterFactory typeAdapterFactory) {
        this.a = jsonSerializer;
        this.b = jsonDeserializer;
        this.c = gson;
        this.d = typeToken;
        this.e = typeAdapterFactory;
    }

    public static TypeAdapterFactory newFactory(TypeToken<?> typeToken, Object obj) {
        return new c(obj, typeToken, false, null);
    }

    public static TypeAdapterFactory newFactoryWithMatchRawType(TypeToken<?> typeToken, Object obj) {
        return new c(obj, typeToken, typeToken.getType() == typeToken.getRawType(), null);
    }

    public static TypeAdapterFactory newTypeHierarchyFactory(Class<?> cls, Object obj) {
        return new c(obj, null, false, cls);
    }

    @Override // com.google.gson.TypeAdapter
    public T read(JsonReader jsonReader) throws JsonParseException, IOException {
        if (this.b != null) {
            JsonElement jsonElement = Streams.parse(jsonReader);
            if (jsonElement.isJsonNull()) {
                return null;
            }
            return this.b.deserialize(jsonElement, this.d.getType(), this.f);
        }
        TypeAdapter<T> delegateAdapter = this.g;
        if (delegateAdapter == null) {
            delegateAdapter = this.c.getDelegateAdapter(this.e, this.d);
            this.g = delegateAdapter;
        }
        return delegateAdapter.read(jsonReader);
    }

    @Override // com.google.gson.TypeAdapter
    public void write(JsonWriter jsonWriter, T t) throws IOException {
        JsonSerializer<T> jsonSerializer = this.a;
        if (jsonSerializer != null) {
            if (t == null) {
                jsonWriter.nullValue();
                return;
            } else {
                Streams.write(jsonSerializer.serialize(t, this.d.getType(), this.f), jsonWriter);
                return;
            }
        }
        TypeAdapter<T> delegateAdapter = this.g;
        if (delegateAdapter == null) {
            delegateAdapter = this.c.getDelegateAdapter(this.e, this.d);
            this.g = delegateAdapter;
        }
        delegateAdapter.write(jsonWriter, t);
    }
}
