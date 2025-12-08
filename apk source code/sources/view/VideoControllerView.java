package view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import de.silpion.mc2.R;
import defpackage.tq;
import java.lang.ref.WeakReference;
import java.util.Formatter;
import java.util.Locale;

/* loaded from: classes.dex */
public class VideoControllerView extends FrameLayout {
    public View.OnClickListener A;
    public MediaPlayerControl a;
    public Context b;
    public ViewGroup c;
    public View d;
    public ProgressBar e;
    public TextView f;
    public TextView g;
    public boolean h;
    public boolean i;
    public boolean j;
    public boolean k;
    public boolean l;
    public View.OnClickListener m;
    public View.OnClickListener n;
    public StringBuilder o;
    public Formatter p;
    public ImageButton q;
    public ImageButton r;
    public ImageButton s;
    public ImageButton t;
    public ImageButton u;
    public Handler v;
    public View.OnClickListener w;
    public View.OnClickListener x;
    public SeekBar.OnSeekBarChangeListener y;
    public View.OnClickListener z;

    public interface MediaPlayerControl {
        boolean canPause();

        boolean canSeekBackward();

        boolean canSeekForward();

        int getBufferPercentage();

        int getCurrentPosition();

        int getDuration();

        boolean isFullScreen();

        boolean isPlaying();

        void pause();

        void seekTo(int i);

        void start();

        void toggleFullScreen();
    }

    public class a implements SeekBar.OnSeekBarChangeListener {
        public a() {
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onProgressChanged(SeekBar seekBar, int i, boolean z) {
            if (VideoControllerView.this.a != null && z) {
                int duration = (int) ((r3.getDuration() * i) / 1000);
                VideoControllerView.this.a.seekTo(duration);
                VideoControllerView videoControllerView = VideoControllerView.this;
                TextView textView = videoControllerView.g;
                if (textView != null) {
                    textView.setText(videoControllerView.b(duration));
                }
            }
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStartTrackingTouch(SeekBar seekBar) {
            VideoControllerView.this.a(3600000);
            VideoControllerView videoControllerView = VideoControllerView.this;
            videoControllerView.i = true;
            videoControllerView.v.removeMessages(2);
        }

        @Override // android.widget.SeekBar.OnSeekBarChangeListener
        public void onStopTrackingTouch(SeekBar seekBar) {
            VideoControllerView videoControllerView = VideoControllerView.this;
            videoControllerView.i = false;
            videoControllerView.d();
            VideoControllerView.this.e();
            VideoControllerView.this.a(3000);
            VideoControllerView.this.v.sendEmptyMessage(2);
        }
    }

    public class b implements View.OnClickListener {
        public b() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view2) {
            if (VideoControllerView.this.a == null) {
                return;
            }
            VideoControllerView.this.a.seekTo(r2.getCurrentPosition() - 5000);
            VideoControllerView.this.d();
            VideoControllerView.this.a(3000);
        }
    }

    public class c implements View.OnClickListener {
        public c() {
        }

        @Override // android.view.View.OnClickListener
        public void onClick(View view2) {
            MediaPlayerControl mediaPlayerControl = VideoControllerView.this.a;
            if (mediaPlayerControl == null) {
                return;
            }
            VideoControllerView.this.a.seekTo(mediaPlayerControl.getCurrentPosition() + 15000);
            VideoControllerView.this.d();
            VideoControllerView.this.a(3000);
        }
    }

    public static class d extends Handler {
        public final WeakReference<VideoControllerView> a;

        public d(VideoControllerView videoControllerView) {
            this.a = new WeakReference<>(videoControllerView);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            VideoControllerView videoControllerView = this.a.get();
            if (videoControllerView == null || videoControllerView.a == null) {
                return;
            }
            int i = message.what;
            if (i == 1) {
                videoControllerView.b();
                return;
            }
            if (i != 2) {
                return;
            }
            int iD = videoControllerView.d();
            if (!videoControllerView.i && videoControllerView.h && videoControllerView.a.isPlaying()) {
                sendMessageDelayed(obtainMessage(2), 1000 - (iD % 1000));
            }
        }
    }

