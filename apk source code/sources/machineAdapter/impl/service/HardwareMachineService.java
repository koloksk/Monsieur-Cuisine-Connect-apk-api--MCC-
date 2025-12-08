package machineAdapter.impl.service;

import android.content.Intent;
import android.util.Log;
import android_serialport_api.SerialUtils;
import defpackage.gm;
import defpackage.im;
import java.io.IOException;
import java.security.InvalidParameterException;
import machineAdapter.impl.hal.PowerBoard;
import machineAdapter.ipc.DeviceState;

/* loaded from: classes.dex */
public class HardwareMachineService extends gm {
    public static final String o = HardwareMachineService.class.getSimpleName();
    public SerialUtils l;
    public PowerBoard m;
    public boolean n = false;

    public HardwareMachineService() {
        SerialUtils serialUtils = new SerialUtils("/dev/ttyMT0", 4800);
        this.l = serialUtils;
        if (serialUtils != null) {
            try {
                serialUtils.open();
            } catch (IOException | SecurityException | InvalidParameterException e) {
                RESA(e);
            }
        } else {
            RESA(null);
        }
        SerialUtils serialUtils2 = this.l;
        if (serialUtils2 != null) {
            try {
                serialUtils2.read();
            } catch (IOException | SecurityException | InvalidParameterException e2) {
                RESA(e2);
            }
        } else {
            RESA(null);
        }
        PowerBoard powerBoard = new PowerBoard();
        this.m = powerBoard;
        powerBoard.setSendWorkCommand(PowerBoard.I_WorkCommand.MOTOR_OFF_HEATER_OFF);
        this.m.setSendMotorDirection(PowerBoard.I_MotorDirection.CLOCKWISE);
        this.m.setSendSpeedLevel(0);
        this.m.setSendTemperatureLevel(0);
        this.m.setSendTareScale(false);
        this.m.setSendScaleCalibration(PowerBoard.I_ScaleCalibration.EXIT);
    }

    @Override // defpackage.gm
    public void RESA(Exception exc) {
        Log.e(o, "RESA", exc);
        synchronized (this) {
            onStopRequested();
        }
    }

    @Override // defpackage.hm
    public int getFirmwareVersion() {
        return this.m.getFirmwareVersion();
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
        synchronized (this) {
            if (this.n) {
                this.m.setSendTareScale(false);
                this.n = false;
            }
            byte[] sendPayload = this.m.getSendPayload();
            SerialUtils serialUtils = this.l;
            if (serialUtils == null || !serialUtils.isOpen()) {
                Log.w(o, "cannot send data to powerboard, serial port null or closed.");
            } else {
                this.l.send(sendPayload);
            }
            if (this.m.isSendTareScale()) {
                this.n = true;
            }
        }
    }

