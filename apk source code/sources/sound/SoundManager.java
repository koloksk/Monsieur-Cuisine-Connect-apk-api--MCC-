package sound;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.SparseIntArray;
import application.App;
import de.silpion.mc2.R;

/* loaded from: classes.dex */
public class SoundManager {
    public long a;
    public Handler b = new Handler();
    public boolean c = false;
    public SparseIntArray e = new SparseIntArray();
    public float f = 1.0f;
    public SoundPool d = new SoundPool.Builder().setMaxStreams(2).build();

    public static class a extends AsyncTask<Void, Void, Void> {
        public final SoundManager a;

        public a(SoundManager soundManager) {
            this.a = soundManager;
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Void[] voidArr) {
            App app = App.getInstance();
            this.a.a(app, R.raw.click);
            this.a.a(app, R.raw.error);
            this.a.a(app, R.raw.lid_closed);
            this.a.a(app, R.raw.lid_opened);
            this.a.a(app, R.raw.finished);
            this.a.a(app, R.raw.info);
            this.a.a(app, R.raw.bell);
            return null;
        }
    }

    public SoundManager() {
        this.a = 0L;
        this.a = System.currentTimeMillis();
    }

    public static int getStreamMusicLevel(Activity activity2) {
        return ((AudioManager) activity2.getSystemService("audio")).getStreamVolume(3);
    }

    public static void initStreamTypeMedia(Activity activity2) {
        activity2.setVolumeControlStream(3);
    }

    public /* synthetic */ void a(int i, int i2, int i3) {
        this.a = System.currentTimeMillis();
        SoundPool soundPool = this.d;
        int i4 = this.e.get(i);
        float f = this.f;
        final int iPlay = soundPool.play(i4, f, f, i2, 0, 1.0f);
        this.b.postDelayed(new Runnable() { // from class: ip
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a(iPlay);
            }
        }, i3);
    }

    public void playSound(final int i, final int i2) {
        if (this.c || this.e.indexOfKey(i) < 0) {
            return;
        }
        if ((i2 == 1000 || i2 == 300) && System.currentTimeMillis() < this.a + i2) {
            return;
        }
        final int i3 = 1;
        switch (i) {
            case R.raw.bell /* 2131623936 */:
            case R.raw.click /* 2131623937 */:
            case R.raw.info /* 2131623942 */:
            case R.raw.lid_closed /* 2131623944 */:
            case R.raw.lid_opened /* 2131623945 */:
            default:
                i3 = 0;
                break;
            case R.raw.error /* 2131623938 */:
                i3 = 2;
                break;
            case R.raw.finished /* 2131623940 */:
            case R.raw.start /* 2131623972 */:
                break;
        }
        new Thread(new Runnable() { // from class: hp
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a(i, i3, i2);
            }
        }).start();
    }

    public void setSoundVolume(int i) {
        this.c = i <= 0;
        this.f = i / 100.0f;
    }

    public void start(Context context) {
        this.d.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() { // from class: gp
            @Override // android.media.SoundPool.OnLoadCompleteListener
            public final void onLoadComplete(SoundPool soundPool, int i, int i2) {
                this.a.a(soundPool, i, i2);
            }
        });
        this.e.put(R.raw.start, this.d.load(context, R.raw.start, 1));
    }

    public /* synthetic */ void a(SoundPool soundPool, int i, int i2) {
        this.d.setOnLoadCompleteListener(null);
        playSound(R.raw.start, SoundLength.LONG);
        new Handler().postDelayed(new Runnable() { // from class: jp
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a();
            }
        }, 3000L);
    }

    public /* synthetic */ void a() {
        new a(this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public void a(Context context, int i) {
        this.e.put(i, this.d.load(context, i, 1));
    }

    public /* synthetic */ void a(int i) {
        this.d.stop(i);
    }
}
