package org.apache.commons.lang3.mutable;

/* loaded from: classes.dex */
public class MutableFloat extends Number implements Comparable<MutableFloat>, Mutable<Number> {
    public static final long serialVersionUID = 5787169186L;
    public float a;

    public MutableFloat() {
    }

    public void add(float f) {
        this.a += f;
    }

    public float addAndGet(float f) {
        float f2 = this.a + f;
        this.a = f2;
        return f2;
    }

    public void decrement() {
        this.a -= 1.0f;
    }

    public float decrementAndGet() {
        float f = this.a - 1.0f;
        this.a = f;
        return f;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.a;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableFloat) && Float.floatToIntBits(((MutableFloat) obj).a) == Float.floatToIntBits(this.a);
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.a;
    }

    public float getAndAdd(float f) {
        float f2 = this.a;
        this.a = f + f2;
        return f2;
    }

    public float getAndDecrement() {
        float f = this.a;
        this.a = f - 1.0f;
        return f;
    }

    public float getAndIncrement() {
        float f = this.a;
        this.a = 1.0f + f;
        return f;
    }

    public int hashCode() {
        return Float.floatToIntBits(this.a);
    }

    public void increment() {
        this.a += 1.0f;
    }

    public float incrementAndGet() {
        float f = this.a + 1.0f;
        this.a = f;
        return f;
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) this.a;
    }

    public boolean isInfinite() {
        return Float.isInfinite(this.a);
    }

    public boolean isNaN() {
        return Float.isNaN(this.a);
    }

    @Override // java.lang.Number
    public long longValue() {
        return (long) this.a;
    }

    public void subtract(float f) {
        this.a -= f;
    }

    public Float toFloat() {
        return Float.valueOf(floatValue());
    }

    public String toString() {
        return String.valueOf(this.a);
    }

    public MutableFloat(float f) {
        this.a = f;
    }

    public void add(Number number) {
        this.a = number.floatValue() + this.a;
    }

    public float addAndGet(Number number) {
        float fFloatValue = number.floatValue() + this.a;
        this.a = fFloatValue;
        return fFloatValue;
    }

    @Override // java.lang.Comparable
    public int compareTo(MutableFloat mutableFloat) {
        return Float.compare(this.a, mutableFloat.a);
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    /* renamed from: getValue */
    public Number getValue2() {
        return Float.valueOf(this.a);
    }

    public void setValue(float f) {
        this.a = f;
    }

    public void subtract(Number number) {
        this.a -= number.floatValue();
    }

    public float getAndAdd(Number number) {
        float f = this.a;
        this.a = number.floatValue() + f;
        return f;
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    public void setValue(Number number) {
        this.a = number.floatValue();
    }

    public MutableFloat(Number number) {
        this.a = number.floatValue();
    }

    public MutableFloat(String str) {
        this.a = Float.parseFloat(str);
    }
}
