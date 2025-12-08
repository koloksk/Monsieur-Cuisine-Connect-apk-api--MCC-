package defpackage;

import org.greenrobot.greendao.AbstractDao;

/* loaded from: classes.dex */
public abstract class ro<T> extends po<T> {
    public final int limitPosition;
    public final int offsetPosition;

    public ro(AbstractDao<T, ?> abstractDao, String str, String[] strArr, int i, int i2) {
        super(abstractDao, str, strArr);
        this.limitPosition = i;
        this.offsetPosition = i2;
    }

    public void setLimit(int i) {
        checkThread();
        int i2 = this.limitPosition;
        if (i2 == -1) {
            throw new IllegalStateException("Limit must be set with QueryBuilder before it can be used here");
        }
        this.parameters[i2] = Integer.toString(i);
    }

    public void setOffset(int i) {
        checkThread();
        int i2 = this.offsetPosition;
        if (i2 == -1) {
            throw new IllegalStateException("Offset must be set with QueryBuilder before it can be used here");
        }
        this.parameters[i2] = Integer.toString(i);
    }

    @Override // defpackage.po
    public ro<T> setParameter(int i, Object obj) {
        if (i < 0 || !(i == this.limitPosition || i == this.offsetPosition)) {
            return (ro) super.setParameter(i, obj);
        }
        throw new IllegalArgumentException(g9.b("Illegal parameter index: ", i));
    }
}
