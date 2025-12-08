package mcapi;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;
import defpackage.g9;
import defpackage.pm;
import defpackage.qm;
import defpackage.rm;
import helper.SharedPreferencesHelper;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mcapi.json.AuthenticationResponse;
import mcapi.json.ConstraintResponse;
import mcapi.json.FavoritesResponse;
import mcapi.json.MachineConfigResponse;
import mcapi.json.Parser;
import mcapi.json.RecipeIdsResponse;
import mcapi.json.RecipeResponse;
import mcapi.json.UserDataResponse;
import mcapi.json.recipes.Recipe;
import org.apache.commons.lang3.StringUtils;
import sound.SoundLength;

/* loaded from: classes.dex */
public class McApi extends pm {
    public static final McApi b = new McApi();

    public class a implements HttpResponseListener {
        public final /* synthetic */ ResponseListener a;

        public a(McApi mcApi, ResponseListener responseListener) {
            this.a = responseListener;
        }

        @Override // mcapi.HttpResponseListener
        public void failure(Exception exc) {
            this.a.receivedResponse(0, null, exc);
        }

        @Override // mcapi.HttpResponseListener
        public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
            McApi.d();
            Log.i("McApi", "AUTHENTICATE >> " + i + StringUtils.SPACE + str);
            if (bufferedReader == null) {
                this.a.receivedResponse(i, null, null);
                return;
            }
            try {
                AuthenticationResponse authenticationResponse = Parser.authenticationResponse(pm.a(bufferedReader));
                Log.i("McApi", "parsed: " + authenticationResponse);
                if (authenticationResponse != null) {
                    SharedPreferencesHelper.getInstance().setUserToken(authenticationResponse.token);
                }
                this.a.receivedResponse(i, authenticationResponse, null);
            } catch (IOException e) {
                Log.e("McApi", "failed to authenticate", e);
                this.a.receivedResponse(i, null, e);
            }
        }
    }

    public class b implements HttpResponseListener {
        public final /* synthetic */ ResponseListener a;

        public b(McApi mcApi, ResponseListener responseListener) {
            this.a = responseListener;
        }

        @Override // mcapi.HttpResponseListener
        public void failure(Exception exc) {
            this.a.receivedResponse(0, false, exc);
        }

        @Override // mcapi.HttpResponseListener
        public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
            if (bufferedReader == null) {
                this.a.receivedResponse(i, false, null);
            }
            this.a.receivedResponse(i, Boolean.valueOf(i == 200), null);
        }
    }

    public class c implements HttpResponseListener {
        public final /* synthetic */ ResponseListener a;

        public c(McApi mcApi, ResponseListener responseListener) {
            this.a = responseListener;
        }

        @Override // mcapi.HttpResponseListener
        public void failure(Exception exc) {
            this.a.receivedResponse(0, null, exc);
        }

        @Override // mcapi.HttpResponseListener
        public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
            if (i != 200 || bufferedReader == null) {
                this.a.receivedResponse(i, null, null);
                return;
            }
            try {
                RecipeIdsResponse recipeIdsResponse = Parser.recipeIdsResponse(pm.a(bufferedReader));
                if (recipeIdsResponse == null) {
                    this.a.receivedResponse(i, null, null);
                    return;
                }
                if (recipeIdsResponse.ids == null) {
                    recipeIdsResponse.ids = new ArrayList();
                }
                this.a.receivedResponse(i, recipeIdsResponse.ids, null);
            } catch (IOException e) {
                e.printStackTrace();
                this.a.receivedResponse(i, null, e);
            }
        }
    }

    public class d implements HttpResponseListener {
        public final /* synthetic */ ResponseListener a;

        public d(McApi mcApi, ResponseListener responseListener) {
            this.a = responseListener;
        }

        @Override // mcapi.HttpResponseListener
        public void failure(Exception exc) {
            this.a.receivedResponse(0, null, exc);
        }

        @Override // mcapi.HttpResponseListener
        public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
            McApi.d();
            Log.i("McApi", "RECIPE >> " + i + StringUtils.SPACE + str);
            if (bufferedReader == null) {
                this.a.receivedResponse(i, null, null);
                return;
            }
            try {
                String strA = pm.a(bufferedReader);
                Log.i("McApi", "RECIPE raw: " + strA.substring(0, Math.min(strA.length(), 100)) + "..");
                List<RecipeResponse> listRecipeResponse = Parser.recipeResponse(strA);
                Log.i("McApi", "RECIPE parsed: " + listRecipeResponse);
                if (listRecipeResponse == null) {
                    this.a.receivedResponse(i, null, null);
                    return;
                }
                ArrayList arrayList = new ArrayList();
                for (RecipeResponse recipeResponse : listRecipeResponse) {
                    if (recipeResponse.data != null) {
                        arrayList.add(recipeResponse.data);
                        Log.i("McApi", "RECIPE parsed: " + recipeResponse.data.name);
                    }
                }
                this.a.receivedResponse(i, arrayList, null);
            } catch (IOException e) {
                Log.e("McApi", "failed to fetch recipes", e);
                this.a.receivedResponse(i, null, e);
            }
        }
    }

    public class e implements HttpResponseListener {
        public final /* synthetic */ ResponseListener a;

        public e(McApi mcApi, ResponseListener responseListener) {
            this.a = responseListener;
        }

        @Override // mcapi.HttpResponseListener
        public void failure(Exception exc) {
            this.a.receivedResponse(0, null, exc);
        }

        @Override // mcapi.HttpResponseListener
        public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
            McApi.d();
            Log.i("McApi", "USERDATA >> " + i + StringUtils.SPACE + str);
            if (bufferedReader == null) {
                this.a.receivedResponse(i, null, null);
                return;
            }
            try {
                String strA = pm.a(bufferedReader);
                Log.i("McApi", "USERDATA raw: " + strA);
                UserDataResponse userDataResponse = Parser.userDataResponse(strA);
                Log.i("McApi", "USERDATA parsed: " + userDataResponse);
                this.a.receivedResponse(i, userDataResponse, null);
            } catch (IOException e) {
                Log.e("McApi", "failed to retrieve user data", e);
                this.a.receivedResponse(i, null, e);
            }
        }
    }

    public class f implements HttpResponseListener {
        public final /* synthetic */ ResponseListener a;

        public f(McApi mcApi, ResponseListener responseListener) {
            this.a = responseListener;
        }

        @Override // mcapi.HttpResponseListener
        public void failure(Exception exc) {
            this.a.receivedResponse(exc instanceof FileNotFoundException ? 404 : 0, null, exc);
        }

        @Override // mcapi.HttpResponseListener
        public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
            if (bufferedReader == null) {
                this.a.receivedResponse(i, null, null);
                return;
            }
            try {
                this.a.receivedResponse(i, Parser.machineConfigResponse(pm.a(bufferedReader)), null);
            } catch (IOException e) {
                McApi.d();
                Log.e("McApi", "failed get machine config", e);
                this.a.receivedResponse(i, null, e);
            }
        }
    }

    public class g implements HttpResponseListener {
        public final /* synthetic */ ResponseListener a;

        public g(McApi mcApi, ResponseListener responseListener) {
            this.a = responseListener;
        }

        @Override // mcapi.HttpResponseListener
        public void failure(Exception exc) {
            this.a.receivedResponse(0, null, exc);
        }

        @Override // mcapi.HttpResponseListener
        public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
            ConstraintResponse constraintResponse;
            McApi.d();
            Log.i("McApi", "REGISTER >> " + i + StringUtils.SPACE + str);
            if (bufferedReader == null) {
                this.a.receivedResponse(i, null, null);
                return;
            }
            try {
                Log.i("McApi", "REGISTER " + str);
                if (i == 409) {
                    constraintResponse = Parser.constraintResponse(pm.a(bufferedReader));
                    Log.i("McApi", "REGISTER parsed: " + constraintResponse);
                } else {
                    constraintResponse = null;
                }
                this.a.receivedResponse(i, constraintResponse, null);
            } catch (IOException e) {
                Log.e("McApi", "failed to register", e);
                this.a.receivedResponse(i, null, e);
            }
        }
    }

    public class h implements HttpResponseListener {
        public h(McApi mcApi) {
        }

        @Override // mcapi.HttpResponseListener
        public void failure(Exception exc) {
        }

        @Override // mcapi.HttpResponseListener
        public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
        }
    }

    public class i implements HttpResponseListener {
        public final /* synthetic */ ResponseListener a;

        public i(McApi mcApi, ResponseListener responseListener) {
            this.a = responseListener;
        }

        @Override // mcapi.HttpResponseListener
        public void failure(Exception exc) {
            this.a.receivedResponse(0, null, exc);
        }

        @Override // mcapi.HttpResponseListener
        public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
            McApi.d();
            Log.i("McApi", "FAVORITES >> " + i + StringUtils.SPACE + str);
            if (bufferedReader == null) {
                this.a.receivedResponse(i, null, null);
                return;
            }
            try {
                String strA = pm.a(bufferedReader);
                Log.i("FAVORITES", "response: " + strA);
                FavoritesResponse favoritesResponse = Parser.favoritesResponse(strA);
                Log.i("McApi", "FAVORITES parsed: " + favoritesResponse);
                if (i == 200 && favoritesResponse != null && favoritesResponse.ids != null) {
                    this.a.receivedResponse(i, favoritesResponse.ids, null);
                    return;
                }
                this.a.receivedResponse(i, null, null);
            } catch (IOException e) {
                Log.e("McApi", "failed to retrieve favorites ", e);
                this.a.receivedResponse(i, null, e);
            }
        }
    }

    public static /* synthetic */ String d() {
        return "McApi";
    }

    public static McApi getInstance() {
        return b;
    }

    public final void a(@NonNull ResponseListener<Long[]> responseListener, Integer num) {
        a("https://mc20.monsieur-cuisine.com/mcc/api/v1/", "favorite", SharedPreferencesHelper.getInstance().getUserToken(), null, null, num, new i(this, responseListener));
    }

    public void authenticate(@NonNull ResponseListener<AuthenticationResponse> responseListener, String str, String str2) {
        c();
        HttpResponseListener aVar = new a(this, responseListener);
        HashMap map = new HashMap();
        map.put("username", str);
        map.put("password", str2);
        HashMap map2 = new HashMap();
        map2.put("Accept-Language", SharedPreferencesHelper.getInstance().getLanguage());
        a("https://mc20.monsieur-cuisine.com/mcc/api/v1/", "authenticate", Parser.serialize(map), map2, aVar);
    }

    public final Map<String, String> b(String str, String str2) {
        Log.i("McApi", "recipe headers: " + str + ", " + str2);
        HashMap map = new HashMap();
        map.put("X-Recipe-Type", str);
        map.put("Accept-Language", str2);
        return map;
    }

    public final void c() {
        Log.i("McApi", "Clearing stored cookie & token.. ");
        SharedPreferencesHelper.getInstance().setUserToken(null);
    }

    public void checkUserToken(@NonNull ResponseListener<Boolean> responseListener) {
        if (a()) {
            Log.w("McApi", "Can't check usertoken - no network");
            responseListener.receivedResponse(0, false, null);
        }
        a(new b(this, responseListener));
    }

    public void fetchFavorites(@NonNull ResponseListener<Long[]> responseListener) {
        a(responseListener, (Integer) null);
    }

    public void fetchFavoritesFast(@NonNull ResponseListener<Long[]> responseListener) {
        a(responseListener, Integer.valueOf(SoundLength.SMALL_THRESHOLD));
    }

    public void fetchRecipeIds(@NonNull ResponseListener<List<Long>> responseListener, String str, String str2) {
        String recipeLocation = SharedPreferencesHelper.getInstance().getRecipeLocation();
        if (recipeLocation == null) {
            recipeLocation = "https://mc20.monsieur-cuisine.com/mcc/api/v1/";
        }
        a(recipeLocation, "recipe/ids", null, null, b(str, str2), null, new c(this, responseListener));
    }

    public void fetchRecipes(@NonNull ResponseListener<List<Recipe>> responseListener, Date date, String str, String str2) {
        String str3;
        d dVar = new d(this, responseListener);
        StringBuilder sbA = g9.a("getRecipes");
        if (date != null) {
            str3 = " since " + date;
        } else {
            str3 = "";
        }
        sbA.append(str3);
        Log.i("McApi", sbA.toString());
        String recipeLocation = SharedPreferencesHelper.getInstance().getRecipeLocation();
        if (recipeLocation == null) {
            recipeLocation = "https://mc20.monsieur-cuisine.com/mcc/api/v1/";
        }
        a(recipeLocation, "recipe/all", SharedPreferencesHelper.getInstance().getUserToken(), date, b(str, str2), null, dVar);
    }

    public void fetchUserData(@NonNull ResponseListener<UserDataResponse> responseListener) {
        a(new e(this, responseListener));
    }

    public void getMachineConfig(@NonNull ResponseListener<MachineConfigResponse> responseListener, @NonNull String str) {
        a("https://mc20.monsieur-cuisine.com/mcc/api/v1/", g9.b("machineconfig/", str), null, null, null, null, new f(this, responseListener));
    }

    public void logout() {
        c();
    }

    public void register(@NonNull ResponseListener<ConstraintResponse> responseListener, String str, String str2, String str3, String str4, String str5) {
        c();
        HttpResponseListener gVar = new g(this, responseListener);
        HashMap map = new HashMap();
        map.put("uid", str);
        map.put("displayname", str2);
        map.put("password", str3);
        HashMap map2 = new HashMap();
        map2.put("Accept-Language", SharedPreferencesHelper.getInstance().getLanguage());
        a("https://mc20.monsieur-cuisine.com/mcc/api/v1/", "register", Parser.serialize(map), map2, gVar);
    }

    public void setIsFavorite(Long l, boolean z) {
        if (z) {
            Log.i("McApi", "Recipe " + l + " will become a favorite..");
            new pm.f("https://mc20.monsieur-cuisine.com/mcc/api/v1/", g9.a("favorite/", l.longValue()), SharedPreferencesHelper.getInstance().getUserToken(), "", new qm(this, l)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
            return;
        }
        Log.i("McApi", "Removing favorite for " + l + "..");
        new pm.b("https://mc20.monsieur-cuisine.com/mcc/api/v1/", g9.a("favorite/", l.longValue()), SharedPreferencesHelper.getInstance().getUserToken(), new rm(this, l)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public void updateDataPrivacyOptions() {
        HashMap map = new HashMap();
        map.put("newsletter", Boolean.valueOf(SharedPreferencesHelper.getInstance().isNewsletterSubscribed()));
        map.put("data", Boolean.valueOf(SharedPreferencesHelper.getInstance().shouldSendTrackingData()));
        map.put("version", Integer.valueOf(SharedPreferencesHelper.getInstance().getAcceptedPrivacyPolicyVersion()));
        map.put("version_terms", Integer.valueOf(SharedPreferencesHelper.getInstance().getAcceptedTermsOfUseVersion()));
        h hVar = new h(this);
        Log.i("McApi", "postDataPrivacyOptions");
        HashMap map2 = new HashMap();
        map2.put("terms", map);
        a("https://mc20.monsieur-cuisine.com/mcc/api/v1/", "privacyterms", SharedPreferencesHelper.getInstance().getUserToken(), Parser.serialize(map2), null, hVar);
    }

    public final void a(@NonNull HttpResponseListener httpResponseListener) {
        a("https://mc20.monsieur-cuisine.com/mcc/api/v1/", "userdata", SharedPreferencesHelper.getInstance().getUserToken(), null, null, null, httpResponseListener);
    }
}
