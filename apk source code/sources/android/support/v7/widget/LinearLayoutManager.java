package android.support.v7.widget;

import android.content.Context;
import android.graphics.PointF;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import defpackage.g9;
import defpackage.q5;
import java.util.List;

/* loaded from: classes.dex */
public class LinearLayoutManager extends RecyclerView.LayoutManager implements ItemTouchHelper.ViewDropHandler, RecyclerView.SmoothScroller.ScrollVectorProvider {
    public static final int HORIZONTAL = 0;
    public static final int INVALID_OFFSET = Integer.MIN_VALUE;
    public static final int VERTICAL = 1;
    public int A;
    public int B;
    public boolean C;
    public SavedState D;
    public final a E;
    public final LayoutChunkResult F;
    public int G;
    public int s;
    public b t;
    public OrientationHelper u;
    public boolean v;
    public boolean w;
    public boolean x;
    public boolean y;
    public boolean z;

    public static class LayoutChunkResult {
        public int mConsumed;
        public boolean mFinished;
        public boolean mFocusable;
        public boolean mIgnoreConsumed;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class SavedState implements Parcelable {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        public int a;
        public int b;
        public boolean c;

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

        public boolean a() {
            return this.a >= 0;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
            parcel.writeInt(this.b);
            parcel.writeInt(this.c ? 1 : 0);
        }

        public SavedState(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = parcel.readInt();
            this.c = parcel.readInt() == 1;
        }

        public SavedState(SavedState savedState) {
            this.a = savedState.a;
            this.b = savedState.b;
            this.c = savedState.c;
        }
    }

    public static class b {
        public int b;
        public int c;
        public int d;
        public int e;
        public int f;
        public int g;
        public boolean i;
        public int j;
        public boolean l;
        public boolean a = true;
        public int h = 0;
        public List<RecyclerView.ViewHolder> k = null;

        public boolean a(RecyclerView.State state) {
            int i = this.d;
            return i >= 0 && i < state.getItemCount();
        }

        public View a(RecyclerView.Recycler recycler) {
            List<RecyclerView.ViewHolder> list = this.k;
            if (list == null) {
                View viewForPosition = recycler.getViewForPosition(this.d);
                this.d += this.e;
                return viewForPosition;
            }
            int size = list.size();
            for (int i = 0; i < size; i++) {
                View view2 = this.k.get(i).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view2.getLayoutParams();
                if (!layoutParams.isItemRemoved() && this.d == layoutParams.getViewLayoutPosition()) {
                    a(view2);
                    return view2;
                }
            }
            return null;
        }

        public void a(View view2) {
            int viewLayoutPosition;
            int size = this.k.size();
            View view3 = null;
            int i = Integer.MAX_VALUE;
            for (int i2 = 0; i2 < size; i2++) {
                View view4 = this.k.get(i2).itemView;
                RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view4.getLayoutParams();
                if (view4 != view2 && !layoutParams.isItemRemoved() && (viewLayoutPosition = (layoutParams.getViewLayoutPosition() - this.d) * this.e) >= 0 && viewLayoutPosition < i) {
                    view3 = view4;
                    if (viewLayoutPosition == 0) {
                        break;
                    } else {
                        i = viewLayoutPosition;
                    }
                }
            }
            if (view3 == null) {
                this.d = -1;
            } else {
                this.d = ((RecyclerView.LayoutParams) view3.getLayoutParams()).getViewLayoutPosition();
            }
        }
    }

    public LinearLayoutManager(Context context) {
        this(context, 1, false);
    }

    public final int a(int i, RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int endAfterPadding;
        int endAfterPadding2 = this.u.getEndAfterPadding() - i;
        if (endAfterPadding2 <= 0) {
            return 0;
        }
        int i2 = -a(-endAfterPadding2, recycler, state);
        int i3 = i + i2;
        if (!z || (endAfterPadding = this.u.getEndAfterPadding() - i3) <= 0) {
            return i2;
        }
        this.u.offsetChildren(endAfterPadding);
        return endAfterPadding + i2;
    }

