package org.apache.commons.lang3;

import android.support.media.ExifInterface;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.mutable.MutableObject;

/* loaded from: classes.dex */
public class ClassUtils {
    public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
    public static final Map<String, Class<?>> a;
    public static final Map<Class<?>, Class<?>> b;
    public static final Map<Class<?>, Class<?>> c;
    public static final Map<String, String> d;
    public static final Map<String, String> e;
    public static final char PACKAGE_SEPARATOR_CHAR = '.';
    public static final String PACKAGE_SEPARATOR = String.valueOf(PACKAGE_SEPARATOR_CHAR);
    public static final String INNER_CLASS_SEPARATOR = String.valueOf('$');

    public enum Interfaces {
        INCLUDE,
        EXCLUDE
    }

    public static class a implements Iterable<Class<?>> {
        public final /* synthetic */ Class a;

        /* renamed from: org.apache.commons.lang3.ClassUtils$a$a, reason: collision with other inner class name */
        public class C0077a implements Iterator<Class<?>> {
            public final /* synthetic */ MutableObject a;

            public C0077a(a aVar, MutableObject mutableObject) {
                this.a = mutableObject;
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.a.getValue2() != null;
            }

            @Override // java.util.Iterator
            public Class<?> next() {
                Class<?> cls = (Class) this.a.getValue2();
                this.a.setValue(cls.getSuperclass());
                return cls;
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        public a(Class cls) {
            this.a = cls;
        }

        @Override // java.lang.Iterable
        public Iterator<Class<?>> iterator() {
            return new C0077a(this, new MutableObject(this.a));
        }
    }

    public static class b implements Iterable<Class<?>> {
        public final /* synthetic */ Iterable a;

        public class a implements Iterator<Class<?>> {
            public Iterator<Class<?>> a = Collections.emptySet().iterator();
            public final /* synthetic */ Iterator b;
            public final /* synthetic */ Set c;

            public a(b bVar, Iterator it, Set set) {
                this.b = it;
                this.c = set;
            }

            public final void a(Set<Class<?>> set, Class<?> cls) {
                for (Class<?> cls2 : cls.getInterfaces()) {
                    if (!this.c.contains(cls2)) {
                        set.add(cls2);
                    }
                    a(set, cls2);
                }
            }

            @Override // java.util.Iterator
            public boolean hasNext() {
                return this.a.hasNext() || this.b.hasNext();
            }

            @Override // java.util.Iterator
            public Class<?> next() {
                if (this.a.hasNext()) {
                    Class<?> next = this.a.next();
                    this.c.add(next);
                    return next;
                }
                Class<?> cls = (Class) this.b.next();
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                a(linkedHashSet, cls);
                this.a = linkedHashSet.iterator();
                return cls;
            }

            @Override // java.util.Iterator
            public void remove() {
                throw new UnsupportedOperationException();
            }
        }

        public b(Iterable iterable) {
            this.a = iterable;
        }

        @Override // java.lang.Iterable
        public Iterator<Class<?>> iterator() {
            return new a(this, this.a.iterator(), new HashSet());
        }
    }

