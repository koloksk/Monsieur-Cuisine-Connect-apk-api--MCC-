package android.support.v4.provider;

import android.content.ContentUris;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Handler;
import android.provider.BaseColumns;
import android.support.annotation.GuardedBy;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.res.FontResourcesParserCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.TypefaceCompat;
import android.support.v4.graphics.TypefaceCompatUtil;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.provider.SelfDestructiveThread;
import android.support.v4.util.LruCache;
import android.support.v4.util.Preconditions;
import android.support.v4.util.SimpleArrayMap;
import defpackage.g9;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.apache.commons.lang3.time.DateUtils;

/* loaded from: classes.dex */
public class FontsContractCompat {

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final String PARCEL_FONT_RESULTS = "font_results";
    public static final LruCache<String, Typeface> a = new LruCache<>(16);
    public static final SelfDestructiveThread b = new SelfDestructiveThread("fonts", 10, 10000);
    public static final Object c = new Object();

    @GuardedBy("sLock")
    public static final SimpleArrayMap<String, ArrayList<SelfDestructiveThread.ReplyCallback<f>>> d = new SimpleArrayMap<>();
    public static final Comparator<byte[]> e = new e();

    public static final class Columns implements BaseColumns {
        public static final String FILE_ID = "file_id";
        public static final String ITALIC = "font_italic";
        public static final String RESULT_CODE = "result_code";
        public static final int RESULT_CODE_FONT_NOT_FOUND = 1;
        public static final int RESULT_CODE_FONT_UNAVAILABLE = 2;
        public static final int RESULT_CODE_MALFORMED_QUERY = 3;
        public static final int RESULT_CODE_OK = 0;
        public static final String TTC_INDEX = "font_ttc_index";
        public static final String VARIATION_SETTINGS = "font_variation_settings";
        public static final String WEIGHT = "font_weight";
    }

    public static class FontFamilyResult {
        public static final int STATUS_OK = 0;
        public static final int STATUS_UNEXPECTED_DATA_PROVIDED = 2;
        public static final int STATUS_WRONG_CERTIFICATES = 1;
        public final int a;
        public final FontInfo[] b;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public FontFamilyResult(int i, @Nullable FontInfo[] fontInfoArr) {
            this.a = i;
            this.b = fontInfoArr;
        }

        public FontInfo[] getFonts() {
            return this.b;
        }

        public int getStatusCode() {
            return this.a;
        }
    }

    public static class FontInfo {
        public final Uri a;
        public final int b;
        public final int c;
        public final boolean d;
        public final int e;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public FontInfo(@NonNull Uri uri, @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED) int i, @IntRange(from = 1, to = DateUtils.MILLIS_PER_SECOND) int i2, boolean z, int i3) {
            this.a = (Uri) Preconditions.checkNotNull(uri);
            this.b = i;
            this.c = i2;
            this.d = z;
            this.e = i3;
        }

        public int getResultCode() {
            return this.e;
        }

        @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
        public int getTtcIndex() {
            return this.b;
        }

        @NonNull
        public Uri getUri() {
            return this.a;
        }

        @IntRange(from = 1, to = DateUtils.MILLIS_PER_SECOND)
        public int getWeight() {
            return this.c;
        }

