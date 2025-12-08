package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.RestrictTo;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import defpackage.g9;
import defpackage.i8;
import defpackage.q5;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

/* loaded from: classes.dex */
public class StaggeredGridLayoutManager extends RecyclerView.LayoutManager implements RecyclerView.SmoothScroller.ScrollVectorProvider {

    @Deprecated
    public static final int GAP_HANDLING_LAZY = 1;
    public static final int GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS = 2;
    public static final int GAP_HANDLING_NONE = 0;
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public BitSet B;
    public boolean G;
    public boolean H;
    public SavedState I;
    public int J;
    public int[] O;
    public c[] t;

    @NonNull
    public OrientationHelper u;

    @NonNull
    public OrientationHelper v;
    public int w;
    public int x;

    @NonNull
    public final i8 y;
    public int s = -1;
    public boolean z = false;
    public boolean A = false;
    public int C = -1;
    public int D = Integer.MIN_VALUE;
    public LazySpanLookup E = new LazySpanLookup();
    public int F = 2;
    public final Rect K = new Rect();
    public final b L = new b();
    public boolean M = false;
    public boolean N = true;
    public final Runnable P = new a();

    public static class LayoutParams extends RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        public c e;
        public boolean f;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
        }

        public final int getSpanIndex() {
            c cVar = this.e;
            if (cVar == null) {
                return -1;
            }
            return cVar.e;
        }

        public boolean isFullSpan() {
            return this.f;
        }

        public void setFullSpan(boolean z) {
            this.f = z;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
        }

        public LayoutParams(RecyclerView.LayoutParams layoutParams) {
            super(layoutParams);
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        public int a;
        public int b;
        public int c;
        public int[] d;
        public int e;
        public int[] f;
        public List<LazySpanLookup.FullSpanItem> g;
        public boolean h;
        public boolean i;
        public boolean j;

        public static class a implements Parcelable.Creator<SavedState> {
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        }

        public SavedState() {
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c);
            if (this.c > 0) {
                parcel.writeIntArray(this.d);
            }
            parcel.writeInt(this.e);
            if (this.e > 0) {
                parcel.writeIntArray(this.f);
            }
            parcel.writeInt(this.h ? 1 : 0);
            parcel.writeInt(this.i ? 1 : 0);
            parcel.writeInt(this.j ? 1 : 0);
            parcel.writeList(this.g);
        }

        public SavedState(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            int i = parcel.readInt();
            this.c = i;
            if (i > 0) {
                int[] iArr = new int[i];
                this.d = iArr;
                parcel.readIntArray(iArr);
            }
            int i2 = parcel.readInt();
            this.e = i2;
            if (i2 > 0) {
                int[] iArr2 = new int[i2];
                this.f = iArr2;
                parcel.readIntArray(iArr2);
            }
            this.h = parcel.readInt() == 1;
            this.i = parcel.readInt() == 1;
            this.j = parcel.readInt() == 1;
            this.g = parcel.readArrayList(LazySpanLookup.FullSpanItem.class.getClassLoader());
        }

        public SavedState(SavedState savedState) {
            this.c = savedState.c;
            this.a = savedState.a;
            this.b = savedState.b;
            this.d = savedState.d;
            this.e = savedState.e;
            this.f = savedState.f;
            this.h = savedState.h;
            this.i = savedState.i;
            this.j = savedState.j;
            this.g = savedState.g;
        }
    }

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            StaggeredGridLayoutManager.this.b();
        }
    }

    public class b {
        public int a;
        public int b;
        public boolean c;
        public boolean d;
        public boolean e;
        public int[] f;

        public b() {
            b();
        }

        public void a() {
            this.b = this.c ? StaggeredGridLayoutManager.this.u.getEndAfterPadding() : StaggeredGridLayoutManager.this.u.getStartAfterPadding();
        }

        public void b() {
            this.a = -1;
            this.b = Integer.MIN_VALUE;
            this.c = false;
            this.d = false;
            this.e = false;
            int[] iArr = this.f;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
        }
    }

    public StaggeredGridLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        RecyclerView.LayoutManager.Properties properties = RecyclerView.LayoutManager.getProperties(context, attributeSet, i, i2);
        setOrientation(properties.orientation);
        setSpanCount(properties.spanCount);
        setReverseLayout(properties.reverseLayout);
        this.y = new i8();
        this.u = OrientationHelper.createOrientationHelper(this, this.w);
        this.v = OrientationHelper.createOrientationHelper(this, 1 - this.w);
    }

    public final int a(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return q5.a(state, this.u, b(!this.N), a(!this.N), this, this.N);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void assertNotInLayoutOrScroll(String str) {
        if (this.I == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    public boolean b() {
        int iC;
        int iD;
        if (getChildCount() == 0 || this.F == 0 || !isAttachedToWindow()) {
            return false;
        }
        if (this.A) {
            iC = d();
            iD = c();
        } else {
            iC = c();
            iD = d();
        }
        if (iC == 0 && e() != null) {
            this.E.a();
            requestSimpleAnimationsInNextLayout();
            requestLayout();
            return true;
        }
        if (!this.M) {
            return false;
        }
        int i = this.A ? -1 : 1;
        int i2 = iD + 1;
        LazySpanLookup.FullSpanItem fullSpanItemA = this.E.a(iC, i2, i, true);
        if (fullSpanItemA == null) {
            this.M = false;
            this.E.b(i2);
            return false;
        }
        LazySpanLookup.FullSpanItem fullSpanItemA2 = this.E.a(iC, fullSpanItemA.a, i * (-1), true);
        if (fullSpanItemA2 == null) {
            this.E.b(fullSpanItemA.a);
        } else {
            this.E.b(fullSpanItemA2.a + 1);
        }
        requestSimpleAnimationsInNextLayout();
        requestLayout();
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:107:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x041a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void c(android.support.v7.widget.RecyclerView.Recycler r13, android.support.v7.widget.RecyclerView.State r14, boolean r15) {
        /*
            Method dump skipped, instructions count: 1083
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.c(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State, boolean):void");
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return this.w == 0;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.w == 1;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public boolean checkLayoutParams(RecyclerView.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    @RestrictTo({RestrictTo.Scope.LIBRARY})
    public void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int iA;
        int iB;
        if (this.w != 0) {
            i = i2;
        }
        if (getChildCount() == 0 || i == 0) {
            return;
        }
        a(i, state);
        int[] iArr = this.O;
        if (iArr == null || iArr.length < this.s) {
            this.O = new int[this.s];
        }
        int i3 = 0;
        for (int i4 = 0; i4 < this.s; i4++) {
            i8 i8Var = this.y;
            if (i8Var.d == -1) {
                iA = i8Var.f;
                iB = this.t[i4].b(iA);
            } else {
                iA = this.t[i4].a(i8Var.g);
                iB = this.y.g;
            }
            int i5 = iA - iB;
            if (i5 >= 0) {
                this.O[i3] = i5;
                i3++;
            }
        }
        Arrays.sort(this.O, 0, i3);
        for (int i6 = 0; i6 < i3; i6++) {
            int i7 = this.y.c;
            if (!(i7 >= 0 && i7 < state.getItemCount())) {
                return;
            }
            layoutPrefetchRegistry.addPosition(this.y.c, this.O[i6]);
            i8 i8Var2 = this.y;
            i8Var2.c += i8Var2.d;
        }
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollExtent(RecyclerView.State state) {
        return a(state);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollOffset(RecyclerView.State state) {
        return b(state);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int computeHorizontalScrollRange(RecyclerView.State state) {
        return c(state);
    }

    @Override // android.support.v7.widget.RecyclerView.SmoothScroller.ScrollVectorProvider
    public PointF computeScrollVectorForPosition(int i) {
        int iA = a(i);
        PointF pointF = new PointF();
        if (iA == 0) {
            return null;
        }
        if (this.w == 0) {
            pointF.x = iA;
            pointF.y = 0.0f;
        } else {
            pointF.x = 0.0f;
            pointF.y = iA;
        }
        return pointF;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollExtent(RecyclerView.State state) {
        return a(state);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollOffset(RecyclerView.State state) {
        return b(state);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int computeVerticalScrollRange(RecyclerView.State state) {
        return c(state);
    }

    public final boolean d(int i) {
        if (this.w == 0) {
            return (i == -1) != this.A;
        }
        return ((i == -1) == this.A) == isLayoutRTL();
    }

    /* JADX WARN: Removed duplicated region for block: B:55:0x00d5  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00d7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View e() {
        /*
            Method dump skipped, instructions count: 251
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.e():android.view.View");
    }

    public final void f() {
        if (this.w == 1 || !isLayoutRTL()) {
            this.A = this.z;
        } else {
            this.A = !this.z;
        }
    }

    public int[] findFirstCompletelyVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.s];
        } else if (iArr.length < this.s) {
            StringBuilder sbA = g9.a("Provided int[]'s size must be more than or equal to span count. Expected:");
            sbA.append(this.s);
            sbA.append(", array size:");
            sbA.append(iArr.length);
            throw new IllegalArgumentException(sbA.toString());
        }
        for (int i = 0; i < this.s; i++) {
            c cVar = this.t[i];
            iArr[i] = StaggeredGridLayoutManager.this.z ? cVar.b(cVar.a.size() - 1, -1, true) : cVar.b(0, cVar.a.size(), true);
        }
        return iArr;
    }

    public int[] findFirstVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.s];
        } else if (iArr.length < this.s) {
            StringBuilder sbA = g9.a("Provided int[]'s size must be more than or equal to span count. Expected:");
            sbA.append(this.s);
            sbA.append(", array size:");
            sbA.append(iArr.length);
            throw new IllegalArgumentException(sbA.toString());
        }
        for (int i = 0; i < this.s; i++) {
            c cVar = this.t[i];
            iArr[i] = StaggeredGridLayoutManager.this.z ? cVar.b(cVar.a.size() - 1, -1, false) : cVar.b(0, cVar.a.size(), false);
        }
        return iArr;
    }

    public int[] findLastCompletelyVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.s];
        } else if (iArr.length < this.s) {
            StringBuilder sbA = g9.a("Provided int[]'s size must be more than or equal to span count. Expected:");
            sbA.append(this.s);
            sbA.append(", array size:");
            sbA.append(iArr.length);
            throw new IllegalArgumentException(sbA.toString());
        }
        for (int i = 0; i < this.s; i++) {
            c cVar = this.t[i];
            iArr[i] = StaggeredGridLayoutManager.this.z ? cVar.b(0, cVar.a.size(), true) : cVar.b(cVar.a.size() - 1, -1, true);
        }
        return iArr;
    }

    public int[] findLastVisibleItemPositions(int[] iArr) {
        if (iArr == null) {
            iArr = new int[this.s];
        } else if (iArr.length < this.s) {
            StringBuilder sbA = g9.a("Provided int[]'s size must be more than or equal to span count. Expected:");
            sbA.append(this.s);
            sbA.append(", array size:");
            sbA.append(iArr.length);
            throw new IllegalArgumentException(sbA.toString());
        }
        for (int i = 0; i < this.s; i++) {
            c cVar = this.t[i];
            iArr[i] = StaggeredGridLayoutManager.this.z ? cVar.b(0, cVar.a.size(), false) : cVar.b(cVar.a.size() - 1, -1, false);
        }
        return iArr;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return this.w == 0 ? new LayoutParams(-2, -1) : new LayoutParams(-1, -2);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
        return new LayoutParams(context, attributeSet);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int getColumnCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.w == 1 ? this.s : super.getColumnCountForAccessibility(recycler, state);
    }

    public int getGapStrategy() {
        return this.F;
    }

    public int getOrientation() {
        return this.w;
    }

    public boolean getReverseLayout() {
        return this.z;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int getRowCountForAccessibility(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return this.w == 0 ? this.s : super.getRowCountForAccessibility(recycler, state);
    }

    public int getSpanCount() {
        return this.s;
    }

    public void invalidateSpanAssignments() {
        this.E.a();
        requestLayout();
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public boolean isAutoMeasureEnabled() {
        return this.F != 0;
    }

    public boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void offsetChildrenHorizontal(int i) {
        super.offsetChildrenHorizontal(i);
        for (int i2 = 0; i2 < this.s; i2++) {
            c cVar = this.t[i2];
            int i3 = cVar.b;
            if (i3 != Integer.MIN_VALUE) {
                cVar.b = i3 + i;
            }
            int i4 = cVar.c;
            if (i4 != Integer.MIN_VALUE) {
                cVar.c = i4 + i;
            }
        }
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void offsetChildrenVertical(int i) {
        super.offsetChildrenVertical(i);
        for (int i2 = 0; i2 < this.s; i2++) {
            c cVar = this.t[i2];
            int i3 = cVar.b;
            if (i3 != Integer.MIN_VALUE) {
                cVar.b = i3 + i;
            }
            int i4 = cVar.c;
            if (i4 != Integer.MIN_VALUE) {
                cVar.c = i4 + i;
            }
        }
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        removeCallbacks(this.P);
        for (int i = 0; i < this.s; i++) {
            this.t[i].c();
        }
        recyclerView.requestLayout();
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x003d  */
    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    @android.support.annotation.Nullable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View onFocusSearchFailed(android.view.View r10, int r11, android.support.v7.widget.RecyclerView.Recycler r12, android.support.v7.widget.RecyclerView.State r13) {
        /*
            Method dump skipped, instructions count: 334
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.onFocusSearchFailed(android.view.View, int, android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State):android.view.View");
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            View viewB = b(false);
            View viewA = a(false);
            if (viewB == null || viewA == null) {
                return;
            }
            int position = getPosition(viewB);
            int position2 = getPosition(viewA);
            if (position < position2) {
                accessibilityEvent.setFromIndex(position);
                accessibilityEvent.setToIndex(position2);
            } else {
                accessibilityEvent.setFromIndex(position2);
                accessibilityEvent.setToIndex(position);
            }
        }
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityNodeInfoForItem(RecyclerView.Recycler recycler, RecyclerView.State state, View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
        ViewGroup.LayoutParams layoutParams = view2.getLayoutParams();
        if (!(layoutParams instanceof LayoutParams)) {
            super.a(view2, accessibilityNodeInfoCompat);
            return;
        }
        LayoutParams layoutParams2 = (LayoutParams) layoutParams;
        if (this.w == 0) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(layoutParams2.getSpanIndex(), layoutParams2.f ? this.s : 1, -1, -1, layoutParams2.f, false));
        } else {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(-1, -1, layoutParams2.getSpanIndex(), layoutParams2.f ? this.s : 1, layoutParams2.f, false));
        }
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        b(i, i2, 1);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onItemsChanged(RecyclerView recyclerView) {
        this.E.a();
        requestLayout();
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        b(i, i2, 8);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        b(i, i2, 2);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
        b(i, i2, 4);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        c(recycler, state, true);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.C = -1;
        this.D = Integer.MIN_VALUE;
        this.I = null;
        this.L.b();
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.I = (SavedState) parcelable;
            requestLayout();
        }
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public Parcelable onSaveInstanceState() {
        int iB;
        int startAfterPadding;
        int[] iArr;
        if (this.I != null) {
            return new SavedState(this.I);
        }
        SavedState savedState = new SavedState();
        savedState.h = this.z;
        savedState.i = this.G;
        savedState.j = this.H;
        LazySpanLookup lazySpanLookup = this.E;
        if (lazySpanLookup == null || (iArr = lazySpanLookup.a) == null) {
            savedState.e = 0;
        } else {
            savedState.f = iArr;
            savedState.e = iArr.length;
            savedState.g = lazySpanLookup.b;
        }
        if (getChildCount() > 0) {
            savedState.a = this.G ? d() : c();
            View viewA = this.A ? a(true) : b(true);
            savedState.b = viewA != null ? getPosition(viewA) : -1;
            int i = this.s;
            savedState.c = i;
            savedState.d = new int[i];
            for (int i2 = 0; i2 < this.s; i2++) {
                if (this.G) {
                    iB = this.t[i2].a(Integer.MIN_VALUE);
                    if (iB != Integer.MIN_VALUE) {
                        startAfterPadding = this.u.getEndAfterPadding();
                        iB -= startAfterPadding;
                    }
                } else {
                    iB = this.t[i2].b(Integer.MIN_VALUE);
                    if (iB != Integer.MIN_VALUE) {
                        startAfterPadding = this.u.getStartAfterPadding();
                        iB -= startAfterPadding;
                    }
                }
                savedState.d[i2] = iB;
            }
        } else {
            savedState.a = -1;
            savedState.b = -1;
            savedState.c = 0;
        }
        return savedState;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onScrollStateChanged(int i) {
        if (i == 0) {
            b();
        }
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return a(i, recycler, state);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void scrollToPosition(int i) {
        SavedState savedState = this.I;
        if (savedState != null && savedState.a != i) {
            savedState.d = null;
            savedState.c = 0;
            savedState.a = -1;
            savedState.b = -1;
        }
        this.C = i;
        this.D = Integer.MIN_VALUE;
        requestLayout();
    }

    public void scrollToPositionWithOffset(int i, int i2) {
        SavedState savedState = this.I;
        if (savedState != null) {
            savedState.d = null;
            savedState.c = 0;
            savedState.a = -1;
            savedState.b = -1;
        }
        this.C = i;
        this.D = i2;
        requestLayout();
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        return a(i, recycler, state);
    }

    public void setGapStrategy(int i) {
        assertNotInLayoutOrScroll(null);
        if (i == this.F) {
            return;
        }
        if (i != 0 && i != 2) {
            throw new IllegalArgumentException("invalid gap strategy. Must be GAP_HANDLING_NONE or GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS");
        }
        this.F = i;
        requestLayout();
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void setMeasuredDimension(Rect rect, int i, int i2) {
        int iChooseSize;
        int iChooseSize2;
        int paddingRight = getPaddingRight() + getPaddingLeft();
        int paddingBottom = getPaddingBottom() + getPaddingTop();
        if (this.w == 1) {
            iChooseSize2 = RecyclerView.LayoutManager.chooseSize(i2, rect.height() + paddingBottom, getMinimumHeight());
            iChooseSize = RecyclerView.LayoutManager.chooseSize(i, (this.x * this.s) + paddingRight, getMinimumWidth());
        } else {
            iChooseSize = RecyclerView.LayoutManager.chooseSize(i, rect.width() + paddingRight, getMinimumWidth());
            iChooseSize2 = RecyclerView.LayoutManager.chooseSize(i2, (this.x * this.s) + paddingBottom, getMinimumHeight());
        }
        setMeasuredDimension(iChooseSize, iChooseSize2);
    }

    public void setOrientation(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException("invalid orientation.");
        }
        assertNotInLayoutOrScroll(null);
        if (i == this.w) {
            return;
        }
        this.w = i;
        OrientationHelper orientationHelper = this.u;
        this.u = this.v;
        this.v = orientationHelper;
        requestLayout();
    }

    public void setReverseLayout(boolean z) {
        assertNotInLayoutOrScroll(null);
        SavedState savedState = this.I;
        if (savedState != null && savedState.h != z) {
            savedState.h = z;
        }
        this.z = z;
        requestLayout();
    }

    public void setSpanCount(int i) {
        assertNotInLayoutOrScroll(null);
        if (i != this.s) {
            invalidateSpanAssignments();
            this.s = i;
            this.B = new BitSet(this.s);
            this.t = new c[this.s];
            for (int i2 = 0; i2 < this.s; i2++) {
                this.t[i2] = new c(i2);
            }
            requestLayout();
        }
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.setTargetPosition(i);
        startSmoothScroll(linearSmoothScroller);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public boolean supportsPredictiveItemAnimations() {
        return this.I == null;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
    }

    public class c {
        public ArrayList<View> a = new ArrayList<>();
        public int b = Integer.MIN_VALUE;
        public int c = Integer.MIN_VALUE;
        public int d = 0;
        public final int e;

        public c(int i) {
            this.e = i;
        }

        public int a(int i) {
            int i2 = this.c;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (this.a.size() == 0) {
                return i;
            }
            a();
            return this.c;
        }

        public int b(int i) {
            int i2 = this.b;
            if (i2 != Integer.MIN_VALUE) {
                return i2;
            }
            if (this.a.size() == 0) {
                return i;
            }
            b();
            return this.b;
        }

        public void c(View view2) {
            LayoutParams layoutParamsB = b(view2);
            layoutParamsB.e = this;
            this.a.add(0, view2);
            this.b = Integer.MIN_VALUE;
            if (this.a.size() == 1) {
                this.c = Integer.MIN_VALUE;
            }
            if (layoutParamsB.isItemRemoved() || layoutParamsB.isItemChanged()) {
                this.d = StaggeredGridLayoutManager.this.u.getDecoratedMeasurement(view2) + this.d;
            }
        }

        public int d() {
            return StaggeredGridLayoutManager.this.z ? a(this.a.size() - 1, -1, true) : a(0, this.a.size(), true);
        }

        public int e() {
            return StaggeredGridLayoutManager.this.z ? a(0, this.a.size(), true) : a(this.a.size() - 1, -1, true);
        }

        public void f() {
            int size = this.a.size();
            View viewRemove = this.a.remove(size - 1);
            LayoutParams layoutParamsB = b(viewRemove);
            layoutParamsB.e = null;
            if (layoutParamsB.isItemRemoved() || layoutParamsB.isItemChanged()) {
                this.d -= StaggeredGridLayoutManager.this.u.getDecoratedMeasurement(viewRemove);
            }
            if (size == 1) {
                this.b = Integer.MIN_VALUE;
            }
            this.c = Integer.MIN_VALUE;
        }

        public void g() {
            View viewRemove = this.a.remove(0);
            LayoutParams layoutParamsB = b(viewRemove);
            layoutParamsB.e = null;
            if (this.a.size() == 0) {
                this.c = Integer.MIN_VALUE;
            }
            if (layoutParamsB.isItemRemoved() || layoutParamsB.isItemChanged()) {
                this.d -= StaggeredGridLayoutManager.this.u.getDecoratedMeasurement(viewRemove);
            }
            this.b = Integer.MIN_VALUE;
        }

        public void a() {
            LazySpanLookup.FullSpanItem fullSpanItemC;
            ArrayList<View> arrayList = this.a;
            View view2 = arrayList.get(arrayList.size() - 1);
            LayoutParams layoutParamsB = b(view2);
            this.c = StaggeredGridLayoutManager.this.u.getDecoratedEnd(view2);
            if (layoutParamsB.f && (fullSpanItemC = StaggeredGridLayoutManager.this.E.c(layoutParamsB.getViewLayoutPosition())) != null && fullSpanItemC.b == 1) {
                int i = this.c;
                int i2 = this.e;
                int[] iArr = fullSpanItemC.c;
                this.c = i + (iArr == null ? 0 : iArr[i2]);
            }
        }

        public void b() {
            LazySpanLookup.FullSpanItem fullSpanItemC;
            View view2 = this.a.get(0);
            LayoutParams layoutParamsB = b(view2);
            this.b = StaggeredGridLayoutManager.this.u.getDecoratedStart(view2);
            if (layoutParamsB.f && (fullSpanItemC = StaggeredGridLayoutManager.this.E.c(layoutParamsB.getViewLayoutPosition())) != null && fullSpanItemC.b == -1) {
                int i = this.b;
                int i2 = this.e;
                int[] iArr = fullSpanItemC.c;
                this.b = i - (iArr != null ? iArr[i2] : 0);
            }
        }

        public void c() {
            this.a.clear();
            this.b = Integer.MIN_VALUE;
            this.c = Integer.MIN_VALUE;
            this.d = 0;
        }

        public void a(View view2) {
            LayoutParams layoutParamsB = b(view2);
            layoutParamsB.e = this;
            this.a.add(view2);
            this.c = Integer.MIN_VALUE;
            if (this.a.size() == 1) {
                this.b = Integer.MIN_VALUE;
            }
            if (layoutParamsB.isItemRemoved() || layoutParamsB.isItemChanged()) {
                this.d = StaggeredGridLayoutManager.this.u.getDecoratedMeasurement(view2) + this.d;
            }
        }

        public LayoutParams b(View view2) {
            return (LayoutParams) view2.getLayoutParams();
        }

        public int b(int i, int i2, boolean z) {
            return a(i, i2, z, true, false);
        }

        public int a(int i, int i2, boolean z, boolean z2, boolean z3) {
            int startAfterPadding = StaggeredGridLayoutManager.this.u.getStartAfterPadding();
            int endAfterPadding = StaggeredGridLayoutManager.this.u.getEndAfterPadding();
            int i3 = i2 > i ? 1 : -1;
            while (i != i2) {
                View view2 = this.a.get(i);
                int decoratedStart = StaggeredGridLayoutManager.this.u.getDecoratedStart(view2);
                int decoratedEnd = StaggeredGridLayoutManager.this.u.getDecoratedEnd(view2);
                boolean z4 = false;
                boolean z5 = !z3 ? decoratedStart >= endAfterPadding : decoratedStart > endAfterPadding;
                if (!z3 ? decoratedEnd > startAfterPadding : decoratedEnd >= startAfterPadding) {
                    z4 = true;
                }
                if (z5 && z4) {
                    if (z && z2) {
                        if (decoratedStart >= startAfterPadding && decoratedEnd <= endAfterPadding) {
                            return StaggeredGridLayoutManager.this.getPosition(view2);
                        }
                    } else {
                        if (z2) {
                            return StaggeredGridLayoutManager.this.getPosition(view2);
                        }
                        if (decoratedStart < startAfterPadding || decoratedEnd > endAfterPadding) {
                            return StaggeredGridLayoutManager.this.getPosition(view2);
                        }
                    }
                }
                i += i3;
            }
            return -1;
        }

        public int a(int i, int i2, boolean z) {
            return a(i, i2, false, false, z);
        }

        public View a(int i, int i2) {
            View view2 = null;
            if (i2 == -1) {
                int size = this.a.size();
                int i3 = 0;
                while (i3 < size) {
                    View view3 = this.a.get(i3);
                    StaggeredGridLayoutManager staggeredGridLayoutManager = StaggeredGridLayoutManager.this;
                    if (staggeredGridLayoutManager.z && staggeredGridLayoutManager.getPosition(view3) <= i) {
                        break;
                    }
                    StaggeredGridLayoutManager staggeredGridLayoutManager2 = StaggeredGridLayoutManager.this;
                    if ((!staggeredGridLayoutManager2.z && staggeredGridLayoutManager2.getPosition(view3) >= i) || !view3.hasFocusable()) {
                        break;
                    }
                    i3++;
                    view2 = view3;
                }
            } else {
                int size2 = this.a.size() - 1;
                while (size2 >= 0) {
                    View view4 = this.a.get(size2);
                    StaggeredGridLayoutManager staggeredGridLayoutManager3 = StaggeredGridLayoutManager.this;
                    if (staggeredGridLayoutManager3.z && staggeredGridLayoutManager3.getPosition(view4) >= i) {
                        break;
                    }
                    StaggeredGridLayoutManager staggeredGridLayoutManager4 = StaggeredGridLayoutManager.this;
                    if ((!staggeredGridLayoutManager4.z && staggeredGridLayoutManager4.getPosition(view4) <= i) || !view4.hasFocusable()) {
                        break;
                    }
                    size2--;
                    view2 = view4;
                }
            }
            return view2;
        }
    }

    public int d() {
        int childCount = getChildCount();
        if (childCount == 0) {
            return 0;
        }
        return getPosition(getChildAt(childCount - 1));
    }

    public static class LazySpanLookup {
        public int[] a;
        public List<FullSpanItem> b;

        public void a(int i) {
            int[] iArr = this.a;
            if (iArr == null) {
                int[] iArr2 = new int[Math.max(i, 10) + 1];
                this.a = iArr2;
                Arrays.fill(iArr2, -1);
            } else if (i >= iArr.length) {
                int length = iArr.length;
                while (length <= i) {
                    length *= 2;
                }
                int[] iArr3 = new int[length];
                this.a = iArr3;
                System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
                int[] iArr4 = this.a;
                Arrays.fill(iArr4, iArr.length, iArr4.length, -1);
            }
        }

        public int b(int i) {
            List<FullSpanItem> list = this.b;
            if (list != null) {
                for (int size = list.size() - 1; size >= 0; size--) {
                    if (this.b.get(size).a >= i) {
                        this.b.remove(size);
                    }
                }
            }
            return d(i);
        }

        public FullSpanItem c(int i) {
            List<FullSpanItem> list = this.b;
            if (list == null) {
                return null;
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                FullSpanItem fullSpanItem = this.b.get(size);
                if (fullSpanItem.a == i) {
                    return fullSpanItem;
                }
            }
            return null;
        }

        /* JADX WARN: Removed duplicated region for block: B:10:0x000e  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public int d(int r5) {
            /*
                r4 = this;
                int[] r0 = r4.a
                r1 = -1
                if (r0 != 0) goto L6
                return r1
            L6:
                int r0 = r0.length
                if (r5 < r0) goto La
                return r1
            La:
                java.util.List<android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> r0 = r4.b
                if (r0 != 0) goto L10
            Le:
                r0 = r1
                goto L46
            L10:
                android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem r0 = r4.c(r5)
                if (r0 == 0) goto L1b
                java.util.List<android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> r2 = r4.b
                r2.remove(r0)
            L1b:
                java.util.List<android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> r0 = r4.b
                int r0 = r0.size()
                r2 = 0
            L22:
                if (r2 >= r0) goto L34
                java.util.List<android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> r3 = r4.b
                java.lang.Object r3 = r3.get(r2)
                android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem r3 = (android.support.v7.widget.StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem) r3
                int r3 = r3.a
                if (r3 < r5) goto L31
                goto L35
            L31:
                int r2 = r2 + 1
                goto L22
            L34:
                r2 = r1
            L35:
                if (r2 == r1) goto Le
                java.util.List<android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> r0 = r4.b
                java.lang.Object r0 = r0.get(r2)
                android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem r0 = (android.support.v7.widget.StaggeredGridLayoutManager.LazySpanLookup.FullSpanItem) r0
                java.util.List<android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup$FullSpanItem> r3 = r4.b
                r3.remove(r2)
                int r0 = r0.a
            L46:
                if (r0 != r1) goto L52
                int[] r0 = r4.a
                int r2 = r0.length
                java.util.Arrays.fill(r0, r5, r2, r1)
                int[] r5 = r4.a
                int r5 = r5.length
                return r5
            L52:
                int[] r2 = r4.a
                int r0 = r0 + 1
                java.util.Arrays.fill(r2, r5, r0, r1)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.LazySpanLookup.d(int):int");
        }

        public static class FullSpanItem implements Parcelable {
            public static final Parcelable.Creator<FullSpanItem> CREATOR = new a();
            public int a;
            public int b;
            public int[] c;
            public boolean d;

            public static class a implements Parcelable.Creator<FullSpanItem> {
                @Override // android.os.Parcelable.Creator
                public FullSpanItem createFromParcel(Parcel parcel) {
                    return new FullSpanItem(parcel);
                }

                @Override // android.os.Parcelable.Creator
                public FullSpanItem[] newArray(int i) {
                    return new FullSpanItem[i];
                }
            }

            public FullSpanItem(Parcel parcel) {
                this.a = parcel.readInt();
                this.b = parcel.readInt();
                this.d = parcel.readInt() == 1;
                int i = parcel.readInt();
                if (i > 0) {
                    int[] iArr = new int[i];
                    this.c = iArr;
                    parcel.readIntArray(iArr);
                }
            }

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            public String toString() {
                StringBuilder sbA = g9.a("FullSpanItem{mPosition=");
                sbA.append(this.a);
                sbA.append(", mGapDir=");
                sbA.append(this.b);
                sbA.append(", mHasUnwantedGapAfter=");
                sbA.append(this.d);
                sbA.append(", mGapPerSpan=");
                sbA.append(Arrays.toString(this.c));
                sbA.append('}');
                return sbA.toString();
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.a);
                parcel.writeInt(this.b);
                parcel.writeInt(this.d ? 1 : 0);
                int[] iArr = this.c;
                if (iArr == null || iArr.length <= 0) {
                    parcel.writeInt(0);
                } else {
                    parcel.writeInt(iArr.length);
                    parcel.writeIntArray(this.c);
                }
            }

            public FullSpanItem() {
            }
        }

        public void b(int i, int i2) {
            int[] iArr = this.a;
            if (iArr == null || i >= iArr.length) {
                return;
            }
            int i3 = i + i2;
            a(i3);
            int[] iArr2 = this.a;
            System.arraycopy(iArr2, i3, iArr2, i, (iArr2.length - i) - i2);
            int[] iArr3 = this.a;
            Arrays.fill(iArr3, iArr3.length - i2, iArr3.length, -1);
            List<FullSpanItem> list = this.b;
            if (list == null) {
                return;
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                FullSpanItem fullSpanItem = this.b.get(size);
                int i4 = fullSpanItem.a;
                if (i4 >= i) {
                    if (i4 < i3) {
                        this.b.remove(size);
                    } else {
                        fullSpanItem.a = i4 - i2;
                    }
                }
            }
        }

        public void a() {
            int[] iArr = this.a;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            this.b = null;
        }

        public void a(int i, int i2) {
            int[] iArr = this.a;
            if (iArr == null || i >= iArr.length) {
                return;
            }
            int i3 = i + i2;
            a(i3);
            int[] iArr2 = this.a;
            System.arraycopy(iArr2, i, iArr2, i3, (iArr2.length - i) - i2);
            Arrays.fill(this.a, i, i3, -1);
            List<FullSpanItem> list = this.b;
            if (list == null) {
                return;
            }
            for (int size = list.size() - 1; size >= 0; size--) {
                FullSpanItem fullSpanItem = this.b.get(size);
                int i4 = fullSpanItem.a;
                if (i4 >= i) {
                    fullSpanItem.a = i4 + i2;
                }
            }
        }

        public void a(FullSpanItem fullSpanItem) {
            if (this.b == null) {
                this.b = new ArrayList();
            }
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                FullSpanItem fullSpanItem2 = this.b.get(i);
                if (fullSpanItem2.a == fullSpanItem.a) {
                    this.b.remove(i);
                }
                if (fullSpanItem2.a >= fullSpanItem.a) {
                    this.b.add(i, fullSpanItem);
                    return;
                }
            }
            this.b.add(fullSpanItem);
        }

        public FullSpanItem a(int i, int i2, int i3, boolean z) {
            List<FullSpanItem> list = this.b;
            if (list == null) {
                return null;
            }
            int size = list.size();
            for (int i4 = 0; i4 < size; i4++) {
                FullSpanItem fullSpanItem = this.b.get(i4);
                int i5 = fullSpanItem.a;
                if (i5 >= i2) {
                    return null;
                }
                if (i5 >= i && (i3 == 0 || fullSpanItem.b == i3 || (z && fullSpanItem.d))) {
                    return fullSpanItem;
                }
            }
            return null;
        }
    }

    public final void a(View view2, int i, int i2, boolean z) {
        boolean zA;
        calculateItemDecorationsForChild(view2, this.K);
        LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
        int i3 = ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
        Rect rect = this.K;
        int iC = c(i, i3 + rect.left, ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + rect.right);
        int i4 = ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
        Rect rect2 = this.K;
        int iC2 = c(i2, i4 + rect2.top, ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + rect2.bottom);
        if (z) {
            zA = b(view2, iC, iC2, layoutParams);
        } else {
            zA = a(view2, iC, iC2, layoutParams);
        }
        if (zA) {
            view2.measure(iC, iC2);
        }
    }

    public View a(boolean z) {
        int startAfterPadding = this.u.getStartAfterPadding();
        int endAfterPadding = this.u.getEndAfterPadding();
        View view2 = null;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            int decoratedStart = this.u.getDecoratedStart(childAt);
            int decoratedEnd = this.u.getDecoratedEnd(childAt);
            if (decoratedEnd > startAfterPadding && decoratedStart < endAfterPadding) {
                if (decoratedEnd <= endAfterPadding || !z) {
                    return childAt;
                }
                if (view2 == null) {
                    view2 = childAt;
                }
            }
        }
        return view2;
    }

    public final void a(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int endAfterPadding;
        int iB = b(Integer.MIN_VALUE);
        if (iB != Integer.MIN_VALUE && (endAfterPadding = this.u.getEndAfterPadding() - iB) > 0) {
            int i = endAfterPadding - (-a(-endAfterPadding, recycler, state));
            if (!z || i <= 0) {
                return;
            }
            this.u.offsetChildren(i);
        }
    }

    public StaggeredGridLayoutManager(int i, int i2) {
        this.w = i2;
        setSpanCount(i);
        this.y = new i8();
        this.u = OrientationHelper.createOrientationHelper(this, this.w);
        this.v = OrientationHelper.createOrientationHelper(this, 1 - this.w);
    }

    public final int a(RecyclerView.Recycler recycler, i8 i8Var, RecyclerView.State state) {
        int i;
        int i2;
        int startAfterPadding;
        int iB;
        c cVar;
        int i3;
        int i4;
        int decoratedMeasurement;
        int decoratedMeasurement2;
        LayoutParams layoutParams;
        int i5;
        boolean z;
        int i6;
        int i7;
        int i8;
        RecyclerView.Recycler recycler2 = recycler;
        int i9 = 0;
        this.B.set(0, this.s, true);
        if (this.y.i) {
            i2 = i8Var.e == 1 ? Integer.MAX_VALUE : Integer.MIN_VALUE;
        } else {
            if (i8Var.e == 1) {
                i = i8Var.g + i8Var.b;
            } else {
                i = i8Var.f - i8Var.b;
            }
            i2 = i;
        }
        c(i8Var.e, i2);
        if (this.A) {
            startAfterPadding = this.u.getEndAfterPadding();
        } else {
            startAfterPadding = this.u.getStartAfterPadding();
        }
        int i10 = startAfterPadding;
        boolean z2 = false;
        while (true) {
            int i11 = i8Var.c;
            if (((i11 < 0 || i11 >= state.getItemCount()) ? i9 : 1) == 0 || (!this.y.i && this.B.isEmpty())) {
                break;
            }
            View viewForPosition = recycler2.getViewForPosition(i8Var.c);
            i8Var.c += i8Var.d;
            LayoutParams layoutParams2 = (LayoutParams) viewForPosition.getLayoutParams();
            int viewLayoutPosition = layoutParams2.getViewLayoutPosition();
            int[] iArr = this.E.a;
            int i12 = (iArr == null || viewLayoutPosition >= iArr.length) ? -1 : iArr[viewLayoutPosition];
            int i13 = i12 == -1 ? 1 : i9;
            if (i13 != 0) {
                if (layoutParams2.f) {
                    cVar = this.t[i9];
                } else {
                    if (d(i8Var.e)) {
                        i8 = this.s - 1;
                        i6 = -1;
                        i7 = -1;
                    } else {
                        i6 = this.s;
                        i7 = 1;
                        i8 = i9;
                    }
                    c cVar2 = null;
                    if (i8Var.e == 1) {
                        int startAfterPadding2 = this.u.getStartAfterPadding();
                        int i14 = Integer.MAX_VALUE;
                        while (i8 != i6) {
                            c cVar3 = this.t[i8];
                            int iA = cVar3.a(startAfterPadding2);
                            if (iA < i14) {
                                cVar2 = cVar3;
                                i14 = iA;
                            }
                            i8 += i7;
                        }
                    } else {
                        int endAfterPadding = this.u.getEndAfterPadding();
                        int i15 = Integer.MIN_VALUE;
                        while (i8 != i6) {
                            c cVar4 = this.t[i8];
                            int iB2 = cVar4.b(endAfterPadding);
                            if (iB2 > i15) {
                                cVar2 = cVar4;
                                i15 = iB2;
                            }
                            i8 += i7;
                        }
                    }
                    cVar = cVar2;
                }
                LazySpanLookup lazySpanLookup = this.E;
                lazySpanLookup.a(viewLayoutPosition);
                lazySpanLookup.a[viewLayoutPosition] = cVar.e;
            } else {
                cVar = this.t[i12];
            }
            c cVar5 = cVar;
            layoutParams2.e = cVar5;
            if (i8Var.e == 1) {
                addView(viewForPosition);
            } else {
                addView(viewForPosition, 0);
            }
            if (layoutParams2.f) {
                if (this.w == 1) {
                    a(viewForPosition, this.J, RecyclerView.LayoutManager.getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingBottom() + getPaddingTop(), ((ViewGroup.MarginLayoutParams) layoutParams2).height, true), false);
                } else {
                    a(viewForPosition, RecyclerView.LayoutManager.getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingRight() + getPaddingLeft(), ((ViewGroup.MarginLayoutParams) layoutParams2).width, true), this.J, false);
                }
            } else if (this.w == 1) {
                a(viewForPosition, RecyclerView.LayoutManager.getChildMeasureSpec(this.x, getWidthMode(), 0, ((ViewGroup.MarginLayoutParams) layoutParams2).width, false), RecyclerView.LayoutManager.getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingBottom() + getPaddingTop(), ((ViewGroup.MarginLayoutParams) layoutParams2).height, true), false);
            } else {
                a(viewForPosition, RecyclerView.LayoutManager.getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingRight() + getPaddingLeft(), ((ViewGroup.MarginLayoutParams) layoutParams2).width, true), RecyclerView.LayoutManager.getChildMeasureSpec(this.x, getHeightMode(), 0, ((ViewGroup.MarginLayoutParams) layoutParams2).height, false), false);
            }
            if (i8Var.e == 1) {
                int iB3 = layoutParams2.f ? b(i10) : cVar5.a(i10);
                int decoratedMeasurement3 = this.u.getDecoratedMeasurement(viewForPosition) + iB3;
                if (i13 != 0 && layoutParams2.f) {
                    LazySpanLookup.FullSpanItem fullSpanItem = new LazySpanLookup.FullSpanItem();
                    fullSpanItem.c = new int[this.s];
                    for (int i16 = 0; i16 < this.s; i16++) {
                        fullSpanItem.c[i16] = iB3 - this.t[i16].a(iB3);
                    }
                    fullSpanItem.b = -1;
                    fullSpanItem.a = viewLayoutPosition;
                    this.E.a(fullSpanItem);
                }
                i4 = iB3;
                i3 = decoratedMeasurement3;
            } else {
                int iC = layoutParams2.f ? c(i10) : cVar5.b(i10);
                int decoratedMeasurement4 = iC - this.u.getDecoratedMeasurement(viewForPosition);
                if (i13 != 0 && layoutParams2.f) {
                    LazySpanLookup.FullSpanItem fullSpanItem2 = new LazySpanLookup.FullSpanItem();
                    fullSpanItem2.c = new int[this.s];
                    for (int i17 = 0; i17 < this.s; i17++) {
                        fullSpanItem2.c[i17] = this.t[i17].b(iC) - iC;
                    }
                    fullSpanItem2.b = 1;
                    fullSpanItem2.a = viewLayoutPosition;
                    this.E.a(fullSpanItem2);
                }
                i3 = iC;
                i4 = decoratedMeasurement4;
            }
            if (layoutParams2.f && i8Var.d == -1) {
                if (i13 != 0) {
                    this.M = true;
                } else {
                    if (i8Var.e == 1) {
                        int iA2 = this.t[0].a(Integer.MIN_VALUE);
                        for (int i18 = 1; i18 < this.s; i18++) {
                            if (this.t[i18].a(Integer.MIN_VALUE) != iA2) {
                                z = false;
                                break;
                            }
                        }
                        z = true;
                    } else {
                        int iB4 = this.t[0].b(Integer.MIN_VALUE);
                        for (int i19 = 1; i19 < this.s; i19++) {
                            if (this.t[i19].b(Integer.MIN_VALUE) != iB4) {
                                z = false;
                                break;
                            }
                        }
                        z = true;
                    }
                    if (!z) {
                        LazySpanLookup.FullSpanItem fullSpanItemC = this.E.c(viewLayoutPosition);
                        if (fullSpanItemC != null) {
                            fullSpanItemC.d = true;
                        }
                        this.M = true;
                    }
                }
            }
            if (i8Var.e == 1) {
                if (layoutParams2.f) {
                    int i20 = this.s;
                    while (true) {
                        i20--;
                        if (i20 < 0) {
                            break;
                        }
                        this.t[i20].a(viewForPosition);
                    }
                } else {
                    layoutParams2.e.a(viewForPosition);
                }
            } else if (layoutParams2.f) {
                int i21 = this.s;
                while (true) {
                    i21--;
                    if (i21 < 0) {
                        break;
                    }
                    this.t[i21].c(viewForPosition);
                }
            } else {
                layoutParams2.e.c(viewForPosition);
            }
            if (isLayoutRTL() && this.w == 1) {
                int endAfterPadding2 = layoutParams2.f ? this.v.getEndAfterPadding() : this.v.getEndAfterPadding() - (((this.s - 1) - cVar5.e) * this.x);
                decoratedMeasurement2 = endAfterPadding2;
                decoratedMeasurement = endAfterPadding2 - this.v.getDecoratedMeasurement(viewForPosition);
            } else {
                int startAfterPadding3 = layoutParams2.f ? this.v.getStartAfterPadding() : (cVar5.e * this.x) + this.v.getStartAfterPadding();
                decoratedMeasurement = startAfterPadding3;
                decoratedMeasurement2 = this.v.getDecoratedMeasurement(viewForPosition) + startAfterPadding3;
            }
            if (this.w == 1) {
                layoutDecoratedWithMargins(viewForPosition, decoratedMeasurement, i4, decoratedMeasurement2, i3);
                layoutParams = layoutParams2;
            } else {
                int i22 = i4;
                int i23 = i3;
                layoutParams = layoutParams2;
                layoutDecoratedWithMargins(viewForPosition, i22, decoratedMeasurement, i23, decoratedMeasurement2);
            }
            if (layoutParams.f) {
                c(this.y.e, i2);
            } else {
                a(cVar5, this.y.e, i2);
            }
            a(recycler, this.y);
            if (!this.y.h || !viewForPosition.hasFocusable()) {
                i5 = 0;
            } else if (layoutParams.f) {
                this.B.clear();
                i5 = 0;
            } else {
                i5 = 0;
                this.B.set(cVar5.e, false);
            }
            recycler2 = recycler;
            i9 = i5;
            z2 = true;
        }
        RecyclerView.Recycler recycler3 = recycler2;
        int i24 = i9;
        if (!z2) {
            a(recycler3, this.y);
        }
        if (this.y.e == -1) {
            iB = this.u.getStartAfterPadding() - c(this.u.getStartAfterPadding());
        } else {
            iB = b(this.u.getEndAfterPadding()) - this.u.getEndAfterPadding();
        }
        return iB > 0 ? Math.min(i8Var.b, iB) : i24;
    }

    public final int b(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return q5.a(state, this.u, b(!this.N), a(!this.N), this, this.N, this.A);
    }

    public View b(boolean z) {
        int startAfterPadding = this.u.getStartAfterPadding();
        int endAfterPadding = this.u.getEndAfterPadding();
        int childCount = getChildCount();
        View view2 = null;
        for (int i = 0; i < childCount; i++) {
            View childAt = getChildAt(i);
            int decoratedStart = this.u.getDecoratedStart(childAt);
            if (this.u.getDecoratedEnd(childAt) > startAfterPadding && decoratedStart < endAfterPadding) {
                if (decoratedStart >= startAfterPadding || !z) {
                    return childAt;
                }
                if (view2 == null) {
                    view2 = childAt;
                }
            }
        }
        return view2;
    }

    public final void b(RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int startAfterPadding;
        int iC = c(Integer.MAX_VALUE);
        if (iC != Integer.MAX_VALUE && (startAfterPadding = iC - this.u.getStartAfterPadding()) > 0) {
            int iA = startAfterPadding - a(startAfterPadding, recycler, state);
            if (!z || iA <= 0) {
                return;
            }
            this.u.offsetChildren(-iA);
        }
    }

    public final void e(int i) {
        i8 i8Var = this.y;
        i8Var.e = i;
        i8Var.d = this.A != (i == -1) ? -1 : 1;
    }

    public final void b(int i, RecyclerView.State state) {
        int totalSpace;
        int totalSpace2;
        int targetScrollPosition;
        i8 i8Var = this.y;
        boolean z = false;
        i8Var.b = 0;
        i8Var.c = i;
        if (!isSmoothScrolling() || (targetScrollPosition = state.getTargetScrollPosition()) == -1) {
            totalSpace = 0;
            totalSpace2 = 0;
        } else {
            if (this.A == (targetScrollPosition < i)) {
                totalSpace = this.u.getTotalSpace();
                totalSpace2 = 0;
            } else {
                totalSpace2 = this.u.getTotalSpace();
                totalSpace = 0;
            }
        }
        if (getClipToPadding()) {
            this.y.f = this.u.getStartAfterPadding() - totalSpace2;
            this.y.g = this.u.getEndAfterPadding() + totalSpace;
        } else {
            this.y.g = this.u.getEnd() + totalSpace;
            this.y.f = -totalSpace2;
        }
        i8 i8Var2 = this.y;
        i8Var2.h = false;
        i8Var2.a = true;
        if (this.u.getMode() == 0 && this.u.getEnd() == 0) {
            z = true;
        }
        i8Var2.i = z;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0043 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void b(int r7, int r8, int r9) {
        /*
            r6 = this;
            boolean r0 = r6.A
            if (r0 == 0) goto L9
            int r0 = r6.d()
            goto Ld
        L9:
            int r0 = r6.c()
        Ld:
            r1 = 8
            if (r9 != r1) goto L1a
            if (r7 >= r8) goto L16
            int r2 = r8 + 1
            goto L1c
        L16:
            int r2 = r7 + 1
            r3 = r8
            goto L1d
        L1a:
            int r2 = r7 + r8
        L1c:
            r3 = r7
        L1d:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r4 = r6.E
            r4.d(r3)
            r4 = 1
            if (r9 == r4) goto L3c
            r5 = 2
            if (r9 == r5) goto L36
            if (r9 == r1) goto L2b
            goto L41
        L2b:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.E
            r9.b(r7, r4)
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r7 = r6.E
            r7.a(r8, r4)
            goto L41
        L36:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.E
            r9.b(r7, r8)
            goto L41
        L3c:
            android.support.v7.widget.StaggeredGridLayoutManager$LazySpanLookup r9 = r6.E
            r9.a(r7, r8)
        L41:
            if (r2 > r0) goto L44
            return
        L44:
            boolean r7 = r6.A
            if (r7 == 0) goto L4d
            int r7 = r6.c()
            goto L51
        L4d:
            int r7 = r6.d()
        L51:
            if (r3 > r7) goto L56
            r6.requestLayout()
        L56:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.StaggeredGridLayoutManager.b(int, int, int):void");
    }

    public final int b(int i) {
        int iA = this.t[0].a(i);
        for (int i2 = 1; i2 < this.s; i2++) {
            int iA2 = this.t[i2].a(i);
            if (iA2 > iA) {
                iA = iA2;
            }
        }
        return iA;
    }

    public final void b(RecyclerView.Recycler recycler, int i) {
        while (getChildCount() > 0) {
            View childAt = getChildAt(0);
            if (this.u.getDecoratedEnd(childAt) > i || this.u.getTransformedEndWithDecoration(childAt) > i) {
                return;
            }
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (layoutParams.f) {
                for (int i2 = 0; i2 < this.s; i2++) {
                    if (this.t[i2].a.size() == 1) {
                        return;
                    }
                }
                for (int i3 = 0; i3 < this.s; i3++) {
                    this.t[i3].g();
                }
            } else if (layoutParams.e.a.size() == 1) {
                return;
            } else {
                layoutParams.e.g();
            }
            removeAndRecycleView(childAt, recycler);
        }
    }

    public final void a(RecyclerView.Recycler recycler, i8 i8Var) {
        int iMin;
        int iMin2;
        if (!i8Var.a || i8Var.i) {
            return;
        }
        if (i8Var.b == 0) {
            if (i8Var.e == -1) {
                a(recycler, i8Var.g);
                return;
            } else {
                b(recycler, i8Var.f);
                return;
            }
        }
        int i = 1;
        if (i8Var.e == -1) {
            int i2 = i8Var.f;
            int iB = this.t[0].b(i2);
            while (i < this.s) {
                int iB2 = this.t[i].b(i2);
                if (iB2 > iB) {
                    iB = iB2;
                }
                i++;
            }
            int i3 = i2 - iB;
            if (i3 < 0) {
                iMin2 = i8Var.g;
            } else {
                iMin2 = i8Var.g - Math.min(i3, i8Var.b);
            }
            a(recycler, iMin2);
            return;
        }
        int i4 = i8Var.g;
        int iA = this.t[0].a(i4);
        while (i < this.s) {
            int iA2 = this.t[i].a(i4);
            if (iA2 < iA) {
                iA = iA2;
            }
            i++;
        }
        int i5 = iA - i8Var.g;
        if (i5 < 0) {
            iMin = i8Var.f;
        } else {
            iMin = Math.min(i5, i8Var.b) + i8Var.f;
        }
        b(recycler, iMin);
    }

    public final void a(RecyclerView.Recycler recycler, int i) {
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            if (this.u.getDecoratedStart(childAt) < i || this.u.getTransformedStartWithDecoration(childAt) < i) {
                return;
            }
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            if (layoutParams.f) {
                for (int i2 = 0; i2 < this.s; i2++) {
                    if (this.t[i2].a.size() == 1) {
                        return;
                    }
                }
                for (int i3 = 0; i3 < this.s; i3++) {
                    this.t[i3].f();
                }
            } else if (layoutParams.e.a.size() == 1) {
                return;
            } else {
                layoutParams.e.f();
            }
            removeAndRecycleView(childAt, recycler);
        }
    }

    public final int c(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        return q5.b(state, this.u, b(!this.N), a(!this.N), this, this.N);
    }

    public final int c(int i, int i2, int i3) {
        if (i2 == 0 && i3 == 0) {
            return i;
        }
        int mode = View.MeasureSpec.getMode(i);
        return (mode == Integer.MIN_VALUE || mode == 1073741824) ? View.MeasureSpec.makeMeasureSpec(Math.max(0, (View.MeasureSpec.getSize(i) - i2) - i3), mode) : i;
    }

    public final void c(int i, int i2) {
        for (int i3 = 0; i3 < this.s; i3++) {
            if (!this.t[i3].a.isEmpty()) {
                a(this.t[i3], i, i2);
            }
        }
    }

    public final int c(int i) {
        int iB = this.t[0].b(i);
        for (int i2 = 1; i2 < this.s; i2++) {
            int iB2 = this.t[i2].b(i);
            if (iB2 < iB) {
                iB = iB2;
            }
        }
        return iB;
    }

    public final int a(int i) {
        if (getChildCount() == 0) {
            return this.A ? 1 : -1;
        }
        return (i < c()) != this.A ? -1 : 1;
    }

    public int c() {
        if (getChildCount() == 0) {
            return 0;
        }
        return getPosition(getChildAt(0));
    }

    public void a(int i, RecyclerView.State state) {
        int i2;
        int iC;
        if (i > 0) {
            iC = d();
            i2 = 1;
        } else {
            i2 = -1;
            iC = c();
        }
        this.y.a = true;
        b(iC, state);
        e(i2);
        i8 i8Var = this.y;
        i8Var.c = iC + i8Var.d;
        i8Var.b = Math.abs(i);
    }

    public int a(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || i == 0) {
            return 0;
        }
        a(i, state);
        int iA = a(recycler, this.y, state);
        if (this.y.b >= iA) {
            i = i < 0 ? -iA : iA;
        }
        this.u.offsetChildren(-i);
        this.G = this.A;
        i8 i8Var = this.y;
        i8Var.b = 0;
        a(recycler, i8Var);
        return i;
    }

    public final void a(c cVar, int i, int i2) {
        int i3 = cVar.d;
        if (i == -1) {
            int i4 = cVar.b;
            if (i4 == Integer.MIN_VALUE) {
                cVar.b();
                i4 = cVar.b;
            }
            if (i4 + i3 <= i2) {
                this.B.set(cVar.e, false);
                return;
            }
            return;
        }
        int i5 = cVar.c;
        if (i5 == Integer.MIN_VALUE) {
            cVar.a();
            i5 = cVar.c;
        }
        if (i5 - i3 >= i2) {
            this.B.set(cVar.e, false);
        }
    }
}
