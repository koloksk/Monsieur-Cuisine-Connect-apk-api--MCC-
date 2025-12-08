package android.support.v4.content;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;

@Deprecated
/* loaded from: classes.dex */
public final class SharedPreferencesCompat {

    @Deprecated
    public static final class EditorCompat {
        public static EditorCompat b;
        public final a a = new a();

        public static class a {
        }

        @Deprecated
        public static EditorCompat getInstance() {
            if (b == null) {
                b = new EditorCompat();
            }
            return b;
        }

        @Deprecated
        public void apply(@NonNull SharedPreferences.Editor editor) {
            if (this.a == null) {
                throw null;
            }
            try {
                editor.apply();
            } catch (AbstractMethodError unused) {
                editor.commit();
            }
        }
    }
}
