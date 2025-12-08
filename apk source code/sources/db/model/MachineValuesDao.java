package db.model;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import android.support.v4.app.NotificationCompat;
import defpackage.g9;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* loaded from: classes.dex */
public class MachineValuesDao extends AbstractDao<MachineValues, Long> {
    public static final String TABLENAME = "MACHINE_VALUES";

    public static class Properties {
        public static final Property Id = new Property(0, Long.class, "id", true, "_id");
        public static final Property Reverse = new Property(1, Boolean.TYPE, "reverse", false, "REVERSE");
        public static final Property Speed = new Property(2, Integer.TYPE, "speed", false, "SPEED");
        public static final Property Temp = new Property(3, Integer.TYPE, "temp", false, "TEMP");
        public static final Property Time = new Property(4, Long.TYPE, NotificationCompat.MessagingStyle.Message.KEY_TIMESTAMP, false, "TIME");
        public static final Property Weight = new Property(5, Integer.TYPE, "weight", false, "WEIGHT");
    }

    public MachineValuesDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public static void createTable(Database database, boolean z) throws SQLException {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"MACHINE_VALUES\" (\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT ,\"REVERSE\" INTEGER NOT NULL ,\"SPEED\" INTEGER NOT NULL ,\"TEMP\" INTEGER NOT NULL ,\"TIME\" INTEGER NOT NULL ,\"WEIGHT\" INTEGER NOT NULL );");
    }

    public static void dropTable(Database database, boolean z) throws SQLException {
        StringBuilder sbA = g9.a("DROP TABLE ");
        sbA.append(z ? "IF EXISTS " : "");
        sbA.append("\"MACHINE_VALUES\"");
        database.execSQL(sbA.toString());
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final boolean isEntityUpdateable() {
        return true;
    }

    public MachineValuesDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(MachineValues machineValues) {
        if (machineValues != null) {
            return machineValues.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(MachineValues machineValues) {
        return machineValues.getId() != null;
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
    public final Long updateKeyAfterInsert(MachineValues machineValues, long j) {
        machineValues.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, MachineValues machineValues) {
        databaseStatement.clearBindings();
        Long id = machineValues.getId();
        if (id != null) {
            databaseStatement.bindLong(1, id.longValue());
        }
        databaseStatement.bindLong(2, machineValues.getReverse() ? 1L : 0L);
        databaseStatement.bindLong(3, machineValues.getSpeed());
        databaseStatement.bindLong(4, machineValues.getTemp());
        databaseStatement.bindLong(5, machineValues.getTime());
        databaseStatement.bindLong(6, machineValues.getWeight());
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public MachineValues readEntity(Cursor cursor, int i) {
        int i2 = i + 0;
        return new MachineValues(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)), cursor.getShort(i + 1) != 0, cursor.getInt(i + 2), cursor.getInt(i + 3), cursor.getLong(i + 4), cursor.getInt(i + 5));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, MachineValues machineValues, int i) {
        int i2 = i + 0;
        machineValues.setId(cursor.isNull(i2) ? null : Long.valueOf(cursor.getLong(i2)));
        machineValues.setReverse(cursor.getShort(i + 1) != 0);
        machineValues.setSpeed(cursor.getInt(i + 2));
        machineValues.setTemp(cursor.getInt(i + 3));
        machineValues.setTime(cursor.getLong(i + 4));
        machineValues.setWeight(cursor.getInt(i + 5));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, MachineValues machineValues) {
        sQLiteStatement.clearBindings();
        Long id = machineValues.getId();
        if (id != null) {
            sQLiteStatement.bindLong(1, id.longValue());
        }
        sQLiteStatement.bindLong(2, machineValues.getReverse() ? 1L : 0L);
        sQLiteStatement.bindLong(3, machineValues.getSpeed());
        sQLiteStatement.bindLong(4, machineValues.getTemp());
        sQLiteStatement.bindLong(5, machineValues.getTime());
        sQLiteStatement.bindLong(6, machineValues.getWeight());
    }
}
