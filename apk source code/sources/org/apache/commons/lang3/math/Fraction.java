package org.apache.commons.lang3.math;

import java.math.BigInteger;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

/* loaded from: classes.dex */
public final class Fraction extends Number implements Comparable<Fraction> {
    public static final long serialVersionUID = 65382027393090L;
    public final int a;
    public final int b;
    public transient int c = 0;
    public transient String d = null;
    public transient String e = null;
    public static final Fraction ZERO = new Fraction(0, 1);
    public static final Fraction ONE = new Fraction(1, 1);
    public static final Fraction ONE_HALF = new Fraction(1, 2);
    public static final Fraction ONE_THIRD = new Fraction(1, 3);
    public static final Fraction TWO_THIRDS = new Fraction(2, 3);
    public static final Fraction ONE_QUARTER = new Fraction(1, 4);
    public static final Fraction TWO_QUARTERS = new Fraction(2, 4);
    public static final Fraction THREE_QUARTERS = new Fraction(3, 4);
    public static final Fraction ONE_FIFTH = new Fraction(1, 5);
    public static final Fraction TWO_FIFTHS = new Fraction(2, 5);
    public static final Fraction THREE_FIFTHS = new Fraction(3, 5);
    public static final Fraction FOUR_FIFTHS = new Fraction(4, 5);

