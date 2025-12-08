package defpackage;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Handler;
import com.squareup.picasso.Cache;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;
import com.squareup.picasso.Transformation;
import defpackage.jd;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;
import okio.BufferedSource;
import okio.Okio;
import okio.Source;

/* loaded from: classes.dex */
public class xc implements Runnable {
    public static final Object t = new Object();
    public static final ThreadLocal<StringBuilder> u = new a();
    public static final AtomicInteger v = new AtomicInteger();
    public static final RequestHandler w = new b();
    public final int a = v.incrementAndGet();
    public final Picasso b;
    public final cd c;
    public final Cache d;
    public final od e;
    public final String f;
    public final Request g;
    public final int h;
    public int i;
    public final RequestHandler j;
    public vc k;
    public List<vc> l;
    public Bitmap m;
    public Future<?> n;
    public Picasso.LoadedFrom o;
    public Exception p;
    public int q;
    public int r;
    public Picasso.Priority s;

    public static class a extends ThreadLocal<StringBuilder> {
        @Override // java.lang.ThreadLocal
        public StringBuilder initialValue() {
            return new StringBuilder("Picasso-");
        }
    }

    public static class b extends RequestHandler {
        @Override // com.squareup.picasso.RequestHandler
        public boolean canHandleRequest(Request request) {
            return true;
        }

        @Override // com.squareup.picasso.RequestHandler
        public RequestHandler.Result load(Request request, int i) throws IOException {
            throw new IllegalStateException("Unrecognized type of request: " + request);
        }
    }

    public static class c implements Runnable {
        public final /* synthetic */ Transformation a;
        public final /* synthetic */ RuntimeException b;

        public c(Transformation transformation, RuntimeException runtimeException) {
            this.a = transformation;
            this.b = runtimeException;
        }

        @Override // java.lang.Runnable
        public void run() {
            StringBuilder sbA = g9.a("Transformation ");
            sbA.append(this.a.key());
            sbA.append(" crashed with exception.");
            throw new RuntimeException(sbA.toString(), this.b);
        }
    }

    public static class d implements Runnable {
        public final /* synthetic */ StringBuilder a;

        public d(StringBuilder sb) {
            this.a = sb;
        }

        @Override // java.lang.Runnable
        public void run() {
            throw new NullPointerException(this.a.toString());
        }
    }

    public static class e implements Runnable {
        public final /* synthetic */ Transformation a;

        public e(Transformation transformation) {
            this.a = transformation;
        }

        @Override // java.lang.Runnable
        public void run() {
            StringBuilder sbA = g9.a("Transformation ");
            sbA.append(this.a.key());
            sbA.append(" returned input Bitmap but recycled it.");
            throw new IllegalStateException(sbA.toString());
        }
    }

    public static class f implements Runnable {
        public final /* synthetic */ Transformation a;

        public f(Transformation transformation) {
            this.a = transformation;
        }

        @Override // java.lang.Runnable
        public void run() {
            StringBuilder sbA = g9.a("Transformation ");
            sbA.append(this.a.key());
            sbA.append(" mutated input Bitmap but failed to recycle the original.");
            throw new IllegalStateException(sbA.toString());
        }
    }

    public xc(Picasso picasso, cd cdVar, Cache cache, od odVar, vc vcVar, RequestHandler requestHandler) {
        this.b = picasso;
        this.c = cdVar;
        this.d = cache;
        this.e = odVar;
        this.k = vcVar;
        this.f = vcVar.i;
        Request request = vcVar.b;
        this.g = request;
        this.s = request.priority;
        this.h = vcVar.e;
        this.i = vcVar.f;
        this.j = requestHandler;
        this.r = requestHandler.a();
    }

