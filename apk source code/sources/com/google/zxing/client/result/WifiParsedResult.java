package com.google.zxing.client.result;

/* loaded from: classes.dex */
public final class WifiParsedResult extends ParsedResult {
    public final String b;
    public final String c;
    public final String d;
    public final boolean e;

    public WifiParsedResult(String str, String str2, String str3) {
        this(str, str2, str3, false);
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(80);
        ParsedResult.maybeAppend(this.b, sb);
        ParsedResult.maybeAppend(this.c, sb);
        ParsedResult.maybeAppend(this.d, sb);
        ParsedResult.maybeAppend(Boolean.toString(this.e), sb);
        return sb.toString();
    }

    public String getNetworkEncryption() {
        return this.c;
    }

    public String getPassword() {
        return this.d;
    }

    public String getSsid() {
        return this.b;
    }

    public boolean isHidden() {
        return this.e;
    }

    public WifiParsedResult(String str, String str2, String str3, boolean z) {
        super(ParsedResultType.WIFI);
        this.b = str2;
        this.c = str;
        this.d = str3;
        this.e = z;
    }
}
