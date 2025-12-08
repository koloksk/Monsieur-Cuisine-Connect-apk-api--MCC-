package com.google.gson.internal;

import defpackage.g9;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/* loaded from: classes.dex */
public abstract class UnsafeAllocator {

    public static class a extends UnsafeAllocator {
        public final /* synthetic */ Method a;
        public final /* synthetic */ Object b;

        public a(Method method, Object obj) {
            this.a = method;
            this.b = obj;
        }

        @Override // com.google.gson.internal.UnsafeAllocator
        public <T> T newInstance(Class<T> cls) throws Exception {
            UnsafeAllocator.a(cls);
            return (T) this.a.invoke(this.b, cls);
        }
    }

    public static class b extends UnsafeAllocator {
        public final /* synthetic */ Method a;
        public final /* synthetic */ int b;

        public b(Method method, int i) {
            this.a = method;
            this.b = i;
        }

        @Override // com.google.gson.internal.UnsafeAllocator
        public <T> T newInstance(Class<T> cls) throws Exception {
            UnsafeAllocator.a(cls);
            return (T) this.a.invoke(null, cls, Integer.valueOf(this.b));
        }
    }

    public static class c extends UnsafeAllocator {
        public final /* synthetic */ Method a;

        public c(Method method) {
            this.a = method;
        }

        @Override // com.google.gson.internal.UnsafeAllocator
        public <T> T newInstance(Class<T> cls) throws Exception {
            UnsafeAllocator.a(cls);
            return (T) this.a.invoke(null, cls, Object.class);
        }
    }

    public static class d extends UnsafeAllocator {
        @Override // com.google.gson.internal.UnsafeAllocator
        public <T> T newInstance(Class<T> cls) {
            throw new UnsupportedOperationException("Cannot allocate " + cls);
        }
    }

    public static void a(Class<?> cls) {
        int modifiers = cls.getModifiers();
        if (Modifier.isInterface(modifiers)) {
            StringBuilder sbA = g9.a("Interface can't be instantiated! Interface name: ");
            sbA.append(cls.getName());
            throw new UnsupportedOperationException(sbA.toString());
        }
        if (Modifier.isAbstract(modifiers)) {
            StringBuilder sbA2 = g9.a("Abstract class can't be instantiated! Class name: ");
            sbA2.append(cls.getName());
            throw new UnsupportedOperationException(sbA2.toString());
        }
    }

    public static UnsafeAllocator create() throws NoSuchFieldException, NoSuchMethodException, ClassNotFoundException, SecurityException {
        try {
            Class<?> cls = Class.forName("sun.misc.Unsafe");
            Field declaredField = cls.getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            return new a(cls.getMethod("allocateInstance", Class.class), declaredField.get(null));
        } catch (Exception unused) {
            try {
                try {
                    Method declaredMethod = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", Class.class);
                    declaredMethod.setAccessible(true);
                    int iIntValue = ((Integer) declaredMethod.invoke(null, Object.class)).intValue();
                    Method declaredMethod2 = ObjectStreamClass.class.getDeclaredMethod("newInstance", Class.class, Integer.TYPE);
                    declaredMethod2.setAccessible(true);
                    return new b(declaredMethod2, iIntValue);
                } catch (Exception unused2) {
                    Method declaredMethod3 = ObjectInputStream.class.getDeclaredMethod("newInstance", Class.class, Class.class);
                    declaredMethod3.setAccessible(true);
                    return new c(declaredMethod3);
                }
            } catch (Exception unused3) {
                return new d();
            }
        }
    }

    public abstract <T> T newInstance(Class<T> cls) throws Exception;
}
