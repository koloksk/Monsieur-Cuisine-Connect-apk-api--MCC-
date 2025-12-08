package defpackage;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.WithHint;
import android.text.TextUtils;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.View;
import android.view.ViewParent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import com.google.zxing.qrcode.encoder.ByteMatrix;
import io.reactivex.CompletableObserver;
import io.reactivex.CompletableSource;
import io.reactivex.MaybeSource;
import io.reactivex.Observer;
import io.reactivex.SingleSource;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.functions.Function;
import io.reactivex.internal.disposables.EmptyDisposable;
import io.reactivex.internal.functions.ObjectHelper;
import io.reactivex.internal.operators.maybe.MaybeToObservable;
import io.reactivex.internal.operators.single.SingleToObservable;
import java.lang.reflect.Field;
import java.util.Locale;
import java.util.concurrent.Callable;
import org.greenrobot.greendao.annotation.apihint.Internal;
import rx.Observable;

@RequiresApi(21)
/* loaded from: classes.dex */
public class q5 {
    public static Field a;
    public static boolean b;
    public static Class c;
    public static boolean d;
    public static Field e;
    public static boolean f;
    public static Field g;
    public static boolean h;

    public static InputConnection a(InputConnection inputConnection, EditorInfo editorInfo, View view2) {
        if (inputConnection != null && editorInfo.hintText == null) {
            ViewParent parent = view2.getParent();
            while (true) {
                if (!(parent instanceof View)) {
                    break;
                }
                if (parent instanceof WithHint) {
                    editorInfo.hintText = ((WithHint) parent).getHint();
                    break;
                }
                parent = parent.getParent();
            }
        }
        return inputConnection;
    }

    public static int b(RecyclerView.State state, OrientationHelper orientationHelper, View view2, View view3, RecyclerView.LayoutManager layoutManager, boolean z) {
        if (layoutManager.getChildCount() == 0 || state.getItemCount() == 0 || view2 == null || view3 == null) {
            return 0;
        }
        if (!z) {
            return state.getItemCount();
        }
        return (int) (((orientationHelper.getDecoratedEnd(view3) - orientationHelper.getDecoratedStart(view2)) / (Math.abs(layoutManager.getPosition(view2) - layoutManager.getPosition(view3)) + 1)) * state.getItemCount());
    }

    public static boolean c(Context context, Uri uri) throws Exception {
        Cursor cursorQuery = null;
        try {
            cursorQuery = context.getContentResolver().query(uri, new String[]{"document_id"}, null, null, null);
            return cursorQuery.getCount() > 0;
        } catch (Exception e2) {
            Log.w("DocumentFile", "Failed query: " + e2);
            return false;
        } finally {
            a((AutoCloseable) cursorQuery);
        }
    }

    public static String d(Context context, Uri uri) {
        return a(context, uri, "mime_type", (String) null);
    }

    public static boolean e(Context context, Uri uri) {
        String strD = d(context, uri);
        return ("vnd.android.document/directory".equals(strD) || TextUtils.isEmpty(strD)) ? false : true;
    }

    public static boolean f(Context context, Uri uri) {
        return DocumentsContract.isDocumentUri(context, uri) && (a(context, uri, "flags", 0L) & 512) != 0;
    }

    @Internal
    public static <T> Observable<T> a(Callable<T> callable) {
        return Observable.defer(new uo(callable));
    }

    public static int c(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        if (i != 17) {
            if (i != 33) {
                if (i != 66) {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                }
            }
            return Math.abs(((rect.width() / 2) + rect.left) - ((rect2.width() / 2) + rect2.left));
        }
        return Math.abs(((rect.height() / 2) + rect.top) - ((rect2.height() / 2) + rect2.top));
    }

    public static int a(RecyclerView.State state, OrientationHelper orientationHelper, View view2, View view3, RecyclerView.LayoutManager layoutManager, boolean z, boolean z2) {
        int iMax;
        if (layoutManager.getChildCount() == 0 || state.getItemCount() == 0 || view2 == null || view3 == null) {
            return 0;
        }
        int iMin = Math.min(layoutManager.getPosition(view2), layoutManager.getPosition(view3));
        int iMax2 = Math.max(layoutManager.getPosition(view2), layoutManager.getPosition(view3));
        if (z2) {
            iMax = Math.max(0, (state.getItemCount() - iMax2) - 1);
        } else {
            iMax = Math.max(0, iMin);
        }
        if (!z) {
            return iMax;
        }
        return Math.round((iMax * (Math.abs(orientationHelper.getDecoratedEnd(view3) - orientationHelper.getDecoratedStart(view2)) / (Math.abs(layoutManager.getPosition(view2) - layoutManager.getPosition(view3)) + 1))) + (orientationHelper.getStartAfterPadding() - orientationHelper.getDecoratedStart(view2)));
    }

