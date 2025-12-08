package okhttp3;

import defpackage.g9;
import java.io.IOException;

/* loaded from: classes.dex */
public enum Protocol {
    HTTP_1_0("http/1.0"),
    HTTP_1_1("http/1.1"),
    SPDY_3("spdy/3.1"),
    HTTP_2("h2"),
    H2_PRIOR_KNOWLEDGE("h2_prior_knowledge"),
    QUIC("quic");

    public final String a;

    Protocol(String str) {
        this.a = str;
    }

    public static Protocol get(String str) throws IOException {
        if (str.equals(HTTP_1_0.a)) {
            return HTTP_1_0;
        }
        if (str.equals(HTTP_1_1.a)) {
            return HTTP_1_1;
        }
        if (str.equals(H2_PRIOR_KNOWLEDGE.a)) {
            return H2_PRIOR_KNOWLEDGE;
        }
        if (str.equals(HTTP_2.a)) {
            return HTTP_2;
        }
        if (str.equals(SPDY_3.a)) {
            return SPDY_3;
        }
        if (str.equals(QUIC.a)) {
            return QUIC;
        }
        throw new IOException(g9.b("Unexpected protocol: ", str));
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.a;
    }
}