    static {
        HashMap map = new HashMap();
        a = map;
        map.put("boolean", Boolean.TYPE);
        a.put("byte", Byte.TYPE);
        a.put("char", Character.TYPE);
        a.put("short", Short.TYPE);
        a.put("int", Integer.TYPE);
        a.put("long", Long.TYPE);
        a.put("double", Double.TYPE);
        a.put("float", Float.TYPE);
        a.put("void", Void.TYPE);
        HashMap map2 = new HashMap();
        b = map2;
        map2.put(Boolean.TYPE, Boolean.class);
        b.put(Byte.TYPE, Byte.class);
        b.put(Character.TYPE, Character.class);
        b.put(Short.TYPE, Short.class);
        b.put(Integer.TYPE, Integer.class);
        b.put(Long.TYPE, Long.class);
        b.put(Double.TYPE, Double.class);
        b.put(Float.TYPE, Float.class);
        Map<Class<?>, Class<?>> map3 = b;
        Class<?> cls = Void.TYPE;
        map3.put(cls, cls);
        c = new HashMap();
        for (Map.Entry<Class<?>, Class<?>> entry : b.entrySet()) {
            Class<?> key = entry.getKey();
            Class<?> value = entry.getValue();
            if (!key.equals(value)) {
                c.put(value, key);
            }
        }
        HashMap map4 = new HashMap();
        map4.put("int", "I");
        map4.put("boolean", "Z");
        map4.put("float", "F");
        map4.put("long", "J");
        map4.put("short", ExifInterface.LATITUDE_SOUTH);
        map4.put("byte", "B");
        map4.put("double", "D");
        map4.put("char", "C");
        HashMap map5 = new HashMap();
        for (Map.Entry entry2 : map4.entrySet()) {
            map5.put(entry2.getValue(), entry2.getKey());
        }
        d = Collections.unmodifiableMap(map4);
        e = Collections.unmodifiableMap(map5);
    }

    public static void a(Class<?> cls, HashSet<Class<?>> hashSet) {
        while (cls != null) {
            for (Class<?> cls2 : cls.getInterfaces()) {
                if (hashSet.add(cls2)) {
                    a(cls2, hashSet);
                }
            }
            cls = cls.getSuperclass();
        }
    }

    public static String b(String str) {
        String strDeleteWhitespace = StringUtils.deleteWhitespace(str);
        Validate.notNull(strDeleteWhitespace, "className must not be null.", new Object[0]);
        if (!strDeleteWhitespace.endsWith("[]")) {
            return strDeleteWhitespace;
        }
        StringBuilder sb = new StringBuilder();
        while (strDeleteWhitespace.endsWith("[]")) {
            strDeleteWhitespace = strDeleteWhitespace.substring(0, strDeleteWhitespace.length() - 2);
            sb.append("[");
        }
        String str2 = d.get(strDeleteWhitespace);
        if (str2 != null) {
            sb.append(str2);
        } else {
            sb.append("L");
            sb.append(strDeleteWhitespace);
            sb.append(";");
        }
        return sb.toString();
    }

    public static List<Class<?>> convertClassNamesToClasses(List<String> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            try {
                arrayList.add(Class.forName(it.next()));
            } catch (Exception unused) {
                arrayList.add(null);
            }
        }
        return arrayList;
    }

    public static List<String> convertClassesToClassNames(List<Class<?>> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (Class<?> cls : list) {
            if (cls == null) {
                arrayList.add(null);
            } else {
                arrayList.add(cls.getName());
            }
        }
        return arrayList;
    }

    public static String getAbbreviatedName(Class<?> cls, int i) {
        return cls == null ? "" : getAbbreviatedName(cls.getName(), i);
    }

    public static List<Class<?>> getAllInterfaces(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        a(cls, linkedHashSet);
        return new ArrayList(linkedHashSet);
    }

