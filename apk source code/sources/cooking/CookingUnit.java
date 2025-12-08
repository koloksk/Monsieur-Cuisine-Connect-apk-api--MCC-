package cooking;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/* loaded from: classes.dex */
public class CookingUnit extends CookingStep {
    public final long a;
    public final String b;
    public final List<SingleCookingStep> c;
    public long d;

    public CookingUnit(long j, long j2, List<SingleCookingStep> list, String str) {
        ArrayList arrayList = new ArrayList();
        this.c = arrayList;
        this.d = j;
        this.a = j2;
        if (list != null) {
            arrayList.addAll(list);
        }
        this.b = str;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || CookingUnit.class != obj.getClass()) {
            return false;
        }
        CookingUnit cookingUnit = (CookingUnit) obj;
        return this.d == cookingUnit.d && this.a == cookingUnit.a && Objects.equals(this.c, cookingUnit.c);
    }

    public long getCookingUnitDurationInMillis() {
        return this.d;
    }

    public long getCookingUnitDurationLimitInMillis() {
        return this.a;
    }

    public List<SingleCookingStep> getSingleCookingSteps() {
        return this.c;
    }

    public long getSumOfCookingStepDurationInMillis() {
        List<SingleCookingStep> list = this.c;
        long cookingDurationInMillis = 0;
        if (list != null && list.size() > 0) {
            Iterator<SingleCookingStep> it = this.c.iterator();
            while (it.hasNext()) {
                cookingDurationInMillis += it.next().getCookingDurationInMillis();
            }
        }
        return cookingDurationInMillis;
    }

    public String getTag() {
        return this.b;
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.d), Long.valueOf(this.a), this.c);
    }

    public void setCookingUnitDurationInMillis(long j) {
        this.d = j;
    }

    @Override // cooking.CookingStep
    /* renamed from: clone */
    public CookingStep mo10clone() {
        ArrayList arrayList = new ArrayList();
        Iterator<SingleCookingStep> it = this.c.iterator();
        while (it.hasNext()) {
            arrayList.add(new SingleCookingStep(it.next()));
        }
        return new CookingUnit(this.d, this.a, arrayList, this.b);
    }
}
