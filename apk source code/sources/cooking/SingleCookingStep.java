package cooking;

import defpackage.g9;
import java.util.Objects;
import model.MachineConfiguration;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class SingleCookingStep extends CookingStep {
    public long a;
    public String b;
    public boolean c;
    public MachineConfiguration d;
    public boolean e;
    public boolean f;
    public String g;
    public boolean h;
    public int i;
    public String j;
    public boolean k;
    public int l;
    public String m;
    public boolean n;
    public long o;
    public boolean p;
    public String q;

    public static class Builder {
        public SingleCookingStep a = new SingleCookingStep((a) null);

        public SingleCookingStep build() {
            SingleCookingStep singleCookingStep = this.a;
            this.a = null;
            return singleCookingStep;
        }

        public Builder cookingDurationInMillis(long j) {
            this.a.a = j;
            return this;
        }

        public Builder customInfo(String str) {
            this.a.q = str;
            return this;
        }

        public Builder description(String str) {
            this.a.b = str;
            return this;
        }

        public Builder directionModifiable(boolean z) {
            this.a.c = z;
            return this;
        }

        public Builder machineConfiguration(MachineConfiguration machineConfiguration) {
            this.a.d = machineConfiguration;
            return this;
        }

        public Builder preheatStep(boolean z) {
            this.a.e = z;
            return this;
        }

        public Builder scaleEnabled(boolean z) {
            this.a.f = z;
            return this;
        }

        public Builder speedDescription(String str) {
            this.a.g = str;
            return this;
        }

        public Builder speedKnobModifiable(boolean z) {
            this.a.h = z;
            return this;
        }

        public Builder speedLimit(int i) {
            this.a.i = i;
            return this;
        }

        public Builder temperatureDescription(String str) {
            this.a.j = str;
            return this;
        }

        public Builder temperatureKnobModifiable(boolean z) {
            this.a.k = z;
            return this;
        }

        public Builder temperatureLimit(int i) {
            this.a.l = i;
            return this;
        }

        public Builder timeDescription(String str) {
            this.a.m = str;
            return this;
        }

        public Builder timeKnobModifiable(boolean z) {
            this.a.n = z;
            return this;
        }

        public Builder timeLimitInMillis(long j) {
            this.a.o = j;
            return this;
        }

        public Builder turboEnabled(boolean z) {
            this.a.p = z;
            return this;
        }
    }

    public SingleCookingStep() {
        this.a = 0L;
        this.b = "";
        this.c = true;
        this.e = false;
        this.f = false;
        this.g = "";
        this.h = true;
        this.i = 10;
        this.j = "";
        this.k = true;
        this.l = -1;
        this.m = "";
        this.n = true;
        this.o = -1L;
        this.p = false;
        this.q = "";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || SingleCookingStep.class != obj.getClass()) {
            return false;
        }
        SingleCookingStep singleCookingStep = (SingleCookingStep) obj;
        return this.a == singleCookingStep.a && this.p == singleCookingStep.p && this.f == singleCookingStep.f && this.c == singleCookingStep.c && this.n == singleCookingStep.n && this.o == singleCookingStep.o && this.h == singleCookingStep.h && this.i == singleCookingStep.i && this.k == singleCookingStep.k && this.l == singleCookingStep.l && this.e == singleCookingStep.e && Objects.equals(this.d, singleCookingStep.d) && Objects.equals(this.b, singleCookingStep.b) && Objects.equals(this.m, singleCookingStep.m) && Objects.equals(this.g, singleCookingStep.g) && Objects.equals(this.j, singleCookingStep.j) && Objects.equals(this.q, singleCookingStep.q);
    }

    public long getCookingDurationInMillis() {
        return this.a;
    }

    public String getCustomInfo() {
        return this.q;
    }

    public String getDescription() {
        return this.b;
    }

    public MachineConfiguration getMachineConfiguration() {
        return this.d;
    }

    public String getSpeedDescription() {
        return this.g;
    }

    public int getSpeedLimit() {
        return this.i;
    }

    public String getTemperatureDescription() {
        return this.j;
    }

    public int getTemperatureLimit() {
        return this.l;
    }

    public String getTimeDescription() {
        return this.m;
    }

    public long getTimeLimitInMillis() {
        return this.o;
    }

    public boolean hasCustomInfo() {
        return !StringUtils.isEmpty(this.q);
    }

    public int hashCode() {
        return Objects.hash(Long.valueOf(this.a), this.d, this.b, Boolean.valueOf(this.p), Boolean.valueOf(this.f), Boolean.valueOf(this.c), Boolean.valueOf(this.n), this.m, Long.valueOf(this.o), Boolean.valueOf(this.h), this.g, Integer.valueOf(this.i), Boolean.valueOf(this.k), this.j, Integer.valueOf(this.l));
    }

    public boolean isDirectionModifiable() {
        return this.c;
    }

    public boolean isPreheatStep() {
        return this.e;
    }

    public boolean isScaleEnabled() {
        return this.f;
    }

    public boolean isSpeedKnobModifiable() {
        return this.h;
    }

    public boolean isTemperatureKnobModifiable() {
        return this.k;
    }

    public boolean isTimeKnobModifiable() {
        return this.n;
    }

    public boolean isTurboEnabled() {
        return this.p;
    }

    public void setCookingDurationInMillis(long j) {
        this.a = j;
    }

    public void setDescription(String str) {
        this.b = str;
    }

    public void setDirectionModifiable(boolean z) {
        this.c = z;
    }

    public void setMachineConfiguration(MachineConfiguration machineConfiguration) {
        this.d = machineConfiguration;
    }

    public void setPreheatStep(boolean z) {
        this.e = z;
    }

    public void setScaleEnabled(boolean z) {
        this.f = z;
    }

    public void setSpeedDescription(String str) {
        this.g = str;
    }

    public void setSpeedKnobModifiable(boolean z) {
        this.h = z;
    }

    public void setSpeedLimit(int i) {
        this.i = i;
    }

    public void setTemperatureDescription(String str) {
        this.j = str;
    }

    public void setTemperatureKnobModifiable(boolean z) {
        this.k = z;
    }

    public void setTemperatureLimit(int i) {
        this.l = i;
    }

    public void setTimeDescription(String str) {
        this.m = str;
    }

    public void setTimeKnobModifiable(boolean z) {
        this.n = z;
    }

    public void setTimeLimitInMillis(long j) {
        this.o = j;
    }

    public void setTurboEnabled(boolean z) {
        this.p = z;
    }

    public String toString() {
        StringBuilder sbA = g9.a("SingleCookingStep{cookingDurationInMillis=");
        sbA.append(this.a);
        sbA.append(", machineConfiguration=");
        sbA.append(this.d);
        sbA.append(", description='");
        g9.a(sbA, this.b, '\'', ", turboEnabled=");
        sbA.append(this.p);
        sbA.append(", scaleEnabled=");
        sbA.append(this.f);
        sbA.append(", directionModifiable=");
        sbA.append(this.c);
        sbA.append(", timeKnobModifiable=");
        sbA.append(this.n);
        sbA.append(", timeDescription='");
        g9.a(sbA, this.m, '\'', ", timeLimitInMillis=");
        sbA.append(this.o);
        sbA.append(", speedKnobModifiable=");
        sbA.append(this.h);
        sbA.append(", speedDescription='");
        g9.a(sbA, this.g, '\'', ", speedLimit=");
        sbA.append(this.i);
        sbA.append(", temperatureKnobModifiable=");
        sbA.append(this.k);
        sbA.append(", temperatureDescription='");
        g9.a(sbA, this.j, '\'', ", temperatureLimit=");
        sbA.append(this.l);
        sbA.append(", preheatStep=");
        sbA.append(this.e);
        sbA.append(", customInfo=");
        sbA.append(this.q);
        sbA.append('}');
        return sbA.toString();
    }

    @Override // cooking.CookingStep
    /* renamed from: clone */
    public CookingStep mo10clone() {
        return new SingleCookingStep(this);
    }

    public /* synthetic */ SingleCookingStep(a aVar) {
        this.a = 0L;
        this.b = "";
        this.c = true;
        this.e = false;
        this.f = false;
        this.g = "";
        this.h = true;
        this.i = 10;
        this.j = "";
        this.k = true;
        this.l = -1;
        this.m = "";
        this.n = true;
        this.o = -1L;
        this.p = false;
        this.q = "";
    }

    public SingleCookingStep(SingleCookingStep singleCookingStep) {
        this.a = 0L;
        this.b = "";
        this.c = true;
        this.e = false;
        this.f = false;
        this.g = "";
        this.h = true;
        this.i = 10;
        this.j = "";
        this.k = true;
        this.l = -1;
        this.m = "";
        this.n = true;
        this.o = -1L;
        this.p = false;
        this.q = "";
        if (singleCookingStep != null) {
            this.a = singleCookingStep.a;
            this.d = new MachineConfiguration(singleCookingStep.d);
            this.b = singleCookingStep.b;
            this.p = singleCookingStep.p;
            this.f = singleCookingStep.f;
            this.c = singleCookingStep.c;
            this.n = singleCookingStep.n;
            this.m = singleCookingStep.m;
            this.o = singleCookingStep.o;
            this.h = singleCookingStep.h;
            this.g = singleCookingStep.g;
            this.i = singleCookingStep.i;
            this.k = singleCookingStep.k;
            this.j = singleCookingStep.j;
            this.e = singleCookingStep.e;
            this.l = singleCookingStep.l;
            this.q = singleCookingStep.q;
        }
    }
}
