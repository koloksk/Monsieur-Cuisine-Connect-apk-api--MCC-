package com.google.gson;

import java.lang.reflect.Field;
import java.util.Locale;
import org.apache.commons.lang3.StringUtils;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public abstract class FieldNamingPolicy implements FieldNamingStrategy {
    public static final FieldNamingPolicy LOWER_CASE_WITH_DOTS;
    public static final /* synthetic */ FieldNamingPolicy[] a;
    public static final FieldNamingPolicy IDENTITY = new a("IDENTITY", 0);
    public static final FieldNamingPolicy UPPER_CAMEL_CASE = new FieldNamingPolicy("UPPER_CAMEL_CASE", 1) { // from class: com.google.gson.FieldNamingPolicy.b
        {
            a aVar = null;
        }

        @Override // com.google.gson.FieldNamingStrategy
        public String translateName(Field field) {
            return FieldNamingPolicy.a(field.getName());
        }
    };
    public static final FieldNamingPolicy UPPER_CAMEL_CASE_WITH_SPACES = new FieldNamingPolicy("UPPER_CAMEL_CASE_WITH_SPACES", 2) { // from class: com.google.gson.FieldNamingPolicy.c
        {
            a aVar = null;
        }

        @Override // com.google.gson.FieldNamingStrategy
        public String translateName(Field field) {
            return FieldNamingPolicy.a(FieldNamingPolicy.a(field.getName(), StringUtils.SPACE));
        }
    };
    public static final FieldNamingPolicy LOWER_CASE_WITH_UNDERSCORES = new FieldNamingPolicy("LOWER_CASE_WITH_UNDERSCORES", 3) { // from class: com.google.gson.FieldNamingPolicy.d
        {
            a aVar = null;
        }

        @Override // com.google.gson.FieldNamingStrategy
        public String translateName(Field field) {
            return FieldNamingPolicy.a(field.getName(), "_").toLowerCase(Locale.ENGLISH);
        }
    };
    public static final FieldNamingPolicy LOWER_CASE_WITH_DASHES = new FieldNamingPolicy("LOWER_CASE_WITH_DASHES", 4) { // from class: com.google.gson.FieldNamingPolicy.e
        {
            a aVar = null;
        }

        @Override // com.google.gson.FieldNamingStrategy
        public String translateName(Field field) {
            return FieldNamingPolicy.a(field.getName(), "-").toLowerCase(Locale.ENGLISH);
        }
    };

    public enum a extends FieldNamingPolicy {
        public a(String str, int i) {
            super(str, i, null);
        }

        @Override // com.google.gson.FieldNamingStrategy
        public String translateName(Field field) {
            return field.getName();
        }
    }

    static {
        FieldNamingPolicy fieldNamingPolicy = new FieldNamingPolicy("LOWER_CASE_WITH_DOTS", 5) { // from class: com.google.gson.FieldNamingPolicy.f
            {
                a aVar = null;
            }

            @Override // com.google.gson.FieldNamingStrategy
            public String translateName(Field field) {
                return FieldNamingPolicy.a(field.getName(), ".").toLowerCase(Locale.ENGLISH);
            }
        };
        LOWER_CASE_WITH_DOTS = fieldNamingPolicy;
        a = new FieldNamingPolicy[]{IDENTITY, UPPER_CAMEL_CASE, UPPER_CAMEL_CASE_WITH_SPACES, LOWER_CASE_WITH_UNDERSCORES, LOWER_CASE_WITH_DASHES, fieldNamingPolicy};
    }

    public /* synthetic */ FieldNamingPolicy(String str, int i, a aVar) {
    }

    public static String a(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char cCharAt = str.charAt(i);
            if (Character.isUpperCase(cCharAt) && sb.length() != 0) {
                sb.append(str2);
            }
            sb.append(cCharAt);
        }
        return sb.toString();
    }

    public static FieldNamingPolicy valueOf(String str) {
        return (FieldNamingPolicy) Enum.valueOf(FieldNamingPolicy.class, str);
    }

    public static FieldNamingPolicy[] values() {
        return (FieldNamingPolicy[]) a.clone();
    }

    public static String a(String str) {
        String strValueOf;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        char cCharAt = str.charAt(0);
        int length = str.length();
        while (i < length - 1 && !Character.isLetter(cCharAt)) {
            sb.append(cCharAt);
            i++;
            cCharAt = str.charAt(i);
        }
        if (Character.isUpperCase(cCharAt)) {
            return str;
        }
        char upperCase = Character.toUpperCase(cCharAt);
        int i2 = i + 1;
        if (i2 < str.length()) {
            strValueOf = upperCase + str.substring(i2);
        } else {
            strValueOf = String.valueOf(upperCase);
        }
        sb.append(strValueOf);
        return sb.toString();
    }
}
