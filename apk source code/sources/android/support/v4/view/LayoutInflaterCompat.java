package android.support.v4.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

/* loaded from: classes.dex */
public final class LayoutInflaterCompat {
    public static final c a = new b();

    public static class a implements LayoutInflater.Factory2 {
        public final LayoutInflaterFactory a;

        public a(LayoutInflaterFactory layoutInflaterFactory) {
            this.a = layoutInflaterFactory;
        }

        @Override // android.view.LayoutInflater.Factory
        public View onCreateView(String str, Context context, AttributeSet attributeSet) {
            return this.a.onCreateView(null, str, context, attributeSet);
        }

        public String toString() {
            return a.class.getName() + "{" + this.a + "}";
        }

        @Override // android.view.LayoutInflater.Factory2
        public View onCreateView(View view2, String str, Context context, AttributeSet attributeSet) {
            return this.a.onCreateView(view2, str, context, attributeSet);
        }
    }

    @RequiresApi(21)
    public static class b extends c {
    }

    public static class c {
    }

    @Deprecated
    public static LayoutInflaterFactory getFactory(LayoutInflater layoutInflater) {
        LayoutInflater.Factory factory = layoutInflater.getFactory();
        if (factory instanceof a) {
            return ((a) factory).a;
        }
        return null;
    }

    @Deprecated
    public static void setFactory(@NonNull LayoutInflater layoutInflater, @NonNull LayoutInflaterFactory layoutInflaterFactory) {
        layoutInflater.setFactory2(layoutInflaterFactory != null ? new a(layoutInflaterFactory) : null);
    }

    public static void setFactory2(@NonNull LayoutInflater layoutInflater, @NonNull LayoutInflater.Factory2 factory2) {
        layoutInflater.setFactory2(factory2);
    }
}
