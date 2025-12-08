package com.squareup.picasso;

import android.app.Notification;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import defpackage.ad;
import defpackage.dd;
import defpackage.fd;
import defpackage.g9;
import defpackage.gd;
import defpackage.kd;
import defpackage.md;
import defpackage.pd;
import defpackage.qd;
import defpackage.vc;
import defpackage.xc;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/* loaded from: classes.dex */
public class RequestCreator {
    public static final AtomicInteger m = new AtomicInteger();
    public final Picasso a;
    public final Request.Builder b;
    public boolean c;
    public boolean d;
    public boolean e;
    public int f;
    public int g;
    public int h;
    public int i;
    public Drawable j;
    public Drawable k;
    public Object l;

    public RequestCreator(Picasso picasso, Uri uri, int i) {
        this.e = true;
        if (picasso.o) {
            throw new IllegalStateException("Picasso instance already shut down. Cannot submit new requests.");
        }
        this.a = picasso;
        this.b = new Request.Builder(uri, i, picasso.l);
    }

    public final Drawable a() {
        int i = this.f;
        return i != 0 ? this.a.e.getDrawable(i) : this.j;
    }

    public RequestCreator centerCrop() {
        this.b.centerCrop(17);
        return this;
    }

    public RequestCreator centerInside() {
        this.b.centerInside();
        return this;
    }

    public RequestCreator config(@NonNull Bitmap.Config config) {
        this.b.config(config);
        return this;
    }

    public RequestCreator error(@DrawableRes int i) {
        if (i == 0) {
            throw new IllegalArgumentException("Error image resource invalid.");
        }
        if (this.k != null) {
            throw new IllegalStateException("Error image already set.");
        }
        this.g = i;
        return this;
    }

    public void fetch() {
        fetch(null);
    }

    public RequestCreator fit() {
        this.d = true;
        return this;
    }

    public Bitmap get() throws IOException {
        long jNanoTime = System.nanoTime();
        if (qd.b()) {
            throw new IllegalStateException("Method call should not happen from the main thread.");
        }
        if (this.d) {
            throw new IllegalStateException("Fit cannot be used with get.");
        }
        if (!this.b.a()) {
            return null;
        }
        Request requestA = a(jNanoTime);
        fd fdVar = new fd(this.a, requestA, this.h, this.i, this.l, qd.a(requestA, new StringBuilder()));
        Picasso picasso = this.a;
        return xc.a(picasso, picasso.f, picasso.g, picasso.h, fdVar).b();
    }

    public void into(@NonNull Target target) {
        Bitmap bitmapA;
        long jNanoTime = System.nanoTime();
        qd.a();
        if (target == null) {
            throw new IllegalArgumentException("Target must not be null.");
        }
        if (this.d) {
            throw new IllegalStateException("Fit cannot be used with a Target.");
        }
        if (!this.b.a()) {
            this.a.cancelRequest(target);
            target.onPrepareLoad(this.e ? a() : null);
            return;
        }
        Request requestA = a(jNanoTime);
        String strA = qd.a(requestA, qd.a);
        qd.a.setLength(0);
        if (!MemoryPolicy.a(this.h) || (bitmapA = this.a.a(strA)) == null) {
            target.onPrepareLoad(this.e ? a() : null);
            this.a.a((vc) new pd(this.a, target, requestA, this.h, this.i, this.k, strA, this.l, this.g));
        } else {
            this.a.cancelRequest(target);
            target.onBitmapLoaded(bitmapA, Picasso.LoadedFrom.MEMORY);
        }
    }

