package okhttp3;

import java.io.Closeable;
import java.io.File;
import java.io.Flushable;
import java.io.IOException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import javax.annotation.Nullable;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.cache.CacheRequest;
import okhttp3.internal.cache.CacheStrategy;
import okhttp3.internal.cache.DiskLruCache;
import okhttp3.internal.cache.InternalCache;
import okhttp3.internal.http.HttpHeaders;
import okhttp3.internal.http.HttpMethod;
import okhttp3.internal.http.StatusLine;
import okhttp3.internal.io.FileSystem;
import okhttp3.internal.platform.Platform;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.ForwardingSink;
import okio.ForwardingSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

/* loaded from: classes.dex */
public final class Cache implements Closeable, Flushable {
    public final InternalCache a;
    public final DiskLruCache b;
    public int c;
    public int d;
    public int e;
    public int f;
    public int g;

    public class a implements InternalCache {
        public a() {
        }

        @Override // okhttp3.internal.cache.InternalCache
        @Nullable
        public Response get(Request request) throws IOException {
            Cache cache = Cache.this;
            if (cache == null) {
                throw null;
            }
            try {
                DiskLruCache.Snapshot snapshot = cache.b.get(Cache.key(request.url()));
                if (snapshot == null) {
                    return null;
                }
                try {
                    boolean z = false;
                    e eVar = new e(snapshot.getSource(0));
                    String str = eVar.g.get("Content-Type");
                    String str2 = eVar.g.get("Content-Length");
                    Response responseBuild = new Response.Builder().request(new Request.Builder().url(eVar.a).method(eVar.c, null).headers(eVar.b).build()).protocol(eVar.d).code(eVar.e).message(eVar.f).headers(eVar.g).body(new d(snapshot, str, str2)).handshake(eVar.h).sentRequestAtMillis(eVar.i).receivedResponseAtMillis(eVar.j).build();
                    if (eVar.a.equals(request.url().toString()) && eVar.c.equals(request.method()) && HttpHeaders.varyMatches(responseBuild, eVar.b, request)) {
                        z = true;
                    }
                    if (z) {
                        return responseBuild;
                    }
                    Util.closeQuietly(responseBuild.body());
                    return null;
                } catch (IOException unused) {
                    Util.closeQuietly(snapshot);
                    return null;
                }
            } catch (IOException unused2) {
                return null;
            }
        }

        @Override // okhttp3.internal.cache.InternalCache
        @Nullable
        public CacheRequest put(Response response) throws IOException {
            DiskLruCache.Editor editorEdit;
            Cache cache = Cache.this;
            if (cache == null) {
                throw null;
            }
            String strMethod = response.request().method();
            try {
                if (HttpMethod.invalidatesCache(response.request().method())) {
                    cache.b.remove(Cache.key(response.request().url()));
                } else {
                    if (!strMethod.equals("GET") || HttpHeaders.hasVaryAll(response)) {
                        return null;
                    }
                    e eVar = new e(response);
                    try {
                        editorEdit = cache.b.edit(Cache.key(response.request().url()));
                        if (editorEdit == null) {
                            return null;
                        }
                        try {
                            eVar.a(editorEdit);
                            return cache.new c(editorEdit);
                        } catch (IOException unused) {
                            if (editorEdit == null) {
                                return null;
                            }
                            editorEdit.abort();
                            return null;
                        }
                    } catch (IOException unused2) {
                        editorEdit = null;
                    }
                }
                return null;
            } catch (IOException unused3) {
                return null;
            }
        }

        @Override // okhttp3.internal.cache.InternalCache
        public void remove(Request request) throws IOException {
            Cache.this.b.remove(Cache.key(request.url()));
        }

        @Override // okhttp3.internal.cache.InternalCache
        public void trackConditionalCacheHit() {
            Cache.this.a();
        }

        @Override // okhttp3.internal.cache.InternalCache
        public void trackResponse(CacheStrategy cacheStrategy) {
            Cache.this.a(cacheStrategy);
        }

