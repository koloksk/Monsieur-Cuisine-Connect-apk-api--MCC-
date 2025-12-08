package org.apache.commons.lang3.mutable;

import org.apache.commons.lang3.math.NumberUtils;

/* loaded from: classes.dex */
public class MutableLong extends Number implements Comparable<MutableLong>, Mutable<Number> {
    public static final long serialVersionUID = 62986528375L;
    public long a;

    public MutableLong() {
    }

    public void add(long j) {
        this.a += j;
    }

    public long addAndGet(long j) {
        long j2 = this.a + j;
        this.a = j2;
        return j2;
    }

    public void decrement() {
        this.a--;
    }

    public long decrementAndGet() {
        long j = this.a - 1;
        this.a = j;
        return j;
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.a;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableLong) && this.a == ((MutableLong) obj).longValue();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.a;
    }

    public long getAndAdd(long j) {
        long j2 = this.a;
        this.a = j + j2;
        return j2;
    }

    public long getAndDecrement() {
        long j = this.a;
        this.a = j - 1;
        return j;
    }

    public long getAndIncrement() {
        long j = this.a;
        this.a = 1 + j;
        return j;
    }

    public int hashCode() {
        long j = this.a;
        return (int) (j ^ (j >>> 32));
    }

    public void increment() {
        this.a++;
    }

    public long incrementAndGet() {
        long j = this.a + 1;
        this.a = j;
        return j;
    }

    @Override // java.lang.Number
    public int intValue() {
        return (int) this.a;
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.a;
    }

    public void subtract(long j) {
        this.a -= j;
    }

    public Long toLong() {
        return Long.valueOf(longValue());
    }

    public String toString() {
        return String.valueOf(this.a);
    }

    public MutableLong(long j) {
        this.a = j;
    }

    public void add(Number number) {
        this.a = number.longValue() + this.a;
    }

    public long addAndGet(Number number) {
        long jLongValue = number.longValue() + this.a;
        this.a = jLongValue;
        return jLongValue;
    }

    @Override // java.lang.Comparable
    public int compareTo(MutableLong mutableLong) {
        return NumberUtils.compare(this.a, mutableLong.a);
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    /* renamed from: getValue */
    public Number getValue2() {
        return Long.valueOf(this.a);
    }

    public void setValue(long j) {
        this.a = j;
    }

    public void subtract(Number number) {
        this.a -= number.longValue();
    }

    public long getAndAdd(Number number) {
        long j = this.a;
        this.a = number.longValue() + j;
        return j;
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    public void setValue(Number number) {
        this.a = number.longValue();
    }

    public MutableLong(Number number) {
        this.a = number.longValue();
    }

    public MutableLong(String str) {
        this.a = Long.parseLong(str);
    }
}
