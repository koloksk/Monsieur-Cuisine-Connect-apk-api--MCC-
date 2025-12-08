package adapter.guidedcooking;

import adapter.guidedcooking.view.RampStepViewInfo;
import adapter.guidedcooking.view.SingleStepViewInfo;
import adapter.guidedcooking.view.StepViewInfo;
import adapter.guidedcooking.viewholder.CookingViewHolder;
import adapter.guidedcooking.viewholder.EndViewHolder;
import adapter.guidedcooking.viewholder.InstructionViewHolder;
import adapter.guidedcooking.viewholder.PreparationViewHolder;
import adapter.guidedcooking.viewholder.ScaleViewHolder;
import adapter.guidedcooking.viewholder.StepViewHolder;
import adapter.guidedcooking.viewholder.TurboViewHolder;
import adapter.guidedcooking.viewholder.WaitViewHolder;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import db.model.Recipe;
import db.model.Step;
import db.model.StepMode;
import de.silpion.mc2.R;
import helper.ActionListener;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import mcapi.McApi;

/* loaded from: classes.dex */
public class GuidedCookingStepsAdapter extends RecyclerView.Adapter<StepViewHolder> {
    public LayoutInflater c;
    public StepViewHolder d;
    public int e = 0;
    public Recipe f;
    public Runnable g;
    public List<StepViewInfo> h;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewType {
        public static final int COOKING = 2;
        public static final int END = 10;
        public static final int INSTRUCTION = 0;
        public static final int PREPARATION = 1;
        public static final int RAMP = 9;
        public static final int SCALE = 7;
        public static final int TURBO = 6;
        public static final int WAIT = 8;
    }

    public GuidedCookingStepsAdapter(Context context, Recipe recipe) {
        this.c = LayoutInflater.from(context);
        this.f = recipe;
        ArrayList arrayList = new ArrayList();
        RampStepViewInfo rampStepViewInfo = null;
        boolean z = false;
        for (Step step : recipe.getSteps()) {
            String mode = step.getMode();
            char c = 65535;
            int iHashCode = mode.hashCode();
            if (iHashCode != 100571) {
                if (iHashCode == 3492882 && mode.equals(StepMode.RAMP)) {
                    c = 0;
                }
            } else if (mode.equals(StepMode.END)) {
                c = 1;
            }
            if (c != 0) {
                if (c != 1) {
                    arrayList.add(new SingleStepViewInfo(step));
                } else {
                    arrayList.add(new SingleStepViewInfo(step));
                    z = true;
                }
            } else if (rampStepViewInfo == null || rampStepViewInfo.getStepIndex() != step.getStep()) {
                rampStepViewInfo = new RampStepViewInfo(step);
                arrayList.add(rampStepViewInfo);
            } else {
                rampStepViewInfo.addStep(step);
            }
        }
        if (!z) {
            Step step2 = new Step();
            step2.setMode(StepMode.END);
            arrayList.add(new SingleStepViewInfo(step2));
        }
        this.h = arrayList;
    }

    public /* synthetic */ void a(StepViewHolder stepViewHolder) {
        Runnable runnable = this.g;
        if (runnable != null) {
            runnable.run();
        }
    }

    public synchronized void activateStep(StepViewHolder stepViewHolder) {
        if (stepViewHolder == this.d) {
            return;
        }
        if (this.d != null) {
            this.d.deactivate();
            this.d = null;
        }
        if (stepViewHolder != null) {
            stepViewHolder.activate();
            this.d = stepViewHolder;
        }
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.h.size();
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        String mode = this.h.get(i).getMode();
        if (mode == null) {
            return 0;
        }
        switch (mode) {
        }
        return 0;
    }

    public void setOrientation(int i) {
        if (i == this.e) {
            return;
        }
        this.e = i;
        notifyDataSetChanged();
    }

    public void setRecipeIsFavorite(boolean z) {
        this.f.setIsFavorite(z);
        this.f.update();
        McApi.getInstance().setIsFavorite(this.f.getId(), z);
    }

    public void setStepDoneCallback(Runnable runnable) {
        this.g = runnable;
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(StepViewHolder stepViewHolder, int i) {
        Log.i("GuidedCookingStepsAdapter", "onBindViewHolder " + i);
        stepViewHolder.bindStep(this.h.get(i), this.f, i, this.e == 1);
        stepViewHolder.setStepDoneListener(new ActionListener() { // from class: v0
            @Override // helper.ActionListener
            public final void onAction(Object obj) {
                this.a.a((StepViewHolder) obj);
            }
        });
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public StepViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 0) {
            return new InstructionViewHolder(this.c.inflate(R.layout.item_step_instruction, viewGroup, false));
        }
        if (i == 1) {
            return new PreparationViewHolder(this.c.inflate(R.layout.item_step_instruction, viewGroup, false));
        }
        if (i != 2) {
            switch (i) {
                case 6:
                    return new TurboViewHolder(this.c.inflate(R.layout.item_step_turbo, viewGroup, false));
                case 7:
                    return new ScaleViewHolder(this.c.inflate(R.layout.item_step_scale, viewGroup, false));
                case 8:
                    return new WaitViewHolder(this.c.inflate(R.layout.item_step, viewGroup, false));
                case 9:
                    break;
                case 10:
                    return new EndViewHolder(this, this.c.inflate(R.layout.item_step_end_layout, viewGroup, false));
                default:
                    return new InstructionViewHolder(this.c.inflate(R.layout.item_step, viewGroup, false));
            }
        }
        return new CookingViewHolder(this.c.inflate(R.layout.item_step_cooking, viewGroup, false));
    }

    @Override // android.support.v7.widget.RecyclerView.Adapter
    public void onViewDetachedFromWindow(StepViewHolder stepViewHolder) {
        StepViewHolder stepViewHolder2 = this.d;
        if (stepViewHolder2 != null && stepViewHolder == stepViewHolder2) {
            Log.w("GuidedCookingStepsAdapter", "onViewDetachedFromWindow >> deactivating detached active step");
            this.d.deactivate();
            this.d = null;
        }
        super.onViewDetachedFromWindow((GuidedCookingStepsAdapter) stepViewHolder);
    }
}
