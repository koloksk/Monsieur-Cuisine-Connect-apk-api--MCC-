package org.greenrobot.greendao;

import android.database.Cursor;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScope;
import org.greenrobot.greendao.internal.DaoConfig;

/* loaded from: classes.dex */
public class InternalUnitTestDaoAccess<T, K> {
    public final AbstractDao<T, K> a;

    public InternalUnitTestDaoAccess(Database database, Class<AbstractDao<T, K>> cls, IdentityScope<?, ?> identityScope) throws Exception {
        DaoConfig daoConfig = new DaoConfig(database, cls);
        daoConfig.setIdentityScope(identityScope);
        this.a = cls.getConstructor(DaoConfig.class).newInstance(daoConfig);
    }

    public AbstractDao<T, K> getDao() {
        return this.a;
    }

    public K getKey(T t) {
        return this.a.getKey(t);
    }

    public Property[] getProperties() {
        return this.a.getProperties();
    }

    public boolean isEntityUpdateable() {
        return this.a.isEntityUpdateable();
    }

    public T readEntity(Cursor cursor, int i) {
        return this.a.readEntity(cursor, i);
    }

    public K readKey(Cursor cursor, int i) {
        return this.a.readKey(cursor, i);
    }
}
