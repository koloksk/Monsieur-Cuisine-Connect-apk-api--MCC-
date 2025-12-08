package android_serialport_api;

import defpackage.g9;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class MyFunc {
    public static String Byte2Hex(Byte b) {
        return String.format("%02X", b);
    }

    public static String ByteArrToHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            sb.append(Byte2Hex(Byte.valueOf(b)));
            sb.append(StringUtils.SPACE);
        }
        return sb.toString();
    }

    public static byte HexToByte(String str) {
        return (byte) Integer.parseInt(str, 16);
    }

    public static byte[] HexToByteArr(String str) {
        byte[] bArr;
        int length = str.length();
        if (isOdd(length) == 1) {
            length++;
            bArr = new byte[length / 2];
            str = g9.b("0", str);
        } else {
            bArr = new byte[length / 2];
        }
        int i = 0;
        int i2 = 0;
        while (i < length) {
            int i3 = i + 2;
            bArr[i2] = HexToByte(str.substring(i, i3));
            i2++;
            i = i3;
        }
        return bArr;
    }

    public static int HexToInt(String str) {
        return Integer.parseInt(str, 16);
    }

    public static int isOdd(int i) {
        return i & 1;
    }

    public static String ByteArrToHex(byte[] bArr, int i, int i2) {
        StringBuilder sb = new StringBuilder();
        while (i < i2) {
            sb.append(Byte2Hex(Byte.valueOf(bArr[i])));
            i++;
        }
        return sb.toString();
    }
}
