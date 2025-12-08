package org.greenrobot.greendao.query;

import android.database.sqlite.SQLiteDatabase;
import android.support.media.ExifInterface;
import defpackage.g9;
import defpackage.po;
import defpackage.so;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.DaoLog;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.annotation.apihint.Experimental;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.query.CountQuery;
import org.greenrobot.greendao.query.DeleteQuery;
import org.greenrobot.greendao.rx.RxQuery;

/* loaded from: classes.dex */
public class QueryBuilder<T> {
    public static boolean LOG_SQL;
    public static boolean LOG_VALUES;
    public final so<T> a;
    public StringBuilder b;
    public final List<Object> c;
    public final List<Join<T, ?>> d;
    public final AbstractDao<T, ?> e;
    public final String f;
    public Integer g;
    public Integer h;
    public boolean i;
    public String j;

    public QueryBuilder(AbstractDao<T, ?> abstractDao) {
        this(abstractDao, ExifInterface.GPS_DIRECTION_TRUE);
    }

    public static <T2> QueryBuilder<T2> internalCreate(AbstractDao<T2, ?> abstractDao) {
        return new QueryBuilder<>(abstractDao);
    }

    public final void a() {
        StringBuilder sb = this.b;
        if (sb == null) {
            this.b = new StringBuilder();
        } else if (sb.length() > 0) {
            this.b.append(",");
        }
    }

    public WhereCondition and(WhereCondition whereCondition, WhereCondition whereCondition2, WhereCondition... whereConditionArr) {
        return this.a.a(" AND ", whereCondition, whereCondition2, whereConditionArr);
    }

    public StringBuilder append(StringBuilder sb, Property property) {
        this.a.a(property);
        sb.append(this.f);
        sb.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
        sb.append('\'');
        sb.append(property.columnName);
        sb.append('\'');
        return sb;
    }

    public final StringBuilder b() {
        StringBuilder sb = new StringBuilder(SqlUtils.createSqlSelect(this.e.getTablename(), this.f, this.e.getAllColumns(), this.i));
        a(sb, this.f);
        StringBuilder sb2 = this.b;
        if (sb2 != null && sb2.length() > 0) {
            sb.append(" ORDER BY ");
            sb.append((CharSequence) this.b);
        }
        return sb;
    }

    public Query<T> build() {
        StringBuilder sbB = b();
        int iA = a(sbB);
        int iB = b(sbB);
        String string = sbB.toString();
        a(string);
        return Query.a(this.e, string, this.c.toArray(), iA, iB);
    }

    public CountQuery<T> buildCount() {
        StringBuilder sb = new StringBuilder(SqlUtils.createSqlSelectCountStar(this.e.getTablename(), this.f));
        a(sb, this.f);
        String string = sb.toString();
        a(string);
        return (CountQuery) new CountQuery.b(this.e, string, po.toStringArray(this.c.toArray()), null).b();
    }

    public CursorQuery buildCursor() {
        StringBuilder sbB = b();
        int iA = a(sbB);
        int iB = b(sbB);
        String string = sbB.toString();
        a(string);
        return CursorQuery.a(this.e, string, this.c.toArray(), iA, iB);
    }

    public DeleteQuery<T> buildDelete() {
        if (!this.d.isEmpty()) {
            throw new DaoException("JOINs are not supported for DELETE queries");
        }
        String tablename = this.e.getTablename();
        StringBuilder sb = new StringBuilder(SqlUtils.createSqlDelete(tablename, null));
        a(sb, this.f);
        String strReplace = sb.toString().replace(this.f + ".\"", '\"' + tablename + "\".\"");
        a(strReplace);
        return (DeleteQuery) new DeleteQuery.b(this.e, strReplace, po.toStringArray(this.c.toArray()), null).b();
    }

    public long count() {
        return buildCount().count();
    }

    public QueryBuilder<T> distinct() {
        this.i = true;
        return this;
    }

    public <J> Join<T, J> join(Class<J> cls, Property property) {
        return join(this.e.getPkProperty(), cls, property);
    }

    public QueryBuilder<T> limit(int i) {
        this.g = Integer.valueOf(i);
        return this;
    }

    public List<T> list() {
        return build().list();
    }

    public CloseableListIterator<T> listIterator() {
        return build().listIterator();
    }

    public LazyList<T> listLazy() {
        return build().listLazy();
    }

    public LazyList<T> listLazyUncached() {
        return build().listLazyUncached();
    }

    public QueryBuilder<T> offset(int i) {
        this.h = Integer.valueOf(i);
        return this;
    }

    public WhereCondition or(WhereCondition whereCondition, WhereCondition whereCondition2, WhereCondition... whereConditionArr) {
        return this.a.a(" OR ", whereCondition, whereCondition2, whereConditionArr);
    }

    public QueryBuilder<T> orderAsc(Property... propertyArr) {
        a(" ASC", propertyArr);
        return this;
    }

