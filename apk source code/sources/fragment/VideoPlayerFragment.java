package fragment;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.RawRes;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import application.App;
import de.silpion.mc2.R;
import defpackage.ek;
import java.io.IOException;
import machineAdapter.adapter.MachineCallbackAdapter;
import view.VideoControllerView;

/* loaded from: classes.dex */
public class VideoPlayerFragment extends BaseFragment implements VideoControllerView.MediaPlayerControl, SurfaceHolder.Callback, MediaPlayer.OnPreparedListener {
    public VideoControllerView c;
    public MachineCallbackAdapter d;
    public MediaPlayer e;
    public RelativeLayout f;

    @RawRes
    public int g;
    public SurfaceView h;

    public /* synthetic */ boolean a(View view2, MotionEvent motionEvent) {
        this.c.show();
        return true;
    }

    public final void b() {
        if (this.d == null) {
            return;
        }
        App.getInstance().getMachineAdapter().unregisterMachineCallback(this.d);
        this.d = null;
    }

    @Override // view.VideoControllerView.MediaPlayerControl
    public boolean canPause() {
        return true;
    }

    @Override // view.VideoControllerView.MediaPlayerControl
    public boolean canSeekBackward() {
        return true;
    }

    @Override // view.VideoControllerView.MediaPlayerControl
    public boolean canSeekForward() {
        return true;
    }

    @Override // view.VideoControllerView.MediaPlayerControl
    public int getBufferPercentage() {
        return 0;
    }

    @Override // view.VideoControllerView.MediaPlayerControl
    public int getCurrentPosition() {
        return this.e.getCurrentPosition();
    }

    @Override // view.VideoControllerView.MediaPlayerControl
    public int getDuration() {
        return this.e.getDuration();
    }

    @Override // view.VideoControllerView.MediaPlayerControl
    public boolean isFullScreen() {
        return false;
    }

    @Override // view.VideoControllerView.MediaPlayerControl
    public boolean isPlaying() {
        return this.e.isPlaying();
    }

    @Override // android.support.v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        b();
        this.d = new ek(this);
        App.getInstance().getMachineAdapter().registerMachineCallback(this.d);
    }

    @Override // android.support.v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        MediaPlayer mediaPlayer = new MediaPlayer();
        this.e = mediaPlayer;
        mediaPlayer.setOnPreparedListener(this);
        this.c = new VideoControllerView(getActivity().getBaseContext());
    }

    @Override // android.support.v4.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        RelativeLayout relativeLayout = (RelativeLayout) layoutInflater.inflate(R.layout.fragment_video_player, viewGroup, false);
        this.f = relativeLayout;
        SurfaceView surfaceView = (SurfaceView) relativeLayout.findViewById(R.id.videoSurface);
        this.h = surfaceView;
        surfaceView.getHolder().addCallback(this);
        a();
        this.c.show();
        this.f.setOnTouchListener(new View.OnTouchListener() { // from class: pj
            @Override // android.view.View.OnTouchListener
            public final boolean onTouch(View view2, MotionEvent motionEvent) {
                return this.a.a(view2, motionEvent);
            }
        });
        return this.f;
    }

    @Override // android.support.v4.app.Fragment
    public void onDetach() throws IllegalStateException {
        super.onDetach();
        b();
        MediaPlayer mediaPlayer = this.e;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        this.e.stop();
    }

    @Override // android.media.MediaPlayer.OnPreparedListener
    public void onPrepared(MediaPlayer mediaPlayer) throws IllegalStateException {
        this.c.setMediaPlayer(this);
        this.c.setAnchorView((FrameLayout) this.f.findViewById(R.id.videoSurfaceContainer));
        this.e.start();
    }

    @Override // view.VideoControllerView.MediaPlayerControl
    public void pause() throws IllegalStateException {
        this.e.pause();
    }

    @Override // view.VideoControllerView.MediaPlayerControl
    public void seekTo(int i) throws IllegalStateException {
        this.e.seekTo(i);
    }

    public void setVideoResId(@RawRes int i) {
        this.g = i;
        a();
    }

    @Override // view.VideoControllerView.MediaPlayerControl
    public void start() throws IllegalStateException {
        this.e.start();
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i2, int i3) {
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        this.e.setDisplay(surfaceHolder);
    }

    @Override // android.view.SurfaceHolder.Callback
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) throws IllegalStateException {
        MediaPlayer mediaPlayer = this.e;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        this.e.stop();
    }

    @Override // view.VideoControllerView.MediaPlayerControl
    public void toggleFullScreen() {
    }

    public final void a() throws IllegalStateException, IOException, SecurityException, IllegalArgumentException {
        MediaPlayer mediaPlayer;
        if (this.g == 0 || (mediaPlayer = this.e) == null) {
            return;
        }
        try {
            mediaPlayer.setAudioStreamType(3);
            this.e.setDataSource(getActivity(), Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + this.g));
            this.e.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
