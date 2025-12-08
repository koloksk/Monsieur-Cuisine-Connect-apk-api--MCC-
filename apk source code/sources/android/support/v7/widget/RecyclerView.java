package android.support.v7.widget;

import android.R;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Observable;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.v4.os.TraceCompat;
import android.support.v4.util.Preconditions;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.NestedScrollingChild2;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewConfigurationCompat;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.widget.ViewBoundsCheck;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.Interpolator;
import android.widget.EdgeEffect;
import android.widget.OverScroller;
import defpackage.g8;
import defpackage.g9;
import defpackage.h8;
import defpackage.m8;
import defpackage.n8;
import defpackage.o7;
import defpackage.z7;
import defpackage.z8;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class RecyclerView extends ViewGroup implements ScrollingView, NestedScrollingChild2 {
    public static final boolean A0 = false;
    public static final boolean B0 = true;
    public static final boolean C0 = true;
    public static final Class<?>[] D0;
    public static final Interpolator E0;
    public static final int HORIZONTAL = 0;
    public static final int INVALID_TYPE = -1;
    public static final long NO_ID = -1;
    public static final int NO_POSITION = -1;
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_IDLE = 0;
    public static final int SCROLL_STATE_SETTLING = 2;
    public static final int TOUCH_SLOP_DEFAULT = 0;
    public static final int TOUCH_SLOP_PAGING = 1;
    public static final int VERTICAL = 1;
    public static final int[] y0 = {R.attr.nestedScrollingEnabled};
    public static final int[] z0 = {R.attr.clipToPadding};
    public boolean A;
    public final AccessibilityManager B;
    public List<OnChildAttachStateChangeListener> C;
    public boolean D;
    public boolean E;
    public int F;
    public int G;

    @NonNull
    public EdgeEffectFactory H;
    public EdgeEffect I;
    public EdgeEffect J;
    public EdgeEffect K;
    public EdgeEffect L;
    public ItemAnimator M;
    public int N;
    public int O;
    public VelocityTracker P;
    public int Q;
    public int R;
    public int S;
    public int T;
    public int U;
    public OnFlingListener V;
    public final int W;
    public final g a;
    public final int a0;
    public final Recycler b;
    public float b0;
    public SavedState c;
    public float c0;
    public o7 d;
    public boolean d0;
    public z7 e;
    public final h e0;
    public final z8 f;
    public h8 f0;
    public boolean g;
    public h8.b g0;
    public final Runnable h;
    public final State h0;
    public final Rect i;
    public OnScrollListener i0;
    public final Rect j;
    public List<OnScrollListener> j0;
    public final RectF k;
    public boolean k0;
    public Adapter l;
    public boolean l0;

    @VisibleForTesting
    public LayoutManager m;
    public ItemAnimator.a m0;
    public RecyclerListener n;
    public boolean n0;
    public final ArrayList<ItemDecoration> o;
    public RecyclerViewAccessibilityDelegate o0;
    public final ArrayList<OnItemTouchListener> p;
    public ChildDrawingOrderCallback p0;
    public OnItemTouchListener q;
    public final int[] q0;
    public boolean r;
    public NestedScrollingChildHelper r0;
    public boolean s;
    public final int[] s0;
    public boolean t;
    public final int[] t0;

    @VisibleForTesting
    public boolean u;
    public final int[] u0;
    public int v;

    @VisibleForTesting
    public final List<ViewHolder> v0;
    public boolean w;
    public Runnable w0;
    public boolean x;
    public final z8.b x0;
    public boolean y;
    public int z;

    public static abstract class AdapterDataObserver {
        public void onChanged() {
        }

        public void onItemRangeChanged(int i, int i2) {
        }

        public void onItemRangeChanged(int i, int i2, @Nullable Object obj) {
            onItemRangeChanged(i, i2);
        }

        public void onItemRangeInserted(int i, int i2) {
        }

        public void onItemRangeMoved(int i, int i2, int i3) {
        }

        public void onItemRangeRemoved(int i, int i2) {
        }
    }

    public interface ChildDrawingOrderCallback {
        int onGetChildDrawingOrder(int i, int i2);
    }

    public static class EdgeEffectFactory {
        public static final int DIRECTION_BOTTOM = 3;
        public static final int DIRECTION_LEFT = 0;
        public static final int DIRECTION_RIGHT = 2;
        public static final int DIRECTION_TOP = 1;

        @Retention(RetentionPolicy.SOURCE)
        public @interface EdgeDirection {
        }

        @NonNull
        public EdgeEffect createEdgeEffect(RecyclerView recyclerView, int i) {
            return new EdgeEffect(recyclerView.getContext());
        }
    }

    public static abstract class ItemAnimator {
        public static final int FLAG_APPEARED_IN_PRE_LAYOUT = 4096;
        public static final int FLAG_CHANGED = 2;
        public static final int FLAG_INVALIDATED = 4;
        public static final int FLAG_MOVED = 2048;
        public static final int FLAG_REMOVED = 8;
        public a a = null;
        public ArrayList<ItemAnimatorFinishedListener> b = new ArrayList<>();
        public long c = 120;
        public long d = 120;
        public long e = 250;
        public long f = 250;

        @Retention(RetentionPolicy.SOURCE)
        public @interface AdapterChanges {
        }

        public interface ItemAnimatorFinishedListener {
            void onAnimationsFinished();
        }

        public static class ItemHolderInfo {
            public int bottom;
            public int changeFlags;
            public int left;
            public int right;
            public int top;

            public ItemHolderInfo setFrom(ViewHolder viewHolder) {
                return setFrom(viewHolder, 0);
            }

            public ItemHolderInfo setFrom(ViewHolder viewHolder, int i) {
                View view2 = viewHolder.itemView;
                this.left = view2.getLeft();
                this.top = view2.getTop();
                this.right = view2.getRight();
                this.bottom = view2.getBottom();
                return this;
            }
        }

        public interface a {
        }

        public static int a(ViewHolder viewHolder) {
            int i = viewHolder.i & 14;
            if (viewHolder.e()) {
                return 4;
            }
            if ((i & 4) != 0) {
                return i;
            }
            int oldPosition = viewHolder.getOldPosition();
            int adapterPosition = viewHolder.getAdapterPosition();
            return (oldPosition == -1 || adapterPosition == -1 || oldPosition == adapterPosition) ? i : i | 2048;
        }

        public abstract boolean animateAppearance(@NonNull ViewHolder viewHolder, @Nullable ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        public abstract boolean animateChange(@NonNull ViewHolder viewHolder, @NonNull ViewHolder viewHolder2, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        public abstract boolean animateDisappearance(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @Nullable ItemHolderInfo itemHolderInfo2);

        public abstract boolean animatePersistence(@NonNull ViewHolder viewHolder, @NonNull ItemHolderInfo itemHolderInfo, @NonNull ItemHolderInfo itemHolderInfo2);

        public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder) {
            return true;
        }

        public boolean canReuseUpdatedViewHolder(@NonNull ViewHolder viewHolder, @NonNull List<Object> list) {
            return canReuseUpdatedViewHolder(viewHolder);
        }

        public final void dispatchAnimationFinished(ViewHolder viewHolder) {
            onAnimationFinished(viewHolder);
            a aVar = this.a;
            if (aVar != null) {
                f fVar = (f) aVar;
                if (fVar == null) {
                    throw null;
                }
                boolean z = true;
                viewHolder.setIsRecyclable(true);
                if (viewHolder.g != null && viewHolder.h == null) {
                    viewHolder.g = null;
                }
                viewHolder.h = null;
                if ((viewHolder.i & 16) != 0) {
                    return;
                }
                RecyclerView recyclerView = RecyclerView.this;
                View view2 = viewHolder.itemView;
                recyclerView.t();
                z7 z7Var = recyclerView.e;
                int iIndexOfChild = ((m8) z7Var.a).a.indexOfChild(view2);
                if (iIndexOfChild == -1) {
                    z7Var.d(view2);
                } else if (z7Var.b.c(iIndexOfChild)) {
                    z7Var.b.d(iIndexOfChild);
                    z7Var.d(view2);
                    ((m8) z7Var.a).b(iIndexOfChild);
                } else {
                    z = false;
                }
                if (z) {
                    ViewHolder viewHolderD = RecyclerView.d(view2);
                    recyclerView.b.b(viewHolderD);
                    recyclerView.b.a(viewHolderD);
                }
                recyclerView.c(!z);
                if (z || !viewHolder.h()) {
                    return;
                }
                RecyclerView.this.removeDetachedView(viewHolder.itemView, false);
            }
        }

        public final void dispatchAnimationStarted(ViewHolder viewHolder) {
            onAnimationStarted(viewHolder);
        }

        public final void dispatchAnimationsFinished() {
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                this.b.get(i).onAnimationsFinished();
            }
            this.b.clear();
        }

        public abstract void endAnimation(ViewHolder viewHolder);

        public abstract void endAnimations();

        public long getAddDuration() {
            return this.c;
        }

        public long getChangeDuration() {
            return this.f;
        }

        public long getMoveDuration() {
            return this.e;
        }

        public long getRemoveDuration() {
            return this.d;
        }

        public abstract boolean isRunning();

        public final boolean isRunning(ItemAnimatorFinishedListener itemAnimatorFinishedListener) {
            boolean zIsRunning = isRunning();
            if (itemAnimatorFinishedListener != null) {
                if (zIsRunning) {
                    this.b.add(itemAnimatorFinishedListener);
                } else {
                    itemAnimatorFinishedListener.onAnimationsFinished();
                }
            }
            return zIsRunning;
        }

        public ItemHolderInfo obtainHolderInfo() {
            return new ItemHolderInfo();
        }

        public void onAnimationFinished(ViewHolder viewHolder) {
        }

        public void onAnimationStarted(ViewHolder viewHolder) {
        }

        @NonNull
        public ItemHolderInfo recordPostLayoutInformation(@NonNull State state, @NonNull ViewHolder viewHolder) {
            return obtainHolderInfo().setFrom(viewHolder);
        }

        @NonNull
        public ItemHolderInfo recordPreLayoutInformation(@NonNull State state, @NonNull ViewHolder viewHolder, int i, @NonNull List<Object> list) {
            return obtainHolderInfo().setFrom(viewHolder);
        }

        public abstract void runPendingAnimations();

        public void setAddDuration(long j) {
            this.c = j;
        }

        public void setChangeDuration(long j) {
            this.f = j;
        }

        public void setMoveDuration(long j) {
            this.e = j;
        }

        public void setRemoveDuration(long j) {
            this.d = j;
        }
    }

    public static abstract class ItemDecoration {
        @Deprecated
        public void getItemOffsets(Rect rect, int i, RecyclerView recyclerView) {
            rect.set(0, 0, 0, 0);
        }

        @Deprecated
        public void onDraw(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDraw(Canvas canvas, RecyclerView recyclerView, State state) {
            onDraw(canvas, recyclerView);
        }

        @Deprecated
        public void onDrawOver(Canvas canvas, RecyclerView recyclerView) {
        }

        public void onDrawOver(Canvas canvas, RecyclerView recyclerView, State state) {
            onDrawOver(canvas, recyclerView);
        }

        public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, State state) {
            getItemOffsets(rect, ((LayoutParams) view2.getLayoutParams()).getViewLayoutPosition(), recyclerView);
        }
    }

    public static abstract class LayoutManager {
        public z7 a;
        public RecyclerView b;

        @Nullable
        public SmoothScroller g;
        public int m;
        public boolean n;
        public int o;
        public int p;
        public int q;
        public int r;
        public final ViewBoundsCheck.b c = new a();
        public final ViewBoundsCheck.b d = new b();
        public ViewBoundsCheck e = new ViewBoundsCheck(this.c);
        public ViewBoundsCheck f = new ViewBoundsCheck(this.d);
        public boolean h = false;
        public boolean i = false;
        public boolean j = false;
        public boolean k = true;
        public boolean l = true;

        public interface LayoutPrefetchRegistry {
            void addPosition(int i, int i2);
        }

        public static class Properties {
            public int orientation;
            public boolean reverseLayout;
            public int spanCount;
            public boolean stackFromEnd;
        }

        public class a implements ViewBoundsCheck.b {
            public a() {
            }

            @Override // android.support.v7.widget.ViewBoundsCheck.b
            public View a(int i) {
                return LayoutManager.this.getChildAt(i);
            }

            @Override // android.support.v7.widget.ViewBoundsCheck.b
            public int b() {
                return LayoutManager.this.getPaddingLeft();
            }

            @Override // android.support.v7.widget.ViewBoundsCheck.b
            public int a() {
                return LayoutManager.this.getWidth() - LayoutManager.this.getPaddingRight();
            }

            @Override // android.support.v7.widget.ViewBoundsCheck.b
            public int b(View view2) {
                return LayoutManager.this.getDecoratedRight(view2) + ((ViewGroup.MarginLayoutParams) ((LayoutParams) view2.getLayoutParams())).rightMargin;
            }

            @Override // android.support.v7.widget.ViewBoundsCheck.b
            public int a(View view2) {
                return LayoutManager.this.getDecoratedLeft(view2) - ((ViewGroup.MarginLayoutParams) ((LayoutParams) view2.getLayoutParams())).leftMargin;
            }
        }

        public class b implements ViewBoundsCheck.b {
            public b() {
            }

            @Override // android.support.v7.widget.ViewBoundsCheck.b
            public View a(int i) {
                return LayoutManager.this.getChildAt(i);
            }

            @Override // android.support.v7.widget.ViewBoundsCheck.b
            public int b() {
                return LayoutManager.this.getPaddingTop();
            }

            @Override // android.support.v7.widget.ViewBoundsCheck.b
            public int a() {
                return LayoutManager.this.getHeight() - LayoutManager.this.getPaddingBottom();
            }

            @Override // android.support.v7.widget.ViewBoundsCheck.b
            public int b(View view2) {
                return LayoutManager.this.getDecoratedBottom(view2) + ((ViewGroup.MarginLayoutParams) ((LayoutParams) view2.getLayoutParams())).bottomMargin;
            }

            @Override // android.support.v7.widget.ViewBoundsCheck.b
            public int a(View view2) {
                return LayoutManager.this.getDecoratedTop(view2) - ((ViewGroup.MarginLayoutParams) ((LayoutParams) view2.getLayoutParams())).topMargin;
            }
        }

        public static int chooseSize(int i, int i2, int i3) {
            int mode = View.MeasureSpec.getMode(i);
            int size = View.MeasureSpec.getSize(i);
            return mode != Integer.MIN_VALUE ? mode != 1073741824 ? Math.max(i2, i3) : size : Math.min(size, Math.max(i2, i3));
        }

        /* JADX WARN: Removed duplicated region for block: B:6:0x000d  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x0011 A[PHI: r3
  0x0011: PHI (r3v3 int) = (r3v0 int), (r3v2 int), (r3v0 int) binds: [B:7:0x000f, B:11:0x0016, B:4:0x000a] A[DONT_GENERATE, DONT_INLINE]] */
        @java.lang.Deprecated
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static int getChildMeasureSpec(int r1, int r2, int r3, boolean r4) {
            /*
                int r1 = r1 - r2
                r2 = 0
                int r1 = java.lang.Math.max(r2, r1)
                r0 = 1073741824(0x40000000, float:2.0)
                if (r4 == 0) goto Lf
                if (r3 < 0) goto Ld
                goto L11
            Ld:
                r3 = r2
                goto L1e
            Lf:
                if (r3 < 0) goto L13
            L11:
                r2 = r0
                goto L1e
            L13:
                r4 = -1
                if (r3 != r4) goto L18
                r3 = r1
                goto L11
            L18:
                r4 = -2
                if (r3 != r4) goto Ld
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = r1
            L1e:
                int r1 = android.view.View.MeasureSpec.makeMeasureSpec(r3, r2)
                return r1
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.LayoutManager.getChildMeasureSpec(int, int, int, boolean):int");
        }

        public static Properties getProperties(Context context, AttributeSet attributeSet, int i, int i2) {
            Properties properties = new Properties();
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.support.v7.recyclerview.R.styleable.RecyclerView, i, i2);
            properties.orientation = typedArrayObtainStyledAttributes.getInt(android.support.v7.recyclerview.R.styleable.RecyclerView_android_orientation, 1);
            properties.spanCount = typedArrayObtainStyledAttributes.getInt(android.support.v7.recyclerview.R.styleable.RecyclerView_spanCount, 1);
            properties.reverseLayout = typedArrayObtainStyledAttributes.getBoolean(android.support.v7.recyclerview.R.styleable.RecyclerView_reverseLayout, false);
            properties.stackFromEnd = typedArrayObtainStyledAttributes.getBoolean(android.support.v7.recyclerview.R.styleable.RecyclerView_stackFromEnd, false);
            typedArrayObtainStyledAttributes.recycle();
            return properties;
        }

        public void a(Recycler recycler) {
            int size = recycler.a.size();
            for (int i = size - 1; i >= 0; i--) {
                View view2 = recycler.a.get(i).itemView;
                ViewHolder viewHolderD = RecyclerView.d(view2);
                if (!viewHolderD.l()) {
                    viewHolderD.setIsRecyclable(false);
                    if (viewHolderD.h()) {
                        this.b.removeDetachedView(view2, false);
                    }
                    ItemAnimator itemAnimator = this.b.M;
                    if (itemAnimator != null) {
                        itemAnimator.endAnimation(viewHolderD);
                    }
                    viewHolderD.setIsRecyclable(true);
                    ViewHolder viewHolderD2 = RecyclerView.d(view2);
                    viewHolderD2.m = null;
                    viewHolderD2.n = false;
                    viewHolderD2.b();
                    recycler.a(viewHolderD2);
                }
            }
            recycler.a.clear();
            ArrayList<ViewHolder> arrayList = recycler.b;
            if (arrayList != null) {
                arrayList.clear();
            }
            if (size > 0) {
                this.b.invalidate();
            }
        }

        public boolean a() {
            return false;
        }

        public void addDisappearingView(View view2) {
            addDisappearingView(view2, -1);
        }

        public void addView(View view2) {
            addView(view2, -1);
        }

        public void assertInLayoutOrScroll(String str) {
            RecyclerView recyclerView = this.b;
            if (recyclerView == null || recyclerView.isComputingLayout()) {
                return;
            }
            if (str != null) {
                throw new IllegalStateException(g9.a(recyclerView, g9.a(str)));
            }
            throw new IllegalStateException(g9.a(recyclerView, g9.a("Cannot call this method unless RecyclerView is computing a layout or scrolling")));
        }

        public void assertNotInLayoutOrScroll(String str) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                recyclerView.a(str);
            }
        }

        public void attachView(View view2, int i, LayoutParams layoutParams) {
            ViewHolder viewHolderD = RecyclerView.d(view2);
            if (viewHolderD.f()) {
                this.b.f.a(viewHolderD);
            } else {
                this.b.f.c(viewHolderD);
            }
            this.a.a(view2, i, layoutParams, viewHolderD.f());
        }

        public void b(RecyclerView recyclerView) {
            if (recyclerView == null) {
                this.b = null;
                this.a = null;
                this.q = 0;
                this.r = 0;
            } else {
                this.b = recyclerView;
                this.a = recyclerView.e;
                this.q = recyclerView.getWidth();
                this.r = recyclerView.getHeight();
            }
            this.o = 1073741824;
            this.p = 1073741824;
        }

        public void calculateItemDecorationsForChild(View view2, Rect rect) {
            RecyclerView recyclerView = this.b;
            if (recyclerView == null) {
                rect.set(0, 0, 0, 0);
            } else {
                rect.set(recyclerView.b(view2));
            }
        }

        public boolean canScrollHorizontally() {
            return false;
        }

        public boolean canScrollVertically() {
            return false;
        }

        public boolean checkLayoutParams(LayoutParams layoutParams) {
            return layoutParams != null;
        }

        public void collectAdjacentPrefetchPositions(int i, int i2, State state, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public void collectInitialPrefetchPositions(int i, LayoutPrefetchRegistry layoutPrefetchRegistry) {
        }

        public int computeHorizontalScrollExtent(State state) {
            return 0;
        }

        public int computeHorizontalScrollOffset(State state) {
            return 0;
        }

        public int computeHorizontalScrollRange(State state) {
            return 0;
        }

        public int computeVerticalScrollExtent(State state) {
            return 0;
        }

        public int computeVerticalScrollOffset(State state) {
            return 0;
        }

        public int computeVerticalScrollRange(State state) {
            return 0;
        }

        public void detachAndScrapAttachedViews(Recycler recycler) {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                a(recycler, childCount, getChildAt(childCount));
            }
        }

        public void detachAndScrapView(View view2, Recycler recycler) {
            a(recycler, this.a.b(view2), view2);
        }

        public void detachAndScrapViewAt(int i, Recycler recycler) {
            a(recycler, i, getChildAt(i));
        }

        public void detachView(View view2) {
            int iB = this.a.b(view2);
            if (iB >= 0) {
                this.a.a(iB);
            }
        }

        public void detachViewAt(int i) {
            getChildAt(i);
            this.a.a(i);
        }

        public void endAnimation(View view2) {
            ItemAnimator itemAnimator = this.b.M;
            if (itemAnimator != null) {
                itemAnimator.endAnimation(RecyclerView.d(view2));
            }
        }

        @Nullable
        public View findContainingItemView(View view2) {
            View viewFindContainingItemView;
            RecyclerView recyclerView = this.b;
            if (recyclerView == null || (viewFindContainingItemView = recyclerView.findContainingItemView(view2)) == null || this.a.c.contains(viewFindContainingItemView)) {
                return null;
            }
            return viewFindContainingItemView;
        }

        public View findViewByPosition(int i) {
            int childCount = getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                View childAt = getChildAt(i2);
                ViewHolder viewHolderD = RecyclerView.d(childAt);
                if (viewHolderD != null && viewHolderD.getLayoutPosition() == i && !viewHolderD.l() && (this.b.h0.isPreLayout() || !viewHolderD.f())) {
                    return childAt;
                }
            }
            return null;
        }

        public abstract LayoutParams generateDefaultLayoutParams();

        public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
            return layoutParams instanceof LayoutParams ? new LayoutParams((LayoutParams) layoutParams) : layoutParams instanceof ViewGroup.MarginLayoutParams ? new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams) : new LayoutParams(layoutParams);
        }

        public int getBaseline() {
            return -1;
        }

        public int getBottomDecorationHeight(View view2) {
            return ((LayoutParams) view2.getLayoutParams()).b.bottom;
        }

        public View getChildAt(int i) {
            z7 z7Var = this.a;
            if (z7Var == null) {
                return null;
            }
            return ((m8) z7Var.a).a(z7Var.c(i));
        }

        public int getChildCount() {
            z7 z7Var = this.a;
            if (z7Var != null) {
                return z7Var.a();
            }
            return 0;
        }

        public boolean getClipToPadding() {
            RecyclerView recyclerView = this.b;
            return recyclerView != null && recyclerView.g;
        }

        public int getColumnCountForAccessibility(Recycler recycler, State state) {
            RecyclerView recyclerView = this.b;
            if (recyclerView == null || recyclerView.l == null || !canScrollHorizontally()) {
                return 1;
            }
            return this.b.l.getItemCount();
        }

        public int getDecoratedBottom(View view2) {
            return getBottomDecorationHeight(view2) + view2.getBottom();
        }

        public void getDecoratedBoundsWithMargins(View view2, Rect rect) {
            RecyclerView.a(view2, rect);
        }

        public int getDecoratedLeft(View view2) {
            return view2.getLeft() - getLeftDecorationWidth(view2);
        }

        public int getDecoratedMeasuredHeight(View view2) {
            Rect rect = ((LayoutParams) view2.getLayoutParams()).b;
            return view2.getMeasuredHeight() + rect.top + rect.bottom;
        }

        public int getDecoratedMeasuredWidth(View view2) {
            Rect rect = ((LayoutParams) view2.getLayoutParams()).b;
            return view2.getMeasuredWidth() + rect.left + rect.right;
        }

        public int getDecoratedRight(View view2) {
            return getRightDecorationWidth(view2) + view2.getRight();
        }

        public int getDecoratedTop(View view2) {
            return view2.getTop() - getTopDecorationHeight(view2);
        }

        public View getFocusedChild() {
            View focusedChild;
            RecyclerView recyclerView = this.b;
            if (recyclerView == null || (focusedChild = recyclerView.getFocusedChild()) == null || this.a.c.contains(focusedChild)) {
                return null;
            }
            return focusedChild;
        }

        public int getHeight() {
            return this.r;
        }

        public int getHeightMode() {
            return this.p;
        }

        public int getItemCount() {
            RecyclerView recyclerView = this.b;
            Adapter adapter2 = recyclerView != null ? recyclerView.getAdapter() : null;
            if (adapter2 != null) {
                return adapter2.getItemCount();
            }
            return 0;
        }

        public int getItemViewType(View view2) {
            return RecyclerView.d(view2).getItemViewType();
        }

        public int getLayoutDirection() {
            return ViewCompat.getLayoutDirection(this.b);
        }

        public int getLeftDecorationWidth(View view2) {
            return ((LayoutParams) view2.getLayoutParams()).b.left;
        }

        public int getMinimumHeight() {
            return ViewCompat.getMinimumHeight(this.b);
        }

        public int getMinimumWidth() {
            return ViewCompat.getMinimumWidth(this.b);
        }

        public int getPaddingBottom() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.getPaddingBottom();
            }
            return 0;
        }

        public int getPaddingEnd() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return ViewCompat.getPaddingEnd(recyclerView);
            }
            return 0;
        }

        public int getPaddingLeft() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.getPaddingLeft();
            }
            return 0;
        }

        public int getPaddingRight() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.getPaddingRight();
            }
            return 0;
        }

        public int getPaddingStart() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return ViewCompat.getPaddingStart(recyclerView);
            }
            return 0;
        }

        public int getPaddingTop() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.getPaddingTop();
            }
            return 0;
        }

        public int getPosition(View view2) {
            return ((LayoutParams) view2.getLayoutParams()).getViewLayoutPosition();
        }

        public int getRightDecorationWidth(View view2) {
            return ((LayoutParams) view2.getLayoutParams()).b.right;
        }

        public int getRowCountForAccessibility(Recycler recycler, State state) {
            RecyclerView recyclerView = this.b;
            if (recyclerView == null || recyclerView.l == null || !canScrollVertically()) {
                return 1;
            }
            return this.b.l.getItemCount();
        }

        public int getSelectionModeForAccessibility(Recycler recycler, State state) {
            return 0;
        }

        public int getTopDecorationHeight(View view2) {
            return ((LayoutParams) view2.getLayoutParams()).b.top;
        }

        public void getTransformedBoundingBox(View view2, boolean z, Rect rect) {
            Matrix matrix;
            if (z) {
                Rect rect2 = ((LayoutParams) view2.getLayoutParams()).b;
                rect.set(-rect2.left, -rect2.top, view2.getWidth() + rect2.right, view2.getHeight() + rect2.bottom);
            } else {
                rect.set(0, 0, view2.getWidth(), view2.getHeight());
            }
            if (this.b != null && (matrix = view2.getMatrix()) != null && !matrix.isIdentity()) {
                RectF rectF = this.b.k;
                rectF.set(rect);
                matrix.mapRect(rectF);
                rect.set((int) Math.floor(rectF.left), (int) Math.floor(rectF.top), (int) Math.ceil(rectF.right), (int) Math.ceil(rectF.bottom));
            }
            rect.offset(view2.getLeft(), view2.getTop());
        }

        public int getWidth() {
            return this.q;
        }

        public int getWidthMode() {
            return this.o;
        }

        public boolean hasFocus() {
            RecyclerView recyclerView = this.b;
            return recyclerView != null && recyclerView.hasFocus();
        }

        public void ignoreView(View view2) {
            ViewParent parent = view2.getParent();
            RecyclerView recyclerView = this.b;
            if (parent != recyclerView || recyclerView.indexOfChild(view2) == -1) {
                throw new IllegalArgumentException(g9.a(this.b, g9.a("View should be fully attached to be ignored")));
            }
            ViewHolder viewHolderD = RecyclerView.d(view2);
            viewHolderD.a(128);
            this.b.f.d(viewHolderD);
        }

        public boolean isAttachedToWindow() {
            return this.i;
        }

        public boolean isAutoMeasureEnabled() {
            return this.j;
        }

        public boolean isFocused() {
            RecyclerView recyclerView = this.b;
            return recyclerView != null && recyclerView.isFocused();
        }

        public final boolean isItemPrefetchEnabled() {
            return this.l;
        }

        public boolean isLayoutHierarchical(Recycler recycler, State state) {
            return false;
        }

        public boolean isMeasurementCacheEnabled() {
            return this.k;
        }

        public boolean isSmoothScrolling() {
            SmoothScroller smoothScroller = this.g;
            return smoothScroller != null && smoothScroller.isRunning();
        }

        public boolean isViewPartiallyVisible(@NonNull View view2, boolean z, boolean z2) {
            boolean z3 = this.e.a(view2, 24579) && this.f.a(view2, 24579);
            return z ? z3 : !z3;
        }

        public void layoutDecorated(View view2, int i, int i2, int i3, int i4) {
            Rect rect = ((LayoutParams) view2.getLayoutParams()).b;
            view2.layout(i + rect.left, i2 + rect.top, i3 - rect.right, i4 - rect.bottom);
        }

        public void layoutDecoratedWithMargins(View view2, int i, int i2, int i3, int i4) {
            LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
            Rect rect = layoutParams.b;
            view2.layout(i + rect.left + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, i2 + rect.top + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, (i3 - rect.right) - ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, (i4 - rect.bottom) - ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
        }

        public void measureChild(View view2, int i, int i2) {
            LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
            Rect rectB = this.b.b(view2);
            int i3 = rectB.left + rectB.right + i;
            int i4 = rectB.top + rectB.bottom + i2;
            int childMeasureSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingRight() + getPaddingLeft() + i3, ((ViewGroup.MarginLayoutParams) layoutParams).width, canScrollHorizontally());
            int childMeasureSpec2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingBottom() + getPaddingTop() + i4, ((ViewGroup.MarginLayoutParams) layoutParams).height, canScrollVertically());
            if (a(view2, childMeasureSpec, childMeasureSpec2, layoutParams)) {
                view2.measure(childMeasureSpec, childMeasureSpec2);
            }
        }

        public void measureChildWithMargins(View view2, int i, int i2) {
            LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
            Rect rectB = this.b.b(view2);
            int i3 = rectB.left + rectB.right + i;
            int i4 = rectB.top + rectB.bottom + i2;
            int childMeasureSpec = getChildMeasureSpec(getWidth(), getWidthMode(), getPaddingRight() + getPaddingLeft() + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + i3, ((ViewGroup.MarginLayoutParams) layoutParams).width, canScrollHorizontally());
            int childMeasureSpec2 = getChildMeasureSpec(getHeight(), getHeightMode(), getPaddingBottom() + getPaddingTop() + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + i4, ((ViewGroup.MarginLayoutParams) layoutParams).height, canScrollVertically());
            if (a(view2, childMeasureSpec, childMeasureSpec2, layoutParams)) {
                view2.measure(childMeasureSpec, childMeasureSpec2);
            }
        }

        public void moveView(int i, int i2) {
            View childAt = getChildAt(i);
            if (childAt != null) {
                detachViewAt(i);
                attachView(childAt, i2);
            } else {
                throw new IllegalArgumentException("Cannot move a child from non-existing index:" + i + this.b.toString());
            }
        }

        public void offsetChildrenHorizontal(int i) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                recyclerView.offsetChildrenHorizontal(i);
            }
        }

        public void offsetChildrenVertical(int i) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                recyclerView.offsetChildrenVertical(i);
            }
        }

        public void onAdapterChanged(Adapter adapter2, Adapter adapter3) {
        }

        public boolean onAddFocusables(RecyclerView recyclerView, ArrayList<View> arrayList, int i, int i2) {
            return false;
        }

        @CallSuper
        public void onAttachedToWindow(RecyclerView recyclerView) {
        }

        @Deprecated
        public void onDetachedFromWindow(RecyclerView recyclerView) {
        }

        @CallSuper
        public void onDetachedFromWindow(RecyclerView recyclerView, Recycler recycler) {
            onDetachedFromWindow(recyclerView);
        }

        @Nullable
        public View onFocusSearchFailed(View view2, int i, Recycler recycler, State state) {
            return null;
        }

        public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
            RecyclerView recyclerView = this.b;
            onInitializeAccessibilityEvent(recyclerView.b, recyclerView.h0, accessibilityEvent);
        }

        public void onInitializeAccessibilityNodeInfo(Recycler recycler, State state, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            if (this.b.canScrollVertically(-1) || this.b.canScrollHorizontally(-1)) {
                accessibilityNodeInfoCompat.addAction(8192);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            if (this.b.canScrollVertically(1) || this.b.canScrollHorizontally(1)) {
                accessibilityNodeInfoCompat.addAction(4096);
                accessibilityNodeInfoCompat.setScrollable(true);
            }
            accessibilityNodeInfoCompat.setCollectionInfo(AccessibilityNodeInfoCompat.CollectionInfoCompat.obtain(getRowCountForAccessibility(recycler, state), getColumnCountForAccessibility(recycler, state), isLayoutHierarchical(recycler, state), getSelectionModeForAccessibility(recycler, state)));
        }

        public void onInitializeAccessibilityNodeInfoForItem(Recycler recycler, State state, View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            accessibilityNodeInfoCompat.setCollectionItemInfo(AccessibilityNodeInfoCompat.CollectionItemInfoCompat.obtain(canScrollVertically() ? getPosition(view2) : 0, 1, canScrollHorizontally() ? getPosition(view2) : 0, 1, false, false));
        }

        public View onInterceptFocusSearch(View view2, int i) {
            return null;
        }

        public void onItemsAdded(RecyclerView recyclerView, int i, int i2) {
        }

        public void onItemsChanged(RecyclerView recyclerView) {
        }

        public void onItemsMoved(RecyclerView recyclerView, int i, int i2, int i3) {
        }

        public void onItemsRemoved(RecyclerView recyclerView, int i, int i2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int i, int i2) {
        }

        public void onItemsUpdated(RecyclerView recyclerView, int i, int i2, Object obj) {
            onItemsUpdated(recyclerView, i, i2);
        }

        public void onLayoutChildren(Recycler recycler, State state) {
            Log.e("RecyclerView", "You must override onLayoutChildren(Recycler recycler, State state) ");
        }

        public void onLayoutCompleted(State state) {
        }

        public void onMeasure(Recycler recycler, State state, int i, int i2) {
            this.b.b(i, i2);
        }

        @Deprecated
        public boolean onRequestChildFocus(RecyclerView recyclerView, View view2, View view3) {
            return isSmoothScrolling() || recyclerView.isComputingLayout();
        }

        public void onRestoreInstanceState(Parcelable parcelable) {
        }

        public Parcelable onSaveInstanceState() {
            return null;
        }

        public void onScrollStateChanged(int i) {
        }

        /* JADX WARN: Removed duplicated region for block: B:24:0x006f A[PHI: r2
  0x006f: PHI (r2v8 int) = (r2v4 int), (r2v12 int) binds: [B:22:0x005e, B:15:0x0030] A[DONT_GENERATE, DONT_INLINE]] */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean performAccessibilityAction(android.support.v7.widget.RecyclerView.Recycler r2, android.support.v7.widget.RecyclerView.State r3, int r4, android.os.Bundle r5) {
            /*
                r1 = this;
                android.support.v7.widget.RecyclerView r2 = r1.b
                r3 = 0
                if (r2 != 0) goto L6
                return r3
            L6:
                r5 = 4096(0x1000, float:5.74E-42)
                r0 = 1
                if (r4 == r5) goto L42
                r5 = 8192(0x2000, float:1.14794E-41)
                if (r4 == r5) goto L12
                r2 = r3
                r4 = r2
                goto L70
            L12:
                r4 = -1
                boolean r2 = r2.canScrollVertically(r4)
                if (r2 == 0) goto L29
                int r2 = r1.getHeight()
                int r5 = r1.getPaddingTop()
                int r2 = r2 - r5
                int r5 = r1.getPaddingBottom()
                int r2 = r2 - r5
                int r2 = -r2
                goto L2a
            L29:
                r2 = r3
            L2a:
                android.support.v7.widget.RecyclerView r5 = r1.b
                boolean r4 = r5.canScrollHorizontally(r4)
                if (r4 == 0) goto L6f
                int r4 = r1.getWidth()
                int r5 = r1.getPaddingLeft()
                int r4 = r4 - r5
                int r5 = r1.getPaddingRight()
                int r4 = r4 - r5
                int r4 = -r4
                goto L70
            L42:
                boolean r2 = r2.canScrollVertically(r0)
                if (r2 == 0) goto L57
                int r2 = r1.getHeight()
                int r4 = r1.getPaddingTop()
                int r2 = r2 - r4
                int r4 = r1.getPaddingBottom()
                int r2 = r2 - r4
                goto L58
            L57:
                r2 = r3
            L58:
                android.support.v7.widget.RecyclerView r4 = r1.b
                boolean r4 = r4.canScrollHorizontally(r0)
                if (r4 == 0) goto L6f
                int r4 = r1.getWidth()
                int r5 = r1.getPaddingLeft()
                int r4 = r4 - r5
                int r5 = r1.getPaddingRight()
                int r4 = r4 - r5
                goto L70
            L6f:
                r4 = r3
            L70:
                if (r2 != 0) goto L75
                if (r4 != 0) goto L75
                return r3
            L75:
                android.support.v7.widget.RecyclerView r3 = r1.b
                r3.scrollBy(r4, r2)
                return r0
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.LayoutManager.performAccessibilityAction(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State, int, android.os.Bundle):boolean");
        }

        public boolean performAccessibilityActionForItem(Recycler recycler, State state, View view2, int i, Bundle bundle) {
            return false;
        }

        public void postOnAnimation(Runnable runnable) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                ViewCompat.postOnAnimation(recyclerView, runnable);
            }
        }

        public void removeAllViews() {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                this.a.e(childCount);
            }
        }

        public void removeAndRecycleAllViews(Recycler recycler) {
            for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
                if (!RecyclerView.d(getChildAt(childCount)).l()) {
                    removeAndRecycleViewAt(childCount, recycler);
                }
            }
        }

        public void removeAndRecycleView(View view2, Recycler recycler) {
            removeView(view2);
            recycler.recycleView(view2);
        }

        public void removeAndRecycleViewAt(int i, Recycler recycler) {
            View childAt = getChildAt(i);
            removeViewAt(i);
            recycler.recycleView(childAt);
        }

        public boolean removeCallbacks(Runnable runnable) {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                return recyclerView.removeCallbacks(runnable);
            }
            return false;
        }

        public void removeDetachedView(View view2) {
            this.b.removeDetachedView(view2, false);
        }

        public void removeView(View view2) {
            z7 z7Var = this.a;
            int iIndexOfChild = ((m8) z7Var.a).a.indexOfChild(view2);
            if (iIndexOfChild < 0) {
                return;
            }
            if (z7Var.b.d(iIndexOfChild)) {
                z7Var.d(view2);
            }
            ((m8) z7Var.a).b(iIndexOfChild);
        }

        public void removeViewAt(int i) {
            if (getChildAt(i) != null) {
                this.a.e(i);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:18:0x0082  */
        /* JADX WARN: Removed duplicated region for block: B:30:0x00bd  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean requestChildRectangleOnScreen(android.support.v7.widget.RecyclerView r10, android.view.View r11, android.graphics.Rect r12, boolean r13, boolean r14) {
            /*
                r9 = this;
                r0 = 2
                int[] r0 = new int[r0]
                int r1 = r9.getPaddingLeft()
                int r2 = r9.getPaddingTop()
                int r3 = r9.getWidth()
                int r4 = r9.getPaddingRight()
                int r3 = r3 - r4
                int r4 = r9.getHeight()
                int r5 = r9.getPaddingBottom()
                int r4 = r4 - r5
                int r5 = r11.getLeft()
                int r6 = r12.left
                int r5 = r5 + r6
                int r6 = r11.getScrollX()
                int r5 = r5 - r6
                int r6 = r11.getTop()
                int r7 = r12.top
                int r6 = r6 + r7
                int r11 = r11.getScrollY()
                int r6 = r6 - r11
                int r11 = r12.width()
                int r11 = r11 + r5
                int r12 = r12.height()
                int r12 = r12 + r6
                int r5 = r5 - r1
                r1 = 0
                int r7 = java.lang.Math.min(r1, r5)
                int r6 = r6 - r2
                int r2 = java.lang.Math.min(r1, r6)
                int r11 = r11 - r3
                int r3 = java.lang.Math.max(r1, r11)
                int r12 = r12 - r4
                int r12 = java.lang.Math.max(r1, r12)
                int r4 = r9.getLayoutDirection()
                r8 = 1
                if (r4 != r8) goto L63
                if (r3 == 0) goto L5e
                goto L6b
            L5e:
                int r3 = java.lang.Math.max(r7, r11)
                goto L6b
            L63:
                if (r7 == 0) goto L66
                goto L6a
            L66:
                int r7 = java.lang.Math.min(r5, r3)
            L6a:
                r3 = r7
            L6b:
                if (r2 == 0) goto L6e
                goto L72
            L6e:
                int r2 = java.lang.Math.min(r6, r12)
            L72:
                r0[r1] = r3
                r0[r8] = r2
                r11 = r0[r1]
                r12 = r0[r8]
                if (r14 == 0) goto Lbd
                android.view.View r14 = r10.getFocusedChild()
                if (r14 != 0) goto L84
            L82:
                r14 = r1
                goto Lbb
            L84:
                int r0 = r9.getPaddingLeft()
                int r2 = r9.getPaddingTop()
                int r3 = r9.getWidth()
                int r4 = r9.getPaddingRight()
                int r3 = r3 - r4
                int r4 = r9.getHeight()
                int r5 = r9.getPaddingBottom()
                int r4 = r4 - r5
                android.support.v7.widget.RecyclerView r5 = r9.b
                android.graphics.Rect r5 = r5.i
                r9.getDecoratedBoundsWithMargins(r14, r5)
                int r14 = r5.left
                int r14 = r14 - r11
                if (r14 >= r3) goto L82
                int r14 = r5.right
                int r14 = r14 - r11
                if (r14 <= r0) goto L82
                int r14 = r5.top
                int r14 = r14 - r12
                if (r14 >= r4) goto L82
                int r14 = r5.bottom
                int r14 = r14 - r12
                if (r14 > r2) goto Lba
                goto L82
            Lba:
                r14 = r8
            Lbb:
                if (r14 == 0) goto Lc2
            Lbd:
                if (r11 != 0) goto Lc3
                if (r12 == 0) goto Lc2
                goto Lc3
            Lc2:
                return r1
            Lc3:
                if (r13 == 0) goto Lc9
                r10.scrollBy(r11, r12)
                goto Lcc
            Lc9:
                r10.smoothScrollBy(r11, r12)
            Lcc:
                return r8
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.LayoutManager.requestChildRectangleOnScreen(android.support.v7.widget.RecyclerView, android.view.View, android.graphics.Rect, boolean, boolean):boolean");
        }

        public void requestLayout() {
            RecyclerView recyclerView = this.b;
            if (recyclerView != null) {
                recyclerView.requestLayout();
            }
        }

        public void requestSimpleAnimationsInNextLayout() {
            this.h = true;
        }

        public int scrollHorizontallyBy(int i, Recycler recycler, State state) {
            return 0;
        }

        public void scrollToPosition(int i) {
        }

        public int scrollVerticallyBy(int i, Recycler recycler, State state) {
            return 0;
        }

        @Deprecated
        public void setAutoMeasureEnabled(boolean z) {
            this.j = z;
        }

        public final void setItemPrefetchEnabled(boolean z) {
            if (z != this.l) {
                this.l = z;
                this.m = 0;
                RecyclerView recyclerView = this.b;
                if (recyclerView != null) {
                    recyclerView.b.c();
                }
            }
        }

        public void setMeasuredDimension(Rect rect, int i, int i2) {
            setMeasuredDimension(chooseSize(i, getPaddingRight() + getPaddingLeft() + rect.width(), getMinimumWidth()), chooseSize(i2, getPaddingBottom() + getPaddingTop() + rect.height(), getMinimumHeight()));
        }

        public void setMeasurementCacheEnabled(boolean z) {
            this.k = z;
        }

        public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i) {
            Log.e("RecyclerView", "You must override smoothScrollToPosition to support smooth scrolling");
        }

        public void startSmoothScroll(SmoothScroller smoothScroller) {
            SmoothScroller smoothScroller2 = this.g;
            if (smoothScroller2 != null && smoothScroller != smoothScroller2 && smoothScroller2.isRunning()) {
                this.g.stop();
            }
            this.g = smoothScroller;
            RecyclerView recyclerView = this.b;
            smoothScroller.b = recyclerView;
            smoothScroller.c = this;
            int i = smoothScroller.a;
            if (i == -1) {
                throw new IllegalArgumentException("Invalid target position");
            }
            recyclerView.h0.a = i;
            smoothScroller.e = true;
            smoothScroller.d = true;
            smoothScroller.f = smoothScroller.findViewByPosition(smoothScroller.getTargetPosition());
            smoothScroller.onStart();
            smoothScroller.b.e0.a();
        }

        public void stopIgnoringView(View view2) {
            ViewHolder viewHolderD = RecyclerView.d(view2);
            viewHolderD.i &= -129;
            viewHolderD.k();
            viewHolderD.a(4);
        }

        public boolean supportsPredictiveItemAnimations() {
            return false;
        }

        public void addDisappearingView(View view2, int i) {
            a(view2, i, true);
        }

        public void addView(View view2, int i) {
            a(view2, i, false);
        }

        public void onInitializeAccessibilityEvent(Recycler recycler, State state, AccessibilityEvent accessibilityEvent) {
            RecyclerView recyclerView = this.b;
            if (recyclerView == null || accessibilityEvent == null) {
                return;
            }
            boolean z = true;
            if (!recyclerView.canScrollVertically(1) && !this.b.canScrollVertically(-1) && !this.b.canScrollHorizontally(-1) && !this.b.canScrollHorizontally(1)) {
                z = false;
            }
            accessibilityEvent.setScrollable(z);
            Adapter adapter2 = this.b.l;
            if (adapter2 != null) {
                accessibilityEvent.setItemCount(adapter2.getItemCount());
            }
        }

        public boolean onRequestChildFocus(RecyclerView recyclerView, State state, View view2, View view3) {
            return onRequestChildFocus(recyclerView, view2, view3);
        }

        /* JADX WARN: Removed duplicated region for block: B:14:0x0020  */
        /* JADX WARN: Removed duplicated region for block: B:21:0x002f  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public static int getChildMeasureSpec(int r4, int r5, int r6, int r7, boolean r8) {
            /*
                int r4 = r4 - r6
                r6 = 0
                int r4 = java.lang.Math.max(r6, r4)
                r0 = -2
                r1 = -1
                r2 = -2147483648(0xffffffff80000000, float:-0.0)
                r3 = 1073741824(0x40000000, float:2.0)
                if (r8 == 0) goto L1a
                if (r7 < 0) goto L11
                goto L1c
            L11:
                if (r7 != r1) goto L2f
                if (r5 == r2) goto L20
                if (r5 == 0) goto L2f
                if (r5 == r3) goto L20
                goto L2f
            L1a:
                if (r7 < 0) goto L1e
            L1c:
                r5 = r3
                goto L31
            L1e:
                if (r7 != r1) goto L22
            L20:
                r7 = r4
                goto L31
            L22:
                if (r7 != r0) goto L2f
                if (r5 == r2) goto L2c
                if (r5 != r3) goto L29
                goto L2c
            L29:
                r7 = r4
                r5 = r6
                goto L31
            L2c:
                r7 = r4
                r5 = r2
                goto L31
            L2f:
                r5 = r6
                r7 = r5
            L31:
                int r4 = android.view.View.MeasureSpec.makeMeasureSpec(r7, r5)
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.LayoutManager.getChildMeasureSpec(int, int, int, int, boolean):int");
        }

        public void attachView(View view2, int i) {
            attachView(view2, i, (LayoutParams) view2.getLayoutParams());
        }

        public LayoutParams generateLayoutParams(Context context, AttributeSet attributeSet) {
            return new LayoutParams(context, attributeSet);
        }

        public void setMeasuredDimension(int i, int i2) {
            this.b.setMeasuredDimension(i, i2);
        }

        public void attachView(View view2) {
            attachView(view2, -1);
        }

        public void b(int i, int i2) {
            int childCount = getChildCount();
            if (childCount == 0) {
                this.b.b(i, i2);
                return;
            }
            int i3 = Integer.MIN_VALUE;
            int i4 = Integer.MAX_VALUE;
            int i5 = Integer.MAX_VALUE;
            int i6 = Integer.MIN_VALUE;
            for (int i7 = 0; i7 < childCount; i7++) {
                View childAt = getChildAt(i7);
                Rect rect = this.b.i;
                getDecoratedBoundsWithMargins(childAt, rect);
                int i8 = rect.left;
                if (i8 < i4) {
                    i4 = i8;
                }
                int i9 = rect.right;
                if (i9 > i3) {
                    i3 = i9;
                }
                int i10 = rect.top;
                if (i10 < i5) {
                    i5 = i10;
                }
                int i11 = rect.bottom;
                if (i11 > i6) {
                    i6 = i11;
                }
            }
            this.b.i.set(i4, i5, i3, i6);
            setMeasuredDimension(this.b.i, i, i2);
        }

        public void a(int i, int i2) {
            this.q = View.MeasureSpec.getSize(i);
            int mode = View.MeasureSpec.getMode(i);
            this.o = mode;
            if (mode == 0 && !RecyclerView.B0) {
                this.q = 0;
            }
            this.r = View.MeasureSpec.getSize(i2);
            int mode2 = View.MeasureSpec.getMode(i2);
            this.p = mode2;
            if (mode2 != 0 || RecyclerView.B0) {
                return;
            }
            this.r = 0;
        }

        public boolean b(View view2, int i, int i2, LayoutParams layoutParams) {
            return (this.k && a(view2.getMeasuredWidth(), i, ((ViewGroup.MarginLayoutParams) layoutParams).width) && a(view2.getMeasuredHeight(), i2, ((ViewGroup.MarginLayoutParams) layoutParams).height)) ? false : true;
        }

        public final void a(View view2, int i, boolean z) {
            ViewHolder viewHolderD = RecyclerView.d(view2);
            if (!z && !viewHolderD.f()) {
                this.b.f.c(viewHolderD);
            } else {
                this.b.f.a(viewHolderD);
            }
            LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
            if (!viewHolderD.m() && !viewHolderD.g()) {
                if (view2.getParent() == this.b) {
                    int iB = this.a.b(view2);
                    if (i == -1) {
                        i = this.a.a();
                    }
                    if (iB == -1) {
                        StringBuilder sbA = g9.a("Added View has RecyclerView as parent but view is not a real child. Unfiltered index:");
                        sbA.append(this.b.indexOfChild(view2));
                        throw new IllegalStateException(g9.a(this.b, sbA));
                    }
                    if (iB != i) {
                        this.b.m.moveView(iB, i);
                    }
                } else {
                    this.a.a(view2, i, false);
                    layoutParams.c = true;
                    SmoothScroller smoothScroller = this.g;
                    if (smoothScroller != null && smoothScroller.isRunning()) {
                        this.g.onChildAttachedToWindow(view2);
                    }
                }
            } else {
                if (viewHolderD.g()) {
                    viewHolderD.m.b(viewHolderD);
                } else {
                    viewHolderD.b();
                }
                this.a.a(view2, i, view2.getLayoutParams(), false);
            }
            if (layoutParams.d) {
                viewHolderD.itemView.invalidate();
                layoutParams.d = false;
            }
        }

        public boolean requestChildRectangleOnScreen(RecyclerView recyclerView, View view2, Rect rect, boolean z) {
            return requestChildRectangleOnScreen(recyclerView, view2, rect, z, false);
        }

        public final void a(Recycler recycler, int i, View view2) {
            ViewHolder viewHolderD = RecyclerView.d(view2);
            if (viewHolderD.l()) {
                return;
            }
            if (viewHolderD.e() && !viewHolderD.f() && !this.b.l.hasStableIds()) {
                removeViewAt(i);
                recycler.a(viewHolderD);
            } else {
                detachViewAt(i);
                recycler.a(view2);
                this.b.f.c(viewHolderD);
            }
        }

        public boolean a(View view2, int i, int i2, LayoutParams layoutParams) {
            return (!view2.isLayoutRequested() && this.k && a(view2.getWidth(), i, ((ViewGroup.MarginLayoutParams) layoutParams).width) && a(view2.getHeight(), i2, ((ViewGroup.MarginLayoutParams) layoutParams).height)) ? false : true;
        }

        public static boolean a(int i, int i2, int i3) {
            int mode = View.MeasureSpec.getMode(i2);
            int size = View.MeasureSpec.getSize(i2);
            if (i3 > 0 && i != i3) {
                return false;
            }
            if (mode == Integer.MIN_VALUE) {
                return size >= i;
            }
            if (mode != 0) {
                return mode == 1073741824 && size == i;
            }
            return true;
        }

        public void a(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            ViewHolder viewHolderD = RecyclerView.d(view2);
            if (viewHolderD == null || viewHolderD.f() || this.a.c(viewHolderD.itemView)) {
                return;
            }
            RecyclerView recyclerView = this.b;
            onInitializeAccessibilityNodeInfoForItem(recyclerView.b, recyclerView.h0, view2, accessibilityNodeInfoCompat);
        }

        public void a(RecyclerView recyclerView) {
            a(View.MeasureSpec.makeMeasureSpec(recyclerView.getWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(recyclerView.getHeight(), 1073741824));
        }
    }

    public interface OnChildAttachStateChangeListener {
        void onChildViewAttachedToWindow(View view2);

        void onChildViewDetachedFromWindow(View view2);
    }

    public static abstract class OnFlingListener {
        public abstract boolean onFling(int i, int i2);
    }

    public interface OnItemTouchListener {
        boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent);

        void onRequestDisallowInterceptTouchEvent(boolean z);

        void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent);
    }

    public static abstract class OnScrollListener {
        public void onScrollStateChanged(RecyclerView recyclerView, int i) {
        }

        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface Orientation {
    }

    public static class RecycledViewPool {
        public SparseArray<a> a = new SparseArray<>();
        public int b = 0;

        public static class a {
            public final ArrayList<ViewHolder> a = new ArrayList<>();
            public int b = 5;
            public long c = 0;
            public long d = 0;
        }

        public long a(long j, long j2) {
            if (j == 0) {
                return j2;
            }
            return (j2 / 4) + ((j / 4) * 3);
        }

        public void clear() {
            for (int i = 0; i < this.a.size(); i++) {
                this.a.valueAt(i).a.clear();
            }
        }

        @Nullable
        public ViewHolder getRecycledView(int i) {
            a aVar = this.a.get(i);
            if (aVar == null || aVar.a.isEmpty()) {
                return null;
            }
            return aVar.a.remove(r2.size() - 1);
        }

        public int getRecycledViewCount(int i) {
            return a(i).a.size();
        }

        public void putRecycledView(ViewHolder viewHolder) {
            int itemViewType = viewHolder.getItemViewType();
            ArrayList<ViewHolder> arrayList = a(itemViewType).a;
            if (this.a.get(itemViewType).b <= arrayList.size()) {
                return;
            }
            viewHolder.k();
            arrayList.add(viewHolder);
        }

        public void setMaxRecycledViews(int i, int i2) {
            a aVarA = a(i);
            aVarA.b = i2;
            ArrayList<ViewHolder> arrayList = aVarA.a;
            while (arrayList.size() > i2) {
                arrayList.remove(arrayList.size() - 1);
            }
        }

        public final a a(int i) {
            a aVar = this.a.get(i);
            if (aVar != null) {
                return aVar;
            }
            a aVar2 = new a();
            this.a.put(i, aVar2);
            return aVar2;
        }
    }

    public interface RecyclerListener {
        void onViewRecycled(ViewHolder viewHolder);
    }

    public static class SimpleOnItemTouchListener implements OnItemTouchListener {
        @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            return false;
        }

        @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
        public void onRequestDisallowInterceptTouchEvent(boolean z) {
        }

        @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        }
    }

    public static abstract class SmoothScroller {
        public RecyclerView b;
        public LayoutManager c;
        public boolean d;
        public boolean e;
        public View f;
        public int a = -1;
        public final Action g = new Action(0, 0);

        public static class Action {
            public static final int UNDEFINED_DURATION = Integer.MIN_VALUE;
            public int a;
            public int b;
            public int c;
            public int d;
            public Interpolator e;
            public boolean f;
            public int g;

            public Action(int i, int i2) {
                this(i, i2, Integer.MIN_VALUE, null);
            }

            public void a(RecyclerView recyclerView) {
                int i = this.d;
                if (i >= 0) {
                    this.d = -1;
                    recyclerView.a(i);
                    this.f = false;
                    return;
                }
                if (!this.f) {
                    this.g = 0;
                    return;
                }
                if (this.e != null && this.c < 1) {
                    throw new IllegalStateException("If you provide an interpolator, you must set a positive duration");
                }
                int i2 = this.c;
                if (i2 < 1) {
                    throw new IllegalStateException("Scroll duration must be a positive number");
                }
                Interpolator interpolator = this.e;
                if (interpolator != null) {
                    recyclerView.e0.a(this.a, this.b, i2, interpolator);
                } else if (i2 == Integer.MIN_VALUE) {
                    h hVar = recyclerView.e0;
                    int i3 = this.a;
                    int i4 = this.b;
                    hVar.a(i3, i4, hVar.a(i3, i4, 0, 0), RecyclerView.E0);
                } else {
                    h hVar2 = recyclerView.e0;
                    int i5 = this.a;
                    int i6 = this.b;
                    if (hVar2 == null) {
                        throw null;
                    }
                    hVar2.a(i5, i6, i2, RecyclerView.E0);
                }
                int i7 = this.g + 1;
                this.g = i7;
                if (i7 > 10) {
                    Log.e("RecyclerView", "Smooth Scroll action is being updated too frequently. Make sure you are not changing it unless necessary");
                }
                this.f = false;
            }

            public int getDuration() {
                return this.c;
            }

            public int getDx() {
                return this.a;
            }

            public int getDy() {
                return this.b;
            }

            public Interpolator getInterpolator() {
                return this.e;
            }

            public void jumpTo(int i) {
                this.d = i;
            }

            public void setDuration(int i) {
                this.f = true;
                this.c = i;
            }

            public void setDx(int i) {
                this.f = true;
                this.a = i;
            }

            public void setDy(int i) {
                this.f = true;
                this.b = i;
            }

            public void setInterpolator(Interpolator interpolator) {
                this.f = true;
                this.e = interpolator;
            }

            public void update(int i, int i2, int i3, Interpolator interpolator) {
                this.a = i;
                this.b = i2;
                this.c = i3;
                this.e = interpolator;
                this.f = true;
            }

            public Action(int i, int i2, int i3) {
                this(i, i2, i3, null);
            }

            public Action(int i, int i2, int i3, Interpolator interpolator) {
                this.d = -1;
                this.f = false;
                this.g = 0;
                this.a = i;
                this.b = i2;
                this.c = i3;
                this.e = interpolator;
            }
        }

        public interface ScrollVectorProvider {
            PointF computeScrollVectorForPosition(int i);
        }

        public static /* synthetic */ void a(SmoothScroller smoothScroller, int i, int i2) {
            RecyclerView recyclerView = smoothScroller.b;
            if (!smoothScroller.e || smoothScroller.a == -1 || recyclerView == null) {
                smoothScroller.stop();
            }
            smoothScroller.d = false;
            View view2 = smoothScroller.f;
            if (view2 != null) {
                if (smoothScroller.getChildPosition(view2) == smoothScroller.a) {
                    smoothScroller.onTargetFound(smoothScroller.f, recyclerView.h0, smoothScroller.g);
                    smoothScroller.g.a(recyclerView);
                    smoothScroller.stop();
                } else {
                    Log.e("RecyclerView", "Passed over target position while smooth scrolling.");
                    smoothScroller.f = null;
                }
            }
            if (smoothScroller.e) {
                smoothScroller.onSeekTargetStep(i, i2, recyclerView.h0, smoothScroller.g);
                boolean z = smoothScroller.g.d >= 0;
                smoothScroller.g.a(recyclerView);
                if (z) {
                    if (!smoothScroller.e) {
                        smoothScroller.stop();
                    } else {
                        smoothScroller.d = true;
                        recyclerView.e0.a();
                    }
                }
            }
        }

        public View findViewByPosition(int i) {
            return this.b.m.findViewByPosition(i);
        }

        public int getChildCount() {
            return this.b.m.getChildCount();
        }

        public int getChildPosition(View view2) {
            return this.b.getChildLayoutPosition(view2);
        }

        @Nullable
        public LayoutManager getLayoutManager() {
            return this.c;
        }

        public int getTargetPosition() {
            return this.a;
        }

        @Deprecated
        public void instantScrollToPosition(int i) {
            this.b.scrollToPosition(i);
        }

        public boolean isPendingInitialRun() {
            return this.d;
        }

        public boolean isRunning() {
            return this.e;
        }

        public void normalize(PointF pointF) {
            float f = pointF.x;
            float f2 = pointF.y;
            float fSqrt = (float) Math.sqrt((f2 * f2) + (f * f));
            pointF.x /= fSqrt;
            pointF.y /= fSqrt;
        }

        public void onChildAttachedToWindow(View view2) {
            if (getChildPosition(view2) == getTargetPosition()) {
                this.f = view2;
            }
        }

        public abstract void onSeekTargetStep(int i, int i2, State state, Action action);

        public abstract void onStart();

        public abstract void onStop();

        public abstract void onTargetFound(View view2, State state, Action action);

        public void setTargetPosition(int i) {
            this.a = i;
        }

        public final void stop() {
            if (this.e) {
                onStop();
                this.b.h0.a = -1;
                this.f = null;
                this.a = -1;
                this.d = false;
                this.e = false;
                LayoutManager layoutManager = this.c;
                if (layoutManager.g == this) {
                    layoutManager.g = null;
                }
                this.c = null;
                this.b = null;
            }
        }
    }

    public static class State {
        public SparseArray<Object> b;
        public int m;
        public long n;
        public int o;
        public int p;
        public int q;
        public int a = -1;
        public int c = 0;
        public int d = 0;
        public int e = 1;
        public int f = 0;
        public boolean g = false;
        public boolean h = false;
        public boolean i = false;
        public boolean j = false;
        public boolean k = false;
        public boolean l = false;

        public void a(int i) {
            if ((this.e & i) != 0) {
                return;
            }
            StringBuilder sbA = g9.a("Layout state should be one of ");
            sbA.append(Integer.toBinaryString(i));
            sbA.append(" but it is ");
            sbA.append(Integer.toBinaryString(this.e));
            throw new IllegalStateException(sbA.toString());
        }

        public boolean didStructureChange() {
            return this.g;
        }

        public <T> T get(int i) {
            SparseArray<Object> sparseArray = this.b;
            if (sparseArray == null) {
                return null;
            }
            return (T) sparseArray.get(i);
        }

        public int getItemCount() {
            return this.h ? this.c - this.d : this.f;
        }

        public int getRemainingScrollHorizontal() {
            return this.p;
        }

        public int getRemainingScrollVertical() {
            return this.q;
        }

        public int getTargetScrollPosition() {
            return this.a;
        }

        public boolean hasTargetScrollPosition() {
            return this.a != -1;
        }

        public boolean isMeasuring() {
            return this.j;
        }

        public boolean isPreLayout() {
            return this.h;
        }

        public void put(int i, Object obj) {
            if (this.b == null) {
                this.b = new SparseArray<>();
            }
            this.b.put(i, obj);
        }

        public void remove(int i) {
            SparseArray<Object> sparseArray = this.b;
            if (sparseArray == null) {
                return;
            }
            sparseArray.remove(i);
        }

        public String toString() {
            StringBuilder sbA = g9.a("State{mTargetPosition=");
            sbA.append(this.a);
            sbA.append(", mData=");
            sbA.append(this.b);
            sbA.append(", mItemCount=");
            sbA.append(this.f);
            sbA.append(", mIsMeasuring=");
            sbA.append(this.j);
            sbA.append(", mPreviousLayoutItemCount=");
            sbA.append(this.c);
            sbA.append(", mDeletedInvisibleItemCountSincePreviousLayout=");
            sbA.append(this.d);
            sbA.append(", mStructureChanged=");
            sbA.append(this.g);
            sbA.append(", mInPreLayout=");
            sbA.append(this.h);
            sbA.append(", mRunSimpleAnimations=");
            sbA.append(this.k);
            sbA.append(", mRunPredictiveAnimations=");
            sbA.append(this.l);
            sbA.append('}');
            return sbA.toString();
        }

        public boolean willRunPredictiveAnimations() {
            return this.l;
        }

        public boolean willRunSimpleAnimations() {
            return this.k;
        }
    }

    public static abstract class ViewCacheExtension {
        public abstract View getViewForPositionAndType(Recycler recycler, int i, int i2);
    }

    public static abstract class ViewHolder {
        public static final List<Object> r = Collections.EMPTY_LIST;
        public WeakReference<RecyclerView> a;
        public int i;
        public final View itemView;
        public RecyclerView q;
        public int b = -1;
        public int c = -1;
        public long d = -1;
        public int e = -1;
        public int f = -1;
        public ViewHolder g = null;
        public ViewHolder h = null;
        public List<Object> j = null;
        public List<Object> k = null;
        public int l = 0;
        public Recycler m = null;
        public boolean n = false;
        public int o = 0;

        @VisibleForTesting
        public int p = -1;

        public ViewHolder(View view2) {
            if (view2 == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = view2;
        }

        public void a(int i, boolean z) {
            if (this.c == -1) {
                this.c = this.b;
            }
            if (this.f == -1) {
                this.f = this.b;
            }
            if (z) {
                this.f += i;
            }
            this.b += i;
            if (this.itemView.getLayoutParams() != null) {
                ((LayoutParams) this.itemView.getLayoutParams()).c = true;
            }
        }

        public void b() {
            this.i &= -33;
        }

        public List<Object> c() {
            if ((this.i & 1024) != 0) {
                return r;
            }
            List<Object> list = this.j;
            return (list == null || list.size() == 0) ? r : this.k;
        }

        public boolean d() {
            return (this.i & 1) != 0;
        }

        public boolean e() {
            return (this.i & 4) != 0;
        }

        public boolean f() {
            return (this.i & 8) != 0;
        }

        public boolean g() {
            return this.m != null;
        }

        public final int getAdapterPosition() {
            RecyclerView recyclerView = this.q;
            if (recyclerView == null) {
                return -1;
            }
            return recyclerView.b(this);
        }

        public final long getItemId() {
            return this.d;
        }

        public final int getItemViewType() {
            return this.e;
        }

        public final int getLayoutPosition() {
            int i = this.f;
            return i == -1 ? this.b : i;
        }

        public final int getOldPosition() {
            return this.c;
        }

        @Deprecated
        public final int getPosition() {
            int i = this.f;
            return i == -1 ? this.b : i;
        }

        public boolean h() {
            return (this.i & 256) != 0;
        }

        public boolean i() {
            return (this.i & 2) != 0;
        }

        public final boolean isRecyclable() {
            return (this.i & 16) == 0 && !ViewCompat.hasTransientState(this.itemView);
        }

        public boolean j() {
            return (this.i & 2) != 0;
        }

        public void k() {
            this.i = 0;
            this.b = -1;
            this.c = -1;
            this.d = -1L;
            this.f = -1;
            this.l = 0;
            this.g = null;
            this.h = null;
            List<Object> list = this.j;
            if (list != null) {
                list.clear();
            }
            this.i &= -1025;
            this.o = 0;
            this.p = -1;
            RecyclerView.d(this);
        }

        public boolean l() {
            return (this.i & 128) != 0;
        }

        public boolean m() {
            return (this.i & 32) != 0;
        }

        public final void setIsRecyclable(boolean z) {
            int i = this.l;
            int i2 = z ? i - 1 : i + 1;
            this.l = i2;
            if (i2 < 0) {
                this.l = 0;
                Log.e("View", "isRecyclable decremented below 0: unmatched pair of setIsRecyable() calls for " + this);
                return;
            }
            if (!z && i2 == 1) {
                this.i |= 16;
            } else if (z && this.l == 0) {
                this.i &= -17;
            }
        }

        public String toString() {
            StringBuilder sbA = g9.a("ViewHolder{");
            sbA.append(Integer.toHexString(hashCode()));
            sbA.append(" position=");
            sbA.append(this.b);
            sbA.append(" id=");
            sbA.append(this.d);
            sbA.append(", oldPos=");
            sbA.append(this.c);
            sbA.append(", pLpos:");
            sbA.append(this.f);
            StringBuilder sb = new StringBuilder(sbA.toString());
            if (g()) {
                sb.append(" scrap ");
                sb.append(this.n ? "[changeScrap]" : "[attachedScrap]");
            }
            if (e()) {
                sb.append(" invalid");
            }
            if (!d()) {
                sb.append(" unbound");
            }
            if (j()) {
                sb.append(" update");
            }
            if (f()) {
                sb.append(" removed");
            }
            if (l()) {
                sb.append(" ignored");
            }
            if (h()) {
                sb.append(" tmpDetached");
            }
            if (!isRecyclable()) {
                StringBuilder sbA2 = g9.a(" not recyclable(");
                sbA2.append(this.l);
                sbA2.append(")");
                sb.append(sbA2.toString());
            }
            if ((this.i & 512) != 0 || e()) {
                sb.append(" undefined adapter position");
            }
            if (this.itemView.getParent() == null) {
                sb.append(" no parent");
            }
            sb.append("}");
            return sb.toString();
        }

        public boolean b(int i) {
            return (i & this.i) != 0;
        }

        public void a() {
            this.c = -1;
            this.f = -1;
        }

        public void a(int i, int i2) {
            this.i = (i & i2) | (this.i & (~i2));
        }

        public void a(int i) {
            this.i = i | this.i;
        }

        public void a(Object obj) {
            if (obj == null) {
                a(1024);
                return;
            }
            if ((1024 & this.i) == 0) {
                if (this.j == null) {
                    ArrayList arrayList = new ArrayList();
                    this.j = arrayList;
                    this.k = Collections.unmodifiableList(arrayList);
                }
                this.j.add(obj);
            }
        }
    }

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            RecyclerView recyclerView = RecyclerView.this;
            if (!recyclerView.u || recyclerView.isLayoutRequested()) {
                return;
            }
            RecyclerView recyclerView2 = RecyclerView.this;
            if (!recyclerView2.r) {
                recyclerView2.requestLayout();
            } else if (recyclerView2.x) {
                recyclerView2.w = true;
            } else {
                recyclerView2.c();
            }
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ItemAnimator itemAnimator = RecyclerView.this.M;
            if (itemAnimator != null) {
                itemAnimator.runPendingAnimations();
            }
            RecyclerView.this.n0 = false;
        }
    }

    public static class c implements Interpolator {
        @Override // android.animation.TimeInterpolator
        public float getInterpolation(float f) {
            float f2 = f - 1.0f;
            return (f2 * f2 * f2 * f2 * f2) + 1.0f;
        }
    }

    public class d implements z8.b {
        public d() {
        }

        public void a(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo, ItemAnimator.ItemHolderInfo itemHolderInfo2) {
            RecyclerView recyclerView = RecyclerView.this;
            if (recyclerView == null) {
                throw null;
            }
            viewHolder.setIsRecyclable(false);
            if (recyclerView.M.animateAppearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
                recyclerView.o();
            }
        }

        public void b(ViewHolder viewHolder, @NonNull ItemAnimator.ItemHolderInfo itemHolderInfo, @Nullable ItemAnimator.ItemHolderInfo itemHolderInfo2) {
            RecyclerView.this.b.b(viewHolder);
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.a(viewHolder);
            viewHolder.setIsRecyclable(false);
            if (recyclerView.M.animateDisappearance(viewHolder, itemHolderInfo, itemHolderInfo2)) {
                recyclerView.o();
            }
        }
    }

    public static class e extends Observable<AdapterDataObserver> {
        public boolean a() {
            return !((Observable) this).mObservers.isEmpty();
        }

        public void b() {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                g gVar = (g) ((AdapterDataObserver) ((Observable) this).mObservers.get(size));
                RecyclerView.this.a((String) null);
                RecyclerView recyclerView = RecyclerView.this;
                recyclerView.h0.g = true;
                recyclerView.b(true);
                if (!RecyclerView.this.d.c()) {
                    RecyclerView.this.requestLayout();
                }
            }
        }

        public void c(int i, int i2) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                g gVar = (g) ((AdapterDataObserver) ((Observable) this).mObservers.get(size));
                RecyclerView.this.a((String) null);
                o7 o7Var = RecyclerView.this.d;
                if (o7Var == null) {
                    throw null;
                }
                boolean z = false;
                if (i2 >= 1) {
                    o7Var.b.add(o7Var.a(2, i, i2, null));
                    o7Var.g |= 2;
                    if (o7Var.b.size() == 1) {
                        z = true;
                    }
                }
                if (z) {
                    gVar.a();
                }
            }
        }

        public void a(int i, int i2, @Nullable Object obj) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                g gVar = (g) ((AdapterDataObserver) ((Observable) this).mObservers.get(size));
                RecyclerView.this.a((String) null);
                o7 o7Var = RecyclerView.this.d;
                if (o7Var == null) {
                    throw null;
                }
                boolean z = false;
                if (i2 >= 1) {
                    o7Var.b.add(o7Var.a(4, i, i2, obj));
                    o7Var.g |= 4;
                    if (o7Var.b.size() == 1) {
                        z = true;
                    }
                }
                if (z) {
                    gVar.a();
                }
            }
        }

        public void b(int i, int i2) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                g gVar = (g) ((AdapterDataObserver) ((Observable) this).mObservers.get(size));
                RecyclerView.this.a((String) null);
                o7 o7Var = RecyclerView.this.d;
                if (o7Var == null) {
                    throw null;
                }
                boolean z = false;
                if (i2 >= 1) {
                    o7Var.b.add(o7Var.a(1, i, i2, null));
                    o7Var.g |= 1;
                    if (o7Var.b.size() == 1) {
                        z = true;
                    }
                }
                if (z) {
                    gVar.a();
                }
            }
        }

        public void a(int i, int i2) {
            for (int size = ((Observable) this).mObservers.size() - 1; size >= 0; size--) {
                g gVar = (g) ((AdapterDataObserver) ((Observable) this).mObservers.get(size));
                RecyclerView.this.a((String) null);
                o7 o7Var = RecyclerView.this.d;
                if (o7Var == null) {
                    throw null;
                }
                boolean z = false;
                if (i != i2) {
                    o7Var.b.add(o7Var.a(8, i, i2, null));
                    o7Var.g |= 8;
                    if (o7Var.b.size() == 1) {
                        z = true;
                    }
                }
                if (z) {
                    gVar.a();
                }
            }
        }
    }

    public class f implements ItemAnimator.a {
        public f() {
        }
    }

    public class g extends AdapterDataObserver {
        public g() {
        }

        public void a() {
            if (RecyclerView.C0) {
                RecyclerView recyclerView = RecyclerView.this;
                if (recyclerView.s && recyclerView.r) {
                    ViewCompat.postOnAnimation(recyclerView, recyclerView.h);
                    return;
                }
            }
            RecyclerView recyclerView2 = RecyclerView.this;
            recyclerView2.A = true;
            recyclerView2.requestLayout();
        }

        @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
        public void onChanged() {
            RecyclerView.this.a((String) null);
            RecyclerView recyclerView = RecyclerView.this;
            recyclerView.h0.g = true;
            recyclerView.b(true);
            if (RecyclerView.this.d.c()) {
                return;
            }
            RecyclerView.this.requestLayout();
        }

        @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeChanged(int i, int i2, Object obj) {
            RecyclerView.this.a((String) null);
            o7 o7Var = RecyclerView.this.d;
            if (o7Var == null) {
                throw null;
            }
            boolean z = false;
            if (i2 >= 1) {
                o7Var.b.add(o7Var.a(4, i, i2, obj));
                o7Var.g |= 4;
                if (o7Var.b.size() == 1) {
                    z = true;
                }
            }
            if (z) {
                a();
            }
        }

        @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeInserted(int i, int i2) {
            RecyclerView.this.a((String) null);
            o7 o7Var = RecyclerView.this.d;
            if (o7Var == null) {
                throw null;
            }
            boolean z = false;
            if (i2 >= 1) {
                o7Var.b.add(o7Var.a(1, i, i2, null));
                o7Var.g |= 1;
                if (o7Var.b.size() == 1) {
                    z = true;
                }
            }
            if (z) {
                a();
            }
        }

        @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeMoved(int i, int i2, int i3) {
            RecyclerView.this.a((String) null);
            o7 o7Var = RecyclerView.this.d;
            if (o7Var == null) {
                throw null;
            }
            boolean z = false;
            if (i != i2) {
                if (i3 != 1) {
                    throw new IllegalArgumentException("Moving more than 1 item is not supported yet");
                }
                o7Var.b.add(o7Var.a(8, i, i2, null));
                o7Var.g |= 8;
                if (o7Var.b.size() == 1) {
                    z = true;
                }
            }
            if (z) {
                a();
            }
        }

        @Override // android.support.v7.widget.RecyclerView.AdapterDataObserver
        public void onItemRangeRemoved(int i, int i2) {
            RecyclerView.this.a((String) null);
            o7 o7Var = RecyclerView.this.d;
            if (o7Var == null) {
                throw null;
            }
            boolean z = false;
            if (i2 >= 1) {
                o7Var.b.add(o7Var.a(2, i, i2, null));
                o7Var.g |= 2;
                if (o7Var.b.size() == 1) {
                    z = true;
                }
            }
            if (z) {
                a();
            }
        }
    }

    static {
        Class<?> cls = Integer.TYPE;
        D0 = new Class[]{Context.class, AttributeSet.class, cls, cls};
        E0 = new c();
    }

    public RecyclerView(Context context) {
        this(context, null);
    }

    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (this.r0 == null) {
            this.r0 = new NestedScrollingChildHelper(this);
        }
        return this.r0;
    }

    public static /* synthetic */ boolean v() {
        return true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void addFocusables(ArrayList<View> arrayList, int i, int i2) {
        LayoutManager layoutManager = this.m;
        if (layoutManager == null || !layoutManager.onAddFocusables(this, arrayList, i, i2)) {
            super.addFocusables(arrayList, i, i2);
        }
    }

    public void addItemDecoration(ItemDecoration itemDecoration, int i) {
        LayoutManager layoutManager = this.m;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot add item decoration during a scroll  or layout");
        }
        if (this.o.isEmpty()) {
            setWillNotDraw(false);
        }
        if (i < 0) {
            this.o.add(itemDecoration);
        } else {
            this.o.add(i, itemDecoration);
        }
        m();
        requestLayout();
    }

    public void addOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        if (this.C == null) {
            this.C = new ArrayList();
        }
        this.C.add(onChildAttachStateChangeListener);
    }

    public void addOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.p.add(onItemTouchListener);
    }

    public void addOnScrollListener(OnScrollListener onScrollListener) {
        if (this.j0 == null) {
            this.j0 = new ArrayList();
        }
        this.j0.add(onScrollListener);
    }

    public void b(int i, int i2) {
        setMeasuredDimension(LayoutManager.chooseSize(i, getPaddingRight() + getPaddingLeft(), ViewCompat.getMinimumWidth(this)), LayoutManager.chooseSize(i2, getPaddingBottom() + getPaddingTop(), ViewCompat.getMinimumHeight(this)));
    }

    public void c() {
        if (!this.u || this.D) {
            TraceCompat.beginSection("RV FullInvalidate");
            d();
            TraceCompat.endSection();
            return;
        }
        if (this.d.c()) {
            boolean z = false;
            if ((this.d.g & 4) != 0) {
                if (!((this.d.g & 11) != 0)) {
                    TraceCompat.beginSection("RV PartialInvalidate");
                    t();
                    n();
                    this.d.d();
                    if (!this.w) {
                        int iA = this.e.a();
                        int i = 0;
                        while (true) {
                            if (i < iA) {
                                ViewHolder viewHolderD = d(this.e.b(i));
                                if (viewHolderD != null && !viewHolderD.l() && viewHolderD.i()) {
                                    z = true;
                                    break;
                                }
                                i++;
                            } else {
                                break;
                            }
                        }
                        if (z) {
                            d();
                        } else {
                            this.d.a();
                        }
                    }
                    c(true);
                    a(true);
                    TraceCompat.endSection();
                    return;
                }
            }
            if (this.d.c()) {
                TraceCompat.beginSection("RV FullInvalidate");
                d();
                TraceCompat.endSection();
            }
        }
    }

    @Override // android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return (layoutParams instanceof LayoutParams) && this.m.checkLayoutParams((LayoutParams) layoutParams);
    }

    public void clearOnChildAttachStateChangeListeners() {
        List<OnChildAttachStateChangeListener> list = this.C;
        if (list != null) {
            list.clear();
        }
    }

    public void clearOnScrollListeners() {
        List<OnScrollListener> list = this.j0;
        if (list != null) {
            list.clear();
        }
    }

    @Override // android.view.View, android.support.v4.view.ScrollingView
    public int computeHorizontalScrollExtent() {
        LayoutManager layoutManager = this.m;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            return this.m.computeHorizontalScrollExtent(this.h0);
        }
        return 0;
    }

    @Override // android.view.View, android.support.v4.view.ScrollingView
    public int computeHorizontalScrollOffset() {
        LayoutManager layoutManager = this.m;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            return this.m.computeHorizontalScrollOffset(this.h0);
        }
        return 0;
    }

    @Override // android.view.View, android.support.v4.view.ScrollingView
    public int computeHorizontalScrollRange() {
        LayoutManager layoutManager = this.m;
        if (layoutManager != null && layoutManager.canScrollHorizontally()) {
            return this.m.computeHorizontalScrollRange(this.h0);
        }
        return 0;
    }

    @Override // android.view.View, android.support.v4.view.ScrollingView
    public int computeVerticalScrollExtent() {
        LayoutManager layoutManager = this.m;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            return this.m.computeVerticalScrollExtent(this.h0);
        }
        return 0;
    }

    @Override // android.view.View, android.support.v4.view.ScrollingView
    public int computeVerticalScrollOffset() {
        LayoutManager layoutManager = this.m;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            return this.m.computeVerticalScrollOffset(this.h0);
        }
        return 0;
    }

    @Override // android.view.View, android.support.v4.view.ScrollingView
    public int computeVerticalScrollRange() {
        LayoutManager layoutManager = this.m;
        if (layoutManager != null && layoutManager.canScrollVertically()) {
            return this.m.computeVerticalScrollRange(this.h0);
        }
        return 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:139:0x0300  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void d() {
        /*
            Method dump skipped, instructions count: 928
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.d():void");
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public boolean dispatchNestedFling(float f2, float f3, boolean z) {
        return getScrollingChildHelper().dispatchNestedFling(f2, f3, z);
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public boolean dispatchNestedPreFling(float f2, float f3) {
        return getScrollingChildHelper().dispatchNestedPreFling(f2, f3);
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2) {
        return getScrollingChildHelper().dispatchNestedPreScroll(i, i2, iArr, iArr2);
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr) {
        return getScrollingChildHelper().dispatchNestedScroll(i, i2, i3, i4, iArr);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchRestoreInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchThawSelfOnly(sparseArray);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        dispatchFreezeSelfOnly(sparseArray);
    }

    @Override // android.view.View
    public void draw(Canvas canvas) {
        boolean z;
        super.draw(canvas);
        int size = this.o.size();
        boolean z2 = false;
        for (int i = 0; i < size; i++) {
            this.o.get(i).onDrawOver(canvas, this, this.h0);
        }
        EdgeEffect edgeEffect = this.I;
        if (edgeEffect == null || edgeEffect.isFinished()) {
            z = false;
        } else {
            int iSave = canvas.save();
            int paddingBottom = this.g ? getPaddingBottom() : 0;
            canvas.rotate(270.0f);
            canvas.translate((-getHeight()) + paddingBottom, 0.0f);
            EdgeEffect edgeEffect2 = this.I;
            z = edgeEffect2 != null && edgeEffect2.draw(canvas);
            canvas.restoreToCount(iSave);
        }
        EdgeEffect edgeEffect3 = this.J;
        if (edgeEffect3 != null && !edgeEffect3.isFinished()) {
            int iSave2 = canvas.save();
            if (this.g) {
                canvas.translate(getPaddingLeft(), getPaddingTop());
            }
            EdgeEffect edgeEffect4 = this.J;
            z |= edgeEffect4 != null && edgeEffect4.draw(canvas);
            canvas.restoreToCount(iSave2);
        }
        EdgeEffect edgeEffect5 = this.K;
        if (edgeEffect5 != null && !edgeEffect5.isFinished()) {
            int iSave3 = canvas.save();
            int width = getWidth();
            int paddingTop = this.g ? getPaddingTop() : 0;
            canvas.rotate(90.0f);
            canvas.translate(-paddingTop, -width);
            EdgeEffect edgeEffect6 = this.K;
            z |= edgeEffect6 != null && edgeEffect6.draw(canvas);
            canvas.restoreToCount(iSave3);
        }
        EdgeEffect edgeEffect7 = this.L;
        if (edgeEffect7 != null && !edgeEffect7.isFinished()) {
            int iSave4 = canvas.save();
            canvas.rotate(180.0f);
            if (this.g) {
                canvas.translate(getPaddingRight() + (-getWidth()), getPaddingBottom() + (-getHeight()));
            } else {
                canvas.translate(-getWidth(), -getHeight());
            }
            EdgeEffect edgeEffect8 = this.L;
            if (edgeEffect8 != null && edgeEffect8.draw(canvas)) {
                z2 = true;
            }
            z |= z2;
            canvas.restoreToCount(iSave4);
        }
        if ((z || this.M == null || this.o.size() <= 0 || !this.M.isRunning()) ? z : true) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.ViewGroup
    public boolean drawChild(Canvas canvas, View view2, long j) {
        return super.drawChild(canvas, view2, j);
    }

    public final void e() {
        this.h0.a(1);
        a(this.h0);
        this.h0.j = false;
        t();
        z8 z8Var = this.f;
        z8Var.a.clear();
        z8Var.b.clear();
        n();
        p();
        View focusedChild = (this.d0 && hasFocus() && this.l != null) ? getFocusedChild() : null;
        ViewHolder viewHolderFindContainingViewHolder = focusedChild != null ? findContainingViewHolder(focusedChild) : null;
        if (viewHolderFindContainingViewHolder == null) {
            State state = this.h0;
            state.n = -1L;
            state.m = -1;
            state.o = -1;
        } else {
            this.h0.n = this.l.hasStableIds() ? viewHolderFindContainingViewHolder.getItemId() : -1L;
            this.h0.m = this.D ? -1 : viewHolderFindContainingViewHolder.f() ? viewHolderFindContainingViewHolder.c : viewHolderFindContainingViewHolder.getAdapterPosition();
            State state2 = this.h0;
            View focusedChild2 = viewHolderFindContainingViewHolder.itemView;
            int id = focusedChild2.getId();
            while (!focusedChild2.isFocused() && (focusedChild2 instanceof ViewGroup) && focusedChild2.hasFocus()) {
                focusedChild2 = ((ViewGroup) focusedChild2).getFocusedChild();
                if (focusedChild2.getId() != -1) {
                    id = focusedChild2.getId();
                }
            }
            state2.o = id;
        }
        State state3 = this.h0;
        state3.i = state3.k && this.l0;
        this.l0 = false;
        this.k0 = false;
        State state4 = this.h0;
        state4.h = state4.l;
        state4.f = this.l.getItemCount();
        a(this.q0);
        if (this.h0.k) {
            int iA = this.e.a();
            for (int i = 0; i < iA; i++) {
                ViewHolder viewHolderD = d(this.e.b(i));
                if (!viewHolderD.l() && (!viewHolderD.e() || this.l.hasStableIds())) {
                    this.f.b(viewHolderD, this.M.recordPreLayoutInformation(this.h0, viewHolderD, ItemAnimator.a(viewHolderD), viewHolderD.c()));
                    if (this.h0.i && viewHolderD.i() && !viewHolderD.f() && !viewHolderD.l() && !viewHolderD.e()) {
                        this.f.b.put(c(viewHolderD), viewHolderD);
                    }
                }
            }
        }
        if (this.h0.l) {
            int iB = this.e.b();
            for (int i2 = 0; i2 < iB; i2++) {
                ViewHolder viewHolderD2 = d(this.e.d(i2));
                if (!viewHolderD2.l() && viewHolderD2.c == -1) {
                    viewHolderD2.c = viewHolderD2.b;
                }
            }
            State state5 = this.h0;
            boolean z = state5.g;
            state5.g = false;
            this.m.onLayoutChildren(this.b, state5);
            this.h0.g = z;
            for (int i3 = 0; i3 < this.e.a(); i3++) {
                ViewHolder viewHolderD3 = d(this.e.b(i3));
                if (!viewHolderD3.l()) {
                    z8.a aVar = this.f.a.get(viewHolderD3);
                    if (!((aVar == null || (aVar.a & 4) == 0) ? false : true)) {
                        int iA2 = ItemAnimator.a(viewHolderD3);
                        boolean zB = viewHolderD3.b(8192);
                        if (!zB) {
                            iA2 |= 4096;
                        }
                        ItemAnimator.ItemHolderInfo itemHolderInfoRecordPreLayoutInformation = this.M.recordPreLayoutInformation(this.h0, viewHolderD3, iA2, viewHolderD3.c());
                        if (zB) {
                            a(viewHolderD3, itemHolderInfoRecordPreLayoutInformation);
                        } else {
                            z8 z8Var2 = this.f;
                            z8.a aVarA = z8Var2.a.get(viewHolderD3);
                            if (aVarA == null) {
                                aVarA = z8.a.a();
                                z8Var2.a.put(viewHolderD3, aVarA);
                            }
                            aVarA.a |= 2;
                            aVarA.b = itemHolderInfoRecordPreLayoutInformation;
                        }
                    }
                }
            }
            b();
        } else {
            b();
        }
        a(true);
        c(false);
        this.h0.e = 2;
    }

    public final void f() {
        t();
        n();
        this.h0.a(6);
        this.d.b();
        this.h0.f = this.l.getItemCount();
        State state = this.h0;
        state.d = 0;
        state.h = false;
        this.m.onLayoutChildren(this.b, state);
        State state2 = this.h0;
        state2.g = false;
        this.c = null;
        state2.k = state2.k && this.M != null;
        this.h0.e = 4;
        a(true);
        c(false);
    }

    public View findChildViewUnder(float f2, float f3) {
        for (int iA = this.e.a() - 1; iA >= 0; iA--) {
            View viewB = this.e.b(iA);
            float translationX = viewB.getTranslationX();
            float translationY = viewB.getTranslationY();
            if (f2 >= viewB.getLeft() + translationX && f2 <= viewB.getRight() + translationX && f3 >= viewB.getTop() + translationY && f3 <= viewB.getBottom() + translationY) {
                return viewB;
            }
        }
        return null;
    }

    @Nullable
    public View findContainingItemView(View view2) {
        ViewParent parent = view2.getParent();
        while (parent != null && parent != this && (parent instanceof View)) {
            view2 = parent;
            parent = view2.getParent();
        }
        if (parent == this) {
            return view2;
        }
        return null;
    }

    @Nullable
    public ViewHolder findContainingViewHolder(View view2) {
        View viewFindContainingItemView = findContainingItemView(view2);
        if (viewFindContainingItemView == null) {
            return null;
        }
        return getChildViewHolder(viewFindContainingItemView);
    }

    public ViewHolder findViewHolderForAdapterPosition(int i) {
        ViewHolder viewHolder = null;
        if (this.D) {
            return null;
        }
        int iB = this.e.b();
        for (int i2 = 0; i2 < iB; i2++) {
            ViewHolder viewHolderD = d(this.e.d(i2));
            if (viewHolderD != null && !viewHolderD.f() && b(viewHolderD) == i) {
                if (!this.e.c(viewHolderD.itemView)) {
                    return viewHolderD;
                }
                viewHolder = viewHolderD;
            }
        }
        return viewHolder;
    }

    public ViewHolder findViewHolderForItemId(long j) {
        Adapter adapter2 = this.l;
        ViewHolder viewHolder = null;
        if (adapter2 != null && adapter2.hasStableIds()) {
            int iB = this.e.b();
            for (int i = 0; i < iB; i++) {
                ViewHolder viewHolderD = d(this.e.d(i));
                if (viewHolderD != null && !viewHolderD.f() && viewHolderD.getItemId() == j) {
                    if (!this.e.c(viewHolderD.itemView)) {
                        return viewHolderD;
                    }
                    viewHolder = viewHolderD;
                }
            }
        }
        return viewHolder;
    }

    public ViewHolder findViewHolderForLayoutPosition(int i) {
        return a(i, false);
    }

    @Deprecated
    public ViewHolder findViewHolderForPosition(int i) {
        return a(i, false);
    }

    public boolean fling(int i, int i2) {
        LayoutManager layoutManager = this.m;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot fling without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return false;
        }
        if (this.x) {
            return false;
        }
        boolean zCanScrollHorizontally = layoutManager.canScrollHorizontally();
        boolean zCanScrollVertically = this.m.canScrollVertically();
        int i3 = (!zCanScrollHorizontally || Math.abs(i) < this.W) ? 0 : i;
        int i4 = (!zCanScrollVertically || Math.abs(i2) < this.W) ? 0 : i2;
        if (i3 == 0 && i4 == 0) {
            return false;
        }
        float f2 = i3;
        float f3 = i4;
        if (!dispatchNestedPreFling(f2, f3)) {
            boolean z = zCanScrollHorizontally || zCanScrollVertically;
            dispatchNestedFling(f2, f3, z);
            OnFlingListener onFlingListener = this.V;
            if (onFlingListener != null && onFlingListener.onFling(i3, i4)) {
                return true;
            }
            if (z) {
                int i5 = zCanScrollHorizontally ? 1 : 0;
                if (zCanScrollVertically) {
                    i5 |= 2;
                }
                startNestedScroll(i5, 1);
                int i6 = this.a0;
                int iMax = Math.max(-i6, Math.min(i3, i6));
                int i7 = this.a0;
                int iMax2 = Math.max(-i7, Math.min(i4, i7));
                h hVar = this.e0;
                RecyclerView.this.setScrollState(2);
                hVar.b = 0;
                hVar.a = 0;
                hVar.c.fling(0, 0, iMax, iMax2, Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
                hVar.a();
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:139:0x01bf  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x005c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x005e  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0061  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0069  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0070  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0072  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0075  */
    @Override // android.view.ViewGroup, android.view.ViewParent
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.view.View focusSearch(android.view.View r14, int r15) {
        /*
            Method dump skipped, instructions count: 456
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.focusSearch(android.view.View, int):android.view.View");
    }

    public void g() {
        if (this.L != null) {
            return;
        }
        EdgeEffect edgeEffectCreateEdgeEffect = this.H.createEdgeEffect(this, 3);
        this.L = edgeEffectCreateEdgeEffect;
        if (this.g) {
            edgeEffectCreateEdgeEffect.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
        } else {
            edgeEffectCreateEdgeEffect.setSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateDefaultLayoutParams() {
        LayoutManager layoutManager = this.m;
        if (layoutManager != null) {
            return layoutManager.generateDefaultLayoutParams();
        }
        throw new IllegalStateException(g9.a(this, g9.a("RecyclerView has no LayoutManager")));
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        LayoutManager layoutManager = this.m;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams(getContext(), attributeSet);
        }
        throw new IllegalStateException(g9.a(this, g9.a("RecyclerView has no LayoutManager")));
    }

    public Adapter getAdapter() {
        return this.l;
    }

    @Override // android.view.View
    public int getBaseline() {
        LayoutManager layoutManager = this.m;
        return layoutManager != null ? layoutManager.getBaseline() : super.getBaseline();
    }

    public int getChildAdapterPosition(View view2) {
        ViewHolder viewHolderD = d(view2);
        if (viewHolderD != null) {
            return viewHolderD.getAdapterPosition();
        }
        return -1;
    }

    @Override // android.view.ViewGroup
    public int getChildDrawingOrder(int i, int i2) {
        ChildDrawingOrderCallback childDrawingOrderCallback = this.p0;
        return childDrawingOrderCallback == null ? super.getChildDrawingOrder(i, i2) : childDrawingOrderCallback.onGetChildDrawingOrder(i, i2);
    }

    public long getChildItemId(View view2) {
        ViewHolder viewHolderD;
        Adapter adapter2 = this.l;
        if (adapter2 == null || !adapter2.hasStableIds() || (viewHolderD = d(view2)) == null) {
            return -1L;
        }
        return viewHolderD.getItemId();
    }

    public int getChildLayoutPosition(View view2) {
        ViewHolder viewHolderD = d(view2);
        if (viewHolderD != null) {
            return viewHolderD.getLayoutPosition();
        }
        return -1;
    }

    @Deprecated
    public int getChildPosition(View view2) {
        return getChildAdapterPosition(view2);
    }

    public ViewHolder getChildViewHolder(View view2) {
        ViewParent parent = view2.getParent();
        if (parent == null || parent == this) {
            return d(view2);
        }
        throw new IllegalArgumentException("View " + view2 + " is not a direct child of " + this);
    }

    @Override // android.view.ViewGroup
    public boolean getClipToPadding() {
        return this.g;
    }

    public RecyclerViewAccessibilityDelegate getCompatAccessibilityDelegate() {
        return this.o0;
    }

    public void getDecoratedBoundsWithMargins(View view2, Rect rect) {
        a(view2, rect);
    }

    public EdgeEffectFactory getEdgeEffectFactory() {
        return this.H;
    }

    public ItemAnimator getItemAnimator() {
        return this.M;
    }

    public ItemDecoration getItemDecorationAt(int i) {
        int itemDecorationCount = getItemDecorationCount();
        if (i >= 0 && i < itemDecorationCount) {
            return this.o.get(i);
        }
        throw new IndexOutOfBoundsException(i + " is an invalid index for size " + itemDecorationCount);
    }

    public int getItemDecorationCount() {
        return this.o.size();
    }

    public LayoutManager getLayoutManager() {
        return this.m;
    }

    public int getMaxFlingVelocity() {
        return this.a0;
    }

    public int getMinFlingVelocity() {
        return this.W;
    }

    public long getNanoTime() {
        return System.nanoTime();
    }

    @Nullable
    public OnFlingListener getOnFlingListener() {
        return this.V;
    }

    public boolean getPreserveFocusAfterLayout() {
        return this.d0;
    }

    public RecycledViewPool getRecycledViewPool() {
        return this.b.a();
    }

    public int getScrollState() {
        return this.N;
    }

    public void h() {
        if (this.I != null) {
            return;
        }
        EdgeEffect edgeEffectCreateEdgeEffect = this.H.createEdgeEffect(this, 0);
        this.I = edgeEffectCreateEdgeEffect;
        if (this.g) {
            edgeEffectCreateEdgeEffect.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
        } else {
            edgeEffectCreateEdgeEffect.setSize(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    public boolean hasFixedSize() {
        return this.s;
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public boolean hasNestedScrollingParent() {
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    public boolean hasPendingAdapterUpdates() {
        return !this.u || this.D || this.d.c();
    }

    public void i() {
        if (this.K != null) {
            return;
        }
        EdgeEffect edgeEffectCreateEdgeEffect = this.H.createEdgeEffect(this, 2);
        this.K = edgeEffectCreateEdgeEffect;
        if (this.g) {
            edgeEffectCreateEdgeEffect.setSize((getMeasuredHeight() - getPaddingTop()) - getPaddingBottom(), (getMeasuredWidth() - getPaddingLeft()) - getPaddingRight());
        } else {
            edgeEffectCreateEdgeEffect.setSize(getMeasuredHeight(), getMeasuredWidth());
        }
    }

    public void invalidateItemDecorations() {
        if (this.o.size() == 0) {
            return;
        }
        LayoutManager layoutManager = this.m;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot invalidate item decorations during a scroll or layout");
        }
        m();
        requestLayout();
    }

    public boolean isAnimating() {
        ItemAnimator itemAnimator = this.M;
        return itemAnimator != null && itemAnimator.isRunning();
    }

    @Override // android.view.View
    public boolean isAttachedToWindow() {
        return this.r;
    }

    public boolean isComputingLayout() {
        return this.F > 0;
    }

    public boolean isLayoutFrozen() {
        return this.x;
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public boolean isNestedScrollingEnabled() {
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    public void j() {
        if (this.J != null) {
            return;
        }
        EdgeEffect edgeEffectCreateEdgeEffect = this.H.createEdgeEffect(this, 1);
        this.J = edgeEffectCreateEdgeEffect;
        if (this.g) {
            edgeEffectCreateEdgeEffect.setSize((getMeasuredWidth() - getPaddingLeft()) - getPaddingRight(), (getMeasuredHeight() - getPaddingTop()) - getPaddingBottom());
        } else {
            edgeEffectCreateEdgeEffect.setSize(getMeasuredWidth(), getMeasuredHeight());
        }
    }

    public String k() {
        StringBuilder sbA = g9.a(StringUtils.SPACE);
        sbA.append(super.toString());
        sbA.append(", adapter:");
        sbA.append(this.l);
        sbA.append(", layout:");
        sbA.append(this.m);
        sbA.append(", context:");
        sbA.append(getContext());
        return sbA.toString();
    }

    public void l() {
        this.L = null;
        this.J = null;
        this.K = null;
        this.I = null;
    }

    public void m() {
        int iB = this.e.b();
        for (int i = 0; i < iB; i++) {
            ((LayoutParams) this.e.d(i).getLayoutParams()).c = true;
        }
        Recycler recycler = this.b;
        int size = recycler.c.size();
        for (int i2 = 0; i2 < size; i2++) {
            LayoutParams layoutParams = (LayoutParams) recycler.c.get(i2).itemView.getLayoutParams();
            if (layoutParams != null) {
                layoutParams.c = true;
            }
        }
    }

    public void n() {
        this.F++;
    }

    public void o() {
        if (this.n0 || !this.r) {
            return;
        }
        ViewCompat.postOnAnimation(this, this.w0);
        this.n0 = true;
    }

    public void offsetChildrenHorizontal(int i) {
        int iA = this.e.a();
        for (int i2 = 0; i2 < iA; i2++) {
            this.e.b(i2).offsetLeftAndRight(i);
        }
    }

    public void offsetChildrenVertical(int i) {
        int iA = this.e.a();
        for (int i2 = 0; i2 < iA; i2++) {
            this.e.b(i2).offsetTopAndBottom(i);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.F = 0;
        this.r = true;
        this.u = this.u && !isLayoutRequested();
        LayoutManager layoutManager = this.m;
        if (layoutManager != null) {
            layoutManager.i = true;
            layoutManager.onAttachedToWindow(this);
        }
        this.n0 = false;
        h8 h8Var = h8.e.get();
        this.f0 = h8Var;
        if (h8Var == null) {
            this.f0 = new h8();
            Display display = ViewCompat.getDisplay(this);
            float f2 = 60.0f;
            if (!isInEditMode() && display != null) {
                float refreshRate = display.getRefreshRate();
                if (refreshRate >= 30.0f) {
                    f2 = refreshRate;
                }
            }
            h8 h8Var2 = this.f0;
            h8Var2.c = (long) (1.0E9f / f2);
            h8.e.set(h8Var2);
        }
        this.f0.a.add(this);
    }

    public void onChildAttachedToWindow(View view2) {
    }

    public void onChildDetachedFromWindow(View view2) {
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ItemAnimator itemAnimator = this.M;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        stopScroll();
        this.r = false;
        LayoutManager layoutManager = this.m;
        if (layoutManager != null) {
            Recycler recycler = this.b;
            layoutManager.i = false;
            layoutManager.onDetachedFromWindow(this, recycler);
        }
        this.v0.clear();
        removeCallbacks(this.w0);
        if (this.f == null) {
            throw null;
        }
        while (z8.a.d.acquire() != null) {
        }
        h8 h8Var = this.f0;
        if (h8Var != null) {
            h8Var.a.remove(this);
            this.f0 = null;
        }
    }

    @Override // android.view.View
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int size = this.o.size();
        for (int i = 0; i < size; i++) {
            this.o.get(i).onDraw(canvas, this, this.h0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0068  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x006c  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onGenericMotionEvent(android.view.MotionEvent r6) {
        /*
            r5 = this;
            android.support.v7.widget.RecyclerView$LayoutManager r0 = r5.m
            r1 = 0
            if (r0 != 0) goto L6
            return r1
        L6:
            boolean r0 = r5.x
            if (r0 == 0) goto Lb
            return r1
        Lb:
            int r0 = r6.getAction()
            r2 = 8
            if (r0 != r2) goto L77
            int r0 = r6.getSource()
            r0 = r0 & 2
            r2 = 0
            if (r0 == 0) goto L3e
            android.support.v7.widget.RecyclerView$LayoutManager r0 = r5.m
            boolean r0 = r0.canScrollVertically()
            if (r0 == 0) goto L2c
            r0 = 9
            float r0 = r6.getAxisValue(r0)
            float r0 = -r0
            goto L2d
        L2c:
            r0 = r2
        L2d:
            android.support.v7.widget.RecyclerView$LayoutManager r3 = r5.m
            boolean r3 = r3.canScrollHorizontally()
            if (r3 == 0) goto L3c
            r3 = 10
            float r3 = r6.getAxisValue(r3)
            goto L64
        L3c:
            r3 = r2
            goto L64
        L3e:
            int r0 = r6.getSource()
            r3 = 4194304(0x400000, float:5.877472E-39)
            r0 = r0 & r3
            if (r0 == 0) goto L62
            r0 = 26
            float r0 = r6.getAxisValue(r0)
            android.support.v7.widget.RecyclerView$LayoutManager r3 = r5.m
            boolean r3 = r3.canScrollVertically()
            if (r3 == 0) goto L57
            float r0 = -r0
            goto L3c
        L57:
            android.support.v7.widget.RecyclerView$LayoutManager r3 = r5.m
            boolean r3 = r3.canScrollHorizontally()
            if (r3 == 0) goto L62
            r3 = r0
            r0 = r2
            goto L64
        L62:
            r0 = r2
            r3 = r0
        L64:
            int r4 = (r0 > r2 ? 1 : (r0 == r2 ? 0 : -1))
            if (r4 != 0) goto L6c
            int r2 = (r3 > r2 ? 1 : (r3 == r2 ? 0 : -1))
            if (r2 == 0) goto L77
        L6c:
            float r2 = r5.b0
            float r3 = r3 * r2
            int r2 = (int) r3
            float r3 = r5.c0
            float r0 = r0 * r3
            int r0 = (int) r0
            r5.a(r2, r0, r6)
        L77:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.onGenericMotionEvent(android.view.MotionEvent):boolean");
    }

    @Override // android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        boolean z;
        boolean z2;
        if (this.x) {
            return false;
        }
        int action = motionEvent.getAction();
        if (action == 3 || action == 0) {
            this.q = null;
        }
        int size = this.p.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                z = false;
                break;
            }
            OnItemTouchListener onItemTouchListener = this.p.get(i);
            if (onItemTouchListener.onInterceptTouchEvent(this, motionEvent) && action != 3) {
                this.q = onItemTouchListener;
                z = true;
                break;
            }
            i++;
        }
        if (z) {
            a();
            return true;
        }
        LayoutManager layoutManager = this.m;
        if (layoutManager == null) {
            return false;
        }
        boolean zCanScrollHorizontally = layoutManager.canScrollHorizontally();
        boolean zCanScrollVertically = this.m.canScrollVertically();
        if (this.P == null) {
            this.P = VelocityTracker.obtain();
        }
        this.P.addMovement(motionEvent);
        int actionMasked = motionEvent.getActionMasked();
        int actionIndex = motionEvent.getActionIndex();
        if (actionMasked == 0) {
            if (this.y) {
                this.y = false;
            }
            this.O = motionEvent.getPointerId(0);
            int x = (int) (motionEvent.getX() + 0.5f);
            this.S = x;
            this.Q = x;
            int y = (int) (motionEvent.getY() + 0.5f);
            this.T = y;
            this.R = y;
            if (this.N == 2) {
                getParent().requestDisallowInterceptTouchEvent(true);
                setScrollState(1);
            }
            int[] iArr = this.u0;
            iArr[1] = 0;
            iArr[0] = 0;
            int i2 = zCanScrollHorizontally ? 1 : 0;
            if (zCanScrollVertically) {
                i2 |= 2;
            }
            startNestedScroll(i2, 0);
        } else if (actionMasked == 1) {
            this.P.clear();
            stopNestedScroll(0);
        } else if (actionMasked == 2) {
            int iFindPointerIndex = motionEvent.findPointerIndex(this.O);
            if (iFindPointerIndex < 0) {
                StringBuilder sbA = g9.a("Error processing scroll; pointer index for id ");
                sbA.append(this.O);
                sbA.append(" not found. Did any MotionEvents get skipped?");
                Log.e("RecyclerView", sbA.toString());
                return false;
            }
            int x2 = (int) (motionEvent.getX(iFindPointerIndex) + 0.5f);
            int y2 = (int) (motionEvent.getY(iFindPointerIndex) + 0.5f);
            if (this.N != 1) {
                int i3 = x2 - this.Q;
                int i4 = y2 - this.R;
                if (!zCanScrollHorizontally || Math.abs(i3) <= this.U) {
                    z2 = false;
                } else {
                    this.S = x2;
                    z2 = true;
                }
                if (zCanScrollVertically && Math.abs(i4) > this.U) {
                    this.T = y2;
                    z2 = true;
                }
                if (z2) {
                    setScrollState(1);
                }
            }
        } else if (actionMasked == 3) {
            a();
        } else if (actionMasked == 5) {
            this.O = motionEvent.getPointerId(actionIndex);
            int x3 = (int) (motionEvent.getX(actionIndex) + 0.5f);
            this.S = x3;
            this.Q = x3;
            int y3 = (int) (motionEvent.getY(actionIndex) + 0.5f);
            this.T = y3;
            this.R = y3;
        } else if (actionMasked == 6) {
            a(motionEvent);
        }
        return this.N == 1;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        TraceCompat.beginSection("RV OnLayout");
        d();
        TraceCompat.endSection();
        this.u = true;
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        LayoutManager layoutManager = this.m;
        if (layoutManager == null) {
            b(i, i2);
            return;
        }
        boolean z = false;
        if (layoutManager.isAutoMeasureEnabled()) {
            int mode = View.MeasureSpec.getMode(i);
            int mode2 = View.MeasureSpec.getMode(i2);
            this.m.onMeasure(this.b, this.h0, i, i2);
            if (mode == 1073741824 && mode2 == 1073741824) {
                z = true;
            }
            if (z || this.l == null) {
                return;
            }
            if (this.h0.e == 1) {
                e();
            }
            this.m.a(i, i2);
            this.h0.j = true;
            f();
            this.m.b(i, i2);
            if (this.m.a()) {
                this.m.a(View.MeasureSpec.makeMeasureSpec(getMeasuredWidth(), 1073741824), View.MeasureSpec.makeMeasureSpec(getMeasuredHeight(), 1073741824));
                this.h0.j = true;
                f();
                this.m.b(i, i2);
                return;
            }
            return;
        }
        if (this.s) {
            this.m.onMeasure(this.b, this.h0, i, i2);
            return;
        }
        if (this.A) {
            t();
            n();
            p();
            a(true);
            State state = this.h0;
            if (state.l) {
                state.h = true;
            } else {
                this.d.b();
                this.h0.h = false;
            }
            this.A = false;
            c(false);
        } else if (this.h0.l) {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
            return;
        }
        Adapter adapter2 = this.l;
        if (adapter2 != null) {
            this.h0.f = adapter2.getItemCount();
        } else {
            this.h0.f = 0;
        }
        t();
        this.m.onMeasure(this.b, this.h0, i, i2);
        c(false);
        this.h0.h = false;
    }

    @Override // android.view.ViewGroup
    public boolean onRequestFocusInDescendants(int i, Rect rect) {
        if (isComputingLayout()) {
            return false;
        }
        return super.onRequestFocusInDescendants(i, rect);
    }

    @Override // android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        Parcelable parcelable2;
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        this.c = savedState;
        super.onRestoreInstanceState(savedState.getSuperState());
        LayoutManager layoutManager = this.m;
        if (layoutManager == null || (parcelable2 = this.c.b) == null) {
            return;
        }
        layoutManager.onRestoreInstanceState(parcelable2);
    }

    @Override // android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SavedState savedState2 = this.c;
        if (savedState2 != null) {
            savedState.b = savedState2.b;
        } else {
            LayoutManager layoutManager = this.m;
            if (layoutManager != null) {
                savedState.b = layoutManager.onSaveInstanceState();
            } else {
                savedState.b = null;
            }
        }
        return savedState;
    }

    public void onScrollStateChanged(int i) {
    }

    public void onScrolled(int i, int i2) {
    }

    @Override // android.view.View
    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i == i3 && i2 == i4) {
            return;
        }
        l();
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0027  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x013a  */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r15) {
        /*
            Method dump skipped, instructions count: 506
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    public final void p() {
        boolean z = false;
        if (this.D) {
            o7 o7Var = this.d;
            o7Var.a(o7Var.b);
            o7Var.a(o7Var.c);
            o7Var.g = 0;
            if (this.E) {
                this.m.onItemsChanged(this);
            }
        }
        if (this.M != null && this.m.supportsPredictiveItemAnimations()) {
            this.d.d();
        } else {
            this.d.b();
        }
        boolean z2 = this.k0 || this.l0;
        this.h0.k = this.u && this.M != null && (this.D || z2 || this.m.h) && (!this.D || this.l.hasStableIds());
        State state = this.h0;
        if (state.k && z2 && !this.D) {
            if (this.M != null && this.m.supportsPredictiveItemAnimations()) {
                z = true;
            }
        }
        state.l = z;
    }

    public void q() {
        ItemAnimator itemAnimator = this.M;
        if (itemAnimator != null) {
            itemAnimator.endAnimations();
        }
        LayoutManager layoutManager = this.m;
        if (layoutManager != null) {
            layoutManager.removeAndRecycleAllViews(this.b);
            this.m.a(this.b);
        }
        this.b.clear();
    }

    public void r() {
        ViewHolder viewHolder;
        int iA = this.e.a();
        for (int i = 0; i < iA; i++) {
            View viewB = this.e.b(i);
            ViewHolder childViewHolder = getChildViewHolder(viewB);
            if (childViewHolder != null && (viewHolder = childViewHolder.h) != null) {
                View view2 = viewHolder.itemView;
                int left = viewB.getLeft();
                int top = viewB.getTop();
                if (left != view2.getLeft() || top != view2.getTop()) {
                    view2.layout(left, top, view2.getWidth() + left, view2.getHeight() + top);
                }
            }
        }
    }

    @Override // android.view.ViewGroup
    public void removeDetachedView(View view2, boolean z) {
        ViewHolder viewHolderD = d(view2);
        if (viewHolderD != null) {
            if (viewHolderD.h()) {
                viewHolderD.i &= -257;
            } else if (!viewHolderD.l()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Called removeDetachedView with a view which is not flagged as tmp detached.");
                sb.append(viewHolderD);
                throw new IllegalArgumentException(g9.a(this, sb));
            }
        }
        view2.clearAnimation();
        a(view2);
        super.removeDetachedView(view2, z);
    }

    public void removeItemDecoration(ItemDecoration itemDecoration) {
        LayoutManager layoutManager = this.m;
        if (layoutManager != null) {
            layoutManager.assertNotInLayoutOrScroll("Cannot remove item decoration during a scroll  or layout");
        }
        this.o.remove(itemDecoration);
        if (this.o.isEmpty()) {
            setWillNotDraw(getOverScrollMode() == 2);
        }
        m();
        requestLayout();
    }

    public void removeItemDecorationAt(int i) {
        int itemDecorationCount = getItemDecorationCount();
        if (i >= 0 && i < itemDecorationCount) {
            removeItemDecoration(getItemDecorationAt(i));
            return;
        }
        throw new IndexOutOfBoundsException(i + " is an invalid index for size " + itemDecorationCount);
    }

    public void removeOnChildAttachStateChangeListener(OnChildAttachStateChangeListener onChildAttachStateChangeListener) {
        List<OnChildAttachStateChangeListener> list = this.C;
        if (list == null) {
            return;
        }
        list.remove(onChildAttachStateChangeListener);
    }

    public void removeOnItemTouchListener(OnItemTouchListener onItemTouchListener) {
        this.p.remove(onItemTouchListener);
        if (this.q == onItemTouchListener) {
            this.q = null;
        }
    }

    public void removeOnScrollListener(OnScrollListener onScrollListener) {
        List<OnScrollListener> list = this.j0;
        if (list != null) {
            list.remove(onScrollListener);
        }
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestChildFocus(View view2, View view3) {
        if (!this.m.onRequestChildFocus(this, this.h0, view2, view3) && view3 != null) {
            a(view2, view3);
        }
        super.requestChildFocus(view2, view3);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public boolean requestChildRectangleOnScreen(View view2, Rect rect, boolean z) {
        return this.m.requestChildRectangleOnScreen(this, view2, rect, z);
    }

    @Override // android.view.ViewGroup, android.view.ViewParent
    public void requestDisallowInterceptTouchEvent(boolean z) {
        int size = this.p.size();
        for (int i = 0; i < size; i++) {
            this.p.get(i).onRequestDisallowInterceptTouchEvent(z);
        }
        super.requestDisallowInterceptTouchEvent(z);
    }

    @Override // android.view.View, android.view.ViewParent
    public void requestLayout() {
        if (this.v != 0 || this.x) {
            this.w = true;
        } else {
            super.requestLayout();
        }
    }

    public final void s() {
        VelocityTracker velocityTracker = this.P;
        if (velocityTracker != null) {
            velocityTracker.clear();
        }
        boolean zIsFinished = false;
        stopNestedScroll(0);
        EdgeEffect edgeEffect = this.I;
        if (edgeEffect != null) {
            edgeEffect.onRelease();
            zIsFinished = this.I.isFinished();
        }
        EdgeEffect edgeEffect2 = this.J;
        if (edgeEffect2 != null) {
            edgeEffect2.onRelease();
            zIsFinished |= this.J.isFinished();
        }
        EdgeEffect edgeEffect3 = this.K;
        if (edgeEffect3 != null) {
            edgeEffect3.onRelease();
            zIsFinished |= this.K.isFinished();
        }
        EdgeEffect edgeEffect4 = this.L;
        if (edgeEffect4 != null) {
            edgeEffect4.onRelease();
            zIsFinished |= this.L.isFinished();
        }
        if (zIsFinished) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override // android.view.View
    public void scrollBy(int i, int i2) {
        LayoutManager layoutManager = this.m;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.x) {
            return;
        }
        boolean zCanScrollHorizontally = layoutManager.canScrollHorizontally();
        boolean zCanScrollVertically = this.m.canScrollVertically();
        if (zCanScrollHorizontally || zCanScrollVertically) {
            if (!zCanScrollHorizontally) {
                i = 0;
            }
            if (!zCanScrollVertically) {
                i2 = 0;
            }
            a(i, i2, (MotionEvent) null);
        }
    }

    @Override // android.view.View
    public void scrollTo(int i, int i2) {
        Log.w("RecyclerView", "RecyclerView does not support scrolling to an absolute position. Use scrollToPosition instead");
    }

    public void scrollToPosition(int i) {
        if (this.x) {
            return;
        }
        stopScroll();
        LayoutManager layoutManager = this.m;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot scroll to position a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else {
            layoutManager.scrollToPosition(i);
            awakenScrollBars();
        }
    }

    @Override // android.view.View, android.view.accessibility.AccessibilityEventSource
    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        if (isComputingLayout()) {
            int contentChangeTypes = accessibilityEvent != null ? AccessibilityEventCompat.getContentChangeTypes(accessibilityEvent) : 0;
            this.z |= contentChangeTypes != 0 ? contentChangeTypes : 0;
            i = 1;
        }
        if (i != 0) {
            return;
        }
        super.sendAccessibilityEventUnchecked(accessibilityEvent);
    }

    public void setAccessibilityDelegateCompat(RecyclerViewAccessibilityDelegate recyclerViewAccessibilityDelegate) {
        this.o0 = recyclerViewAccessibilityDelegate;
        ViewCompat.setAccessibilityDelegate(this, recyclerViewAccessibilityDelegate);
    }

    public void setAdapter(Adapter adapter2) {
        setLayoutFrozen(false);
        a(adapter2, false, true);
        b(false);
        requestLayout();
    }

    public void setChildDrawingOrderCallback(ChildDrawingOrderCallback childDrawingOrderCallback) {
        if (childDrawingOrderCallback == this.p0) {
            return;
        }
        this.p0 = childDrawingOrderCallback;
        setChildrenDrawingOrderEnabled(childDrawingOrderCallback != null);
    }

    @Override // android.view.ViewGroup
    public void setClipToPadding(boolean z) {
        if (z != this.g) {
            l();
        }
        this.g = z;
        super.setClipToPadding(z);
        if (this.u) {
            requestLayout();
        }
    }

    public void setEdgeEffectFactory(@NonNull EdgeEffectFactory edgeEffectFactory) {
        Preconditions.checkNotNull(edgeEffectFactory);
        this.H = edgeEffectFactory;
        l();
    }

    public void setHasFixedSize(boolean z) {
        this.s = z;
    }

    public void setItemAnimator(ItemAnimator itemAnimator) {
        ItemAnimator itemAnimator2 = this.M;
        if (itemAnimator2 != null) {
            itemAnimator2.endAnimations();
            this.M.a = null;
        }
        this.M = itemAnimator;
        if (itemAnimator != null) {
            itemAnimator.a = this.m0;
        }
    }

    public void setItemViewCacheSize(int i) {
        this.b.setViewCacheSize(i);
    }

    public void setLayoutFrozen(boolean z) {
        if (z != this.x) {
            a("Do not setLayoutFrozen in layout or scroll");
            if (z) {
                long jUptimeMillis = SystemClock.uptimeMillis();
                onTouchEvent(MotionEvent.obtain(jUptimeMillis, jUptimeMillis, 3, 0.0f, 0.0f, 0));
                this.x = true;
                this.y = true;
                stopScroll();
                return;
            }
            this.x = false;
            if (this.w && this.m != null && this.l != null) {
                requestLayout();
            }
            this.w = false;
        }
    }

    public void setLayoutManager(LayoutManager layoutManager) {
        if (layoutManager == this.m) {
            return;
        }
        stopScroll();
        if (this.m != null) {
            ItemAnimator itemAnimator = this.M;
            if (itemAnimator != null) {
                itemAnimator.endAnimations();
            }
            this.m.removeAndRecycleAllViews(this.b);
            this.m.a(this.b);
            this.b.clear();
            if (this.r) {
                LayoutManager layoutManager2 = this.m;
                Recycler recycler = this.b;
                layoutManager2.i = false;
                layoutManager2.onDetachedFromWindow(this, recycler);
            }
            this.m.b(null);
            this.m = null;
        } else {
            this.b.clear();
        }
        z7 z7Var = this.e;
        z7.a aVar = z7Var.b;
        aVar.a = 0L;
        z7.a aVar2 = aVar.b;
        if (aVar2 != null) {
            aVar2.b();
        }
        int size = z7Var.c.size();
        while (true) {
            size--;
            if (size < 0) {
                m8 m8Var = (m8) z7Var.a;
                int iA = m8Var.a();
                for (int i = 0; i < iA; i++) {
                    View viewA = m8Var.a(i);
                    m8Var.a.a(viewA);
                    viewA.clearAnimation();
                }
                m8Var.a.removeAllViews();
                this.m = layoutManager;
                if (layoutManager != null) {
                    if (layoutManager.b != null) {
                        StringBuilder sb = new StringBuilder();
                        sb.append("LayoutManager ");
                        sb.append(layoutManager);
                        sb.append(" is already attached to a RecyclerView:");
                        throw new IllegalArgumentException(g9.a(layoutManager.b, sb));
                    }
                    layoutManager.b(this);
                    if (this.r) {
                        LayoutManager layoutManager3 = this.m;
                        layoutManager3.i = true;
                        layoutManager3.onAttachedToWindow(this);
                    }
                }
                this.b.c();
                requestLayout();
                return;
            }
            z7.b bVar = z7Var.a;
            View view2 = z7Var.c.get(size);
            m8 m8Var2 = (m8) bVar;
            if (m8Var2 == null) {
                throw null;
            }
            ViewHolder viewHolderD = d(view2);
            if (viewHolderD != null) {
                m8Var2.a.a(viewHolderD, viewHolderD.o);
                viewHolderD.o = 0;
            }
            z7Var.c.remove(size);
        }
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public void setNestedScrollingEnabled(boolean z) {
        getScrollingChildHelper().setNestedScrollingEnabled(z);
    }

    public void setOnFlingListener(@Nullable OnFlingListener onFlingListener) {
        this.V = onFlingListener;
    }

    @Deprecated
    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.i0 = onScrollListener;
    }

    public void setPreserveFocusAfterLayout(boolean z) {
        this.d0 = z;
    }

    public void setRecycledViewPool(RecycledViewPool recycledViewPool) {
        Recycler recycler = this.b;
        if (recycler.g != null) {
            r1.b--;
        }
        recycler.g = recycledViewPool;
        if (recycledViewPool != null) {
            RecyclerView.this.getAdapter();
            recycledViewPool.b++;
        }
    }

    public void setRecyclerListener(RecyclerListener recyclerListener) {
        this.n = recyclerListener;
    }

    public void setScrollState(int i) {
        if (i == this.N) {
            return;
        }
        this.N = i;
        if (i != 2) {
            u();
        }
        LayoutManager layoutManager = this.m;
        if (layoutManager != null) {
            layoutManager.onScrollStateChanged(i);
        }
        onScrollStateChanged(i);
        OnScrollListener onScrollListener = this.i0;
        if (onScrollListener != null) {
            onScrollListener.onScrollStateChanged(this, i);
        }
        List<OnScrollListener> list = this.j0;
        if (list == null) {
            return;
        }
        int size = list.size();
        while (true) {
            size--;
            if (size < 0) {
                return;
            } else {
                this.j0.get(size).onScrollStateChanged(this, i);
            }
        }
    }

    public void setScrollingTouchSlop(int i) {
        ViewConfiguration viewConfiguration = ViewConfiguration.get(getContext());
        if (i != 0) {
            if (i == 1) {
                this.U = viewConfiguration.getScaledPagingTouchSlop();
                return;
            }
            Log.w("RecyclerView", "setScrollingTouchSlop(): bad argument constant " + i + "; using default value");
        }
        this.U = viewConfiguration.getScaledTouchSlop();
    }

    public void setViewCacheExtension(ViewCacheExtension viewCacheExtension) {
        this.b.h = viewCacheExtension;
    }

    public void smoothScrollBy(int i, int i2) {
        smoothScrollBy(i, i2, null);
    }

    public void smoothScrollToPosition(int i) {
        if (this.x) {
            return;
        }
        LayoutManager layoutManager = this.m;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
        } else {
            layoutManager.smoothScrollToPosition(this, this.h0, i);
        }
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public boolean startNestedScroll(int i) {
        return getScrollingChildHelper().startNestedScroll(i);
    }

    @Override // android.view.View, android.support.v4.view.NestedScrollingChild
    public void stopNestedScroll() {
        getScrollingChildHelper().stopNestedScroll();
    }

    public void stopScroll() {
        setScrollState(0);
        u();
    }

    public void swapAdapter(Adapter adapter2, boolean z) {
        setLayoutFrozen(false);
        a(adapter2, true, z);
        b(true);
        requestLayout();
    }

    public void t() {
        int i = this.v + 1;
        this.v = i;
        if (i != 1 || this.x) {
            return;
        }
        this.w = false;
    }

    public final void u() {
        SmoothScroller smoothScroller;
        h hVar = this.e0;
        RecyclerView.this.removeCallbacks(hVar);
        hVar.c.abortAnimation();
        LayoutManager layoutManager = this.m;
        if (layoutManager == null || (smoothScroller = layoutManager.g) == null) {
            return;
        }
        smoothScroller.stop();
    }

    public static abstract class Adapter<VH extends ViewHolder> {
        public final e a = new e();
        public boolean b = false;

        public final void bindViewHolder(@NonNull VH vh, int i) {
            vh.b = i;
            if (hasStableIds()) {
                vh.d = getItemId(i);
            }
            vh.a(1, 519);
            TraceCompat.beginSection("RV OnBindView");
            onBindViewHolder(vh, i, vh.c());
            List<Object> list = vh.j;
            if (list != null) {
                list.clear();
            }
            vh.i &= -1025;
            ViewGroup.LayoutParams layoutParams = vh.itemView.getLayoutParams();
            if (layoutParams instanceof LayoutParams) {
                ((LayoutParams) layoutParams).c = true;
            }
            TraceCompat.endSection();
        }

        public final VH createViewHolder(@NonNull ViewGroup viewGroup, int i) {
            try {
                TraceCompat.beginSection("RV CreateView");
                VH vh = (VH) onCreateViewHolder(viewGroup, i);
                if (vh.itemView.getParent() != null) {
                    throw new IllegalStateException("ViewHolder views must not be attached when created. Ensure that you are not passing 'true' to the attachToRoot parameter of LayoutInflater.inflate(..., boolean attachToRoot)");
                }
                vh.e = i;
                return vh;
            } finally {
                TraceCompat.endSection();
            }
        }

        public abstract int getItemCount();

        public long getItemId(int i) {
            return -1L;
        }

        public int getItemViewType(int i) {
            return 0;
        }

        public final boolean hasObservers() {
            return this.a.a();
        }

        public final boolean hasStableIds() {
            return this.b;
        }

        public final void notifyDataSetChanged() {
            this.a.b();
        }

        public final void notifyItemChanged(int i) {
            this.a.a(i, 1, null);
        }

        public final void notifyItemInserted(int i) {
            this.a.b(i, 1);
        }

        public final void notifyItemMoved(int i, int i2) {
            this.a.a(i, i2);
        }

        public final void notifyItemRangeChanged(int i, int i2) {
            this.a.a(i, i2, null);
        }

        public final void notifyItemRangeInserted(int i, int i2) {
            this.a.b(i, i2);
        }

        public final void notifyItemRangeRemoved(int i, int i2) {
            this.a.c(i, i2);
        }

        public final void notifyItemRemoved(int i) {
            this.a.c(i, 1);
        }

        public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        }

        public abstract void onBindViewHolder(@NonNull VH vh, int i);

        public void onBindViewHolder(@NonNull VH vh, int i, @NonNull List<Object> list) {
            onBindViewHolder(vh, i);
        }

        @NonNull
        public abstract VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i);

        public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        }

        public boolean onFailedToRecycleView(@NonNull VH vh) {
            return false;
        }

        public void onViewAttachedToWindow(@NonNull VH vh) {
        }

        public void onViewDetachedFromWindow(@NonNull VH vh) {
        }

        public void onViewRecycled(@NonNull VH vh) {
        }

        public void registerAdapterDataObserver(@NonNull AdapterDataObserver adapterDataObserver) {
            this.a.registerObserver(adapterDataObserver);
        }

        public void setHasStableIds(boolean z) {
            if (hasObservers()) {
                throw new IllegalStateException("Cannot change whether this adapter has stable IDs while the adapter has registered observers.");
            }
            this.b = z;
        }

        public void unregisterAdapterDataObserver(@NonNull AdapterDataObserver adapterDataObserver) {
            this.a.unregisterObserver(adapterDataObserver);
        }

        public final void notifyItemChanged(int i, @Nullable Object obj) {
            this.a.a(i, 1, obj);
        }

        public final void notifyItemRangeChanged(int i, int i2, @Nullable Object obj) {
            this.a.a(i, i2, obj);
        }
    }

    public RecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    @Override // android.support.v4.view.NestedScrollingChild2
    public boolean dispatchNestedPreScroll(int i, int i2, int[] iArr, int[] iArr2, int i3) {
        return getScrollingChildHelper().dispatchNestedPreScroll(i, i2, iArr, iArr2, i3);
    }

    @Override // android.support.v4.view.NestedScrollingChild2
    public boolean dispatchNestedScroll(int i, int i2, int i3, int i4, int[] iArr, int i5) {
        return getScrollingChildHelper().dispatchNestedScroll(i, i2, i3, i4, iArr, i5);
    }

    @Override // android.support.v4.view.NestedScrollingChild2
    public boolean hasNestedScrollingParent(int i) {
        return getScrollingChildHelper().hasNestedScrollingParent(i);
    }

    public void smoothScrollBy(int i, int i2, Interpolator interpolator) {
        LayoutManager layoutManager = this.m;
        if (layoutManager == null) {
            Log.e("RecyclerView", "Cannot smooth scroll without a LayoutManager set. Call setLayoutManager with a non-null argument.");
            return;
        }
        if (this.x) {
            return;
        }
        if (!layoutManager.canScrollHorizontally()) {
            i = 0;
        }
        if (!this.m.canScrollVertically()) {
            i2 = 0;
        }
        if (i == 0 && i2 == 0) {
            return;
        }
        h hVar = this.e0;
        int iA = hVar.a(i, i2, 0, 0);
        if (interpolator == null) {
            interpolator = E0;
        }
        hVar.a(i, i2, iA, interpolator);
    }

    @Override // android.support.v4.view.NestedScrollingChild2
    public boolean startNestedScroll(int i, int i2) {
        return getScrollingChildHelper().startNestedScroll(i, i2);
    }

    @Override // android.support.v4.view.NestedScrollingChild2
    public void stopNestedScroll(int i) {
        getScrollingChildHelper().stopNestedScroll(i);
    }

    public RecyclerView(Context context, @Nullable AttributeSet attributeSet, int i) throws NoSuchMethodException, SecurityException {
        TypedArray typedArray;
        char c2;
        ClassLoader classLoader;
        Constructor constructor;
        super(context, attributeSet, i);
        this.a = new g();
        this.b = new Recycler();
        this.f = new z8();
        this.h = new a();
        this.i = new Rect();
        this.j = new Rect();
        this.k = new RectF();
        this.o = new ArrayList<>();
        this.p = new ArrayList<>();
        this.v = 0;
        this.D = false;
        this.E = false;
        this.F = 0;
        this.G = 0;
        this.H = new EdgeEffectFactory();
        this.M = new DefaultItemAnimator();
        this.N = 0;
        this.O = -1;
        this.b0 = Float.MIN_VALUE;
        this.c0 = Float.MIN_VALUE;
        boolean z = true;
        this.d0 = true;
        this.e0 = new h();
        this.g0 = new h8.b();
        this.h0 = new State();
        this.k0 = false;
        this.l0 = false;
        this.m0 = new f();
        this.n0 = false;
        this.q0 = new int[2];
        this.s0 = new int[2];
        this.t0 = new int[2];
        this.u0 = new int[2];
        this.v0 = new ArrayList();
        this.w0 = new b();
        this.x0 = new d();
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, z0, i, 0);
            this.g = typedArrayObtainStyledAttributes.getBoolean(0, true);
            typedArrayObtainStyledAttributes.recycle();
        } else {
            this.g = true;
        }
        setScrollContainer(true);
        setFocusableInTouchMode(true);
        ViewConfiguration viewConfiguration = ViewConfiguration.get(context);
        this.U = viewConfiguration.getScaledTouchSlop();
        this.b0 = ViewConfigurationCompat.getScaledHorizontalScrollFactor(viewConfiguration, context);
        this.c0 = ViewConfigurationCompat.getScaledVerticalScrollFactor(viewConfiguration, context);
        this.W = viewConfiguration.getScaledMinimumFlingVelocity();
        this.a0 = viewConfiguration.getScaledMaximumFlingVelocity();
        setWillNotDraw(getOverScrollMode() == 2);
        this.M.a = this.m0;
        this.d = new o7(new n8(this));
        this.e = new z7(new m8(this));
        if (ViewCompat.getImportantForAccessibility(this) == 0) {
            ViewCompat.setImportantForAccessibility(this, 1);
        }
        this.B = (AccessibilityManager) getContext().getSystemService("accessibility");
        setAccessibilityDelegateCompat(new RecyclerViewAccessibilityDelegate(this));
        if (attributeSet != null) {
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, android.support.v7.recyclerview.R.styleable.RecyclerView, i, 0);
            String string = typedArrayObtainStyledAttributes2.getString(android.support.v7.recyclerview.R.styleable.RecyclerView_layoutManager);
            if (typedArrayObtainStyledAttributes2.getInt(android.support.v7.recyclerview.R.styleable.RecyclerView_android_descendantFocusability, -1) == -1) {
                setDescendantFocusability(262144);
            }
            boolean z2 = typedArrayObtainStyledAttributes2.getBoolean(android.support.v7.recyclerview.R.styleable.RecyclerView_fastScrollEnabled, false);
            this.t = z2;
            if (z2) {
                StateListDrawable stateListDrawable = (StateListDrawable) typedArrayObtainStyledAttributes2.getDrawable(android.support.v7.recyclerview.R.styleable.RecyclerView_fastScrollVerticalThumbDrawable);
                Drawable drawable = typedArrayObtainStyledAttributes2.getDrawable(android.support.v7.recyclerview.R.styleable.RecyclerView_fastScrollVerticalTrackDrawable);
                StateListDrawable stateListDrawable2 = (StateListDrawable) typedArrayObtainStyledAttributes2.getDrawable(android.support.v7.recyclerview.R.styleable.RecyclerView_fastScrollHorizontalThumbDrawable);
                Drawable drawable2 = typedArrayObtainStyledAttributes2.getDrawable(android.support.v7.recyclerview.R.styleable.RecyclerView_fastScrollHorizontalTrackDrawable);
                if (stateListDrawable != null && drawable != null && stateListDrawable2 != null && drawable2 != null) {
                    Resources resources = getContext().getResources();
                    typedArray = typedArrayObtainStyledAttributes2;
                    c2 = 2;
                    new g8(this, stateListDrawable, drawable, stateListDrawable2, drawable2, resources.getDimensionPixelSize(android.support.v7.recyclerview.R.dimen.fastscroll_default_thickness), resources.getDimensionPixelSize(android.support.v7.recyclerview.R.dimen.fastscroll_minimum_range), resources.getDimensionPixelOffset(android.support.v7.recyclerview.R.dimen.fastscroll_margin));
                } else {
                    throw new IllegalArgumentException(g9.a(this, g9.a("Trying to set fast scroller without both required drawables.")));
                }
            } else {
                typedArray = typedArrayObtainStyledAttributes2;
                c2 = 2;
            }
            typedArray.recycle();
            if (string != null) {
                String strTrim = string.trim();
                if (!strTrim.isEmpty()) {
                    if (strTrim.charAt(0) == '.') {
                        strTrim = context.getPackageName() + strTrim;
                    } else if (!strTrim.contains(".")) {
                        strTrim = RecyclerView.class.getPackage().getName() + ClassUtils.PACKAGE_SEPARATOR_CHAR + strTrim;
                    }
                    String str = strTrim;
                    try {
                        if (isInEditMode()) {
                            classLoader = getClass().getClassLoader();
                        } else {
                            classLoader = context.getClassLoader();
                        }
                        Class<? extends U> clsAsSubclass = classLoader.loadClass(str).asSubclass(LayoutManager.class);
                        Object[] objArr = null;
                        try {
                            constructor = clsAsSubclass.getConstructor(D0);
                            Object[] objArr2 = new Object[4];
                            objArr2[0] = context;
                            objArr2[1] = attributeSet;
                            objArr2[c2] = Integer.valueOf(i);
                            objArr2[3] = 0;
                            objArr = objArr2;
                        } catch (NoSuchMethodException e2) {
                            try {
                                constructor = clsAsSubclass.getConstructor(new Class[0]);
                            } catch (NoSuchMethodException e3) {
                                e3.initCause(e2);
                                throw new IllegalStateException(attributeSet.getPositionDescription() + ": Error creating LayoutManager " + str, e3);
                            }
                        }
                        constructor.setAccessible(true);
                        setLayoutManager((LayoutManager) constructor.newInstance(objArr));
                    } catch (ClassCastException e4) {
                        throw new IllegalStateException(attributeSet.getPositionDescription() + ": Class is not a LayoutManager " + str, e4);
                    } catch (ClassNotFoundException e5) {
                        throw new IllegalStateException(attributeSet.getPositionDescription() + ": Unable to find LayoutManager " + str, e5);
                    } catch (IllegalAccessException e6) {
                        throw new IllegalStateException(attributeSet.getPositionDescription() + ": Cannot access non-public constructor " + str, e6);
                    } catch (InstantiationException e7) {
                        throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + str, e7);
                    } catch (InvocationTargetException e8) {
                        throw new IllegalStateException(attributeSet.getPositionDescription() + ": Could not instantiate the LayoutManager: " + str, e8);
                    }
                }
            }
            TypedArray typedArrayObtainStyledAttributes3 = context.obtainStyledAttributes(attributeSet, y0, i, 0);
            z = typedArrayObtainStyledAttributes3.getBoolean(0, true);
            typedArrayObtainStyledAttributes3.recycle();
        } else {
            setDescendantFocusability(262144);
        }
        setNestedScrollingEnabled(z);
    }

    public static class LayoutParams extends ViewGroup.MarginLayoutParams {
        public ViewHolder a;
        public final Rect b;
        public boolean c;
        public boolean d;

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.b = new Rect();
            this.c = true;
            this.d = false;
        }

        public int getViewAdapterPosition() {
            return this.a.getAdapterPosition();
        }

        public int getViewLayoutPosition() {
            return this.a.getLayoutPosition();
        }

        @Deprecated
        public int getViewPosition() {
            return this.a.getPosition();
        }

        public boolean isItemChanged() {
            return this.a.i();
        }

        public boolean isItemRemoved() {
            return this.a.f();
        }

        public boolean isViewInvalid() {
            return this.a.e();
        }

        public boolean viewNeedsUpdate() {
            return this.a.j();
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.b = new Rect();
            this.c = true;
            this.d = false;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.b = new Rect();
            this.c = true;
            this.d = false;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.b = new Rect();
            this.c = true;
            this.d = false;
        }

        public LayoutParams(LayoutParams layoutParams) {
            super((ViewGroup.LayoutParams) layoutParams);
            this.b = new Rect();
            this.c = true;
            this.d = false;
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new a();
        public Parcelable b;

        public static class a implements Parcelable.ClassLoaderCreator<SavedState> {
            @Override // android.os.Parcelable.ClassLoaderCreator
            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.b = parcel.readParcelable(classLoader == null ? LayoutManager.class.getClassLoader() : classLoader);
        }

        @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeParcelable(this.b, 0);
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }
    }

    public class h implements Runnable {
        public int a;
        public int b;
        public OverScroller c;
        public Interpolator d = RecyclerView.E0;
        public boolean e = false;
        public boolean f = false;

        public h() {
            this.c = new OverScroller(RecyclerView.this.getContext(), RecyclerView.E0);
        }

        public void a() {
            if (this.e) {
                this.f = true;
            } else {
                RecyclerView.this.removeCallbacks(this);
                ViewCompat.postOnAnimation(RecyclerView.this, this);
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:50:0x0125  */
        /* JADX WARN: Removed duplicated region for block: B:52:0x0128  */
        /* JADX WARN: Removed duplicated region for block: B:56:0x012f  */
        /* JADX WARN: Removed duplicated region for block: B:59:0x0138  */
        /* JADX WARN: Type inference failed for: r2v1 */
        /* JADX WARN: Type inference failed for: r2v10 */
        /* JADX WARN: Type inference failed for: r2v12 */
        /* JADX WARN: Type inference failed for: r2v2, types: [boolean, int] */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 547
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.h.run():void");
        }

        public final int a(int i, int i2, int i3, int i4) {
            int iRound;
            int iAbs = Math.abs(i);
            int iAbs2 = Math.abs(i2);
            boolean z = iAbs > iAbs2;
            int iSqrt = (int) Math.sqrt((i4 * i4) + (i3 * i3));
            int iSqrt2 = (int) Math.sqrt((i2 * i2) + (i * i));
            RecyclerView recyclerView = RecyclerView.this;
            int width = z ? recyclerView.getWidth() : recyclerView.getHeight();
            int i5 = width / 2;
            float f = width;
            float f2 = i5;
            float fSin = (((float) Math.sin((Math.min(1.0f, (iSqrt2 * 1.0f) / f) - 0.5f) * 0.47123894f)) * f2) + f2;
            if (iSqrt > 0) {
                iRound = Math.round(Math.abs(fSin / iSqrt) * 1000.0f) * 4;
            } else {
                if (!z) {
                    iAbs = iAbs2;
                }
                iRound = (int) (((iAbs / f) + 1.0f) * 300.0f);
            }
            return Math.min(iRound, 2000);
        }

        public void a(int i, int i2, int i3, Interpolator interpolator) {
            if (this.d != interpolator) {
                this.d = interpolator;
                this.c = new OverScroller(RecyclerView.this.getContext(), interpolator);
            }
            RecyclerView.this.setScrollState(2);
            this.b = 0;
            this.a = 0;
            this.c.startScroll(0, 0, i, i2, i3);
            a();
        }
    }

    @Override // android.view.ViewGroup
    public ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        LayoutManager layoutManager = this.m;
        if (layoutManager != null) {
            return layoutManager.generateLayoutParams(layoutParams);
        }
        throw new IllegalStateException(g9.a(this, g9.a("RecyclerView has no LayoutManager")));
    }

    public final void a(Adapter adapter2, boolean z, boolean z2) {
        Adapter adapter3 = this.l;
        if (adapter3 != null) {
            adapter3.unregisterAdapterDataObserver(this.a);
            this.l.onDetachedFromRecyclerView(this);
        }
        if (!z || z2) {
            q();
        }
        o7 o7Var = this.d;
        o7Var.a(o7Var.b);
        o7Var.a(o7Var.c);
        o7Var.g = 0;
        Adapter adapter4 = this.l;
        this.l = adapter2;
        if (adapter2 != null) {
            adapter2.registerAdapterDataObserver(this.a);
            adapter2.onAttachedToRecyclerView(this);
        }
        LayoutManager layoutManager = this.m;
        if (layoutManager != null) {
            layoutManager.onAdapterChanged(adapter4, this.l);
        }
        Recycler recycler = this.b;
        Adapter adapter5 = this.l;
        recycler.clear();
        RecycledViewPool recycledViewPoolA = recycler.a();
        if (recycledViewPoolA != null) {
            if (adapter4 != null) {
                recycledViewPoolA.b--;
            }
            if (!z && recycledViewPoolA.b == 0) {
                recycledViewPoolA.clear();
            }
            if (adapter5 != null) {
                recycledViewPoolA.b++;
            }
            this.h0.g = true;
            return;
        }
        throw null;
    }

    public final class Recycler {
        public final ArrayList<ViewHolder> a = new ArrayList<>();
        public ArrayList<ViewHolder> b = null;
        public final ArrayList<ViewHolder> c = new ArrayList<>();
        public final List<ViewHolder> d = Collections.unmodifiableList(this.a);
        public int e = 2;
        public int f = 2;
        public RecycledViewPool g;
        public ViewCacheExtension h;

        public Recycler() {
        }

        public final boolean a(ViewHolder viewHolder, int i, int i2, long j) {
            viewHolder.q = RecyclerView.this;
            int itemViewType = viewHolder.getItemViewType();
            long nanoTime = RecyclerView.this.getNanoTime();
            boolean z = false;
            if (j != Long.MAX_VALUE) {
                long j2 = this.g.a(itemViewType).d;
                if (!(j2 == 0 || j2 + nanoTime < j)) {
                    return false;
                }
            }
            RecyclerView.this.l.bindViewHolder(viewHolder, i);
            long nanoTime2 = RecyclerView.this.getNanoTime();
            RecycledViewPool recycledViewPool = this.g;
            RecycledViewPool.a aVarA = recycledViewPool.a(viewHolder.getItemViewType());
            aVarA.d = recycledViewPool.a(aVarA.d, nanoTime2 - nanoTime);
            AccessibilityManager accessibilityManager = RecyclerView.this.B;
            if (accessibilityManager != null && accessibilityManager.isEnabled()) {
                z = true;
            }
            if (z) {
                View view2 = viewHolder.itemView;
                if (ViewCompat.getImportantForAccessibility(view2) == 0) {
                    ViewCompat.setImportantForAccessibility(view2, 1);
                }
                if (!ViewCompat.hasAccessibilityDelegate(view2)) {
                    viewHolder.a(16384);
                    ViewCompat.setAccessibilityDelegate(view2, RecyclerView.this.o0.getItemDelegate());
                }
            }
            if (RecyclerView.this.h0.isPreLayout()) {
                viewHolder.f = i2;
            }
            return true;
        }

        public void b() {
            for (int size = this.c.size() - 1; size >= 0; size--) {
                a(size);
            }
            this.c.clear();
            RecyclerView.v();
            h8.b bVar = RecyclerView.this.g0;
            int[] iArr = bVar.c;
            if (iArr != null) {
                Arrays.fill(iArr, -1);
            }
            bVar.d = 0;
        }

        public void bindViewToPosition(View view2, int i) {
            LayoutParams layoutParams;
            ViewHolder viewHolderD = RecyclerView.d(view2);
            if (viewHolderD == null) {
                throw new IllegalArgumentException(g9.a(RecyclerView.this, g9.a("The view does not have a ViewHolder. You cannot pass arbitrary views to this method, they should be created by the Adapter")));
            }
            int iA = RecyclerView.this.d.a(i, 0);
            if (iA < 0 || iA >= RecyclerView.this.l.getItemCount()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Inconsistency detected. Invalid item position ");
                sb.append(i);
                sb.append("(offset:");
                sb.append(iA);
                sb.append(").");
                sb.append("state:");
                sb.append(RecyclerView.this.h0.getItemCount());
                throw new IndexOutOfBoundsException(g9.a(RecyclerView.this, sb));
            }
            a(viewHolderD, iA, i, Long.MAX_VALUE);
            ViewGroup.LayoutParams layoutParams2 = viewHolderD.itemView.getLayoutParams();
            if (layoutParams2 == null) {
                layoutParams = (LayoutParams) RecyclerView.this.generateDefaultLayoutParams();
                viewHolderD.itemView.setLayoutParams(layoutParams);
            } else if (RecyclerView.this.checkLayoutParams(layoutParams2)) {
                layoutParams = (LayoutParams) layoutParams2;
            } else {
                layoutParams = (LayoutParams) RecyclerView.this.generateLayoutParams(layoutParams2);
                viewHolderD.itemView.setLayoutParams(layoutParams);
            }
            layoutParams.c = true;
            layoutParams.a = viewHolderD;
            layoutParams.d = viewHolderD.itemView.getParent() == null;
        }

        public void c() {
            LayoutManager layoutManager = RecyclerView.this.m;
            this.f = this.e + (layoutManager != null ? layoutManager.m : 0);
            for (int size = this.c.size() - 1; size >= 0 && this.c.size() > this.f; size--) {
                a(size);
            }
        }

        public void clear() {
            this.a.clear();
            b();
        }

        public int convertPreLayoutPositionToPostLayout(int i) {
            if (i >= 0 && i < RecyclerView.this.h0.getItemCount()) {
                return !RecyclerView.this.h0.isPreLayout() ? i : RecyclerView.this.d.a(i, 0);
            }
            StringBuilder sb = new StringBuilder();
            sb.append("invalid position ");
            sb.append(i);
            sb.append(". State ");
            sb.append("item count is ");
            sb.append(RecyclerView.this.h0.getItemCount());
            throw new IndexOutOfBoundsException(g9.a(RecyclerView.this, sb));
        }

        public List<ViewHolder> getScrapList() {
            return this.d;
        }

        public View getViewForPosition(int i) {
            return a(i, false, Long.MAX_VALUE).itemView;
        }

        public void recycleView(View view2) {
            ViewHolder viewHolderD = RecyclerView.d(view2);
            if (viewHolderD.h()) {
                RecyclerView.this.removeDetachedView(view2, false);
            }
            if (viewHolderD.g()) {
                viewHolderD.m.b(viewHolderD);
            } else if (viewHolderD.m()) {
                viewHolderD.b();
            }
            a(viewHolderD);
        }

        public void setViewCacheSize(int i) {
            this.e = i;
            c();
        }

        public void b(ViewHolder viewHolder) {
            if (viewHolder.n) {
                this.b.remove(viewHolder);
            } else {
                this.a.remove(viewHolder);
            }
            viewHolder.m = null;
            viewHolder.n = false;
            viewHolder.b();
        }

        /* JADX WARN: Code restructure failed: missing block: B:165:0x031d, code lost:
        
            r5 = null;
         */
        /* JADX WARN: Code restructure failed: missing block: B:71:0x010b, code lost:
        
            if (r11 == null) goto L85;
         */
        /* JADX WARN: Code restructure failed: missing block: B:72:0x010d, code lost:
        
            r5 = android.support.v7.widget.RecyclerView.d(r11);
            r9 = r16.i.e;
            r10 = ((defpackage.m8) r9.a).a.indexOfChild(r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:73:0x011f, code lost:
        
            if (r10 < 0) goto L83;
         */
        /* JADX WARN: Code restructure failed: missing block: B:75:0x0127, code lost:
        
            if (r9.b.c(r10) == false) goto L81;
         */
        /* JADX WARN: Code restructure failed: missing block: B:76:0x0129, code lost:
        
            r9.b.a(r10);
            r9.d(r11);
            r9 = r16.i.e.b(r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:77:0x0139, code lost:
        
            if (r9 == (-1)) goto L79;
         */
        /* JADX WARN: Code restructure failed: missing block: B:78:0x013b, code lost:
        
            r16.i.e.a(r9);
            a(r11);
            r5.a(8224);
         */
        /* JADX WARN: Code restructure failed: missing block: B:79:0x014c, code lost:
        
            r1 = new java.lang.StringBuilder();
            r1.append("layout index should not be -1 after unhiding a view:");
            r1.append(r5);
         */
        /* JADX WARN: Code restructure failed: missing block: B:80:0x0164, code lost:
        
            throw new java.lang.IllegalStateException(defpackage.g9.a(r16.i, r1));
         */
        /* JADX WARN: Code restructure failed: missing block: B:82:0x017b, code lost:
        
            throw new java.lang.RuntimeException("trying to unhide a view that was not hidden" + r11);
         */
        /* JADX WARN: Code restructure failed: missing block: B:84:0x0192, code lost:
        
            throw new java.lang.IllegalArgumentException("view is not a child, cannot hide " + r11);
         */
        /* JADX WARN: Removed duplicated region for block: B:130:0x025d  */
        /* JADX WARN: Removed duplicated region for block: B:209:0x0411 A[PHI: r0 r5
  0x0411: PHI (r0v10 boolean) = (r0v9 boolean), (r0v35 boolean) binds: [B:129:0x025b, B:191:0x0381] A[DONT_GENERATE, DONT_INLINE]
  0x0411: PHI (r5v4 android.support.v7.widget.RecyclerView$ViewHolder) = (r5v3 android.support.v7.widget.RecyclerView$ViewHolder), (r5v7 android.support.v7.widget.RecyclerView$ViewHolder) binds: [B:129:0x025b, B:191:0x0381] A[DONT_GENERATE, DONT_INLINE]] */
        /* JADX WARN: Removed duplicated region for block: B:217:0x0432  */
        /* JADX WARN: Removed duplicated region for block: B:223:0x045e  */
        /* JADX WARN: Removed duplicated region for block: B:234:0x048e  */
        /* JADX WARN: Removed duplicated region for block: B:235:0x049c  */
        /* JADX WARN: Removed duplicated region for block: B:241:0x04b8 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:243:0x04bb  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x0092  */
        /* JADX WARN: Removed duplicated region for block: B:42:0x0099  */
        /* JADX WARN: Removed duplicated region for block: B:97:0x01bf  */
        @android.support.annotation.Nullable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public android.support.v7.widget.RecyclerView.ViewHolder a(int r17, boolean r18, long r19) {
            /*
                Method dump skipped, instructions count: 1264
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.Recycler.a(int, boolean, long):android.support.v7.widget.RecyclerView$ViewHolder");
        }

        public final void a(ViewGroup viewGroup, boolean z) {
            for (int childCount = viewGroup.getChildCount() - 1; childCount >= 0; childCount--) {
                View childAt = viewGroup.getChildAt(childCount);
                if (childAt instanceof ViewGroup) {
                    a((ViewGroup) childAt, true);
                }
            }
            if (z) {
                if (viewGroup.getVisibility() == 4) {
                    viewGroup.setVisibility(0);
                    viewGroup.setVisibility(4);
                } else {
                    int visibility = viewGroup.getVisibility();
                    viewGroup.setVisibility(4);
                    viewGroup.setVisibility(visibility);
                }
            }
        }

        public void a(int i) {
            a(this.c.get(i), true);
            this.c.remove(i);
        }

        public void a(ViewHolder viewHolder) {
            boolean z;
            boolean z2;
            if (!viewHolder.g() && viewHolder.itemView.getParent() == null) {
                if (!viewHolder.h()) {
                    if (!viewHolder.l()) {
                        boolean z3 = (viewHolder.i & 16) == 0 && ViewCompat.hasTransientState(viewHolder.itemView);
                        Adapter adapter2 = RecyclerView.this.l;
                        if ((adapter2 != null && z3 && adapter2.onFailedToRecycleView(viewHolder)) || viewHolder.isRecyclable()) {
                            if (this.f <= 0 || viewHolder.b(526)) {
                                z = false;
                            } else {
                                int size = this.c.size();
                                if (size >= this.f && size > 0) {
                                    a(0);
                                    size--;
                                }
                                if (size > 0 && !RecyclerView.this.g0.a(viewHolder.b)) {
                                    do {
                                        size--;
                                        if (size < 0) {
                                            break;
                                        }
                                    } while (RecyclerView.this.g0.a(this.c.get(size).b));
                                    size++;
                                }
                                this.c.add(size, viewHolder);
                                z = true;
                            }
                            if (!z) {
                                a(viewHolder, true);
                                z = true;
                            }
                            z2 = z;
                            z = z;
                        } else {
                            z2 = false;
                        }
                        RecyclerView.this.f.d(viewHolder);
                        if (z || z2 || !z3) {
                            return;
                        }
                        viewHolder.q = null;
                        return;
                    }
                    throw new IllegalArgumentException(g9.a(RecyclerView.this, g9.a("Trying to recycle an ignored view holder. You should first call stopIgnoringView(view) before calling recycle.")));
                }
                StringBuilder sb = new StringBuilder();
                sb.append("Tmp detached view should be removed from RecyclerView before it can be recycled: ");
                sb.append(viewHolder);
                throw new IllegalArgumentException(g9.a(RecyclerView.this, sb));
            }
            StringBuilder sbA = g9.a("Scrapped or attached views may not be recycled. isScrap:");
            sbA.append(viewHolder.g());
            sbA.append(" isAttached:");
            sbA.append(viewHolder.itemView.getParent() != null);
            throw new IllegalArgumentException(g9.a(RecyclerView.this, sbA));
        }

        public void a(ViewHolder viewHolder, boolean z) {
            RecyclerView.d(viewHolder);
            if (viewHolder.b(16384)) {
                viewHolder.a(0, 16384);
                ViewCompat.setAccessibilityDelegate(viewHolder.itemView, null);
            }
            if (z) {
                RecyclerListener recyclerListener = RecyclerView.this.n;
                if (recyclerListener != null) {
                    recyclerListener.onViewRecycled(viewHolder);
                }
                Adapter adapter2 = RecyclerView.this.l;
                if (adapter2 != null) {
                    adapter2.onViewRecycled(viewHolder);
                }
                RecyclerView recyclerView = RecyclerView.this;
                if (recyclerView.h0 != null) {
                    recyclerView.f.d(viewHolder);
                }
            }
            viewHolder.q = null;
            a().putRecycledView(viewHolder);
        }

        public void a(View view2) {
            ViewHolder viewHolderD = RecyclerView.d(view2);
            if (!viewHolderD.b(12) && viewHolderD.i()) {
                ItemAnimator itemAnimator = RecyclerView.this.M;
                if (!(itemAnimator == null || itemAnimator.canReuseUpdatedViewHolder(viewHolderD, viewHolderD.c()))) {
                    if (this.b == null) {
                        this.b = new ArrayList<>();
                    }
                    viewHolderD.m = this;
                    viewHolderD.n = true;
                    this.b.add(viewHolderD);
                    return;
                }
            }
            if (viewHolderD.e() && !viewHolderD.f() && !RecyclerView.this.l.hasStableIds()) {
                throw new IllegalArgumentException(g9.a(RecyclerView.this, g9.a("Called scrap view with an invalid view. Invalid views cannot be reused from scrap, they should rebound from recycler pool.")));
            }
            viewHolderD.m = this;
            viewHolderD.n = false;
            this.a.add(viewHolderD);
        }

        public RecycledViewPool a() {
            if (this.g == null) {
                this.g = new RecycledViewPool();
            }
            return this.g;
        }
    }

    public void b() {
        int iB = this.e.b();
        for (int i = 0; i < iB; i++) {
            ViewHolder viewHolderD = d(this.e.d(i));
            if (!viewHolderD.l()) {
                viewHolderD.a();
            }
        }
        Recycler recycler = this.b;
        int size = recycler.c.size();
        for (int i2 = 0; i2 < size; i2++) {
            recycler.c.get(i2).a();
        }
        int size2 = recycler.a.size();
        for (int i3 = 0; i3 < size2; i3++) {
            recycler.a.get(i3).a();
        }
        ArrayList<ViewHolder> arrayList = recycler.b;
        if (arrayList != null) {
            int size3 = arrayList.size();
            for (int i4 = 0; i4 < size3; i4++) {
                recycler.b.get(i4).a();
            }
        }
    }

    public void addItemDecoration(ItemDecoration itemDecoration) {
        addItemDecoration(itemDecoration, -1);
    }

    public void b(boolean z) {
        this.E = z | this.E;
        this.D = true;
        int iB = this.e.b();
        for (int i = 0; i < iB; i++) {
            ViewHolder viewHolderD = d(this.e.d(i));
            if (viewHolderD != null && !viewHolderD.l()) {
                viewHolderD.a(6);
            }
        }
        m();
        Recycler recycler = this.b;
        int size = recycler.c.size();
        for (int i2 = 0; i2 < size; i2++) {
            ViewHolder viewHolder = recycler.c.get(i2);
            if (viewHolder != null) {
                viewHolder.a(6);
                viewHolder.a((Object) null);
            }
        }
        Adapter adapter2 = RecyclerView.this.l;
        if (adapter2 == null || !adapter2.hasStableIds()) {
            recycler.b();
        }
    }

    public final void a(ViewHolder viewHolder) {
        View view2 = viewHolder.itemView;
        boolean z = view2.getParent() == this;
        this.b.b(getChildViewHolder(view2));
        if (viewHolder.h()) {
            this.e.a(view2, -1, view2.getLayoutParams(), true);
            return;
        }
        if (!z) {
            this.e.a(view2, -1, true);
            return;
        }
        z7 z7Var = this.e;
        int iIndexOfChild = ((m8) z7Var.a).a.indexOfChild(view2);
        if (iIndexOfChild >= 0) {
            z7Var.b.e(iIndexOfChild);
            z7Var.a(view2);
        } else {
            throw new IllegalArgumentException("view is not a child, cannot hide " + view2);
        }
    }

    public void c(boolean z) {
        if (this.v < 1) {
            this.v = 1;
        }
        if (!z && !this.x) {
            this.w = false;
        }
        if (this.v == 1) {
            if (z && this.w && !this.x && this.m != null && this.l != null) {
                d();
            }
            if (!this.x) {
                this.w = false;
            }
        }
        this.v--;
    }

    public Rect b(View view2) {
        LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
        if (!layoutParams.c) {
            return layoutParams.b;
        }
        if (this.h0.isPreLayout() && (layoutParams.isItemChanged() || layoutParams.isViewInvalid())) {
            return layoutParams.b;
        }
        Rect rect = layoutParams.b;
        rect.set(0, 0, 0, 0);
        int size = this.o.size();
        for (int i = 0; i < size; i++) {
            this.i.set(0, 0, 0, 0);
            this.o.get(i).getItemOffsets(this.i, view2, this, this.h0);
            int i2 = rect.left;
            Rect rect2 = this.i;
            rect.left = i2 + rect2.left;
            rect.top += rect2.top;
            rect.right += rect2.right;
            rect.bottom += rect2.bottom;
        }
        layoutParams.c = false;
        return rect;
    }

    public long c(ViewHolder viewHolder) {
        return this.l.hasStableIds() ? viewHolder.getItemId() : viewHolder.b;
    }

    public void c(int i, int i2) {
        this.G++;
        int scrollX = getScrollX();
        int scrollY = getScrollY();
        onScrollChanged(scrollX, scrollY, scrollX, scrollY);
        onScrolled(i, i2);
        OnScrollListener onScrollListener = this.i0;
        if (onScrollListener != null) {
            onScrollListener.onScrolled(this, i, i2);
        }
        List<OnScrollListener> list = this.j0;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.j0.get(size).onScrolled(this, i, i2);
            }
        }
        this.G--;
    }

    public void a(int i) {
        LayoutManager layoutManager = this.m;
        if (layoutManager == null) {
            return;
        }
        layoutManager.scrollToPosition(i);
        awakenScrollBars();
    }

    /* JADX WARN: Removed duplicated region for block: B:37:0x0102  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x013d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean a(int r19, int r20, android.view.MotionEvent r21) {
        /*
            Method dump skipped, instructions count: 346
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.a(int, int, android.view.MotionEvent):boolean");
    }

    @Nullable
    public static RecyclerView c(@NonNull View view2) {
        if (!(view2 instanceof ViewGroup)) {
            return null;
        }
        if (view2 instanceof RecyclerView) {
            return (RecyclerView) view2;
        }
        ViewGroup viewGroup = (ViewGroup) view2;
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            RecyclerView recyclerViewC = c(viewGroup.getChildAt(i));
            if (recyclerViewC != null) {
                return recyclerViewC;
            }
        }
        return null;
    }

    public int b(ViewHolder viewHolder) {
        if (viewHolder.b(524) || !viewHolder.d()) {
            return -1;
        }
        o7 o7Var = this.d;
        int i = viewHolder.b;
        int size = o7Var.b.size();
        for (int i2 = 0; i2 < size; i2++) {
            o7.b bVar = o7Var.b.get(i2);
            int i3 = bVar.a;
            if (i3 != 1) {
                if (i3 == 2) {
                    int i4 = bVar.b;
                    if (i4 <= i) {
                        int i5 = bVar.d;
                        if (i4 + i5 > i) {
                            return -1;
                        }
                        i -= i5;
                    } else {
                        continue;
                    }
                } else if (i3 == 8) {
                    int i6 = bVar.b;
                    if (i6 == i) {
                        i = bVar.d;
                    } else {
                        if (i6 < i) {
                            i--;
                        }
                        if (bVar.d <= i) {
                            i++;
                        }
                    }
                }
            } else if (bVar.b <= i) {
                i += bVar.d;
            }
        }
        return i;
    }

    public void a(int i, int i2) {
        boolean zIsFinished;
        EdgeEffect edgeEffect = this.I;
        if (edgeEffect == null || edgeEffect.isFinished() || i <= 0) {
            zIsFinished = false;
        } else {
            this.I.onRelease();
            zIsFinished = this.I.isFinished();
        }
        EdgeEffect edgeEffect2 = this.K;
        if (edgeEffect2 != null && !edgeEffect2.isFinished() && i < 0) {
            this.K.onRelease();
            zIsFinished |= this.K.isFinished();
        }
        EdgeEffect edgeEffect3 = this.J;
        if (edgeEffect3 != null && !edgeEffect3.isFinished() && i2 > 0) {
            this.J.onRelease();
            zIsFinished |= this.J.isFinished();
        }
        EdgeEffect edgeEffect4 = this.L;
        if (edgeEffect4 != null && !edgeEffect4.isFinished() && i2 < 0) {
            this.L.onRelease();
            zIsFinished |= this.L.isFinished();
        }
        if (zIsFinished) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    public final void a(@NonNull View view2, @Nullable View view3) {
        View view4 = view3 != null ? view3 : view2;
        this.i.set(0, 0, view4.getWidth(), view4.getHeight());
        ViewGroup.LayoutParams layoutParams = view4.getLayoutParams();
        if (layoutParams instanceof LayoutParams) {
            LayoutParams layoutParams2 = (LayoutParams) layoutParams;
            if (!layoutParams2.c) {
                Rect rect = layoutParams2.b;
                Rect rect2 = this.i;
                rect2.left -= rect.left;
                rect2.right += rect.right;
                rect2.top -= rect.top;
                rect2.bottom += rect.bottom;
            }
        }
        if (view3 != null) {
            offsetDescendantRectToMyCoords(view3, this.i);
            offsetRectIntoDescendantCoords(view2, this.i);
        }
        this.m.requestChildRectangleOnScreen(this, view2, this.i, !this.u, view3 == null);
    }

    public void a(String str) {
        if (isComputingLayout()) {
            if (str == null) {
                throw new IllegalStateException(g9.a(this, g9.a("Cannot call this method while RecyclerView is computing a layout or scrolling")));
            }
            throw new IllegalStateException(str);
        }
        if (this.G > 0) {
            Log.w("RecyclerView", "Cannot call this method in a scroll callback. Scroll callbacks mightbe run during a measure & layout pass where you cannot change theRecyclerView data. Any method call that might change the structureof the RecyclerView or the adapter contents should be postponed tothe next frame.", new IllegalStateException(g9.a(this, g9.a(""))));
        }
    }

    public final void a() {
        s();
        setScrollState(0);
    }

    public final void a(MotionEvent motionEvent) {
        int actionIndex = motionEvent.getActionIndex();
        if (motionEvent.getPointerId(actionIndex) == this.O) {
            int i = actionIndex == 0 ? 1 : 0;
            this.O = motionEvent.getPointerId(i);
            int x = (int) (motionEvent.getX(i) + 0.5f);
            this.S = x;
            this.Q = x;
            int y = (int) (motionEvent.getY(i) + 0.5f);
            this.T = y;
            this.R = y;
        }
    }

    public void a(boolean z) {
        int i;
        int i2 = this.F - 1;
        this.F = i2;
        if (i2 < 1) {
            this.F = 0;
            if (z) {
                int i3 = this.z;
                this.z = 0;
                if (i3 != 0) {
                    AccessibilityManager accessibilityManager = this.B;
                    if (accessibilityManager != null && accessibilityManager.isEnabled()) {
                        AccessibilityEvent accessibilityEventObtain = AccessibilityEvent.obtain();
                        accessibilityEventObtain.setEventType(2048);
                        AccessibilityEventCompat.setContentChangeTypes(accessibilityEventObtain, i3);
                        sendAccessibilityEventUnchecked(accessibilityEventObtain);
                    }
                }
                for (int size = this.v0.size() - 1; size >= 0; size--) {
                    ViewHolder viewHolder = this.v0.get(size);
                    if (viewHolder.itemView.getParent() == this && !viewHolder.l() && (i = viewHolder.p) != -1) {
                        ViewCompat.setImportantForAccessibility(viewHolder.itemView, i);
                        viewHolder.p = -1;
                    }
                }
                this.v0.clear();
            }
        }
    }

    public static ViewHolder d(View view2) {
        if (view2 == null) {
            return null;
        }
        return ((LayoutParams) view2.getLayoutParams()).a;
    }

    public static void d(@NonNull ViewHolder viewHolder) {
        WeakReference<RecyclerView> weakReference = viewHolder.a;
        if (weakReference != null) {
            RecyclerView recyclerView = weakReference.get();
            while (recyclerView != null) {
                if (recyclerView == viewHolder.itemView) {
                    return;
                }
                Object parent = recyclerView.getParent();
                recyclerView = parent instanceof View ? (View) parent : null;
            }
            viewHolder.a = null;
        }
    }

    public final void a(State state) {
        if (getScrollState() == 2) {
            OverScroller overScroller = this.e0.c;
            state.p = overScroller.getFinalX() - overScroller.getCurrX();
            state.q = overScroller.getFinalY() - overScroller.getCurrY();
        } else {
            state.p = 0;
            state.q = 0;
        }
    }

    public void a(ViewHolder viewHolder, ItemAnimator.ItemHolderInfo itemHolderInfo) {
        viewHolder.a(0, 8192);
        if (this.h0.i && viewHolder.i() && !viewHolder.f() && !viewHolder.l()) {
            this.f.b.put(c(viewHolder), viewHolder);
        }
        this.f.b(viewHolder, itemHolderInfo);
    }

    public final void a(int[] iArr) {
        int iA = this.e.a();
        if (iA == 0) {
            iArr[0] = -1;
            iArr[1] = -1;
            return;
        }
        int i = Integer.MAX_VALUE;
        int i2 = Integer.MIN_VALUE;
        for (int i3 = 0; i3 < iA; i3++) {
            ViewHolder viewHolderD = d(this.e.b(i3));
            if (!viewHolderD.l()) {
                int layoutPosition = viewHolderD.getLayoutPosition();
                if (layoutPosition < i) {
                    i = layoutPosition;
                }
                if (layoutPosition > i2) {
                    i2 = layoutPosition;
                }
            }
        }
        iArr[0] = i;
        iArr[1] = i2;
    }

    public void a(int i, int i2, boolean z) {
        int i3 = i + i2;
        int iB = this.e.b();
        for (int i4 = 0; i4 < iB; i4++) {
            ViewHolder viewHolderD = d(this.e.d(i4));
            if (viewHolderD != null && !viewHolderD.l()) {
                int i5 = viewHolderD.b;
                if (i5 >= i3) {
                    viewHolderD.a(-i2, z);
                    this.h0.g = true;
                } else if (i5 >= i) {
                    viewHolderD.a(8);
                    viewHolderD.a(-i2, z);
                    viewHolderD.b = i - 1;
                    this.h0.g = true;
                }
            }
        }
        Recycler recycler = this.b;
        int size = recycler.c.size();
        while (true) {
            size--;
            if (size >= 0) {
                ViewHolder viewHolder = recycler.c.get(size);
                if (viewHolder != null) {
                    int i6 = viewHolder.b;
                    if (i6 >= i3) {
                        viewHolder.a(-i2, z);
                    } else if (i6 >= i) {
                        viewHolder.a(8);
                        recycler.a(size);
                    }
                }
            } else {
                requestLayout();
                return;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public android.support.v7.widget.RecyclerView.ViewHolder a(int r6, boolean r7) {
        /*
            r5 = this;
            z7 r0 = r5.e
            int r0 = r0.b()
            r1 = 0
            r2 = 0
        L8:
            if (r2 >= r0) goto L3a
            z7 r3 = r5.e
            android.view.View r3 = r3.d(r2)
            android.support.v7.widget.RecyclerView$ViewHolder r3 = d(r3)
            if (r3 == 0) goto L37
            boolean r4 = r3.f()
            if (r4 != 0) goto L37
            if (r7 == 0) goto L23
            int r4 = r3.b
            if (r4 == r6) goto L2a
            goto L37
        L23:
            int r4 = r3.getLayoutPosition()
            if (r4 == r6) goto L2a
            goto L37
        L2a:
            z7 r1 = r5.e
            android.view.View r4 = r3.itemView
            boolean r1 = r1.c(r4)
            if (r1 == 0) goto L36
            r1 = r3
            goto L37
        L36:
            return r3
        L37:
            int r2 = r2 + 1
            goto L8
        L3a:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.RecyclerView.a(int, boolean):android.support.v7.widget.RecyclerView$ViewHolder");
    }

    public static void a(View view2, Rect rect) {
        LayoutParams layoutParams = (LayoutParams) view2.getLayoutParams();
        Rect rect2 = layoutParams.b;
        rect.set((view2.getLeft() - rect2.left) - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin, (view2.getTop() - rect2.top) - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, view2.getRight() + rect2.right + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin, view2.getBottom() + rect2.bottom + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin);
    }

    public void a(View view2) {
        ViewHolder viewHolderD = d(view2);
        onChildDetachedFromWindow(view2);
        Adapter adapter2 = this.l;
        if (adapter2 != null && viewHolderD != null) {
            adapter2.onViewDetachedFromWindow(viewHolderD);
        }
        List<OnChildAttachStateChangeListener> list = this.C;
        if (list != null) {
            for (int size = list.size() - 1; size >= 0; size--) {
                this.C.get(size).onChildViewDetachedFromWindow(view2);
            }
        }
    }

    @VisibleForTesting
    public boolean a(ViewHolder viewHolder, int i) {
        if (isComputingLayout()) {
            viewHolder.p = i;
            this.v0.add(viewHolder);
            return false;
        }
        ViewCompat.setImportantForAccessibility(viewHolder.itemView, i);
        return true;
    }
}
