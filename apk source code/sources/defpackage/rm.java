package defpackage;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import mcapi.HttpResponseListener;
import mcapi.McApi;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class rm implements HttpResponseListener {
    public final /* synthetic */ Long a;

    public rm(McApi mcApi, Long l) {
        this.a = l;
    }

    @Override // mcapi.HttpResponseListener
    public void failure(Exception exc) {
        McApi.d();
        Log.e("McApi", "Failed to remove favorite for " + this.a + ".", exc);
    }

    @Override // mcapi.HttpResponseListener
    public void receivedResponse(int i, String str, BufferedReader bufferedReader) {
        if (i == 204) {
            McApi.d();
            Log.i("McApi", "Removed favorite for " + this.a + " :)");
            return;
        }
        String strA = null;
        try {
            strA = pm.a(bufferedReader);
        } catch (IOException e) {
            e.printStackTrace();
        }
        McApi.d();
        Log.w("McApi", "Probably failed to remove favorite for " + this.a + ", got " + i + StringUtils.SPACE + str + " >> response " + strA);
    }
}
