package com.google.gson;

import com.google.gson.internal.LazilyParsedNumber;
import java.math.BigDecimal;
import java.math.BigInteger;

/* loaded from: classes.dex */
public final class JsonPrimitive extends JsonElement {
    public static final Class<?>[] b = {Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    public Object a;

    public JsonPrimitive(Boolean bool) {
        a(bool);
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x0036  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void a(java.lang.Object r8) {
        /*
            r7 = this;
            boolean r0 = r8 instanceof java.lang.Character
            if (r0 == 0) goto L11
            java.lang.Character r8 = (java.lang.Character) r8
            char r8 = r8.charValue()
            java.lang.String r8 = java.lang.String.valueOf(r8)
            r7.a = r8
            goto L3c
        L11:
            boolean r0 = r8 instanceof java.lang.Number
            r1 = 0
            r2 = 1
            if (r0 != 0) goto L36
            boolean r0 = r8 instanceof java.lang.String
            if (r0 == 0) goto L1d
        L1b:
            r0 = r2
            goto L34
        L1d:
            java.lang.Class r0 = r8.getClass()
            java.lang.Class<?>[] r3 = com.google.gson.JsonPrimitive.b
            int r4 = r3.length
            r5 = r1
        L25:
            if (r5 >= r4) goto L33
            r6 = r3[r5]
            boolean r6 = r6.isAssignableFrom(r0)
            if (r6 == 0) goto L30
            goto L1b
        L30:
            int r5 = r5 + 1
            goto L25
        L33:
            r0 = r1
        L34:
            if (r0 == 0) goto L37
        L36:
            r1 = r2
        L37:
            com.google.gson.internal.C$Gson$Preconditions.checkArgument(r1)
            r7.a = r8
        L3c:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.JsonPrimitive.a(java.lang.Object):void");
    }

    @Override // com.google.gson.JsonElement
    public JsonPrimitive deepCopy() {
        return this;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || JsonPrimitive.class != obj.getClass()) {
            return false;
        }
        JsonPrimitive jsonPrimitive = (JsonPrimitive) obj;
        if (this.a == null) {
            return jsonPrimitive.a == null;
        }
        if (a(this) && a(jsonPrimitive)) {
            return getAsNumber().longValue() == jsonPrimitive.getAsNumber().longValue();
        }
        if (!(this.a instanceof Number) || !(jsonPrimitive.a instanceof Number)) {
            return this.a.equals(jsonPrimitive.a);
        }
        double dDoubleValue = getAsNumber().doubleValue();
        double dDoubleValue2 = jsonPrimitive.getAsNumber().doubleValue();
        if (dDoubleValue != dDoubleValue2) {
            return Double.isNaN(dDoubleValue) && Double.isNaN(dDoubleValue2);
        }
        return true;
    }

    @Override // com.google.gson.JsonElement
    public BigDecimal getAsBigDecimal() {
        Object obj = this.a;
        return obj instanceof BigDecimal ? (BigDecimal) obj : new BigDecimal(this.a.toString());
    }

    @Override // com.google.gson.JsonElement
    public BigInteger getAsBigInteger() {
        Object obj = this.a;
        return obj instanceof BigInteger ? (BigInteger) obj : new BigInteger(this.a.toString());
    }

    @Override // com.google.gson.JsonElement
    public boolean getAsBoolean() {
        return isBoolean() ? ((Boolean) this.a).booleanValue() : Boolean.parseBoolean(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public byte getAsByte() {
        return isNumber() ? getAsNumber().byteValue() : Byte.parseByte(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public char getAsCharacter() {
        return getAsString().charAt(0);
    }

    @Override // com.google.gson.JsonElement
    public double getAsDouble() {
        return isNumber() ? getAsNumber().doubleValue() : Double.parseDouble(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public float getAsFloat() {
        return isNumber() ? getAsNumber().floatValue() : Float.parseFloat(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public int getAsInt() {
        return isNumber() ? getAsNumber().intValue() : Integer.parseInt(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public long getAsLong() {
        return isNumber() ? getAsNumber().longValue() : Long.parseLong(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public Number getAsNumber() {
        Object obj = this.a;
        return obj instanceof String ? new LazilyParsedNumber((String) this.a) : (Number) obj;
    }

    @Override // com.google.gson.JsonElement
    public short getAsShort() {
        return isNumber() ? getAsNumber().shortValue() : Short.parseShort(getAsString());
    }

    @Override // com.google.gson.JsonElement
    public String getAsString() {
        return isNumber() ? getAsNumber().toString() : isBoolean() ? ((Boolean) this.a).toString() : (String) this.a;
    }

    public int hashCode() {
        long jDoubleToLongBits;
        if (this.a == null) {
            return 31;
        }
        if (a(this)) {
            jDoubleToLongBits = getAsNumber().longValue();
        } else {
            Object obj = this.a;
            if (!(obj instanceof Number)) {
                return obj.hashCode();
            }
            jDoubleToLongBits = Double.doubleToLongBits(getAsNumber().doubleValue());
        }
        return (int) ((jDoubleToLongBits >>> 32) ^ jDoubleToLongBits);
    }

    public boolean isBoolean() {
        return this.a instanceof Boolean;
    }

    public boolean isNumber() {
        return this.a instanceof Number;
    }

    public boolean isString() {
        return this.a instanceof String;
    }

    public JsonPrimitive(Number number) {
        a(number);
    }

    public JsonPrimitive(String str) {
        a(str);
    }

    public JsonPrimitive(Character ch) {
        a(ch);
    }

    public JsonPrimitive(Object obj) {
        a(obj);
    }

    public static boolean a(JsonPrimitive jsonPrimitive) {
        Object obj = jsonPrimitive.a;
        if (!(obj instanceof Number)) {
            return false;
        }
        Number number = (Number) obj;
        return (number instanceof BigInteger) || (number instanceof Long) || (number instanceof Integer) || (number instanceof Short) || (number instanceof Byte);
    }
}