    public static boolean b(Context context, Uri uri) {
        if (context.checkCallingOrSelfUriPermission(uri, 2) != 0) {
            return false;
        }
        String strD = d(context, uri);
        int iA = (int) a(context, uri, "flags", 0);
        if (TextUtils.isEmpty(strD)) {
            return false;
        }
        if ((iA & 4) != 0) {
            return true;
        }
        if (!"vnd.android.document/directory".equals(strD) || (iA & 8) == 0) {
            return (TextUtils.isEmpty(strD) || (iA & 2) == 0) ? false : true;
        }
        return true;
    }

    public static <T, R> boolean b(Object obj, Function<? super T, ? extends SingleSource<? extends R>> function, Observer<? super R> observer) {
        if (!(obj instanceof Callable)) {
            return false;
        }
        try {
            a aVar = (Object) ((Callable) obj).call();
            SingleSource singleSource = aVar != null ? (SingleSource) ObjectHelper.requireNonNull(function.apply(aVar), "The mapper returned a null SingleSource") : null;
            if (singleSource == null) {
                EmptyDisposable.complete(observer);
            } else {
                singleSource.subscribe(SingleToObservable.create(observer));
            }
            return true;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
            return true;
        }
    }

    public static int b(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        int i2;
        int i3;
        if (i == 17) {
            i2 = rect.left;
            i3 = rect2.right;
        } else if (i == 33) {
            i2 = rect.top;
            i3 = rect2.bottom;
        } else if (i == 66) {
            i2 = rect2.left;
            i3 = rect.right;
        } else if (i == 130) {
            i2 = rect2.top;
            i3 = rect.bottom;
        } else {
            throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
        }
        return Math.max(0, i2 - i3);
    }

    public static Locale a(String str) {
        if (str.contains("-")) {
            String[] strArrSplit = str.split("-");
            if (strArrSplit.length > 2) {
                return new Locale(strArrSplit[0], strArrSplit[1], strArrSplit[2]);
            }
            if (strArrSplit.length > 1) {
                return new Locale(strArrSplit[0], strArrSplit[1]);
            }
            if (strArrSplit.length == 1) {
                return new Locale(strArrSplit[0]);
            }
        } else if (str.contains("_")) {
            String[] strArrSplit2 = str.split("_");
            if (strArrSplit2.length > 2) {
                return new Locale(strArrSplit2[0], strArrSplit2[1], strArrSplit2[2]);
            }
            if (strArrSplit2.length > 1) {
                return new Locale(strArrSplit2[0], strArrSplit2[1]);
            }
            if (strArrSplit2.length == 1) {
                return new Locale(strArrSplit2[0]);
            }
        } else {
            return new Locale(str);
        }
        throw new IllegalArgumentException(g9.a("Can not parse language tag: [", str, "]"));
    }

