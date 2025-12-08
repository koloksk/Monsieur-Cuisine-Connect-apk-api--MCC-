package android.support.v4.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import defpackage.g9;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class Pair<F, S> {

    @Nullable
    public final F first;

    @Nullable
    public final S second;

    public Pair(@Nullable F f, @Nullable S s) {
        this.first = f;
        this.second = s;
    }

    @NonNull
    public static <A, B> Pair<A, B> create(@Nullable A a, @Nullable B b) {
        return new Pair<>(a, b);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Pair)) {
            return false;
        }
        Pair pair = (Pair) obj;
        F f = pair.first;
        F f2 = this.first;
        if (!(f == f2 || (f != null && f.equals(f2)))) {
            return false;
        }
        S s = pair.second;
        S s2 = this.second;
        return s == s2 || (s != null && s.equals(s2));
    }

    public int hashCode() {
        F f = this.first;
        int iHashCode = f == null ? 0 : f.hashCode();
        S s = this.second;
        return iHashCode ^ (s != null ? s.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sbA = g9.a("Pair{");
        sbA.append(String.valueOf(this.first));
        sbA.append(StringUtils.SPACE);
        sbA.append(String.valueOf(this.second));
        sbA.append("}");
        return sbA.toString();
    }
}
