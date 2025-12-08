package defpackage;

import com.google.zxing.client.android.AmbientLightManager;

/* loaded from: classes.dex */
public class ha implements Runnable {
    public final /* synthetic */ boolean a;
    public final /* synthetic */ AmbientLightManager b;

    public ha(AmbientLightManager ambientLightManager, boolean z) {
        this.b = ambientLightManager;
        this.a = z;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.b.a.setTorch(this.a);
    }
}