    public static <T> boolean a(Object obj, Function<? super T, ? extends CompletableSource> function, CompletableObserver completableObserver) {
        if (!(obj instanceof Callable)) {
            return false;
        }
        try {
            a aVar = (Object) ((Callable) obj).call();
            CompletableSource completableSource = aVar != null ? (CompletableSource) ObjectHelper.requireNonNull(function.apply(aVar), "The mapper returned a null CompletableSource") : null;
            if (completableSource == null) {
                EmptyDisposable.complete(completableObserver);
            } else {
                completableSource.subscribe(completableObserver);
            }
            return true;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, completableObserver);
            return true;
        }
    }

    public static int a(RecyclerView.State state, OrientationHelper orientationHelper, View view2, View view3, RecyclerView.LayoutManager layoutManager, boolean z) {
        if (layoutManager.getChildCount() == 0 || state.getItemCount() == 0 || view2 == null || view3 == null) {
            return 0;
        }
        if (!z) {
            return Math.abs(layoutManager.getPosition(view2) - layoutManager.getPosition(view3)) + 1;
        }
        return Math.min(orientationHelper.getTotalSpace(), orientationHelper.getDecoratedEnd(view3) - orientationHelper.getDecoratedStart(view2));
    }

    public static <T, R> boolean a(Object obj, Function<? super T, ? extends MaybeSource<? extends R>> function, Observer<? super R> observer) {
        if (!(obj instanceof Callable)) {
            return false;
        }
        try {
            a aVar = (Object) ((Callable) obj).call();
            MaybeSource maybeSource = aVar != null ? (MaybeSource) ObjectHelper.requireNonNull(function.apply(aVar), "The mapper returned a null MaybeSource") : null;
            if (maybeSource == null) {
                EmptyDisposable.complete(observer);
            } else {
                maybeSource.subscribe(MaybeToObservable.create(observer));
            }
            return true;
        } catch (Throwable th) {
            Exceptions.throwIfFatal(th);
            EmptyDisposable.error(th, observer);
            return true;
        }
    }

    public static boolean a(Context context, Uri uri) {
        return context.checkCallingOrSelfUriPermission(uri, 1) == 0 && !TextUtils.isEmpty(d(context, uri));
    }

    public static boolean a(byte[] bArr, int i, int i2) {
        int iMin = Math.min(i2, bArr.length);
        for (int iMax = Math.max(i, 0); iMax < iMin; iMax++) {
            if (bArr[iMax] == 1) {
                return false;
            }
        }
        return true;
    }

    public static boolean a(byte[][] bArr, int i, int i2, int i3) {
        int iMin = Math.min(i3, bArr.length);
        for (int iMax = Math.max(i2, 0); iMax < iMin; iMax++) {
            if (bArr[iMax][i] == 1) {
                return false;
            }
        }
        return true;
    }

    public static String a(Context context, Uri uri, String str, String str2) throws Exception {
        Cursor cursorQuery = null;
        try {
            cursorQuery = context.getContentResolver().query(uri, new String[]{str}, null, null, null);
            return (!cursorQuery.moveToFirst() || cursorQuery.isNull(0)) ? str2 : cursorQuery.getString(0);
        } catch (Exception e2) {
            Log.w("DocumentFile", "Failed query: " + e2);
            return str2;
        } finally {
            a((AutoCloseable) cursorQuery);
        }
    }

    @RequiresApi(16)
    public static boolean a(@NonNull Object obj) throws NoSuchFieldException {
        if (!d) {
            try {
                c = Class.forName("android.content.res.ThemedResourceCache");
            } catch (ClassNotFoundException e2) {
                Log.e("ResourcesFlusher", "Could not find ThemedResourceCache class", e2);
            }
            d = true;
        }
        Class cls = c;
        if (cls == null) {
            return false;
        }
        if (!f) {
            try {
                Field declaredField = cls.getDeclaredField("mUnthemedEntries");
                e = declaredField;
                declaredField.setAccessible(true);
            } catch (NoSuchFieldException e3) {
                Log.e("ResourcesFlusher", "Could not retrieve ThemedResourceCache#mUnthemedEntries field", e3);
            }
            f = true;
        }
        Field field = e;
        if (field == null) {
            return false;
        }
        LongSparseArray longSparseArray = null;
        try {
            longSparseArray = (LongSparseArray) field.get(obj);
        } catch (IllegalAccessException e4) {
            Log.e("ResourcesFlusher", "Could not retrieve value from ThemedResourceCache#mUnthemedEntries", e4);
        }
        if (longSparseArray == null) {
            return false;
        }
        longSparseArray.clear();
        return true;
    }

    public static long a(Context context, Uri uri, String str, long j) throws Exception {
        Cursor cursorQuery = null;
        try {
            cursorQuery = context.getContentResolver().query(uri, new String[]{str}, null, null, null);
            return (!cursorQuery.moveToFirst() || cursorQuery.isNull(0)) ? j : cursorQuery.getLong(0);
        } catch (Exception e2) {
            Log.w("DocumentFile", "Failed query: " + e2);
            return j;
        } finally {
            a((AutoCloseable) cursorQuery);
        }
    }

    public static int a(ByteMatrix byteMatrix, boolean z) {
        int height = z ? byteMatrix.getHeight() : byteMatrix.getWidth();
        int width = z ? byteMatrix.getWidth() : byteMatrix.getHeight();
        byte[][] array = byteMatrix.getArray();
        int i = 0;
        for (int i2 = 0; i2 < height; i2++) {
            byte b2 = -1;
            int i3 = 0;
            for (int i4 = 0; i4 < width; i4++) {
                byte b3 = z ? array[i2][i4] : array[i4][i2];
                if (b3 == b2) {
                    i3++;
                } else {
                    if (i3 >= 5) {
                        i += (i3 - 5) + 3;
                    }
                    i3 = 1;
                    b2 = b3;
                }
            }
            if (i3 >= 5) {
                i = (i3 - 5) + 3 + i;
            }
        }
        return i;
    }

    public static void a(AutoCloseable autoCloseable) throws Exception {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (RuntimeException e2) {
                throw e2;
            } catch (Exception unused) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002a  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean a(int r9, @android.support.annotation.NonNull android.graphics.Rect r10, @android.support.annotation.NonNull android.graphics.Rect r11, @android.support.annotation.NonNull android.graphics.Rect r12) {
        /*
            boolean r0 = a(r9, r10, r11)
            boolean r1 = a(r9, r10, r12)
            r2 = 0
            if (r1 != 0) goto L7e
            if (r0 != 0) goto Lf
            goto L7e
        Lf:
            java.lang.String r0 = "direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}."
            r1 = 130(0x82, float:1.82E-43)
            r3 = 33
            r4 = 66
            r5 = 17
            r6 = 1
            if (r9 == r5) goto L40
            if (r9 == r3) goto L39
            if (r9 == r4) goto L32
            if (r9 != r1) goto L2c
            int r7 = r10.bottom
            int r8 = r12.top
            if (r7 > r8) goto L2a
        L28:
            r7 = r6
            goto L47
        L2a:
            r7 = r2
            goto L47
        L2c:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            r9.<init>(r0)
            throw r9
        L32:
            int r7 = r10.right
            int r8 = r12.left
            if (r7 > r8) goto L2a
            goto L28
        L39:
            int r7 = r10.top
            int r8 = r12.bottom
            if (r7 < r8) goto L2a
            goto L28
        L40:
            int r7 = r10.left
            int r8 = r12.right
            if (r7 < r8) goto L2a
            goto L28
        L47:
            if (r7 != 0) goto L4a
            return r6
        L4a:
            if (r9 == r5) goto L7d
            if (r9 != r4) goto L4f
            goto L7d
        L4f:
            int r11 = b(r9, r10, r11)
            if (r9 == r5) goto L70
            if (r9 == r3) goto L6b
            if (r9 == r4) goto L66
            if (r9 != r1) goto L60
            int r9 = r12.bottom
            int r10 = r10.bottom
            goto L74
        L60:
            java.lang.IllegalArgumentException r9 = new java.lang.IllegalArgumentException
            r9.<init>(r0)
            throw r9
        L66:
            int r9 = r12.right
            int r10 = r10.right
            goto L74
        L6b:
            int r9 = r10.top
            int r10 = r12.top
            goto L74
        L70:
            int r9 = r10.left
            int r10 = r12.left
        L74:
            int r9 = r9 - r10
            int r9 = java.lang.Math.max(r6, r9)
            if (r11 >= r9) goto L7c
            r2 = r6
        L7c:
            return r2
        L7d:
            return r6
        L7e:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.q5.a(int, android.graphics.Rect, android.graphics.Rect, android.graphics.Rect):boolean");
    }

    public static boolean a(@NonNull Rect rect, @NonNull Rect rect2, int i) {
        if (i == 17) {
            int i2 = rect.right;
            int i3 = rect2.right;
            return (i2 > i3 || rect.left >= i3) && rect.left > rect2.left;
        }
        if (i == 33) {
            int i4 = rect.bottom;
            int i5 = rect2.bottom;
            return (i4 > i5 || rect.top >= i5) && rect.top > rect2.top;
        }
        if (i == 66) {
            int i6 = rect.left;
            int i7 = rect2.left;
            return (i6 < i7 || rect.right <= i7) && rect.right < rect2.right;
        }
        if (i == 130) {
            int i8 = rect.top;
            int i9 = rect2.top;
            return (i8 < i9 || rect.bottom <= i9) && rect.bottom < rect2.bottom;
        }
        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
    }

    public static boolean a(int i, @NonNull Rect rect, @NonNull Rect rect2) {
        if (i != 17) {
            if (i != 33) {
                if (i != 66) {
                    if (i != 130) {
                        throw new IllegalArgumentException("direction must be one of {FOCUS_UP, FOCUS_DOWN, FOCUS_LEFT, FOCUS_RIGHT}.");
                    }
                }
            }
            return rect2.right >= rect.left && rect2.left <= rect.right;
        }
        return rect2.bottom >= rect.top && rect2.top <= rect.bottom;
    }
}
