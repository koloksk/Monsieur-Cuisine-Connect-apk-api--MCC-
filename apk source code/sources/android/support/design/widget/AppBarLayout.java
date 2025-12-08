package android.support.design.widget;

import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.design.R;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.math.MathUtils;
import android.support.v4.util.ObjectsCompat;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.LinearLayout;
import cooking.Limits;
import defpackage.p2;
import defpackage.q1;
import defpackage.r1;
import defpackage.r2;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

@CoordinatorLayout.DefaultBehavior(Behavior.class)
/* loaded from: classes.dex */
public class AppBarLayout extends LinearLayout {
    public int a;
    public int b;
    public int c;
    public boolean d;
    public int e;
    public WindowInsetsCompat f;
    public List<OnOffsetChangedListener> g;
    public boolean h;
    public boolean i;
    public int[] j;

    public static class Behavior extends HeaderBehavior<AppBarLayout> {
        public int k;
        public ValueAnimator l;
        public int m;
        public boolean n;
        public float o;
        public WeakReference<View> p;
        public DragCallback q;

        public static abstract class DragCallback {
            public abstract boolean canDrag(@NonNull AppBarLayout appBarLayout);
        }

        public Behavior() {
            this.m = -1;
        }

        @Override // android.support.design.widget.HeaderBehavior
        public boolean a(View view2) {
            View view3;
            AppBarLayout appBarLayout = (AppBarLayout) view2;
            DragCallback dragCallback = this.q;
            if (dragCallback != null) {
                return dragCallback.canDrag(appBarLayout);
            }
            WeakReference<View> weakReference = this.p;
            return weakReference == null || !((view3 = weakReference.get()) == null || !view3.isShown() || view3.canScrollVertically(-1));
        }

        @Override // android.support.design.widget.HeaderBehavior
        public int b(CoordinatorLayout coordinatorLayout, View view2, int i, int i2, int i3) {
            int top;
            AppBarLayout appBarLayout = (AppBarLayout) view2;
            int iA = a();
            int topInset = 0;
            if (i2 == 0 || iA < i2 || iA > i3) {
                this.k = 0;
                return 0;
            }
            int iClamp = MathUtils.clamp(i, i2, i3);
            if (iA == iClamp) {
                return 0;
            }
            if (appBarLayout.d) {
                int iAbs = Math.abs(iClamp);
                int childCount = appBarLayout.getChildCount();
                int i4 = 0;
                while (true) {
                    if (i4 >= childCount) {
                        break;
                    }
                    View childAt = appBarLayout.getChildAt(i4);
                    LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
                    Interpolator scrollInterpolator = layoutParams.getScrollInterpolator();
                    if (iAbs < childAt.getTop() || iAbs > childAt.getBottom()) {
                        i4++;
                    } else if (scrollInterpolator != null) {
                        int scrollFlags = layoutParams.getScrollFlags();
                        if ((scrollFlags & 1) != 0) {
                            topInset = 0 + childAt.getHeight() + ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
                            if ((scrollFlags & 2) != 0) {
                                topInset -= ViewCompat.getMinimumHeight(childAt);
                            }
                        }
                        if (ViewCompat.getFitsSystemWindows(childAt)) {
                            topInset -= appBarLayout.getTopInset();
                        }
                        if (topInset > 0) {
                            float f = topInset;
                            top = (childAt.getTop() + Math.round(scrollInterpolator.getInterpolation((iAbs - childAt.getTop()) / f) * f)) * Integer.signum(iClamp);
                        }
                    }
                }
                top = iClamp;
            } else {
                top = iClamp;
            }
            boolean topAndBottomOffset = setTopAndBottomOffset(top);
            int i5 = iA - iClamp;
            this.k = iClamp - top;
            if (!topAndBottomOffset && appBarLayout.d) {
                coordinatorLayout.dispatchDependentViewsChanged(appBarLayout);
            }
            appBarLayout.a(getTopAndBottomOffset());
            a(coordinatorLayout, appBarLayout, iClamp, iClamp < iA ? -1 : 1, false);
            return i5;
        }

