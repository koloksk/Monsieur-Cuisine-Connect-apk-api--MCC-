package mcapi.json;

import defpackage.g9;
import java.util.Arrays;

/* loaded from: classes.dex */
public class FavoritesResponse {
    public Long[] ids;

    public String toString() {
        StringBuilder sbA = g9.a("FAVORITES ");
        sbA.append(Arrays.toString(this.ids));
        return sbA.toString();
    }
}
