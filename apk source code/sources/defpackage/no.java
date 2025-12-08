package defpackage;

import java.util.Date;
import java.util.TimeZone;
import org.apache.commons.lang3.time.TimeZones;

/* loaded from: classes.dex */
public class no extends TimeZone {
    public static final long serialVersionUID = 1;
    public final int a;
    public final String b;

    public no(boolean z, int i, int i2) {
        if (i >= 24) {
            throw new IllegalArgumentException(i + " hours out of range");
        }
        if (i2 >= 60) {
            throw new IllegalArgumentException(i2 + " minutes out of range");
        }
        int i3 = ((i * 60) + i2) * 60000;
        this.a = z ? -i3 : i3;
        StringBuilder sb = new StringBuilder(9);
        sb.append(TimeZones.GMT_ID);
        sb.append(z ? '-' : '+');
        sb.append((char) ((i / 10) + 48));
        sb.append((char) ((i % 10) + 48));
        sb.append(':');
        sb.append((char) ((i2 / 10) + 48));
        sb.append((char) ((i2 % 10) + 48));
        this.b = sb.toString();
    }

    public boolean equals(Object obj) {
        return (obj instanceof no) && this.b == ((no) obj).b;
    }

    @Override // java.util.TimeZone
    public String getID() {
        return this.b;
    }

    @Override // java.util.TimeZone
    public int getOffset(int i, int i2, int i3, int i4, int i5, int i6) {
        return this.a;
    }

    @Override // java.util.TimeZone
    public int getRawOffset() {
        return this.a;
    }

    public int hashCode() {
        return this.a;
    }

    @Override // java.util.TimeZone
    public boolean inDaylightTime(Date date) {
        return false;
    }

    @Override // java.util.TimeZone
    public void setRawOffset(int i) {
        throw new UnsupportedOperationException();
    }

    public String toString() {
        StringBuilder sbA = g9.a("[GmtTimeZone id=\"");
        sbA.append(this.b);
        sbA.append("\",offset=");
        sbA.append(this.a);
        sbA.append(']');
        return sbA.toString();
    }

    @Override // java.util.TimeZone
    public boolean useDaylightTime() {
        return false;
    }
}
