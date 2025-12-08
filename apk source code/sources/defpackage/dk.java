package defpackage;

import android.content.res.Resources;
import android.util.Log;
import fragment.TutorialFragment;
import helper.LayoutHelper;
import machineAdapter.adapter.MachineCallbackAdapter;

/* loaded from: classes.dex */
public class dk extends MachineCallbackAdapter {
    public final /* synthetic */ TutorialFragment a;

    public dk(TutorialFragment tutorialFragment) {
        this.a = tutorialFragment;
    }

    public /* synthetic */ void a(int i) throws Resources.NotFoundException {
        this.a.d.setCurrentItem(i - 1, false);
    }

    public /* synthetic */ void b(int i) throws Resources.NotFoundException {
        this.a.d.setCurrentItem(i + 1, false);
    }

    @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
    public void onJogDialTurned(int i, long j) {
        if (LayoutHelper.getInstance().isViewSelected(8)) {
            final int currentItem = this.a.d.getCurrentItem();
            if (1 == i) {
                Log.d(TutorialFragment.g, "jog dial turned right");
                if (currentItem > 0) {
                    this.a.getActivity().runOnUiThread(new Runnable() { // from class: jj
                        @Override // java.lang.Runnable
                        public final void run() throws Resources.NotFoundException {
                            this.a.a(currentItem);
                        }
                    });
                    return;
                }
                return;
            }
            Log.d(TutorialFragment.g, "jog dial turned left");
            if (currentItem < this.a.e.getCount() - 1) {
                this.a.getActivity().runOnUiThread(new Runnable() { // from class: ij
                    @Override // java.lang.Runnable
                    public final void run() throws Resources.NotFoundException {
                        this.a.b(currentItem);
                    }
                });
            }
        }
    }
}
