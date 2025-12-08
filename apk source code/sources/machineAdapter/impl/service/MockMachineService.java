package machineAdapter.impl.service;

import android.content.Intent;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android_serialport_api.MyFunc;
import defpackage.gm;
import defpackage.im;
import java.util.Date;
import machineAdapter.impl.hal.PowerBoard;
import machineAdapter.ipc.DeviceState;

/* loaded from: classes.dex */
public final class MockMachineService extends gm {
    public static final String m = MockMachineService.class.getSimpleName();
    public PowerBoard l;

    public MockMachineService() {
        new AccelerateDecelerateInterpolator();
        PowerBoard powerBoard = new PowerBoard();
        this.l = powerBoard;
        powerBoard.setSendWorkCommand(PowerBoard.I_WorkCommand.MOTOR_OFF_HEATER_OFF);
        this.l.setSendMotorDirection(PowerBoard.I_MotorDirection.CLOCKWISE);
        this.l.setSendSpeedLevel(0);
        this.l.setSendTemperatureLevel(0);
        this.l.setSendTareScale(false);
        this.l.setSendScaleCalibration(PowerBoard.I_ScaleCalibration.EXIT);
    }

    @Override // defpackage.gm
    public void RESA(Exception exc) {
        Log.e(m, "RESA", exc);
    }

    @Override // defpackage.hm
    public int getFirmwareVersion() {
        return -1;
    }

    @Override // defpackage.gm, android.app.Service
    public /* bridge */ /* synthetic */ void onCreate() {
        super.onCreate();
    }

    @Override // defpackage.gm, android.app.Service
    public /* bridge */ /* synthetic */ void onDestroy() {
        super.onDestroy();
    }

    @Override // defpackage.gm, defpackage.hm
    public /* bridge */ /* synthetic */ DeviceState onDeviceStateRequested() {
        return super.onDeviceStateRequested();
    }

    @Override // defpackage.gm
    public void onPerformPeriodicHardwareAction() {
    }

    @Override // defpackage.gm
    public void onProcessSensorData(im imVar) {
        if (imVar == null || this.l == null) {
            return;
        }
        byte[] bArrHexToByteArr = new Date().getTime() % 2 == 0 ? MyFunc.HexToByteArr("551BB1040300000000177000000000280000000F019A02000083AA") : null;
        if (PowerBoard.areDataCorrect(bArrHexToByteArr)) {
            this.l.setReceivedData(bArrHexToByteArr);
            imVar.d = this.l.isMotorDirectionClockwise() ? 1 : 0;
            int iOrdinal = this.l.getMotorHeaterState().ordinal();
            if (iOrdinal == 0) {
                imVar.f = 1;
                imVar.a = 1;
            } else if (iOrdinal == 1) {
                imVar.f = 0;
                imVar.a = 0;
            } else if (iOrdinal == 2) {
                imVar.f = 0;
                imVar.a = 1;
            } else if (iOrdinal == 3) {
                imVar.f = 1;
                imVar.a = 0;
            }
            imVar.e = this.l.getActualSpeedLevel();
            imVar.b = this.l.getActualTemperature();
            int iOrdinal2 = this.l.getScaleState().ordinal();
            if (iOrdinal2 == 0) {
                imVar.i = 0;
            } else if (iOrdinal2 == 1) {
                imVar.i = 2;
            } else if (iOrdinal2 == 2) {
                imVar.i = 1;
            }
            imVar.h = this.l.getScaleWeight();
            imVar.c = !this.l.isCupCoverOpened() ? 1 : 0;
            if (!this.l.isErrorDetected()) {
                imVar.j = 0;
                return;
            }
            int iOrdinal3 = this.l.getError().ordinal();
            if (iOrdinal3 == 0) {
                imVar.j = 1;
                return;
            }
            if (iOrdinal3 == 1) {
                imVar.j = 2;
                return;
            }
            if (iOrdinal3 == 2) {
                imVar.j = 3;
                return;
            }
            if (iOrdinal3 == 3) {
                imVar.j = 4;
                return;
            }
            if (iOrdinal3 == 4) {
                imVar.j = 5;
                return;
            }
            if (iOrdinal3 == 6) {
                imVar.j = 12;
            } else if (iOrdinal3 != 7) {
                imVar.j = 6;
            } else {
                imVar.j = 13;
            }
        }
    }

    @Override // defpackage.hm
    public void onScaleCalibrationRequested(int i) {
        if (i == 0) {
            this.l.setSendScaleCalibration(PowerBoard.I_ScaleCalibration.ENTER);
        } else if (i == 1) {
            this.l.setSendScaleCalibration(PowerBoard.I_ScaleCalibration.START);
        } else {
            if (i != 2) {
                return;
            }
            this.l.setSendScaleCalibration(PowerBoard.I_ScaleCalibration.EXIT);
        }
    }

    @Override // defpackage.hm
    public void onScaleTareRequested() {
        Log.v(m, "onScaleTareRequested");
    }

    public void onSleepModeRequested() {
    }

    @Override // defpackage.gm, android.app.Service
    public /* bridge */ /* synthetic */ int onStartCommand(Intent intent, int i, int i2) {
        return super.onStartCommand(intent, i, i2);
    }

    @Override // defpackage.hm
    public void onStartRequested(int i, int i2, int i3, int i4) {
    }

    @Override // defpackage.hm
    public void onStopRequested() {
    }
}
