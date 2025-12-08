package db;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import application.App;
import db.model.DaoMaster;
import db.model.DaoSession;
import db.model.Ingredient;
import db.model.IngredientsBase;
import db.model.LED;
import db.model.LEDDao;
import db.model.MachineValues;
import db.model.MachineValuesDao;
import db.model.Measurement;
import db.model.MeasurementDao;
import db.model.Recipe;
import db.model.RecipeDao;
import db.model.Step;
import db.model.Tag;
import db.model.TagDao;
import de.silpion.mc2.R;
import defpackage.g9;
import helper.ActionListener;
import helper.RecipeAssetsHelper;
import helper.ResultListener;
import helper.SharedPreferencesHelper;
import java.text.Normalizer;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;
import mcapi.McApi;
import mcapi.ResponseListener;
import mcapi.json.MachineConfigResponse;
import mcapi.json.recipes.IngredientBase;
import mcapi.json.recipes.Nutrient;
import org.apache.commons.lang3.StringUtils;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.query.WhereCondition;

/* loaded from: classes.dex */
public class DbHelper {
    public static DbHelper b;
    public DaoSession a;

    public static class ImportResult {
        public int a;
        public int b;
        public boolean category_new_added;
        public boolean category_new_removed;

        public String toString() {
            return String.format(Locale.getDefault(), "All (++ %d, -- %d) .. new (++ %s, -- %s)", Integer.valueOf(this.a), Integer.valueOf(this.b), Boolean.valueOf(this.category_new_added), Boolean.valueOf(this.category_new_removed));
        }
    }

    public static class a extends DaoMaster.OpenHelper {
        public a(Context context, String str) {
            super(context, str);
        }

        @Override // db.model.DaoMaster.OpenHelper, org.greenrobot.greendao.database.DatabaseOpenHelper
        public void onCreate(Database database) {
            Log.i("DbHelper", "onCreate");
            super.onCreate(database);
        }

        @Override // org.greenrobot.greendao.database.DatabaseOpenHelper
        public void onUpgrade(Database database, int i, int i2) {
            Log.w("DbHelper", "onUpgrade " + i + " -> " + i2);
            DaoMaster.dropAllTables(database, true);
            DaoMaster.createAllTables(database, false);
            SharedPreferencesHelper.getInstance().setImportedRecipesVersion(-1);
        }
    }

    public static String a(String str, Map<String, ?> map) {
        for (String str2 : map.keySet()) {
            str = Pattern.compile(Pattern.quote("#{" + str2 + "}")).matcher(str).replaceAll(map.get(str2).toString());
        }
        return str;
    }

    public static DbHelper getInstance() {
        if (b == null) {
            b = new DbHelper();
        }
        return b;
    }

    public final void b(Recipe recipe) {
        List<Tag> tags = recipe.getTags();
        if (tags != null && tags.size() > 0) {
            ArrayList arrayList = new ArrayList();
            Iterator<Tag> it = tags.iterator();
            while (it.hasNext()) {
                arrayList.add(it.next().getId());
            }
            this.a.getTagDao().deleteByKeyInTx(arrayList);
        }
        recipe.resetTags();
    }

