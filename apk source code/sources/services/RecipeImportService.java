package services;

import android.app.IntentService;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import application.App;
import db.DbHelper;
import db.RecipeType;
import de.silpion.mc2.R;
import defpackage.g9;
import defpackage.zo;
import fragment.SettingsFragment;
import helper.ActionListener;
import helper.EncryptionChipServiceAdapter;
import helper.LayoutHelper;
import helper.ResultListener;
import helper.SharedPreferencesHelper;
import io.reactivex.annotations.SchedulerSupport;
import java.util.Arrays;
import java.util.Map;
import mcapi.McApi;
import mcapi.ResponseListener;
import mcapi.json.MachineConfigResponse;
import services.RecipeImportService;

/* loaded from: classes.dex */
public class RecipeImportService extends IntentService {
    public static final String f = RecipeImportService.class.getSimpleName();
    public static long g;
    public final Handler a;
    public boolean b;
    public ConnectivityManager c;
    public String d;
    public boolean e;

    public RecipeImportService() {
        super(f);
        this.a = new Handler();
        this.d = null;
        this.e = false;
    }

    public /* synthetic */ void a(int i, MachineConfigResponse machineConfigResponse, Exception exc) {
        Map<String, String> map;
        boolean z = false;
        if (machineConfigResponse == null || (map = machineConfigResponse.config) == null) {
            this.d = null;
            this.e = false;
            if (i == 404) {
                this.b = true;
                Log.i(f, "No configuration found for this machine. Use standard config...");
                SharedPreferencesHelper.getInstance().setRecipeLocation(null);
            } else {
                String str = f;
                StringBuilder sbA = g9.a("ERROR: Cannot get configuration. ");
                sbA.append(exc.getMessage());
                Log.i(str, sbA.toString());
            }
        } else {
            this.d = map.containsKey(MachineConfigResponse.KEY_RECIPE_TYPE) ? machineConfigResponse.config.get(MachineConfigResponse.KEY_RECIPE_TYPE) : null;
            SharedPreferencesHelper.getInstance().setMachineType(this.d);
            String str2 = machineConfigResponse.config.containsKey(MachineConfigResponse.KEY_RECIPE_LOCATION) ? machineConfigResponse.config.get(MachineConfigResponse.KEY_RECIPE_LOCATION) : null;
            String str3 = f;
            StringBuilder sbA2 = g9.a("found specified recipe location: ");
            sbA2.append(str2 == null ? SchedulerSupport.NONE : str2);
            Log.i(str3, sbA2.toString());
            SharedPreferencesHelper.getInstance().setRecipeLocation(str2);
            if (machineConfigResponse.config.containsKey(MachineConfigResponse.KEY_RECIPE_TYPE_ONLY) && "true".equals(machineConfigResponse.config.get(MachineConfigResponse.KEY_RECIPE_TYPE_ONLY))) {
                z = true;
            }
            this.e = z;
        }
        if (i != 0) {
            this.b = true;
        }
        this.a.post(new Runnable() { // from class: xo
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a();
            }
        });
    }

    public final void b() {
        McApi.getInstance().fetchFavorites(new ResponseListener() { // from class: cp
            @Override // mcapi.ResponseListener
            public final void receivedResponse(int i, Object obj, Exception exc) {
                RecipeImportService.a(i, (Long[]) obj, exc);
            }
        });
    }

    public final void c() {
        String language = SharedPreferencesHelper.getInstance().getLanguage();
        a(language);
        a(RecipeType.BETA);
        a(new DbHelper.ImportResult(), language, RecipeType.DEFAULT);
    }

    public final void d() {
        String language = SharedPreferencesHelper.getInstance().getLanguage();
        a(language);
        a(new DbHelper.ImportResult(), language, this.d, RecipeType.DEFAULT);
    }

    public final void e() {
        String language = SharedPreferencesHelper.getInstance().getLanguage();
        a(language);
        String[] strArr = {RecipeType.DEFAULT, RecipeType.LIVE};
        if (RecipeType.DEFAULT.equals(this.d)) {
            strArr[0] = RecipeType.BETA;
        } else if (RecipeType.LIVE.equals(this.d)) {
            strArr[1] = RecipeType.BETA;
        }
        a(strArr);
        a(new DbHelper.ImportResult(), language, this.d);
    }

    public final void f() {
        NetworkInfo activeNetworkInfo;
        if (this.c == null) {
            this.c = (ConnectivityManager) App.getInstance().getSystemService("connectivity");
        }
        ConnectivityManager connectivityManager = this.c;
        boolean z = true;
        if ((connectivityManager == null || ((activeNetworkInfo = connectivityManager.getActiveNetworkInfo()) != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected())) ? false : true) {
            Log.w(f, "won't start recipe import - no connection.");
            return;
        }
        if (g + 300000 <= System.currentTimeMillis()) {
            g = System.currentTimeMillis();
            z = false;
        }
        if (z) {
            Log.w(f, "won't start recipe import - rate limiting");
            return;
        }
        Log.i(f, "checkMachineConfig");
        if (!this.b) {
            McApi.getInstance().getMachineConfig(new ResponseListener() { // from class: dp
                @Override // mcapi.ResponseListener
                public final void receivedResponse(int i, Object obj, Exception exc) {
                    this.a.a(i, (MachineConfigResponse) obj, exc);
                }
            }, EncryptionChipServiceAdapter.getInstance().getSESerial());
            return;
        }
        Log.i(f, "checkUserToken");
        if (SharedPreferencesHelper.getInstance().hasUserToken()) {
            McApi.getInstance().checkUserToken(new zo(this));
            return;
        }
        if (this.d == null) {
            c();
        } else if (this.e) {
            e();
        } else {
            d();
        }
    }

    @Override // android.app.IntentService
    public void onHandleIntent(Intent intent) {
        Log.i(f, "onHandleIntent");
        if (SharedPreferencesHelper.getInstance().isInitialImportPending()) {
            Log.w(f, "won't import - initial import still running.");
        } else {
            this.a.post(new Runnable() { // from class: fp
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.f();
                }
            });
        }
    }

    public final void a() {
        Log.i(f, "checkUserToken");
        if (SharedPreferencesHelper.getInstance().hasUserToken()) {
            McApi.getInstance().checkUserToken(new zo(this));
            return;
        }
        if (this.d != null) {
            if (this.e) {
                e();
                return;
            } else {
                d();
                return;
            }
        }
        c();
    }

    public /* synthetic */ void a(int i, Boolean bool, Exception exc) {
        if (i == 0) {
            return;
        }
        if (bool.booleanValue() && this.d != null) {
            if (this.e) {
                e();
                return;
            }
            String language = SharedPreferencesHelper.getInstance().getLanguage();
            a(language);
            a(new DbHelper.ImportResult(), language, this.d, RecipeType.LIVE, RecipeType.DEFAULT);
            return;
        }
        if (bool.booleanValue()) {
            String language2 = SharedPreferencesHelper.getInstance().getLanguage();
            a(language2);
            a(RecipeType.BETA);
            a(new DbHelper.ImportResult(), language2, RecipeType.LIVE, RecipeType.DEFAULT);
            return;
        }
        if (this.d != null) {
            if (this.e) {
                e();
                return;
            } else {
                d();
                return;
            }
        }
        c();
    }

    public static /* synthetic */ void a(int i, final Long[] lArr, Exception exc) {
        if (exc == null && lArr != null && i == 200) {
            DbHelper.getInstance().replaceFavorites(lArr, new ActionListener() { // from class: ep
                @Override // helper.ActionListener
                public final void onAction(Object obj) {
                    RecipeImportService.a(lArr, (Boolean) obj);
                }
            });
        }
    }

    public static /* synthetic */ void a(Long[] lArr, Boolean bool) {
        String str = f;
        StringBuilder sbA = g9.a("Imported ");
        sbA.append(lArr.length);
        sbA.append(" favorites.");
        Log.i(str, sbA.toString());
    }

    public /* synthetic */ void a(DbHelper.ImportResult importResult) {
        String str = f;
        StringBuilder sbA = g9.a("importDone ");
        sbA.append(importResult == null ? "" : importResult.toString());
        Log.i(str, sbA.toString());
        if (SharedPreferencesHelper.getInstance().hasUserToken()) {
            this.a.post(new Runnable() { // from class: yo
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.b();
                }
            });
        }
        SharedPreferencesHelper.getInstance().setImportedRecipesLanguage(SharedPreferencesHelper.getInstance().getLanguage());
        if (importResult != null && importResult.category_new_added) {
            if (importResult.category_new_removed) {
                App.getInstance().getMainActivity().showNewRecipesDialog(R.string.new_recipes_received_and_removed_message);
            } else {
                App.getInstance().getMainActivity().showNewRecipesDialog(R.string.new_recipes_message);
            }
        }
        App.getInstance().getMainActivity().refreshRecipesDisplay();
        SettingsFragment settingsFragment = (SettingsFragment) LayoutHelper.getInstance().findFragment(7);
        if (settingsFragment != null) {
            settingsFragment.updateMachineConfigView();
        }
    }

    public final void a(DbHelper.ImportResult importResult, final String str, @NonNull String... strArr) {
        if (strArr.length == 0) {
            return;
        }
        if (importResult == null) {
            importResult = new DbHelper.ImportResult();
        }
        final String str2 = strArr[0];
        final String[] strArr2 = strArr.length > 1 ? (String[]) Arrays.copyOfRange(strArr, 1, strArr.length) : null;
        Log.i(f, "Importing " + str2 + " recipes.");
        DbHelper.getInstance().importRecipesFromApi(new ResultListener() { // from class: ap
            @Override // helper.ResultListener
            public final void onResult(boolean z, Object obj) {
                this.a.a(str2, strArr2, str, z, (DbHelper.ImportResult) obj);
            }
        }, importResult, str2, str);
    }

    public /* synthetic */ void a(String str, String[] strArr, String str2, boolean z, final DbHelper.ImportResult importResult) {
        String str3 = f;
        StringBuilder sbA = g9.a("Import ", str);
        sbA.append(z ? " successful" : " failed");
        Log.i(str3, sbA.toString());
        if (!z) {
            Log.w(f, "An error occurred during the import process.");
        }
        SharedPreferencesHelper.getInstance().addImportedRecipesType(str);
        if (strArr != null) {
            a(importResult, str2, strArr);
        } else {
            this.a.post(new Runnable() { // from class: bp
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.a(importResult);
                }
            });
        }
    }

    public final void a(String str) {
        if (TextUtils.isEmpty(str)) {
            return;
        }
        DbHelper.getInstance().deleteRecipesOfOtherLanguages(str);
    }

    public final void a(String... strArr) {
        for (String str : strArr) {
            DbHelper.getInstance().deleteRecipesOfType(str);
            SharedPreferencesHelper.getInstance().removeImportedRecipesType(str);
        }
    }
}
