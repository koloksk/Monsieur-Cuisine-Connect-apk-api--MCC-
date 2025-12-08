package defpackage;

import android.graphics.Matrix;
import android.support.annotation.RequiresApi;
import android.support.transition.GhostViewImpl;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@RequiresApi(21)
/* loaded from: classes.dex */
public class z2 implements GhostViewImpl {
    public static Class<?> b;
    public static boolean c;
    public static Method d;
    public static boolean e;
    public static Method f;
    public static boolean g;
    public final View a;

    public static class b implements GhostViewImpl.Creator {
        @Override // android.support.transition.GhostViewImpl.Creator
        public GhostViewImpl addGhost(View view2, ViewGroup viewGroup, Matrix matrix) throws NoSuchMethodException, SecurityException {
            if (!z2.e) {
                try {
                    z2.a();
                    Method declaredMethod = z2.b.getDeclaredMethod("addGhost", View.class, ViewGroup.class, Matrix.class);
                    z2.d = declaredMethod;
                    declaredMethod.setAccessible(true);
                } catch (NoSuchMethodException e) {
                    Log.i("GhostViewApi21", "Failed to retrieve addGhost method", e);
                }
                z2.e = true;
            }
            Method method = z2.d;
            a aVar = null;
            if (method != null) {
                try {
                    return new z2((View) method.invoke(null, view2, viewGroup, matrix), aVar);
                } catch (IllegalAccessException unused) {
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2.getCause());
                }
            }
            return null;
        }

        @Override // android.support.transition.GhostViewImpl.Creator
        public void removeGhost(View view2) throws IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
            if (!z2.g) {
                try {
                    z2.a();
                    Method declaredMethod = z2.b.getDeclaredMethod("removeGhost", View.class);
                    z2.f = declaredMethod;
                    declaredMethod.setAccessible(true);
                } catch (NoSuchMethodException e) {
                    Log.i("GhostViewApi21", "Failed to retrieve removeGhost method", e);
                }
                z2.g = true;
            }
            Method method = z2.f;
            if (method != null) {
                try {
                    method.invoke(null, view2);
                } catch (IllegalAccessException unused) {
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2.getCause());
                }
            }
        }
    }

    public /* synthetic */ z2(View view2, a aVar) {
        this.a = view2;
    }

    public static void a() {
        if (c) {
            return;
        }
        try {
            b = Class.forName("android.view.GhostView");
        } catch (ClassNotFoundException e2) {
            Log.i("GhostViewApi21", "Failed to retrieve GhostView class", e2);
        }
        c = true;
    }

    @Override // android.support.transition.GhostViewImpl
    public void a(ViewGroup viewGroup, View view2) {
    }

    @Override // android.support.transition.GhostViewImpl
    public void setVisibility(int i) {
        this.a.setVisibility(i);
    }
}
