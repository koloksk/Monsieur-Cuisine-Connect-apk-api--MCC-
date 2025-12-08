package android.support.v7.widget;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/* loaded from: classes.dex */
public abstract class OrientationHelper {
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    public int a = Integer.MIN_VALUE;
    public final Rect b = new Rect();
    public final RecyclerView.LayoutManager mLayoutManager;

    public static class a extends OrientationHelper {
        public a(RecyclerView.LayoutManager layoutManager) {
            super(layoutManager, null);
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getDecoratedEnd(View view2) {
            return this.mLayoutManager.getDecoratedRight(view2) + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view2.getLayoutParams())).rightMargin;
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getDecoratedMeasurement(View view2) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view2.getLayoutParams();
            return this.mLayoutManager.getDecoratedMeasuredWidth(view2) + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getDecoratedMeasurementInOther(View view2) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view2.getLayoutParams();
            return this.mLayoutManager.getDecoratedMeasuredHeight(view2) + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getDecoratedStart(View view2) {
            return this.mLayoutManager.getDecoratedLeft(view2) - ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view2.getLayoutParams())).leftMargin;
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getEnd() {
            return this.mLayoutManager.getWidth();
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getEndAfterPadding() {
            return this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingRight();
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getEndPadding() {
            return this.mLayoutManager.getPaddingRight();
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getMode() {
            return this.mLayoutManager.getWidthMode();
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getModeInOther() {
            return this.mLayoutManager.getHeightMode();
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getStartAfterPadding() {
            return this.mLayoutManager.getPaddingLeft();
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getTotalSpace() {
            return (this.mLayoutManager.getWidth() - this.mLayoutManager.getPaddingLeft()) - this.mLayoutManager.getPaddingRight();
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getTransformedEndWithDecoration(View view2) {
            this.mLayoutManager.getTransformedBoundingBox(view2, true, this.b);
            return this.b.right;
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getTransformedStartWithDecoration(View view2) {
            this.mLayoutManager.getTransformedBoundingBox(view2, true, this.b);
            return this.b.left;
        }

        @Override // android.support.v7.widget.OrientationHelper
        public void offsetChild(View view2, int i) {
            view2.offsetLeftAndRight(i);
        }

        @Override // android.support.v7.widget.OrientationHelper
        public void offsetChildren(int i) {
            this.mLayoutManager.offsetChildrenHorizontal(i);
        }
    }

    public static class b extends OrientationHelper {
        public b(RecyclerView.LayoutManager layoutManager) {
            super(layoutManager, null);
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getDecoratedEnd(View view2) {
            return this.mLayoutManager.getDecoratedBottom(view2) + ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view2.getLayoutParams())).bottomMargin;
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getDecoratedMeasurement(View view2) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view2.getLayoutParams();
            return this.mLayoutManager.getDecoratedMeasuredHeight(view2) + ((ViewGroup.MarginLayoutParams) layoutParams).topMargin + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getDecoratedMeasurementInOther(View view2) {
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view2.getLayoutParams();
            return this.mLayoutManager.getDecoratedMeasuredWidth(view2) + ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getDecoratedStart(View view2) {
            return this.mLayoutManager.getDecoratedTop(view2) - ((ViewGroup.MarginLayoutParams) ((RecyclerView.LayoutParams) view2.getLayoutParams())).topMargin;
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getEnd() {
            return this.mLayoutManager.getHeight();
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getEndAfterPadding() {
            return this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingBottom();
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getEndPadding() {
            return this.mLayoutManager.getPaddingBottom();
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getMode() {
            return this.mLayoutManager.getHeightMode();
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getModeInOther() {
            return this.mLayoutManager.getWidthMode();
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getStartAfterPadding() {
            return this.mLayoutManager.getPaddingTop();
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getTotalSpace() {
            return (this.mLayoutManager.getHeight() - this.mLayoutManager.getPaddingTop()) - this.mLayoutManager.getPaddingBottom();
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getTransformedEndWithDecoration(View view2) {
            this.mLayoutManager.getTransformedBoundingBox(view2, true, this.b);
            return this.b.bottom;
        }

        @Override // android.support.v7.widget.OrientationHelper
        public int getTransformedStartWithDecoration(View view2) {
            this.mLayoutManager.getTransformedBoundingBox(view2, true, this.b);
            return this.b.top;
        }

        @Override // android.support.v7.widget.OrientationHelper
        public void offsetChild(View view2, int i) {
            view2.offsetTopAndBottom(i);
        }

        @Override // android.support.v7.widget.OrientationHelper
        public void offsetChildren(int i) {
            this.mLayoutManager.offsetChildrenVertical(i);
        }
    }

    public /* synthetic */ OrientationHelper(RecyclerView.LayoutManager layoutManager, a aVar) {
        this.mLayoutManager = layoutManager;
    }

    public static OrientationHelper createHorizontalHelper(RecyclerView.LayoutManager layoutManager) {
        return new a(layoutManager);
    }

    public static OrientationHelper createOrientationHelper(RecyclerView.LayoutManager layoutManager, int i) {
        if (i == 0) {
            return createHorizontalHelper(layoutManager);
        }
        if (i == 1) {
            return createVerticalHelper(layoutManager);
        }
        throw new IllegalArgumentException("invalid orientation");
    }

    public static OrientationHelper createVerticalHelper(RecyclerView.LayoutManager layoutManager) {
        return new b(layoutManager);
    }

    public abstract int getDecoratedEnd(View view2);

    public abstract int getDecoratedMeasurement(View view2);

    public abstract int getDecoratedMeasurementInOther(View view2);

    public abstract int getDecoratedStart(View view2);

    public abstract int getEnd();

    public abstract int getEndAfterPadding();

    public abstract int getEndPadding();

    public RecyclerView.LayoutManager getLayoutManager() {
        return this.mLayoutManager;
    }

    public abstract int getMode();

    public abstract int getModeInOther();

    public abstract int getStartAfterPadding();

    public abstract int getTotalSpace();

    public int getTotalSpaceChange() {
        if (Integer.MIN_VALUE == this.a) {
            return 0;
        }
        return getTotalSpace() - this.a;
    }

    public abstract int getTransformedEndWithDecoration(View view2);

    public abstract int getTransformedStartWithDecoration(View view2);

    public abstract void offsetChild(View view2, int i);

    public abstract void offsetChildren(int i);

    public void onLayoutComplete() {
        this.a = getTotalSpace();
    }
}