        @Override // android.support.design.widget.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        @Override // android.support.design.widget.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        /* JADX WARN: Removed duplicated region for block: B:12:0x0021  */
        @Override // android.support.design.widget.CoordinatorLayout.Behavior
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean onStartNestedScroll(android.support.design.widget.CoordinatorLayout r1, android.support.design.widget.AppBarLayout r2, android.view.View r3, android.view.View r4, int r5, int r6) {
            /*
                r0 = this;
                r4 = r5 & 2
                r5 = 1
                r6 = 0
                if (r4 == 0) goto L21
                int r4 = r2.getTotalScrollRange()
                if (r4 == 0) goto Le
                r4 = r5
                goto Lf
            Le:
                r4 = r6
            Lf:
                if (r4 == 0) goto L21
                int r1 = r1.getHeight()
                int r3 = r3.getHeight()
                int r1 = r1 - r3
                int r2 = r2.getHeight()
                if (r1 > r2) goto L21
                goto L22
            L21:
                r5 = r6
            L22:
                if (r5 == 0) goto L2b
                android.animation.ValueAnimator r1 = r0.l
                if (r1 == 0) goto L2b
                r1.cancel()
            L2b:
                r1 = 0
                r0.p = r1
                return r5
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.AppBarLayout.Behavior.onStartNestedScroll(android.support.design.widget.CoordinatorLayout, android.support.design.widget.AppBarLayout, android.view.View, android.view.View, int, int):boolean");
        }

        public void setDragCallback(@Nullable DragCallback dragCallback) {
            this.q = dragCallback;
        }

        @Override // android.support.design.widget.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i) {
            return super.setLeftAndRightOffset(i);
        }

        @Override // android.support.design.widget.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i) {
            return super.setTopAndBottomOffset(i);
        }

        @Override // android.support.design.widget.ViewOffsetBehavior, android.support.design.widget.CoordinatorLayout.Behavior
        public boolean onLayoutChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i) {
            boolean zOnLayoutChild = super.onLayoutChild(coordinatorLayout, (CoordinatorLayout) appBarLayout, i);
            int pendingAction = appBarLayout.getPendingAction();
            int i2 = this.m;
            if (i2 >= 0 && (pendingAction & 8) == 0) {
                View childAt = appBarLayout.getChildAt(i2);
                int i3 = -childAt.getBottom();
                a(coordinatorLayout, appBarLayout, this.n ? appBarLayout.getTopInset() + ViewCompat.getMinimumHeight(childAt) + i3 : Math.round(childAt.getHeight() * this.o) + i3);
            } else if (pendingAction != 0) {
                boolean z = (pendingAction & 4) != 0;
                if ((pendingAction & 2) != 0) {
                    int i4 = -appBarLayout.getUpNestedPreScrollRange();
                    if (z) {
                        a(coordinatorLayout, appBarLayout, i4, 0.0f);
                    } else {
                        a(coordinatorLayout, appBarLayout, i4);
                    }
                } else if ((pendingAction & 1) != 0) {
                    if (z) {
                        a(coordinatorLayout, appBarLayout, 0, 0.0f);
                    } else {
                        a(coordinatorLayout, appBarLayout, 0);
                    }
                }
            }
            appBarLayout.e = 0;
            this.m = -1;
            setTopAndBottomOffset(MathUtils.clamp(getTopAndBottomOffset(), -appBarLayout.getTotalScrollRange(), 0));
            a(coordinatorLayout, appBarLayout, getTopAndBottomOffset(), 0, true);
            appBarLayout.a(getTopAndBottomOffset());
            return zOnLayoutChild;
        }

        @Override // android.support.design.widget.CoordinatorLayout.Behavior
        public boolean onMeasureChild(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, int i2, int i3, int i4) {
            if (((ViewGroup.MarginLayoutParams) ((CoordinatorLayout.LayoutParams) appBarLayout.getLayoutParams())).height != -2) {
                return super.onMeasureChild(coordinatorLayout, (CoordinatorLayout) appBarLayout, i, i2, i3, i4);
            }
            coordinatorLayout.onMeasureChild(appBarLayout, i, i2, View.MeasureSpec.makeMeasureSpec(0, 0), i4);
            return true;
        }

        @Override // android.support.design.widget.CoordinatorLayout.Behavior
        public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view2, int i, int i2, int[] iArr, int i3) {
            int i4;
            int downNestedPreScrollRange;
            if (i2 != 0) {
                if (i2 < 0) {
                    i4 = -appBarLayout.getTotalScrollRange();
                    downNestedPreScrollRange = appBarLayout.getDownNestedPreScrollRange() + i4;
                } else {
                    i4 = -appBarLayout.getUpNestedPreScrollRange();
                    downNestedPreScrollRange = 0;
                }
                int i5 = i4;
                int i6 = downNestedPreScrollRange;
                if (i5 != i6) {
                    iArr[1] = a(coordinatorLayout, (CoordinatorLayout) appBarLayout, i2, i5, i6);
                }
            }
        }

        @Override // android.support.design.widget.CoordinatorLayout.Behavior
        public void onNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view2, int i, int i2, int i3, int i4, int i5) {
            if (i4 < 0) {
                a(coordinatorLayout, (CoordinatorLayout) appBarLayout, i4, -appBarLayout.getDownNestedScrollRange(), 0);
            }
        }

        @Override // android.support.design.widget.CoordinatorLayout.Behavior
        public void onRestoreInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, Parcelable parcelable) {
            if (!(parcelable instanceof SavedState)) {
                super.onRestoreInstanceState(coordinatorLayout, (CoordinatorLayout) appBarLayout, parcelable);
                this.m = -1;
                return;
            }
            SavedState savedState = (SavedState) parcelable;
            super.onRestoreInstanceState(coordinatorLayout, (CoordinatorLayout) appBarLayout, savedState.getSuperState());
            this.m = savedState.b;
            this.o = savedState.c;
            this.n = savedState.d;
        }

        @Override // android.support.design.widget.CoordinatorLayout.Behavior
        public Parcelable onSaveInstanceState(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            Parcelable parcelableOnSaveInstanceState = super.onSaveInstanceState(coordinatorLayout, (CoordinatorLayout) appBarLayout);
            int topAndBottomOffset = getTopAndBottomOffset();
            int childCount = appBarLayout.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = appBarLayout.getChildAt(i);
                int bottom = childAt.getBottom() + topAndBottomOffset;
                if (childAt.getTop() + topAndBottomOffset <= 0 && bottom >= 0) {
                    SavedState savedState = new SavedState(parcelableOnSaveInstanceState);
                    savedState.b = i;
                    savedState.d = bottom == appBarLayout.getTopInset() + ViewCompat.getMinimumHeight(childAt);
                    savedState.c = bottom / childAt.getHeight();
                    return savedState;
                }
            }
            return parcelableOnSaveInstanceState;
        }

        @Override // android.support.design.widget.CoordinatorLayout.Behavior
        public void onStopNestedScroll(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, View view2, int i) {
            if (i == 0) {
                a(coordinatorLayout, appBarLayout);
            }
            this.p = new WeakReference<>(view2);
        }

        public Behavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.m = -1;
        }

        public static class SavedState extends AbsSavedState {
            public static final Parcelable.Creator<SavedState> CREATOR = new a();
            public int b;
            public float c;
            public boolean d;

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
                this.b = parcel.readInt();
                this.c = parcel.readFloat();
                this.d = parcel.readByte() != 0;
            }

            @Override // android.support.v4.view.AbsSavedState, android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                super.writeToParcel(parcel, i);
                parcel.writeInt(this.b);
                parcel.writeFloat(this.c);
                parcel.writeByte(this.d ? (byte) 1 : (byte) 0);
            }

            public SavedState(Parcelable parcelable) {
                super(parcelable);
            }
        }

        public final void a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout, int i, float f) {
            int height;
            int iAbs = Math.abs(a() - i);
            float fAbs = Math.abs(f);
            if (fAbs > 0.0f) {
                height = Math.round((iAbs / fAbs) * 1000.0f) * 3;
            } else {
                height = (int) (((iAbs / appBarLayout.getHeight()) + 1.0f) * 150.0f);
            }
            int iA = a();
            if (iA == i) {
                ValueAnimator valueAnimator = this.l;
                if (valueAnimator == null || !valueAnimator.isRunning()) {
                    return;
                }
                this.l.cancel();
                return;
            }
            ValueAnimator valueAnimator2 = this.l;
            if (valueAnimator2 == null) {
                ValueAnimator valueAnimator3 = new ValueAnimator();
                this.l = valueAnimator3;
                valueAnimator3.setInterpolator(q1.e);
                this.l.addUpdateListener(new r1(this, coordinatorLayout, appBarLayout));
            } else {
                valueAnimator2.cancel();
            }
            this.l.setDuration(Math.min(height, Limits.TURBO_MAX_SECONDS));
            this.l.setIntValues(iA, i);
            this.l.start();
        }

        public final void a(CoordinatorLayout coordinatorLayout, AppBarLayout appBarLayout) {
            int iA = a();
            int childCount = appBarLayout.getChildCount();
            int i = 0;
            while (true) {
                if (i >= childCount) {
                    i = -1;
                    break;
                }
                View childAt = appBarLayout.getChildAt(i);
                int i2 = -iA;
                if (childAt.getTop() <= i2 && childAt.getBottom() >= i2) {
                    break;
                } else {
                    i++;
                }
            }
            if (i >= 0) {
                View childAt2 = appBarLayout.getChildAt(i);
                int scrollFlags = ((LayoutParams) childAt2.getLayoutParams()).getScrollFlags();
                if ((scrollFlags & 17) == 17) {
                    int i3 = -childAt2.getTop();
                    int minimumHeight = -childAt2.getBottom();
                    if (i == appBarLayout.getChildCount() - 1) {
                        minimumHeight += appBarLayout.getTopInset();
                    }
                    if ((scrollFlags & 2) == 2) {
                        minimumHeight += ViewCompat.getMinimumHeight(childAt2);
                    } else {
                        if ((scrollFlags & 5) == 5) {
                            int minimumHeight2 = ViewCompat.getMinimumHeight(childAt2) + minimumHeight;
                            if (iA < minimumHeight2) {
                                i3 = minimumHeight2;
                            } else {
                                minimumHeight = minimumHeight2;
                            }
                        }
                    }
                    if (iA < (minimumHeight + i3) / 2) {
                        i3 = minimumHeight;
                    }
                    a(coordinatorLayout, appBarLayout, MathUtils.clamp(i3, -appBarLayout.getTotalScrollRange(), 0), 0.0f);
                }
            }
        }

        /* JADX WARN: Removed duplicated region for block: B:21:0x004b  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void a(android.support.design.widget.CoordinatorLayout r7, android.support.design.widget.AppBarLayout r8, int r9, int r10, boolean r11) {
            /*
                r6 = this;
                int r0 = java.lang.Math.abs(r9)
                int r1 = r8.getChildCount()
                r2 = 0
                r3 = r2
            La:
                if (r3 >= r1) goto L20
                android.view.View r4 = r8.getChildAt(r3)
                int r5 = r4.getTop()
                if (r0 < r5) goto L1d
                int r5 = r4.getBottom()
                if (r0 > r5) goto L1d
                goto L21
            L1d:
                int r3 = r3 + 1
                goto La
            L20:
                r4 = 0
            L21:
                if (r4 == 0) goto La0
                android.view.ViewGroup$LayoutParams r0 = r4.getLayoutParams()
                android.support.design.widget.AppBarLayout$LayoutParams r0 = (android.support.design.widget.AppBarLayout.LayoutParams) r0
                int r0 = r0.getScrollFlags()
                r1 = r0 & 1
                r3 = 1
                if (r1 == 0) goto L4b
                int r1 = android.support.v4.view.ViewCompat.getMinimumHeight(r4)
                if (r10 <= 0) goto L4d
                r10 = r0 & 12
                if (r10 == 0) goto L4d
                int r9 = -r9
                int r10 = r4.getBottom()
                int r10 = r10 - r1
                int r0 = r8.getTopInset()
                int r10 = r10 - r0
                if (r9 < r10) goto L4b
            L49:
                r9 = r3
                goto L5f
            L4b:
                r9 = r2
                goto L5f
            L4d:
                r10 = r0 & 2
                if (r10 == 0) goto L4b
                int r9 = -r9
                int r10 = r4.getBottom()
                int r10 = r10 - r1
                int r0 = r8.getTopInset()
                int r10 = r10 - r0
                if (r9 < r10) goto L4b
                goto L49
            L5f:
                boolean r10 = r8.i
                if (r10 == r9) goto L6a
                r8.i = r9
                r8.refreshDrawableState()
                r9 = r3
                goto L6b
            L6a:
                r9 = r2
            L6b:
                if (r11 != 0) goto L9d
                if (r9 == 0) goto La0
                java.util.List r7 = r7.getDependents(r8)
                int r9 = r7.size()
                r10 = r2
            L78:
                if (r10 >= r9) goto L9b
                java.lang.Object r11 = r7.get(r10)
                android.view.View r11 = (android.view.View) r11
                android.view.ViewGroup$LayoutParams r11 = r11.getLayoutParams()
                android.support.design.widget.CoordinatorLayout$LayoutParams r11 = (android.support.design.widget.CoordinatorLayout.LayoutParams) r11
                android.support.design.widget.CoordinatorLayout$Behavior r11 = r11.getBehavior()
                boolean r0 = r11 instanceof android.support.design.widget.AppBarLayout.ScrollingViewBehavior
                if (r0 == 0) goto L98
                android.support.design.widget.AppBarLayout$ScrollingViewBehavior r11 = (android.support.design.widget.AppBarLayout.ScrollingViewBehavior) r11
                int r7 = r11.getOverlayTop()
                if (r7 == 0) goto L9b
                r2 = r3
                goto L9b
            L98:
                int r10 = r10 + 1
                goto L78
            L9b:
                if (r2 == 0) goto La0
            L9d:
                r8.jumpDrawablesToCurrentState()
            La0:
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.design.widget.AppBarLayout.Behavior.a(android.support.design.widget.CoordinatorLayout, android.support.design.widget.AppBarLayout, int, int, boolean):void");
        }

        @Override // android.support.design.widget.HeaderBehavior
        public int a() {
            return getTopAndBottomOffset() + this.k;
        }
    }

    public interface OnOffsetChangedListener {
        void onOffsetChanged(AppBarLayout appBarLayout, int i);
    }

    public static class ScrollingViewBehavior extends HeaderScrollingViewBehavior {
        public ScrollingViewBehavior() {
        }

        public AppBarLayout a(List<View> list) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                View view2 = list.get(i);
                if (view2 instanceof AppBarLayout) {
                    return (AppBarLayout) view2;
                }
            }
            return null;
        }

        @Override // android.support.design.widget.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getLeftAndRightOffset() {
            return super.getLeftAndRightOffset();
        }

        @Override // android.support.design.widget.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ int getTopAndBottomOffset() {
            return super.getTopAndBottomOffset();
        }

        @Override // android.support.design.widget.CoordinatorLayout.Behavior
        public boolean layoutDependsOn(CoordinatorLayout coordinatorLayout, View view2, View view3) {
            return view3 instanceof AppBarLayout;
        }

        @Override // android.support.design.widget.CoordinatorLayout.Behavior
        public boolean onDependentViewChanged(CoordinatorLayout coordinatorLayout, View view2, View view3) {
            CoordinatorLayout.Behavior behavior = ((CoordinatorLayout.LayoutParams) view3.getLayoutParams()).getBehavior();
            if (!(behavior instanceof Behavior)) {
                return false;
            }
            ViewCompat.offsetTopAndBottom(view2, (((view3.getBottom() - view2.getTop()) + ((Behavior) behavior).k) + this.f) - a(view3));
            return false;
        }

        @Override // android.support.design.widget.ViewOffsetBehavior, android.support.design.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onLayoutChild(CoordinatorLayout coordinatorLayout, View view2, int i) {
            return super.onLayoutChild(coordinatorLayout, view2, i);
        }

        @Override // android.support.design.widget.HeaderScrollingViewBehavior, android.support.design.widget.CoordinatorLayout.Behavior
        public /* bridge */ /* synthetic */ boolean onMeasureChild(CoordinatorLayout coordinatorLayout, View view2, int i, int i2, int i3, int i4) {
            return super.onMeasureChild(coordinatorLayout, view2, i, i2, i3, i4);
        }

        @Override // android.support.design.widget.CoordinatorLayout.Behavior
        public boolean onRequestChildRectangleOnScreen(CoordinatorLayout coordinatorLayout, View view2, Rect rect, boolean z) {
            AppBarLayout appBarLayoutA = a(coordinatorLayout.getDependencies(view2));
            if (appBarLayoutA != null) {
                rect.offset(view2.getLeft(), view2.getTop());
                Rect rect2 = this.d;
                rect2.set(0, 0, coordinatorLayout.getWidth(), coordinatorLayout.getHeight());
                if (!rect2.contains(rect)) {
                    appBarLayoutA.setExpanded(false, !z);
                    return true;
                }
            }
            return false;
        }

        @Override // android.support.design.widget.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setLeftAndRightOffset(int i) {
            return super.setLeftAndRightOffset(i);
        }