    @Override // defpackage.gm
    public void onProcessSensorData(im imVar) {
        SerialUtils serialUtils;
        if (imVar == null || this.m == null || (serialUtils = this.l) == null || !serialUtils.isOpen()) {
            return;
        }
        byte[] deviceData = this.l.getDeviceData();
        if (!PowerBoard.areDataCorrect(deviceData)) {
            Log.w(o, "onProcessSensorData -- data incorrect");
            return;
        }
        this.m.setReceivedData(deviceData);
        imVar.d = this.m.isMotorDirectionClockwise() ? 1 : 0;
        int iOrdinal = this.m.getMotorHeaterState().ordinal();
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
        imVar.e = this.m.getActualSpeedLevel();
        imVar.b = this.m.getActualTemperature();
        int iOrdinal2 = this.m.getScaleState().ordinal();
        if (iOrdinal2 == 0) {
            imVar.i = 0;
        } else if (iOrdinal2 == 1) {
            imVar.i = 2;
        } else if (iOrdinal2 == 2) {
            imVar.i = 1;
        }
        imVar.h = this.m.getScaleWeight();
        switch (this.m.getScaleCalibrationStep()) {
            case INITIALIZING:
                imVar.g = 0;
                break;
            case ZERO_POINT:
                imVar.g = 1;
                break;
            case FIRST_LOAD_1_KG:
                imVar.g = 2;
                break;
            case SECOND_LOAD_3_KG:
                imVar.g = 3;
                break;
            case SUCCESS:
                imVar.g = 4;
                break;
            case OVERLOAD:
                imVar.g = 5;
                break;
            case FAILURE:
                imVar.g = 6;
                break;
            case NOT_AVAILABLE:
                imVar.g = 7;
                break;
        }
        imVar.c = !this.m.isCupCoverOpened() ? 1 : 0;
        if (!this.m.isErrorDetected()) {
            imVar.j = 0;
            return;
        }
        int iOrdinal3 = this.m.getError().ordinal();
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

    @Override // defpackage.hm
    public void onScaleCalibrationRequested(int i) {
        if (i == 0) {
            this.m.setSendScaleCalibration(PowerBoard.I_ScaleCalibration.ENTER);
        } else if (i == 1) {
            this.m.setSendScaleCalibration(PowerBoard.I_ScaleCalibration.START);
        } else {
            if (i != 2) {
                return;
            }
            this.m.setSendScaleCalibration(PowerBoard.I_ScaleCalibration.EXIT);
        }
    }

    @Override // defpackage.hm
    public void onScaleTareRequested() {
        synchronized (this) {
            this.n = false;
            this.m.setSendTareScale(true);
        }
    }

    public void onSleepModeRequested() {
        synchronized (this) {
            this.m.setSendWorkCommand(PowerBoard.I_WorkCommand.SLEEP);
            this.m.setSendSpeedLevel(0);
            this.m.setSendTemperatureLevel(0);
        }
    }

    @Override // defpackage.gm, android.app.Service
    public /* bridge */ /* synthetic */ int onStartCommand(Intent intent, int i, int i2) {
        return super.onStartCommand(intent, i, i2);
    }

    @Override // defpackage.hm
    public void onStartRequested(int i, int i2, int i3, int i4) {
        synchronized (this) {
            try {
                switch (i) {
                    case 0:
                        this.m.setSendWorkCommand(PowerBoard.I_WorkCommand.MOTOR_OFF_HEATER_OFF);
                        break;
                    case 1:
                        this.m.setSendWorkCommand(PowerBoard.I_WorkCommand.MOTOR_ON_HEATER_ON);
                        break;
                    case 2:
                        this.m.setSendWorkCommand(PowerBoard.I_WorkCommand.MOTOR_ON_HEATER_OFF_TURBO_MODE);
                        break;
                    case 3:
                        this.m.setSendWorkCommand(PowerBoard.I_WorkCommand.STEAM);
                        break;
                    case 4:
                        this.m.setSendWorkCommand(PowerBoard.I_WorkCommand.TOAST);
                        break;
                    case 5:
                        this.m.setSendWorkCommand(PowerBoard.I_WorkCommand.KNEAD);
                        break;
                    case 6:
                        this.m.setSendWorkCommand(PowerBoard.I_WorkCommand.SLEEP);
                        break;
                    default:
                        this.m.setSendWorkCommand(PowerBoard.I_WorkCommand.MOTOR_OFF_HEATER_OFF);
                        break;
                }
                this.m.setSendSpeedLevel(i2);
                this.m.setSendTemperatureLevel(i4);
                if (i3 == 0) {
                    this.m.setSendMotorDirection(PowerBoard.I_MotorDirection.COUNTER_CLOCKWISE);
                } else {
                    this.m.setSendMotorDirection(PowerBoard.I_MotorDirection.CLOCKWISE);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // defpackage.hm
    public void onStopRequested() {
        synchronized (this) {
            if (this.m != null) {
                this.m.setSendWorkCommand(PowerBoard.I_WorkCommand.MOTOR_OFF_HEATER_OFF);
                this.m.setSendSpeedLevel(0);
                this.m.setSendTemperatureLevel(0);
            }
        }
    }
}
