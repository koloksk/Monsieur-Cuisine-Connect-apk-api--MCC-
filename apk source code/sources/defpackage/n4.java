package defpackage;

import android.hardware.fingerprint.FingerprintManager;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;

/* loaded from: classes.dex */
public final class n4 extends FingerprintManager.AuthenticationCallback {
    public final /* synthetic */ FingerprintManagerCompat.AuthenticationCallback a;

    public n4(FingerprintManagerCompat.AuthenticationCallback authenticationCallback) {
        this.a = authenticationCallback;
    }

    @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
    public void onAuthenticationError(int i, CharSequence charSequence) {
        this.a.onAuthenticationError(i, charSequence);
    }

    @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
    public void onAuthenticationFailed() {
        this.a.onAuthenticationFailed();
    }

    @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
    public void onAuthenticationHelp(int i, CharSequence charSequence) {
        this.a.onAuthenticationHelp(i, charSequence);
    }

    @Override // android.hardware.fingerprint.FingerprintManager.AuthenticationCallback
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult authenticationResult) {
        FingerprintManagerCompat.AuthenticationCallback authenticationCallback = this.a;
        FingerprintManager.CryptoObject cryptoObject = authenticationResult.getCryptoObject();
        FingerprintManagerCompat.CryptoObject cryptoObject2 = null;
        if (cryptoObject != null) {
            if (cryptoObject.getCipher() != null) {
                cryptoObject2 = new FingerprintManagerCompat.CryptoObject(cryptoObject.getCipher());
            } else if (cryptoObject.getSignature() != null) {
                cryptoObject2 = new FingerprintManagerCompat.CryptoObject(cryptoObject.getSignature());
            } else if (cryptoObject.getMac() != null) {
                cryptoObject2 = new FingerprintManagerCompat.CryptoObject(cryptoObject.getMac());
            }
        }
        authenticationCallback.onAuthenticationSucceeded(new FingerprintManagerCompat.AuthenticationResult(cryptoObject2));
    }
}
