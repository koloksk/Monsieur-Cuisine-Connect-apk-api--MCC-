package com.google.zxing;

import java.util.EnumMap;
import java.util.Map;

/* loaded from: classes.dex */
public final class Result {
    public final String a;
    public final byte[] b;
    public final int c;
    public ResultPoint[] d;
    public final BarcodeFormat e;
    public Map<ResultMetadataType, Object> f;
    public final long g;

    public Result(String str, byte[] bArr, ResultPoint[] resultPointArr, BarcodeFormat barcodeFormat) {
        this(str, bArr, resultPointArr, barcodeFormat, System.currentTimeMillis());
    }

    public void addResultPoints(ResultPoint[] resultPointArr) {
        ResultPoint[] resultPointArr2 = this.d;
        if (resultPointArr2 == null) {
            this.d = resultPointArr;
            return;
        }
        if (resultPointArr == null || resultPointArr.length <= 0) {
            return;
        }
        ResultPoint[] resultPointArr3 = new ResultPoint[resultPointArr2.length + resultPointArr.length];
        System.arraycopy(resultPointArr2, 0, resultPointArr3, 0, resultPointArr2.length);
        System.arraycopy(resultPointArr, 0, resultPointArr3, resultPointArr2.length, resultPointArr.length);
        this.d = resultPointArr3;
    }

    public BarcodeFormat getBarcodeFormat() {
        return this.e;
    }

    public int getNumBits() {
        return this.c;
    }

    public byte[] getRawBytes() {
        return this.b;
    }

    public Map<ResultMetadataType, Object> getResultMetadata() {
        return this.f;
    }

    public ResultPoint[] getResultPoints() {
        return this.d;
    }

    public String getText() {
        return this.a;
    }

    public long getTimestamp() {
        return this.g;
    }

    public void putAllMetadata(Map<ResultMetadataType, Object> map) {
        if (map != null) {
            Map<ResultMetadataType, Object> map2 = this.f;
            if (map2 == null) {
                this.f = map;
            } else {
                map2.putAll(map);
            }
        }
    }

    public void putMetadata(ResultMetadataType resultMetadataType, Object obj) {
        if (this.f == null) {
            this.f = new EnumMap(ResultMetadataType.class);
        }
        this.f.put(resultMetadataType, obj);
    }

    public String toString() {
        return this.a;
    }

    public Result(String str, byte[] bArr, ResultPoint[] resultPointArr, BarcodeFormat barcodeFormat, long j) {
        this(str, bArr, bArr == null ? 0 : bArr.length * 8, resultPointArr, barcodeFormat, j);
    }

    public Result(String str, byte[] bArr, int i, ResultPoint[] resultPointArr, BarcodeFormat barcodeFormat, long j) {
        this.a = str;
        this.b = bArr;
        this.c = i;
        this.d = resultPointArr;
        this.e = barcodeFormat;
        this.f = null;
        this.g = j;
    }
}
