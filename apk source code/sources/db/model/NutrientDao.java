package db.model;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import android.support.media.ExifInterface;
import android.support.v4.app.NotificationCompat;
import defpackage.g9;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.identityscope.IdentityScope;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

/* loaded from: classes.dex */
public class NutrientDao extends AbstractDao<Nutrient, Long> {
    public static final String TABLENAME = "NUTRIENT";
    public DaoSession c;
    public Query<Nutrient> d;
    public String e;

    public static class Properties {
        public static final Property Amount = new Property(0, String.class, "amount", false, "AMOUNT");
        public static final Property Id = new Property(1, Long.class, "id", true, "_id");
        public static final Property RecipeId = new Property(2, Long.class, "recipeId", false, "RECIPE_ID");
        public static final Property Type = new Property(3, String.class, NotificationCompat.MessagingStyle.Message.KEY_DATA_MIME_TYPE, false, "TYPE");
        public static final Property Unit = new Property(4, String.class, "unit", false, "UNIT");
    }

    public NutrientDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public static void createTable(Database database, boolean z) throws SQLException {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"NUTRIENT\" (\"AMOUNT\" TEXT,\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"RECIPE_ID\" INTEGER,\"TYPE\" TEXT,\"UNIT\" TEXT);");
    }

    public static void dropTable(Database database, boolean z) throws SQLException {
        StringBuilder sbA = g9.a("DROP TABLE ");
        sbA.append(z ? "IF EXISTS " : "");
        sbA.append("\"NUTRIENT\"");
        database.execSQL(sbA.toString());
    }

    public List<Nutrient> _queryRecipe_Nutrients(Long l) {
        synchronized (this) {
            if (this.d == null) {
                QueryBuilder<Nutrient> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.RecipeId.eq(null), new WhereCondition[0]);
                this.d = queryBuilder.build();
            }
        }
        Query<Nutrient> queryForCurrentThread = this.d.forCurrentThread();
        queryForCurrentThread.setParameter(0, (Object) l);
        return queryForCurrentThread.list();
    }

    public String getSelectDeep() {
        if (this.e == null) {
            StringBuilder sb = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(sb, ExifInterface.GPS_DIRECTION_TRUE, getAllColumns());
            sb.append(',');
            SqlUtils.appendColumns(sb, "T0", this.c.getRecipeDao().getAllColumns());
            sb.append(" FROM NUTRIENT T");
            sb.append(" LEFT JOIN RECIPE T0 ON T.\"RECIPE_ID\"=T0.\"_id\"");
            sb.append(' ');
            this.e = sb.toString();
        }
        return this.e;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final boolean isEntityUpdateable() {
        return true;
    }

    public List<Nutrient> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        ArrayList arrayList = new ArrayList(count);
        if (cursor.moveToFirst()) {
            IdentityScope<K, T> identityScope = this.identityScope;
            if (identityScope != 0) {
                identityScope.lock();
                this.identityScope.reserveRoom(count);
            }
            do {
                try {
                    arrayList.add(loadCurrentDeep(cursor, false));
                } finally {
                    IdentityScope<K, T> identityScope2 = this.identityScope;
                    if (identityScope2 != 0) {
                        identityScope2.unlock();
                    }
                }
            } while (cursor.moveToNext());
        }
        return arrayList;
    }

    public Nutrient loadCurrentDeep(Cursor cursor, boolean z) {
        Nutrient nutrientLoadCurrent = loadCurrent(cursor, 0, z);
        nutrientLoadCurrent.setRecipe((Recipe) loadCurrentOther(this.c.getRecipeDao(), cursor, getAllColumns().length));
        return nutrientLoadCurrent;
    }

    public Nutrient loadDeep(Long l) {
        assertSinglePk();
        if (l == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder(getSelectDeep());
        sb.append("WHERE ");
        SqlUtils.appendColumnsEqValue(sb, ExifInterface.GPS_DIRECTION_TRUE, getPkColumns());
        Cursor cursorRawQuery = this.f7db.rawQuery(sb.toString(), new String[]{l.toString()});
        try {
            if (!cursorRawQuery.moveToFirst()) {
                return null;
            }
            if (cursorRawQuery.isLast()) {
                return loadCurrentDeep(cursorRawQuery, true);
            }
            throw new IllegalStateException("Expected unique result, but count was " + cursorRawQuery.getCount());
        } finally {
            cursorRawQuery.close();
        }
    }

    public List<Nutrient> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }

    public List<Nutrient> queryDeep(String str, String... strArr) {
        return loadDeepAllAndCloseCursor(this.f7db.rawQuery(getSelectDeep() + str, strArr));
    }

    public NutrientDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
        this.c = daoSession;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void attachEntity(Nutrient nutrient) {
        super.attachEntity((NutrientDao) nutrient);
        nutrient.__setDaoSession(this.c);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(Nutrient nutrient) {
        if (nutrient != null) {
            return nutrient.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(Nutrient nutrient) {
        return nutrient.getId() != null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Long readKey(Cursor cursor, int i) {
        int i2 = i + 1;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final Long updateKeyAfterInsert(Nutrient nutrient, long j) {
        nutrient.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, Nutrient nutrient) {
        databaseStatement.clearBindings();
        String amount = nutrient.getAmount();
        if (amount != null) {
            databaseStatement.bindString(1, amount);
        }
        Long id = nutrient.getId();
        if (id != null) {
            databaseStatement.bindLong(2, id.longValue());
        }
        Long lValueOf = Long.valueOf(nutrient.getRecipeId());
        if (lValueOf != null) {
            databaseStatement.bindLong(3, lValueOf.longValue());
        }
        String type = nutrient.getType();
        if (type != null) {
            databaseStatement.bindString(4, type);
        }
        String unit = nutrient.getUnit();
        if (unit != null) {
            databaseStatement.bindString(5, unit);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Nutrient readEntity(Cursor cursor, int i) {
        int i2 = i + 0;
        String string = cursor.isNull(i2) ? null : cursor.getString(i2);
        int i3 = i + 1;
        Long lValueOf = cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3));
        int i4 = i + 2;
        Long lValueOf2 = cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4));
        int i5 = i + 3;
        int i6 = i + 4;
        return new Nutrient(string, lValueOf, lValueOf2, cursor.isNull(i5) ? null : cursor.getString(i5), cursor.isNull(i6) ? null : cursor.getString(i6));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, Nutrient nutrient, int i) {
        int i2 = i + 0;
        nutrient.setAmount(cursor.isNull(i2) ? null : cursor.getString(i2));
        int i3 = i + 1;
        nutrient.setId(cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3)));
        int i4 = i + 2;
        nutrient.setRecipeId(cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4)));
        int i5 = i + 3;
        nutrient.setType(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = i + 4;
        nutrient.setUnit(cursor.isNull(i6) ? null : cursor.getString(i6));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, Nutrient nutrient) {
        sQLiteStatement.clearBindings();
        String amount = nutrient.getAmount();
        if (amount != null) {
            sQLiteStatement.bindString(1, amount);
        }
        Long id = nutrient.getId();
        if (id != null) {
            sQLiteStatement.bindLong(2, id.longValue());
        }
        Long lValueOf = Long.valueOf(nutrient.getRecipeId());
        if (lValueOf != null) {
            sQLiteStatement.bindLong(3, lValueOf.longValue());
        }
        String type = nutrient.getType();
        if (type != null) {
            sQLiteStatement.bindString(4, type);
        }
        String unit = nutrient.getUnit();
        if (unit != null) {
            sQLiteStatement.bindString(5, unit);
        }
    }
}
