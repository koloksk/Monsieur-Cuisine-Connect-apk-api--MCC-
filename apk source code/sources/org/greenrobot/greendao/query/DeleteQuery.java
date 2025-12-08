package org.greenrobot.greendao.query;

import android.database.SQLException;
import defpackage.po;
import defpackage.qo;
import java.util.Date;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

/* loaded from: classes.dex */
public class DeleteQuery<T> extends po<T> {
    public final b<T> a;

    public static final class b<T2> extends qo<T2, DeleteQuery<T2>> {
        public /* synthetic */ b(AbstractDao abstractDao, String str, String[] strArr, a aVar) {
            super(abstractDao, str, strArr);
        }

        @Override // defpackage.qo
        public po a() {
            return new DeleteQuery(this, this.b, this.a, (String[]) this.c.clone(), null);
        }
    }

    public /* synthetic */ DeleteQuery(b bVar, AbstractDao abstractDao, String str, String[] strArr, a aVar) {
        super(abstractDao, str, strArr);
        this.a = bVar;
    }

    public void executeDeleteWithoutDetachingEntities() throws SQLException {
        checkThread();
        Database database = this.dao.getDatabase();
        if (database.isDbLockedByCurrentThread()) {
            this.dao.getDatabase().execSQL(this.sql, this.parameters);
            return;
        }
        database.beginTransaction();
        try {
            this.dao.getDatabase().execSQL(this.sql, this.parameters);
            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
        }
    }

    public DeleteQuery<T> forCurrentThread() {
        return (DeleteQuery) this.a.a(this);
    }

    @Override // defpackage.po
    public DeleteQuery<T> setParameter(int i, Object obj) {
        return (DeleteQuery) super.setParameter(i, obj);
    }

    @Override // defpackage.po
    public DeleteQuery<T> setParameter(int i, Date date) {
        return (DeleteQuery) super.setParameter(i, date);
    }

    @Override // defpackage.po
    public DeleteQuery<T> setParameter(int i, Boolean bool) {
        return (DeleteQuery) super.setParameter(i, bool);
    }
}
