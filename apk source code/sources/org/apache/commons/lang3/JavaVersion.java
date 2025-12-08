package org.apache.commons.lang3;

import org.apache.commons.lang3.math.NumberUtils;

/* loaded from: classes.dex */
public enum JavaVersion {
    JAVA_0_9(1.5f, "0.9"),
    JAVA_1_1(1.1f, "1.1"),
    JAVA_1_2(1.2f, "1.2"),
    JAVA_1_3(1.3f, "1.3"),
    JAVA_1_4(1.4f, "1.4"),
    JAVA_1_5(1.5f, "1.5"),
    JAVA_1_6(1.6f, "1.6"),
    JAVA_1_7(1.7f, "1.7"),
    JAVA_1_8(1.8f, "1.8"),
    JAVA_1_9(9.0f, "9"),
    JAVA_9(9.0f, "9"),
    JAVA_10(10.0f, "10"),
    JAVA_11(11.0f, "11"),
    JAVA_RECENT(a(), Float.toString(a()));

    public final float a;
    public final String b;

    JavaVersion(float f, String str) {
        this.a = f;
        this.b = str;
    }

    public static JavaVersion a(String str) {
        if ("0.9".equals(str)) {
            return JAVA_0_9;
        }
        if ("1.1".equals(str)) {
            return JAVA_1_1;
        }
        if ("1.2".equals(str)) {
            return JAVA_1_2;
        }
        if ("1.3".equals(str)) {
            return JAVA_1_3;
        }
        if ("1.4".equals(str)) {
            return JAVA_1_4;
        }
        if ("1.5".equals(str)) {
            return JAVA_1_5;
        }
        if ("1.6".equals(str)) {
            return JAVA_1_6;
        }
        if ("1.7".equals(str)) {
            return JAVA_1_7;
        }
        if ("1.8".equals(str)) {
            return JAVA_1_8;
        }
        if ("9".equals(str)) {
            return JAVA_9;
        }
        if ("10".equals(str)) {
            return JAVA_10;
        }
        if ("11".equals(str)) {
            return JAVA_11;
        }
        if (str == null) {
            return null;
        }
        float fB = b(str);
        if (fB - 1.0d < 1.0d) {
            int iMax = Math.max(str.indexOf(46), str.indexOf(44));
            if (Float.parseFloat(str.substring(iMax + 1, Math.max(str.length(), str.indexOf(44, iMax)))) > 0.9f) {
                return JAVA_RECENT;
            }
        } else if (fB > 10.0f) {
            return JAVA_RECENT;
        }
        return null;
    }

    public static float b(String str) {
        if (!str.contains(".")) {
            return NumberUtils.toFloat(str, -1.0f);
        }
        String[] strArrSplit = str.split("\\.");
        if (strArrSplit.length < 2) {
            return -1.0f;
        }
        return NumberUtils.toFloat(strArrSplit[0] + ClassUtils.PACKAGE_SEPARATOR_CHAR + strArrSplit[1], -1.0f);
    }

    public boolean atLeast(JavaVersion javaVersion) {
        return this.a >= javaVersion.a;
    }

    @Override // java.lang.Enum
    public String toString() {
        return this.b;
    }

    public static float a() {
        float fB = b(System.getProperty("java.specification.version", "99.0"));
        if (fB > 0.0f) {
            return fB;
        }
        return 99.0f;
    }
}
