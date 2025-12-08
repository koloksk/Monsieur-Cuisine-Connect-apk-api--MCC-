package machineAdapter.impl.service;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import defpackage.cm;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Semaphore;

/* loaded from: classes.dex */
public class HardwareLEDService {
    public static HardwareLEDService e;
    public static volatile String f;
    public static volatile Semaphore g;
    public final Handler a;
    public final Map<String, Integer> b = new a(this);
    public final double[] c;
    public final Handler d;

    public class a extends HashMap<String, Integer> {
        public a(HardwareLEDService hardwareLEDService) {
            put("/sys/devices/platform/leds-mt65xx/leds/red/brightness", 0);
            put("/sys/devices/platform/leds-mt65xx/leds/green/brightness", 0);
            put("/sys/devices/platform/leds-mt65xx/leds/blue/brightness", 0);
        }
    }

    public class b extends Handler {
        public b(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) throws InterruptedException, IOException {
            removeCallbacksAndMessages(null);
            try {
                HardwareLEDService.g.acquire();
                HardwareLEDService.this.d.removeCallbacksAndMessages(null);
                final HardwareLEDService hardwareLEDService = HardwareLEDService.this;
                int i = message.arg1;
                if (hardwareLEDService == null) {
                    throw null;
                }
                if (i >= 0) {
                    hardwareLEDService.d.postDelayed(new Runnable() { // from class: dm
                        @Override // java.lang.Runnable
                        public final void run() {
                            hardwareLEDService.a();
                        }
                    }, i);
                }
                int i2 = message.what;
                if (i2 == 0) {
                    HardwareLEDService.this.a((LEDColor) message.obj);
                    return;
                }
                boolean z = true;
                if (i2 == 1) {
                    HardwareLEDService hardwareLEDService2 = HardwareLEDService.this;
                    LEDColor lEDColor = (LEDColor) message.obj;
                    if (hardwareLEDService2 == null) {
                        throw null;
                    }
                    String str = HardwareLEDService.f;
                    while (HardwareLEDService.f.equals(str)) {
                        if (z) {
                            hardwareLEDService2.a(lEDColor);
                        } else {
                            hardwareLEDService2.a(LEDColor.OFF);
                        }
                        z = !z;
                        try {
                            Thread.sleep(500L);
                        } catch (InterruptedException unused) {
                        }
                    }
                    return;
                }
                if (i2 != 2) {
                    if (i2 != 3) {
                        return;
                    }
                    HardwareLEDService hardwareLEDService3 = HardwareLEDService.this;
                    hardwareLEDService3.a.removeCallbacksAndMessages(null);
                    hardwareLEDService3.a(LEDColor.OFF);
                    return;
                }
                HardwareLEDService hardwareLEDService4 = HardwareLEDService.this;
                LEDColor lEDColor2 = (LEDColor) message.obj;
                String str2 = HardwareLEDService.f;
                int length = hardwareLEDService4.c.length;
                int i3 = 0;
                while (HardwareLEDService.f.equals(str2)) {
                    i3 = (i3 + 1) % length;
                    double d = hardwareLEDService4.c[i3];
                    hardwareLEDService4.a((int) (lEDColor2.getRedValue() * d), (int) (lEDColor2.getGreenValue() * d), (int) (lEDColor2.getBlueValue() * d));
                    try {
                        Thread.sleep(30L);
                    } catch (InterruptedException unused2) {
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public HardwareLEDService() {
        double[] dArr = new double[100];
        for (int i = 0; i < 100; i++) {
            dArr[i] = (Math.cos(((i * 2) / 100.0d) * 3.141592653589793d) + 1.0d) * 0.5d;
        }
        this.c = dArr;
        HandlerThread handlerThread = new HandlerThread("LED Thread");
        HandlerThread handlerThread2 = new HandlerThread("Task Thread");
        g = new Semaphore(0);
        handlerThread.start();
        handlerThread2.start();
        this.d = new Handler(handlerThread2.getLooper());
        this.a = new b(handlerThread.getLooper());
    }

    public static /* synthetic */ void c() {
        f = UUID.randomUUID().toString();
        g.drainPermits();
        g.release();
    }

    public static HardwareLEDService getInstance() {
        if (e == null) {
            e = new HardwareLEDService();
        }
        return e;
    }

    public final synchronized void a(int i, LEDColor lEDColor, int i2) {
        g.drainPermits();
        this.d.post(cm.a);
        Message message = new Message();
        message.what = i;
        message.obj = lEDColor;
        message.arg1 = i2;
        this.a.removeCallbacksAndMessages(null);
        this.a.sendMessage(message);
    }

    public final synchronized void b() {
        g.drainPermits();
        this.d.post(cm.a);
        Message message = new Message();
        message.what = 3;
        message.arg1 = -1;
        this.a.removeCallbacksAndMessages(null);
        this.a.sendMessage(message);
    }

    public void blinkLED(LEDColor lEDColor, int i) {
        a(1, lEDColor, i);
    }

    public boolean isOff() {
        Iterator<Integer> it = this.b.values().iterator();
        while (it.hasNext()) {
            if (it.next().intValue() > 0) {
                return false;
            }
        }
        return true;
    }

    public void pulseLED(LEDColor lEDColor) {
        a(2, lEDColor, -1);
    }

    public void switchLEDOn(LEDColor lEDColor) {
        a(0, lEDColor, -1);
    }

    public void turnOff() {
        b();
    }

    public void blinkLED(LEDColor lEDColor) {
        blinkLED(lEDColor, -1);
    }

    public final void a(LEDColor lEDColor) throws IOException {
        a(lEDColor.getRedValue(), lEDColor.getGreenValue(), lEDColor.getBlueValue());
    }

    public final void a(int i, int i2, int i3) throws IOException {
        this.b.put("/sys/devices/platform/leds-mt65xx/leds/red/brightness", Integer.valueOf(i));
        this.b.put("/sys/devices/platform/leds-mt65xx/leds/green/brightness", Integer.valueOf(i2));
        this.b.put("/sys/devices/platform/leds-mt65xx/leds/blue/brightness", Integer.valueOf(i3));
        try {
            for (Map.Entry<String, Integer> entry : this.b.entrySet()) {
                FileOutputStream fileOutputStream = new FileOutputStream(entry.getKey());
                fileOutputStream.write(entry.getValue().toString().getBytes());
                fileOutputStream.close();
            }
        } catch (Exception unused) {
        }
    }

    public /* synthetic */ void a() {
        switchLEDOn(LEDColor.WHITE);
    }
}
