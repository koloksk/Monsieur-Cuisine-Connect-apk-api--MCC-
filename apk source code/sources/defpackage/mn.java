package defpackage;

import java.io.IOException;
import java.util.List;
import okhttp3.internal.NamedRunnable;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Http2Connection;

/* loaded from: classes.dex */
public class mn extends NamedRunnable {
    public final /* synthetic */ int a;
    public final /* synthetic */ List b;
    public final /* synthetic */ boolean c;
    public final /* synthetic */ Http2Connection d;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public mn(Http2Connection http2Connection, String str, Object[] objArr, int i, List list, boolean z) {
        super(str, objArr);
        this.d = http2Connection;
        this.a = i;
        this.b = list;
        this.c = z;
    }

    @Override // okhttp3.internal.NamedRunnable
    public void execute() {
        boolean zOnHeaders = this.d.j.onHeaders(this.a, this.b, this.c);
        if (zOnHeaders) {
            try {
                this.d.v.a(this.a, ErrorCode.CANCEL);
            } catch (IOException unused) {
                return;
            }
        }
        if (zOnHeaders || this.c) {
            synchronized (this.d) {
                this.d.x.remove(Integer.valueOf(this.a));
            }
        }
    }
}
