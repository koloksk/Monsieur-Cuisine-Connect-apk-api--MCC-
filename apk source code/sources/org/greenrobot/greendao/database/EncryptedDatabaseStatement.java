package org.greenrobot.greendao.database;

import net.sqlcipher.database.SQLiteStatement;

/* loaded from: classes.dex */
public class EncryptedDatabaseStatement implements DatabaseStatement {
    public final SQLiteStatement a;

    public EncryptedDatabaseStatement(SQLiteStatement sQLiteStatement) {
        this.a = sQLiteStatement;
    }

    @Override // org.greenrobot.greendao.database.DatabaseStatement
    public void bindBlob(int i, byte[] bArr) {
        this.a.bindBlob(i, bArr);
    }

    @Override // org.greenrobot.greendao.database.DatabaseStatement
    public void bindDouble(int i, double d) {
        this.a.bindDouble(i, d);
    }

    @Override // org.greenrobot.greendao.database.DatabaseStatement
    public void bindLong(int i, long j) {
        this.a.bindLong(i, j);
    }

    @Override // org.greenrobot.greendao.database.DatabaseStatement
    public void bindNull(int i) {
        this.a.bindNull(i);
    }

    @Override // org.greenrobot.greendao.database.DatabaseStatement
    public void bindString(int i, String str) {
        this.a.bindString(i, str);
    }

    @Override // org.greenrobot.greendao.database.DatabaseStatement
    public void clearBindings() {
        this.a.clearBindings();
    }

    @Override // org.greenrobot.greendao.database.DatabaseStatement
    public void close() {
        this.a.close();
    }

    @Override // org.greenrobot.greendao.database.DatabaseStatement
    public void execute() {
        this.a.execute();
    }

    @Override // org.greenrobot.greendao.database.DatabaseStatement
    public long executeInsert() {
        return this.a.executeInsert();
    }

    @Override // org.greenrobot.greendao.database.DatabaseStatement
    public Object getRawStatement() {
        return this.a;
    }

    @Override // org.greenrobot.greendao.database.DatabaseStatement
    public long simpleQueryForLong() {
        return this.a.simpleQueryForLong();
    }
}
