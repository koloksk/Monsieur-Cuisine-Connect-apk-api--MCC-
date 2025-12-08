package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import defpackage.g9;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class AtomicFile {
    public final File a;
    public final File b;

    public AtomicFile(@NonNull File file) {
        this.a = file;
        this.b = new File(file.getPath() + ".bak");
    }

    public void delete() {
        this.a.delete();
        this.b.delete();
    }

    public void failWrite(@Nullable FileOutputStream fileOutputStream) throws IOException {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.getFD().sync();
            } catch (IOException unused) {
            }
            try {
                fileOutputStream.close();
                this.a.delete();
                this.b.renameTo(this.a);
            } catch (IOException e) {
                Log.w("AtomicFile", "failWrite: Got exception:", e);
            }
        }
    }

    public void finishWrite(@Nullable FileOutputStream fileOutputStream) throws IOException {
        if (fileOutputStream != null) {
            try {
                fileOutputStream.getFD().sync();
            } catch (IOException unused) {
            }
            try {
                fileOutputStream.close();
                this.b.delete();
            } catch (IOException e) {
                Log.w("AtomicFile", "finishWrite: Got exception:", e);
            }
        }
    }

    @NonNull
    public File getBaseFile() {
        return this.a;
    }

    @NonNull
    public FileInputStream openRead() throws FileNotFoundException {
        if (this.b.exists()) {
            this.a.delete();
            this.b.renameTo(this.a);
        }
        return new FileInputStream(this.a);
    }

    @NonNull
    public byte[] readFully() throws IOException {
        FileInputStream fileInputStreamOpenRead = openRead();
        try {
            byte[] bArr = new byte[fileInputStreamOpenRead.available()];
            int i = 0;
            while (true) {
                int i2 = fileInputStreamOpenRead.read(bArr, i, bArr.length - i);
                if (i2 <= 0) {
                    return bArr;
                }
                i += i2;
                int iAvailable = fileInputStreamOpenRead.available();
                if (iAvailable > bArr.length - i) {
                    byte[] bArr2 = new byte[iAvailable + i];
                    System.arraycopy(bArr, 0, bArr2, 0, i);
                    bArr = bArr2;
                }
            }
        } finally {
            fileInputStreamOpenRead.close();
        }
    }

    @NonNull
    public FileOutputStream startWrite() throws IOException {
        if (this.a.exists()) {
            if (this.b.exists()) {
                this.a.delete();
            } else if (!this.a.renameTo(this.b)) {
                StringBuilder sbA = g9.a("Couldn't rename file ");
                sbA.append(this.a);
                sbA.append(" to backup file ");
                sbA.append(this.b);
                Log.w("AtomicFile", sbA.toString());
            }
        }
        try {
            return new FileOutputStream(this.a);
        } catch (FileNotFoundException unused) {
            if (!this.a.getParentFile().mkdirs()) {
                StringBuilder sbA2 = g9.a("Couldn't create directory ");
                sbA2.append(this.a);
                throw new IOException(sbA2.toString());
            }
            try {
                return new FileOutputStream(this.a);
            } catch (FileNotFoundException unused2) {
                StringBuilder sbA3 = g9.a("Couldn't create ");
                sbA3.append(this.a);
                throw new IOException(sbA3.toString());
            }
        }
    }
}
