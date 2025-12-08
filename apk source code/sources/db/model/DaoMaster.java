package db.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import org.greenrobot.greendao.AbstractDaoMaster;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseOpenHelper;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.identityscope.IdentityScopeType;

/* loaded from: classes.dex */
public class DaoMaster extends AbstractDaoMaster {
    public static final int SCHEMA_VERSION = 9;

    public static class DevOpenHelper extends OpenHelper {
        public DevOpenHelper(Context context, String str) {
            super(context, str);
        }

        @Override // org.greenrobot.greendao.database.DatabaseOpenHelper
        public void onUpgrade(Database database, int i, int i2) {
            Log.i("greenDAO", "Upgrading schema from version " + i + " to " + i2 + " by dropping all tables");
            DaoMaster.dropAllTables(database, true);
            onCreate(database);
        }

        public DevOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory) {
            super(context, str, cursorFactory);
        }
    }

    public static abstract class OpenHelper extends DatabaseOpenHelper {
        public OpenHelper(Context context, String str) {
            super(context, str, 9);
        }

        @Override // org.greenrobot.greendao.database.DatabaseOpenHelper
        public void onCreate(Database database) {
            Log.i("greenDAO", "Creating tables for schema version 9");
            DaoMaster.createAllTables(database, false);
        }

        public OpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory) {
            super(context, str, cursorFactory, 9);
        }
    }

    public DaoMaster(SQLiteDatabase sQLiteDatabase) {
        this(new StandardDatabase(sQLiteDatabase));
    }

    public static void createAllTables(Database database, boolean z) {
        IngredientsBaseDao.createTable(database, z);
        TagDao.createTable(database, z);
        IngredientDao.createTable(database, z);
        LEDDao.createTable(database, z);
        MeasurementDao.createTable(database, z);
        RecipeDao.createTable(database, z);
        NutrientDao.createTable(database, z);
        StepDao.createTable(database, z);
        MachineValuesDao.createTable(database, z);
    }

    public static void dropAllTables(Database database, boolean z) {
        IngredientsBaseDao.dropTable(database, z);
        TagDao.dropTable(database, z);
        IngredientDao.dropTable(database, z);
        LEDDao.dropTable(database, z);
        MeasurementDao.dropTable(database, z);
        RecipeDao.dropTable(database, z);
        NutrientDao.dropTable(database, z);
        StepDao.dropTable(database, z);
        MachineValuesDao.dropTable(database, z);
    }

    public static DaoSession newDevSession(Context context, String str) {
        return new DaoMaster(new DevOpenHelper(context, str).getWritableDb()).newSession();
    }

    public DaoMaster(Database database) {
        super(database, 9);
        registerDaoClass(IngredientsBaseDao.class);
        registerDaoClass(TagDao.class);
        registerDaoClass(IngredientDao.class);
        registerDaoClass(LEDDao.class);
        registerDaoClass(MeasurementDao.class);
        registerDaoClass(RecipeDao.class);
        registerDaoClass(NutrientDao.class);
        registerDaoClass(StepDao.class);
        registerDaoClass(MachineValuesDao.class);
    }

    @Override // org.greenrobot.greendao.AbstractDaoMaster
    public DaoSession newSession() {
        return new DaoSession(this.f8db, IdentityScopeType.Session, this.daoConfigMap);
    }

    @Override // org.greenrobot.greendao.AbstractDaoMaster
    public DaoSession newSession(IdentityScopeType identityScopeType) {
        return new DaoSession(this.f8db, identityScopeType, this.daoConfigMap);
    }
}
