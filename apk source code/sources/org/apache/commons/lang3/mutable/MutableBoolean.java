package org.apache.commons.lang3.mutable;

import java.io.Serializable;
import org.apache.commons.lang3.BooleanUtils;

/* loaded from: classes.dex */
public class MutableBoolean implements Mutable<Boolean>, Serializable, Comparable<MutableBoolean> {
    public static final long serialVersionUID = -4830728138360036487L;
    public boolean a;

    public MutableBoolean() {
    }

    public boolean booleanValue() {
        return this.a;
    }

    public boolean equals(Object obj) {
        return (obj instanceof MutableBoolean) && this.a == ((MutableBoolean) obj).booleanValue();
    }

    public int hashCode() {
        return (this.a ? Boolean.TRUE : Boolean.FALSE).hashCode();
    }

    public boolean isFalse() {
        return !this.a;
    }

    public boolean isTrue() {
        return this.a;
    }

    public void setFalse() {
        this.a = false;
    }

    public void setTrue() {
        this.a = true;
    }

    public Boolean toBoolean() {
        return Boolean.valueOf(booleanValue());
    }

    public String toString() {
        return String.valueOf(this.a);
    }

    public MutableBoolean(boolean z) {
        this.a = z;
    }

    @Override // java.lang.Comparable
    public int compareTo(MutableBoolean mutableBoolean) {
        return BooleanUtils.compare(this.a, mutableBoolean.a);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.apache.commons.lang3.mutable.Mutable
    /* renamed from: getValue */
    public Boolean getValue2() {
        return Boolean.valueOf(this.a);
    }

    public void setValue(boolean z) {
        this.a = z;
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    public void setValue(Boolean bool) {
        this.a = bool.booleanValue();
    }

    public MutableBoolean(Boolean bool) {
        this.a = bool.booleanValue();
    }
}
