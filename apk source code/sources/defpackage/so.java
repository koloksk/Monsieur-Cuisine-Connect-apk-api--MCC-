package defpackage;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.WhereCondition;

/* loaded from: classes.dex */
public class so<T> {
    public final AbstractDao<T, ?> a;
    public final List<WhereCondition> b = new ArrayList();
    public final String c;

    public so(AbstractDao<T, ?> abstractDao, String str) {
        this.a = abstractDao;
        this.c = str;
    }

    public void a(WhereCondition whereCondition, WhereCondition... whereConditionArr) {
        a(whereCondition);
        this.b.add(whereCondition);
        for (WhereCondition whereCondition2 : whereConditionArr) {
            a(whereCondition2);
            this.b.add(whereCondition2);
        }
    }

    public WhereCondition a(String str, WhereCondition whereCondition, WhereCondition whereCondition2, WhereCondition... whereConditionArr) {
        StringBuilder sb = new StringBuilder("(");
        ArrayList arrayList = new ArrayList();
        a(whereCondition);
        whereCondition.appendTo(sb, this.c);
        whereCondition.appendValuesTo(arrayList);
        sb.append(str);
        a(whereCondition2);
        whereCondition2.appendTo(sb, this.c);
        whereCondition2.appendValuesTo(arrayList);
        for (WhereCondition whereCondition3 : whereConditionArr) {
            sb.append(str);
            a(whereCondition3);
            whereCondition3.appendTo(sb, this.c);
            whereCondition3.appendValuesTo(arrayList);
        }
        sb.append(')');
        return new WhereCondition.StringCondition(sb.toString(), arrayList.toArray());
    }

    public void a(WhereCondition whereCondition) {
        if (whereCondition instanceof WhereCondition.PropertyCondition) {
            a(((WhereCondition.PropertyCondition) whereCondition).property);
        }
    }

    public void a(Property property) {
        AbstractDao<T, ?> abstractDao = this.a;
        if (abstractDao != null) {
            Property[] properties = abstractDao.getProperties();
            int length = properties.length;
            boolean z = false;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (property == properties[i]) {
                    z = true;
                    break;
                }
                i++;
            }
            if (z) {
                return;
            }
            StringBuilder sbA = g9.a("Property '");
            sbA.append(property.name);
            sbA.append("' is not part of ");
            sbA.append(this.a);
            throw new DaoException(sbA.toString());
        }
    }

    public void a(StringBuilder sb, String str, List<Object> list) {
        ListIterator<WhereCondition> listIterator = this.b.listIterator();
        while (listIterator.hasNext()) {
            if (listIterator.hasPrevious()) {
                sb.append(" AND ");
            }
            WhereCondition next = listIterator.next();
            next.appendTo(sb, str);
            next.appendValuesTo(list);
        }
    }
}
