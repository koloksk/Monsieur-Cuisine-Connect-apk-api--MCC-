package defpackage;

import com.google.zxing.Dimension;
import com.google.zxing.datamatrix.encoder.SymbolInfo;
import com.google.zxing.datamatrix.encoder.SymbolShapeHint;
import java.nio.charset.Charset;
import org.apache.commons.lang3.CharEncoding;

/* loaded from: classes.dex */
public final class ta {
    public final String a;
    public SymbolShapeHint b;
    public Dimension c;
    public Dimension d;
    public final StringBuilder e;
    public int f;
    public int g;
    public SymbolInfo h;
    public int i;

    public ta(String str) {
        byte[] bytes = str.getBytes(Charset.forName(CharEncoding.ISO_8859_1));
        StringBuilder sb = new StringBuilder(bytes.length);
        int length = bytes.length;
        for (int i = 0; i < length; i++) {
            char c = (char) (bytes[i] & 255);
            if (c == '?' && str.charAt(i) != '?') {
                throw new IllegalArgumentException("Message contains characters outside ISO-8859-1 encoding.");
            }
            sb.append(c);
        }
        this.a = sb.toString();
        this.b = SymbolShapeHint.FORCE_NONE;
        this.e = new StringBuilder(str.length());
        this.g = -1;
    }

    public int a() {
        return this.e.length();
    }

    public char b() {
        return this.a.charAt(this.f);
    }

    public int c() {
        return (this.a.length() - this.i) - this.f;
    }

    public boolean d() {
        return this.f < this.a.length() - this.i;
    }

    public void e() {
        a(a());
    }

    public void a(int i) {
        SymbolInfo symbolInfo = this.h;
        if (symbolInfo == null || i > symbolInfo.getDataCapacity()) {
            this.h = SymbolInfo.lookup(i, this.b, this.c, this.d, true);
        }
    }
}
