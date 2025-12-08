package defpackage;

import java.io.IOException;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.RealConnection;
import okhttp3.internal.ws.RealWebSocket;
import okio.BufferedSink;
import okio.BufferedSource;

/* loaded from: classes.dex */
public class jn extends RealWebSocket.Streams {
    public final /* synthetic */ Exchange a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public jn(RealConnection realConnection, boolean z, BufferedSource bufferedSource, BufferedSink bufferedSink, Exchange exchange) {
        super(z, bufferedSource, bufferedSink);
        this.a = exchange;
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.a.a(-1L, true, true, null);
    }
}
