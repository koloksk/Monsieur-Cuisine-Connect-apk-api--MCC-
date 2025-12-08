package com.google.zxing.client.result;

import com.google.zxing.Result;
import defpackage.ia;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class EmailDoCoMoResultParser extends ia {
    public static final Pattern e = Pattern.compile("[a-zA-Z0-9@.!#$%&'*+\\-/=?^_`{|}~]+");

    public static boolean b(String str) {
        return str != null && e.matcher(str).matches() && str.indexOf(64) >= 0;
    }

    @Override // com.google.zxing.client.result.ResultParser
    public EmailAddressParsedResult parse(Result result) {
        String[] strArrA;
        String massagedText = ResultParser.getMassagedText(result);
        if (!massagedText.startsWith("MATMSG:") || (strArrA = ia.a("TO:", massagedText, true)) == null) {
            return null;
        }
        for (String str : strArrA) {
            if (!b(str)) {
                return null;
            }
        }
        return new EmailAddressParsedResult(strArrA, null, null, ia.b("SUB:", massagedText, false), ia.b("BODY:", massagedText, false));
    }
}
