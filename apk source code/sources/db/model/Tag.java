package db.model;

import defpackage.g9;
import org.greenrobot.greendao.DaoException;

/* loaded from: classes.dex */
public class Tag {
    public String a;
    public transient DaoSession b;
    public Long c;
    public transient TagDao d;
    public String e;
    public Recipe f;
    public Long g;
    public transient Long h;

    public Tag(String str, Long l, String str2, Long l2) {
        this.a = str;
        this.c = l;
        this.e = str2;
        this.g = l2;
    }

    public void __setDaoSession(DaoSession daoSession) {
        this.b = daoSession;
        this.d = daoSession != null ? daoSession.getTagDao() : null;
    }

    public void delete() {
        TagDao tagDao = this.d;
        if (tagDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        tagDao.delete(this);
    }

    public String getCategory() {
        return this.a;
    }

    public Long getId() {
        return this.c;
    }

    public String getName() {
        return this.e;
    }

    public Recipe getRecipe() {
        Long l = this.g;
        Long l2 = this.h;
        if (l2 == null || !l2.equals(l)) {
            DaoSession daoSession = this.b;
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
        TagDao tagDao = this.d;
        if (tagDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        tagDao.refresh(this);
    }

    public void setCategory(String str) {
        this.a = str;
    }

    public void setId(Long l) {
        this.c = l;
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
        StringBuilder sbA = g9.a("Tag{id='");
        sbA.append(this.c);
        sbA.append('\'');
        sbA.append(", recipeId=");
        sbA.append(this.g);
        sbA.append(", category='");
        g9.a(sbA, this.a, '\'', ", name='");
        sbA.append(this.e);
        sbA.append('\'');
        sbA.append('}');
        return sbA.toString();
    }

    public void update() {
        TagDao tagDao = this.d;
        if (tagDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        tagDao.update(this);
    }

    public Tag() {
    }
}
