package defpackage;

import android.net.Uri;
import android.support.v4.provider.DocumentFile;
import android.util.Log;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class v5 extends DocumentFile {
    public File b;

    public v5(DocumentFile documentFile, File file) {
        super(documentFile);
        this.b = file;
    }

    public static boolean a(File file) {
        File[] fileArrListFiles = file.listFiles();
        boolean zA = true;
        if (fileArrListFiles != null) {
            for (File file2 : fileArrListFiles) {
                if (file2.isDirectory()) {
                    zA &= a(file2);
                }
                if (!file2.delete()) {
                    Log.w("DocumentFile", "Failed to delete " + file2);
                    zA = false;
                }
            }
        }
        return zA;
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean canRead() {
        return this.b.canRead();
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean canWrite() {
        return this.b.canWrite();
    }

    @Override // android.support.v4.provider.DocumentFile
    public DocumentFile createDirectory(String str) {
        File file = new File(this.b, str);
        if (file.isDirectory() || file.mkdir()) {
            return new v5(this, file);
        }
        return null;
    }

    @Override // android.support.v4.provider.DocumentFile
    public DocumentFile createFile(String str, String str2) throws IOException {
        String extensionFromMimeType = MimeTypeMap.getSingleton().getExtensionFromMimeType(str);
        if (extensionFromMimeType != null) {
            str2 = g9.a(str2, ".", extensionFromMimeType);
        }
        File file = new File(this.b, str2);
        try {
            file.createNewFile();
            return new v5(this, file);
        } catch (IOException e) {
            Log.w("DocumentFile", "Failed to createFile: " + e);
            return null;
        }
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean delete() {
        a(this.b);
        return this.b.delete();
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean exists() {
        return this.b.exists();
    }

    @Override // android.support.v4.provider.DocumentFile
    public String getName() {
        return this.b.getName();
    }

    @Override // android.support.v4.provider.DocumentFile
    public String getType() {
        if (this.b.isDirectory()) {
            return null;
        }
        String name = this.b.getName();
        int iLastIndexOf = name.lastIndexOf(46);
        if (iLastIndexOf >= 0) {
            String mimeTypeFromExtension = MimeTypeMap.getSingleton().getMimeTypeFromExtension(name.substring(iLastIndexOf + 1).toLowerCase());
            if (mimeTypeFromExtension != null) {
                return mimeTypeFromExtension;
            }
        }
        return "application/octet-stream";
    }

    @Override // android.support.v4.provider.DocumentFile
    public Uri getUri() {
        return Uri.fromFile(this.b);
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean isDirectory() {
        return this.b.isDirectory();
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean isFile() {
        return this.b.isFile();
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean isVirtual() {
        return false;
    }

    @Override // android.support.v4.provider.DocumentFile
    public long lastModified() {
        return this.b.lastModified();
    }

    @Override // android.support.v4.provider.DocumentFile
    public long length() {
        return this.b.length();
    }

    @Override // android.support.v4.provider.DocumentFile
    public DocumentFile[] listFiles() {
        ArrayList arrayList = new ArrayList();
        File[] fileArrListFiles = this.b.listFiles();
        if (fileArrListFiles != null) {
            for (File file : fileArrListFiles) {
                arrayList.add(new v5(this, file));
            }
        }
        return (DocumentFile[]) arrayList.toArray(new DocumentFile[arrayList.size()]);
    }

    @Override // android.support.v4.provider.DocumentFile
    public boolean renameTo(String str) {
        File file = new File(this.b.getParentFile(), str);
        if (!this.b.renameTo(file)) {
            return false;
        }
        this.b = file;
        return true;
    }
}
