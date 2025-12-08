package com.google.gson;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class JsonArray extends JsonElement implements Iterable<JsonElement> {
    public final List<JsonElement> a;

    public JsonArray() {
        this.a = new ArrayList();
    }

    public void add(Boolean bool) {
        this.a.add(bool == null ? JsonNull.INSTANCE : new JsonPrimitive(bool));
    }

    public void addAll(JsonArray jsonArray) {
        this.a.addAll(jsonArray.a);
    }

    public boolean contains(JsonElement jsonElement) {
        return this.a.contains(jsonElement);
    }

    public boolean equals(Object obj) {
        return obj == this || ((obj instanceof JsonArray) && ((JsonArray) obj).a.equals(this.a));
    }

    public JsonElement get(int i) {
        return this.a.get(i);
    }

    @Override // com.google.gson.JsonElement
    public BigDecimal getAsBigDecimal() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsBigDecimal();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public BigInteger getAsBigInteger() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsBigInteger();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public boolean getAsBoolean() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsBoolean();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public byte getAsByte() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsByte();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public char getAsCharacter() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsCharacter();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public double getAsDouble() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsDouble();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public float getAsFloat() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsFloat();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public int getAsInt() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsInt();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public long getAsLong() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsLong();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public Number getAsNumber() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsNumber();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public short getAsShort() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsShort();
        }
        throw new IllegalStateException();
    }

    @Override // com.google.gson.JsonElement
    public String getAsString() {
        if (this.a.size() == 1) {
            return this.a.get(0).getAsString();
        }
        throw new IllegalStateException();
    }

    public int hashCode() {
        return this.a.hashCode();
    }

    @Override // java.lang.Iterable
    public Iterator<JsonElement> iterator() {
        return this.a.iterator();
    }

    public boolean remove(JsonElement jsonElement) {
        return this.a.remove(jsonElement);
    }

    public JsonElement set(int i, JsonElement jsonElement) {
        return this.a.set(i, jsonElement);
    }

    public int size() {
        return this.a.size();
    }

    public void add(Character ch) {
        this.a.add(ch == null ? JsonNull.INSTANCE : new JsonPrimitive(ch));
    }

    @Override // com.google.gson.JsonElement
    public JsonArray deepCopy() {
        if (this.a.isEmpty()) {
            return new JsonArray();
        }
        JsonArray jsonArray = new JsonArray(this.a.size());
        Iterator<JsonElement> it = this.a.iterator();
        while (it.hasNext()) {
            jsonArray.add(it.next().deepCopy());
        }
        return jsonArray;
    }

    public JsonElement remove(int i) {
        return this.a.remove(i);
    }

    public JsonArray(int i) {
        this.a = new ArrayList(i);
    }

    public void add(Number number) {
        this.a.add(number == null ? JsonNull.INSTANCE : new JsonPrimitive(number));
    }

    public void add(String str) {
        this.a.add(str == null ? JsonNull.INSTANCE : new JsonPrimitive(str));
    }

    public void add(JsonElement jsonElement) {
        if (jsonElement == null) {
            jsonElement = JsonNull.INSTANCE;
        }
        this.a.add(jsonElement);
    }
}
