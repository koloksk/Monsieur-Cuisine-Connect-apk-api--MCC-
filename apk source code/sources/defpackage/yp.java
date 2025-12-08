package defpackage;

import view.ProfileLayout;

/* compiled from: lambda */
/* loaded from: classes.dex */
public final /* synthetic */ class yp implements Runnable {
    private final /* synthetic */ ProfileLayout a;
    private final /* synthetic */ boolean b;
    private final /* synthetic */ String c;

    public /* synthetic */ yp(ProfileLayout profileLayout, boolean z, String str) {
        this.a = profileLayout;
        this.b = z;
        this.c = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.a(this.b, this.c);
    }
}
