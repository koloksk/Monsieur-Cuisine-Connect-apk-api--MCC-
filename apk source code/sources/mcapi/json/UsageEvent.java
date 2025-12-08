package mcapi.json;

import com.google.gson.Gson;

/* loaded from: classes.dex */
public class UsageEvent<T> {
    public String event;
    public T payload;

    public enum Event {
        START_MANUAL_COOKING,
        STOP_MANUAL_COOKING,
        START_PRESET_COOKING,
        STOP_PRESET_COOKING,
        START_RECIPE_STEP_COOKING,
        STOP_RECIPE_STEP_COOKING,
        RECIPE_DONE,
        CONNECTED_TO_SERVER,
        PAUSE_STANDBY,
        PAUSE_ONLINE
    }

    public String toJson() {
        return new Gson().toJson(this);
    }
}
