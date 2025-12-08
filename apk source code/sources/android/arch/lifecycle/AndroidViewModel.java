package android.arch.lifecycle;

import android.annotation.SuppressLint;
import android.app.Application;
import android.support.annotation.NonNull;

/* loaded from: classes.dex */
public class AndroidViewModel extends ViewModel {

    @SuppressLint({"StaticFieldLeak"})
    public Application a;

    public AndroidViewModel(@NonNull Application application2) {
        this.a = application2;
    }

    @NonNull
    public <T extends Application> T getApplication() {
        return (T) this.a;
    }
}
