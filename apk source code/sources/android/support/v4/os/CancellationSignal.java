package android.support.v4.os;

/* loaded from: classes.dex */
public final class CancellationSignal {
    public boolean a;
    public OnCancelListener b;
    public Object c;
    public boolean d;

    public interface OnCancelListener {
        void onCancel();
    }

    public void cancel() {
        synchronized (this) {
            if (this.a) {
                return;
            }
            this.a = true;
            this.d = true;
            OnCancelListener onCancelListener = this.b;
            Object obj = this.c;
            if (onCancelListener != null) {
                try {
                    onCancelListener.onCancel();
                } catch (Throwable th) {
                    synchronized (this) {
                        this.d = false;
                        notifyAll();
                        throw th;
                    }
                }
            }
            if (obj != null) {
                ((android.os.CancellationSignal) obj).cancel();
            }
            synchronized (this) {
                this.d = false;
                notifyAll();
            }
        }
    }

    public Object getCancellationSignalObject() {
        Object obj;
        synchronized (this) {
            if (this.c == null) {
                android.os.CancellationSignal cancellationSignal = new android.os.CancellationSignal();
                this.c = cancellationSignal;
                if (this.a) {
                    cancellationSignal.cancel();
                }
            }
            obj = this.c;
        }
        return obj;
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this) {
            z = this.a;
        }
        return z;
    }

    public void setOnCancelListener(OnCancelListener onCancelListener) {
        synchronized (this) {
            while (this.d) {
                try {
                    wait();
                } catch (InterruptedException unused) {
                }
            }
            if (this.b == onCancelListener) {
                return;
            }
            this.b = onCancelListener;
            if (this.a && onCancelListener != null) {
                onCancelListener.onCancel();
            }
        }
    }

    public void throwIfCanceled() {
        if (isCanceled()) {
            throw new OperationCanceledException();
        }
    }
}
