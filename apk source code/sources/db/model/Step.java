package db.model;

import defpackage.g9;
import org.greenrobot.greendao.DaoException;

/* loaded from: classes.dex */
public class Step {
    public transient DaoSession a;
    public Long b;
    public LED c;
    public Long d;
    public transient Long e;
    public MachineValues f;
    public Long g;
    public transient Long h;
    public Measurement i;
    public Long j;
    public transient Long k;
    public String l;
    public transient StepDao m;
    public Recipe n;
    public Long o;
    public transient Long p;
    public int q;
    public String r;

    public Step(Long l, Long l2, Long l3, Long l4, String str, Long l5, int i, String str2) {
        this.b = l;
        this.d = l2;
        this.g = l3;
        this.j = l4;
        this.l = str;
        this.o = l5;
        this.q = i;
        this.r = str2;
    }

    public void __setDaoSession(DaoSession daoSession) {
        this.a = daoSession;
        this.m = daoSession != null ? daoSession.getStepDao() : null;
    }

    public void delete() {
        StepDao stepDao = this.m;
        if (stepDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        stepDao.delete(this);
    }

    public Long getId() {
        return this.b;
    }

    public LED getLed() {
        Long l = this.d;
        Long l2 = this.e;
        if (l2 == null || !l2.equals(l)) {
            DaoSession daoSession = this.a;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LED ledLoad = daoSession.getLEDDao().load(l);
            synchronized (this) {
                this.c = ledLoad;
                this.e = l;
            }
        }
        return this.c;
    }

    public Long getLedId() {
        return this.d;
    }

    public MachineValues getMachineValues() {
        Long l = this.g;
        Long l2 = this.h;
        if (l2 == null || !l2.equals(l)) {
            DaoSession daoSession = this.a;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            MachineValues machineValuesLoad = daoSession.getMachineValuesDao().load(l);
            synchronized (this) {
                this.f = machineValuesLoad;
                this.h = l;
            }
        }
        return this.f;
    }

    public Long getMachineValuesId() {
        return this.g;
    }

    public Measurement getMeasurement() {
        Long l = this.j;
        Long l2 = this.k;
        if (l2 == null || !l2.equals(l)) {
            DaoSession daoSession = this.a;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            Measurement measurementLoad = daoSession.getMeasurementDao().load(l);
            synchronized (this) {
                this.i = measurementLoad;
                this.k = l;
            }
        }
        return this.i;
    }

    public Long getMeasurementId() {
        return this.j;
    }

    public String getMode() {
        return this.l;
    }

    public Recipe getRecipe() {
        Long l = this.o;
        Long l2 = this.p;
        if (l2 == null || !l2.equals(l)) {
            DaoSession daoSession = this.a;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            Recipe recipeLoad = daoSession.getRecipeDao().load(l);
            synchronized (this) {
                this.n = recipeLoad;
                this.p = l;
            }
        }
        return this.n;
    }

    public Long getRecipeId() {
        return this.o;
    }

    public int getStep() {
        return this.q;
    }

    public String getText() {
        return this.r;
    }

    public void refresh() {
        StepDao stepDao = this.m;
        if (stepDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        stepDao.refresh(this);
    }

    public void setId(Long l) {
        this.b = l;
    }

    public void setLed(LED led) {
        synchronized (this) {
            this.c = led;
            Long id = led == null ? null : led.getId();
            this.d = id;
            this.e = id;
        }
    }

    public void setLedId(Long l) {
        this.d = l;
    }

    public void setMachineValues(MachineValues machineValues) {
        synchronized (this) {
            this.f = machineValues;
            Long id = machineValues == null ? null : machineValues.getId();
            this.g = id;
            this.h = id;
        }
    }

    public void setMachineValuesId(Long l) {
        this.g = l;
    }

    public void setMeasurement(Measurement measurement) {
        synchronized (this) {
            this.i = measurement;
            Long id = measurement == null ? null : measurement.getId();
            this.j = id;
            this.k = id;
        }
    }

    public void setMeasurementId(Long l) {
        this.j = l;
    }

    public void setMode(String str) {
        this.l = str;
    }

    public void setRecipe(Recipe recipe) {
        synchronized (this) {
            this.n = recipe;
            Long id = recipe == null ? null : recipe.getId();
            this.o = id;
            this.p = id;
        }
    }

    public void setRecipeId(Long l) {
        this.o = l;
    }

    public void setStep(int i) {
        this.q = i;
    }

    public void setText(String str) {
        this.r = str;
    }

    public String toString() {
        StringBuilder sbA = g9.a("Step{id=");
        sbA.append(this.b);
        sbA.append(", recipeId=");
        sbA.append(this.o);
        sbA.append(", step=");
        sbA.append(this.q);
        sbA.append(", mode='");
        g9.a(sbA, this.l, '\'', ", text='");
        g9.a(sbA, this.r, '\'', ", machineValues=");
        sbA.append(this.f);
        sbA.append(", measurement=");
        sbA.append(this.i);
        sbA.append(", led=");
        sbA.append(this.c);
        sbA.append('}');
        return sbA.toString();
    }

    public void update() {
        StepDao stepDao = this.m;
        if (stepDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        stepDao.update(this);
    }

    public Step() {
    }
}
