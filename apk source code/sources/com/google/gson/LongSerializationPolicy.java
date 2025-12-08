package com.google.gson;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public abstract class LongSerializationPolicy {
    public static final LongSerializationPolicy DEFAULT = new a("DEFAULT", 0);
    public static final LongSerializationPolicy STRING;
    public static final /* synthetic */ LongSerializationPolicy[] a;

    public enum a extends LongSerializationPolicy {
        public a(String str, int i) {
            super(str, i, null);
        }

        @Override // com.google.gson.LongSerializationPolicy
        public JsonElement serialize(Long l) {
            return new JsonPrimitive((Number) l);
        }
    }

    static {
        LongSerializationPolicy longSerializationPolicy = new LongSerializationPolicy("STRING", 1) { // from class: com.google.gson.LongSerializationPolicy.b
            {
                a aVar = null;
            }

            @Override // com.google.gson.LongSerializationPolicy
            public JsonElement serialize(Long l) {
                return new JsonPrimitive(String.valueOf(l));
            }
        };
        STRING = longSerializationPolicy;
        a = new LongSerializationPolicy[]{DEFAULT, longSerializationPolicy};
    }

    public /* synthetic */ LongSerializationPolicy(String str, int i, a aVar) {
    }

    public static LongSerializationPolicy valueOf(String str) {
        return (LongSerializationPolicy) Enum.valueOf(LongSerializationPolicy.class, str);
    }

    public static LongSerializationPolicy[] values() {
        return (LongSerializationPolicy[]) a.clone();
    }

    public abstract JsonElement serialize(Long l);
}
