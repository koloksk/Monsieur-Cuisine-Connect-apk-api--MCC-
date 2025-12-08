package org.apache.commons.lang3.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;

@Deprecated
/* loaded from: classes.dex */
public class CompositeFormat extends Format {
    public static final long serialVersionUID = -4329119827877627683L;
    public final Format a;
    public final Format b;

    public CompositeFormat(Format format, Format format2) {
        this.a = format;
        this.b = format2;
    }

    @Override // java.text.Format
    public StringBuffer format(Object obj, StringBuffer stringBuffer, FieldPosition fieldPosition) {
        return this.b.format(obj, stringBuffer, fieldPosition);
    }

    public Format getFormatter() {
        return this.b;
    }

    public Format getParser() {
        return this.a;
    }

    @Override // java.text.Format
    public Object parseObject(String str, ParsePosition parsePosition) {
        return this.a.parseObject(str, parsePosition);
    }

    public String reformat(String str) throws ParseException {
        return format(parseObject(str));
    }
}
