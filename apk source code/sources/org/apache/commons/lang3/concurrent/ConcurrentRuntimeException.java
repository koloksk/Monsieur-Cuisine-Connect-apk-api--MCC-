package org.apache.commons.lang3.concurrent;

/* loaded from: classes.dex */
public class ConcurrentRuntimeException extends RuntimeException {
    public static final long serialVersionUID = -6582182735562919670L;

    public ConcurrentRuntimeException() {
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConcurrentRuntimeException(Throwable th) {
        super(th);
        ConcurrentUtils.a(th);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConcurrentRuntimeException(String str, Throwable th) {
        super(str, th);
        ConcurrentUtils.a(th);
    }
}
