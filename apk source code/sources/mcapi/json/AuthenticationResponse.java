package mcapi.json;

import java.util.Locale;

/* loaded from: classes.dex */
public class AuthenticationResponse {
    public String legacy_cookie;
    public String token;

    public String toString() {
        return String.format(Locale.getDefault(), "AUTH token:%s cookie:%s", this.token, this.legacy_cookie);
    }
}
