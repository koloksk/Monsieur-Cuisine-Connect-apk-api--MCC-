package com.google.zxing.qrcode.encoder;

import java.lang.reflect.Array;

/* loaded from: classes.dex */
public final class ByteMatrix {
    public final byte[][] a;
    public final int b;
    public final int c;

    public ByteMatrix(int i, int i2) {
        this.a = (byte[][]) Array.newInstance((Class<?>) byte.class, i2, i);
        this.b = i;
        this.c = i2;
    }

    public void clear(byte b) {
        for (int i = 0; i < this.c; i++) {
            for (int i2 = 0; i2 < this.b; i2++) {
                this.a[i][i2] = b;
            }
        }
    }

    public byte get(int i, int i2) {
        return this.a[i2][i];
    }

    public byte[][] getArray() {
        return this.a;
    }

    public int getHeight() {
        return this.c;
    }

    public int getWidth() {
        return this.b;
    }

    public void set(int i, int i2, byte b) {
        this.a[i2][i] = b;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder((this.b * 2 * this.c) + 2);
        for (int i = 0; i < this.c; i++) {
            for (int i2 = 0; i2 < this.b; i2++) {
                byte b = this.a[i][i2];
                if (b == 0) {
                    sb.append(" 0");
                } else if (b != 1) {
                    sb.append("  ");
                } else {
                    sb.append(" 1");
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    public void set(int i, int i2, int i3) {
        this.a[i2][i] = (byte) i3;
    }

    public void set(int i, int i2, boolean z) {
        this.a[i2][i] = z ? (byte) 1 : (byte) 0;
    }
}
