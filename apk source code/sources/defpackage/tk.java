package defpackage;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.internal.util.ExceptionHelper;

/* loaded from: classes.dex */
public final class tk extends vk<Action> {
    public static final long serialVersionUID = -8219729196779211169L;

    public tk(Action action) {
        super(action);
    }

    @Override // defpackage.vk
    public void a(@NonNull Action action) {
        try {
            action.run();
        } catch (Throwable th) {
            throw ExceptionHelper.wrapOrThrow(th);
        }
    }
}
