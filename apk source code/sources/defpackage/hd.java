package defpackage;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public final class hd extends InputStream {
    public final InputStream a;
    public long b;
    public long c;
    public long d;
    public long e = -1;
    public boolean f = true;
    public int g;

    public hd(InputStream inputStream) {
        this.g = -1;
        this.a = inputStream.markSupported() ? inputStream : new BufferedInputStream(inputStream, 4096);
        this.g = 1024;
    }

    public void a(long j) throws IOException {
        if (this.b > this.d || j < this.c) {
            throw new IOException("Cannot reset");
        }
        this.a.reset();
        a(this.c, j);
        this.b = j;
    }

    @Override // java.io.InputStream
    public int available() throws IOException {
        return this.a.available();
    }

    public final void b(long j) throws IOException {
        try {
            if (this.c >= this.b || this.b > this.d) {
                this.c = this.b;
                this.a.mark((int) (j - this.b));
            } else {
                this.a.reset();
                this.a.mark((int) (j - this.c));
                a(this.c, this.b);
            }
            this.d = j;
        } catch (IOException e) {
            throw new IllegalStateException("Unable to mark: " + e);
        }
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() throws IOException {
        this.a.close();
    }

    @Override // java.io.InputStream
    public void mark(int i) throws IOException {
        long j = this.b + i;
        if (this.d < j) {
            b(j);
        }
        this.e = this.b;
    }

    @Override // java.io.InputStream
    public boolean markSupported() {
        return this.a.markSupported();
    }

    @Override // java.io.InputStream
    public int read() throws IOException {
        if (!this.f) {
            long j = this.b + 1;
            long j2 = this.d;
            if (j > j2) {
                b(j2 + this.g);
            }
        }
        int i = this.a.read();
        if (i != -1) {
            this.b++;
        }
        return i;
    }

    @Override // java.io.InputStream
    public void reset() throws IOException {
        a(this.e);
    }

    @Override // java.io.InputStream
    public long skip(long j) throws IOException {
        if (!this.f) {
            long j2 = this.b;
            if (j2 + j > this.d) {
                b(j2 + j + this.g);
            }
        }
        long jSkip = this.a.skip(j);
        this.b += jSkip;
        return jSkip;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) throws IOException {
        if (!this.f) {
            long j = this.b;
            if (bArr.length + j > this.d) {
                b(j + bArr.length + this.g);
            }
        }
        int i = this.a.read(bArr);
        if (i != -1) {
            this.b += i;
        }
        return i;
    }

    public final void a(long j, long j2) throws IOException {
        while (j < j2) {
            long jSkip = this.a.skip(j2 - j);
            if (jSkip == 0) {
                if (read() == -1) {
                    return;
                } else {
                    jSkip = 1;
                }
            }
            j += jSkip;
        }
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        if (!this.f) {
            long j = this.b;
            long j2 = i2;
            if (j + j2 > this.d) {
                b(j + j2 + this.g);
            }
        }
        int i3 = this.a.read(bArr, i, i2);
        if (i3 != -1) {
            this.b += i3;
        }
        return i3;
    }
}