    public static List<Class<?>> getAllSuperclasses(Class<?> cls) {
        if (cls == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        for (Class<? super Object> superclass = cls.getSuperclass(); superclass != null; superclass = superclass.getSuperclass()) {
            arrayList.add(superclass);
        }
        return arrayList;
    }

    public static String getCanonicalName(Class<?> cls) {
        return getCanonicalName(cls, "");
    }

    public static Class<?> getClass(ClassLoader classLoader, String str, boolean z) throws ClassNotFoundException {
        try {
            return a.containsKey(str) ? a.get(str) : Class.forName(b(str), z, classLoader);
        } catch (ClassNotFoundException e2) {
            int iLastIndexOf = str.lastIndexOf(46);
            if (iLastIndexOf != -1) {
                try {
                    return getClass(classLoader, str.substring(0, iLastIndexOf) + '$' + str.substring(iLastIndexOf + 1), z);
                } catch (ClassNotFoundException unused) {
                    throw e2;
                }
            }
            throw e2;
        }
    }

    public static String getName(Class<?> cls) {
        return getName(cls, "");
    }

    public static String getPackageCanonicalName(Object obj, String str) {
        return obj == null ? str : getPackageCanonicalName(obj.getClass().getName());
    }

    public static String getPackageName(Object obj, String str) {
        return obj == null ? str : getPackageName(obj.getClass());
    }

    public static Method getPublicMethod(Class<?> cls, String str, Class<?>... clsArr) throws NoSuchMethodException, SecurityException {
        Method method = cls.getMethod(str, clsArr);
        if (Modifier.isPublic(method.getDeclaringClass().getModifiers())) {
            return method;
        }
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(getAllInterfaces(cls));
        arrayList.addAll(getAllSuperclasses(cls));
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Class cls2 = (Class) it.next();
            if (Modifier.isPublic(cls2.getModifiers())) {
                try {
                    Method method2 = cls2.getMethod(str, clsArr);
                    if (Modifier.isPublic(method2.getDeclaringClass().getModifiers())) {
                        return method2;
                    }
                } catch (NoSuchMethodException unused) {
                    continue;
                }
            }
        }
        throw new NoSuchMethodException("Can't find a public method for " + str + StringUtils.SPACE + ArrayUtils.toString(clsArr));
    }

    public static String getShortCanonicalName(Object obj, String str) {
        return obj == null ? str : getShortCanonicalName(obj.getClass().getName());
    }

    public static String getShortClassName(Object obj, String str) {
        return obj == null ? str : getShortClassName(obj.getClass());
    }

    public static String getSimpleName(Class<?> cls) {
        return getSimpleName(cls, "");
    }

    public static Iterable<Class<?>> hierarchy(Class<?> cls) {
        return hierarchy(cls, Interfaces.EXCLUDE);
    }

    public static boolean isAssignable(Class<?>[] clsArr, Class<?>... clsArr2) {
        return isAssignable(clsArr, clsArr2, true);
    }

    public static boolean isInnerClass(Class<?> cls) {
        return (cls == null || cls.getEnclosingClass() == null) ? false : true;
    }

    public static boolean isPrimitiveOrWrapper(Class<?> cls) {
        if (cls == null) {
            return false;
        }
        return cls.isPrimitive() || isPrimitiveWrapper(cls);
    }

    public static boolean isPrimitiveWrapper(Class<?> cls) {
        return c.containsKey(cls);
    }

    public static Class<?> primitiveToWrapper(Class<?> cls) {
        return (cls == null || !cls.isPrimitive()) ? cls : b.get(cls);
    }

    public static Class<?>[] primitivesToWrappers(Class<?>... clsArr) {
        if (clsArr == null) {
            return null;
        }
        if (clsArr.length == 0) {
            return clsArr;
        }
        Class<?>[] clsArr2 = new Class[clsArr.length];
        for (int i = 0; i < clsArr.length; i++) {
            clsArr2[i] = primitiveToWrapper(clsArr[i]);
        }
        return clsArr2;
    }

    public static Class<?>[] toClass(Object... objArr) {
        if (objArr == null) {
            return null;
        }
        if (objArr.length == 0) {
            return ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        Class<?>[] clsArr = new Class[objArr.length];
        for (int i = 0; i < objArr.length; i++) {
            clsArr[i] = objArr[i] == null ? null : objArr[i].getClass();
        }
        return clsArr;
    }

    public static Class<?> wrapperToPrimitive(Class<?> cls) {
        return c.get(cls);
    }

    public static Class<?>[] wrappersToPrimitives(Class<?>... clsArr) {
        if (clsArr == null) {
            return null;
        }
        if (clsArr.length == 0) {
            return clsArr;
        }
        Class<?>[] clsArr2 = new Class[clsArr.length];
        for (int i = 0; i < clsArr.length; i++) {
            clsArr2[i] = wrapperToPrimitive(clsArr[i]);
        }
        return clsArr2;
    }

    public static String getAbbreviatedName(String str, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("len must be > 0");
        }
        if (str == null) {
            return "";
        }
        int iCountMatches = StringUtils.countMatches(str, PACKAGE_SEPARATOR_CHAR);
        String[] strArr = new String[iCountMatches + 1];
        int length = str.length() - 1;
        for (int i2 = iCountMatches; i2 >= 0; i2--) {
            int iLastIndexOf = str.lastIndexOf(46, length);
            String strSubstring = str.substring(iLastIndexOf + 1, length + 1);
            i -= strSubstring.length();
            if (i2 > 0) {
                i--;
            }
            if (i2 == iCountMatches) {
                strArr[i2] = strSubstring;
            } else if (i > 0) {
                strArr[i2] = strSubstring;
            } else {
                strArr[i2] = strSubstring.substring(0, 1);
            }
            length = iLastIndexOf - 1;
        }
        return StringUtils.join(strArr, PACKAGE_SEPARATOR_CHAR);
    }

