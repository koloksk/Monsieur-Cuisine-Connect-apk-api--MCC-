package machineAdapter.adapter;

import machineAdapter.IMachineCallback;
import machineAdapter.MachineException;

/* loaded from: classes.dex */
public abstract class MachineCallbackAdapter implements IMachineCallback {
    @Override // machineAdapter.IMachineCallback
    public void onBadState(int[] iArr) {
    }

    @Override // machineAdapter.IMachineCallback
    public void onHeatingElementStateChanged(int i, long j) {
    }

    @Override // machineAdapter.IMachineCallback
    public void onJogDialPagingRequested(int i) {
    }

    @Override // machineAdapter.IMachineCallback
    public void onJogDialPushed(int i) {
    }

    @Override // machineAdapter.IMachineCallback
    public void onJogDialTurned(int i, long j) {
    }

    @Override // machineAdapter.IMachineCallback
    public void onLidClosed() {
    }

    @Override // machineAdapter.IMachineCallback
    public void onLidOpened() {
    }

    @Override // machineAdapter.IMachineCallback
    public void onMachineException(MachineException machineException) {
    }

    @Override // machineAdapter.IMachineCallback
    public void onMachineInterfaceConnected() {
    }

    @Override // machineAdapter.IMachineCallback
    public void onMachineInterfaceDisconnected() {
    }

    @Override // machineAdapter.IMachineCallback
    public void onMotorSpeedChanged(long j, int i) {
    }

    @Override // machineAdapter.IMachineCallback
    public void onMotorStateChanged(int i) {
    }

    @Override // machineAdapter.IMachineCallback
    public void onScaleStateChanged(int i, long j, int i2) {
    }

    @Override // machineAdapter.IMachineCallback
    public void onTemperatureChanged(long j) {
    }
}
