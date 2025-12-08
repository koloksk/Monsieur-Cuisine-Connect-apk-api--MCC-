package com.google.zxing.pdf417;

/* loaded from: classes.dex */
public final class PDF417ResultMetadata {
    public int a;
    public String b;
    public int[] c;
    public boolean d;

    public String getFileId() {
        return this.b;
    }

    public int[] getOptionalData() {
        return this.c;
    }

    public int getSegmentIndex() {
        return this.a;
    }

    public boolean isLastSegment() {
        return this.d;
    }

    public void setFileId(String str) {
        this.b = str;
    }

    public void setLastSegment(boolean z) {
        this.d = z;
    }

    public void setOptionalData(int[] iArr) {
        this.c = iArr;
    }

    public void setSegmentIndex(int i) {
        this.a = i;
    }
}
