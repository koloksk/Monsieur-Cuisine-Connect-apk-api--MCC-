package defpackage;

import okhttp3.internal.NamedRunnable;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Http2Connection;

/* loaded from: classes.dex */
public class on extends NamedRunnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ ErrorCode b;
    public final /* synthetic */ Http2Connection c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public on(Http2Connection http2Connection, String str, Object[] objArr, int i, ErrorCode errorCode) {
        super(str, objArr);
        this.c = http2Connection;
        this.a = i;
        this.b = errorCode;
    }

    @Override // okhttp3.internal.NamedRunnable
    public void execute() {
        this.c.j.onReset(this.a, this.b);
        synchronized (this.c) {
            this.c.x.remove(Integer.valueOf(this.a));
        }
    }
}
