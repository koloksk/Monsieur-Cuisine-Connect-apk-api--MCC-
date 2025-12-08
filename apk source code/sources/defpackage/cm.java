package defpackage;

import machineAdapter.impl.service.HardwareLEDService;

/* compiled from: lambda */
/* loaded from: classes.dex */
public final /* synthetic */ class cm implements Runnable {
    public static final /* synthetic */ cm a = new cm();

    private /* synthetic */ cm() {
    }

    @Override // java.lang.Runnable
    public final void run() {
        HardwareLEDService.c();
    }
}
