package mcapi.json.usages;

/* loaded from: classes.dex */
public class PresetCookingEventPayload {
    public ManualCookingEventPayload configuration;
    public String mode;

    public PresetCookingEventPayload(String str, ManualCookingEventPayload manualCookingEventPayload) {
        this.mode = str;
        this.configuration = manualCookingEventPayload;
    }
}
