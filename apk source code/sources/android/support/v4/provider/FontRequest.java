package android.support.v4.provider;

import android.support.annotation.ArrayRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.util.Preconditions;
import android.util.Base64;
import defpackage.g9;
import java.util.List;

/* loaded from: classes.dex */
public final class FontRequest {
    public final String a;
    public final String b;
    public final String c;
    public final List<List<byte[]>> d;
    public final int e;
    public final String f;

    public FontRequest(@NonNull String str, @NonNull String str2, @NonNull String str3, @NonNull List<List<byte[]>> list) {
        this.a = (String) Preconditions.checkNotNull(str);
        this.b = (String) Preconditions.checkNotNull(str2);
        this.c = (String) Preconditions.checkNotNull(str3);
        this.d = (List) Preconditions.checkNotNull(list);
        this.e = 0;
        this.f = this.a + "-" + this.b + "-" + this.c;
    }

    @Nullable
    public List<List<byte[]>> getCertificates() {
        return this.d;
    }

    @ArrayRes
    public int getCertificatesArrayResId() {
        return this.e;
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public String getIdentifier() {
        return this.f;
    }

    @NonNull
    public String getProviderAuthority() {
        return this.a;
    }

    @NonNull
    public String getProviderPackage() {
        return this.b;
    }

    @NonNull
    public String getQuery() {
        return this.c;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder sbA = g9.a("FontRequest {mProviderAuthority: ");
        sbA.append(this.a);
        sbA.append(", mProviderPackage: ");
        sbA.append(this.b);
        sbA.append(", mQuery: ");
        sbA.append(this.c);
        sbA.append(", mCertificates:");
        sb.append(sbA.toString());
        for (int i = 0; i < this.d.size(); i++) {
            sb.append(" [");
            List<byte[]> list = this.d.get(i);
            for (int i2 = 0; i2 < list.size(); i2++) {
                sb.append(" \"");
                sb.append(Base64.encodeToString(list.get(i2), 0));
                sb.append("\"");
            }
            sb.append(" ]");
        }
        sb.append("}");
        sb.append("mCertificatesArray: " + this.e);
        return sb.toString();
    }

    public FontRequest(@NonNull String str, @NonNull String str2, @NonNull String str3, @ArrayRes int i) {
        this.a = (String) Preconditions.checkNotNull(str);
        this.b = (String) Preconditions.checkNotNull(str2);
        this.c = (String) Preconditions.checkNotNull(str3);
        this.d = null;
        Preconditions.checkArgument(i != 0);
        this.e = i;
        this.f = this.a + "-" + this.b + "-" + this.c;
    }
}