        public boolean isItalic() {
            return this.d;
        }
    }

    public static class FontRequestCallback {
        public static final int FAIL_REASON_FONT_LOAD_ERROR = -3;
        public static final int FAIL_REASON_FONT_NOT_FOUND = 1;
        public static final int FAIL_REASON_FONT_UNAVAILABLE = 2;
        public static final int FAIL_REASON_MALFORMED_QUERY = 3;
        public static final int FAIL_REASON_PROVIDER_NOT_FOUND = -1;
        public static final int FAIL_REASON_SECURITY_VIOLATION = -4;
        public static final int FAIL_REASON_WRONG_CERTIFICATES = -2;

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static final int RESULT_OK = 0;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public @interface FontRequestFailReason {
        }

        public void onTypefaceRequestFailed(int i) {
        }

        public void onTypefaceRetrieved(Typeface typeface) {
        }
    }

    public static class a implements Callable<f> {
        public final /* synthetic */ Context a;
        public final /* synthetic */ FontRequest b;
        public final /* synthetic */ int c;
        public final /* synthetic */ String d;

        public a(Context context, FontRequest fontRequest, int i, String str) {
            this.a = context;
            this.b = fontRequest;
            this.c = i;
            this.d = str;
        }

        @Override // java.util.concurrent.Callable
        public f call() throws Exception {
            f fVarA = FontsContractCompat.a(this.a, this.b, this.c);
            Typeface typeface = fVarA.a;
            if (typeface != null) {
                FontsContractCompat.a.put(this.d, typeface);
            }
            return fVarA;
        }
    }

    public static class b implements SelfDestructiveThread.ReplyCallback<f> {
        public final /* synthetic */ ResourcesCompat.FontCallback a;
        public final /* synthetic */ Handler b;

        public b(ResourcesCompat.FontCallback fontCallback, Handler handler) {
            this.a = fontCallback;
            this.b = handler;
        }

        @Override // android.support.v4.provider.SelfDestructiveThread.ReplyCallback
        public void onReply(f fVar) {
            f fVar2 = fVar;
            if (fVar2 == null) {
                this.a.callbackFailAsync(1, this.b);
                return;
            }
            int i = fVar2.b;
            if (i == 0) {
                this.a.callbackSuccessAsync(fVar2.a, this.b);
            } else {
                this.a.callbackFailAsync(i, this.b);
            }
        }
    }

    public static class c implements SelfDestructiveThread.ReplyCallback<f> {
        public final /* synthetic */ String a;

        public c(String str) {
            this.a = str;
        }

        @Override // android.support.v4.provider.SelfDestructiveThread.ReplyCallback
        /* renamed from: a, reason: merged with bridge method [inline-methods] */
        public void onReply(f fVar) {
            synchronized (FontsContractCompat.c) {
                ArrayList<SelfDestructiveThread.ReplyCallback<f>> arrayList = FontsContractCompat.d.get(this.a);
                if (arrayList == null) {
                    return;
                }
                FontsContractCompat.d.remove(this.a);
                for (int i = 0; i < arrayList.size(); i++) {
                    arrayList.get(i).onReply(fVar);
                }
            }
        }
    }

    public static class d implements Runnable {
        public final /* synthetic */ Context a;
        public final /* synthetic */ FontRequest b;
        public final /* synthetic */ Handler c;
        public final /* synthetic */ FontRequestCallback d;

        public class a implements Runnable {
            public a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                d.this.d.onTypefaceRequestFailed(-1);
            }
        }

        public class b implements Runnable {
            public b() {
            }

            @Override // java.lang.Runnable
            public void run() {
                d.this.d.onTypefaceRequestFailed(-2);
            }
        }

        public class c implements Runnable {
            public c() {
            }

            @Override // java.lang.Runnable
            public void run() {
                d.this.d.onTypefaceRequestFailed(-3);
            }
        }

        /* renamed from: android.support.v4.provider.FontsContractCompat$d$d, reason: collision with other inner class name */
        public class RunnableC0005d implements Runnable {
            public RunnableC0005d() {
            }

            @Override // java.lang.Runnable
            public void run() {
                d.this.d.onTypefaceRequestFailed(-3);
            }
        }

        public class e implements Runnable {
            public e() {
            }

            @Override // java.lang.Runnable
            public void run() {
                d.this.d.onTypefaceRequestFailed(1);
            }
        }

        public class f implements Runnable {
            public f() {
            }

            @Override // java.lang.Runnable
            public void run() {
                d.this.d.onTypefaceRequestFailed(-3);
            }
        }

        public class g implements Runnable {
            public final /* synthetic */ int a;

            public g(int i) {
                this.a = i;
            }

            @Override // java.lang.Runnable
            public void run() {
                d.this.d.onTypefaceRequestFailed(this.a);
            }
        }

        public class h implements Runnable {
            public h() {
            }

            @Override // java.lang.Runnable
            public void run() {
                d.this.d.onTypefaceRequestFailed(-3);
            }
        }

        public class i implements Runnable {
            public final /* synthetic */ Typeface a;

            public i(Typeface typeface) {
                this.a = typeface;
            }

            @Override // java.lang.Runnable
            public void run() {
                d.this.d.onTypefaceRetrieved(this.a);
            }
        }

        public d(Context context, FontRequest fontRequest, Handler handler, FontRequestCallback fontRequestCallback) {
            this.a = context;
            this.b = fontRequest;
            this.c = handler;
            this.d = fontRequestCallback;
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                FontFamilyResult fontFamilyResultFetchFonts = FontsContractCompat.fetchFonts(this.a, null, this.b);
                if (fontFamilyResultFetchFonts.getStatusCode() != 0) {
                    int statusCode = fontFamilyResultFetchFonts.getStatusCode();
                    if (statusCode == 1) {
                        this.c.post(new b());
                        return;
                    } else if (statusCode != 2) {
                        this.c.post(new RunnableC0005d());
                        return;
                    } else {
                        this.c.post(new c());
                        return;
                    }
                }
                FontInfo[] fonts = fontFamilyResultFetchFonts.getFonts();
                if (fonts == null || fonts.length == 0) {
                    this.c.post(new e());
                    return;
                }
                for (FontInfo fontInfo : fonts) {
                    if (fontInfo.getResultCode() != 0) {
                        int resultCode = fontInfo.getResultCode();
                        if (resultCode < 0) {
                            this.c.post(new f());
                            return;
                        } else {
                            this.c.post(new g(resultCode));
                            return;
                        }
                    }
                }
                Typeface typefaceBuildTypeface = FontsContractCompat.buildTypeface(this.a, null, fonts);
                if (typefaceBuildTypeface == null) {
                    this.c.post(new h());
                } else {
                    this.c.post(new i(typefaceBuildTypeface));
                }
            } catch (PackageManager.NameNotFoundException unused) {
                this.c.post(new a());
            }
        }
    }

    public static class e implements Comparator<byte[]> {
        @Override // java.util.Comparator
        public int compare(byte[] bArr, byte[] bArr2) {
            int length;
            int length2;
            byte[] bArr3 = bArr;
            byte[] bArr4 = bArr2;
            if (bArr3.length == bArr4.length) {
                for (int i = 0; i < bArr3.length; i++) {
                    if (bArr3[i] != bArr4[i]) {
                        length = bArr3[i];
                        length2 = bArr4[i];
                    }
                }
                return 0;
            }
            length = bArr3.length;
            length2 = bArr4.length;
            return length - length2;
        }
    }

    public static final class f {
        public final Typeface a;
        public final int b;

        public f(@Nullable Typeface typeface, int i) {
            this.a = typeface;
            this.b = i;
        }
    }

    @NonNull
    public static f a(Context context, FontRequest fontRequest, int i) {
        try {
            FontFamilyResult fontFamilyResultFetchFonts = fetchFonts(context, null, fontRequest);
            if (fontFamilyResultFetchFonts.getStatusCode() != 0) {
                return new f(null, fontFamilyResultFetchFonts.getStatusCode() == 1 ? -2 : -3);
            }
            Typeface typefaceCreateFromFontInfo = TypefaceCompat.createFromFontInfo(context, null, fontFamilyResultFetchFonts.getFonts(), i);
            return new f(typefaceCreateFromFontInfo, typefaceCreateFromFontInfo != null ? 0 : -3);
        } catch (PackageManager.NameNotFoundException unused) {
            return new f(null, -1);
        }
    }

    @Nullable
    public static Typeface buildTypeface(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontInfo[] fontInfoArr) {
        return TypefaceCompat.createFromFontInfo(context, cancellationSignal, fontInfoArr, 0);
    }

    @NonNull
    public static FontFamilyResult fetchFonts(@NonNull Context context, @Nullable CancellationSignal cancellationSignal, @NonNull FontRequest fontRequest) throws PackageManager.NameNotFoundException {
        ProviderInfo provider = getProvider(context.getPackageManager(), fontRequest, context.getResources());
        Cursor cursorQuery = null;
        if (provider == null) {
            return new FontFamilyResult(1, null);
        }
        String str = provider.authority;
        ArrayList arrayList = new ArrayList();
        Uri uriBuild = new Uri.Builder().scheme("content").authority(str).build();
        Uri uriBuild2 = new Uri.Builder().scheme("content").authority(str).appendPath("file").build();
        try {
            cursorQuery = context.getContentResolver().query(uriBuild, new String[]{"_id", Columns.FILE_ID, Columns.TTC_INDEX, Columns.VARIATION_SETTINGS, Columns.WEIGHT, Columns.ITALIC, Columns.RESULT_CODE}, "query = ?", new String[]{fontRequest.getQuery()}, null, cancellationSignal);
            if (cursorQuery != null && cursorQuery.getCount() > 0) {
                int columnIndex = cursorQuery.getColumnIndex(Columns.RESULT_CODE);
                arrayList = new ArrayList();
                int columnIndex2 = cursorQuery.getColumnIndex("_id");
                int columnIndex3 = cursorQuery.getColumnIndex(Columns.FILE_ID);
                int columnIndex4 = cursorQuery.getColumnIndex(Columns.TTC_INDEX);
                int columnIndex5 = cursorQuery.getColumnIndex(Columns.WEIGHT);
                int columnIndex6 = cursorQuery.getColumnIndex(Columns.ITALIC);
                while (cursorQuery.moveToNext()) {
                    int i = columnIndex != -1 ? cursorQuery.getInt(columnIndex) : 0;
                    arrayList.add(new FontInfo(columnIndex3 == -1 ? ContentUris.withAppendedId(uriBuild, cursorQuery.getLong(columnIndex2)) : ContentUris.withAppendedId(uriBuild2, cursorQuery.getLong(columnIndex3)), columnIndex4 != -1 ? cursorQuery.getInt(columnIndex4) : 0, columnIndex5 != -1 ? cursorQuery.getInt(columnIndex5) : 400, columnIndex6 != -1 && cursorQuery.getInt(columnIndex6) == 1, i));
                }
            }
            return new FontFamilyResult(0, (FontInfo[]) arrayList.toArray(new FontInfo[0]));
        } finally {
            if (cursorQuery != null) {
                cursorQuery.close();
            }
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Typeface getFontSync(Context context, FontRequest fontRequest, @Nullable ResourcesCompat.FontCallback fontCallback, @Nullable Handler handler, boolean z, int i, int i2) {
        String str = fontRequest.getIdentifier() + "-" + i2;
        Typeface typeface = a.get(str);
        if (typeface != null) {
            if (fontCallback != null) {
                fontCallback.onFontRetrieved(typeface);
            }
            return typeface;
        }
        if (z && i == -1) {
            f fVarA = a(context, fontRequest, i2);
            if (fontCallback != null) {
                int i3 = fVarA.b;
                if (i3 == 0) {
                    fontCallback.callbackSuccessAsync(fVarA.a, handler);
                } else {
                    fontCallback.callbackFailAsync(i3, handler);
                }
            }
            return fVarA.a;
        }
        a aVar = new a(context, fontRequest, i2, str);
        if (z) {
            try {
                return ((f) b.postAndWait(aVar, i)).a;
            } catch (InterruptedException unused) {
                return null;
            }
        }
        b bVar = fontCallback == null ? null : new b(fontCallback, handler);
        synchronized (c) {
            if (d.containsKey(str)) {
                if (bVar != null) {
                    d.get(str).add(bVar);
                }
                return null;
            }
            if (bVar != null) {
                ArrayList<SelfDestructiveThread.ReplyCallback<f>> arrayList = new ArrayList<>();
                arrayList.add(bVar);
                d.put(str, arrayList);
            }
            b.postAndReply(aVar, new c(str));
            return null;
        }
    }

    @VisibleForTesting
    @Nullable
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static ProviderInfo getProvider(@NonNull PackageManager packageManager, @NonNull FontRequest fontRequest, @Nullable Resources resources) throws PackageManager.NameNotFoundException {
        boolean z;
        String providerAuthority = fontRequest.getProviderAuthority();
        ProviderInfo providerInfoResolveContentProvider = packageManager.resolveContentProvider(providerAuthority, 0);
        if (providerInfoResolveContentProvider == null) {
            throw new PackageManager.NameNotFoundException(g9.b("No package found for authority: ", providerAuthority));
        }
        if (!providerInfoResolveContentProvider.packageName.equals(fontRequest.getProviderPackage())) {
            throw new PackageManager.NameNotFoundException("Found content provider " + providerAuthority + ", but package was not " + fontRequest.getProviderPackage());
        }
        Signature[] signatureArr = packageManager.getPackageInfo(providerInfoResolveContentProvider.packageName, 64).signatures;
        ArrayList arrayList = new ArrayList();
        for (Signature signature : signatureArr) {
            arrayList.add(signature.toByteArray());
        }
        Collections.sort(arrayList, e);
        List<List<byte[]>> certificates = fontRequest.getCertificates() != null ? fontRequest.getCertificates() : FontResourcesParserCompat.readCerts(resources, fontRequest.getCertificatesArrayResId());
        for (int i = 0; i < certificates.size(); i++) {
            ArrayList arrayList2 = new ArrayList(certificates.get(i));
            Collections.sort(arrayList2, e);
            if (arrayList.size() != arrayList2.size()) {
                z = false;
                break;
            }
            for (int i2 = 0; i2 < arrayList.size(); i2++) {
                if (!Arrays.equals((byte[]) arrayList.get(i2), (byte[]) arrayList2.get(i2))) {
                    z = false;
                    break;
                }
            }
            z = true;
            if (z) {
                return providerInfoResolveContentProvider;
            }
        }
        return null;
    }

    @RequiresApi(19)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static Map<Uri, ByteBuffer> prepareFontData(Context context, FontInfo[] fontInfoArr, CancellationSignal cancellationSignal) {
        HashMap map = new HashMap();
        for (FontInfo fontInfo : fontInfoArr) {
            if (fontInfo.getResultCode() == 0) {
                Uri uri = fontInfo.getUri();
                if (!map.containsKey(uri)) {
                    map.put(uri, TypefaceCompatUtil.mmap(context, cancellationSignal, uri));
                }
            }
        }
        return Collections.unmodifiableMap(map);
    }

    public static void requestFont(@NonNull Context context, @NonNull FontRequest fontRequest, @NonNull FontRequestCallback fontRequestCallback, @NonNull Handler handler) {
        handler.post(new d(context, fontRequest, new Handler(), fontRequestCallback));
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static void resetCache() {
        a.evictAll();
    }
}
