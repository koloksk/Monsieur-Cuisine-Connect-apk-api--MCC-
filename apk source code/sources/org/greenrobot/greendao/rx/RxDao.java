package org.greenrobot.greendao.rx;

import defpackage.to;
import java.util.List;
import java.util.concurrent.Callable;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.annotation.apihint.Experimental;
import rx.Observable;
import rx.Scheduler;

@Experimental
/* loaded from: classes.dex */
public class RxDao<T, K> extends to {
    public final AbstractDao<T, K> a;

    public class a implements Callable<T> {
        public final /* synthetic */ Object a;

        public a(Object obj) {
            this.a = obj;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public T call() throws Exception {
            RxDao.this.a.save(this.a);
            return (T) this.a;
        }
    }

    public class b implements Callable<Iterable<T>> {
        public final /* synthetic */ Iterable a;

        public b(Iterable iterable) {
            this.a = iterable;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            RxDao.this.a.saveInTx(this.a);
            return this.a;
        }
    }

    public class c implements Callable<Object[]> {
        public final /* synthetic */ Object[] a;

        public c(Object[] objArr) {
            this.a = objArr;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public Object[] call() throws Exception {
            RxDao.this.a.saveInTx(this.a);
            return this.a;
        }
    }

    public class d implements Callable<T> {
        public final /* synthetic */ Object a;

        public d(Object obj) {
            this.a = obj;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public T call() throws Exception {
            RxDao.this.a.update(this.a);
            return (T) this.a;
        }
    }

    public class e implements Callable<Iterable<T>> {
        public final /* synthetic */ Iterable a;

        public e(Iterable iterable) {
            this.a = iterable;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            RxDao.this.a.updateInTx(this.a);
            return this.a;
        }
    }

    public class f implements Callable<Object[]> {
        public final /* synthetic */ Object[] a;

        public f(Object[] objArr) {
            this.a = objArr;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public Object[] call() throws Exception {
            RxDao.this.a.updateInTx(this.a);
            return this.a;
        }
    }

    public class g implements Callable<Void> {
        public final /* synthetic */ Object a;

        public g(Object obj) {
            this.a = obj;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public Void call() throws Exception {
            RxDao.this.a.delete(this.a);
            return null;
        }
    }

    public class h implements Callable<Void> {
        public final /* synthetic */ Object a;

        public h(Object obj) {
            this.a = obj;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public Void call() throws Exception {
            RxDao.this.a.deleteByKey(this.a);
            return null;
        }
    }

    public class i implements Callable<Void> {
        public i() {
        }

        @Override // java.util.concurrent.Callable
        public Void call() throws Exception {
            RxDao.this.a.deleteAll();
            return null;
        }
    }

    public class j implements Callable<Void> {
        public final /* synthetic */ Iterable a;

        public j(Iterable iterable) {
            this.a = iterable;
        }

        @Override // java.util.concurrent.Callable
        public Void call() throws Exception {
            RxDao.this.a.deleteInTx(this.a);
            return null;
        }
    }

    public class k implements Callable<List<T>> {
        public k() {
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            return RxDao.this.a.loadAll();
        }
    }

    public class l implements Callable<Void> {
        public final /* synthetic */ Object[] a;

        public l(Object[] objArr) {
            this.a = objArr;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public Void call() throws Exception {
            RxDao.this.a.deleteInTx(this.a);
            return null;
        }
    }

    public class m implements Callable<Void> {
        public final /* synthetic */ Iterable a;

        public m(Iterable iterable) {
            this.a = iterable;
        }

        @Override // java.util.concurrent.Callable
        public Void call() throws Exception {
            RxDao.this.a.deleteByKeyInTx(this.a);
            return null;
        }
    }

    public class n implements Callable<Void> {
        public final /* synthetic */ Object[] a;

        public n(Object[] objArr) {
            this.a = objArr;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public Void call() throws Exception {
            RxDao.this.a.deleteByKeyInTx(this.a);
            return null;
        }
    }

    public class o implements Callable<Long> {
        public o() {
        }

        @Override // java.util.concurrent.Callable
        public Long call() throws Exception {
            return Long.valueOf(RxDao.this.a.count());
        }
    }

    public class p implements Callable<T> {
        public final /* synthetic */ Object a;

        public p(Object obj) {
            this.a = obj;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public T call() throws Exception {
            return (T) RxDao.this.a.load(this.a);
        }
    }

    public class q implements Callable<T> {
        public final /* synthetic */ Object a;

        public q(Object obj) {
            this.a = obj;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public T call() throws Exception {
            RxDao.this.a.refresh(this.a);
            return (T) this.a;
        }
    }

    public class r implements Callable<T> {
        public final /* synthetic */ Object a;

        public r(Object obj) {
            this.a = obj;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public T call() throws Exception {
            RxDao.this.a.insert(this.a);
            return (T) this.a;
        }
    }

    public class s implements Callable<Iterable<T>> {
        public final /* synthetic */ Iterable a;

