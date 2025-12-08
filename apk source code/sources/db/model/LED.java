package db.model;

import defpackage.g9;

/* loaded from: classes.dex */
public class LED {
    public String a;
    public String b;
    public Long c;

    public LED(String str, String str2, Long l) {
        this.a = str;
        this.b = str2;
        this.c = l;
    }

    public String getAction() {
        return this.a;
    }

    public String getColor() {
        return this.b;
    }

    public Long getId() {
        return this.c;
    }

    public void setAction(String str) {
        this.a = str;
    }

    public void setColor(String str) {
        this.b = str;
    }

    public void setId(Long l) {
        this.c = l;
    }

    public String toString() {
        StringBuilder sbA = g9.a("LED{id='");
        sbA.append(this.c);
        sbA.append('\'');
        sbA.append(", color='");
        g9.a(sbA, this.b, '\'', ", action='");
        sbA.append(this.a);
        sbA.append('\'');
        sbA.append('}');
        return sbA.toString();
    }

    public LED() {
    }
}
