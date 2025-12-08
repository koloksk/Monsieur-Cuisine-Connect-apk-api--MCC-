package android.databinding;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewStub;

/* loaded from: classes.dex */
public class ViewStubProxy {
    public ViewStub a;
    public ViewDataBinding b;
    public View c;
    public ViewStub.OnInflateListener d;
    public ViewDataBinding e;
    public ViewStub.OnInflateListener f;

    public class a implements ViewStub.OnInflateListener {
        public a() {
        }

        @Override // android.view.ViewStub.OnInflateListener
        public void onInflate(ViewStub viewStub, View view2) {
            ViewStubProxy viewStubProxy = ViewStubProxy.this;
            viewStubProxy.c = view2;
            viewStubProxy.b = DataBindingUtil.a(viewStubProxy.e.mBindingComponent, view2, viewStub.getLayoutResource());
            ViewStubProxy viewStubProxy2 = ViewStubProxy.this;
            viewStubProxy2.a = null;
            ViewStub.OnInflateListener onInflateListener = viewStubProxy2.d;
            if (onInflateListener != null) {
                onInflateListener.onInflate(viewStub, view2);
                ViewStubProxy.this.d = null;
            }
            ViewStubProxy.this.e.invalidateAll();
            ViewStubProxy.this.e.b();
        }
    }

    public ViewStubProxy(@NonNull ViewStub viewStub) {
        a aVar = new a();
        this.f = aVar;
        this.a = viewStub;
        viewStub.setOnInflateListener(aVar);
    }

    @Nullable
    public ViewDataBinding getBinding() {
        return this.b;
    }

    public View getRoot() {
        return this.c;
    }

    @Nullable
    public ViewStub getViewStub() {
        return this.a;
    }

    public boolean isInflated() {
        return this.c != null;
    }

    public void setContainingBinding(@NonNull ViewDataBinding viewDataBinding) {
        this.e = viewDataBinding;
    }

    public void setOnInflateListener(@Nullable ViewStub.OnInflateListener onInflateListener) {
        if (this.a != null) {
            this.d = onInflateListener;
        }
    }
}
