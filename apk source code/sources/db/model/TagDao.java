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
public class TagDao extends AbstractDao<Tag, Long> {
    public static final String TABLENAME = "TAG";
    public DaoSession c;
    public Query<Tag> d;
    public String e;

    public static class Properties {
        public static final Property Category = new Property(0, String.class, "category", false, "CATEGORY");
        public static final Property Id = new Property(1, Long.class, "id", true, "_id");
        public static final Property Name = new Property(2, String.class, "name", false, "NAME");
        public static final Property RecipeId = new Property(3, Long.class, "recipeId", false, "RECIPE_ID");
    }

    public TagDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public static void createTable(Database database, boolean z) throws SQLException {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"TAG\" (\"CATEGORY\" TEXT,\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"NAME\" TEXT,\"RECIPE_ID\" INTEGER);");
    }

    public static void dropTable(Database database, boolean z) throws SQLException {
        StringBuilder sbA = g9.a("DROP TABLE ");
        sbA.append(z ? "IF EXISTS " : "");
        sbA.append("\"TAG\"");
        database.execSQL(sbA.toString());
    }

    public List<Tag> _queryRecipe_Tags(Long l) {
        synchronized (this) {
            if (this.d == null) {
                QueryBuilder<Tag> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.RecipeId.eq(null), new WhereCondition[0]);
                this.d = queryBuilder.build();
            }
        }
        Query<Tag> queryForCurrentThread = this.d.forCurrentThread();
        queryForCurrentThread.setParameter(0, (Object) l);
        return queryForCurrentThread.list();
    }

    public String getSelectDeep() {
        if (this.e == null) {
            StringBuilder sb = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(sb, ExifInterface.GPS_DIRECTION_TRUE, getAllColumns());
            sb.append(',');
            SqlUtils.appendColumns(sb, "T0", this.c.getRecipeDao().getAllColumns());
            sb.append(" FROM TAG T");
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

    public List<Tag> loadAllDeepFromCursor(Cursor cursor) {
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

    public Tag loadCurrentDeep(Cursor cursor, boolean z) {
        Tag tagLoadCurrent = loadCurrent(cursor, 0, z);
        tagLoadCurrent.setRecipe((Recipe) loadCurrentOther(this.c.getRecipeDao(), cursor, getAllColumns().length));
        return tagLoadCurrent;
    }

    public Tag loadDeep(Long l) {
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

    public List<Tag> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }

    public List<Tag> queryDeep(String str, String... strArr) {
        return loadDeepAllAndCloseCursor(this.f7db.rawQuery(getSelectDeep() + str, strArr));
    }

    public TagDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
        this.c = daoSession;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void attachEntity(Tag tag) {
        super.attachEntity((TagDao) tag);
        tag.__setDaoSession(this.c);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(Tag tag) {
        if (tag != null) {
            return tag.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(Tag tag) {
        return tag.getId() != null;
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
    public final Long updateKeyAfterInsert(Tag tag, long j) {
        tag.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, Tag tag) {
        databaseStatement.clearBindings();
        String category = tag.getCategory();
        if (category != null) {
            databaseStatement.bindString(1, category);
        }
        Long id = tag.getId();
        if (id != null) {
            databaseStatement.bindLong(2, id.longValue());
        }
        String name = tag.getName();
        if (name != null) {
            databaseStatement.bindString(3, name);
        }
        Long recipeId = tag.getRecipeId();
        if (recipeId != null) {
            databaseStatement.bindLong(4, recipeId.longValue());
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Tag readEntity(Cursor cursor, int i) {
        int i2 = i + 0;
        int i3 = i + 1;
        int i4 = i + 2;
        int i5 = i + 3;
        return new Tag(cursor.isNull(i2) ? null : cursor.getString(i2), cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3)), cursor.isNull(i4) ? null : cursor.getString(i4), cursor.isNull(i5) ? null : Long.valueOf(cursor.getLong(i5)));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, Tag tag, int i) {
        int i2 = i + 0;
        tag.setCategory(cursor.isNull(i2) ? null : cursor.getString(i2));
        int i3 = i + 1;
        tag.setId(cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3)));
        int i4 = i + 2;
        tag.setName(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = i + 3;
        tag.setRecipeId(cursor.isNull(i5) ? null : Long.valueOf(cursor.getLong(i5)));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, Tag tag) {
        sQLiteStatement.clearBindings();
        String category = tag.getCategory();
        if (category != null) {
            sQLiteStatement.bindString(1, category);
        }
        Long id = tag.getId();
        if (id != null) {
            sQLiteStatement.bindLong(2, id.longValue());
        }
        String name = tag.getName();
        if (name != null) {
            sQLiteStatement.bindString(3, name);
        }
        Long recipeId = tag.getRecipeId();
        if (recipeId != null) {
            sQLiteStatement.bindLong(4, recipeId.longValue());
        }
    }
}
