package db.model;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteStatement;
import db.model.Recipe;
import defpackage.g9;
import java.util.Date;
import java.util.List;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.internal.DaoConfig;

/* loaded from: classes.dex */
public class RecipeDao extends AbstractDao<Recipe, Long> {
    public static final String TABLENAME = "RECIPE";
    public DaoSession c;
    public final Recipe.a d;
    public final Recipe.a e;

    public static class Properties {
        public static final Property Complexity = new Property(0, String.class, "complexity", false, "COMPLEXITY");
        public static final Property Duration = new Property(1, Integer.TYPE, "duration", false, "DURATION");
        public static final Property DurationTotal = new Property(2, Integer.TYPE, "durationTotal", false, "DURATION_TOTAL");
        public static final Property Id = new Property(3, Long.class, "id", true, "_id");
        public static final Property ImageBase = new Property(4, String.class, "imageBase", false, "IMAGE_BASE");
        public static final Property ImageName = new Property(5, String.class, "imageName", false, "IMAGE_NAME");
        public static final Property RecipeType = new Property(6, String.class, "recipeType", false, "RECIPE_TYPE");
        public static final Property Instructions = new Property(7, String.class, "instructions", false, "INSTRUCTIONS");
        public static final Property IsFavorite = new Property(8, Boolean.TYPE, "isFavorite", false, "IS_FAVORITE");
        public static final Property Language = new Property(9, String.class, "language", false, "LANGUAGE");
        public static final Property Level = new Property(10, Integer.TYPE, "level", false, "LEVEL");
        public static final Property MachineType = new Property(11, String.class, "machineType", false, "MACHINE_TYPE");
        public static final Property MachineVersion = new Property(12, Float.TYPE, "machineVersion", false, "MACHINE_VERSION");
        public static final Property Name = new Property(13, String.class, "name", false, "NAME");
        public static final Property Preparations = new Property(14, String.class, "preparations", false, "PREPARATIONS");
        public static final Property SchemeVersion = new Property(15, Integer.TYPE, "schemeVersion", false, "SCHEME_VERSION");
        public static final Property Unit = new Property(16, String.class, "unit", false, "UNIT");
        public static final Property Updated = new Property(17, Date.class, "updated", false, "UPDATED");
        public static final Property ValidFrom = new Property(18, Date.class, "validFrom", false, "VALID_FROM");
        public static final Property ValidTo = new Property(19, Date.class, "validTo", false, "VALID_TO");
        public static final Property Version = new Property(20, Integer.TYPE, "version", false, "VERSION");
        public static final Property Yield = new Property(21, Integer.TYPE, "yield", false, "YIELD");
        public static final Property YieldUnit = new Property(22, String.class, "yieldUnit", false, "YIELD_UNIT");
    }

    public RecipeDao(DaoConfig daoConfig) {
        super(daoConfig);
        this.d = new Recipe.a();
        this.e = new Recipe.a();
    }

    public static void createTable(Database database, boolean z) throws SQLException {
        database.execSQL("CREATE TABLE " + (z ? "IF NOT EXISTS " : "") + "\"RECIPE\" (\"COMPLEXITY\" TEXT,\"DURATION\" INTEGER NOT NULL ,\"DURATION_TOTAL\" INTEGER NOT NULL ,\"_id\" INTEGER PRIMARY KEY ,\"IMAGE_BASE\" TEXT,\"IMAGE_NAME\" TEXT,\"RECIPE_TYPE\" TEXT,\"INSTRUCTIONS\" TEXT,\"IS_FAVORITE\" INTEGER NOT NULL ,\"LANGUAGE\" TEXT,\"LEVEL\" INTEGER NOT NULL ,\"MACHINE_TYPE\" TEXT,\"MACHINE_VERSION\" REAL NOT NULL ,\"NAME\" TEXT,\"PREPARATIONS\" TEXT,\"SCHEME_VERSION\" INTEGER NOT NULL ,\"UNIT\" TEXT,\"UPDATED\" INTEGER,\"VALID_FROM\" INTEGER,\"VALID_TO\" INTEGER,\"VERSION\" INTEGER NOT NULL ,\"YIELD\" INTEGER NOT NULL ,\"YIELD_UNIT\" TEXT);");
    }

