package okio;

import defpackage.Cdo;
import defpackage.ao;
import defpackage.eo;
import defpackage.fo;
import defpackage.g9;
import defpackage.go;
import defpackage.io;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.logging.Logger;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;

/* loaded from: classes.dex */
public final class Okio {
    public static final Logger a = Logger.getLogger(Okio.class.getName());

    public class a implements Sink {
        public final /* synthetic */ Timeout a;
        public final /* synthetic */ OutputStream b;

        public a(Timeout timeout, OutputStream outputStream) {
            this.a = timeout;
            this.b = outputStream;
        }

        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
        public void close() throws IOException {
            this.b.close();
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
            this.b.flush();
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return this.a;
        }

        public String toString() {
            StringBuilder sbA = g9.a("sink(");
            sbA.append(this.b);
            sbA.append(")");
            return sbA.toString();
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            io.a(buffer.b, 0L, j);
            while (j > 0) {
                this.a.throwIfReached();
                fo foVar = buffer.a;
                int iMin = (int) Math.min(j, foVar.c - foVar.b);
                this.b.write(foVar.a, foVar.b, iMin);
                int i = foVar.b + iMin;
                foVar.b = i;
                long j2 = iMin;
                j -= j2;
                buffer.b -= j2;
                if (i == foVar.c) {
                    buffer.a = foVar.a();
                    go.a(foVar);
                }
            }
        }
    }

    public class b implements Source {
        public final /* synthetic */ Timeout a;
        public final /* synthetic */ InputStream b;

        public b(Timeout timeout, InputStream inputStream) {
            this.a = timeout;
            this.b = inputStream;
        }

        @Override // okio.Source, java.io.Closeable, java.lang.AutoCloseable
        public void close() throws IOException {
            this.b.close();
        }

        @Override // okio.Source
        public long read(Buffer buffer, long j) throws IOException {
            if (j < 0) {
                throw new IllegalArgumentException(g9.a("byteCount < 0: ", j));
            }
            if (j == 0) {
                return 0L;
            }
            try {
                this.a.throwIfReached();
                fo foVarA = buffer.a(1);
                int i = this.b.read(foVarA.a, foVarA.c, (int) Math.min(j, 8192 - foVarA.c));
                if (i == -1) {
                    return -1L;
                }
                foVarA.c += i;
                long j2 = i;
                buffer.b += j2;
                return j2;
            } catch (AssertionError e) {
                if (Okio.a(e)) {
                    throw new IOException(e);
                }
                throw e;
            }
        }

        @Override // okio.Source
        public Timeout timeout() {
            return this.a;
        }

        public String toString() {
            StringBuilder sbA = g9.a("source(");
            sbA.append(this.b);
            sbA.append(")");
            return sbA.toString();
        }
    }

    public class c implements Sink {
        @Override // okio.Sink, java.io.Closeable, java.lang.AutoCloseable, java.nio.channels.Channel
        public void close() throws IOException {
        }

        @Override // okio.Sink, java.io.Flushable
        public void flush() throws IOException {
        }

        @Override // okio.Sink
        public Timeout timeout() {
            return Timeout.NONE;
        }

        @Override // okio.Sink
        public void write(Buffer buffer, long j) throws IOException {
            buffer.skip(j);
        }
    }

    public static Sink a(OutputStream outputStream, Timeout timeout) {
        if (outputStream == null) {
            throw new IllegalArgumentException("out == null");
        }
        if (timeout != null) {
            return new a(timeout, outputStream);
        }
        throw new IllegalArgumentException("timeout == null");
    }

    public static Sink appendingSink(File file) throws FileNotFoundException {
        if (file != null) {
            return sink(new FileOutputStream(file, true));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static Sink blackhole() {
        return new c();
    }

    public static BufferedSource buffer(Source source) {
        return new eo(source);
    }

    public static Sink sink(OutputStream outputStream) {
        return a(outputStream, new Timeout());
    }

    public static Source source(InputStream inputStream) {
        return a(inputStream, new Timeout());
    }

    public static BufferedSink buffer(Sink sink) {
        return new Cdo(sink);
    }

    public static Sink sink(Socket socket) throws IOException {
        if (socket == null) {
            throw new IllegalArgumentException("socket == null");
        }
        if (socket.getOutputStream() == null) {
            throw new IOException("socket's output stream == null");
        }
        ao aoVar = new ao(socket);
        return aoVar.sink(a(socket.getOutputStream(), aoVar));
    }

    public static Source source(File file) throws FileNotFoundException {
        if (file != null) {
            return source(new FileInputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    public static Source a(InputStream inputStream, Timeout timeout) {
        if (inputStream == null) {
            throw new IllegalArgumentException("in == null");
        }
        if (timeout != null) {
            return new b(timeout, inputStream);
        }
        throw new IllegalArgumentException("timeout == null");
    }

    @IgnoreJRERequirement
    public static Source source(Path path, OpenOption... openOptionArr) throws IOException {
        if (path != null) {
            return source(Files.newInputStream(path, openOptionArr));
        }
        throw new IllegalArgumentException("path == null");
    }

    public static Source source(Socket socket) throws IOException {
        if (socket != null) {
            if (socket.getInputStream() != null) {
                ao aoVar = new ao(socket);
                return aoVar.source(a(socket.getInputStream(), aoVar));
            }
            throw new IOException("socket's input stream == null");
        }
        throw new IllegalArgumentException("socket == null");
    }

    public static boolean a(AssertionError assertionError) {
        return (assertionError.getCause() == null || assertionError.getMessage() == null || !assertionError.getMessage().contains("getsockname failed")) ? false : true;
    }

    public static Sink sink(File file) throws FileNotFoundException {
        if (file != null) {
            return sink(new FileOutputStream(file));
        }
        throw new IllegalArgumentException("file == null");
    }

    @IgnoreJRERequirement
    public static Sink sink(Path path, OpenOption... openOptionArr) throws IOException {
        if (path != null) {
            return sink(Files.newOutputStream(path, openOptionArr));
        }
        throw new IllegalArgumentException("path == null");
    }
}
