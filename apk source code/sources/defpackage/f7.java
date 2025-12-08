package defpackage;

/* loaded from: classes.dex */
public class f7<T> {
    public final T a;

    public f7(T t) {
        if (t == null) {
            throw new IllegalArgumentException("Wrapped Object can not be null.");
        }
        this.a = t;
    }

    public T getWrappedObject() {
        return this.a;
    }
}
