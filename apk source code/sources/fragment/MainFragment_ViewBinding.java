package fragment;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import de.silpion.mc2.R;

/* loaded from: classes.dex */
public class MainFragment_ViewBinding implements Unbinder {
    public MainFragment a;

    @UiThread
    public MainFragment_ViewBinding(MainFragment mainFragment, View view2) {
        this.a = mainFragment;
        mainFragment.controlRelativeLayout = (RelativeLayout) Utils.findOptionalViewAsType(view2, R.id.cooking_controls_container, "field 'controlRelativeLayout'", RelativeLayout.class);
        mainFragment.knobLeftContainer = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.knob_left, "field 'knobLeftContainer'", RelativeLayout.class);
        mainFragment.knobLeftTurboContainer = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.knob_left_turbo, "field 'knobLeftTurboContainer'", RelativeLayout.class);
        mainFragment.knobMiddleContainer = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.knob_middle, "field 'knobMiddleContainer'", RelativeLayout.class);
        mainFragment.knobMiddleTurboContainer = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.knob_middle_turbo, "field 'knobMiddleTurboContainer'", RelativeLayout.class);
        mainFragment.knobRightContainer = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.knob_right, "field 'knobRightContainer'", RelativeLayout.class);
        mainFragment.scaleContainer = (RelativeLayout) Utils.findRequiredViewAsType(view2, R.id.scale_container, "field 'scaleContainer'", RelativeLayout.class);
    }

    @Override // butterknife.Unbinder
    @CallSuper
    public void unbind() {
        MainFragment mainFragment = this.a;
        if (mainFragment == null) {
            throw new IllegalStateException("Bindings already cleared.");
        }
        this.a = null;
        mainFragment.controlRelativeLayout = null;
        mainFragment.knobLeftContainer = null;
        mainFragment.knobLeftTurboContainer = null;
        mainFragment.knobMiddleContainer = null;
        mainFragment.knobMiddleTurboContainer = null;
        mainFragment.knobRightContainer = null;
        mainFragment.scaleContainer = null;
    }
}