        @Override // okhttp3.internal.cache.InternalCache
        public void update(Response response, Response response2) {
            DiskLruCache.Editor editorEdit = null;
            if (Cache.this == null) {
                throw null;
            }
            e eVar = new e(response2);
            try {
                editorEdit = ((d) response.body()).a.edit();
                if (editorEdit != null) {
                    eVar.a(editorEdit);
                    editorEdit.commit();
                }
            } catch (IOException unused) {
                if (editorEdit != null) {
                    try {
                        editorEdit.abort();
                    } catch (IOException unused2) {
                    }
                }
            }
        }
    }

    public class b implements Iterator<String> {
        public final Iterator<DiskLruCache.Snapshot> a;

        @Nullable
        public String b;
        public boolean c;

        public b() throws IOException {
            this.a = Cache.this.b.snapshots();
        }

        @Override // java.util.Iterator
        public boolean hasNext() {
            if (this.b != null) {
                return true;
            }
            this.c = false;
            while (this.a.hasNext()) {
                try {
                    DiskLruCache.Snapshot next = this.a.next();
                    try {
                        continue;
                        this.b = Okio.buffer(next.getSource(0)).readUtf8LineStrict();
                        next.close();
                        return true;
                    } finally {
                        try {
                            continue;
                        } finally {
                        }
                    }
                } catch (IOException unused) {
                }
            }
            return false;
        }

        @Override // java.util.Iterator
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            String str = this.b;
            this.b = null;
            this.c = true;
            return str;
        }

