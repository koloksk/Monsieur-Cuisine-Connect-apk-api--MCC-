package com.google.zxing.common;

import com.google.zxing.FormatException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.CharEncoding;

/* loaded from: classes.dex */
public enum CharacterSetECI {
    Cp437(new int[]{0, 2}, new String[0]),
    ISO8859_1(new int[]{1, 3}, CharEncoding.ISO_8859_1),
    ISO8859_2(4, "ISO-8859-2"),
    ISO8859_3(5, "ISO-8859-3"),
    ISO8859_4(6, "ISO-8859-4"),
    ISO8859_5(7, "ISO-8859-5"),
    ISO8859_6(8, "ISO-8859-6"),
    ISO8859_7(9, "ISO-8859-7"),
    ISO8859_8(10, "ISO-8859-8"),
    ISO8859_9(11, "ISO-8859-9"),
    ISO8859_10(12, "ISO-8859-10"),
    ISO8859_11(13, "ISO-8859-11"),
    ISO8859_13(15, "ISO-8859-13"),
    ISO8859_14(16, "ISO-8859-14"),
    ISO8859_15(17, "ISO-8859-15"),
    ISO8859_16(18, "ISO-8859-16"),
    SJIS(20, "Shift_JIS"),
    Cp1250(21, "windows-1250"),
    Cp1251(22, "windows-1251"),
    Cp1252(23, "windows-1252"),
    Cp1256(24, "windows-1256"),
    UnicodeBigUnmarked(25, CharEncoding.UTF_16BE, "UnicodeBig"),
    UTF8(26, CharEncoding.UTF_8),
    ASCII(new int[]{27, 170}, CharEncoding.US_ASCII),
    Big5(28),
    GB18030(29, StringUtils.GB2312, "EUC_CN", "GBK"),
    EUC_KR(30, "EUC-KR");

    public static final Map<Integer, CharacterSetECI> c = new HashMap();
    public static final Map<String, CharacterSetECI> d = new HashMap();
    public final int[] a;
    public final String[] b;

    static {
        for (CharacterSetECI characterSetECI : values()) {
            for (int i : characterSetECI.a) {
                c.put(Integer.valueOf(i), characterSetECI);
            }
            d.put(characterSetECI.name(), characterSetECI);
            for (String str : characterSetECI.b) {
                d.put(str, characterSetECI);
            }
        }
    }

    CharacterSetECI(int i, String... strArr) {
        this.a = new int[]{i};
        this.b = strArr;
    }

    public static CharacterSetECI getCharacterSetECIByName(String str) {
        return d.get(str);
    }

    public static CharacterSetECI getCharacterSetECIByValue(int i) throws FormatException {
        if (i < 0 || i >= 900) {
            throw FormatException.getFormatInstance();
        }
        return c.get(Integer.valueOf(i));
    }

    public int getValue() {
        return this.a[0];
    }

    CharacterSetECI(int i) {
        this.a = new int[]{i};
        this.b = new String[0];
    }

    CharacterSetECI(int[] iArr, String... strArr) {
        this.a = iArr;
        this.b = strArr;
    }
}
