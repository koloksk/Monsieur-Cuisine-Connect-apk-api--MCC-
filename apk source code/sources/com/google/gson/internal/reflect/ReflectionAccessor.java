package com.google.gson.internal.reflect;

import com.google.gson.internal.JavaVersion;
import defpackage.ba;
import defpackage.ca;
import java.lang.reflect.AccessibleObject;

/* loaded from: classes.dex */
public abstract class ReflectionAccessor {
    public static final ReflectionAccessor a;

    static {
        a = JavaVersion.getMajorJavaVersion() < 9 ? new ba() : new ca();
    }

    public static ReflectionAccessor getInstance() {
        return a;
    }

    public abstract void makeAccessible(AccessibleObject accessibleObject);
}
