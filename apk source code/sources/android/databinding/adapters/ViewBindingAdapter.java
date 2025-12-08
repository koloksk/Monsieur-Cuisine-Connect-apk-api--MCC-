package android.databinding.adapters;

import android.annotation.TargetApi;
import android.databinding.BindingAdapter;
import android.databinding.BindingMethod;
import android.databinding.BindingMethods;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.view.View;
import com.android.databinding.library.baseAdapters.R;

@BindingMethods({@BindingMethod(attribute = "android:backgroundTint", method = "setBackgroundTintList", type = View.class), @BindingMethod(attribute = "android:fadeScrollbars", method = "setScrollbarFadingEnabled", type = View.class), @BindingMethod(attribute = "android:getOutline", method = "setOutlineProvider", type = View.class), @BindingMethod(attribute = "android:nextFocusForward", method = "setNextFocusForwardId", type = View.class), @BindingMethod(attribute = "android:nextFocusLeft", method = "setNextFocusLeftId", type = View.class), @BindingMethod(attribute = "android:nextFocusRight", method = "setNextFocusRightId", type = View.class), @BindingMethod(attribute = "android:nextFocusUp", method = "setNextFocusUpId", type = View.class), @BindingMethod(attribute = "android:nextFocusDown", method = "setNextFocusDownId", type = View.class), @BindingMethod(attribute = "android:requiresFadingEdge", method = "setVerticalFadingEdgeEnabled", type = View.class), @BindingMethod(attribute = "android:scrollbarDefaultDelayBeforeFade", method = "setScrollBarDefaultDelayBeforeFade", type = View.class), @BindingMethod(attribute = "android:scrollbarFadeDuration", method = "setScrollBarFadeDuration", type = View.class), @BindingMethod(attribute = "android:scrollbarSize", method = "setScrollBarSize", type = View.class), @BindingMethod(attribute = "android:scrollbarStyle", method = "setScrollBarStyle", type = View.class), @BindingMethod(attribute = "android:transformPivotX", method = "setPivotX", type = View.class), @BindingMethod(attribute = "android:transformPivotY", method = "setPivotY", type = View.class), @BindingMethod(attribute = "android:onDrag", method = "setOnDragListener", type = View.class), @BindingMethod(attribute = "android:onClick", method = "setOnClickListener", type = View.class), @BindingMethod(attribute = "android:onApplyWindowInsets", method = "setOnApplyWindowInsetsListener", type = View.class), @BindingMethod(attribute = "android:onCreateContextMenu", method = "setOnCreateContextMenuListener", type = View.class), @BindingMethod(attribute = "android:onFocusChange", method = "setOnFocusChangeListener", type = View.class), @BindingMethod(attribute = "android:onGenericMotion", method = "setOnGenericMotionListener", type = View.class), @BindingMethod(attribute = "android:onHover", method = "setOnHoverListener", type = View.class), @BindingMethod(attribute = "android:onKey", method = "setOnKeyListener", type = View.class), @BindingMethod(attribute = "android:onLongClick", method = "setOnLongClickListener", type = View.class), @BindingMethod(attribute = "android:onSystemUiVisibilityChange", method = "setOnSystemUiVisibilityChangeListener", type = View.class), @BindingMethod(attribute = "android:onTouch", method = "setOnTouchListener", type = View.class)})
@RestrictTo({RestrictTo.Scope.LIBRARY})
/* loaded from: classes.dex */
public class ViewBindingAdapter {
    public static final int FADING_EDGE_HORIZONTAL = 1;
    public static final int FADING_EDGE_NONE = 0;
    public static final int FADING_EDGE_VERTICAL = 2;

    @TargetApi(12)
    public interface OnViewAttachedToWindow {
        void onViewAttachedToWindow(View view2);
    }

    @TargetApi(12)
    public interface OnViewDetachedFromWindow {
        void onViewDetachedFromWindow(View view2);
    }

    public static class a implements View.OnAttachStateChangeListener {
        public final /* synthetic */ OnViewAttachedToWindow a;
        public final /* synthetic */ OnViewDetachedFromWindow b;

        public a(OnViewAttachedToWindow onViewAttachedToWindow, OnViewDetachedFromWindow onViewDetachedFromWindow) {
            this.a = onViewAttachedToWindow;
            this.b = onViewDetachedFromWindow;
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewAttachedToWindow(View view2) {
            OnViewAttachedToWindow onViewAttachedToWindow = this.a;
            if (onViewAttachedToWindow != null) {
                onViewAttachedToWindow.onViewAttachedToWindow(view2);
            }
        }

        @Override // android.view.View.OnAttachStateChangeListener
        public void onViewDetachedFromWindow(View view2) {
            OnViewDetachedFromWindow onViewDetachedFromWindow = this.b;
            if (onViewDetachedFromWindow != null) {
                onViewDetachedFromWindow.onViewDetachedFromWindow(view2);
            }
        }
    }

