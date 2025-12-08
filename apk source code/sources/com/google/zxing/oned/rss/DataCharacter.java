package com.google.zxing.oned.rss;

/* loaded from: classes.dex */
public class DataCharacter {
    public final int a;
    public final int b;

    public DataCharacter(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof DataCharacter)) {
            return false;
        }
        DataCharacter dataCharacter = (DataCharacter) obj;
        return this.a == dataCharacter.a && this.b == dataCharacter.b;
    }

    public final int getChecksumPortion() {
        return this.b;
    }

    public final int getValue() {
        return this.a;
    }

    public final int hashCode() {
        return this.a ^ this.b;
    }

    public final String toString() {
        return this.a + "(" + this.b + ')';
    }
}
