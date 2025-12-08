package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.annotation.StyleRes;
import android.support.v4.view.PointerIconCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.ShowableListMenu;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import defpackage.f8;
import defpackage.g9;
import defpackage.j8;
import defpackage.k8;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class ListPopupWindow implements ShowableListMenu {
    public static Method G = null;
    public static Method H = null;
    public static Method I = null;
    public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
    public static final int INPUT_METHOD_NEEDED = 1;
    public static final int INPUT_METHOD_NOT_NEEDED = 2;
    public static final int MATCH_PARENT = -1;
    public static final int POSITION_PROMPT_ABOVE = 0;
    public static final int POSITION_PROMPT_BELOW = 1;
    public static final int WRAP_CONTENT = -2;
    public Runnable A;
    public final Handler B;
    public final Rect C;
    public Rect D;
    public boolean E;
    public PopupWindow F;
    public Context a;
    public ListAdapter b;
    public f8 c;
    public int d;
    public int e;
    public int f;
    public int g;
    public int h;
    public boolean i;
    public boolean j;
    public boolean k;
    public int l;
    public boolean m;
    public boolean n;
    public int o;
    public View p;
    public int q;
    public DataSetObserver r;
    public View s;
    public Drawable t;
    public AdapterView.OnItemClickListener u;
    public AdapterView.OnItemSelectedListener v;
    public final f w;
    public final e x;
    public final d y;
    public final b z;

    public class a extends ForwardingListener {
        public a(View view2) {
            super(view2);
        }

        @Override // android.support.v7.widget.ForwardingListener
        public ShowableListMenu getPopup() {
            return ListPopupWindow.this;
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ListPopupWindow.this.clearListSelection();
        }
    }

    public class c extends DataSetObserver {
        public c() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            if (ListPopupWindow.this.isShowing()) {
                ListPopupWindow.this.show();
            }
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    public class d implements AbsListView.OnScrollListener {
        public d() {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScroll(AbsListView absListView, int i, int i2, int i3) {
        }

        @Override // android.widget.AbsListView.OnScrollListener
        public void onScrollStateChanged(AbsListView absListView, int i) {
            if (i != 1 || ListPopupWindow.this.isInputMethodNotNeeded() || ListPopupWindow.this.F.getContentView() == null) {
                return;
            }
            ListPopupWindow listPopupWindow = ListPopupWindow.this;
            listPopupWindow.B.removeCallbacks(listPopupWindow.w);
            ListPopupWindow.this.w.run();
        }
    }

    public class e implements View.OnTouchListener {
        public e() {
        }

        @Override // android.view.View.OnTouchListener
        public boolean onTouch(View view2, MotionEvent motionEvent) {
            PopupWindow popupWindow;
            int action = motionEvent.getAction();
            int x = (int) motionEvent.getX();
            int y = (int) motionEvent.getY();
            if (action == 0 && (popupWindow = ListPopupWindow.this.F) != null && popupWindow.isShowing() && x >= 0 && x < ListPopupWindow.this.F.getWidth() && y >= 0 && y < ListPopupWindow.this.F.getHeight()) {
                ListPopupWindow listPopupWindow = ListPopupWindow.this;
                listPopupWindow.B.postDelayed(listPopupWindow.w, 250L);
                return false;
            }
            if (action != 1) {
                return false;
            }
            ListPopupWindow listPopupWindow2 = ListPopupWindow.this;
            listPopupWindow2.B.removeCallbacks(listPopupWindow2.w);
            return false;
        }
    }

    public class f implements Runnable {
        public f() {
        }

        @Override // java.lang.Runnable
        public void run() {
            f8 f8Var = ListPopupWindow.this.c;
            if (f8Var == null || !ViewCompat.isAttachedToWindow(f8Var) || ListPopupWindow.this.c.getCount() <= ListPopupWindow.this.c.getChildCount()) {
                return;
            }
            int childCount = ListPopupWindow.this.c.getChildCount();
            ListPopupWindow listPopupWindow = ListPopupWindow.this;
            if (childCount <= listPopupWindow.o) {
                listPopupWindow.F.setInputMethodMode(2);
                ListPopupWindow.this.show();
            }
        }
    }

    static {
        try {
            G = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE);
        } catch (NoSuchMethodException unused) {
            Log.i("ListPopupWindow", "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
        }
        try {
            H = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", View.class, Integer.TYPE, Boolean.TYPE);
        } catch (NoSuchMethodException unused2) {
            Log.i("ListPopupWindow", "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
        }
        try {
            I = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
        } catch (NoSuchMethodException unused3) {
            Log.i("ListPopupWindow", "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
        }
    }

    public ListPopupWindow(@NonNull Context context) {
        this(context, null, R.attr.listPopupWindowStyle);
    }

    @NonNull
    public f8 a(Context context, boolean z) {
        return new f8(context, z);
    }

    public void clearListSelection() {
        f8 f8Var = this.c;
        if (f8Var != null) {
            f8Var.setListSelectionHidden(true);
            f8Var.requestLayout();
        }
    }

    public View.OnTouchListener createDragToOpenListener(View view2) {
        return new a(view2);
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public void dismiss() {
        this.F.dismiss();
        View view2 = this.p;
        if (view2 != null) {
            ViewParent parent = view2.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.p);
            }
        }
        this.F.setContentView(null);
        this.c = null;
        this.B.removeCallbacks(this.w);
    }

    @Nullable
    public View getAnchorView() {
        return this.s;
    }

    @StyleRes
    public int getAnimationStyle() {
        return this.F.getAnimationStyle();
    }

    @Nullable
    public Drawable getBackground() {
        return this.F.getBackground();
    }

    public int getHeight() {
        return this.d;
    }

    public int getHorizontalOffset() {
        return this.f;
    }

    public int getInputMethodMode() {
        return this.F.getInputMethodMode();
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    @Nullable
    public ListView getListView() {
        return this.c;
    }

    public int getPromptPosition() {
        return this.q;
    }

    @Nullable
    public Object getSelectedItem() {
        if (isShowing()) {
            return this.c.getSelectedItem();
        }
        return null;
    }

    public long getSelectedItemId() {
        if (isShowing()) {
            return this.c.getSelectedItemId();
        }
        return Long.MIN_VALUE;
    }

    public int getSelectedItemPosition() {
        if (isShowing()) {
            return this.c.getSelectedItemPosition();
        }
        return -1;
    }

    @Nullable
    public View getSelectedView() {
        if (isShowing()) {
            return this.c.getSelectedView();
        }
        return null;
    }

    public int getSoftInputMode() {
        return this.F.getSoftInputMode();
    }

    public int getVerticalOffset() {
        if (this.i) {
            return this.g;
        }
        return 0;
    }

    public int getWidth() {
        return this.e;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public boolean isDropDownAlwaysVisible() {
        return this.m;
    }

    public boolean isInputMethodNotNeeded() {
        return this.F.getInputMethodMode() == 2;
    }

    public boolean isModal() {
        return this.E;
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public boolean isShowing() {
        return this.F.isShowing();
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0022  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onKeyDown(int r12, @android.support.annotation.NonNull android.view.KeyEvent r13) {
        /*
            r11 = this;
            boolean r0 = r11.isShowing()
            r1 = 0
            if (r0 == 0) goto Laa
            r0 = 62
            if (r12 == r0) goto Laa
            f8 r0 = r11.c
            int r0 = r0.getSelectedItemPosition()
            r2 = 23
            r3 = 66
            r4 = 1
            if (r0 >= 0) goto L22
            if (r12 == r3) goto L1f
            if (r12 != r2) goto L1d
            goto L1f
        L1d:
            r0 = r1
            goto L20
        L1f:
            r0 = r4
        L20:
            if (r0 != 0) goto Laa
        L22:
            f8 r0 = r11.c
            int r0 = r0.getSelectedItemPosition()
            android.widget.PopupWindow r5 = r11.F
            boolean r5 = r5.isAboveAnchor()
            r5 = r5 ^ r4
            android.widget.ListAdapter r6 = r11.b
            r7 = 2147483647(0x7fffffff, float:NaN)
            r8 = -2147483648(0xffffffff80000000, float:-0.0)
            if (r6 == 0) goto L5b
            boolean r7 = r6.areAllItemsEnabled()
            if (r7 == 0) goto L40
            r8 = r1
            goto L46
        L40:
            f8 r8 = r11.c
            int r8 = r8.lookForSelectablePosition(r1, r4)
        L46:
            if (r7 == 0) goto L4e
            int r6 = r6.getCount()
            int r6 = r6 - r4
            goto L59
        L4e:
            f8 r7 = r11.c
            int r6 = r6.getCount()
            int r6 = r6 - r4
            int r6 = r7.lookForSelectablePosition(r6, r1)
        L59:
            r7 = r8
            r8 = r6
        L5b:
            r6 = 19
            if (r5 == 0) goto L63
            if (r12 != r6) goto L63
            if (r0 <= r7) goto L6b
        L63:
            r9 = 20
            if (r5 != 0) goto L77
            if (r12 != r9) goto L77
            if (r0 < r8) goto L77
        L6b:
            r11.clearListSelection()
            android.widget.PopupWindow r12 = r11.F
            r12.setInputMethodMode(r4)
            r11.show()
            return r4
        L77:
            f8 r10 = r11.c
            r10.setListSelectionHidden(r1)
            f8 r10 = r11.c
            boolean r13 = r10.onKeyDown(r12, r13)
            if (r13 == 0) goto L9c
            android.widget.PopupWindow r13 = r11.F
            r0 = 2
            r13.setInputMethodMode(r0)
            f8 r13 = r11.c
            r13.requestFocusFromTouch()
            r11.show()
            if (r12 == r6) goto L9b
            if (r12 == r9) goto L9b
            if (r12 == r2) goto L9b
            if (r12 == r3) goto L9b
            goto Laa
        L9b:
            return r4
        L9c:
            if (r5 == 0) goto La3
            if (r12 != r9) goto La3
            if (r0 != r8) goto Laa
            return r4
        La3:
            if (r5 != 0) goto Laa
            if (r12 != r6) goto Laa
            if (r0 != r7) goto Laa
            return r4
        Laa:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ListPopupWindow.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    public boolean onKeyPreIme(int i, @NonNull KeyEvent keyEvent) {
        if (i != 4 || !isShowing()) {
            return false;
        }
        View view2 = this.s;
        if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
            KeyEvent.DispatcherState keyDispatcherState = view2.getKeyDispatcherState();
            if (keyDispatcherState != null) {
                keyDispatcherState.startTracking(keyEvent, this);
            }
            return true;
        }
        if (keyEvent.getAction() != 1) {
            return false;
        }
        KeyEvent.DispatcherState keyDispatcherState2 = view2.getKeyDispatcherState();
        if (keyDispatcherState2 != null) {
            keyDispatcherState2.handleUpEvent(keyEvent);
        }
        if (!keyEvent.isTracking() || keyEvent.isCanceled()) {
            return false;
        }
        dismiss();
        return true;
    }

    public boolean onKeyUp(int i, @NonNull KeyEvent keyEvent) {
        if (!isShowing() || this.c.getSelectedItemPosition() < 0) {
            return false;
        }
        boolean zOnKeyUp = this.c.onKeyUp(i, keyEvent);
        if (zOnKeyUp) {
            if (i == 66 || i == 23) {
                dismiss();
            }
        }
        return zOnKeyUp;
    }

    public boolean performItemClick(int i) {
        if (!isShowing()) {
            return false;
        }
        if (this.u == null) {
            return true;
        }
        f8 f8Var = this.c;
        this.u.onItemClick(f8Var, f8Var.getChildAt(i - f8Var.getFirstVisiblePosition()), i, f8Var.getAdapter().getItemId(i));
        return true;
    }

    public void postShow() {
        this.B.post(this.A);
    }

    public void setAdapter(@Nullable ListAdapter listAdapter) {
        DataSetObserver dataSetObserver = this.r;
        if (dataSetObserver == null) {
            this.r = new c();
        } else {
            ListAdapter listAdapter2 = this.b;
            if (listAdapter2 != null) {
                listAdapter2.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.b = listAdapter;
        if (listAdapter != null) {
            listAdapter.registerDataSetObserver(this.r);
        }
        f8 f8Var = this.c;
        if (f8Var != null) {
            f8Var.setAdapter(this.b);
        }
    }

    public void setAnchorView(@Nullable View view2) {
        this.s = view2;
    }

    public void setAnimationStyle(@StyleRes int i) {
        this.F.setAnimationStyle(i);
    }

    public void setBackgroundDrawable(@Nullable Drawable drawable) {
        this.F.setBackgroundDrawable(drawable);
    }

    public void setContentWidth(int i) {
        Drawable background = this.F.getBackground();
        if (background == null) {
            setWidth(i);
            return;
        }
        background.getPadding(this.C);
        Rect rect = this.C;
        this.e = rect.left + rect.right + i;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setDropDownAlwaysVisible(boolean z) {
        this.m = z;
    }

    public void setDropDownGravity(int i) {
        this.l = i;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setEpicenterBounds(Rect rect) {
        this.D = rect;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setForceIgnoreOutsideTouch(boolean z) {
        this.n = z;
    }

    public void setHeight(int i) {
        if (i < 0 && -2 != i && -1 != i) {
            throw new IllegalArgumentException("Invalid height. Must be a positive value, MATCH_PARENT, or WRAP_CONTENT.");
        }
        this.d = i;
    }

    public void setHorizontalOffset(int i) {
        this.f = i;
    }

    public void setInputMethodMode(int i) {
        this.F.setInputMethodMode(i);
    }

    public void setListSelector(Drawable drawable) {
        this.t = drawable;
    }

    public void setModal(boolean z) {
        this.E = z;
        this.F.setFocusable(z);
    }

    public void setOnDismissListener(@Nullable PopupWindow.OnDismissListener onDismissListener) {
        this.F.setOnDismissListener(onDismissListener);
    }

    public void setOnItemClickListener(@Nullable AdapterView.OnItemClickListener onItemClickListener) {
        this.u = onItemClickListener;
    }

    public void setOnItemSelectedListener(@Nullable AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.v = onItemSelectedListener;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setOverlapAnchor(boolean z) {
        this.k = true;
        this.j = z;
    }

    public void setPromptPosition(int i) {
        this.q = i;
    }

    public void setPromptView(@Nullable View view2) {
        View view3;
        boolean zIsShowing = isShowing();
        if (zIsShowing && (view3 = this.p) != null) {
            ViewParent parent = view3.getParent();
            if (parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(this.p);
            }
        }
        this.p = view2;
        if (zIsShowing) {
            show();
        }
    }

    public void setSelection(int i) {
        f8 f8Var = this.c;
        if (!isShowing() || f8Var == null) {
            return;
        }
        f8Var.setListSelectionHidden(false);
        f8Var.setSelection(i);
        if (f8Var.getChoiceMode() != 0) {
            f8Var.setItemChecked(i, true);
        }
    }

    public void setSoftInputMode(int i) {
        this.F.setSoftInputMode(i);
    }

    public void setVerticalOffset(int i) {
        this.g = i;
        this.i = true;
    }

    public void setWidth(int i) {
        this.e = i;
    }

    public void setWindowLayoutType(int i) {
        this.h = i;
    }

    @Override // android.support.v7.view.menu.ShowableListMenu
    public void show() {
        int measuredHeight;
        int i;
        int iIntValue;
        int i2;
        int iMakeMeasureSpec;
        int i3;
        if (this.c == null) {
            Context context = this.a;
            this.A = new j8(this);
            f8 f8VarA = a(context, !this.E);
            this.c = f8VarA;
            Drawable drawable = this.t;
            if (drawable != null) {
                f8VarA.setSelector(drawable);
            }
            this.c.setAdapter(this.b);
            this.c.setOnItemClickListener(this.u);
            this.c.setFocusable(true);
            this.c.setFocusableInTouchMode(true);
            this.c.setOnItemSelectedListener(new k8(this));
            this.c.setOnScrollListener(this.y);
            AdapterView.OnItemSelectedListener onItemSelectedListener = this.v;
            if (onItemSelectedListener != null) {
                this.c.setOnItemSelectedListener(onItemSelectedListener);
            }
            View view2 = this.c;
            View view3 = this.p;
            if (view3 != null) {
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(1);
                ViewGroup.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 1.0f);
                int i4 = this.q;
                if (i4 == 0) {
                    linearLayout.addView(view3);
                    linearLayout.addView(view2, layoutParams);
                } else if (i4 != 1) {
                    StringBuilder sbA = g9.a("Invalid hint position ");
                    sbA.append(this.q);
                    Log.e("ListPopupWindow", sbA.toString());
                } else {
                    linearLayout.addView(view2, layoutParams);
                    linearLayout.addView(view3);
                }
                int i5 = this.e;
                if (i5 >= 0) {
                    i3 = Integer.MIN_VALUE;
                } else {
                    i5 = 0;
                    i3 = 0;
                }
                view3.measure(View.MeasureSpec.makeMeasureSpec(i5, i3), 0);
                LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) view3.getLayoutParams();
                measuredHeight = view3.getMeasuredHeight() + layoutParams2.topMargin + layoutParams2.bottomMargin;
                view2 = linearLayout;
            } else {
                measuredHeight = 0;
            }
            this.F.setContentView(view2);
        } else {
            View view4 = this.p;
            if (view4 != null) {
                LinearLayout.LayoutParams layoutParams3 = (LinearLayout.LayoutParams) view4.getLayoutParams();
                measuredHeight = view4.getMeasuredHeight() + layoutParams3.topMargin + layoutParams3.bottomMargin;
            } else {
                measuredHeight = 0;
            }
        }
        Drawable background = this.F.getBackground();
        if (background != null) {
            background.getPadding(this.C);
            Rect rect = this.C;
            int i6 = rect.top;
            i = rect.bottom + i6;
            if (!this.i) {
                this.g = -i6;
            }
        } else {
            this.C.setEmpty();
            i = 0;
        }
        boolean z = this.F.getInputMethodMode() == 2;
        View anchorView = getAnchorView();
        int i7 = this.g;
        Method method = H;
        if (method != null) {
            try {
                iIntValue = ((Integer) method.invoke(this.F, anchorView, Integer.valueOf(i7), Boolean.valueOf(z))).intValue();
            } catch (Exception unused) {
                Log.i("ListPopupWindow", "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
            }
        } else {
            iIntValue = this.F.getMaxAvailableHeight(anchorView, i7);
        }
        if (this.m || this.d == -1) {
            i2 = iIntValue + i;
        } else {
            int i8 = this.e;
            if (i8 == -2) {
                int i9 = this.a.getResources().getDisplayMetrics().widthPixels;
                Rect rect2 = this.C;
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i9 - (rect2.left + rect2.right), Integer.MIN_VALUE);
            } else if (i8 != -1) {
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i8, 1073741824);
            } else {
                int i10 = this.a.getResources().getDisplayMetrics().widthPixels;
                Rect rect3 = this.C;
                iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i10 - (rect3.left + rect3.right), 1073741824);
            }
            int iMeasureHeightOfChildrenCompat = this.c.measureHeightOfChildrenCompat(iMakeMeasureSpec, 0, -1, iIntValue - measuredHeight, -1);
            if (iMeasureHeightOfChildrenCompat > 0) {
                measuredHeight += this.c.getPaddingBottom() + this.c.getPaddingTop() + i;
            }
            i2 = iMeasureHeightOfChildrenCompat + measuredHeight;
        }
        boolean zIsInputMethodNotNeeded = isInputMethodNotNeeded();
        PopupWindowCompat.setWindowLayoutType(this.F, this.h);
        if (this.F.isShowing()) {
            if (ViewCompat.isAttachedToWindow(getAnchorView())) {
                int width = this.e;
                if (width == -1) {
                    width = -1;
                } else if (width == -2) {
                    width = getAnchorView().getWidth();
                }
                int i11 = this.d;
                if (i11 == -1) {
                    if (!zIsInputMethodNotNeeded) {
                        i2 = -1;
                    }
                    if (zIsInputMethodNotNeeded) {
                        this.F.setWidth(this.e == -1 ? -1 : 0);
                        this.F.setHeight(0);
                    } else {
                        this.F.setWidth(this.e == -1 ? -1 : 0);
                        this.F.setHeight(-1);
                    }
                } else if (i11 != -2) {
                    i2 = i11;
                }
                this.F.setOutsideTouchable((this.n || this.m) ? false : true);
                this.F.update(getAnchorView(), this.f, this.g, width < 0 ? -1 : width, i2 < 0 ? -1 : i2);
                return;
            }
            return;
        }
        int width2 = this.e;
        if (width2 == -1) {
            width2 = -1;
        } else if (width2 == -2) {
            width2 = getAnchorView().getWidth();
        }
        int i12 = this.d;
        if (i12 == -1) {
            i2 = -1;
        } else if (i12 != -2) {
            i2 = i12;
        }
        this.F.setWidth(width2);
        this.F.setHeight(i2);
        Method method2 = G;
        if (method2 != null) {
            try {
                method2.invoke(this.F, true);
            } catch (Exception unused2) {
                Log.i("ListPopupWindow", "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
        }
        this.F.setOutsideTouchable((this.n || this.m) ? false : true);
        this.F.setTouchInterceptor(this.x);
        if (this.k) {
            PopupWindowCompat.setOverlapAnchor(this.F, this.j);
        }
        Method method3 = I;
        if (method3 != null) {
            try {
                method3.invoke(this.F, this.D);
            } catch (Exception e2) {
                Log.e("ListPopupWindow", "Could not invoke setEpicenterBounds on PopupWindow", e2);
            }
        }
        PopupWindowCompat.showAsDropDown(this.F, getAnchorView(), this.f, this.g, this.l);
        this.c.setSelection(-1);
        if (!this.E || this.c.isInTouchMode()) {
            clearListSelection();
        }
        if (this.E) {
            return;
        }
        this.B.post(this.z);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i) {
        this(context, attributeSet, i, 0);
    }

    public ListPopupWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, @AttrRes int i, @StyleRes int i2) {
        this.d = -2;
        this.e = -2;
        this.h = PointerIconCompat.TYPE_HAND;
        this.l = 0;
        this.m = false;
        this.n = false;
        this.o = Integer.MAX_VALUE;
        this.q = 0;
        this.w = new f();
        this.x = new e();
        this.y = new d();
        this.z = new b();
        this.C = new Rect();
        this.a = context;
        this.B = new Handler(context.getMainLooper());
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ListPopupWindow, i, i2);
        this.f = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        int dimensionPixelOffset = typedArrayObtainStyledAttributes.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
        this.g = dimensionPixelOffset;
        if (dimensionPixelOffset != 0) {
            this.i = true;
        }
        typedArrayObtainStyledAttributes.recycle();
        AppCompatPopupWindow appCompatPopupWindow = new AppCompatPopupWindow(context, attributeSet, i, i2);
        this.F = appCompatPopupWindow;
        appCompatPopupWindow.setInputMethodMode(1);
    }
}
