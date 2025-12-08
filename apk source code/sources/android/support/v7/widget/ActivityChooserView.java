package android.support.v7.widget;

import android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.RestrictTo;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v7.view.menu.ShowableListMenu;
import android.support.v7.widget.ActivityChooserModel;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
/* loaded from: classes.dex */
public class ActivityChooserView extends ViewGroup implements ActivityChooserModel.ActivityChooserModelClient {
    public final f a;
    public final g b;
    public final LinearLayoutCompat c;
    public final Drawable d;
    public final FrameLayout e;
    public final ImageView f;
    public final FrameLayout g;
    public final ImageView h;
    public final int i;
    public ActionProvider j;
    public final DataSetObserver k;
    public final ViewTreeObserver.OnGlobalLayoutListener l;
    public ListPopupWindow m;
    public PopupWindow.OnDismissListener n;
    public boolean o;
    public int p;
    public boolean q;
    public int r;

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static class InnerLayout extends LinearLayout {
        public static final int[] a = {R.attr.background};

        public InnerLayout(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            TintTypedArray tintTypedArrayObtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, attributeSet, a);
            setBackgroundDrawable(tintTypedArrayObtainStyledAttributes.getDrawable(0));
            tintTypedArrayObtainStyledAttributes.recycle();
        }
    }

    public class a extends DataSetObserver {
        public a() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            super.onChanged();
            ActivityChooserView.this.a.notifyDataSetChanged();
        }

        @Override // android.database.DataSetObserver
        public void onInvalidated() {
            super.onInvalidated();
            ActivityChooserView.this.a.notifyDataSetInvalidated();
        }
    }

    public class b implements ViewTreeObserver.OnGlobalLayoutListener {
        public b() {
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            if (ActivityChooserView.this.isShowingPopup()) {
                if (!ActivityChooserView.this.isShown()) {
                    ActivityChooserView.this.getListPopupWindow().dismiss();
                    return;
                }
                ActivityChooserView.this.getListPopupWindow().show();
                ActionProvider actionProvider = ActivityChooserView.this.j;
                if (actionProvider != null) {
                    actionProvider.subUiVisibilityChanged(true);
                }
            }
        }
    }

    public class c extends View.AccessibilityDelegate {
        public c(ActivityChooserView activityChooserView) {
        }

        @Override // android.view.View.AccessibilityDelegate
        public void onInitializeAccessibilityNodeInfo(View view2, AccessibilityNodeInfo accessibilityNodeInfo) {
            super.onInitializeAccessibilityNodeInfo(view2, accessibilityNodeInfo);
            AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCanOpenPopup(true);
        }
    }

    public class d extends ForwardingListener {
        public d(View view2) {
            super(view2);
        }

        @Override // android.support.v7.widget.ForwardingListener
        public ShowableListMenu getPopup() {
            return ActivityChooserView.this.getListPopupWindow();
        }

        @Override // android.support.v7.widget.ForwardingListener
        public boolean onForwardingStarted() {
            ActivityChooserView.this.showPopup();
            return true;
        }

        @Override // android.support.v7.widget.ForwardingListener
        public boolean onForwardingStopped() {
            ActivityChooserView.this.dismissPopup();
            return true;
        }
    }

    public class e extends DataSetObserver {
        public e() {
        }

        @Override // android.database.DataSetObserver
        public void onChanged() {
            super.onChanged();
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (activityChooserView.a.getCount() > 0) {
                activityChooserView.e.setEnabled(true);
            } else {
                activityChooserView.e.setEnabled(false);
            }
            int iB = activityChooserView.a.a.b();
            int iD = activityChooserView.a.a.d();
            if (iB == 1 || (iB > 1 && iD > 0)) {
                activityChooserView.g.setVisibility(0);
                ResolveInfo resolveInfoC = activityChooserView.a.a.c();
                PackageManager packageManager = activityChooserView.getContext().getPackageManager();
                activityChooserView.h.setImageDrawable(resolveInfoC.loadIcon(packageManager));
                if (activityChooserView.r != 0) {
                    activityChooserView.g.setContentDescription(activityChooserView.getContext().getString(activityChooserView.r, resolveInfoC.loadLabel(packageManager)));
                }
            } else {
                activityChooserView.g.setVisibility(8);
            }
            if (activityChooserView.g.getVisibility() == 0) {
                activityChooserView.c.setBackgroundDrawable(activityChooserView.d);
            } else {
                activityChooserView.c.setBackgroundDrawable(null);
            }
        }
    }

    public class f extends BaseAdapter {
        public ActivityChooserModel a;
        public int b = 4;
        public boolean c;
        public boolean d;
        public boolean e;

        public f() {
        }

        @Override // android.widget.Adapter
        public int getCount() {
            int iB = this.a.b();
            if (!this.c && this.a.c() != null) {
                iB--;
            }
            int iMin = Math.min(iB, this.b);
            return this.e ? iMin + 1 : iMin;
        }

        @Override // android.widget.Adapter
        public Object getItem(int i) {
            int itemViewType = getItemViewType(i);
            if (itemViewType != 0) {
                if (itemViewType == 1) {
                    return null;
                }
                throw new IllegalArgumentException();
            }
            if (!this.c && this.a.c() != null) {
                i++;
            }
            return this.a.b(i);
        }

        @Override // android.widget.Adapter
        public long getItemId(int i) {
            return i;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getItemViewType(int i) {
            return (this.e && i == getCount() - 1) ? 1 : 0;
        }

        @Override // android.widget.Adapter
        public View getView(int i, View view2, ViewGroup viewGroup) {
            int itemViewType = getItemViewType(i);
            if (itemViewType != 0) {
                if (itemViewType != 1) {
                    throw new IllegalArgumentException();
                }
                if (view2 != null && view2.getId() == 1) {
                    return view2;
                }
                View viewInflate = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(android.support.v7.appcompat.R.layout.abc_activity_chooser_view_list_item, viewGroup, false);
                viewInflate.setId(1);
                ((TextView) viewInflate.findViewById(android.support.v7.appcompat.R.id.title)).setText(ActivityChooserView.this.getContext().getString(android.support.v7.appcompat.R.string.abc_activity_chooser_view_see_all));
                return viewInflate;
            }
            if (view2 == null || view2.getId() != android.support.v7.appcompat.R.id.list_item) {
                view2 = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(android.support.v7.appcompat.R.layout.abc_activity_chooser_view_list_item, viewGroup, false);
            }
            PackageManager packageManager = ActivityChooserView.this.getContext().getPackageManager();
            ImageView imageView = (ImageView) view2.findViewById(android.support.v7.appcompat.R.id.icon);
            ResolveInfo resolveInfo = (ResolveInfo) getItem(i);
            imageView.setImageDrawable(resolveInfo.loadIcon(packageManager));
            ((TextView) view2.findViewById(android.support.v7.appcompat.R.id.title)).setText(resolveInfo.loadLabel(packageManager));
            if (this.c && i == 0 && this.d) {
                view2.setActivated(true);
            } else {
                view2.setActivated(false);
            }
            return view2;
        }

        @Override // android.widget.BaseAdapter, android.widget.Adapter
        public int getViewTypeCount() {
            return 3;
        }
    }

    public class g implements AdapterView.OnItemClickListener, View.OnClickListener, View.OnLongClickListener, PopupWindow.OnDismissListener {
        public g() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view2) {
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (view2 != activityChooserView.g) {
                if (view2 != activityChooserView.e) {
                    throw new IllegalArgumentException();
                }
                activityChooserView.o = false;
                activityChooserView.a(activityChooserView.p);
                return;
            }
            activityChooserView.dismissPopup();
            Intent intentA = ActivityChooserView.this.a.a.a(ActivityChooserView.this.a.a.a(ActivityChooserView.this.a.a.c()));
            if (intentA != null) {
                intentA.addFlags(524288);
                ActivityChooserView.this.getContext().startActivity(intentA);
            }
        }

        @Override // android.widget.PopupWindow.OnDismissListener
        public void onDismiss() {
            PopupWindow.OnDismissListener onDismissListener = ActivityChooserView.this.n;
            if (onDismissListener != null) {
                onDismissListener.onDismiss();
            }
            ActionProvider actionProvider = ActivityChooserView.this.j;
            if (actionProvider != null) {
                actionProvider.subUiVisibilityChanged(false);
            }
        }

        @Override // android.widget.AdapterView.OnItemClickListener
        public void onItemClick(AdapterView<?> adapterView, View view2, int i, long j) {
            int itemViewType = ((f) adapterView.getAdapter()).getItemViewType(i);
            if (itemViewType != 0) {
                if (itemViewType != 1) {
                    throw new IllegalArgumentException();
                }
                ActivityChooserView.this.a(Integer.MAX_VALUE);
                return;
            }
            ActivityChooserView.this.dismissPopup();
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (activityChooserView.o) {
                if (i > 0) {
                    activityChooserView.a.a.c(i);
                    return;
                }
                return;
            }
            if (!activityChooserView.a.c) {
                i++;
            }
            Intent intentA = ActivityChooserView.this.a.a.a(i);
            if (intentA != null) {
                intentA.addFlags(524288);
                ActivityChooserView.this.getContext().startActivity(intentA);
            }
        }

        @Override // android.view.View.OnLongClickListener
        public boolean onLongClick(View view2) {
            ActivityChooserView activityChooserView = ActivityChooserView.this;
            if (view2 != activityChooserView.g) {
                throw new IllegalArgumentException();
            }
            if (activityChooserView.a.getCount() > 0) {
                ActivityChooserView activityChooserView2 = ActivityChooserView.this;
                activityChooserView2.o = true;
                activityChooserView2.a(activityChooserView2.p);
            }
            return true;
        }
    }

    public ActivityChooserView(Context context) {
        this(context, null);
    }

    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7, types: [boolean, int] */
    public void a(int i) {
        if (this.a.a == null) {
            throw new IllegalStateException("No data model. Did you call #setDataModel?");
        }
        getViewTreeObserver().addOnGlobalLayoutListener(this.l);
        ?? r0 = this.g.getVisibility() == 0 ? 1 : 0;
        int iB = this.a.a.b();
        if (i == Integer.MAX_VALUE || iB <= i + r0) {
            f fVar = this.a;
            if (fVar.e) {
                fVar.e = false;
                fVar.notifyDataSetChanged();
            }
            f fVar2 = this.a;
            if (fVar2.b != i) {
                fVar2.b = i;
                fVar2.notifyDataSetChanged();
            }
        } else {
            f fVar3 = this.a;
            if (!fVar3.e) {
                fVar3.e = true;
                fVar3.notifyDataSetChanged();
            }
            f fVar4 = this.a;
            int i2 = i - 1;
            if (fVar4.b != i2) {
                fVar4.b = i2;
                fVar4.notifyDataSetChanged();
            }
        }
        ListPopupWindow listPopupWindow = getListPopupWindow();
        if (listPopupWindow.isShowing()) {
            return;
        }
        if (this.o || r0 == 0) {
            f fVar5 = this.a;
            if (!fVar5.c || fVar5.d != r0) {
                fVar5.c = true;
                fVar5.d = r0;
                fVar5.notifyDataSetChanged();
            }
        } else {
            f fVar6 = this.a;
            if (fVar6.c || fVar6.d) {
                fVar6.c = false;
                fVar6.d = false;
                fVar6.notifyDataSetChanged();
            }
        }
        f fVar7 = this.a;
        int i3 = fVar7.b;
        fVar7.b = Integer.MAX_VALUE;
        int iMakeMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, 0);
        int iMakeMeasureSpec2 = View.MeasureSpec.makeMeasureSpec(0, 0);
        int count = fVar7.getCount();
        int iMax = 0;
        View view2 = null;
        for (int i4 = 0; i4 < count; i4++) {
            view2 = fVar7.getView(i4, view2, null);
            view2.measure(iMakeMeasureSpec, iMakeMeasureSpec2);
            iMax = Math.max(iMax, view2.getMeasuredWidth());
        }
        fVar7.b = i3;
        listPopupWindow.setContentWidth(Math.min(iMax, this.i));
        listPopupWindow.show();
        ActionProvider actionProvider = this.j;
        if (actionProvider != null) {
            actionProvider.subUiVisibilityChanged(true);
        }
        listPopupWindow.getListView().setContentDescription(getContext().getString(android.support.v7.appcompat.R.string.abc_activitychooserview_choose_application));
        listPopupWindow.getListView().setSelector(new ColorDrawable(0));
    }

    public boolean dismissPopup() {
        if (!isShowingPopup()) {
            return true;
        }
        getListPopupWindow().dismiss();
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (!viewTreeObserver.isAlive()) {
            return true;
        }
        viewTreeObserver.removeGlobalOnLayoutListener(this.l);
        return true;
    }

    public ActivityChooserModel getDataModel() {
        return this.a.a;
    }

    public ListPopupWindow getListPopupWindow() {
        if (this.m == null) {
            ListPopupWindow listPopupWindow = new ListPopupWindow(getContext());
            this.m = listPopupWindow;
            listPopupWindow.setAdapter(this.a);
            this.m.setAnchorView(this);
            this.m.setModal(true);
            this.m.setOnItemClickListener(this.b);
            this.m.setOnDismissListener(this.b);
        }
        return this.m;
    }

    public boolean isShowingPopup() {
        return getListPopupWindow().isShowing();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        ActivityChooserModel activityChooserModel = this.a.a;
        if (activityChooserModel != null) {
            activityChooserModel.registerObserver(this.k);
        }
        this.q = true;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActivityChooserModel activityChooserModel = this.a.a;
        if (activityChooserModel != null) {
            activityChooserModel.unregisterObserver(this.k);
        }
        ViewTreeObserver viewTreeObserver = getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeGlobalOnLayoutListener(this.l);
        }
        if (isShowingPopup()) {
            dismissPopup();
        }
        this.q = false;
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onLayout(boolean z, int i, int i2, int i3, int i4) {
        this.c.layout(0, 0, i3 - i, i4 - i2);
        if (isShowingPopup()) {
            return;
        }
        dismissPopup();
    }

    @Override // android.view.View
    public void onMeasure(int i, int i2) {
        LinearLayoutCompat linearLayoutCompat = this.c;
        if (this.g.getVisibility() != 0) {
            i2 = View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.getSize(i2), 1073741824);
        }
        measureChild(linearLayoutCompat, i, i2);
        setMeasuredDimension(linearLayoutCompat.getMeasuredWidth(), linearLayoutCompat.getMeasuredHeight());
    }

    @Override // android.support.v7.widget.ActivityChooserModel.ActivityChooserModelClient
    public void setActivityChooserModel(ActivityChooserModel activityChooserModel) {
        f fVar = this.a;
        ActivityChooserView activityChooserView = ActivityChooserView.this;
        ActivityChooserModel activityChooserModel2 = activityChooserView.a.a;
        if (activityChooserModel2 != null && activityChooserView.isShown()) {
            activityChooserModel2.unregisterObserver(ActivityChooserView.this.k);
        }
        fVar.a = activityChooserModel;
        if (activityChooserModel != null && ActivityChooserView.this.isShown()) {
            activityChooserModel.registerObserver(ActivityChooserView.this.k);
        }
        fVar.notifyDataSetChanged();
        if (isShowingPopup()) {
            dismissPopup();
            showPopup();
        }
    }

    public void setDefaultActionButtonContentDescription(int i) {
        this.r = i;
    }

    public void setExpandActivityOverflowButtonContentDescription(int i) {
        this.f.setContentDescription(getContext().getString(i));
    }

    public void setExpandActivityOverflowButtonDrawable(Drawable drawable) {
        this.f.setImageDrawable(drawable);
    }

    public void setInitialActivityCount(int i) {
        this.p = i;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.n = onDismissListener;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public void setProvider(ActionProvider actionProvider) {
        this.j = actionProvider;
    }

    public boolean showPopup() {
        if (isShowingPopup() || !this.q) {
            return false;
        }
        this.o = false;
        a(this.p);
        return true;
    }

    public ActivityChooserView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActivityChooserView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.k = new a();
        this.l = new b();
        this.p = 4;
        TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.support.v7.appcompat.R.styleable.ActivityChooserView, i, 0);
        this.p = typedArrayObtainStyledAttributes.getInt(android.support.v7.appcompat.R.styleable.ActivityChooserView_initialActivityCount, 4);
        Drawable drawable = typedArrayObtainStyledAttributes.getDrawable(android.support.v7.appcompat.R.styleable.ActivityChooserView_expandActivityOverflowButtonDrawable);
        typedArrayObtainStyledAttributes.recycle();
        LayoutInflater.from(getContext()).inflate(android.support.v7.appcompat.R.layout.abc_activity_chooser_view, (ViewGroup) this, true);
        this.b = new g();
        LinearLayoutCompat linearLayoutCompat = (LinearLayoutCompat) findViewById(android.support.v7.appcompat.R.id.activity_chooser_view_content);
        this.c = linearLayoutCompat;
        this.d = linearLayoutCompat.getBackground();
        FrameLayout frameLayout = (FrameLayout) findViewById(android.support.v7.appcompat.R.id.default_activity_button);
        this.g = frameLayout;
        frameLayout.setOnClickListener(this.b);
        this.g.setOnLongClickListener(this.b);
        this.h = (ImageView) this.g.findViewById(android.support.v7.appcompat.R.id.image);
        FrameLayout frameLayout2 = (FrameLayout) findViewById(android.support.v7.appcompat.R.id.expand_activities_button);
        frameLayout2.setOnClickListener(this.b);
        frameLayout2.setAccessibilityDelegate(new c(this));
        frameLayout2.setOnTouchListener(new d(frameLayout2));
        this.e = frameLayout2;
        ImageView imageView = (ImageView) frameLayout2.findViewById(android.support.v7.appcompat.R.id.image);
        this.f = imageView;
        imageView.setImageDrawable(drawable);
        f fVar = new f();
        this.a = fVar;
        fVar.registerDataSetObserver(new e());
        Resources resources = context.getResources();
        this.i = Math.max(resources.getDisplayMetrics().widthPixels / 2, resources.getDimensionPixelSize(android.support.v7.appcompat.R.dimen.abc_config_prefDialogWidth));
    }
}
