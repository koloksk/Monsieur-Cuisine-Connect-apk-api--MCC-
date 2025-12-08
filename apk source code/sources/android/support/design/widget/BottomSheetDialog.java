package android.support.design.widget;

import android.R;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.view.AccessibilityDelegateCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.app.AppCompatDialog;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

/* loaded from: classes.dex */
public class BottomSheetDialog extends AppCompatDialog {
    public BottomSheetBehavior<FrameLayout> b;
    public boolean c;
    public boolean d;
    public boolean e;
    public BottomSheetBehavior.BottomSheetCallback f;

    public class a implements View.OnClickListener {
        public a() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view2) {
            BottomSheetDialog bottomSheetDialog = BottomSheetDialog.this;
            if (bottomSheetDialog.c && bottomSheetDialog.isShowing()) {
                BottomSheetDialog bottomSheetDialog2 = BottomSheetDialog.this;
                if (!bottomSheetDialog2.e) {
                    TypedArray typedArrayObtainStyledAttributes = bottomSheetDialog2.getContext().obtainStyledAttributes(new int[]{R.attr.windowCloseOnTouchOutside});
                    bottomSheetDialog2.d = typedArrayObtainStyledAttributes.getBoolean(0, true);
                    typedArrayObtainStyledAttributes.recycle();
                    bottomSheetDialog2.e = true;
                }
                if (bottomSheetDialog2.d) {
                    BottomSheetDialog.this.cancel();
                }
            }
        }
    }

    public class b extends AccessibilityDelegateCompat {
        public b() {
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfoCompat);
            if (!BottomSheetDialog.this.c) {
                accessibilityNodeInfoCompat.setDismissable(false);
            } else {
                accessibilityNodeInfoCompat.addAction(1048576);
                accessibilityNodeInfoCompat.setDismissable(true);
            }
        }

        @Override // android.support.v4.view.AccessibilityDelegateCompat
        public boolean performAccessibilityAction(View view2, int i, Bundle bundle) {
            if (i == 1048576) {
                BottomSheetDialog bottomSheetDialog = BottomSheetDialog.this;
                if (bottomSheetDialog.c) {
                    bottomSheetDialog.cancel();
                    return true;
                }
            }
            return super.performAccessibilityAction(view2, i, bundle);
        }
    }

    public class c implements View.OnTouchListener {
        public c(BottomSheetDialog bottomSheetDialog) {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view2, MotionEvent motionEvent) {
            return true;
        }
    }

    public class d extends BottomSheetBehavior.BottomSheetCallback {
        public d() {
        }

        @Override // android.support.design.widget.BottomSheetBehavior.BottomSheetCallback
        public void onSlide(@NonNull View view2, float f) {
        }

        @Override // android.support.design.widget.BottomSheetBehavior.BottomSheetCallback
        public void onStateChanged(@NonNull View view2, int i) {
            if (i == 5) {
                BottomSheetDialog.this.cancel();
            }
        }
    }

    public BottomSheetDialog(@NonNull Context context) {
        this(context, 0);
    }

    public final View a(int i, View view2, ViewGroup.LayoutParams layoutParams) {
        FrameLayout frameLayout = (FrameLayout) View.inflate(getContext(), android.support.design.R.layout.design_bottom_sheet_dialog, null);
        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) frameLayout.findViewById(android.support.design.R.id.coordinator);
        if (i != 0 && view2 == null) {
            view2 = getLayoutInflater().inflate(i, (ViewGroup) coordinatorLayout, false);
        }
        FrameLayout frameLayout2 = (FrameLayout) coordinatorLayout.findViewById(android.support.design.R.id.design_bottom_sheet);
        BottomSheetBehavior<FrameLayout> bottomSheetBehaviorFrom = BottomSheetBehavior.from(frameLayout2);
        this.b = bottomSheetBehaviorFrom;
        bottomSheetBehaviorFrom.setBottomSheetCallback(this.f);
        this.b.setHideable(this.c);
        if (layoutParams == null) {
            frameLayout2.addView(view2);
        } else {
            frameLayout2.addView(view2, layoutParams);
        }
        coordinatorLayout.findViewById(android.support.design.R.id.touch_outside).setOnClickListener(new a());
        ViewCompat.setAccessibilityDelegate(frameLayout2, new b());
        frameLayout2.setOnTouchListener(new c(this));
        return frameLayout;
    }

    @Override // android.support.v7.app.AppCompatDialog, android.app.Dialog
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        Window window = getWindow();
        if (window != null) {
            window.clearFlags(67108864);
            window.addFlags(Integer.MIN_VALUE);
            window.setLayout(-1, -1);
        }
    }

    @Override // android.app.Dialog
    public void onStart() {
        super.onStart();
        BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.b;
        if (bottomSheetBehavior != null) {
            bottomSheetBehavior.setState(4);
        }
    }

    @Override // android.app.Dialog
    public void setCancelable(boolean z) {
        super.setCancelable(z);
        if (this.c != z) {
            this.c = z;
            BottomSheetBehavior<FrameLayout> bottomSheetBehavior = this.b;
            if (bottomSheetBehavior != null) {
                bottomSheetBehavior.setHideable(z);
            }
        }
    }

    @Override // android.app.Dialog
    public void setCanceledOnTouchOutside(boolean z) {
        super.setCanceledOnTouchOutside(z);
        if (z && !this.c) {
            this.c = true;
        }
        this.d = z;
        this.e = true;
    }

    @Override // android.support.v7.app.AppCompatDialog, android.app.Dialog
    public void setContentView(@LayoutRes int i) {
        super.setContentView(a(i, null, null));
    }

    public BottomSheetDialog(@NonNull Context context, boolean z, DialogInterface.OnCancelListener onCancelListener) {
        super(context, z, onCancelListener);
        this.c = true;
        this.d = true;
        this.f = new d();
        supportRequestWindowFeature(1);
        this.c = z;
    }

    @Override // android.support.v7.app.AppCompatDialog, android.app.Dialog
    public void setContentView(View view2) {
        super.setContentView(a(0, view2, null));
    }

    @Override // android.support.v7.app.AppCompatDialog, android.app.Dialog
    public void setContentView(View view2, ViewGroup.LayoutParams layoutParams) {
        super.setContentView(a(0, view2, layoutParams));
    }

    public BottomSheetDialog(@NonNull Context context, @StyleRes int i) {
        if (i == 0) {
            TypedValue typedValue = new TypedValue();
            if (context.getTheme().resolveAttribute(android.support.design.R.attr.bottomSheetDialogTheme, typedValue, true)) {
                i = typedValue.resourceId;
            } else {
                i = android.support.design.R.style.Theme_Design_Light_BottomSheetDialog;
            }
        }
        super(context, i);
        this.c = true;
        this.d = true;
        this.f = new d();
        supportRequestWindowFeature(1);
    }
}
