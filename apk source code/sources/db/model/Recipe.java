package db.model;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.annotations.Expose;
import de.silpion.mc2.R;
import defpackage.g9;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.converter.PropertyConverter;

/* loaded from: classes.dex */
public class Recipe {

    @Expose
    public static final transient SimpleDateFormat E = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.GERMANY);
    public Date A;
    public int B;
    public int C;
    public String D;
    public String a;
    public transient DaoSession b;
    public int c;
    public int d;
    public Long e;
    public String f;
    public String g;
    public String h;
    public List<IngredientsBase> i;
    public List<String> j;
    public boolean k;

    @Expose
    public transient boolean l = false;
    public String m;
    public int n;
    public String o;
    public float p;
    public transient RecipeDao q;
    public String r;
    public List<Nutrient> s;
    public List<String> t;
    public int u;
    public List<Step> v;
    public List<Tag> w;
    public String x;
    public Date y;
    public Date z;

    public static class a implements PropertyConverter<List<String>, String> {
        @Override // org.greenrobot.greendao.converter.PropertyConverter
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public List<String> convertToEntityProperty(String str) {
            if (str == null) {
                return null;
            }
            return Arrays.asList(str.split("↵"));
        }

        @Override // org.greenrobot.greendao.converter.PropertyConverter
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public String convertToDatabaseValue(List<String> list) {
            if (list == null) {
                return null;
            }
            return TextUtils.join("↵", list);
        }
    }

    public Recipe() {
    }

    public void __setDaoSession(DaoSession daoSession) {
        this.b = daoSession;
        this.q = daoSession != null ? daoSession.getRecipeDao() : null;
    }

    public void delete() {
        RecipeDao recipeDao = this.q;
        if (recipeDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        recipeDao.delete(this);
    }

    public String getComplexity() {
        return this.a;
    }

    public int getDuration() {
        return this.c;
    }

    public int getDurationTotal() {
        return this.d;
    }

    public String getFormattedUpdateString() {
        return E.format(this.y);
    }

    public Long getId() {
        return this.e;
    }

    public String getImageBase() {
        return this.f;
    }

    public String getImageName() {
        return this.g;
    }

    public List<IngredientsBase> getIngredientsBases() {
        if (this.i == null) {
            DaoSession daoSession = this.b;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            List<IngredientsBase> list_queryRecipe_IngredientsBases = daoSession.getIngredientsBaseDao()._queryRecipe_IngredientsBases(this.e);
            synchronized (this) {
                if (this.i == null) {
                    this.i = list_queryRecipe_IngredientsBases;
                }
            }
        }
        return this.i;
    }

    public List<String> getInstructions() {
        return this.j;
    }

    public boolean getIsFavorite() {
        return this.k;
    }

    public String getLanguage() {
        return this.m;
    }

    public String getLargeImageName() {
        StringBuilder sbA = g9.a("large-");
        sbA.append(getImageName());
        return sbA.toString();
    }

    public String getLargeImagePath(String str) {
        StringBuilder sbA = g9.a(str, "/");
        sbA.append(getLargeImageName());
        return sbA.toString();
    }

    public int getLevel() {
        return this.n;
    }

    public String getLevelDescription(Context context) {
        int level = getLevel();
        return level != 1 ? level != 2 ? level != 3 ? context.getResources().getString(R.string.level) : context.getResources().getString(R.string.recipe_level_3) : context.getResources().getString(R.string.recipe_level_2) : context.getResources().getString(R.string.recipe_level_1);
    }

    public String getMachineType() {
        return this.o;
    }

    public float getMachineVersion() {
        return this.p;
    }

    public String getName() {
        return this.r;
    }

    public List<Nutrient> getNutrients() {
        if (this.s == null) {
            DaoSession daoSession = this.b;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            List<Nutrient> list_queryRecipe_Nutrients = daoSession.getNutrientDao()._queryRecipe_Nutrients(this.e);
            synchronized (this) {
                if (this.s == null) {
                    this.s = list_queryRecipe_Nutrients;
                }
            }
        }
        return this.s;
    }

    public List<String> getPreparations() {
        return this.t;
    }

    public String getRecipeType() {
        return this.h;
    }

    public int getSchemeVersion() {
        return this.u;
    }

    public String getSmallImageName() {
        StringBuilder sbA = g9.a("small-");
        sbA.append(getImageName());
        return sbA.toString();
    }

    public String getSmallImagePath(String str) {
        StringBuilder sbA = g9.a(str, "/");
        sbA.append(getSmallImageName());
        return sbA.toString();
    }

    public List<Step> getSteps() {
        if (this.v == null) {
            DaoSession daoSession = this.b;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            List<Step> list_queryRecipe_Steps = daoSession.getStepDao()._queryRecipe_Steps(this.e);
            synchronized (this) {
                if (this.v == null) {
                    this.v = list_queryRecipe_Steps;
                }
            }
        }
        return this.v;
    }

    public List<Tag> getTags() {
        if (this.w == null) {
            DaoSession daoSession = this.b;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            List<Tag> list_queryRecipe_Tags = daoSession.getTagDao()._queryRecipe_Tags(this.e);
            synchronized (this) {
                if (this.w == null) {
                    this.w = list_queryRecipe_Tags;
                }
            }
        }
        return this.w;
    }

    public String getUnit() {
        return this.x;
    }

    public Date getUpdated() {
        return this.y;
    }

    public Date getValidFrom() {
        return this.z;
    }

    public Date getValidTo() {
        return this.A;
    }

    public int getVersion() {
        return this.B;
    }

    public int getYield() {
        return this.C;
    }

    public String getYieldUnit() {
        return this.D;
    }

    public boolean hasTag(String str) {
        Iterator<Tag> it = getTags().iterator();
        while (it.hasNext()) {
            if (it.next().getName().equals(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSelected() {
        return this.l;
    }

    public void refresh() {
        RecipeDao recipeDao = this.q;
        if (recipeDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        recipeDao.refresh(this);
    }

    public synchronized void resetIngredientsBases() {
        this.i = null;
    }

    public synchronized void resetNutrients() {
        this.s = null;
    }

    public synchronized void resetSteps() {
        this.v = null;
    }

    public synchronized void resetTags() {
        this.w = null;
    }

    public void setComplexity(String str) {
        this.a = str;
    }

    public void setDuration(int i) {
        this.c = i;
    }

    public void setDurationTotal(int i) {
        this.d = i;
    }

    public void setId(Long l) {
        this.e = l;
    }

    public void setImageBase(String str) {
        this.f = str;
    }

    public void setImageName(String str) {
        this.g = str;
    }

    public void setInstructions(List<String> list) {
        this.j = list;
    }

    public void setIsFavorite(boolean z) {
        this.k = z;
    }

    public void setLanguage(String str) {
        this.m = str;
    }

    public void setLevel(int i) {
        this.n = i;
    }

    public void setMachineType(String str) {
        this.o = str;
    }

    public void setMachineVersion(float f) {
        this.p = f;
    }

    public void setName(String str) {
        this.r = str;
    }

    public void setPreparations(List<String> list) {
        this.t = list;
    }

    public void setRecipeType(String str) {
        this.h = str;
    }

    public void setSchemeVersion(int i) {
        this.u = i;
    }

    public void setSelected(boolean z) {
        this.l = z;
    }

    public void setUnit(String str) {
        this.x = str;
    }

    public void setUpdated(Date date) {
        this.y = date;
    }

    public void setValidFrom(Date date) {
        this.z = date;
    }

    public void setValidTo(Date date) {
        this.A = date;
    }

    public void setVersion(int i) {
        this.B = i;
    }

    public void setYield(int i) {
        this.C = i;
    }

    public void setYieldUnit(String str) {
        this.D = str;
    }

    public String toString() {
        StringBuilder sbA = g9.a("Recipe{id=");
        sbA.append(this.e);
        sbA.append(", updated=");
        sbA.append(this.y);
        sbA.append(", validFrom=");
        sbA.append(this.z);
        sbA.append(", validTo=");
        sbA.append(this.A);
        sbA.append(", version=");
        sbA.append(this.B);
        sbA.append(", schemeVersion=");
        sbA.append(this.u);
        sbA.append(", language='");
        g9.a(sbA, this.m, '\'', ", durationTotal=");
        sbA.append(this.d);
        sbA.append(", duration=");
        sbA.append(this.c);
        sbA.append(", machineType='");
        g9.a(sbA, this.o, '\'', ", machineVersion=");
        sbA.append(this.p);
        sbA.append(", imageBase='");
        g9.a(sbA, this.f, '\'', ", imageName='");
        g9.a(sbA, this.g, '\'', ", instructions=");
        sbA.append(this.j);
        sbA.append(", preparations=");
        sbA.append(this.t);
        sbA.append(", level=");
        sbA.append(this.n);
        sbA.append(", complexity='");
        g9.a(sbA, this.a, '\'', ", name='");
        g9.a(sbA, this.r, '\'', ", unit='");
        g9.a(sbA, this.x, '\'', ", yield=");
        sbA.append(this.C);
        sbA.append(", yieldUnit='");
        g9.a(sbA, this.D, '\'', ", nutrients=");
        sbA.append(this.s);
        sbA.append(", tags=");
        sbA.append(this.w);
        sbA.append(", ingredientsBases=");
        sbA.append(this.i);
        sbA.append(", isFavorite=");
        sbA.append(this.k);
        sbA.append('}');
        return sbA.toString();
    }

    public void update() {
        RecipeDao recipeDao = this.q;
        if (recipeDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        recipeDao.update(this);
    }

    public Recipe(String str, int i, int i2, Long l, String str2, String str3, String str4, List<String> list, boolean z, String str5, int i3, String str6, float f, String str7, List<String> list2, int i4, String str8, Date date, Date date2, Date date3, int i5, int i6, String str9) {
        this.a = str;
        this.c = i;
        this.d = i2;
        this.e = l;
        this.f = str2;
        this.g = str3;
        this.h = str4;
        this.j = list;
        this.k = z;
        this.m = str5;
        this.n = i3;
        this.o = str6;
        this.p = f;
        this.r = str7;
        this.t = list2;
        this.u = i4;
        this.x = str8;
        this.y = date;
        this.z = date2;
        this.A = date3;
        this.B = i5;
        this.C = i6;
        this.D = str9;
    }
}
