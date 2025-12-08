package org.apache.commons.lang3.builder;

import defpackage.g9;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class MultilineRecursiveToStringStyle extends RecursiveToStringStyle {
    public static final long serialVersionUID = 1;
    public int v = 2;

    public MultilineRecursiveToStringStyle() {
        b();
    }

    public final StringBuilder a(int i) {
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(StringUtils.SPACE);
        }
        return sb;
    }

    @Override // org.apache.commons.lang3.builder.RecursiveToStringStyle, org.apache.commons.lang3.builder.ToStringStyle
    public void appendDetail(StringBuffer stringBuffer, String str, Object obj) {
        if (ClassUtils.isPrimitiveWrapper(obj.getClass()) || String.class.equals(obj.getClass()) || !accept(obj.getClass())) {
            super.appendDetail(stringBuffer, str, obj);
            return;
        }
        this.v += 2;
        b();
        stringBuffer.append(ReflectionToStringBuilder.toString(obj, this));
        this.v -= 2;
        b();
    }

    public final void b() {
        StringBuilder sbA = g9.a("{");
        sbA.append(System.lineSeparator());
        sbA.append((Object) a(this.v));
        setArrayStart(sbA.toString());
        setArraySeparator("," + System.lineSeparator() + ((Object) a(this.v)));
        setArrayEnd(System.lineSeparator() + ((Object) a(this.v + (-2))) + "}");
        setContentStart("[" + System.lineSeparator() + ((Object) a(this.v)));
        setFieldSeparator("," + System.lineSeparator() + ((Object) a(this.v)));
        setContentEnd(System.lineSeparator() + ((Object) a(this.v + (-2))) + "]");
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    public void reflectionAppendArrayDetail(StringBuffer stringBuffer, String str, Object obj) throws ArrayIndexOutOfBoundsException, IllegalArgumentException {
        this.v += 2;
        b();
        super.reflectionAppendArrayDetail(stringBuffer, str, obj);
        this.v -= 2;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    public void appendDetail(StringBuffer stringBuffer, String str, Object[] objArr) {
        this.v += 2;
        b();
        super.appendDetail(stringBuffer, str, objArr);
        this.v -= 2;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    public void appendDetail(StringBuffer stringBuffer, String str, long[] jArr) {
        this.v += 2;
        b();
        super.appendDetail(stringBuffer, str, jArr);
        this.v -= 2;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    public void appendDetail(StringBuffer stringBuffer, String str, int[] iArr) {
        this.v += 2;
        b();
        super.appendDetail(stringBuffer, str, iArr);
        this.v -= 2;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    public void appendDetail(StringBuffer stringBuffer, String str, short[] sArr) {
        this.v += 2;
        b();
        super.appendDetail(stringBuffer, str, sArr);
        this.v -= 2;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    public void appendDetail(StringBuffer stringBuffer, String str, byte[] bArr) {
        this.v += 2;
        b();
        super.appendDetail(stringBuffer, str, bArr);
        this.v -= 2;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    public void appendDetail(StringBuffer stringBuffer, String str, char[] cArr) {
        this.v += 2;
        b();
        super.appendDetail(stringBuffer, str, cArr);
        this.v -= 2;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    public void appendDetail(StringBuffer stringBuffer, String str, double[] dArr) {
        this.v += 2;
        b();
        super.appendDetail(stringBuffer, str, dArr);
        this.v -= 2;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    public void appendDetail(StringBuffer stringBuffer, String str, float[] fArr) {
        this.v += 2;
        b();
        super.appendDetail(stringBuffer, str, fArr);
        this.v -= 2;
        b();
    }

    @Override // org.apache.commons.lang3.builder.ToStringStyle
    public void appendDetail(StringBuffer stringBuffer, String str, boolean[] zArr) {
        this.v += 2;
        b();
        super.appendDetail(stringBuffer, str, zArr);
        this.v -= 2;
        b();
    }
}
