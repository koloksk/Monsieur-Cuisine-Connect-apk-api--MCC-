package defpackage;

import okhttp3.Call;
import okhttp3.EventListener;

/* compiled from: lambda */
/* loaded from: classes.dex */
public final /* synthetic */ class xm implements EventListener.Factory {
    private final /* synthetic */ EventListener a;

    public /* synthetic */ xm(EventListener eventListener) {
        this.a = eventListener;
    }

    @Override // okhttp3.EventListener.Factory
    public final EventListener create(Call call) {
        EventListener eventListener = this.a;
        EventListener.a(eventListener, call);
        return eventListener;
    }
}
