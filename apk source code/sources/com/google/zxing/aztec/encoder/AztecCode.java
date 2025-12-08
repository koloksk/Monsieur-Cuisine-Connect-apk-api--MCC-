package com.google.zxing.aztec.encoder;

import com.google.zxing.common.BitMatrix;

/* loaded from: classes.dex */
public final class AztecCode {
    public boolean a;
    public int b;
    public int c;
    public int d;
    public BitMatrix e;

    public int getCodeWords() {
        return this.d;
    }

    public int getLayers() {
        return this.c;
    }

    public BitMatrix getMatrix() {
        return this.e;
    }

    public int getSize() {
        return this.b;
    }

    public boolean isCompact() {
        return this.a;
    }

    public void setCodeWords(int i) {
        this.d = i;
    }

    public void setCompact(boolean z) {
        this.a = z;
    }

    public void setLayers(int i) {
        this.c = i;
    }

    public void setMatrix(BitMatrix bitMatrix) {
        this.e = bitMatrix;
    }

    public void setSize(int i) {
        this.b = i;
    }
}
