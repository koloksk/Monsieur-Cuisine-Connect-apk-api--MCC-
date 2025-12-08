package com.google.gson;

import com.google.gson.internal.LinkedTreeMap;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class JsonObject extends JsonElement {
    public final LinkedTreeMap<String, JsonElement> a = new LinkedTreeMap<>();

    public final JsonElement a(Object obj) {
        return obj == null ? JsonNull.INSTANCE : new JsonPrimitive(obj);
    }

    public void add(String str, JsonElement jsonElement) {
        if (jsonElement == null) {
            jsonElement = JsonNull.INSTANCE;
        }
        this.a.put(str, jsonElement);
    }

    public void addProperty(String str, String str2) {
        add(str, a(str2));
    }

    public Set<Map.Entry<String, JsonElement>> entrySet() {
        return this.a.entrySet();
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof JsonObject) && ((JsonObject) obj).a.equals(this.a));
    }

    public JsonElement get(String str) {
        return this.a.get(str);
    }

    public JsonArray getAsJsonArray(String str) {
        return (JsonArray) this.a.get(str);
    }

    public JsonObject getAsJsonObject(String str) {
        return (JsonObject) this.a.get(str);
    }

    public JsonPrimitive getAsJsonPrimitive(String str) {
        return (JsonPrimitive) this.a.get(str);
    }

    public boolean has(String str) {
        return this.a.containsKey(str);
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    public Set<String> keySet() {
        return this.a.keySet();
    }

    public JsonElement remove(String str) {
        return this.a.remove(str);
    }

    public int size() {
        return this.a.size();
    }

    public void addProperty(String str, Number number) {
        add(str, a(number));
    }

    @Override // com.google.gson.JsonElement
    public JsonObject deepCopy() {
        JsonObject jsonObject = new JsonObject();
        for (Map.Entry<String, JsonElement> entry : this.a.entrySet()) {
            jsonObject.add(entry.getKey(), entry.getValue().deepCopy());
        }
        return jsonObject;
    }

    public void addProperty(String str, Boolean bool) {
        add(str, a(bool));
    }

    public void addProperty(String str, Character ch) {
        add(str, a(ch));
    }
}
