package android.support.v7.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.view.ActionProvider;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.ActivityChooserModel;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;

/* loaded from: classes.dex */
public class ShareActionProvider extends ActionProvider {
    public static final String DEFAULT_SHARE_HISTORY_FILE_NAME = "share_history.xml";
    public int d;
    public final b e;
    public final Context f;
    public String g;
    public OnShareTargetSelectedListener h;
    public ActivityChooserModel.OnChooseActivityListener i;

    public interface OnShareTargetSelectedListener {
        boolean onShareTargetSelected(ShareActionProvider shareActionProvider, Intent intent);
    }

    public class a implements ActivityChooserModel.OnChooseActivityListener {
        public a() {
        }

        @Override // android.support.v7.widget.ActivityChooserModel.OnChooseActivityListener
        public boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent) {
            ShareActionProvider shareActionProvider = ShareActionProvider.this;
            OnShareTargetSelectedListener onShareTargetSelectedListener = shareActionProvider.h;
            if (onShareTargetSelectedListener == null) {
                return false;
            }
            onShareTargetSelectedListener.onShareTargetSelected(shareActionProvider, intent);
            return false;
        }
    }

    public class b implements MenuItem.OnMenuItemClickListener {
        public b() {
        }

        @Override // android.view.MenuItem.OnMenuItemClickListener
        public boolean onMenuItemClick(MenuItem menuItem) {
            ShareActionProvider shareActionProvider = ShareActionProvider.this;
            Intent intentA = ActivityChooserModel.a(shareActionProvider.f, shareActionProvider.g).a(menuItem.getItemId());
            if (intentA == null) {
                return true;
            }
            String action = intentA.getAction();
            if ("android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action)) {
                if (ShareActionProvider.this == null) {
                    throw null;
                }
                intentA.addFlags(134742016);
            }
            ShareActionProvider.this.f.startActivity(intentA);
            return true;
        }
    }

    public ShareActionProvider(Context context) {
        super(context);
        this.d = 4;
        this.e = new b();
        this.g = DEFAULT_SHARE_HISTORY_FILE_NAME;
        this.f = context;
    }

    public final void a() {
        if (this.h == null) {
            return;
        }
        if (this.i == null) {
            this.i = new a();
        }
        ActivityChooserModel.a(this.f, this.g).a(this.i);
    }

    @Override // android.support.v4.view.ActionProvider
    public boolean hasSubMenu() {
        return true;
    }

    @Override // android.support.v4.view.ActionProvider
    public View onCreateActionView() {
        ActivityChooserView activityChooserView = new ActivityChooserView(this.f);
        if (!activityChooserView.isInEditMode()) {
            activityChooserView.setActivityChooserModel(ActivityChooserModel.a(this.f, this.g));
        }
        TypedValue typedValue = new TypedValue();
        this.f.getTheme().resolveAttribute(R.attr.actionModeShareDrawable, typedValue, true);
        activityChooserView.setExpandActivityOverflowButtonDrawable(AppCompatResources.getDrawable(this.f, typedValue.resourceId));
        activityChooserView.setProvider(this);
        activityChooserView.setDefaultActionButtonContentDescription(R.string.abc_shareactionprovider_share_with_application);
        activityChooserView.setExpandActivityOverflowButtonContentDescription(R.string.abc_shareactionprovider_share_with);
        return activityChooserView;
    }

    @Override // android.support.v4.view.ActionProvider
    public void onPrepareSubMenu(SubMenu subMenu) {
        subMenu.clear();
        ActivityChooserModel activityChooserModelA = ActivityChooserModel.a(this.f, this.g);
        PackageManager packageManager = this.f.getPackageManager();
        int iB = activityChooserModelA.b();
        int iMin = Math.min(iB, this.d);
        for (int i = 0; i < iMin; i++) {
            ResolveInfo resolveInfoB = activityChooserModelA.b(i);
            subMenu.add(0, i, i, resolveInfoB.loadLabel(packageManager)).setIcon(resolveInfoB.loadIcon(packageManager)).setOnMenuItemClickListener(this.e);
        }
        if (iMin < iB) {
            SubMenu subMenuAddSubMenu = subMenu.addSubMenu(0, iMin, iMin, this.f.getString(R.string.abc_activity_chooser_view_see_all));
            for (int i2 = 0; i2 < iB; i2++) {
                ResolveInfo resolveInfoB2 = activityChooserModelA.b(i2);
                subMenuAddSubMenu.add(0, i2, i2, resolveInfoB2.loadLabel(packageManager)).setIcon(resolveInfoB2.loadIcon(packageManager)).setOnMenuItemClickListener(this.e);
            }
        }
    }

    public void setOnShareTargetSelectedListener(OnShareTargetSelectedListener onShareTargetSelectedListener) {
        this.h = onShareTargetSelectedListener;
        a();
    }

    public void setShareHistoryFileName(String str) {
        this.g = str;
        a();
    }

    public void setShareIntent(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            if ("android.intent.action.SEND".equals(action) || "android.intent.action.SEND_MULTIPLE".equals(action)) {
                intent.addFlags(134742016);
            }
        }
        ActivityChooserModel.a(this.f, this.g).a(intent);
    }
}
