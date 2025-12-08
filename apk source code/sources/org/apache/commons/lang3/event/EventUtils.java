package org.apache.commons.lang3.event;

import defpackage.g9;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.reflect.MethodUtils;

/* loaded from: classes.dex */
public class EventUtils {

    public static class a implements InvocationHandler {
        public final Object a;
        public final String b;
        public final Set<String> c;

        public a(Object obj, String str, String[] strArr) {
            this.a = obj;
            this.b = str;
            this.c = new HashSet(Arrays.asList(strArr));
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            if (this.c.isEmpty() || this.c.contains(method.getName())) {
                return MethodUtils.getAccessibleMethod(this.a.getClass(), this.b, method.getParameterTypes()) != null ? MethodUtils.invokeMethod(this.a, this.b, objArr) : MethodUtils.invokeMethod(this.a, this.b);
            }
            return null;
        }
    }

    public static <L> void addEventListener(Object obj, Class<L> cls, L l) {
        try {
            MethodUtils.invokeMethod(obj, "add" + cls.getSimpleName(), l);
        } catch (IllegalAccessException unused) {
            StringBuilder sbA = g9.a("Class ");
            sbA.append(obj.getClass().getName());
            sbA.append(" does not have an accessible add");
            sbA.append(cls.getSimpleName());
            sbA.append(" method which takes a parameter of type ");
            sbA.append(cls.getName());
            sbA.append(".");
            throw new IllegalArgumentException(sbA.toString());
        } catch (NoSuchMethodException unused2) {
            StringBuilder sbA2 = g9.a("Class ");
            sbA2.append(obj.getClass().getName());
            sbA2.append(" does not have a public add");
            sbA2.append(cls.getSimpleName());
            sbA2.append(" method which takes a parameter of type ");
            sbA2.append(cls.getName());
            sbA2.append(".");
            throw new IllegalArgumentException(sbA2.toString());
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Unable to add listener.", e.getCause());
        }
    }

    public static <L> void bindEventsToMethod(Object obj, String str, Object obj2, Class<L> cls, String... strArr) {
        addEventListener(obj2, cls, cls.cast(Proxy.newProxyInstance(obj.getClass().getClassLoader(), new Class[]{cls}, new a(obj, str, strArr))));
    }
}
