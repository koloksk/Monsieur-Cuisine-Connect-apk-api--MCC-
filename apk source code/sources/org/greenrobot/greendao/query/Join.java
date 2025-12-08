package org.greenrobot.greendao.query;

import defpackage.so;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;

/* loaded from: classes.dex */
public class Join<SRC, DST> {
    public final String a;
    public final AbstractDao<DST, ?> b;
    public final Property c;
    public final Property d;
    public final String e;
    public final so<DST> f;

    public Join(String str, Property property, AbstractDao<DST, ?> abstractDao, Property property2, String str2) {
        this.a = str;
        this.c = property;
        this.b = abstractDao;
        this.d = property2;
        this.e = str2;
        this.f = new so<>(abstractDao, str2);
    }

    public WhereCondition and(WhereCondition whereCondition, WhereCondition whereCondition2, WhereCondition... whereConditionArr) {
        return this.f.a(" AND ", whereCondition, whereCondition2, whereConditionArr);
    }

    public String getTablePrefix() {
        return this.e;
    }

    public WhereCondition or(WhereCondition whereCondition, WhereCondition whereCondition2, WhereCondition... whereConditionArr) {
        return this.f.a(" OR ", whereCondition, whereCondition2, whereConditionArr);
    }

    public Join<SRC, DST> where(WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        this.f.a(whereCondition, whereConditionArr);
        return this;
    }

    public Join<SRC, DST> whereOr(WhereCondition whereCondition, WhereCondition whereCondition2, WhereCondition... whereConditionArr) {
        this.f.a(or(whereCondition, whereCondition2, whereConditionArr), new WhereCondition[0]);
        return this;
    }
}
