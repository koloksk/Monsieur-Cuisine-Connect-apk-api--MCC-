package org.apache.commons.lang3.mutable;

import org.apache.commons.lang3.math.NumberUtils;

/* loaded from: classes.dex */
public class MutableByte extends Number implements Comparable<MutableByte>, Mutable<Number> {
    public static final long serialVersionUID = -1585823265;
    public byte a;

    public MutableByte() {
    }

    public void add(byte b) {
        this.a = (byte) (this.a + b);
    }

    public byte addAndGet(byte b) {
        byte b2 = (byte) (this.a + b);
        this.a = b2;
        return b2;
    }

    @Override // java.lang.Number
    public byte byteValue() {
        return this.a;
    }

    public void decrement() {
        this.a = (byte) (this.a - 1);
    }

    public byte decrementAndGet() {
        byte b = (byte) (this.a - 1);
        this.a = b;
        return b;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.a;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableByte) && this.a == ((MutableByte) obj).byteValue();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.a;
    }

    public byte getAndAdd(byte b) {
        byte b2 = this.a;
        this.a = (byte) (b + b2);
        return b2;
    }

    public byte getAndDecrement() {
        byte b = this.a;
        this.a = (byte) (b - 1);
        return b;
    }

    public byte getAndIncrement() {
        byte b = this.a;
        this.a = (byte) (b + 1);
        return b;
    }

    public int hashCode() {
        return this.a;
    }

    public void increment() {
        this.a = (byte) (this.a + 1);
    }

    public byte incrementAndGet() {
        byte b = (byte) (this.a + 1);
        this.a = b;
        return b;
    }

    @Override // java.lang.Number
    public int intValue() {
        return this.a;
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.a;
    }

    public void subtract(byte b) {
        this.a = (byte) (this.a - b);
    }

    public Byte toByte() {
        return Byte.valueOf(byteValue());
    }

    public String toString() {
        return String.valueOf((int) this.a);
    }

    public MutableByte(byte b) {
        this.a = b;
    }

    public void add(Number number) {
        this.a = (byte) (number.byteValue() + this.a);
    }

    public byte addAndGet(Number number) {
        byte bByteValue = (byte) (number.byteValue() + this.a);
        this.a = bByteValue;
        return bByteValue;
    }

    @Override // java.lang.Comparable
    public int compareTo(MutableByte mutableByte) {
        return NumberUtils.compare(this.a, mutableByte.a);
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    /* renamed from: getValue */
    public Number getValue2() {
        return Byte.valueOf(this.a);
    }

    public void setValue(byte b) {
        this.a = b;
    }

    public void subtract(Number number) {
        this.a = (byte) (this.a - number.byteValue());
    }

    public byte getAndAdd(Number number) {
        byte b = this.a;
        this.a = (byte) (number.byteValue() + b);
        return b;
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    public void setValue(Number number) {
        this.a = number.byteValue();
    }

    public MutableByte(Number number) {
        this.a = number.byteValue();
    }

    public MutableByte(String str) {
        this.a = Byte.parseByte(str);
    }
}
