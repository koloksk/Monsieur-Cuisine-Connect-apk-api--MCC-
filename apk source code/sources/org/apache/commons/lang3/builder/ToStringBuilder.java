package org.apache.commons.lang3.builder;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;

/* loaded from: classes.dex */
public class ToStringBuilder implements Builder<String> {
    public static volatile ToStringStyle d = ToStringStyle.DEFAULT_STYLE;
    public final StringBuffer a;
    public final Object b;
    public final ToStringStyle c;

    public ToStringBuilder(Object obj) {
        this(obj, null, null);
    }

    public static ToStringStyle getDefaultStyle() {
        return d;
    }

    public static String reflectionToString(Object obj) {
        return ReflectionToStringBuilder.toString(obj);
    }

    public static void setDefaultStyle(ToStringStyle toStringStyle) {
        Validate.isTrue(toStringStyle != null, "The style must not be null", new Object[0]);
        d = toStringStyle;
    }

    public ToStringBuilder append(boolean z) {
        this.c.append(this.a, (String) null, z);
        return this;
    }

    public ToStringBuilder appendAsObjectToString(Object obj) {
        ObjectUtils.identityToString(getStringBuffer(), obj);
        return this;
    }

    public ToStringBuilder appendSuper(String str) {
        if (str != null) {
            this.c.appendSuper(this.a, str);
        }
        return this;
    }

    public ToStringBuilder appendToString(String str) {
        if (str != null) {
            this.c.appendToString(this.a, str);
        }
        return this;
    }

    public Object getObject() {
        return this.b;
    }

    public StringBuffer getStringBuffer() {
        return this.a;
    }

    public ToStringStyle getStyle() {
        return this.c;
    }

    public String toString() {
        if (getObject() == null) {
            getStringBuffer().append(getStyle().getNullText());
        } else {
            this.c.appendEnd(getStringBuffer(), getObject());
        }
        return getStringBuffer().toString();
    }

    public ToStringBuilder(Object obj, ToStringStyle toStringStyle) {
        this(obj, toStringStyle, null);
    }

    public static String reflectionToString(Object obj, ToStringStyle toStringStyle) {
        return ReflectionToStringBuilder.toString(obj, toStringStyle);
    }

    public ToStringBuilder append(boolean[] zArr) {
        this.c.append(this.a, (String) null, zArr, (Boolean) null);
        return this;
    }

    @Override // org.apache.commons.lang3.builder.Builder
    public String build() {
        return toString();
    }

    public ToStringBuilder(Object obj, ToStringStyle toStringStyle, StringBuffer stringBuffer) {
        toStringStyle = toStringStyle == null ? getDefaultStyle() : toStringStyle;
        stringBuffer = stringBuffer == null ? new StringBuffer(512) : stringBuffer;
        this.a = stringBuffer;
        this.c = toStringStyle;
        this.b = obj;
        toStringStyle.appendStart(stringBuffer, obj);
    }

    public static String reflectionToString(Object obj, ToStringStyle toStringStyle, boolean z) {
        return ReflectionToStringBuilder.toString(obj, toStringStyle, z, false, null);
    }

    public ToStringBuilder append(byte b) {
        this.c.append(this.a, (String) null, b);
        return this;
    }

    public static <T> String reflectionToString(T t, ToStringStyle toStringStyle, boolean z, Class<? super T> cls) {
        return ReflectionToStringBuilder.toString(t, toStringStyle, z, false, cls);
    }