        @Override // android.support.design.widget.ViewOffsetBehavior
        public /* bridge */ /* synthetic */ boolean setTopAndBottomOffset(int i) {
            return super.setTopAndBottomOffset(i);
        }

        public ScrollingViewBehavior(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ScrollingViewBehavior_Layout);
            setOverlayTop(typedArrayObtainStyledAttributes.getDimensionPixelSize(R.styleable.ScrollingViewBehavior_Layout_behavior_overlapTop, 0));
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public class a implements OnApplyWindowInsetsListener {
        public a() {
        }

        @Override // android.support.v4.view.OnApplyWindowInsetsListener
        public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) {
            AppBarLayout appBarLayout = AppBarLayout.this;
            if (appBarLayout == null) {
                throw null;
            }
            WindowInsetsCompat windowInsetsCompat2 = ViewCompat.getFitsSystemWindows(appBarLayout) ? windowInsetsCompat : null;
            if (!ObjectsCompat.equals(appBarLayout.f, windowInsetsCompat2)) {
                appBarLayout.f = windowInsetsCompat2;
                appBarLayout.a();
            }
            return windowInsetsCompat;
        }
    }

    public AppBarLayout(Context context) {
        this(context, null);
    }

    public final void a() {
        this.a = -1;
        this.b = -1;
        this.c = -1;
    }

