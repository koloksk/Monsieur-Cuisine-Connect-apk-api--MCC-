package defpackage;

import android.R;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.VisibleForTesting;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import sound.SoundLength;

@VisibleForTesting
/* loaded from: classes.dex */
public class g8 extends RecyclerView.ItemDecoration implements RecyclerView.OnItemTouchListener {
    public static final int[] D = {R.attr.state_pressed};
    public static final int[] E = new int[0];
    public final int a;
    public final int b;
    public final StateListDrawable c;
    public final Drawable d;
    public final int e;
    public final int f;
    public final StateListDrawable g;
    public final Drawable h;
    public final int i;
    public final int j;

    @VisibleForTesting
    public int k;

    @VisibleForTesting
    public int l;

    @VisibleForTesting
    public float m;

    @VisibleForTesting
    public int n;

    @VisibleForTesting
    public int o;

    @VisibleForTesting
    public float p;
    public RecyclerView s;
    public int q = 0;
    public int r = 0;
    public boolean t = false;
    public boolean u = false;
    public int v = 0;
    public int w = 0;
    public final int[] x = new int[2];
    public final int[] y = new int[2];
    public final ValueAnimator z = ValueAnimator.ofFloat(0.0f, 1.0f);
    public int A = 0;
    public final Runnable B = new a();
    public final RecyclerView.OnScrollListener C = new b();

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            g8 g8Var = g8.this;
            int i = g8Var.A;
            if (i == 1) {
                g8Var.z.cancel();
            } else if (i != 2) {
                return;
            }
            g8Var.A = 3;
            ValueAnimator valueAnimator = g8Var.z;
            valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 0.0f);
            g8Var.z.setDuration(SoundLength.SHORT);
            g8Var.z.start();
        }
    }

    public class b extends RecyclerView.OnScrollListener {
        public b() {
        }

        @Override // android.support.v7.widget.RecyclerView.OnScrollListener
        public void onScrolled(RecyclerView recyclerView, int i, int i2) {
            g8 g8Var = g8.this;
            int iComputeHorizontalScrollOffset = recyclerView.computeHorizontalScrollOffset();
            int iComputeVerticalScrollOffset = recyclerView.computeVerticalScrollOffset();
            int iComputeVerticalScrollRange = g8Var.s.computeVerticalScrollRange();
            int i3 = g8Var.r;
            g8Var.t = iComputeVerticalScrollRange - i3 > 0 && i3 >= g8Var.a;
            int iComputeHorizontalScrollRange = g8Var.s.computeHorizontalScrollRange();
            int i4 = g8Var.q;
            boolean z = iComputeHorizontalScrollRange - i4 > 0 && i4 >= g8Var.a;
            g8Var.u = z;
            if (!g8Var.t && !z) {
                if (g8Var.v != 0) {
                    g8Var.a(0);
                    return;
                }
                return;
            }
            if (g8Var.t) {
                float f = i3;
                g8Var.l = (int) ((((f / 2.0f) + iComputeVerticalScrollOffset) * f) / iComputeVerticalScrollRange);
                g8Var.k = Math.min(i3, (i3 * i3) / iComputeVerticalScrollRange);
            }
            if (g8Var.u) {
                float f2 = iComputeHorizontalScrollOffset;
                float f3 = i4;
                g8Var.o = (int) ((((f3 / 2.0f) + f2) * f3) / iComputeHorizontalScrollRange);
                g8Var.n = Math.min(i4, (i4 * i4) / iComputeHorizontalScrollRange);
            }
            int i5 = g8Var.v;
            if (i5 == 0 || i5 == 1) {
                g8Var.a(1);
            }
        }
    }

    public class c extends AnimatorListenerAdapter {
        public boolean a = false;

        public /* synthetic */ c(a aVar) {
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationCancel(Animator animator) {
            this.a = true;
        }

        @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
        public void onAnimationEnd(Animator animator) {
            if (this.a) {
                this.a = false;
                return;
            }
            if (((Float) g8.this.z.getAnimatedValue()).floatValue() == 0.0f) {
                g8 g8Var = g8.this;
                g8Var.A = 0;
                g8Var.a(0);
            } else {
                g8 g8Var2 = g8.this;
                g8Var2.A = 2;
                g8Var2.s.invalidate();
            }
        }
    }

    public class d implements ValueAnimator.AnimatorUpdateListener {
        public /* synthetic */ d(a aVar) {
        }

        @Override // android.animation.ValueAnimator.AnimatorUpdateListener
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            int iFloatValue = (int) (((Float) valueAnimator.getAnimatedValue()).floatValue() * 255.0f);
            g8.this.c.setAlpha(iFloatValue);
            g8.this.d.setAlpha(iFloatValue);
            g8.this.s.invalidate();
        }
    }

    public g8(RecyclerView recyclerView, StateListDrawable stateListDrawable, Drawable drawable, StateListDrawable stateListDrawable2, Drawable drawable2, int i, int i2, int i3) {
        this.c = stateListDrawable;
        this.d = drawable;
        this.g = stateListDrawable2;
        this.h = drawable2;
        this.e = Math.max(i, stateListDrawable.getIntrinsicWidth());
        this.f = Math.max(i, drawable.getIntrinsicWidth());
        this.i = Math.max(i, stateListDrawable2.getIntrinsicWidth());
        this.j = Math.max(i, drawable2.getIntrinsicWidth());
        this.a = i2;
        this.b = i3;
        this.c.setAlpha(255);
        this.d.setAlpha(255);
        a aVar = null;
        this.z.addListener(new c(aVar));
        this.z.addUpdateListener(new d(aVar));
        RecyclerView recyclerView2 = this.s;
        if (recyclerView2 == recyclerView) {
            return;
        }
        if (recyclerView2 != null) {
            recyclerView2.removeItemDecoration(this);
            this.s.removeOnItemTouchListener(this);
            this.s.removeOnScrollListener(this.C);
            a();
        }
        this.s = recyclerView;
        if (recyclerView != null) {
            recyclerView.addItemDecoration(this);
            this.s.addOnItemTouchListener(this);
            this.s.addOnScrollListener(this.C);
        }
    }

    public final void a(int i) {
        if (i == 2 && this.v != 2) {
            this.c.setState(D);
            a();
        }
        if (i == 0) {
            this.s.invalidate();
        } else {
            b();
        }
        if (this.v == 2 && i != 2) {
            this.c.setState(E);
            a();
            this.s.postDelayed(this.B, 1200);
        } else if (i == 1) {
            a();
            this.s.postDelayed(this.B, 1500);
        }
        this.v = i;
    }

    @VisibleForTesting
    public boolean b(float f, float f2) {
        if (ViewCompat.getLayoutDirection(this.s) == 1) {
            if (f > this.e / 2) {
                return false;
            }
        } else if (f < this.q - this.e) {
            return false;
        }
        int i = this.l;
        int i2 = this.k / 2;
        return f2 >= ((float) (i - i2)) && f2 <= ((float) (i2 + i));
    }

    @Override // android.support.v7.widget.RecyclerView.ItemDecoration
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        if (this.q != this.s.getWidth() || this.r != this.s.getHeight()) {
            this.q = this.s.getWidth();
            this.r = this.s.getHeight();
            a(0);
            return;
        }
        if (this.A != 0) {
            if (this.t) {
                int i = this.q;
                int i2 = this.e;
                int i3 = i - i2;
                int i4 = this.l;
                int i5 = this.k;
                int i6 = i4 - (i5 / 2);
                this.c.setBounds(0, 0, i2, i5);
                this.d.setBounds(0, 0, this.f, this.r);
                if (ViewCompat.getLayoutDirection(this.s) == 1) {
                    this.d.draw(canvas);
                    canvas.translate(this.e, i6);
                    canvas.scale(-1.0f, 1.0f);
                    this.c.draw(canvas);
                    canvas.scale(1.0f, 1.0f);
                    canvas.translate(-this.e, -i6);
                } else {
                    canvas.translate(i3, 0.0f);
                    this.d.draw(canvas);
                    canvas.translate(0.0f, i6);
                    this.c.draw(canvas);
                    canvas.translate(-i3, -i6);
                }
            }
            if (this.u) {
                int i7 = this.r;
                int i8 = this.i;
                int i9 = this.o;
                int i10 = this.n;
                this.g.setBounds(0, 0, i10, i8);
                this.h.setBounds(0, 0, this.q, this.j);
                canvas.translate(0.0f, i7 - i8);
                this.h.draw(canvas);
                canvas.translate(i9 - (i10 / 2), 0.0f);
                this.g.draw(canvas);
                canvas.translate(-r2, -r7);
            }
        }
    }

    @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
    public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        int i = this.v;
        if (i == 1) {
            boolean zB = b(motionEvent.getX(), motionEvent.getY());
            boolean zA = a(motionEvent.getX(), motionEvent.getY());
            if (motionEvent.getAction() != 0) {
                return false;
            }
            if (!zB && !zA) {
                return false;
            }
            if (zA) {
                this.w = 1;
                this.p = (int) motionEvent.getX();
            } else if (zB) {
                this.w = 2;
                this.m = (int) motionEvent.getY();
            }
            a(2);
        } else if (i != 2) {
            return false;
        }
        return true;
    }

    @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
    public void onRequestDisallowInterceptTouchEvent(boolean z) {
    }

    @Override // android.support.v7.widget.RecyclerView.OnItemTouchListener
    public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
        if (this.v == 0) {
            return;
        }
        if (motionEvent.getAction() == 0) {
            boolean zB = b(motionEvent.getX(), motionEvent.getY());
            boolean zA = a(motionEvent.getX(), motionEvent.getY());
            if (zB || zA) {
                if (zA) {
                    this.w = 1;
                    this.p = (int) motionEvent.getX();
                } else if (zB) {
                    this.w = 2;
                    this.m = (int) motionEvent.getY();
                }
                a(2);
                return;
            }
            return;
        }
        if (motionEvent.getAction() == 1 && this.v == 2) {
            this.m = 0.0f;
            this.p = 0.0f;
            a(1);
            this.w = 0;
            return;
        }
        if (motionEvent.getAction() == 2 && this.v == 2) {
            b();
            if (this.w == 1) {
                float x = motionEvent.getX();
                int[] iArr = this.y;
                int i = this.b;
                iArr[0] = i;
                iArr[1] = this.q - i;
                float fMax = Math.max(iArr[0], Math.min(iArr[1], x));
                if (Math.abs(this.o - fMax) >= 2.0f) {
                    int iA = a(this.p, fMax, iArr, this.s.computeHorizontalScrollRange(), this.s.computeHorizontalScrollOffset(), this.q);
                    if (iA != 0) {
                        this.s.scrollBy(iA, 0);
                    }
                    this.p = fMax;
                }
            }
            if (this.w == 2) {
                float y = motionEvent.getY();
                int[] iArr2 = this.x;
                int i2 = this.b;
                iArr2[0] = i2;
                iArr2[1] = this.r - i2;
                float fMax2 = Math.max(iArr2[0], Math.min(iArr2[1], y));
                if (Math.abs(this.l - fMax2) < 2.0f) {
                    return;
                }
                int iA2 = a(this.m, fMax2, iArr2, this.s.computeVerticalScrollRange(), this.s.computeVerticalScrollOffset(), this.r);
                if (iA2 != 0) {
                    this.s.scrollBy(0, iA2);
                }
                this.m = fMax2;
            }
        }
    }

    public void b() {
        int i = this.A;
        if (i != 0) {
            if (i != 3) {
                return;
            } else {
                this.z.cancel();
            }
        }
        this.A = 1;
        ValueAnimator valueAnimator = this.z;
        valueAnimator.setFloatValues(((Float) valueAnimator.getAnimatedValue()).floatValue(), 1.0f);
        this.z.setDuration(500L);
        this.z.setStartDelay(0L);
        this.z.start();
    }

    public final void a() {
        this.s.removeCallbacks(this.B);
    }

    public final int a(float f, float f2, int[] iArr, int i, int i2, int i3) {
        int i4 = iArr[1] - iArr[0];
        if (i4 == 0) {
            return 0;
        }
        int i5 = i - i3;
        int i6 = (int) (((f2 - f) / i4) * i5);
        int i7 = i2 + i6;
        if (i7 >= i5 || i7 < 0) {
            return 0;
        }
        return i6;
    }

    @VisibleForTesting
    public boolean a(float f, float f2) {
        if (f2 >= this.r - this.i) {
            int i = this.o;
            int i2 = this.n;
            if (f >= i - (i2 / 2) && f <= (i2 / 2) + i) {
                return true;
            }
        }
        return false;
    }
}
