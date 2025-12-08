package com.google.zxing.client.result;

import defpackage.g9;

/* loaded from: classes.dex */
public final class GeoParsedResult extends ParsedResult {
    public final double b;
    public final double c;
    public final double d;
    public final String e;

    public GeoParsedResult(double d, double d2, double d3, String str) {
        super(ParsedResultType.GEO);
        this.b = d;
        this.c = d2;
        this.d = d3;
        this.e = str;
    }

    public double getAltitude() {
        return this.d;
    }

    @Override // com.google.zxing.client.result.ParsedResult
    public String getDisplayResult() {
        StringBuilder sb = new StringBuilder(20);
        sb.append(this.b);
        sb.append(", ");
        sb.append(this.c);
        if (this.d > 0.0d) {
            sb.append(", ");
            sb.append(this.d);
            sb.append('m');
        }
        if (this.e != null) {
            sb.append(" (");
            sb.append(this.e);
            sb.append(')');
        }
        return sb.toString();
    }

    public String getGeoURI() {
        StringBuilder sbA = g9.a("geo:");
        sbA.append(this.b);
        sbA.append(',');
        sbA.append(this.c);
        if (this.d > 0.0d) {
            sbA.append(',');
            sbA.append(this.d);
        }
        if (this.e != null) {
            sbA.append('?');
            sbA.append(this.e);
        }
        return sbA.toString();
    }

    public double getLatitude() {
        return this.b;
    }

    public double getLongitude() {
        return this.c;
    }

    public String getQuery() {
        return this.e;
    }
}
