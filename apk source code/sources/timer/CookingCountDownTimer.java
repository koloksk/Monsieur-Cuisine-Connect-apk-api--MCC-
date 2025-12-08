package timer;

/* loaded from: classes.dex */
public abstract class CookingCountDownTimer extends BaseCountDownTimer {
    public CookingCountDownTimer(long j) {
        super(j);
    }

    @Override // timer.BaseCountDownTimer
    public void onFinish() {
    }

    @Override // timer.BaseCountDownTimer
    public void onTick(long j) {
    }

    public void skip() {
        cancel();
        onFinish();
    }

    public void startOrResume() {
        if (this.mPaused) {
            resume();
        }
        if (this.mCancelled || !this.mStarted) {
            start();
        }
    }
}
