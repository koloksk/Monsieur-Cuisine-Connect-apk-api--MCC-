package android.arch.lifecycle;

import android.arch.core.internal.FastSafeIterableMap;
import android.arch.lifecycle.Lifecycle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Map;

/* loaded from: classes.dex */
public class LifecycleRegistry extends Lifecycle {
    public final WeakReference<LifecycleOwner> c;
    public FastSafeIterableMap<LifecycleObserver, a> a = new FastSafeIterableMap<>();
    public int d = 0;
    public boolean e = false;
    public boolean f = false;
    public ArrayList<Lifecycle.State> g = new ArrayList<>();
    public Lifecycle.State b = Lifecycle.State.INITIALIZED;

    public static class a {
        public Lifecycle.State a;
        public GenericLifecycleObserver b;

        public a(LifecycleObserver lifecycleObserver, Lifecycle.State state) {
            this.b = Lifecycling.a(lifecycleObserver);
            this.a = state;
        }

        public void a(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
            Lifecycle.State stateA = LifecycleRegistry.a(event);
            this.a = LifecycleRegistry.a(this.a, stateA);
            this.b.onStateChanged(lifecycleOwner, event);
            this.a = stateA;
        }
    }

    public LifecycleRegistry(@NonNull LifecycleOwner lifecycleOwner) {
        this.c = new WeakReference<>(lifecycleOwner);
    }

    public static Lifecycle.Event b(Lifecycle.State state) {
        int iOrdinal = state.ordinal();
        if (iOrdinal == 0 || iOrdinal == 1) {
            return Lifecycle.Event.ON_CREATE;
        }
        if (iOrdinal == 2) {
            return Lifecycle.Event.ON_START;
        }
        if (iOrdinal == 3) {
            return Lifecycle.Event.ON_RESUME;
        }
        if (iOrdinal == 4) {
            throw new IllegalArgumentException();
        }
        throw new IllegalArgumentException("Unexpected state value " + state);
    }

    public final void a(Lifecycle.State state) {
        if (this.b == state) {
            return;
        }
        this.b = state;
        if (this.e || this.d != 0) {
            this.f = true;
            return;
        }
        this.e = true;
        b();
        this.e = false;
    }

    @Override // android.arch.lifecycle.Lifecycle
    public void addObserver(@NonNull LifecycleObserver lifecycleObserver) {
        LifecycleOwner lifecycleOwner;
        Lifecycle.State state = this.b;
        Lifecycle.State state2 = Lifecycle.State.DESTROYED;
        if (state != state2) {
            state2 = Lifecycle.State.INITIALIZED;
        }
        a aVar = new a(lifecycleObserver, state2);
        if (this.a.putIfAbsent(lifecycleObserver, aVar) == null && (lifecycleOwner = this.c.get()) != null) {
            boolean z = this.d != 0 || this.e;
            Lifecycle.State stateA = a(lifecycleObserver);
            this.d++;
            while (aVar.a.compareTo(stateA) < 0 && this.a.contains(lifecycleObserver)) {
                this.g.add(aVar.a);
                aVar.a(lifecycleOwner, b(aVar.a));
                a();
                stateA = a(lifecycleObserver);
            }
            if (!z) {
                b();
            }
            this.d--;
        }
    }

    @Override // android.arch.lifecycle.Lifecycle
    @NonNull
    public Lifecycle.State getCurrentState() {
        return this.b;
    }

    public int getObserverCount() {
        return this.a.size();
    }

    public void handleLifecycleEvent(@NonNull Lifecycle.Event event) {
        a(a(event));
    }

    @MainThread
    public void markState(@NonNull Lifecycle.State state) {
        a(state);
    }

    @Override // android.arch.lifecycle.Lifecycle
    public void removeObserver(@NonNull LifecycleObserver lifecycleObserver) {
        this.a.remove(lifecycleObserver);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:13:0x003f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void b() {
        /*
            Method dump skipped, instructions count: 329
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.arch.lifecycle.LifecycleRegistry.b():void");
    }

    public final Lifecycle.State a(LifecycleObserver lifecycleObserver) {
        Map.Entry<LifecycleObserver, a> entryCeil = this.a.ceil(lifecycleObserver);
        Lifecycle.State state = null;
        Lifecycle.State state2 = entryCeil != null ? entryCeil.getValue().a : null;
        if (!this.g.isEmpty()) {
            state = this.g.get(r0.size() - 1);
        }
        return a(a(this.b, state2), state);
    }

    public final void a() {
        this.g.remove(r0.size() - 1);
    }

    public static Lifecycle.State a(Lifecycle.Event event) {
        int iOrdinal = event.ordinal();
        if (iOrdinal != 0) {
            if (iOrdinal != 1) {
                if (iOrdinal == 2) {
                    return Lifecycle.State.RESUMED;
                }
                if (iOrdinal != 3) {
                    if (iOrdinal != 4) {
                        if (iOrdinal == 5) {
                            return Lifecycle.State.DESTROYED;
                        }
                        throw new IllegalArgumentException("Unexpected event value " + event);
                    }
                }
            }
            return Lifecycle.State.STARTED;
        }
        return Lifecycle.State.CREATED;
    }

    public static Lifecycle.State a(@NonNull Lifecycle.State state, @Nullable Lifecycle.State state2) {
        return (state2 == null || state2.compareTo(state) >= 0) ? state : state2;
    }
}
