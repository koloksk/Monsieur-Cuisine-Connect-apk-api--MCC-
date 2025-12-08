package org.greenrobot.greendao.query;

import android.database.Cursor;
import defpackage.po;
import defpackage.qo;
import java.util.Date;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.DaoException;

/* loaded from: classes.dex */
public class CountQuery<T> extends po<T> {
    public final b<T> a;

    public static final class b<T2> extends qo<T2, CountQuery<T2>> {
        public /* synthetic */ b(AbstractDao abstractDao, String str, String[] strArr, a aVar) {
            super(abstractDao, str, strArr);
        }

        @Override // defpackage.qo
        public po a() {
            return new CountQuery(this, this.b, this.a, (String[]) this.c.clone(), null);
        }
    }

    public /* synthetic */ CountQuery(b bVar, AbstractDao abstractDao, String str, String[] strArr, a aVar) {
        super(abstractDao, str, strArr);
        this.a = bVar;
    }

    public long count() {
        checkThread();
        Cursor cursorRawQuery = this.dao.getDatabase().rawQuery(this.sql, this.parameters);
        try {
            if (!cursorRawQuery.moveToNext()) {
                throw new DaoException("No result for count");
            }
            if (!cursorRawQuery.isLast()) {
                throw new DaoException("Unexpected row count: " + cursorRawQuery.getCount());
            }
            if (cursorRawQuery.getColumnCount() == 1) {
                return cursorRawQuery.getLong(0);
            }
            throw new DaoException("Unexpected column count: " + cursorRawQuery.getColumnCount());
        } finally {
            cursorRawQuery.close();
        }
    }

    public CountQuery<T> forCurrentThread() {
        return (CountQuery) this.a.a(this);
    }

    @Override // defpackage.po
    public CountQuery<T> setParameter(int i, Object obj) {
        return (CountQuery) super.setParameter(i, obj);
    }

    @Override // defpackage.po
    public CountQuery<T> setParameter(int i, Date date) {
        return (CountQuery) super.setParameter(i, date);
    }

    @Override // defpackage.po
    public CountQuery<T> setParameter(int i, Boolean bool) {
        return (CountQuery) super.setParameter(i, bool);
    }
}
