package org.apache.commons.lang3.mutable;

import org.apache.commons.lang3.math.NumberUtils;

/* loaded from: classes.dex */
public class MutableShort extends Number implements Comparable<MutableShort>, Mutable<Number> {
    public static final long serialVersionUID = -2135791679;
    public short a;

    public MutableShort() {
    }

    public void add(short s) {
        this.a = (short) (this.a + s);
    }

    public short addAndGet(short s) {
        short s2 = (short) (this.a + s);
        this.a = s2;
        return s2;
    }

    public void decrement() {
        this.a = (short) (this.a - 1);
    }

    public short decrementAndGet() {
        short s = (short) (this.a - 1);
        this.a = s;
        return s;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.a;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableShort) && this.a == ((MutableShort) obj).shortValue();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.a;
    }

    public short getAndAdd(short s) {
        short s2 = this.a;
        this.a = (short) (s + s2);
        return s2;
    }

    public short getAndDecrement() {
        short s = this.a;
        this.a = (short) (s - 1);
        return s;
    }

    public short getAndIncrement() {
        short s = this.a;
        this.a = (short) (s + 1);
        return s;
    }

    public int hashCode() {
        return this.a;
    }

    public void increment() {
        this.a = (short) (this.a + 1);
    }

    public short incrementAndGet() {
        short s = (short) (this.a + 1);
        this.a = s;
        return s;
    }

    @Override // java.lang.Number
    public int intValue() {
        return this.a;
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.a;
    }

    @Override // java.lang.Number
    public short shortValue() {
        return this.a;
    }

    public void subtract(short s) {
        this.a = (short) (this.a - s);
    }

    public Short toShort() {
        return Short.valueOf(shortValue());
    }

    public String toString() {
        return String.valueOf((int) this.a);
    }

    public MutableShort(short s) {
        this.a = s;
    }

    public void add(Number number) {
        this.a = (short) (number.shortValue() + this.a);
    }

    public short addAndGet(Number number) {
        short sShortValue = (short) (number.shortValue() + this.a);
        this.a = sShortValue;
        return sShortValue;
    }

    @Override // java.lang.Comparable
    public int compareTo(MutableShort mutableShort) {
        return NumberUtils.compare(this.a, mutableShort.a);
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    /* renamed from: getValue, reason: avoid collision after fix types in other method */
    public Number getValue2() {
        return Short.valueOf(this.a);
    }

    public void setValue(short s) {
        this.a = s;
    }

    public void subtract(Number number) {
        this.a = (short) (this.a - number.shortValue());
    }

    public short getAndAdd(Number number) {
        short s = this.a;
        this.a = (short) (number.shortValue() + s);
        return s;
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    public void setValue(Number number) {
        this.a = number.shortValue();
    }

    public MutableShort(Number number) {
        this.a = number.shortValue();
    }

    public MutableShort(String str) {
        this.a = Short.parseShort(str);
    }
}
