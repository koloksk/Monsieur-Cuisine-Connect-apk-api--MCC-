package org.greenrobot.greendao.async;

import defpackage.oo;
import java.util.concurrent.Callable;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.query.Query;

/* loaded from: classes.dex */
public class AsyncSession {
    public final AbstractDaoSession a;
    public final oo b = new oo();
    public int c;

    public AsyncSession(AbstractDaoSession abstractDaoSession) {
        this.a = abstractDaoSession;
    }

    public final AsyncOperation a(AsyncOperation.OperationType operationType, Object obj, int i) {
        AsyncOperation asyncOperation = new AsyncOperation(operationType, null, this.a.getDatabase(), obj, i | this.c);
        this.b.a(asyncOperation);
        return asyncOperation;
    }

    public AsyncOperation callInTx(Callable<?> callable) {
        return callInTx(callable, 0);
    }

    public AsyncOperation count(Class<?> cls) {
        return count(cls, 0);
    }

    public AsyncOperation delete(Object obj) {
        return delete(obj, 0);
    }

    public <E> AsyncOperation deleteAll(Class<E> cls) {
        return deleteAll(cls, 0);
    }

    public AsyncOperation deleteByKey(Object obj) {
        return deleteByKey(obj, 0);
    }

    public <E> AsyncOperation deleteInTx(Class<E> cls, E... eArr) {
        return deleteInTx(cls, 0, eArr);
    }

    public AsyncOperationListener getListener() {
        return this.b.d;
    }

    public AsyncOperationListener getListenerMainThread() {
        return this.b.e;
    }

    public int getMaxOperationCountToMerge() {
        return this.b.c;
    }

    public int getSessionFlags() {
        return this.c;
    }

    public int getWaitForMergeMillis() {
        return this.b.f;
    }

    public AsyncOperation insert(Object obj) {
        return insert(obj, 0);
    }

    public <E> AsyncOperation insertInTx(Class<E> cls, E... eArr) {
        return insertInTx(cls, 0, eArr);
    }

    public AsyncOperation insertOrReplace(Object obj) {
        return insertOrReplace(obj, 0);
    }

    public <E> AsyncOperation insertOrReplaceInTx(Class<E> cls, E... eArr) {
        return insertOrReplaceInTx(cls, 0, eArr);
    }

    public boolean isCompleted() {
        return this.b.a();
    }

    public AsyncOperation load(Class<?> cls, Object obj) {
        return load(cls, obj, 0);
    }

    public AsyncOperation loadAll(Class<?> cls) {
        return loadAll(cls, 0);
    }

    public AsyncOperation queryList(Query<?> query) {
        return queryList(query, 0);
    }

    public AsyncOperation queryUnique(Query<?> query) {
        return queryUnique(query, 0);
    }

    public AsyncOperation refresh(Object obj) {
        return refresh(obj, 0);
    }

    public AsyncOperation runInTx(Runnable runnable) {
        return runInTx(runnable, 0);
    }

    public void setListener(AsyncOperationListener asyncOperationListener) {
        this.b.d = asyncOperationListener;
    }

    public void setListenerMainThread(AsyncOperationListener asyncOperationListener) {
        this.b.e = asyncOperationListener;
    }

    public void setMaxOperationCountToMerge(int i) {
        this.b.c = i;
    }

    public void setSessionFlags(int i) {
        this.c = i;
    }

    public void setWaitForMergeMillis(int i) {
        this.b.f = i;
    }

    public AsyncOperation update(Object obj) {
        return update(obj, 0);
    }

    public <E> AsyncOperation updateInTx(Class<E> cls, E... eArr) {
        return updateInTx(cls, 0, eArr);
    }

    public void waitForCompletion() {
        this.b.b();
    }

    public AsyncOperation callInTx(Callable<?> callable, int i) {
        return a(AsyncOperation.OperationType.TransactionCallable, callable, i);
    }

    public AsyncOperation count(Class<?> cls, int i) {
        return a(AsyncOperation.OperationType.Count, cls, null, i);
    }

    public AsyncOperation delete(Object obj, int i) {
        return a(AsyncOperation.OperationType.Delete, obj.getClass(), obj, i);
    }

    public <E> AsyncOperation deleteAll(Class<E> cls, int i) {
        return a(AsyncOperation.OperationType.DeleteAll, cls, null, i);
    }

