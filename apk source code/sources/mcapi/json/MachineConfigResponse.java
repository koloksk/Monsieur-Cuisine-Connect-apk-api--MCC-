package mcapi.json;

import java.util.Map;

/* loaded from: classes.dex */
public class MachineConfigResponse {
    public static final String KEY_RECIPE_LOCATION = "recipelocation";
    public static final String KEY_RECIPE_TYPE = "recipetype";
    public static final String KEY_RECIPE_TYPE_ONLY = "recipetypeonly";
    public static final String KEY_UPDATE_URL = "updatelocation";
    public Map<String, String> config;
    public String seserial;

    public String toString() {
        return super.toString() + " >>> machine serial " + this.seserial + " >>> config " + this.config;
    }
}
