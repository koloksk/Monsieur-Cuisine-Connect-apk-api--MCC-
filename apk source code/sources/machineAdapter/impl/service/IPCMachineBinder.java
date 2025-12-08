package machineAdapter.impl.service;

import android.os.Process;
import android.os.RemoteException;
import android.util.Log;
import defpackage.hm;
import java.util.Locale;
import machineAdapter.ipc.DeviceState;
import machineAdapter.ipc.IPCMachineInterface;

/* loaded from: classes.dex */
public class IPCMachineBinder extends IPCMachineInterface.Stub {
    public static final String b = IPCMachineBinder.class.getSimpleName();
    public hm a;

    public IPCMachineBinder(hm hmVar) {
        this.a = hmVar;
    }

    @Override // machineAdapter.ipc.IPCMachineInterface
    public int getFirmwareVersion() throws RemoteException {
        Log.d(b, "### Client called: \"getFirmwareVersion().\"");
        return this.a.getFirmwareVersion();
    }

    @Override // machineAdapter.ipc.IPCMachineInterface
    public int getPid() throws RemoteException {
        Log.d(b, "### Client called: \"getPid().\"");
        return Process.myPid();
    }

    @Override // machineAdapter.ipc.IPCMachineInterface
    public DeviceState requestDeviceState() throws RemoteException {
        Log.d(b, "### Client called: \"onDeviceStateRequested().\"");
        return this.a.onDeviceStateRequested();
    }

    @Override // machineAdapter.ipc.IPCMachineInterface
    public void requestScaleCalibrate(int i) throws RemoteException {
        this.a.onScaleCalibrationRequested(i);
    }

    @Override // machineAdapter.ipc.IPCMachineInterface
    public void requestScaleTare() throws RemoteException {
        Log.d(b, "### Client called: \"onScaleTareRequested().\"");
        this.a.onScaleTareRequested();
    }

    @Override // machineAdapter.ipc.IPCMachineInterface
    public void requestStart(int i, int i2, int i3, int i4) throws RemoteException {
        Log.d(b, String.format(Locale.ENGLISH, "### Client called: \"onStartRequested()\" with workMode: %d, speed: %d, direction: %d, temperatureLevel: %d.", Integer.valueOf(i), Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)));
        this.a.onStartRequested(i, i2, i3, i4);
    }

    @Override // machineAdapter.ipc.IPCMachineInterface
    public void requestStop() throws RemoteException {
        Log.d(b, "### Client called: \"onStopRequested().\"");
        this.a.onStopRequested();
    }
}