    public VideoControllerView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.v = new d(this);
        this.w = new View.OnClickListener() { // from class: vq
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.b(view2);
            }
        };
        this.x = tq.a;
        new View.OnClickListener() { // from class: uq
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.c(view2);
            }
        };
        this.y = new a();
        this.z = new b();
        this.A = new c();
        this.d = null;
        this.b = context;
        this.j = true;
        this.k = true;
    }

    public final void b() {
        ViewGroup viewGroup = this.c;
        if (viewGroup == null) {
            return;
        }
        try {
            viewGroup.removeView(this);
            this.v.removeMessages(2);
        } catch (IllegalArgumentException unused) {
            Log.w("MediaController", "already removed");
        }
        this.h = false;
    }

    public /* synthetic */ void c(View view2) {
        MediaPlayerControl mediaPlayerControl = this.a;
        if (mediaPlayerControl != null) {
            mediaPlayerControl.toggleFullScreen();
        }
        a(3000);
    }

    public final int d() {
        MediaPlayerControl mediaPlayerControl = this.a;
        if (mediaPlayerControl == null || this.i) {
            return 0;
        }
        int currentPosition = mediaPlayerControl.getCurrentPosition();
        int duration = this.a.getDuration();
        ProgressBar progressBar = this.e;
        if (progressBar != null) {
            if (duration > 0) {
                progressBar.setProgress((int) ((currentPosition * 1000) / duration));
            }
            this.e.setSecondaryProgress(this.a.getBufferPercentage() * 10);
        }
        TextView textView = this.f;
        if (textView != null) {
            textView.setText(b(duration));
        }
        TextView textView2 = this.g;
        if (textView2 != null) {
            textView2.setText(b(currentPosition));
        }
        return currentPosition;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (this.a == null) {
            return true;
        }
        int keyCode = keyEvent.getKeyCode();
        boolean z = keyEvent.getRepeatCount() == 0 && keyEvent.getAction() == 0;
        if (keyCode == 79 || keyCode == 85 || keyCode == 62) {
            if (z) {
                doPauseResume();
                a(3000);
                ImageButton imageButton = this.q;
                if (imageButton != null) {
                    imageButton.requestFocus();
                }
            }
            return true;
        }
        if (keyCode == 126) {
            if (z && !this.a.isPlaying()) {
                this.a.start();
                e();
                a(3000);
            }
            return true;
        }
        if (keyCode == 86 || keyCode == 127) {
            if (z && this.a.isPlaying()) {
                this.a.pause();
                e();
                a(3000);
            }
            return true;
        }
        if (keyCode == 25 || keyCode == 24 || keyCode == 164) {
            return super.dispatchKeyEvent(keyEvent);
        }
        if (keyCode != 4 && keyCode != 82) {
            a(3000);
            return super.dispatchKeyEvent(keyEvent);
        }
        if (z) {
            b();
        }
        return true;
    }

    public void doPauseResume() {
        MediaPlayerControl mediaPlayerControl = this.a;
        if (mediaPlayerControl == null) {
            return;
        }
        if (mediaPlayerControl.isPlaying()) {
            this.a.pause();
        } else {
            this.a.start();
        }
        e();
    }

    public final void e() {
        MediaPlayerControl mediaPlayerControl;
        if (this.d == null || this.q == null || (mediaPlayerControl = this.a) == null) {
            return;
        }
        if (mediaPlayerControl.isPlaying()) {
            this.q.setImageResource(R.drawable.asset_009_button_of_video_player_pause_orange);
        } else {
            this.q.setImageResource(R.drawable.button_play);
        }
    }

    public boolean isShowing() {
        return this.h;
    }

    @Override // android.view.View
    public void onFinishInflate() {
        super.onFinishInflate();
        View view2 = this.d;
        if (view2 != null) {
            a(view2);
        }
    }

    @Override // android.view.View
    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setClassName(VideoControllerView.class.getName());
    }

    @Override // android.view.View
    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setClassName(VideoControllerView.class.getName());
    }

    @Override // android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        a(3000);
        return true;
    }

    @Override // android.view.View
    public boolean onTrackballEvent(MotionEvent motionEvent) {
        a(3000);
        return false;
    }

    public void setAnchorView(ViewGroup viewGroup) {
        this.c = viewGroup;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, -1);
        removeAllViews();
        View viewInflate = ((LayoutInflater) this.b.getSystemService("layout_inflater")).inflate(R.layout.media_controller, this);
        this.d = viewInflate;
        a(viewInflate);
        addView(this.d, layoutParams);
    }

    @Override // android.view.View
    public void setEnabled(boolean z) {
        ImageButton imageButton = this.q;
        if (imageButton != null) {
            imageButton.setEnabled(z);
        }
        ImageButton imageButton2 = this.r;
        if (imageButton2 != null) {
            imageButton2.setEnabled(z);
        }
        ImageButton imageButton3 = this.s;
        if (imageButton3 != null) {
            imageButton3.setEnabled(z);
        }
        ImageButton imageButton4 = this.t;
        if (imageButton4 != null) {
            imageButton4.setEnabled(z && this.m != null);
        }
        ImageButton imageButton5 = this.u;
        if (imageButton5 != null) {
            imageButton5.setEnabled(z && this.n != null);
        }
        ProgressBar progressBar = this.e;
        if (progressBar != null) {
            progressBar.setEnabled(z);
        }
        a();
        super.setEnabled(z);
    }

    public void setMediaPlayer(MediaPlayerControl mediaPlayerControl) {
        this.a = mediaPlayerControl;
        e();
    }

    public void setPrevNextListeners(View.OnClickListener onClickListener, View.OnClickListener onClickListener2) {
        this.m = onClickListener;
        this.n = onClickListener2;
        this.l = true;
        if (this.d != null) {
            c();
            ImageButton imageButton = this.t;
            if (imageButton != null && !this.k) {
                imageButton.setVisibility(0);
            }
            ImageButton imageButton2 = this.u;
            if (imageButton2 == null || this.k) {
                return;
            }
            imageButton2.setVisibility(0);
        }
    }

    public void show() {
        a(3000);
    }

    public final void a(View view2) {
        ImageButton imageButton = (ImageButton) view2.findViewById(R.id.pause);
        this.q = imageButton;
        if (imageButton != null) {
            imageButton.requestFocus();
            this.q.setOnClickListener(this.w);
        }
        ImageButton imageButton2 = (ImageButton) view2.findViewById(R.id.back_btn);
        if (imageButton2 != null) {
            imageButton2.setOnClickListener(this.x);
            imageButton2.setVisibility(0);
        }
        ImageButton imageButton3 = (ImageButton) view2.findViewById(R.id.ffwd);
        this.r = imageButton3;
        if (imageButton3 != null) {
            imageButton3.setOnClickListener(this.A);
            if (!this.k) {
                this.r.setVisibility(this.j ? 0 : 8);
            }
        }
        ImageButton imageButton4 = (ImageButton) view2.findViewById(R.id.rew);
        this.s = imageButton4;
        if (imageButton4 != null) {
            imageButton4.setOnClickListener(this.z);
            if (!this.k) {
                this.s.setVisibility(this.j ? 0 : 8);
            }
        }
        ImageButton imageButton5 = (ImageButton) view2.findViewById(R.id.next);
        this.t = imageButton5;
        if (imageButton5 != null && !this.k && !this.l) {
            imageButton5.setVisibility(8);
        }
        ImageButton imageButton6 = (ImageButton) view2.findViewById(R.id.prev);
        this.u = imageButton6;
        if (imageButton6 != null && !this.k && !this.l) {
            imageButton6.setVisibility(8);
        }
        SeekBar seekBar = (SeekBar) view2.findViewById(R.id.mediacontroller_progress);
        this.e = seekBar;
        if (seekBar != null) {
            if (seekBar instanceof SeekBar) {
                seekBar.setOnSeekBarChangeListener(this.y);
            }
            this.e.setMax(1000);
        }
        this.f = (TextView) view2.findViewById(R.id.time);
        this.g = (TextView) view2.findViewById(R.id.time_current);
        this.o = new StringBuilder();
        this.p = new Formatter(this.o, Locale.getDefault());
        c();
    }

    public final void c() {
        ImageButton imageButton = this.t;
        if (imageButton != null) {
            imageButton.setOnClickListener(this.m);
            this.t.setEnabled(this.m != null);
        }
        ImageButton imageButton2 = this.u;
        if (imageButton2 != null) {
            imageButton2.setOnClickListener(this.n);
            this.u.setEnabled(this.n != null);
        }
    }

    public final String b(int i) {
        int i2 = i / 1000;
        int i3 = i2 % 60;
        int i4 = (i2 / 60) % 60;
        int i5 = i2 / 3600;
        this.o.setLength(0);
        return i5 > 0 ? this.p.format("%d:%02d:%02d", Integer.valueOf(i5), Integer.valueOf(i4), Integer.valueOf(i3)).toString() : this.p.format("%02d:%02d", Integer.valueOf(i4), Integer.valueOf(i3)).toString();
    }

    public VideoControllerView(Context context) {
        super(context);
        this.v = new d(this);
        this.w = new View.OnClickListener() { // from class: vq
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.b(view2);
            }
        };
        this.x = tq.a;
        new View.OnClickListener() { // from class: uq
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.c(view2);
            }
        };
        this.y = new a();
        this.z = new b();
        this.A = new c();
        this.b = context;
        this.j = true;
    }

    public /* synthetic */ void b(View view2) {
        doPauseResume();
        a(3000);
    }

    public final void a() {
        MediaPlayerControl mediaPlayerControl = this.a;
        if (mediaPlayerControl == null) {
            return;
        }
        try {
            if (this.q != null && !mediaPlayerControl.canPause()) {
                this.q.setEnabled(false);
            }
            if (this.s != null && !this.a.canSeekBackward()) {
                this.s.setEnabled(false);
            }
            if (this.r == null || this.a.canSeekForward()) {
                return;
            }
            this.r.setEnabled(false);
        } catch (IncompatibleClassChangeError unused) {
        }
    }

    public final void a(int i) {
        if (!this.h && this.c != null) {
            d();
            ImageButton imageButton = this.q;
            if (imageButton != null) {
                imageButton.requestFocus();
            }
            a();
            this.c.addView(this, new FrameLayout.LayoutParams(-1, -2, 80));
            this.h = true;
        }
        e();
        this.v.sendEmptyMessage(2);
        Message messageObtainMessage = this.v.obtainMessage(1);
        if (i != 0) {
            this.v.removeMessages(1);
            this.v.sendMessageDelayed(messageObtainMessage, i);
        }
    }
}
