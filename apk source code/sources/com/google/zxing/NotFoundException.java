package com.google.zxing;

/* loaded from: classes.dex */
public final class NotFoundException extends ReaderException {
    public static final NotFoundException a;

    static {
        NotFoundException notFoundException = new NotFoundException();
        a = notFoundException;
        notFoundException.setStackTrace(ReaderException.NO_TRACE);
    }

    public static NotFoundException getNotFoundInstance() {
        return a;
    }
}
