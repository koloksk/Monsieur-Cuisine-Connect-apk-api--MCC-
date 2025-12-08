package mcapi;

import java.io.BufferedReader;

/* loaded from: classes.dex */
public interface HttpResponseListener {
    void failure(Exception exc);

    void receivedResponse(int i, String str, BufferedReader bufferedReader);
}
