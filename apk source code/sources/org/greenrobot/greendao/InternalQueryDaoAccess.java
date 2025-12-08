package org.greenrobot.greendao;

import android.database.Cursor;
import java.util.List;
import org.greenrobot.greendao.internal.TableStatements;

/* loaded from: classes.dex */
public final class InternalQueryDaoAccess<T> {
    public final AbstractDao<T, ?> a;

    public InternalQueryDaoAccess(AbstractDao<T, ?> abstractDao) {
        this.a = abstractDao;
    }

    public TableStatements getStatements() {
        return this.a.config.statements;
    }

    public List<T> loadAllAndCloseCursor(Cursor cursor) {
        return this.a.loadAllAndCloseCursor(cursor);
    }

    public T loadCurrent(Cursor cursor, int i, boolean z) {
        return this.a.loadCurrent(cursor, i, z);
    }

    public T loadUniqueAndCloseCursor(Cursor cursor) {
        return this.a.loadUniqueAndCloseCursor(cursor);
    }

    public static <T2> TableStatements getStatements(AbstractDao<T2, ?> abstractDao) {
        return abstractDao.config.statements;
    }
}
