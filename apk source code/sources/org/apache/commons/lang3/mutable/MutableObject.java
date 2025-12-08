package org.apache.commons.lang3.mutable;

import java.io.Serializable;

/* loaded from: classes.dex */
public class MutableObject<T> implements Mutable<T>, Serializable {
    public static final long serialVersionUID = 86241875189L;
    public T a;

    public MutableObject() {
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (MutableObject.class == obj.getClass()) {
            return this.a.equals(((MutableObject) obj).a);
        }
        return false;
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    public T getValue() {
        return this.a;
    }

    public int hashCode() {
        T t = this.a;
        if (t == null) {
            return 0;
        }
        return t.hashCode();
    }

    @Override // org.apache.commons.lang3.mutable.Mutable
    public void setValue(T t) {
        this.a = t;
    }

    public String toString() {
        T t = this.a;
        return t == null ? "null" : t.toString();
    }

    public MutableObject(T t) {
        this.a = t;
    }
}
