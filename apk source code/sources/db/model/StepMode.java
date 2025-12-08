package db.model;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface StepMode {
    public static final String COOKING = "cooking";
    public static final String END = "end";
    public static final String INSTRUCTION = "instruction";
    public static final String KNEADING = "kneading";
    public static final String PREPARATION = "preparation";
    public static final String RAMP = "ramp";
    public static final String ROASTING = "roasting";
    public static final String SCALE = "scale";
    public static final String STEAMING = "steaming";
    public static final String TURBO = "turbo";
    public static final String WAIT = "wait";
}
