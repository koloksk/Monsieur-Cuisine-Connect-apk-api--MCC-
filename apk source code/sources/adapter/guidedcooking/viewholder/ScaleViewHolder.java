package adapter.guidedcooking.viewholder;

import adapter.guidedcooking.view.StepViewInfo;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.App;
import butterknife.BindView;
import butterknife.ButterKnife;
import db.model.Recipe;
import de.silpion.mc2.R;
import helper.LayoutHelper;
import helper.Logger;
import machineAdapter.ICommandInterface;
import machineAdapter.adapter.MachineCallbackAdapter;
import sound.SoundLength;
import view.knob.KnobScale;

/* loaded from: classes.dex */
public class ScaleViewHolder extends StepViewHolder implements KnobScale.KnobScaleTareListener {

    @BindView(R.id.item_step_name_tv)
    public TextView nameTextView;

    @BindView(R.id.step_number_rl)
    public RelativeLayout numberContainerRelativeLayout;

    @BindView(R.id.item_step_number_tv)
    public TextView numberTextView;

    @BindView(R.id.knob)
    public KnobScale scaleKnob;

    @BindView(R.id.knob_scale_container)
    public RelativeLayout scaleKnobContainer;
    public TextView t;
    public TextView u;
    public ICommandInterface v;
    public MachineCallbackAdapter w;

    public class a extends MachineCallbackAdapter {
        public final /* synthetic */ View a;

        public a(View view2) {
            this.a = view2;
        }

        public /* synthetic */ void a(int i, long j) {
            KnobScale knobScale;
            ScaleViewHolder scaleViewHolder = ScaleViewHolder.this;
            if (scaleViewHolder.scaleKnobContainer == null || (knobScale = scaleViewHolder.scaleKnob) == null) {
                return;
            }
            if (1 == i) {
                knobScale.setScaleOverloadText();
            } else {
                knobScale.setScaleFromBus((int) j);
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onScaleStateChanged(final int i, final long j, int i2) {
            ScaleViewHolder.p();
            Log.v("ScaleViewHolder", "onScaleStateChange >> state" + i + " >> value " + j);
            this.a.post(new Runnable() { // from class: i1
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.a(i, j);
                }
            });
        }
    }

    public ScaleViewHolder(View view2) {
        super(view2);
        this.v = App.getInstance().getMachineAdapter().getCommandInterface();
        ButterKnife.bind(this, view2);
        ((ImageView) view2.findViewById(R.id.knob_bg_iv)).setImageResource(R.drawable.main_interface_knob_shadow_v3);
        this.scaleKnob.setParent(this.scaleKnobContainer);
        this.scaleKnob.initViews();
        this.scaleKnob.init();
        this.scaleKnob.setKnobScaleTareListener(this);
        this.w = new a(view2);
        this.t = (TextView) view2.findViewById(R.id.recipe_name_horizontal);
        this.u = (TextView) view2.findViewById(R.id.recipe_name_vertical);
    }

    public static /* synthetic */ String p() {
        return "ScaleViewHolder";
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void activate() {
        App.getInstance().getMachineAdapter().registerMachineCallback(this.w);
        this.v.setScaleTare();
        this.v.setScaleTare();
        this.v.setScaleTare();
        this.scaleKnob.setTargetWeightReachedCallback(new Runnable() { // from class: j1
            @Override // java.lang.Runnable
            public final void run() {
                this.a.o();
            }
        });
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void bindStep(StepViewInfo stepViewInfo, Recipe recipe, int i, boolean z) {
        if (z) {
            this.numberContainerRelativeLayout.setVisibility(0);
            this.numberTextView.setText(c(i));
            this.u.setVisibility(0);
            this.t.setVisibility(8);
            this.u.setText(recipe.getName());
        } else {
            this.numberContainerRelativeLayout.setVisibility(8);
            this.u.setVisibility(8);
            this.t.setVisibility(0);
            this.t.setText(recipe.getName());
        }
        this.nameTextView.setText(stepViewInfo.getText());
        int weight = stepViewInfo.getMachineValues().getWeight();
        Logger.i("ScaleViewHolder", "targetWeight: " + weight);
        if (weight > 0) {
            this.scaleKnob.setTargetWeight(weight);
        }
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void deactivate() {
        this.scaleKnob.setTargetWeightReachedCallback(null);
        this.scaleKnob.setTargetWeight(0);
        App.getInstance().getMachineAdapter().unregisterMachineCallback(this.w);
        this.scaleKnob.setTargetWeightReachedCallback(null);
    }

    public /* synthetic */ void o() {
        if (LayoutHelper.getInstance().getSelectedView() == 4) {
            App.getInstance().playSound(R.raw.bell, SoundLength.SHORT);
            n();
        }
    }

    @Override // view.knob.KnobScale.KnobScaleTareListener
    public void onTareClick() {
        this.v.setScaleTare();
        this.v.setScaleTare();
        this.v.setScaleTare();
    }
}