    public RequestCreator memoryPolicy(@NonNull MemoryPolicy memoryPolicy, @NonNull MemoryPolicy... memoryPolicyArr) {
        if (memoryPolicy == null) {
            throw new IllegalArgumentException("Memory policy cannot be null.");
        }
        this.h = memoryPolicy.a | this.h;
        if (memoryPolicyArr == null) {
            throw new IllegalArgumentException("Memory policy cannot be null.");
        }
        if (memoryPolicyArr.length > 0) {
            for (MemoryPolicy memoryPolicy2 : memoryPolicyArr) {
                if (memoryPolicy2 == null) {
                    throw new IllegalArgumentException("Memory policy cannot be null.");
                }
                this.h = memoryPolicy2.a | this.h;
            }
        }
        return this;
    }

    public RequestCreator networkPolicy(@NonNull NetworkPolicy networkPolicy, @NonNull NetworkPolicy... networkPolicyArr) {
        if (networkPolicy == null) {
            throw new IllegalArgumentException("Network policy cannot be null.");
        }
        this.i = networkPolicy.a | this.i;
        if (networkPolicyArr == null) {
            throw new IllegalArgumentException("Network policy cannot be null.");
        }
        if (networkPolicyArr.length > 0) {
            for (NetworkPolicy networkPolicy2 : networkPolicyArr) {
                if (networkPolicy2 == null) {
                    throw new IllegalArgumentException("Network policy cannot be null.");
                }
                this.i = networkPolicy2.a | this.i;
            }
        }
        return this;
    }

    public RequestCreator noFade() {
        this.c = true;
        return this;
    }

    public RequestCreator noPlaceholder() {
        if (this.f != 0) {
            throw new IllegalStateException("Placeholder resource already set.");
        }
        if (this.j != null) {
            throw new IllegalStateException("Placeholder image already set.");
        }
        this.e = false;
        return this;
    }

    public RequestCreator onlyScaleDown() {
        this.b.onlyScaleDown();
        return this;
    }

    public RequestCreator placeholder(@DrawableRes int i) {
        if (!this.e) {
            throw new IllegalStateException("Already explicitly declared as no placeholder.");
        }
        if (i == 0) {
            throw new IllegalArgumentException("Placeholder image resource invalid.");
        }
        if (this.j != null) {
            throw new IllegalStateException("Placeholder image already set.");
        }
        this.f = i;
        return this;
    }

    public RequestCreator priority(@NonNull Picasso.Priority priority) {
        this.b.priority(priority);
        return this;
    }

    public RequestCreator purgeable() {
        this.b.purgeable();
        return this;
    }

    public RequestCreator resize(int i, int i2) {
        this.b.resize(i, i2);
        return this;
    }

    public RequestCreator resizeDimen(int i, int i2) {
        Resources resources = this.a.e.getResources();
        return resize(resources.getDimensionPixelSize(i), resources.getDimensionPixelSize(i2));
    }

    public RequestCreator rotate(float f) {
        this.b.rotate(f);
        return this;
    }

    public RequestCreator stableKey(@NonNull String str) {
        this.b.stableKey(str);
        return this;
    }

    public RequestCreator tag(@NonNull Object obj) {
        if (obj == null) {
            throw new IllegalArgumentException("Tag invalid.");
        }
        if (this.l != null) {
            throw new IllegalStateException("Tag already set.");
        }
        this.l = obj;
        return this;
    }

    public RequestCreator transform(@NonNull Transformation transformation) {
        this.b.transform(transformation);
        return this;
    }

    public RequestCreator centerCrop(int i) {
        this.b.centerCrop(i);
        return this;
    }

    public void fetch(@Nullable Callback callback) {
        long jNanoTime = System.nanoTime();
        if (this.d) {
            throw new IllegalStateException("Fit cannot be used with fetch.");
        }
        if (this.b.a()) {
            if (!(this.b.q != null)) {
                this.b.priority(Picasso.Priority.LOW);
            }
            Request requestA = a(jNanoTime);
            String strA = qd.a(requestA, new StringBuilder());
            if (!MemoryPolicy.a(this.h) || this.a.a(strA) == null) {
                dd ddVar = new dd(this.a, requestA, this.h, this.i, this.l, strA, callback);
                Handler handler = this.a.f.i;
                handler.sendMessage(handler.obtainMessage(1, ddVar));
                return;
            }
            if (this.a.n) {
                String strC = requestA.c();
                StringBuilder sbA = g9.a("from ");
                sbA.append(Picasso.LoadedFrom.MEMORY);
                qd.a("Main", "completed", strC, sbA.toString());
            }
            if (callback != null) {
                callback.onSuccess();
            }
        }
    }

