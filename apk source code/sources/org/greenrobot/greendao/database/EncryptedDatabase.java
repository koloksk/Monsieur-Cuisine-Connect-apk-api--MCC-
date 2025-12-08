package org.greenrobot.greendao.database;

import android.database.Cursor;
import android.database.SQLException;
import net.sqlcipher.database.SQLiteDatabase;

/* loaded from: classes.dex */
public class EncryptedDatabase implements Database {
    public final SQLiteDatabase a;

    public EncryptedDatabase(SQLiteDatabase sQLiteDatabase) {
        this.a = sQLiteDatabase;
    }

    @Override // org.greenrobot.greendao.database.Database
    public void beginTransaction() {
        this.a.beginTransaction();
    }

    @Override // org.greenrobot.greendao.database.Database
    public void close() {
        this.a.close();
    }

    @Override // org.greenrobot.greendao.database.Database
    public DatabaseStatement compileStatement(String str) {
        return new EncryptedDatabaseStatement(this.a.compileStatement(str));
    }

    @Override // org.greenrobot.greendao.database.Database
    public void endTransaction() {
        this.a.endTransaction();
    }

    @Override // org.greenrobot.greendao.database.Database
    public void execSQL(String str) throws SQLException {
        this.a.execSQL(str);
    }

    @Override // org.greenrobot.greendao.database.Database
    public Object getRawDatabase() {
        return this.a;
    }

    public SQLiteDatabase getSQLiteDatabase() {
        return this.a;
    }

    @Override // org.greenrobot.greendao.database.Database
    public boolean inTransaction() {
        return this.a.inTransaction();
    }

    @Override // org.greenrobot.greendao.database.Database
    public boolean isDbLockedByCurrentThread() {
        return this.a.isDbLockedByCurrentThread();
    }

    @Override // org.greenrobot.greendao.database.Database
    public Cursor rawQuery(String str, String[] strArr) {
        return this.a.rawQuery(str, strArr);
    }

    @Override // org.greenrobot.greendao.database.Database
    public void setTransactionSuccessful() {
        this.a.setTransactionSuccessful();
    }

    @Override // org.greenrobot.greendao.database.Database
    public void execSQL(String str, Object[] objArr) throws SQLException {
        this.a.execSQL(str, objArr);
    }
}
