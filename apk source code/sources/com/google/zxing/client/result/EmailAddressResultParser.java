package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.lang3.CharEncoding;

/* loaded from: classes.dex */
public final class EmailAddressResultParser extends ResultParser {
    public static final Pattern e = Pattern.compile(",");

    @Override // com.google.zxing.client.result.ResultParser
    public EmailAddressParsedResult parse(Result result) throws UnsupportedEncodingException {
        String[] strArr;
        String[] strArr2;
        String[] strArr3;
        String str;
        String str2;
        String str3;
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.startsWith("mailto:") && !massagedText.startsWith("MAILTO:")) {
            if (EmailDoCoMoResultParser.b(massagedText)) {
                return new EmailAddressParsedResult(new String[]{massagedText}, null, null, null, null);
            }
            return null;
        }
        String strSubstring = massagedText.substring(7);
        int iIndexOf = strSubstring.indexOf(63);
        if (iIndexOf >= 0) {
            strSubstring = strSubstring.substring(0, iIndexOf);
        }
        try {
            try {
                String strDecode = URLDecoder.decode(strSubstring, CharEncoding.UTF_8);
                String[] strArrSplit = !strDecode.isEmpty() ? e.split(strDecode) : null;
                Map<String, String> mapA = ResultParser.a(massagedText);
                if (mapA != null) {
                    if (strArrSplit == null && (str3 = mapA.get("to")) != null) {
                        strArrSplit = e.split(str3);
                    }
                    String str4 = mapA.get("cc");
                    String[] strArrSplit2 = str4 != null ? e.split(str4) : null;
                    String str5 = mapA.get("bcc");
                    String[] strArrSplit3 = str5 != null ? e.split(str5) : null;
                    String str6 = mapA.get("subject");
                    str2 = mapA.get("body");
                    strArr = strArrSplit;
                    strArr2 = strArrSplit2;
                    strArr3 = strArrSplit3;
                    str = str6;
                } else {
                    strArr = strArrSplit;
                    strArr2 = null;
                    strArr3 = null;
                    str = null;
                    str2 = null;
                }
                return new EmailAddressParsedResult(strArr, strArr2, strArr3, str, str2);
            } catch (UnsupportedEncodingException e2) {
                throw new IllegalStateException(e2);
            }
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}
