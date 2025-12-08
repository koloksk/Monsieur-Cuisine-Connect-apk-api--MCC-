package org.apache.commons.lang3.builder;

import defpackage.ko;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.tuple.Pair;

/* loaded from: classes.dex */
public class EqualsBuilder implements Builder<Boolean> {
    public static final ThreadLocal<Set<Pair<ko, ko>>> g = new ThreadLocal<>();
    public List<Class<?>> d;
    public boolean a = true;
    public boolean b = false;
    public boolean c = false;
    public Class<?> e = null;
    public String[] f = null;

    public EqualsBuilder() {
        ArrayList arrayList = new ArrayList();
        this.d = arrayList;
        arrayList.add(String.class);
    }

    public static Set<Pair<ko, ko>> a() {
        return g.get();
    }

    public static void b(Object obj, Object obj2) {
        Set<Pair<ko, ko>> setA = a();
        if (setA != null) {
            setA.remove(a(obj, obj2));
            if (setA.isEmpty()) {
                g.remove();
            }
        }
    }

    public static boolean reflectionEquals(Object obj, Object obj2, Collection<String> collection) {
        return reflectionEquals(obj, obj2, ReflectionToStringBuilder.a(collection));
    }

    public EqualsBuilder append(Object obj, Object obj2) {
        if (!this.a || obj == obj2) {
            return this;
        }
        if (obj == null || obj2 == null) {
            setEquals(false);
            return this;
        }
        Class<?> cls = obj.getClass();
        if (cls.isArray()) {
            if (obj.getClass() != obj2.getClass()) {
                setEquals(false);
            } else if (obj instanceof long[]) {
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
                append((Object[]) obj, (Object[]) obj2);
            }
        } else if (!this.c || ClassUtils.isPrimitiveOrWrapper(cls)) {
            this.a = obj.equals(obj2);
        } else {
            reflectionAppend(obj, obj2);
        }
        return this;
    }

    public EqualsBuilder appendSuper(boolean z) {
        if (!this.a) {
            return this;
        }
        this.a = z;
        return this;
    }

