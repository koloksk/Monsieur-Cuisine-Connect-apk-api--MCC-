package defpackage;

import okhttp3.internal.NamedRunnable;
import okhttp3.internal.http2.Http2Connection;
import okhttp3.internal.http2.Settings;

/* loaded from: classes.dex */
public class pn extends NamedRunnable {
    public final /* synthetic */ boolean a;
    public final /* synthetic */ Settings b;
    public final /* synthetic */ Http2Connection.g c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public pn(Http2Connection.g gVar, String str, Object[] objArr, boolean z, Settings settings) {
        super(str, objArr);
        this.c = gVar;
        this.a = z;
        this.b = settings;
    }

    @Override // okhttp3.internal.NamedRunnable
    public void execute() {
        this.c.a(this.a, this.b);
    }
}
