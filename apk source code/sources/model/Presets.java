package model;

import application.App;
import cooking.CookingSteps;
import cooking.CookingUnit;
import cooking.Limits;
import cooking.SingleCookingStep;
import de.silpion.mc2.R;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import model.MachineValues;
import org.apache.commons.lang3.time.DateUtils;

/* loaded from: classes.dex */
public final class Presets {
    public static final int KNEADING_MAX_TIME = 120;
    public static final long KNEADING_TIME_IDLE = 1;
    public static final long KNEADING_TIME_LEFT = 15;
    public static final long KNEADING_TIME_RIGHT = 29;
    public static final int a = Limits.TEMPERATURE_LEVEL_MAX;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Tags {
        public static final String KNEADING = "KNEADING";
        public static final String ROASTING = "ROASTING";
        public static final String STEAMING = "STEAMING";
    }

    public static SingleCookingStep defaultCookingStep() {
        return new SingleCookingStep.Builder().cookingDurationInMillis(0L).machineConfiguration(defaultMachineConfiguration()).turboEnabled(true).directionModifiable(true).scaleEnabled(true).build();
    }

    public static MachineConfiguration defaultMachineConfiguration() {
        return new MachineConfiguration(1, 0, 1, 0);
    }

    public static CookingUnit kneadingCookingUnit() {
        return new CookingUnit(45000L, 120000L, Arrays.asList(new SingleCookingStep.Builder().cookingDurationInMillis(15000L).machineConfiguration(new MachineConfiguration(5, 4, 0, 0)).description(App.getInstance().getBaseContext().getString(R.string.kneading)).turboEnabled(false).directionModifiable(false).scaleEnabled(true).timeKnobModifiable(true).timeDescription("").timeLimitInMillis(120000L).speedKnobModifiable(false).speedDescription("").speedLimit(4).temperatureKnobModifiable(false).temperatureDescription("").temperatureLimit(-1).build(), new SingleCookingStep.Builder().cookingDurationInMillis(1000L).machineConfiguration(new MachineConfiguration(5, 0, 0, 0)).description(App.getInstance().getBaseContext().getString(R.string.kneading)).turboEnabled(false).directionModifiable(false).scaleEnabled(true).timeKnobModifiable(true).timeDescription("").timeLimitInMillis(120000L).speedKnobModifiable(false).speedDescription("").speedLimit(0).temperatureKnobModifiable(false).temperatureDescription("").temperatureLimit(-1).customInfo(App.getInstance().getBaseContext().getString(R.string.rest_phase)).build(), new SingleCookingStep.Builder().cookingDurationInMillis(104000L).machineConfiguration(new MachineConfiguration(5, 4, 1, 0)).description(App.getInstance().getBaseContext().getString(R.string.kneading)).turboEnabled(false).directionModifiable(false).scaleEnabled(true).timeKnobModifiable(true).timeDescription("").timeLimitInMillis(120000L).speedKnobModifiable(false).speedDescription("").speedLimit(4).temperatureKnobModifiable(false).temperatureDescription("").temperatureLimit(-1).build()), Tags.KNEADING);
    }

    public static MachineValues kneadingMachineValues() {
        return new MachineValues.Builder().timeInMillis(45000L).speed(4).temperature(0).direction(0).tag(Tags.KNEADING).build();
    }

    public static CookingUnit roastingCookingUnit() {
        return new CookingUnit(420000L, 840000L, Arrays.asList(new SingleCookingStep.Builder().cookingDurationInMillis(5000L).machineConfiguration(new MachineConfiguration(4, 1, 0, a)).description(App.getInstance().getBaseContext().getString(R.string.roasting)).turboEnabled(false).directionModifiable(false).scaleEnabled(true).timeKnobModifiable(true).timeDescription("").timeLimitInMillis(-1L).speedKnobModifiable(false).speedDescription("").speedLimit(-1).temperatureKnobModifiable(true).temperatureDescription("").temperatureLimit(-1).build(), new SingleCookingStep.Builder().cookingDurationInMillis(30000L).machineConfiguration(new MachineConfiguration(4, 0, 0, a)).description(App.getInstance().getBaseContext().getString(R.string.heating_up)).turboEnabled(false).directionModifiable(false).scaleEnabled(true).timeKnobModifiable(true).timeDescription("").timeLimitInMillis(-1L).speedKnobModifiable(false).speedDescription("").speedLimit(-1).temperatureKnobModifiable(true).temperatureDescription("").temperatureLimit(-1).build()), Tags.ROASTING);
    }

    public static MachineValues roastingMachineValues() {
        return new MachineValues.Builder().timeInMillis(420000L).speed(1).temperature(Limits.MAX_TEMPERATURE).direction(0).tag(Tags.ROASTING).build();
    }

    public static CookingSteps steamingCookingSteps() {
        return new CookingSteps(new SingleCookingStep.Builder().cookingDurationInMillis(600000L).machineConfiguration(new MachineConfiguration(3, 1, 1, 17)).description(App.getInstance().getBaseContext().getString(R.string.steaming)).turboEnabled(false).directionModifiable(false).scaleEnabled(true).timeKnobModifiable(true).timeDescription("").timeLimitInMillis(600000L).speedKnobModifiable(false).speedDescription("").speedLimit(-1).temperatureKnobModifiable(false).temperatureDescription("").temperatureLimit(-1).preheatStep(true).build(), new SingleCookingStep.Builder().cookingDurationInMillis(1200000L).machineConfiguration(new MachineConfiguration(3, 1, 1, 17)).description(App.getInstance().getBaseContext().getString(R.string.steaming)).turboEnabled(false).directionModifiable(false).scaleEnabled(true).timeKnobModifiable(true).timeDescription("").timeLimitInMillis(DateUtils.MILLIS_PER_HOUR).speedKnobModifiable(false).speedDescription("").speedLimit(-1).temperatureKnobModifiable(false).temperatureDescription("").temperatureLimit(-1).build());
    }

    public static MachineValues steamingMachineValues() {
        return new MachineValues.Builder().timeInMillis(1200000L).speed(1).temperature(KNEADING_MAX_TIME).direction(1).tag(Tags.STEAMING).build();
    }
}
