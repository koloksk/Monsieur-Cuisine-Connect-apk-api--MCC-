package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;

@Deprecated
/* loaded from: classes.dex */
public class LookupTranslator extends CharSequenceTranslator {
    public final HashMap<String, String> b = new HashMap<>();
    public final HashSet<Character> c = new HashSet<>();
    public final int d;
    public final int e;

    public LookupTranslator(CharSequence[]... charSequenceArr) {
        int i = 0;
        int i2 = Integer.MAX_VALUE;
        if (charSequenceArr != null) {
            int i3 = 0;
            for (CharSequence[] charSequenceArr2 : charSequenceArr) {
                this.b.put(charSequenceArr2[0].toString(), charSequenceArr2[1].toString());
                this.c.add(Character.valueOf(charSequenceArr2[0].charAt(0)));
                int length = charSequenceArr2[0].length();
                i2 = length < i2 ? length : i2;
                if (length > i3) {
                    i3 = length;
                }
            }
            i = i3;
        }
        this.d = i2;
        this.e = i;
    }

    @Override // org.apache.commons.lang3.text.translate.CharSequenceTranslator
    public int translate(CharSequence charSequence, int i, Writer writer) throws IOException {
        if (!this.c.contains(Character.valueOf(charSequence.charAt(i)))) {
            return 0;
        }
        int length = this.e;
        if (i + length > charSequence.length()) {
            length = charSequence.length() - i;
        }
        while (length >= this.d) {
            String str = this.b.get(charSequence.subSequence(i, i + length).toString());
            if (str != null) {
                writer.write(str);
                return length;
            }
            length--;
        }
        return 0;
    }
}
