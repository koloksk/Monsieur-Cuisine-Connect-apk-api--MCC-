package org.apache.commons.lang3;

/* loaded from: classes.dex */
public class NotImplementedException extends UnsupportedOperationException {
    public static final long serialVersionUID = 20131021;
    public final String a;

    public NotImplementedException(String str) {
        this(str, (String) null);
    }

    public String getCode() {
        return this.a;
    }

    public NotImplementedException(Throwable th) {
        this(th, (String) null);
    }

    public NotImplementedException(String str, Throwable th) {
        this(str, th, null);
    }

    public NotImplementedException(String str, String str2) {
        super(str);
        this.a = str2;
    }

    public NotImplementedException(Throwable th, String str) {
        super(th);
        this.a = str;
    }

    public NotImplementedException(String str, Throwable th, String str2) {
        super(str, th);
        this.a = str2;
    }
}
