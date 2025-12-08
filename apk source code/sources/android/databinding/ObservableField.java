package android.databinding;

import android.support.annotation.Nullable;
import defpackage.o1;
import java.io.Serializable;

/* loaded from: classes.dex */
public class ObservableField<T> extends o1 implements Serializable {
    public static final long serialVersionUID = 1;
    public T b;

    public ObservableField(T t) {
        this.b = t;
    }

    @Nullable
    public T get() {
        return this.b;
    }

    public void set(T t) {
        if (t != this.b) {
            this.b = t;
            notifyChange();
        }
    }

    public ObservableField() {
    }

    public ObservableField(Observable... observableArr) {
        super(observableArr);
    }
}
