package fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import application.App;
import de.silpion.mc2.R;
import helper.ActionListener;
import helper.LayoutHelper;
import helper.UsageLogger;
import machineAdapter.ICommandInterface;
import machineAdapter.IMachineCallback;
import machineAdapter.adapter.MachineCallbackAdapter;
import view.QuestionDialogView;

/* loaded from: classes.dex */
public class ScaleCalibrationFragment extends BaseFragment {
    public static final String l = ScaleCalibrationFragment.class.getSimpleName();
    public RelativeLayout d;
    public QuestionDialogView e;
    public QuestionDialogView f;
    public QuestionDialogView g;
    public QuestionDialogView h;
    public QuestionDialogView i;
    public final ICommandInterface c = App.getInstance().getMachineAdapter().getCommandInterface();
    public boolean j = false;
    public final IMachineCallback k = new a();

    public class a extends MachineCallbackAdapter {
        public a() {
        }

        public /* synthetic */ void a(int i, int i2) {
            if (i == 0) {
                ScaleCalibrationFragment.this.c.setScaleCalibration(1);
                ScaleCalibrationFragment.this.j = false;
                return;
            }
            if (i == 1) {
                ScaleCalibrationFragment.this.c.setScaleCalibration(1);
                ScaleCalibrationFragment.this.e.setButtonTwoEnabled(true);
                ScaleCalibrationFragment.this.e.setVisibility(8);
                ScaleCalibrationFragment.this.f.setVisibility(0);
                return;
            }
            if (i == 2) {
                ScaleCalibrationFragment.this.c.setScaleCalibration(1);
                ScaleCalibrationFragment.this.f.setVisibility(8);
                ScaleCalibrationFragment.this.g.setVisibility(0);
                return;
            }
            if (i == 3) {
                ScaleCalibrationFragment.this.c.setScaleCalibration(1);
                ScaleCalibrationFragment.this.g.setVisibility(8);
                ScaleCalibrationFragment.this.h.setVisibility(0);
                return;
            }
            if (i == 4) {
                ScaleCalibrationFragment.this.h.setVisibility(8);
                ScaleCalibrationFragment.this.i.setVisibility(0);
                ScaleCalibrationFragment scaleCalibrationFragment = ScaleCalibrationFragment.this;
                if (scaleCalibrationFragment.j) {
                    return;
                }
                scaleCalibrationFragment.j = true;
                UsageLogger.getInstance().logScaleCalibration();
                return;
            }
            if (i == 7) {
                if (i2 == 2) {
                    ScaleCalibrationFragment.this.c.setScaleCalibration(1);
                }
            } else {
                Log.w(ScaleCalibrationFragment.l, "unexpected ... " + i);
            }
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onScaleStateChanged(final int i, long j, final int i2) {
            super.onScaleStateChanged(i, j, i2);
            Log.i(ScaleCalibrationFragment.l, "Scale >> state " + i + " >> calibration " + i2 + " >> weight " + j);
            ScaleCalibrationFragment.this.d.post(new Runnable() { // from class: ng
                @Override // java.lang.Runnable
                public final void run() {
                    this.a.a(i2, i);
                }
            });
        }
    }

    public /* synthetic */ void a(QuestionDialogView questionDialogView) {
        a();
    }

    public /* synthetic */ void b(QuestionDialogView questionDialogView) {
        App.getInstance().getMachineAdapter().registerMachineCallback(this.k);
        this.c.setScaleCalibration(0);
        this.e.setButtonTwoEnabled(false);
    }

    public /* synthetic */ void c(QuestionDialogView questionDialogView) {
        a();
    }

    public /* synthetic */ void d(QuestionDialogView questionDialogView) {
        a();
    }

    public /* synthetic */ void e(QuestionDialogView questionDialogView) {
        a();
    }

    public /* synthetic */ void f(QuestionDialogView questionDialogView) {
        a();
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.fragment_scale_calibration, viewGroup, false);
        this.d = relativeLayout;
        this.e = (QuestionDialogView) relativeLayout.findViewById(R.id.scale_calib_step_0);
        this.f = (QuestionDialogView) this.d.findViewById(R.id.scale_calib_step_1);
        this.g = (QuestionDialogView) this.d.findViewById(R.id.scale_calib_step_2);
        this.h = (QuestionDialogView) this.d.findViewById(R.id.scale_calib_step_3);
        this.i = (QuestionDialogView) this.d.findViewById(R.id.scale_calib_step_4);
        this.e.setButtonOneClickListener(new ActionListener() { // from class: qg
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((QuestionDialogView) obj);
            }
        });
        this.e.setButtonTwoClickListener(new ActionListener() { // from class: og
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.b((QuestionDialogView) obj);
            }
        });
        this.f.setButtonTwoVisibility(8);
        this.f.setButtonOneClickListener(new ActionListener() { // from class: mg
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.c((QuestionDialogView) obj);
            }
        });
        this.g.setButtonTwoVisibility(8);
        this.g.setButtonOneClickListener(new ActionListener() { // from class: rg
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.d((QuestionDialogView) obj);
            }
        });
        this.h.setButtonTwoVisibility(8);
        this.h.setButtonOneClickListener(new ActionListener() { // from class: sg
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.e((QuestionDialogView) obj);
            }
        });
        this.i.setButtonTwoVisibility(8);
        this.i.setButtonOneClickListener(new ActionListener() { // from class: pg
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.f((QuestionDialogView) obj);
            }
        });
        return this.d;
    }

    public final void a() {
        App.getInstance().getMachineAdapter().unregisterMachineCallback(this.k);
        this.c.setScaleCalibration(2);
        this.e.setVisibility(0);
        this.e.setButtonTwoEnabled(true);
        this.f.setVisibility(8);
        this.g.setVisibility(8);
        this.h.setVisibility(8);
        this.i.setVisibility(8);
        LayoutHelper.getInstance().closeScaleCalibrationFragment();
    }
}
