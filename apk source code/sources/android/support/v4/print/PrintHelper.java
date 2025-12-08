package android.support.v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import android.support.annotation.RequiresApi;
import android.util.Log;
import defpackage.u5;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class PrintHelper {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    public final e a;

    public interface OnPrintFinishCallback {
        void onFinish();
    }

    @RequiresApi(19)
    public static class a implements e {
        public final Context a;
        public int h;
        public BitmapFactory.Options b = null;
        public final Object c = new Object();
        public int f = 2;
        public int g = 2;
        public boolean d = true;
        public boolean e = true;

        /* renamed from: android.support.v4.print.PrintHelper$a$a, reason: collision with other inner class name */
        public class C0002a extends PrintDocumentAdapter {
            public PrintAttributes a;
            public final /* synthetic */ String b;
            public final /* synthetic */ int c;
            public final /* synthetic */ Bitmap d;
            public final /* synthetic */ OnPrintFinishCallback e;

            public C0002a(String str, int i, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
                this.b = str;
                this.c = i;
                this.d = bitmap;
                this.e = onPrintFinishCallback;
            }

            @Override // android.print.PrintDocumentAdapter
            public void onFinish() {
                OnPrintFinishCallback onPrintFinishCallback = this.e;
                if (onPrintFinishCallback != null) {
                    onPrintFinishCallback.onFinish();
                }
            }

            @Override // android.print.PrintDocumentAdapter
            public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
                this.a = printAttributes2;
                layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.b).setContentType(1).setPageCount(1).build(), !printAttributes2.equals(printAttributes));
            }

            @Override // android.print.PrintDocumentAdapter
            public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
                a.a(a.this, this.a, this.c, this.d, parcelFileDescriptor, cancellationSignal, writeResultCallback);
            }
        }

        public class b extends PrintDocumentAdapter {
            public PrintAttributes a;
            public AsyncTask<Uri, Boolean, Bitmap> b;
            public Bitmap c = null;
            public final /* synthetic */ String d;
            public final /* synthetic */ Uri e;
            public final /* synthetic */ OnPrintFinishCallback f;
            public final /* synthetic */ int g;

            /* renamed from: android.support.v4.print.PrintHelper$a$b$a, reason: collision with other inner class name */
            public class AsyncTaskC0003a extends AsyncTask<Uri, Boolean, Bitmap> {
                public final /* synthetic */ CancellationSignal a;
                public final /* synthetic */ PrintAttributes b;
                public final /* synthetic */ PrintAttributes c;
                public final /* synthetic */ PrintDocumentAdapter.LayoutResultCallback d;

                /* renamed from: android.support.v4.print.PrintHelper$a$b$a$a, reason: collision with other inner class name */
                public class C0004a implements CancellationSignal.OnCancelListener {
                    public C0004a() {
                    }

                    @Override // android.os.CancellationSignal.OnCancelListener
                    public void onCancel() {
                        b.this.a();
                        AsyncTaskC0003a.this.cancel(false);
                    }
                }

                public AsyncTaskC0003a(CancellationSignal cancellationSignal, PrintAttributes printAttributes, PrintAttributes printAttributes2, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback) {
                    this.a = cancellationSignal;
                    this.b = printAttributes;
                    this.c = printAttributes2;
                    this.d = layoutResultCallback;
                }

                @Override // android.os.AsyncTask
                /* renamed from: a, reason: merged with bridge method [inline-methods] */
                public void onPostExecute(Bitmap bitmap) {
                    PrintAttributes.MediaSize mediaSize;
                    super.onPostExecute(bitmap);
                    if (bitmap != null) {
                        a aVar = a.this;
                        if (!aVar.d || aVar.h == 0) {
                            synchronized (this) {
                                mediaSize = b.this.a.getMediaSize();
                            }
                            if (mediaSize != null) {
                                if (mediaSize.isPortrait() != (bitmap.getWidth() <= bitmap.getHeight())) {
                                    Matrix matrix = new Matrix();
                                    matrix.postRotate(90.0f);
                                    bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                                }
                            }
                        }
                    }
                    b.this.c = bitmap;
                    if (bitmap != null) {
                        this.d.onLayoutFinished(new PrintDocumentInfo.Builder(b.this.d).setContentType(1).setPageCount(1).build(), true ^ this.b.equals(this.c));
                    } else {
                        this.d.onLayoutFailed(null);
                    }
                    b.this.b = null;
                }

                @Override // android.os.AsyncTask
                public Bitmap doInBackground(Uri[] uriArr) {
                    try {
                        return a.this.a(b.this.e);
                    } catch (FileNotFoundException unused) {
                        return null;
                    }
                }

                @Override // android.os.AsyncTask
                public void onCancelled(Bitmap bitmap) {
                    this.d.onLayoutCancelled();
                    b.this.b = null;
                }

                @Override // android.os.AsyncTask
                public void onPreExecute() {
                    this.a.setOnCancelListener(new C0004a());
                }
            }

            public b(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback, int i) {
                this.d = str;
                this.e = uri;
                this.f = onPrintFinishCallback;
                this.g = i;
            }

            @Override // android.print.PrintDocumentAdapter
            public void onFinish() {
                super.onFinish();
                a();
                AsyncTask<Uri, Boolean, Bitmap> asyncTask = this.b;
                if (asyncTask != null) {
                    asyncTask.cancel(true);
                }
                OnPrintFinishCallback onPrintFinishCallback = this.f;
                if (onPrintFinishCallback != null) {
                    onPrintFinishCallback.onFinish();
                }
                Bitmap bitmap = this.c;
                if (bitmap != null) {
                    bitmap.recycle();
                    this.c = null;
                }
            }

            @Override // android.print.PrintDocumentAdapter
            public void onLayout(PrintAttributes printAttributes, PrintAttributes printAttributes2, CancellationSignal cancellationSignal, PrintDocumentAdapter.LayoutResultCallback layoutResultCallback, Bundle bundle) {
                synchronized (this) {
                    this.a = printAttributes2;
                }
                if (cancellationSignal.isCanceled()) {
                    layoutResultCallback.onLayoutCancelled();
                } else if (this.c != null) {
                    layoutResultCallback.onLayoutFinished(new PrintDocumentInfo.Builder(this.d).setContentType(1).setPageCount(1).build(), !printAttributes2.equals(printAttributes));
                } else {
                    this.b = new AsyncTaskC0003a(cancellationSignal, printAttributes2, printAttributes, layoutResultCallback).execute(new Uri[0]);
                }
            }

            @Override // android.print.PrintDocumentAdapter
            public void onWrite(PageRange[] pageRangeArr, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
                a.a(a.this, this.a, this.g, this.c, parcelFileDescriptor, cancellationSignal, writeResultCallback);
            }

            public final void a() {
                synchronized (a.this.c) {
                    if (a.this.b != null) {
                        a.this.b.requestCancelDecode();
                        a.this.b = null;
                    }
                }
            }
        }

        public a(Context context) {
            this.a = context;
        }

        @Override // android.support.v4.print.PrintHelper.e
        public void a(int i) {
            this.f = i;
        }

        @Override // android.support.v4.print.PrintHelper.e
        public int b() {
            return this.f;
        }

        @Override // android.support.v4.print.PrintHelper.e
        public void c(int i) {
            this.g = i;
        }

        @Override // android.support.v4.print.PrintHelper.e
        public int a() {
            int i = this.h;
            if (i == 0) {
                return 1;
            }
            return i;
        }

        @Override // android.support.v4.print.PrintHelper.e
        public void b(int i) {
            this.h = i;
        }

        @Override // android.support.v4.print.PrintHelper.e
        public int c() {
            return this.g;
        }

        public PrintAttributes.Builder a(PrintAttributes printAttributes) {
            PrintAttributes.Builder minMargins = new PrintAttributes.Builder().setMediaSize(printAttributes.getMediaSize()).setResolution(printAttributes.getResolution()).setMinMargins(printAttributes.getMinMargins());
            if (printAttributes.getColorMode() != 0) {
                minMargins.setColorMode(printAttributes.getColorMode());
            }
            return minMargins;
        }

        @Override // android.support.v4.print.PrintHelper.e
        public void a(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
            PrintAttributes.MediaSize mediaSize;
            if (bitmap == null) {
                return;
            }
            int i = this.f;
            PrintManager printManager = (PrintManager) this.a.getSystemService("print");
            if (bitmap.getWidth() <= bitmap.getHeight()) {
                mediaSize = PrintAttributes.MediaSize.UNKNOWN_PORTRAIT;
            } else {
                mediaSize = PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE;
            }
            printManager.print(str, new C0002a(str, i, bitmap, onPrintFinishCallback), new PrintAttributes.Builder().setMediaSize(mediaSize).setColorMode(this.g).build());
        }

        public static /* synthetic */ void a(a aVar, PrintAttributes printAttributes, int i, Bitmap bitmap, ParcelFileDescriptor parcelFileDescriptor, CancellationSignal cancellationSignal, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
            new u5(aVar, cancellationSignal, aVar.e ? printAttributes : aVar.a(printAttributes).setMinMargins(new PrintAttributes.Margins(0, 0, 0, 0)).build(), bitmap, printAttributes, i, parcelFileDescriptor, writeResultCallback).execute(new Void[0]);
        }

        @Override // android.support.v4.print.PrintHelper.e
        public void a(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
            b bVar = new b(str, uri, onPrintFinishCallback, this.f);
            PrintManager printManager = (PrintManager) this.a.getSystemService("print");
            PrintAttributes.Builder builder = new PrintAttributes.Builder();
            builder.setColorMode(this.g);
            int i = this.h;
            if (i == 1 || i == 0) {
                builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_LANDSCAPE);
            } else if (i == 2) {
                builder.setMediaSize(PrintAttributes.MediaSize.UNKNOWN_PORTRAIT);
            }
            printManager.print(str, bVar, builder.build());
        }

        public final Bitmap a(Uri uri) throws Throwable {
            BitmapFactory.Options options;
            if (uri != null && this.a != null) {
                BitmapFactory.Options options2 = new BitmapFactory.Options();
                options2.inJustDecodeBounds = true;
                a(uri, options2);
                int i = options2.outWidth;
                int i2 = options2.outHeight;
                if (i > 0 && i2 > 0) {
                    int iMax = Math.max(i, i2);
                    int i3 = 1;
                    while (iMax > 3500) {
                        iMax >>>= 1;
                        i3 <<= 1;
                    }
                    if (i3 > 0 && Math.min(i, i2) / i3 > 0) {
                        synchronized (this.c) {
                            options = new BitmapFactory.Options();
                            this.b = options;
                            options.inMutable = true;
                            options.inSampleSize = i3;
                        }
                        try {
                            Bitmap bitmapA = a(uri, options);
                            synchronized (this.c) {
                                this.b = null;
                            }
                            return bitmapA;
                        } catch (Throwable th) {
                            synchronized (this.c) {
                                this.b = null;
                                throw th;
                            }
                        }
                    }
                }
                return null;
            }
            throw new IllegalArgumentException("bad argument to getScaledBitmap");
        }

        public final Bitmap a(Uri uri, BitmapFactory.Options options) throws Throwable {
            Context context;
            InputStream inputStreamOpenInputStream;
            if (uri != null && (context = this.a) != null) {
                InputStream inputStream = null;
                try {
                    inputStreamOpenInputStream = context.getContentResolver().openInputStream(uri);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    Bitmap bitmapDecodeStream = BitmapFactory.decodeStream(inputStreamOpenInputStream, null, options);
                    if (inputStreamOpenInputStream != null) {
                        try {
                            inputStreamOpenInputStream.close();
                        } catch (IOException e) {
                            Log.w("PrintHelperApi19", "close fail ", e);
                        }
                    }
                    return bitmapDecodeStream;
                } catch (Throwable th2) {
                    th = th2;
                    inputStream = inputStreamOpenInputStream;
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e2) {
                            Log.w("PrintHelperApi19", "close fail ", e2);
                        }
                    }
                    throw th;
                }
            }
            throw new IllegalArgumentException("bad argument to loadBitmap");
        }

        public static /* synthetic */ Bitmap a(a aVar, Bitmap bitmap, int i) {
            if (aVar == null) {
                throw null;
            }
            if (i != 1) {
                return bitmap;
            }
            Bitmap bitmapCreateBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateBitmap);
            Paint paint = new Paint();
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0.0f);
            paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
            canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
            canvas.setBitmap(null);
            return bitmapCreateBitmap;
        }
    }

    @RequiresApi(20)
    public static class b extends a {
        public b(Context context) {
            super(context);
            this.d = false;
        }
    }

    @RequiresApi(23)
    public static class c extends b {
        public c(Context context) {
            super(context);
            this.e = false;
        }

        @Override // android.support.v4.print.PrintHelper.a
        public PrintAttributes.Builder a(PrintAttributes printAttributes) {
            PrintAttributes.Builder builderA = super.a(printAttributes);
            if (printAttributes.getDuplexMode() != 0) {
                builderA.setDuplexMode(printAttributes.getDuplexMode());
            }
            return builderA;
        }
    }

    @RequiresApi(24)
    public static class d extends c {
        public d(Context context) {
            super(context);
            this.e = true;
            this.d = true;
        }
    }

    public interface e {
        int a();

        void a(int i);

        void a(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback);

        void a(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException;

        int b();

        void b(int i);

        int c();

        void c(int i);
    }

    public PrintHelper(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            this.a = new d(context);
        } else {
            this.a = new c(context);
        }
    }

    public static boolean systemSupportsPrint() {
        return true;
    }

    public int getColorMode() {
        return this.a.c();
    }

    public int getOrientation() {
        return this.a.a();
    }

    public int getScaleMode() {
        return this.a.b();
    }

    public void printBitmap(String str, Bitmap bitmap) {
        this.a.a(str, bitmap, (OnPrintFinishCallback) null);
    }

    public void setColorMode(int i) {
        this.a.c(i);
    }

    public void setOrientation(int i) {
        this.a.b(i);
    }

    public void setScaleMode(int i) {
        this.a.a(i);
    }

    public void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) {
        this.a.a(str, bitmap, onPrintFinishCallback);
    }

    public void printBitmap(String str, Uri uri) throws FileNotFoundException {
        this.a.a(str, uri, (OnPrintFinishCallback) null);
    }

    public void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException {
        this.a.a(str, uri, onPrintFinishCallback);
    }
}
