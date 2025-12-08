package defpackage;

import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.internal.JavaVersion;
import com.google.gson.internal.PreJava9DateFormatProvider;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public final class h9 extends TypeAdapter<Date> {
    public final Class<? extends Date> a;
    public final List<DateFormat> b = new ArrayList();

    public h9(Class<? extends Date> cls, String str) {
        a(cls);
        this.a = cls;
        this.b.add(new SimpleDateFormat(str, Locale.US));
        if (Locale.getDefault().equals(Locale.US)) {
            return;
        }
        this.b.add(new SimpleDateFormat(str));
    }

    public static Class<? extends Date> a(Class<? extends Date> cls) {
        if (cls == Date.class || cls == java.sql.Date.class || cls == Timestamp.class) {
            return cls;
        }
        throw new IllegalArgumentException("Date type must be one of " + Date.class + ", " + Timestamp.class + ", or " + java.sql.Date.class + " but was " + cls);
    }

    @Override // com.google.gson.TypeAdapter
    public Date read(JsonReader jsonReader) throws IOException {
        Date date;
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        Date dateA = a(jsonReader.nextString());
        Class<? extends Date> cls = this.a;
        if (cls == Date.class) {
            return dateA;
        }
        if (cls == Timestamp.class) {
            date = new Timestamp(dateA.getTime());
        } else {
            if (cls != java.sql.Date.class) {
                throw new AssertionError();
            }
            date = new java.sql.Date(dateA.getTime());
        }
        return date;
    }

    public String toString() {
        DateFormat dateFormat = this.b.get(0);
        if (dateFormat instanceof SimpleDateFormat) {
            StringBuilder sbA = g9.a("DefaultDateTypeAdapter(");
            sbA.append(((SimpleDateFormat) dateFormat).toPattern());
            sbA.append(')');
            return sbA.toString();
        }
        StringBuilder sbA2 = g9.a("DefaultDateTypeAdapter(");
        sbA2.append(dateFormat.getClass().getSimpleName());
        sbA2.append(')');
        return sbA2.toString();
    }

    @Override // com.google.gson.TypeAdapter
    public void write(JsonWriter jsonWriter, Date date) throws IOException {
        if (date == null) {
            jsonWriter.nullValue();
            return;
        }
        synchronized (this.b) {
            jsonWriter.value(this.b.get(0).format(date));
        }
    }

    public final Date a(String str) {
        synchronized (this.b) {
            Iterator<DateFormat> it = this.b.iterator();
            while (it.hasNext()) {
                try {
                    return it.next().parse(str);
                } catch (ParseException unused) {
                }
            }
            try {
                return ISO8601Utils.parse(str, new ParsePosition(0));
            } catch (ParseException e) {
                throw new JsonSyntaxException(str, e);
            }
        }
    }

    public h9(Class<? extends Date> cls, int i, int i2) {
        a(cls);
        this.a = cls;
        this.b.add(DateFormat.getDateTimeInstance(i, i2, Locale.US));
        if (!Locale.getDefault().equals(Locale.US)) {
            this.b.add(DateFormat.getDateTimeInstance(i, i2));
        }
        if (JavaVersion.isJava9OrLater()) {
            this.b.add(PreJava9DateFormatProvider.getUSDateTimeFormat(i, i2));
        }
    }
}
