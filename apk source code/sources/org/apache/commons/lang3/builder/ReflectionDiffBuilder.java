package org.apache.commons.lang3.builder;

import defpackage.g9;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import org.apache.commons.lang3.reflect.FieldUtils;

/* loaded from: classes.dex */
public class ReflectionDiffBuilder implements Builder<DiffResult> {
    public final Object a;
    public final Object b;
    public final DiffBuilder c;

    public <T> ReflectionDiffBuilder(T t, T t2, ToStringStyle toStringStyle) {
        this.a = t;
        this.b = t2;
        this.c = new DiffBuilder(t, t2, toStringStyle);
    }

    @Override // org.apache.commons.lang3.builder.Builder
    public DiffResult build() {
        if (this.a.equals(this.b)) {
            return this.c.build();
        }
        for (Field field : FieldUtils.getAllFields(this.a.getClass())) {
            if ((field.getName().indexOf(36) == -1 && !Modifier.isTransient(field.getModifiers())) ? !Modifier.isStatic(field.getModifiers()) : false) {
                try {
                    this.c.append(field.getName(), FieldUtils.readField(field, this.a, true), FieldUtils.readField(field, this.b, true));
                } catch (IllegalAccessException e) {
                    StringBuilder sbA = g9.a("Unexpected IllegalAccessException: ");
                    sbA.append(e.getMessage());
                    throw new InternalError(sbA.toString());
                }
            }
        }
        return this.c.build();
    }
}