    public void addOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        if (this.g == null) {
            this.g = new ArrayList();
        }
        if (onOffsetChangedListener == null || this.g.contains(onOffsetChangedListener)) {
            return;
        }
        this.g.add(onOffsetChangedListener);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
        return layoutParams instanceof LayoutParams;
    }

    public int getDownNestedPreScrollRange() {
        int i = this.b;
        if (i != -1) {
            return i;
        }
        int minimumHeight = 0;
        for (int childCount = getChildCount() - 1; childCount >= 0; childCount--) {
            View childAt = getChildAt(childCount);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i2 = layoutParams.a;
            if ((i2 & 5) != 5) {
                if (minimumHeight > 0) {
                    break;
                }
            } else {
                int i3 = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + minimumHeight;
                minimumHeight = (i2 & 8) != 0 ? ViewCompat.getMinimumHeight(childAt) + i3 : (measuredHeight - ((i2 & 2) != 0 ? ViewCompat.getMinimumHeight(childAt) : getTopInset())) + i3;
            }
        }
        int iMax = Math.max(0, minimumHeight);
        this.b = iMax;
        return iMax;
    }

    public int getDownNestedScrollRange() {
        int i = this.c;
        if (i != -1) {
            return i;
        }
        int childCount = getChildCount();
        int i2 = 0;
        int topInset = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin + childAt.getMeasuredHeight();
            int i3 = layoutParams.a;
            if ((i3 & 1) == 0) {
                break;
            }
            topInset += measuredHeight;
            if ((i3 & 2) != 0) {
                topInset -= getTopInset() + ViewCompat.getMinimumHeight(childAt);
                break;
            }
            i2++;
        }
        int iMax = Math.max(0, topInset);
        this.c = iMax;
        return iMax;
    }

    public final int getMinimumHeightForVisibleOverlappingContent() {
        int topInset = getTopInset();
        int minimumHeight = ViewCompat.getMinimumHeight(this);
        if (minimumHeight == 0) {
            int childCount = getChildCount();
            minimumHeight = childCount >= 1 ? ViewCompat.getMinimumHeight(getChildAt(childCount - 1)) : 0;
            if (minimumHeight == 0) {
                return getHeight() / 3;
            }
        }
        return (minimumHeight * 2) + topInset;
    }

    public int getPendingAction() {
        return this.e;
    }

    @Deprecated
    public float getTargetElevation() {
        return 0.0f;
    }

    @VisibleForTesting
    public final int getTopInset() {
        WindowInsetsCompat windowInsetsCompat = this.f;
        if (windowInsetsCompat != null) {
            return windowInsetsCompat.getSystemWindowInsetTop();
        }
        return 0;
    }

    public final int getTotalScrollRange() {
        int i = this.a;
        if (i != -1) {
            return i;
        }
        int childCount = getChildCount();
        int i2 = 0;
        int minimumHeight = 0;
        while (true) {
            if (i2 >= childCount) {
                break;
            }
            View childAt = getChildAt(i2);
            LayoutParams layoutParams = (LayoutParams) childAt.getLayoutParams();
            int measuredHeight = childAt.getMeasuredHeight();
            int i3 = layoutParams.a;
            if ((i3 & 1) == 0) {
                break;
            }
            minimumHeight += measuredHeight + ((LinearLayout.LayoutParams) layoutParams).topMargin + ((LinearLayout.LayoutParams) layoutParams).bottomMargin;
            if ((i3 & 2) != 0) {
                minimumHeight -= ViewCompat.getMinimumHeight(childAt);
                break;
            }
            i2++;
        }
        int iMax = Math.max(0, minimumHeight - getTopInset());
        this.a = iMax;
        return iMax;
    }

    public int getUpNestedPreScrollRange() {
        return getTotalScrollRange();
    }

    @Override // android.view.ViewGroup, android.view.View
    public int[] onCreateDrawableState(int i) {
        if (this.j == null) {
            this.j = new int[2];
        }
        int[] iArr = this.j;
        int[] iArrOnCreateDrawableState = super.onCreateDrawableState(i + iArr.length);
        iArr[0] = this.h ? R.attr.state_collapsible : -R.attr.state_collapsible;
        iArr[1] = (this.h && this.i) ? R.attr.state_collapsed : -R.attr.state_collapsed;
        return LinearLayout.mergeDrawableStates(iArrOnCreateDrawableState, iArr);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        a();
        boolean z2 = false;
        this.d = false;
        int childCount = getChildCount();
        int i5 = 0;
        while (true) {
            if (i5 >= childCount) {
                break;
            }
            if (((LayoutParams) getChildAt(i5).getLayoutParams()).getScrollInterpolator() != null) {
                this.d = true;
                break;
            }
            i5++;
        }
        int childCount2 = getChildCount();
        int i6 = 0;
        while (true) {
            if (i6 >= childCount2) {
                break;
            }
            int i7 = ((LayoutParams) getChildAt(i6).getLayoutParams()).a;
            if ((i7 & 1) == 1 && (i7 & 10) != 0) {
                z2 = true;
                break;
            }
            i6++;
        }
        if (this.h != z2) {
            this.h = z2;
            refreshDrawableState();
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        a();
    }

    public void removeOnOffsetChangedListener(OnOffsetChangedListener onOffsetChangedListener) {
        List<OnOffsetChangedListener> list = this.g;
        if (list == null || onOffsetChangedListener == null) {
            return;
        }
        list.remove(onOffsetChangedListener);
    }

    public void setExpanded(boolean z) {
        setExpanded(z, ViewCompat.isLaidOut(this));
    }

    @Override // android.widget.LinearLayout
    public void setOrientation(int i) {
        if (i != 1) {
            throw new IllegalArgumentException("AppBarLayout is always vertical and does not support horizontal orientation");
        }
        super.setOrientation(i);
    }

    @Deprecated
    public void setTargetElevation(float f) throws Resources.NotFoundException {
        r2.a(this, f);
    }

    public AppBarLayout(Context context, AttributeSet attributeSet) throws Resources.NotFoundException {
        super(context, attributeSet);
        this.a = -1;
        this.b = -1;
        this.c = -1;
        this.e = 0;
        setOrientation(1);
        p2.a(context);
        setOutlineProvider(ViewOutlineProvider.BOUNDS);
        int i = R.style.Widget_Design_AppBarLayout;
        Context context2 = getContext();
        TypedArray typedArrayObtainStyledAttributes = context2.obtainStyledAttributes(attributeSet, r2.a, 0, i);
        try {
            if (typedArrayObtainStyledAttributes.hasValue(0)) {
                setStateListAnimator(AnimatorInflater.loadStateListAnimator(context2, typedArrayObtainStyledAttributes.getResourceId(0, 0)));
            }
            typedArrayObtainStyledAttributes.recycle();
            TypedArray typedArrayObtainStyledAttributes2 = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout, 0, R.style.Widget_Design_AppBarLayout);
            ViewCompat.setBackground(this, typedArrayObtainStyledAttributes2.getDrawable(R.styleable.AppBarLayout_android_background));
            if (typedArrayObtainStyledAttributes2.hasValue(R.styleable.AppBarLayout_expanded)) {
                a(typedArrayObtainStyledAttributes2.getBoolean(R.styleable.AppBarLayout_expanded, false), false, false);
            }
            if (typedArrayObtainStyledAttributes2.hasValue(R.styleable.AppBarLayout_elevation)) {
                r2.a(this, typedArrayObtainStyledAttributes2.getDimensionPixelSize(R.styleable.AppBarLayout_elevation, 0));
            }
            if (Build.VERSION.SDK_INT >= 26) {
                if (typedArrayObtainStyledAttributes2.hasValue(R.styleable.AppBarLayout_android_keyboardNavigationCluster)) {
                    setKeyboardNavigationCluster(typedArrayObtainStyledAttributes2.getBoolean(R.styleable.AppBarLayout_android_keyboardNavigationCluster, false));
                }
                if (typedArrayObtainStyledAttributes2.hasValue(R.styleable.AppBarLayout_android_touchscreenBlocksFocus)) {
                    setTouchscreenBlocksFocus(typedArrayObtainStyledAttributes2.getBoolean(R.styleable.AppBarLayout_android_touchscreenBlocksFocus, false));
                }
            }
            typedArrayObtainStyledAttributes2.recycle();
            ViewCompat.setOnApplyWindowInsetsListener(this, new a());
        } catch (Throwable th) {
            typedArrayObtainStyledAttributes.recycle();
            throw th;
        }
    }

    public void setExpanded(boolean z, boolean z2) {
        a(z, z2, true);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateDefaultLayoutParams() {
        return new LayoutParams(-1, -2);
    }

    public final void a(boolean z, boolean z2, boolean z3) {
        this.e = (z ? 1 : 2) | (z2 ? 4 : 0) | (z3 ? 8 : 0);
        requestLayout();
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(AttributeSet attributeSet) {
        return new LayoutParams(getContext(), attributeSet);
    }

    public void a(int i) {
        List<OnOffsetChangedListener> list = this.g;
        if (list != null) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                OnOffsetChangedListener onOffsetChangedListener = this.g.get(i2);
                if (onOffsetChangedListener != null) {
                    onOffsetChangedListener.onOffsetChanged(this, i);
                }
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup
    public LayoutParams generateLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof LinearLayout.LayoutParams) {
            return new LayoutParams((LinearLayout.LayoutParams) layoutParams);
        }
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            return new LayoutParams((ViewGroup.MarginLayoutParams) layoutParams);
        }
        return new LayoutParams(layoutParams);
    }

    public static class LayoutParams extends LinearLayout.LayoutParams {
        public static final int SCROLL_FLAG_ENTER_ALWAYS = 4;
        public static final int SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED = 8;
        public static final int SCROLL_FLAG_EXIT_UNTIL_COLLAPSED = 2;
        public static final int SCROLL_FLAG_SCROLL = 1;
        public static final int SCROLL_FLAG_SNAP = 16;
        public int a;
        public Interpolator b;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public @interface ScrollFlags {
        }

        public LayoutParams(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            this.a = 1;
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.AppBarLayout_Layout);
            this.a = typedArrayObtainStyledAttributes.getInt(R.styleable.AppBarLayout_Layout_layout_scrollFlags, 0);
            if (typedArrayObtainStyledAttributes.hasValue(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator)) {
                this.b = AnimationUtils.loadInterpolator(context, typedArrayObtainStyledAttributes.getResourceId(R.styleable.AppBarLayout_Layout_layout_scrollInterpolator, 0));
            }
            typedArrayObtainStyledAttributes.recycle();
        }

        public int getScrollFlags() {
            return this.a;
        }

        public Interpolator getScrollInterpolator() {
            return this.b;
        }

        public void setScrollFlags(int i) {
            this.a = i;
        }

        public void setScrollInterpolator(Interpolator interpolator) {
            this.b = interpolator;
        }

        public LayoutParams(int i, int i2) {
            super(i, i2);
            this.a = 1;
        }

        public LayoutParams(int i, int i2, float f) {
            super(i, i2, f);
            this.a = 1;
        }

        public LayoutParams(ViewGroup.LayoutParams layoutParams) {
            super(layoutParams);
            this.a = 1;
        }

        public LayoutParams(ViewGroup.MarginLayoutParams marginLayoutParams) {
            super(marginLayoutParams);
            this.a = 1;
        }

        @RequiresApi(19)
        public LayoutParams(LinearLayout.LayoutParams layoutParams) {
            super(layoutParams);
            this.a = 1;
        }

        @RequiresApi(19)
        public LayoutParams(LayoutParams layoutParams) {
            super((LinearLayout.LayoutParams) layoutParams);
            this.a = 1;
            this.a = layoutParams.a;
            this.b = layoutParams.b;
        }
    }
}
