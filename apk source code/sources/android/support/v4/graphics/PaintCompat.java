package android.support.v4.graphics;

import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

/* loaded from: classes.dex */
public final class PaintCompat {
    public static final ThreadLocal<Pair<Rect, Rect>> a = new ThreadLocal<>();

    public static boolean hasGlyph(@NonNull Paint paint, @NonNull String str) {
        return paint.hasGlyph(str);
    }
}
