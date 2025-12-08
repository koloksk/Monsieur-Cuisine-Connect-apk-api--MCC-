package db;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.SOURCE)
/* loaded from: classes.dex */
public @interface RecipeType {
    public static final String BETA = "beta";
    public static final String DEFAULT = "default";
    public static final String LIVE = "live";
    public static final String NONE = "";
}
