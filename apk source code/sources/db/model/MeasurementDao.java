package db.model;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import defpackage.g9;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* loaded from: classes.dex */
public class MeasurementDao extends AbstractDao<Measurement, Long> {
    public static final String TABLENAME = "MEASUREMENT";

    public static class Properties {
        public static final Property Id = new Property(0, Long.class, "id", true, "_id");
        public static final Property Lid = new Property(1, Boolean.TYPE, "lid", false, "LID");
        public static final Property Speed = new Property(2, Integer.TYPE, "speed", false, "SPEED");
        public static final Property Temp = new Property(3, Integer.TYPE, "temp", false, "TEMP");
        public static final Property Weight = new Property(4, Integer.TYPE, "weight", false, "WEIGHT");
    }

    public MeasurementDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public static void createTable(Database database, boolean z) throws SQLException {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"MEASUREMENT\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"LID\" INTEGER NOT NULL ,\"SPEED\" INTEGER NOT NULL ,\"TEMP\" INTEGER NOT NULL ,\"WEIGHT\" INTEGER NOT NULL );");
    }

    public static void dropTable(Database database, boolean z) throws SQLException {
        StringBuilder sbA = g9.a("DROP TABLE ");
        sbA.append(z ? "IF EXISTS " : "");
        sbA.append("\"MEASUREMENT\"");
        database.execSQL(sbA.toString());
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final boolean isEntityUpdateable() {
        return true;
    }

    public MeasurementDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(Measurement measurement) {
        if (measurement != null) {
            return measurement.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(Measurement measurement) {
        return measurement.getId() != null;
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
    public final Long updateKeyAfterInsert(Measurement measurement, long j) {
        measurement.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, Measurement measurement) {
        databaseStatement.clearBindings();
        Long id = measurement.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        databaseStatement.bindLong(2, measurement.getLid() ? 1L : 0L);
        databaseStatement.bindLong(3, measurement.getSpeed());
        databaseStatement.bindLong(4, measurement.getTemp());
        databaseStatement.bindLong(5, measurement.getWeight());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Measurement readEntity(Cursor cursor, int i) {
        int i2 = i + 0;
        return new Measurement(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)), cursor.getShort(i + 1) != 0, cursor.getInt(i + 2), cursor.getInt(i + 3), cursor.getInt(i + 4));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, Measurement measurement, int i) {
        int i2 = i + 0;
        measurement.setId(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        measurement.setLid(cursor.getShort(i + 1) != 0);
        measurement.setSpeed(cursor.getInt(i + 2));
        measurement.setTemp(cursor.getInt(i + 3));
        measurement.setWeight(cursor.getInt(i + 4));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, Measurement measurement) {
        sQLiteStatement.clearBindings();
        Long id = measurement.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        sQLiteStatement.bindLong(2, measurement.getLid() ? 1L : 0L);
        sQLiteStatement.bindLong(3, measurement.getSpeed());
        sQLiteStatement.bindLong(4, measurement.getTemp());
        sQLiteStatement.bindLong(5, measurement.getWeight());
    }
}