    public QueryBuilder<T> orderCustom(Property property, String str) {
        a();
        append(this.b, property).append(' ');
        this.b.append(str);
        return this;
    }

    public QueryBuilder<T> orderDesc(Property... propertyArr) {
        a(" DESC", propertyArr);
        return this;
    }

    public QueryBuilder<T> orderRaw(String str) {
        a();
        this.b.append(str);
        return this;
    }

    public QueryBuilder<T> preferLocalizedStringOrder() {
        if (this.e.getDatabase().getRawDatabase() instanceof SQLiteDatabase) {
            this.j = " COLLATE LOCALIZED";
        }
        return this;
    }

    @Experimental
    public RxQuery<T> rx() {
        return build().__InternalRx();
    }

    @Experimental
    public RxQuery<T> rxPlain() {
        return build().__internalRxPlain();
    }

    public QueryBuilder<T> stringOrderCollation(String str) {
        if (this.e.getDatabase().getRawDatabase() instanceof SQLiteDatabase) {
            if (str != null && !str.startsWith(StringUtils.SPACE)) {
                str = g9.b(StringUtils.SPACE, str);
            }
            this.j = str;
        }
        return this;
    }

    public T unique() {
        return build().unique();
    }

    public T uniqueOrThrow() {
        return build().uniqueOrThrow();
    }

    public QueryBuilder<T> where(WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        this.a.a(whereCondition, whereConditionArr);
        return this;
    }

    public QueryBuilder<T> whereOr(WhereCondition whereCondition, WhereCondition whereCondition2, WhereCondition... whereConditionArr) {
        this.a.a(or(whereCondition, whereCondition2, whereConditionArr), new WhereCondition[0]);
        return this;
    }

    public QueryBuilder(AbstractDao<T, ?> abstractDao, String str) {
        this.e = abstractDao;
        this.f = str;
        this.c = new ArrayList();
        this.d = new ArrayList();
        this.a = new so<>(abstractDao, str);
        this.j = " COLLATE NOCASE";
    }

    public <J> Join<T, J> join(Property property, Class<J> cls) {
        AbstractDao<?, ?> dao = this.e.getSession().getDao(cls);
        return a(this.f, property, dao, dao.getPkProperty());
    }

    public final <J> Join<T, J> a(String str, Property property, AbstractDao<J, ?> abstractDao, Property property2) {
        StringBuilder sbA = g9.a("J");
        sbA.append(this.d.size() + 1);
        Join<T, J> join = new Join<>(str, property, abstractDao, property2, sbA.toString());
        this.d.add(join);
        return join;
    }

    public <J> Join<T, J> join(Property property, Class<J> cls, Property property2) {
        return a(this.f, property, this.e.getSession().getDao(cls), property2);
    }

    public final int b(StringBuilder sb) {
        if (this.h == null) {
            return -1;
        }
        if (this.g != null) {
            sb.append(" OFFSET ?");
            this.c.add(this.h);
            return this.c.size() - 1;
        }
        throw new IllegalStateException("Offset cannot be set without limit");
    }

    public <J> Join<T, J> join(Join<?, T> join, Property property, Class<J> cls, Property property2) {
        return a(join.e, property, this.e.getSession().getDao(cls), property2);
    }

    public final void a(String str, Property... propertyArr) {
        String str2;
        for (Property property : propertyArr) {
            a();
            append(this.b, property);
            if (String.class.equals(property.type) && (str2 = this.j) != null) {
                this.b.append(str2);
            }
            this.b.append(str);
        }
    }

    public final int a(StringBuilder sb) {
        if (this.g == null) {
            return -1;
        }
        sb.append(" LIMIT ?");
        this.c.add(this.g);
        return this.c.size() - 1;
    }

    public final void a(String str) {
        if (LOG_SQL) {
            DaoLog.d("Built SQL for query: " + str);
        }
        if (LOG_VALUES) {
            StringBuilder sbA = g9.a("Values for query: ");
            sbA.append(this.c);
            DaoLog.d(sbA.toString());
        }
    }

    public final void a(StringBuilder sb, String str) {
        this.c.clear();
        for (Join<T, ?> join : this.d) {
            sb.append(" JOIN ");
            sb.append(join.b.getTablename());
            sb.append(' ');
            sb.append(join.e);
            sb.append(" ON ");
            SqlUtils.appendProperty(sb, join.a, join.c).append('=');
            SqlUtils.appendProperty(sb, join.e, join.d);
        }
        boolean z = !this.a.b.isEmpty();
        if (z) {
            sb.append(" WHERE ");
            this.a.a(sb, str, this.c);
        }
        for (Join<T, ?> join2 : this.d) {
            if (!join2.f.b.isEmpty()) {
                if (!z) {
                    sb.append(" WHERE ");
                    z = true;
                } else {
                    sb.append(" AND ");
                }
                join2.f.a(sb, join2.e, this.c);
            }
        }
    }
}