        public s(Iterable iterable) {
            this.a = iterable;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            RxDao.this.a.insertInTx(this.a);
            return this.a;
        }
    }

    public class t implements Callable<Object[]> {
        public final /* synthetic */ Object[] a;

        public t(Object[] objArr) {
            this.a = objArr;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public Object[] call() throws Exception {
            RxDao.this.a.insertInTx(this.a);
            return this.a;
        }
    }

    public class u implements Callable<T> {
        public final /* synthetic */ Object a;

        public u(Object obj) {
            this.a = obj;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public T call() throws Exception {
            RxDao.this.a.insertOrReplace(this.a);
            return (T) this.a;
        }
    }

    public class v implements Callable<Iterable<T>> {
        public final /* synthetic */ Iterable a;

        public v(Iterable iterable) {
            this.a = iterable;
        }

        @Override // java.util.concurrent.Callable
        public Object call() throws Exception {
            RxDao.this.a.insertOrReplaceInTx(this.a);
            return this.a;
        }
    }

    public class w implements Callable<Object[]> {
        public final /* synthetic */ Object[] a;

        public w(Object[] objArr) {
            this.a = objArr;
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // java.util.concurrent.Callable
        public Object[] call() throws Exception {
            RxDao.this.a.insertOrReplaceInTx(this.a);
            return this.a;
        }
    }

    @Experimental
    public RxDao(AbstractDao<T, K> abstractDao) {
        this(abstractDao, null);
    }

    @Experimental
    public Observable<Long> count() {
        return wrap(new o());
    }

    @Experimental
    public Observable<Void> delete(T t2) {
        return wrap(new g(t2));
    }

    @Experimental
    public Observable<Void> deleteAll() {
        return wrap(new i());
    }

    @Experimental
    public Observable<Void> deleteByKey(K k2) {
        return wrap(new h(k2));
    }

    @Experimental
    public Observable<Void> deleteByKeyInTx(Iterable<K> iterable) {
        return wrap(new m(iterable));
    }

    @Experimental
    public Observable<Void> deleteInTx(Iterable<T> iterable) {
        return wrap(new j(iterable));
    }

    @Experimental
    public AbstractDao<T, K> getDao() {
        return this.a;
    }

    @Override // defpackage.to
    @Experimental
    public /* bridge */ /* synthetic */ Scheduler getScheduler() {
        return super.getScheduler();
    }

    @Experimental
    public Observable<T> insert(T t2) {
        return (Observable<T>) wrap(new r(t2));
    }

    @Experimental
    public Observable<Iterable<T>> insertInTx(Iterable<T> iterable) {
        return (Observable<Iterable<T>>) wrap(new s(iterable));
    }

    @Experimental
    public Observable<T> insertOrReplace(T t2) {
        return (Observable<T>) wrap(new u(t2));
    }

    @Experimental
    public Observable<Iterable<T>> insertOrReplaceInTx(Iterable<T> iterable) {
        return (Observable<Iterable<T>>) wrap(new v(iterable));
    }

    @Experimental
    public Observable<T> load(K k2) {
        return (Observable<T>) wrap(new p(k2));
    }

    @Experimental
    public Observable<List<T>> loadAll() {
        return (Observable<List<T>>) wrap(new k());
    }

    @Experimental
    public Observable<T> refresh(T t2) {
        return (Observable<T>) wrap(new q(t2));
    }

    @Experimental
    public Observable<T> save(T t2) {
        return (Observable<T>) wrap(new a(t2));
    }

    @Experimental
    public Observable<Iterable<T>> saveInTx(Iterable<T> iterable) {
        return (Observable<Iterable<T>>) wrap(new b(iterable));
    }

    @Experimental
    public Observable<T> update(T t2) {
        return (Observable<T>) wrap(new d(t2));
    }

    @Experimental
    public Observable<Iterable<T>> updateInTx(Iterable<T> iterable) {
        return (Observable<Iterable<T>>) wrap(new e(iterable));
    }

    @Experimental
    public RxDao(AbstractDao<T, K> abstractDao, Scheduler scheduler) {
        super(scheduler);
        this.a = abstractDao;
    }

    @Experimental
    public Observable<Void> deleteByKeyInTx(K... kArr) {
        return wrap(new n(kArr));
    }

    @Experimental
    public Observable<Void> deleteInTx(T... tArr) {
        return wrap(new l(tArr));
    }

    @Experimental
    public Observable<Object[]> insertInTx(T... tArr) {
        return wrap(new t(tArr));
    }

    @Experimental
    public Observable<Object[]> insertOrReplaceInTx(T... tArr) {
        return wrap(new w(tArr));
    }

    @Experimental
    public Observable<Object[]> saveInTx(T... tArr) {
        return wrap(new c(tArr));
    }

    @Experimental
    public Observable<Object[]> updateInTx(T... tArr) {
        return wrap(new f(tArr));
    }
}