    public final String c(String str) {
        return str == null ? str : Normalizer.normalize(str, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
    }

    public void clearDatabase(Context context) {
        DaoSession daoSession = this.a;
        if (daoSession != null) {
            daoSession.clear();
            this.a.getDatabase().close();
            this.a = null;
        }
        SQLiteDatabase.deleteDatabase(context.getDatabasePath("Recipes"));
        createSession(context);
    }

    public long countRecipesByType(String str, String str2, boolean z) {
        QueryBuilder<Recipe> queryBuilder = this.a.getRecipeDao().queryBuilder();
        if (z) {
            a(queryBuilder);
        }
        return queryBuilder.where(RecipeDao.Properties.RecipeType.eq(str), RecipeDao.Properties.Language.eq(str2)).buildCount().count();
    }

    public void createSession(Context context) {
        DaoSession daoSession = this.a;
        if (daoSession != null) {
            daoSession.clear();
            this.a.getDatabase().close();
            this.a = null;
        }
        this.a = new DaoMaster(new a(context.getApplicationContext(), "Recipes").getWritableDb()).newSession();
    }

    public void deleteRecipesOfOtherLanguages(String str) {
        Log.i("DbHelper", "deleting recipes where language != " + str);
        App app = App.getInstance();
        for (Recipe recipe : this.a.getRecipeDao().queryBuilder().where(RecipeDao.Properties.Language.notEq(str), new WhereCondition[0]).build().list()) {
            StringBuilder sbA = g9.a(" ... ");
            sbA.append(recipe.getName());
            Log.i("DbHelper", sbA.toString());
            a(app, recipe);
        }
    }

    public void deleteRecipesOfType(String str) {
        App app = App.getInstance();
        Iterator<Recipe> it = this.a.getRecipeDao().queryBuilder().where(RecipeDao.Properties.RecipeType.eq(str), new WhereCondition[0]).build().list().iterator();
        while (it.hasNext()) {
            a(app, it.next());
        }
    }

    public List<Recipe> getAllRecipesSortedByCategory(boolean z) {
        List<String> categoryNames = getCategoryNames(z, true);
        QueryBuilder<Recipe> queryBuilder = this.a.getRecipeDao().queryBuilder();
        queryBuilder.join(Tag.class, TagDao.Properties.RecipeId);
        queryBuilder.distinct();
        if (z) {
            queryBuilder.where(RecipeDao.Properties.IsFavorite.eq(true), new WhereCondition[0]);
        }
        b(queryBuilder);
        a(queryBuilder);
        queryBuilder.where(RecipeDao.Properties.Language.eq(SharedPreferencesHelper.getInstance().getLanguage()), new WhereCondition[0]);
        queryBuilder.orderAsc(RecipeDao.Properties.Name);
        List<Recipe> list = queryBuilder.list();
        final HashMap map = new HashMap();
        for (Recipe recipe : list) {
            int iValueOf = -1;
            List<Tag> tags = recipe.getTags();
            int i = 0;
            while (true) {
                if (i >= tags.size()) {
                    break;
                }
                if (categoryNames.contains(tags.get(i).getName())) {
                    iValueOf = Integer.valueOf(categoryNames.indexOf(tags.get(i).getName()));
                    break;
                }
                i++;
            }
            map.put(recipe, iValueOf);
        }
        Collections.sort(list, new Comparator() { // from class: sd
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                Map map2 = map;
                return ((Integer) map2.get((Recipe) obj)).compareTo((Integer) map2.get((Recipe) obj2));
            }
        });
        return list;
    }

    public List<String> getCategoryNames(boolean z, boolean z2) {
        HashMap map = new HashMap();
        map.put("tagtable", TagDao.TABLENAME);
        map.put("tagname", TagDao.Properties.Name.columnName);
        map.put("tagcategory", TagDao.Properties.Category.columnName);
        map.put("tagrecipeid", TagDao.Properties.RecipeId.columnName);
        map.put("recipetable", RecipeDao.TABLENAME);
        map.put("recipeid", RecipeDao.Properties.Id.columnName);
        map.put("recipelanguage", RecipeDao.Properties.Language.columnName);
        map.put("language", SharedPreferencesHelper.getInstance().getLanguage());
        String strB = "SELECT DISTINCT t.#{tagname} FROM #{tagtable} t INNER JOIN #{recipetable} r ON t.#{tagrecipeid} = r.#{recipeid} WHERE t.#{tagcategory} = 'other' AND r.#{recipelanguage} = '#{language}'";
        if (z) {
            strB = g9.b("SELECT DISTINCT t.#{tagname} FROM #{tagtable} t INNER JOIN #{recipetable} r ON t.#{tagrecipeid} = r.#{recipeid} WHERE t.#{tagcategory} = 'other' AND r.#{recipelanguage} = '#{language}'", " AND r.#{recipeisfavorite} = 1");
            map.put("recipeisfavorite", RecipeDao.Properties.IsFavorite.columnName);
        }
        if (z2) {
            strB = g9.b(strB, " AND r.#{recipetype} in (#{recipetypes})");
            map.put(MachineConfigResponse.KEY_RECIPE_TYPE, RecipeDao.Properties.RecipeType.columnName);
            map.put("recipetypes", "'" + TextUtils.join("', '", SharedPreferencesHelper.getInstance().getImportedRecipesTypes()) + "'");
        }
        Cursor cursorRawQuery = this.a.getDatabase().rawQuery(a(g9.b(strB, " ORDER BY t.#{tagname} ASC"), map), null);
        ArrayList arrayList = new ArrayList();
        if (cursorRawQuery.moveToFirst()) {
            do {
                String string = cursorRawQuery.getString(0);
                if (string.equals(App.getInstance().getString(R.string.new_recipes_category_name))) {
                    arrayList.add(0, string);
                } else {
                    arrayList.add(string);
                }
            } while (cursorRawQuery.moveToNext());
        }
        cursorRawQuery.close();
        return arrayList;
    }

