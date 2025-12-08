package db;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import fragment.SettingsFragment;
import helper.ActionListener;
import helper.LayoutHelper;
import helper.SharedPreferencesHelper;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class ImportRecipesAndImagesTask extends AsyncTask<Void, Void, Boolean> {
    public static final String d = ImportRecipesAndImagesTask.class.getSimpleName();
    public final ActionListener<Boolean> a;
    public final WeakReference<Context> b;
    public final int c;

    public ImportRecipesAndImagesTask(@NonNull Context context, int i, @NonNull ActionListener<Boolean> actionListener) {
        this.b = new WeakReference<>(context);
        this.c = i;
        this.a = actionListener;
    }

    @Override // android.os.AsyncTask
    public void onPreExecute() {
        SharedPreferencesHelper.getInstance().setImportedRecipesLanguage(null);
        SharedPreferencesHelper.getInstance().clearImportedRecipesTypes();
        SharedPreferencesHelper.getInstance().setImportedRecipesVersion(-1);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(8:8|(1:26)(9:13|(1:22)(3:18|(2:21|19)|39)|23|(0)|28|37|29|33|34)|27|28|37|29|33|34) */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00e8, code lost:
    
        r12 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00e9, code lost:
    
        r0 = db.ImportRecipesAndImagesTask.d;
        r1 = defpackage.g9.a("Fatal error occurred during import process: ");
        r1.append(r12.getMessage());
        android.util.Log.e(r0, r1.toString());
     */
    @Override // android.os.AsyncTask
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.lang.Boolean doInBackground(java.lang.Void... r12) throws java.io.IOException {
        /*
            r11 = this;
            java.lang.ref.WeakReference<android.content.Context> r12 = r11.b
            java.lang.Object r12 = r12.get()
            android.content.Context r12 = (android.content.Context) r12
            r0 = 0
            if (r12 != 0) goto L17
            java.lang.String r12 = db.ImportRecipesAndImagesTask.d
            java.lang.String r1 = "doInBackground: context == null"
            android.util.Log.e(r12, r1)
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r0)
            return r12
        L17:
            java.lang.String r1 = db.ImportRecipesAndImagesTask.d
            java.lang.String r2 = "import recipes, start"
            android.util.Log.d(r1, r2)
            db.DbHelper r1 = db.DbHelper.getInstance()
            if (r1 == 0) goto L104
            java.lang.String r2 = "DbHelper"
            java.lang.String r3 = "Importing recipes from resources."
            android.util.Log.i(r2, r3)
            r3 = 2131623970(0x7f0e0022, float:1.8875107E38)
            java.lang.String r3 = helper.RecipeAssetsHelper.readTextFile(r12, r3)
            java.util.List r4 = mcapi.json.Parser.parseRecipes(r3)
            if (r4 == 0) goto Lc0
            int r5 = r4.size()
            if (r5 != 0) goto L40
            goto Lc0
        L40:
            java.lang.String r3 = ""
            r1.deleteRecipesOfType(r3)
            db.model.DaoSession r3 = r1.a
            db.model.RecipeDao r3 = r3.getRecipeDao()
            org.greenrobot.greendao.query.QueryBuilder r3 = r3.queryBuilder()
            org.greenrobot.greendao.Property r5 = db.model.RecipeDao.Properties.RecipeType
            java.lang.String r6 = "default"
            org.greenrobot.greendao.query.WhereCondition r5 = r5.notEq(r6)
            r7 = 1
            org.greenrobot.greendao.query.WhereCondition[] r8 = new org.greenrobot.greendao.query.WhereCondition[r7]
            org.greenrobot.greendao.Property r9 = db.model.RecipeDao.Properties.Language
            helper.SharedPreferencesHelper r10 = helper.SharedPreferencesHelper.getInstance()
            java.lang.String r10 = r10.getLanguage()
            org.greenrobot.greendao.query.WhereCondition r9 = r9.notEq(r10)
            r8[r0] = r9
            org.greenrobot.greendao.query.QueryBuilder r3 = r3.where(r5, r8)
            org.greenrobot.greendao.query.Query r3 = r3.build()
            java.util.List r3 = r3.list()
            if (r3 == 0) goto Lb0
            int r5 = r3.size()
            if (r5 != 0) goto L7f
            goto Lb0
        L7f:
            java.lang.String r5 = "Deleting "
            java.lang.StringBuilder r5 = defpackage.g9.a(r5)
            int r8 = r3.size()
            r5.append(r8)
            java.lang.String r8 = " non default recipes from previous language."
            r5.append(r8)
            java.lang.String r5 = r5.toString()
            android.util.Log.i(r2, r5)
            application.App r2 = application.App.getInstance()
            java.util.Iterator r3 = r3.iterator()
        La0:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto Lb5
            java.lang.Object r5 = r3.next()
            db.model.Recipe r5 = (db.model.Recipe) r5
            r1.a(r2, r5)
            goto La0
        Lb0:
            java.lang.String r3 = "no non default recipes to delete from previous language."
            android.util.Log.i(r2, r3)
        Lb5:
            int r1 = r1.a(r4, r7, r6)
            int r2 = r4.size()
            if (r1 != r2) goto Ld4
            goto Ld5
        Lc0:
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r4 = "Parsing yielded no recipes, input:\n"
            r1.append(r4)
            r1.append(r3)
            java.lang.String r1 = r1.toString()
            android.util.Log.w(r2, r1)
        Ld4:
            r7 = r0
        Ld5:
            java.lang.String r1 = db.ImportRecipesAndImagesTask.d
            java.lang.String r2 = "import images, start"
            android.util.Log.d(r1, r2)
            db.DbHelper r1 = db.DbHelper.getInstance()     // Catch: java.lang.Exception -> Le8
            java.util.List r0 = r1.getRecipesByName(r0, r0, r0, r0)     // Catch: java.lang.Exception -> Le8
            helper.RecipeAssetsHelper.copyAssets(r12, r0)     // Catch: java.lang.Exception -> Le8
            goto Lff
        Le8:
            r12 = move-exception
            java.lang.String r0 = db.ImportRecipesAndImagesTask.d
            java.lang.String r1 = "Fatal error occurred during import process: "
            java.lang.StringBuilder r1 = defpackage.g9.a(r1)
            java.lang.String r12 = r12.getMessage()
            r1.append(r12)
            java.lang.String r12 = r1.toString()
            android.util.Log.e(r0, r12)
        Lff:
            java.lang.Boolean r12 = java.lang.Boolean.valueOf(r7)
            return r12
        L104:
            r12 = 0
            throw r12
        */
        throw new UnsupportedOperationException("Method not decompiled: db.ImportRecipesAndImagesTask.doInBackground(java.lang.Void[]):java.lang.Boolean");
    }

    @Override // android.os.AsyncTask
    public void onPostExecute(Boolean bool) {
        if (bool.booleanValue()) {
            Log.d(d, "import images & recipes, done");
            SettingsFragment settingsFragment = (SettingsFragment) LayoutHelper.getInstance().findFragment(7);
            if (settingsFragment != null) {
                settingsFragment.updateMachineConfigView();
            }
            SharedPreferencesHelper.getInstance().setImportedRecipesVersion(this.c);
            SharedPreferencesHelper.getInstance().addImportedRecipesType(RecipeType.DEFAULT);
            SharedPreferencesHelper.getInstance().setImportedRecipesLanguage(SharedPreferencesHelper.getInstance().getLanguage());
        } else {
            Log.w(d, "import images & recipes, failed");
        }
        this.a.onAction(bool);
    }
}
