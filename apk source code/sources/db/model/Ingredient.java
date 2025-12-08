package db.model;

import defpackage.g9;
import org.greenrobot.greendao.DaoException;

/* loaded from: classes.dex */
public class Ingredient {
    public String a;
    public transient DaoSession b;
    public Long c;
    public IngredientsBase d;
    public Long e;
    public transient Long f;
    public transient boolean g;
    public transient IngredientDao h;
    public String i;
    public String j;

    public Ingredient(String str, Long l, Long l2, String str2, String str3) {
        this.a = str;
        this.c = l;
        this.e = l2;
        this.i = str2;
        this.j = str3;
    }

    public void __setDaoSession(DaoSession daoSession) {
        this.b = daoSession;
        this.h = daoSession != null ? daoSession.getIngredientDao() : null;
    }

    public void delete() {
        IngredientDao ingredientDao = this.h;
        if (ingredientDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        ingredientDao.delete(this);
    }

    public String getAmount() {
        return this.a;
    }

    public Long getId() {
        return this.c;
    }

    public IngredientsBase getIngredientsBase() {
        Long l = this.e;
        Long l2 = this.f;
        if (l2 == null || !l2.equals(l)) {
            DaoSession daoSession = this.b;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            IngredientsBase ingredientsBaseLoad = daoSession.getIngredientsBaseDao().load(l);
            synchronized (this) {
                this.d = ingredientsBaseLoad;
                this.f = l;
            }
        }
        return this.d;
    }

    public Long getIngredientsBaseId() {
        return this.e;
    }

    public boolean getIsHeader() {
        return this.g;
    }

    public String getName() {
        return this.i;
    }

    public String getUnit() {
        return this.j;
    }

    public void refresh() {
        IngredientDao ingredientDao = this.h;
        if (ingredientDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        ingredientDao.refresh(this);
    }

    public void setAmount(String str) {
        this.a = str;
    }

    public void setId(Long l) {
        this.c = l;
    }

    public void setIngredientsBase(IngredientsBase ingredientsBase) {
        synchronized (this) {
            this.d = ingredientsBase;
            Long id = ingredientsBase == null ? null : ingredientsBase.getId();
            this.e = id;
            this.f = id;
        }
    }

    public void setIngredientsBaseId(Long l) {
        this.e = l;
    }

    public void setIsHeader(boolean z) {
        this.g = z;
    }

    public void setName(String str) {
        this.i = str;
    }

    public void setUnit(String str) {
        this.j = str;
    }

    public String toString() {
        StringBuilder sbA = g9.a("Ingredient{id='");
        sbA.append(this.c);
        sbA.append('\'');
        sbA.append(", unit='");
        g9.a(sbA, this.j, '\'', ", amount='");
        g9.a(sbA, this.a, '\'', ", name='");
        sbA.append(this.i);
        sbA.append('\'');
        sbA.append('}');
        return sbA.toString();
    }

    public void update() {
        IngredientDao ingredientDao = this.h;
        if (ingredientDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        ingredientDao.update(this);
    }

    public Ingredient() {
    }
}
