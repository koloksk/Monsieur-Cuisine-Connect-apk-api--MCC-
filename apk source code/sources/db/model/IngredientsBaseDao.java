package db.model;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import android.support.media.ExifInterface;
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
public class IngredientsBaseDao extends AbstractDao<IngredientsBase, Long> {
    public static final String TABLENAME = "INGREDIENTS_BASE";
    public DaoSession c;
    public Query<IngredientsBase> d;
    public String e;

    public static class Properties {
        public static final Property Id = new Property(0, Long.class, "id", true, "_id");
        public static final Property Name = new Property(1, String.class, "name", false, "NAME");
        public static final Property RecipeId = new Property(2, Long.class, "recipeId", false, "RECIPE_ID");
    }

    public IngredientsBaseDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public static void createTable(Database database, boolean z) throws SQLException {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"INGREDIENTS_BASE\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"NAME\" TEXT,\"RECIPE_ID\" INTEGER);");
    }

    public static void dropTable(Database database, boolean z) throws SQLException {
        StringBuilder sbA = g9.a("DROP TABLE ");
        sbA.append(z ? "IF EXISTS " : "");
        sbA.append("\"INGREDIENTS_BASE\"");
        database.execSQL(sbA.toString());
    }

    public List<IngredientsBase> _queryRecipe_IngredientsBases(Long l) {
        synchronized (this) {
            if (this.d == null) {
                QueryBuilder<IngredientsBase> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.RecipeId.eq(null), new WhereCondition[0]);
                this.d = queryBuilder.build();
            }
        }
        Query<IngredientsBase> queryForCurrentThread = this.d.forCurrentThread();
        queryForCurrentThread.setParameter(0, (Object) l);
        return queryForCurrentThread.list();
    }

    public String getSelectDeep() {
        if (this.e == null) {
            StringBuilder sb = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(sb, ExifInterface.GPS_DIRECTION_TRUE, getAllColumns());
            sb.append(',');
            SqlUtils.appendColumns(sb, "T0", this.c.getRecipeDao().getAllColumns());
            sb.append(" FROM INGREDIENTS_BASE T");
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

    public List<IngredientsBase> loadAllDeepFromCursor(Cursor cursor) {
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

    public IngredientsBase loadCurrentDeep(Cursor cursor, boolean z) {
        IngredientsBase ingredientsBaseLoadCurrent = loadCurrent(cursor, 0, z);
        ingredientsBaseLoadCurrent.setRecipe((Recipe) loadCurrentOther(this.c.getRecipeDao(), cursor, getAllColumns().length));
        return ingredientsBaseLoadCurrent;
    }

    public IngredientsBase loadDeep(Long l) {
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

    public List<IngredientsBase> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }

    public List<IngredientsBase> queryDeep(String str, String... strArr) {
        return loadDeepAllAndCloseCursor(this.f7db.rawQuery(getSelectDeep() + str, strArr));
    }

    public IngredientsBaseDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
        this.c = daoSession;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void attachEntity(IngredientsBase ingredientsBase) {
        super.attachEntity((IngredientsBaseDao) ingredientsBase);
        ingredientsBase.__setDaoSession(this.c);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(IngredientsBase ingredientsBase) {
        if (ingredientsBase != null) {
            return ingredientsBase.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(IngredientsBase ingredientsBase) {
        return ingredientsBase.getId() != null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Long readKey(Cursor cursor, int i) {
        int i2 = i + 0;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final Long updateKeyAfterInsert(IngredientsBase ingredientsBase, long j) {
        ingredientsBase.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, IngredientsBase ingredientsBase) {
        databaseStatement.clearBindings();
        Long id = ingredientsBase.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        String name = ingredientsBase.getName();
        if (name != null) {
            databaseStatement.bindString(2, name);
        }
        Long recipeId = ingredientsBase.getRecipeId();
        if (recipeId != null) {
            databaseStatement.bindLong(3, recipeId.longValue());
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public IngredientsBase readEntity(Cursor cursor, int i) {
        int i2 = i + 0;
        int i3 = i + 1;
        int i4 = i + 2;
        return new IngredientsBase(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)), cursor.isNull(i3) ? null : cursor.getString(i3), cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4)));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, IngredientsBase ingredientsBase, int i) {
        int i2 = i + 0;
        ingredientsBase.setId(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = i + 1;
        ingredientsBase.setName(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = i + 2;
        ingredientsBase.setRecipeId(cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4)));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, IngredientsBase ingredientsBase) {
        sQLiteStatement.clearBindings();
        Long id = ingredientsBase.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        String name = ingredientsBase.getName();
        if (name != null) {
            sQLiteStatement.bindString(2, name);
        }
        Long recipeId = ingredientsBase.getRecipeId();
        if (recipeId != null) {
            sQLiteStatement.bindLong(3, recipeId.longValue());
        }
    }
}
