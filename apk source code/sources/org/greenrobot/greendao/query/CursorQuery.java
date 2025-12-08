package org.greenrobot.greendao.query;

import android.database.Cursor;
import defpackage.po;
import defpackage.qo;
import defpackage.ro;
import java.util.Date;
import org.greenrobot.greendao.AbstractDao;

/* loaded from: classes.dex */
public class CursorQuery<T> extends ro<T> {
    public final b<T> a;

    public static final class b<T2> extends qo<T2, CursorQuery<T2>> {
        public final int e;
        public final int f;

        public b(AbstractDao abstractDao, String str, String[] strArr, int i, int i2) {
            super(abstractDao, str, strArr);
            this.e = i;
            this.f = i2;
        }

        @Override // defpackage.qo
        public po a() {
            return new CursorQuery(this, this.b, this.a, (String[]) this.c.clone(), this.e, this.f, null);
        }
    }

    public /* synthetic */ CursorQuery(b bVar, AbstractDao abstractDao, String str, String[] strArr, int i, int i2, a aVar) {
        super(abstractDao, str, strArr, i, i2);
        this.a = bVar;
    }

    public static <T2> CursorQuery<T2> a(AbstractDao<T2, ?> abstractDao, String str, Object[] objArr, int i, int i2) {
        return new b(abstractDao, str, po.toStringArray(objArr), i, i2).b();
    }

    public static <T2> CursorQuery<T2> internalCreate(AbstractDao<T2, ?> abstractDao, String str, Object[] objArr) {
        return a(abstractDao, str, objArr, -1, -1);
    }

    public CursorQuery forCurrentThread() {
        return this.a.a(this);
    }

    public Cursor query() {
        checkThread();
        return this.dao.getDatabase().rawQuery(this.sql, this.parameters);
    }

    @Override // defpackage.ro
    public /* bridge */ /* synthetic */ void setLimit(int i) {
        super.setLimit(i);
    }

    @Override // defpackage.ro
    public /* bridge */ /* synthetic */ void setOffset(int i) {
        super.setOffset(i);
    }

    @Override // defpackage.ro, defpackage.po
    public CursorQuery<T> setParameter(int i, Object obj) {
        return (CursorQuery) super.setParameter(i, obj);
    }

    @Override // defpackage.po
    public CursorQuery<T> setParameter(int i, Date date) {
        return (CursorQuery) super.setParameter(i, date);
    }

    @Override // defpackage.po
    public CursorQuery<T> setParameter(int i, Boolean bool) {
        return (CursorQuery) super.setParameter(i, bool);
    }
}