    public Fraction(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public static int a(int i, int i2) {
        int i3;
        if (i == 0 || i2 == 0) {
            if (i == Integer.MIN_VALUE || i2 == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: gcd is 2^31");
            }
            return Math.abs(i2) + Math.abs(i);
        }
        if (Math.abs(i) == 1 || Math.abs(i2) == 1) {
            return 1;
        }
        if (i > 0) {
            i = -i;
        }
        if (i2 > 0) {
            i2 = -i2;
        }
        int i4 = 0;
        while (true) {
            i3 = i & 1;
            if (i3 != 0 || (i2 & 1) != 0 || i4 >= 31) {
                break;
            }
            i /= 2;
            i2 /= 2;
            i4++;
        }
        if (i4 == 31) {
            throw new ArithmeticException("overflow: gcd is 2^31");
        }
        int i5 = i3 == 1 ? i2 : -(i / 2);
        while (true) {
            if ((i5 & 1) == 0) {
                i5 /= 2;
            } else {
                if (i5 > 0) {
                    i = -i5;
                } else {
                    i2 = i5;
                }
                i5 = (i2 - i) / 2;
                if (i5 == 0) {
                    return (-i) * (1 << i4);
                }
            }
        }
    }

    public static int b(int i, int i2) {
        long j = i * i2;
        if (j < -2147483648L || j > 2147483647L) {
            throw new ArithmeticException("overflow: mul");
        }
        return (int) j;
    }

    public static int c(int i, int i2) {
        long j = i * i2;
        if (j <= 2147483647L) {
            return (int) j;
        }
        throw new ArithmeticException("overflow: mulPos");
    }

    public static Fraction getFraction(int i, int i2) {
        if (i2 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (i2 < 0) {
            if (i == Integer.MIN_VALUE || i2 == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: can't negate");
            }
            i = -i;
            i2 = -i2;
        }
        return new Fraction(i, i2);
    }

    public static Fraction getReducedFraction(int i, int i2) {
        if (i2 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (i == 0) {
            return ZERO;
        }
        if (i2 == Integer.MIN_VALUE && (i & 1) == 0) {
            i /= 2;
            i2 /= 2;
        }
        if (i2 < 0) {
            if (i == Integer.MIN_VALUE || i2 == Integer.MIN_VALUE) {
                throw new ArithmeticException("overflow: can't negate");
            }
            i = -i;
            i2 = -i2;
        }
        int iA = a(i, i2);
        return new Fraction(i / iA, i2 / iA);
    }

    public Fraction abs() {
        return this.a >= 0 ? this : negate();
    }

    public Fraction add(Fraction fraction) {
        return a(fraction, true);
    }

    public Fraction divideBy(Fraction fraction) {
        Validate.isTrue(fraction != null, "The fraction must not be null", new Object[0]);
        if (fraction.a != 0) {
            return multiplyBy(fraction.invert());
        }
        throw new ArithmeticException("The fraction to divide by must not be zero");
    }

    @Override // java.lang.Number
    public double doubleValue() {
        return this.a / this.b;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Fraction)) {
            return false;
        }
        Fraction fraction = (Fraction) obj;
        return getNumerator() == fraction.getNumerator() && getDenominator() == fraction.getDenominator();
    }

    @Override // java.lang.Number
    public float floatValue() {
        return this.a / this.b;
    }

    public int getDenominator() {
        return this.b;
    }

    public int getNumerator() {
        return this.a;
    }

    public int getProperNumerator() {
        return Math.abs(this.a % this.b);
    }

    public int getProperWhole() {
        return this.a / this.b;
    }

    public int hashCode() {
        if (this.c == 0) {
            this.c = getDenominator() + ((getNumerator() + 629) * 37);
        }
        return this.c;
    }

    @Override // java.lang.Number
    public int intValue() {
        return this.a / this.b;
    }

    public Fraction invert() {
        int i = this.a;
        if (i == 0) {
            throw new ArithmeticException("Unable to invert zero.");
        }
        if (i != Integer.MIN_VALUE) {
            return i < 0 ? new Fraction(-this.b, -i) : new Fraction(this.b, i);
        }
        throw new ArithmeticException("overflow: can't negate numerator");
    }

    @Override // java.lang.Number
    public long longValue() {
        return this.a / this.b;
    }

    public Fraction multiplyBy(Fraction fraction) {
        Validate.isTrue(fraction != null, "The fraction must not be null", new Object[0]);
        int i = this.a;
        if (i == 0 || fraction.a == 0) {
            return ZERO;
        }
        int iA = a(i, fraction.b);
        int iA2 = a(fraction.a, this.b);
        return getReducedFraction(b(this.a / iA, fraction.a / iA2), c(this.b / iA2, fraction.b / iA));
    }

    public Fraction negate() {
        int i = this.a;
        if (i != Integer.MIN_VALUE) {
            return new Fraction(-i, this.b);
        }
        throw new ArithmeticException("overflow: too large to negate");
    }

    public Fraction pow(int i) {
        if (i == 1) {
            return this;
        }
        if (i == 0) {
            return ONE;
        }
        if (i < 0) {
            return i == Integer.MIN_VALUE ? invert().pow(2).pow(-(i / 2)) : invert().pow(-i);
        }
        Fraction fractionMultiplyBy = multiplyBy(this);
        return i % 2 == 0 ? fractionMultiplyBy.pow(i / 2) : fractionMultiplyBy.pow(i / 2).multiplyBy(this);
    }

    public Fraction reduce() {
        int i = this.a;
        if (i == 0) {
            return equals(ZERO) ? this : ZERO;
        }
        int iA = a(Math.abs(i), this.b);
        return iA == 1 ? this : getFraction(this.a / iA, this.b / iA);
    }

    public Fraction subtract(Fraction fraction) {
        return a(fraction, false);
    }

    public String toProperString() {
        if (this.e == null) {
            int i = this.a;
            if (i == 0) {
                this.e = "0";
            } else {
                int i2 = this.b;
                if (i == i2) {
                    this.e = "1";
                } else if (i == i2 * (-1)) {
                    this.e = "-1";
                } else {
                    if (i > 0) {
                        i = -i;
                    }
                    if (i < (-this.b)) {
                        int properNumerator = getProperNumerator();
                        if (properNumerator == 0) {
                            this.e = Integer.toString(getProperWhole());
                        } else {
                            this.e = getProperWhole() + StringUtils.SPACE + properNumerator + "/" + getDenominator();
                        }
                    } else {
                        this.e = getNumerator() + "/" + getDenominator();
                    }
                }
            }
        }
        return this.e;
    }

    public String toString() {
        if (this.d == null) {
            this.d = getNumerator() + "/" + getDenominator();
        }
        return this.d;
    }

    @Override // java.lang.Comparable
    public int compareTo(Fraction fraction) {
        if (this == fraction) {
            return 0;
        }
        if (this.a == fraction.a && this.b == fraction.b) {
            return 0;
        }
        long j = this.a * fraction.b;
        long j2 = fraction.a * this.b;
        if (j == j2) {
            return 0;
        }
        return j < j2 ? -1 : 1;
    }

    public static Fraction getFraction(int i, int i2, int i3) {
        if (i3 == 0) {
            throw new ArithmeticException("The denominator must not be zero");
        }
        if (i3 < 0) {
            throw new ArithmeticException("The denominator must not be negative");
        }
        if (i2 < 0) {
            throw new ArithmeticException("The numerator must not be negative");
        }
        long j = i < 0 ? (i * i3) - i2 : (i * i3) + i2;
        if (j >= -2147483648L && j <= 2147483647L) {
            return new Fraction((int) j, i3);
        }
        throw new ArithmeticException("Numerator too large to represent as an Integer.");
    }

    public static Fraction getFraction(double d) {
        int i;
        int i2 = d < 0.0d ? -1 : 1;
        double dAbs = Math.abs(d);
        if (dAbs > 2.147483647E9d || Double.isNaN(dAbs)) {
            throw new ArithmeticException("The value must not be greater than Integer.MAX_VALUE or NaN");
        }
        int i3 = (int) dAbs;
        double d2 = dAbs - i3;
        int i4 = (int) d2;
        double d3 = 1.0d;
        double d4 = d2 - i4;
        double d5 = Double.MAX_VALUE;
        int i5 = 0;
        int i6 = 0;
        int i7 = 1;
        int i8 = 1;
        int i9 = 1;
        while (true) {
            int i10 = (int) (d3 / d4);
            double d6 = d3 - (i10 * d4);
            int i11 = (i4 * i7) + i5;
            int i12 = (i4 * i6) + i8;
            double dAbs2 = Math.abs(d2 - (i11 / i12));
            i = i9 + 1;
            if (d5 <= dAbs2 || i12 > 10000 || i12 <= 0 || i >= 25) {
                break;
            }
            d5 = dAbs2;
            i9 = i;
            i4 = i10;
            d3 = d4;
            i8 = i6;
            d4 = d6;
            i6 = i12;
            i5 = i7;
            i7 = i11;
        }
        if (i != 25) {
            return getReducedFraction(((i3 * i6) + i7) * i2, i6);
        }
        throw new ArithmeticException("Unable to convert double to fraction");
    }

    public final Fraction a(Fraction fraction, boolean z) {
        long j;
        Validate.isTrue(fraction != null, "The fraction must not be null", new Object[0]);
        if (this.a == 0) {
            return z ? fraction : fraction.negate();
        }
        if (fraction.a == 0) {
            return this;
        }
        int iA = a(this.b, fraction.b);
        if (iA == 1) {
            int iB = b(this.a, fraction.b);
            int iB2 = b(fraction.a, this.b);
            if (z) {
                j = iB + iB2;
                if (j < -2147483648L || j > 2147483647L) {
                    throw new ArithmeticException("overflow: add");
                }
            } else {
                j = iB - iB2;
                if (j < -2147483648L || j > 2147483647L) {
                    throw new ArithmeticException("overflow: add");
                }
            }
            return new Fraction((int) j, c(this.b, fraction.b));
        }
        BigInteger bigIntegerMultiply = BigInteger.valueOf(this.a).multiply(BigInteger.valueOf(fraction.b / iA));
        BigInteger bigIntegerMultiply2 = BigInteger.valueOf(fraction.a).multiply(BigInteger.valueOf(this.b / iA));
        BigInteger bigIntegerAdd = z ? bigIntegerMultiply.add(bigIntegerMultiply2) : bigIntegerMultiply.subtract(bigIntegerMultiply2);
        int iIntValue = bigIntegerAdd.mod(BigInteger.valueOf(iA)).intValue();
        int iA2 = iIntValue == 0 ? iA : a(iIntValue, iA);
        BigInteger bigIntegerDivide = bigIntegerAdd.divide(BigInteger.valueOf(iA2));
        if (bigIntegerDivide.bitLength() <= 31) {
            return new Fraction(bigIntegerDivide.intValue(), c(this.b / iA, fraction.b / iA2));
        }
        throw new ArithmeticException("overflow: numerator too large after multiply");
    }

    public static Fraction getFraction(String str) throws NumberFormatException {
        Validate.isTrue(str != null, "The string must not be null", new Object[0]);
        if (str.indexOf(46) >= 0) {
            return getFraction(Double.parseDouble(str));
        }
        int iIndexOf = str.indexOf(32);
        if (iIndexOf > 0) {
            int i = Integer.parseInt(str.substring(0, iIndexOf));
            String strSubstring = str.substring(iIndexOf + 1);
            int iIndexOf2 = strSubstring.indexOf(47);
            if (iIndexOf2 >= 0) {
                return getFraction(i, Integer.parseInt(strSubstring.substring(0, iIndexOf2)), Integer.parseInt(strSubstring.substring(iIndexOf2 + 1)));
            }
            throw new NumberFormatException("The fraction could not be parsed as the format X Y/Z");
        }
        int iIndexOf3 = str.indexOf(47);
        if (iIndexOf3 < 0) {
            return getFraction(Integer.parseInt(str), 1);
        }
        return getFraction(Integer.parseInt(str.substring(0, iIndexOf3)), Integer.parseInt(str.substring(iIndexOf3 + 1)));
    }
}
