package org.greenrobot.greendao;

import android.database.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.greenrobot.greendao.annotation.apihint.Experimental;
import org.greenrobot.greendao.async.AsyncSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.rx.RxTransaction;
import rx.schedulers.Schedulers;

/* loaded from: classes.dex */
public class AbstractDaoSession {
    public final Database a;
    public final Map<Class<?>, AbstractDao<?, ?>> b = new HashMap();
    public volatile RxTransaction c;
    public volatile RxTransaction d;

    public AbstractDaoSession(Database database) {
        this.a = database;
    }

    public <V> V callInTx(Callable<V> callable) throws Exception {
        this.a.beginTransaction();
        try {
            V vCall = callable.call();
            this.a.setTransactionSuccessful();
            return vCall;
        } finally {
            this.a.endTransaction();
        }
    }

    public <V> V callInTxNoException(Callable<V> callable) {
        this.a.beginTransaction();
        try {
            try {
                V vCall = callable.call();
                this.a.setTransactionSuccessful();
                return vCall;
            } catch (Exception e) {
                throw new DaoException("Callable failed", e);
            }
        } finally {
            this.a.endTransaction();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void delete(T t) {
        getDao(t.getClass()).delete(t);
    }

    public <T> void deleteAll(Class<T> cls) throws SQLException {
        getDao(cls).deleteAll();
    }

    public Collection<AbstractDao<?, ?>> getAllDaos() {
        return Collections.unmodifiableCollection(this.b.values());
    }

    public AbstractDao<?, ?> getDao(Class<? extends Object> cls) {
        AbstractDao<?, ?> abstractDao = this.b.get(cls);
        if (abstractDao != null) {
            return abstractDao;
        }
        throw new DaoException("No DAO registered for " + cls);
    }

    public Database getDatabase() {
        return this.a;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> long insert(T t) {
        return getDao(t.getClass()).insert(t);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> long insertOrReplace(T t) {
        return getDao(t.getClass()).insertOrReplace(t);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T, K> T load(Class<T> cls, K k) {
        return (T) getDao(cls).load(k);
    }

    public <T, K> List<T> loadAll(Class<T> cls) {
        return (List<T>) getDao(cls).loadAll();
    }

    public <T> QueryBuilder<T> queryBuilder(Class<T> cls) {
        return (QueryBuilder<T>) getDao(cls).queryBuilder();
    }

    public <T, K> List<T> queryRaw(Class<T> cls, String str, String... strArr) {
        return (List<T>) getDao(cls).queryRaw(str, strArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void refresh(T t) {
        getDao(t.getClass()).refresh(t);
    }

    public <T> void registerDao(Class<T> cls, AbstractDao<T, ?> abstractDao) {
        this.b.put(cls, abstractDao);
    }

    public void runInTx(Runnable runnable) {
        this.a.beginTransaction();
        try {
            runnable.run();
            this.a.setTransactionSuccessful();
        } finally {
            this.a.endTransaction();
        }
    }

    @Experimental
    public RxTransaction rxTx() {
        if (this.d == null) {
            this.d = new RxTransaction(this, Schedulers.io());
        }
        return this.d;
    }

    @Experimental
    public RxTransaction rxTxPlain() {
        if (this.c == null) {
            this.c = new RxTransaction(this);
        }
        return this.c;
    }

    public AsyncSession startAsyncSession() {
        return new AsyncSession(this);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <T> void update(T t) {
        getDao(t.getClass()).update(t);
    }
}
