package android.support.v7.widget.helper;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.recyclerview.R;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import defpackage.a9;
import defpackage.b9;
import defpackage.g9;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ItemTouchHelper extends RecyclerView.ItemDecoration implements RecyclerView.OnChildAttachStateChangeListener {
    public static final int ACTION_STATE_DRAG = 2;
    public static final int ACTION_STATE_IDLE = 0;
    public static final int ACTION_STATE_SWIPE = 1;
    public static final int ANIMATION_TYPE_DRAG = 8;
    public static final int ANIMATION_TYPE_SWIPE_CANCEL = 4;
    public static final int ANIMATION_TYPE_SWIPE_SUCCESS = 2;
    public static final int DOWN = 2;
    public static final int END = 32;
    public static final int LEFT = 4;
    public static final int RIGHT = 8;
    public static final int START = 16;
    public static final int UP = 1;
    public d A;
    public Rect C;
    public long D;
    public float d;
    public float e;
    public float f;
    public float g;
    public float h;
    public float i;
    public float j;
    public float k;
    public Callback m;
    public int o;
    public int q;
    public RecyclerView r;
    public VelocityTracker t;
    public List<RecyclerView.ViewHolder> u;
    public List<Integer> v;
    public GestureDetectorCompat z;
    public final List<View> a = new ArrayList();
    public final float[] b = new float[2];
    public RecyclerView.ViewHolder c = null;
    public int l = -1;
    public int n = 0;
    public List<e> p = new ArrayList();
    public final Runnable s = new a();
    public RecyclerView.ChildDrawingOrderCallback w = null;
    public View x = null;
    public int y = -1;
    public final RecyclerView.OnItemTouchListener B = new b();

    public static abstract class Callback {
        public static final int DEFAULT_DRAG_ANIMATION_DURATION = 200;
        public static final int DEFAULT_SWIPE_ANIMATION_DURATION = 250;
        public int a = -1;
        public static final Interpolator c = new a();
        public static final Interpolator d = new b();
        public static final ItemTouchUIUtil b = new b9();

        public static class a implements Interpolator {
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float f) {
                return f * f * f * f * f;
            }
        }

        public static class b implements Interpolator {
            @Override // android.animation.TimeInterpolator
            public float getInterpolation(float f) {
                float f2 = f - 1.0f;
                return (f2 * f2 * f2 * f2 * f2) + 1.0f;
            }
        }

        public static int convertToRelativeDirection(int i, int i2) {
            int i3;
            int i4 = i & 789516;
            if (i4 == 0) {
                return i;
            }
            int i5 = i & (~i4);
            if (i2 == 0) {
                i3 = i4 << 2;
            } else {
                int i6 = i4 << 1;
                i5 |= (-789517) & i6;
                i3 = (i6 & 789516) << 2;
            }
            return i5 | i3;
        }

        public static ItemTouchUIUtil getDefaultUIUtil() {
            return b;
        }

        public static int makeFlag(int i, int i2) {
            return i2 << (i * 8);
        }

        public static int makeMovementFlags(int i, int i2) {
            return makeFlag(2, i) | makeFlag(1, i2) | makeFlag(0, i2 | i);
        }

        public final int a(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return convertToAbsoluteDirection(getMovementFlags(recyclerView, viewHolder), ViewCompat.getLayoutDirection(recyclerView));
        }

        public boolean b(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return (a(recyclerView, viewHolder) & 16711680) != 0;
        }

        public boolean c(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return (a(recyclerView, viewHolder) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) != 0;
        }

        public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
            return true;
        }

        public RecyclerView.ViewHolder chooseDropTarget(RecyclerView.ViewHolder viewHolder, List<RecyclerView.ViewHolder> list, int i, int i2) {
            int bottom;
            int iAbs;
            int top;
            int iAbs2;
            int left;
            int iAbs3;
            int right;
            int iAbs4;
            int width = viewHolder.itemView.getWidth() + i;
            int height = viewHolder.itemView.getHeight() + i2;
            int left2 = i - viewHolder.itemView.getLeft();
            int top2 = i2 - viewHolder.itemView.getTop();
            int size = list.size();
            RecyclerView.ViewHolder viewHolder2 = null;
            int i3 = -1;
            for (int i4 = 0; i4 < size; i4++) {
                RecyclerView.ViewHolder viewHolder3 = list.get(i4);
                if (left2 > 0 && (right = viewHolder3.itemView.getRight() - width) < 0 && viewHolder3.itemView.getRight() > viewHolder.itemView.getRight() && (iAbs4 = Math.abs(right)) > i3) {
                    viewHolder2 = viewHolder3;
                    i3 = iAbs4;
                }
                if (left2 < 0 && (left = viewHolder3.itemView.getLeft() - i) > 0 && viewHolder3.itemView.getLeft() < viewHolder.itemView.getLeft() && (iAbs3 = Math.abs(left)) > i3) {
                    viewHolder2 = viewHolder3;
                    i3 = iAbs3;
                }
                if (top2 < 0 && (top = viewHolder3.itemView.getTop() - i2) > 0 && viewHolder3.itemView.getTop() < viewHolder.itemView.getTop() && (iAbs2 = Math.abs(top)) > i3) {
                    viewHolder2 = viewHolder3;
                    i3 = iAbs2;
                }
                if (top2 > 0 && (bottom = viewHolder3.itemView.getBottom() - height) < 0 && viewHolder3.itemView.getBottom() > viewHolder.itemView.getBottom() && (iAbs = Math.abs(bottom)) > i3) {
                    viewHolder2 = viewHolder3;
                    i3 = iAbs;
                }
            }
            return viewHolder2;
        }

        public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            b.clearView(viewHolder.itemView);
        }

        public int convertToAbsoluteDirection(int i, int i2) {
            int i3;
            int i4 = i & 3158064;
            if (i4 == 0) {
                return i;
            }
            int i5 = i & (~i4);
            if (i2 == 0) {
                i3 = i4 >> 2;
            } else {
                int i6 = i4 >> 1;
                i5 |= (-3158065) & i6;
                i3 = (i6 & 3158064) >> 2;
            }
            return i5 | i3;
        }

        public long getAnimationDuration(RecyclerView recyclerView, int i, float f, float f2) {
            RecyclerView.ItemAnimator itemAnimator = recyclerView.getItemAnimator();
            return itemAnimator == null ? i == 8 ? 200L : 250L : i == 8 ? itemAnimator.getMoveDuration() : itemAnimator.getRemoveDuration();
        }

        public int getBoundingBoxMargin() {
            return 0;
        }

        public float getMoveThreshold(RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        public abstract int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder);

        public float getSwipeEscapeVelocity(float f) {
            return f;
        }

        public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
            return 0.5f;
        }

        public float getSwipeVelocityThreshold(float f) {
            return f;
        }

        public int interpolateOutOfBoundsScroll(RecyclerView recyclerView, int i, int i2, int i3, long j) {
            if (this.a == -1) {
                this.a = recyclerView.getResources().getDimensionPixelSize(R.dimen.item_touch_helper_max_drag_scroll_per_frame);
            }
            int interpolation = (int) (c.getInterpolation(j <= 2000 ? j / 2000.0f : 1.0f) * ((int) (d.getInterpolation(Math.min(1.0f, (Math.abs(i2) * 1.0f) / i)) * ((int) Math.signum(i2)) * this.a)));
            return interpolation == 0 ? i2 > 0 ? 1 : -1 : interpolation;
        }

        public boolean isItemViewSwipeEnabled() {
            return true;
        }

        public boolean isLongPressDragEnabled() {
            return true;
        }

        public void onChildDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            b.onDraw(canvas, recyclerView, viewHolder.itemView, f, f2, i, z);
        }

        public void onChildDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float f, float f2, int i, boolean z) {
            b.onDrawOver(canvas, recyclerView, viewHolder.itemView, f, f2, i, z);
        }

        public abstract boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2);

        /* JADX WARN: Multi-variable type inference failed */
        public void onMoved(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, int i, RecyclerView.ViewHolder viewHolder2, int i2, int i3, int i4) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof ViewDropHandler) {
                ((ViewDropHandler) layoutManager).prepareForDrop(viewHolder.itemView, viewHolder2.itemView, i3, i4);
                return;
            }
            if (layoutManager.canScrollHorizontally()) {
                if (layoutManager.getDecoratedLeft(viewHolder2.itemView) <= recyclerView.getPaddingLeft()) {
                    recyclerView.scrollToPosition(i2);
                }
                if (layoutManager.getDecoratedRight(viewHolder2.itemView) >= recyclerView.getWidth() - recyclerView.getPaddingRight()) {
                    recyclerView.scrollToPosition(i2);
                }
            }
            if (layoutManager.canScrollVertically()) {
                if (layoutManager.getDecoratedTop(viewHolder2.itemView) <= recyclerView.getPaddingTop()) {
                    recyclerView.scrollToPosition(i2);
                }
                if (layoutManager.getDecoratedBottom(viewHolder2.itemView) >= recyclerView.getHeight() - recyclerView.getPaddingBottom()) {
                    recyclerView.scrollToPosition(i2);
                }
            }
        }

        public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int i) {
            if (viewHolder != null) {
                b.onSelected(viewHolder.itemView);
            }
        }

        public abstract void onSwiped(RecyclerView.ViewHolder viewHolder, int i);

        public void b(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, List<e> list, int i, float f, float f2) {
            int size = list.size();
            boolean z = false;
            for (int i2 = 0; i2 < size; i2++) {
                e eVar = list.get(i2);
                int iSave = canvas.save();
                onChildDrawOver(canvas, recyclerView, eVar.e, eVar.j, eVar.k, eVar.f, false);
                canvas.restoreToCount(iSave);
            }
            if (viewHolder != null) {
                int iSave2 = canvas.save();
                onChildDrawOver(canvas, recyclerView, viewHolder, f, f2, i, true);
                canvas.restoreToCount(iSave2);
            }
            for (int i3 = size - 1; i3 >= 0; i3--) {
                e eVar2 = list.get(i3);
                if (eVar2.m && !eVar2.i) {
                    list.remove(i3);
                } else if (!eVar2.m) {
                    z = true;
                }
            }
            if (z) {
                recyclerView.invalidate();
            }
        }

        public void a(Canvas canvas, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, List<e> list, int i, float f, float f2) {
            int size = list.size();
            for (int i2 = 0; i2 < size; i2++) {
                e eVar = list.get(i2);
                float f3 = eVar.a;
                float f4 = eVar.c;
                if (f3 == f4) {
                    eVar.j = eVar.e.itemView.getTranslationX();
                } else {
                    eVar.j = g9.a(f4, f3, eVar.n, f3);
                }
                float f5 = eVar.b;
                float f6 = eVar.d;
                if (f5 == f6) {
                    eVar.k = eVar.e.itemView.getTranslationY();
                } else {
                    eVar.k = g9.a(f6, f5, eVar.n, f5);
                }
                int iSave = canvas.save();
                onChildDraw(canvas, recyclerView, eVar.e, eVar.j, eVar.k, eVar.f, false);
                canvas.restoreToCount(iSave);
            }
            if (viewHolder != null) {
                int iSave2 = canvas.save();
                onChildDraw(canvas, recyclerView, viewHolder, f, f2, i, true);
                canvas.restoreToCount(iSave2);
            }
        }
    }

    public static abstract class SimpleCallback extends Callback {
        public int e;
        public int f;

        public SimpleCallback(int i, int i2) {
            this.e = i2;
            this.f = i;
        }

        public int getDragDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return this.f;
        }

        @Override // android.support.v7.widget.helper.ItemTouchHelper.Callback
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return Callback.makeMovementFlags(getDragDirs(recyclerView, viewHolder), getSwipeDirs(recyclerView, viewHolder));
        }

        public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            return this.e;
        }

        public void setDefaultDragDirs(int i) {
            this.f = i;
        }

        public void setDefaultSwipeDirs(int i) {
            this.e = i;
        }
    }

    public interface ViewDropHandler {
        void prepareForDrop(View view2, View view3, int i, int i2);
    }

    public class a implements Runnable {
        public a() {
        }

        /* JADX WARN: Removed duplicated region for block: B:25:0x0083  */
        /* JADX WARN: Removed duplicated region for block: B:37:0x00cc  */
        @Override // java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public void run() {
            /*
                Method dump skipped, instructions count: 312
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.helper.ItemTouchHelper.a.run():void");
        }
    }

    public class b implements RecyclerView.OnItemTouchListener {
        public b() {
        }

        @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
        public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            int iFindPointerIndex;
            ItemTouchHelper.this.z.onTouchEvent(motionEvent);
            int actionMasked = motionEvent.getActionMasked();
            e eVar = null;
            if (actionMasked == 0) {
                ItemTouchHelper.this.l = motionEvent.getPointerId(0);
                ItemTouchHelper.this.d = motionEvent.getX();
                ItemTouchHelper.this.e = motionEvent.getY();
                ItemTouchHelper.this.a();
                ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                if (itemTouchHelper.c == null) {
                    if (!itemTouchHelper.p.isEmpty()) {
                        View viewA = itemTouchHelper.a(motionEvent);
                        int size = itemTouchHelper.p.size() - 1;
                        while (true) {
                            if (size < 0) {
                                break;
                            }
                            e eVar2 = itemTouchHelper.p.get(size);
                            if (eVar2.e.itemView == viewA) {
                                eVar = eVar2;
                                break;
                            }
                            size--;
                        }
                    }
                    if (eVar != null) {
                        ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                        itemTouchHelper2.d -= eVar.j;
                        itemTouchHelper2.e -= eVar.k;
                        itemTouchHelper2.a(eVar.e, true);
                        if (ItemTouchHelper.this.a.remove(eVar.e.itemView)) {
                            ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
                            itemTouchHelper3.m.clearView(itemTouchHelper3.r, eVar.e);
                        }
                        ItemTouchHelper.this.c(eVar.e, eVar.f);
                        ItemTouchHelper itemTouchHelper4 = ItemTouchHelper.this;
                        itemTouchHelper4.a(motionEvent, itemTouchHelper4.o, 0);
                    }
                }
            } else if (actionMasked == 3 || actionMasked == 1) {
                ItemTouchHelper itemTouchHelper5 = ItemTouchHelper.this;
                itemTouchHelper5.l = -1;
                itemTouchHelper5.c(null, 0);
            } else {
                int i = ItemTouchHelper.this.l;
                if (i != -1 && (iFindPointerIndex = motionEvent.findPointerIndex(i)) >= 0) {
                    ItemTouchHelper.this.a(actionMasked, motionEvent, iFindPointerIndex);
                }
            }
            VelocityTracker velocityTracker = ItemTouchHelper.this.t;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEvent);
            }
            return ItemTouchHelper.this.c != null;
        }

        @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
        public void onRequestDisallowInterceptTouchEvent(boolean z) {
            if (z) {
                ItemTouchHelper.this.c(null, 0);
            }
        }

        @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
        public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
            ItemTouchHelper.this.z.onTouchEvent(motionEvent);
            VelocityTracker velocityTracker = ItemTouchHelper.this.t;
            if (velocityTracker != null) {
                velocityTracker.addMovement(motionEvent);
            }
            if (ItemTouchHelper.this.l == -1) {
                return;
            }
            int actionMasked = motionEvent.getActionMasked();
            int iFindPointerIndex = motionEvent.findPointerIndex(ItemTouchHelper.this.l);
            if (iFindPointerIndex >= 0) {
                ItemTouchHelper.this.a(actionMasked, motionEvent, iFindPointerIndex);
            }
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            RecyclerView.ViewHolder viewHolder = itemTouchHelper.c;
            if (viewHolder == null) {
                return;
            }
            if (actionMasked != 1) {
                if (actionMasked == 2) {
                    if (iFindPointerIndex >= 0) {
                        itemTouchHelper.a(motionEvent, itemTouchHelper.o, iFindPointerIndex);
                        ItemTouchHelper.this.a(viewHolder);
                        ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                        itemTouchHelper2.r.removeCallbacks(itemTouchHelper2.s);
                        ItemTouchHelper.this.s.run();
                        ItemTouchHelper.this.r.invalidate();
                        return;
                    }
                    return;
                }
                if (actionMasked != 3) {
                    if (actionMasked != 6) {
                        return;
                    }
                    int actionIndex = motionEvent.getActionIndex();
                    if (motionEvent.getPointerId(actionIndex) == ItemTouchHelper.this.l) {
                        ItemTouchHelper.this.l = motionEvent.getPointerId(actionIndex == 0 ? 1 : 0);
                        ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
                        itemTouchHelper3.a(motionEvent, itemTouchHelper3.o, actionIndex);
                        return;
                    }
                    return;
                }
                VelocityTracker velocityTracker2 = itemTouchHelper.t;
                if (velocityTracker2 != null) {
                    velocityTracker2.clear();
                }
            }
            ItemTouchHelper.this.c(null, 0);
            ItemTouchHelper.this.l = -1;
        }
    }

    public class c extends e {
        public final /* synthetic */ int o;
        public final /* synthetic */ RecyclerView.ViewHolder p;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public c(RecyclerView.ViewHolder viewHolder, int i, int i2, float f, float f2, float f3, float f4, int i3, RecyclerView.ViewHolder viewHolder2) {
            super(viewHolder, i, i2, f, f2, f3, f4);
            this.o = i3;
            this.p = viewHolder2;
        }

        @Override // android.support.v7.widget.helper.ItemTouchHelper.e, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (!this.m) {
                this.e.setIsRecyclable(true);
            }
            this.m = true;
            if (this.l) {
                return;
            }
            if (this.o <= 0) {
                ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
                itemTouchHelper.m.clearView(itemTouchHelper.r, this.p);
            } else {
                ItemTouchHelper.this.a.add(this.p.itemView);
                this.i = true;
                int i = this.o;
                if (i > 0) {
                    ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                    itemTouchHelper2.r.post(new a9(itemTouchHelper2, this, i));
                }
            }
            ItemTouchHelper itemTouchHelper3 = ItemTouchHelper.this;
            View view2 = itemTouchHelper3.x;
            View view3 = this.p.itemView;
            if (view2 == view3) {
                itemTouchHelper3.a(view3);
            }
        }
    }

    public class d extends GestureDetector.SimpleOnGestureListener {
        public boolean a = true;

        public d() {
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            View viewA;
            RecyclerView.ViewHolder childViewHolder;
            if (!this.a || (viewA = ItemTouchHelper.this.a(motionEvent)) == null || (childViewHolder = ItemTouchHelper.this.r.getChildViewHolder(viewA)) == null) {
                return;
            }
            ItemTouchHelper itemTouchHelper = ItemTouchHelper.this;
            if (itemTouchHelper.m.b(itemTouchHelper.r, childViewHolder)) {
                int pointerId = motionEvent.getPointerId(0);
                int i = ItemTouchHelper.this.l;
                if (pointerId == i) {
                    int iFindPointerIndex = motionEvent.findPointerIndex(i);
                    float x = motionEvent.getX(iFindPointerIndex);
                    float y = motionEvent.getY(iFindPointerIndex);
                    ItemTouchHelper itemTouchHelper2 = ItemTouchHelper.this;
                    itemTouchHelper2.d = x;
                    itemTouchHelper2.e = y;
                    itemTouchHelper2.i = 0.0f;
                    itemTouchHelper2.h = 0.0f;
                    if (itemTouchHelper2.m.isLongPressDragEnabled()) {
                        ItemTouchHelper.this.c(childViewHolder, 2);
                    }
                }
            }
        }
    }

    public static class e implements Animator.AnimatorListener {
        public final float a;
        public final float b;
        public final float c;
        public final float d;
        public final RecyclerView.ViewHolder e;
        public final int f;
        public final ValueAnimator g;
        public final int h;
        public boolean i;
        public float j;
        public float k;
        public boolean l = false;
        public boolean m = false;
        public float n;

        public class a implements ValueAnimator.AnimatorUpdateListener {
            public a() {
            }

            @Override // android.animation.ValueAnimator.AnimatorUpdateListener
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                e.this.n = valueAnimator.getAnimatedFraction();
            }
        }

        public e(RecyclerView.ViewHolder viewHolder, int i, int i2, float f, float f2, float f3, float f4) {
            this.f = i2;
            this.h = i;
            this.e = viewHolder;
            this.a = f;
            this.b = f2;
            this.c = f3;
            this.d = f4;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            this.g = valueAnimatorOfFloat;
            valueAnimatorOfFloat.addUpdateListener(new a());
            this.g.setTarget(viewHolder.itemView);
            this.g.addListener(this);
            this.n = 0.0f;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            this.n = 1.0f;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (!this.m) {
                this.e.setIsRecyclable(true);
            }
            this.m = true;
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationRepeat(Animator animator) {
        }

        @Override // android.animation.Animator.AnimatorListener
        public void onAnimationStart(Animator animator) {
        }
    }

    public ItemTouchHelper(Callback callback) {
        this.m = callback;
    }

    public static boolean a(View view2, float f, float f2, float f3, float f4) {
        return f >= f3 && f <= f3 + ((float) view2.getWidth()) && f2 >= f4 && f2 <= f4 + ((float) view2.getHeight());
    }

    public void attachToRecyclerView(@Nullable RecyclerView recyclerView) {
        RecyclerView recyclerView2 = this.r;
        if (recyclerView2 == recyclerView) {
            return;
        }
        if (recyclerView2 != null) {
            recyclerView2.removeItemDecoration(this);
            this.r.removeOnItemTouchListener(this.B);
            this.r.removeOnChildAttachStateChangeListener(this);
            for (int size = this.p.size() - 1; size >= 0; size--) {
                this.m.clearView(this.r, this.p.get(0).e);
            }
            this.p.clear();
            this.x = null;
            this.y = -1;
            VelocityTracker velocityTracker = this.t;
            if (velocityTracker != null) {
                velocityTracker.recycle();
                this.t = null;
            }
            d dVar = this.A;
            if (dVar != null) {
                dVar.a = false;
                this.A = null;
            }
            if (this.z != null) {
                this.z = null;
            }
        }
        this.r = recyclerView;
        if (recyclerView != null) {
            Resources resources = recyclerView.getResources();
            this.f = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_velocity);
            this.g = resources.getDimension(R.dimen.item_touch_helper_swipe_escape_max_velocity);
            this.q = ViewConfiguration.get(this.r.getContext()).getScaledTouchSlop();
            this.r.addItemDecoration(this);
            this.r.addOnItemTouchListener(this.B);
            this.r.addOnChildAttachStateChangeListener(this);
            this.A = new d();
            this.z = new GestureDetectorCompat(this.r.getContext(), this.A);
        }
    }

    public final int b(RecyclerView.ViewHolder viewHolder, int i) {
        if ((i & 3) == 0) {
            return 0;
        }
        int i2 = this.i > 0.0f ? 2 : 1;
        VelocityTracker velocityTracker = this.t;
        if (velocityTracker != null && this.l > -1) {
            velocityTracker.computeCurrentVelocity(1000, this.m.getSwipeVelocityThreshold(this.g));
            float xVelocity = this.t.getXVelocity(this.l);
            float yVelocity = this.t.getYVelocity(this.l);
            int i3 = yVelocity <= 0.0f ? 1 : 2;
            float fAbs = Math.abs(yVelocity);
            if ((i3 & i) != 0 && i3 == i2 && fAbs >= this.m.getSwipeEscapeVelocity(this.f) && fAbs > Math.abs(xVelocity)) {
                return i3;
            }
        }
        float swipeThreshold = this.m.getSwipeThreshold(viewHolder) * this.r.getHeight();
        if ((i & i2) == 0 || Math.abs(this.i) <= swipeThreshold) {
            return 0;
        }
        return i2;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00a7  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void c(android.support.v7.widget.RecyclerView.ViewHolder r24, int r25) {
        /*
            Method dump skipped, instructions count: 441
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.helper.ItemTouchHelper.c(android.support.v7.widget.RecyclerView$ViewHolder, int):void");
    }

    @Override // android.support.v7.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view2, RecyclerView recyclerView, RecyclerView.State state) {
        rect.setEmpty();
    }

    @Override // android.support.v7.widget.RecyclerView.OnChildAttachStateChangeListener
    public void onChildViewAttachedToWindow(View view2) {
    }

    @Override // android.support.v7.widget.RecyclerView.OnChildAttachStateChangeListener
    public void onChildViewDetachedFromWindow(View view2) {
        a(view2);
        RecyclerView.ViewHolder childViewHolder = this.r.getChildViewHolder(view2);
        if (childViewHolder == null) {
            return;
        }
        RecyclerView.ViewHolder viewHolder = this.c;
        if (viewHolder != null && childViewHolder == viewHolder) {
            c(null, 0);
            return;
        }
        a(childViewHolder, false);
        if (this.a.remove(childViewHolder.itemView)) {
            this.m.clearView(this.r, childViewHolder);
        }
    }

    @Override // android.support.v7.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        float f;
        float f2;
        this.y = -1;
        if (this.c != null) {
            a(this.b);
            float[] fArr = this.b;
            float f3 = fArr[0];
            f2 = fArr[1];
            f = f3;
        } else {
            f = 0.0f;
            f2 = 0.0f;
        }
        this.m.a(canvas, recyclerView, this.c, this.p, this.n, f, f2);
    }

    @Override // android.support.v7.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        float f;
        float f2;
        if (this.c != null) {
            a(this.b);
            float[] fArr = this.b;
            float f3 = fArr[0];
            f2 = fArr[1];
            f = f3;
        } else {
            f = 0.0f;
            f2 = 0.0f;
        }
        this.m.b(canvas, recyclerView, this.c, this.p, this.n, f, f2);
    }

    public void startDrag(RecyclerView.ViewHolder viewHolder) {
        if (!this.m.b(this.r, viewHolder)) {
            Log.e("ItemTouchHelper", "Start drag has been called but dragging is not enabled");
            return;
        }
        if (viewHolder.itemView.getParent() != this.r) {
            Log.e("ItemTouchHelper", "Start drag has been called with a view holder which is not a child of the RecyclerView which is controlled by this ItemTouchHelper.");
            return;
        }
        a();
        this.i = 0.0f;
        this.h = 0.0f;
        c(viewHolder, 2);
    }

    public void startSwipe(RecyclerView.ViewHolder viewHolder) {
        if (!this.m.c(this.r, viewHolder)) {
            Log.e("ItemTouchHelper", "Start swipe has been called but swiping is not enabled");
            return;
        }
        if (viewHolder.itemView.getParent() != this.r) {
            Log.e("ItemTouchHelper", "Start swipe has been called with a view holder which is not a child of the RecyclerView controlled by this ItemTouchHelper.");
            return;
        }
        a();
        this.i = 0.0f;
        this.h = 0.0f;
        c(viewHolder, 1);
    }

    public final void a(float[] fArr) {
        if ((this.o & 12) != 0) {
            fArr[0] = (this.j + this.h) - this.c.itemView.getLeft();
        } else {
            fArr[0] = this.c.itemView.getTranslationX();
        }
        if ((this.o & 3) != 0) {
            fArr[1] = (this.k + this.i) - this.c.itemView.getTop();
        } else {
            fArr[1] = this.c.itemView.getTranslationY();
        }
    }

    public void a(RecyclerView.ViewHolder viewHolder) {
        int i;
        int i2;
        int i3;
        if (!this.r.isLayoutRequested() && this.n == 2) {
            float moveThreshold = this.m.getMoveThreshold(viewHolder);
            int i4 = (int) (this.j + this.h);
            int i5 = (int) (this.k + this.i);
            if (Math.abs(i5 - viewHolder.itemView.getTop()) >= viewHolder.itemView.getHeight() * moveThreshold || Math.abs(i4 - viewHolder.itemView.getLeft()) >= viewHolder.itemView.getWidth() * moveThreshold) {
                List<RecyclerView.ViewHolder> list = this.u;
                if (list == null) {
                    this.u = new ArrayList();
                    this.v = new ArrayList();
                } else {
                    list.clear();
                    this.v.clear();
                }
                int boundingBoxMargin = this.m.getBoundingBoxMargin();
                int iRound = Math.round(this.j + this.h) - boundingBoxMargin;
                int iRound2 = Math.round(this.k + this.i) - boundingBoxMargin;
                int i6 = boundingBoxMargin * 2;
                int width = viewHolder.itemView.getWidth() + iRound + i6;
                int height = viewHolder.itemView.getHeight() + iRound2 + i6;
                int i7 = (iRound + width) / 2;
                int i8 = (iRound2 + height) / 2;
                RecyclerView.LayoutManager layoutManager = this.r.getLayoutManager();
                int childCount = layoutManager.getChildCount();
                int i9 = 0;
                while (i9 < childCount) {
                    View childAt = layoutManager.getChildAt(i9);
                    if (childAt != viewHolder.itemView && childAt.getBottom() >= iRound2 && childAt.getTop() <= height && childAt.getRight() >= iRound && childAt.getLeft() <= width) {
                        RecyclerView.ViewHolder childViewHolder = this.r.getChildViewHolder(childAt);
                        i2 = iRound;
                        i3 = iRound2;
                        if (this.m.canDropOver(this.r, this.c, childViewHolder)) {
                            int iAbs = Math.abs(i7 - ((childAt.getRight() + childAt.getLeft()) / 2));
                            int iAbs2 = Math.abs(i8 - ((childAt.getBottom() + childAt.getTop()) / 2));
                            int i10 = (iAbs2 * iAbs2) + (iAbs * iAbs);
                            int size = this.u.size();
                            int i11 = 0;
                            int i12 = 0;
                            while (true) {
                                i = i7;
                                if (i12 >= size || i10 <= this.v.get(i12).intValue()) {
                                    break;
                                }
                                i11++;
                                i12++;
                                i7 = i;
                            }
                            this.u.add(i11, childViewHolder);
                            this.v.add(i11, Integer.valueOf(i10));
                        } else {
                            i = i7;
                        }
                    } else {
                        i = i7;
                        i2 = iRound;
                        i3 = iRound2;
                    }
                    i9++;
                    iRound = i2;
                    iRound2 = i3;
                    i7 = i;
                }
                List<RecyclerView.ViewHolder> list2 = this.u;
                if (list2.size() == 0) {
                    return;
                }
                RecyclerView.ViewHolder viewHolderChooseDropTarget = this.m.chooseDropTarget(viewHolder, list2, i4, i5);
                if (viewHolderChooseDropTarget == null) {
                    this.u.clear();
                    this.v.clear();
                    return;
                }
                int adapterPosition = viewHolderChooseDropTarget.getAdapterPosition();
                int adapterPosition2 = viewHolder.getAdapterPosition();
                if (this.m.onMove(this.r, viewHolder, viewHolderChooseDropTarget)) {
                    this.m.onMoved(this.r, viewHolder, adapterPosition2, viewHolderChooseDropTarget, adapterPosition, i4, i5);
                }
            }
        }
    }

    public int a(RecyclerView.ViewHolder viewHolder, boolean z) {
        for (int size = this.p.size() - 1; size >= 0; size--) {
            e eVar = this.p.get(size);
            if (eVar.e == viewHolder) {
                eVar.l |= z;
                if (!eVar.m) {
                    eVar.g.cancel();
                }
                this.p.remove(size);
                return eVar.h;
            }
        }
        return 0;
    }

    public void a() {
        VelocityTracker velocityTracker = this.t;
        if (velocityTracker != null) {
            velocityTracker.recycle();
        }
        this.t = VelocityTracker.obtain();
    }

    public boolean a(int i, MotionEvent motionEvent, int i2) {
        int iA;
        View viewA;
        if (this.c != null || i != 2 || this.n == 2 || !this.m.isItemViewSwipeEnabled() || this.r.getScrollState() == 1) {
            return false;
        }
        RecyclerView.LayoutManager layoutManager = this.r.getLayoutManager();
        int i3 = this.l;
        RecyclerView.ViewHolder childViewHolder = null;
        if (i3 != -1) {
            int iFindPointerIndex = motionEvent.findPointerIndex(i3);
            float x = motionEvent.getX(iFindPointerIndex) - this.d;
            float y = motionEvent.getY(iFindPointerIndex) - this.e;
            float fAbs = Math.abs(x);
            float fAbs2 = Math.abs(y);
            float f = this.q;
            if ((fAbs >= f || fAbs2 >= f) && ((fAbs <= fAbs2 || !layoutManager.canScrollHorizontally()) && ((fAbs2 <= fAbs || !layoutManager.canScrollVertically()) && (viewA = a(motionEvent)) != null))) {
                childViewHolder = this.r.getChildViewHolder(viewA);
            }
        }
        if (childViewHolder == null || (iA = (this.m.a(this.r, childViewHolder) & MotionEventCompat.ACTION_POINTER_INDEX_MASK) >> 8) == 0) {
            return false;
        }
        float x2 = motionEvent.getX(i2);
        float y2 = motionEvent.getY(i2);
        float f2 = x2 - this.d;
        float f3 = y2 - this.e;
        float fAbs3 = Math.abs(f2);
        float fAbs4 = Math.abs(f3);
        int i4 = this.q;
        if (fAbs3 < i4 && fAbs4 < i4) {
            return false;
        }
        if (fAbs3 > fAbs4) {
            if (f2 < 0.0f && (iA & 4) == 0) {
                return false;
            }
            if (f2 > 0.0f && (iA & 8) == 0) {
                return false;
            }
        } else {
            if (f3 < 0.0f && (iA & 1) == 0) {
                return false;
            }
            if (f3 > 0.0f && (iA & 2) == 0) {
                return false;
            }
        }
        this.i = 0.0f;
        this.h = 0.0f;
        this.l = motionEvent.getPointerId(0);
        c(childViewHolder, 1);
        return true;
    }

    public View a(MotionEvent motionEvent) {
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        RecyclerView.ViewHolder viewHolder = this.c;
        if (viewHolder != null) {
            View view2 = viewHolder.itemView;
            if (a(view2, x, y, this.j + this.h, this.k + this.i)) {
                return view2;
            }
        }
        for (int size = this.p.size() - 1; size >= 0; size--) {
            e eVar = this.p.get(size);
            View view3 = eVar.e.itemView;
            if (a(view3, x, y, eVar.j, eVar.k)) {
                return view3;
            }
        }
        return this.r.findChildViewUnder(x, y);
    }

    public void a(MotionEvent motionEvent, int i, int i2) {
        float x = motionEvent.getX(i2);
        float y = motionEvent.getY(i2);
        float f = x - this.d;
        this.h = f;
        this.i = y - this.e;
        if ((i & 4) == 0) {
            this.h = Math.max(0.0f, f);
        }
        if ((i & 8) == 0) {
            this.h = Math.min(0.0f, this.h);
        }
        if ((i & 1) == 0) {
            this.i = Math.max(0.0f, this.i);
        }
        if ((i & 2) == 0) {
            this.i = Math.min(0.0f, this.i);
        }
    }

    public final int a(RecyclerView.ViewHolder viewHolder, int i) {
        if ((i & 12) == 0) {
            return 0;
        }
        int i2 = this.h > 0.0f ? 8 : 4;
        VelocityTracker velocityTracker = this.t;
        if (velocityTracker != null && this.l > -1) {
            velocityTracker.computeCurrentVelocity(1000, this.m.getSwipeVelocityThreshold(this.g));
            float xVelocity = this.t.getXVelocity(this.l);
            float yVelocity = this.t.getYVelocity(this.l);
            int i3 = xVelocity <= 0.0f ? 4 : 8;
            float fAbs = Math.abs(xVelocity);
            if ((i3 & i) != 0 && i2 == i3 && fAbs >= this.m.getSwipeEscapeVelocity(this.f) && fAbs > Math.abs(yVelocity)) {
                return i3;
            }
        }
        float swipeThreshold = this.m.getSwipeThreshold(viewHolder) * this.r.getWidth();
        if ((i & i2) == 0 || Math.abs(this.h) <= swipeThreshold) {
            return 0;
        }
        return i2;
    }

    public void a(View view2) {
        if (view2 == this.x) {
            this.x = null;
            if (this.w != null) {
                this.r.setChildDrawingOrderCallback(null);
            }
        }
    }
}