    public static xc a(Picasso picasso, cd cdVar, Cache cache, od odVar, vc vcVar) {
        Request request = vcVar.b;
        List<RequestHandler> list = picasso.d;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            RequestHandler requestHandler = list.get(i);
            if (requestHandler.canHandleRequest(request)) {
                return new xc(picasso, cdVar, cache, odVar, vcVar, requestHandler);
            }
        }
        return new xc(picasso, cdVar, cache, odVar, vcVar, w);
    }

    public static boolean a(boolean z, int i, int i2, int i3, int i4) {
        return !z || (i3 != 0 && i > i3) || (i4 != 0 && i2 > i4);
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x00af  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00b9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.graphics.Bitmap b() throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 293
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.xc.b():android.graphics.Bitmap");
    }

    @Override // java.lang.Runnable
    public void run() {
        try {
            try {
                try {
                    a(this.g);
                    if (this.b.n) {
                        qd.a("Hunter", "executing", qd.a(this), "");
                    }
                    Bitmap bitmapB = b();
                    this.m = bitmapB;
                    if (bitmapB == null) {
                        this.c.c(this);
                    } else {
                        this.c.b(this);
                    }
                } catch (IOException e2) {
                    this.p = e2;
                    Handler handler = this.c.i;
                    handler.sendMessageDelayed(handler.obtainMessage(5, this), 500L);
                } catch (Exception e3) {
                    this.p = e3;
                    Handler handler2 = this.c.i;
                    handler2.sendMessage(handler2.obtainMessage(6, this));
                }
            } catch (OutOfMemoryError e4) {
                StringWriter stringWriter = new StringWriter();
                this.e.a().dump(new PrintWriter(stringWriter));
                this.p = new RuntimeException(stringWriter.toString(), e4);
                Handler handler3 = this.c.i;
                handler3.sendMessage(handler3.obtainMessage(6, this));
            } catch (jd.b e5) {
                if (!NetworkPolicy.isOfflineOnly(e5.b) || e5.a != 504) {
                    this.p = e5;
                }
                Handler handler4 = this.c.i;
                handler4.sendMessage(handler4.obtainMessage(6, this));
            }
        } finally {
            Thread.currentThread().setName("Picasso-Idle");
        }
    }

    public static Bitmap a(Source source, Request request) throws IOException {
        BufferedSource bufferedSourceBuffer = Okio.buffer(source);
        boolean z = bufferedSourceBuffer.rangeEquals(0L, qd.b) && bufferedSourceBuffer.rangeEquals(8L, qd.c);
        boolean z2 = request.purgeable;
        BitmapFactory.Options optionsA = RequestHandler.a(request);
        boolean z3 = optionsA != null && optionsA.inJustDecodeBounds;
        if (!z) {
            InputStream inputStream = bufferedSourceBuffer.inputStream();
            if (z3) {
                hd hdVar = new hd(inputStream);
                hdVar.f = false;
                long j = hdVar.b + 1024;
                if (hdVar.d < j) {
                    hdVar.b(j);
                }
                long j2 = hdVar.b;
                BitmapFactory.decodeStream(hdVar, null, optionsA);
                RequestHandler.a(request.targetWidth, request.targetHeight, optionsA, request);
                hdVar.a(j2);
                hdVar.f = true;
                inputStream = hdVar;
            }
            Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStream, null, optionsA);
            if (bitmapDecodeStream != null) {
                return bitmapDecodeStream;
            }
            throw new IOException("Failed to decode stream.");
        }
        byte[] byteArray = bufferedSourceBuffer.readByteArray();
        if (z3) {
            BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, optionsA);
            RequestHandler.a(request.targetWidth, request.targetHeight, optionsA, request);
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, optionsA);
    }

    public static void a(Request request) {
        String hexString;
        Uri uri = request.uri;
        if (uri != null) {
            hexString = String.valueOf(uri.getPath());
        } else {
            hexString = Integer.toHexString(request.resourceId);
        }
        StringBuilder sb = u.get();
        sb.ensureCapacity(hexString.length() + 8);
        sb.replace(8, sb.length(), hexString);
        Thread.currentThread().setName(sb.toString());
    }

    public void a(vc vcVar) {
        boolean zRemove;
        boolean z = true;
        if (this.k == vcVar) {
            this.k = null;
            zRemove = true;
        } else {
            List<vc> list = this.l;
            zRemove = list != null ? list.remove(vcVar) : false;
        }
        if (zRemove && vcVar.b.priority == this.s) {
            Picasso.Priority priority = Picasso.Priority.LOW;
            List<vc> list2 = this.l;
            boolean z2 = (list2 == null || list2.isEmpty()) ? false : true;
            if (this.k == null && !z2) {
                z = false;
            }
            if (z) {
                vc vcVar2 = this.k;
                if (vcVar2 != null) {
                    priority = vcVar2.b.priority;
                }
                if (z2) {
                    int size = this.l.size();
                    for (int i = 0; i < size; i++) {
                        Picasso.Priority priority2 = this.l.get(i).b.priority;
                        if (priority2.ordinal() > priority.ordinal()) {
                            priority = priority2;
                        }
                    }
                }
            }
            this.s = priority;
        }
        if (this.b.n) {
            qd.a("Hunter", "removed", vcVar.b.a(), qd.a(this, "from "));
        }
    }

    public boolean a() {
        Future<?> future;
        if (this.k != null) {
            return false;
        }
        List<vc> list = this.l;
        return (list == null || list.isEmpty()) && (future = this.n) != null && future.cancel(false);
    }

    public static Bitmap a(List<Transformation> list, Bitmap bitmap) {
        int size = list.size();
        int i = 0;
        while (i < size) {
            Transformation transformation = list.get(i);
            try {
                Bitmap bitmapTransform = transformation.transform(bitmap);
                if (bitmapTransform == null) {
                    StringBuilder sbA = g9.a("Transformation ");
                    sbA.append(transformation.key());
                    sbA.append(" returned null after ");
                    sbA.append(i);
                    sbA.append(" previous transformation(s).\n\nTransformation list:\n");
                    Iterator<Transformation> it = list.iterator();
                    while (it.hasNext()) {
                        sbA.append(it.next().key());
                        sbA.append('\n');
                    }
                    Picasso.p.post(new d(sbA));
                    return null;
                }
                if (bitmapTransform == bitmap && bitmap.isRecycled()) {
                    Picasso.p.post(new e(transformation));
                    return null;
                }
                if (bitmapTransform != bitmap && !bitmap.isRecycled()) {
                    Picasso.p.post(new f(transformation));
                    return null;
                }
                i++;
                bitmap = bitmapTransform;
            } catch (RuntimeException e2) {
                Picasso.p.post(new c(transformation, e2));
                return null;
            }
        }
        return bitmap;
    }

    /* JADX WARN: Removed duplicated region for block: B:108:0x025a  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x025e  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01f7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static android.graphics.Bitmap a(com.squareup.picasso.Request r27, android.graphics.Bitmap r28, int r29) {
        /*
            Method dump skipped, instructions count: 624
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.xc.a(com.squareup.picasso.Request, android.graphics.Bitmap, int):android.graphics.Bitmap");
    }
}
