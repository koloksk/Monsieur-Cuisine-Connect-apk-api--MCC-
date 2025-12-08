package org.greenrobot.greendao.test;

import android.app.Application;
import android.app.Instrumentation;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import defpackage.g9;
import java.util.Random;
import org.greenrobot.greendao.DaoLog;
import org.greenrobot.greendao.DbUtils;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;

/* loaded from: classes.dex */
public abstract class DbTest extends AndroidTestCase {
    public static final String DB_NAME = "greendao-unittest-db.temp";
    public Application a;

    /* renamed from: db, reason: collision with root package name */
    public Database f10db;
    public final boolean inMemory;
    public final Random random;

    public DbTest() {
        this(true);
    }

    public <T extends Application> T createApplication(Class<T> cls) {
        AndroidTestCase.assertNull("Application already created", this.a);
        try {
            T t = (T) Instrumentation.newApplication(cls, getContext());
            t.onCreate();
            this.a = t;
            return t;
        } catch (Exception e) {
            throw new RuntimeException("Could not create application " + cls, e);
        }
    }

    public Database createDatabase() {
        SQLiteDatabase sQLiteDatabaseOpenOrCreateDatabase;
        if (this.inMemory) {
            sQLiteDatabaseOpenOrCreateDatabase = SQLiteDatabase.create(null);
        } else {
            getContext().deleteDatabase(DB_NAME);
            sQLiteDatabaseOpenOrCreateDatabase = getContext().openOrCreateDatabase(DB_NAME, 0, null);
        }
        return new StandardDatabase(sQLiteDatabaseOpenOrCreateDatabase);
    }

    public <T extends Application> T getApplication() {
        AndroidTestCase.assertNotNull("Application not yet created", this.a);
        return (T) this.a;
    }

    public void logTableDump(String str) {
        Database database = this.f10db;
        if (database instanceof StandardDatabase) {
            DbUtils.logTableDump(((StandardDatabase) database).getSQLiteDatabase(), str);
            return;
        }
        StringBuilder sbA = g9.a("Table dump unsupported for ");
        sbA.append(this.f10db);
        DaoLog.w(sbA.toString());
    }

    public void setUp() throws Exception {
        super.setUp();
        this.f10db = createDatabase();
    }

    public void tearDown() throws Exception {
        if (this.a != null) {
            terminateApplication();
        }
        this.f10db.close();
        if (!this.inMemory) {
            getContext().deleteDatabase(DB_NAME);
        }
        super.tearDown();
    }

    public void terminateApplication() {
        AndroidTestCase.assertNotNull("Application not yet created", this.a);
        this.a.onTerminate();
        this.a = null;
    }

    public DbTest(boolean z) {
        this.inMemory = z;
        this.random = new Random();
    }
}
