package defpackage;

import android.app.Notification;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.graphics.Bitmap;
import android.widget.RemoteViews;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;

/* loaded from: classes.dex */
public abstract class md extends vc<c> {
    public final RemoteViews m;
    public final int n;
    public Callback o;
    public c p;

    public static class a extends md {
        public final int[] q;

        public a(Picasso picasso, Request request, RemoteViews remoteViews, int i, int[] iArr, int i2, int i3, String str, Object obj, int i4, Callback callback) {
            super(picasso, request, remoteViews, i, i4, i2, i3, obj, str, callback);
            this.q = iArr;
        }

        @Override // defpackage.vc
        public c b() {
            if (this.p == null) {
                this.p = new c(this.m, this.n);
            }
            return this.p;
        }

        @Override // defpackage.md
        public void c() {
            AppWidgetManager.getInstance(this.a.e).updateAppWidget(this.q, this.m);
        }
    }

    public static class b extends md {
        public final int q;
        public final String r;
        public final Notification s;

        public b(Picasso picasso, Request request, RemoteViews remoteViews, int i, int i2, Notification notification, String str, int i3, int i4, String str2, Object obj, int i5, Callback callback) {
            super(picasso, request, remoteViews, i, i5, i3, i4, obj, str2, callback);
            this.q = i2;
            this.r = str;
            this.s = notification;
        }

        @Override // defpackage.vc
        public c b() {
            if (this.p == null) {
                this.p = new c(this.m, this.n);
            }
            return this.p;
        }

        @Override // defpackage.md
        public void c() {
            ((NotificationManager) qd.a(this.a.e, "notification")).notify(this.r, this.q, this.s);
        }
    }

    public static class c {
        public final RemoteViews a;
        public final int b;

        public c(RemoteViews remoteViews, int i) {
            this.a = remoteViews;
            this.b = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || c.class != obj.getClass()) {
                return false;
            }
            c cVar = (c) obj;
            return this.b == cVar.b && this.a.equals(cVar.a);
        }

        public int hashCode() {
            return (this.a.hashCode() * 31) + this.b;
        }
    }

    public md(Picasso picasso, Request request, RemoteViews remoteViews, int i, int i2, int i3, int i4, Object obj, String str, Callback callback) {
        super(picasso, null, request, i3, i4, i2, null, str, obj, false);
        this.m = remoteViews;
        this.n = i;
        this.o = callback;
    }

    @Override // defpackage.vc
    public void a(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
        this.m.setImageViewBitmap(this.n, bitmap);
        c();
        Callback callback = this.o;
        if (callback != null) {
            callback.onSuccess();
        }
    }

    public abstract void c();

    @Override // defpackage.vc
    public void a(Exception exc) {
        int i = this.g;
        if (i != 0) {
            this.m.setImageViewResource(this.n, i);
            c();
        }
        Callback callback = this.o;
        if (callback != null) {
            callback.onError(exc);
        }
    }

    @Override // defpackage.vc
    public void a() {
        this.l = true;
        if (this.o != null) {
            this.o = null;
        }
    }
}
