package android.support.v7.util;

/* loaded from: classes.dex */
public class BatchingListUpdateCallback implements ListUpdateCallback {
    public final ListUpdateCallback a;
    public int b = 0;
    public int c = -1;
    public int d = -1;
    public Object e = null;

    public BatchingListUpdateCallback(ListUpdateCallback listUpdateCallback) {
        this.a = listUpdateCallback;
    }

    public void dispatchLastEvent() {
        int i = this.b;
        if (i == 0) {
            return;
        }
        if (i == 1) {
            this.a.onInserted(this.c, this.d);
        } else if (i == 2) {
            this.a.onRemoved(this.c, this.d);
        } else if (i == 3) {
            this.a.onChanged(this.c, this.d, this.e);
        }
        this.e = null;
        this.b = 0;
    }

    @Override // android.support.v7.util.ListUpdateCallback
    public void onChanged(int i, int i2, Object obj) {
        int i3;
        if (this.b == 3) {
            int i4 = this.c;
            int i5 = this.d;
            if (i <= i4 + i5 && (i3 = i + i2) >= i4 && this.e == obj) {
                this.c = Math.min(i, i4);
                this.d = Math.max(i5 + i4, i3) - this.c;
                return;
            }
        }
        dispatchLastEvent();
        this.c = i;
        this.d = i2;
        this.e = obj;
        this.b = 3;
    }

    @Override // android.support.v7.util.ListUpdateCallback
    public void onInserted(int i, int i2) {
        int i3;
        if (this.b == 1 && i >= (i3 = this.c)) {
            int i4 = this.d;
            if (i <= i3 + i4) {
                this.d = i4 + i2;
                this.c = Math.min(i, i3);
                return;
            }
        }
        dispatchLastEvent();
        this.c = i;
        this.d = i2;
        this.b = 1;
    }

    @Override // android.support.v7.util.ListUpdateCallback
    public void onMoved(int i, int i2) {
        dispatchLastEvent();
        this.a.onMoved(i, i2);
    }

    @Override // android.support.v7.util.ListUpdateCallback
    public void onRemoved(int i, int i2) {
        int i3;
        if (this.b == 2 && (i3 = this.c) >= i && i3 <= i + i2) {
            this.d += i2;
            this.c = i;
        } else {
            dispatchLastEvent();
            this.c = i;
            this.d = i2;
            this.b = 2;
        }
    }
}