        @Override // java.util.Iterator
        public void remove() {
            if (!this.c) {
                throw new IllegalStateException("remove() before next()");
            }
            this.a.remove();
        }
    }

    public final class c implements CacheRequest {
        public final DiskLruCache.Editor a;
        public Sink b;
        public Sink c;
        public boolean d;

        public class a extends ForwardingSink {
            public final /* synthetic */ DiskLruCache.Editor b;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public a(Sink sink, Cache cache, DiskLruCache.Editor editor) {
                super(sink);
                this.b = editor;
            }

            @Override // okio.ForwardingSink, okio.Sink, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
            public void close() throws IOException {
                synchronized (Cache.this) {
                    if (c.this.d) {
                        return;
                    }
                    c.this.d = true;
                    Cache.this.c++;
                    super.close();
                    this.b.commit();
                }
            }
        }

        public c(DiskLruCache.Editor editor) {
            this.a = editor;
            Sink sinkNewSink = editor.newSink(1);
            this.b = sinkNewSink;
            this.c = new a(sinkNewSink, Cache.this, editor);
        }

        @Override // okhttp3.internal.cache.CacheRequest
        public void abort() {
            synchronized (Cache.this) {
                if (this.d) {
                    return;
                }
                this.d = true;
                Cache.this.d++;
                Util.closeQuietly(this.b);
                try {
                    this.a.abort();
                } catch (IOException unused) {
                }
            }
        }

        @Override // okhttp3.internal.cache.CacheRequest
        public Sink body() {
            return this.c;
        }
    }

    public static class d extends ResponseBody {
        public final DiskLruCache.Snapshot a;
        public final BufferedSource b;

        @Nullable
        public final String c;

        @Nullable
        public final String d;

        public class a extends ForwardingSource {
            public final /* synthetic */ DiskLruCache.Snapshot a;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public a(d dVar, Source source, DiskLruCache.Snapshot snapshot) {
                super(source);
                this.a = snapshot;
            }

            @Override // okio.ForwardingSource, okio.Source, java.io.Closeable, java.lang.AutoCloseable
            public void close() throws IOException {
                this.a.close();
                super.close();
            }
        }

        public d(DiskLruCache.Snapshot snapshot, String str, String str2) {
            this.a = snapshot;
            this.c = str;
            this.d = str2;
            this.b = Okio.buffer(new a(this, snapshot.getSource(1), snapshot));
        }

        @Override // okhttp3.ResponseBody
        public long contentLength() {
            try {
                if (this.d != null) {
                    return Long.parseLong(this.d);
                }
                return -1L;
            } catch (NumberFormatException unused) {
                return -1L;
            }
        }

        @Override // okhttp3.ResponseBody
        public MediaType contentType() {
            String str = this.c;
            if (str != null) {
                return MediaType.parse(str);
            }
            return null;
        }

        @Override // okhttp3.ResponseBody
        public BufferedSource source() {
            return this.b;
        }
    }

    public Cache(File file, long j) {
        FileSystem fileSystem = FileSystem.SYSTEM;
        this.a = new a();
        this.b = DiskLruCache.create(fileSystem, file, 201105, 2, j);
    }

    public static String key(HttpUrl httpUrl) {
        return ByteString.encodeUtf8(httpUrl.toString()).md5().hex();
    }

    public synchronized void a(CacheStrategy cacheStrategy) {
        this.g++;
        if (cacheStrategy.networkRequest != null) {
            this.e++;
        } else if (cacheStrategy.cacheResponse != null) {
            this.f++;
        }
    }

    @Override // java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.b.close();
    }

    public void delete() throws IOException {
        this.b.delete();
    }

    public File directory() {
        return this.b.getDirectory();
    }

    public void evictAll() throws IOException {
        this.b.evictAll();
    }

    @Override // java.io.Flushable
    public void flush() throws IOException {
        this.b.flush();
    }

    public synchronized int hitCount() {
        return this.f;
    }

    public void initialize() throws IOException {
        this.b.initialize();
    }

    public boolean isClosed() {
        return this.b.isClosed();
    }

    public long maxSize() {
        return this.b.getMaxSize();
    }

    public synchronized int networkCount() {
        return this.e;
    }

    public synchronized int requestCount() {
        return this.g;
    }

    public long size() throws IOException {
        return this.b.size();
    }

    public Iterator<String> urls() throws IOException {
        return new b();
    }

    public synchronized int writeAbortCount() {
        return this.d;
    }

    public synchronized int writeSuccessCount() {
        return this.c;
    }

    public synchronized void a() {
        this.f++;
    }

    public static int a(BufferedSource bufferedSource) throws IOException {
        try {
            long decimalLong = bufferedSource.readDecimalLong();
            String utf8LineStrict = bufferedSource.readUtf8LineStrict();
            if (decimalLong >= 0 && decimalLong <= 2147483647L && utf8LineStrict.isEmpty()) {
                return (int) decimalLong;
            }
            throw new IOException("expected an int but was \"" + decimalLong + utf8LineStrict + "\"");
        } catch (NumberFormatException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    public static final class e {
        public static final String k = Platform.get().getPrefix() + "-Sent-Millis";
        public static final String l = Platform.get().getPrefix() + "-Received-Millis";
        public final String a;
        public final Headers b;
        public final String c;
        public final Protocol d;
        public final int e;
        public final String f;
        public final Headers g;

        @Nullable
        public final Handshake h;
        public final long i;
        public final long j;

        public e(Source source) throws IOException {
            try {
                BufferedSource bufferedSourceBuffer = Okio.buffer(source);
                this.a = bufferedSourceBuffer.readUtf8LineStrict();
                this.c = bufferedSourceBuffer.readUtf8LineStrict();
                Headers.Builder builder = new Headers.Builder();
                int iA = Cache.a(bufferedSourceBuffer);
                for (int i = 0; i < iA; i++) {
                    builder.a(bufferedSourceBuffer.readUtf8LineStrict());
                }
                this.b = builder.build();
                StatusLine statusLine = StatusLine.parse(bufferedSourceBuffer.readUtf8LineStrict());
                this.d = statusLine.protocol;
                this.e = statusLine.code;
                this.f = statusLine.message;
                Headers.Builder builder2 = new Headers.Builder();
                int iA2 = Cache.a(bufferedSourceBuffer);
                for (int i2 = 0; i2 < iA2; i2++) {
                    builder2.a(bufferedSourceBuffer.readUtf8LineStrict());
                }
                String str = builder2.get(k);
                String str2 = builder2.get(l);
                builder2.removeAll(k);
                builder2.removeAll(l);
                this.i = str != null ? Long.parseLong(str) : 0L;
                this.j = str2 != null ? Long.parseLong(str2) : 0L;
                this.g = builder2.build();
                if (this.a.startsWith("https://")) {
                    String utf8LineStrict = bufferedSourceBuffer.readUtf8LineStrict();
                    if (utf8LineStrict.length() > 0) {
                        throw new IOException("expected \"\" but was \"" + utf8LineStrict + "\"");
                    }
                    this.h = Handshake.get(!bufferedSourceBuffer.exhausted() ? TlsVersion.forJavaName(bufferedSourceBuffer.readUtf8LineStrict()) : TlsVersion.SSL_3_0, CipherSuite.forJavaName(bufferedSourceBuffer.readUtf8LineStrict()), a(bufferedSourceBuffer), a(bufferedSourceBuffer));
                } else {
                    this.h = null;
                }
            } finally {
                source.close();
            }
        }

        public void a(DiskLruCache.Editor editor) throws IOException {
            BufferedSink bufferedSinkBuffer = Okio.buffer(editor.newSink(0));
            bufferedSinkBuffer.writeUtf8(this.a).writeByte(10);
            bufferedSinkBuffer.writeUtf8(this.c).writeByte(10);
            bufferedSinkBuffer.writeDecimalLong(this.b.size()).writeByte(10);
            int size = this.b.size();
            for (int i = 0; i < size; i++) {
                bufferedSinkBuffer.writeUtf8(this.b.name(i)).writeUtf8(": ").writeUtf8(this.b.value(i)).writeByte(10);
            }
            bufferedSinkBuffer.writeUtf8(new StatusLine(this.d, this.e, this.f).toString()).writeByte(10);
            bufferedSinkBuffer.writeDecimalLong(this.g.size() + 2).writeByte(10);
            int size2 = this.g.size();
            for (int i2 = 0; i2 < size2; i2++) {
                bufferedSinkBuffer.writeUtf8(this.g.name(i2)).writeUtf8(": ").writeUtf8(this.g.value(i2)).writeByte(10);
            }
            bufferedSinkBuffer.writeUtf8(k).writeUtf8(": ").writeDecimalLong(this.i).writeByte(10);
            bufferedSinkBuffer.writeUtf8(l).writeUtf8(": ").writeDecimalLong(this.j).writeByte(10);
            if (this.a.startsWith("https://")) {
                bufferedSinkBuffer.writeByte(10);
                bufferedSinkBuffer.writeUtf8(this.h.cipherSuite().javaName()).writeByte(10);
                a(bufferedSinkBuffer, this.h.peerCertificates());
                a(bufferedSinkBuffer, this.h.localCertificates());
                bufferedSinkBuffer.writeUtf8(this.h.tlsVersion().javaName()).writeByte(10);
            }
            bufferedSinkBuffer.close();
        }

        public final List<Certificate> a(BufferedSource bufferedSource) throws IOException, CertificateException {
            int iA = Cache.a(bufferedSource);
            if (iA == -1) {
                return Collections.emptyList();
            }
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                ArrayList arrayList = new ArrayList(iA);
                for (int i = 0; i < iA; i++) {
                    String utf8LineStrict = bufferedSource.readUtf8LineStrict();
                    Buffer buffer = new Buffer();
                    buffer.write(ByteString.decodeBase64(utf8LineStrict));
                    arrayList.add(certificateFactory.generateCertificate(buffer.inputStream()));
                }
                return arrayList;
            } catch (CertificateException e) {
                throw new IOException(e.getMessage());
            }
        }

        public e(Response response) {
            this.a = response.request().url().toString();
            this.b = HttpHeaders.varyHeaders(response);
            this.c = response.request().method();
            this.d = response.protocol();
            this.e = response.code();
            this.f = response.message();
            this.g = response.headers();
            this.h = response.handshake();
            this.i = response.sentRequestAtMillis();
            this.j = response.receivedResponseAtMillis();
        }

        public final void a(BufferedSink bufferedSink, List<Certificate> list) throws IOException {
            try {
                bufferedSink.writeDecimalLong(list.size()).writeByte(10);
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    bufferedSink.writeUtf8(ByteString.of(list.get(i).getEncoded()).base64()).writeByte(10);
                }
            } catch (CertificateEncodingException e) {
                throw new IOException(e.getMessage());
            }
        }
    }
}
