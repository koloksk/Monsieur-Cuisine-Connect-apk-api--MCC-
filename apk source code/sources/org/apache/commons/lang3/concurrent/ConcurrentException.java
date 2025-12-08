package org.apache.commons.lang3.concurrent;

/* loaded from: classes.dex */
public class ConcurrentException extends Exception {
    public static final long serialVersionUID = 6622707671812226130L;

    public ConcurrentException() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConcurrentException(Throwable th) {
        super(th);
        ConcurrentUtils.a(th);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConcurrentException(String str, Throwable th) {
        super(str, th);
        ConcurrentUtils.a(th);
    }
}
