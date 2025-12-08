package com.google.zxing.client.result;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.CharEncoding;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class VCardResultParser extends ResultParser {
    public static final Pattern e = Pattern.compile("BEGIN:VCARD", 2);
    public static final Pattern f = Pattern.compile("\\d{4}-?\\d{2}-?\\d{2}");
    public static final Pattern g = Pattern.compile("\r\n[ \t]");
    public static final Pattern h = Pattern.compile("\\\\[nN]");
    public static final Pattern i = Pattern.compile("\\\\([,;\\\\])");
    public static final Pattern j = Pattern.compile("=");
    public static final Pattern k = Pattern.compile(";");
    public static final Pattern l = Pattern.compile("(?<!\\\\);+");
    public static final Pattern m = Pattern.compile(",");
    public static final Pattern n = Pattern.compile("[;,]");

    public static void a(ByteArrayOutputStream byteArrayOutputStream, String str, StringBuilder sb) {
        String str2;
        if (byteArrayOutputStream.size() > 0) {
            byte[] byteArray = byteArrayOutputStream.toByteArray();
            if (str == null) {
                str2 = new String(byteArray, Charset.forName(CharEncoding.UTF_8));
            } else {
                try {
                    str2 = new String(byteArray, str);
                } catch (UnsupportedEncodingException unused) {
                    str2 = new String(byteArray, Charset.forName(CharEncoding.UTF_8));
                }
            }
            byteArrayOutputStream.reset();
            sb.append(str2);
        }
    }

    public static List<List<String>> b(CharSequence charSequence, String str, boolean z, boolean z2) {
        ArrayList arrayList;
        int i2;
        String str2;
        char c;
        int iIndexOf;
        int i3;
        String strReplaceAll;
        char cCharAt;
        int length = str.length();
        int i4 = 0;
        int i5 = 0;
        ArrayList arrayList2 = null;
        while (i5 < length) {
            Matcher matcher = Pattern.compile("(?:^|\n)" + ((Object) charSequence) + "(?:;([^:]*))?:", 2).matcher(str);
            if (i5 > 0) {
                i5--;
            }
            if (!matcher.find(i5)) {
                break;
            }
            int iEnd = matcher.end(i4);
            String strGroup = matcher.group(1);
            if (strGroup != null) {
                String[] strArrSplit = k.split(strGroup);
                int length2 = strArrSplit.length;
                int i6 = i4;
                i2 = i6;
                arrayList = null;
                str2 = null;
                while (i6 < length2) {
                    String str3 = strArrSplit[i6];
                    if (arrayList == null) {
                        arrayList = new ArrayList(1);
                    }
                    arrayList.add(str3);
                    String[] strArrSplit2 = j.split(str3, 2);
                    if (strArrSplit2.length > 1) {
                        String str4 = strArrSplit2[i4];
                        String str5 = strArrSplit2[1];
                        if ("ENCODING".equalsIgnoreCase(str4) && "QUOTED-PRINTABLE".equalsIgnoreCase(str5)) {
                            i2 = 1;
                        } else if ("CHARSET".equalsIgnoreCase(str4)) {
                            str2 = str5;
                        }
                    }
                    i6++;
                    i4 = 0;
                }
            } else {
                arrayList = null;
                i2 = 0;
                str2 = null;
            }
            int i7 = iEnd;
            while (true) {
                c = '\n';
                iIndexOf = str.indexOf(10, i7);
                if (iIndexOf < 0) {
                    break;
                }
                if (iIndexOf < str.length() - 1) {
                    int i8 = iIndexOf + 1;
                    if (str.charAt(i8) == ' ' || str.charAt(i8) == '\t') {
                        i7 = iIndexOf + 2;
                    }
                }
                if (i2 == 0 || ((iIndexOf <= 0 || str.charAt(iIndexOf - 1) != '=') && (iIndexOf < 2 || str.charAt(iIndexOf - 2) != '='))) {
                    break;
                }
                i7 = iIndexOf + 1;
            }
            if (iIndexOf < 0) {
                i5 = length;
                i4 = 0;
            } else if (iIndexOf <= iEnd) {
                i3 = 0;
                i4 = i3;
                i5 = iIndexOf + 1;
            } else {
                if (arrayList2 == null) {
                    arrayList2 = new ArrayList(1);
                }
                if (iIndexOf > 0 && str.charAt(iIndexOf - 1) == '\r') {
                    iIndexOf--;
                }
                String strSubstring = str.substring(iEnd, iIndexOf);
                if (z) {
                    strSubstring = strSubstring.trim();
                }
                if (i2 != 0) {
                    int length3 = strSubstring.length();
                    StringBuilder sb = new StringBuilder(length3);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    int i9 = 0;
                    while (i9 < length3) {
                        char cCharAt2 = strSubstring.charAt(i9);
                        if (cCharAt2 != c && cCharAt2 != '\r') {
                            if (cCharAt2 != '=') {
                                a(byteArrayOutputStream, str2, sb);
                                sb.append(cCharAt2);
                            } else if (i9 < length3 - 2 && (cCharAt = strSubstring.charAt(i9 + 1)) != '\r') {
                                c = '\n';
                                if (cCharAt != '\n') {
                                    i9 += 2;
                                    char cCharAt3 = strSubstring.charAt(i9);
                                    int hexDigit = ResultParser.parseHexDigit(cCharAt);
                                    int hexDigit2 = ResultParser.parseHexDigit(cCharAt3);
                                    if (hexDigit >= 0 && hexDigit2 >= 0) {
                                        byteArrayOutputStream.write((hexDigit << 4) + hexDigit2);
                                    }
                                }
                            }
                            c = '\n';
                        }
                        i9++;
                    }
                    a(byteArrayOutputStream, str2, sb);
                    strReplaceAll = sb.toString();
                    if (z2) {
                        strReplaceAll = l.matcher(strReplaceAll).replaceAll(StringUtils.LF).trim();
                    }
                } else {
                    if (z2) {
                        strSubstring = l.matcher(strSubstring).replaceAll(StringUtils.LF).trim();
                    }
                    strReplaceAll = i.matcher(h.matcher(g.matcher(strSubstring).replaceAll("")).replaceAll(StringUtils.LF)).replaceAll("$1");
                }
                if (arrayList == null) {
                    ArrayList arrayList3 = new ArrayList(1);
                    arrayList3.add(strReplaceAll);
                    arrayList2.add(arrayList3);
                    i3 = 0;
                    i4 = i3;
                    i5 = iIndexOf + 1;
                } else {
                    i3 = 0;
                    arrayList.add(0, strReplaceAll);
                    arrayList2.add(arrayList);
                    i4 = i3;
                    i5 = iIndexOf + 1;
                }
            }
        }
        return arrayList2;
    }

    /* JADX WARN: Removed duplicated region for block: B:36:0x00e0  */
    @Override // com.google.zxing.client.result.ResultParser
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.google.zxing.client.result.AddressBookParsedResult parse(com.google.zxing.Result r25) {
        /*
            Method dump skipped, instructions count: 337
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.zxing.client.result.VCardResultParser.parse(com.google.zxing.Result):com.google.zxing.client.result.AddressBookParsedResult");
    }

    public static List<String> a(CharSequence charSequence, String str, boolean z, boolean z2) {
        List<List<String>> listB = b(charSequence, str, z, z2);
        if (listB == null || listB.isEmpty()) {
            return null;
        }
        return listB.get(0);
    }

    public static String a(List<String> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public static String[] a(Collection<List<String>> collection) {
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator<List<String>> it = collection.iterator();
        while (it.hasNext()) {
            String str = it.next().get(0);
            if (str != null && !str.isEmpty()) {
                arrayList.add(str);
            }
        }
        return (String[]) arrayList.toArray(new String[collection.size()]);
    }

    public static void a(String[] strArr, int i2, StringBuilder sb) {
        if (strArr[i2] == null || strArr[i2].isEmpty()) {
            return;
        }
        if (sb.length() > 0) {
            sb.append(' ');
        }
        sb.append(strArr[i2]);
    }

    public static String[] b(Collection<List<String>> collection) {
        String strSubstring;
        if (collection == null || collection.isEmpty()) {
            return null;
        }
        ArrayList arrayList = new ArrayList(collection.size());
        for (List<String> list : collection) {
            int i2 = 1;
            while (true) {
                if (i2 >= list.size()) {
                    strSubstring = null;
                    break;
                }
                strSubstring = list.get(i2);
                int iIndexOf = strSubstring.indexOf(61);
                if (iIndexOf >= 0) {
                    if ("TYPE".equalsIgnoreCase(strSubstring.substring(0, iIndexOf))) {
                        strSubstring = strSubstring.substring(iIndexOf + 1);
                        break;
                    }
                    i2++;
                }
            }
            arrayList.add(strSubstring);
        }
        return (String[]) arrayList.toArray(new String[collection.size()]);
    }
}
