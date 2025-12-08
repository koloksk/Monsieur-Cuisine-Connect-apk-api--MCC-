package org.apache.commons.lang3.mutable;

import org.apache.commons.lang3.math.NumberUtils;

/* loaded from: classes.dex */
public class MutableInt extends Number implements Comparable<MutableInt>, Mutable<Number> {
    public static final long serialVersionUID = 512176391864L;
    public int a;

    public MutableInt() {
    }

    public void add(int i) {
        this.a += i;
    }

    public int addAndGet(int i) {
        int i2 = this.a + i;
        this.a = i2;
        return i2;
    }

    public void decrement() {
        this.a--;
    }

    public int decrementAndGet() {
        int i = this.a - 1;
        this.a = i;
        return i;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.a;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableInt) && this.a == ((MutableInt) obj).intValue();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.a;
    }

    public int getAndAdd(int i) {
        int i2 = this.a;
        this.a = i + i2;
        return i2;
    }

    public int getAndDecrement() {
        int i = this.a;
        this.a = i - 1;
        return i;
    }

    public int getAndIncrement() {
        int i = this.a;
        this.a = i + 1;
        return i;
    }

    public int hashCode() {
        return this.a;
    }

    public void increment() {
        this.a++;
    }

    public int incrementAndGet() {
        int i = this.a + 1;
        this.a = i;
        return i;
    }

    @Override // java.lang.Number
    public int intValue() {
        return this.a;
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.a;
    }

    public void subtract(int i) {
        this.a -= i;
    }

    public Integer toInteger() {
        return Integer.valueOf(intValue());
    }

    public String toString() {
        return String.valueOf(this.a);
    }

    public MutableInt(int i) {
        this.a = i;
    }

    public void add(Number number) {
        this.a = number.intValue() + this.a;
    }

    public int addAndGet(Number number) {
        int iIntValue = number.intValue() + this.a;
        this.a = iIntValue;
        return iIntValue;
    }

    @Override // java.lang.Comparable
    public int compareTo(MutableInt mutableInt) {
        return NumberUtils.compare(this.a, mutableInt.a);
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    /* renamed from: getValue */
    public Number getValue2() {
        return Integer.valueOf(this.a);
    }

    public void setValue(int i) {
        this.a = i;
    }

    public void subtract(Number number) {
        this.a -= number.intValue();
    }

    public int getAndAdd(Number number) {
        int i = this.a;
        this.a = number.intValue() + i;
        return i;
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    public void setValue(Number number) {
        this.a = number.intValue();
    }

    public MutableInt(Number number) {
        this.a = number.intValue();
    }

    public MutableInt(String str) {
        this.a = Integer.parseInt(str);
    }
}
