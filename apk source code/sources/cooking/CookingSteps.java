package cooking;

import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class CookingSteps extends CookingStep {

    @NonNull
    public List<SingleCookingStep> a;

    public CookingSteps(SingleCookingStep... singleCookingStepArr) {
        this.a = new ArrayList(Arrays.asList(singleCookingStepArr));
    }

    public void addSingleCookingStep(SingleCookingStep singleCookingStep) {
        this.a.add(singleCookingStep);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Objects.equals(this.a, ((CookingSteps) obj).a);
    }

    @NonNull
    public List<SingleCookingStep> getSingleCookingSteps() {
        return this.a;
    }

    public long getSumOfCookingStepDurationInMillis() {
        long cookingDurationInMillis = 0;
        if (this.a.size() > 0) {
            Iterator<SingleCookingStep> it = this.a.iterator();
            while (it.hasNext()) {
                cookingDurationInMillis += it.next().getCookingDurationInMillis();
            }
        }
        return cookingDurationInMillis;
    }

    public int hashCode() {
        return Objects.hash(this.a);
    }

    public void setSingleCookingSteps(@NonNull List<SingleCookingStep> list) {
        this.a = list;
    }

    @Override // cooking.CookingStep
    /* renamed from: clone */
    public CookingStep mo10clone() {
        SingleCookingStep[] singleCookingStepArr = new SingleCookingStep[this.a.size()];
        for (int i = 0; i < this.a.size(); i++) {
            singleCookingStepArr[i] = new SingleCookingStep(this.a.get(i));
        }
        return new CookingSteps(singleCookingStepArr);
    }
}
