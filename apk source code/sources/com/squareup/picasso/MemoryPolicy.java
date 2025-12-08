package com.squareup.picasso;

/* loaded from: classes.dex */
public enum MemoryPolicy {
    NO_CACHE(1),
    NO_STORE(2);

    public final int a;

    MemoryPolicy(int i) {
        this.a = i;
    }

    public static boolean a(int i) {
        return (i & NO_CACHE.a) == 0;
    }
}
