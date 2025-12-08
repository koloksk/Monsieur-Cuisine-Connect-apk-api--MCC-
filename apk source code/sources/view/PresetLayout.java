package view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.App;
import butterknife.BindView;
import butterknife.ButterKnife;
import cooking.CookingStep;
import de.silpion.mc2.R;
import helper.DataHolder;
import helper.KnobUtils;
import helper.LayoutHelper;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import machineAdapter.IMachineCallback;
import machineAdapter.adapter.MachineCallbackAdapter;
import model.MachineValues;
import model.Presets;

/* loaded from: classes.dex */
public class PresetLayout extends RelativeLayout {
    public static final String d = PresetLayout.class.getSimpleName();
    public final IMachineCallback a;
    public int b;
    public PresetPlayListener c;

    @BindView(R.id.kneading_container_ll)
    public LinearLayout kneadingContainerLinearLayout;

    @BindView(R.id.roasting_container_ll)
    public LinearLayout roastingContainerLinearLayout;

    @BindView(R.id.preset_middle_iv)
    public ImageView speedImageView;

    @BindView(R.id.preset_middle_tv)
    public TextView speedTextView;

    @BindView(R.id.steaming_container_ll)
    public LinearLayout steamingContainerLinearLayout;

    @BindView(R.id.preset_right_tv)
    public TextView temperatureTextView;

    @BindView(R.id.preset_left_tv)
    public TextView timeTextView;

    public interface PresetPlayListener {
        void onPresetSelected(CookingStep cookingStep);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Selection {
        public static final int KNEADING = 1;
        public static final int ROASTING = 3;
        public static final int STEAMING = 2;
    }

    public class b extends MachineCallbackAdapter {
        public /* synthetic */ b(a aVar) {
        }

        public /* synthetic */ void a() {
            PresetLayout presetLayout = PresetLayout.this;
            int i = presetLayout.b;
            if (i == 1) {
                presetLayout.a(2);
            } else if (i == 2) {
                presetLayout.a(3);
            }
        }

        public /* synthetic */ void b() {
            PresetLayout presetLayout = PresetLayout.this;
            int i = presetLayout.b;
            if (i == 2) {
                presetLayout.a(1);
            } else if (i == 3) {
                presetLayout.a(2);
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onJogDialPushed(int i) {
            if (LayoutHelper.getInstance().isViewSelected(1)) {
                final PresetLayout presetLayout = PresetLayout.this;
                presetLayout.post(new Runnable() { // from class: rp
                    @Override // java.lang.Runnable
                    public final void run() {
                        presetLayout.a();
                    }
                });
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onJogDialTurned(int i, long j) {
            super.onJogDialTurned(i, j);
            if (LayoutHelper.getInstance().isViewSelected(1)) {
                Log.d(PresetLayout.d, "onJogDialTurned: " + i);
                if (1 == i) {
                    PresetLayout.this.post(new Runnable() { // from class: qp
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.a.a();
                        }
                    });
                } else {
                    PresetLayout.this.post(new Runnable() { // from class: sp
                        @Override // java.lang.Runnable
                        public final void run() {
                            this.a.b();
                        }
                    });
                }
            }
        }
    }

    public PresetLayout(Context context) {
        super(context);
        this.a = new b(null);
        this.b = 1;
    }

    public void init() {
        Log.d(d, "init");
        a(1);
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            App.getInstance().getMachineAdapter().registerMachineCallback(this.a);
        }
        ButterKnife.bind(this, this);
        init();
    }

    @Override // android.view.ViewGroup, android.view.View
    public void onDetachedFromWindow() {
        App.getInstance().getMachineAdapter().unregisterMachineCallback(this.a);
        super.onDetachedFromWindow();
    }

    public void setPresetPlayListener(PresetPlayListener presetPlayListener) {
        this.c = presetPlayListener;
    }

    public final void a(int i) {
        MachineValues machineValuesKneadingMachineValues;
        this.b = i;
        a(this.kneadingContainerLinearLayout, i == 1);
        a(this.steamingContainerLinearLayout, this.b == 2);
        a(this.roastingContainerLinearLayout, this.b == 3);
        if (i == 1) {
            machineValuesKneadingMachineValues = Presets.kneadingMachineValues();
        } else if (i == 2) {
            machineValuesKneadingMachineValues = Presets.steamingMachineValues();
        } else if (i != 3) {
            return;
        } else {
            machineValuesKneadingMachineValues = Presets.roastingMachineValues();
        }
        this.speedImageView.setSelected(machineValuesKneadingMachineValues.getDirection() == 0);
        this.timeTextView.setText(KnobUtils.getMinSecString(machineValuesKneadingMachineValues.getTimeInMillis()));
        this.speedTextView.setText(String.valueOf(machineValuesKneadingMachineValues.getSpeed()));
        this.temperatureTextView.setText(String.valueOf(machineValuesKneadingMachineValues.getTemperature()));
    }

    public PresetLayout(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.a = new b(null);
        this.b = 1;
    }

    public PresetLayout(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.a = new b(null);
        this.b = 1;
    }

    public final void a(ViewGroup viewGroup, boolean z) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            viewGroup.getChildAt(i).setSelected(z);
        }
    }

    public final void a() {
        if (this.c == null) {
            Log.w(d, "no preset selection listener .. can't do anything.");
            return;
        }
        int i = this.b;
        if (i == 1) {
            DataHolder.getInstance().setPresetMachineValues(Presets.kneadingMachineValues());
            this.c.onPresetSelected(Presets.kneadingCookingUnit());
        } else if (i == 2) {
            DataHolder.getInstance().setPresetMachineValues(Presets.steamingMachineValues());
            this.c.onPresetSelected(Presets.steamingCookingSteps());
        } else if (i == 3) {
            DataHolder.getInstance().setPresetMachineValues(Presets.roastingMachineValues());
            this.c.onPresetSelected(Presets.roastingCookingUnit());
        }
        setVisibility(8);
    }
}
