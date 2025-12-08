package defpackage;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import cooking.CookingUtils;
import org.apache.commons.lang3.Range;

/* compiled from: outline */
/* loaded from: classes.dex */
public class g9 {
    public static float a(float f, float f2, float f3, float f4) {
        return ((f - f2) * f3) + f4;
    }

    public static int a(int i, int i2, int i3, int i4) {
        return ((i + i2) * i3) + i4;
    }

    public static Integer a(int i, Integer num, CookingUtils.a aVar, int i2) {
        aVar.add(Range.between(num, Integer.valueOf(i)));
        return Integer.valueOf(i2);
    }

    public static String a(RecyclerView recyclerView, StringBuilder sb) {
        sb.append(recyclerView.k());
        return sb.toString();
    }

    public static String a(String str, int i) {
        return str + i;
    }

    public static String a(String str, long j) {
        return str + j;
    }

    public static String a(String str, String str2, String str3) {
        return str + str2 + str3;
    }

    public static StringBuilder a(String str) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        return sb;
    }

    public static StringBuilder a(String str, int i, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(i);
        sb.append(str2);
        return sb;
    }

    public static StringBuilder a(String str, String str2) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(str2);
        return sb;
    }

    public static void a(String str, Fragment fragment2, String str2) {
        Log.v(str2, str + fragment2);
    }

    public static void a(StringBuilder sb, String str, char c, String str2) {
        sb.append(str);
        sb.append(c);
        sb.append(str2);
    }

    public static String b(String str, int i) {
        return str + i;
    }

    public static String b(String str, Fragment fragment2, String str2) {
        return str + fragment2 + str2;
    }

    public static String b(String str, String str2) {
        return str + str2;
    }
}
