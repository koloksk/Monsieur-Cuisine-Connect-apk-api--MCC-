package org.apache.commons.lang3.builder;

import defpackage.g9;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.Validate;

/* loaded from: classes.dex */
public class DiffBuilder implements Builder<DiffResult> {
    public final List<Diff<?>> a;
    public final boolean b;
    public final Object c;
    public final Object d;
    public final ToStringStyle e;

    public class a extends Diff<Float[]> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ float[] c;
        public final /* synthetic */ float[] d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(DiffBuilder diffBuilder, String str, float[] fArr, float[] fArr2) {
            super(str);
            this.c = fArr;
            this.d = fArr2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return ArrayUtils.toObject(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return ArrayUtils.toObject(this.d);
        }
    }

    public class b extends Diff<Integer> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ int c;
        public final /* synthetic */ int d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(DiffBuilder diffBuilder, String str, int i, int i2) {
            super(str);
            this.c = i;
            this.d = i2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return Integer.valueOf(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return Integer.valueOf(this.d);
        }
    }

    public class c extends Diff<Integer[]> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ int[] c;
        public final /* synthetic */ int[] d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public c(DiffBuilder diffBuilder, String str, int[] iArr, int[] iArr2) {
            super(str);
            this.c = iArr;
            this.d = iArr2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return ArrayUtils.toObject(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return ArrayUtils.toObject(this.d);
        }
    }

    public class d extends Diff<Long> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ long c;
        public final /* synthetic */ long d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public d(DiffBuilder diffBuilder, String str, long j, long j2) {
            super(str);
            this.c = j;
            this.d = j2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return Long.valueOf(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return Long.valueOf(this.d);
        }
    }

    public class e extends Diff<Long[]> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ long[] c;
        public final /* synthetic */ long[] d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public e(DiffBuilder diffBuilder, String str, long[] jArr, long[] jArr2) {
            super(str);
            this.c = jArr;
            this.d = jArr2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return ArrayUtils.toObject(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return ArrayUtils.toObject(this.d);
        }
    }

    public class f extends Diff<Short> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ short c;
        public final /* synthetic */ short d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public f(DiffBuilder diffBuilder, String str, short s, short s2) {
            super(str);
            this.c = s;
            this.d = s2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return Short.valueOf(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return Short.valueOf(this.d);
        }
    }

    public class g extends Diff<Short[]> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ short[] c;
        public final /* synthetic */ short[] d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public g(DiffBuilder diffBuilder, String str, short[] sArr, short[] sArr2) {
            super(str);
            this.c = sArr;
            this.d = sArr2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return ArrayUtils.toObject(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return ArrayUtils.toObject(this.d);
        }
    }

