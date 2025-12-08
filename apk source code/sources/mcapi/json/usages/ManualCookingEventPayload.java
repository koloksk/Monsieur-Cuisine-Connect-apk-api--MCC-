package mcapi.json.usages;

/* loaded from: classes.dex */
public class ManualCookingEventPayload {
    public int speedLevel;
    public int temperatureLevel;
    public long time;

    public ManualCookingEventPayload(long j, int i, int i2) {
        this.time = j;
        this.speedLevel = i;
        this.temperatureLevel = i2;
    }
}
