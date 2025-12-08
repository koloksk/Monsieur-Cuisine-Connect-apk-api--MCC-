package com.google.zxing.client.android;

import android.content.Intent;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.android.Intents;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class DecodeFormatManager {
    public static final Set<BarcodeFormat> d;
    public static final Map<String, Set<BarcodeFormat>> i;
    public static final Pattern a = Pattern.compile(",");
    public static final Set<BarcodeFormat> e = EnumSet.of(BarcodeFormat.QR_CODE);
    public static final Set<BarcodeFormat> f = EnumSet.of(BarcodeFormat.DATA_MATRIX);
    public static final Set<BarcodeFormat> g = EnumSet.of(BarcodeFormat.AZTEC);
    public static final Set<BarcodeFormat> h = EnumSet.of(BarcodeFormat.PDF_417);
    public static final Set<BarcodeFormat> b = EnumSet.of(BarcodeFormat.UPC_A, BarcodeFormat.UPC_E, BarcodeFormat.EAN_13, BarcodeFormat.EAN_8, BarcodeFormat.RSS_14, BarcodeFormat.RSS_EXPANDED);
    public static final Set<BarcodeFormat> c = EnumSet.of(BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.CODE_128, BarcodeFormat.ITF, BarcodeFormat.CODABAR);

    static {
        EnumSet enumSetCopyOf = EnumSet.copyOf((Collection) b);
        d = enumSetCopyOf;
        enumSetCopyOf.addAll(c);
        HashMap map = new HashMap();
        i = map;
        map.put(Intents.Scan.ONE_D_MODE, d);
        i.put(Intents.Scan.PRODUCT_MODE, b);
        i.put(Intents.Scan.QR_CODE_MODE, e);
        i.put(Intents.Scan.DATA_MATRIX_MODE, f);
        i.put(Intents.Scan.AZTEC_MODE, g);
        i.put(Intents.Scan.PDF417_MODE, h);
    }

    public static Set<BarcodeFormat> parseDecodeFormats(Intent intent) {
        String stringExtra = intent.getStringExtra(Intents.Scan.FORMATS);
        List listAsList = stringExtra != null ? Arrays.asList(a.split(stringExtra)) : null;
        String stringExtra2 = intent.getStringExtra(Intents.Scan.MODE);
        if (listAsList != null) {
            EnumSet enumSetNoneOf = EnumSet.noneOf(BarcodeFormat.class);
            try {
                Iterator it = listAsList.iterator();
                while (it.hasNext()) {
                    enumSetNoneOf.add(BarcodeFormat.valueOf((String) it.next()));
                }
                return enumSetNoneOf;
            } catch (IllegalArgumentException unused) {
            }
        }
        if (stringExtra2 != null) {
            return i.get(stringExtra2);
        }
        return null;
    }
}