    public class h extends Diff<Object> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ Object c;
        public final /* synthetic */ Object d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public h(DiffBuilder diffBuilder, String str, Object obj, Object obj2) {
            super(str);
            this.c = obj;
            this.d = obj2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return this.c;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return this.d;
        }
    }

    public class i extends Diff<Object[]> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ Object[] c;
        public final /* synthetic */ Object[] d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public i(DiffBuilder diffBuilder, String str, Object[] objArr, Object[] objArr2) {
            super(str);
            this.c = objArr;
            this.d = objArr2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return this.c;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return this.d;
        }
    }

    public class j extends Diff<Boolean> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ boolean c;
        public final /* synthetic */ boolean d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public j(DiffBuilder diffBuilder, String str, boolean z, boolean z2) {
            super(str);
            this.c = z;
            this.d = z2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return Boolean.valueOf(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return Boolean.valueOf(this.d);
        }
    }

    public class k extends Diff<Boolean[]> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ boolean[] c;
        public final /* synthetic */ boolean[] d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public k(DiffBuilder diffBuilder, String str, boolean[] zArr, boolean[] zArr2) {
            super(str);
            this.c = zArr;
            this.d = zArr2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return ArrayUtils.toObject(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return ArrayUtils.toObject(this.d);
        }
    }

    public class l extends Diff<Byte> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ byte c;
        public final /* synthetic */ byte d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public l(DiffBuilder diffBuilder, String str, byte b, byte b2) {
            super(str);
            this.c = b;
            this.d = b2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return Byte.valueOf(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return Byte.valueOf(this.d);
        }
    }

    public class m extends Diff<Byte[]> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ byte[] c;
        public final /* synthetic */ byte[] d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public m(DiffBuilder diffBuilder, String str, byte[] bArr, byte[] bArr2) {
            super(str);
            this.c = bArr;
            this.d = bArr2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return ArrayUtils.toObject(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return ArrayUtils.toObject(this.d);
        }
    }

    public class n extends Diff<Character> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ char c;
        public final /* synthetic */ char d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public n(DiffBuilder diffBuilder, String str, char c, char c2) {
            super(str);
            this.c = c;
            this.d = c2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return Character.valueOf(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return Character.valueOf(this.d);
        }
    }

    public class o extends Diff<Character[]> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ char[] c;
        public final /* synthetic */ char[] d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public o(DiffBuilder diffBuilder, String str, char[] cArr, char[] cArr2) {
            super(str);
            this.c = cArr;
            this.d = cArr2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return ArrayUtils.toObject(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return ArrayUtils.toObject(this.d);
        }
    }

    public class p extends Diff<Double> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ double c;
        public final /* synthetic */ double d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public p(DiffBuilder diffBuilder, String str, double d, double d2) {
            super(str);
            this.c = d;
            this.d = d2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return Double.valueOf(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return Double.valueOf(this.d);
        }
    }

    public class q extends Diff<Double[]> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ double[] c;
        public final /* synthetic */ double[] d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public q(DiffBuilder diffBuilder, String str, double[] dArr, double[] dArr2) {
            super(str);
            this.c = dArr;
            this.d = dArr2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return ArrayUtils.toObject(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return ArrayUtils.toObject(this.d);
        }
    }

    public class r extends Diff<Float> {
        public static final long serialVersionUID = 1;
        public final /* synthetic */ float c;
        public final /* synthetic */ float d;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public r(DiffBuilder diffBuilder, String str, float f, float f2) {
            super(str);
            this.c = f;
            this.d = f2;
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getLeft() {
            return Float.valueOf(this.c);
        }

        @Override // org.apache.commons.lang3.tuple.Pair
        public Object getRight() {
            return Float.valueOf(this.d);
        }
    }

    public DiffBuilder(Object obj, Object obj2, ToStringStyle toStringStyle, boolean z) {
        boolean z2 = true;
        Validate.isTrue(obj != null, "lhs cannot be null", new Object[0]);
        Validate.isTrue(obj2 != null, "rhs cannot be null", new Object[0]);
        this.a = new ArrayList();
        this.c = obj;
        this.d = obj2;
        this.e = toStringStyle;
        if (!z || (obj != obj2 && !obj.equals(obj2))) {
            z2 = false;
        }
        this.b = z2;
    }

    public final void a(String str) {
        Validate.isTrue(str != null, "Field name cannot be null", new Object[0]);
    }

    public DiffBuilder append(String str, boolean z, boolean z2) {
        a(str);
        if (!this.b && z != z2) {
            this.a.add(new j(this, str, z, z2));
        }
        return this;
    }

    @Override // org.apache.commons.lang3.builder.Builder
    public DiffResult build() {
        return new DiffResult(this.c, this.d, this.a, this.e);
    }

    public DiffBuilder append(String str, boolean[] zArr, boolean[] zArr2) {
        a(str);
        if (!this.b && !Arrays.equals(zArr, zArr2)) {
            this.a.add(new k(this, str, zArr, zArr2));
        }
        return this;
    }

    public DiffBuilder append(String str, byte b2, byte b3) {
        a(str);
        if (!this.b && b2 != b3) {
            this.a.add(new l(this, str, b2, b3));
        }
        return this;
    }

    public DiffBuilder(Object obj, Object obj2, ToStringStyle toStringStyle) {
        this(obj, obj2, toStringStyle, true);
    }

    public DiffBuilder append(String str, byte[] bArr, byte[] bArr2) {
        a(str);
        if (!this.b && !Arrays.equals(bArr, bArr2)) {
            this.a.add(new m(this, str, bArr, bArr2));
        }
        return this;
    }

    public DiffBuilder append(String str, char c2, char c3) {
        a(str);
        if (!this.b && c2 != c3) {
            this.a.add(new n(this, str, c2, c3));
        }
        return this;
    }

    public DiffBuilder append(String str, char[] cArr, char[] cArr2) {
        a(str);
        if (!this.b && !Arrays.equals(cArr, cArr2)) {
            this.a.add(new o(this, str, cArr, cArr2));
        }
        return this;
    }

    public DiffBuilder append(String str, double d2, double d3) {
        a(str);
        if (!this.b && Double.doubleToLongBits(d2) != Double.doubleToLongBits(d3)) {
            this.a.add(new p(this, str, d2, d3));
        }
        return this;
    }

    public DiffBuilder append(String str, double[] dArr, double[] dArr2) {
        a(str);
        if (!this.b && !Arrays.equals(dArr, dArr2)) {
            this.a.add(new q(this, str, dArr, dArr2));
        }
        return this;
    }

    public DiffBuilder append(String str, float f2, float f3) {
        a(str);
        if (!this.b && Float.floatToIntBits(f2) != Float.floatToIntBits(f3)) {
            this.a.add(new r(this, str, f2, f3));
        }
        return this;
    }

    public DiffBuilder append(String str, float[] fArr, float[] fArr2) {
        a(str);
        if (!this.b && !Arrays.equals(fArr, fArr2)) {
            this.a.add(new a(this, str, fArr, fArr2));
        }
        return this;
    }

    public DiffBuilder append(String str, int i2, int i3) {
        a(str);
        if (!this.b && i2 != i3) {
            this.a.add(new b(this, str, i2, i3));
        }
        return this;
    }

    public DiffBuilder append(String str, int[] iArr, int[] iArr2) {
        a(str);
        if (!this.b && !Arrays.equals(iArr, iArr2)) {
            this.a.add(new c(this, str, iArr, iArr2));
        }
        return this;
    }

    public DiffBuilder append(String str, long j2, long j3) {
        a(str);
        if (!this.b && j2 != j3) {
            this.a.add(new d(this, str, j2, j3));
        }
        return this;
    }

    public DiffBuilder append(String str, long[] jArr, long[] jArr2) {
        a(str);
        if (!this.b && !Arrays.equals(jArr, jArr2)) {
            this.a.add(new e(this, str, jArr, jArr2));
        }
        return this;
    }

    public DiffBuilder append(String str, short s, short s2) {
        a(str);
        if (!this.b && s != s2) {
            this.a.add(new f(this, str, s, s2));
        }
        return this;
    }

    public DiffBuilder append(String str, short[] sArr, short[] sArr2) {
        a(str);
        if (!this.b && !Arrays.equals(sArr, sArr2)) {
            this.a.add(new g(this, str, sArr, sArr2));
        }
        return this;
    }

    public DiffBuilder append(String str, Object obj, Object obj2) {
        a(str);
        if (this.b || obj == obj2) {
            return this;
        }
        Object obj3 = obj != null ? obj : obj2;
        if (obj3.getClass().isArray()) {
            if (obj3 instanceof boolean[]) {
                return append(str, (boolean[]) obj, (boolean[]) obj2);
            }
            if (obj3 instanceof byte[]) {
                return append(str, (byte[]) obj, (byte[]) obj2);
            }
            if (obj3 instanceof char[]) {
                return append(str, (char[]) obj, (char[]) obj2);
            }
            if (obj3 instanceof double[]) {
                return append(str, (double[]) obj, (double[]) obj2);
            }
            if (obj3 instanceof float[]) {
                return append(str, (float[]) obj, (float[]) obj2);
            }
            if (obj3 instanceof int[]) {
                return append(str, (int[]) obj, (int[]) obj2);
            }
            if (obj3 instanceof long[]) {
                return append(str, (long[]) obj, (long[]) obj2);
            }
            if (obj3 instanceof short[]) {
                return append(str, (short[]) obj, (short[]) obj2);
            }
            return append(str, (Object[]) obj, (Object[]) obj2);
        }
        if (obj != null && obj.equals(obj2)) {
            return this;
        }
        this.a.add(new h(this, str, obj, obj2));
        return this;
    }

    public DiffBuilder append(String str, Object[] objArr, Object[] objArr2) {
        a(str);
        if (!this.b && !Arrays.equals(objArr, objArr2)) {
            this.a.add(new i(this, str, objArr, objArr2));
        }
        return this;
    }

    public DiffBuilder append(String str, DiffResult diffResult) {
        a(str);
        Validate.isTrue(diffResult != null, "Diff result cannot be null", new Object[0]);
        if (this.b) {
            return this;
        }
        for (Diff<?> diff : diffResult.getDiffs()) {
            StringBuilder sbA = g9.a(str, ".");
            sbA.append(diff.getFieldName());
            append(sbA.toString(), diff.getLeft(), diff.getRight());
        }
        return this;
    }
}
