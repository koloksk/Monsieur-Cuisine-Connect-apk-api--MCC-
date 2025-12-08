package helper;

import android.content.Context;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class PowerManagerWrapper {
    public final Method a;
    public final Method b;
    public final PowerManager c;
    public final Method d;

    public PowerManagerWrapper(Context context) throws NoSuchMethodException, SecurityException {
        Method method;
        Method method2;
        PowerManager powerManager = (PowerManager) context.getSystemService("power");
        this.c = powerManager;
        Class<?> cls = powerManager.getClass();
        Method method3 = null;
        try {
            method = cls.getMethod("goToSleep", Long.TYPE);
            try {
                method2 = cls.getMethod("wakeUp", Long.TYPE);
            } catch (NoSuchMethodException e) {
                e = e;
                method2 = null;
            }
        } catch (NoSuchMethodException e2) {
            e = e2;
            method = null;
            method2 = null;
        }
        try {
            method3 = cls.getMethod("nap", Long.TYPE);
        } catch (NoSuchMethodException e3) {
            e = e3;
            e.printStackTrace();
            this.a = method;
            this.d = method2;
            this.b = method3;
        }
        this.a = method;
        this.d = method2;
        this.b = method3;
    }

    public boolean isSleeping() {
        return !this.c.isInteractive();
    }

    public void napNow() {
        Method method = this.b;
        if (method != null) {
            try {
                method.invoke(this.c, Long.valueOf(SystemClock.uptimeMillis()));
            } catch (IllegalAccessException | InvocationTargetException e) {
                Log.e("PowerManagerWrapper", "napNow", e);
            }
        }
    }

    public void sleepNow() {
        Method method = this.a;
        if (method != null) {
            try {
                method.invoke(this.c, Long.valueOf(SystemClock.uptimeMillis()));
            } catch (IllegalAccessException | InvocationTargetException e) {
                Log.e("PowerManagerWrapper", "sleepNow", e);
            }
        }
    }

    public void wakeUpNow() {
        Method method = this.d;
        if (method != null) {
            try {
                method.invoke(this.c, Long.valueOf(SystemClock.uptimeMillis()));
            } catch (IllegalAccessException | InvocationTargetException e) {
                Log.e("PowerManagerWrapper", "wakeUpNow", e);
            }
        }
    }
}
