package view.knob;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import application.App;
import de.silpion.mc2.R;
import sound.SoundLength;

/* loaded from: classes.dex */
public class KnobScale extends Knob {
    public final int[] e0;
    public final int[] f0;
    public KnobScaleTareListener g0;
    public Paint h0;
    public int i0;
    public boolean j0;
    public Runnable k0;

    public interface KnobScaleTareListener {
        void onTareClick();
    }

    public KnobScale(Context context) {
        this(context, null, 0);
    }

    public /* synthetic */ void a(View view2) {
        App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
        KnobScaleTareListener knobScaleTareListener = this.g0;
        if (knobScaleTareListener != null) {
            knobScaleTareListener.onTareClick();
        }
    }

    @Override // view.knob.Knob
    public void d() {
        if (this.g0 != null) {
            App.getInstance().playSound(R.raw.click, SoundLength.SHORT);
            this.g0.onTareClick();
        }
    }

    public void init() {
        setMoveEnabled(false);
        setBackgroundVisibility(true);
        setScaleFromBus(0L);
        setThumbVisible(false);
        setThumbEnabled(false);
        setBottomViews(true);
        this.N.setTranslationY(16.0f);
        invalidate();
        if (this.K == 1) {
            d();
        }
    }

    @Override // view.knob.Knob
    public void initViews() {
        super.initViews();
        ((ViewGroup) this.c.findViewById(R.id.tara_button)).setOnClickListener(new View.OnClickListener() { // from class: lr
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                this.a.a(view2);
            }
        });
        scaleKnob();
    }

    @Override // view.knob.Knob, view.knob.BaseKnob, android.view.View
    public void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (this.i0 != 0) {
            canvas.drawArc(this.a, ((a(r0) * this.i) / 1000) + (this.h - 90) + this.g, 2.0f, false, this.h0);
        }
    }

    @Override // view.knob.Knob
    public void scaleKnob() {
        int i = this.K;
        if (i == 1) {
            b();
        } else if (i == 2) {
            this.I = ObjectAnimator.ofFloat(this.c, "scaleX", 1.15f, 1.0f);
            this.J = ObjectAnimator.ofFloat(this.c, "scaleY", 1.15f, 1.0f);
        }
        c();
    }

    public void setKnobScaleTareListener(@Nullable KnobScaleTareListener knobScaleTareListener) {
        this.g0 = knobScaleTareListener;
    }

    @SuppressLint({"DefaultLocale"})
    public void setScaleFromBus(long j) {
        Runnable runnable;
        int iRound = (int) (Math.round(j / 5.0d) * 5);
        String str = String.format("%d", Integer.valueOf(iRound));
        setProgress(a(iRound));
        setText(str + "g");
        int i = this.i0;
        if (i > 0 && iRound >= i && (runnable = this.k0) != null && !this.j0) {
            runnable.run();
            this.j0 = true;
        }
        if (iRound < this.i0) {
            this.j0 = false;
        }
    }

    public void setScaleOverloadText() {
        setText("----g");
    }

    public void setTargetWeight(int i) {
        if (this.i0 == i) {
            return;
        }
        this.i0 = i;
        invalidate();
    }

    public void setTargetWeightReachedCallback(Runnable runnable) {
        this.k0 = runnable;
    }

    public KnobScale(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public KnobScale(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.e0 = new int[]{350, 350, SoundLength.SMALL_THRESHOLD};
        this.f0 = new int[]{100, 400, 4500};
        this.i0 = 0;
        this.j0 = false;
        DashPathEffect dashPathEffect = new DashPathEffect(new float[]{4.0f, 7.0f}, 0.0f);
        Paint paint = new Paint();
        this.h0 = paint;
        paint.setColor(getProgressColor());
        this.h0.setAntiAlias(true);
        this.h0.setStyle(Paint.Style.STROKE);
        this.h0.setStrokeWidth(12.0f);
        this.h0.setPathEffect(dashPathEffect);
    }

    public final int a(int i) {
        int i2 = 0;
        if (i == 0) {
            return 0;
        }
        int i3 = 0;
        while (true) {
            int[] iArr = this.f0;
            if (i2 >= iArr.length || i <= iArr[i2]) {
                break;
            }
            i3 += this.e0[i2];
            i -= iArr[i2];
            i2++;
        }
        Log.d("KnobScale", "i = " + i2);
        int[] iArr2 = this.f0;
        return i2 < iArr2.length ? i3 + ((i * this.e0[i2]) / iArr2[i2]) : i3;
    }
}
