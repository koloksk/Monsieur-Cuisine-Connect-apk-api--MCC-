package org.apache.commons.lang3.reflect;

import defpackage.g9;
import defpackage.lo;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.Validate;

/* loaded from: classes.dex */
public class ConstructorUtils {
    public static <T> Constructor<T> getAccessibleConstructor(Class<T> cls, Class<?>... clsArr) {
        Validate.notNull(cls, "class cannot be null", new Object[0]);
        try {
            return getAccessibleConstructor(cls.getConstructor(clsArr));
        } catch (NoSuchMethodException unused) {
            return null;
        }
    }

    public static <T> Constructor<T> getMatchingAccessibleConstructor(Class<T> cls, Class<?>... clsArr) throws NoSuchMethodException, SecurityException {
        Constructor<T> accessibleConstructor;
        Validate.notNull(cls, "class cannot be null", new Object[0]);
        try {
            Constructor<T> constructor = cls.getConstructor(clsArr);
            lo.a((AccessibleObject) constructor);
            return constructor;
        } catch (NoSuchMethodException unused) {
            Constructor<T> constructor2 = null;
            for (Constructor<?> constructor3 : cls.getConstructors()) {
                if (lo.a(constructor3, clsArr) && (accessibleConstructor = getAccessibleConstructor(constructor3)) != null) {
                    lo.a((AccessibleObject) accessibleConstructor);
                    if (constructor2 == null || lo.a(new lo.a((Constructor<?>) accessibleConstructor), new lo.a((Constructor<?>) constructor2), clsArr) < 0) {
                        constructor2 = accessibleConstructor;
                    }
                }
            }
            return constructor2;
        }
    }

    public static <T> T invokeConstructor(Class<T> cls, Object... objArr) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Object[] objArrNullToEmpty = ArrayUtils.nullToEmpty(objArr);
        return (T) invokeConstructor(cls, objArrNullToEmpty, ClassUtils.toClass(objArrNullToEmpty));
    }

    public static <T> T invokeExactConstructor(Class<T> cls, Object... objArr) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Object[] objArrNullToEmpty = ArrayUtils.nullToEmpty(objArr);
        return (T) invokeExactConstructor(cls, objArrNullToEmpty, ClassUtils.toClass(objArrNullToEmpty));
    }

    public static <T> Constructor<T> getAccessibleConstructor(Constructor<T> constructor) {
        boolean z = false;
        Validate.notNull(constructor, "constructor cannot be null", new Object[0]);
        if (lo.a((Member) constructor)) {
            Class declaringClass = constructor.getDeclaringClass();
            while (true) {
                if (declaringClass == null) {
                    z = true;
                    break;
                }
                if (!Modifier.isPublic(declaringClass.getModifiers())) {
                    break;
                }
                declaringClass = declaringClass.getEnclosingClass();
            }
            if (z) {
                return constructor;
            }
        }
        return null;
    }

    public static <T> T invokeConstructor(Class<T> cls, Object[] objArr, Class<?>[] clsArr) throws IllegalAccessException, NoSuchMethodException, InstantiationException, SecurityException, NegativeArraySizeException, InvocationTargetException {
        Object[] objArrNullToEmpty = ArrayUtils.nullToEmpty(objArr);
        Constructor matchingAccessibleConstructor = getMatchingAccessibleConstructor(cls, ArrayUtils.nullToEmpty(clsArr));
        if (matchingAccessibleConstructor != null) {
            if (matchingAccessibleConstructor.isVarArgs()) {
                objArrNullToEmpty = MethodUtils.a(objArrNullToEmpty, matchingAccessibleConstructor.getParameterTypes());
            }
            return (T) matchingAccessibleConstructor.newInstance(objArrNullToEmpty);
        }
        StringBuilder sbA = g9.a("No such accessible constructor on object: ");
        sbA.append(cls.getName());
        throw new NoSuchMethodException(sbA.toString());
    }

    public static <T> T invokeExactConstructor(Class<T> cls, Object[] objArr, Class<?>[] clsArr) throws IllegalAccessException, NoSuchMethodException, InstantiationException, InvocationTargetException {
        Object[] objArrNullToEmpty = ArrayUtils.nullToEmpty(objArr);
        Constructor accessibleConstructor = getAccessibleConstructor(cls, ArrayUtils.nullToEmpty(clsArr));
        if (accessibleConstructor != null) {
            return (T) accessibleConstructor.newInstance(objArrNullToEmpty);
        }
        StringBuilder sbA = g9.a("No such accessible constructor on object: ");
        sbA.append(cls.getName());
        throw new NoSuchMethodException(sbA.toString());
    }
}
