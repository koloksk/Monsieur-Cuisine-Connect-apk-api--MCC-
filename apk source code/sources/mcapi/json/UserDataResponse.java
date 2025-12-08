package mcapi.json;

import defpackage.g9;
import java.util.Map;

/* loaded from: classes.dex */
public class UserDataResponse {
    public String displayname;
    public Map<String, Object> settings;
    public String uid;

    public String toString() {
        StringBuilder sbA = g9.a("USERDATA >> displayname ");
        sbA.append(this.displayname);
        sbA.append(" >> username ");
        sbA.append(this.uid);
        sbA.append(" >> settings ");
        sbA.append(this.settings.toString());
        return sbA.toString();
    }
}
