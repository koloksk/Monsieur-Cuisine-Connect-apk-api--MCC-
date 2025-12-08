package org.apache.commons.lang3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamClass;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes.dex */
public class SerializationUtils {

    public static class a extends ObjectInputStream {
        public static final Map<String, Class<?>> b;
        public final ClassLoader a;

        static {
            HashMap map = new HashMap();
            b = map;
            map.put("byte", Byte.TYPE);
            b.put("short", Short.TYPE);
            b.put("int", Integer.TYPE);
            b.put("long", Long.TYPE);
            b.put("float", Float.TYPE);
            b.put("double", Double.TYPE);
            b.put("boolean", Boolean.TYPE);
            b.put("char", Character.TYPE);
            b.put("void", Void.TYPE);
        }

        public a(InputStream inputStream, ClassLoader classLoader) throws IOException {
            super(inputStream);
            this.a = classLoader;
        }

        @Override // java.io.ObjectInputStream
        public Class<?> resolveClass(ObjectStreamClass objectStreamClass) throws ClassNotFoundException, IOException {
            String name = objectStreamClass.getName();
            try {
                try {
                    return Class.forName(name, false, this.a);
                } catch (ClassNotFoundException unused) {
                    return Class.forName(name, false, Thread.currentThread().getContextClassLoader());
                }
            } catch (ClassNotFoundException e) {
                Class<?> cls = b.get(name);
                if (cls != null) {
                    return cls;
                }
                throw e;
            }
        }
    }

    public static <T extends Serializable> T clone(T t) throws IOException {
        if (t == null) {
            return null;
        }
        try {
            a aVar = new a(new ByteArrayInputStream(serialize(t)), t.getClass().getClassLoader());
            try {
                T t2 = (T) aVar.readObject();
                aVar.close();
                return t2;
            } catch (Throwable th) {
                try {
                    throw th;
                } catch (Throwable th2) {
                    try {
                        aVar.close();
                    } catch (Throwable th3) {
                        th.addSuppressed(th3);
                    }
                    throw th2;
                }
            }
        } catch (IOException e) {
            throw new SerializationException("IOException while reading or closing cloned object data", e);
        } catch (ClassNotFoundException e2) {
            throw new SerializationException("ClassNotFoundException while reading cloned object data", e2);
        }
    }

    public static <T> T deserialize(InputStream inputStream) throws IOException {
        Validate.isTrue(inputStream != null, "The InputStream must not be null", new Object[0]);
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            try {
                T t = (T) objectInputStream.readObject();
                objectInputStream.close();
                return t;
            } finally {
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new SerializationException(e);
        }
    }

    public static <T extends Serializable> T roundtrip(T t) {
        return (T) deserialize(serialize(t));
    }

    public static void serialize(Serializable serializable, OutputStream outputStream) throws IOException {
        Validate.isTrue(outputStream != null, "The OutputStream must not be null", new Object[0]);
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            try {
                objectOutputStream.writeObject(serializable);
                objectOutputStream.close();
            } finally {
            }
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }

    public static <T> T deserialize(byte[] bArr) {
        Validate.isTrue(bArr != null, "The byte[] must not be null", new Object[0]);
        return (T) deserialize(new ByteArrayInputStream(bArr));
    }

    public static byte[] serialize(Serializable serializable) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(512);
        serialize(serializable, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
