package adapter.guidedcooking.viewholder;

import adapter.guidedcooking.view.StepViewInfo;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import application.App;
import cooking.Limits;
import db.model.Recipe;
import de.silpion.mc2.R;
import helper.DialogHelper;
import helper.KnobUtils;
import helper.LayoutHelper;
import machineAdapter.ICommandInterface;
import machineAdapter.IMachineCallback;
import machineAdapter.adapter.MachineCallbackAdapter;
import sound.SoundLength;
import view.knob.KnobTime;

/* loaded from: classes.dex */
public class TurboViewHolder extends StepViewHolder {

    @Nullable
    public Handler A;
    public ImageView B;
    public int C;
    public TextView D;
    public long E;

    @Nullable
    public Runnable F;
    public final IMachineCallback G;
    public TextView t;
    public TextView u;
    public RelativeLayout v;
    public ICommandInterface w;
    public KnobTime x;
    public int y;
    public TextView z;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            if (TurboViewHolder.this.p()) {
                TurboViewHolder.this.b(false);
                return;
            }
            TurboViewHolder turboViewHolder = TurboViewHolder.this;
            if (turboViewHolder.C == 0) {
                turboViewHolder.b(true);
            }
            TurboViewHolder turboViewHolder2 = TurboViewHolder.this;
            if (turboViewHolder2.C >= turboViewHolder2.E) {
                KnobUtils.setWarningProgressColor(turboViewHolder2.x);
            }
            TurboViewHolder turboViewHolder3 = TurboViewHolder.this;
            int i = turboViewHolder3.C;
            if (i <= turboViewHolder3.y) {
                int i2 = i + 1;
                turboViewHolder3.C = i2;
                if (turboViewHolder3.A != null) {
                    KnobUtils.updateTurbo(turboViewHolder3.x, i2);
                    TurboViewHolder.this.A.postDelayed(this, 1000L);
                    return;
                }
                return;
            }
            Handler handler = turboViewHolder3.A;
            if (handler != null) {
                handler.removeCallbacks(turboViewHolder3.F);
            }
            TurboViewHolder turboViewHolder4 = TurboViewHolder.this;
            turboViewHolder4.C = 0;
            turboViewHolder4.o();
            TurboViewHolder.this.b(false);
            TurboViewHolder.this.n();
        }
    }

    public class b extends MachineCallbackAdapter {
        public b() {
        }

        public /* synthetic */ void a() {
            App.getInstance().playSound(R.raw.lid_closed, SoundLength.MIDDLE);
            TurboViewHolder.this.a(false, false);
        }

        public /* synthetic */ void b() {
            App.getInstance().playSound(R.raw.lid_opened, SoundLength.MIDDLE);
            TurboViewHolder.this.q();
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onLidClosed() {
            if (LayoutHelper.getInstance().isViewSelected(4)) {
                TurboViewHolder.s();
                Log.v("TurboViewHolder", "onLidClosed");
                TurboViewHolder.this.itemView.post(new Runnable() { // from class: k1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.a.a();
                    }
                });
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onLidOpened() {
            if (LayoutHelper.getInstance().isViewSelected(4)) {
                TurboViewHolder.s();
                Log.v("TurboViewHolder", "onLidOpened");
                TurboViewHolder.this.itemView.post(new Runnable() { // from class: l1
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.a.b();
                    }
                });
            }
        }
    }

    public TurboViewHolder(View view2) {
        super(view2);
        this.w = App.getInstance().getMachineAdapter().getCommandInterface();
        this.E = 8L;
        this.F = new a();
        this.G = new b();
        ((ImageView) view2.findViewById(R.id.knob_bg_iv)).setImageResource(R.drawable.main_interface_knob_shadow_v3);
        this.D = (TextView) view2.findViewById(R.id.item_step_turbo_text);
        this.v = (RelativeLayout) view2.findViewById(R.id.step_number_rl);
        this.z = (TextView) view2.findViewById(R.id.item_step_number_tv);
        this.t = (TextView) view2.findViewById(R.id.recipe_name_horizontal);
        this.u = (TextView) view2.findViewById(R.id.recipe_name_vertical);
        RelativeLayout relativeLayout = (RelativeLayout) view2.findViewById(R.id.knob_left_turbo);
        KnobTime knobTime = (KnobTime) relativeLayout.findViewById(R.id.knob);
        this.x = knobTime;
        knobTime.setParent(relativeLayout);
        this.x.initViews();
        this.x.initTurbo();
        ImageView imageView = (ImageView) view2.findViewById(R.id.turbo_iv);
        this.B = imageView;
        imageView.setOnTouchListener(new View.OnTouchListener() { // from class: m1
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view3, MotionEvent motionEvent) {
                return this.a.a(view3, motionEvent);
            }
        });
    }

    public static /* synthetic */ String s() {
        return "TurboViewHolder";
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void activate() {
        a(false, true);
        App.getInstance().getMachineAdapter().registerMachineCallback(this.G);
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void bindStep(StepViewInfo stepViewInfo, Recipe recipe, int i, boolean z) {
        a(false, true);
        if (z) {
            this.v.setVisibility(0);
            this.z.setText(c(i));
            this.u.setVisibility(0);
            this.t.setVisibility(8);
            this.u.setText(recipe.getName());
        } else {
            this.v.setVisibility(8);
            this.u.setVisibility(8);
            this.t.setVisibility(0);
            this.t.setText(recipe.getName());
        }
        this.y = Math.min((int) stepViewInfo.getMachineValues().getTime(), Limits.TURBO_MAX_SECONDS);
        this.E = Math.min(stepViewInfo.getMachineValues().getTime(), 8L);
        this.x.setTime(0L);
        this.x.valueSetText(this.y * 1000);
        this.D.setText(stepViewInfo.getText());
        this.x.setMaxTimeInMillis(this.y);
    }

    @Override // adapter.guidedcooking.viewholder.StepViewHolder
    public void deactivate() {
        App.getInstance().getMachineAdapter().unregisterMachineCallback(this.G);
        q();
    }

    public final void o() {
        KnobTime knobTime = this.x;
        if (knobTime != null) {
            KnobUtils.resetWarningProgressColor(knobTime);
            this.x.setProgress(0);
        }
    }

    public final boolean p() {
        a(false, true);
        if (this.w.getCurrentHeatingElementState().temperature < 60) {
            return false;
        }
        b(false);
        DialogHelper.getInstance().showTurboDisabledDialog((RelativeLayout) App.getInstance().getMainActivity().findViewById(R.id.dialog_turbo_disabled));
        return true;
    }

    public final void q() {
        Handler handler = this.A;
        if (handler != null) {
            handler.removeCallbacks(this.F);
        }
        this.C = 0;
        this.A = null;
        o();
        b(false);
        a(true, true);
    }

    public final boolean r() {
        return this.w.getCurrentHeatingElementState().temperature < 60;
    }

    public final void b(boolean z) {
        Log.i("TurboViewHolder", "turboBusCommand: start >> " + z);
        ICommandInterface commandInterface = App.getInstance().getMachineAdapter().getCommandInterface();
        if (z) {
            commandInterface.start(2, 10, 1, 0);
        } else {
            commandInterface.stop();
        }
    }

    public /* synthetic */ boolean a(View view2, MotionEvent motionEvent) {
        if (!this.B.isSelected()) {
            p();
        } else if (motionEvent.getAction() == 0) {
            App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
            Handler handler = new Handler();
            this.A = handler;
            this.C = 0;
            handler.postDelayed(this.F, 100L);
        } else if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            Handler handler2 = this.A;
            if (handler2 != null) {
                handler2.removeCallbacks(this.F);
            }
            o();
            this.A = null;
            b(false);
        }
        return true;
    }

    public final void a(boolean z) {
        this.B.setSelected(z);
        this.B.invalidate();
        if (z) {
            if (this.B != null) {
                this.B.setImageDrawable(ContextCompat.getDrawable(App.getInstance(), R.drawable.button_turbo_enabled));
                return;
            }
            return;
        }
        if (this.B != null) {
            this.B.setImageDrawable(ContextCompat.getDrawable(App.getInstance(), R.drawable.button_turbo_disabled));
        }
    }

    public final void a(boolean z, boolean z2) {
        int currentLidState = this.w.getCurrentLidState();
        if (!z2) {
            if (z) {
                a(false);
                return;
            } else {
                a(r());
                return;
            }
        }
        if (1 != currentLidState) {
            a(false);
        } else if (z) {
            a(false);
        } else {
            a(r());
        }
    }
}
