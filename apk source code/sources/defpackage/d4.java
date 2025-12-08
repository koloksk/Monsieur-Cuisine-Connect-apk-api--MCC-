package defpackage;

import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(19)
/* loaded from: classes.dex */
public class d4 extends c4 {
    public static Method a;
    public static boolean b;
    public static Method c;
    public static boolean d;

    @Override // defpackage.b4
    public float a(@NonNull View view2) throws NoSuchMethodException, SecurityException {
        if (!d) {
            try {
                Method declaredMethod = View.class.getDeclaredMethod("getTransitionAlpha", new Class[0]);
                c = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.i("ViewUtilsApi19", "Failed to retrieve getTransitionAlpha method", e);
            }
            d = true;
        }
        Method method = c;
        if (method != null) {
            try {
                return ((Float) method.invoke(view2, new Object[0])).floatValue();
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e2) {
                throw new RuntimeException(e2.getCause());
            }
        }
        return super.a(view2);
    }
}
