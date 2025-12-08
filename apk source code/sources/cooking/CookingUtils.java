package cooking;

import defpackage.g9;
import java.util.ArrayList;
import java.util.List;
import model.Presets;
import org.apache.commons.lang3.Range;

/* loaded from: classes.dex */
public final class CookingUtils {
    public static final List<Range<Integer>> a = new a();

    public static class a extends ArrayList<Range<Integer>> {
        public a() {
            add(Range.between(g9.a(Limits.MAX_TEMPERATURE, g9.a(125, g9.a(Presets.KNEADING_MAX_TIME, g9.a(115, g9.a(110, g9.a(105, g9.a(100, g9.a(95, g9.a(90, g9.a(85, g9.a(80, g9.a(75, g9.a(70, g9.a(65, g9.a(60, g9.a(55, g9.a(50, g9.a(45, g9.a(37, g9.a(0, (Integer) Integer.MIN_VALUE, this, 30), this, 38), this, 46), this, 51), this, 56), this, 61), this, 66), this, 71), this, 76), this, 81), this, 86), this, 91), this, 96), this, 101), this, 106), this, 111), this, 116), this, 121), this, 126), this, 131), Integer.MAX_VALUE));
        }
    }

    public static int getTemperature(int i) {
        if (i <= a.size() - 2) {
            if (i >= 0) {
                return a.get(i).getMaximum().intValue();
            }
            return 0;
        }
        if (a.size() < 2) {
            return 0;
        }
        List<Range<Integer>> list = a;
        return list.get(list.size() - 2).getMaximum().intValue();
    }

    public static int getTemperatureLevel(int i) {
        int i2 = 0;
        while (i2 < a.size()) {
            if (a.get(i2).contains(Integer.valueOf(i))) {
                return (a.size() < 2 || i2 <= a.size() - 2) ? i2 : a.size() - 2;
            }
            i2++;
        }
        return 0;
    }
}
