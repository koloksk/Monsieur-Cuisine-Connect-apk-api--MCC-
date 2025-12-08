package org.greenrobot.greendao.rx;

import defpackage.to;
import java.util.List;
import java.util.concurrent.Callable;
import org.greenrobot.greendao.annotation.apihint.Experimental;
import org.greenrobot.greendao.query.Query;
import rx.Observable;
import rx.Scheduler;

@Experimental
/* loaded from: classes.dex */
public class RxQuery<T> extends to {
    public final Query<T> a;

    public class a implements Callable<List<T>> {
        public a() {
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            return RxQuery.this.a.forCurrentThread().list();
        }
    }

    public class b implements Callable<T> {
        public b() {
        }

        @Override // java.util.concurrent.Callable
        public T call() throws Exception {
            return RxQuery.this.a.forCurrentThread().unique();
        }
    }

    public class c implements Observable.OnSubscribe<T> {
        public c(RxQuery rxQuery) {
        }
    }

    public RxQuery(Query<T> query) {
        this.a = query;
    }

    @Override // defpackage.to
    @Experimental
    public /* bridge */ /* synthetic */ Scheduler getScheduler() {
        return super.getScheduler();
    }

    @Experimental
    public Observable<List<T>> list() {
        return (Observable<List<T>>) wrap(new a());
    }

    public Observable<T> oneByOne() {
        return (Observable<T>) wrap(Observable.create(new c(this)));
    }

    @Experimental
    public Observable<T> unique() {
        return (Observable<T>) wrap(new b());
    }

    public RxQuery(Query<T> query, Scheduler scheduler) {
        super(scheduler);
        this.a = query;
    }
}
