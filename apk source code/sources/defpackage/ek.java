package defpackage;

import android.media.MediaPlayer;
import fragment.VideoPlayerFragment;
import helper.LayoutHelper;
import machineAdapter.adapter.MachineCallbackAdapter;

/* loaded from: classes.dex */
public class ek extends MachineCallbackAdapter {
    public final /* synthetic */ VideoPlayerFragment a;

    public ek(VideoPlayerFragment videoPlayerFragment) {
        this.a = videoPlayerFragment;
    }

    public /* synthetic */ void a() {
        this.a.c.doPauseResume();
        this.a.c.show();
    }

    public /* synthetic */ void b() throws IllegalStateException {
        MediaPlayer mediaPlayer = this.a.e;
        mediaPlayer.seekTo(mediaPlayer.getCurrentPosition() + 2000);
        this.a.c.show();
    }

    public /* synthetic */ void c() throws IllegalStateException {
        this.a.e.seekTo(r0.getCurrentPosition() - 2000);
        this.a.c.show();
    }

    @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
    public void onJogDialPushed(int i) {
        VideoPlayerFragment videoPlayerFragment = this.a;
        if (videoPlayerFragment.f == null || videoPlayerFragment.c == null || !LayoutHelper.getInstance().isViewSelected(9) || i != 0) {
            return;
        }
        this.a.f.post(new Runnable() { // from class: nj
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a();
            }
        });
    }

    @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
    public void onJogDialTurned(int i, long j) {
        VideoPlayerFragment videoPlayerFragment = this.a;
        if (videoPlayerFragment.f == null || videoPlayerFragment.e == null || videoPlayerFragment.c == null || !LayoutHelper.getInstance().isViewSelected(9)) {
            return;
        }
        if (1 == i) {
            this.a.f.post(new Runnable() { // from class: oj
                @Override // java.lang.Runnable
                public final void run() throws IllegalStateException {
                    this.a.b();
                }
            });
        } else {
            this.a.f.post(new Runnable() { // from class: mj
                @Override // java.lang.Runnable
                public final void run() throws IllegalStateException {
                    this.a.c();
                }
            });
        }
    }
}
