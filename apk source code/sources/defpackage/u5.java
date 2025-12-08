package defpackage;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.pdf.PdfDocument;
import android.os.AsyncTask;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.pdf.PrintedPdfDocument;
import android.support.v4.print.PrintHelper;
import android.util.Log;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class u5 extends AsyncTask<Void, Void, Throwable> {
    public final /* synthetic */ CancellationSignal a;
    public final /* synthetic */ PrintAttributes b;
    public final /* synthetic */ Bitmap c;
    public final /* synthetic */ PrintAttributes d;
    public final /* synthetic */ int e;
    public final /* synthetic */ ParcelFileDescriptor f;
    public final /* synthetic */ PrintDocumentAdapter.WriteResultCallback g;
    public final /* synthetic */ PrintHelper.a h;

    public u5(PrintHelper.a aVar, CancellationSignal cancellationSignal, PrintAttributes printAttributes, Bitmap bitmap, PrintAttributes printAttributes2, int i, ParcelFileDescriptor parcelFileDescriptor, PrintDocumentAdapter.WriteResultCallback writeResultCallback) {
        this.h = aVar;
        this.a = cancellationSignal;
        this.b = printAttributes;
        this.c = bitmap;
        this.d = printAttributes2;
        this.e = i;
        this.f = parcelFileDescriptor;
        this.g = writeResultCallback;
    }

    @Override // android.os.AsyncTask
    public Throwable doInBackground(Void[] voidArr) {
        RectF rectF;
        try {
            if (this.a.isCanceled()) {
                return null;
            }
            PrintedPdfDocument printedPdfDocument = new PrintedPdfDocument(this.h.a, this.b);
            Bitmap bitmapA = PrintHelper.a.a(this.h, this.c, this.b.getColorMode());
            if (this.a.isCanceled()) {
                return null;
            }
            try {
                PdfDocument.Page pageStartPage = printedPdfDocument.startPage(1);
                if (this.h.e) {
                    rectF = new RectF(pageStartPage.getInfo().getContentRect());
                } else {
                    PrintedPdfDocument printedPdfDocument2 = new PrintedPdfDocument(this.h.a, this.d);
                    PdfDocument.Page pageStartPage2 = printedPdfDocument2.startPage(1);
                    RectF rectF2 = new RectF(pageStartPage2.getInfo().getContentRect());
                    printedPdfDocument2.finishPage(pageStartPage2);
                    printedPdfDocument2.close();
                    rectF = rectF2;
                }
                PrintHelper.a aVar = this.h;
                int width = bitmapA.getWidth();
                int height = bitmapA.getHeight();
                int i = this.e;
                if (aVar == null) {
                    throw null;
                }
                Matrix matrix = new Matrix();
                float f = width;
                float fWidth = rectF.width() / f;
                float fMax = i == 2 ? Math.max(fWidth, rectF.height() / height) : Math.min(fWidth, rectF.height() / height);
                matrix.postScale(fMax, fMax);
                matrix.postTranslate((rectF.width() - (f * fMax)) / 2.0f, (rectF.height() - (height * fMax)) / 2.0f);
                if (!this.h.e) {
                    matrix.postTranslate(rectF.left, rectF.top);
                    pageStartPage.getCanvas().clipRect(rectF);
                }
                pageStartPage.getCanvas().drawBitmap(bitmapA, matrix, null);
                printedPdfDocument.finishPage(pageStartPage);
                if (this.a.isCanceled()) {
                    printedPdfDocument.close();
                    if (this.f != null) {
                        try {
                            this.f.close();
                        } catch (IOException unused) {
                        }
                    }
                    if (bitmapA == this.c) {
                        return null;
                    }
                } else {
                    printedPdfDocument.writeTo(new FileOutputStream(this.f.getFileDescriptor()));
                    printedPdfDocument.close();
                    if (this.f != null) {
                        try {
                            this.f.close();
                        } catch (IOException unused2) {
                        }
                    }
                    if (bitmapA == this.c) {
                        return null;
                    }
                }
                bitmapA.recycle();
                return null;
            } finally {
            }
        } catch (Throwable th) {
            return th;
        }
    }

    @Override // android.os.AsyncTask
    public void onPostExecute(Throwable th) {
        Throwable th2 = th;
        if (this.a.isCanceled()) {
            this.g.onWriteCancelled();
        } else if (th2 == null) {
            this.g.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
        } else {
            Log.e("PrintHelperApi19", "Error writing printed content", th2);
            this.g.onWriteFailed(null);
        }
    }
}
