package org.greenrobot.greendao;

import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import defpackage.g9;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import org.greenrobot.greendao.annotation.apihint.Experimental;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;
import org.greenrobot.greendao.identityscope.IdentityScope;
import org.greenrobot.greendao.identityscope.IdentityScopeLong;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.internal.TableStatements;
import org.greenrobot.greendao.query.Query;
import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.rx.RxDao;
import rx.schedulers.Schedulers;

/* loaded from: classes.dex */
public abstract class AbstractDao<T, K> {
    public volatile RxDao<T, K> a;
    public volatile RxDao<T, K> b;
    public final DaoConfig config;

    /* renamed from: db, reason: collision with root package name */
    public final Database f7db;
    public final IdentityScope<K, T> identityScope;
    public final IdentityScopeLong<T> identityScopeLong;
    public final boolean isStandardSQLite;
    public final int pkOrdinal;
    public final AbstractDaoSession session;
    public final TableStatements statements;

    public AbstractDao(DaoConfig daoConfig) {
        this(daoConfig, null);
    }

    public final void a(DatabaseStatement databaseStatement, Iterable<T> iterable, boolean z) {
        this.f7db.beginTransaction();
        try {
            synchronized (databaseStatement) {
                if (this.identityScope != null) {
                    this.identityScope.lock();
                }
                try {
                    if (this.isStandardSQLite) {
                        SQLiteStatement sQLiteStatement = (SQLiteStatement) databaseStatement.getRawStatement();
                        for (T t : iterable) {
                            bindValues(sQLiteStatement, (SQLiteStatement) t);
                            if (z) {
                                updateKeyAfterInsertAndAttach(t, sQLiteStatement.executeInsert(), false);
                            } else {
                                sQLiteStatement.execute();
                            }
                        }
                    } else {
                        for (T t2 : iterable) {
                            bindValues(databaseStatement, (DatabaseStatement) t2);
                            if (z) {
                                updateKeyAfterInsertAndAttach(t2, databaseStatement.executeInsert(), false);
                            } else {
                                databaseStatement.execute();
                            }
                        }
                    }
                } finally {
                    if (this.identityScope != null) {
                        this.identityScope.unlock();
                    }
                }
            }
            this.f7db.setTransactionSuccessful();
        } finally {
            this.f7db.endTransaction();
        }
    }

    public void assertSinglePk() {
        if (this.config.pkColumns.length == 1) {
            return;
        }
        throw new DaoException(this + " (" + this.config.tablename + ") does not have a single-column primary key");
    }

    public void attachEntity(T t) {
    }

    public final void attachEntity(K k, T t, boolean z) {
        attachEntity(t);
        IdentityScope<K, T> identityScope = this.identityScope;
        if (identityScope == null || k == null) {
            return;
        }
        if (z) {
            identityScope.put(k, t);
        } else {
            identityScope.putNoLock(k, t);
        }
    }

    public final long b(T t, DatabaseStatement databaseStatement) {
        synchronized (databaseStatement) {
            if (!this.isStandardSQLite) {
                bindValues(databaseStatement, (DatabaseStatement) t);
                return databaseStatement.executeInsert();
            }
            SQLiteStatement sQLiteStatement = (SQLiteStatement) databaseStatement.getRawStatement();
            bindValues(sQLiteStatement, (SQLiteStatement) t);
            return sQLiteStatement.executeInsert();
        }
    }

    public abstract void bindValues(SQLiteStatement sQLiteStatement, T t);

    public abstract void bindValues(DatabaseStatement databaseStatement, T t);

    public long count() {
        return this.statements.getCountStatement().simpleQueryForLong();
    }

    public void delete(T t) {
        assertSinglePk();
        deleteByKey(getKeyVerified(t));
    }

    public void deleteAll() throws SQLException {
        Database database = this.f7db;
        StringBuilder sbA = g9.a("DELETE FROM '");
        sbA.append(this.config.tablename);
        sbA.append("'");
        database.execSQL(sbA.toString());
        IdentityScope<K, T> identityScope = this.identityScope;
        if (identityScope != null) {
            identityScope.clear();
        }
    }

