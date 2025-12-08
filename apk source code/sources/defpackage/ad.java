package defpackage;

import android.support.annotation.VisibleForTesting;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.RequestCreator;
import java.lang.ref.WeakReference;

/* loaded from: classes.dex */
public class ad implements ViewTreeObserver.OnPreDrawListener, View.OnAttachStateChangeListener {
    public final RequestCreator a;

    @VisibleForTesting
    public final WeakReference<ImageView> b;

    @VisibleForTesting
    public Callback c;

    public ad(RequestCreator requestCreator, ImageView imageView, Callback callback) {
        this.a = requestCreator;
        this.b = new WeakReference<>(imageView);
        this.c = callback;
        imageView.addOnAttachStateChangeListener(this);
        if (imageView.getWindowToken() != null) {
            onViewAttachedToWindow(imageView);
        }
    }

    public void a() {
        this.a.l = null;
        this.c = null;
        ImageView imageView = this.b.get();
        if (imageView == null) {
            return;
        }
        this.b.clear();
        imageView.removeOnAttachStateChangeListener(this);
        ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.removeOnPreDrawListener(this);
        }
    }

    @Override // android.view.ViewTreeObserver.OnPreDrawListener
    public boolean onPreDraw() {
        ImageView imageView = this.b.get();
        if (imageView == null) {
            return true;
        }
        ViewTreeObserver viewTreeObserver = imageView.getViewTreeObserver();
        if (!viewTreeObserver.isAlive()) {
            return true;
        }
        int width = imageView.getWidth();
        int height = imageView.getHeight();
        if (width > 0 && height > 0) {
            imageView.removeOnAttachStateChangeListener(this);
            viewTreeObserver.removeOnPreDrawListener(this);
            this.b.clear();
            RequestCreator requestCreator = this.a;
            requestCreator.d = false;
            requestCreator.resize(width, height).into(imageView, this.c);
        }
        return true;
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewAttachedToWindow(View view2) {
        view2.getViewTreeObserver().addOnPreDrawListener(this);
    }

    @Override // android.view.View.OnAttachStateChangeListener
    public void onViewDetachedFromWindow(View view2) {
        view2.getViewTreeObserver().removeOnPreDrawListener(this);
    }
}
