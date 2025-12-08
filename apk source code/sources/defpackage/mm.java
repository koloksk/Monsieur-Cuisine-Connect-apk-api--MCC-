package defpackage;

import android.util.Log;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/* compiled from: lambda */
/* loaded from: classes.dex */
public final /* synthetic */ class mm implements SingleTransformer {
    public static final /* synthetic */ mm a = new mm();

    private /* synthetic */ mm() {
    }

    @Override // io.reactivex.SingleTransformer
    public final SingleSource apply(Single single) {
        return single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).doOnError(new Consumer() { // from class: nm
            @Override // io.reactivex.functions.Consumer
            public final void accept(Object obj) {
                Log.d("HTTP API", ((Throwable) obj).getMessage());
            }
        });
    }
}
