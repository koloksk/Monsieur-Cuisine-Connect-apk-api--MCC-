package android_serialport_api;

import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

/* loaded from: classes.dex */
public class SerialUtils {
    public SerialPort a;
    public OutputStream b;
    public InputStream c;
    public b d;
    public String e;
    public int f;
    public boolean g;
    public byte[] h;

    public class b extends Thread {
        public /* synthetic */ b(a aVar) {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            super.run();
            while (!isInterrupted()) {
                try {
                    if (SerialUtils.this.c == null) {
                        Log.w("SerialUtils", "ReadThread run mInputStream == null");
                        return;
                    }
                    byte[] bArr = new byte[1024];
                    int i = SerialUtils.this.c.read(bArr);
                    if (i > 0) {
                        int i2 = 0;
                        while (i >= 27) {
                            byte[] bArr2 = new byte[27];
                            System.arraycopy(bArr, i2 * 27, bArr2, 0, 27);
                            SerialUtils.this.h = bArr2;
                            i -= 27;
                            i2++;
                        }
                    }
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        Log.i("SerialUtils", "ReadThread run InterruptedException Error!");
                        e.printStackTrace();
                    }
                } catch (Throwable th) {
                    Log.e("SerialUtils", "ReadThread run Throwable Error!", th);
                    return;
                }
                Log.e("SerialUtils", "ReadThread run Throwable Error!", th);
                return;
            }
        }
    }

    public SerialUtils(String str, int i) {
        this.e = "/dev/ttyMT0";
        this.f = 921600;
        this.g = false;
        this.h = null;
        this.e = str;
        this.f = i;
    }

    public void close() {
        b bVar = this.d;
        if (bVar != null) {
            bVar.interrupt();
        }
        SerialPort serialPort = this.a;
        if (serialPort != null) {
            serialPort.close();
            this.a = null;
        }
        this.g = false;
    }

    public byte[] getDeviceData() {
        byte[] bArr = this.h;
        if (bArr != null) {
            return (byte[]) bArr.clone();
        }
        return null;
    }

    public boolean isOpen() {
        return this.g;
    }

    public void open() throws InvalidParameterException, SecurityException, IOException {
        SerialPort serialPort = new SerialPort(new File(this.e), this.f, 0);
        this.a = serialPort;
        this.b = serialPort.getOutputStream();
        this.c = this.a.getInputStream();
        this.g = true;
    }

    public void read() throws InvalidParameterException, SecurityException, IOException {
        if (this.a == null || !this.g) {
            return;
        }
        b bVar = new b(null);
        this.d = bVar;
        bVar.start();
    }

    public void send(byte[] bArr) throws IOException {
        try {
            this.b.write(bArr);
            this.b.flush();
        } catch (IOException e) {
            Log.i("SerialUtils", "send Error!");
            e.printStackTrace();
        }
    }

    public SerialUtils() {
        this("/dev/ttyMT0", 921600);
    }

    public SerialUtils(String str) {
        this(str, 921600);
    }

    public SerialUtils(String str, String str2) {
        this(str, Integer.parseInt(str2));
    }
}
