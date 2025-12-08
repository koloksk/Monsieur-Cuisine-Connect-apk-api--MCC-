package android.support.v4.hardware.display;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.Display;
import java.util.WeakHashMap;

/* loaded from: classes.dex */
public abstract class DisplayManagerCompat {
    public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
    public static final WeakHashMap<Context, DisplayManagerCompat> a = new WeakHashMap<>();

    @RequiresApi(17)
    public static class a extends DisplayManagerCompat {
        public final DisplayManager b;

        public a(Context context) {
            this.b = (DisplayManager) context.getSystemService("display");
        }

        @Override // android.support.v4.hardware.display.DisplayManagerCompat
        public Display getDisplay(int i) {
            return this.b.getDisplay(i);
        }

        @Override // android.support.v4.hardware.display.DisplayManagerCompat
        public Display[] getDisplays() {
            return this.b.getDisplays();
        }

        @Override // android.support.v4.hardware.display.DisplayManagerCompat
        public Display[] getDisplays(String str) {
            return this.b.getDisplays(str);
        }
    }

    @NonNull
    public static DisplayManagerCompat getInstance(@NonNull Context context) {
        DisplayManagerCompat aVar;
        synchronized (a) {
            aVar = a.get(context);
            if (aVar == null) {
                aVar = new a(context);
                a.put(context, aVar);
            }
        }
        return aVar;
    }

    @Nullable
    public abstract Display getDisplay(int i);

    @NonNull
    public abstract Display[] getDisplays();

    @NonNull
    public abstract Display[] getDisplays(String str);
}
