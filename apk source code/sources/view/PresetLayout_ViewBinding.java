package view;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import application.App;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import de.silpion.mc2.R;
import sound.SoundLength;

/* loaded from: classes.dex */
public class PresetLayout_ViewBinding implements Unbinder {
    public PresetLayout a;
    public View b;
    public View c;
    public View d;
    public View e;

    public class a extends DebouncingOnClickListener {
        public final /* synthetic */ PresetLayout c;

        public a(PresetLayout_ViewBinding presetLayout_ViewBinding, PresetLayout presetLayout) {
            this.c = presetLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            PresetLayout presetLayout = this.c;
            if (presetLayout == null) {
                throw null;
            }
            App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
            presetLayout.a(1);
        }
    }

    public class b extends DebouncingOnClickListener {
        public final /* synthetic */ PresetLayout c;

        public b(PresetLayout_ViewBinding presetLayout_ViewBinding, PresetLayout presetLayout) {
            this.c = presetLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            PresetLayout presetLayout = this.c;
            if (presetLayout == null) {
                throw null;
            }
            App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
            presetLayout.a(3);
        }
    }

    public class c extends DebouncingOnClickListener {
        public final /* synthetic */ PresetLayout c;

        public c(PresetLayout_ViewBinding presetLayout_ViewBinding, PresetLayout presetLayout) {
            this.c = presetLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            PresetLayout presetLayout = this.c;
            if (presetLayout == null) {
                throw null;
            }
            App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
            presetLayout.a(2);
        }
    }

    public class d extends DebouncingOnClickListener {
        public final /* synthetic */ PresetLayout c;

        public d(PresetLayout_ViewBinding presetLayout_ViewBinding, PresetLayout presetLayout) {
            this.c = presetLayout;
        }

        @Override // butterknife.internal.DebouncingOnClickListener
        public void doClick(View view2) {
            PresetLayout presetLayout = this.c;
            if (presetLayout == null) {
                throw null;
            }
            App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
            presetLayout.a();
        }
    }

    @UiThread
    public PresetLayout_ViewBinding(PresetLayout presetLayout) {
        this(presetLayout, presetLayout);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        PresetLayout presetLayout = this.a;
        if (presetLayout == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        presetLayout.kneadingContainerLinearLayout = null;
        presetLayout.roastingContainerLinearLayout = null;
        presetLayout.speedImageView = null;
        presetLayout.speedTextView = null;
        presetLayout.steamingContainerLinearLayout = null;
        presetLayout.temperatureTextView = null;
        presetLayout.timeTextView = null;
        this.b.setOnClickListener(null);
        this.b = null;
        this.c.setOnClickListener(null);
        this.c = null;
        this.d.setOnClickListener(null);
        this.d = null;
        this.e.setOnClickListener(null);
        this.e = null;
    }

    @UiThread
    public PresetLayout_ViewBinding(PresetLayout presetLayout, View view2) {
        this.a = presetLayout;
        View viewFindRequiredView = Utils.findRequiredView(view2, R.id.kneading_container_ll, "field 'kneadingContainerLinearLayout' and method 'onKneadingClick'");
        presetLayout.kneadingContainerLinearLayout = (LinearLayout) Utils.castView(viewFindRequiredView, R.id.kneading_container_ll, "field 'kneadingContainerLinearLayout'", LinearLayout.class);
        this.b = viewFindRequiredView;
        viewFindRequiredView.setOnClickListener(new a(this, presetLayout));
        View viewFindRequiredView2 = Utils.findRequiredView(view2, R.id.roasting_container_ll, "field 'roastingContainerLinearLayout' and method 'onRoastingClick'");
        presetLayout.roastingContainerLinearLayout = (LinearLayout) Utils.castView(viewFindRequiredView2, R.id.roasting_container_ll, "field 'roastingContainerLinearLayout'", LinearLayout.class);
        this.c = viewFindRequiredView2;
        viewFindRequiredView2.setOnClickListener(new b(this, presetLayout));
        presetLayout.speedImageView = (ImageView) Utils.findRequiredViewAsType(view2, R.id.preset_middle_iv, "field 'speedImageView'", ImageView.class);
        presetLayout.speedTextView = (TextView) Utils.findRequiredViewAsType(view2, R.id.preset_middle_tv, "field 'speedTextView'", TextView.class);
        View viewFindRequiredView3 = Utils.findRequiredView(view2, R.id.steaming_container_ll, "field 'steamingContainerLinearLayout' and method 'onSteamingClick'");
        presetLayout.steamingContainerLinearLayout = (LinearLayout) Utils.castView(viewFindRequiredView3, R.id.steaming_container_ll, "field 'steamingContainerLinearLayout'", LinearLayout.class);
        this.d = viewFindRequiredView3;
        viewFindRequiredView3.setOnClickListener(new c(this, presetLayout));
        presetLayout.temperatureTextView = (TextView) Utils.findRequiredViewAsType(view2, R.id.preset_right_tv, "field 'temperatureTextView'", TextView.class);
        presetLayout.timeTextView = (TextView) Utils.findRequiredViewAsType(view2, R.id.preset_left_tv, "field 'timeTextView'", TextView.class);
        View viewFindRequiredView4 = Utils.findRequiredView(view2, R.id.play_iv, "method 'onPlayClick'");
        this.e = viewFindRequiredView4;
        viewFindRequiredView4.setOnClickListener(new d(this, presetLayout));
    }
}
