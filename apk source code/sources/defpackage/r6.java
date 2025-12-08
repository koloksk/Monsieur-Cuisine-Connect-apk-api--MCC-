package defpackage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import defpackage.q6;

/* loaded from: classes.dex */
public class r6 extends BroadcastReceiver {
    public final /* synthetic */ q6.b a;

    public r6(q6.b bVar) {
        this.a = bVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        q6.b bVar = this.a;
        boolean zA = bVar.a.a();
        if (zA != bVar.b) {
            bVar.b = zA;
            q6.this.applyDayNight();
        }
    }
}