    public void deleteByKey(K k) {
        assertSinglePk();
        DatabaseStatement deleteStatement = this.statements.getDeleteStatement();
        if (this.f7db.isDbLockedByCurrentThread()) {
            synchronized (deleteStatement) {
                a((AbstractDao<T, K>) k, deleteStatement);
            }
        } else {
            this.f7db.beginTransaction();
            try {
                synchronized (deleteStatement) {
                    a((AbstractDao<T, K>) k, deleteStatement);
                }
                this.f7db.setTransactionSuccessful();
            } finally {
                this.f7db.endTransaction();
            }
        }
        IdentityScope<K, T> identityScope = this.identityScope;
        if (identityScope != null) {
            identityScope.remove((IdentityScope<K, T>) k);
        }
    }

    public void deleteByKeyInTx(Iterable<K> iterable) {
        a((Iterable) null, iterable);
    }

    public void deleteInTx(Iterable<T> iterable) {
        a(iterable, (Iterable) null);
    }

    public boolean detach(T t) {
        if (this.identityScope == null) {
            return false;
        }
        return this.identityScope.detach(getKeyVerified(t), t);
    }

    public void detachAll() {
        IdentityScope<K, T> identityScope = this.identityScope;
        if (identityScope != null) {
            identityScope.clear();
        }
    }

    public String[] getAllColumns() {
        return this.config.allColumns;
    }

    public Database getDatabase() {
        return this.f7db;
    }

    public abstract K getKey(T t);

    public K getKeyVerified(T t) {
        K key = getKey(t);
        if (key != null) {
            return key;
        }
        if (t == null) {
            throw new NullPointerException("Entity may not be null");
        }
        throw new DaoException("Entity has no key");
    }

    public String[] getNonPkColumns() {
        return this.config.nonPkColumns;
    }

    public String[] getPkColumns() {
        return this.config.pkColumns;
    }

    public Property getPkProperty() {
        return this.config.pkProperty;
    }

    public Property[] getProperties() {
        return this.config.properties;
    }

    public AbstractDaoSession getSession() {
        return this.session;
    }

    public String getTablename() {
        return this.config.tablename;
    }

    public abstract boolean hasKey(T t);

    public long insert(T t) {
        return a((AbstractDao<T, K>) t, this.statements.getInsertStatement(), true);
    }

    public void insertInTx(Iterable<T> iterable) {
        insertInTx(iterable, isEntityUpdateable());
    }

    public long insertOrReplace(T t) {
        return a((AbstractDao<T, K>) t, this.statements.getInsertOrReplaceStatement(), true);
    }

    public void insertOrReplaceInTx(Iterable<T> iterable, boolean z) {
        a(this.statements.getInsertOrReplaceStatement(), iterable, z);
    }

    public long insertWithoutSettingPk(T t) {
        return a((AbstractDao<T, K>) t, this.statements.getInsertOrReplaceStatement(), false);
    }

    public abstract boolean isEntityUpdateable();

    public T load(K k) {
        T t;
        assertSinglePk();
        if (k == null) {
            return null;
        }
        IdentityScope<K, T> identityScope = this.identityScope;
        return (identityScope == null || (t = identityScope.get(k)) == null) ? loadUniqueAndCloseCursor(this.f7db.rawQuery(this.statements.getSelectByKey(), new String[]{k.toString()})) : t;
    }

    public List<T> loadAll() {
        return loadAllAndCloseCursor(this.f7db.rawQuery(this.statements.getSelectAll(), null));
    }

