package defpackage;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.DaoLog;
import org.greenrobot.greendao.async.AsyncOperation;
import org.greenrobot.greendao.async.AsyncOperationListener;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.Query;

/* loaded from: classes.dex */
public class oo implements Runnable, Handler.Callback {
    public static ExecutorService k = Executors.newCachedThreadPool();
    public volatile boolean b;
    public volatile AsyncOperationListener d;
    public volatile AsyncOperationListener e;
    public int g;
    public int h;
    public Handler i;
    public int j;
    public final BlockingQueue<AsyncOperation> a = new LinkedBlockingQueue();
    public volatile int c = 50;
    public volatile int f = 50;

    public void a(AsyncOperation asyncOperation) {
        synchronized (this) {
            int i = this.j + 1;
            this.j = i;
            asyncOperation.m = i;
            this.a.add(asyncOperation);
            this.g++;
            if (!this.b) {
                this.b = true;
                k.execute(this);
            }
        }
    }

    public synchronized void b() {
        while (!a()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new DaoException("Interrupted while waiting for all operations to complete", e);
            }
        }
    }

    public final void c(AsyncOperation asyncOperation) {
        asyncOperation.b();
        AsyncOperationListener asyncOperationListener = this.d;
        if (asyncOperationListener != null) {
            asyncOperationListener.onAsyncOperationCompleted(asyncOperation);
        }
        if (this.e != null) {
            if (this.i == null) {
                this.i = new Handler(Looper.getMainLooper(), this);
            }
            this.i.sendMessage(this.i.obtainMessage(1, asyncOperation));
        }
        synchronized (this) {
            int i = this.h + 1;
            this.h = i;
            if (i == this.g) {
                notifyAll();
            }
        }
    }

    @Override // android.os.Handler.Callback
    public boolean handleMessage(Message message) {
        AsyncOperationListener asyncOperationListener = this.e;
        if (asyncOperationListener == null) {
            return false;
        }
        asyncOperationListener.onAsyncOperationCompleted((AsyncOperation) message.obj);
        return false;
    }

    @Override // java.lang.Runnable
    public void run() {
        AsyncOperation asyncOperationPoll;
        while (true) {
            try {
                AsyncOperation asyncOperationPoll2 = this.a.poll(1L, TimeUnit.SECONDS);
                if (asyncOperationPoll2 == null) {
                    synchronized (this) {
                        asyncOperationPoll2 = this.a.poll();
                        if (asyncOperationPoll2 == null) {
                            return;
                        }
                    }
                }
                if (!asyncOperationPoll2.isMergeTx() || (asyncOperationPoll = this.a.poll(this.f, TimeUnit.MILLISECONDS)) == null) {
                    b(asyncOperationPoll2);
                    c(asyncOperationPoll2);
                } else if (asyncOperationPoll2.a(asyncOperationPoll)) {
                    a(asyncOperationPoll2, asyncOperationPoll);
                } else {
                    b(asyncOperationPoll2);
                    c(asyncOperationPoll2);
                    b(asyncOperationPoll);
                    c(asyncOperationPoll);
                }
            } catch (InterruptedException e) {
                DaoLog.w(Thread.currentThread().getName() + " was interruppted", e);
                return;
            } finally {
                this.b = false;
            }
        }
    }

    public final void b(AsyncOperation asyncOperation) {
        Database databaseA;
        asyncOperation.f = System.currentTimeMillis();
        try {
        } catch (Throwable th) {
            asyncOperation.i = th;
        }
        switch (asyncOperation.a) {
            case Insert:
                asyncOperation.b.insert(asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            case InsertInTxIterable:
                asyncOperation.b.insertInTx((Iterable<Object>) asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            case InsertInTxArray:
                asyncOperation.b.insertInTx((Object[]) asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            case InsertOrReplace:
                asyncOperation.b.insertOrReplace(asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            case InsertOrReplaceInTxIterable:
                asyncOperation.b.insertOrReplaceInTx((Iterable<Object>) asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            case InsertOrReplaceInTxArray:
                asyncOperation.b.insertOrReplaceInTx((Object[]) asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            case Update:
                asyncOperation.b.update(asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            case UpdateInTxIterable:
                asyncOperation.b.updateInTx((Iterable<Object>) asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            case UpdateInTxArray:
                asyncOperation.b.updateInTx((Object[]) asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            case Delete:
                asyncOperation.b.delete(asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            case DeleteInTxIterable:
                asyncOperation.b.deleteInTx((Iterable<Object>) asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            case DeleteInTxArray:
                asyncOperation.b.deleteInTx((Object[]) asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            case DeleteByKey:
                asyncOperation.b.deleteByKey(asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            case DeleteAll:
                asyncOperation.b.deleteAll();
                asyncOperation.g = System.currentTimeMillis();
                return;
            case TransactionRunnable:
                databaseA = asyncOperation.a();
                databaseA.beginTransaction();
                try {
                    ((Runnable) asyncOperation.d).run();
                    databaseA.setTransactionSuccessful();
                    databaseA.endTransaction();
                    asyncOperation.g = System.currentTimeMillis();
                    return;
                } finally {
                }
            case TransactionCallable:
                databaseA = asyncOperation.a();
                databaseA.beginTransaction();
                try {
                    asyncOperation.k = ((Callable) asyncOperation.d).call();
                    databaseA.setTransactionSuccessful();
                    databaseA.endTransaction();
                    asyncOperation.g = System.currentTimeMillis();
                    return;
                } finally {
                }
            case QueryList:
                asyncOperation.k = ((Query) asyncOperation.d).forCurrentThread().list();
                asyncOperation.g = System.currentTimeMillis();
                return;
            case QueryUnique:
                asyncOperation.k = ((Query) asyncOperation.d).forCurrentThread().unique();
                asyncOperation.g = System.currentTimeMillis();
                return;
            case Load:
                asyncOperation.k = asyncOperation.b.load(asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            case LoadAll:
                asyncOperation.k = asyncOperation.b.loadAll();
                asyncOperation.g = System.currentTimeMillis();
                return;
            case Count:
                asyncOperation.k = Long.valueOf(asyncOperation.b.count());
                asyncOperation.g = System.currentTimeMillis();
                return;
            case Refresh:
                asyncOperation.b.refresh(asyncOperation.d);
                asyncOperation.g = System.currentTimeMillis();
                return;
            default:
                throw new DaoException("Unsupported operation: " + asyncOperation.a);
        }
    }

    public synchronized boolean a() {
        return this.g == this.h;
    }

    public synchronized boolean a(int i) {
        if (!a()) {
            try {
                wait(i);
            } catch (InterruptedException e) {
                throw new DaoException("Interrupted while waiting for all operations to complete", e);
            }
        }
        return a();
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x005c, code lost:
    
        r8.setTransactionSuccessful();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0063, code lost:
    
        r4 = false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0064, code lost:
    
        r8.endTransaction();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0068, code lost:
    
        r8 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0069, code lost:
    
        org.greenrobot.greendao.DaoLog.i("Async transaction could not be ended, success so far was: " + r4, r8);
        r4 = false;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void a(org.greenrobot.greendao.async.AsyncOperation r8, org.greenrobot.greendao.async.AsyncOperation r9) {
        /*
            r7 = this;
            java.lang.String r0 = "Async transaction could not be ended, success so far was: "
            java.util.ArrayList r1 = new java.util.ArrayList
            r1.<init>()
            r1.add(r8)
            r1.add(r9)
            org.greenrobot.greendao.database.Database r8 = r8.a()
            r8.beginTransaction()
            r9 = 0
            r2 = r9
        L16:
            int r3 = r1.size()     // Catch: java.lang.Throwable -> Lc4
            r4 = 1
            if (r2 >= r3) goto L63
            java.lang.Object r3 = r1.get(r2)     // Catch: java.lang.Throwable -> Lc4
            org.greenrobot.greendao.async.AsyncOperation r3 = (org.greenrobot.greendao.async.AsyncOperation) r3     // Catch: java.lang.Throwable -> Lc4
            r7.b(r3)     // Catch: java.lang.Throwable -> Lc4
            boolean r5 = r3.isFailed()     // Catch: java.lang.Throwable -> Lc4
            if (r5 == 0) goto L2d
            goto L63
        L2d:
            int r5 = r1.size()     // Catch: java.lang.Throwable -> Lc4
            int r5 = r5 - r4
            if (r2 != r5) goto L60
            java.util.concurrent.BlockingQueue<org.greenrobot.greendao.async.AsyncOperation> r5 = r7.a     // Catch: java.lang.Throwable -> Lc4
            java.lang.Object r5 = r5.peek()     // Catch: java.lang.Throwable -> Lc4
            org.greenrobot.greendao.async.AsyncOperation r5 = (org.greenrobot.greendao.async.AsyncOperation) r5     // Catch: java.lang.Throwable -> Lc4
            int r6 = r7.c     // Catch: java.lang.Throwable -> Lc4
            if (r2 >= r6) goto L5c
            boolean r3 = r3.a(r5)     // Catch: java.lang.Throwable -> Lc4
            if (r3 == 0) goto L5c
            java.util.concurrent.BlockingQueue<org.greenrobot.greendao.async.AsyncOperation> r3 = r7.a     // Catch: java.lang.Throwable -> Lc4
            java.lang.Object r3 = r3.remove()     // Catch: java.lang.Throwable -> Lc4
            org.greenrobot.greendao.async.AsyncOperation r3 = (org.greenrobot.greendao.async.AsyncOperation) r3     // Catch: java.lang.Throwable -> Lc4
            if (r3 != r5) goto L54
            r1.add(r3)     // Catch: java.lang.Throwable -> Lc4
            goto L60
        L54:
            org.greenrobot.greendao.DaoException r1 = new org.greenrobot.greendao.DaoException     // Catch: java.lang.Throwable -> Lc4
            java.lang.String r2 = "Internal error: peeked op did not match removed op"
            r1.<init>(r2)     // Catch: java.lang.Throwable -> Lc4
            throw r1     // Catch: java.lang.Throwable -> Lc4
        L5c:
            r8.setTransactionSuccessful()     // Catch: java.lang.Throwable -> Lc4
            goto L64
        L60:
            int r2 = r2 + 1
            goto L16
        L63:
            r4 = r9
        L64:
            r8.endTransaction()     // Catch: java.lang.RuntimeException -> L68
            goto L7c
        L68:
            r8 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            r2.append(r4)
            java.lang.String r0 = r2.toString()
            org.greenrobot.greendao.DaoLog.i(r0, r8)
            r4 = r9
        L7c:
            if (r4 == 0) goto L98
            int r8 = r1.size()
            java.util.Iterator r9 = r1.iterator()
        L86:
            boolean r0 = r9.hasNext()
            if (r0 == 0) goto Lc3
            java.lang.Object r0 = r9.next()
            org.greenrobot.greendao.async.AsyncOperation r0 = (org.greenrobot.greendao.async.AsyncOperation) r0
            r0.l = r8
            r7.c(r0)
            goto L86
        L98:
            java.lang.String r8 = "Reverted merged transaction because one of the operations failed. Executing operations one by one instead..."
            org.greenrobot.greendao.DaoLog.i(r8)
            java.util.Iterator r8 = r1.iterator()
        La1:
            boolean r0 = r8.hasNext()
            if (r0 == 0) goto Lc3
            java.lang.Object r0 = r8.next()
            org.greenrobot.greendao.async.AsyncOperation r0 = (org.greenrobot.greendao.async.AsyncOperation) r0
            r1 = 0
            r0.f = r1
            r0.g = r1
            r0.h = r9
            r1 = 0
            r0.i = r1
            r0.k = r1
            r0.l = r9
            r7.b(r0)
            r7.c(r0)
            goto La1
        Lc3:
            return
        Lc4:
            r1 = move-exception
            r8.endTransaction()     // Catch: java.lang.RuntimeException -> Lc9
            goto Ldc
        Lc9:
            r8 = move-exception
            java.lang.StringBuilder r2 = new java.lang.StringBuilder
            r2.<init>()
            r2.append(r0)
            r2.append(r9)
            java.lang.String r9 = r2.toString()
            org.greenrobot.greendao.DaoLog.i(r9, r8)
        Ldc:
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.oo.a(org.greenrobot.greendao.async.AsyncOperation, org.greenrobot.greendao.async.AsyncOperation):void");
    }
}
