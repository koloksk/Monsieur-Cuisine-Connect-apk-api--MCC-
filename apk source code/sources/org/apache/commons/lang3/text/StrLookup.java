package org.apache.commons.lang3.text;

import java.util.Map;

@Deprecated
/* loaded from: classes.dex */
public abstract class StrLookup<V> {
    public static final StrLookup<String> a = new b(null);
    public static final StrLookup<String> b = new c(null);

    public static class b<V> extends StrLookup<V> {
        public final Map<String, V> c;

        public b(Map<String, V> map) {
            this.c = map;
        }

        @Override // org.apache.commons.lang3.text.StrLookup
        public String lookup(String str) {
            V v;
            Map<String, V> map = this.c;
            if (map == null || (v = map.get(str)) == null) {
                return null;
            }
            return v.toString();
        }
    }

    public static class c extends StrLookup<String> {
        public /* synthetic */ c(a aVar) {
        }

        @Override // org.apache.commons.lang3.text.StrLookup
        public String lookup(String str) {
            if (str.isEmpty()) {
                return null;
            }
            try {
                return System.getProperty(str);
            } catch (SecurityException unused) {
                return null;
            }
        }
    }

    public static <V> StrLookup<V> mapLookup(Map<String, V> map) {
        return new b(map);
    }

    public static StrLookup<?> noneLookup() {
        return a;
    }

    public static StrLookup<String> systemPropertiesLookup() {
        return b;
    }

    public abstract String lookup(String str);
}
