package defpackage;

import io.reactivex.disposables.Disposable;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

/* loaded from: classes.dex */
public final class uk extends AtomicReference<Future<?>> implements Disposable {
    public static final long serialVersionUID = 6545242830671168775L;
    public final boolean a;

    public uk(Future<?> future, boolean z) {
        super(future);
        this.a = z;
    }

    @Override // io.reactivex.disposables.Disposable
    public void dispose() {
        Future<?> andSet = getAndSet(null);
        if (andSet != null) {
            andSet.cancel(this.a);
        }
    }

    @Override // io.reactivex.disposables.Disposable
    public boolean isDisposed() {
        Future<?> future = get();
        return future == null || future.isDone();
    }
}
