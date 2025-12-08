package okio;

import defpackage.fo;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* loaded from: classes.dex */
public final class HashingSource extends ForwardingSource {
    public final MessageDigest a;
    public final Mac b;

    public HashingSource(Source source, String str) {
        super(source);
        try {
            this.a = MessageDigest.getInstance(str);
            this.b = null;
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }

    public static HashingSource hmacSha1(Source source, ByteString byteString) {
        return new HashingSource(source, byteString, "HmacSHA1");
    }

    public static HashingSource hmacSha256(Source source, ByteString byteString) {
        return new HashingSource(source, byteString, "HmacSHA256");
    }

    public static HashingSource md5(Source source) {
        return new HashingSource(source, "MD5");
    }

    public static HashingSource sha1(Source source) {
        return new HashingSource(source, "SHA-1");
    }

    public static HashingSource sha256(Source source) {
        return new HashingSource(source, "SHA-256");
    }

    public final ByteString hash() {
        MessageDigest messageDigest = this.a;
        return ByteString.of(messageDigest != null ? messageDigest.digest() : this.b.doFinal());
    }

    @Override // okio.ForwardingSource, okio.Source
    public long read(Buffer buffer, long j) throws IllegalStateException, IOException {
        long j2 = super.read(buffer, j);
        if (j2 != -1) {
            long j3 = buffer.b;
            long j4 = j3 - j2;
            fo foVar = buffer.a;
            while (j3 > j4) {
                foVar = foVar.g;
                j3 -= foVar.c - foVar.b;
            }
            while (j3 < buffer.b) {
                int i = (int) ((foVar.b + j4) - j3);
                MessageDigest messageDigest = this.a;
                if (messageDigest != null) {
                    messageDigest.update(foVar.a, i, foVar.c - i);
                } else {
                    this.b.update(foVar.a, i, foVar.c - i);
                }
                j4 = (foVar.c - foVar.b) + j3;
                foVar = foVar.f;
                j3 = j4;
            }
        }
        return j2;
    }

    public HashingSource(Source source, ByteString byteString, String str) throws NoSuchAlgorithmException, InvalidKeyException {
        super(source);
        try {
            Mac mac = Mac.getInstance(str);
            this.b = mac;
            mac.init(new SecretKeySpec(byteString.toByteArray(), str));
            this.a = null;
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        } catch (NoSuchAlgorithmException unused) {
            throw new AssertionError();
        }
    }
}
