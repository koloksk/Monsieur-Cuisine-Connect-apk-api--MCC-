package machineAdapter.impl.hal;

import android.support.v4.view.PointerIconCompat;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.Range;

/* loaded from: classes.dex */
public class PowerBoard {
    public static final int OUTPUT_SIZE = 27;
    public static final List<Range<Integer>> i = new a();
    public byte[] a;
    public boolean b;
    public I_WorkCommand c = I_WorkCommand.MOTOR_OFF_HEATER_OFF;
    public int d = 0;
    public int e = 0;
    public I_MotorDirection f = I_MotorDirection.CLOCKWISE;
    public boolean g = false;
    public I_ScaleCalibration h = I_ScaleCalibration.EXIT;

    public enum I_MotorDirection {
        CLOCKWISE,
        COUNTER_CLOCKWISE
    }

    public enum I_ScaleCalibration {
        ENTER,
        START,
        EXIT
    }

    public enum I_WorkCommand {
        MOTOR_ON_HEATER_ON,
        MOTOR_OFF_HEATER_OFF,
        MOTOR_OFF_HEATER_ON,
        MOTOR_ON_HEATER_OFF_TURBO_MODE,
        STEAM,
        TOAST,
        KNEAD,
        SLEEP
    }

    public enum O_Error {
        SERIAL_PORT_COMMUNICATION,
        POWER_INPUT_OVERVOLTAGE,
        POWER_INPUT_UNDERVOLTAGE,
        MOTOR,
        NO_WATER,
        UNDEFINED,
        MOTOR_OVERHEAT,
        MAX_RUNTIME_EXCEEDED
    }

    public enum O_MotorHeaterExtendedState {
        STEAM,
        TOAST,
        KNEAD,
        UNDEFINED
    }

    public enum O_MotorHeaterState {
        MOTOR_ON_HEATER_ON,
        MOTOR_OFF_HEATER_OFF,
        MOTOR_OFF_HEATER_ON,
        MOTOR_ON_HEATER_OFF_TURBO_MODE
    }

    public enum O_ScaleCalibrationStep {
        INITIALIZING,
        ZERO_POINT,
        FIRST_LOAD_1_KG,
        SECOND_LOAD_3_KG,
        SUCCESS,
        OVERLOAD,
        FAILURE,
        NOT_AVAILABLE
    }

    public enum O_ScaleState {
        NORMAL,
        CALIBRATION,
        OVERLOAD
    }

    public static class a extends ArrayList<Range<Integer>> {
        public a() {
            add(Range.between(Integer.MIN_VALUE, 0));
            add(Range.between(1, 558));
            add(Range.between(559, Integer.valueOf(PointerIconCompat.TYPE_TEXT)));
            add(Range.between(Integer.valueOf(PointerIconCompat.TYPE_VERTICAL_TEXT), 1437));
            add(Range.between(1438, 3359));
            add(Range.between(3360, 6349));
            add(Range.between(6350, 9309));
            add(Range.between(9310, 12312));
            add(Range.between(12313, 15315));
            add(Range.between(15316, 18232));
            add(Range.between(18233, Integer.MAX_VALUE));
        }
    }

    public static byte a(byte[] bArr) {
        int i2 = 0;
        if (bArr != null && bArr.length > 0) {
            int length = bArr.length;
            short s = 0;
            while (i2 < length) {
                s = (short) (s + bArr[i2]);
                i2++;
            }
            i2 = s;
        }
        return (byte) i2;
    }

