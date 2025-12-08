package org.apache.commons.lang3.text.translate;

import defpackage.g9;
import java.io.IOException;
import java.io.Writer;

@Deprecated
/* loaded from: classes.dex */
public class UnicodeEscaper extends CodePointTranslator {
    public final int b;
    public final int c;
    public final boolean d;

    public UnicodeEscaper() {
        this(0, Integer.MAX_VALUE, true);
    }

    public static UnicodeEscaper above(int i) {
        return outsideOf(0, i);
    }

    public static UnicodeEscaper below(int i) {
        return outsideOf(i, Integer.MAX_VALUE);
    }

    public static UnicodeEscaper between(int i, int i2) {
        return new UnicodeEscaper(i, i2, true);
    }

    public static UnicodeEscaper outsideOf(int i, int i2) {
        return new UnicodeEscaper(i, i2, false);
    }

    public String toUtf16Escape(int i) {
        StringBuilder sbA = g9.a("\\u");
        sbA.append(CharSequenceTranslator.hex(i));
        return sbA.toString();
    }

    @Override // org.apache.commons.lang3.text.translate.CodePointTranslator
    public boolean translate(int i, Writer writer) throws IOException {
        if (this.d) {
            if (i < this.b || i > this.c) {
                return false;
            }
        } else if (i >= this.b && i <= this.c) {
            return false;
        }
        if (i > 65535) {
            writer.write(toUtf16Escape(i));
            return true;
        }
        writer.write("\\u");
        writer.write(CharSequenceTranslator.a[(i >> 12) & 15]);
        writer.write(CharSequenceTranslator.a[(i >> 8) & 15]);
        writer.write(CharSequenceTranslator.a[(i >> 4) & 15]);
        writer.write(CharSequenceTranslator.a[i & 15]);
        return true;
    }

    public UnicodeEscaper(int i, int i2, boolean z) {
        this.b = i;
        this.c = i2;
        this.d = z;
    }
}
