package android.support.v4.text;

import android.text.SpannableStringBuilder;
import java.util.Locale;

/* loaded from: classes.dex */
public final class BidiFormatter {
    public static TextDirectionHeuristicCompat d = TextDirectionHeuristicsCompat.FIRSTSTRONG_LTR;
    public static final String e = Character.toString(8206);
    public static final String f = Character.toString(8207);
    public static final BidiFormatter g = new BidiFormatter(false, 2, d);
    public static final BidiFormatter h = new BidiFormatter(true, 2, d);
    public final boolean a;
    public final int b;
    public final TextDirectionHeuristicCompat c;

    public static class b {
        public static final byte[] f = new byte[1792];
        public final CharSequence a;
        public final boolean b;
        public final int c;
        public int d;
        public char e;

        static {
            for (int i = 0; i < 1792; i++) {
                f[i] = Character.getDirectionality(i);
            }
        }

        public b(CharSequence charSequence, boolean z) {
            this.a = charSequence;
            this.b = z;
            this.c = charSequence.length();
        }

        public byte a() {
            char cCharAt;
            char cCharAt2;
            char cCharAt3 = this.a.charAt(this.d - 1);
            this.e = cCharAt3;
            if (Character.isLowSurrogate(cCharAt3)) {
                int iCodePointBefore = Character.codePointBefore(this.a, this.d);
                this.d -= Character.charCount(iCodePointBefore);
                return Character.getDirectionality(iCodePointBefore);
            }
            this.d--;
            char c = this.e;
            byte directionality = c < 1792 ? f[c] : Character.getDirectionality(c);
            if (!this.b) {
                return directionality;
            }
            char c2 = this.e;
            if (c2 != '>') {
                if (c2 != ';') {
                    return directionality;
                }
                int i = this.d;
                do {
                    int i2 = this.d;
                    if (i2 <= 0) {
                        break;
                    }
                    CharSequence charSequence = this.a;
                    int i3 = i2 - 1;
                    this.d = i3;
                    cCharAt = charSequence.charAt(i3);
                    this.e = cCharAt;
                    if (cCharAt == '&') {
                        return (byte) 12;
                    }
                } while (cCharAt != ';');
                this.d = i;
                this.e = ';';
                return (byte) 13;
            }
            int i4 = this.d;
            while (true) {
                int i5 = this.d;
                if (i5 <= 0) {
                    break;
                }
                CharSequence charSequence2 = this.a;
                int i6 = i5 - 1;
                this.d = i6;
                char cCharAt4 = charSequence2.charAt(i6);
                this.e = cCharAt4;
                if (cCharAt4 == '<') {
                    break;
                }
                if (cCharAt4 == '>') {
                    break;
                }
                if (cCharAt4 == '\"' || cCharAt4 == '\'') {
                    char c3 = this.e;
                    do {
                        int i7 = this.d;
                        if (i7 > 0) {
                            CharSequence charSequence3 = this.a;
                            int i8 = i7 - 1;
                            this.d = i8;
                            cCharAt2 = charSequence3.charAt(i8);
                            this.e = cCharAt2;
                        }
                    } while (cCharAt2 != c3);
                }
            }
            this.d = i4;
            this.e = '>';
            return (byte) 13;
        }
    }

    public BidiFormatter(boolean z, int i, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        this.a = z;
        this.b = i;
        this.c = textDirectionHeuristicCompat;
    }

