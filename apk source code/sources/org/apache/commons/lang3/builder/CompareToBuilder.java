package org.apache.commons.lang3.builder;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Comparator;
import org.apache.commons.lang3.ArrayUtils;

/* loaded from: classes.dex */
public class CompareToBuilder implements Builder<Integer> {
    public int a = 0;

    public static void a(Object obj, Object obj2, Class<?> cls, CompareToBuilder compareToBuilder, boolean z, String[] strArr) throws SecurityException {
        Field[] declaredFields = cls.getDeclaredFields();
        AccessibleObject.setAccessible(declaredFields, true);
        for (int i = 0; i < declaredFields.length && compareToBuilder.a == 0; i++) {
            Field field = declaredFields[i];
            if (!ArrayUtils.contains(strArr, field.getName()) && !field.getName().contains("$") && ((z || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers()))) {
                try {
                    compareToBuilder.append(field.get(obj), field.get(obj2));
                } catch (IllegalAccessException unused) {
                    throw new InternalError("Unexpected IllegalAccessException");
                }
            }
        }
    }

    public static int reflectionCompare(Object obj, Object obj2) {
        return reflectionCompare(obj, obj2, false, null, new String[0]);
    }

    public CompareToBuilder append(Object obj, Object obj2) {
        return append(obj, obj2, (Comparator<?>) null);
    }

    public CompareToBuilder appendSuper(int i) {
        if (this.a != 0) {
            return this;
        }
        this.a = i;
        return this;
    }

    public int toComparison() {
        return this.a;
    }

    public static int reflectionCompare(Object obj, Object obj2, boolean z) {
        return reflectionCompare(obj, obj2, z, null, new String[0]);
    }

