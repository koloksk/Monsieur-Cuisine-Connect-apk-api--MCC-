package org.apache.commons.lang3.builder;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.Validate;

/* loaded from: classes.dex */
public class DiffResult implements Iterable<Diff<?>> {
    public static final String OBJECTS_SAME_STRING = "";
    public final List<Diff<?>> a;
    public final Object b;
    public final Object c;
    public final ToStringStyle d;

    public DiffResult(Object obj, Object obj2, List<Diff<?>> list, ToStringStyle toStringStyle) {
        Validate.isTrue(obj != null, "Left hand object cannot be null", new Object[0]);
        Validate.isTrue(obj2 != null, "Right hand object cannot be null", new Object[0]);
        Validate.isTrue(list != null, "List of differences cannot be null", new Object[0]);
        this.a = list;
        this.b = obj;
        this.c = obj2;
        if (toStringStyle == null) {
            this.d = ToStringStyle.DEFAULT_STYLE;
        } else {
            this.d = toStringStyle;
        }
    }

    public List<Diff<?>> getDiffs() {
        return Collections.unmodifiableList(this.a);
    }

    public int getNumberOfDiffs() {
        return this.a.size();
    }

    public ToStringStyle getToStringStyle() {
        return this.d;
    }

    @Override // java.lang.Iterable
    public Iterator<Diff<?>> iterator() {
        return this.a.iterator();
    }

    public String toString() {
        return toString(this.d);
    }

    public String toString(ToStringStyle toStringStyle) {
        if (this.a.isEmpty()) {
            return "";
        }
        ToStringBuilder toStringBuilder = new ToStringBuilder(this.b, toStringStyle);
        ToStringBuilder toStringBuilder2 = new ToStringBuilder(this.c, toStringStyle);
        for (Diff<?> diff : this.a) {
            toStringBuilder.append(diff.getFieldName(), diff.getLeft());
            toStringBuilder2.append(diff.getFieldName(), diff.getRight());
        }
        return String.format("%s %s %s", toStringBuilder.build(), "differs from", toStringBuilder2.build());
    }
}
