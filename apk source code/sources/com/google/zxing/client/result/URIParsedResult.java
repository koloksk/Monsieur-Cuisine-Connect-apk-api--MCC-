package com.google.zxing.client.result;

import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class URIParsedResult extends ParsedResult {
    public static final Pattern d = Pattern.compile(":/*([^/@]+)@[^/]+");
    public final String b;
    public final String c;

    /* JADX WARN: Removed duplicated region for block: B:9:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public URIParsedResult(java.lang.String r3, java.lang.String r4) {
        /*
            r2 = this;
            com.google.zxing.client.result.ParsedResultType r0 = com.google.zxing.client.result.ParsedResultType.URI
            r2.<init>(r0)
            java.lang.String r3 = r3.trim()
            r0 = 58
            int r0 = r3.indexOf(r0)
            if (r0 < 0) goto L26
            int r0 = r0 + 1
            r1 = 47
            int r1 = r3.indexOf(r1, r0)
            if (r1 >= 0) goto L1f
            int r1 = r3.length()
        L1f:
            int r1 = r1 - r0
            boolean r0 = com.google.zxing.client.result.ResultParser.isSubstringOfDigits(r3, r0, r1)
            if (r0 == 0) goto L34
        L26:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            java.lang.String r1 = "http://"
            r0.<init>(r1)
            r0.append(r3)
            java.lang.String r3 = r0.toString()
        L34:
            r2.b = r3
            r2.c = r4
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.client.result.URIParsedResult.<init>(java.lang.String, java.lang.String):void");
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(30);
        ParsedResult.maybeAppend(this.c, sb);
        ParsedResult.maybeAppend(this.b, sb);
        return sb.toString();
    }

    public String getTitle() {
        return this.c;
    }

    public String getURI() {
        return this.b;
    }

    public boolean isPossiblyMaliciousURI() {
        return d.matcher(this.b).find();
    }
}