    public void a(RecyclerView.Recycler recycler, RecyclerView.State state, a aVar, int i) {
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void assertNotInLayoutOrScroll(String str) {
        if (this.D == null) {
            super.assertNotInLayoutOrScroll(str);
        }
    }

    public final int b(int i, RecyclerView.Recycler recycler, RecyclerView.State state, boolean z) {
        int startAfterPadding;
        int startAfterPadding2 = i - this.u.getStartAfterPadding();
        if (startAfterPadding2 <= 0) {
            return 0;
        }
        int i2 = -a(startAfterPadding2, recycler, state);
        int i3 = i + i2;
        if (!z || (startAfterPadding = i3 - this.u.getStartAfterPadding()) <= 0) {
            return i2;
        }
        this.u.offsetChildren(-startAfterPadding);
        return i2 - startAfterPadding;
    }

    public final int c(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        b();
        return q5.b(state, this.u, b(!this.z, true), a(!this.z, true), this, this.z);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public boolean canScrollHorizontally() {
        return this.s == 0;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public boolean canScrollVertically() {
        return this.s == 1;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void collectAdjacentPrefetchPositions(int i, int i2, RecyclerView.State state, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        if (this.s != 0) {
            i = i2;
        }
        if (getChildCount() == 0 || i == 0) {
            return;
        }
        b();
        a(i > 0 ? 1 : -1, Math.abs(i), true, state);
        a(state, this.t, layoutPrefetchRegistry);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void collectInitialPrefetchPositions(int i, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        boolean z;
        int i2;
        SavedState savedState = this.D;
        if (savedState == null || !savedState.a()) {
            f();
            z = this.x;
            i2 = this.A;
            if (i2 == -1) {
                i2 = z ? i - 1 : 0;
            }
        } else {
            SavedState savedState2 = this.D;
            z = savedState2.c;
            i2 = savedState2.a;
        }
        int i3 = z ? -1 : 1;
        for (int i4 = 0; i4 < this.G && i2 >= 0 && i2 < i; i4++) {
            layoutPrefetchRegistry.addPosition(i2, 0);
            i2 += i3;
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
        if (getChildCount() == 0) {
            return null;
        }
        int i2 = (i < getPosition(getChildAt(0))) != this.x ? -1 : 1;
        return this.s == 0 ? new PointF(i2, 0.0f) : new PointF(0.0f, i2);
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

    public final void d(int i, int i2) {
        this.t.c = this.u.getEndAfterPadding() - i2;
        this.t.e = this.x ? -1 : 1;
        b bVar = this.t;
        bVar.d = i;
        bVar.f = 1;
        bVar.b = i2;
        bVar.g = Integer.MIN_VALUE;
    }

    public final void e(int i, int i2) {
        this.t.c = i2 - this.u.getStartAfterPadding();
        b bVar = this.t;
        bVar.d = i;
        bVar.e = this.x ? 1 : -1;
        b bVar2 = this.t;
        bVar2.f = -1;
        bVar2.b = i2;
        bVar2.g = Integer.MIN_VALUE;
    }

    public final void f() {
        if (this.s == 1 || !isLayoutRTL()) {
            this.x = this.w;
        } else {
            this.x = !this.w;
        }
    }

    public int findFirstCompletelyVisibleItemPosition() {
        View viewA = a(0, getChildCount(), true, false);
        if (viewA == null) {
            return -1;
        }
        return getPosition(viewA);
    }

    public int findFirstVisibleItemPosition() {
        View viewA = a(0, getChildCount(), false, true);
        if (viewA == null) {
            return -1;
        }
        return getPosition(viewA);
    }

    public int findLastCompletelyVisibleItemPosition() {
        View viewA = a(getChildCount() - 1, -1, true, false);
        if (viewA == null) {
            return -1;
        }
        return getPosition(viewA);
    }

    public int findLastVisibleItemPosition() {
        View viewA = a(getChildCount() - 1, -1, false, true);
        if (viewA == null) {
            return -1;
        }
        return getPosition(viewA);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public View findViewByPosition(int i) {
        int childCount = getChildCount();
        if (childCount == 0) {
            return null;
        }
        int position = i - getPosition(getChildAt(0));
        if (position >= 0 && position < childCount) {
            View childAt = getChildAt(position);
            if (getPosition(childAt) == i) {
                return childAt;
            }
        }
        return super.findViewByPosition(i);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(-2, -2);
    }

    public int getExtraLayoutSpace(RecyclerView.State state) {
        if (state.hasTargetScrollPosition()) {
            return this.u.getTotalSpace();
        }
        return 0;
    }

    public int getInitialPrefetchItemCount() {
        return this.G;
    }

    public int getOrientation() {
        return this.s;
    }

    public boolean getRecycleChildrenOnDetach() {
        return this.C;
    }

    public boolean getReverseLayout() {
        return this.w;
    }

    public boolean getStackFromEnd() {
        return this.y;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public boolean isAutoMeasureEnabled() {
        return true;
    }

    public boolean isLayoutRTL() {
        return getLayoutDirection() == 1;
    }

    public boolean isSmoothScrollbarEnabled() {
        return this.z;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onDetachedFromWindow(RecyclerView recyclerView, RecyclerView.Recycler recycler) {
        super.onDetachedFromWindow(recyclerView, recycler);
        if (this.C) {
            removeAndRecycleAllViews(recycler);
            recycler.clear();
        }
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public View onFocusSearchFailed(View view2, int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int iA;
        f();
        if (getChildCount() == 0 || (iA = a(i)) == Integer.MIN_VALUE) {
            return null;
        }
        b();
        b();
        a(iA, (int) (this.u.getTotalSpace() * 0.33333334f), false, state);
        b bVar = this.t;
        bVar.g = Integer.MIN_VALUE;
        bVar.a = false;
        a(recycler, bVar, state, true);
        View viewC = iA == -1 ? this.x ? c(getChildCount() - 1, -1) : c(0, getChildCount()) : this.x ? c(0, getChildCount()) : c(getChildCount() - 1, -1);
        View viewD = iA == -1 ? d() : c();
        if (!viewD.hasFocusable()) {
            return viewC;
        }
        if (viewC == null) {
            return null;
        }
        return viewD;
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        if (getChildCount() > 0) {
            accessibilityEvent.setFromIndex(findFirstVisibleItemPosition());
            accessibilityEvent.setToIndex(findLastVisibleItemPosition());
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:134:0x0233  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0184  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01bc  */
    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void onLayoutChildren(android.support.v7.widget.RecyclerView.Recycler r17, android.support.v7.widget.RecyclerView.State r18) {
        /*
            Method dump skipped, instructions count: 1085
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.LinearLayoutManager.onLayoutChildren(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State):void");
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onLayoutCompleted(RecyclerView.State state) {
        super.onLayoutCompleted(state);
        this.D = null;
        this.A = -1;
        this.B = Integer.MIN_VALUE;
        this.E.b();
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void onRestoreInstanceState(Parcelable parcelable) {
        if (parcelable instanceof SavedState) {
            this.D = (SavedState) parcelable;
            requestLayout();
        }
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public Parcelable onSaveInstanceState() {
        if (this.D != null) {
            return new SavedState(this.D);
        }
        SavedState savedState = new SavedState();
        if (getChildCount() > 0) {
            b();
            boolean z = this.v ^ this.x;
            savedState.c = z;
            if (z) {
                View viewC = c();
                savedState.b = this.u.getEndAfterPadding() - this.u.getDecoratedEnd(viewC);
                savedState.a = getPosition(viewC);
            } else {
                View viewD = d();
                savedState.a = getPosition(viewD);
                savedState.b = this.u.getDecoratedStart(viewD) - this.u.getStartAfterPadding();
            }
        } else {
            savedState.a = -1;
        }
        return savedState;
    }

    @Override // android.support.v7.widget.helper.ItemTouchHelper.ViewDropHandler
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void prepareForDrop(View view2, View view3, int i, int i2) {
        assertNotInLayoutOrScroll("Cannot drop a view during a scroll or layout calculation");
        b();
        f();
        int position = getPosition(view2);
        int position2 = getPosition(view3);
        char c = position < position2 ? (char) 1 : (char) 65535;
        if (this.x) {
            if (c == 1) {
                scrollToPositionWithOffset(position2, this.u.getEndAfterPadding() - (this.u.getDecoratedMeasurement(view2) + this.u.getDecoratedStart(view3)));
                return;
            } else {
                scrollToPositionWithOffset(position2, this.u.getEndAfterPadding() - this.u.getDecoratedEnd(view3));
                return;
            }
        }
        if (c == 65535) {
            scrollToPositionWithOffset(position2, this.u.getDecoratedStart(view3));
        } else {
            scrollToPositionWithOffset(position2, this.u.getDecoratedEnd(view3) - this.u.getDecoratedMeasurement(view2));
        }
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int scrollHorizontallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.s == 1) {
            return 0;
        }
        return a(i, recycler, state);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void scrollToPosition(int i) {
        this.A = i;
        this.B = Integer.MIN_VALUE;
        SavedState savedState = this.D;
        if (savedState != null) {
            savedState.a = -1;
        }
        requestLayout();
    }

    public void scrollToPositionWithOffset(int i, int i2) {
        this.A = i;
        this.B = i2;
        SavedState savedState = this.D;
        if (savedState != null) {
            savedState.a = -1;
        }
        requestLayout();
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public int scrollVerticallyBy(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (this.s == 0) {
            return 0;
        }
        return a(i, recycler, state);
    }

    public void setInitialPrefetchItemCount(int i) {
        this.G = i;
    }

    public void setOrientation(int i) {
        if (i != 0 && i != 1) {
            throw new IllegalArgumentException(g9.b("invalid orientation:", i));
        }
        assertNotInLayoutOrScroll(null);
        if (i != this.s || this.u == null) {
            OrientationHelper orientationHelperCreateOrientationHelper = OrientationHelper.createOrientationHelper(this, i);
            this.u = orientationHelperCreateOrientationHelper;
            this.E.a = orientationHelperCreateOrientationHelper;
            this.s = i;
            requestLayout();
        }
    }

    public void setRecycleChildrenOnDetach(boolean z) {
        this.C = z;
    }

    public void setReverseLayout(boolean z) {
        assertNotInLayoutOrScroll(null);
        if (z == this.w) {
            return;
        }
        this.w = z;
        requestLayout();
    }

    public void setSmoothScrollbarEnabled(boolean z) {
        this.z = z;
    }

    public void setStackFromEnd(boolean z) {
        assertNotInLayoutOrScroll(null);
        if (this.y == z) {
            return;
        }
        this.y = z;
        requestLayout();
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int i) {
        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext());
        linearSmoothScroller.setTargetPosition(i);
        startSmoothScroll(linearSmoothScroller);
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public boolean supportsPredictiveItemAnimations() {
        return this.D == null && this.v == this.y;
    }

    public LinearLayoutManager(Context context, int i, boolean z) {
        this.s = 1;
        this.w = false;
        this.x = false;
        this.y = false;
        this.z = true;
        this.A = -1;
        this.B = Integer.MIN_VALUE;
        this.D = null;
        this.E = new a();
        this.F = new LayoutChunkResult();
        this.G = 2;
        setOrientation(i);
        setReverseLayout(z);
    }

    public static class a {
        public OrientationHelper a;
        public int b;
        public int c;
        public boolean d;
        public boolean e;

        public a() {
            b();
        }

        public void a() {
            this.c = this.d ? this.a.getEndAfterPadding() : this.a.getStartAfterPadding();
        }

        public void b() {
            this.b = -1;
            this.c = Integer.MIN_VALUE;
            this.d = false;
            this.e = false;
        }

        public String toString() {
            StringBuilder sbA = g9.a("AnchorInfo{mPosition=");
            sbA.append(this.b);
            sbA.append(", mCoordinate=");
            sbA.append(this.c);
            sbA.append(", mLayoutFromEnd=");
            sbA.append(this.d);
            sbA.append(", mValid=");
            sbA.append(this.e);
            sbA.append('}');
            return sbA.toString();
        }

        public void a(View view2, int i) {
            if (this.d) {
                this.c = this.a.getTotalSpaceChange() + this.a.getDecoratedEnd(view2);
            } else {
                this.c = this.a.getDecoratedStart(view2);
            }
            this.b = i;
        }

        public void b(View view2, int i) {
            int totalSpaceChange = this.a.getTotalSpaceChange();
            if (totalSpaceChange >= 0) {
                a(view2, i);
                return;
            }
            this.b = i;
            if (this.d) {
                int endAfterPadding = (this.a.getEndAfterPadding() - totalSpaceChange) - this.a.getDecoratedEnd(view2);
                this.c = this.a.getEndAfterPadding() - endAfterPadding;
                if (endAfterPadding > 0) {
                    int decoratedMeasurement = this.c - this.a.getDecoratedMeasurement(view2);
                    int startAfterPadding = this.a.getStartAfterPadding();
                    int iMin = decoratedMeasurement - (Math.min(this.a.getDecoratedStart(view2) - startAfterPadding, 0) + startAfterPadding);
                    if (iMin < 0) {
                        this.c = Math.min(endAfterPadding, -iMin) + this.c;
                        return;
                    }
                    return;
                }
                return;
            }
            int decoratedStart = this.a.getDecoratedStart(view2);
            int startAfterPadding2 = decoratedStart - this.a.getStartAfterPadding();
            this.c = decoratedStart;
            if (startAfterPadding2 > 0) {
                int endAfterPadding2 = (this.a.getEndAfterPadding() - Math.min(0, (this.a.getEndAfterPadding() - totalSpaceChange) - this.a.getDecoratedEnd(view2))) - (this.a.getDecoratedMeasurement(view2) + decoratedStart);
                if (endAfterPadding2 < 0) {
                    this.c -= Math.min(startAfterPadding2, -endAfterPadding2);
                }
            }
        }
    }

    public final int a(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        b();
        return q5.a(state, this.u, b(!this.z, true), a(!this.z, true), this, this.z);
    }

    public void b() {
        if (this.t == null) {
            this.t = new b();
        }
    }

    public final View c() {
        return getChildAt(this.x ? 0 : getChildCount() - 1);
    }

    public final View d() {
        return getChildAt(this.x ? getChildCount() - 1 : 0);
    }

    public boolean e() {
        return this.u.getMode() == 0 && this.u.getEnd() == 0;
    }

    public final int b(RecyclerView.State state) {
        if (getChildCount() == 0) {
            return 0;
        }
        b();
        return q5.a(state, this.u, b(!this.z, true), a(!this.z, true), this, this.z, this.x);
    }

    public View c(int i, int i2) {
        int i3;
        int i4;
        b();
        if ((i2 > i ? (char) 1 : i2 < i ? (char) 65535 : (char) 0) == 0) {
            return getChildAt(i);
        }
        if (this.u.getDecoratedStart(getChildAt(i)) < this.u.getStartAfterPadding()) {
            i3 = 16644;
            i4 = 16388;
        } else {
            i3 = 4161;
            i4 = FragmentTransaction.TRANSIT_FRAGMENT_OPEN;
        }
        if (this.s == 0) {
            return this.e.a(i, i2, i3, i4);
        }
        return this.f.a(i, i2, i3, i4);
    }

    public final void a(int i, int i2, boolean z, RecyclerView.State state) {
        int startAfterPadding;
        this.t.l = e();
        this.t.h = getExtraLayoutSpace(state);
        b bVar = this.t;
        bVar.f = i;
        if (i == 1) {
            bVar.h = this.u.getEndPadding() + bVar.h;
            View viewC = c();
            this.t.e = this.x ? -1 : 1;
            b bVar2 = this.t;
            int position = getPosition(viewC);
            b bVar3 = this.t;
            bVar2.d = position + bVar3.e;
            bVar3.b = this.u.getDecoratedEnd(viewC);
            startAfterPadding = this.u.getDecoratedEnd(viewC) - this.u.getEndAfterPadding();
        } else {
            View viewD = d();
            b bVar4 = this.t;
            bVar4.h = this.u.getStartAfterPadding() + bVar4.h;
            this.t.e = this.x ? 1 : -1;
            b bVar5 = this.t;
            int position2 = getPosition(viewD);
            b bVar6 = this.t;
            bVar5.d = position2 + bVar6.e;
            bVar6.b = this.u.getDecoratedStart(viewD);
            startAfterPadding = (-this.u.getDecoratedStart(viewD)) + this.u.getStartAfterPadding();
        }
        b bVar7 = this.t;
        bVar7.c = i2;
        if (z) {
            bVar7.c = i2 - startAfterPadding;
        }
        this.t.g = startAfterPadding;
    }

    public final View b(boolean z, boolean z2) {
        if (this.x) {
            return a(getChildCount() - 1, -1, z, z2);
        }
        return a(0, getChildCount(), z, z2);
    }

    public LinearLayoutManager(Context context, AttributeSet attributeSet, int i, int i2) {
        this.s = 1;
        this.w = false;
        this.x = false;
        this.y = false;
        this.z = true;
        this.A = -1;
        this.B = Integer.MIN_VALUE;
        this.D = null;
        this.E = new a();
        this.F = new LayoutChunkResult();
        this.G = 2;
        RecyclerView.LayoutManager.Properties properties = RecyclerView.LayoutManager.getProperties(context, attributeSet, i, i2);
        setOrientation(properties.orientation);
        setReverseLayout(properties.reverseLayout);
        setStackFromEnd(properties.stackFromEnd);
    }

    public final View b(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return a(recycler, state, getChildCount() - 1, -1, state.getItemCount());
    }

    public void a(RecyclerView.State state, b bVar, RecyclerView.LayoutManager.LayoutPrefetchRegistry layoutPrefetchRegistry) {
        int i = bVar.d;
        if (i < 0 || i >= state.getItemCount()) {
            return;
        }
        layoutPrefetchRegistry.addPosition(i, Math.max(0, bVar.g));
    }

    public int a(int i, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0 || i == 0) {
            return 0;
        }
        this.t.a = true;
        b();
        int i2 = i > 0 ? 1 : -1;
        int iAbs = Math.abs(i);
        a(i2, iAbs, true, state);
        b bVar = this.t;
        int iA = a(recycler, bVar, state, false) + bVar.g;
        if (iA < 0) {
            return 0;
        }
        if (iAbs > iA) {
            i = i2 * iA;
        }
        this.u.offsetChildren(-i);
        this.t.j = i;
        return i;
    }

    public final void a(RecyclerView.Recycler recycler, int i, int i2) {
        if (i == i2) {
            return;
        }
        if (i2 <= i) {
            while (i > i2) {
                removeAndRecycleViewAt(i, recycler);
                i--;
            }
        } else {
            for (int i3 = i2 - 1; i3 >= i; i3--) {
                removeAndRecycleViewAt(i3, recycler);
            }
        }
    }

    public final void a(RecyclerView.Recycler recycler, b bVar) {
        if (!bVar.a || bVar.l) {
            return;
        }
        if (bVar.f == -1) {
            int i = bVar.g;
            int childCount = getChildCount();
            if (i < 0) {
                return;
            }
            int end = this.u.getEnd() - i;
            if (this.x) {
                for (int i2 = 0; i2 < childCount; i2++) {
                    View childAt = getChildAt(i2);
                    if (this.u.getDecoratedStart(childAt) < end || this.u.getTransformedStartWithDecoration(childAt) < end) {
                        a(recycler, 0, i2);
                        return;
                    }
                }
                return;
            }
            int i3 = childCount - 1;
            for (int i4 = i3; i4 >= 0; i4--) {
                View childAt2 = getChildAt(i4);
                if (this.u.getDecoratedStart(childAt2) < end || this.u.getTransformedStartWithDecoration(childAt2) < end) {
                    a(recycler, i3, i4);
                    return;
                }
            }
            return;
        }
        int i5 = bVar.g;
        if (i5 < 0) {
            return;
        }
        int childCount2 = getChildCount();
        if (!this.x) {
            for (int i6 = 0; i6 < childCount2; i6++) {
                View childAt3 = getChildAt(i6);
                if (this.u.getDecoratedEnd(childAt3) > i5 || this.u.getTransformedEndWithDecoration(childAt3) > i5) {
                    a(recycler, 0, i6);
                    return;
                }
            }
            return;
        }
        int i7 = childCount2 - 1;
        for (int i8 = i7; i8 >= 0; i8--) {
            View childAt4 = getChildAt(i8);
            if (this.u.getDecoratedEnd(childAt4) > i5 || this.u.getTransformedEndWithDecoration(childAt4) > i5) {
                a(recycler, i7, i8);
                return;
            }
        }
    }

    public int a(RecyclerView.Recycler recycler, b bVar, RecyclerView.State state, boolean z) {
        int i = bVar.c;
        int i2 = bVar.g;
        if (i2 != Integer.MIN_VALUE) {
            if (i < 0) {
                bVar.g = i2 + i;
            }
            a(recycler, bVar);
        }
        int i3 = bVar.c + bVar.h;
        LayoutChunkResult layoutChunkResult = this.F;
        while (true) {
            if ((!bVar.l && i3 <= 0) || !bVar.a(state)) {
                break;
            }
            layoutChunkResult.mConsumed = 0;
            layoutChunkResult.mFinished = false;
            layoutChunkResult.mIgnoreConsumed = false;
            layoutChunkResult.mFocusable = false;
            a(recycler, state, bVar, layoutChunkResult);
            if (!layoutChunkResult.mFinished) {
                bVar.b = (layoutChunkResult.mConsumed * bVar.f) + bVar.b;
                if (!layoutChunkResult.mIgnoreConsumed || this.t.k != null || !state.isPreLayout()) {
                    int i4 = bVar.c;
                    int i5 = layoutChunkResult.mConsumed;
                    bVar.c = i4 - i5;
                    i3 -= i5;
                }
                int i6 = bVar.g;
                if (i6 != Integer.MIN_VALUE) {
                    int i7 = i6 + layoutChunkResult.mConsumed;
                    bVar.g = i7;
                    int i8 = bVar.c;
                    if (i8 < 0) {
                        bVar.g = i7 + i8;
                    }
                    a(recycler, bVar);
                }
                if (z && layoutChunkResult.mFocusable) {
                    break;
                }
            } else {
                break;
            }
        }
        return i - bVar.c;
    }

    public void a(RecyclerView.Recycler recycler, RecyclerView.State state, b bVar, LayoutChunkResult layoutChunkResult) {
        int i;
        int i2;
        int i3;
        int paddingLeft;
        int decoratedMeasurementInOther;
        View viewA = bVar.a(recycler);
        if (viewA == null) {
            layoutChunkResult.mFinished = true;
            return;
        }
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) viewA.getLayoutParams();
        if (bVar.k == null) {
            if (this.x == (bVar.f == -1)) {
                addView(viewA);
            } else {
                addView(viewA, 0);
            }
        } else {
            if (this.x == (bVar.f == -1)) {
                addDisappearingView(viewA);
            } else {
                addDisappearingView(viewA, 0);
            }
        }
        measureChildWithMargins(viewA, 0, 0);
        layoutChunkResult.mConsumed = this.u.getDecoratedMeasurement(viewA);
        if (this.s == 1) {
            if (isLayoutRTL()) {
                decoratedMeasurementInOther = getWidth() - getPaddingRight();
                paddingLeft = decoratedMeasurementInOther - this.u.getDecoratedMeasurementInOther(viewA);
            } else {
                paddingLeft = getPaddingLeft();
                decoratedMeasurementInOther = this.u.getDecoratedMeasurementInOther(viewA) + paddingLeft;
            }
            if (bVar.f == -1) {
                int i4 = bVar.b;
                i3 = i4;
                i2 = decoratedMeasurementInOther;
                i = i4 - layoutChunkResult.mConsumed;
            } else {
                int i5 = bVar.b;
                i = i5;
                i2 = decoratedMeasurementInOther;
                i3 = layoutChunkResult.mConsumed + i5;
            }
        } else {
            int paddingTop = getPaddingTop();
            int decoratedMeasurementInOther2 = this.u.getDecoratedMeasurementInOther(viewA) + paddingTop;
            if (bVar.f == -1) {
                int i6 = bVar.b;
                i2 = i6;
                i = paddingTop;
                i3 = decoratedMeasurementInOther2;
                paddingLeft = i6 - layoutChunkResult.mConsumed;
            } else {
                int i7 = bVar.b;
                i = paddingTop;
                i2 = layoutChunkResult.mConsumed + i7;
                i3 = decoratedMeasurementInOther2;
                paddingLeft = i7;
            }
        }
        layoutDecoratedWithMargins(viewA, paddingLeft, i, i2, i3);
        if (layoutParams.isItemRemoved() || layoutParams.isItemChanged()) {
            layoutChunkResult.mIgnoreConsumed = true;
        }
        layoutChunkResult.mFocusable = viewA.hasFocusable();
    }

    @Override // android.support.v7.widget.RecyclerView.LayoutManager
    public boolean a() {
        boolean z;
        if (getHeightMode() == 1073741824 || getWidthMode() == 1073741824) {
            return false;
        }
        int childCount = getChildCount();
        int i = 0;
        while (true) {
            if (i >= childCount) {
                z = false;
                break;
            }
            ViewGroup.LayoutParams layoutParams = getChildAt(i).getLayoutParams();
            if (layoutParams.width < 0 && layoutParams.height < 0) {
                z = true;
                break;
            }
            i++;
        }
        return z;
    }

    public int a(int i) {
        return i != 1 ? i != 2 ? i != 17 ? i != 33 ? i != 66 ? (i == 130 && this.s == 1) ? 1 : Integer.MIN_VALUE : this.s == 0 ? 1 : Integer.MIN_VALUE : this.s == 1 ? -1 : Integer.MIN_VALUE : this.s == 0 ? -1 : Integer.MIN_VALUE : (this.s != 1 && isLayoutRTL()) ? -1 : 1 : (this.s != 1 && isLayoutRTL()) ? 1 : -1;
    }

    public final View a(boolean z, boolean z2) {
        if (this.x) {
            return a(0, getChildCount(), z, z2);
        }
        return a(getChildCount() - 1, -1, z, z2);
    }

    public final View a(RecyclerView.Recycler recycler, RecyclerView.State state) {
        return a(recycler, state, 0, getChildCount(), state.getItemCount());
    }

    public View a(RecyclerView.Recycler recycler, RecyclerView.State state, int i, int i2, int i3) {
        b();
        int startAfterPadding = this.u.getStartAfterPadding();
        int endAfterPadding = this.u.getEndAfterPadding();
        int i4 = i2 > i ? 1 : -1;
        View view2 = null;
        View view3 = null;
        while (i != i2) {
            View childAt = getChildAt(i);
            int position = getPosition(childAt);
            if (position >= 0 && position < i3) {
                if (((RecyclerView.LayoutParams) childAt.getLayoutParams()).isItemRemoved()) {
                    if (view3 == null) {
                        view3 = childAt;
                    }
                } else {
                    if (this.u.getDecoratedStart(childAt) < endAfterPadding && this.u.getDecoratedEnd(childAt) >= startAfterPadding) {
                        return childAt;
                    }
                    if (view2 == null) {
                        view2 = childAt;
                    }
                }
            }
            i += i4;
        }
        return view2 != null ? view2 : view3;
    }

    public View a(int i, int i2, boolean z, boolean z2) {
        b();
        int i3 = z ? 24579 : 320;
        int i4 = z2 ? 320 : 0;
        if (this.s == 0) {
            return this.e.a(i, i2, i3, i4);
        }
        return this.f.a(i, i2, i3, i4);
    }
}
