package org.greenrobot.greendao.internal;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScope;
import org.greenrobot.greendao.identityscope.IdentityScopeLong;
import org.greenrobot.greendao.identityscope.IdentityScopeObject;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

/* loaded from: classes.dex */
public final class DaoConfig implements Cloneable {
    public IdentityScope<?, ?> a;
    public final String[] allColumns;

    /* renamed from: db, reason: collision with root package name */
    public final Database f9db;
    public final boolean keyIsNumeric;
    public final String[] nonPkColumns;
    public final String[] pkColumns;
    public final Property pkProperty;
    public final Property[] properties;
    public final TableStatements statements;
    public final String tablename;

    public DaoConfig(Database database, Class<? extends AbstractDao<?, ?>> cls) {
        this.f9db = database;
        try {
            this.tablename = (String) cls.getField("TABLENAME").get(null);
            Property[] propertyArrA = a(cls);
            this.properties = propertyArrA;
            this.allColumns = new String[propertyArrA.length];
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            Property property = null;
            for (int i = 0; i < propertyArrA.length; i++) {
                Property property2 = propertyArrA[i];
                String str = property2.columnName;
                this.allColumns[i] = str;
                if (property2.primaryKey) {
                    arrayList.add(str);
                    property = property2;
                } else {
                    arrayList2.add(str);
                }
            }
            this.nonPkColumns = (String[]) arrayList2.toArray(new String[arrayList2.size()]);
            String[] strArr = (String[]) arrayList.toArray(new String[arrayList.size()]);
            this.pkColumns = strArr;
            this.pkProperty = strArr.length == 1 ? property : null;
            this.statements = new TableStatements(database, this.tablename, this.allColumns, this.pkColumns);
            if (this.pkProperty == null) {
                this.keyIsNumeric = false;
            } else {
                Class<?> cls2 = this.pkProperty.type;
                this.keyIsNumeric = cls2.equals(Long.TYPE) || cls2.equals(Long.class) || cls2.equals(Integer.TYPE) || cls2.equals(Integer.class) || cls2.equals(Short.TYPE) || cls2.equals(Short.class) || cls2.equals(Byte.TYPE) || cls2.equals(Byte.class);
            }
        } catch (Exception e) {
            throw new DaoException("Could not init DAOConfig", e);
        }
    }

    public static Property[] a(Class<? extends AbstractDao<?, ?>> cls) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException {
        Field[] declaredFields = Class.forName(cls.getName() + "$Properties").getDeclaredFields();
        ArrayList arrayList = new ArrayList();
        for (Field field : declaredFields) {
            if ((field.getModifiers() & 9) == 9) {
                Object obj = field.get(null);
                if (obj instanceof Property) {
                    arrayList.add((Property) obj);
                }
            }
        }
        Property[] propertyArr = new Property[arrayList.size()];
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Property property = (Property) it.next();
            int i = property.ordinal;
            if (propertyArr[i] != null) {
                throw new DaoException("Duplicate property ordinals");
            }
            propertyArr[i] = property;
        }
        return propertyArr;
    }

    public void clearIdentityScope() {
        IdentityScope<?, ?> identityScope = this.a;
        if (identityScope != null) {
            identityScope.clear();
        }
    }

    public IdentityScope<?, ?> getIdentityScope() {
        return this.a;
    }

    public void initIdentityScope(IdentityScopeType identityScopeType) {
        if (identityScopeType == IdentityScopeType.None) {
            this.a = null;
            return;
        }
        if (identityScopeType != IdentityScopeType.Session) {
            throw new IllegalArgumentException("Unsupported type: " + identityScopeType);
        }
        if (this.keyIsNumeric) {
            this.a = new IdentityScopeLong();
        } else {
            this.a = new IdentityScopeObject();
        }
    }

    public void setIdentityScope(IdentityScope<?, ?> identityScope) {
        this.a = identityScope;
    }

    public DaoConfig clone() {
        return new DaoConfig(this);
    }

    public DaoConfig(DaoConfig daoConfig) {
        this.f9db = daoConfig.f9db;
        this.tablename = daoConfig.tablename;
        this.properties = daoConfig.properties;
        this.allColumns = daoConfig.allColumns;
        this.pkColumns = daoConfig.pkColumns;
        this.nonPkColumns = daoConfig.nonPkColumns;
        this.pkProperty = daoConfig.pkProperty;
        this.statements = daoConfig.statements;
        this.keyIsNumeric = daoConfig.keyIsNumeric;
    }
}