    public static int a(float f) {
        int i = (int) (0.5f + f);
        if (i != 0) {
            return i;
        }
        if (f == 0.0f) {
            return 0;
        }
        return f > 0.0f ? 1 : -1;
    }

    @BindingAdapter({"android:background"})
    public static void setBackground(View view2, Drawable drawable) {
        view2.setBackground(drawable);
    }

    @BindingAdapter({"android:onClickListener", "android:clickable"})
    public static void setClickListener(View view2, View.OnClickListener onClickListener, boolean z) {
        view2.setOnClickListener(onClickListener);
        view2.setClickable(z);
    }

    @BindingAdapter(requireAll = false, value = {"android:onViewDetachedFromWindow", "android:onViewAttachedToWindow"})
    public static void setOnAttachStateChangeListener(View view2, OnViewDetachedFromWindow onViewDetachedFromWindow, OnViewAttachedToWindow onViewAttachedToWindow) {
        a aVar = (onViewDetachedFromWindow == null && onViewAttachedToWindow == null) ? null : new a(onViewAttachedToWindow, onViewDetachedFromWindow);
        View.OnAttachStateChangeListener onAttachStateChangeListener = (View.OnAttachStateChangeListener) ListenerUtil.trackListener(view2, aVar, R.id.onAttachStateChangeListener);
        if (onAttachStateChangeListener != null) {
            view2.removeOnAttachStateChangeListener(onAttachStateChangeListener);
        }
        if (aVar != null) {
            view2.addOnAttachStateChangeListener(aVar);
        }
    }

    @BindingAdapter({"android:onClick", "android:clickable"})
    public static void setOnClick(View view2, View.OnClickListener onClickListener, boolean z) {
        view2.setOnClickListener(onClickListener);
        view2.setClickable(z);
    }

    @BindingAdapter({"android:onLayoutChange"})
    public static void setOnLayoutChangeListener(View view2, View.OnLayoutChangeListener onLayoutChangeListener, View.OnLayoutChangeListener onLayoutChangeListener2) {
        if (onLayoutChangeListener != null) {
            view2.removeOnLayoutChangeListener(onLayoutChangeListener);
        }
        if (onLayoutChangeListener2 != null) {
            view2.addOnLayoutChangeListener(onLayoutChangeListener2);
        }
    }

    @BindingAdapter({"android:onLongClick", "android:longClickable"})
    public static void setOnLongClick(View view2, View.OnLongClickListener onLongClickListener, boolean z) {
        view2.setOnLongClickListener(onLongClickListener);
        view2.setLongClickable(z);
    }

    @BindingAdapter({"android:onLongClickListener", "android:longClickable"})
    public static void setOnLongClickListener(View view2, View.OnLongClickListener onLongClickListener, boolean z) {
        view2.setOnLongClickListener(onLongClickListener);
        view2.setLongClickable(z);
    }

    @BindingAdapter({"android:padding"})
    public static void setPadding(View view2, float f) {
        int iA = a(f);
        view2.setPadding(iA, iA, iA, iA);
    }

    @BindingAdapter({"android:paddingBottom"})
    public static void setPaddingBottom(View view2, float f) {
        view2.setPadding(view2.getPaddingLeft(), view2.getPaddingTop(), view2.getPaddingRight(), a(f));
    }

    @BindingAdapter({"android:paddingEnd"})
    public static void setPaddingEnd(View view2, float f) {
        view2.setPaddingRelative(view2.getPaddingStart(), view2.getPaddingTop(), a(f), view2.getPaddingBottom());
    }

    @BindingAdapter({"android:paddingLeft"})
    public static void setPaddingLeft(View view2, float f) {
        view2.setPadding(a(f), view2.getPaddingTop(), view2.getPaddingRight(), view2.getPaddingBottom());
    }

    @BindingAdapter({"android:paddingRight"})
    public static void setPaddingRight(View view2, float f) {
        view2.setPadding(view2.getPaddingLeft(), view2.getPaddingTop(), a(f), view2.getPaddingBottom());
    }

    @BindingAdapter({"android:paddingStart"})
    public static void setPaddingStart(View view2, float f) {
        view2.setPaddingRelative(a(f), view2.getPaddingTop(), view2.getPaddingEnd(), view2.getPaddingBottom());
    }

    @BindingAdapter({"android:paddingTop"})
    public static void setPaddingTop(View view2, float f) {
        view2.setPadding(view2.getPaddingLeft(), a(f), view2.getPaddingRight(), view2.getPaddingBottom());
    }

    @BindingAdapter({"android:requiresFadingEdge"})
    public static void setRequiresFadingEdge(View view2, int i) {
        boolean z = (i & 2) != 0;
        boolean z2 = (i & 1) != 0;
        view2.setVerticalFadingEdgeEnabled(z);
        view2.setHorizontalFadingEdgeEnabled(z2);
    }
}
