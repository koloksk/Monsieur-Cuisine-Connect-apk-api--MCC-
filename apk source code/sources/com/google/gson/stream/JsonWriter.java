package com.google.gson.stream;

import defpackage.g9;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class JsonWriter implements Closeable, Flushable {
    public static final String[] j = new String[128];
    public static final String[] k;
    public final Writer a;
    public int[] b = new int[32];
    public int c = 0;
    public String d;
    public String e;
    public boolean f;
    public boolean g;
    public String h;
    public boolean i;

    static {
        for (int i = 0; i <= 31; i++) {
            j[i] = String.format("\\u%04x", Integer.valueOf(i));
        }
        String[] strArr = j;
        strArr[34] = "\\\"";
        strArr[92] = "\\\\";
        strArr[9] = "\\t";
        strArr[8] = "\\b";
        strArr[10] = "\\n";
        strArr[13] = "\\r";
        strArr[12] = "\\f";
        String[] strArr2 = (String[]) strArr.clone();
        k = strArr2;
        strArr2[60] = "\\u003c";
        strArr2[62] = "\\u003e";
        strArr2[38] = "\\u0026";
        strArr2[61] = "\\u003d";
        strArr2[39] = "\\u0027";
    }

    public JsonWriter(Writer writer) {
        a(6);
        this.e = ":";
        this.i = true;
        if (writer == null) {
            throw new NullPointerException("out == null");
        }
        this.a = writer;
    }

    public final JsonWriter a(int i, int i2, String str) throws IOException {
        int iPeek = peek();
        if (iPeek != i2 && iPeek != i) {
            throw new IllegalStateException("Nesting problem.");
        }
        if (this.h != null) {
            StringBuilder sbA = g9.a("Dangling name: ");
            sbA.append(this.h);
            throw new IllegalStateException(sbA.toString());
        }
        this.c--;
        if (iPeek == i2) {
            b();
        }
        this.a.write(str);
        return this;
    }

    public final void b(int i) {
        this.b[this.c - 1] = i;
    }

    public JsonWriter beginArray() throws IOException {
        c();
        a();
        a(1);
        this.a.write("[");
        return this;
    }

    public JsonWriter beginObject() throws IOException {
        c();
        a();
        a(3);
        this.a.write("{");
        return this;
    }

    public final void c() throws IOException {
        if (this.h != null) {
            int iPeek = peek();
            if (iPeek == 5) {
                this.a.write(44);
            } else if (iPeek != 3) {
                throw new IllegalStateException("Nesting problem.");
            }
            b();
            b(4);
            a(this.h);
            this.h = null;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.a.close();
        int i = this.c;
        if (i > 1 || (i == 1 && this.b[i - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.c = 0;
    }

    public JsonWriter endArray() throws IOException {
        a(1, 2, "]");
        return this;
    }

    public JsonWriter endObject() throws IOException {
        a(3, 5, "}");
        return this;
    }

    public void flush() throws IOException {
        if (this.c == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.a.flush();
    }

    public final boolean getSerializeNulls() {
        return this.i;
    }

    public final boolean isHtmlSafe() {
        return this.g;
    }

    public boolean isLenient() {
        return this.f;
    }

    public JsonWriter jsonValue(String str) throws IOException {
        if (str == null) {
            return nullValue();
        }
        c();
        a();
        this.a.append((CharSequence) str);
        return this;
    }

    public JsonWriter name(String str) throws IOException {
        if (str == null) {
            throw new NullPointerException("name == null");
        }
        if (this.h != null) {
            throw new IllegalStateException();
        }
        if (this.c == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.h = str;
        return this;
    }

    public JsonWriter nullValue() throws IOException {
        if (this.h != null) {
            if (!this.i) {
                this.h = null;
                return this;
            }
            c();
        }
        a();
        this.a.write("null");
        return this;
    }

    public final int peek() {
        int i = this.c;
        if (i != 0) {
            return this.b[i - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    public final void setHtmlSafe(boolean z) {
        this.g = z;
    }

    public final void setIndent(String str) {
        if (str.length() == 0) {
            this.d = null;
            this.e = ":";
        } else {
            this.d = str;
            this.e = ": ";
        }
    }

    public final void setLenient(boolean z) {
        this.f = z;
    }

    public final void setSerializeNulls(boolean z) {
        this.i = z;
    }

    public JsonWriter value(String str) throws IOException {
        if (str == null) {
            return nullValue();
        }
        c();
        a();
        a(str);
        return this;
    }

    public final void b() throws IOException {
        if (this.d == null) {
            return;
        }
        this.a.write(StringUtils.LF);
        int i = this.c;
        for (int i2 = 1; i2 < i; i2++) {
            this.a.write(this.d);
        }
    }

    public JsonWriter value(boolean z) throws IOException {
        c();
        a();
        this.a.write(z ? "true" : "false");
        return this;
    }

    public final void a(int i) {
        int i2 = this.c;
        int[] iArr = this.b;
        if (i2 == iArr.length) {
            int[] iArr2 = new int[i2 * 2];
            System.arraycopy(iArr, 0, iArr2, 0, i2);
            this.b = iArr2;
        }
        int[] iArr3 = this.b;
        int i3 = this.c;
        this.c = i3 + 1;
        iArr3[i3] = i;
    }

    public JsonWriter value(Boolean bool) throws IOException {
        if (bool == null) {
            return nullValue();
        }
        c();
        a();
        this.a.write(bool.booleanValue() ? "true" : "false");
        return this;
    }

    public JsonWriter value(double d) throws IOException {
        c();
        if (!this.f && (Double.isNaN(d) || Double.isInfinite(d))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + d);
        }
        a();
        this.a.append((CharSequence) Double.toString(d));
        return this;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0034  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(java.lang.String r9) throws java.io.IOException {
        /*
            r8 = this;
            boolean r0 = r8.g
            if (r0 == 0) goto L7
            java.lang.String[] r0 = com.google.gson.stream.JsonWriter.k
            goto L9
        L7:
            java.lang.String[] r0 = com.google.gson.stream.JsonWriter.j
        L9:
            java.io.Writer r1 = r8.a
            java.lang.String r2 = "\""
            r1.write(r2)
            int r1 = r9.length()
            r3 = 0
            r4 = r3
        L16:
            if (r3 >= r1) goto L45
            char r5 = r9.charAt(r3)
            r6 = 128(0x80, float:1.794E-43)
            if (r5 >= r6) goto L25
            r5 = r0[r5]
            if (r5 != 0) goto L32
            goto L42
        L25:
            r6 = 8232(0x2028, float:1.1535E-41)
            if (r5 != r6) goto L2c
            java.lang.String r5 = "\\u2028"
            goto L32
        L2c:
            r6 = 8233(0x2029, float:1.1537E-41)
            if (r5 != r6) goto L42
            java.lang.String r5 = "\\u2029"
        L32:
            if (r4 >= r3) goto L3b
            java.io.Writer r6 = r8.a
            int r7 = r3 - r4
            r6.write(r9, r4, r7)
        L3b:
            java.io.Writer r4 = r8.a
            r4.write(r5)
            int r4 = r3 + 1
        L42:
            int r3 = r3 + 1
            goto L16
        L45:
            if (r4 >= r1) goto L4d
            java.io.Writer r0 = r8.a
            int r1 = r1 - r4
            r0.write(r9, r4, r1)
        L4d:
            java.io.Writer r9 = r8.a
            r9.write(r2)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.stream.JsonWriter.a(java.lang.String):void");
    }

    public JsonWriter value(long j2) throws IOException {
        c();
        a();
        this.a.write(Long.toString(j2));
        return this;
    }

    public JsonWriter value(Number number) throws IOException {
        if (number == null) {
            return nullValue();
        }
        c();
        String string = number.toString();
        if (!this.f && (string.equals("-Infinity") || string.equals("Infinity") || string.equals("NaN"))) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + number);
        }
        a();
        this.a.append((CharSequence) string);
        return this;
    }

    public final void a() throws IOException {
        int iPeek = peek();
        if (iPeek == 1) {
            b(2);
            b();
            return;
        }
        if (iPeek == 2) {
            this.a.append(',');
            b();
        } else {
            if (iPeek != 4) {
                if (iPeek != 6) {
                    if (iPeek == 7) {
                        if (!this.f) {
                            throw new IllegalStateException("JSON must have only one top-level value.");
                        }
                    } else {
                        throw new IllegalStateException("Nesting problem.");
                    }
                }
                b(7);
                return;
            }
            this.a.append((CharSequence) this.e);
            b(5);
        }
    }
}
