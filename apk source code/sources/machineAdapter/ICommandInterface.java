package machineAdapter;

/* loaded from: classes.dex */
public interface ICommandInterface {

    public static class HeatingElementState {
        public long durationLeft;
        public int enableState;
        public long temperature;
    }

    public static class MotorState {
        public int direction;
        public long durationLeft;
        public int enableState;
        public long speed;
    }

    public static class ScaleState {
        public long scaleMeasureValue;
        public int scaleState;
    }

    HeatingElementState getCurrentHeatingElementState();

    int getCurrentLidState();

    MotorState getCurrentMotorState();

    ScaleState getCurrentScaleState();

    int getErrorState();

    int getFirmwareVersion();

    boolean setScaleCalibration(int i);

    boolean setScaleTare();

    boolean start(int i, int i2, int i3, int i4);

    boolean stop();
}
