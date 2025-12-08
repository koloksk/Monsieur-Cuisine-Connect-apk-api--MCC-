package defpackage;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresPermission;
import android.support.annotation.VisibleForTesting;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import java.util.Calendar;
import org.apache.commons.lang3.time.DateUtils;

/* loaded from: classes.dex */
public class a7 {
    public static a7 d;
    public final Context a;
    public final LocationManager b;
    public final a c = new a();

    public static class a {
        public boolean a;
        public long b;
    }

    @VisibleForTesting
    public a7(@NonNull Context context, @NonNull LocationManager locationManager) {
        this.a = context;
        this.b = locationManager;
    }

    public boolean a() {
        long j;
        a aVar = this.c;
        if (aVar.b > System.currentTimeMillis()) {
            return aVar.a;
        }
        Location locationA = PermissionChecker.checkSelfPermission(this.a, "android.permission.ACCESS_COARSE_LOCATION") == 0 ? a("network") : null;
        Location locationA2 = PermissionChecker.checkSelfPermission(this.a, "android.permission.ACCESS_FINE_LOCATION") == 0 ? a("gps") : null;
        if (locationA2 == null || locationA == null ? locationA2 != null : locationA2.getTime() > locationA.getTime()) {
            locationA = locationA2;
        }
        if (locationA == null) {
            Log.i("TwilightManager", "Could not get last known location. This is probably because the app does not have any location permissions. Falling back to hardcoded sunrise/sunset values.");
            int i = Calendar.getInstance().get(11);
            return i < 6 || i >= 22;
        }
        a aVar2 = this.c;
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (z6.d == null) {
            z6.d = new z6();
        }
        z6 z6Var = z6.d;
        z6Var.a(jCurrentTimeMillis - DateUtils.MILLIS_PER_DAY, locationA.getLatitude(), locationA.getLongitude());
        z6Var.a(jCurrentTimeMillis, locationA.getLatitude(), locationA.getLongitude());
        boolean z = z6Var.c == 1;
        long j2 = z6Var.b;
        long j3 = z6Var.a;
        z6Var.a(jCurrentTimeMillis + DateUtils.MILLIS_PER_DAY, locationA.getLatitude(), locationA.getLongitude());
        long j4 = z6Var.b;
        if (j2 == -1 || j3 == -1) {
            j = jCurrentTimeMillis + 43200000;
        } else {
            j = (jCurrentTimeMillis > j3 ? j4 + 0 : jCurrentTimeMillis > j2 ? j3 + 0 : j2 + 0) + 60000;
        }
        aVar2.a = z;
        aVar2.b = j;
        return aVar.a;
    }

    @RequiresPermission(anyOf = {"android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"})
    public final Location a(String str) {
        try {
            if (this.b.isProviderEnabled(str)) {
                return this.b.getLastKnownLocation(str);
            }
            return null;
        } catch (Exception e) {
            Log.d("TwilightManager", "Failed to get last known location", e);
            return null;
        }
    }
}