    public boolean isEquals() {
        return this.a;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0032  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public org.apache.commons.lang3.builder.EqualsBuilder reflectionAppend(java.lang.Object r6, java.lang.Object r7) {
        /*
            r5 = this;
            boolean r0 = r5.a
            if (r0 != 0) goto L5
            return r5
        L5:
            if (r6 != r7) goto L8
            return r5
        L8:
            r0 = 0
            if (r6 == 0) goto L74
            if (r7 != 0) goto Lf
            goto L74
        Lf:
            java.lang.Class r1 = r6.getClass()
            java.lang.Class r2 = r7.getClass()
            boolean r3 = r1.isInstance(r7)
            if (r3 == 0) goto L24
            boolean r3 = r2.isInstance(r6)
            if (r3 != 0) goto L30
            goto L32
        L24:
            boolean r3 = r2.isInstance(r6)
            if (r3 == 0) goto L71
            boolean r3 = r1.isInstance(r7)
            if (r3 != 0) goto L32
        L30:
            r3 = r1
            goto L33
        L32:
            r3 = r2
        L33:
            boolean r4 = r3.isArray()     // Catch: java.lang.IllegalArgumentException -> L6e
            if (r4 == 0) goto L3d
            r5.append(r6, r7)     // Catch: java.lang.IllegalArgumentException -> L6e
            goto L6d
        L3d:
            java.util.List<java.lang.Class<?>> r4 = r5.d     // Catch: java.lang.IllegalArgumentException -> L6e
            if (r4 == 0) goto L58
            java.util.List<java.lang.Class<?>> r4 = r5.d     // Catch: java.lang.IllegalArgumentException -> L6e
            boolean r1 = r4.contains(r1)     // Catch: java.lang.IllegalArgumentException -> L6e
            if (r1 != 0) goto L51
            java.util.List<java.lang.Class<?>> r1 = r5.d     // Catch: java.lang.IllegalArgumentException -> L6e
            boolean r1 = r1.contains(r2)     // Catch: java.lang.IllegalArgumentException -> L6e
            if (r1 == 0) goto L58
        L51:
            boolean r6 = r6.equals(r7)     // Catch: java.lang.IllegalArgumentException -> L6e
            r5.a = r6     // Catch: java.lang.IllegalArgumentException -> L6e
            goto L6d
        L58:
            r5.a(r6, r7, r3)     // Catch: java.lang.IllegalArgumentException -> L6e
        L5b:
            java.lang.Class r1 = r3.getSuperclass()     // Catch: java.lang.IllegalArgumentException -> L6e
            if (r1 == 0) goto L6d
            java.lang.Class<?> r1 = r5.e     // Catch: java.lang.IllegalArgumentException -> L6e
            if (r3 == r1) goto L6d
            java.lang.Class r3 = r3.getSuperclass()     // Catch: java.lang.IllegalArgumentException -> L6e
            r5.a(r6, r7, r3)     // Catch: java.lang.IllegalArgumentException -> L6e
            goto L5b
        L6d:
            return r5
        L6e:
            r5.a = r0
            return r5
        L71:
            r5.a = r0
            return r5
        L74:
            r5.a = r0
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.lang3.builder.EqualsBuilder.reflectionAppend(java.lang.Object, java.lang.Object):org.apache.commons.lang3.builder.EqualsBuilder");
    }

    public void reset() {
        this.a = true;
    }

    public EqualsBuilder setBypassReflectionClasses(List<Class<?>> list) {
        this.d = list;
        return this;
    }

    public void setEquals(boolean z) {
        this.a = z;
    }

    public EqualsBuilder setExcludeFields(String... strArr) {
        this.f = strArr;
        return this;
    }

    public EqualsBuilder setReflectUpToClass(Class<?> cls) {
        this.e = cls;
        return this;
    }

    public EqualsBuilder setTestRecursive(boolean z) {
        this.c = z;
        return this;
    }

    public EqualsBuilder setTestTransients(boolean z) {
        this.b = z;
        return this;
    }

    public static Pair<ko, ko> a(Object obj, Object obj2) {
        return Pair.of(new ko(obj), new ko(obj2));
    }

    public static boolean reflectionEquals(Object obj, Object obj2, String... strArr) {
        return reflectionEquals(obj, obj2, false, null, strArr);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.apache.commons.lang3.builder.Builder
    public Boolean build() {
        return Boolean.valueOf(isEquals());
    }

    public static boolean reflectionEquals(Object obj, Object obj2, boolean z) {
        return reflectionEquals(obj, obj2, z, null, new String[0]);
    }

    public static boolean reflectionEquals(Object obj, Object obj2, boolean z, Class<?> cls, String... strArr) {
        return reflectionEquals(obj, obj2, z, cls, false, strArr);
    }

    public static boolean reflectionEquals(Object obj, Object obj2, boolean z, Class<?> cls, boolean z2, String... strArr) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        return new EqualsBuilder().setExcludeFields(strArr).setReflectUpToClass(cls).setTestTransients(z).setTestRecursive(z2).reflectionAppend(obj, obj2).isEquals();
    }

    public final void a(Object obj, Object obj2, Class<?> cls) {
        Set<Pair<ko, ko>> setA = a();
        Pair<ko, ko> pairA = a(obj, obj2);
        if (setA != null && (setA.contains(pairA) || setA.contains(Pair.of(pairA.getRight(), pairA.getLeft())))) {
            return;
        }
        try {
            Set<Pair<ko, ko>> setA2 = a();
            if (setA2 == null) {
                setA2 = new HashSet<>();
                g.set(setA2);
            }
            setA2.add(a(obj, obj2));
            Field[] declaredFields = cls.getDeclaredFields();
            AccessibleObject.setAccessible(declaredFields, true);
            for (int i = 0; i < declaredFields.length && this.a; i++) {
                Field field = declaredFields[i];
                if (!ArrayUtils.contains(this.f, field.getName()) && !field.getName().contains("$") && ((this.b || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers()) && !field.isAnnotationPresent(EqualsExclude.class))) {
                    try {
                        append(field.get(obj), field.get(obj2));
                    } catch (IllegalAccessException unused) {
                        throw new InternalError("Unexpected IllegalAccessException");
                    }
                }
            }
        } finally {
            b(obj, obj2);
        }
    }

    public EqualsBuilder append(long j, long j2) {
        if (!this.a) {
            return this;
        }
        this.a = j == j2;
        return this;
    }

    public EqualsBuilder append(int i, int i2) {
        if (!this.a) {
            return this;
        }
        this.a = i == i2;
        return this;
    }

    public EqualsBuilder append(short s, short s2) {
        if (!this.a) {
            return this;
        }
        this.a = s == s2;
        return this;
    }

    public EqualsBuilder append(char c, char c2) {
        if (!this.a) {
            return this;
        }
        this.a = c == c2;
        return this;
    }

    public EqualsBuilder append(byte b, byte b2) {
        if (!this.a) {
            return this;
        }
        this.a = b == b2;
        return this;
    }

    public EqualsBuilder append(double d, double d2) {
        return !this.a ? this : append(Double.doubleToLongBits(d), Double.doubleToLongBits(d2));
    }

    public EqualsBuilder append(float f, float f2) {
        return !this.a ? this : append(Float.floatToIntBits(f), Float.floatToIntBits(f2));
    }

    public EqualsBuilder append(boolean z, boolean z2) {
        if (!this.a) {
            return this;
        }
        this.a = z == z2;
        return this;
    }

    public EqualsBuilder append(Object[] objArr, Object[] objArr2) {
        if (!this.a || objArr == objArr2) {
            return this;
        }
        if (objArr != null && objArr2 != null) {
            if (objArr.length != objArr2.length) {
                setEquals(false);
                return this;
            }
            for (int i = 0; i < objArr.length && this.a; i++) {
                append(objArr[i], objArr2[i]);
            }
            return this;
        }
        setEquals(false);
        return this;
    }

    public EqualsBuilder append(long[] jArr, long[] jArr2) {
        if (!this.a || jArr == jArr2) {
            return this;
        }
        if (jArr != null && jArr2 != null) {
            if (jArr.length != jArr2.length) {
                setEquals(false);
                return this;
            }
            for (int i = 0; i < jArr.length && this.a; i++) {
                append(jArr[i], jArr2[i]);
            }
            return this;
        }
        setEquals(false);
        return this;
    }

    public EqualsBuilder append(int[] iArr, int[] iArr2) {
        if (!this.a || iArr == iArr2) {
            return this;
        }
        if (iArr != null && iArr2 != null) {
            if (iArr.length != iArr2.length) {
                setEquals(false);
                return this;
            }
            for (int i = 0; i < iArr.length && this.a; i++) {
                append(iArr[i], iArr2[i]);
            }
            return this;
        }
        setEquals(false);
        return this;
    }

    public EqualsBuilder append(short[] sArr, short[] sArr2) {
        if (!this.a || sArr == sArr2) {
            return this;
        }
        if (sArr != null && sArr2 != null) {
            if (sArr.length != sArr2.length) {
                setEquals(false);
                return this;
            }
            for (int i = 0; i < sArr.length && this.a; i++) {
                append(sArr[i], sArr2[i]);
            }
            return this;
        }
        setEquals(false);
        return this;
    }

    public EqualsBuilder append(char[] cArr, char[] cArr2) {
        if (!this.a || cArr == cArr2) {
            return this;
        }
        if (cArr != null && cArr2 != null) {
            if (cArr.length != cArr2.length) {
                setEquals(false);
                return this;
            }
            for (int i = 0; i < cArr.length && this.a; i++) {
                append(cArr[i], cArr2[i]);
            }
            return this;
        }
        setEquals(false);
        return this;
    }

    public EqualsBuilder append(byte[] bArr, byte[] bArr2) {
        if (!this.a || bArr == bArr2) {
            return this;
        }
        if (bArr != null && bArr2 != null) {
            if (bArr.length != bArr2.length) {
                setEquals(false);
                return this;
            }
            for (int i = 0; i < bArr.length && this.a; i++) {
                append(bArr[i], bArr2[i]);
            }
            return this;
        }
        setEquals(false);
        return this;
    }

    public EqualsBuilder append(double[] dArr, double[] dArr2) {
        if (!this.a || dArr == dArr2) {
            return this;
        }
        if (dArr != null && dArr2 != null) {
            if (dArr.length != dArr2.length) {
                setEquals(false);
                return this;
            }
            for (int i = 0; i < dArr.length && this.a; i++) {
                append(dArr[i], dArr2[i]);
            }
            return this;
        }
        setEquals(false);
        return this;
    }

    public EqualsBuilder append(float[] fArr, float[] fArr2) {
        if (!this.a || fArr == fArr2) {
            return this;
        }
        if (fArr != null && fArr2 != null) {
            if (fArr.length != fArr2.length) {
                setEquals(false);
                return this;
            }
            for (int i = 0; i < fArr.length && this.a; i++) {
                append(fArr[i], fArr2[i]);
            }
            return this;
        }
        setEquals(false);
        return this;
    }

    public EqualsBuilder append(boolean[] zArr, boolean[] zArr2) {
        if (!this.a || zArr == zArr2) {
            return this;
        }
        if (zArr != null && zArr2 != null) {
            if (zArr.length != zArr2.length) {
                setEquals(false);
                return this;
            }
            for (int i = 0; i < zArr.length && this.a; i++) {
                append(zArr[i], zArr2[i]);
            }
            return this;
        }
        setEquals(false);
        return this;
    }
}
