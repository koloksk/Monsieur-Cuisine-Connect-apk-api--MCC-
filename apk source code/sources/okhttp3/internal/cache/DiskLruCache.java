package okhttp3.internal.cache;

import defpackage.en;
import defpackage.fn;
import defpackage.g9;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Flushable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.NoSuchElementException;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import okhttp3.internal.Util;
import okhttp3.internal.io.FileSystem;
import okhttp3.internal.platform.Platform;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public final class DiskLruCache implements Closeable, Flushable {
    public static final Pattern u = Pattern.compile("[a-z0-9_-]{1,120}");
    public final FileSystem a;
    public final File b;
    public final File c;
    public final File d;
    public final File e;
    public final int f;
    public long g;
    public final int h;
    public BufferedSink j;
    public int l;
    public boolean m;
    public boolean n;
    public boolean o;
    public boolean p;
    public boolean q;
    public final Executor s;
    public long i = 0;
    public final LinkedHashMap<String, c> k = new LinkedHashMap<>(0, 0.75f, true);
    public long r = 0;
    public final Runnable t = new a();

    public final class Editor {
        public final c a;
        public final boolean[] b;
        public boolean c;

        public class a extends fn {
            public a(Sink sink) {
                super(sink);
            }

            @Override // defpackage.fn
            public void a(IOException iOException) {
                synchronized (DiskLruCache.this) {
                    Editor.this.a();
                }
            }
        }

        public Editor(c cVar) {
            this.a = cVar;
            this.b = cVar.e ? null : new boolean[DiskLruCache.this.h];
        }

        public void a() {
            if (this.a.f != this) {
                return;
            }
            int i = 0;
            while (true) {
                DiskLruCache diskLruCache = DiskLruCache.this;
                if (i >= diskLruCache.h) {
                    this.a.f = null;
                    return;
                } else {
                    try {
                        diskLruCache.a.delete(this.a.d[i]);
                    } catch (IOException unused) {
                    }
                    i++;
                }
            }
        }

        public void abort() throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.c) {
                    throw new IllegalStateException();
                }
                if (this.a.f == this) {
                    DiskLruCache.this.a(this, false);
                }
                this.c = true;
            }
        }

        public void abortUnlessCommitted() {
            synchronized (DiskLruCache.this) {
                if (!this.c && this.a.f == this) {
                    try {
                        DiskLruCache.this.a(this, false);
                    } catch (IOException unused) {
                    }
                }
            }
        }

        public void commit() throws IOException {
            synchronized (DiskLruCache.this) {
                if (this.c) {
                    throw new IllegalStateException();
                }
                if (this.a.f == this) {
                    DiskLruCache.this.a(this, true);
                }
                this.c = true;
            }
        }

        public Sink newSink(int i) {
            synchronized (DiskLruCache.this) {
                if (this.c) {
                    throw new IllegalStateException();
                }
                if (this.a.f != this) {
                    return Okio.blackhole();
                }
                if (!this.a.e) {
                    this.b[i] = true;
                }
                try {
                    return new a(DiskLruCache.this.a.sink(this.a.d[i]));
                } catch (FileNotFoundException unused) {
                    return Okio.blackhole();
                }
            }
        }

        public Source newSource(int i) {
            synchronized (DiskLruCache.this) {
                if (this.c) {
                    throw new IllegalStateException();
                }
                if (!this.a.e || this.a.f != this) {
                    return null;
                }
                try {
                    return DiskLruCache.this.a.source(this.a.c[i]);
                } catch (FileNotFoundException unused) {
                    return null;
                }
            }
        }
    }

    public final class Snapshot implements Closeable {
        public final String a;
        public final long b;
        public final Source[] c;
        public final long[] d;

        public Snapshot(String str, long j, Source[] sourceArr, long[] jArr) {
            this.a = str;
            this.b = j;
            this.c = sourceArr;
            this.d = jArr;
        }

        @Override // java.io.Closeable, java.lang.AutoCloseable
        public void close() {
            for (Source source : this.c) {
                Util.closeQuietly(source);
            }
        }

        @Nullable
        public Editor edit() throws IOException {
            return DiskLruCache.this.a(this.a, this.b);
        }

        public long getLength(int i) {
            return this.d[i];
        }

        public Source getSource(int i) {
            return this.c[i];
        }

        public String key() {
            return this.a;
        }
    }

    public class a implements Runnable {
        public a() {
        }

        @Override // java.lang.Runnable
        public void run() {
            synchronized (DiskLruCache.this) {
                if ((!DiskLruCache.this.n) || DiskLruCache.this.o) {
                    return;
                }
                try {
                    DiskLruCache.this.f();
                } catch (IOException unused) {
                    DiskLruCache.this.p = true;
                }
                try {
                    if (DiskLruCache.this.b()) {
                        DiskLruCache.this.e();
                        DiskLruCache.this.l = 0;
                    }
                } catch (IOException unused2) {
                    DiskLruCache.this.q = true;
                    DiskLruCache.this.j = Okio.buffer(Okio.blackhole());
                }
            }
        }
    }

    public class b implements Iterator<Snapshot> {
        public final Iterator<c> a;
        public Snapshot b;
        public Snapshot c;

        public b() {
            this.a = new ArrayList(DiskLruCache.this.k.values()).iterator();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            Snapshot snapshotA;
            if (this.b != null) {
                return true;
            }
            synchronized (DiskLruCache.this) {
                if (DiskLruCache.this.o) {
                    return false;
                }
                while (this.a.hasNext()) {
                    c next = this.a.next();
                    if (next.e && (snapshotA = next.a()) != null) {
                        this.b = snapshotA;
                        return true;
                    }
                }
                return false;
            }
        }

        @Override // java.util.Iterator
        public Snapshot next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Snapshot snapshot = this.b;
            this.c = snapshot;
            this.b = null;
            return snapshot;
        }

        @Override // java.util.Iterator
        public void remove() {
            Snapshot snapshot = this.c;
            if (snapshot == null) {
                throw new IllegalStateException("remove() before next()");
            }
            try {
                DiskLruCache.this.remove(snapshot.a);
            } catch (IOException unused) {
            } catch (Throwable th) {
                this.c = null;
                throw th;
            }
            this.c = null;
        }
    }

    public DiskLruCache(FileSystem fileSystem, File file, int i, int i2, long j, Executor executor) {
        this.a = fileSystem;
        this.b = file;
        this.f = i;
        this.c = new File(file, "journal");
        this.d = new File(file, "journal.tmp");
        this.e = new File(file, "journal.bkp");
        this.h = i2;
        this.g = j;
        this.s = executor;
    }

    public static /* synthetic */ void a(Throwable th, AutoCloseable autoCloseable) throws Exception {
        if (th == null) {
            autoCloseable.close();
            return;
        }
        try {
            autoCloseable.close();
        } catch (Throwable th2) {
            th.addSuppressed(th2);
        }
    }

    public static DiskLruCache create(FileSystem fileSystem, File file, int i, int i2, long j) {
        if (j <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        if (i2 > 0) {
            return new DiskLruCache(fileSystem, file, i, i2, j, new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue(), Util.threadFactory("OkHttp DiskLruCache", true)));
        }
        throw new IllegalArgumentException("valueCount <= 0");
    }

    public boolean b() {
        int i = this.l;
        return i >= 2000 && i >= this.k.size();
    }

    public final void c() throws IOException {
        this.a.delete(this.d);
        Iterator<c> it = this.k.values().iterator();
        while (it.hasNext()) {
            c next = it.next();
            int i = 0;
            if (next.f == null) {
                while (i < this.h) {
                    this.i += next.b[i];
                    i++;
                }
            } else {
                next.f = null;
                while (i < this.h) {
                    this.a.delete(next.c[i]);
                    this.a.delete(next.d[i]);
                    i++;
                }
                it.remove();
            }
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public synchronized void close() throws IOException {
        if (this.n && !this.o) {
            for (c cVar : (c[]) this.k.values().toArray(new c[this.k.size()])) {
                if (cVar.f != null) {
                    cVar.f.abort();
                }
            }
            f();
            this.j.close();
            this.j = null;
            this.o = true;
            return;
        }
        this.o = true;
    }

    public final void d() throws Exception {
        BufferedSource bufferedSourceBuffer = Okio.buffer(this.a.source(this.c));
        try {
            String utf8LineStrict = bufferedSourceBuffer.readUtf8LineStrict();
            String utf8LineStrict2 = bufferedSourceBuffer.readUtf8LineStrict();
            String utf8LineStrict3 = bufferedSourceBuffer.readUtf8LineStrict();
            String utf8LineStrict4 = bufferedSourceBuffer.readUtf8LineStrict();
            String utf8LineStrict5 = bufferedSourceBuffer.readUtf8LineStrict();
            if (!"libcore.io.DiskLruCache".equals(utf8LineStrict) || !"1".equals(utf8LineStrict2) || !Integer.toString(this.f).equals(utf8LineStrict3) || !Integer.toString(this.h).equals(utf8LineStrict4) || !"".equals(utf8LineStrict5)) {
                throw new IOException("unexpected journal header: [" + utf8LineStrict + ", " + utf8LineStrict2 + ", " + utf8LineStrict4 + ", " + utf8LineStrict5 + "]");
            }
            int i = 0;
            while (true) {
                try {
                    a(bufferedSourceBuffer.readUtf8LineStrict());
                    i++;
                } catch (EOFException unused) {
                    this.l = i - this.k.size();
                    if (bufferedSourceBuffer.exhausted()) {
                        this.j = Okio.buffer(new en(this, this.a.appendingSink(this.c)));
                    } else {
                        e();
                    }
                    a((Throwable) null, bufferedSourceBuffer);
                    return;
                }
            }
        } finally {
        }
    }

    public void delete() throws IOException {
        close();
        this.a.deleteContents(this.b);
    }

    public synchronized void e() throws IOException {
        if (this.j != null) {
            this.j.close();
        }
        BufferedSink bufferedSinkBuffer = Okio.buffer(this.a.sink(this.d));
        try {
            bufferedSinkBuffer.writeUtf8("libcore.io.DiskLruCache").writeByte(10);
            bufferedSinkBuffer.writeUtf8("1").writeByte(10);
            bufferedSinkBuffer.writeDecimalLong(this.f).writeByte(10);
            bufferedSinkBuffer.writeDecimalLong(this.h).writeByte(10);
            bufferedSinkBuffer.writeByte(10);
            for (c cVar : this.k.values()) {
                if (cVar.f != null) {
                    bufferedSinkBuffer.writeUtf8("DIRTY").writeByte(32);
                    bufferedSinkBuffer.writeUtf8(cVar.a);
                    bufferedSinkBuffer.writeByte(10);
                } else {
                    bufferedSinkBuffer.writeUtf8("CLEAN").writeByte(32);
                    bufferedSinkBuffer.writeUtf8(cVar.a);
                    cVar.a(bufferedSinkBuffer);
                    bufferedSinkBuffer.writeByte(10);
                }
            }
            a((Throwable) null, bufferedSinkBuffer);
            if (this.a.exists(this.c)) {
                this.a.rename(this.c, this.e);
            }
            this.a.rename(this.d, this.c);
            this.a.delete(this.e);
            this.j = Okio.buffer(new en(this, this.a.appendingSink(this.c)));
            this.m = false;
            this.q = false;
        } finally {
        }
    }

    @Nullable
    public Editor edit(String str) throws IOException {
        return a(str, -1L);
    }

    public synchronized void evictAll() throws IOException {
        initialize();
        for (c cVar : (c[]) this.k.values().toArray(new c[this.k.size()])) {
            a(cVar);
        }
        this.p = false;
    }

    public void f() throws IOException {
        while (this.i > this.g) {
            a(this.k.values().iterator().next());
        }
        this.p = false;
    }

    @Override // java.io.Flushable
    public synchronized void flush() throws IOException {
        if (this.n) {
            a();
            f();
            this.j.flush();
        }
    }

    public synchronized Snapshot get(String str) throws IOException {
        initialize();
        a();
        b(str);
        c cVar = this.k.get(str);
        if (cVar != null && cVar.e) {
            Snapshot snapshotA = cVar.a();
            if (snapshotA == null) {
                return null;
            }
            this.l++;
            this.j.writeUtf8("READ").writeByte(32).writeUtf8(str).writeByte(10);
            if (b()) {
                this.s.execute(this.t);
            }
            return snapshotA;
        }
        return null;
    }

    public File getDirectory() {
        return this.b;
    }

    public synchronized long getMaxSize() {
        return this.g;
    }

    public synchronized void initialize() throws IOException {
        if (this.n) {
            return;
        }
        if (this.a.exists(this.e)) {
            if (this.a.exists(this.c)) {
                this.a.delete(this.e);
            } else {
                this.a.rename(this.e, this.c);
            }
        }
        if (this.a.exists(this.c)) {
            try {
                d();
                c();
                this.n = true;
                return;
            } catch (IOException e) {
                Platform.get().log(5, "DiskLruCache " + this.b + " is corrupt: " + e.getMessage() + ", removing", e);
                try {
                    delete();
                    this.o = false;
                } catch (Throwable th) {
                    this.o = false;
                    throw th;
                }
            }
        }
        e();
        this.n = true;
    }

    public synchronized boolean isClosed() {
        return this.o;
    }

    public synchronized boolean remove(String str) throws IOException {
        initialize();
        a();
        b(str);
        c cVar = this.k.get(str);
        if (cVar == null) {
            return false;
        }
        a(cVar);
        if (this.i <= this.g) {
            this.p = false;
        }
        return true;
    }

    public synchronized void setMaxSize(long j) {
        this.g = j;
        if (this.n) {
            this.s.execute(this.t);
        }
    }

    public synchronized long size() throws IOException {
        initialize();
        return this.i;
    }

    public synchronized Iterator<Snapshot> snapshots() throws IOException {
        initialize();
        return new b();
    }

    public final class c {
        public final String a;
        public final long[] b;
        public final File[] c;
        public final File[] d;
        public boolean e;
        public Editor f;
        public long g;

        public c(String str) {
            this.a = str;
            int i = DiskLruCache.this.h;
            this.b = new long[i];
            this.c = new File[i];
            this.d = new File[i];
            StringBuilder sb = new StringBuilder(str);
            sb.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
            int length = sb.length();
            for (int i2 = 0; i2 < DiskLruCache.this.h; i2++) {
                sb.append(i2);
                this.c[i2] = new File(DiskLruCache.this.b, sb.toString());
                sb.append(".tmp");
                this.d[i2] = new File(DiskLruCache.this.b, sb.toString());
                sb.setLength(length);
            }
        }

        public void a(BufferedSink bufferedSink) throws IOException {
            for (long j : this.b) {
                bufferedSink.writeByte(32).writeDecimalLong(j);
            }
        }

        public final IOException a(String[] strArr) throws IOException {
            StringBuilder sbA = g9.a("unexpected journal line: ");
            sbA.append(Arrays.toString(strArr));
            throw new IOException(sbA.toString());
        }

        public Snapshot a() {
            if (Thread.holdsLock(DiskLruCache.this)) {
                Source[] sourceArr = new Source[DiskLruCache.this.h];
                long[] jArr = (long[]) this.b.clone();
                for (int i = 0; i < DiskLruCache.this.h; i++) {
                    try {
                        sourceArr[i] = DiskLruCache.this.a.source(this.c[i]);
                    } catch (FileNotFoundException unused) {
                        for (int i2 = 0; i2 < DiskLruCache.this.h && sourceArr[i2] != null; i2++) {
                            Util.closeQuietly(sourceArr[i2]);
                        }
                        try {
                            DiskLruCache.this.a(this);
                            return null;
                        } catch (IOException unused2) {
                            return null;
                        }
                    }
                }
                return DiskLruCache.this.new Snapshot(this.a, this.g, sourceArr, jArr);
            }
            throw new AssertionError();
        }
    }

    public final void a(String str) throws IOException {
        String strSubstring;
        int iIndexOf = str.indexOf(32);
        if (iIndexOf == -1) {
            throw new IOException(g9.b("unexpected journal line: ", str));
        }
        int i = iIndexOf + 1;
        int iIndexOf2 = str.indexOf(32, i);
        if (iIndexOf2 == -1) {
            strSubstring = str.substring(i);
            if (iIndexOf == 6 && str.startsWith("REMOVE")) {
                this.k.remove(strSubstring);
                return;
            }
        } else {
            strSubstring = str.substring(i, iIndexOf2);
        }
        c cVar = this.k.get(strSubstring);
        if (cVar == null) {
            cVar = new c(strSubstring);
            this.k.put(strSubstring, cVar);
        }
        if (iIndexOf2 == -1 || iIndexOf != 5 || !str.startsWith("CLEAN")) {
            if (iIndexOf2 == -1 && iIndexOf == 5 && str.startsWith("DIRTY")) {
                cVar.f = new Editor(cVar);
                return;
            } else {
                if (iIndexOf2 != -1 || iIndexOf != 4 || !str.startsWith("READ")) {
                    throw new IOException(g9.b("unexpected journal line: ", str));
                }
                return;
            }
        }
        String[] strArrSplit = str.substring(iIndexOf2 + 1).split(StringUtils.SPACE);
        cVar.e = true;
        cVar.f = null;
        if (strArrSplit.length != DiskLruCache.this.h) {
            cVar.a(strArrSplit);
            throw null;
        }
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            try {
                cVar.b[i2] = Long.parseLong(strArrSplit[i2]);
            } catch (NumberFormatException unused) {
                cVar.a(strArrSplit);
                throw null;
            }
        }
    }

    public final void b(String str) {
        if (!u.matcher(str).matches()) {
            throw new IllegalArgumentException(g9.a("keys must match regex [a-z0-9_-]{1,120}: \"", str, "\""));
        }
    }

    public synchronized Editor a(String str, long j) throws IOException {
        initialize();
        a();
        b(str);
        c cVar = this.k.get(str);
        if (j != -1 && (cVar == null || cVar.g != j)) {
            return null;
        }
        if (cVar != null && cVar.f != null) {
            return null;
        }
        if (!this.p && !this.q) {
            this.j.writeUtf8("DIRTY").writeByte(32).writeUtf8(str).writeByte(10);
            this.j.flush();
            if (this.m) {
                return null;
            }
            if (cVar == null) {
                cVar = new c(str);
                this.k.put(str, cVar);
            }
            Editor editor = new Editor(cVar);
            cVar.f = editor;
            return editor;
        }
        this.s.execute(this.t);
        return null;
    }

    public synchronized void a(Editor editor, boolean z) throws IOException {
        c cVar = editor.a;
        if (cVar.f == editor) {
            if (z && !cVar.e) {
                for (int i = 0; i < this.h; i++) {
                    if (editor.b[i]) {
                        if (!this.a.exists(cVar.d[i])) {
                            editor.abort();
                            return;
                        }
                    } else {
                        editor.abort();
                        throw new IllegalStateException("Newly created entry didn't create value for index " + i);
                    }
                }
            }
            for (int i2 = 0; i2 < this.h; i2++) {
                File file = cVar.d[i2];
                if (z) {
                    if (this.a.exists(file)) {
                        File file2 = cVar.c[i2];
                        this.a.rename(file, file2);
                        long j = cVar.b[i2];
                        long size = this.a.size(file2);
                        cVar.b[i2] = size;
                        this.i = (this.i - j) + size;
                    }
                } else {
                    this.a.delete(file);
                }
            }
            this.l++;
            cVar.f = null;
            if (cVar.e | z) {
                cVar.e = true;
                this.j.writeUtf8("CLEAN").writeByte(32);
                this.j.writeUtf8(cVar.a);
                cVar.a(this.j);
                this.j.writeByte(10);
                if (z) {
                    long j2 = this.r;
                    this.r = 1 + j2;
                    cVar.g = j2;
                }
            } else {
                this.k.remove(cVar.a);
                this.j.writeUtf8("REMOVE").writeByte(32);
                this.j.writeUtf8(cVar.a);
                this.j.writeByte(10);
            }
            this.j.flush();
            if (this.i > this.g || b()) {
                this.s.execute(this.t);
            }
            return;
        }
        throw new IllegalStateException();
    }

    public boolean a(c cVar) throws IOException {
        Editor editor = cVar.f;
        if (editor != null) {
            editor.a();
        }
        for (int i = 0; i < this.h; i++) {
            this.a.delete(cVar.c[i]);
            long j = this.i;
            long[] jArr = cVar.b;
            this.i = j - jArr[i];
            jArr[i] = 0;
        }
        this.l++;
        this.j.writeUtf8("REMOVE").writeByte(32).writeUtf8(cVar.a).writeByte(10);
        this.k.remove(cVar.a);
        if (b()) {
            this.s.execute(this.t);
        }
        return true;
    }

    public final synchronized void a() {
        if (isClosed()) {
            throw new IllegalStateException("cache is closed");
        }
    }
}
