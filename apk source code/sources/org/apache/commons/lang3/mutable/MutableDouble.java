package org.apache.commons.lang3.mutable;

/* loaded from: classes.dex */
public class MutableDouble extends Number implements Comparable<MutableDouble>, Mutable<Number> {
    public static final long serialVersionUID = 1587163916;
    public double a;

    public MutableDouble() {
    }

    public void add(double d) {
        this.a += d;
    }

    public double addAndGet(double d) {
        double d2 = this.a + d;
        this.a = d2;
        return d2;
    }

    public void decrement() {
        this.a -= 1.0d;
    }

    public double decrementAndGet() {
        double d = this.a - 1.0d;
        this.a = d;
        return d;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.a;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableDouble) && Double.doubleToLongBits(((MutableDouble) obj).a) == Double.doubleToLongBits(this.a);
    }

    @Override // java.lang.Number
    public float floatValue() {
        return (float) this.a;
    }

    public double getAndAdd(double d) {
        double d2 = this.a;
        this.a = d + d2;
        return d2;
    }

    public double getAndDecrement() {
        double d = this.a;
        this.a = d - 1.0d;
        return d;
    }

    public double getAndIncrement() {
        double d = this.a;
        this.a = 1.0d + d;
        return d;
    }

    public int hashCode() {
        long jDoubleToLongBits = Double.doubleToLongBits(this.a);
        return (int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32));
    }

    public void increment() {
        this.a += 1.0d;
    }

    public double incrementAndGet() {
        double d = this.a + 1.0d;
        this.a = d;
        return d;
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) this.a;
    }

    public boolean isInfinite() {
        return Double.isInfinite(this.a);
    }

    public boolean isNaN() {
        return Double.isNaN(this.a);
    }

    @Override // java.lang.Number
    public long longValue() {
        return (long) this.a;
    }

    public void subtract(double d) {
        this.a -= d;
    }

    public Double toDouble() {
        return Double.valueOf(doubleValue());
    }

    public String toString() {
        return String.valueOf(this.a);
    }

    public MutableDouble(double d) {
        this.a = d;
    }

    public void add(Number number) {
        this.a = number.doubleValue() + this.a;
    }

    public double addAndGet(Number number) {
        double dDoubleValue = number.doubleValue() + this.a;
        this.a = dDoubleValue;
        return dDoubleValue;
    }

    @Override // java.lang.Comparable
    public int compareTo(MutableDouble mutableDouble) {
        return Double.compare(this.a, mutableDouble.a);
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    /* renamed from: getValue, reason: merged with bridge method [inline-methods] */
    public Number getValue2() {
        return Double.valueOf(this.a);
    }

    public void setValue(double d) {
        this.a = d;
    }

    public void subtract(Number number) {
        this.a -= number.doubleValue();
    }

    public double getAndAdd(Number number) {
        double d = this.a;
        this.a = number.doubleValue() + d;
        return d;
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    public void setValue(Number number) {
        this.a = number.doubleValue();
    }

    public MutableDouble(Number number) {
        this.a = number.doubleValue();
    }

    public MutableDouble(String str) {
        this.a = Double.parseDouble(str);
    }
}
