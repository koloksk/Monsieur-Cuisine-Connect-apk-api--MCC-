package defpackage;

import io.reactivex.annotations.NonNull;

/* loaded from: classes.dex */
public final class wk extends vk<Runnable> {
    public static final long serialVersionUID = -8219729196779211169L;

    public wk(Runnable runnable) {
        super(runnable);
    }

    @Override // defpackage.vk
    public void a(@NonNull Runnable runnable) {
        runnable.run();
    }

    @Override // java.util.concurrent.atomic.AtomicReference
    public String toString() {
        StringBuilder sbA = g9.a("RunnableDisposable(disposed=");
        sbA.append(isDisposed());
        sbA.append(", ");
        sbA.append(get());
        sbA.append(")");
        return sbA.toString();
    }
}
