package com.squareup.picasso;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.internal.view.SupportMenu;
import android.widget.ImageView;
import android.widget.RemoteViews;
import defpackage.ad;
import defpackage.bd;
import defpackage.cd;
import defpackage.ed;
import defpackage.g9;
import defpackage.id;
import defpackage.jd;
import defpackage.ld;
import defpackage.md;
import defpackage.nd;
import defpackage.od;
import defpackage.qd;
import defpackage.vc;
import defpackage.wc;
import defpackage.xc;
import defpackage.yc;
import defpackage.zc;
import java.io.File;
import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;

/* loaded from: classes.dex */
public class Picasso {
    public static final Handler p = new a(Looper.getMainLooper());

    @SuppressLint({"StaticFieldLeak"})
    public static volatile Picasso q = null;
    public final Listener a;
    public final RequestTransformer b;
    public final b c;
    public final List<RequestHandler> d;
    public final Context e;
    public final cd f;
    public final Cache g;
    public final od h;
    public final Map<Object, vc> i;
    public final Map<ImageView, ad> j;
    public final ReferenceQueue<Object> k;
    public final Bitmap.Config l;
    public boolean m;
    public volatile boolean n;
    public boolean o;

    public static class Builder {
        public final Context a;
        public Downloader b;
        public ExecutorService c;
        public Cache d;
        public Listener e;
        public RequestTransformer f;
        public List<RequestHandler> g;
        public Bitmap.Config h;
        public boolean i;
        public boolean j;

        public Builder(@NonNull Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.a = context.getApplicationContext();
        }

        public Builder addRequestHandler(@NonNull RequestHandler requestHandler) {
            if (requestHandler == null) {
                throw new IllegalArgumentException("RequestHandler must not be null.");
            }
            if (this.g == null) {
                this.g = new ArrayList();
            }
            if (this.g.contains(requestHandler)) {
                throw new IllegalStateException("RequestHandler already registered.");
            }
            this.g.add(requestHandler);
            return this;
        }

        public Picasso build() {
            Context context = this.a;
            if (this.b == null) {
                this.b = new OkHttp3Downloader(context);
            }
            if (this.d == null) {
                this.d = new LruCache(context);
            }
            if (this.c == null) {
                this.c = new ld();
            }
            if (this.f == null) {
                this.f = RequestTransformer.IDENTITY;
            }
            od odVar = new od(this.d);
            return new Picasso(context, new cd(context, this.c, Picasso.p, this.b, this.d, odVar), this.d, this.e, this.f, this.g, odVar, this.h, this.i, this.j);
        }

        public Builder defaultBitmapConfig(@NonNull Bitmap.Config config) {
            if (config == null) {
                throw new IllegalArgumentException("Bitmap config must not be null.");
            }
            this.h = config;
            return this;
        }

        public Builder downloader(@NonNull Downloader downloader) {
            if (downloader == null) {
                throw new IllegalArgumentException("Downloader must not be null.");
            }
            if (this.b != null) {
                throw new IllegalStateException("Downloader already set.");
            }
            this.b = downloader;
            return this;
        }

        public Builder executor(@NonNull ExecutorService executorService) {
            if (executorService == null) {
                throw new IllegalArgumentException("Executor service must not be null.");
            }
            if (this.c != null) {
                throw new IllegalStateException("Executor service already set.");
            }
            this.c = executorService;
            return this;
        }

        public Builder indicatorsEnabled(boolean z) {
            this.i = z;
            return this;
        }

        public Builder listener(@NonNull Listener listener) {
            if (listener == null) {
                throw new IllegalArgumentException("Listener must not be null.");
            }
            if (this.e != null) {
                throw new IllegalStateException("Listener already set.");
            }
            this.e = listener;
            return this;
        }

        public Builder loggingEnabled(boolean z) {
            this.j = z;
            return this;
        }

        public Builder memoryCache(@NonNull Cache cache) {
            if (cache == null) {
                throw new IllegalArgumentException("Memory cache must not be null.");
            }
            if (this.d != null) {
                throw new IllegalStateException("Memory cache already set.");
            }
            this.d = cache;
            return this;
        }

