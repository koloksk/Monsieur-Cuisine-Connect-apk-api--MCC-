package android.support.v4.hardware.fingerprint;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RequiresPermission;
import android.support.v4.os.CancellationSignal;
import defpackage.n4;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.Mac;

/* loaded from: classes.dex */
public final class FingerprintManagerCompat {
    public final Context a;

    public static abstract class AuthenticationCallback {
        public void onAuthenticationError(int i, CharSequence charSequence) {
        }

        public void onAuthenticationFailed() {
        }

        public void onAuthenticationHelp(int i, CharSequence charSequence) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
        }
    }

    public static final class AuthenticationResult {
        public final CryptoObject a;

        public AuthenticationResult(CryptoObject cryptoObject) {
            this.a = cryptoObject;
        }

        public CryptoObject getCryptoObject() {
            return this.a;
        }
    }

    public FingerprintManagerCompat(Context context) {
        this.a = context;
    }

    @RequiresApi(23)
    @Nullable
    public static FingerprintManager a(@NonNull Context context) {
        if (context.getPackageManager().hasSystemFeature("android.hardware.fingerprint")) {
            return (FingerprintManager) context.getSystemService(FingerprintManager.class);
        }
        return null;
    }

    @NonNull
    public static FingerprintManagerCompat from(@NonNull Context context) {
        return new FingerprintManagerCompat(context);
    }

    @RequiresPermission("android.permission.USE_FINGERPRINT")
    public void authenticate(@Nullable CryptoObject cryptoObject, int i, @Nullable CancellationSignal cancellationSignal, @NonNull AuthenticationCallback authenticationCallback, @Nullable Handler handler) {
        FingerprintManager.CryptoObject cryptoObject2;
        FingerprintManager.CryptoObject cryptoObject3;
        FingerprintManager fingerprintManagerA = a(this.a);
        if (fingerprintManagerA != null) {
            FingerprintManager.CryptoObject cryptoObject4 = null;
            android.os.CancellationSignal cancellationSignal2 = cancellationSignal != null ? (android.os.CancellationSignal) cancellationSignal.getCancellationSignalObject() : null;
            if (cryptoObject != null) {
                if (cryptoObject.getCipher() != null) {
                    cryptoObject2 = new FingerprintManager.CryptoObject(cryptoObject.getCipher());
                } else if (cryptoObject.getSignature() != null) {
                    cryptoObject2 = new FingerprintManager.CryptoObject(cryptoObject.getSignature());
                } else {
                    if (cryptoObject.getMac() != null) {
                        cryptoObject4 = new FingerprintManager.CryptoObject(cryptoObject.getMac());
                    }
                    cryptoObject3 = cryptoObject4;
                }
                cryptoObject3 = cryptoObject2;
            } else {
                cryptoObject3 = cryptoObject4;
            }
            fingerprintManagerA.authenticate(cryptoObject3, cancellationSignal2, i, new n4(authenticationCallback), handler);
        }
    }

    @RequiresPermission("android.permission.USE_FINGERPRINT")
    public boolean hasEnrolledFingerprints() {
        FingerprintManager fingerprintManagerA = a(this.a);
        return fingerprintManagerA != null && fingerprintManagerA.hasEnrolledFingerprints();
    }

    @RequiresPermission("android.permission.USE_FINGERPRINT")
    public boolean isHardwareDetected() {
        FingerprintManager fingerprintManagerA = a(this.a);
        return fingerprintManagerA != null && fingerprintManagerA.isHardwareDetected();
    }

    public static class CryptoObject {
        public final Signature a;
        public final Cipher b;
        public final Mac c;

        public CryptoObject(@NonNull Signature signature) {
            this.a = signature;
            this.b = null;
            this.c = null;
        }

        @Nullable
        public Cipher getCipher() {
            return this.b;
        }

        @Nullable
        public Mac getMac() {
            return this.c;
        }

        @Nullable
        public Signature getSignature() {
            return this.a;
        }

        public CryptoObject(@NonNull Cipher cipher) {
            this.b = cipher;
            this.a = null;
            this.c = null;
        }

        public CryptoObject(@NonNull Mac mac) {
            this.c = mac;
            this.b = null;
            this.a = null;
        }
    }
}
