package defpackage;

import java.io.IOException;
import okhttp3.internal.cache.DiskLruCache;
import okio.Sink;

/* loaded from: classes.dex */
public class en extends fn {
    public final /* synthetic */ DiskLruCache c;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public en(DiskLruCache diskLruCache, Sink sink) {
        super(sink);
        this.c = diskLruCache;
    }

    @Override // defpackage.fn
    public void a(IOException iOException) {
        this.c.m = true;
    }
}
