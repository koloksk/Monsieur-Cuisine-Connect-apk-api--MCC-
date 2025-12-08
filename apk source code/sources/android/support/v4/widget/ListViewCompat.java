package android.support.v4.widget;

import android.support.annotation.NonNull;
import android.widget.ListView;

/* loaded from: classes.dex */
public final class ListViewCompat {
    public static boolean canScrollList(@NonNull ListView listView, int i) {
        return listView.canScrollList(i);
    }

    public static void scrollListBy(@NonNull ListView listView, int i) {
        listView.scrollListBy(i);
    }
}