    public static boolean areDataCorrect(byte[] bArr) {
        if (bArr == null || bArr.length != 27) {
            Log.w("PowerBoard", "received data from board is null or haven't the defined length 27");
            return false;
        }
        if (bArr[0] != 85) {
            Log.w("PowerBoard", String.format("expected start byte '%02x' but it is '0x%02x'", (byte) 85, Byte.valueOf(bArr[0])));
            return false;
        }
        if (bArr[bArr.length - 1] != -86) {
            Log.w("PowerBoard", String.format("expected end byte '%02x' but it is '0x%02x'", (byte) -86, Byte.valueOf(bArr[bArr.length - 1])));
            return false;
        }
        if (bArr[1] != 27) {
            Log.w("PowerBoard", String.format(Locale.ENGLISH, "expected frame size '%d' but it is '%d'", 27, Byte.valueOf(bArr[1])));
            return false;
        }
        int length = bArr.length - 2;
        byte[] bArr2 = new byte[length];
        if (length <= 0) {
            Log.w("PowerBoard", "can not allocate checksum byte array");
            return false;
        }
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length - 2);
        byte bA = a(bArr2);
        if (bArr[bArr.length - 2] != bA) {
            Log.w("PowerBoard", String.format("expected checksum '%02x' but it is '0x%02x'", Byte.valueOf(bA), Byte.valueOf(bArr[bArr.length - 2])));
            return false;
        }
        if (bArr[2] == -79) {
            return true;
        }
        Log.w("PowerBoard", String.format("expected fix answer code '%02x' but it is '0x%02x'", (byte) -79, Byte.valueOf(bArr[2])));
        return false;
    }

    public boolean areScaleDataStable() {
        return this.b && (this.a[3] & 4) == 4;
    }

    public int getActualRpm() {
        if (!this.b) {
            return 0;
        }
        byte[] bArr = this.a;
        byte b = bArr[9];
        return (bArr[10] & 255) | ((b & 255) << 8);
    }

    public int getActualSpeedLevel() {
        if (i.size() > 0) {
            int actualRpm = getActualRpm();
            for (int i2 = 0; i2 < i.size(); i2++) {
                if (i.get(i2).contains(Integer.valueOf(actualRpm))) {
                    return i2;
                }
            }
        }
        return 0;
    }

    public int getActualTemperature() {
        if (!this.b) {
            return 0;
        }
        byte[] bArr = this.a;
        byte b = bArr[14];
        return (bArr[15] & 255) | ((b & 255) << 8);
    }

    public O_Error getError() {
        O_Error o_Error = O_Error.UNDEFINED;
        if (!this.b) {
            return o_Error;
        }
        switch (this.a[5]) {
            case 1:
                return O_Error.SERIAL_PORT_COMMUNICATION;
            case 2:
            default:
                return o_Error;
            case 3:
                return O_Error.POWER_INPUT_OVERVOLTAGE;
            case 4:
                return O_Error.POWER_INPUT_UNDERVOLTAGE;
            case 5:
                return O_Error.MOTOR;
            case 6:
                return O_Error.NO_WATER;
            case 7:
                return O_Error.MOTOR_OVERHEAT;
            case 8:
                return O_Error.MAX_RUNTIME_EXCEEDED;
        }
    }

    public int getFirmwareVersion() {
        if (!this.b) {
            return 0;
        }
        byte[] bArr = this.a;
        byte b = bArr[20];
        return (bArr[21] & 255) | ((b & 255) << 8);
    }

    public O_MotorHeaterExtendedState getMotorHeaterExtendedState() {
        if (this.b) {
            byte b = this.a[4];
            if (b == 3) {
                return O_MotorHeaterExtendedState.STEAM;
            }
            if (b == 4) {
                return O_MotorHeaterExtendedState.TOAST;
            }
            if (b == 5) {
                return O_MotorHeaterExtendedState.KNEAD;
            }
        }
        return O_MotorHeaterExtendedState.UNDEFINED;
    }

    public O_MotorHeaterState getMotorHeaterState() {
        if (this.b) {
            int i2 = this.a[3] & 3;
            if (i2 == 3) {
                return O_MotorHeaterState.MOTOR_ON_HEATER_OFF_TURBO_MODE;
            }
            if (i2 == 2) {
                return O_MotorHeaterState.MOTOR_OFF_HEATER_ON;
            }
            if (i2 == 1) {
                return O_MotorHeaterState.MOTOR_ON_HEATER_ON;
            }
            if (i2 == 0) {
                return O_MotorHeaterState.MOTOR_OFF_HEATER_OFF;
            }
        }
        return O_MotorHeaterState.MOTOR_OFF_HEATER_OFF;
    }

    public int getNotAvailable() {
        if (this.b) {
            return this.a[24];
        }
        return 0;
    }

    public O_ScaleCalibrationStep getScaleCalibrationStep() {
        if (this.b) {
            byte b = this.a[23];
            if (b == 0) {
                return O_ScaleCalibrationStep.INITIALIZING;
            }
            if (b == 1) {
                return O_ScaleCalibrationStep.ZERO_POINT;
            }
            if (b == 2) {
                return O_ScaleCalibrationStep.FIRST_LOAD_1_KG;
            }
            if (b == 3) {
                return O_ScaleCalibrationStep.SECOND_LOAD_3_KG;
            }
            if (b == 4) {
                return O_ScaleCalibrationStep.SUCCESS;
            }
            if (b == 5) {
                return O_ScaleCalibrationStep.OVERLOAD;
            }
            if (b == 7) {
                return O_ScaleCalibrationStep.FAILURE;
            }
        }
        return O_ScaleCalibrationStep.NOT_AVAILABLE;
    }

    public O_ScaleState getScaleState() {
        if (this.b) {
            byte b = this.a[22];
            if ((b & 4) == 4) {
                return O_ScaleState.OVERLOAD;
            }
            if ((b & 1) == 1) {
                return O_ScaleState.CALIBRATION;
            }
            if ((b & 2) == 2) {
                return O_ScaleState.NORMAL;
            }
        }
        return O_ScaleState.OVERLOAD;
    }

    public long getScaleWeight() {
        if (!this.b) {
            return 0L;
        }
        byte[] bArr = this.a;
        byte b = bArr[19];
        byte b2 = bArr[18];
        byte b3 = bArr[17];
        return ((bArr[16] & 255) << 24) | (b & 255) | ((b2 & 255) << 8) | ((b3 & 255) << 16);
    }

    public byte[] getSendPayload() {
        byte[] bArr = new byte[11];
        bArr[0] = -95;
        switch (this.c) {
            case MOTOR_ON_HEATER_ON:
                bArr[1] = 1;
                break;
            case MOTOR_OFF_HEATER_OFF:
                bArr[1] = 0;
                break;
            case MOTOR_OFF_HEATER_ON:
                bArr[1] = 1;
                break;
            case MOTOR_ON_HEATER_OFF_TURBO_MODE:
                bArr[1] = -58;
                break;
            case STEAM:
                bArr[1] = 3;
                break;
            case TOAST:
                bArr[1] = 4;
                break;
            case KNEAD:
                bArr[1] = 5;
                break;
            case SLEEP:
                bArr[1] = 15;
                break;
            default:
                bArr[1] = 0;
                break;
        }
        bArr[2] = this.g ? (byte) -87 : (byte) 0;
        int i2 = this.d;
        if (i2 < 0 || i2 > 10) {
            bArr[3] = 0;
        } else {
            bArr[3] = (byte) i2;
        }
        int i3 = this.e;
        if (i3 < 0 || i3 > 19) {
            bArr[4] = 0;
        } else {
            bArr[4] = (byte) i3;
        }
        bArr[5] = 0;
        bArr[6] = 0;
        bArr[7] = 0;
        bArr[8] = 0;
        int iOrdinal = this.f.ordinal();
        if (iOrdinal == 0 || iOrdinal != 1) {
            bArr[9] = 0;
        } else {
            bArr[9] = 1;
        }
        int iOrdinal2 = this.h.ordinal();
        if (iOrdinal2 == 0) {
            bArr[10] = -26;
            for (int i4 = 1; i4 < 10; i4++) {
                bArr[i4] = 0;
            }
        } else if (iOrdinal2 != 1) {
            bArr[10] = 0;
        } else {
            bArr[10] = -23;
            for (int i5 = 1; i5 < 10; i5++) {
                bArr[i5] = 0;
            }
        }
        byte[] bArr2 = new byte[15];
        bArr2[0] = 85;
        bArr2[14] = -86;
        bArr2[1] = (byte) 15;
        for (int i6 = 0; i6 < 11; i6++) {
            bArr2[i6 + 2] = bArr[i6];
        }
        byte[] bArr3 = new byte[13];
        System.arraycopy(bArr2, 0, bArr3, 0, 13);
        bArr2[13] = a(bArr3);
        return bArr2;
    }

    public int getTargetRpm() {
        if (!this.b) {
            return 0;
        }
        byte[] bArr = this.a;
        byte b = bArr[7];
        return (bArr[8] & 255) | ((b & 255) << 8);
    }

    public int getTargetSpeedLevel() {
        if (this.b) {
            return this.a[6];
        }
        return 0;
    }

    public int getTargetTemperature() {
        if (!this.b) {
            return 0;
        }
        byte[] bArr = this.a;
        byte b = bArr[12];
        return (bArr[13] & 255) | ((b & 255) << 8);
    }

    public int getTargetTemperatureLevel() {
        if (this.b) {
            return this.a[11];
        }
        return 0;
    }

    public boolean isCupCoverOpened() {
        return !this.b || (this.a[3] & 128) == 128;
    }

    public boolean isCupTemperatureHigherThanTargetTemperature() {
        return this.b && (this.a[3] & 32) == 32;
    }

    public boolean isCupTemperatureLowerThanTargetTemperature() {
        return !isCupTemperatureHigherThanTargetTemperature();
    }

    public boolean isCupsNTCDetected() {
        return this.b && (this.a[3] & 64) == 64;
    }

    public boolean isErrorDetected() {
        return !this.b || (this.a[3] & 16) == 16;
    }

    public boolean isMotorDirectionClockwise() {
        return !this.b || (this.a[3] & 8) == 8;
    }

    public boolean isSendTareScale() {
        return this.g;
    }

    public void setReceivedData(byte[] bArr) {
        this.a = bArr;
        this.b = areDataCorrect(bArr);
    }

    public void setSendMotorDirection(I_MotorDirection i_MotorDirection) {
        this.f = i_MotorDirection;
    }

    public void setSendScaleCalibration(I_ScaleCalibration i_ScaleCalibration) {
        this.h = i_ScaleCalibration;
    }

    public void setSendSpeedLevel(int i2) {
        this.d = i2;
    }

    public void setSendTareScale(boolean z) {
        this.g = z;
    }

    public void setSendTemperatureLevel(int i2) {
        this.e = i2;
    }

    public void setSendWorkCommand(I_WorkCommand i_WorkCommand) {
        this.c = i_WorkCommand;
    }
}