    public AsyncOperation deleteByKey(Object obj, int i) {
        return a(AsyncOperation.OperationType.DeleteByKey, obj.getClass(), obj, i);
    }

    public <E> AsyncOperation deleteInTx(Class<E> cls, int i, E... eArr) {
        return a(AsyncOperation.OperationType.DeleteInTxArray, cls, eArr, i);
    }

    public AsyncOperation insert(Object obj, int i) {
        return a(AsyncOperation.OperationType.Insert, obj.getClass(), obj, i);
    }

    public <E> AsyncOperation insertInTx(Class<E> cls, int i, E... eArr) {
        return a(AsyncOperation.OperationType.InsertInTxArray, cls, eArr, i);
    }

    public AsyncOperation insertOrReplace(Object obj, int i) {
        return a(AsyncOperation.OperationType.InsertOrReplace, obj.getClass(), obj, i);
    }

    public <E> AsyncOperation insertOrReplaceInTx(Class<E> cls, int i, E... eArr) {
        return a(AsyncOperation.OperationType.InsertOrReplaceInTxArray, cls, eArr, i);
    }

    public AsyncOperation load(Class<?> cls, Object obj, int i) {
        return a(AsyncOperation.OperationType.Load, cls, obj, i);
    }

    public AsyncOperation loadAll(Class<?> cls, int i) {
        return a(AsyncOperation.OperationType.LoadAll, cls, null, i);
    }

    public AsyncOperation queryList(Query<?> query, int i) {
        return a(AsyncOperation.OperationType.QueryList, query, i);
    }

    public AsyncOperation queryUnique(Query<?> query, int i) {
        return a(AsyncOperation.OperationType.QueryUnique, query, i);
    }

    public AsyncOperation refresh(Object obj, int i) {
        return a(AsyncOperation.OperationType.Refresh, obj.getClass(), obj, i);
    }

    public AsyncOperation runInTx(Runnable runnable, int i) {
        return a(AsyncOperation.OperationType.TransactionRunnable, runnable, i);
    }

    public AsyncOperation update(Object obj, int i) {
        return a(AsyncOperation.OperationType.Update, obj.getClass(), obj, i);
    }

    public <E> AsyncOperation updateInTx(Class<E> cls, int i, E... eArr) {
        return a(AsyncOperation.OperationType.UpdateInTxArray, cls, eArr, i);
    }

    public boolean waitForCompletion(int i) {
        return this.b.a(i);
    }

    public <E> AsyncOperation deleteInTx(Class<E> cls, Iterable<E> iterable) {
        return deleteInTx(cls, iterable, 0);
    }

    public <E> AsyncOperation insertInTx(Class<E> cls, Iterable<E> iterable) {
        return insertInTx(cls, iterable, 0);
    }

    public <E> AsyncOperation insertOrReplaceInTx(Class<E> cls, Iterable<E> iterable) {
        return insertOrReplaceInTx(cls, iterable, 0);
    }

    public <E> AsyncOperation updateInTx(Class<E> cls, Iterable<E> iterable) {
        return updateInTx(cls, iterable, 0);
    }

    public final <E> AsyncOperation a(AsyncOperation.OperationType operationType, Class<E> cls, Object obj, int i) {
        AsyncOperation asyncOperation = new AsyncOperation(operationType, this.a.getDao(cls), null, obj, i | this.c);
        this.b.a(asyncOperation);
        return asyncOperation;
    }

    public <E> AsyncOperation deleteInTx(Class<E> cls, Iterable<E> iterable, int i) {
        return a(AsyncOperation.OperationType.DeleteInTxIterable, cls, iterable, i);
    }

    public <E> AsyncOperation insertInTx(Class<E> cls, Iterable<E> iterable, int i) {
        return a(AsyncOperation.OperationType.InsertInTxIterable, cls, iterable, i);
    }

    public <E> AsyncOperation insertOrReplaceInTx(Class<E> cls, Iterable<E> iterable, int i) {
        return a(AsyncOperation.OperationType.InsertOrReplaceInTxIterable, cls, iterable, i);
    }

    public <E> AsyncOperation updateInTx(Class<E> cls, Iterable<E> iterable, int i) {
        return a(AsyncOperation.OperationType.UpdateInTxIterable, cls, iterable, i);
    }
}
