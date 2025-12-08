package com.google.zxing.client.result;

import com.google.zxing.Result;
import defpackage.ia;

/* loaded from: classes.dex */
public final class BookmarkDoCoMoResultParser extends ia {
    @Override // com.google.zxing.client.result.ResultParser
    public URIParsedResult parse(Result result) {
        String text = result.getText();
        if (!text.startsWith("MEBKM:")) {
            return null;
        }
        String strB = ia.b("TITLE:", text, true);
        String[] strArrA = ia.a("URL:", text, true);
        if (strArrA == null) {
            return null;
        }
        String str = strArrA[0];
        if (URIResultParser.b(str)) {
            return new URIParsedResult(str, strB);
        }
        return null;
    }
}
