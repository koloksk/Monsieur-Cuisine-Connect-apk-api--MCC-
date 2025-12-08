package defpackage;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.provider.DocumentFile;

@RequiresApi(19)
/* loaded from: classes.dex */
public class w5 extends DocumentFile {
    public Context b;
    public Uri c;

    public w5(DocumentFile documentFile, Context context, Uri uri) {
        super(documentFile);
        this.b = context;
        this.c = uri;
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean canRead() {
        return q5.a(this.b, this.c);
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean canWrite() {
        return q5.b(this.b, this.c);
    }

    @Override // android.support.v4.provider.DocumentFile
    public DocumentFile createDirectory(String str) {
        throw new UnsupportedOperationException();
    }

    @Override // android.support.v4.provider.DocumentFile
    public DocumentFile createFile(String str, String str2) {
        throw new UnsupportedOperationException();
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean delete() {
        try {
            return DocumentsContract.deleteDocument(this.b.getContentResolver(), this.c);
        } catch (Exception unused) {
            return false;
        }
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean exists() {
        return q5.c(this.b, this.c);
    }

    @Override // android.support.v4.provider.DocumentFile
    public String getName() {
        return q5.a(this.b, this.c, "_display_name", (String) null);
    }

    @Override // android.support.v4.provider.DocumentFile
    public String getType() {
        String strD = q5.d(this.b, this.c);
        if ("vnd.android.document/directory".equals(strD)) {
            return null;
        }
        return strD;
    }

    @Override // android.support.v4.provider.DocumentFile
    public Uri getUri() {
        return this.c;
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean isDirectory() {
        return "vnd.android.document/directory".equals(q5.d(this.b, this.c));
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean isFile() {
        return q5.e(this.b, this.c);
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean isVirtual() {
        return q5.f(this.b, this.c);
    }

    @Override // android.support.v4.provider.DocumentFile
    public long lastModified() {
        return q5.a(this.b, this.c, "last_modified", 0L);
    }

    @Override // android.support.v4.provider.DocumentFile
    public long length() {
        return q5.a(this.b, this.c, "_size", 0L);
    }

    @Override // android.support.v4.provider.DocumentFile
    public DocumentFile[] listFiles() {
        throw new UnsupportedOperationException();
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean renameTo(String str) {
        throw new UnsupportedOperationException();
    }
}
