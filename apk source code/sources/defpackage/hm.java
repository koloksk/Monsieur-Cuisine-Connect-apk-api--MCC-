package defpackage;

import machineAdapter.ipc.DeviceState;

/* loaded from: classes.dex */
public interface hm {
    int getFirmwareVersion();

    DeviceState onDeviceStateRequested();

    void onScaleCalibrationRequested(int i);

    void onScaleTareRequested();

    void onStartRequested(int i, int i2, int i3, int i4);

    void onStopRequested();
}