    public CompareToBuilder append(Object obj, Object obj2, Comparator<?> comparator) {
        if (this.a != 0 || obj == obj2) {
            return this;
        }
        if (obj == null) {
            this.a = -1;
            return this;
        }
        if (obj2 == null) {
            this.a = 1;
            return this;
        }
        if (obj.getClass().isArray()) {
            if (obj instanceof long[]) {
                append((long[]) obj, (long[]) obj2);
            } else if (obj instanceof int[]) {
                append((int[]) obj, (int[]) obj2);
            } else if (obj instanceof short[]) {
                append((short[]) obj, (short[]) obj2);
            } else if (obj instanceof char[]) {
                append((char[]) obj, (char[]) obj2);
            } else if (obj instanceof byte[]) {
                append((byte[]) obj, (byte[]) obj2);
            } else if (obj instanceof double[]) {
                append((double[]) obj, (double[]) obj2);
            } else if (obj instanceof float[]) {
                append((float[]) obj, (float[]) obj2);
            } else if (obj instanceof boolean[]) {
                append((boolean[]) obj, (boolean[]) obj2);
            } else {
                append((Object[]) obj, (Object[]) obj2, comparator);
            }
        } else if (comparator == null) {
            this.a = ((Comparable) obj).compareTo(obj2);
        } else {
            this.a = comparator.compare(obj, obj2);
        }
        return this;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.apache.commons.lang3.builder.Builder
    public Integer build() {
        return Integer.valueOf(toComparison());
    }

    public static int reflectionCompare(Object obj, Object obj2, Collection<String> collection) {
        return reflectionCompare(obj, obj2, ReflectionToStringBuilder.a(collection));
    }

    public static int reflectionCompare(Object obj, Object obj2, String... strArr) {
        return reflectionCompare(obj, obj2, false, null, strArr);
    }

    public static int reflectionCompare(Object obj, Object obj2, boolean z, Class<?> cls, String... strArr) throws SecurityException {
        if (obj == obj2) {
            return 0;
        }
        if (obj != null && obj2 != null) {
            Class<?> superclass = obj.getClass();
            if (superclass.isInstance(obj2)) {
                CompareToBuilder compareToBuilder = new CompareToBuilder();
                a(obj, obj2, superclass, compareToBuilder, z, strArr);
                while (superclass.getSuperclass() != null && superclass != cls) {
                    superclass = superclass.getSuperclass();
                    a(obj, obj2, superclass, compareToBuilder, z, strArr);
                }
                return compareToBuilder.toComparison();
            }
            throw new ClassCastException();
        }
        throw null;
    }

    public CompareToBuilder append(long j, long j2) {
        if (this.a != 0) {
            return this;
        }
        this.a = Long.compare(j, j2);
        return this;
    }

    public CompareToBuilder append(int i, int i2) {
        if (this.a != 0) {
            return this;
        }
        this.a = Integer.compare(i, i2);
        return this;
    }

    public CompareToBuilder append(short s, short s2) {
        if (this.a != 0) {
            return this;
        }
        this.a = Short.compare(s, s2);
        return this;
    }

    public CompareToBuilder append(char c, char c2) {
        if (this.a != 0) {
            return this;
        }
        this.a = Character.compare(c, c2);
        return this;
    }

    public CompareToBuilder append(byte b, byte b2) {
        if (this.a != 0) {
            return this;
        }
        this.a = Byte.compare(b, b2);
        return this;
    }

    public CompareToBuilder append(double d, double d2) {
        if (this.a != 0) {
            return this;
        }
        this.a = Double.compare(d, d2);
        return this;
    }

    public CompareToBuilder append(float f, float f2) {
        if (this.a != 0) {
            return this;
        }
        this.a = Float.compare(f, f2);
        return this;
    }

    public CompareToBuilder append(boolean z, boolean z2) {
        if (this.a != 0 || z == z2) {
            return this;
        }
        if (z) {
            this.a = 1;
        } else {
            this.a = -1;
        }
        return this;
    }

    public CompareToBuilder append(Object[] objArr, Object[] objArr2) {
        return append(objArr, objArr2, (Comparator<?>) null);
    }

    public CompareToBuilder append(Object[] objArr, Object[] objArr2, Comparator<?> comparator) {
        if (this.a != 0 || objArr == objArr2) {
            return this;
        }
        if (objArr == null) {
            this.a = -1;
            return this;
        }
        if (objArr2 == null) {
            this.a = 1;
            return this;
        }
        if (objArr.length != objArr2.length) {
            this.a = objArr.length >= objArr2.length ? 1 : -1;
            return this;
        }
        for (int i = 0; i < objArr.length && this.a == 0; i++) {
            append(objArr[i], objArr2[i], comparator);
        }
        return this;
    }

    public CompareToBuilder append(long[] jArr, long[] jArr2) {
        if (this.a != 0 || jArr == jArr2) {
            return this;
        }
        if (jArr == null) {
            this.a = -1;
            return this;
        }
        if (jArr2 == null) {
            this.a = 1;
            return this;
        }
        if (jArr.length != jArr2.length) {
            this.a = jArr.length >= jArr2.length ? 1 : -1;
            return this;
        }
        for (int i = 0; i < jArr.length && this.a == 0; i++) {
            append(jArr[i], jArr2[i]);
        }
        return this;
    }

    public CompareToBuilder append(int[] iArr, int[] iArr2) {
        if (this.a != 0 || iArr == iArr2) {
            return this;
        }
        if (iArr == null) {
            this.a = -1;
            return this;
        }
        if (iArr2 == null) {
            this.a = 1;
            return this;
        }
        if (iArr.length != iArr2.length) {
            this.a = iArr.length >= iArr2.length ? 1 : -1;
            return this;
        }
        for (int i = 0; i < iArr.length && this.a == 0; i++) {
            append(iArr[i], iArr2[i]);
        }
        return this;
    }

    public CompareToBuilder append(short[] sArr, short[] sArr2) {
        if (this.a != 0 || sArr == sArr2) {
            return this;
        }
        if (sArr == null) {
            this.a = -1;
            return this;
        }
        if (sArr2 == null) {
            this.a = 1;
            return this;
        }
        if (sArr.length != sArr2.length) {
            this.a = sArr.length >= sArr2.length ? 1 : -1;
            return this;
        }
        for (int i = 0; i < sArr.length && this.a == 0; i++) {
            append(sArr[i], sArr2[i]);
        }
        return this;
    }

    public CompareToBuilder append(char[] cArr, char[] cArr2) {
        if (this.a != 0 || cArr == cArr2) {
            return this;
        }
        if (cArr == null) {
            this.a = -1;
            return this;
        }
        if (cArr2 == null) {
            this.a = 1;
            return this;
        }
        if (cArr.length != cArr2.length) {
            this.a = cArr.length >= cArr2.length ? 1 : -1;
            return this;
        }
        for (int i = 0; i < cArr.length && this.a == 0; i++) {
            append(cArr[i], cArr2[i]);
        }
        return this;
    }

    public CompareToBuilder append(byte[] bArr, byte[] bArr2) {
        if (this.a != 0 || bArr == bArr2) {
            return this;
        }
        if (bArr == null) {
            this.a = -1;
            return this;
        }
        if (bArr2 == null) {
            this.a = 1;
            return this;
        }
        if (bArr.length != bArr2.length) {
            this.a = bArr.length >= bArr2.length ? 1 : -1;
            return this;
        }
        for (int i = 0; i < bArr.length && this.a == 0; i++) {
            append(bArr[i], bArr2[i]);
        }
        return this;
    }

    public CompareToBuilder append(double[] dArr, double[] dArr2) {
        if (this.a != 0 || dArr == dArr2) {
            return this;
        }
        if (dArr == null) {
            this.a = -1;
            return this;
        }
        if (dArr2 == null) {
            this.a = 1;
            return this;
        }
        if (dArr.length != dArr2.length) {
            this.a = dArr.length >= dArr2.length ? 1 : -1;
            return this;
        }
        for (int i = 0; i < dArr.length && this.a == 0; i++) {
            append(dArr[i], dArr2[i]);
        }
        return this;
    }

    public CompareToBuilder append(float[] fArr, float[] fArr2) {
        if (this.a != 0 || fArr == fArr2) {
            return this;
        }
        if (fArr == null) {
            this.a = -1;
            return this;
        }
        if (fArr2 == null) {
            this.a = 1;
            return this;
        }
        if (fArr.length != fArr2.length) {
            this.a = fArr.length >= fArr2.length ? 1 : -1;
            return this;
        }
        for (int i = 0; i < fArr.length && this.a == 0; i++) {
            append(fArr[i], fArr2[i]);
        }
        return this;
    }

    public CompareToBuilder append(boolean[] zArr, boolean[] zArr2) {
        if (this.a != 0 || zArr == zArr2) {
            return this;
        }
        if (zArr == null) {
            this.a = -1;
            return this;
        }
        if (zArr2 == null) {
            this.a = 1;
            return this;
        }
        if (zArr.length != zArr2.length) {
            this.a = zArr.length >= zArr2.length ? 1 : -1;
            return this;
        }
        for (int i = 0; i < zArr.length && this.a == 0; i++) {
            append(zArr[i], zArr2[i]);
        }
        return this;
    }
}
