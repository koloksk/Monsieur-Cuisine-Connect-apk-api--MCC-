package android.support.v13.view.inputmethod;

import android.content.ClipDescription;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.view.inputmethod.InputContentInfo;

/* loaded from: classes.dex */
public final class InputContentInfoCompat {
    public final c a;

    public static final class b implements c {

        @NonNull
        public final Uri a;

        @NonNull
        public final ClipDescription b;

        @Nullable
        public final Uri c;

        public b(@NonNull Uri uri, @NonNull ClipDescription clipDescription, @Nullable Uri uri2) {
            this.a = uri;
            this.b = clipDescription;
            this.c = uri2;
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.c
        @NonNull
        public ClipDescription a() {
            return this.b;
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.c
        @Nullable
        public Object b() {
            return null;
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.c
        @NonNull
        public Uri c() {
            return this.a;
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.c
        public void d() {
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.c
        public void e() {
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.c
        @Nullable
        public Uri f() {
            return this.c;
        }
    }

    public interface c {
        @NonNull
        ClipDescription a();

        @Nullable
        Object b();

        @NonNull
        Uri c();

        void d();

        void e();

        @Nullable
        Uri f();
    }

    public InputContentInfoCompat(@NonNull Uri uri, @NonNull ClipDescription clipDescription, @Nullable Uri uri2) {
        if (Build.VERSION.SDK_INT >= 25) {
            this.a = new a(uri, clipDescription, uri2);
        } else {
            this.a = new b(uri, clipDescription, uri2);
        }
    }

    @Nullable
    public static InputContentInfoCompat wrap(@Nullable Object obj) {
        if (obj != null && Build.VERSION.SDK_INT >= 25) {
            return new InputContentInfoCompat(new a(obj));
        }
        return null;
    }

    @NonNull
    public Uri getContentUri() {
        return this.a.c();
    }

    @NonNull
    public ClipDescription getDescription() {
        return this.a.a();
    }

    @Nullable
    public Uri getLinkUri() {
        return this.a.f();
    }

    public void releasePermission() {
        this.a.e();
    }

    public void requestPermission() {
        this.a.d();
    }

    @Nullable
    public Object unwrap() {
        return this.a.b();
    }

    @RequiresApi(25)
    public static final class a implements c {

        @NonNull
        public final InputContentInfo a;

        public a(@NonNull Object obj) {
            this.a = (InputContentInfo) obj;
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.c
        @NonNull
        public ClipDescription a() {
            return this.a.getDescription();
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.c
        @Nullable
        public Object b() {
            return this.a;
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.c
        @NonNull
        public Uri c() {
            return this.a.getContentUri();
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.c
        public void d() {
            this.a.requestPermission();
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.c
        public void e() {
            this.a.releasePermission();
        }

        @Override // android.support.v13.view.inputmethod.InputContentInfoCompat.c
        @Nullable
        public Uri f() {
            return this.a.getLinkUri();
        }

        public a(@NonNull Uri uri, @NonNull ClipDescription clipDescription, @Nullable Uri uri2) {
            this.a = new InputContentInfo(uri, clipDescription, uri2);
        }
    }

    public InputContentInfoCompat(@NonNull c cVar) {
        this.a = cVar;
    }
}
