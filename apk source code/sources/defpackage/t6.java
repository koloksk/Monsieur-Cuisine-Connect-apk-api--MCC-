package defpackage;

import android.support.v4.view.OnApplyWindowInsetsListener;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.WindowInsetsCompat;
import android.support.v7.app.AppCompatDelegateImplV9;
import android.view.View;
import java.lang.reflect.InvocationTargetException;

/* loaded from: classes.dex */
public class t6 implements OnApplyWindowInsetsListener {
    public final /* synthetic */ AppCompatDelegateImplV9 a;

    public t6(AppCompatDelegateImplV9 appCompatDelegateImplV9) {
        this.a = appCompatDelegateImplV9;
    }

    @Override // android.support.v4.view.OnApplyWindowInsetsListener
    public WindowInsetsCompat onApplyWindowInsets(View view2, WindowInsetsCompat windowInsetsCompat) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
        int iE = this.a.e(systemWindowInsetTop);
        if (systemWindowInsetTop != iE) {
            windowInsetsCompat = windowInsetsCompat.replaceSystemWindowInsets(windowInsetsCompat.getSystemWindowInsetLeft(), iE, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
        }
        return ViewCompat.onApplyWindowInsets(view2, windowInsetsCompat);
    }
}
