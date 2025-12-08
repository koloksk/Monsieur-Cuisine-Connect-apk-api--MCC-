package defpackage;

import activity.MainActivity;
import android.util.Log;
import java.io.BufferedReader;
import mcapi.HttpResponseListener;

/* loaded from: classes.dex */
public class p0 implements HttpResponseListener {
    public final /* synthetic */ MainActivity a;

    public p0(MainActivity mainActivity) {
        this.a = mainActivity;
    }

    @Override // mcapi.HttpResponseListener
    public void failure(Exception exc) {
        Log.i(MainActivity.w, "Machine is Offline");
    }

    @Override // mcapi.HttpResponseListener
    public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
        if (i != 0) {
            Log.i(MainActivity.w, "Machine is Online, statusCode = " + i);
            this.a.triggerImportService();
        }
        this.a.checkAndShowDataPrivacyDialog();
    }
}
