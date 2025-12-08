package defpackage;

import android.app.UiModeManager;
import android.content.Context;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatCallback;
import android.view.ActionMode;
import android.view.Window;
import defpackage.q6;

@RequiresApi(23)
/* loaded from: classes.dex */
public class s6 extends q6 {
    public final UiModeManager V;

    public class a extends q6.a {
        public a(Window.Callback callback) {
            super(callback);
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            return null;
        }

        @Override // android.support.v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
            return (s6.this.T && i == 0) ? a(callback) : super.onWindowStartingActionMode(callback, i);
        }
    }

    public s6(Context context, Window window, AppCompatCallback appCompatCallback) {
        super(context, window, appCompatCallback);
        this.V = (UiModeManager) context.getSystemService("uimode");
    }

    @Override // defpackage.o6
    public Window.Callback a(Window.Callback callback) {
        return new a(callback);
    }

    @Override // defpackage.q6
    public int f(int i) {
        if (i == 0 && this.V.getNightMode() == 0) {
            return -1;
        }
        return super.f(i);
    }
}
