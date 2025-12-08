package defpackage;

import android.graphics.Matrix;
import android.util.Log;
import android.widget.ImageView;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class b3 {
    public static final d3 a = new c3();

    public static void a(ImageView imageView) {
    }

    public static void a(ImageView imageView, Matrix matrix) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
        if (!c3.b) {
            try {
                Method declaredMethod = ImageView.class.getDeclaredMethod("animateTransform", Matrix.class);
                c3.a = declaredMethod;
                declaredMethod.setAccessible(true);
            } catch (NoSuchMethodException e) {
                Log.i("ImageViewUtilsApi21", "Failed to retrieve animateTransform method", e);
            }
            c3.b = true;
        }
        Method method = c3.a;
        if (method != null) {
            try {
                method.invoke(imageView, matrix);
            } catch (IllegalAccessException unused) {
            } catch (InvocationTargetException e2) {
                throw new RuntimeException(e2.getCause());
            }
        }
    }
}
