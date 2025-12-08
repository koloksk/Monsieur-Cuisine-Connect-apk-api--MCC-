package timer;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public abstract class BaseCountDownTimer {
    public final long c;
    public long d;
    public long e;
    public long f;
    public final a b = new a(this);
    public boolean mCancelled = false;
    public boolean mPaused = false;
    public boolean mStarted = false;
    public final long a = 1000;

    public static class a extends Handler {
        public final WeakReference<BaseCountDownTimer> a;

        public a(BaseCountDownTimer baseCountDownTimer) {
            this.a = new WeakReference<>(baseCountDownTimer);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            BaseCountDownTimer baseCountDownTimer = this.a.get();
            if (baseCountDownTimer == null) {
                return;
            }
            synchronized (baseCountDownTimer) {
                if (!baseCountDownTimer.mPaused) {
                    long j = baseCountDownTimer.f - jElapsedRealtime;
                    if (j <= 0) {
                        baseCountDownTimer.onFinish();
                    } else {
                        baseCountDownTimer.onTick(j);
                        long jElapsedRealtime2 = baseCountDownTimer.a - ((SystemClock.elapsedRealtime() - baseCountDownTimer.e) % baseCountDownTimer.a);
                        while (jElapsedRealtime2 < 0) {
                            jElapsedRealtime2 += baseCountDownTimer.a;
                        }
                        if (!baseCountDownTimer.mCancelled) {
                            sendMessageDelayed(obtainMessage(1), jElapsedRealtime2);
                        }
                    }
                }
            }
        }
    }

    public BaseCountDownTimer(long j) {
        this.c = j;
    }

    public final void cancel() {
        this.b.removeMessages(1);
        this.mCancelled = true;
    }

    public abstract void onFinish();

    public abstract void onTick(long j);

    public long pause() {
        long jElapsedRealtime = this.f - SystemClock.elapsedRealtime();
        this.d = jElapsedRealtime;
        this.mPaused = true;
        return jElapsedRealtime;
    }

    public long resume() {
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        this.e = jElapsedRealtime;
        this.f = jElapsedRealtime + this.d;
        this.mPaused = false;
        a aVar = this.b;
        aVar.sendMessage(aVar.obtainMessage(1));
        return this.d;
    }

    @NonNull
    public final synchronized BaseCountDownTimer start() {
        if (this.c <= 0) {
            onFinish();
            return this;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        this.e = jElapsedRealtime;
        this.f = jElapsedRealtime + this.c;
        this.b.sendMessage(this.b.obtainMessage(1));
        this.mCancelled = false;
        this.mPaused = false;
        this.mStarted = true;
        return this;
    }
}
