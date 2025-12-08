package org.apache.commons.lang3;

import defpackage.g9;
import java.io.Serializable;
import java.util.Comparator;

/* loaded from: classes.dex */
public final class Range<T> implements Serializable {
    public static final long serialVersionUID = 1;
    public final Comparator<T> a;
    public final T b;
    public final T c;
    public transient int d;
    public transient String e;

    public enum a implements Comparator {
        INSTANCE;

        @Override // java.util.Comparator
        public int compare(Object obj, Object obj2) {
            return ((Comparable) obj).compareTo(obj2);
        }
    }

    public Range(T t, T t2, Comparator<T> comparator) {
        if (t == null || t2 == null) {
            throw new IllegalArgumentException("Elements in a range must not be null: element1=" + t + ", element2=" + t2);
        }
        if (comparator == null) {
            this.a = a.INSTANCE;
        } else {
            this.a = comparator;
        }
        if (this.a.compare(t, t2) < 1) {
            this.b = t;
            this.c = t2;
        } else {
            this.b = t2;
            this.c = t;
        }
    }

    /* JADX WARN: Incorrect types in method signature: <T::Ljava/lang/Comparable<TT;>;>(TT;TT;)Lorg/apache/commons/lang3/Range<TT;>; */
    public static Range between(Comparable comparable, Comparable comparable2) {
        return between(comparable, comparable2, null);
    }

    /* JADX WARN: Incorrect types in method signature: <T::Ljava/lang/Comparable<TT;>;>(TT;)Lorg/apache/commons/lang3/Range<TT;>; */
    public static Range is(Comparable comparable) {
        return between(comparable, comparable, null);
    }

    public boolean contains(T t) {
        return t != null && this.a.compare(t, this.b) > -1 && this.a.compare(t, this.c) < 1;
    }

    public boolean containsRange(Range<T> range) {
        return range != null && contains(range.b) && contains(range.c);
    }

    public int elementCompareTo(T t) {
        Validate.notNull(t, "Element is null", new Object[0]);
        if (isAfter(t)) {
            return -1;
        }
        return isBefore(t) ? 1 : 0;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != Range.class) {
            return false;
        }
        Range range = (Range) obj;
        return this.b.equals(range.b) && this.c.equals(range.c);
    }

    public Comparator<T> getComparator() {
        return this.a;
    }

    public T getMaximum() {
        return this.c;
    }

    public T getMinimum() {
        return this.b;
    }

    public int hashCode() {
        int i = this.d;
        if (i != 0) {
            return i;
        }
        int iHashCode = this.c.hashCode() + ((this.b.hashCode() + ((Range.class.hashCode() + 629) * 37)) * 37);
        this.d = iHashCode;
        return iHashCode;
    }

    public Range<T> intersectionWith(Range<T> range) {
        if (!isOverlappedBy(range)) {
            throw new IllegalArgumentException(String.format("Cannot calculate intersection with non-overlapping range %s", range));
        }
        if (equals(range)) {
            return this;
        }
        return between(getComparator().compare(this.b, range.b) < 0 ? range.b : this.b, getComparator().compare(this.c, range.c) < 0 ? this.c : range.c, getComparator());
    }

    public boolean isAfter(T t) {
        return t != null && this.a.compare(t, this.b) < 0;
    }

    public boolean isAfterRange(Range<T> range) {
        if (range == null) {
            return false;
        }
        return isAfter(range.c);
    }

    public boolean isBefore(T t) {
        return t != null && this.a.compare(t, this.c) > 0;
    }

    public boolean isBeforeRange(Range<T> range) {
        if (range == null) {
            return false;
        }
        return isBefore(range.b);
    }

    public boolean isEndedBy(T t) {
        return t != null && this.a.compare(t, this.c) == 0;
    }

    public boolean isNaturalOrdering() {
        return this.a == a.INSTANCE;
    }

    public boolean isOverlappedBy(Range<T> range) {
        if (range == null) {
            return false;
        }
        return range.contains(this.b) || range.contains(this.c) || contains(range.b);
    }

    public boolean isStartedBy(T t) {
        return t != null && this.a.compare(t, this.b) == 0;
    }

    public String toString() {
        if (this.e == null) {
            StringBuilder sbA = g9.a("[");
            sbA.append(this.b);
            sbA.append("..");
            sbA.append(this.c);
            sbA.append("]");
            this.e = sbA.toString();
        }
        return this.e;
    }

    public static <T> Range<T> between(T t, T t2, Comparator<T> comparator) {
        return new Range<>(t, t2, comparator);
    }

    public static <T> Range<T> is(T t, Comparator<T> comparator) {
        return between(t, t, comparator);
    }

    public String toString(String str) {
        return String.format(str, this.b, this.c, this.a);
    }
}