    public Date getMaxRecipesUpdate(String str, String str2) {
        List<Recipe> list = this.a.getRecipeDao().queryBuilder().where(RecipeDao.Properties.RecipeType.eq(str), RecipeDao.Properties.Language.eq(str2), RecipeDao.Properties.Updated.isNotNull()).orderDesc(RecipeDao.Properties.Updated).limit(1).list();
        if (list == null || list.size() <= 0) {
            return null;
        }
        return list.get(0).getUpdated();
    }

    public Recipe getRecipeById(long j) {
        return this.a.getRecipeDao().queryBuilder().where(RecipeDao.Properties.Id.eq(Long.valueOf(j)), new WhereCondition[0]).unique();
    }

    public List<Recipe> getRecipesByCategory(String str, boolean z) {
        QueryBuilder<Recipe> queryBuilder = this.a.getRecipeDao().queryBuilder();
        if (!TextUtils.isEmpty(str)) {
            queryBuilder.join(Tag.class, TagDao.Properties.RecipeId).where(TagDao.Properties.Name.eq(str), new WhereCondition[0]);
            queryBuilder.distinct();
        }
        if (z) {
            queryBuilder.where(RecipeDao.Properties.IsFavorite.eq(true), new WhereCondition[0]);
        }
        b(queryBuilder);
        a(queryBuilder);
        queryBuilder.where(RecipeDao.Properties.Language.eq(SharedPreferencesHelper.getInstance().getLanguage()), new WhereCondition[0]);
        queryBuilder.orderAsc(RecipeDao.Properties.Name);
        return queryBuilder.list();
    }

