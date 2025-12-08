package android.arch.lifecycle;

import java.util.HashMap;
import java.util.Iterator;

/* loaded from: classes.dex */
public class ViewModelStore {
    public final HashMap<String, ViewModel> a = new HashMap<>();

    public final void clear() {
        Iterator<ViewModel> it = this.a.values().iterator();
        while (it.hasNext()) {
            it.next().onCleared();
        }
        this.a.clear();
    }
}