    public static void dropTable(Database database, boolean z) throws SQLException {
        StringBuilder sbA = g9.a("DROP TABLE ");
        sbA.append(z ? "IF EXISTS " : "");
        sbA.append("\"RECIPE\"");
        database.execSQL(sbA.toString());
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final boolean isEntityUpdateable() {
        return true;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void attachEntity(Recipe recipe) {
        super.attachEntity((RecipeDao) recipe);
        recipe.__setDaoSession(this.c);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public Long getKey(Recipe recipe) {
        if (recipe != null) {
            return recipe.getId();
        }
        return null;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public boolean hasKey(Recipe recipe) {
        return recipe.getId() != null;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Long readKey(Cursor cursor, int i) {
        int i2 = i + 3;
        if (cursor.isNull(i2)) {
            return null;
        }
        return Long.valueOf(cursor.getLong(i2));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final Long updateKeyAfterInsert(Recipe recipe, long j) {
        recipe.setId(Long.valueOf(j));
        return Long.valueOf(j);
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(DatabaseStatement databaseStatement, Recipe recipe) {
        databaseStatement.clearBindings();
        String complexity = recipe.getComplexity();
        if (complexity != null) {
            databaseStatement.bindString(1, complexity);
        }
        databaseStatement.bindLong(2, recipe.getDuration());
        databaseStatement.bindLong(3, recipe.getDurationTotal());
        Long id = recipe.getId();
        if (id != null) {
            databaseStatement.bindLong(4, id.longValue());
        }
        String imageBase = recipe.getImageBase();
        if (imageBase != null) {
            databaseStatement.bindString(5, imageBase);
        }
        String imageName = recipe.getImageName();
        if (imageName != null) {
            databaseStatement.bindString(6, imageName);
        }
        String recipeType = recipe.getRecipeType();
        if (recipeType != null) {
            databaseStatement.bindString(7, recipeType);
        }
        List<String> instructions = recipe.getInstructions();
        if (instructions != null) {
            databaseStatement.bindString(8, this.d.convertToDatabaseValue(instructions));
        }
        databaseStatement.bindLong(9, recipe.getIsFavorite() ? 1L : 0L);
        String language = recipe.getLanguage();
        if (language != null) {
            databaseStatement.bindString(10, language);
        }
        databaseStatement.bindLong(11, recipe.getLevel());
        String machineType = recipe.getMachineType();
        if (machineType != null) {
            databaseStatement.bindString(12, machineType);
        }
        databaseStatement.bindDouble(13, recipe.getMachineVersion());
        String name = recipe.getName();
        if (name != null) {
            databaseStatement.bindString(14, name);
        }
        List<String> preparations = recipe.getPreparations();
        if (preparations != null) {
            databaseStatement.bindString(15, this.e.convertToDatabaseValue(preparations));
        }
        databaseStatement.bindLong(16, recipe.getSchemeVersion());
        String unit = recipe.getUnit();
        if (unit != null) {
            databaseStatement.bindString(17, unit);
        }
        Date updated = recipe.getUpdated();
        if (updated != null) {
            databaseStatement.bindLong(18, updated.getTime());
        }
        Date validFrom = recipe.getValidFrom();
        if (validFrom != null) {
            databaseStatement.bindLong(19, validFrom.getTime());
        }
        Date validTo = recipe.getValidTo();
        if (validTo != null) {
            databaseStatement.bindLong(20, validTo.getTime());
        }
        databaseStatement.bindLong(21, recipe.getVersion());
        databaseStatement.bindLong(22, recipe.getYield());
        String yieldUnit = recipe.getYieldUnit();
        if (yieldUnit != null) {
            databaseStatement.bindString(23, yieldUnit);
        }
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // org.greenrobot.greendao.AbstractDao
    public Recipe readEntity(Cursor cursor, int i) {
        float f;
        List<String> listConvertToEntityProperty;
        int i2;
        String str;
        Date date;
        int i3 = i + 0;
        String string = cursor.isNull(i3) ? null : cursor.getString(i3);
        int i4 = cursor.getInt(i + 1);
        int i5 = cursor.getInt(i + 2);
        int i6 = i + 3;
        Long lValueOf = cursor.isNull(i6) ? null : Long.valueOf(cursor.getLong(i6));
        int i7 = i + 4;
        String string2 = cursor.isNull(i7) ? null : cursor.getString(i7);
        int i8 = i + 5;
        String string3 = cursor.isNull(i8) ? null : cursor.getString(i8);
        int i9 = i + 6;
        String string4 = cursor.isNull(i9) ? null : cursor.getString(i9);
        int i10 = i + 7;
        List<String> listConvertToEntityProperty2 = cursor.isNull(i10) ? null : this.d.convertToEntityProperty(cursor.getString(i10));
        boolean z = cursor.getShort(i + 8) != 0;
        int i11 = i + 9;
        String string5 = cursor.isNull(i11) ? null : cursor.getString(i11);
        int i12 = cursor.getInt(i + 10);
        int i13 = i + 11;
        String string6 = cursor.isNull(i13) ? null : cursor.getString(i13);
        float f2 = cursor.getFloat(i + 12);
        int i14 = i + 13;
        String string7 = cursor.isNull(i14) ? null : cursor.getString(i14);
        int i15 = i + 14;
        if (cursor.isNull(i15)) {
            f = f2;
            listConvertToEntityProperty = null;
        } else {
            f = f2;
            listConvertToEntityProperty = this.e.convertToEntityProperty(cursor.getString(i15));
        }
        int i16 = cursor.getInt(i + 15);
        int i17 = i + 16;
        String string8 = cursor.isNull(i17) ? null : cursor.getString(i17);
        int i18 = i + 17;
        if (cursor.isNull(i18)) {
            i2 = i12;
            str = string6;
            date = null;
        } else {
            i2 = i12;
            str = string6;
            date = new Date(cursor.getLong(i18));
        }
        int i19 = i + 18;
        Date date2 = cursor.isNull(i19) ? null : new Date(cursor.getLong(i19));
        int i20 = i + 19;
        Date date3 = cursor.isNull(i20) ? null : new Date(cursor.getLong(i20));
        int i21 = i + 22;
        return new Recipe(string, i4, i5, lValueOf, string2, string3, string4, listConvertToEntityProperty2, z, string5, i2, str, f, string7, listConvertToEntityProperty, i16, string8, date, date2, date3, cursor.getInt(i + 20), cursor.getInt(i + 21), cursor.isNull(i21) ? null : cursor.getString(i21));
    }

    public RecipeDao(DaoConfig daoConfig, DaoSession daoSession) {
        super(daoConfig, daoSession);
        this.d = new Recipe.a();
        this.e = new Recipe.a();
        this.c = daoSession;
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public void readEntity(Cursor cursor, Recipe recipe, int i) {
        int i2 = i + 0;
        recipe.setComplexity(cursor.isNull(i2) ? null : cursor.getString(i2));
        recipe.setDuration(cursor.getInt(i + 1));
        recipe.setDurationTotal(cursor.getInt(i + 2));
        int i3 = i + 3;
        recipe.setId(cursor.isNull(i3) ? null : Long.valueOf(cursor.getLong(i3)));
        int i4 = i + 4;
        recipe.setImageBase(cursor.isNull(i4) ? null : cursor.getString(i4));
        int i5 = i + 5;
        recipe.setImageName(cursor.isNull(i5) ? null : cursor.getString(i5));
        int i6 = i + 6;
        recipe.setRecipeType(cursor.isNull(i6) ? null : cursor.getString(i6));
        int i7 = i + 7;
        recipe.setInstructions(cursor.isNull(i7) ? null : this.d.convertToEntityProperty(cursor.getString(i7)));
        recipe.setIsFavorite(cursor.getShort(i + 8) != 0);
        int i8 = i + 9;
        recipe.setLanguage(cursor.isNull(i8) ? null : cursor.getString(i8));
        recipe.setLevel(cursor.getInt(i + 10));
        int i9 = i + 11;
        recipe.setMachineType(cursor.isNull(i9) ? null : cursor.getString(i9));
        recipe.setMachineVersion(cursor.getFloat(i + 12));
        int i10 = i + 13;
        recipe.setName(cursor.isNull(i10) ? null : cursor.getString(i10));
        int i11 = i + 14;
        recipe.setPreparations(cursor.isNull(i11) ? null : this.e.convertToEntityProperty(cursor.getString(i11)));
        recipe.setSchemeVersion(cursor.getInt(i + 15));
        int i12 = i + 16;
        recipe.setUnit(cursor.isNull(i12) ? null : cursor.getString(i12));
        int i13 = i + 17;
        recipe.setUpdated(cursor.isNull(i13) ? null : new Date(cursor.getLong(i13)));
        int i14 = i + 18;
        recipe.setValidFrom(cursor.isNull(i14) ? null : new Date(cursor.getLong(i14)));
        int i15 = i + 19;
        recipe.setValidTo(cursor.isNull(i15) ? null : new Date(cursor.getLong(i15)));
        recipe.setVersion(cursor.getInt(i + 20));
        recipe.setYield(cursor.getInt(i + 21));
        int i16 = i + 22;
        recipe.setYieldUnit(cursor.isNull(i16) ? null : cursor.getString(i16));
    }

    @Override // org.greenrobot.greendao.AbstractDao
    public final void bindValues(SQLiteStatement sQLiteStatement, Recipe recipe) {
        sQLiteStatement.clearBindings();
        String complexity = recipe.getComplexity();
        if (complexity != null) {
            sQLiteStatement.bindString(1, complexity);
        }
        sQLiteStatement.bindLong(2, recipe.getDuration());
        sQLiteStatement.bindLong(3, recipe.getDurationTotal());
        Long id = recipe.getId();
        if (id != null) {
            sQLiteStatement.bindLong(4, id.longValue());
        }
        String imageBase = recipe.getImageBase();
        if (imageBase != null) {
            sQLiteStatement.bindString(5, imageBase);
        }
        String imageName = recipe.getImageName();
        if (imageName != null) {
            sQLiteStatement.bindString(6, imageName);
        }
        String recipeType = recipe.getRecipeType();
        if (recipeType != null) {
            sQLiteStatement.bindString(7, recipeType);
        }
        List<String> instructions = recipe.getInstructions();
        if (instructions != null) {
            sQLiteStatement.bindString(8, this.d.convertToDatabaseValue(instructions));
        }
        sQLiteStatement.bindLong(9, recipe.getIsFavorite() ? 1L : 0L);
        String language = recipe.getLanguage();
        if (language != null) {
            sQLiteStatement.bindString(10, language);
        }
        sQLiteStatement.bindLong(11, recipe.getLevel());
        String machineType = recipe.getMachineType();
        if (machineType != null) {
            sQLiteStatement.bindString(12, machineType);
        }
        sQLiteStatement.bindDouble(13, recipe.getMachineVersion());
        String name = recipe.getName();
        if (name != null) {
            sQLiteStatement.bindString(14, name);
        }
        List<String> preparations = recipe.getPreparations();
        if (preparations != null) {
            sQLiteStatement.bindString(15, this.e.convertToDatabaseValue(preparations));
        }
        sQLiteStatement.bindLong(16, recipe.getSchemeVersion());
        String unit = recipe.getUnit();
        if (unit != null) {
            sQLiteStatement.bindString(17, unit);
        }
        Date updated = recipe.getUpdated();
        if (updated != null) {
            sQLiteStatement.bindLong(18, updated.getTime());
        }
        Date validFrom = recipe.getValidFrom();
        if (validFrom != null) {
            sQLiteStatement.bindLong(19, validFrom.getTime());
        }
        Date validTo = recipe.getValidTo();
        if (validTo != null) {
            sQLiteStatement.bindLong(20, validTo.getTime());
        }
        sQLiteStatement.bindLong(21, recipe.getVersion());
        sQLiteStatement.bindLong(22, recipe.getYield());
        String yieldUnit = recipe.getYieldUnit();
        if (yieldUnit != null) {
            sQLiteStatement.bindString(23, yieldUnit);
        }
    }
}
