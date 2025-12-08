package view.knob;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import application.App;
import de.silpion.mc2.R;
import helper.KnobUtils;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import view.QuicksandTextView;

/* loaded from: classes.dex */
public abstract class Knob extends BaseKnob implements View.OnClickListener {
    public static final String b0 = Knob.class.getSimpleName();
    public static Bitmap c0;
    public static Bitmap d0;
    public final Drawable G;
    public final Drawable H;
    public ObjectAnimator I;
    public ObjectAnimator J;
    public int K;
    public ImageView L;
    public QuicksandTextView M;
    public RelativeLayout N;
    public boolean O;
    public Paint P;
    public ViewGroup Q;
    public boolean R;

    @Nullable
    public KnobValueListener S;
    public int T;
    public int U;
    public SkipPreheatStepListener V;

    @Nullable
    public StateListener W;
    public QuicksandTextView a0;

    public interface KnobValueListener {
        void onValueSet(int i);
    }

    public interface SkipPreheatStepListener {
        void onSkipPreheatStep();
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
        public static final int IDLE = 1;
        public static final int SELECTED = 2;
    }

    public interface StateListener {
        void knobSelected(Knob knob, int i, String str);
    }

    public Knob(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.K = 1;
        this.O = false;
        this.R = false;
        this.T = 10;
        this.U = 10;
        if (attributeSet == null) {
            this.G = null;
            this.H = null;
        } else {
            TypedArray typedArrayObtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.Knob, i, 0);
            this.G = typedArrayObtainStyledAttributes.getDrawable(0);
            this.H = typedArrayObtainStyledAttributes.getDrawable(1);
            typedArrayObtainStyledAttributes.recycle();
        }
    }

    public void b() {
        this.I = ObjectAnimator.ofFloat(this.c, "scaleX", 1.15f);
        this.J = ObjectAnimator.ofFloat(this.c, "scaleY", 1.15f);
        c();
    }

    public void c() {
        ObjectAnimator objectAnimator = this.I;
        if (objectAnimator == null || this.J == null) {
            return;
        }
        objectAnimator.setDuration(50L);
        this.J.setDuration(50L);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(this.I).with(this.J);
        animatorSet.start();
    }

    public void d() {
        if (this.c == null) {
            this.c = (RelativeLayout) getParent();
        }
        if (this.c != null) {
            if (this.K == 2) {
                scaleKnob();
                this.K = 1;
                return;
            }
            KnobUtils.setActiveIndicator(this);
            scaleKnob();
            this.K = 2;
            StateListener stateListener = this.W;
            if (stateListener != null) {
                stateListener.knobSelected(this, 2, this.d);
            }
            setThumbEnabled(true);
        }
    }

    public void deselect() {
        if (this.K == 2) {
            d();
        }
    }

    public int getCurrentState() {
        return this.K;
    }

    public int getMaxValue() {
        return this.T;
    }

    public int getMaxValueLimit() {
        return this.U;
    }

    public int getProgressFromValue(int i) {
        if (i == 0 || this.T == 0) {
            return 0;
        }
        return (getMaxProgress() * i) / this.T;
    }

    public int getValueFromProgress() {
        int progress = getProgress();
        int maxProgress = getMaxProgress();
        if (maxProgress == 0) {
            return 0;
        }
        return Math.round((progress * this.T) / maxProgress);
    }

    public void initViews() {
        this.L = (ImageView) this.c.findViewById(R.id.knob_mode_iv);
        this.a0 = (QuicksandTextView) this.c.findViewById(R.id.knob_colon_tv);
        this.Q = (ViewGroup) this.c.findViewById(R.id.bottom_dial_rl);
        this.N = (RelativeLayout) this.c.findViewById(R.id.top_layout);
        this.M = (QuicksandTextView) this.c.findViewById(R.id.bottom_dial_colon_tv);
        Paint paint = new Paint();
        this.P = paint;
        paint.setAntiAlias(true);
        if (c0 == null) {
            c0 = BitmapFactory.decodeResource(getResources(), R.drawable.led_enabled_half_4);
        }
        if (d0 == null) {
            d0 = BitmapFactory.decodeResource(getResources(), R.drawable.led_disabled_half);
        }
    }

    public boolean isActive() {
        return this.O;
    }

    public boolean isDrawActiveIndicator() {
        return this.R;
    }

    @Override // android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        View viewFindViewById = ((View) getParent()).findViewById(R.id.preheat_skip_btn);
        if (viewFindViewById != null) {
            viewFindViewById.setOnClickListener(this);
        }
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view2) {
        SkipPreheatStepListener skipPreheatStepListener = this.V;
        if (skipPreheatStepListener != null) {
            skipPreheatStepListener.onSkipPreheatStep();
        }
    }

    @Override // view.knob.BaseKnob, android.view.View
    public void onDraw(@NonNull Canvas canvas) {
        if (this.R) {
            if (this.O) {
                canvas.drawBitmap(c0, (getWidth() / 2.0f) - 4.0f, getHeight() - 14, this.P);
            } else {
                canvas.drawBitmap(d0, (getWidth() / 2.0f) - 4.0f, getHeight() - 14, this.P);
            }
        }
        super.onDraw(canvas);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x004d  */
    @Override // view.knob.BaseKnob, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(@android.support.annotation.NonNull android.view.MotionEvent r8) {
        /*
            r7 = this;
            boolean r0 = r7.f
            r1 = 0
            if (r0 == 0) goto L64
            android.view.ViewParent r0 = r7.getParent()
            r2 = 1
            r0.requestDisallowInterceptTouchEvent(r2)
            int r0 = r8.getAction()
            if (r0 == 0) goto L5a
            if (r0 == r2) goto L4d
            r3 = 2
            if (r0 == r3) goto L1c
            r8 = 3
            if (r0 == r8) goto L4d
            goto L63
        L1c:
            boolean r0 = r7.j
            if (r0 == 0) goto L63
            boolean r0 = r7.b
            if (r0 != 0) goto L63
            float r0 = r8.getX()
            float r3 = r8.getY()
            double r3 = r7.a(r0, r3)
            int r0 = r7.getProgress()
            float r0 = (float) r0
            float r5 = r7.a()
            float r0 = r0 / r5
            double r5 = (double) r0
            double r5 = r5 - r3
            double r3 = java.lang.Math.abs(r5)
            r5 = 4633641066610819072(0x404e000000000000, double:60.0)
            int r0 = (r3 > r5 ? 1 : (r3 == r5 ? 0 : -1))
            if (r0 < 0) goto L47
            r1 = r2
        L47:
            if (r1 != 0) goto L63
            r7.a(r8)
            goto L63
        L4d:
            r7.b = r1
            r7.deselect()
            android.view.ViewParent r8 = r7.getParent()
            r8.requestDisallowInterceptTouchEvent(r1)
            goto L63
        L5a:
            r7.b = r1
            int r8 = r7.K
            if (r8 != r2) goto L63
            r7.d()
        L63:
            return r2
        L64:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: view.knob.Knob.onTouchEvent(android.view.MotionEvent):boolean");
    }

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

    public void setActive(boolean z) {
        if (this.L.isActivated() != z) {
            this.L.setActivated(z);
            updateModeIndicatorImage();
        }
        if (this.O != z) {
            this.O = z;
            invalidate();
        }
    }

    public void setBottomViews(boolean z) {
        if (!z) {
            this.M.setVisibility(0);
        } else {
            setModeImageToIdle();
            this.M.setVisibility(8);
        }
    }

    public void setCurrentState(int i) {
        this.K = i;
    }

    public void setDrawActiveIndicator(boolean z) {
        this.R = z;
    }

    public void setKnobValueListener(@Nullable KnobValueListener knobValueListener) {
        if (knobValueListener != null) {
            this.S = knobValueListener;
        }
    }

    public void setMaxValue(int i) {
        Log.v(b0, "setMaxValue " + i);
        if (this.U > i) {
            this.U = i;
        }
        this.T = i;
    }

    public void setMaxValueLimit(int i) {
        Log.v(b0, "setMaxValueLimit " + i);
        if (i > this.T) {
            this.T = i;
        }
        this.U = i;
        setMaxProgressLimit(getProgressFromValue(i));
    }

    public void setModeImageToIdle() {
        ImageView imageView = this.L;
        if (imageView != null) {
            imageView.setScaleX(1.0f);
            this.L.setScaleY(1.0f);
            this.L.setTranslationY(0.0f);
        }
    }

    public void setModeImageToSelected() {
        ImageView imageView = this.L;
        if (imageView != null) {
            imageView.setScaleX(0.8f);
            this.L.setScaleY(0.8f);
            this.L.setTranslationY(-27.0f);
        }
    }

    public void setProgress(int i) {
        updateProgress(i, false);
    }

    public void setScaled() {
        b();
    }

    public void setSkipCookingStepListener(SkipPreheatStepListener skipPreheatStepListener) {
        this.V = skipPreheatStepListener;
    }

    public void setStateListener(@Nullable StateListener stateListener) {
        if (stateListener != null) {
            this.W = stateListener;
        }
    }

    public void setText(String str) {
        QuicksandTextView quicksandTextView = this.a0;
        if (quicksandTextView != null) {
            quicksandTextView.setText(str);
        }
    }

    public void setToIdleState() {
        if (2 == this.K) {
            d();
        }
        this.K = 1;
        KnobUtils.setActiveIndicator(this);
        setModeImageToIdle();
        valueSetVisibility(false);
    }

    public void setValue(int i) {
        updateProgress(getProgressFromValue(i), false);
    }

    @Override // view.knob.BaseKnob
    public void setValueFromUser() {
        Log.v(b0, "setValueFromUser");
        int valueFromProgress = getValueFromProgress();
        KnobValueListener knobValueListener = this.S;
        if (knobValueListener != null) {
            knobValueListener.onValueSet(valueFromProgress);
        }
    }

    public void setValueSetTextSizeNormal() {
        QuicksandTextView quicksandTextView = this.M;
        if (quicksandTextView != null) {
            quicksandTextView.setTextSize(0, App.getInstance().getResources().getDimension(R.dimen.text_size_knob_small));
        }
    }

    public void setValueSetTextSizeSmall() {
        QuicksandTextView quicksandTextView = this.M;
        if (quicksandTextView != null) {
            quicksandTextView.setTextSize(0, App.getInstance().getResources().getDimension(R.dimen.text_size_knob_smaller));
        }
    }

    public void updateModeIndicatorImage() {
        if (this.L.isActivated()) {
            this.L.setImageDrawable(this.H);
        } else {
            this.L.setImageDrawable(this.G);
        }
    }

    public void valueSetText(String str) {
        QuicksandTextView quicksandTextView = this.M;
        if (quicksandTextView != null) {
            quicksandTextView.setText(str);
        }
    }

    public void valueSetVisibility(boolean z) {
        ViewGroup viewGroup = this.Q;
        if (viewGroup != null) {
            if (z) {
                viewGroup.setVisibility(0);
            } else {
                viewGroup.setVisibility(8);
            }
        }
    }
}