    public static String getCanonicalName(Class<?> cls, String str) {
        String canonicalName;
        return (cls == null || (canonicalName = cls.getCanonicalName()) == null) ? str : canonicalName;
    }

    public static String getName(Class<?> cls, String str) {
        return cls == null ? str : cls.getName();
    }

    public static String getPackageCanonicalName(Class<?> cls) {
        return cls == null ? "" : getPackageCanonicalName(cls.getName());
    }

    public static String getPackageName(Class<?> cls) {
        return cls == null ? "" : getPackageName(cls.getName());
    }

    public static String getShortCanonicalName(Class<?> cls) {
        return cls == null ? "" : getShortCanonicalName(cls.getName());
    }

    public static String getShortClassName(Class<?> cls) {
        return cls == null ? "" : getShortClassName(cls.getName());
    }

    public static String getSimpleName(Class<?> cls, String str) {
        return cls == null ? str : cls.getSimpleName();
    }

    public static Iterable<Class<?>> hierarchy(Class<?> cls, Interfaces interfaces) {
        a aVar = new a(cls);
        return interfaces != Interfaces.INCLUDE ? aVar : new b(aVar);
    }

    public static boolean isAssignable(Class<?>[] clsArr, Class<?>[] clsArr2, boolean z) {
        if (!ArrayUtils.isSameLength(clsArr, clsArr2)) {
            return false;
        }
        if (clsArr == null) {
            clsArr = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        if (clsArr2 == null) {
            clsArr2 = ArrayUtils.EMPTY_CLASS_ARRAY;
        }
        for (int i = 0; i < clsArr.length; i++) {
            if (!isAssignable(clsArr[i], clsArr2[i], z)) {
                return false;
            }
        }
        return true;
    }

    public static String getCanonicalName(Object obj) {
        return getCanonicalName(obj, "");
    }

    public static String getName(Object obj) {
        return getName(obj, "");
    }

    public static String getPackageCanonicalName(String str) {
        return getPackageName(a(str));
    }

    public static String getPackageName(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        while (str.charAt(0) == '[') {
            str = str.substring(1);
        }
        if (str.charAt(0) == 'L' && str.charAt(str.length() - 1) == ';') {
            str = str.substring(1);
        }
        int iLastIndexOf = str.lastIndexOf(46);
        return iLastIndexOf == -1 ? "" : str.substring(0, iLastIndexOf);
    }

    public static String getShortCanonicalName(String str) {
        return getShortClassName(a(str));
    }

    public static String getShortClassName(String str) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        if (str.startsWith("[")) {
            while (str.charAt(0) == '[') {
                str = str.substring(1);
                sb.append("[]");
            }
            if (str.charAt(0) == 'L' && str.charAt(str.length() - 1) == ';') {
                str = str.substring(1, str.length() - 1);
            }
            if (e.containsKey(str)) {
                str = e.get(str);
            }
        }
        int iLastIndexOf = str.lastIndexOf(46);
        int iIndexOf = str.indexOf(36, iLastIndexOf != -1 ? iLastIndexOf + 1 : 0);
        String strSubstring = str.substring(iLastIndexOf + 1);
        if (iIndexOf != -1) {
            strSubstring = strSubstring.replace('$', PACKAGE_SEPARATOR_CHAR);
        }
        return strSubstring + ((Object) sb);
    }