        public Builder requestTransformer(@NonNull RequestTransformer requestTransformer) {
            if (requestTransformer == null) {
                throw new IllegalArgumentException("Transformer must not be null.");
            }
            if (this.f != null) {
                throw new IllegalStateException("Transformer already set.");
            }
            this.f = requestTransformer;
            return this;
        }
    }

    public interface Listener {
        void onImageLoadFailed(Picasso picasso, Uri uri, Exception exc);
    }

    public enum LoadedFrom {
        MEMORY(-16711936),
        DISK(-16776961),
        NETWORK(SupportMenu.CATEGORY_MASK);

        public final int a;

        LoadedFrom(int i) {
            this.a = i;
        }
    }

    public enum Priority {
        LOW,
        NORMAL,
        HIGH
    }

    public interface RequestTransformer {
        public static final RequestTransformer IDENTITY = new a();

        public static class a implements RequestTransformer {
            @Override // com.squareup.picasso.Picasso.RequestTransformer
            public Request transformRequest(Request request) {
                return request;
            }
        }

        Request transformRequest(Request request);
    }

    public static class a extends Handler {
        public a(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 3) {
                vc vcVar = (vc) message.obj;
                if (vcVar.a.n) {
                    qd.a("Main", "canceled", vcVar.b.a(), "target got garbage collected");
                }
                vcVar.a.a(vcVar.b());
                return;
            }
            if (i != 8) {
                if (i != 13) {
                    StringBuilder sbA = g9.a("Unknown handler message received: ");
                    sbA.append(message.what);
                    throw new AssertionError(sbA.toString());
                }
                List list = (List) message.obj;
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    vc vcVar2 = (vc) list.get(i2);
                    Picasso picasso = vcVar2.a;
                    if (picasso == null) {
                        throw null;
                    }
                    Bitmap bitmapA = MemoryPolicy.a(vcVar2.e) ? picasso.a(vcVar2.i) : null;
                    if (bitmapA != null) {
                        picasso.a(bitmapA, LoadedFrom.MEMORY, vcVar2, null);
                        if (picasso.n) {
                            String strA = vcVar2.b.a();
                            StringBuilder sbA2 = g9.a("from ");
                            sbA2.append(LoadedFrom.MEMORY);
                            qd.a("Main", "completed", strA, sbA2.toString());
                        }
                    } else {
                        picasso.a(vcVar2);
                        if (picasso.n) {
                            qd.a("Main", "resumed", vcVar2.b.a(), "");
                        }
                    }
                }
                return;
            }
            List list2 = (List) message.obj;
            int size2 = list2.size();
            for (int i3 = 0; i3 < size2; i3++) {
                xc xcVar = (xc) list2.get(i3);
                Picasso picasso2 = xcVar.b;
                if (picasso2 == null) {
                    throw null;
                }
                vc vcVar3 = xcVar.k;
                List<vc> list3 = xcVar.l;
                boolean z = true;
                boolean z2 = (list3 == null || list3.isEmpty()) ? false : true;
                if (vcVar3 == null && !z2) {
                    z = false;
                }
                if (z) {
                    Uri uri = xcVar.g.uri;
                    Exception exc = xcVar.p;
                    Bitmap bitmap = xcVar.m;
                    LoadedFrom loadedFrom = xcVar.o;
                    if (vcVar3 != null) {
                        picasso2.a(bitmap, loadedFrom, vcVar3, exc);
                    }
                    if (z2) {
                        int size3 = list3.size();
                        for (int i4 = 0; i4 < size3; i4++) {
                            picasso2.a(bitmap, loadedFrom, list3.get(i4), exc);
                        }
                    }
                    Listener listener = picasso2.a;
                    if (listener != null && exc != null) {
                        listener.onImageLoadFailed(picasso2, uri, exc);
                    }
                }
            }
        }
    }

    public static class b extends Thread {
        public final ReferenceQueue<Object> a;
        public final Handler b;

        public class a implements Runnable {
            public final /* synthetic */ Exception a;

            public a(b bVar, Exception exc) {
                this.a = exc;
            }

            @Override // java.lang.Runnable
            public void run() {
                throw new RuntimeException(this.a);
            }
        }

        public b(ReferenceQueue<Object> referenceQueue, Handler handler) {
            this.a = referenceQueue;
            this.b = handler;
            setDaemon(true);
            setName("Picasso-refQueue");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws SecurityException, IllegalArgumentException {
            Process.setThreadPriority(10);
            while (true) {
                try {
                    vc.a aVar = (vc.a) this.a.remove(1000L);
                    Message messageObtainMessage = this.b.obtainMessage();
                    if (aVar != null) {
                        messageObtainMessage.what = 3;
                        messageObtainMessage.obj = aVar.a;
                        this.b.sendMessage(messageObtainMessage);
                    } else {
                        messageObtainMessage.recycle();
                    }
                } catch (InterruptedException unused) {
                    return;
                } catch (Exception e) {
                    this.b.post(new a(this, e));
                    return;
                }
            }
        }
    }

    public Picasso(Context context, cd cdVar, Cache cache, Listener listener, RequestTransformer requestTransformer, List<RequestHandler> list, od odVar, Bitmap.Config config, boolean z, boolean z2) {
        this.e = context;
        this.f = cdVar;
        this.g = cache;
        this.a = listener;
        this.b = requestTransformer;
        this.l = config;
        ArrayList arrayList = new ArrayList((list != null ? list.size() : 0) + 7);
        arrayList.add(new nd(context));
        if (list != null) {
            arrayList.addAll(list);
        }
        arrayList.add(new yc(context));
        arrayList.add(new id(context));
        arrayList.add(new zc(context));
        arrayList.add(new wc(context));
        arrayList.add(new ed(context));
        arrayList.add(new jd(cdVar.d, odVar));
        this.d = Collections.unmodifiableList(arrayList);
        this.h = odVar;
        this.i = new WeakHashMap();
        this.j = new WeakHashMap();
        this.m = z;
        this.n = z2;
        this.k = new ReferenceQueue<>();
        b bVar = new b(this.k, p);
        this.c = bVar;
        bVar.start();
    }

    public static Picasso get() {
        if (q == null) {
            synchronized (Picasso.class) {
                if (q == null) {
                    if (PicassoProvider.a == null) {
                        throw new IllegalStateException("context == null");
                    }
                    q = new Builder(PicassoProvider.a).build();
                }
            }
        }
        return q;
    }

    public static void setSingletonInstance(@NonNull Picasso picasso) {
        if (picasso == null) {
            throw new IllegalArgumentException("Picasso must not be null.");
        }
        synchronized (Picasso.class) {
            if (q != null) {
                throw new IllegalStateException("Singleton instance already exists.");
            }
            q = picasso;
        }
    }

    public final void a(Bitmap bitmap, LoadedFrom loadedFrom, vc vcVar, Exception exc) {
        if (vcVar.l) {
            return;
        }
        if (!vcVar.k) {
            this.i.remove(vcVar.b());
        }
        if (bitmap == null) {
            vcVar.a(exc);
            if (this.n) {
                qd.a("Main", "errored", vcVar.b.a(), exc.getMessage());
                return;
            }
            return;
        }
        if (loadedFrom == null) {
            throw new AssertionError("LoadedFrom cannot be null.");
        }
        vcVar.a(bitmap, loadedFrom);
        if (this.n) {
            qd.a("Main", "completed", vcVar.b.a(), "from " + loadedFrom);
        }
    }

    public boolean areIndicatorsEnabled() {
        return this.m;
    }

    public void cancelRequest(@NonNull ImageView imageView) {
        if (imageView == null) {
            throw new IllegalArgumentException("view cannot be null.");
        }
        a(imageView);
    }

    public void cancelTag(@NonNull Object obj) {
        qd.a();
        if (obj == null) {
            throw new IllegalArgumentException("Cannot cancel requests with null tag.");
        }
        ArrayList arrayList = new ArrayList(this.i.values());
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            vc vcVar = (vc) arrayList.get(i);
            if (obj.equals(vcVar.j)) {
                a(vcVar.b());
            }
        }
        ArrayList arrayList2 = new ArrayList(this.j.values());
        int size2 = arrayList2.size();
        for (int i2 = 0; i2 < size2; i2++) {
            ad adVar = (ad) arrayList2.get(i2);
            if (obj.equals(adVar.a.l)) {
                adVar.a();
            }
        }
    }

    public StatsSnapshot getSnapshot() {
        return this.h.a();
    }

    public void invalidate(@Nullable Uri uri) {
        if (uri != null) {
            this.g.clearKeyUri(uri.toString());
        }
    }

    public boolean isLoggingEnabled() {
        return this.n;
    }

    public RequestCreator load(@Nullable Uri uri) {
        return new RequestCreator(this, uri, 0);
    }

    public void pauseTag(@NonNull Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("tag == null");
        }
        Handler handler = this.f.i;
        handler.sendMessage(handler.obtainMessage(11, obj));
    }

    public void resumeTag(@NonNull Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("tag == null");
        }
        Handler handler = this.f.i;
        handler.sendMessage(handler.obtainMessage(12, obj));
    }

    public void setIndicatorsEnabled(boolean z) {
        this.m = z;
    }

    public void setLoggingEnabled(boolean z) {
        this.n = z;
    }

    public void shutdown() {
        if (this == q) {
            throw new UnsupportedOperationException("Default singleton instance cannot be shutdown.");
        }
        if (this.o) {
            return;
        }
        this.g.clear();
        this.c.interrupt();
        this.h.a.quit();
        cd cdVar = this.f;
        ExecutorService executorService = cdVar.c;
        if (executorService instanceof ld) {
            executorService.shutdown();
        }
        cdVar.d.shutdown();
        cdVar.a.quit();
        p.post(new bd(cdVar));
        Iterator<ad> it = this.j.values().iterator();
        while (it.hasNext()) {
            it.next().a();
        }
        this.j.clear();
        this.o = true;
    }

    public void invalidate(@Nullable String str) {
        if (str != null) {
            invalidate(Uri.parse(str));
        }
    }

    public RequestCreator load(@Nullable String str) {
        if (str == null) {
            return new RequestCreator(this, null, 0);
        }
        if (str.trim().length() != 0) {
            return load(Uri.parse(str));
        }
        throw new IllegalArgumentException("Path must not be empty.");
    }

    public void cancelRequest(@NonNull Target target) {
        if (target != null) {
            a(target);
            return;
        }
        throw new IllegalArgumentException("target cannot be null.");
    }

    public void invalidate(@NonNull File file) {
        if (file != null) {
            invalidate(Uri.fromFile(file));
            return;
        }
        throw new IllegalArgumentException("file == null");
    }

    public void cancelRequest(@NonNull RemoteViews remoteViews, @IdRes int i) {
        if (remoteViews != null) {
            a(new md.c(remoteViews, i));
            return;
        }
        throw new IllegalArgumentException("remoteViews cannot be null.");
    }

    public RequestCreator load(@NonNull File file) {
        if (file == null) {
            return new RequestCreator(this, null, 0);
        }
        return load(Uri.fromFile(file));
    }

    public RequestCreator load(@DrawableRes int i) {
        if (i != 0) {
            return new RequestCreator(this, null, i);
        }
        throw new IllegalArgumentException("Resource ID must not be zero.");
    }

    public void a(vc vcVar) {
        Object objB = vcVar.b();
        if (objB != null && this.i.get(objB) != vcVar) {
            a(objB);
            this.i.put(objB, vcVar);
        }
        Handler handler = this.f.i;
        handler.sendMessage(handler.obtainMessage(1, vcVar));
    }

    public Bitmap a(String str) {
        Bitmap bitmap = this.g.get(str);
        if (bitmap != null) {
            this.h.c.sendEmptyMessage(0);
        } else {
            this.h.c.sendEmptyMessage(1);
        }
        return bitmap;
    }

    public void a(Object obj) {
        qd.a();
        vc vcVarRemove = this.i.remove(obj);
        if (vcVarRemove != null) {
            vcVarRemove.a();
            Handler handler = this.f.i;
            handler.sendMessage(handler.obtainMessage(2, vcVarRemove));
        }
        if (obj instanceof ImageView) {
            ad adVarRemove = this.j.remove((ImageView) obj);
            if (adVarRemove != null) {
                adVarRemove.a();
            }
        }
    }
}
