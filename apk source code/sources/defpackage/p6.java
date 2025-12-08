package defpackage;

import android.content.Context;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegateImplV9;
import android.support.v7.view.menu.MenuBuilder;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.Window;
import defpackage.s6;
import java.util.List;

@RequiresApi(24)
/* loaded from: classes.dex */
public class p6 extends s6 {

    public class a extends s6.a {
        public a(Window.Callback callback) {
            super(callback);
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i) {
            MenuBuilder menuBuilder;
            AppCompatDelegateImplV9.PanelFeatureState panelFeatureStateB = p6.this.b(0);
            if (panelFeatureStateB == null || (menuBuilder = panelFeatureStateB.j) == null) {
                super.onProvideKeyboardShortcuts(list, menu, i);
            } else {
                super.onProvideKeyboardShortcuts(list, menuBuilder, i);
            }
        }
    }

    public p6(Context context, Window window, AppCompatCallback appCompatCallback) {
        super(context, window, appCompatCallback);
    }

    @Override // defpackage.s6, defpackage.o6
    public Window.Callback a(Window.Callback callback) {
        return new a(callback);
    }
}
