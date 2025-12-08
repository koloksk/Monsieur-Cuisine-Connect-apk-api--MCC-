package defpackage;

import io.reactivex.annotations.NonNull;
import org.reactivestreams.Subscription;

/* loaded from: classes.dex */
public final class xk extends vk<Subscription> {
    public static final long serialVersionUID = -707001650852963139L;

    public xk(Subscription subscription) {
        super(subscription);
    }

    @Override // defpackage.vk
    public void a(@NonNull Subscription subscription) {
        subscription.cancel();
    }
}
