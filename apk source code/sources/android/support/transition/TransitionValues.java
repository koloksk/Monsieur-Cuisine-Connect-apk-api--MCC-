package android.support.transition;

import android.view.View;
import defpackage.g9;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class TransitionValues {

    /* renamed from: view, reason: collision with root package name */
    public View f0view;
    public final Map<String, Object> values = new HashMap();
    public final ArrayList<Transition> a = new ArrayList<>();

    public boolean equals(Object obj) {
        if (!(obj instanceof TransitionValues)) {
            return false;
        }
        TransitionValues transitionValues = (TransitionValues) obj;
        return this.f0view == transitionValues.f0view && this.values.equals(transitionValues.values);
    }

    public int hashCode() {
        return this.values.hashCode() + (this.f0view.hashCode() * 31);
    }

    public String toString() {
        StringBuilder sbA = g9.a("TransitionValues@");
        sbA.append(Integer.toHexString(hashCode()));
        sbA.append(":\n");
        StringBuilder sbA2 = g9.a(sbA.toString(), "    view = ");
        sbA2.append(this.f0view);
        sbA2.append(StringUtils.LF);
        String strB = g9.b(sbA2.toString(), "    values:");
        for (String str : this.values.keySet()) {
            strB = strB + "    " + str + ": " + this.values.get(str) + StringUtils.LF;
        }
        return strB;
    }
}
