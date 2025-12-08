package db.model;

import defpackage.g9;
import java.util.List;
import org.greenrobot.greendao.DaoException;

/* loaded from: classes.dex */
public class IngredientsBase {
    public transient DaoSession a;
    public Long b;
    public List<Ingredient> c;
    public transient IngredientsBaseDao d;
    public String e;
    public Recipe f;
    public Long g;
    public transient Long h;

    public IngredientsBase(Long l, String str, Long l2) {
        this.b = l;
        this.e = str;
        this.g = l2;
    }

    public void __setDaoSession(DaoSession daoSession) {
        this.a = daoSession;
        this.d = daoSession != null ? daoSession.getIngredientsBaseDao() : null;
    }

    public void delete() {
        IngredientsBaseDao ingredientsBaseDao = this.d;
        if (ingredientsBaseDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        ingredientsBaseDao.delete(this);
    }

    public Long getId() {
        return this.b;
    }

    public List<Ingredient> getIngredients() {
        if (this.c == null) {
            DaoSession daoSession = this.a;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            List<Ingredient> list_queryIngredientsBase_Ingredients = daoSession.getIngredientDao()._queryIngredientsBase_Ingredients(this.b);
            synchronized (this) {
                if (this.c == null) {
                    this.c = list_queryIngredientsBase_Ingredients;
                }
            }
        }
        return this.c;
    }

    public String getName() {
        return this.e;
    }

    public Recipe getRecipe() {
        Long l = this.g;
        Long l2 = this.h;
        if (l2 == null || !l2.equals(l)) {
            DaoSession daoSession = this.a;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            Recipe recipeLoad = daoSession.getRecipeDao().load(l);
            synchronized (this) {
                this.f = recipeLoad;
                this.h = l;
            }
        }
        return this.f;
    }

    public Long getRecipeId() {
        return this.g;
    }

    public void refresh() {
        IngredientsBaseDao ingredientsBaseDao = this.d;
        if (ingredientsBaseDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        ingredientsBaseDao.refresh(this);
    }

    public synchronized void resetIngredients() {
        this.c = null;
    }

    public void setId(Long l) {
        this.b = l;
    }

    public void setName(String str) {
        this.e = str;
    }

    public void setRecipe(Recipe recipe) {
        synchronized (this) {
            this.f = recipe;
            Long id = recipe == null ? null : recipe.getId();
            this.g = id;
            this.h = id;
        }
    }

    public void setRecipeId(Long l) {
        this.g = l;
    }

    public String toString() {
        StringBuilder sbA = g9.a("IngredientsBase{id='");
        sbA.append(this.b);
        sbA.append('\'');
        sbA.append(", recipeId=");
        sbA.append(this.g);
        sbA.append(", name='");
        g9.a(sbA, this.e, '\'', ", ingredients=");
        sbA.append(this.c);
        sbA.append('}');
        return sbA.toString();
    }

    public void update() {
        IngredientsBaseDao ingredientsBaseDao = this.d;
        if (ingredientsBaseDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        ingredientsBaseDao.update(this);
    }

    public IngredientsBase() {
    }
}
