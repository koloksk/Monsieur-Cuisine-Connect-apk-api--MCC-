package db.model;

import java.util.Map;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

/* loaded from: classes.dex */
public class DaoSession extends AbstractDaoSession {
    public final DaoConfig e;
    public final DaoConfig f;
    public final DaoConfig g;
    public final DaoConfig h;
    public final DaoConfig i;
    public final DaoConfig j;
    public final DaoConfig k;
    public final DaoConfig l;
    public final DaoConfig m;
    public final IngredientsBaseDao n;
    public final TagDao o;
    public final IngredientDao p;
    public final LEDDao q;
    public final MeasurementDao r;
    public final RecipeDao s;
    public final NutrientDao t;
    public final StepDao u;
    public final MachineValuesDao v;

    public DaoSession(Database database, IdentityScopeType identityScopeType, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig> map) {
        super(database);
        DaoConfig daoConfigClone = map.get(IngredientsBaseDao.class).clone();
        this.e = daoConfigClone;
        daoConfigClone.initIdentityScope(identityScopeType);
        DaoConfig daoConfigClone2 = map.get(TagDao.class).clone();
        this.f = daoConfigClone2;
        daoConfigClone2.initIdentityScope(identityScopeType);
        DaoConfig daoConfigClone3 = map.get(IngredientDao.class).clone();
        this.g = daoConfigClone3;
        daoConfigClone3.initIdentityScope(identityScopeType);
        DaoConfig daoConfigClone4 = map.get(LEDDao.class).clone();
        this.h = daoConfigClone4;
        daoConfigClone4.initIdentityScope(identityScopeType);
        DaoConfig daoConfigClone5 = map.get(MeasurementDao.class).clone();
        this.i = daoConfigClone5;
        daoConfigClone5.initIdentityScope(identityScopeType);
        DaoConfig daoConfigClone6 = map.get(RecipeDao.class).clone();
        this.j = daoConfigClone6;
        daoConfigClone6.initIdentityScope(identityScopeType);
        DaoConfig daoConfigClone7 = map.get(NutrientDao.class).clone();
        this.k = daoConfigClone7;
        daoConfigClone7.initIdentityScope(identityScopeType);
        DaoConfig daoConfigClone8 = map.get(StepDao.class).clone();
        this.l = daoConfigClone8;
        daoConfigClone8.initIdentityScope(identityScopeType);
        DaoConfig daoConfigClone9 = map.get(MachineValuesDao.class).clone();
        this.m = daoConfigClone9;
        daoConfigClone9.initIdentityScope(identityScopeType);
        this.n = new IngredientsBaseDao(this.e, this);
        this.o = new TagDao(this.f, this);
        this.p = new IngredientDao(this.g, this);
        this.q = new LEDDao(this.h, this);
        this.r = new MeasurementDao(this.i, this);
        this.s = new RecipeDao(this.j, this);
        this.t = new NutrientDao(this.k, this);
        this.u = new StepDao(this.l, this);
        this.v = new MachineValuesDao(this.m, this);
        registerDao(IngredientsBase.class, this.n);
        registerDao(Tag.class, this.o);
        registerDao(Ingredient.class, this.p);
        registerDao(LED.class, this.q);
        registerDao(Measurement.class, this.r);
        registerDao(Recipe.class, this.s);
        registerDao(Nutrient.class, this.t);
        registerDao(Step.class, this.u);
        registerDao(MachineValues.class, this.v);
    }

    public void clear() {
        this.e.clearIdentityScope();
        this.f.clearIdentityScope();
        this.g.clearIdentityScope();
        this.h.clearIdentityScope();
        this.i.clearIdentityScope();
        this.j.clearIdentityScope();
        this.k.clearIdentityScope();
        this.l.clearIdentityScope();
        this.m.clearIdentityScope();
    }

    public IngredientDao getIngredientDao() {
        return this.p;
    }

    public IngredientsBaseDao getIngredientsBaseDao() {
        return this.n;
    }

    public LEDDao getLEDDao() {
        return this.q;
    }

    public MachineValuesDao getMachineValuesDao() {
        return this.v;
    }

    public MeasurementDao getMeasurementDao() {
        return this.r;
    }

    public NutrientDao getNutrientDao() {
        return this.t;
    }

    public RecipeDao getRecipeDao() {
        return this.s;
    }

    public StepDao getStepDao() {
        return this.u;
    }

    public TagDao getTagDao() {
        return this.o;
    }
}