    public List<T> loadAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:39:? A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public java.util.List<T> loadAllFromCursor(android.database.Cursor r7) {
        /*
            r6 = this;
            int r0 = r7.getCount()
            if (r0 != 0) goto Lc
            java.util.ArrayList r7 = new java.util.ArrayList
            r7.<init>()
            return r7
        Lc:
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>(r0)
            r2 = 0
            boolean r3 = r7 instanceof android.database.CrossProcessCursor
            r4 = 0
            if (r3 == 0) goto L49
            r2 = r7
            android.database.CrossProcessCursor r2 = (android.database.CrossProcessCursor) r2
            android.database.CursorWindow r2 = r2.getWindow()
            if (r2 == 0) goto L49
            int r3 = r2.getNumRows()
            if (r3 != r0) goto L2d
            org.greenrobot.greendao.internal.FastCursor r7 = new org.greenrobot.greendao.internal.FastCursor
            r7.<init>(r2)
            r3 = 1
            goto L4a
        L2d:
            java.lang.String r3 = "Window vs. result size: "
            java.lang.StringBuilder r3 = defpackage.g9.a(r3)
            int r5 = r2.getNumRows()
            r3.append(r5)
            java.lang.String r5 = "/"
            r3.append(r5)
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            org.greenrobot.greendao.DaoLog.d(r3)
        L49:
            r3 = r4
        L4a:
            boolean r5 = r7.moveToFirst()
            if (r5 == 0) goto L86
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r5 = r6.identityScope
            if (r5 == 0) goto L5c
            r5.lock()
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r5 = r6.identityScope
            r5.reserveRoom(r0)
        L5c:
            if (r3 != 0) goto L68
            if (r2 == 0) goto L68
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r0 = r6.identityScope     // Catch: java.lang.Throwable -> L7d
            if (r0 == 0) goto L68
            r6.a(r7, r2, r1)     // Catch: java.lang.Throwable -> L7d
            goto L75
        L68:
            java.lang.Object r0 = r6.loadCurrent(r7, r4, r4)     // Catch: java.lang.Throwable -> L7d
            r1.add(r0)     // Catch: java.lang.Throwable -> L7d
            boolean r0 = r7.moveToNext()     // Catch: java.lang.Throwable -> L7d
            if (r0 != 0) goto L68
        L75:
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r7 = r6.identityScope
            if (r7 == 0) goto L86
            r7.unlock()
            goto L86
        L7d:
            r7 = move-exception
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r0 = r6.identityScope
            if (r0 == 0) goto L85
            r0.unlock()
        L85:
            throw r7
        L86:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.greenrobot.greendao.AbstractDao.loadAllFromCursor(android.database.Cursor):java.util.List");
    }

    public T loadByRowId(long j) {
        return loadUniqueAndCloseCursor(this.f7db.rawQuery(this.statements.getSelectByRowId(), new String[]{Long.toString(j)}));
    }

    public final T loadCurrent(Cursor cursor, int i, boolean z) {
        if (this.identityScopeLong != null) {
            if (i != 0 && cursor.isNull(this.pkOrdinal + i)) {
                return null;
            }
            long j = cursor.getLong(this.pkOrdinal + i);
            IdentityScopeLong<T> identityScopeLong = this.identityScopeLong;
            T t = z ? identityScopeLong.get2(j) : identityScopeLong.get2NoLock(j);
            if (t != null) {
                return t;
            }
            T entity = readEntity(cursor, i);
            attachEntity(entity);
            if (z) {
                this.identityScopeLong.put2(j, entity);
            } else {
                this.identityScopeLong.put2NoLock(j, entity);
            }
            return entity;
        }
        if (this.identityScope == null) {
            if (i != 0 && readKey(cursor, i) == null) {
                return null;
            }
            T entity2 = readEntity(cursor, i);
            attachEntity(entity2);
            return entity2;
        }
        K key = readKey(cursor, i);
        if (i != 0 && key == null) {
            return null;
        }
        IdentityScope<K, T> identityScope = this.identityScope;
        T noLock = z ? identityScope.get(key) : identityScope.getNoLock(key);
        if (noLock != null) {
            return noLock;
        }
        T entity3 = readEntity(cursor, i);
        attachEntity(key, entity3, z);
        return entity3;
    }

    public final <O> O loadCurrentOther(AbstractDao<O, ?> abstractDao, Cursor cursor, int i) {
        return abstractDao.loadCurrent(cursor, i, true);
    }

    public T loadUnique(Cursor cursor) {
        if (!cursor.moveToFirst()) {
            return null;
        }
        if (cursor.isLast()) {
            return loadCurrent(cursor, 0, true);
        }
        StringBuilder sbA = g9.a("Expected unique result, but count was ");
        sbA.append(cursor.getCount());
        throw new DaoException(sbA.toString());
    }

    public T loadUniqueAndCloseCursor(Cursor cursor) {
        try {
            return loadUnique(cursor);
        } finally {
            cursor.close();
        }
    }

    public QueryBuilder<T> queryBuilder() {
        return QueryBuilder.internalCreate(this);
    }

    public List<T> queryRaw(String str, String... strArr) {
        return loadAllAndCloseCursor(this.f7db.rawQuery(this.statements.getSelectAll() + str, strArr));
    }

    public Query<T> queryRawCreate(String str, Object... objArr) {
        return queryRawCreateListArgs(str, Arrays.asList(objArr));
    }

    public Query<T> queryRawCreateListArgs(String str, Collection<Object> collection) {
        return Query.internalCreate(this, this.statements.getSelectAll() + str, collection.toArray());
    }

    public abstract T readEntity(Cursor cursor, int i);

    public abstract void readEntity(Cursor cursor, T t, int i);

    public abstract K readKey(Cursor cursor, int i);

    public void refresh(T t) {
        assertSinglePk();
        K keyVerified = getKeyVerified(t);
        Cursor cursorRawQuery = this.f7db.rawQuery(this.statements.getSelectByKey(), new String[]{keyVerified.toString()});
        try {
            if (!cursorRawQuery.moveToFirst()) {
                throw new DaoException("Entity does not exist in the database anymore: " + t.getClass() + " with key " + keyVerified);
            }
            if (cursorRawQuery.isLast()) {
                readEntity(cursorRawQuery, t, 0);
                attachEntity(keyVerified, t, true);
            } else {
                throw new DaoException("Expected unique result, but count was " + cursorRawQuery.getCount());
            }
        } finally {
            cursorRawQuery.close();
        }
    }

    @Experimental
    public RxDao<T, K> rx() {
        if (this.a == null) {
            this.a = new RxDao<>(this, Schedulers.io());
        }
        return this.a;
    }

    @Experimental
    public RxDao<T, K> rxPlain() {
        if (this.b == null) {
            this.b = new RxDao<>(this);
        }
        return this.b;
    }

    public void save(T t) {
        if (hasKey(t)) {
            update(t);
        } else {
            insert(t);
        }
    }

    public void saveInTx(T... tArr) {
        saveInTx(Arrays.asList(tArr));
    }

    public void update(T t) {
        assertSinglePk();
        DatabaseStatement updateStatement = this.statements.getUpdateStatement();
        if (this.f7db.isDbLockedByCurrentThread()) {
            synchronized (updateStatement) {
                if (this.isStandardSQLite) {
                    updateInsideSynchronized((AbstractDao<T, K>) t, (SQLiteStatement) updateStatement.getRawStatement(), true);
                } else {
                    updateInsideSynchronized((AbstractDao<T, K>) t, updateStatement, true);
                }
            }
            return;
        }
        this.f7db.beginTransaction();
        try {
            synchronized (updateStatement) {
                updateInsideSynchronized((AbstractDao<T, K>) t, updateStatement, true);
            }
            this.f7db.setTransactionSuccessful();
        } finally {
            this.f7db.endTransaction();
        }
    }

    public void updateInTx(Iterable<T> iterable) {
        DatabaseStatement updateStatement = this.statements.getUpdateStatement();
        this.f7db.beginTransaction();
        try {
            synchronized (updateStatement) {
                if (this.identityScope != null) {
                    this.identityScope.lock();
                }
                try {
                    if (this.isStandardSQLite) {
                        SQLiteStatement sQLiteStatement = (SQLiteStatement) updateStatement.getRawStatement();
                        Iterator<T> it = iterable.iterator();
                        while (it.hasNext()) {
                            updateInsideSynchronized((AbstractDao<T, K>) it.next(), sQLiteStatement, false);
                        }
                    } else {
                        Iterator<T> it2 = iterable.iterator();
                        while (it2.hasNext()) {
                            updateInsideSynchronized((AbstractDao<T, K>) it2.next(), updateStatement, false);
                        }
                    }
                } finally {
                    if (this.identityScope != null) {
                        this.identityScope.unlock();
                    }
                }
            }
            this.f7db.setTransactionSuccessful();
        } catch (RuntimeException e) {
            e = e;
            try {
                this.f7db.endTransaction();
            } catch (RuntimeException e2) {
                DaoLog.w("Could not end transaction (rethrowing initial exception)", e2);
                throw e;
            }
        } catch (Throwable th) {
            try {
                this.f7db.endTransaction();
                throw th;
            } catch (RuntimeException e3) {
                throw e3;
            }
        }
        try {
            this.f7db.endTransaction();
            e = null;
            if (e != null) {
                throw e;
            }
        } catch (RuntimeException e4) {
            throw e4;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void updateInsideSynchronized(T t, DatabaseStatement databaseStatement, boolean z) {
        bindValues(databaseStatement, (DatabaseStatement) t);
        int length = this.config.allColumns.length + 1;
        Object key = getKey(t);
        if (key instanceof Long) {
            databaseStatement.bindLong(length, ((Long) key).longValue());
        } else {
            if (key == null) {
                throw new DaoException("Cannot update entity without key - was it inserted before?");
            }
            databaseStatement.bindString(length, key.toString());
        }
        databaseStatement.execute();
        attachEntity(key, t, z);
    }

    public abstract K updateKeyAfterInsert(T t, long j);

    public void updateKeyAfterInsertAndAttach(T t, long j, boolean z) {
        if (j != -1) {
            attachEntity(updateKeyAfterInsert(t, j), t, z);
        } else {
            DaoLog.w("Could not insert row (executeInsert returned -1)");
        }
    }

    public AbstractDao(DaoConfig daoConfig, AbstractDaoSession abstractDaoSession) {
        this.config = daoConfig;
        this.session = abstractDaoSession;
        Database database = daoConfig.f9db;
        this.f7db = database;
        this.isStandardSQLite = database.getRawDatabase() instanceof SQLiteDatabase;
        IdentityScopeLong<T> identityScopeLong = (IdentityScope<K, T>) daoConfig.getIdentityScope();
        this.identityScope = identityScopeLong;
        if (identityScopeLong instanceof IdentityScopeLong) {
            this.identityScopeLong = identityScopeLong;
        } else {
            this.identityScopeLong = null;
        }
        this.statements = daoConfig.statements;
        Property property = daoConfig.pkProperty;
        this.pkOrdinal = property != null ? property.ordinal : -1;
    }

    public void deleteByKeyInTx(K... kArr) {
        a((Iterable) null, Arrays.asList(kArr));
    }

    public void deleteInTx(T... tArr) {
        a(Arrays.asList(tArr), (Iterable) null);
    }

    public void insertInTx(T... tArr) {
        insertInTx(Arrays.asList(tArr), isEntityUpdateable());
    }

    public void saveInTx(Iterable<T> iterable) {
        Iterator<T> it = iterable.iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            if (hasKey(it.next())) {
                i++;
            } else {
                i2++;
            }
        }
        if (i <= 0 || i2 <= 0) {
            if (i2 > 0) {
                insertInTx(iterable);
                return;
            } else {
                if (i > 0) {
                    updateInTx(iterable);
                    return;
                }
                return;
            }
        }
        ArrayList arrayList = new ArrayList(i);
        ArrayList arrayList2 = new ArrayList(i2);
        for (T t : iterable) {
            if (hasKey(t)) {
                arrayList.add(t);
            } else {
                arrayList2.add(t);
            }
        }
        this.f7db.beginTransaction();
        try {
            updateInTx(arrayList);
            insertInTx(arrayList2);
            this.f7db.setTransactionSuccessful();
        } finally {
            this.f7db.endTransaction();
        }
    }

    public void insertInTx(Iterable<T> iterable, boolean z) {
        a(this.statements.getInsertStatement(), iterable, z);
    }

    public void insertOrReplaceInTx(Iterable<T> iterable) {
        insertOrReplaceInTx(iterable, isEntityUpdateable());
    }

    public void insertOrReplaceInTx(T... tArr) {
        insertOrReplaceInTx(Arrays.asList(tArr), isEntityUpdateable());
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void updateInsideSynchronized(T t, SQLiteStatement sQLiteStatement, boolean z) {
        bindValues(sQLiteStatement, (SQLiteStatement) t);
        int length = this.config.allColumns.length + 1;
        Object key = getKey(t);
        if (key instanceof Long) {
            sQLiteStatement.bindLong(length, ((Long) key).longValue());
        } else if (key != null) {
            sQLiteStatement.bindString(length, key.toString());
        } else {
            throw new DaoException("Cannot update entity without key - was it inserted before?");
        }
        sQLiteStatement.execute();
        attachEntity(key, t, z);
    }

    public final long a(T t, DatabaseStatement databaseStatement, boolean z) {
        long jB;
        if (this.f7db.isDbLockedByCurrentThread()) {
            jB = b(t, databaseStatement);
        } else {
            this.f7db.beginTransaction();
            try {
                jB = b(t, databaseStatement);
                this.f7db.setTransactionSuccessful();
            } finally {
                this.f7db.endTransaction();
            }
        }
        if (z) {
            updateKeyAfterInsertAndAttach(t, jB, true);
        }
        return jB;
    }

    public void updateInTx(T... tArr) {
        updateInTx(Arrays.asList(tArr));
    }

    public final void a(Cursor cursor, CursorWindow cursorWindow, List<T> list) {
        int numRows = cursorWindow.getNumRows() + cursorWindow.getStartPosition();
        int i = 0;
        while (true) {
            list.add(loadCurrent(cursor, 0, false));
            int i2 = i + 1;
            if (i2 >= numRows) {
                this.identityScope.unlock();
                try {
                    CursorWindow window = cursor.moveToNext() ? ((CrossProcessCursor) cursor).getWindow() : null;
                    if (window == null) {
                        return;
                    } else {
                        numRows = window.getNumRows() + window.getStartPosition();
                    }
                } finally {
                    this.identityScope.lock();
                }
            } else if (!cursor.moveToNext()) {
                return;
            }
            i = i2 + 1;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void a(K k, DatabaseStatement databaseStatement) {
        if (k instanceof Long) {
            databaseStatement.bindLong(1, ((Long) k).longValue());
        } else if (k != 0) {
            databaseStatement.bindString(1, k.toString());
        } else {
            throw new DaoException("Cannot delete entity, key is null");
        }
        databaseStatement.execute();
    }

    /* JADX WARN: Removed duplicated region for block: B:19:0x0040 A[Catch: all -> 0x003c, TryCatch #2 {all -> 0x003c, blocks: (B:10:0x0021, B:11:0x0025, B:13:0x002b, B:15:0x0038, B:19:0x0040, B:20:0x0044, B:22:0x004a, B:24:0x0053), top: B:49:0x0021, outer: #0 }] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0065 A[Catch: all -> 0x0081, Merged into TryCatch #1 {all -> 0x0084, blocks: (B:3:0x000e, B:34:0x006b, B:36:0x0072, B:38:0x0076, B:43:0x0083, B:4:0x000f, B:6:0x0013, B:30:0x0061, B:32:0x0065, B:33:0x006a, B:26:0x0057, B:28:0x005b, B:29:0x0060, B:10:0x0021, B:11:0x0025, B:13:0x002b, B:15:0x0038, B:19:0x0040, B:20:0x0044, B:22:0x004a, B:24:0x0053), top: B:48:0x000e }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(java.lang.Iterable<T> r4, java.lang.Iterable<K> r5) {
        /*
            r3 = this;
            r3.assertSinglePk()
            org.greenrobot.greendao.internal.TableStatements r0 = r3.statements
            org.greenrobot.greendao.database.DatabaseStatement r0 = r0.getDeleteStatement()
            org.greenrobot.greendao.database.Database r1 = r3.f7db
            r1.beginTransaction()
            monitor-enter(r0)     // Catch: java.lang.Throwable -> L84
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r1 = r3.identityScope     // Catch: java.lang.Throwable -> L81
            if (r1 == 0) goto L1e
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r1 = r3.identityScope     // Catch: java.lang.Throwable -> L81
            r1.lock()     // Catch: java.lang.Throwable -> L81
            java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> L81
            r1.<init>()     // Catch: java.lang.Throwable -> L81
            goto L1f
        L1e:
            r1 = 0
        L1f:
            if (r4 == 0) goto L3e
            java.util.Iterator r4 = r4.iterator()     // Catch: java.lang.Throwable -> L3c
        L25:
            boolean r2 = r4.hasNext()     // Catch: java.lang.Throwable -> L3c
            if (r2 == 0) goto L3e
            java.lang.Object r2 = r4.next()     // Catch: java.lang.Throwable -> L3c
            java.lang.Object r2 = r3.getKeyVerified(r2)     // Catch: java.lang.Throwable -> L3c
            r3.a(r2, r0)     // Catch: java.lang.Throwable -> L3c
            if (r1 == 0) goto L25
            r1.add(r2)     // Catch: java.lang.Throwable -> L3c
            goto L25
        L3c:
            r4 = move-exception
            goto L57
        L3e:
            if (r5 == 0) goto L61
            java.util.Iterator r4 = r5.iterator()     // Catch: java.lang.Throwable -> L3c
        L44:
            boolean r5 = r4.hasNext()     // Catch: java.lang.Throwable -> L3c
            if (r5 == 0) goto L61
            java.lang.Object r5 = r4.next()     // Catch: java.lang.Throwable -> L3c
            r3.a(r5, r0)     // Catch: java.lang.Throwable -> L3c
            if (r1 == 0) goto L44
            r1.add(r5)     // Catch: java.lang.Throwable -> L3c
            goto L44
        L57:
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r5 = r3.identityScope     // Catch: java.lang.Throwable -> L81
            if (r5 == 0) goto L60
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r5 = r3.identityScope     // Catch: java.lang.Throwable -> L81
            r5.unlock()     // Catch: java.lang.Throwable -> L81
        L60:
            throw r4     // Catch: java.lang.Throwable -> L81
        L61:
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r4 = r3.identityScope     // Catch: java.lang.Throwable -> L81
            if (r4 == 0) goto L6a
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r4 = r3.identityScope     // Catch: java.lang.Throwable -> L81
            r4.unlock()     // Catch: java.lang.Throwable -> L81
        L6a:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L81
            org.greenrobot.greendao.database.Database r4 = r3.f7db     // Catch: java.lang.Throwable -> L84
            r4.setTransactionSuccessful()     // Catch: java.lang.Throwable -> L84
            if (r1 == 0) goto L7b
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r4 = r3.identityScope     // Catch: java.lang.Throwable -> L84
            if (r4 == 0) goto L7b
            org.greenrobot.greendao.identityscope.IdentityScope<K, T> r4 = r3.identityScope     // Catch: java.lang.Throwable -> L84
            r4.remove(r1)     // Catch: java.lang.Throwable -> L84
        L7b:
            org.greenrobot.greendao.database.Database r4 = r3.f7db
            r4.endTransaction()
            return
        L81:
            r4 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L81
            throw r4     // Catch: java.lang.Throwable -> L84
        L84:
            r4 = move-exception
            org.greenrobot.greendao.database.Database r5 = r3.f7db
            r5.endTransaction()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: org.greenrobot.greendao.AbstractDao.a(java.lang.Iterable, java.lang.Iterable):void");
    }
}
