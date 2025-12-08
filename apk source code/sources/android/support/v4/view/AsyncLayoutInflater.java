package android.support.v4.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.util.Pools;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.concurrent.ArrayBlockingQueue;

/* loaded from: classes.dex */
public final class AsyncLayoutInflater {
    public LayoutInflater a;
    public Handler.Callback d = new a();
    public Handler b = new Handler(this.d);
    public d c = d.c;

    public interface OnInflateFinishedListener {
        void onInflateFinished(@NonNull View view2, @LayoutRes int i, @Nullable ViewGroup viewGroup);
    }

    public class a implements Handler.Callback {
        public a() {
        }

        @Override // android.os.Handler.Callback
        public boolean handleMessage(Message message) {
            c cVar = (c) message.obj;
            if (cVar.d == null) {
                cVar.d = AsyncLayoutInflater.this.a.inflate(cVar.c, cVar.b, false);
            }
            cVar.e.onInflateFinished(cVar.d, cVar.c, cVar.b);
            d dVar = AsyncLayoutInflater.this.c;
            if (dVar == null) {
                throw null;
            }
            cVar.e = null;
            cVar.a = null;
            cVar.b = null;
            cVar.c = 0;
            cVar.d = null;
            dVar.b.release(cVar);
            return true;
        }
    }

    public static class b extends LayoutInflater {
        public static final String[] a = {"android.widget.", "android.webkit.", "android.app."};

        public b(Context context) {
            super(context);
        }

        @Override // android.view.LayoutInflater
        public LayoutInflater cloneInContext(Context context) {
            return new b(context);
        }

        @Override // android.view.LayoutInflater
        public View onCreateView(String str, AttributeSet attributeSet) throws InflateException, ClassNotFoundException {
            View viewCreateView;
            for (String str2 : a) {
                try {
                    viewCreateView = createView(str, str2, attributeSet);
                } catch (ClassNotFoundException unused) {
                }
                if (viewCreateView != null) {
                    return viewCreateView;
                }
            }
            return super.onCreateView(str, attributeSet);
        }
    }

    public static class c {
        public AsyncLayoutInflater a;
        public ViewGroup b;
        public int c;
        public View d;
        public OnInflateFinishedListener e;
    }

    public static class d extends Thread {
        public static final d c;
        public ArrayBlockingQueue<c> a = new ArrayBlockingQueue<>(10);
        public Pools.SynchronizedPool<c> b = new Pools.SynchronizedPool<>(10);

        static {
            d dVar = new d();
            c = dVar;
            dVar.start();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws InterruptedException {
            while (true) {
                try {
                    c cVarTake = this.a.take();
                    try {
                        cVarTake.d = cVarTake.a.a.inflate(cVarTake.c, cVarTake.b, false);
                    } catch (RuntimeException e) {
                        Log.w("AsyncLayoutInflater", "Failed to inflate resource in the background! Retrying on the UI thread", e);
                    }
                    Message.obtain(cVarTake.a.b, 0, cVarTake).sendToTarget();
                } catch (InterruptedException e2) {
                    Log.w("AsyncLayoutInflater", e2);
                }
            }
        }
    }

    public AsyncLayoutInflater(@NonNull Context context) {
        this.a = new b(context);
    }

    @UiThread
    public void inflate(@LayoutRes int i, @Nullable ViewGroup viewGroup, @NonNull OnInflateFinishedListener onInflateFinishedListener) throws InterruptedException {
        if (onInflateFinishedListener == null) {
            throw new NullPointerException("callback argument may not be null!");
        }
        c cVarAcquire = this.c.b.acquire();
        if (cVarAcquire == null) {
            cVarAcquire = new c();
        }
        cVarAcquire.a = this;
        cVarAcquire.c = i;
        cVarAcquire.b = viewGroup;
        cVarAcquire.e = onInflateFinishedListener;
        d dVar = this.c;
        if (dVar == null) {
            throw null;
        }
        try {
            dVar.a.put(cVarAcquire);
        } catch (InterruptedException e) {
            throw new RuntimeException("Failed to enqueue async inflate request", e);
        }
    }
}
