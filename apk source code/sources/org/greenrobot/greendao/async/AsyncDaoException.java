package org.greenrobot.greendao.async;

import org.greenrobot.greendao.DaoException;

/* loaded from: classes.dex */
public class AsyncDaoException extends DaoException {
    public static final long serialVersionUID = 5872157552005102382L;
    public final AsyncOperation a;

    public AsyncDaoException(AsyncOperation asyncOperation, Throwable th) {
        super(th);
        this.a = asyncOperation;
    }

    public AsyncOperation getFailedOperation() {
        return this.a;
    }
}
