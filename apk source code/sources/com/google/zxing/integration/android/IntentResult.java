package com.google.zxing.integration.android;

import defpackage.g9;

/* loaded from: classes.dex */
public final class IntentResult {
    public final String a;
    public final String b;
    public final byte[] c;
    public final Integer d;
    public final String e;
    public final String f;

    public IntentResult() {
        this.a = null;
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.f = null;
    }

    public String getBarcodeImagePath() {
        return this.f;
    }

    public String getContents() {
        return this.a;
    }

    public String getErrorCorrectionLevel() {
        return this.e;
    }

    public String getFormatName() {
        return this.b;
    }

    public Integer getOrientation() {
        return this.d;
    }

    public byte[] getRawBytes() {
        return this.c;
    }

    public String toString() {
        byte[] bArr = this.c;
        int length = bArr == null ? 0 : bArr.length;
        StringBuilder sbA = g9.a("Format: ");
        g9.a(sbA, this.b, '\n', "Contents: ");
        sbA.append(this.a);
        sbA.append('\n');
        sbA.append("Raw bytes: (");
        sbA.append(length);
        sbA.append(" bytes)\nOrientation: ");
        sbA.append(this.d);
        sbA.append('\n');
        sbA.append("EC level: ");
        g9.a(sbA, this.e, '\n', "Barcode image: ");
        sbA.append(this.f);
        sbA.append('\n');
        return sbA.toString();
    }

    public IntentResult(String str, String str2, byte[] bArr, Integer num, String str3, String str4) {
        this.a = str;
        this.b = str2;
        this.c = bArr;
        this.d = num;
        this.e = str3;
        this.f = str4;
    }
}
