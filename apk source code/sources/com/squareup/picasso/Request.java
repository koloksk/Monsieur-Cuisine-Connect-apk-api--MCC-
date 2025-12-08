package com.squareup.picasso;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.Px;
import com.squareup.picasso.Picasso;
import defpackage.g9;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public final class Request {
    public static final long d = TimeUnit.SECONDS.toNanos(5);
    public int a;
    public long b;
    public int c;
    public final boolean centerCrop;
    public final int centerCropGravity;
    public final boolean centerInside;
    public final Bitmap.Config config;
    public final boolean hasRotationPivot;
    public final boolean onlyScaleDown;
    public final Picasso.Priority priority;
    public final boolean purgeable;
    public final int resourceId;
    public final float rotationDegrees;
    public final float rotationPivotX;
    public final float rotationPivotY;
    public final String stableKey;
    public final int targetHeight;
    public final int targetWidth;
    public final List<Transformation> transformations;
    public final Uri uri;

    public static final class Builder {
        public Uri a;
        public int b;
        public String c;
        public int d;
        public int e;
        public boolean f;
        public int g;
        public boolean h;
        public boolean i;
        public float j;
        public float k;
        public float l;
        public boolean m;
        public boolean n;
        public List<Transformation> o;
        public Bitmap.Config p;
        public Picasso.Priority q;

        public Builder(@NonNull Uri uri) {
            setUri(uri);
        }

        public boolean a() {
            return (this.a == null && this.b == 0) ? false : true;
        }

        public Request build() {
            if (this.h && this.f) {
                throw new IllegalStateException("Center crop and center inside can not be used together.");
            }
            if (this.f && this.d == 0 && this.e == 0) {
                throw new IllegalStateException("Center crop requires calling resize with positive width and height.");
            }
            if (this.h && this.d == 0 && this.e == 0) {
                throw new IllegalStateException("Center inside requires calling resize with positive width and height.");
            }
            if (this.q == null) {
                this.q = Picasso.Priority.NORMAL;
            }
            return new Request(this.a, this.b, this.c, this.o, this.d, this.e, this.f, this.h, this.g, this.i, this.j, this.k, this.l, this.m, this.n, this.p, this.q, null);
        }

        public Builder centerCrop() {
            return centerCrop(17);
        }

        public Builder centerInside() {
            if (this.f) {
                throw new IllegalStateException("Center inside can not be used after calling centerCrop");
            }
            this.h = true;
            return this;
        }

        public Builder clearCenterCrop() {
            this.f = false;
            this.g = 17;
            return this;
        }

        public Builder clearCenterInside() {
            this.h = false;
            return this;
        }

        public Builder clearOnlyScaleDown() {
            this.i = false;
            return this;
        }

        public Builder clearResize() {
            this.d = 0;
            this.e = 0;
            this.f = false;
            this.h = false;
            return this;
        }

        public Builder clearRotation() {
            this.j = 0.0f;
            this.k = 0.0f;
            this.l = 0.0f;
            this.m = false;
            return this;
        }

        public Builder config(@NonNull Bitmap.Config config) {
            if (config == null) {
                throw new IllegalArgumentException("config == null");
            }
            this.p = config;
            return this;
        }

        public Builder onlyScaleDown() {
            if (this.e == 0 && this.d == 0) {
                throw new IllegalStateException("onlyScaleDown can not be applied without resize");
            }
            this.i = true;
            return this;
        }

        public Builder priority(@NonNull Picasso.Priority priority) {
            if (priority == null) {
                throw new IllegalArgumentException("Priority invalid.");
            }
            if (this.q != null) {
                throw new IllegalStateException("Priority already set.");
            }
            this.q = priority;
            return this;
        }

        public Builder purgeable() {
            this.n = true;
            return this;
        }

        public Builder resize(@Px int i, @Px int i2) {
            if (i < 0) {
                throw new IllegalArgumentException("Width must be positive number or 0.");
            }
            if (i2 < 0) {
                throw new IllegalArgumentException("Height must be positive number or 0.");
            }
            if (i2 == 0 && i == 0) {
                throw new IllegalArgumentException("At least one dimension has to be positive number.");
            }
            this.d = i;
            this.e = i2;
            return this;
        }

        public Builder rotate(float f) {
            this.j = f;
            return this;
        }

        public Builder setResourceId(@DrawableRes int i) {
            if (i == 0) {
                throw new IllegalArgumentException("Image resource ID may not be 0.");
            }
            this.b = i;
            this.a = null;
            return this;
        }

        public Builder setUri(@NonNull Uri uri) {
            if (uri == null) {
                throw new IllegalArgumentException("Image URI may not be null.");
            }
            this.a = uri;
            this.b = 0;
            return this;
        }

        public Builder stableKey(@Nullable String str) {
            this.c = str;
            return this;
        }

        public Builder transform(@NonNull Transformation transformation) {
            if (transformation == null) {
                throw new IllegalArgumentException("Transformation must not be null.");
            }
            if (transformation.key() == null) {
                throw new IllegalArgumentException("Transformation key must not be null.");
            }
            if (this.o == null) {
                this.o = new ArrayList(2);
            }
            this.o.add(transformation);
            return this;
        }

        public Builder centerCrop(int i) {
            if (this.h) {
                throw new IllegalStateException("Center crop can not be used after calling centerInside");
            }
            this.f = true;
            this.g = i;
            return this;
        }

        public Builder rotate(float f, float f2, float f3) {
            this.j = f;
            this.k = f2;
            this.l = f3;
            this.m = true;
            return this;
        }

        public Builder(@DrawableRes int i) {
            setResourceId(i);
        }

        public Builder(Uri uri, int i, Bitmap.Config config) {
            this.a = uri;
            this.b = i;
            this.p = config;
        }

        public Builder transform(@NonNull List<? extends Transformation> list) {
            if (list != null) {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    transform(list.get(i));
                }
                return this;
            }
            throw new IllegalArgumentException("Transformation list must not be null.");
        }

        public /* synthetic */ Builder(Request request, a aVar) {
            this.a = request.uri;
            this.b = request.resourceId;
            this.c = request.stableKey;
            this.d = request.targetWidth;
            this.e = request.targetHeight;
            this.f = request.centerCrop;
            this.h = request.centerInside;
            this.g = request.centerCropGravity;
            this.j = request.rotationDegrees;
            this.k = request.rotationPivotX;
            this.l = request.rotationPivotY;
            this.m = request.hasRotationPivot;
            this.n = request.purgeable;
            this.i = request.onlyScaleDown;
            if (request.transformations != null) {
                this.o = new ArrayList(request.transformations);
            }
            this.p = request.config;
            this.q = request.priority;
        }
    }

    public /* synthetic */ Request(Uri uri, int i, String str, List list, int i2, int i3, boolean z, boolean z2, int i4, boolean z3, float f, float f2, float f3, boolean z4, boolean z5, Bitmap.Config config, Picasso.Priority priority, a aVar) {
        this.uri = uri;
        this.resourceId = i;
        this.stableKey = str;
        if (list == null) {
            this.transformations = null;
        } else {
            this.transformations = Collections.unmodifiableList(list);
        }
        this.targetWidth = i2;
        this.targetHeight = i3;
        this.centerCrop = z;
        this.centerInside = z2;
        this.centerCropGravity = i4;
        this.onlyScaleDown = z3;
        this.rotationDegrees = f;
        this.rotationPivotX = f2;
        this.rotationPivotY = f3;
        this.hasRotationPivot = z4;
        this.purgeable = z5;
        this.config = config;
        this.priority = priority;
    }

    public String a() {
        long jNanoTime = System.nanoTime() - this.b;
        if (jNanoTime > d) {
            return c() + '+' + TimeUnit.NANOSECONDS.toSeconds(jNanoTime) + 's';
        }
        return c() + '+' + TimeUnit.NANOSECONDS.toMillis(jNanoTime) + "ms";
    }

    public boolean b() {
        return hasSize() || this.rotationDegrees != 0.0f;
    }

    public Builder buildUpon() {
        return new Builder(this, null);
    }

    public String c() {
        StringBuilder sbA = g9.a("[R");
        sbA.append(this.a);
        sbA.append(']');
        return sbA.toString();
    }

    public boolean hasSize() {
        return (this.targetWidth == 0 && this.targetHeight == 0) ? false : true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Request{");
        int i = this.resourceId;
        if (i > 0) {
            sb.append(i);
        } else {
            sb.append(this.uri);
        }
        List<Transformation> list = this.transformations;
        if (list != null && !list.isEmpty()) {
            for (Transformation transformation : this.transformations) {
                sb.append(' ');
                sb.append(transformation.key());
            }
        }
        if (this.stableKey != null) {
            sb.append(" stableKey(");
            sb.append(this.stableKey);
            sb.append(')');
        }
        if (this.targetWidth > 0) {
            sb.append(" resize(");
            sb.append(this.targetWidth);
            sb.append(',');
            sb.append(this.targetHeight);
            sb.append(')');
        }
        if (this.centerCrop) {
            sb.append(" centerCrop");
        }
        if (this.centerInside) {
            sb.append(" centerInside");
        }
        if (this.rotationDegrees != 0.0f) {
            sb.append(" rotation(");
            sb.append(this.rotationDegrees);
            if (this.hasRotationPivot) {
                sb.append(" @ ");
                sb.append(this.rotationPivotX);
                sb.append(',');
                sb.append(this.rotationPivotY);
            }
            sb.append(')');
        }
        if (this.purgeable) {
            sb.append(" purgeable");
        }
        if (this.config != null) {
            sb.append(' ');
            sb.append(this.config);
        }
        sb.append('}');
        return sb.toString();
    }
}
