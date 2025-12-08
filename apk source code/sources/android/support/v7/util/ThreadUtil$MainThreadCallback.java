package android.support.v7.util;

import android.support.v7.util.TileList;

/* loaded from: classes.dex */
public interface ThreadUtil$MainThreadCallback<T> {
    void addTile(int i, TileList.Tile<T> tile);

    void removeTile(int i, int i2);

    void updateItemCount(int i, int i2);
}