    public RequestCreator rotate(float f, float f2, float f3) {
        this.b.rotate(f, f2, f3);
        return this;
    }

    public RequestCreator transform(@NonNull List<? extends Transformation> list) {
        this.b.transform(list);
        return this;
    }

    public final Request a(long j) {
        int andIncrement = m.getAndIncrement();
        Request requestBuild = this.b.build();
        requestBuild.a = andIncrement;
        requestBuild.b = j;
        boolean z = this.a.n;
        if (z) {
            qd.a("Main", "created", requestBuild.c(), requestBuild.toString());
        }
        Picasso picasso = this.a;
        Request requestTransformRequest = picasso.b.transformRequest(requestBuild);
        if (requestTransformRequest == null) {
            StringBuilder sbA = g9.a("Request transformer ");
            sbA.append(picasso.b.getClass().getCanonicalName());
            sbA.append(" returned null for ");
            sbA.append(requestBuild);
            throw new IllegalStateException(sbA.toString());
        }
        if (requestTransformRequest != requestBuild) {
            requestTransformRequest.a = andIncrement;
            requestTransformRequest.b = j;
            if (z) {
                qd.a("Main", "changed", requestTransformRequest.a(), "into " + requestTransformRequest);
            }
        }
        return requestTransformRequest;
    }

    public RequestCreator error(@NonNull Drawable drawable) {
        if (drawable != null) {
            if (this.g == 0) {
                this.k = drawable;
                return this;
            }
            throw new IllegalStateException("Error image already set.");
        }
        throw new IllegalArgumentException("Error image may not be null.");
    }

    @VisibleForTesting
    public RequestCreator() {
        this.e = true;
        this.a = null;
        this.b = new Request.Builder(null, 0, null);
    }

    public RequestCreator placeholder(@NonNull Drawable drawable) {
        if (this.e) {
            if (this.f == 0) {
                this.j = drawable;
                return this;
            }
            throw new IllegalStateException("Placeholder image already set.");
        }
        throw new IllegalStateException("Already explicitly declared as no placeholder.");
    }

    public final void a(md mdVar) {
        Bitmap bitmapA;
        if (MemoryPolicy.a(this.h) && (bitmapA = this.a.a(mdVar.i)) != null) {
            Picasso.LoadedFrom loadedFrom = Picasso.LoadedFrom.MEMORY;
            mdVar.m.setImageViewBitmap(mdVar.n, bitmapA);
            mdVar.c();
            Callback callback = mdVar.o;
            if (callback != null) {
                callback.onSuccess();
                return;
            }
            return;
        }
        int i = this.f;
        if (i != 0) {
            mdVar.m.setImageViewResource(mdVar.n, i);
            mdVar.c();
        }
        this.a.a((vc) mdVar);
    }

    public void into(@NonNull RemoteViews remoteViews, @IdRes int i, int i2, @NonNull Notification notification) {
        into(remoteViews, i, i2, notification, null);
    }

    public void into(@NonNull RemoteViews remoteViews, @IdRes int i, int i2, @NonNull Notification notification, @Nullable String str) {
        into(remoteViews, i, i2, notification, str, null);
    }

