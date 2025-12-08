package defpackage;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Nullable;
import okio.AsyncTimeout;
import okio.Okio;

/* loaded from: classes.dex */
public final class ao extends AsyncTimeout {
    public final /* synthetic */ Socket j;

    public ao(Socket socket) {
        this.j = socket;
    }

    @Override // okio.AsyncTimeout
    public IOException newTimeoutException(@Nullable IOException iOException) {
        SocketTimeoutException socketTimeoutException = new SocketTimeoutException("timeout");
        if (iOException != null) {
            socketTimeoutException.initCause(iOException);
        }
        return socketTimeoutException;
    }

    @Override // okio.AsyncTimeout
    public void timedOut() throws IOException {
        try {
            this.j.close();
        } catch (AssertionError e) {
            if (!Okio.a(e)) {
                throw e;
            }
            Logger logger = Okio.a;
            Level level = Level.WARNING;
            StringBuilder sbA = g9.a("Failed to close timed out socket ");
            sbA.append(this.j);
            logger.log(level, sbA.toString(), (Throwable) e);
        } catch (Exception e2) {
            Logger logger2 = Okio.a;
            Level level2 = Level.WARNING;
            StringBuilder sbA2 = g9.a("Failed to close timed out socket ");
            sbA2.append(this.j);
            logger2.log(level2, sbA2.toString(), (Throwable) e2);
        }
    }
}
