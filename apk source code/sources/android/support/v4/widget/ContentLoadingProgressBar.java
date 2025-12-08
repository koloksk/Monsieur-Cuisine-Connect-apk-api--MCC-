package android.support.v4.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ProgressBar;

/* loaded from: classes.dex */
public class ContentLoadingProgressBar extends ProgressBar {
    public long a;
    public boolean b;
    public boolean c;
    public boolean d;
    public final Runnable e;
    public final Runnable f;

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ContentLoadingProgressBar contentLoadingProgressBar = ContentLoadingProgressBar.this;
            contentLoadingProgressBar.b = false;
            contentLoadingProgressBar.a = -1L;
            contentLoadingProgressBar.setVisibility(8);
        }
    }

    public class b implements Runnable {
        public b() {
        }

        @Override // java.lang.Runnable
        public void run() {
            ContentLoadingProgressBar contentLoadingProgressBar = ContentLoadingProgressBar.this;
            contentLoadingProgressBar.c = false;
            if (contentLoadingProgressBar.d) {
                return;
            }
            contentLoadingProgressBar.a = System.currentTimeMillis();
            ContentLoadingProgressBar.this.setVisibility(0);
        }
    }

    public ContentLoadingProgressBar(@NonNull Context context) {
        this(context, null);
    }

    public synchronized void hide() {
        this.d = true;
        removeCallbacks(this.f);
        this.c = false;
        long jCurrentTimeMillis = System.currentTimeMillis() - this.a;
        if (jCurrentTimeMillis >= 500 || this.a == -1) {
            setVisibility(8);
        } else if (!this.b) {
            postDelayed(this.e, 500 - jCurrentTimeMillis);
            this.b = true;
        }
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        removeCallbacks(this.e);
        removeCallbacks(this.f);
    }

    @Override // android.widget.ProgressBar, android.view.View
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(this.e);
        removeCallbacks(this.f);
    }

    public synchronized void show() {
        this.a = -1L;
        this.d = false;
        removeCallbacks(this.e);
        this.b = false;
        if (!this.c) {
            postDelayed(this.f, 500L);
            this.c = true;
        }
    }

    public ContentLoadingProgressBar(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet, 0);
        this.a = -1L;
        this.b = false;
        this.c = false;
        this.d = false;
        this.e = new a();
        this.f = new b();
    }
}