    public static String getSimpleName(Object obj) {
        return getSimpleName(obj, "");
    }

    public static String getCanonicalName(Object obj, String str) {
        String canonicalName;
        return (obj == null || (canonicalName = obj.getClass().getCanonicalName()) == null) ? str : canonicalName;
    }

    public static String getName(Object obj, String str) {
        return obj == null ? str : obj.getClass().getName();
    }

    public static String getSimpleName(Object obj, String str) {
        return obj == null ? str : obj.getClass().getSimpleName();
    }

    public static String a(String str) {
        int length;
        String strDeleteWhitespace = StringUtils.deleteWhitespace(str);
        if (strDeleteWhitespace == null) {
            return null;
        }
        int i = 0;
        while (strDeleteWhitespace.startsWith("[")) {
            i++;
            strDeleteWhitespace = strDeleteWhitespace.substring(1);
        }
        if (i < 1) {
            return strDeleteWhitespace;
        }
        if (strDeleteWhitespace.startsWith("L")) {
            if (strDeleteWhitespace.endsWith(";")) {
                length = strDeleteWhitespace.length() - 1;
            } else {
                length = strDeleteWhitespace.length();
            }
            strDeleteWhitespace = strDeleteWhitespace.substring(1, length);
        } else if (!strDeleteWhitespace.isEmpty()) {
            strDeleteWhitespace = e.get(strDeleteWhitespace.substring(0, 1));
        }
        StringBuilder sb = new StringBuilder(strDeleteWhitespace);
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("[]");
        }
        return sb.toString();
    }

    public static boolean isAssignable(Class<?> cls, Class<?> cls2) {
        return isAssignable(cls, cls2, true);
    }

    public static boolean isAssignable(Class<?> cls, Class<?> cls2, boolean z) {
        if (cls2 == null) {
            return false;
        }
        if (cls == null) {
            return !cls2.isPrimitive();
        }
        if (z) {
            if (cls.isPrimitive() && !cls2.isPrimitive() && (cls = primitiveToWrapper(cls)) == null) {
                return false;
            }
            if (cls2.isPrimitive() && !cls.isPrimitive() && (cls = wrapperToPrimitive(cls)) == null) {
                return false;
            }
        }
        if (cls.equals(cls2)) {
            return true;
        }
        if (cls.isPrimitive()) {
            if (!cls2.isPrimitive()) {
                return false;
            }
            if (Integer.TYPE.equals(cls)) {
                return Long.TYPE.equals(cls2) || Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
            }
            if (Long.TYPE.equals(cls)) {
                return Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
            }
            if (Boolean.TYPE.equals(cls) || Double.TYPE.equals(cls)) {
                return false;
            }
            if (Float.TYPE.equals(cls)) {
                return Double.TYPE.equals(cls2);
            }
            if (Character.TYPE.equals(cls)) {
                return Integer.TYPE.equals(cls2) || Long.TYPE.equals(cls2) || Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
            }
            if (Short.TYPE.equals(cls)) {
                return Integer.TYPE.equals(cls2) || Long.TYPE.equals(cls2) || Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
            }
            if (Byte.TYPE.equals(cls)) {
                return Short.TYPE.equals(cls2) || Integer.TYPE.equals(cls2) || Long.TYPE.equals(cls2) || Float.TYPE.equals(cls2) || Double.TYPE.equals(cls2);
            }
            return false;
        }
        return cls2.isAssignableFrom(cls);
    }

    public static Class<?> getClass(ClassLoader classLoader, String str) throws ClassNotFoundException {
        return getClass(classLoader, str, true);
    }

    public static Class<?> getClass(String str) throws ClassNotFoundException {
        return getClass(str, true);
    }

    public static Class<?> getClass(String str, boolean z) throws ClassNotFoundException {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        if (contextClassLoader == null) {
            contextClassLoader = ClassUtils.class.getClassLoader();
        }
        return getClass(contextClassLoader, str, z);
    }
}
