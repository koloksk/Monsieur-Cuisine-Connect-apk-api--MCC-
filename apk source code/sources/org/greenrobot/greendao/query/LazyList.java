package org.greenrobot.greendao.query;

import android.database.Cursor;
import defpackage.g9;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.concurrent.locks.ReentrantLock;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.InternalQueryDaoAccess;

/* loaded from: classes.dex */
public class LazyList<E> implements List<E>, Closeable {
    public final InternalQueryDaoAccess<E> a;
    public final Cursor b;
    public final List<E> c;
    public final int d;
    public final ReentrantLock e;
    public volatile int f;

    public class LazyIterator implements CloseableListIterator<E> {
        public int a;
        public final boolean b;

        public LazyIterator(int i, boolean z) {
            this.a = i;
            this.b = z;
        }

        @Override // java.util.ListIterator
        public void add(E e) {
            throw new UnsupportedOperationException();
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            LazyList.this.close();
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public boolean hasNext() {
            return this.a < LazyList.this.d;
        }

        @Override // java.util.ListIterator
        public boolean hasPrevious() {
            return this.a > 0;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public E next() {
            int i = this.a;
            LazyList lazyList = LazyList.this;
            if (i >= lazyList.d) {
                throw new NoSuchElementException();
            }
            E e = (E) lazyList.get(i);
            int i2 = this.a + 1;
            this.a = i2;
            if (i2 == LazyList.this.d && this.b) {
                close();
            }
            return e;
        }

        @Override // java.util.ListIterator
        public int nextIndex() {
            return this.a;
        }

        @Override // java.util.ListIterator
        public E previous() {
            int i = this.a;
            if (i <= 0) {
                throw new NoSuchElementException();
            }
            int i2 = i - 1;
            this.a = i2;
            return (E) LazyList.this.get(i2);
        }

        @Override // java.util.ListIterator
        public int previousIndex() {
            return this.a - 1;
        }

        @Override // java.util.ListIterator, java.util.Iterator
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override // java.util.ListIterator
        public void set(E e) {
            throw new UnsupportedOperationException();
        }
    }

    public LazyList(InternalQueryDaoAccess<E> internalQueryDaoAccess, Cursor cursor, boolean z) {
        this.b = cursor;
        this.a = internalQueryDaoAccess;
        this.d = cursor.getCount();
        if (z) {
            this.c = new ArrayList(this.d);
            for (int i = 0; i < this.d; i++) {
                this.c.add(null);
            }
        } else {
            this.c = null;
        }
        if (this.d == 0) {
            cursor.close();
        }
        this.e = new ReentrantLock();
    }

    @Override // java.util.List, java.util.Collection
    public boolean add(E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean addAll(Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    public void checkCached() {
        if (this.c == null) {
            throw new DaoException("This operation only works with cached lazy lists");
        }
    }

    @Override // java.util.List, java.util.Collection
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.b.close();
    }

    @Override // java.util.List, java.util.Collection
    public boolean contains(Object obj) {
        loadRemaining();
        return this.c.contains(obj);
    }

    @Override // java.util.List, java.util.Collection
    public boolean containsAll(Collection<?> collection) {
        loadRemaining();
        return this.c.containsAll(collection);
    }

    @Override // java.util.List
    public E get(int i) {
        List<E> list = this.c;
        if (list == null) {
            this.e.lock();
            try {
                return loadEntity(i);
            } finally {
            }
        }
        E eLoadEntity = list.get(i);
        if (eLoadEntity == null) {
            this.e.lock();
            try {
                eLoadEntity = this.c.get(i);
                if (eLoadEntity == null) {
                    eLoadEntity = loadEntity(i);
                    this.c.set(i, eLoadEntity);
                    this.f++;
                    if (this.f == this.d) {
                        this.b.close();
                    }
                }
            } finally {
            }
        }
        return eLoadEntity;
    }

    public int getLoadedCount() {
        return this.f;
    }

    @Override // java.util.List
    public int indexOf(Object obj) {
        loadRemaining();
        return this.c.indexOf(obj);
    }

    public boolean isClosed() {
        return this.b.isClosed();
    }

    @Override // java.util.List, java.util.Collection
    public boolean isEmpty() {
        return this.d == 0;
    }

    public boolean isLoadedCompletely() {
        return this.f == this.d;
    }

    @Override // java.util.List, java.util.Collection, java.lang.Iterable
    public Iterator<E> iterator() {
        return new LazyIterator(0, false);
    }

    @Override // java.util.List
    public int lastIndexOf(Object obj) {
        loadRemaining();
        return this.c.lastIndexOf(obj);
    }

    public CloseableListIterator<E> listIteratorAutoClose() {
        return new LazyIterator(0, true);
    }

    public E loadEntity(int i) {
        if (!this.b.moveToPosition(i)) {
            throw new DaoException(g9.b("Could not move to cursor location ", i));
        }
        E eLoadCurrent = this.a.loadCurrent(this.b, 0, true);
        if (eLoadCurrent != null) {
            return eLoadCurrent;
        }
        throw new DaoException(g9.b("Loading of entity failed (null) at position ", i));
    }

    public void loadRemaining() {
        checkCached();
        int size = this.c.size();
        for (int i = 0; i < size; i++) {
            get(i);
        }
    }

    public E peek(int i) {
        List<E> list = this.c;
        if (list != null) {
            return list.get(i);
        }
        return null;
    }

    @Override // java.util.List
    public E remove(int i) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean removeAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public boolean retainAll(Collection<?> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public E set(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List, java.util.Collection
    public int size() {
        return this.d;
    }

    @Override // java.util.List
    public List<E> subList(int i, int i2) {
        checkCached();
        for (int i3 = i; i3 < i2; i3++) {
            get(i3);
        }
        return this.c.subList(i, i2);
    }

    @Override // java.util.List, java.util.Collection
    public Object[] toArray() {
        loadRemaining();
        return this.c.toArray();
    }

    @Override // java.util.List
    public void add(int i, E e) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public boolean addAll(int i, Collection<? extends E> collection) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public CloseableListIterator<E> listIterator() {
        return new LazyIterator(0, false);
    }

    @Override // java.util.List, java.util.Collection
    public boolean remove(Object obj) {
        throw new UnsupportedOperationException();
    }

    @Override // java.util.List
    public ListIterator<E> listIterator(int i) {
        return new LazyIterator(i, false);
    }

    @Override // java.util.List, java.util.Collection
    public <T> T[] toArray(T[] tArr) {
        loadRemaining();
        return (T[]) this.c.toArray(tArr);
    }
}
