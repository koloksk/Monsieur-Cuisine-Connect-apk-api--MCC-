package org.greenrobot.greendao.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes.dex */
public abstract class DatabaseOpenHelper extends SQLiteOpenHelper {
    public final Context a;
    public final String b;
    public final int c;
    public a d;
    public boolean e;

    public class a extends net.sqlcipher.database.SQLiteOpenHelper {
        public a(DatabaseOpenHelper databaseOpenHelper, Context context, String str, int i, boolean z) {
            super(context, str, (SQLiteDatabase.CursorFactory) null, i);
            if (z) {
                SQLiteDatabase.loadLibs(context);
            }
        }
    }

    public DatabaseOpenHelper(Context context, String str, int i) {
        this(context, str, null, i);
    }

    public final a a() {
        if (this.d == null) {
            this.d = new a(this, this.a, this.b, this.c, this.e);
        }
        return this.d;
    }

    public Database getEncryptedReadableDb(String str) {
        return new EncryptedDatabase(a().getReadableDatabase(str));
    }

    public Database getEncryptedWritableDb(String str) {
        return new EncryptedDatabase(a().getWritableDatabase(str));
    }

    public Database getReadableDb() {
        return wrap(getReadableDatabase());
    }

    public Database getWritableDb() {
        return wrap(getWritableDatabase());
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        onCreate(wrap(sQLiteDatabase));
    }

    public void onCreate(Database database) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onOpen(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        onOpen(wrap(sQLiteDatabase));
    }

    public void onOpen(Database database) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(android.database.sqlite.SQLiteDatabase sQLiteDatabase, int i, int i2) {
        onUpgrade(wrap(sQLiteDatabase), i, i2);
    }

    public void onUpgrade(Database database, int i, int i2) {
    }

    public void setLoadSQLCipherNativeLibs(boolean z) {
        this.e = z;
    }

    public Database wrap(android.database.sqlite.SQLiteDatabase sQLiteDatabase) {
        return new StandardDatabase(sQLiteDatabase);
    }

    public DatabaseOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i) {
        super(context, str, cursorFactory, i);
        this.e = true;
        this.a = context;
        this.b = str;
        this.c = i;
    }

    public Database getEncryptedReadableDb(char[] cArr) {
        return new EncryptedDatabase(a().getReadableDatabase(cArr));
    }

    public Database getEncryptedWritableDb(char[] cArr) {
        return new EncryptedDatabase(a().getWritableDatabase(cArr));
    }
}
