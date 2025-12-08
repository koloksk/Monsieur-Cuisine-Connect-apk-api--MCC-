package defpackage;

import android.support.v4.util.ArraySet;
import java.util.Map;

/* JADX INFO: Add missing generic type declarations: [E] */
/* loaded from: classes.dex */
public class y5<E> extends a6<E, E> {
    public final /* synthetic */ ArraySet d;

    public y5(ArraySet arraySet) {
        this.d = arraySet;
    }

    @Override // defpackage.a6
    public Object a(int i, int i2) {
        return this.d.b[i];
    }

    @Override // defpackage.a6
    public int b(Object obj) {
        return this.d.indexOf(obj);
    }

    @Override // defpackage.a6
    public int c() {
        return this.d.c;
    }

    @Override // defpackage.a6
    public Map<E, E> b() {
        throw new UnsupportedOperationException("not a map");
    }

    @Override // defpackage.a6
    public int a(Object obj) {
        return this.d.indexOf(obj);
    }

    @Override // defpackage.a6
    public void a(E e, E e2) {
        this.d.add(e);
    }

    @Override // defpackage.a6
    public E a(int i, E e) {
        throw new UnsupportedOperationException("not a map");
    }

    @Override // defpackage.a6
    public void a(int i) {
        this.d.removeAt(i);
    }

    @Override // defpackage.a6
    public void a() {
        this.d.clear();
    }
}
