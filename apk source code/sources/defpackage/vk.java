package defpackage;

import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.functions.ObjectHelper;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public abstract class vk<T> extends AtomicReference<T> implements Disposable {
    public static final long serialVersionUID = 6537757548749041217L;

    public vk(T t) {
        super(ObjectHelper.requireNonNull(t, "value is null"));
    }

    public abstract void a(@NonNull T t);

    @Override // io.reactivex.disposables.Disposable
    public final void dispose() {
        T andSet;
        if (get() == null || (andSet = getAndSet(null)) == null) {
            return;
        }
        a(andSet);
    }

    @Override // io.reactivex.disposables.Disposable
    public final boolean isDisposed() {
        return get() == null;
    }
}
