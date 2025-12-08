package org.apache.commons.lang3.text.translate;

import java.io.IOException;
import java.io.Writer;
import org.apache.commons.lang3.ArrayUtils;

@Deprecated
/* loaded from: classes.dex */
public class AggregateTranslator extends CharSequenceTranslator {
    public final CharSequenceTranslator[] b;

    public AggregateTranslator(CharSequenceTranslator... charSequenceTranslatorArr) {
        this.b = (CharSequenceTranslator[]) ArrayUtils.clone(charSequenceTranslatorArr);
    }

    @Override // org.apache.commons.lang3.text.translate.CharSequenceTranslator
    public int translate(CharSequence charSequence, int i, Writer writer) throws IOException {
        for (CharSequenceTranslator charSequenceTranslator : this.b) {
            int iTranslate = charSequenceTranslator.translate(charSequence, i, writer);
            if (iTranslate != 0) {
                return iTranslate;
            }
        }
        return 0;
    }
}
