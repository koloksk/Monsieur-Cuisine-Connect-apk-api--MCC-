package defpackage;

import defpackage.cd;

/* loaded from: classes.dex */
public class bd implements Runnable {
    public final /* synthetic */ cd a;

    public bd(cd cdVar) {
        this.a = cdVar;
    }

    @Override // java.lang.Runnable
    public void run() {
        cd.c cVar = this.a.n;
        cVar.a.b.unregisterReceiver(cVar);
    }
}
