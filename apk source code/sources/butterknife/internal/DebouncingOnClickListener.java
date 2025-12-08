package butterknife.internal;

import android.view.View;

/* loaded from: classes.dex */
public abstract class DebouncingOnClickListener implements View.OnClickListener {
    public static boolean a = true;
    public static final Runnable b = new a();

    public static class a implements Runnable {
        @Override // java.lang.Runnable
        public void run() {
            DebouncingOnClickListener.a = true;
        }
    }

    public abstract void doClick(View view2);

    @Override // android.view.View.OnClickListener
    public final void onClick(View view2) {
        if (a) {
            a = false;
            view2.post(b);
            doClick(view2);
        }
    }
}
