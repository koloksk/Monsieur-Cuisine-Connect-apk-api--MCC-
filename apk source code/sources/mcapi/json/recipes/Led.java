package mcapi.json.recipes;

import db.model.LED;

/* loaded from: classes.dex */
public class Led {
    public String action;
    public String color;

    public LED toLed() {
        LED led = new LED();
        led.setAction(this.action);
        led.setColor(this.color);
        return led;
    }
}
