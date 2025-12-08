package okio;

import defpackage.fo;
import defpackage.io;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.Nullable;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public final class HashingSink extends ForwardingSink {

    @Nullable
    public final MessageDigest b;

    @Nullable
    public final Mac c;

    public HashingSink(Sink sink, String str) {
        super(sink);
        try {
            this.b = MessageDigest.getInstance(str);
            this.c = null;
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    public static HashingSink hmacSha1(Sink sink, ByteString byteString) {
        return new HashingSink(sink, byteString, "HmacSHA1");
    }

    public static HashingSink hmacSha256(Sink sink, ByteString byteString) {
        return new HashingSink(sink, byteString, "HmacSHA256");
    }

    public static HashingSink hmacSha512(Sink sink, ByteString byteString) {
        return new HashingSink(sink, byteString, "HmacSHA512");
    }

    public static HashingSink md5(Sink sink) {
        return new HashingSink(sink, "MD5");
    }

    public static HashingSink sha1(Sink sink) {
        return new HashingSink(sink, "SHA-1");
    }

    public static HashingSink sha256(Sink sink) {
        return new HashingSink(sink, "SHA-256");
    }

    public static HashingSink sha512(Sink sink) {
        return new HashingSink(sink, "SHA-512");
    }

    public final ByteString hash() {
        MessageDigest messageDigest = this.b;
        return ByteString.of(messageDigest != null ? messageDigest.digest() : this.c.doFinal());
    }

    @Override // okio.ForwardingSink, okio.Sink
    public void write(Buffer buffer, long j) throws IllegalStateException, IOException {
        io.a(buffer.b, 0L, j);
        fo foVar = buffer.a;
        long j2 = 0;
        while (j2 < j) {
            int iMin = (int) Math.min(j - j2, foVar.c - foVar.b);
            MessageDigest messageDigest = this.b;
            if (messageDigest != null) {
                messageDigest.update(foVar.a, foVar.b, iMin);
            } else {
                this.c.update(foVar.a, foVar.b, iMin);
            }
            j2 += iMin;
            foVar = foVar.f;
        }
        super.write(buffer, j);
    }

    public HashingSink(Sink sink, ByteString byteString, String str) throws NoSuchAlgorithmException, InvalidKeyException {
        super(sink);
        try {
            Mac mac = Mac.getInstance(str);
            this.c = mac;
            mac.init(new SecretKeySpec(byteString.toByteArray(), str));
            this.b = null;
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }
}
