package helper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class SystemProperties {
    public static Method a;
    public static Method b;

    static {
        try {
            for (Method method : Class.forName("android.os.SystemProperties").getMethods()) {
                String name = method.getName();
                if (name.equals("get")) {
                    a = method;
                } else if (name.equals("set")) {
                    b = method;
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String get(String str, String str2) {
        try {
            return (String) a.invoke(null, str, str2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return str2;
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
            return str2;
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
            return str2;
        }
    }

    public static void set(String str, String str2) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        try {
            b.invoke(null, str, str2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e2) {
            e2.printStackTrace();
        } catch (InvocationTargetException e3) {
            e3.printStackTrace();
        }
    }
}
