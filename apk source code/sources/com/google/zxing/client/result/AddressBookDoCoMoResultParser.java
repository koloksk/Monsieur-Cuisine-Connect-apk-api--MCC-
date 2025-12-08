package com.google.zxing.client.result;

import com.google.zxing.Result;
import defpackage.ia;

/* loaded from: classes.dex */
public final class AddressBookDoCoMoResultParser extends ia {
    @Override // com.google.zxing.client.result.ResultParser
    public AddressBookParsedResult parse(Result result) {
        String[] strArrA;
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.startsWith("MECARD:") || (strArrA = ia.a("N:", massagedText, true)) == null) {
            return null;
        }
        String str = strArrA[0];
        int iIndexOf = str.indexOf(44);
        if (iIndexOf >= 0) {
            str = str.substring(iIndexOf + 1) + ' ' + str.substring(0, iIndexOf);
        }
        String strB = ia.b("SOUND:", massagedText, true);
        String[] strArrA2 = ia.a("TEL:", massagedText, true);
        String[] strArrA3 = ia.a("EMAIL:", massagedText, true);
        String strB2 = ia.b("NOTE:", massagedText, false);
        String[] strArrA4 = ia.a("ADR:", massagedText, true);
        String strB3 = ia.b("BDAY:", massagedText, true);
        return new AddressBookParsedResult(ResultParser.maybeWrap(str), null, strB, strArrA2, null, strArrA3, null, null, strB2, strArrA4, null, ia.b("ORG:", massagedText, true), !ResultParser.isStringOfDigits(strB3, 8) ? null : strB3, null, ia.a("URL:", massagedText, true), null);
    }
}
