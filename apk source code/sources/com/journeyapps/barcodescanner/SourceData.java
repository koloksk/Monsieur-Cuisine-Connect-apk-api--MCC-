package com.journeyapps.barcodescanner;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import com.google.zxing.PlanarYUVLuminanceSource;
import java.io.ByteArrayOutputStream;

/* loaded from: classes.dex */
public class SourceData {
    public byte[] a;
    public int b;
    public int c;
    public int d;
    public int e;
    public Rect f;

    public SourceData(byte[] bArr, int i, int i2, int i3, int i4) {
        this.a = bArr;
        this.b = i;
        this.c = i2;
        this.e = i4;
        this.d = i3;
        if (i * i2 <= bArr.length) {
            return;
        }
        throw new IllegalArgumentException("Image data does not match the resolution. " + i + "x" + i2 + " > " + bArr.length);
    }

    public static byte[] rotate180(byte[] bArr, int i, int i2) {
        int i3 = i * i2;
        byte[] bArr2 = new byte[i3];
        int i4 = i3 - 1;
        for (int i5 = 0; i5 < i3; i5++) {
            bArr2[i4] = bArr[i5];
            i4--;
        }
        return bArr2;
    }

    public static byte[] rotateCCW(byte[] bArr, int i, int i2) {
        int i3 = i * i2;
        byte[] bArr2 = new byte[i3];
        int i4 = i3 - 1;
        for (int i5 = 0; i5 < i; i5++) {
            for (int i6 = i2 - 1; i6 >= 0; i6--) {
                bArr2[i4] = bArr[(i6 * i) + i5];
                i4--;
            }
        }
        return bArr2;
    }

    public static byte[] rotateCW(byte[] bArr, int i, int i2) {
        byte[] bArr2 = new byte[i * i2];
        int i3 = 0;
        for (int i4 = 0; i4 < i; i4++) {
            for (int i5 = i2 - 1; i5 >= 0; i5--) {
                bArr2[i3] = bArr[(i5 * i) + i4];
                i3++;
            }
        }
        return bArr2;
    }

    public static byte[] rotateCameraPreview(int i, byte[] bArr, int i2, int i3) {
        return i != 90 ? i != 180 ? i != 270 ? bArr : rotateCCW(bArr, i2, i3) : rotate180(bArr, i2, i3) : rotateCW(bArr, i2, i3);
    }

    public PlanarYUVLuminanceSource createSource() {
        byte[] bArrRotateCameraPreview = rotateCameraPreview(this.e, this.a, this.b, this.c);
        if (isRotated()) {
            int i = this.c;
            int i2 = this.b;
            Rect rect = this.f;
            return new PlanarYUVLuminanceSource(bArrRotateCameraPreview, i, i2, rect.left, rect.top, rect.width(), this.f.height(), false);
        }
        int i3 = this.b;
        int i4 = this.c;
        Rect rect2 = this.f;
        return new PlanarYUVLuminanceSource(bArrRotateCameraPreview, i3, i4, rect2.left, rect2.top, rect2.width(), this.f.height(), false);
    }

    public Bitmap getBitmap() {
        return getBitmap(1);
    }

    public Rect getCropRect() {
        return this.f;
    }

    public byte[] getData() {
        return this.a;
    }

    public int getDataHeight() {
        return this.c;
    }

    public int getDataWidth() {
        return this.b;
    }

    public int getImageFormat() {
        return this.d;
    }

    public boolean isRotated() {
        return this.e % 180 != 0;
    }

    public void setCropRect(Rect rect) {
        this.f = rect;
    }

    public Bitmap getBitmap(int i) {
        Rect rect = this.f;
        if (isRotated()) {
            rect = new Rect(rect.top, rect.left, rect.bottom, rect.right);
        }
        YuvImage yuvImage = new YuvImage(this.a, this.d, this.b, this.c, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        yuvImage.compressToJpeg(rect, 90, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = i;
        Bitmap bitmapDecodeByteArray = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length, options);
        if (this.e == 0) {
            return bitmapDecodeByteArray;
        }
        Matrix matrix = new Matrix();
        matrix.postRotate(this.e);
        return Bitmap.createBitmap(bitmapDecodeByteArray, 0, 0, bitmapDecodeByteArray.getWidth(), bitmapDecodeByteArray.getHeight(), matrix, false);
    }
}
