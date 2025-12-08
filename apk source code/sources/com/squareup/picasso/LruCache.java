package com.squareup.picasso;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import defpackage.qd;

/* loaded from: classes.dex */
public final class LruCache implements Cache {
    public final android.util.LruCache<String, b> a;

    public class a extends android.util.LruCache<String, b> {
        public a(LruCache lruCache, int i) {
            super(i);
        }

        @Override // android.util.LruCache
        public int sizeOf(String str, b bVar) {
            return bVar.b;
        }
    }

    public static final class b {
        public final Bitmap a;
        public final int b;

        public b(Bitmap bitmap, int i) {
            this.a = bitmap;
            this.b = i;
        }
    }

    public LruCache(@NonNull Context context) {
        this(qd.a(context));
    }

    @Override // com.squareup.picasso.Cache
    public void clear() {
        this.a.evictAll();
    }

    @Override // com.squareup.picasso.Cache
    public void clearKeyUri(String str) {
        for (String str2 : this.a.snapshot().keySet()) {
            if (str2.startsWith(str) && str2.length() > str.length() && str2.charAt(str.length()) == '\n') {
                this.a.remove(str2);
            }
        }
    }

    public int evictionCount() {
        return this.a.evictionCount();
    }

    @Override // com.squareup.picasso.Cache
    @Nullable
    public Bitmap get(@NonNull String str) {
        b bVar = this.a.get(str);
        if (bVar != null) {
            return bVar.a;
        }
        return null;
    }

    public int hitCount() {
        return this.a.hitCount();
    }

    @Override // com.squareup.picasso.Cache
    public int maxSize() {
        return this.a.maxSize();
    }

    public int missCount() {
        return this.a.missCount();
    }

    public int putCount() {
        return this.a.putCount();
    }

    @Override // com.squareup.picasso.Cache
    public void set(@NonNull String str, @NonNull Bitmap bitmap) {
        if (str == null || bitmap == null) {
            throw new NullPointerException("key == null || bitmap == null");
        }
        int iA = qd.a(bitmap);
        if (iA > maxSize()) {
            this.a.remove(str);
        } else {
            this.a.put(str, new b(bitmap, iA));
        }
    }

    @Override // com.squareup.picasso.Cache
    public int size() {
        return this.a.size();
    }

    public LruCache(int i) {
        this.a = new a(this, i);
    }
}
