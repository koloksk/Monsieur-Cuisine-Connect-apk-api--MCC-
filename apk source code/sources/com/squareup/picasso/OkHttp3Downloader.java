package com.squareup.picasso;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import defpackage.qd;
import java.io.File;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/* loaded from: classes.dex */
public final class OkHttp3Downloader implements Downloader {

    @VisibleForTesting
    public final Call.Factory a;
    public final okhttp3.Cache b;
    public boolean c;

    public OkHttp3Downloader(Context context) {
        this(qd.b(context));
    }

    @Override // com.squareup.picasso.Downloader
    @NonNull
    public Response load(@NonNull okhttp3.Request request) throws IOException {
        return this.a.newCall(request).execute();
    }

    @Override // com.squareup.picasso.Downloader
    public void shutdown() {
        okhttp3.Cache cache;
        if (this.c || (cache = this.b) == null) {
            return;
        }
        try {
            cache.close();
        } catch (IOException unused) {
        }
    }

    public OkHttp3Downloader(File file) {
        this(file, qd.a(file));
    }

    public OkHttp3Downloader(Context context, long j) {
        this(qd.b(context), j);
    }

    public OkHttp3Downloader(File file, long j) {
        this(new OkHttpClient.Builder().cache(new okhttp3.Cache(file, j)).build());
        this.c = false;
    }

    public OkHttp3Downloader(OkHttpClient okHttpClient) {
        this.c = true;
        this.a = okHttpClient;
        this.b = okHttpClient.cache();
    }

    public OkHttp3Downloader(Call.Factory factory) {
        this.c = true;
        this.a = factory;
        this.b = null;
    }
}
