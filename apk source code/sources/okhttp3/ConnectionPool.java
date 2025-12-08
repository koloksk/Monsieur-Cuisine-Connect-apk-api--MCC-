package okhttp3;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.internal.connection.RealConnectionPool;

/* loaded from: classes.dex */
public final class ConnectionPool {
    public final RealConnectionPool a;

    public ConnectionPool() {
        this(5, 5L, TimeUnit.MINUTES);
    }

    public int connectionCount() {
        return this.a.connectionCount();
    }

    public void evictAll() throws IOException {
        this.a.evictAll();
    }

    public int idleConnectionCount() {
        return this.a.idleConnectionCount();
    }

    public ConnectionPool(int i, long j, TimeUnit timeUnit) {
        this.a = new RealConnectionPool(i, j, timeUnit);
    }
}
