package android.support.v7.util;

import android.support.v7.util.TileList;

/* loaded from: classes.dex */
public interface ThreadUtil$BackgroundCallback<T> {
    void loadTile(int i, int i2);

    void recycleTile(TileList.Tile<T> tile);

    void refresh(int i);

    void updateRange(int i, int i2, int i3, int i4, int i5);
}
