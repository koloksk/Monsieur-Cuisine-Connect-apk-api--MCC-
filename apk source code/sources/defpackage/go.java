package defpackage;

import android.support.v4.media.session.PlaybackStateCompat;
import javax.annotation.Nullable;

/* loaded from: classes.dex */
public final class go {

    @Nullable
    public static fo a;
    public static long b;

    public static fo a() {
        synchronized (go.class) {
            if (a == null) {
                return new fo();
            }
            fo foVar = a;
            a = foVar.f;
            foVar.f = null;
            b -= PlaybackStateCompat.ACTION_PLAY_FROM_URI;
            return foVar;
        }
    }

    public static void a(fo foVar) {
        if (foVar.f == null && foVar.g == null) {
            if (foVar.d) {
                return;
            }
            synchronized (go.class) {
                if (b + PlaybackStateCompat.ACTION_PLAY_FROM_URI > PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH) {
                    return;
                }
                b += PlaybackStateCompat.ACTION_PLAY_FROM_URI;
                foVar.f = a;
                foVar.c = 0;
                foVar.b = 0;
                a = foVar;
                return;
            }
        }
        throw new IllegalArgumentException();
    }
}
