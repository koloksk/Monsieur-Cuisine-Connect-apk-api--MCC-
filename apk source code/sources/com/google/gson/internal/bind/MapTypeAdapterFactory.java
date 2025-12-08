package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.C$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import defpackage.aa;
import defpackage.g9;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public final class MapTypeAdapterFactory implements TypeAdapterFactory {
    public final ConstructorConstructor a;
    public final boolean b;

    public final class a<K, V> extends TypeAdapter<Map<K, V>> {
        public final TypeAdapter<K> a;
        public final TypeAdapter<V> b;
        public final ObjectConstructor<? extends Map<K, V>> c;

        public a(Gson gson, Type type, TypeAdapter<K> typeAdapter, Type type2, TypeAdapter<V> typeAdapter2, ObjectConstructor<? extends Map<K, V>> objectConstructor) {
            this.a = new aa(gson, typeAdapter, type);
            this.b = new aa(gson, typeAdapter2, type2);
            this.c = objectConstructor;
        }

        @Override // com.google.gson.TypeAdapter
        public Object read(JsonReader jsonReader) throws IOException {
            JsonToken jsonTokenPeek = jsonReader.peek();
            if (jsonTokenPeek == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            Map<K, V> mapConstruct = this.c.construct();
            if (jsonTokenPeek == JsonToken.BEGIN_ARRAY) {
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    jsonReader.beginArray();
                    K k = this.a.read(jsonReader);
                    if (mapConstruct.put(k, this.b.read(jsonReader)) != null) {
                        throw new JsonSyntaxException("duplicate key: " + k);
                    }
                    jsonReader.endArray();
                }
                jsonReader.endArray();
            } else {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    if (((JsonReader.a) JsonReaderInternalAccess.INSTANCE) == null) {
                        throw null;
                    }
                    if (jsonReader instanceof JsonTreeReader) {
                        ((JsonTreeReader) jsonReader).promoteNameToValue();
                    } else {
                        int iB = jsonReader.h;
                        if (iB == 0) {
                            iB = jsonReader.b();
                        }
                        if (iB == 13) {
                            jsonReader.h = 9;
                        } else if (iB == 12) {
                            jsonReader.h = 8;
                        } else {
                            if (iB != 14) {
                                StringBuilder sbA = g9.a("Expected a name but was ");
                                sbA.append(jsonReader.peek());
                                sbA.append(jsonReader.c());
                                throw new IllegalStateException(sbA.toString());
                            }
                            jsonReader.h = 10;
                        }
                    }
                    K k2 = this.a.read(jsonReader);
                    if (mapConstruct.put(k2, this.b.read(jsonReader)) != null) {
                        throw new JsonSyntaxException("duplicate key: " + k2);
                    }
                }
                jsonReader.endObject();
            }
            return mapConstruct;
        }

        @Override // com.google.gson.TypeAdapter
        public void write(JsonWriter jsonWriter, Object obj) throws IOException {
            String asString;
            Map map = (Map) obj;
            if (map == null) {
                jsonWriter.nullValue();
                return;
            }
            if (!MapTypeAdapterFactory.this.b) {
                jsonWriter.beginObject();
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    jsonWriter.name(String.valueOf(entry.getKey()));
                    this.b.write(jsonWriter, entry.getValue());
                }
                jsonWriter.endObject();
                return;
            }
            ArrayList arrayList = new ArrayList(map.size());
            ArrayList arrayList2 = new ArrayList(map.size());
            int i = 0;
            boolean z = false;
            for (Map.Entry<K, V> entry2 : map.entrySet()) {
                JsonElement jsonTree = this.a.toJsonTree(entry2.getKey());
                arrayList.add(jsonTree);
                arrayList2.add(entry2.getValue());
                z |= jsonTree.isJsonArray() || jsonTree.isJsonObject();
            }
            if (z) {
                jsonWriter.beginArray();
                int size = arrayList.size();
                while (i < size) {
                    jsonWriter.beginArray();
                    Streams.write((JsonElement) arrayList.get(i), jsonWriter);
                    this.b.write(jsonWriter, arrayList2.get(i));
                    jsonWriter.endArray();
                    i++;
                }
                jsonWriter.endArray();
                return;
            }
            jsonWriter.beginObject();
            int size2 = arrayList.size();
            while (i < size2) {
                JsonElement jsonElement = (JsonElement) arrayList.get(i);
                if (jsonElement.isJsonPrimitive()) {
                    JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonPrimitive();
                    if (asJsonPrimitive.isNumber()) {
                        asString = String.valueOf(asJsonPrimitive.getAsNumber());
                    } else if (asJsonPrimitive.isBoolean()) {
                        asString = Boolean.toString(asJsonPrimitive.getAsBoolean());
                    } else {
                        if (!asJsonPrimitive.isString()) {
                            throw new AssertionError();
                        }
                        asString = asJsonPrimitive.getAsString();
                    }
                } else {
                    if (!jsonElement.isJsonNull()) {
                        throw new AssertionError();
                    }
                    asString = "null";
                }
                jsonWriter.name(asString);
                this.b.write(jsonWriter, arrayList2.get(i));
                i++;
            }
            jsonWriter.endObject();
        }
    }

    public MapTypeAdapterFactory(ConstructorConstructor constructorConstructor, boolean z) {
        this.a = constructorConstructor;
        this.b = z;
    }

    @Override // com.google.gson.TypeAdapterFactory
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) throws NoSuchMethodException, SecurityException {
        Type type = typeToken.getType();
        if (!Map.class.isAssignableFrom(typeToken.getRawType())) {
            return null;
        }
        Type[] mapKeyAndValueTypes = C$Gson$Types.getMapKeyAndValueTypes(type, C$Gson$Types.getRawType(type));
        Type type2 = mapKeyAndValueTypes[0];
        return new a(gson, mapKeyAndValueTypes[0], (type2 == Boolean.TYPE || type2 == Boolean.class) ? TypeAdapters.BOOLEAN_AS_STRING : gson.getAdapter(TypeToken.get(type2)), mapKeyAndValueTypes[1], gson.getAdapter(TypeToken.get(mapKeyAndValueTypes[1])), this.a.get(typeToken));
    }
}
