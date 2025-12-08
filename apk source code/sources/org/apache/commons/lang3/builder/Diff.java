package org.apache.commons.lang3.builder;

import java.lang.reflect.Type;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.apache.commons.lang3.tuple.Pair;

/* loaded from: classes.dex */
public abstract class Diff<T> extends Pair<T, T> {
    public static final long serialVersionUID = 1;
    public final Type a = (Type) ObjectUtils.defaultIfNull(TypeUtils.getTypeArguments(getClass(), Diff.class).get(Diff.class.getTypeParameters()[0]), Object.class);
    public final String b;

    public Diff(String str) {
        this.b = str;
    }

    public final String getFieldName() {
        return this.b;
    }

    public final Type getType() {
        return this.a;
    }

    @Override // java.util.Map.Entry
    public final T setValue(T t) {
        throw new UnsupportedOperationException("Cannot alter Diff object.");
    }

    @Override // org.apache.commons.lang3.tuple.Pair
    public final String toString() {
        return String.format("[%s: %s, %s]", this.b, getLeft(), getRight());
    }
}
