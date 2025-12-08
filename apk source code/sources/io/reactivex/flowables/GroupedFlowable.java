package io.reactivex.flowables;

import io.reactivex.Flowable;
import io.reactivex.annotations.Nullable;

/* loaded from: classes.dex */
public abstract class GroupedFlowable<K, T> extends Flowable<T> {
    public final K b;

    public GroupedFlowable(@Nullable K k) {
        this.b = k;
    }

    @Nullable
    public K getKey() {
        return this.b;
    }
}
