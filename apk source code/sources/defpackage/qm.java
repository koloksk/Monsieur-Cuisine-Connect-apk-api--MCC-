package defpackage;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import mcapi.HttpResponseListener;
import mcapi.McApi;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class qm implements HttpResponseListener {
    public final /* synthetic */ Long a;

    public qm(McApi mcApi, Long l) {
        this.a = l;
    }

    @Override // mcapi.HttpResponseListener
    public void failure(Exception exc) {
        McApi.d();
        Log.e("McApi", "Failed to make " + this.a + " a favorite.", exc);
    }

    @Override // mcapi.HttpResponseListener
    public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
        if (i == 204) {
            McApi.d();
            Log.i("McApi", "Recipe " + this.a + " is a favorite now :)");
            return;
        }
        String strA = null;
        try {
            strA = pm.a(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        McApi.d();
        Log.w("McApi", "Probably failed to make " + this.a + " a favorite, got " + i + StringUtils.SPACE + str + " >> response " + strA);
    }
}
