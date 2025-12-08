package org.apache.commons.lang3.exception;

import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;

/* loaded from: classes.dex */
public class ContextedException extends Exception implements ExceptionContext {
    public static final long serialVersionUID = 20110706;
    public final ExceptionContext a;

    public ContextedException() {
        this.a = new DefaultExceptionContext();
    }

    @Override // org.apache.commons.lang3.exception.ExceptionContext
    public List<Pair<String, Object>> getContextEntries() {
        return this.a.getContextEntries();
    }

    @Override // org.apache.commons.lang3.exception.ExceptionContext
    public Set<String> getContextLabels() {
        return this.a.getContextLabels();
    }

    @Override // org.apache.commons.lang3.exception.ExceptionContext
    public List<Object> getContextValues(String str) {
        return this.a.getContextValues(str);
    }

    @Override // org.apache.commons.lang3.exception.ExceptionContext
    public Object getFirstContextValue(String str) {
        return this.a.getFirstContextValue(str);
    }

    @Override // org.apache.commons.lang3.exception.ExceptionContext
    public String getFormattedExceptionMessage(String str) {
        return this.a.getFormattedExceptionMessage(str);
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return getFormattedExceptionMessage(super.getMessage());
    }

    public String getRawMessage() {
        return super.getMessage();
    }

    @Override // org.apache.commons.lang3.exception.ExceptionContext
    public ContextedException addContextValue(String str, Object obj) {
        this.a.addContextValue(str, obj);
        return this;
    }

    @Override // org.apache.commons.lang3.exception.ExceptionContext
    public ContextedException setContextValue(String str, Object obj) {
        this.a.setContextValue(str, obj);
        return this;
    }

    public ContextedException(String str) {
        super(str);
        this.a = new DefaultExceptionContext();
    }

    public ContextedException(Throwable th) {
        super(th);
        this.a = new DefaultExceptionContext();
    }

    public ContextedException(String str, Throwable th) {
        super(str, th);
        this.a = new DefaultExceptionContext();
    }

    public ContextedException(String str, Throwable th, ExceptionContext exceptionContext) {
        super(str, th);
        this.a = exceptionContext == null ? new DefaultExceptionContext() : exceptionContext;
    }
}
