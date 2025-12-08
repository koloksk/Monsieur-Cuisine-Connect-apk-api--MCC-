package machineAdapter;

/* loaded from: classes.dex */
public interface IMachineCallback {
    void onBadState(int[] iArr);

    void onHeatingElementStateChanged(int i, long j);

    void onJogDialPagingRequested(int i);

    void onJogDialPushed(int i);

    void onJogDialTurned(int i, long j);

    void onLidClosed();

    void onLidOpened();

    void onMachineException(MachineException machineException);

    void onMachineInterfaceConnected();

    void onMachineInterfaceDisconnected();

    void onMotorSpeedChanged(long j, int i);

    void onMotorStateChanged(int i);

    void onScaleStateChanged(int i, long j, int i2);

    void onTemperatureChanged(long j);
}
