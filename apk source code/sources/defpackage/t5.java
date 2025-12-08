package defpackage;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.media.MediaDescriptionCompat;
import java.util.Locale;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public interface t5 {
    @IntRange(from = -1)
    int a(Locale locale);

    String a();

    @Nullable
    Locale a(String[] strArr);

    void a(@NonNull Locale... localeArr);

    Object b();

    boolean equals(Object obj);

    Locale get(int i);

    int hashCode();

    boolean isEmpty();

    @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
    int size();

    String toString();
}
