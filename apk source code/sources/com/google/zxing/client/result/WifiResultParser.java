package com.google.zxing.client.result;

import com.google.zxing.Result;

/* loaded from: classes.dex */
public final class WifiResultParser extends ResultParser {
    @Override // com.google.zxing.client.result.ResultParser
    public WifiParsedResult parse(Result result) {
        String strB;
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.startsWith("WIFI:") || (strB = ResultParser.b("S:", massagedText, ';', false)) == null || strB.isEmpty()) {
            return null;
        }
        String strB2 = ResultParser.b("P:", massagedText, ';', false);
        String strB3 = ResultParser.b("T:", massagedText, ';', false);
        if (strB3 == null) {
            strB3 = "nopass";
        }
        return new WifiParsedResult(strB3, strB, strB2, Boolean.parseBoolean(ResultParser.b("H:", massagedText, ';', false)));
    }
}
