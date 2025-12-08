package com.google.gson.reflect;

import com.google.gson.internal.C$Gson$Preconditions;
import com.google.gson.internal.C$Gson$Types;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import org.apache.commons.lang3.ClassUtils;

/* loaded from: classes.dex */
public class TypeToken<T> {
    public final Class<? super T> a;
    public final Type b;
    public final int c;

    public TypeToken() {
        Type genericSuperclass = getClass().getGenericSuperclass();
        if (genericSuperclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        Type typeCanonicalize = C$Gson$Types.canonicalize(((ParameterizedType) genericSuperclass).getActualTypeArguments()[0]);
        this.b = typeCanonicalize;
        this.a = (Class<? super T>) C$Gson$Types.getRawType(typeCanonicalize);
        this.c = this.b.hashCode();
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x008c A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean a(java.lang.reflect.Type r9, java.lang.reflect.ParameterizedType r10, java.util.Map<java.lang.String, java.lang.reflect.Type> r11) {
        /*
            r0 = 0
            if (r9 != 0) goto L4
            return r0
        L4:
            boolean r1 = r10.equals(r9)
            r2 = 1
            if (r1 == 0) goto Lc
            return r2
        Lc:
            java.lang.Class r1 = com.google.gson.internal.C$Gson$Types.getRawType(r9)
            r3 = 0
            boolean r4 = r9 instanceof java.lang.reflect.ParameterizedType
            if (r4 == 0) goto L18
            r3 = r9
            java.lang.reflect.ParameterizedType r3 = (java.lang.reflect.ParameterizedType) r3
        L18:
            if (r3 == 0) goto L8d
            java.lang.reflect.Type[] r9 = r3.getActualTypeArguments()
            java.lang.reflect.TypeVariable[] r4 = r1.getTypeParameters()
            r5 = r0
        L23:
            int r6 = r9.length
            if (r5 >= r6) goto L45
            r6 = r9[r5]
            r7 = r4[r5]
        L2a:
            boolean r8 = r6 instanceof java.lang.reflect.TypeVariable
            if (r8 == 0) goto L3b
            java.lang.reflect.TypeVariable r6 = (java.lang.reflect.TypeVariable) r6
            java.lang.String r6 = r6.getName()
            java.lang.Object r6 = r11.get(r6)
            java.lang.reflect.Type r6 = (java.lang.reflect.Type) r6
            goto L2a
        L3b:
            java.lang.String r7 = r7.getName()
            r11.put(r7, r6)
            int r5 = r5 + 1
            goto L23
        L45:
            java.lang.reflect.Type r9 = r3.getRawType()
            java.lang.reflect.Type r4 = r10.getRawType()
            boolean r9 = r9.equals(r4)
            if (r9 == 0) goto L89
            java.lang.reflect.Type[] r9 = r3.getActualTypeArguments()
            java.lang.reflect.Type[] r3 = r10.getActualTypeArguments()
            r4 = r0
        L5c:
            int r5 = r9.length
            if (r4 >= r5) goto L87
            r5 = r9[r4]
            r6 = r3[r4]
            boolean r7 = r6.equals(r5)
            if (r7 != 0) goto L80
            boolean r7 = r5 instanceof java.lang.reflect.TypeVariable
            if (r7 == 0) goto L7e
            java.lang.reflect.TypeVariable r5 = (java.lang.reflect.TypeVariable) r5
            java.lang.String r5 = r5.getName()
            java.lang.Object r5 = r11.get(r5)
            boolean r5 = r6.equals(r5)
            if (r5 == 0) goto L7e
            goto L80
        L7e:
            r5 = r0
            goto L81
        L80:
            r5 = r2
        L81:
            if (r5 != 0) goto L84
            goto L89
        L84:
            int r4 = r4 + 1
            goto L5c
        L87:
            r9 = r2
            goto L8a
        L89:
            r9 = r0
        L8a:
            if (r9 == 0) goto L8d
            return r2
        L8d:
            java.lang.reflect.Type[] r9 = r1.getGenericInterfaces()
            int r3 = r9.length
        L92:
            if (r0 >= r3) goto La5
            r4 = r9[r0]
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>(r11)
            boolean r4 = a(r4, r10, r5)
            if (r4 == 0) goto La2
            return r2
        La2:
            int r0 = r0 + 1
            goto L92
        La5:
            java.lang.reflect.Type r9 = r1.getGenericSuperclass()
            java.util.HashMap r0 = new java.util.HashMap
            r0.<init>(r11)
            boolean r9 = a(r9, r10, r0)
            return r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.reflect.TypeToken.a(java.lang.reflect.Type, java.lang.reflect.ParameterizedType, java.util.Map):boolean");
    }

    public static TypeToken<?> get(Type type) {
        return new TypeToken<>(type);
    }

    public static TypeToken<?> getArray(Type type) {
        return new TypeToken<>(C$Gson$Types.arrayOf(type));
    }

    public static TypeToken<?> getParameterized(Type type, Type... typeArr) {
        return new TypeToken<>(C$Gson$Types.newParameterizedTypeWithOwner(null, type, typeArr));
    }

    public final boolean equals(Object obj) {
        return (obj instanceof TypeToken) && C$Gson$Types.equals(this.b, ((TypeToken) obj).b);
    }

    public final Class<? super T> getRawType() {
        return this.a;
    }

    public final Type getType() {
        return this.b;
    }

    public final int hashCode() {
        return this.c;
    }

    @Deprecated
    public boolean isAssignableFrom(Class<?> cls) {
        return isAssignableFrom((Type) cls);
    }

    public final String toString() {
        return C$Gson$Types.typeToString(this.b);
    }

    public static <T> TypeToken<T> get(Class<T> cls) {
        return new TypeToken<>(cls);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.Object, java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r6v12, types: [java.lang.Class] */
    /* JADX WARN: Type inference failed for: r6v14, types: [java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r6v17, types: [java.lang.reflect.Type] */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v22 */
    @Deprecated
    public boolean isAssignableFrom(Type type) {
        boolean zA;
        if (type == 0) {
            return false;
        }
        if (this.b.equals(type)) {
            return true;
        }
        Type type2 = this.b;
        if (type2 instanceof Class) {
            return this.a.isAssignableFrom(C$Gson$Types.getRawType(type));
        }
        if (type2 instanceof ParameterizedType) {
            return a(type, (ParameterizedType) type2, new HashMap());
        }
        if (!(type2 instanceof GenericArrayType)) {
            Class[] clsArr = {Class.class, ParameterizedType.class, GenericArrayType.class};
            StringBuilder sb = new StringBuilder("Unexpected type. Expected one of: ");
            for (int i = 0; i < 3; i++) {
                sb.append(clsArr[i].getName());
                sb.append(", ");
            }
            sb.append("but got: ");
            sb.append(type2.getClass().getName());
            sb.append(", for type token: ");
            sb.append(type2.toString());
            sb.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
            throw new AssertionError(sb.toString());
        }
        if (!this.a.isAssignableFrom(C$Gson$Types.getRawType(type))) {
            return false;
        }
        Type genericComponentType = ((GenericArrayType) this.b).getGenericComponentType();
        if (genericComponentType instanceof ParameterizedType) {
            if (type instanceof GenericArrayType) {
                type = ((GenericArrayType) type).getGenericComponentType();
            } else if (type instanceof Class) {
                type = (Class) type;
                while (type.isArray()) {
                    type = type.getComponentType();
                }
            }
            zA = a(type, (ParameterizedType) genericComponentType, new HashMap());
        } else {
            zA = true;
        }
        return zA;
    }

    public TypeToken(Type type) {
        Type typeCanonicalize = C$Gson$Types.canonicalize((Type) C$Gson$Preconditions.checkNotNull(type));
        this.b = typeCanonicalize;
        this.a = (Class<? super T>) C$Gson$Types.getRawType(typeCanonicalize);
        this.c = this.b.hashCode();
    }

    @Deprecated
    public boolean isAssignableFrom(TypeToken<?> typeToken) {
        return isAssignableFrom(typeToken.getType());
    }
}
