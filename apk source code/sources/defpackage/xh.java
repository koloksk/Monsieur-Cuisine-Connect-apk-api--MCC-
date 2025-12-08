package defpackage;

import fragment.SettingsFragment;

/* compiled from: lambda */
/* loaded from: classes.dex */
public final /* synthetic */ class xh implements Runnable {
    private final /* synthetic */ SettingsFragment a;
    private final /* synthetic */ String b;

    public /* synthetic */ xh(SettingsFragment settingsFragment, String str) {
        this.a = settingsFragment;
        this.b = str;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.a.d(this.b);
    }
}
