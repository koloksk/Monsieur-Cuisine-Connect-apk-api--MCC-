package org.apache.commons.lang3.concurrent;

import java.lang.Thread;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.lang3.Validate;

/* loaded from: classes.dex */
public class BasicThreadFactory implements ThreadFactory {
    public final AtomicLong a;
    public final ThreadFactory b;
    public final Thread.UncaughtExceptionHandler c;
    public final String d;
    public final Integer e;
    public final Boolean f;

    public static class Builder implements org.apache.commons.lang3.builder.Builder<BasicThreadFactory> {
        public ThreadFactory a;
        public Thread.UncaughtExceptionHandler b;
        public String c;
        public Integer d;
        public Boolean e;

        public Builder daemon(boolean z) {
            this.e = Boolean.valueOf(z);
            return this;
        }

        public Builder namingPattern(String str) {
            Validate.notNull(str, "Naming pattern must not be null!", new Object[0]);
            this.c = str;
            return this;
        }

        public Builder priority(int i) {
            this.d = Integer.valueOf(i);
            return this;
        }

        public void reset() {
            this.a = null;
            this.b = null;
            this.c = null;
            this.d = null;
            this.e = null;
        }

        public Builder uncaughtExceptionHandler(Thread.UncaughtExceptionHandler uncaughtExceptionHandler) {
            Validate.notNull(uncaughtExceptionHandler, "Uncaught exception handler must not be null!", new Object[0]);
            this.b = uncaughtExceptionHandler;
            return this;
        }

        public Builder wrappedFactory(ThreadFactory threadFactory) {
            Validate.notNull(threadFactory, "Wrapped ThreadFactory must not be null!", new Object[0]);
            this.a = threadFactory;
            return this;
        }

        @Override // org.apache.commons.lang3.builder.Builder
        public BasicThreadFactory build() {
            BasicThreadFactory basicThreadFactory = new BasicThreadFactory(this, null);
            reset();
            return basicThreadFactory;
        }
    }

    public /* synthetic */ BasicThreadFactory(Builder builder, a aVar) {
        ThreadFactory threadFactory = builder.a;
        if (threadFactory == null) {
            this.b = Executors.defaultThreadFactory();
        } else {
            this.b = threadFactory;
        }
        this.d = builder.c;
        this.e = builder.d;
        this.f = builder.e;
        this.c = builder.b;
        this.a = new AtomicLong();
    }

    public final Boolean getDaemonFlag() {
        return this.f;
    }

    public final String getNamingPattern() {
        return this.d;
    }

    public final Integer getPriority() {
        return this.e;
    }

    public long getThreadCount() {
        return this.a.get();
    }

    public final Thread.UncaughtExceptionHandler getUncaughtExceptionHandler() {
        return this.c;
    }

    public final ThreadFactory getWrappedFactory() {
        return this.b;
    }

    @Override // java.util.concurrent.ThreadFactory
    public Thread newThread(Runnable runnable) {
        Thread threadNewThread = getWrappedFactory().newThread(runnable);
        if (getNamingPattern() != null) {
            threadNewThread.setName(String.format(getNamingPattern(), Long.valueOf(this.a.incrementAndGet())));
        }
        if (getUncaughtExceptionHandler() != null) {
            threadNewThread.setUncaughtExceptionHandler(getUncaughtExceptionHandler());
        }
        if (getPriority() != null) {
            threadNewThread.setPriority(getPriority().intValue());
        }
        if (getDaemonFlag() != null) {
            threadNewThread.setDaemon(getDaemonFlag().booleanValue());
        }
        return threadNewThread;
    }
}
