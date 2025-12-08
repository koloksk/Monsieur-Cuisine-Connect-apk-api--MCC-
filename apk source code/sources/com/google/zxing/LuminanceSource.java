package com.google.zxing;

import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes.dex */
public abstract class LuminanceSource {
    public final int a;
    public final int b;

    public LuminanceSource(int i, int i2) {
        this.a = i;
        this.b = i2;
    }

    public LuminanceSource crop(int i, int i2, int i3, int i4) {
        throw new UnsupportedOperationException("This luminance source does not support cropping.");
    }

    public final int getHeight() {
        return this.b;
    }

    public abstract byte[] getMatrix();

    public abstract byte[] getRow(int i, byte[] bArr);

    public final int getWidth() {
        return this.a;
    }

    public LuminanceSource invert() {
        return new InvertedLuminanceSource(this);
    }

    public boolean isCropSupported() {
        return false;
    }

    public boolean isRotateSupported() {
        return false;
    }

    public LuminanceSource rotateCounterClockwise() {
        throw new UnsupportedOperationException("This luminance source does not support rotation by 90 degrees.");
    }

    public LuminanceSource rotateCounterClockwise45() {
        throw new UnsupportedOperationException("This luminance source does not support rotation by 45 degrees.");
    }

    public final String toString() {
        int i = this.a;
        byte[] row = new byte[i];
        StringBuilder sb = new StringBuilder((i + 1) * this.b);
        for (int i2 = 0; i2 < this.b; i2++) {
            row = getRow(i2, row);
            for (int i3 = 0; i3 < this.a; i3++) {
                int i4 = row[i3] & 255;
                sb.append(i4 < 64 ? '#' : i4 < 128 ? '+' : i4 < 192 ? ClassUtils.PACKAGE_SEPARATOR_CHAR : ' ');
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
