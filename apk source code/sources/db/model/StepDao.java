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
public class StepDao extends AbstractDao<Step, Long> {
    public static final String TABLENAME = "STEP";
    public DaoSession c;
    public Query<Step> d;
    public String e;

    public static class Properties {
        public static final Property Id = new Property(0, Long.class, "id", true, "_id");
        public static final Property LedId = new Property(1, Long.class, "ledId", false, "LED_ID");
        public static final Property MachineValuesId = new Property(2, Long.class, "machineValuesId", false, "MACHINE_VALUES_ID");
        public static final Property MeasurementId = new Property(3, Long.class, "measurementId", false, "MEASUREMENT_ID");
        public static final Property Mode = new Property(4, String.class, "mode", false, "MODE");
        public static final Property RecipeId = new Property(5, Long.class, "recipeId", false, "RECIPE_ID");
        public static final Property Step = new Property(6, Integer.TYPE, "step", false, StepDao.TABLENAME);
        public static final Property Text = new Property(7, String.class, "text", false, "TEXT");
    }

    public StepDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public static void createTable(Database database, boolean z) throws SQLException {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"STEP\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"LED_ID\" INTEGER,\"MACHINE_VALUES_ID\" INTEGER,\"MEASUREMENT_ID\" INTEGER,\"MODE\" TEXT,\"RECIPE_ID\" INTEGER,\"STEP\" INTEGER NOT NULL ,\"TEXT\" TEXT);");
    }

    public static void dropTable(Database database, boolean z) throws SQLException {
        StringBuilder sbA = g9.a("DROP TABLE ");
        sbA.append(z ? "IF EXISTS " : "");
        sbA.append("\"STEP\"");
        database.execSQL(sbA.toString());
    }

    public List<Step> _queryRecipe_Steps(Long l) {
        synchronized (this) {
            if (this.d == null) {
                QueryBuilder<Step> queryBuilder = queryBuilder();
                queryBuilder.where(Properties.RecipeId.eq(null), new WhereCondition[0]);
                this.d = queryBuilder.build();
            }
        }
        Query<Step> queryForCurrentThread = this.d.forCurrentThread();
        queryForCurrentThread.setParameter(0, (Object) l);
        return queryForCurrentThread.list();
    }

    public String getSelectDeep() {
        if (this.e == null) {
            StringBuilder sb = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(sb, ExifInterface.GPS_DIRECTION_TRUE, getAllColumns());
            sb.append(',');
            SqlUtils.appendColumns(sb, "T0", this.c.getLEDDao().getAllColumns());
            sb.append(',');
            SqlUtils.appendColumns(sb, "T1", this.c.getMachineValuesDao().getAllColumns());
            sb.append(',');
            SqlUtils.appendColumns(sb, "T2", this.c.getMeasurementDao().getAllColumns());
            sb.append(',');
            SqlUtils.appendColumns(sb, "T3", this.c.getRecipeDao().getAllColumns());
            sb.append(" FROM STEP T");
            sb.append(" LEFT JOIN LED T0 ON T.\"LED_ID\"=T0.\"_id\"");
            sb.append(" LEFT JOIN MACHINE_VALUES T1 ON T.\"MACHINE_VALUES_ID\"=T1.\"_id\"");
            sb.append(" LEFT JOIN MEASUREMENT T2 ON T.\"MEASUREMENT_ID\"=T2.\"_id\"");
            sb.append(" LEFT JOIN RECIPE T3 ON T.\"RECIPE_ID\"=T3.\"_id\"");
            sb.append(' ');
            this.e = sb.toString();
        }
        return this.e;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final boolean isEntityUpdateable() {
        return true;
    }

    public List<Step> loadAllDeepFromCursor(Cursor cursor) {
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

    public Step loadCurrentDeep(Cursor cursor, boolean z) {
        Step stepLoadCurrent = loadCurrent(cursor, 0, z);
        int length = getAllColumns().length;
        stepLoadCurrent.setLed((LED) loadCurrentOther(this.c.getLEDDao(), cursor, length));
        int length2 = length + this.c.getLEDDao().getAllColumns().length;
        stepLoadCurrent.setMachineValues((MachineValues) loadCurrentOther(this.c.getMachineValuesDao(), cursor, length2));
        int length3 = length2 + this.c.getMachineValuesDao().getAllColumns().length;
        stepLoadCurrent.setMeasurement((Measurement) loadCurrentOther(this.c.getMeasurementDao(), cursor, length3));
        stepLoadCurrent.setRecipe((Recipe) loadCurrentOther(this.c.getRecipeDao(), cursor, length3 + this.c.getMeasurementDao().getAllColumns().length));
        return stepLoadCurrent;
    }

    public Step loadDeep(Long l) {
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

    public List<Step> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }

    public List<Step> queryDeep(String str, String... strArr) {
        return loadDeepAllAndCloseCursor(this.f7db.rawQuery(getSelectDeep() + str, strArr));
    }

    public StepDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
        this.c = daoSession;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void attachEntity(Step step) {
        super.attachEntity((StepDao) step);
        step.__setDaoSession(this.c);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(Step step) {
        if (step != null) {
            return step.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(Step step) {
        return step.getId() != null;
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
    public final Long updateKeyAfterInsert(Step step, long j) {
        step.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, Step step) {
        databaseStatement.clearBindings();
        Long id = step.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        Long ledId = step.getLedId();
        if (ledId != null) {
            databaseStatement.bindLong(2, ledId.longValue());
        }
        Long machineValuesId = step.getMachineValuesId();
        if (machineValuesId != null) {
            databaseStatement.bindLong(3, machineValuesId.longValue());
        }
        Long measurementId = step.getMeasurementId();
        if (measurementId != null) {
            databaseStatement.bindLong(4, measurementId.longValue());
        }
        String mode = step.getMode();
        if (mode != null) {
            databaseStatement.bindString(5, mode);
        }
        Long recipeId = step.getRecipeId();
        if (recipeId != null) {
            databaseStatement.bindLong(6, recipeId.longValue());
        }
        databaseStatement.bindLong(7, step.getStep());
        String text = step.getText();
        if (text != null) {
            databaseStatement.bindString(8, text);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Step readEntity(Cursor cursor, int i) {
        int i2 = i + 0;
        Long lValueOf = cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2));
        int i3 = i + 1;
        Long lValueOf2 = cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3));
        int i4 = i + 2;
        Long lValueOf3 = cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4));
        int i5 = i + 3;
        Long lValueOf4 = cursor.isNull(i5) ? null : Long.valueOf(cursor.getLong(i5));
        int i6 = i + 4;
        String string = cursor.isNull(i6) ? null : cursor.getString(i6);
        int i7 = i + 5;
        Long lValueOf5 = cursor.isNull(i7) ? null : Long.valueOf(cursor.getLong(i7));
        int i8 = cursor.getInt(i + 6);
        int i9 = i + 7;
        return new Step(lValueOf, lValueOf2, lValueOf3, lValueOf4, string, lValueOf5, i8, cursor.isNull(i9) ? null : cursor.getString(i9));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, Step step, int i) {
        int i2 = i + 0;
        step.setId(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        int i3 = i + 1;
        step.setLedId(cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3)));
        int i4 = i + 2;
        step.setMachineValuesId(cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4)));
        int i5 = i + 3;
        step.setMeasurementId(cursor.isNull(i5) ? null : Long.valueOf(cursor.getLong(i5)));
        int i6 = i + 4;
        step.setMode(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = i + 5;
        step.setRecipeId(cursor.isNull(i7) ? null : Long.valueOf(cursor.getLong(i7)));
        step.setStep(cursor.getInt(i + 6));
        int i8 = i + 7;
        step.setText(cursor.isNull(i8) ? null : cursor.getString(i8));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, Step step) {
        sQLiteStatement.clearBindings();
        Long id = step.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        Long ledId = step.getLedId();
        if (ledId != null) {
            sQLiteStatement.bindLong(2, ledId.longValue());
        }
        Long machineValuesId = step.getMachineValuesId();
        if (machineValuesId != null) {
            sQLiteStatement.bindLong(3, machineValuesId.longValue());
        }
        Long measurementId = step.getMeasurementId();
        if (measurementId != null) {
            sQLiteStatement.bindLong(4, measurementId.longValue());
        }
        String mode = step.getMode();
        if (mode != null) {
            sQLiteStatement.bindString(5, mode);
        }
        Long recipeId = step.getRecipeId();
        if (recipeId != null) {
            sQLiteStatement.bindLong(6, recipeId.longValue());
        }
        sQLiteStatement.bindLong(7, step.getStep());
        String text = step.getText();
        if (text != null) {
            sQLiteStatement.bindString(8, text);
        }
    }
}
