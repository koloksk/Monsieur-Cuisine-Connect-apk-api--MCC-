package okhttp3;

import java.io.IOException;
import javax.annotation.Nullable;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/* loaded from: classes.dex */
public interface Authenticator {
    public static final Authenticator NONE = new Authenticator() { // from class: um
        @Override // okhttp3.Authenticator
        public final Request authenticate(Route route, Response response) {
            return ym.a(route, response);
        }
    };

    @Nullable
    Request authenticate(@Nullable Route route, Response response) throws IOException;
}
