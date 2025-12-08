package mcapi;

/* loaded from: classes.dex */
public interface ResponseListener<T> {
    void receivedResponse(int i, T t, Exception exc);
}
