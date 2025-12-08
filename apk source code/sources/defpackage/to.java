package defpackage;

import java.util.concurrent.Callable;
import org.greenrobot.greendao.annotation.apihint.Experimental;
import org.greenrobot.greendao.annotation.apihint.Internal;
import rx.Observable;
import rx.Scheduler;

@Internal
/* loaded from: classes.dex */
public class to {
    public final Scheduler scheduler;

    public to() {
        this.scheduler = null;
    }

    @Experimental
    public Scheduler getScheduler() {
        return this.scheduler;
    }

    public <R> Observable<R> wrap(Callable<R> callable) {
        return wrap(q5.a((Callable) callable));
    }

    public <R> Observable<R> wrap(Observable<R> observable) {
        Scheduler scheduler = this.scheduler;
        return scheduler != null ? observable.subscribeOn(scheduler) : observable;
    }

    @Experimental
    public to(Scheduler scheduler) {
        this.scheduler = scheduler;
    }
}
