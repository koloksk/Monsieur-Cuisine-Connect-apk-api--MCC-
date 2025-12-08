package db.model;

import defpackage.g9;
import org.greenrobot.greendao.DaoException;

/* loaded from: classes.dex */
public class Nutrient {
    public String a;
    public transient DaoSession b;
    public Long c;
    public transient NutrientDao d;
    public Recipe e;
    public Long f;
    public transient Long g;
    public String h;
    public String i;

    public Nutrient(String str, Long l, Long l2, String str2, String str3) {
        this.a = str;
        this.c = l;
        this.f = l2;
        this.h = str2;
        this.i = str3;
    }

    public void __setDaoSession(DaoSession daoSession) {
        this.b = daoSession;
        this.d = daoSession != null ? daoSession.getNutrientDao() : null;
    }

    public void delete() {
        NutrientDao nutrientDao = this.d;
        if (nutrientDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        nutrientDao.delete(this);
    }

    public String getAmount() {
        return this.a;
    }

    public Long getId() {
        return this.c;
    }

    public Recipe getRecipe() {
        Long l = this.f;
        Long l2 = this.g;
        if (l2 == null || !l2.equals(l)) {
            DaoSession daoSession = this.b;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            Recipe recipeLoad = daoSession.getRecipeDao().load(l);
            synchronized (this) {
                this.e = recipeLoad;
                this.g = l;
            }
        }
        return this.e;
    }

    public long getRecipeId() {
        return this.f.longValue();
    }

    public String getType() {
        return this.h;
    }

    public String getUnit() {
        return this.i;
    }

    public void refresh() {
        NutrientDao nutrientDao = this.d;
        if (nutrientDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        nutrientDao.refresh(this);
    }

    public void setAmount(String str) {
        this.a = str;
    }

    public void setId(Long l) {
        this.c = l;
    }

    public void setRecipe(Recipe recipe) {
        synchronized (this) {
            this.e = recipe;
            Long id = recipe == null ? null : recipe.getId();
            this.f = id;
            this.g = id;
        }
    }

    public void setRecipeId(Long l) {
        this.f = l;
    }

    public void setType(String str) {
        this.h = str;
    }

    public void setUnit(String str) {
        this.i = str;
    }

    public String toString() {
        StringBuilder sbA = g9.a("Nutrient{id='");
        sbA.append(this.c);
        sbA.append('\'');
        sbA.append(", recipeId=");
        sbA.append(this.f);
        sbA.append(", type='");
        g9.a(sbA, this.h, '\'', ", amount='");
        g9.a(sbA, this.a, '\'', ", unit='");
        sbA.append(this.i);
        sbA.append('\'');
        sbA.append('}');
        return sbA.toString();
    }

    public void update() {
        NutrientDao nutrientDao = this.d;
        if (nutrientDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        nutrientDao.update(this);
    }

    public void setRecipeId(long j) {
        this.f = Long.valueOf(j);
    }

    public Nutrient() {
    }
}
