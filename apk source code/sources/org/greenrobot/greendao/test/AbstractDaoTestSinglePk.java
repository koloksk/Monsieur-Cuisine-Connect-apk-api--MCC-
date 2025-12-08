package org.greenrobot.greendao.test;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.support.media.ExifInterface;
import android.test.AndroidTestCase;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.DaoLog;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;

/* loaded from: classes.dex */
public abstract class AbstractDaoTestSinglePk<D extends AbstractDao<T, K>, T, K> extends AbstractDaoTest<D, T, K> {
    public Property b;
    public Set<K> usedPks;

    public AbstractDaoTestSinglePk(Class<D> cls) {
        super(cls);
        this.usedPks = new HashSet();
    }

    public boolean checkKeyIsNullable() {
        if (createEntity(null) != null) {
            return true;
        }
        DaoLog.d("Test is not available for entities with non-null keys");
        return false;
    }

    public abstract T createEntity(K k);

    public T createEntityWithRandomPk() {
        return createEntity(nextPk());
    }

    public abstract K createRandomPk();

    public K nextPk() {
        for (int i = 0; i < 100000; i++) {
            K kCreateRandomPk = createRandomPk();
            if (this.usedPks.add(kCreateRandomPk)) {
                return kCreateRandomPk;
            }
        }
        throw new IllegalStateException("Could not find a new PK");
    }

    public Cursor queryWithDummyColumnsInFront(int i, String str, K k) {
        StringBuilder sb = new StringBuilder("SELECT ");
        for (int i2 = 0; i2 < i; i2++) {
            sb.append(str);
            sb.append(",");
        }
        SqlUtils.appendColumns(sb, ExifInterface.GPS_DIRECTION_TRUE, this.dao.getAllColumns()).append(" FROM ");
        sb.append('\"');
        sb.append(this.dao.getTablename());
        sb.append('\"');
        sb.append(" T");
        if (k != null) {
            sb.append(" WHERE ");
            AndroidTestCase.assertEquals(1, this.dao.getPkColumns().length);
            sb.append(this.dao.getPkColumns()[0]);
            sb.append("=");
            DatabaseUtils.appendValueToSql(sb, k);
        }
        Cursor cursorRawQuery = this.f10db.rawQuery(sb.toString(), null);
        AndroidTestCase.assertTrue(cursorRawQuery.moveToFirst());
        for (int i3 = 0; i3 < i; i3++) {
            try {
                AndroidTestCase.assertEquals(str, cursorRawQuery.getString(i3));
            } catch (RuntimeException e) {
                cursorRawQuery.close();
                throw e;
            }
        }
        if (k != null) {
            AndroidTestCase.assertEquals(1, cursorRawQuery.getCount());
        }
        return cursorRawQuery;
    }

    public void runLoadPkTest(int i) {
        K kNextPk = nextPk();
        this.dao.insert(createEntity(kNextPk));
        Cursor cursorQueryWithDummyColumnsInFront = queryWithDummyColumnsInFront(i, "42", kNextPk);
        try {
            AndroidTestCase.assertEquals(kNextPk, this.daoAccess.readKey(cursorQueryWithDummyColumnsInFront, i));
        } finally {
            cursorQueryWithDummyColumnsInFront.close();
        }
    }

    @Override // org.greenrobot.greendao.test.AbstractDaoTest, org.greenrobot.greendao.test.DbTest
    public void setUp() throws Exception {
        super.setUp();
        for (Property property : this.daoAccess.getProperties()) {
            if (property.primaryKey) {
                if (this.b != null) {
                    throw new RuntimeException("Test does not work with multiple PK columns");
                }
                this.b = property;
            }
        }
        if (this.b == null) {
            throw new RuntimeException("Test does not work without a PK column");
        }
    }

    public void testCount() throws SQLException {
        this.dao.deleteAll();
        AndroidTestCase.assertEquals(0L, this.dao.count());
        this.dao.insert(createEntityWithRandomPk());
        AndroidTestCase.assertEquals(1L, this.dao.count());
        this.dao.insert(createEntityWithRandomPk());
        AndroidTestCase.assertEquals(2L, this.dao.count());
    }

