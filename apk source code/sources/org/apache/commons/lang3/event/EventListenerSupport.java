package org.apache.commons.lang3.event;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.apache.commons.lang3.Validate;

/* loaded from: classes.dex */
public class EventListenerSupport<L> implements Serializable {
    public static final long serialVersionUID = 3593265990380473632L;
    public List<L> a;
    public transient L b;
    public transient L[] c;

    public class ProxyInvocationHandler implements InvocationHandler {
        public ProxyInvocationHandler() {
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            Iterator<L> it = EventListenerSupport.this.a.iterator();
            while (it.hasNext()) {
                method.invoke(it.next(), objArr);
            }
            return null;
        }
    }

    public EventListenerSupport(Class<L> cls) {
        this(cls, Thread.currentThread().getContextClassLoader());
    }

    public static <T> EventListenerSupport<T> create(Class<T> cls) {
        return new EventListenerSupport<>(cls);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        Object[] objArr = (Object[]) objectInputStream.readObject();
        this.a = new CopyOnWriteArrayList(objArr);
        a(objArr.getClass().getComponentType(), Thread.currentThread().getContextClassLoader());
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        ArrayList arrayList = new ArrayList();
        ObjectOutputStream objectOutputStream2 = new ObjectOutputStream(new ByteArrayOutputStream());
        for (L l : this.a) {
            try {
                objectOutputStream2.writeObject(l);
                arrayList.add(l);
            } catch (IOException unused) {
                objectOutputStream2 = new ObjectOutputStream(new ByteArrayOutputStream());
            }
        }
        objectOutputStream.writeObject(arrayList.toArray(this.c));
    }

    public final void a(Class<L> cls, ClassLoader classLoader) {
        this.c = (L[]) ((Object[]) Array.newInstance((Class<?>) cls, 0));
        this.b = cls.cast(Proxy.newProxyInstance(classLoader, new Class[]{cls}, createInvocationHandler()));
    }

    public void addListener(L l) {
        addListener(l, true);
    }

    public InvocationHandler createInvocationHandler() {
        return new ProxyInvocationHandler();
    }

    public L fire() {
        return this.b;
    }

    public L[] getListeners() {
        return (L[]) this.a.toArray(this.c);
    }

    public void removeListener(L l) {
        Validate.notNull(l, "Listener object cannot be null.", new Object[0]);
        this.a.remove(l);
    }

    public EventListenerSupport() {
        this.a = new CopyOnWriteArrayList();
    }

    public void addListener(L l, boolean z) {
        Validate.notNull(l, "Listener object cannot be null.", new Object[0]);
        if (z) {
            this.a.add(l);
        } else {
            if (this.a.contains(l)) {
                return;
            }
            this.a.add(l);
        }
    }

    public EventListenerSupport(Class<L> cls, ClassLoader classLoader) {
        this.a = new CopyOnWriteArrayList();
        Validate.notNull(cls, "Listener interface cannot be null.", new Object[0]);
        Validate.notNull(classLoader, "ClassLoader cannot be null.", new Object[0]);
        Validate.isTrue(cls.isInterface(), "Class {0} is not an interface", cls.getName());
        a(cls, classLoader);
    }
}
