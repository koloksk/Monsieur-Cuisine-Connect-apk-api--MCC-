package mcapi;

import android.util.Log;
import com.google.gson.Gson;
import cooking.CookingManager;
import defpackage.g9;
import defpackage.pm;
import helper.EncryptionChipServiceAdapter;
import helper.SharedPreferencesHelper;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import mcapi.json.MachineInfoData;
import mcapi.json.UsageEvent;
import mcapi.json.usages.ManualCookingEventPayload;
import mcapi.json.usages.PresetCookingEventPayload;
import mcapi.json.usages.RecipeCookingEventPayload;
import mcapi.json.usages.RecipeStepCookingEventPayload;
import model.Presets;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class McUsageApi extends pm {
    public static final String b = EncryptionChipServiceAdapter.getInstance().getSESerial();
    public static McUsageApi c;

    public class a implements HttpResponseListener {
        public a(McUsageApi mcUsageApi) {
        }

        @Override // mcapi.HttpResponseListener
        public void failure(Exception exc) {
        }

        @Override // mcapi.HttpResponseListener
        public void receivedResponse(int i, String str, BufferedReader bufferedReader) throws IOException {
            McUsageApi.c();
            Log.i("McUsageApi", "POST machine -> " + i);
            SharedPreferencesHelper.getInstance().resetFailedRequestCounter();
            try {
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        Log.d("McUsageApi", "Received response from /machine:");
                        Log.d("McUsageApi", sb.toString());
                        return;
                    }
                    sb.append(line);
                }
            } catch (Exception e) {
                StringBuilder sbA = g9.a("An error occured while parsing response from /machine:");
                sbA.append(e.getMessage());
                Log.d("McUsageApi", sbA.toString());
            }
        }
    }

    public class b implements HttpResponseListener {
        public b(McUsageApi mcUsageApi) {
        }

        @Override // mcapi.HttpResponseListener
        public void failure(Exception exc) {
        }

        @Override // mcapi.HttpResponseListener
        public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
        }
    }

    public static /* synthetic */ String c() {
        return "McUsageApi";
    }

    public static McUsageApi getInstance() {
        if (c == null) {
            c = new McUsageApi();
        }
        return c;
    }

    public final void a(UsageEvent usageEvent) throws JSONException {
        if (!a()) {
            a(usageEvent, new b(this));
            return;
        }
        Log.w("McUsageApi", "Unable to POST usage - no network\n" + usageEvent);
    }

    public void postAllMachineUsageLog() {
        postAllMachineUsageLog(new a(this));
    }

    public void postConnectionCheckEvent(HttpResponseListener httpResponseListener) throws JSONException {
        UsageEvent usageEvent = new UsageEvent();
        UsageEvent.Event event = UsageEvent.Event.CONNECTED_TO_SERVER;
        usageEvent.event = "CONNECTED_TO_SERVER";
        a(usageEvent, httpResponseListener);
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [T, mcapi.json.usages.ManualCookingEventPayload] */
    /* JADX WARN: Type inference failed for: r8v3, types: [T, mcapi.json.usages.PresetCookingEventPayload] */
    public void postMachineEvent(int i, boolean z) {
        UsageEvent usageEvent;
        ?? manualCookingEventPayload = new ManualCookingEventPayload(CookingManager.getInstance().getTime(), CookingManager.getInstance().getSpeedLevel(), CookingManager.getInstance().getTemperatureLevel());
        if (i == 1) {
            Log.d("McUsageApi", "UsageEvent - manual");
            usageEvent = new UsageEvent();
            if (z) {
                UsageEvent.Event event = UsageEvent.Event.START_MANUAL_COOKING;
                usageEvent.event = "START_MANUAL_COOKING";
            } else {
                UsageEvent.Event event2 = UsageEvent.Event.STOP_MANUAL_COOKING;
                usageEvent.event = "STOP_MANUAL_COOKING";
            }
            usageEvent.payload = manualCookingEventPayload;
        } else {
            if (i != 3 && i != 4 && i != 5) {
                Log.w("McUsageApi", "UsageEvent - unhandled >> mode " + i + " >> start " + z);
                return;
            }
            usageEvent = new UsageEvent();
            if (z) {
                UsageEvent.Event event3 = UsageEvent.Event.START_PRESET_COOKING;
                usageEvent.event = "START_PRESET_COOKING";
            } else {
                UsageEvent.Event event4 = UsageEvent.Event.STOP_PRESET_COOKING;
                usageEvent.event = "STOP_PRESET_COOKING";
            }
            usageEvent.payload = new PresetCookingEventPayload(i != 3 ? i != 4 ? i != 5 ? "NORMAL" : Presets.Tags.KNEADING : Presets.Tags.ROASTING : Presets.Tags.STEAMING, manualCookingEventPayload);
            StringBuilder sbA = g9.a("UsageEvent - preset ");
            sbA.append(usageEvent.toJson());
            Log.d("McUsageApi", sbA.toString());
        }
        a(usageEvent);
    }

    /* JADX WARN: Type inference failed for: r1v2, types: [T, mcapi.json.usages.RecipeCookingEventPayload] */
    public void postRecipeDoneEvent(long j) throws JSONException {
        UsageEvent usageEvent = new UsageEvent();
        UsageEvent.Event event = UsageEvent.Event.RECIPE_DONE;
        usageEvent.event = "RECIPE_DONE";
        usageEvent.payload = new RecipeCookingEventPayload(j);
        a(usageEvent);
    }

    /* JADX WARN: Type inference failed for: r5v3, types: [T, mcapi.json.usages.RecipeStepCookingEventPayload] */
    public void postRecipeStepEvent(long j, int i, boolean z) throws JSONException {
        UsageEvent usageEvent = new UsageEvent();
        if (z) {
            UsageEvent.Event event = UsageEvent.Event.START_RECIPE_STEP_COOKING;
            usageEvent.event = "START_RECIPE_STEP_COOKING";
        } else {
            UsageEvent.Event event2 = UsageEvent.Event.STOP_RECIPE_STEP_COOKING;
            usageEvent.event = "STOP_RECIPE_STEP_COOKING";
        }
        usageEvent.payload = new RecipeStepCookingEventPayload(j, i);
        a(usageEvent);
    }

    public void postSpecialSENumberEvent(HttpResponseListener httpResponseListener) throws JSONException {
        UsageEvent usageEvent = new UsageEvent();
        usageEvent.event = "SPECIAL_SE";
        a(usageEvent, httpResponseListener);
    }

    public void postStandbyEvent(boolean z, HttpResponseListener httpResponseListener) throws JSONException {
        String str;
        UsageEvent usageEvent = new UsageEvent();
        if (z) {
            UsageEvent.Event event = UsageEvent.Event.PAUSE_STANDBY;
            str = "PAUSE_STANDBY";
        } else {
            UsageEvent.Event event2 = UsageEvent.Event.PAUSE_ONLINE;
            str = "PAUSE_ONLINE";
        }
        usageEvent.event = str;
        a(usageEvent, httpResponseListener);
    }

    public void postAllMachineUsageLog(HttpResponseListener httpResponseListener) {
        if (a()) {
            Log.w("McUsageApi", "Unable to POST usage log - no network");
            return;
        }
        String language = SharedPreferencesHelper.getInstance().getLanguage();
        String json = new Gson().toJson(new MachineInfoData());
        a("https://mc20.monsieur-cuisine.com/mcc/api/v1/", "machine/", json, a(language), httpResponseListener);
        Log.d("McUsageApi", "Posting to /machine:");
        Log.d("McUsageApi", json);
    }

    public final void a(UsageEvent usageEvent, HttpResponseListener httpResponseListener) throws JSONException {
        String language = SharedPreferencesHelper.getInstance().getLanguage();
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("seserial", b);
            jSONObject.put("data", usageEvent.toJson());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        a("https://mc20.monsieur-cuisine.com/mcc/api/v1/", "event/", jSONObject.toString(), a(language), httpResponseListener);
    }

    public final Map<String, String> a(String str) {
        Log.i("McUsageApi", "usage event headers: " + str);
        HashMap map = new HashMap();
        map.put("Accept-Language", str);
        return map;
    }
}
