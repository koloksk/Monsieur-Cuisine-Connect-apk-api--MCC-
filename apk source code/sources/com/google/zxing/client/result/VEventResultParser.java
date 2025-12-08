package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.List;

/* loaded from: classes.dex */
public final class VEventResultParser extends ResultParser {
    public static String a(CharSequence charSequence, String str, boolean z) {
        List<String> listA = VCardResultParser.a(charSequence, str, z, false);
        if (listA == null || listA.isEmpty()) {
            return null;
        }
        return listA.get(0);
    }

    public static String b(String str) {
        return str != null ? (str.startsWith("mailto:") || str.startsWith("MAILTO:")) ? str.substring(7) : str : str;
    }

    @Override // com.google.zxing.client.result.ResultParser
    public CalendarParsedResult parse(Result result) throws NumberFormatException {
        String[] strArr;
        double d;
        String massagedText = ResultParser.getMassagedText(result);
        if (massagedText.indexOf("BEGIN:VEVENT") < 0) {
            return null;
        }
        String strA = a("SUMMARY", massagedText, true);
        String strA2 = a("DTSTART", massagedText, true);
        if (strA2 == null) {
            return null;
        }
        String strA3 = a("DTEND", massagedText, true);
        String strA4 = a("DURATION", massagedText, true);
        String strA5 = a("LOCATION", massagedText, true);
        String strB = b(a("ORGANIZER", massagedText, true));
        List<List<String>> listB = VCardResultParser.b((CharSequence) "ATTENDEE", massagedText, true, false);
        if (listB == null || listB.isEmpty()) {
            strArr = null;
        } else {
            int size = listB.size();
            String[] strArr2 = new String[size];
            for (int i = 0; i < size; i++) {
                strArr2[i] = listB.get(i).get(0);
            }
            strArr = strArr2;
        }
        if (strArr != null) {
            for (int i2 = 0; i2 < strArr.length; i2++) {
                strArr[i2] = b(strArr[i2]);
            }
        }
        String strA6 = a("DESCRIPTION", massagedText, true);
        String strA7 = a("GEO", massagedText, true);
        double d2 = Double.NaN;
        if (strA7 == null) {
            d = Double.NaN;
        } else {
            int iIndexOf = strA7.indexOf(59);
            if (iIndexOf < 0) {
                return null;
            }
            try {
                d2 = Double.parseDouble(strA7.substring(0, iIndexOf));
                d = Double.parseDouble(strA7.substring(iIndexOf + 1));
            } catch (NumberFormatException | IllegalArgumentException unused) {
                return null;
            }
        }
        return new CalendarParsedResult(strA, strA2, strA3, strA4, strA5, strB, strArr, strA6, d2, d);
    }
}
