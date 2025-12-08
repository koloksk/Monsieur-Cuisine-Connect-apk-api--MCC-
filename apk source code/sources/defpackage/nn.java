package defpackage;

import java.io.IOException;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Http2Connection;
import okio.Buffer;

/* loaded from: classes.dex */
public class nn extends NamedRunnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ Buffer b;
    public final /* synthetic */ int c;
    public final /* synthetic */ boolean d;
    public final /* synthetic */ Http2Connection e;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public nn(Http2Connection http2Connection, String str, Object[] objArr, int i, Buffer buffer, int i2, boolean z) {
        super(str, objArr);
        this.e = http2Connection;
        this.a = i;
        this.b = buffer;
        this.c = i2;
        this.d = z;
    }

    @Override // okhttp3.internal.NamedRunnable
    public void execute() {
        try {
            boolean zOnData = this.e.j.onData(this.a, this.b, this.c, this.d);
            if (zOnData) {
                this.e.v.a(this.a, ErrorCode.CANCEL);
            }
            if (zOnData || this.d) {
                synchronized (this.e) {
                    this.e.x.remove(Integer.valueOf(this.a));
                }
            }
        } catch (IOException unused) {
        }
    }
}
