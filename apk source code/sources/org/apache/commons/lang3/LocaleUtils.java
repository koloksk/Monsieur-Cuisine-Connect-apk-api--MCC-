package org.apache.commons.lang3;

import defpackage.g9;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* loaded from: classes.dex */
public class LocaleUtils {
    public static final ConcurrentMap<String, List<Locale>> a = new ConcurrentHashMap();
    public static final ConcurrentMap<String, List<Locale>> b = new ConcurrentHashMap();

    public static class a {
        public static final List<Locale> a;
        public static final Set<Locale> b;

        static {
            ArrayList arrayList = new ArrayList(Arrays.asList(Locale.getAvailableLocales()));
            a = Collections.unmodifiableList(arrayList);
            b = Collections.unmodifiableSet(new HashSet(arrayList));
        }
    }

    public static boolean a(String str) {
        return StringUtils.isAllUpperCase(str) && str.length() == 2;
    }

    public static List<Locale> availableLocaleList() {
        return a.a;
    }

    public static Set<Locale> availableLocaleSet() {
        return a.b;
    }

    public static boolean b(String str) {
        return StringUtils.isAllLowerCase(str) && (str.length() == 2 || str.length() == 3);
    }

    public static boolean c(String str) {
        return StringUtils.isNumeric(str) && str.length() == 3;
    }

    public static List<Locale> countriesByLanguage(String str) {
        if (str == null) {
            return Collections.emptyList();
        }
        List<Locale> list = b.get(str);
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (Locale locale : availableLocaleList()) {
            if (str.equals(locale.getLanguage()) && !locale.getCountry().isEmpty() && locale.getVariant().isEmpty()) {
                arrayList.add(locale);
            }
        }
        b.putIfAbsent(str, Collections.unmodifiableList(arrayList));
        return b.get(str);
    }

    public static boolean isAvailableLocale(Locale locale) {
        return availableLocaleList().contains(locale);
    }

    public static List<Locale> languagesByCountry(String str) {
        if (str == null) {
            return Collections.emptyList();
        }
        List<Locale> list = a.get(str);
        if (list != null) {
            return list;
        }
        ArrayList arrayList = new ArrayList();
        for (Locale locale : availableLocaleList()) {
            if (str.equals(locale.getCountry()) && locale.getVariant().isEmpty()) {
                arrayList.add(locale);
            }
        }
        a.putIfAbsent(str, Collections.unmodifiableList(arrayList));
        return a.get(str);
    }

    public static List<Locale> localeLookupList(Locale locale) {
        return localeLookupList(locale, locale);
    }

    public static Locale toLocale(String str) {
        Locale locale;
        if (str == null) {
            return null;
        }
        if (str.isEmpty()) {
            return new Locale("", "");
        }
        if (str.contains("#")) {
            throw new IllegalArgumentException(g9.b("Invalid locale format: ", str));
        }
        int length = str.length();
        if (length < 2) {
            throw new IllegalArgumentException(g9.b("Invalid locale format: ", str));
        }
        if (str.charAt(0) == '_') {
            if (length < 3) {
                throw new IllegalArgumentException(g9.b("Invalid locale format: ", str));
            }
            char cCharAt = str.charAt(1);
            char cCharAt2 = str.charAt(2);
            if (!Character.isUpperCase(cCharAt) || !Character.isUpperCase(cCharAt2)) {
                throw new IllegalArgumentException(g9.b("Invalid locale format: ", str));
            }
            if (length == 3) {
                return new Locale("", str.substring(1, 3));
            }
            if (length < 5) {
                throw new IllegalArgumentException(g9.b("Invalid locale format: ", str));
            }
            if (str.charAt(3) == '_') {
                return new Locale("", str.substring(1, 3), str.substring(4));
            }
            throw new IllegalArgumentException(g9.b("Invalid locale format: ", str));
        }
        if (b(str)) {
            return new Locale(str);
        }
        String[] strArrSplit = str.split("_", -1);
        String str2 = strArrSplit[0];
        if (strArrSplit.length == 2) {
            String str3 = strArrSplit[1];
            if ((b(str2) && a(str3)) || c(str3)) {
                locale = new Locale(str2, str3);
                return locale;
            }
            throw new IllegalArgumentException(g9.b("Invalid locale format: ", str));
        }
        if (strArrSplit.length == 3) {
            String str4 = strArrSplit[1];
            String str5 = strArrSplit[2];
            if (b(str2) && ((str4.isEmpty() || a(str4) || c(str4)) && !str5.isEmpty())) {
                locale = new Locale(str2, str4, str5);
                return locale;
            }
        }
        throw new IllegalArgumentException(g9.b("Invalid locale format: ", str));
    }

    public static List<Locale> localeLookupList(Locale locale, Locale locale2) {
        ArrayList arrayList = new ArrayList(4);
        if (locale != null) {
            arrayList.add(locale);
            if (!locale.getVariant().isEmpty()) {
                arrayList.add(new Locale(locale.getLanguage(), locale.getCountry()));
            }
            if (!locale.getCountry().isEmpty()) {
                arrayList.add(new Locale(locale.getLanguage(), ""));
            }
            if (!arrayList.contains(locale2)) {
                arrayList.add(locale2);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }
}
