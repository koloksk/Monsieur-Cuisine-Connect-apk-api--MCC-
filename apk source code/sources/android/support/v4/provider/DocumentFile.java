package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;
import android.provider.DocumentsContract;
import defpackage.v5;
import defpackage.w5;
import defpackage.x5;
import java.io.File;

/* loaded from: classes.dex */
public abstract class DocumentFile {
    public final DocumentFile a;

    public DocumentFile(DocumentFile documentFile) {
        this.a = documentFile;
    }

    public static DocumentFile fromFile(File file) {
        return new v5(null, file);
    }

    public static DocumentFile fromSingleUri(Context context, Uri uri) {
        return new w5(null, context, uri);
    }

    public static DocumentFile fromTreeUri(Context context, Uri uri) {
        return new x5(null, context, DocumentsContract.buildDocumentUriUsingTree(uri, DocumentsContract.getTreeDocumentId(uri)));
    }

    public static boolean isDocumentUri(Context context, Uri uri) {
        return DocumentsContract.isDocumentUri(context, uri);
    }

    public abstract boolean canRead();

    public abstract boolean canWrite();

    public abstract DocumentFile createDirectory(String str);

    public abstract DocumentFile createFile(String str, String str2);

    public abstract boolean delete();

    public abstract boolean exists();

    public DocumentFile findFile(String str) {
        for (DocumentFile documentFile : listFiles()) {
            if (str.equals(documentFile.getName())) {
                return documentFile;
            }
        }
        return null;
    }

    public abstract String getName();

    public DocumentFile getParentFile() {
        return this.a;
    }

    public abstract String getType();

    public abstract Uri getUri();

    public abstract boolean isDirectory();

    public abstract boolean isFile();

    public abstract boolean isVirtual();

    public abstract long lastModified();

    public abstract long length();

    public abstract DocumentFile[] listFiles();

    public abstract boolean renameTo(String str);
}
