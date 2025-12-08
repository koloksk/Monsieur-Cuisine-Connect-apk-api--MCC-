package org.greenrobot.greendao.query;

import defpackage.po;
import defpackage.qo;
import defpackage.ro;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.apihint.Internal;
import org.greenrobot.greendao.rx.RxQuery;
import rx.schedulers.Schedulers;

/* loaded from: classes.dex */
public class Query<T> extends ro<T> {
    public final b<T> a;
    public volatile RxQuery b;
    public volatile RxQuery c;

    public static final class b<T2> extends qo<T2, Query<T2>> {
        public final int e;
        public final int f;

        public b(AbstractDao<T2, ?> abstractDao, String str, String[] strArr, int i, int i2) {
            super(abstractDao, str, strArr);
            this.e = i;
            this.f = i2;
        }

        @Override // defpackage.qo
        public po a() {
            return new Query(this, this.b, this.a, (String[]) this.c.clone(), this.e, this.f, null);
        }
    }

    public /* synthetic */ Query(b bVar, AbstractDao abstractDao, String str, String[] strArr, int i, int i2, a aVar) {
        super(abstractDao, str, strArr, i, i2);
        this.a = bVar;
    }

    public static <T2> Query<T2> a(AbstractDao<T2, ?> abstractDao, String str, Object[] objArr, int i, int i2) {
        return new b(abstractDao, str, po.toStringArray(objArr), i, i2).b();
    }

    public static <T2> Query<T2> internalCreate(AbstractDao<T2, ?> abstractDao, String str, Object[] objArr) {
        return a(abstractDao, str, objArr, -1, -1);
    }

    @Internal
    public RxQuery __InternalRx() {
        if (this.c == null) {
            this.c = new RxQuery(this, Schedulers.io());
        }
        return this.c;
    }

    @Internal
    public RxQuery __internalRxPlain() {
        if (this.b == null) {
            this.b = new RxQuery(this);
        }
        return this.b;
    }

    public Query<T> forCurrentThread() {
        return (Query) this.a.a(this);
    }

    public List<T> list() {
        checkThread();
        return this.daoAccess.loadAllAndCloseCursor(this.dao.getDatabase().rawQuery(this.sql, this.parameters));
    }

    public CloseableListIterator<T> listIterator() {
        return listLazyUncached().listIteratorAutoClose();
    }

    public LazyList<T> listLazy() {
        checkThread();
        return new LazyList<>(this.daoAccess, this.dao.getDatabase().rawQuery(this.sql, this.parameters), true);
    }

    public LazyList<T> listLazyUncached() {
        checkThread();
        return new LazyList<>(this.daoAccess, this.dao.getDatabase().rawQuery(this.sql, this.parameters), false);
    }

    @Override // defpackage.ro
    public /* bridge */ /* synthetic */ void setLimit(int i) {
        super.setLimit(i);
    }

    @Override // defpackage.ro
    public /* bridge */ /* synthetic */ void setOffset(int i) {
        super.setOffset(i);
    }

    public T unique() {
        checkThread();
        return this.daoAccess.loadUniqueAndCloseCursor(this.dao.getDatabase().rawQuery(this.sql, this.parameters));
    }

    public T uniqueOrThrow() {
        T tUnique = unique();
        if (tUnique != null) {
            return tUnique;
        }
        throw new DaoException("No entity found for query");
    }

    @Override // defpackage.ro, defpackage.po
    public Query<T> setParameter(int i, Object obj) {
        return (Query) super.setParameter(i, obj);
    }

    @Override // defpackage.po
    public Query<T> setParameter(int i, Date date) {
        return (Query) super.setParameter(i, date);
    }

    @Override // defpackage.po
    public Query<T> setParameter(int i, Boolean bool) {
        return (Query) super.setParameter(i, bool);
    }
}