    public void into(@NonNull RemoteViews remoteViews, @IdRes int i, int i2, @NonNull Notification notification, @Nullable String str, Callback callback) {
        long jNanoTime = System.nanoTime();
        if (remoteViews == null) {
            throw new IllegalArgumentException("RemoteViews must not be null.");
        }
        if (notification != null) {
            if (!this.d) {
                if (this.j == null && this.f == 0 && this.k == null) {
                    Request requestA = a(jNanoTime);
                    a(new md.b(this.a, requestA, remoteViews, i, i2, notification, str, this.h, this.i, qd.a(requestA, new StringBuilder()), this.l, this.g, callback));
                    return;
                }
                throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
            }
            throw new IllegalStateException("Fit cannot be used with RemoteViews.");
        }
        throw new IllegalArgumentException("Notification must not be null.");
    }

    public void into(@NonNull RemoteViews remoteViews, @IdRes int i, @NonNull int[] iArr) {
        into(remoteViews, i, iArr, (Callback) null);
    }

    public void into(@NonNull RemoteViews remoteViews, @IdRes int i, @NonNull int[] iArr, Callback callback) {
        long jNanoTime = System.nanoTime();
        if (remoteViews == null) {
            throw new IllegalArgumentException("remoteViews must not be null.");
        }
        if (iArr != null) {
            if (!this.d) {
                if (this.j == null && this.f == 0 && this.k == null) {
                    Request requestA = a(jNanoTime);
                    a(new md.a(this.a, requestA, remoteViews, i, iArr, this.h, this.i, qd.a(requestA, new StringBuilder()), this.l, this.g, callback));
                    return;
                }
                throw new IllegalArgumentException("Cannot use placeholder or error drawables with remote views.");
            }
            throw new IllegalStateException("Fit cannot be used with remote views.");
        }
        throw new IllegalArgumentException("appWidgetIds must not be null.");
    }

    public void into(ImageView imageView) {
        into(imageView, null);
    }

    public void into(ImageView imageView, Callback callback) {
        Bitmap bitmapA;
        long jNanoTime = System.nanoTime();
        qd.a();
        if (imageView != null) {
            if (!this.b.a()) {
                this.a.cancelRequest(imageView);
                if (this.e) {
                    kd.a(imageView, a());
                    return;
                }
                return;
            }
            if (this.d) {
                Request.Builder builder = this.b;
                if (!((builder.d == 0 && builder.e == 0) ? false : true)) {
                    int width = imageView.getWidth();
                    int height = imageView.getHeight();
                    if (width != 0 && height != 0) {
                        this.b.resize(width, height);
                    } else {
                        if (this.e) {
                            kd.a(imageView, a());
                        }
                        Picasso picasso = this.a;
                        ad adVar = new ad(this, imageView, callback);
                        if (picasso.j.containsKey(imageView)) {
                            picasso.a(imageView);
                        }
                        picasso.j.put(imageView, adVar);
                        return;
                    }
                } else {
                    throw new IllegalStateException("Fit cannot be used with resize.");
                }
            }
            Request requestA = a(jNanoTime);
            String strA = qd.a(requestA, qd.a);
            qd.a.setLength(0);
            if (MemoryPolicy.a(this.h) && (bitmapA = this.a.a(strA)) != null) {
                this.a.cancelRequest(imageView);
                Picasso picasso2 = this.a;
                kd.a(imageView, picasso2.e, bitmapA, Picasso.LoadedFrom.MEMORY, this.c, picasso2.m);
                if (this.a.n) {
                    String strC = requestA.c();
                    StringBuilder sbA = g9.a("from ");
                    sbA.append(Picasso.LoadedFrom.MEMORY);
                    qd.a("Main", "completed", strC, sbA.toString());
                }
                if (callback != null) {
                    callback.onSuccess();
                    return;
                }
                return;
            }
            if (this.e) {
                kd.a(imageView, a());
            }
            this.a.a((vc) new gd(this.a, imageView, requestA, this.h, this.i, this.g, this.k, strA, this.l, callback, this.c));
            return;
        }
        throw new IllegalArgumentException("Target must not be null.");
    }
}
