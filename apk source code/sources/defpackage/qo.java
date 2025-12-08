package defpackage;

import defpackage.po;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.greenrobot.greendao.AbstractDao;

/* loaded from: classes.dex */
public abstract class qo<T, Q extends po<T>> {
    public final String a;
    public final AbstractDao<T, ?> b;
    public final String[] c;
    public final Map<Long, WeakReference<Q>> d = new HashMap();

    public qo(AbstractDao<T, ?> abstractDao, String str, String[] strArr) {
        this.b = abstractDao;
        this.a = str;
        this.c = strArr;
    }

    public abstract Q a();

    public Q a(Q q) {
        if (Thread.currentThread() != q.ownerThread) {
            return (Q) b();
        }
        String[] strArr = this.c;
        System.arraycopy(strArr, 0, q.parameters, 0, strArr.length);
        return q;
    }

    public Q b() {
        Q q;
        long id = Thread.currentThread().getId();
        synchronized (this.d) {
            WeakReference<Q> weakReference = this.d.get(Long.valueOf(id));
            q = weakReference != null ? weakReference.get() : null;
            if (q == null) {
                c();
                q = (Q) a();
                this.d.put(Long.valueOf(id), new WeakReference<>(q));
            } else {
                System.arraycopy(this.c, 0, q.parameters, 0, this.c.length);
            }
        }
        return q;
    }

    public void c() {
        synchronized (this.d) {
            Iterator<Map.Entry<Long, WeakReference<Q>>> it = this.d.entrySet().iterator();
            while (it.hasNext()) {
                if (it.next().getValue().get() == null) {
                    it.remove();
                }
            }
        }
    }
}
