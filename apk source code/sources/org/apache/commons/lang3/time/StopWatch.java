package org.apache.commons.lang3.time;

import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class StopWatch {
    public c a = c.a;
    public b b = b.UNSPLIT;
    public long c;
    public long d;
    public long e;

    public enum b {
        SPLIT,
        UNSPLIT
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static abstract class c {
        public static final c a = new a("UNSTARTED", 0);
        public static final c b = new b("RUNNING", 1);
        public static final c c = new C0078c("STOPPED", 2);
        public static final c d;
        public static final /* synthetic */ c[] e;

        public enum a extends c {
            public a(String str, int i) {
                super(str, i, null);
            }

            @Override // org.apache.commons.lang3.time.StopWatch.c
            public boolean a() {
                return false;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.c
            public boolean b() {
                return true;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.c
            public boolean c() {
                return false;
            }
        }

        public enum b extends c {
            public b(String str, int i) {
                super(str, i, null);
            }

            @Override // org.apache.commons.lang3.time.StopWatch.c
            public boolean a() {
                return true;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.c
            public boolean b() {
                return false;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.c
            public boolean c() {
                return false;
            }
        }

        /* renamed from: org.apache.commons.lang3.time.StopWatch$c$c, reason: collision with other inner class name */
        public enum C0078c extends c {
            public C0078c(String str, int i) {
                super(str, i, null);
            }

            @Override // org.apache.commons.lang3.time.StopWatch.c
            public boolean a() {
                return false;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.c
            public boolean b() {
                return true;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.c
            public boolean c() {
                return false;
            }
        }

        public enum d extends c {
            public d(String str, int i) {
                super(str, i, null);
            }

            @Override // org.apache.commons.lang3.time.StopWatch.c
            public boolean a() {
                return true;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.c
            public boolean b() {
                return false;
            }

            @Override // org.apache.commons.lang3.time.StopWatch.c
            public boolean c() {
                return true;
            }
        }

        static {
            d dVar = new d("SUSPENDED", 3);
            d = dVar;
            e = new c[]{a, b, c, dVar};
        }

        public /* synthetic */ c(String str, int i, a aVar) {
        }

        public static c valueOf(String str) {
            return (c) Enum.valueOf(c.class, str);
        }

        public static c[] values() {
            return (c[]) e.clone();
        }

        public abstract boolean a();

        public abstract boolean b();

        public abstract boolean c();
    }

    public static StopWatch createStarted() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        return stopWatch;
    }

    public long getNanoTime() {
        long jNanoTime;
        long j;
        c cVar = this.a;
        if (cVar == c.c || cVar == c.d) {
            jNanoTime = this.e;
            j = this.c;
        } else {
            if (cVar == c.a) {
                return 0L;
            }
            if (cVar != c.b) {
                throw new RuntimeException("Illegal running state has occurred.");
            }
            jNanoTime = System.nanoTime();
            j = this.c;
        }
        return jNanoTime - j;
    }

    public long getSplitNanoTime() {
        if (this.b == b.SPLIT) {
            return this.e - this.c;
        }
        throw new IllegalStateException("Stopwatch must be split to get the split time. ");
    }

    public long getSplitTime() {
        return getSplitNanoTime() / 1000000;
    }

    public long getStartTime() {
        if (this.a != c.a) {
            return this.d;
        }
        throw new IllegalStateException("Stopwatch has not been started");
    }

    public long getTime() {
        return getNanoTime() / 1000000;
    }

    public boolean isStarted() {
        return this.a.a();
    }

    public boolean isStopped() {
        return this.a.b();
    }

    public boolean isSuspended() {
        return this.a.c();
    }

    public void reset() {
        this.a = c.a;
        this.b = b.UNSPLIT;
    }

    public void resume() {
        if (this.a != c.d) {
            throw new IllegalStateException("Stopwatch must be suspended to resume. ");
        }
        this.c = (System.nanoTime() - this.e) + this.c;
        this.a = c.b;
    }

    public void split() {
        if (this.a != c.b) {
            throw new IllegalStateException("Stopwatch is not running. ");
        }
        this.e = System.nanoTime();
        this.b = b.SPLIT;
    }

    public void start() {
        c cVar = this.a;
        if (cVar == c.c) {
            throw new IllegalStateException("Stopwatch must be reset before being restarted. ");
        }
        if (cVar != c.a) {
            throw new IllegalStateException("Stopwatch already started. ");
        }
        this.c = System.nanoTime();
        this.d = System.currentTimeMillis();
        this.a = c.b;
    }

    public void stop() {
        c cVar = this.a;
        if (cVar != c.b && cVar != c.d) {
            throw new IllegalStateException("Stopwatch is not running. ");
        }
        if (this.a == c.b) {
            this.e = System.nanoTime();
        }
        this.a = c.c;
    }

    public void suspend() {
        if (this.a != c.b) {
            throw new IllegalStateException("Stopwatch must be running to suspend. ");
        }
        this.e = System.nanoTime();
        this.a = c.d;
    }

    public String toSplitString() {
        return DurationFormatUtils.formatDurationHMS(getSplitTime());
    }

    public String toString() {
        return DurationFormatUtils.formatDurationHMS(getTime());
    }

    public void unsplit() {
        if (this.b != b.SPLIT) {
            throw new IllegalStateException("Stopwatch has not been split. ");
        }
        this.b = b.UNSPLIT;
    }

    public long getTime(TimeUnit timeUnit) {
        return timeUnit.convert(getNanoTime(), TimeUnit.NANOSECONDS);
    }
}
