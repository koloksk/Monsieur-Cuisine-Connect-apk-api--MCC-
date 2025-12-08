package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.commons.lang3.CharEncoding;

/* loaded from: classes.dex */
public abstract class ResultParser {
    public static final ResultParser[] a = {new BookmarkDoCoMoResultParser(), new AddressBookDoCoMoResultParser(), new EmailDoCoMoResultParser(), new AddressBookAUResultParser(), new VCardResultParser(), new BizcardResultParser(), new VEventResultParser(), new EmailAddressResultParser(), new SMTPResultParser(), new TelResultParser(), new SMSMMSResultParser(), new SMSTOMMSTOResultParser(), new GeoResultParser(), new WifiResultParser(), new URLTOResultParser(), new URIResultParser(), new ISBNResultParser(), new ProductResultParser(), new ExpandedProductResultParser(), new VINResultParser()};
    public static final Pattern b = Pattern.compile("\\d+");
    public static final Pattern c = Pattern.compile("&");
    public static final Pattern d = Pattern.compile("=");

    public static Map<String, String> a(String str) {
        int iIndexOf = str.indexOf(63);
        if (iIndexOf < 0) {
            return null;
        }
        HashMap map = new HashMap(3);
        for (String str2 : c.split(str.substring(iIndexOf + 1))) {
            String[] strArrSplit = d.split(str2, 2);
            if (strArrSplit.length == 2) {
                try {
                    try {
                        map.put(strArrSplit[0], URLDecoder.decode(strArrSplit[1], CharEncoding.UTF_8));
                    } catch (UnsupportedEncodingException e) {
                        throw new IllegalStateException(e);
                    }
                } catch (IllegalArgumentException unused) {
                    continue;
                }
            }
        }
        return map;
    }

    public static String b(String str, String str2, char c2, boolean z) {
        String[] strArrA = a(str, str2, c2, z);
        if (strArrA == null) {
            return null;
        }
        return strArrA[0];
    }

    public static String getMassagedText(Result result) {
        String text = result.getText();
        return text.startsWith("\ufeff") ? text.substring(1) : text;
    }

    public static boolean isStringOfDigits(CharSequence charSequence, int i) {
        return charSequence != null && i > 0 && i == charSequence.length() && b.matcher(charSequence).matches();
    }

    public static boolean isSubstringOfDigits(CharSequence charSequence, int i, int i2) {
        int i3;
        return charSequence != null && i2 > 0 && charSequence.length() >= (i3 = i2 + i) && b.matcher(charSequence.subSequence(i, i3)).matches();
    }

    public static void maybeAppend(String str, StringBuilder sb) {
        if (str != null) {
            sb.append('\n');
            sb.append(str);
        }
    }

    public static String[] maybeWrap(String str) {
        if (str == null) {
            return null;
        }
        return new String[]{str};
    }

    public static int parseHexDigit(char c2) {
        if (c2 >= '0' && c2 <= '9') {
            return c2 - '0';
        }
        char c3 = 'a';
        if (c2 < 'a' || c2 > 'f') {
            c3 = 'A';
            if (c2 < 'A' || c2 > 'F') {
                return -1;
            }
        }
        return (c2 - c3) + 10;
    }

    public static ParsedResult parseResult(Result result) {
        for (ResultParser resultParser : a) {
            ParsedResult parsedResult = resultParser.parse(result);
            if (parsedResult != null) {
                return parsedResult;
            }
        }
        return new TextParsedResult(result.getText(), null);
    }

    public static String unescapeBackslash(String str) {
        int iIndexOf = str.indexOf(92);
        if (iIndexOf < 0) {
            return str;
        }
        int length = str.length();
        StringBuilder sb = new StringBuilder(length - 1);
        sb.append(str.toCharArray(), 0, iIndexOf);
        boolean z = false;
        while (iIndexOf < length) {
            char cCharAt = str.charAt(iIndexOf);
            if (z || cCharAt != '\\') {
                sb.append(cCharAt);
                z = false;
            } else {
                z = true;
            }
            iIndexOf++;
        }
        return sb.toString();
    }

    public abstract ParsedResult parse(Result result);

    public static void maybeAppend(String[] strArr, StringBuilder sb) {
        if (strArr != null) {
            for (String str : strArr) {
                sb.append('\n');
                sb.append(str);
            }
        }
    }

    public static String[] a(String str, String str2, char c2, boolean z) {
        int length = str2.length();
        ArrayList arrayList = null;
        int i = 0;
        while (i < length) {
            int iIndexOf = str2.indexOf(str, i);
            if (iIndexOf < 0) {
                break;
            }
            int length2 = str.length() + iIndexOf;
            boolean z2 = true;
            ArrayList arrayList2 = arrayList;
            int length3 = length2;
            while (z2) {
                int iIndexOf2 = str2.indexOf(c2, length3);
                if (iIndexOf2 < 0) {
                    length3 = str2.length();
                } else {
                    int i2 = 0;
                    for (int i3 = iIndexOf2 - 1; i3 >= 0 && str2.charAt(i3) == '\\'; i3--) {
                        i2++;
                    }
                    if (i2 % 2 != 0) {
                        length3 = iIndexOf2 + 1;
                    } else {
                        if (arrayList2 == null) {
                            arrayList2 = new ArrayList(3);
                        }
                        String strUnescapeBackslash = unescapeBackslash(str2.substring(length2, iIndexOf2));
                        if (z) {
                            strUnescapeBackslash = strUnescapeBackslash.trim();
                        }
                        if (!strUnescapeBackslash.isEmpty()) {
                            arrayList2.add(strUnescapeBackslash);
                        }
                        length3 = iIndexOf2 + 1;
                    }
                }
                z2 = false;
            }
            i = length3;
            arrayList = arrayList2;
        }
        if (arrayList == null || arrayList.isEmpty()) {
            return null;
        }
        return (String[]) arrayList.toArray(new String[arrayList.size()]);
    }
}
