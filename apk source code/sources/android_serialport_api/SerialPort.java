package android_serialport_api;

import android.util.Log;
import defpackage.g9;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class SerialPort {
    public FileDescriptor a;
    public FileInputStream b;
    public FileOutputStream c;

    static {
        System.loadLibrary("serial_port");
    }

    public SerialPort(File file, int i, int i2) throws IOException, SecurityException {
        StringBuilder sbA = g9.a("creating >> device.getAbsolutePath() is ");
        sbA.append(file.getAbsolutePath());
        Log.i("SerialPort", sbA.toString());
        FileDescriptor fileDescriptorOpen = open(file.getAbsolutePath(), i, i2);
        this.a = fileDescriptorOpen;
        if (fileDescriptorOpen == null) {
            Log.e("SerialPort", "creating >> native open returns null");
            throw new IOException();
        }
        this.b = new FileInputStream(this.a);
        this.c = new FileOutputStream(this.a);
    }

    public static native FileDescriptor open(String str, int i, int i2);

    public native void close();

    public InputStream getInputStream() {
        if (this.b == null) {
            Log.i("SerialPort", "getInputStream >> mFileInputStream is null");
        }
        return this.b;
    }

    public OutputStream getOutputStream() {
        if (this.c == null) {
            Log.i("SerialPort", "getOutputStream >> mFileOutputStream is null");
        }
        return this.c;
    }
}
