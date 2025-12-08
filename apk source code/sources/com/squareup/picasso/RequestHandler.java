package com.squareup.picasso;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.squareup.picasso.Picasso;
import defpackage.qd;
import java.io.IOException;
import okio.Source;

/* loaded from: classes.dex */
public abstract class RequestHandler {

    public static final class Result {
        public final Picasso.LoadedFrom a;
        public final Bitmap b;
        public final Source c;
        public final int d;

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Result(@NonNull Bitmap bitmap, @NonNull Picasso.LoadedFrom loadedFrom) {
            this(bitmap, null, loadedFrom, 0);
            qd.a(bitmap, "bitmap == null");
        }

        @Nullable
        public Bitmap getBitmap() {
            return this.b;
        }

        @NonNull
        public Picasso.LoadedFrom getLoadedFrom() {
            return this.a;
        }

        @Nullable
        public Source getSource() {
            return this.c;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public Result(@NonNull Source source, @NonNull Picasso.LoadedFrom loadedFrom) {
            this(null, source, loadedFrom, 0);
            qd.a(source, "source == null");
        }

        public Result(@Nullable Bitmap bitmap, @Nullable Source source, @NonNull Picasso.LoadedFrom loadedFrom, int i) {
            if ((bitmap != null) != (source != null)) {
                this.b = bitmap;
                this.c = source;
                qd.a(loadedFrom, "loadedFrom == null");
                this.a = loadedFrom;
                this.d = i;
                return;
            }
            throw new AssertionError();
        }
    }

    public static BitmapFactory.Options a(Request request) {
        boolean zHasSize = request.hasSize();
        boolean z = request.config != null;
        BitmapFactory.Options options = null;
        if (zHasSize || z || request.purgeable) {
            options = new BitmapFactory.Options();
            options.inJustDecodeBounds = zHasSize;
            boolean z2 = request.purgeable;
            options.inInputShareable = z2;
            options.inPurgeable = z2;
            if (z) {
                options.inPreferredConfig = request.config;
            }
        }
        return options;
    }

    public int a() {
        return 0;
    }

    public boolean a(boolean z, NetworkInfo networkInfo) {
        return false;
    }

    public boolean b() {
        return false;
    }

    public abstract boolean canHandleRequest(Request request);

    @Nullable
    public abstract Result load(Request request, int i) throws IOException;

    public static void a(int i, int i2, BitmapFactory.Options options, Request request) {
        a(i, i2, options.outWidth, options.outHeight, options, request);
    }

    public static void a(int i, int i2, int i3, int i4, BitmapFactory.Options options, Request request) {
        int iMin;
        double dFloor;
        if (i4 > i2 || i3 > i) {
            if (i2 == 0) {
                dFloor = Math.floor(i3 / i);
            } else if (i == 0) {
                dFloor = Math.floor(i4 / i2);
            } else {
                int iFloor = (int) Math.floor(i4 / i2);
                int iFloor2 = (int) Math.floor(i3 / i);
                if (request.centerInside) {
                    iMin = Math.max(iFloor, iFloor2);
                } else {
                    iMin = Math.min(iFloor, iFloor2);
                }
            }
            iMin = (int) dFloor;
        } else {
            iMin = 1;
        }
        options.inSampleSize = iMin;
        options.inJustDecodeBounds = false;
    }
}
