package helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import application.App;
import model.RecipeBookmark;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class SharedPreferencesHelper {
    public static SharedPreferencesHelper c;
    public final SharedPreferences.Editor a;
    public final SharedPreferences b;

    @SuppressLint({"CommitPrefEdits"})
    public SharedPreferencesHelper(@NonNull Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("PREFERENCES_MC2", 0);
        this.b = sharedPreferences;
        this.a = sharedPreferences.edit();
        if (this.b.getInt("SHARED_PREFERENCES_VERSION", -1) != 3) {
            this.a.clear().commit();
            Log.i("SharedPreferencesHelper", "saveSpVersion, spVersion >> 3");
            this.a.putInt("SHARED_PREFERENCES_VERSION", 3);
            this.a.apply();
        }
    }

    public static SharedPreferencesHelper getInstance() {
        if (c == null) {
            c = new SharedPreferencesHelper(App.getInstance());
        }
        return c;
    }

    public final void a(String str, boolean z) {
        if (str.equals("DATA_PRIVACY_POLICY_ACCEPTED") || str.equals("DATA_PRIVACY_POLICY_ACKNOWLEDGED") || str.equals("SEND_TRACKING_DATA") || str.equals("SHARE_TRACKING_DATA_LIDL") || str.equals("SHOW_MANUAL_TUTORIAL") || str.equals("SHOW_PRESET_TUTORIAL") || str.equals("SUBSCRIBE_NEWSLETTER") || str.equals("SUBSCRIBE_NEWSLETTER_LIDL")) {
            this.a.putBoolean(str, z);
            this.a.apply();
        }
    }

    public void acceptDataPrivacyPolicy(boolean z) {
        this.a.putBoolean("DATA_PRIVACY_POLICY_ACCEPTED", z);
        this.a.apply();
    }

    public void addImportedRecipesType(String str) {
        String string = this.b.getString("RECIPES_IMPORTED_TYPE", "");
        if (string.equals("")) {
            this.a.putString("RECIPES_IMPORTED_TYPE", str);
        } else if (!string.contains(str)) {
            this.a.putString("RECIPES_IMPORTED_TYPE", string + StringUtils.LF + str);
        }
        this.a.commit();
    }

    public void clearImportedRecipesTypes() {
        this.a.putString("RECIPES_IMPORTED_TYPE", "");
        this.a.commit();
    }

    public void clearUserSettings() {
        setAcceptedPrivacyPolicyVersion(0);
        setAcceptedTermsOfUseVersion(0);
    }

    public void countLaunch() {
        this.a.putInt("FIRST_LAUNCH", this.b.getInt("FIRST_LAUNCH", 0) + 1);
        this.a.apply();
        Log.d("SharedPreferencesHelper", "countLaunch ... " + this.b.getInt("FIRST_LAUNCH", 0));
    }

    public void doFactoryReset() {
        this.a.clear();
        removeBookmark();
        this.a.commit();
    }

    public void flush() {
        this.a.commit();
    }

    public int getAcceptedPrivacyPolicyVersion() {
        return this.b.getInt("KEY_ACCEPTED_PRIVACY_POLICY_VERSION", 0);
    }

    public int getAcceptedTermsOfUseVersion() {
        return this.b.getInt("KEY_ACCEPTED_TERMS_OF_USE_VERSION", 0);
    }

    public int getFailedRequestCount() {
        return this.b.getInt("KEY_REQUEST_FAILED_COUNT", 0);
    }

    public String getImportedRecipesLanguage() {
        return this.b.getString("RECIPES_IMPORTED_LANGUAGE", null);
    }

    @NonNull
    public String[] getImportedRecipesTypes() {
        return this.b.getString("RECIPES_IMPORTED_TYPE", "").split(StringUtils.LF);
    }

    public int getImportedRecipesVersion() {
        return this.b.getInt("RECIPES_IMPORTED_VERSION", -1);
    }

    public String getLanguage() {
        return this.b.getString("LANGUAGE", "de");
    }

    public long getLastInternetCheckTimestamp() {
        return this.b.getLong("KEY_LAST_INTERNET_CHECK", 0L);
    }

    public long getLastSurveyPopupTimestamp() {
        return this.b.getLong("KEY_LAST_SURVEY_POPUP_TIMESTAMP", 0L);
    }

    public String getMachineType() {
        return this.b.getString("KEY_MACHINE_TYPE", "");
    }

    public RecipeBookmark getRecipeBookmark() {
        String string = this.b.getString("BOOKMARK_RECIPE", null);
        if (string == null) {
            return null;
        }
        return new RecipeBookmark(string);
    }

    public String getRecipeLocation() {
        return this.b.getString("KEY_RECIPE_LOCATION", null);
    }

    public boolean getShowLabels() {
        return this.b.getBoolean("SHOW_ICON_LABELS", true);
    }

    public int getSoundVolume() {
        return this.b.getInt("SOUND_VOLUME", 100);
    }

    public String getUserToken() {
        return this.b.getString("USER_TOKEN", null);
    }

    public boolean hasUserAcknowledgedDataPrivacyTerms() {
        return this.b.getBoolean("DATA_PRIVACY_POLICY_ACKNOWLEDGED", false);
    }

    public boolean hasUserToken() {
        return this.b.getBoolean("USER_LOGGED_IN", false);
    }

    public void incrementFailedRequestCounter() {
        this.a.putInt("KEY_REQUEST_FAILED_COUNT", getFailedRequestCount() + 1);
        this.a.commit();
    }

    public boolean isDataPrivacyPolicyAccepted() {
        return this.b.getBoolean("DATA_PRIVACY_POLICY_ACCEPTED", false);
    }

    public boolean isInitialImportPending() {
        return !getLanguage().equals(getImportedRecipesLanguage()) || 179 > getImportedRecipesVersion();
    }

    public boolean isJogDialPushedAtBootTime() {
        return this.b.getBoolean("KEY_JOGDIAL_BOOTING", false);
    }

    public boolean isLidlNewsletterSubscribed() {
        return this.b.getBoolean("SUBSCRIBE_NEWSLETTER_LIDL", false);
    }

    public boolean isLocaleChosen() {
        return this.b.getBoolean("LOCALE_CHOSEN", false);
    }

    public boolean isMachineTimeReset() {
        return this.b.getBoolean("KEY_MACHINE_TIME_RESET", false);
    }

    public boolean isNewsletterSubscribed() {
        return this.b.getBoolean("SUBSCRIBE_NEWSLETTER", false);
    }

    public void markMachineTimeAsReset() {
        this.a.putBoolean("KEY_MACHINE_TIME_RESET", true);
        this.a.commit();
    }

    public void removeBookmark() {
        this.a.putString("BOOKMARK_RECIPE", null);
        this.a.apply();
    }

    public void removeImportedRecipesType(String str) {
        String string = this.b.getString("RECIPES_IMPORTED_TYPE", "");
        if (string.contains(str)) {
            String[] strArrSplit = string.split(StringUtils.LF);
            if (strArrSplit.length == 1) {
                this.a.putString("RECIPES_IMPORTED_TYPE", "");
            } else {
                StringBuilder sb = new StringBuilder();
                for (String str2 : strArrSplit) {
                    if (!str2.equals(str)) {
                        sb.append(StringUtils.LF);
                        sb.append(str2);
                    }
                }
                String string2 = sb.toString();
                this.a.putString("RECIPES_IMPORTED_TYPE", string2.length() > 1 ? string2.substring(1) : "");
            }
            this.a.commit();
        }
    }

    public void resetFailedRequestCounter() {
        this.a.putInt("KEY_REQUEST_FAILED_COUNT", 0);
        this.a.commit();
    }

    public void saveRecipeBookmark(RecipeBookmark recipeBookmark) {
        this.a.putString("BOOKMARK_RECIPE", recipeBookmark.toString());
        this.a.apply();
    }

    public void saveSoundVolume(int i) {
        this.a.putInt("SOUND_VOLUME", i);
        this.a.apply();
    }

    public void setAcceptedPrivacyPolicyVersion(int i) {
        this.a.putInt("KEY_ACCEPTED_PRIVACY_POLICY_VERSION", i);
        this.a.apply();
    }

    public void setAcceptedTermsOfUseVersion(int i) {
        this.a.putInt("KEY_ACCEPTED_TERMS_OF_USE_VERSION", i);
        this.a.apply();
    }

    public void setFirstLaunchDone() {
        this.a.putInt("FIRST_LAUNCH", Math.max(this.b.getInt("FIRST_LAUNCH", 0), 3));
        this.a.apply();
        Log.d("SharedPreferencesHelper", "setFirstLaunchDone... " + this.b.getInt("FIRST_LAUNCH", 0));
    }

    public void setImportedRecipesLanguage(String str) {
        this.a.putString("RECIPES_IMPORTED_LANGUAGE", str);
        this.a.commit();
    }

    public void setImportedRecipesVersion(int i) {
        this.a.putInt("RECIPES_IMPORTED_VERSION", i);
        this.a.commit();
    }

    public void setJogDialPushedAtBooting(boolean z) {
        this.a.putBoolean("KEY_JOGDIAL_BOOTING", z);
        this.a.commit();
    }

    public void setLanguage(String str) {
        if (!str.equals(getLanguage())) {
            removeBookmark();
        }
        this.a.putString("LANGUAGE", str);
        this.a.apply();
    }

    public void setLastInternetCheckTimestamp(long j) {
        this.a.putLong("KEY_LAST_INTERNET_CHECK", j);
        this.a.apply();
    }

    public void setLastSurveyPopupTimestamp(long j) {
        this.a.putLong("KEY_LAST_SURVEY_POPUP_TIMESTAMP", j);
        this.a.apply();
    }

    public void setLidlNewsletterSubscribed(boolean z) {
        a("SUBSCRIBE_NEWSLETTER_LIDL", z);
    }

    public void setLocaleChosen() {
        this.a.putBoolean("LOCALE_CHOSEN", true);
        this.a.apply();
    }

    public void setMachineType(String str) {
        Log.i("SharedPreferencesHelper", "setMachineType, type >> " + str);
        this.a.putString("KEY_MACHINE_TYPE", str);
        this.a.apply();
    }

    public void setManualTutorialShown() {
        this.a.putBoolean("SHOW_MANUAL_TUTORIAL", false);
        this.a.apply();
    }

    public void setNewsletterSubscribed(boolean z) {
        a("SUBSCRIBE_NEWSLETTER", z);
    }

    public void setPresetTutorialShown() {
        this.a.putBoolean("SHOW_PRESET_TUTORIAL", false);
        this.a.apply();
    }

    public void setRecipeLocation(String str) {
        Log.i("SharedPreferencesHelper", "setRecipeLocation, type >> " + str);
        this.a.putString("KEY_RECIPE_LOCATION", str);
        this.a.apply();
    }

    public void setSendTrackingData(boolean z) {
        a("SEND_TRACKING_DATA", z);
    }

    public void setShareTrackingDataLidl(boolean z) {
        a("SHARE_TRACKING_DATA_LIDL", z);
    }

    public void setShowLabels(boolean z) {
        this.a.putBoolean("SHOW_ICON_LABELS", z);
        this.a.apply();
    }

    public void setShowSurveyDialogs(boolean z) {
        this.a.putBoolean("KEY_SHOW_SURVEY_DIALOGS", z);
        this.a.commit();
    }

    public void setUserToken(String str) {
        Log.i("SharedPreferencesHelper", "setUserToken: " + str);
        this.a.putString("USER_TOKEN", str);
        this.a.putBoolean("USER_LOGGED_IN", TextUtils.isEmpty(str) ^ true);
        this.a.apply();
    }

    public boolean shouldSendTrackingData() {
        return this.b.getBoolean("SEND_TRACKING_DATA", false);
    }

    public boolean shouldShareTrackingDataLidl() {
        return this.b.getBoolean("SHARE_TRACKING_DATA_LIDL", false);
    }

    public boolean shouldShowFirstLaunch() {
        int i = this.b.getInt("FIRST_LAUNCH", 0);
        Log.d("SharedPreferencesHelper", "launches " + i);
        return i < 3;
    }

    public boolean shouldShowManualTutorial() {
        return this.b.getBoolean("SHOW_MANUAL_TUTORIAL", true);
    }

    public boolean shouldShowPresetTutorial() {
        return this.b.getBoolean("SHOW_PRESET_TUTORIAL", true);
    }

    public boolean shouldShowSurveyDialogs() {
        return this.b.getBoolean("KEY_SHOW_SURVEY_DIALOGS", true);
    }

    public void userAcknowledgedDataPrivacyTerms() {
        a("DATA_PRIVACY_POLICY_ACKNOWLEDGED", true);
    }
}
