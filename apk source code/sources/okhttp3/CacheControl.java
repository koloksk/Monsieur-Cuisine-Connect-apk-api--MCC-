package okhttp3;

import defpackage.g9;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public final class CacheControl {
    public final boolean a;
    public final boolean b;
    public final int c;
    public final int d;
    public final boolean e;
    public final boolean f;
    public final boolean g;
    public final int h;
    public final int i;
    public final boolean j;
    public final boolean k;
    public final boolean l;

    @Nullable
    public String m;
    public static final CacheControl FORCE_NETWORK = new Builder().noCache().build();
    public static final CacheControl FORCE_CACHE = new Builder().onlyIfCached().maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();

    public static final class Builder {
        public boolean a;
        public boolean b;
        public int c = -1;
        public int d = -1;
        public int e = -1;
        public boolean f;
        public boolean g;
        public boolean h;

        public CacheControl build() {
            return new CacheControl(this);
        }

        public Builder immutable() {
            this.h = true;
            return this;
        }

        public Builder maxAge(int i, TimeUnit timeUnit) {
            if (i < 0) {
                throw new IllegalArgumentException(g9.b("maxAge < 0: ", i));
            }
            long seconds = timeUnit.toSeconds(i);
            this.c = seconds > 2147483647L ? Integer.MAX_VALUE : (int) seconds;
            return this;
        }

        public Builder maxStale(int i, TimeUnit timeUnit) {
            if (i < 0) {
                throw new IllegalArgumentException(g9.b("maxStale < 0: ", i));
            }
            long seconds = timeUnit.toSeconds(i);
            this.d = seconds > 2147483647L ? Integer.MAX_VALUE : (int) seconds;
            return this;
        }

        public Builder minFresh(int i, TimeUnit timeUnit) {
            if (i < 0) {
                throw new IllegalArgumentException(g9.b("minFresh < 0: ", i));
            }
            long seconds = timeUnit.toSeconds(i);
            this.e = seconds > 2147483647L ? Integer.MAX_VALUE : (int) seconds;
            return this;
        }

        public Builder noCache() {
            this.a = true;
            return this;
        }

        public Builder noStore() {
            this.b = true;
            return this;
        }

        public Builder noTransform() {
            this.g = true;
            return this;
        }

        public Builder onlyIfCached() {
            this.f = true;
            return this;
        }
    }

    public CacheControl(boolean z, boolean z2, int i, int i2, boolean z3, boolean z4, boolean z5, int i3, int i4, boolean z6, boolean z7, boolean z8, @Nullable String str) {
        this.a = z;
        this.b = z2;
        this.c = i;
        this.d = i2;
        this.e = z3;
        this.f = z4;
        this.g = z5;
        this.h = i3;
        this.i = i4;
        this.j = z6;
        this.k = z7;
        this.l = z8;
        this.m = str;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0041  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static okhttp3.CacheControl parse(okhttp3.Headers r22) {
        /*
            Method dump skipped, instructions count: 333
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: okhttp3.CacheControl.parse(okhttp3.Headers):okhttp3.CacheControl");
    }

    public boolean immutable() {
        return this.l;
    }

    public boolean isPrivate() {
        return this.e;
    }

    public boolean isPublic() {
        return this.f;
    }

    public int maxAgeSeconds() {
        return this.c;
    }

    public int maxStaleSeconds() {
        return this.h;
    }

    public int minFreshSeconds() {
        return this.i;
    }

    public boolean mustRevalidate() {
        return this.g;
    }

    public boolean noCache() {
        return this.a;
    }

    public boolean noStore() {
        return this.b;
    }

    public boolean noTransform() {
        return this.k;
    }

    public boolean onlyIfCached() {
        return this.j;
    }

    public int sMaxAgeSeconds() {
        return this.d;
    }

    public String toString() {
        String string = this.m;
        if (string == null) {
            StringBuilder sb = new StringBuilder();
            if (this.a) {
                sb.append("no-cache, ");
            }
            if (this.b) {
                sb.append("no-store, ");
            }
            if (this.c != -1) {
                sb.append("max-age=");
                sb.append(this.c);
                sb.append(", ");
            }
            if (this.d != -1) {
                sb.append("s-maxage=");
                sb.append(this.d);
                sb.append(", ");
            }
            if (this.e) {
                sb.append("private, ");
            }
            if (this.f) {
                sb.append("public, ");
            }
            if (this.g) {
                sb.append("must-revalidate, ");
            }
            if (this.h != -1) {
                sb.append("max-stale=");
                sb.append(this.h);
                sb.append(", ");
            }
            if (this.i != -1) {
                sb.append("min-fresh=");
                sb.append(this.i);
                sb.append(", ");
            }
            if (this.j) {
                sb.append("only-if-cached, ");
            }
            if (this.k) {
                sb.append("no-transform, ");
            }
            if (this.l) {
                sb.append("immutable, ");
            }
            if (sb.length() == 0) {
                string = "";
            } else {
                sb.delete(sb.length() - 2, sb.length());
                string = sb.toString();
            }
            this.m = string;
        }
        return string;
    }

    public CacheControl(Builder builder) {
        this.a = builder.a;
        this.b = builder.b;
        this.c = builder.c;
        this.d = -1;
        this.e = false;
        this.f = false;
        this.g = false;
        this.h = builder.d;
        this.i = builder.e;
        this.j = builder.f;
        this.k = builder.g;
        this.l = builder.h;
    }
}
