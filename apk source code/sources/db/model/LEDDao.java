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
public class LEDDao extends AbstractDao<LED, Long> {
    public static final String TABLENAME = "LED";

    public static class Properties {
        public static final Property Action = new Property(0, String.class, "action", false, "ACTION");
        public static final Property Color = new Property(1, String.class, "color", false, "COLOR");
        public static final Property Id = new Property(2, Long.class, "id", true, "_id");
    }

    public LEDDao(DaoConfig daoConfig) {
        super(daoConfig);
    }

    public static void createTable(Database database, boolean z) throws SQLException {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"LED\" (\"ACTION\" TEXT,\"COLOR\" TEXT,\"_id\" INTEGER PRIMARY KEY AUTOINCREMENT );");
    }

    public static void dropTable(Database database, boolean z) throws SQLException {
        StringBuilder sbA = g9.a("DROP TABLE ");
        sbA.append(z ? "IF EXISTS " : "");
        sbA.append("\"LED\"");
        database.execSQL(sbA.toString());
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final boolean isEntityUpdateable() {
        return true;
    }

    public LEDDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(LED led) {
        if (led != null) {
            return led.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(LED led) {
        return led.getId() != null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Long readKey(Cursor cursor, int i) {
        int i2 = i + 2;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final Long updateKeyAfterInsert(LED led, long j) {
        led.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, LED led) {
        databaseStatement.clearBindings();
        String action = led.getAction();
        if (action != null) {
            databaseStatement.bindString(1, action);
        }
        String color = led.getColor();
        if (color != null) {
            databaseStatement.bindString(2, color);
        }
        Long id = led.getId();
        if (id != null) {
            databaseStatement.bindLong(3, id.longValue());
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public LED readEntity(Cursor cursor, int i) {
        int i2 = i + 0;
        int i3 = i + 1;
        int i4 = i + 2;
        return new LED(cursor.isNull(i2) ? null : cursor.getString(i2), cursor.isNull(i3) ? null : cursor.getString(i3), cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4)));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, LED led, int i) {
        int i2 = i + 0;
        led.setAction(cursor.isNull(i2) ? null : cursor.getString(i2));
        int i3 = i + 1;
        led.setColor(cursor.isNull(i3) ? null : cursor.getString(i3));
        int i4 = i + 2;
        led.setId(cursor.isNull(i4) ? null : Long.valueOf(cursor.getLong(i4)));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, LED led) {
        sQLiteStatement.clearBindings();
        String action = led.getAction();
        if (action != null) {
            sQLiteStatement.bindString(1, action);
        }
        String color = led.getColor();
        if (color != null) {
            sQLiteStatement.bindString(2, color);
        }
        Long id = led.getId();
        if (id != null) {
            sQLiteStatement.bindLong(3, id.longValue());
        }
    }
}
