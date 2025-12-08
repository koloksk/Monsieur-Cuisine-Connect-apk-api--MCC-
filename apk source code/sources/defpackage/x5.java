package defpackage;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.provider.DocumentFile;
import android.util.Log;
import java.io.FileNotFoundException;
import java.util.ArrayList;

@RequiresApi(21)
/* loaded from: classes.dex */
public class x5 extends DocumentFile {
    public Context b;
    public Uri c;

    public x5(DocumentFile documentFile, Context context, Uri uri) {
        super(documentFile);
        this.b = context;
        this.c = uri;
    }

    public static void a(AutoCloseable autoCloseable) throws Exception {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (RuntimeException e) {
                throw e;
            } catch (Exception unused) {
            }
        }
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
    public DocumentFile createDirectory(String str) throws FileNotFoundException {
        Uri uriCreateDocument;
        try {
            uriCreateDocument = DocumentsContract.createDocument(this.b.getContentResolver(), this.c, "vnd.android.document/directory", str);
        } catch (Exception unused) {
            uriCreateDocument = null;
        }
        if (uriCreateDocument != null) {
            return new x5(this, this.b, uriCreateDocument);
        }
        return null;
    }

    @Override // android.support.v4.provider.DocumentFile
    public DocumentFile createFile(String str, String str2) throws FileNotFoundException {
        Uri uriCreateDocument;
        try {
            uriCreateDocument = DocumentsContract.createDocument(this.b.getContentResolver(), this.c, str, str2);
        } catch (Exception unused) {
            uriCreateDocument = null;
        }
        if (uriCreateDocument != null) {
            return new x5(this, this.b, uriCreateDocument);
        }
        return null;
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
    public DocumentFile[] listFiles() throws Exception {
        ContentResolver contentResolver = this.b.getContentResolver();
        Uri uri = this.c;
        Uri uriBuildChildDocumentsUriUsingTree = DocumentsContract.buildChildDocumentsUriUsingTree(uri, DocumentsContract.getDocumentId(uri));
        ArrayList arrayList = new ArrayList();
        Cursor cursorQuery = null;
        try {
            try {
                cursorQuery = contentResolver.query(uriBuildChildDocumentsUriUsingTree, new String[]{"document_id"}, null, null, null);
                while (cursorQuery.moveToNext()) {
                    arrayList.add(DocumentsContract.buildDocumentUriUsingTree(this.c, cursorQuery.getString(0)));
                }
            } catch (Exception e) {
                Log.w("DocumentFile", "Failed query: " + e);
            }
            Uri[] uriArr = (Uri[]) arrayList.toArray(new Uri[arrayList.size()]);
            DocumentFile[] documentFileArr = new DocumentFile[uriArr.length];
            for (int i = 0; i < uriArr.length; i++) {
                documentFileArr[i] = new x5(this, this.b, uriArr[i]);
            }
            return documentFileArr;
        } finally {
            a(cursorQuery);
        }
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean renameTo(String str) throws FileNotFoundException {
        try {
            Uri uriRenameDocument = DocumentsContract.renameDocument(this.b.getContentResolver(), this.c, str);
            if (uriRenameDocument != null) {
                this.c = uriRenameDocument;
                return true;
            }
        } catch (Exception unused) {
        }
        return false;
    }
}
