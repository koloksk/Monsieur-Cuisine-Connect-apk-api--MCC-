package org.apache.commons.lang3.reflect;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.builder.Builder;

/* loaded from: classes.dex */
public class TypeUtils {
    public static final WildcardType WILDCARD_ALL = wildcardType().withUpperBounds(Object.class).build();

    public static class WildcardTypeBuilder implements Builder<WildcardType> {
        public Type[] a;
        public Type[] b;

        public WildcardTypeBuilder() {
        }

        public WildcardTypeBuilder withLowerBounds(Type... typeArr) {
            this.b = typeArr;
            return this;
        }

        public WildcardTypeBuilder withUpperBounds(Type... typeArr) {
            this.a = typeArr;
            return this;
        }

        public /* synthetic */ WildcardTypeBuilder(a aVar) {
        }

        @Override // org.apache.commons.lang3.builder.Builder
        public WildcardType build() {
            return new d(this.a, this.b, null);
        }
    }

    /* JADX INFO: Add missing generic type declarations: [T] */
    public static class a<T> implements Typed<T> {
        public final /* synthetic */ Type a;

        public a(Type type) {
            this.a = type;
        }

        @Override // org.apache.commons.lang3.reflect.Typed
        public Type getType() {
            return this.a;
        }
    }

    public static final class b implements GenericArrayType {
        public final Type a;

        public /* synthetic */ b(Type type, a aVar) {
            this.a = type;
        }

        public boolean equals(Object obj) {
            return obj == this || ((obj instanceof GenericArrayType) && TypeUtils.a(this, (GenericArrayType) obj));
        }

        @Override // java.lang.reflect.GenericArrayType
        public Type getGenericComponentType() {
            return this.a;
        }

        public int hashCode() {
            return this.a.hashCode() | 1072;
        }

        public String toString() {
            return TypeUtils.toString(this);
        }
    }

    public static final class c implements ParameterizedType {
        public final Class<?> a;
        public final Type b;
        public final Type[] c;

        public /* synthetic */ c(Class cls, Type type, Type[] typeArr, a aVar) {
            this.a = cls;
            this.b = type;
            this.c = (Type[]) Arrays.copyOf(typeArr, typeArr.length, Type[].class);
        }

