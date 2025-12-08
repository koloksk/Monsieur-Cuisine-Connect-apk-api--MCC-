package org.apache.commons.lang3.concurrent;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.lang3.concurrent.AbstractCircuitBreaker;

/* loaded from: classes.dex */
public class EventCountCircuitBreaker extends AbstractCircuitBreaker<Integer> {
    public static final Map<AbstractCircuitBreaker.State, c> g;
    public final AtomicReference<b> b;
    public final int c;
    public final long d;
    public final int e;
    public final long f;

    public static class b {
        public final int a;
        public final long b;

        public b(int i, long j) {
            this.a = i;
            this.b = j;
        }
    }

    public static abstract class c {
        public c() {
        }

        public abstract long a(EventCountCircuitBreaker eventCountCircuitBreaker);

        public abstract boolean a(EventCountCircuitBreaker eventCountCircuitBreaker, b bVar, b bVar2);

        public /* synthetic */ c(a aVar) {
        }
    }

    static {
        EnumMap enumMap = new EnumMap(AbstractCircuitBreaker.State.class);
        a aVar = null;
        enumMap.put((EnumMap) AbstractCircuitBreaker.State.CLOSED, (AbstractCircuitBreaker.State) new d(aVar));
        enumMap.put((EnumMap) AbstractCircuitBreaker.State.OPEN, (AbstractCircuitBreaker.State) new e(aVar));
        g = enumMap;
    }

    public EventCountCircuitBreaker(int i, long j, TimeUnit timeUnit, int i2, long j2, TimeUnit timeUnit2) {
        this.b = new AtomicReference<>(new b(0, 0L));
        this.c = i;
        this.d = timeUnit.toNanos(j);
        this.e = i2;
        this.f = timeUnit2.toNanos(j2);
    }

    public final boolean a(int i) {
        AbstractCircuitBreaker.State stateOppositeState;
        b bVar;
        b bVar2;
        do {
            long jNanoTime = System.nanoTime();
            stateOppositeState = this.state.get();
            bVar = this.b.get();
            c cVar = g.get(stateOppositeState);
            if (cVar == null) {
                throw null;
            }
            bVar2 = ((jNanoTime - bVar.b) > cVar.a(this) ? 1 : ((jNanoTime - bVar.b) == cVar.a(this) ? 0 : -1)) > 0 ? new b(i, jNanoTime) : i == 0 ? bVar : new b(bVar.a + i, bVar.b);
        } while (!(bVar == bVar2 || this.b.compareAndSet(bVar, bVar2)));
        if (g.get(stateOppositeState).a(this, bVar, bVar2)) {
            stateOppositeState = stateOppositeState.oppositeState();
            changeState(stateOppositeState);
            this.b.set(new b(0, System.nanoTime()));
        }
        return !AbstractCircuitBreaker.isOpen(stateOppositeState);
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker, org.apache.commons.lang3.concurrent.CircuitBreaker
    public boolean checkState() {
        return a(0);
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker, org.apache.commons.lang3.concurrent.CircuitBreaker
    public void close() {
        super.close();
        this.b.set(new b(0, System.nanoTime()));
    }

    public long getClosingInterval() {
        return this.f;
    }

    public int getClosingThreshold() {
        return this.e;
    }

    public long getOpeningInterval() {
        return this.d;
    }

    public int getOpeningThreshold() {
        return this.c;
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker, org.apache.commons.lang3.concurrent.CircuitBreaker
    public void open() {
        super.open();
        this.b.set(new b(0, System.nanoTime()));
    }

    public static class d extends c {
        public /* synthetic */ d(a aVar) {
            super(null);
        }

        @Override // org.apache.commons.lang3.concurrent.EventCountCircuitBreaker.c
        public boolean a(EventCountCircuitBreaker eventCountCircuitBreaker, b bVar, b bVar2) {
            return bVar2.a > eventCountCircuitBreaker.getOpeningThreshold();
        }

        @Override // org.apache.commons.lang3.concurrent.EventCountCircuitBreaker.c
        public long a(EventCountCircuitBreaker eventCountCircuitBreaker) {
            return eventCountCircuitBreaker.getOpeningInterval();
        }
    }

    @Override // org.apache.commons.lang3.concurrent.AbstractCircuitBreaker, org.apache.commons.lang3.concurrent.CircuitBreaker
    public boolean incrementAndCheckState(Integer num) {
        return a(num.intValue());
    }

    public boolean incrementAndCheckState() {
        return incrementAndCheckState((Integer) 1);
    }

    public static class e extends c {
        public /* synthetic */ e(a aVar) {
            super(null);
        }

        @Override // org.apache.commons.lang3.concurrent.EventCountCircuitBreaker.c
        public boolean a(EventCountCircuitBreaker eventCountCircuitBreaker, b bVar, b bVar2) {
            return bVar2.b != bVar.b && bVar.a < eventCountCircuitBreaker.getClosingThreshold();
        }

        @Override // org.apache.commons.lang3.concurrent.EventCountCircuitBreaker.c
        public long a(EventCountCircuitBreaker eventCountCircuitBreaker) {
            return eventCountCircuitBreaker.getClosingInterval();
        }
    }

    public EventCountCircuitBreaker(int i, long j, TimeUnit timeUnit, int i2) {
        this(i, j, timeUnit, i2, j, timeUnit);
    }

    public EventCountCircuitBreaker(int i, long j, TimeUnit timeUnit) {
        this(i, j, timeUnit, i);
    }
}
