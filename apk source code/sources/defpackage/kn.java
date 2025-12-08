package defpackage;

import java.util.LinkedHashSet;
import java.util.Set;
import okhttp3.Route;

/* loaded from: classes.dex */
public final class kn {
    public final Set<Route> a = new LinkedHashSet();

    public synchronized void a(Route route) {
        this.a.remove(route);
    }

    public synchronized void b(Route route) {
        this.a.add(route);
    }

    public synchronized boolean c(Route route) {
        return this.a.contains(route);
    }
}