    public void testDelete() {
        K kNextPk = nextPk();
        this.dao.deleteByKey(kNextPk);
        this.dao.insert(createEntity(kNextPk));
        AndroidTestCase.assertNotNull(this.dao.load(kNextPk));
        this.dao.deleteByKey(kNextPk);
        AndroidTestCase.assertNull(this.dao.load(kNextPk));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void testDeleteAll() throws SQLException {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            arrayList.add(createEntityWithRandomPk());
        }
        this.dao.insertInTx(arrayList);
        this.dao.deleteAll();
        AndroidTestCase.assertEquals(0L, this.dao.count());
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object key = this.daoAccess.getKey(it.next());
            AndroidTestCase.assertNotNull(key);
            AndroidTestCase.assertNull(this.dao.load(key));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void testDeleteByKeyInTx() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            arrayList.add(createEntityWithRandomPk());
        }
        this.dao.insertInTx(arrayList);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(this.daoAccess.getKey(arrayList.get(0)));
        arrayList2.add(this.daoAccess.getKey(arrayList.get(3)));
        arrayList2.add(this.daoAccess.getKey(arrayList.get(4)));
        arrayList2.add(this.daoAccess.getKey(arrayList.get(8)));
        this.dao.deleteByKeyInTx(arrayList2);
        AndroidTestCase.assertEquals(arrayList.size() - arrayList2.size(), this.dao.count());
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            AndroidTestCase.assertNotNull(next);
            AndroidTestCase.assertNull(this.dao.load(next));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void testDeleteInTx() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 10; i++) {
            arrayList.add(createEntityWithRandomPk());
        }
        this.dao.insertInTx(arrayList);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(arrayList.get(0));
        arrayList2.add(arrayList.get(3));
        arrayList2.add(arrayList.get(4));
        arrayList2.add(arrayList.get(8));
        this.dao.deleteInTx(arrayList2);
        AndroidTestCase.assertEquals(arrayList.size() - arrayList2.size(), this.dao.count());
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            Object key = this.daoAccess.getKey(it.next());
            AndroidTestCase.assertNotNull(key);
            AndroidTestCase.assertNull(this.dao.load(key));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void testInsertAndLoad() {
        K kNextPk = nextPk();
        T tCreateEntity = createEntity(kNextPk);
        this.dao.insert(tCreateEntity);
        AndroidTestCase.assertEquals(kNextPk, this.daoAccess.getKey(tCreateEntity));
        Object objLoad = this.dao.load(kNextPk);
        AndroidTestCase.assertNotNull(objLoad);
        AndroidTestCase.assertEquals(this.daoAccess.getKey(tCreateEntity), this.daoAccess.getKey(objLoad));
    }

    public void testInsertInTx() throws SQLException {
        this.dao.deleteAll();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 20; i++) {
            arrayList.add(createEntityWithRandomPk());
        }
        this.dao.insertInTx(arrayList);
        AndroidTestCase.assertEquals(arrayList.size(), this.dao.count());
    }

    public void testInsertOrReplaceInTx() throws SQLException {
        this.dao.deleteAll();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (int i = 0; i < 20; i++) {
            T tCreateEntityWithRandomPk = createEntityWithRandomPk();
            if (i % 2 == 0) {
                arrayList.add(tCreateEntityWithRandomPk);
            }
            arrayList2.add(tCreateEntityWithRandomPk);
        }
        this.dao.insertOrReplaceInTx(arrayList);
        this.dao.insertOrReplaceInTx(arrayList2);
        AndroidTestCase.assertEquals(arrayList2.size(), this.dao.count());
    }

    public void testInsertOrReplaceTwice() {
        T tCreateEntityWithRandomPk = createEntityWithRandomPk();
        long jInsert = this.dao.insert(tCreateEntityWithRandomPk);
        long jInsertOrReplace = this.dao.insertOrReplace(tCreateEntityWithRandomPk);
        if (this.dao.getPkProperty().type == Long.class) {
            AndroidTestCase.assertEquals(jInsert, jInsertOrReplace);
        }
    }

    public void testInsertTwice() {
        T tCreateEntity = createEntity(nextPk());
        this.dao.insert(tCreateEntity);
        try {
            this.dao.insert(tCreateEntity);
            AndroidTestCase.fail("Inserting twice should not work");
        } catch (SQLException unused) {
        }
    }

    public void testLoadAll() throws SQLException {
        this.dao.deleteAll();
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < 15; i++) {
            arrayList.add(createEntity(nextPk()));
        }
        this.dao.insertInTx(arrayList);
        AndroidTestCase.assertEquals(arrayList.size(), this.dao.loadAll().size());
    }

    public void testLoadPk() {
        runLoadPkTest(0);
    }

    public void testLoadPkWithOffset() {
        runLoadPkTest(10);
    }

    public void testQuery() {
        this.dao.insert(createEntityWithRandomPk());
        K kNextPk = nextPk();
        this.dao.insert(createEntity(kNextPk));
        this.dao.insert(createEntityWithRandomPk());
        List<T> listQueryRaw = this.dao.queryRaw("WHERE " + this.dao.getPkColumns()[0] + "=?", kNextPk.toString());
        AndroidTestCase.assertEquals(1, listQueryRaw.size());
        AndroidTestCase.assertEquals(kNextPk, this.daoAccess.getKey(listQueryRaw.get(0)));
    }

    public void testReadWithOffset() {
        K kNextPk = nextPk();
        this.dao.insert(createEntity(kNextPk));
        Cursor cursorQueryWithDummyColumnsInFront = queryWithDummyColumnsInFront(5, "42", kNextPk);
        try {
            AndroidTestCase.assertEquals(kNextPk, this.daoAccess.getKey(this.daoAccess.readEntity(cursorQueryWithDummyColumnsInFront, 5)));
        } finally {
            cursorQueryWithDummyColumnsInFront.close();
        }
    }

    public void testRowId() {
        AndroidTestCase.assertTrue(this.dao.insert(createEntityWithRandomPk()) != this.dao.insert(createEntityWithRandomPk()));
    }

    public void testSave() throws SQLException {
        if (checkKeyIsNullable()) {
            this.dao.deleteAll();
            T tCreateEntity = createEntity(null);
            if (tCreateEntity != null) {
                this.dao.save(tCreateEntity);
                this.dao.save(tCreateEntity);
                AndroidTestCase.assertEquals(1L, this.dao.count());
            }
        }
    }

    public void testSaveInTx() throws SQLException {
        if (checkKeyIsNullable()) {
            this.dao.deleteAll();
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < 20; i++) {
                T tCreateEntity = createEntity(null);
                if (i % 2 == 0) {
                    arrayList.add(tCreateEntity);
                }
                arrayList2.add(tCreateEntity);
            }
            this.dao.saveInTx(arrayList);
            this.dao.saveInTx(arrayList2);
            AndroidTestCase.assertEquals(arrayList2.size(), this.dao.count());
        }
    }

    public void testUpdate() throws SQLException {
        this.dao.deleteAll();
        T tCreateEntityWithRandomPk = createEntityWithRandomPk();
        this.dao.insert(tCreateEntityWithRandomPk);
        this.dao.update(tCreateEntityWithRandomPk);
        AndroidTestCase.assertEquals(1L, this.dao.count());
    }
}