    public static /* synthetic */ boolean a(Locale locale) {
        return TextUtilsCompat.getLayoutDirectionFromLocale(locale) == 1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:30:0x0041, code lost:
    
        return 1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int b(java.lang.CharSequence r6) {
        /*
            android.support.v4.text.BidiFormatter$b r0 = new android.support.v4.text.BidiFormatter$b
            r1 = 0
            r0.<init>(r6, r1)
            int r6 = r0.c
            r0.d = r6
            r6 = r1
        Lb:
            r2 = r6
        Lc:
            int r3 = r0.d
            r4 = 1
            if (r3 <= 0) goto L41
            byte r3 = r0.a()
            if (r3 == 0) goto L39
            if (r3 == r4) goto L32
            r5 = 2
            if (r3 == r5) goto L32
            r5 = 9
            if (r3 == r5) goto Lc
            switch(r3) {
                case 14: goto L2c;
                case 15: goto L2c;
                case 16: goto L29;
                case 17: goto L29;
                case 18: goto L26;
                default: goto L23;
            }
        L23:
            if (r6 != 0) goto Lc
            goto L3f
        L26:
            int r2 = r2 + 1
            goto Lc
        L29:
            if (r6 != r2) goto L2f
            goto L34
        L2c:
            if (r6 != r2) goto L2f
            goto L3b
        L2f:
            int r2 = r2 + (-1)
            goto Lc
        L32:
            if (r2 != 0) goto L36
        L34:
            r1 = r4
            goto L41
        L36:
            if (r6 != 0) goto Lc
            goto L3f
        L39:
            if (r2 != 0) goto L3d
        L3b:
            r1 = -1
            goto L41
        L3d:
            if (r6 != 0) goto Lc
        L3f:
            r6 = r2
            goto Lb
        L41:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.text.BidiFormatter.b(java.lang.CharSequence):int");
    }

    public static BidiFormatter getInstance() {
        return new Builder().build();
    }

    public boolean getStereoReset() {
        return (this.b & 2) != 0;
    }

    public boolean isRtl(String str) {
        return isRtl((CharSequence) str);
    }

    public boolean isRtlContext() {
        return this.a;
    }

    public String unicodeWrap(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean z) {
        if (str == null) {
            return null;
        }
        return unicodeWrap((CharSequence) str, textDirectionHeuristicCompat, z).toString();
    }

    public static final class Builder {
        public boolean a;
        public int b;
        public TextDirectionHeuristicCompat c;

        public Builder() {
            a(BidiFormatter.a(Locale.getDefault()));
        }

        public final void a(boolean z) {
            this.a = z;
            this.c = BidiFormatter.d;
            this.b = 2;
        }

        public BidiFormatter build() {
            return (this.b == 2 && this.c == BidiFormatter.d) ? this.a ? BidiFormatter.h : BidiFormatter.g : new BidiFormatter(this.a, this.b, this.c, null);
        }

        public Builder setTextDirectionHeuristic(TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
            this.c = textDirectionHeuristicCompat;
            return this;
        }

        public Builder stereoReset(boolean z) {
            if (z) {
                this.b |= 2;
            } else {
                this.b &= -3;
            }
            return this;
        }

        public Builder(boolean z) {
            this.a = z;
            this.c = BidiFormatter.d;
            this.b = 2;
        }

        public Builder(Locale locale) {
            a(BidiFormatter.a(locale));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:114:?, code lost:
    
        return 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:?, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:?, code lost:
    
        return 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00dc, code lost:
    
        if (r3 != 0) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x00df, code lost:
    
        if (r4 == 0) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00e5, code lost:
    
        if (r0.d <= 0) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00eb, code lost:
    
        switch(r0.a()) {
            case 14: goto L106;
            case 15: goto L106;
            case 16: goto L105;
            case 17: goto L105;
            case 18: goto L104;
            default: goto L111;
        };
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00ef, code lost:
    
        r5 = r5 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00f2, code lost:
    
        if (r3 != r5) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00f6, code lost:
    
        if (r3 != r5) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x00fa, code lost:
    
        r5 = r5 - 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00fd, code lost:
    
        return r4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static int a(java.lang.CharSequence r13) {
        /*
            Method dump skipped, instructions count: 282
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.text.BidiFormatter.a(java.lang.CharSequence):int");
    }

    public static BidiFormatter getInstance(boolean z) {
        return new Builder(z).build();
    }

    public boolean isRtl(CharSequence charSequence) {
        return this.c.isRtl(charSequence, 0, charSequence.length());
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat, boolean z) {
        if (charSequence == null) {
            return null;
        }
        boolean zIsRtl = textDirectionHeuristicCompat.isRtl(charSequence, 0, charSequence.length());
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
        String str = "";
        if (getStereoReset() && z) {
            boolean zIsRtl2 = (zIsRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR).isRtl(charSequence, 0, charSequence.length());
            spannableStringBuilder.append((CharSequence) ((this.a || !(zIsRtl2 || a(charSequence) == 1)) ? (!this.a || (zIsRtl2 && a(charSequence) != -1)) ? "" : f : e));
        }
        if (zIsRtl != this.a) {
            spannableStringBuilder.append(zIsRtl ? (char) 8235 : (char) 8234);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.append((char) 8236);
        } else {
            spannableStringBuilder.append(charSequence);
        }
        if (z) {
            boolean zIsRtl3 = (zIsRtl ? TextDirectionHeuristicsCompat.RTL : TextDirectionHeuristicsCompat.LTR).isRtl(charSequence, 0, charSequence.length());
            if (!this.a && (zIsRtl3 || b(charSequence) == 1)) {
                str = e;
            } else if (this.a && (!zIsRtl3 || b(charSequence) == -1)) {
                str = f;
            }
            spannableStringBuilder.append((CharSequence) str);
        }
        return spannableStringBuilder;
    }

    public static BidiFormatter getInstance(Locale locale) {
        return new Builder(locale).build();
    }

    public /* synthetic */ BidiFormatter(boolean z, int i, TextDirectionHeuristicCompat textDirectionHeuristicCompat, a aVar) {
        this.a = z;
        this.b = i;
        this.c = textDirectionHeuristicCompat;
    }

    public String unicodeWrap(String str, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return unicodeWrap(str, textDirectionHeuristicCompat, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, TextDirectionHeuristicCompat textDirectionHeuristicCompat) {
        return unicodeWrap(charSequence, textDirectionHeuristicCompat, true);
    }

    public String unicodeWrap(String str, boolean z) {
        return unicodeWrap(str, this.c, z);
    }

    public CharSequence unicodeWrap(CharSequence charSequence, boolean z) {
        return unicodeWrap(charSequence, this.c, z);
    }

    public String unicodeWrap(String str) {
        return unicodeWrap(str, this.c, true);
    }

    public CharSequence unicodeWrap(CharSequence charSequence) {
        return unicodeWrap(charSequence, this.c, true);
    }
}
