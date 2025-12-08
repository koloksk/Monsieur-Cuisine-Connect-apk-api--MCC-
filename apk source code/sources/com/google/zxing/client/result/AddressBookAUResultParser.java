package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.ArrayList;
import org.apache.commons.lang3.CharUtils;

/* loaded from: classes.dex */
public final class AddressBookAUResultParser extends ResultParser {
    public static String[] a(String str, int i, String str2, boolean z) {
        ArrayList arrayList = null;
        for (int i2 = 1; i2 <= i; i2++) {
            String strB = ResultParser.b(str + i2 + ':', str2, CharUtils.CR, z);
            if (strB == null) {
                break;
            }
            if (arrayList == null) {
                arrayList = new ArrayList(i);
            }
            arrayList.add(strB);
        }
        if (arrayList == null) {
            return null;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }

    @Override // com.google.zxing.client.result.ResultParser
    public AddressBookParsedResult parse(Result result) {
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.contains("MEMORY") || !massagedText.contains("\r\n")) {
            return null;
        }
        String strB = ResultParser.b("NAME1:", massagedText, CharUtils.CR, true);
        String strB2 = ResultParser.b("NAME2:", massagedText, CharUtils.CR, true);
        String[] strArrA = a("TEL", 3, massagedText, true);
        String[] strArrA2 = a("MAIL", 3, massagedText, true);
        String strB3 = ResultParser.b("MEMORY:", massagedText, CharUtils.CR, false);
        String strB4 = ResultParser.b("ADD:", massagedText, CharUtils.CR, true);
        return new AddressBookParsedResult(ResultParser.maybeWrap(strB), null, strB2, strArrA, null, strArrA2, null, null, strB3, strB4 != null ? new String[]{strB4} : null, null, null, null, null, null, null);
    }
}
