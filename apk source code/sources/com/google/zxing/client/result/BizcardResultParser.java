package com.google.zxing.client.result;

import com.google.zxing.Result;
import defpackage.ia;
import java.util.ArrayList;

/* loaded from: classes.dex */
public final class BizcardResultParser extends ia {
    @Override // com.google.zxing.client.result.ResultParser
    public AddressBookParsedResult parse(Result result) {
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.startsWith("BIZCARD:")) {
            return null;
        }
        String strB = ia.b("N:", massagedText, true);
        String strB2 = ia.b("X:", massagedText, true);
        if (strB == null) {
            strB = strB2;
        } else if (strB2 != null) {
            strB = strB + ' ' + strB2;
        }
        String strB3 = ia.b("T:", massagedText, true);
        String strB4 = ia.b("C:", massagedText, true);
        String[] strArrA = ia.a("A:", massagedText, true);
        String strB5 = ia.b("B:", massagedText, true);
        String strB6 = ia.b("M:", massagedText, true);
        String strB7 = ia.b("F:", massagedText, true);
        String strB8 = ia.b("E:", massagedText, true);
        String[] strArrMaybeWrap = ResultParser.maybeWrap(strB);
        ArrayList arrayList = new ArrayList(3);
        if (strB5 != null) {
            arrayList.add(strB5);
        }
        if (strB6 != null) {
            arrayList.add(strB6);
        }
        if (strB7 != null) {
            arrayList.add(strB7);
        }
        int size = arrayList.size();
        return new AddressBookParsedResult(strArrMaybeWrap, null, null, size != 0 ? (String[]) arrayList.toArray(new String[size]) : null, null, ResultParser.maybeWrap(strB8), null, null, null, strArrA, null, strB4, null, strB3, null, null);
    }
}
