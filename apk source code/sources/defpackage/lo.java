package defpackage;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes.dex */
public abstract class lo {
    public static final Class<?>[] a = {Byte.TYPE, Short.TYPE, Character.TYPE, Integer.TYPE, Long.TYPE, Float.TYPE, Double.TYPE};

    /* JADX WARN: Multi-variable type inference failed */
    public static boolean a(AccessibleObject accessibleObject) throws SecurityException {
        if (accessibleObject != 0 && !accessibleObject.isAccessible()) {
            Member member = (Member) accessibleObject;
            if (!accessibleObject.isAccessible() && Modifier.isPublic(member.getModifiers())) {
                if ((member.getDeclaringClass().getModifiers() & 7) == 0) {
                    try {
                        accessibleObject.setAccessible(true);
                        return true;
                    } catch (SecurityException unused) {
                    }
                }
            }
        }
        return false;
    }

    public static final class a {
        public final Class<?>[] a;
        public final boolean b;

        public a(Method method) {
            this.a = method.getParameterTypes();
            this.b = method.isVarArgs();
        }

        public a(Constructor<?> constructor) {
            this.a = constructor.getParameterTypes();
            this.b = constructor.isVarArgs();
        }
    }

    public static boolean a(Member member) {
        return (member == null || !Modifier.isPublic(member.getModifiers()) || member.isSynthetic()) ? false : true;
    }

    public static int a(a aVar, a aVar2, Class<?>[] clsArr) {
        float fA = a(clsArr, aVar);
        float fA2 = a(clsArr, aVar2);
        if (fA < fA2) {
            return -1;
        }
        return fA2 < fA ? 1 : 0;
    }

    public static float a(Class<?> cls, Class<?> cls2) {
        float f = 0.0f;
        if (!cls2.isPrimitive()) {
            while (true) {
                if (cls != null && !cls2.equals(cls)) {
                    if (cls2.isInterface() && ClassUtils.isAssignable(cls, cls2)) {
                        f += 0.25f;
                        break;
                    }
                    f += 1.0f;
                    cls = cls.getSuperclass();
                } else {
                    break;
                }
            }
            return cls == null ? f + 1.5f : f;
        }
        if (!cls.isPrimitive()) {
            cls = ClassUtils.wrapperToPrimitive(cls);
            f = 0.1f;
        }
        int i = 0;
        while (cls != cls2) {
            Class<?>[] clsArr = a;
            if (i >= clsArr.length) {
                break;
            }
            if (cls == clsArr[i]) {
                f += 0.1f;
                if (i < clsArr.length - 1) {
                    cls = clsArr[i + 1];
                }
            }
            i++;
        }
        return f;
    }

    public static boolean a(Method method, Class<?>[] clsArr) {
        Class<?>[] parameterTypes = method.getParameterTypes();
        boolean zIsVarArgs = method.isVarArgs();
        if (ClassUtils.isAssignable(clsArr, parameterTypes, true)) {
            return true;
        }
        if (zIsVarArgs) {
            int i = 0;
            while (i < parameterTypes.length - 1 && i < clsArr.length) {
                if (!ClassUtils.isAssignable(clsArr[i], parameterTypes[i], true)) {
                    break;
                }
                i++;
            }
            Class<?> componentType = parameterTypes[parameterTypes.length - 1].getComponentType();
            while (i < clsArr.length) {
                if (ClassUtils.isAssignable(clsArr[i], componentType, true)) {
                    i++;
                }
            }
            return true;
        }
        return false;
    }

    public static boolean a(Constructor<?> constructor, Class<?>[] clsArr) {
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        boolean zIsVarArgs = constructor.isVarArgs();
        if (ClassUtils.isAssignable(clsArr, parameterTypes, true)) {
            return true;
        }
        if (zIsVarArgs) {
            int i = 0;
            while (i < parameterTypes.length - 1 && i < clsArr.length) {
                if (!ClassUtils.isAssignable(clsArr[i], parameterTypes[i], true)) {
                    break;
                }
                i++;
            }
            Class<?> componentType = parameterTypes[parameterTypes.length - 1].getComponentType();
            while (i < clsArr.length) {
                if (ClassUtils.isAssignable(clsArr[i], componentType, true)) {
                    i++;
                }
            }
            return true;
        }
        return false;
    }

    public static float a(Class<?>[] clsArr, a aVar) {
        float fA;
        Class<?>[] clsArr2 = aVar.a;
        boolean z = aVar.b;
        int length = clsArr2.length;
        if (z) {
            length--;
        }
        long j = length;
        if (clsArr.length < j) {
            return Float.MAX_VALUE;
        }
        boolean z2 = false;
        float fA2 = 0.0f;
        for (int i = 0; i < j; i++) {
            fA2 += a(clsArr[i], clsArr2[i]);
        }
        if (!z) {
            return fA2;
        }
        boolean z3 = clsArr.length < clsArr2.length;
        if (clsArr.length == clsArr2.length && clsArr[clsArr.length - 1].isArray()) {
            z2 = true;
        }
        Class<?> componentType = clsArr2[clsArr2.length - 1].getComponentType();
        if (z3) {
            fA = a(componentType, (Class<?>) Object.class);
        } else if (z2) {
            fA = a(clsArr[clsArr.length - 1].getComponentType(), componentType);
        } else {
            for (int length2 = clsArr2.length - 1; length2 < clsArr.length; length2++) {
                fA2 += a(clsArr[length2], componentType) + 0.001f;
            }
            return fA2;
        }
        return fA2 + fA + 0.001f;
    }
}
