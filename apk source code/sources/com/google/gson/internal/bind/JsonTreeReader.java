package com.google.gson.internal.bind;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import defpackage.g9;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.Map;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes.dex */
public final class JsonTreeReader extends JsonReader {
    public static final Reader u = new a();
    public static final Object v = new Object();
    public Object[] q;
    public int r;
    public String[] s;
    public int[] t;

    public static class a extends Reader {
        @Override // java.io.Reader, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            throw new AssertionError();
        }

        @Override // java.io.Reader
        public int read(char[] cArr, int i, int i2) throws IOException {
            throw new AssertionError();
        }
    }

    public JsonTreeReader(JsonElement jsonElement) {
        super(u);
        this.q = new Object[32];
        this.r = 0;
        this.s = new String[32];
        this.t = new int[32];
        a(jsonElement);
    }

    private String c() {
        StringBuilder sbA = g9.a(" at path ");
        sbA.append(getPath());
        return sbA.toString();
    }

    public final void a(JsonToken jsonToken) throws IOException {
        if (peek() == jsonToken) {
            return;
        }
        throw new IllegalStateException("Expected " + jsonToken + " but was " + peek() + c());
    }

    @Override // com.google.gson.stream.JsonReader
    public void beginArray() throws IOException {
        a(JsonToken.BEGIN_ARRAY);
        a(((JsonArray) g()).iterator());
        this.t[this.r - 1] = 0;
    }

    @Override // com.google.gson.stream.JsonReader
    public void beginObject() throws IOException {
        a(JsonToken.BEGIN_OBJECT);
        a(((JsonObject) g()).entrySet().iterator());
    }

    @Override // com.google.gson.stream.JsonReader, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.q = new Object[]{v};
        this.r = 1;
    }

    @Override // com.google.gson.stream.JsonReader
    public void endArray() throws IOException {
        a(JsonToken.END_ARRAY);
        h();
        h();
        int i = this.r;
        if (i > 0) {
            int[] iArr = this.t;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
        }
    }

    @Override // com.google.gson.stream.JsonReader
    public void endObject() throws IOException {
        a(JsonToken.END_OBJECT);
        h();
        h();
        int i = this.r;
        if (i > 0) {
            int[] iArr = this.t;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
        }
    }

    public final Object g() {
        return this.q[this.r - 1];
    }

    @Override // com.google.gson.stream.JsonReader
    public String getPath() {
        StringBuilder sb = new StringBuilder();
        sb.append('$');
        int i = 0;
        while (i < this.r) {
            Object[] objArr = this.q;
            if (objArr[i] instanceof JsonArray) {
                i++;
                if (objArr[i] instanceof Iterator) {
                    sb.append('[');
                    sb.append(this.t[i]);
                    sb.append(']');
                }
            } else if (objArr[i] instanceof JsonObject) {
                i++;
                if (objArr[i] instanceof Iterator) {
                    sb.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
                    String[] strArr = this.s;
                    if (strArr[i] != null) {
                        sb.append(strArr[i]);
                    }
                }
            }
            i++;
        }
        return sb.toString();
    }

    public final Object h() {
        Object[] objArr = this.q;
        int i = this.r - 1;
        this.r = i;
        Object obj = objArr[i];
        objArr[i] = null;
        return obj;
    }

    @Override // com.google.gson.stream.JsonReader
    public boolean hasNext() throws IOException {
        JsonToken jsonTokenPeek = peek();
        return (jsonTokenPeek == JsonToken.END_OBJECT || jsonTokenPeek == JsonToken.END_ARRAY) ? false : true;
    }

    @Override // com.google.gson.stream.JsonReader
    public boolean nextBoolean() throws IOException {
        a(JsonToken.BOOLEAN);
        boolean asBoolean = ((JsonPrimitive) h()).getAsBoolean();
        int i = this.r;
        if (i > 0) {
            int[] iArr = this.t;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
        }
        return asBoolean;
    }

    @Override // com.google.gson.stream.JsonReader
    public double nextDouble() throws IOException {
        JsonToken jsonTokenPeek = peek();
        if (jsonTokenPeek != JsonToken.NUMBER && jsonTokenPeek != JsonToken.STRING) {
            StringBuilder sbA = g9.a("Expected ");
            sbA.append(JsonToken.NUMBER);
            sbA.append(" but was ");
            sbA.append(jsonTokenPeek);
            sbA.append(c());
            throw new IllegalStateException(sbA.toString());
        }
        double asDouble = ((JsonPrimitive) g()).getAsDouble();
        if (!isLenient() && (Double.isNaN(asDouble) || Double.isInfinite(asDouble))) {
            throw new NumberFormatException("JSON forbids NaN and infinities: " + asDouble);
        }
        h();
        int i = this.r;
        if (i > 0) {
            int[] iArr = this.t;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
        }
        return asDouble;
    }

    @Override // com.google.gson.stream.JsonReader
    public int nextInt() throws IOException {
        JsonToken jsonTokenPeek = peek();
        if (jsonTokenPeek != JsonToken.NUMBER && jsonTokenPeek != JsonToken.STRING) {
            StringBuilder sbA = g9.a("Expected ");
            sbA.append(JsonToken.NUMBER);
            sbA.append(" but was ");
            sbA.append(jsonTokenPeek);
            sbA.append(c());
            throw new IllegalStateException(sbA.toString());
        }
        int asInt = ((JsonPrimitive) g()).getAsInt();
        h();
        int i = this.r;
        if (i > 0) {
            int[] iArr = this.t;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
        }
        return asInt;
    }

    @Override // com.google.gson.stream.JsonReader
    public long nextLong() throws IOException {
        JsonToken jsonTokenPeek = peek();
        if (jsonTokenPeek != JsonToken.NUMBER && jsonTokenPeek != JsonToken.STRING) {
            StringBuilder sbA = g9.a("Expected ");
            sbA.append(JsonToken.NUMBER);
            sbA.append(" but was ");
            sbA.append(jsonTokenPeek);
            sbA.append(c());
            throw new IllegalStateException(sbA.toString());
        }
        long asLong = ((JsonPrimitive) g()).getAsLong();
        h();
        int i = this.r;
        if (i > 0) {
            int[] iArr = this.t;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
        }
        return asLong;
    }

    @Override // com.google.gson.stream.JsonReader
    public String nextName() throws IOException {
        a(JsonToken.NAME);
        Map.Entry entry = (Map.Entry) ((Iterator) g()).next();
        String str = (String) entry.getKey();
        this.s[this.r - 1] = str;
        a(entry.getValue());
        return str;
    }

    @Override // com.google.gson.stream.JsonReader
    public void nextNull() throws IOException {
        a(JsonToken.NULL);
        h();
        int i = this.r;
        if (i > 0) {
            int[] iArr = this.t;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
        }
    }

    @Override // com.google.gson.stream.JsonReader
    public String nextString() throws IOException {
        JsonToken jsonTokenPeek = peek();
        if (jsonTokenPeek != JsonToken.STRING && jsonTokenPeek != JsonToken.NUMBER) {
            StringBuilder sbA = g9.a("Expected ");
            sbA.append(JsonToken.STRING);
            sbA.append(" but was ");
            sbA.append(jsonTokenPeek);
            sbA.append(c());
            throw new IllegalStateException(sbA.toString());
        }
        String asString = ((JsonPrimitive) h()).getAsString();
        int i = this.r;
        if (i > 0) {
            int[] iArr = this.t;
            int i2 = i - 1;
            iArr[i2] = iArr[i2] + 1;
        }
        return asString;
    }

    @Override // com.google.gson.stream.JsonReader
    public JsonToken peek() throws IOException {
        if (this.r == 0) {
            return JsonToken.END_DOCUMENT;
        }
        Object objG = g();
        if (objG instanceof Iterator) {
            boolean z = this.q[this.r - 2] instanceof JsonObject;
            Iterator it = (Iterator) objG;
            if (!it.hasNext()) {
                return z ? JsonToken.END_OBJECT : JsonToken.END_ARRAY;
            }
            if (z) {
                return JsonToken.NAME;
            }
            a(it.next());
            return peek();
        }
        if (objG instanceof JsonObject) {
            return JsonToken.BEGIN_OBJECT;
        }
        if (objG instanceof JsonArray) {
            return JsonToken.BEGIN_ARRAY;
        }
        if (!(objG instanceof JsonPrimitive)) {
            if (objG instanceof JsonNull) {
                return JsonToken.NULL;
            }
            if (objG == v) {
                throw new IllegalStateException("JsonReader is closed");
            }
            throw new AssertionError();
        }
        JsonPrimitive jsonPrimitive = (JsonPrimitive) objG;
        if (jsonPrimitive.isString()) {
            return JsonToken.STRING;
        }
        if (jsonPrimitive.isBoolean()) {
            return JsonToken.BOOLEAN;
        }
        if (jsonPrimitive.isNumber()) {
            return JsonToken.NUMBER;
        }
        throw new AssertionError();
    }

    public void promoteNameToValue() throws IOException {
        a(JsonToken.NAME);
        Map.Entry entry = (Map.Entry) ((Iterator) g()).next();
        a(entry.getValue());
        a(new JsonPrimitive((String) entry.getKey()));
    }

    @Override // com.google.gson.stream.JsonReader
    public void skipValue() throws IOException {
        if (peek() == JsonToken.NAME) {
            nextName();
            this.s[this.r - 2] = "null";
        } else {
            h();
            int i = this.r;
            if (i > 0) {
                this.s[i - 1] = "null";
            }
        }
        int i2 = this.r;
        if (i2 > 0) {
            int[] iArr = this.t;
            int i3 = i2 - 1;
            iArr[i3] = iArr[i3] + 1;
        }
    }

    @Override // com.google.gson.stream.JsonReader
    public String toString() {
        return JsonTreeReader.class.getSimpleName();
    }

    public final void a(Object obj) {
        int i = this.r;
        Object[] objArr = this.q;
        if (i == objArr.length) {
            Object[] objArr2 = new Object[i * 2];
            int[] iArr = new int[i * 2];
            String[] strArr = new String[i * 2];
            System.arraycopy(objArr, 0, objArr2, 0, i);
            System.arraycopy(this.t, 0, iArr, 0, this.r);
            System.arraycopy(this.s, 0, strArr, 0, this.r);
            this.q = objArr2;
            this.t = iArr;
            this.s = strArr;
        }
        Object[] objArr3 = this.q;
        int i2 = this.r;
        this.r = i2 + 1;
        objArr3[i2] = obj;
    }
}