        public boolean equals(Object obj) {
            return obj == this || ((obj instanceof ParameterizedType) && TypeUtils.a(this, (ParameterizedType) obj));
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type[] getActualTypeArguments() {
            return (Type[]) this.c.clone();
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type getOwnerType() {
            return this.b;
        }

        @Override // java.lang.reflect.ParameterizedType
        public Type getRawType() {
            return this.a;
        }

        public int hashCode() {
            return ((((this.a.hashCode() | 1136) << 4) | Objects.hashCode(this.b)) << 8) | Arrays.hashCode(this.c);
        }

        public String toString() {
            return TypeUtils.toString(this);
        }
    }

    public static final class d implements WildcardType {
        public static final Type[] c = new Type[0];
        public final Type[] a;
        public final Type[] b;

        public /* synthetic */ d(Type[] typeArr, Type[] typeArr2, a aVar) {
            this.a = (Type[]) ObjectUtils.defaultIfNull(typeArr, c);
            this.b = (Type[]) ObjectUtils.defaultIfNull(typeArr2, c);
        }

        public boolean equals(Object obj) {
            return obj == this || ((obj instanceof WildcardType) && TypeUtils.a(this, (WildcardType) obj));
        }

        @Override // java.lang.reflect.WildcardType
        public Type[] getLowerBounds() {
            return (Type[]) this.b.clone();
        }

        @Override // java.lang.reflect.WildcardType
        public Type[] getUpperBounds() {
            return (Type[]) this.a.clone();
        }

        public int hashCode() {
            return ((Arrays.hashCode(this.a) | 18688) << 8) | Arrays.hashCode(this.b);
        }

        public String toString() {
            return TypeUtils.toString(this);
        }
    }

    public static boolean a(Type type, Type type2, Map<TypeVariable<?>, Type> map) {
        if (type2 == null || (type2 instanceof Class)) {
            return a(type, (Class<?>) type2);
        }
        if (type2 instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) type2;
            if (type != null && !parameterizedType.equals(type)) {
                Class<?> clsA = a(parameterizedType);
                Map<TypeVariable<?>, Type> mapA = a(type, clsA, (Map<TypeVariable<?>, Type>) null);
                if (mapA == null) {
                    return false;
                }
                if (!mapA.isEmpty()) {
                    Map<TypeVariable<?>, Type> mapA2 = a(parameterizedType, clsA, map);
                    for (TypeVariable<?> typeVariable : mapA2.keySet()) {
                        Type typeA = a(typeVariable, mapA2);
                        Type typeA2 = a(typeVariable, mapA);
                        if (typeA != null || !(typeA2 instanceof Class)) {
                            if (typeA2 != null && !typeA.equals(typeA2) && (!(typeA instanceof WildcardType) || !a(typeA2, typeA, map))) {
                                return false;
                            }
                        }
                    }
                }
            }
            return true;
        }
        if (type2 instanceof GenericArrayType) {
            GenericArrayType genericArrayType = (GenericArrayType) type2;
            if (type != null && !genericArrayType.equals(type)) {
                Type genericComponentType = genericArrayType.getGenericComponentType();
                if (!(type instanceof Class)) {
                    if (type instanceof GenericArrayType) {
                        return a(((GenericArrayType) type).getGenericComponentType(), genericComponentType, map);
                    }
                    if (type instanceof WildcardType) {
                        for (Type type3 : getImplicitUpperBounds((WildcardType) type)) {
                            if (!isAssignable(type3, genericArrayType)) {
                            }
                        }
                        return false;
                    }
                    if (!(type instanceof TypeVariable)) {
                        if (type instanceof ParameterizedType) {
                            return false;
                        }
                        throw new IllegalStateException("found an unhandled type: " + type);
                    }
                    for (Type type4 : getImplicitBounds((TypeVariable) type)) {
                        if (!isAssignable(type4, genericArrayType)) {
                        }
                    }
                    return false;
                }
                Class cls = (Class) type;
                if (!cls.isArray() || !a(cls.getComponentType(), genericComponentType, map)) {
                    return false;
                }
            }
            return true;
        }
        if (!(type2 instanceof WildcardType)) {
            if (type2 instanceof TypeVariable) {
                return a(type, (TypeVariable<?>) type2, map);
            }
            throw new IllegalStateException("found an unhandled type: " + type2);
        }
        WildcardType wildcardType = (WildcardType) type2;
        if (type != null && !wildcardType.equals(type)) {
            Type[] implicitUpperBounds = getImplicitUpperBounds(wildcardType);
            Type[] implicitLowerBounds = getImplicitLowerBounds(wildcardType);
            if (type instanceof WildcardType) {
                WildcardType wildcardType2 = (WildcardType) type;
                Type[] implicitUpperBounds2 = getImplicitUpperBounds(wildcardType2);
                Type[] implicitLowerBounds2 = getImplicitLowerBounds(wildcardType2);
                for (Type type5 : implicitUpperBounds) {
                    Type typeA3 = a(type5, map);
                    for (Type type6 : implicitUpperBounds2) {
                        if (!a(type6, typeA3, map)) {
                            return false;
                        }
                    }
                }
                for (Type type7 : implicitLowerBounds) {
                    Type typeA4 = a(type7, map);
                    for (Type type8 : implicitLowerBounds2) {
                        if (!a(typeA4, type8, map)) {
                            return false;
                        }
                    }
                }
            } else {
                for (Type type9 : implicitUpperBounds) {
                    if (!a(type, a(type9, map), map)) {
                        return false;
                    }
                }
                for (Type type10 : implicitLowerBounds) {
                    if (!a(a(type10, map), type, map)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static boolean containsTypeVariables(Type type) {
        if (type instanceof TypeVariable) {
            return true;
        }
        if (type instanceof Class) {
            return ((Class) type).getTypeParameters().length > 0;
        }
        if (!(type instanceof ParameterizedType)) {
            if (!(type instanceof WildcardType)) {
                return false;
            }
            WildcardType wildcardType = (WildcardType) type;
            return containsTypeVariables(getImplicitLowerBounds(wildcardType)[0]) || containsTypeVariables(getImplicitUpperBounds(wildcardType)[0]);
        }
        for (Type type2 : ((ParameterizedType) type).getActualTypeArguments()) {
            if (containsTypeVariables(type2)) {
                return true;
            }
        }
        return false;
    }

    public static Map<TypeVariable<?>, Type> determineTypeArguments(Class<?> cls, ParameterizedType parameterizedType) {
        Validate.notNull(cls, "cls is null", new Object[0]);
        Validate.notNull(parameterizedType, "superType is null", new Object[0]);
        Class<?> clsA = a(parameterizedType);
        if (!a((Type) cls, clsA)) {
            return null;
        }
        if (cls.equals(clsA)) {
            return a(parameterizedType, clsA, (Map<TypeVariable<?>, Type>) null);
        }
        Type typeA = a(cls, clsA);
        if (typeA instanceof Class) {
            return determineTypeArguments((Class) typeA, parameterizedType);
        }
        ParameterizedType parameterizedType2 = (ParameterizedType) typeA;
        Map<TypeVariable<?>, Type> mapDetermineTypeArguments = determineTypeArguments(a(parameterizedType2), parameterizedType);
        a((Class) cls, parameterizedType2, mapDetermineTypeArguments);
        return mapDetermineTypeArguments;
    }

    public static boolean equals(Type type, Type type2) {
        if (Objects.equals(type, type2)) {
            return true;
        }
        if (type instanceof ParameterizedType) {
            return a((ParameterizedType) type, type2);
        }
        if (type instanceof GenericArrayType) {
            return a((GenericArrayType) type, type2);
        }
        if (type instanceof WildcardType) {
            return a((WildcardType) type, type2);
        }
        return false;
    }

    public static GenericArrayType genericArrayType(Type type) {
        return new b((Type) Validate.notNull(type, "componentType is null", new Object[0]), null);
    }

    public static Type getArrayComponentType(Type type) {
        if (!(type instanceof Class)) {
            if (type instanceof GenericArrayType) {
                return ((GenericArrayType) type).getGenericComponentType();
            }
            return null;
        }
        Class cls = (Class) type;
        if (cls.isArray()) {
            return cls.getComponentType();
        }
        return null;
    }

    public static Type[] getImplicitBounds(TypeVariable<?> typeVariable) {
        Validate.notNull(typeVariable, "typeVariable is null", new Object[0]);
        Type[] bounds = typeVariable.getBounds();
        return bounds.length == 0 ? new Type[]{Object.class} : normalizeUpperBounds(bounds);
    }

    public static Type[] getImplicitLowerBounds(WildcardType wildcardType) {
        Validate.notNull(wildcardType, "wildcardType is null", new Object[0]);
        Type[] lowerBounds = wildcardType.getLowerBounds();
        return lowerBounds.length == 0 ? new Type[]{null} : lowerBounds;
    }

    public static Type[] getImplicitUpperBounds(WildcardType wildcardType) {
        Validate.notNull(wildcardType, "wildcardType is null", new Object[0]);
        Type[] upperBounds = wildcardType.getUpperBounds();
        return upperBounds.length == 0 ? new Type[]{Object.class} : normalizeUpperBounds(upperBounds);
    }

    public static Class<?> getRawType(Type type, Type type2) {
        Map<TypeVariable<?>, Type> typeArguments;
        Type type3;
        if (type instanceof Class) {
            return (Class) type;
        }
        if (type instanceof ParameterizedType) {
            return a((ParameterizedType) type);
        }
        if (type instanceof TypeVariable) {
            if (type2 == null) {
                return null;
            }
            GenericDeclaration genericDeclaration = ((TypeVariable) type).getGenericDeclaration();
            if (!(genericDeclaration instanceof Class) || (typeArguments = getTypeArguments(type2, (Class) genericDeclaration)) == null || (type3 = typeArguments.get(type)) == null) {
                return null;
            }
            return getRawType(type3, type2);
        }
        if (type instanceof GenericArrayType) {
            return Array.newInstance(getRawType(((GenericArrayType) type).getGenericComponentType(), type2), 0).getClass();
        }
        if (type instanceof WildcardType) {
            return null;
        }
        throw new IllegalArgumentException("unknown type: " + type);
    }

    public static Map<TypeVariable<?>, Type> getTypeArguments(ParameterizedType parameterizedType) {
        return a(parameterizedType, a(parameterizedType), (Map<TypeVariable<?>, Type>) null);
    }

    public static boolean isArrayType(Type type) {
        return (type instanceof GenericArrayType) || ((type instanceof Class) && ((Class) type).isArray());
    }

    public static boolean isAssignable(Type type, Type type2) {
        return a(type, type2, (Map<TypeVariable<?>, Type>) null);
    }

    public static boolean isInstance(Object obj, Type type) {
        if (type == null) {
            return false;
        }
        return obj == null ? ((type instanceof Class) && ((Class) type).isPrimitive()) ? false : true : a(obj.getClass(), type, (Map<TypeVariable<?>, Type>) null);
    }

    public static Type[] normalizeUpperBounds(Type[] typeArr) {
        boolean z;
        Validate.notNull(typeArr, "null value specified for bounds array", new Object[0]);
        if (typeArr.length < 2) {
            return typeArr;
        }
        HashSet hashSet = new HashSet(typeArr.length);
        for (Type type : typeArr) {
            int length = typeArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    z = false;
                    break;
                }
                Type type2 = typeArr[i];
                if (type != type2 && a(type2, type, (Map<TypeVariable<?>, Type>) null)) {
                    z = true;
                    break;
                }
                i++;
            }
            if (!z) {
                hashSet.add(type);
            }
        }
        return (Type[]) hashSet.toArray(new Type[hashSet.size()]);
    }

    public static final ParameterizedType parameterize(Class<?> cls, Type... typeArr) {
        return parameterizeWithOwner((Type) null, cls, typeArr);
    }

    public static final ParameterizedType parameterizeWithOwner(Type type, Class<?> cls, Type... typeArr) {
        Validate.notNull(cls, "raw class is null", new Object[0]);
        a aVar = null;
        if (cls.getEnclosingClass() == null) {
            Validate.isTrue(type == null, "no owner allowed for top-level %s", cls);
            type = null;
        } else if (type == null) {
            type = cls.getEnclosingClass();
        } else {
            Validate.isTrue(a(type, cls.getEnclosingClass()), "%s is invalid owner type for parameterized %s", type, cls);
        }
        Validate.noNullElements(typeArr, "null type argument at index %s", new Object[0]);
        Validate.isTrue(cls.getTypeParameters().length == typeArr.length, "invalid number of type parameters specified: expected %d, got %d", Integer.valueOf(cls.getTypeParameters().length), Integer.valueOf(typeArr.length));
        return new c(cls, type, typeArr, aVar);
    }

    public static String toLongString(TypeVariable<?> typeVariable) {
        Validate.notNull(typeVariable, "var is null", new Object[0]);
        StringBuilder sb = new StringBuilder();
        GenericDeclaration genericDeclaration = typeVariable.getGenericDeclaration();
        if (genericDeclaration instanceof Class) {
            Class<?> enclosingClass = (Class) genericDeclaration;
            while (enclosingClass.getEnclosingClass() != null) {
                sb.insert(0, enclosingClass.getSimpleName()).insert(0, ClassUtils.PACKAGE_SEPARATOR_CHAR);
                enclosingClass = enclosingClass.getEnclosingClass();
            }
            sb.insert(0, enclosingClass.getName());
        } else if (genericDeclaration instanceof Type) {
            sb.append(toString((Type) genericDeclaration));
        } else {
            sb.append(genericDeclaration);
        }
        sb.append(':');
        sb.append(a(typeVariable));
        return sb.toString();
    }

    public static String toString(Type type) {
        Validate.notNull(type);
        if (type instanceof Class) {
            return a((Class<?>) type);
        }
        if (!(type instanceof ParameterizedType)) {
            if (!(type instanceof WildcardType)) {
                if (type instanceof TypeVariable) {
                    return a((TypeVariable<?>) type);
                }
                if (type instanceof GenericArrayType) {
                    return String.format("%s[]", toString(((GenericArrayType) type).getGenericComponentType()));
                }
                throw new IllegalArgumentException(ObjectUtils.identityToString(type));
            }
            WildcardType wildcardType = (WildcardType) type;
            StringBuilder sb = new StringBuilder();
            sb.append('?');
            Type[] lowerBounds = wildcardType.getLowerBounds();
            Type[] upperBounds = wildcardType.getUpperBounds();
            if (lowerBounds.length > 1 || (lowerBounds.length == 1 && lowerBounds[0] != null)) {
                sb.append(" super ");
                a(sb, " & ", lowerBounds);
            } else if (upperBounds.length > 1 || (upperBounds.length == 1 && !Object.class.equals(upperBounds[0]))) {
                sb.append(" extends ");
                a(sb, " & ", upperBounds);
            }
            return sb.toString();
        }
        ParameterizedType parameterizedType = (ParameterizedType) type;
        StringBuilder sb2 = new StringBuilder();
        Type ownerType = parameterizedType.getOwnerType();
        Class cls = (Class) parameterizedType.getRawType();
        if (ownerType == null) {
            sb2.append(cls.getName());
        } else {
            if (ownerType instanceof Class) {
                sb2.append(((Class) ownerType).getName());
            } else {
                sb2.append(ownerType.toString());
            }
            sb2.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
            sb2.append(cls.getSimpleName());
        }
        Type[] typeArr = (Type[]) Arrays.copyOf(parameterizedType.getActualTypeArguments(), parameterizedType.getActualTypeArguments().length);
        int[] iArrAdd = new int[0];
        for (int i = 0; i < typeArr.length; i++) {
            if ((typeArr[i] instanceof TypeVariable) && ArrayUtils.contains(((TypeVariable) typeArr[i]).getBounds(), parameterizedType)) {
                iArrAdd = ArrayUtils.add(iArrAdd, i);
            }
        }
        if (iArrAdd.length > 0) {
            Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
            for (int i2 = 0; i2 < iArrAdd.length; i2++) {
                sb2.append('<');
                a(sb2, ", ", actualTypeArguments[i2].toString());
                sb2.append('>');
            }
            Type[] typeArr2 = (Type[]) ArrayUtils.removeAll(actualTypeArguments, iArrAdd);
            if (typeArr2.length > 0) {
                sb2.append('<');
                a(sb2, ", ", typeArr2);
                sb2.append('>');
            }
        } else {
            sb2.append('<');
            a(sb2, ", ", parameterizedType.getActualTypeArguments());
            sb2.append('>');
        }
        return sb2.toString();
    }

    public static boolean typesSatisfyVariables(Map<TypeVariable<?>, Type> map) {
        Validate.notNull(map, "typeVarAssigns is null", new Object[0]);
        for (Map.Entry<TypeVariable<?>, Type> entry : map.entrySet()) {
            TypeVariable<?> key = entry.getKey();
            Type value = entry.getValue();
            for (Type type : getImplicitBounds(key)) {
                if (!a(value, a(type, map), map)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static Type unrollVariables(Map<TypeVariable<?>, Type> map, Type type) {
        if (map == null) {
            map = Collections.emptyMap();
        }
        if (containsTypeVariables(type)) {
            if (type instanceof TypeVariable) {
                return unrollVariables(map, map.get(type));
            }
            if (type instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) type;
                if (parameterizedType.getOwnerType() != null) {
                    HashMap map2 = new HashMap(map);
                    map2.putAll(getTypeArguments(parameterizedType));
                    map = map2;
                }
                Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
                for (int i = 0; i < actualTypeArguments.length; i++) {
                    Type typeUnrollVariables = unrollVariables(map, actualTypeArguments[i]);
                    if (typeUnrollVariables != null) {
                        actualTypeArguments[i] = typeUnrollVariables;
                    }
                }
                return parameterizeWithOwner(parameterizedType.getOwnerType(), (Class<?>) parameterizedType.getRawType(), actualTypeArguments);
            }
            if (type instanceof WildcardType) {
                WildcardType wildcardType = (WildcardType) type;
                return wildcardType().withUpperBounds(a(map, wildcardType.getUpperBounds())).withLowerBounds(a(map, wildcardType.getLowerBounds())).build();
            }
        }
        return type;
    }

    public static WildcardTypeBuilder wildcardType() {
        return new WildcardTypeBuilder(null);
    }

    public static <T> Typed<T> wrap(Type type) {
        return new a(type);
    }

    public static Map<TypeVariable<?>, Type> getTypeArguments(Type type, Class<?> cls) {
        return a(type, cls, (Map<TypeVariable<?>, Type>) null);
    }

    public static final ParameterizedType parameterize(Class<?> cls, Map<TypeVariable<?>, Type> map) {
        Validate.notNull(cls, "raw class is null", new Object[0]);
        Validate.notNull(map, "typeArgMappings is null", new Object[0]);
        return parameterizeWithOwner((Type) null, cls, a(map, (TypeVariable<?>[]) cls.getTypeParameters()));
    }

    public static <T> Typed<T> wrap(Class<T> cls) {
        return wrap((Type) cls);
    }

    public static final ParameterizedType parameterizeWithOwner(Type type, Class<?> cls, Map<TypeVariable<?>, Type> map) {
        Validate.notNull(cls, "raw class is null", new Object[0]);
        Validate.notNull(map, "typeArgMappings is null", new Object[0]);
        return parameterizeWithOwner(type, cls, a(map, (TypeVariable<?>[]) cls.getTypeParameters()));
    }

    public static boolean a(Type type, Class<?> cls) {
        if (type == null) {
            return cls == null || !cls.isPrimitive();
        }
        if (cls == null) {
            return false;
        }
        if (cls.equals(type)) {
            return true;
        }
        if (type instanceof Class) {
            return ClassUtils.isAssignable((Class<?>) type, cls);
        }
        if (type instanceof ParameterizedType) {
            return a((Type) a((ParameterizedType) type), cls);
        }
        if (type instanceof TypeVariable) {
            for (Type type2 : ((TypeVariable) type).getBounds()) {
                if (a(type2, cls)) {
                    return true;
                }
            }
            return false;
        }
        if (type instanceof GenericArrayType) {
            if (cls.equals(Object.class)) {
                return true;
            }
            return cls.isArray() && a(((GenericArrayType) type).getGenericComponentType(), cls.getComponentType());
        }
        if (type instanceof WildcardType) {
            return false;
        }
        throw new IllegalStateException("found an unhandled type: " + type);
    }

    public static Type a(TypeVariable<?> typeVariable, Map<TypeVariable<?>, Type> map) {
        Type type;
        while (true) {
            type = map.get(typeVariable);
            if (!(type instanceof TypeVariable) || type.equals(typeVariable)) {
                break;
            }
            typeVariable = (TypeVariable) type;
        }
        return type;
    }

    public static boolean a(Type type, TypeVariable<?> typeVariable, Map<TypeVariable<?>, Type> map) {
        if (type == null) {
            return true;
        }
        if (typeVariable == null) {
            return false;
        }
        if (typeVariable.equals(type)) {
            return true;
        }
        if (type instanceof TypeVariable) {
            for (Type type2 : getImplicitBounds((TypeVariable) type)) {
                if (a(type2, typeVariable, map)) {
                    return true;
                }
            }
        }
        if ((type instanceof Class) || (type instanceof ParameterizedType) || (type instanceof GenericArrayType) || (type instanceof WildcardType)) {
            return false;
        }
        throw new IllegalStateException("found an unhandled type: " + type);
    }

    public static Type a(Type type, Map<TypeVariable<?>, Type> map) {
        if (!(type instanceof TypeVariable) || map == null) {
            return type;
        }
        Type type2 = map.get(type);
        if (type2 != null) {
            return type2;
        }
        throw new IllegalArgumentException("missing assignment type for type variable " + type);
    }

    public static Map<TypeVariable<?>, Type> a(Type type, Class<?> cls, Map<TypeVariable<?>, Type> map) {
        if (type instanceof Class) {
            Class<?> clsPrimitiveToWrapper = (Class) type;
            if (!a((Type) clsPrimitiveToWrapper, cls)) {
                return null;
            }
            if (clsPrimitiveToWrapper.isPrimitive()) {
                if (cls.isPrimitive()) {
                    return new HashMap();
                }
                clsPrimitiveToWrapper = ClassUtils.primitiveToWrapper(clsPrimitiveToWrapper);
            }
            HashMap map2 = map == null ? new HashMap() : new HashMap(map);
            return cls.equals(clsPrimitiveToWrapper) ? map2 : a(a(clsPrimitiveToWrapper, cls), cls, (Map<TypeVariable<?>, Type>) map2);
        }
        if (type instanceof ParameterizedType) {
            return a((ParameterizedType) type, cls, map);
        }
        if (type instanceof GenericArrayType) {
            Type genericComponentType = ((GenericArrayType) type).getGenericComponentType();
            if (cls.isArray()) {
                cls = cls.getComponentType();
            }
            return a(genericComponentType, cls, map);
        }
        int i = 0;
        if (type instanceof WildcardType) {
            Type[] implicitUpperBounds = getImplicitUpperBounds((WildcardType) type);
            int length = implicitUpperBounds.length;
            while (i < length) {
                Type type2 = implicitUpperBounds[i];
                if (a(type2, cls)) {
                    return a(type2, cls, map);
                }
                i++;
            }
            return null;
        }
        if (type instanceof TypeVariable) {
            Type[] implicitBounds = getImplicitBounds((TypeVariable) type);
            int length2 = implicitBounds.length;
            while (i < length2) {
                Type type3 = implicitBounds[i];
                if (a(type3, cls)) {
                    return a(type3, cls, map);
                }
                i++;
            }
            return null;
        }
        throw new IllegalStateException("found an unhandled type: " + type);
    }

    public static Map<TypeVariable<?>, Type> a(ParameterizedType parameterizedType, Class<?> cls, Map<TypeVariable<?>, Type> map) {
        Map<TypeVariable<?>, Type> map2;
        Class<?> clsA = a(parameterizedType);
        if (!a((Type) clsA, cls)) {
            return null;
        }
        Type ownerType = parameterizedType.getOwnerType();
        if (ownerType instanceof ParameterizedType) {
            ParameterizedType parameterizedType2 = (ParameterizedType) ownerType;
            map2 = a(parameterizedType2, a(parameterizedType2), map);
        } else {
            map2 = map == null ? new HashMap<>() : new HashMap(map);
        }
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        TypeVariable<Class<?>>[] typeParameters = clsA.getTypeParameters();
        for (int i = 0; i < typeParameters.length; i++) {
            Type type = actualTypeArguments[i];
            TypeVariable<Class<?>> typeVariable = typeParameters[i];
            if (map2.containsKey(type)) {
                type = map2.get(type);
            }
            map2.put(typeVariable, type);
        }
        return cls.equals(clsA) ? map2 : a(a(clsA, cls), cls, map2);
    }

    public static <T> void a(Class<T> cls, ParameterizedType parameterizedType, Map<TypeVariable<?>, Type> map) {
        Type ownerType = parameterizedType.getOwnerType();
        if (ownerType instanceof ParameterizedType) {
            a((Class) cls, (ParameterizedType) ownerType, map);
        }
        Type[] actualTypeArguments = parameterizedType.getActualTypeArguments();
        TypeVariable<Class<?>>[] typeParameters = a(parameterizedType).getTypeParameters();
        List listAsList = Arrays.asList(cls.getTypeParameters());
        for (int i = 0; i < actualTypeArguments.length; i++) {
            TypeVariable<Class<?>> typeVariable = typeParameters[i];
            Type type = actualTypeArguments[i];
            if (listAsList.contains(type) && map.containsKey(typeVariable)) {
                map.put((TypeVariable) type, map.get(typeVariable));
            }
        }
    }

    public static Type a(Class<?> cls, Class<?> cls2) {
        Class<?> clsA;
        if (cls2.isInterface()) {
            Type type = null;
            for (Type type2 : cls.getGenericInterfaces()) {
                if (type2 instanceof ParameterizedType) {
                    clsA = a((ParameterizedType) type2);
                } else if (type2 instanceof Class) {
                    clsA = (Class) type2;
                } else {
                    throw new IllegalStateException("Unexpected generic interface type found: " + type2);
                }
                if (a((Type) clsA, cls2) && isAssignable(type, clsA)) {
                    type = type2;
                }
            }
            if (type != null) {
                return type;
            }
        }
        return cls.getGenericSuperclass();
    }

    public static Class<?> a(ParameterizedType parameterizedType) {
        Type rawType = parameterizedType.getRawType();
        if (rawType instanceof Class) {
            return (Class) rawType;
        }
        throw new IllegalStateException("Wait... What!? Type of rawType: " + rawType);
    }

    public static Type[] a(Map<TypeVariable<?>, Type> map, Type[] typeArr) {
        int i = 0;
        while (i < typeArr.length) {
            Type typeUnrollVariables = unrollVariables(map, typeArr[i]);
            if (typeUnrollVariables == null) {
                typeArr = (Type[]) ArrayUtils.remove(typeArr, i);
                i--;
            } else {
                typeArr[i] = typeUnrollVariables;
            }
            i++;
        }
        return typeArr;
    }

    public static Type[] a(Map<TypeVariable<?>, Type> map, TypeVariable<?>[] typeVariableArr) {
        Type[] typeArr = new Type[typeVariableArr.length];
        int length = typeVariableArr.length;
        int i = 0;
        int i2 = 0;
        while (i < length) {
            TypeVariable<?> typeVariable = typeVariableArr[i];
            Validate.isTrue(map.containsKey(typeVariable), "missing argument mapping for %s", toString(typeVariable));
            typeArr[i2] = map.get(typeVariable);
            i++;
            i2++;
        }
        return typeArr;
    }

    public static boolean a(ParameterizedType parameterizedType, Type type) {
        if (!(type instanceof ParameterizedType)) {
            return false;
        }
        ParameterizedType parameterizedType2 = (ParameterizedType) type;
        if (equals(parameterizedType.getRawType(), parameterizedType2.getRawType()) && equals(parameterizedType.getOwnerType(), parameterizedType2.getOwnerType())) {
            return a(parameterizedType.getActualTypeArguments(), parameterizedType2.getActualTypeArguments());
        }
        return false;
    }

    public static boolean a(GenericArrayType genericArrayType, Type type) {
        return (type instanceof GenericArrayType) && equals(genericArrayType.getGenericComponentType(), ((GenericArrayType) type).getGenericComponentType());
    }

    public static boolean a(WildcardType wildcardType, Type type) {
        if (!(type instanceof WildcardType)) {
            return false;
        }
        WildcardType wildcardType2 = (WildcardType) type;
        return a(getImplicitLowerBounds(wildcardType), getImplicitLowerBounds(wildcardType2)) && a(getImplicitUpperBounds(wildcardType), getImplicitUpperBounds(wildcardType2));
    }

    public static boolean a(Type[] typeArr, Type[] typeArr2) {
        if (typeArr.length != typeArr2.length) {
            return false;
        }
        for (int i = 0; i < typeArr.length; i++) {
            if (!equals(typeArr[i], typeArr2[i])) {
                return false;
            }
        }
        return true;
    }

    public static String a(Class<?> cls) {
        if (cls.isArray()) {
            return toString(cls.getComponentType()) + "[]";
        }
        StringBuilder sb = new StringBuilder();
        if (cls.getEnclosingClass() != null) {
            sb.append(a(cls.getEnclosingClass()));
            sb.append(ClassUtils.PACKAGE_SEPARATOR_CHAR);
            sb.append(cls.getSimpleName());
        } else {
            sb.append(cls.getName());
        }
        if (cls.getTypeParameters().length > 0) {
            sb.append('<');
            a(sb, ", ", cls.getTypeParameters());
            sb.append('>');
        }
        return sb.toString();
    }

    public static String a(TypeVariable<?> typeVariable) {
        StringBuilder sb = new StringBuilder(typeVariable.getName());
        Type[] bounds = typeVariable.getBounds();
        if (bounds.length > 0 && (bounds.length != 1 || !Object.class.equals(bounds[0]))) {
            sb.append(" extends ");
            a(sb, " & ", typeVariable.getBounds());
        }
        return sb.toString();
    }

    public static <T> StringBuilder a(StringBuilder sb, String str, T... tArr) {
        Validate.notEmpty(Validate.noNullElements(tArr));
        if (tArr.length > 0) {
            T t = tArr[0];
            sb.append(t instanceof Type ? toString((Type) t) : t.toString());
            for (int i = 1; i < tArr.length; i++) {
                sb.append(str);
                T t2 = tArr[i];
                sb.append(t2 instanceof Type ? toString((Type) t2) : t2.toString());
            }
        }
        return sb;
    }
}
