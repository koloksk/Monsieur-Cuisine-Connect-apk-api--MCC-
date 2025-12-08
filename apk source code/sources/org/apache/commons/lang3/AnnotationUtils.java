package org.apache.commons.lang3;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/* loaded from: classes.dex */
public class AnnotationUtils {
    public static final ToStringStyle a = new a();

    public static class a extends ToStringStyle {
        public static final long serialVersionUID = 1;

        public a() {
            setDefaultFullDetail(true);
            setArrayContentDetail(true);
            setUseClassName(true);
            setUseShortClassName(true);
            setUseIdentityHashCode(false);
            setContentStart("(");
            setContentEnd(")");
            setFieldSeparator(", ");
            setArrayStart("[");
            setArrayEnd("]");
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        public void appendDetail(StringBuffer stringBuffer, String str, Object obj) throws SecurityException {
            if (obj instanceof Annotation) {
                obj = AnnotationUtils.toString((Annotation) obj);
            }
            super.appendDetail(stringBuffer, str, obj);
        }

        @Override // org.apache.commons.lang3.builder.ToStringStyle
        public String getShortClassName(Class<?> cls) {
            Class<?> next;
            Iterator<Class<?>> it = ClassUtils.getAllInterfaces(cls).iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                if (Annotation.class.isAssignableFrom(next)) {
                    break;
                }
            }
            return new StringBuilder(next == null ? "" : next.getName()).insert(0, '@').toString();
        }
    }

    public static int a(String str, Object obj) {
        int iHashCode = str.hashCode() * 127;
        if (!obj.getClass().isArray()) {
            return iHashCode ^ (obj instanceof Annotation ? hashCode((Annotation) obj) : obj.hashCode());
        }
        Class<?> componentType = obj.getClass().getComponentType();
        return iHashCode ^ (componentType.equals(Byte.TYPE) ? Arrays.hashCode((byte[]) obj) : componentType.equals(Short.TYPE) ? Arrays.hashCode((short[]) obj) : componentType.equals(Integer.TYPE) ? Arrays.hashCode((int[]) obj) : componentType.equals(Character.TYPE) ? Arrays.hashCode((char[]) obj) : componentType.equals(Long.TYPE) ? Arrays.hashCode((long[]) obj) : componentType.equals(Float.TYPE) ? Arrays.hashCode((float[]) obj) : componentType.equals(Double.TYPE) ? Arrays.hashCode((double[]) obj) : componentType.equals(Boolean.TYPE) ? Arrays.hashCode((boolean[]) obj) : Arrays.hashCode((Object[]) obj));
    }

    public static boolean equals(Annotation annotation, Annotation annotation2) throws SecurityException {
        if (annotation == annotation2) {
            return true;
        }
        if (annotation != null && annotation2 != null) {
            Class<? extends Annotation> clsAnnotationType = annotation.annotationType();
            Class<? extends Annotation> clsAnnotationType2 = annotation2.annotationType();
            Validate.notNull(clsAnnotationType, "Annotation %s with null annotationType()", annotation);
            Validate.notNull(clsAnnotationType2, "Annotation %s with null annotationType()", annotation2);
            if (!clsAnnotationType.equals(clsAnnotationType2)) {
                return false;
            }
            try {
                for (Method method : clsAnnotationType.getDeclaredMethods()) {
                    if (method.getParameterTypes().length == 0 && isValidAnnotationMemberType(method.getReturnType()) && !a(method.getReturnType(), method.invoke(annotation, new Object[0]), method.invoke(annotation2, new Object[0]))) {
                        return false;
                    }
                }
                return true;
            } catch (IllegalAccessException | InvocationTargetException unused) {
            }
        }
        return false;
    }

    public static int hashCode(Annotation annotation) throws IllegalAccessException, SecurityException, IllegalArgumentException, InvocationTargetException {
        int iA = 0;
        for (Method method : annotation.annotationType().getDeclaredMethods()) {
            try {
                Object objInvoke = method.invoke(annotation, new Object[0]);
                if (objInvoke == null) {
                    throw new IllegalStateException(String.format("Annotation method %s returned null", method));
                }
                iA += a(method.getName(), objInvoke);
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception e2) {
                throw new RuntimeException(e2);
            }
        }
        return iA;
    }

    public static boolean isValidAnnotationMemberType(Class<?> cls) {
        if (cls == null) {
            return false;
        }
        if (cls.isArray()) {
            cls = cls.getComponentType();
        }
        return cls.isPrimitive() || cls.isEnum() || cls.isAnnotation() || String.class.equals(cls) || Class.class.equals(cls);
    }

    public static String toString(Annotation annotation) throws SecurityException {
        ToStringBuilder toStringBuilder = new ToStringBuilder(annotation, a);
        for (Method method : annotation.annotationType().getDeclaredMethods()) {
            if (method.getParameterTypes().length <= 0) {
                try {
                    toStringBuilder.append(method.getName(), method.invoke(annotation, new Object[0]));
                } catch (RuntimeException e) {
                    throw e;
                } catch (Exception e2) {
                    throw new RuntimeException(e2);
                }
            }
        }
        return toStringBuilder.build();
    }

    public static boolean a(Class<?> cls, Object obj, Object obj2) {
        if (obj == obj2) {
            return true;
        }
        if (obj == null || obj2 == null) {
            return false;
        }
        if (cls.isArray()) {
            Class<?> componentType = cls.getComponentType();
            if (componentType.isAnnotation()) {
                return a((Annotation[]) obj, (Annotation[]) obj2);
            }
            if (componentType.equals(Byte.TYPE)) {
                return Arrays.equals((byte[]) obj, (byte[]) obj2);
            }
            if (componentType.equals(Short.TYPE)) {
                return Arrays.equals((short[]) obj, (short[]) obj2);
            }
            if (componentType.equals(Integer.TYPE)) {
                return Arrays.equals((int[]) obj, (int[]) obj2);
            }
            if (componentType.equals(Character.TYPE)) {
                return Arrays.equals((char[]) obj, (char[]) obj2);
            }
            if (componentType.equals(Long.TYPE)) {
                return Arrays.equals((long[]) obj, (long[]) obj2);
            }
            if (componentType.equals(Float.TYPE)) {
                return Arrays.equals((float[]) obj, (float[]) obj2);
            }
            if (componentType.equals(Double.TYPE)) {
                return Arrays.equals((double[]) obj, (double[]) obj2);
            }
            if (componentType.equals(Boolean.TYPE)) {
                return Arrays.equals((boolean[]) obj, (boolean[]) obj2);
            }
            return Arrays.equals((Object[]) obj, (Object[]) obj2);
        }
        if (cls.isAnnotation()) {
            return equals((Annotation) obj, (Annotation) obj2);
        }
        return obj.equals(obj2);
    }

    public static boolean a(Annotation[] annotationArr, Annotation[] annotationArr2) {
        if (annotationArr.length != annotationArr2.length) {
            return false;
        }
        for (int i = 0; i < annotationArr.length; i++) {
            if (!equals(annotationArr[i], annotationArr2[i])) {
                return false;
            }
        }
        return true;
    }
}