    public ToStringBuilder append(byte[] bArr) {
        this.c.append(this.a, (String) null, bArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(char c) {
        this.c.append(this.a, (String) null, c);
        return this;
    }

    public ToStringBuilder append(char[] cArr) {
        this.c.append(this.a, (String) null, cArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(double d2) {
        this.c.append(this.a, (String) null, d2);
        return this;
    }

    public ToStringBuilder append(double[] dArr) {
        this.c.append(this.a, (String) null, dArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(float f) {
        this.c.append(this.a, (String) null, f);
        return this;
    }

    public ToStringBuilder append(float[] fArr) {
        this.c.append(this.a, (String) null, fArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(int i) {
        this.c.append(this.a, (String) null, i);
        return this;
    }

    public ToStringBuilder append(int[] iArr) {
        this.c.append(this.a, (String) null, iArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(long j) {
        this.c.append(this.a, (String) null, j);
        return this;
    }

    public ToStringBuilder append(long[] jArr) {
        this.c.append(this.a, (String) null, jArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(Object obj) {
        this.c.append(this.a, (String) null, obj, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(Object[] objArr) {
        this.c.append(this.a, (String) null, objArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(short s) {
        this.c.append(this.a, (String) null, s);
        return this;
    }

    public ToStringBuilder append(short[] sArr) {
        this.c.append(this.a, (String) null, sArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, boolean z) {
        this.c.append(this.a, str, z);
        return this;
    }

    public ToStringBuilder append(String str, boolean[] zArr) {
        this.c.append(this.a, str, zArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, boolean[] zArr, boolean z) {
        this.c.append(this.a, str, zArr, Boolean.valueOf(z));
        return this;
    }

    public ToStringBuilder append(String str, byte b) {
        this.c.append(this.a, str, b);
        return this;
    }

    public ToStringBuilder append(String str, byte[] bArr) {
        this.c.append(this.a, str, bArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, byte[] bArr, boolean z) {
        this.c.append(this.a, str, bArr, Boolean.valueOf(z));
        return this;
    }

    public ToStringBuilder append(String str, char c) {
        this.c.append(this.a, str, c);
        return this;
    }

    public ToStringBuilder append(String str, char[] cArr) {
        this.c.append(this.a, str, cArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, char[] cArr, boolean z) {
        this.c.append(this.a, str, cArr, Boolean.valueOf(z));
        return this;
    }

    public ToStringBuilder append(String str, double d2) {
        this.c.append(this.a, str, d2);
        return this;
    }

    public ToStringBuilder append(String str, double[] dArr) {
        this.c.append(this.a, str, dArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, double[] dArr, boolean z) {
        this.c.append(this.a, str, dArr, Boolean.valueOf(z));
        return this;
    }

    public ToStringBuilder append(String str, float f) {
        this.c.append(this.a, str, f);
        return this;
    }

    public ToStringBuilder append(String str, float[] fArr) {
        this.c.append(this.a, str, fArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, float[] fArr, boolean z) {
        this.c.append(this.a, str, fArr, Boolean.valueOf(z));
        return this;
    }

    public ToStringBuilder append(String str, int i) {
        this.c.append(this.a, str, i);
        return this;
    }

    public ToStringBuilder append(String str, int[] iArr) {
        this.c.append(this.a, str, iArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, int[] iArr, boolean z) {
        this.c.append(this.a, str, iArr, Boolean.valueOf(z));
        return this;
    }

    public ToStringBuilder append(String str, long j) {
        this.c.append(this.a, str, j);
        return this;
    }

    public ToStringBuilder append(String str, long[] jArr) {
        this.c.append(this.a, str, jArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, long[] jArr, boolean z) {
        this.c.append(this.a, str, jArr, Boolean.valueOf(z));
        return this;
    }

    public ToStringBuilder append(String str, Object obj) {
        this.c.append(this.a, str, obj, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, Object obj, boolean z) {
        this.c.append(this.a, str, obj, Boolean.valueOf(z));
        return this;
    }

    public ToStringBuilder append(String str, Object[] objArr) {
        this.c.append(this.a, str, objArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, Object[] objArr, boolean z) {
        this.c.append(this.a, str, objArr, Boolean.valueOf(z));
        return this;
    }

    public ToStringBuilder append(String str, short s) {
        this.c.append(this.a, str, s);
        return this;
    }

    public ToStringBuilder append(String str, short[] sArr) {
        this.c.append(this.a, str, sArr, (Boolean) null);
        return this;
    }

    public ToStringBuilder append(String str, short[] sArr, boolean z) {
        this.c.append(this.a, str, sArr, Boolean.valueOf(z));
        return this;
    }
}
