package view.knob;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import de.silpion.mc2.R;
import defpackage.g9;
import helper.KnobUtils;
import helper.MinutesSeconds;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import sound.SoundLength;
import view.QuicksandTextView;

/* loaded from: classes.dex */
public class KnobTime extends Knob {
    public static final String l0 = KnobTime.class.getSimpleName();
    public static final List<Integer> m0 = new a();
    public static final int n0;
    public int e0;
    public boolean f0;
    public KnobTimeListener g0;
    public QuicksandTextView h0;
    public QuicksandTextView i0;
    public QuicksandTextView j0;
    public QuicksandTextView k0;

    public interface KnobTimeListener {
        void onTimeValueSet(long j);
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
        public static final int TIMER_DOWN = 1;
        public static final int TIMER_UP = 10;
    }

    public static class a extends ArrayList<Integer> {
        public a() {
            addAll(KnobTime.a(0, 60, 1));
            addAll(KnobTime.a(60, SoundLength.SMALL_THRESHOLD, 10));
            addAll(KnobTime.a(SoundLength.SMALL_THRESHOLD, 1200, 30));
            addAll(KnobTime.a(1200, 5940, 60));
        }
    }

    static {
        n0 = r0.size() - 1;
    }

    public KnobTime(Context context) {
        this(context, null, 0);
    }

    public static /* synthetic */ List a(int i, int i2, int i3) {
        if (i3 == 0) {
            i3 = 1;
        } else if (i3 < 0) {
            i3 *= -1;
        }
        if (i <= i2) {
            i2 = i;
            i = i2;
        }
        ArrayList arrayList = new ArrayList((i - i2) / i3);
        while (i2 <= i) {
            arrayList.add(Integer.valueOf(i2));
            i2 += i3;
        }
        return arrayList;
    }

    private long getMillisFromProgress() {
        return a(getProgress());
    }

    public int getCurrentMode() {
        return this.e0;
    }

    public long getMaxTimeInMillis() {
        return a(getMaxProgressLimit());
    }

    public void init() {
        setCurrentMode(10);
        initTimeKnob();
        setDrawActiveIndicator(true);
    }

    public void initTimeKnob() {
        setMaxProgress(n0);
        setMaxProgressLimit(n0);
    }

    public void initTurbo() {
        initTimeKnob();
        setModeImageToSelected();
        setBackgroundVisibility(true);
        valueSetVisibility(true);
        setMaxTimeInMillis(600000L);
        setTime(600000L);
        setThumbEnabled(false);
        setMoveEnabled(false);
        scaleKnob();
    }

    @Override // view.knob.Knob
    public void initViews() {
        super.initViews();
        this.h0 = (QuicksandTextView) this.c.findViewById(R.id.knob_minutes_tv);
        this.i0 = (QuicksandTextView) this.c.findViewById(R.id.knob_seconds_tv);
        this.j0 = (QuicksandTextView) this.c.findViewById(R.id.bottom_dial_minutes_tv);
        this.k0 = (QuicksandTextView) this.c.findViewById(R.id.bottom_dial_seconds_tv);
        this.M.setText(getResources().getString(R.string.colon));
        this.M.setVisibility(8);
    }

    public void setCurrentAngleUsingMillis(long j) {
        this.e = a(j) / a();
        String str = l0;
        StringBuilder sbA = g9.a("setCurrentAngleUsingMillis >> mCurrentAngle = ");
        sbA.append(this.e);
        Log.d(str, sbA.toString());
    }

    public void setCurrentMode(int i) {
        this.e0 = i;
    }

    public void setDisplayProgress(boolean z) {
        this.f0 = z;
    }

    public void setKnobTimeListener(@Nullable KnobTimeListener knobTimeListener) {
        if (knobTimeListener != null) {
            this.g0 = knobTimeListener;
        }
    }

    public void setMaxTimeInMillis(long j) {
        setMaxProgressLimit(a(j));
    }

    public void setPreheatMode() {
        valueSetVisibility(false);
        setModeImageToIdle();
        setDisplayProgress(false);
    }

    public void setTime(long j) {
        MinutesSeconds minSec;
        QuicksandTextView quicksandTextView;
        if (!this.f0 || (minSec = KnobUtils.getMinSec(j)) == null || (quicksandTextView = this.h0) == null || this.i0 == null) {
            return;
        }
        quicksandTextView.setText(minSec.minutes);
        this.i0.setText(minSec.seconds);
    }

    @Override // view.knob.Knob, view.knob.BaseKnob
    public synchronized void setValueFromUser() {
        super.setValueFromUser();
        boolean z = this.f0;
        long millisFromProgress = getMillisFromProgress();
        this.f0 = true;
        setTime(millisFromProgress);
        this.f0 = z;
        if (this.g0 != null) {
            String str = l0;
            StringBuilder sb = new StringBuilder();
            sb.append("setValueFromUser: TIMER_");
            sb.append(this.e0 == 10 ? "UP" : "DOWN");
            sb.append("  >> ");
            sb.append(millisFromProgress);
            Log.i(str, sb.toString());
            this.g0.onTimeValueSet(millisFromProgress);
        }
    }

    public void unsetPreheatMode() {
        valueSetVisibility(true);
        setDisplayProgress(true);
    }

    public void updateProgressTime(long j) {
        if (this.f0) {
            setProgress(a(j));
        }
    }

    public void valueSetText(long j) {
        MinutesSeconds minSec = KnobUtils.getMinSec(j);
        if (minSec != null) {
            String str = minSec.minutes;
            String str2 = minSec.seconds;
            QuicksandTextView quicksandTextView = this.j0;
            if (quicksandTextView == null || this.k0 == null) {
                return;
            }
            quicksandTextView.setText(str);
            this.k0.setText(str2);
            this.M.setVisibility(0);
        }
    }

    public KnobTime(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KnobTime(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f0 = true;
        setMaxValue(n0);
        setMaxValueLimit(n0);
    }

    public final long a(int i) {
        int iIntValue;
        if (i < 0) {
            return 0L;
        }
        int i2 = n0;
        if (i >= i2) {
            iIntValue = m0.get(i2).intValue();
        } else {
            iIntValue = m0.get(i).intValue();
        }
        return iIntValue * 1000;
    }

    public final int a(long j) {
        if (j < 0) {
            return n0;
        }
        int i = 0;
        while (true) {
            int i2 = n0;
            if (i > i2) {
                return i2;
            }
            if (m0.get(i).intValue() * 1000 >= j) {
                return i;
            }
            i++;
        }
    }
}
