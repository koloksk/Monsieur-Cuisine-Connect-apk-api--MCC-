package com.google.zxing.client.result;

import com.google.zxing.Result;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class URIResultParser extends ResultParser {
    public static final Pattern e = Pattern.compile("[a-zA-Z][a-zA-Z0-9+-.]+:");
    public static final Pattern f = Pattern.compile("([a-zA-Z0-9\\-]+\\.){1,6}[a-zA-Z]{2,}(:\\d{1,5})?(/|\\?|$)");

    public static boolean b(String str) {
        if (str.contains(StringUtils.SPACE)) {
            return false;
        }
        Matcher matcher = e.matcher(str);
        if (matcher.find() && matcher.start() == 0) {
            return true;
        }
        Matcher matcher2 = f.matcher(str);
        return matcher2.find() && matcher2.start() == 0;
    }

    @Override // com.google.zxing.client.result.ResultParser
    public URIParsedResult parse(Result result) {
        String massagedText = ResultParser.getMassagedText(result);
        if (massagedText.startsWith("URL:") || massagedText.startsWith("URI:")) {
            return new URIParsedResult(massagedText.substring(4).trim(), null);
        }
        String strTrim = massagedText.trim();
        if (b(strTrim)) {
            return new URIParsedResult(strTrim, null);
        }
        return null;
    }
}