    public List<Recipe> getRecipesByIngredientsFromSearchString(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return new ArrayList();
        }
        List<Recipe> recipesByName = TextUtils.isEmpty(str2) ? getRecipesByName(false, true, true, true) : getRecipesByCategory(str2, false);
        String strC = c(str);
        ArrayList arrayList = new ArrayList();
        for (Recipe recipe : recipesByName) {
            boolean z = false;
            for (IngredientsBase ingredientsBase : recipe.getIngredientsBases()) {
                if (z) {
                    break;
                }
                Iterator<Ingredient> it = ingredientsBase.getIngredients().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    if (StringUtils.containsIgnoreCase(c(it.next().getName()), strC)) {
                        arrayList.add(recipe);
                        z = true;
                        break;
                    }
                }
            }
        }
        Log.d("DbHelper", "getRecipesByIngredientsFromSearchString >> search " + str + " >> category " + str2 + " >> " + arrayList.size());
        return arrayList;
    }

    public List<Recipe> getRecipesByName(boolean z, boolean z2, boolean z3, boolean z4) {
        QueryBuilder<Recipe> queryBuilder = this.a.getRecipeDao().queryBuilder();
        if (z) {
            queryBuilder.where(RecipeDao.Properties.IsFavorite.eq(true), new WhereCondition[0]);
        }
        if (z2) {
            b(queryBuilder);
        }
        if (z3) {
            a(queryBuilder);
        }
        if (z4) {
            queryBuilder.where(RecipeDao.Properties.Language.eq(SharedPreferencesHelper.getInstance().getLanguage()), new WhereCondition[0]);
        }
        List<Recipe> list = queryBuilder.orderAsc(RecipeDao.Properties.Name).list();
        StringBuilder sbA = g9.a("getRecipesByName ");
        sbA.append(list.size());
        sbA.append(" recipes");
        Log.d("DbHelper", sbA.toString());
        return list;
    }

    public List<Recipe> getRecipesBySearchString(String str, String str2) {
        if (TextUtils.isEmpty(str)) {
            return new ArrayList();
        }
        QueryBuilder<Recipe> queryBuilder = this.a.getRecipeDao().queryBuilder();
        if (!TextUtils.isEmpty(str2)) {
            queryBuilder.join(Tag.class, TagDao.Properties.RecipeId).where(TagDao.Properties.Name.eq(str2), new WhereCondition[0]);
            queryBuilder.distinct();
        }
        b(queryBuilder);
        a(queryBuilder);
        queryBuilder.where(RecipeDao.Properties.Language.eq(SharedPreferencesHelper.getInstance().getLanguage()), new WhereCondition[0]);
        queryBuilder.orderAsc(RecipeDao.Properties.Name);
        ArrayList arrayList = new ArrayList();
        for (Recipe recipe : queryBuilder.list()) {
            if (StringUtils.containsIgnoreCase(c(recipe.getName()), c(str))) {
                arrayList.add(recipe);
            }
        }
        return arrayList;
    }

    public boolean hasCategoryRecipes(String str) {
        QueryBuilder<Recipe> queryBuilder = this.a.getRecipeDao().queryBuilder();
        if (!TextUtils.isEmpty(str)) {
            queryBuilder.join(Tag.class, TagDao.Properties.RecipeId).where(TagDao.Properties.Name.eq(str), new WhereCondition[0]);
            queryBuilder.distinct();
        }
        return queryBuilder.buildCount().count() > 0;
    }

    public void importRecipesFromApi(@NonNull final ResultListener<ImportResult> resultListener, @NonNull final ImportResult importResult, final String str, final String str2) {
        String importedRecipesLanguage = SharedPreferencesHelper.getInstance().getImportedRecipesLanguage();
        Date maxRecipesUpdate = importedRecipesLanguage != null && !importedRecipesLanguage.equals(str2) ? null : getMaxRecipesUpdate(str, str2);
        final Date date = maxRecipesUpdate;
        McApi.getInstance().fetchRecipes(new ResponseListener() { // from class: td
            @Override // mcapi.ResponseListener
            public final void receivedResponse(int i, Object obj, Exception exc) {
                this.a.a(date, resultListener, importResult, str, str2, i, (List) obj, exc);
            }
        }, maxRecipesUpdate, str, str2);
    }

    public void removeEmptyTags() {
        Log.i("DbHelper", "Deleting empty tags...");
        HashMap map = new HashMap();
        map.put("tagtable", TagDao.TABLENAME);
        map.put("tagid", TagDao.Properties.Id.columnName);
        map.put("tagname", TagDao.Properties.Name.columnName);
        map.put("tagcategory", TagDao.Properties.Category.columnName);
        map.put("tagrecipeid", TagDao.Properties.RecipeId.columnName);
        map.put("recipetable", RecipeDao.TABLENAME);
        map.put("recipeid", RecipeDao.Properties.Id.columnName);
        Cursor cursorRawQuery = this.a.getDatabase().rawQuery(a("SELECT DISTINCT t.#{tagid}, t.#{tagname} FROM #{tagtable} t LEFT JOIN #{recipetable} r ON t.#{tagrecipeid} = r.#{recipeid} WHERE t.#{tagcategory} = 'other' AND r.#{recipeid} IS NULL", map), null);
        long j = 0;
        if (cursorRawQuery.moveToFirst()) {
            do {
                long j2 = cursorRawQuery.getLong(0);
                String string = cursorRawQuery.getString(1);
                this.a.getTagDao().deleteByKey(Long.valueOf(j2));
                j++;
                Log.i("DbHelper", "Empty tag deleted (TagName = " + string + ", TagId = " + j2 + ")");
            } while (cursorRawQuery.moveToNext());
        }
        Log.i("DbHelper", "Deleted " + j + " empty tag(s)");
        cursorRawQuery.close();
    }

    public void replaceFavorites(Long[] lArr, @NonNull ActionListener<Boolean> actionListener) {
        boolean z = true;
        try {
            try {
                Log.d("DbHelper", "applying favorites.");
                this.a.getDatabase().beginTransaction();
                String str = String.format("UPDATE %s SET %s = 0", RecipeDao.TABLENAME, RecipeDao.Properties.IsFavorite.columnName);
                Log.d("DbHelper", str);
                Cursor cursorRawQuery = this.a.getDatabase().rawQuery(str, null);
                cursorRawQuery.moveToFirst();
                cursorRawQuery.close();
                if (lArr != null) {
                    String str2 = String.format("UPDATE %s SET %s = 1 WHERE %s in (%s)", RecipeDao.TABLENAME, RecipeDao.Properties.IsFavorite.columnName, RecipeDao.Properties.Id.columnName, Arrays.toString(lArr).replaceAll("[\\[\\]]+", ""));
                    Log.i("DbHelper", "apply favorites: " + str2);
                    Cursor cursorRawQuery2 = this.a.getDatabase().rawQuery(str2, null);
                    cursorRawQuery2.moveToFirst();
                    cursorRawQuery2.close();
                }
                this.a.getDatabase().setTransactionSuccessful();
            } catch (SQLException e) {
                Log.e("DbHelper", "failed to apply remote favorites", e);
                this.a.getDatabase().endTransaction();
                z = false;
            }
            this.a.getRecipeDao().detachAll();
            actionListener.onAction(Boolean.valueOf(z));
        } finally {
            this.a.getDatabase().endTransaction();
        }
    }

    public /* synthetic */ void a(Date date, ResultListener resultListener, ImportResult importResult, String str, String str2, int i, List list, Exception exc) {
        if (i == 304) {
            Log.i("DbHelper", "No updated recipes since " + date);
            a(resultListener, importResult, str, str2);
            return;
        }
        if (i == 200 && list != null && exc == null) {
            Collection<?> collectionA = a(str);
            int iA = a((List<mcapi.json.recipes.Recipe>) list, false, str);
            Collection<?> collectionA2 = a(str);
            boolean z = true;
            importResult.category_new_added = importResult.category_new_added || !((AbstractCollection) collectionA).containsAll(collectionA2);
            if (!importResult.category_new_removed && ((AbstractCollection) collectionA2).containsAll(collectionA)) {
                z = false;
            }
            importResult.category_new_removed = z;
            importResult.a += iA;
            Log.i("DbHelper", "imported " + iA + " recipes.");
            if (iA < list.size()) {
                resultListener.onResult(false, null);
                return;
            } else {
                Log.i("DbHelper", "removeDeletedRecipes started");
                a(resultListener, importResult, str, str2);
                return;
            }
        }
        if (exc != null) {
            Log.e("DbHelper", "failed to fetch recipes .. " + i, exc);
        } else {
            Log.w("DbHelper", "failed to fetch recipes .. " + i + " .. " + list);
        }
        resultListener.onResult(false, null);
    }

    public final List<Long> b(String str) {
        Cursor cursorRawQuery = this.a.getDatabase().rawQuery(String.format("SELECT t.%s FROM %s t WHERE t.%s = ?", RecipeDao.Properties.Id.columnName, RecipeDao.TABLENAME, RecipeDao.Properties.RecipeType.columnName), new String[]{str});
        if (!cursorRawQuery.moveToFirst()) {
            cursorRawQuery.close();
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        do {
            arrayList.add(Long.valueOf(cursorRawQuery.getLong(0)));
        } while (cursorRawQuery.moveToNext());
        cursorRawQuery.close();
        return arrayList;
    }

    public long countRecipesByType(String str, String str2) {
        return countRecipesByType(str, str2, true);
    }

    public final void b(QueryBuilder<Recipe> queryBuilder) {
        String[] importedRecipesTypes = SharedPreferencesHelper.getInstance().getImportedRecipesTypes();
        if (importedRecipesTypes.length > 0) {
            queryBuilder.where(RecipeDao.Properties.RecipeType.in(importedRecipesTypes), new WhereCondition[0]);
        }
    }

    public final void a(Context context, Recipe recipe) {
        RecipeAssetsHelper.deleteRecipeAssets(context, recipe);
        a(recipe);
        b(recipe);
        recipe.delete();
    }

    public final void a(Recipe recipe) {
        List<Step> steps = recipe.getSteps();
        if (steps != null && steps.size() > 0) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            ArrayList arrayList4 = new ArrayList();
            for (Step step : steps) {
                arrayList.add(step.getId());
                MachineValues machineValues = step.getMachineValues();
                if (machineValues != null) {
                    arrayList2.add(machineValues.getId());
                }
                Measurement measurement = step.getMeasurement();
                if (measurement != null) {
                    arrayList3.add(measurement.getId());
                }
                LED led = step.getLed();
                if (led != null) {
                    arrayList4.add(led.getId());
                }
            }
            this.a.getMachineValuesDao().deleteByKeyInTx(arrayList2);
            this.a.getMeasurementDao().deleteByKeyInTx(arrayList3);
            this.a.getLEDDao().deleteByKeyInTx(arrayList4);
            this.a.getStepDao().deleteByKeyInTx(arrayList);
        }
        recipe.resetSteps();
    }

    public final List<Long> a(String str) {
        QueryBuilder<Recipe> queryBuilder = this.a.getRecipeDao().queryBuilder();
        queryBuilder.join(Tag.class, TagDao.Properties.RecipeId).where(TagDao.Properties.Name.eq(App.getInstance().getString(R.string.new_recipes_category_name)), new WhereCondition[0]);
        queryBuilder.distinct();
        queryBuilder.where(RecipeDao.Properties.RecipeType.eq(str), new WhereCondition[0]);
        ArrayList arrayList = new ArrayList();
        Iterator<Recipe> it = queryBuilder.list().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getId());
        }
        return arrayList;
    }

    public final void a(mcapi.json.recipes.Recipe recipe) {
        Recipe recipeUpdateRecipe = recipe.updateRecipe(this.a.getRecipeDao().load(recipe.id));
        this.a.insertOrReplace(recipeUpdateRecipe);
        List<mcapi.json.recipes.Step> list = recipe.guidedCooking.steps;
        a(recipeUpdateRecipe);
        MachineValuesDao machineValuesDao = this.a.getMachineValuesDao();
        MeasurementDao measurementDao = this.a.getMeasurementDao();
        LEDDao lEDDao = this.a.getLEDDao();
        ArrayList arrayList = new ArrayList();
        for (mcapi.json.recipes.Step step : list) {
            Step step2 = step.toStep(recipeUpdateRecipe);
            step2.setMachineValuesId(Long.valueOf(machineValuesDao.insert(step.machineValues.toMachineValues())));
            step2.setMeasurementId(Long.valueOf(measurementDao.insert(step.measurement.toMeasurement())));
            step2.setLedId(Long.valueOf(lEDDao.insert(step.led.toLed())));
            arrayList.add(step2);
        }
        this.a.getStepDao().insertInTx(arrayList, false);
        recipeUpdateRecipe.resetSteps();
        List<IngredientBase> list2 = recipe.ingredientsBases;
        List<IngredientsBase> ingredientsBases = recipeUpdateRecipe.getIngredientsBases();
        if (ingredientsBases != null && ingredientsBases.size() > 0) {
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            for (IngredientsBase ingredientsBase : ingredientsBases) {
                arrayList2.add(ingredientsBase.getId());
                Iterator<Ingredient> it = ingredientsBase.getIngredients().iterator();
                while (it.hasNext()) {
                    arrayList3.add(it.next().getId());
                }
            }
            this.a.getIngredientDao().deleteByKeyInTx(arrayList3);
            this.a.getIngredientsBaseDao().deleteByKeyInTx(arrayList2);
        }
        for (IngredientBase ingredientBase : list2) {
            IngredientsBase base = ingredientBase.toBase(recipeUpdateRecipe);
            base.setId(Long.valueOf(this.a.getIngredientsBaseDao().insert(base)));
            ArrayList arrayList4 = new ArrayList();
            Iterator<mcapi.json.recipes.Ingredient> it2 = ingredientBase.ingredients.iterator();
            while (it2.hasNext()) {
                arrayList4.add(it2.next().toIngredient(base));
            }
            this.a.getIngredientDao().insertInTx(arrayList4);
        }
        recipeUpdateRecipe.resetIngredientsBases();
        List<Nutrient> list3 = recipe.nutrients;
        List<db.model.Nutrient> nutrients = recipeUpdateRecipe.getNutrients();
        if (nutrients != null && nutrients.size() > 0) {
            ArrayList arrayList5 = new ArrayList();
            Iterator<db.model.Nutrient> it3 = nutrients.iterator();
            while (it3.hasNext()) {
                arrayList5.add(it3.next().getId());
            }
            this.a.getNutrientDao().deleteByKeyInTx(arrayList5);
        }
        ArrayList arrayList6 = new ArrayList();
        Iterator<Nutrient> it4 = list3.iterator();
        while (it4.hasNext()) {
            arrayList6.add(it4.next().toNutrient(recipeUpdateRecipe));
        }
        this.a.getNutrientDao().insertInTx(arrayList6);
        recipeUpdateRecipe.resetNutrients();
        List<mcapi.json.recipes.Tag> list4 = recipe.tags;
        b(recipeUpdateRecipe);
        HashSet hashSet = new HashSet();
        ArrayList arrayList7 = new ArrayList();
        for (mcapi.json.recipes.Tag tag : list4) {
            String str = tag.category + "::" + tag.name;
            if (hashSet.contains(str)) {
                Log.w("DbHelper", recipeUpdateRecipe.getName() + " --- Skip duplicate tag {category=" + tag.category + ", name=" + tag.name + "}");
            } else {
                hashSet.add(str);
                arrayList7.add(tag.toTag(recipeUpdateRecipe));
            }
        }
        this.a.getTagDao().insertInTx(arrayList7, false);
    }

    public final int a(List<mcapi.json.recipes.Recipe> list, boolean z, String str) {
        int i = 0;
        try {
            List<Long> listB = b(str);
            for (mcapi.json.recipes.Recipe recipe : list) {
                recipe.recipeType = str;
                a(recipe);
                ((ArrayList) listB).remove(recipe.id);
                i++;
                Log.i("DbHelper", "RecipeType: " + str + " >> " + i + " of " + list.size() + " imported. ");
            }
            if (z) {
                a(listB);
            }
        } catch (Exception e) {
            Log.e("DbHelper", "import failed", e);
        }
        removeEmptyTags();
        return i;
    }

    public final void a(@NonNull final ResultListener<ImportResult> resultListener, @NonNull final ImportResult importResult, final String str, String str2) {
        Log.i("DbHelper", "removeDeletedRecipesAndFetchAssets type " + str);
        McApi.getInstance().fetchRecipeIds(new ResponseListener() { // from class: ud
            @Override // mcapi.ResponseListener
            public final void receivedResponse(int i, Object obj, Exception exc) {
                this.a.a(resultListener, str, importResult, i, (List) obj, exc);
            }
        }, str, str2);
    }

    public /* synthetic */ void a(ResultListener resultListener, String str, ImportResult importResult, int i, List list, Exception exc) {
        if (list == null) {
            resultListener.onResult(false, null);
            return;
        }
        ArrayList arrayList = new ArrayList();
        List<Long> listA = a(str);
        Iterator it = ((ArrayList) b(str)).iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            Long l = (Long) it.next();
            if (!list.contains(l)) {
                arrayList.add(l);
                if (((ArrayList) listA).contains(l)) {
                    importResult.category_new_removed = true;
                }
            }
        }
        int iA = a(arrayList);
        Log.i("DbHelper", "removed " + iA + " recipes.");
        boolean z = iA == arrayList.size();
        importResult.b += iA;
        resultListener.onResult(RecipeAssetsHelper.download(App.getInstance(), getRecipesByName(false, false, false, false)) & z, importResult);
    }

    public final int a(List<Long> list) {
        App app = App.getInstance();
        int i = 0;
        try {
            for (Long l : list) {
                Recipe recipeLoad = this.a.getRecipeDao().load(l);
                if (recipeLoad != null) {
                    Log.i("DbHelper", " ~~~ deleting old recipe " + l);
                    a(app, recipeLoad);
                } else {
                    Log.i("DbHelper", " ~~~ recipe already gone " + l);
                }
                i++;
            }
        } catch (Exception e) {
            Log.e("DbHelper", "Failed to remove recipes.", e);
        }
        return i;
    }

    public final void a(QueryBuilder<Recipe> queryBuilder) {
        Date date = new Date();
        queryBuilder.whereOr(queryBuilder.and(RecipeDao.Properties.ValidFrom.isNull(), queryBuilder.or(RecipeDao.Properties.ValidTo.isNull(), RecipeDao.Properties.ValidTo.gt(date), new WhereCondition[0]), new WhereCondition[0]), queryBuilder.and(RecipeDao.Properties.ValidFrom.lt(date), queryBuilder.or(RecipeDao.Properties.ValidTo.isNull(), RecipeDao.Properties.ValidTo.gt(date), new WhereCondition[0]), new WhereCondition[0]), new WhereCondition[0]);
    }
}
