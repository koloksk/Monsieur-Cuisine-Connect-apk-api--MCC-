package android.support.v4.text.util;

import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.support.v4.util.PatternsCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.method.MovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.webkit.WebView;
import android.widget.TextView;
import java.io.UnsupportedEncodingException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.CharEncoding;

/* loaded from: classes.dex */
public final class LinkifyCompat {
    public static final String[] a = new String[0];
    public static final Comparator<b> b = new a();

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface LinkifyMask {
    }

    public static class a implements Comparator<b> {
        @Override // java.util.Comparator
        public int compare(b bVar, b bVar2) {
            int i;
            int i2;
            b bVar3 = bVar;
            b bVar4 = bVar2;
            int i3 = bVar3.c;
            int i4 = bVar4.c;
            if (i3 < i4) {
                return -1;
            }
            if (i3 <= i4 && (i = bVar3.d) >= (i2 = bVar4.d)) {
                return i > i2 ? -1 : 0;
            }
            return 1;
        }
    }

    public static class b {
        public URLSpan a;
        public String b;
        public int c;
        public int d;
    }

    public static void a(@NonNull TextView textView) {
        MovementMethod movementMethod = textView.getMovementMethod();
        if ((movementMethod == null || !(movementMethod instanceof LinkMovementMethod)) && textView.getLinksClickable()) {
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    public static boolean addLinks(@NonNull Spannable spannable, int i) throws UnsupportedEncodingException {
        int i2;
        int i3;
        int i4;
        int iIndexOf;
        if (Build.VERSION.SDK_INT >= 27) {
            return Linkify.addLinks(spannable, i);
        }
        if (i == 0) {
            return false;
        }
        Object[] objArr = (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int length = objArr.length - 1; length >= 0; length--) {
            spannable.removeSpan(objArr[length]);
        }
        if ((i & 4) != 0) {
            Linkify.addLinks(spannable, 4);
        }
        ArrayList arrayList = new ArrayList();
        if ((i & 1) != 0) {
            a(arrayList, spannable, PatternsCompat.AUTOLINK_WEB_URL, new String[]{"http://", "https://", "rtsp://"}, Linkify.sUrlMatchFilter, null);
        }
        if ((i & 2) != 0) {
            a(arrayList, spannable, PatternsCompat.AUTOLINK_EMAIL_ADDRESS, new String[]{"mailto:"}, null, null);
        }
        if ((i & 8) != 0) {
            String string = spannable.toString();
            int i5 = 0;
            while (true) {
                try {
                    String strFindAddress = WebView.findAddress(string);
                    if (strFindAddress == null || (iIndexOf = string.indexOf(strFindAddress)) < 0) {
                        break;
                    }
                    b bVar = new b();
                    int length2 = strFindAddress.length() + iIndexOf;
                    bVar.c = iIndexOf + i5;
                    i5 += length2;
                    bVar.d = i5;
                    string = string.substring(length2);
                    try {
                        bVar.b = "geo:0,0?q=" + URLEncoder.encode(strFindAddress, CharEncoding.UTF_8);
                        arrayList.add(bVar);
                    } catch (UnsupportedEncodingException unused) {
                    }
                } catch (UnsupportedOperationException unused2) {
                }
            }
        }
        URLSpan[] uRLSpanArr = (URLSpan[]) spannable.getSpans(0, spannable.length(), URLSpan.class);
        for (int i6 = 0; i6 < uRLSpanArr.length; i6++) {
            b bVar2 = new b();
            bVar2.a = uRLSpanArr[i6];
            bVar2.c = spannable.getSpanStart(uRLSpanArr[i6]);
            bVar2.d = spannable.getSpanEnd(uRLSpanArr[i6]);
            arrayList.add(bVar2);
        }
        Collections.sort(arrayList, b);
        int size = arrayList.size();
        int i7 = 0;
        while (true) {
            int i8 = size - 1;
            if (i7 >= i8) {
                break;
            }
            b bVar3 = (b) arrayList.get(i7);
            int i9 = i7 + 1;
            b bVar4 = (b) arrayList.get(i9);
            int i10 = bVar3.c;
            int i11 = bVar4.c;
            if (i10 <= i11 && (i2 = bVar3.d) > i11) {
                int i12 = bVar4.d;
                int i13 = (i12 > i2 && (i3 = i2 - i10) <= (i4 = i12 - i11)) ? i3 < i4 ? i7 : -1 : i9;
                if (i13 != -1) {
                    Object obj = ((b) arrayList.get(i13)).a;
                    if (obj != null) {
                        spannable.removeSpan(obj);
                    }
                    arrayList.remove(i13);
                    size = i8;
                }
            }
            i7 = i9;
        }
        if (arrayList.size() == 0) {
            return false;
        }
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            b bVar5 = (b) it.next();
            if (bVar5.a == null) {
                spannable.setSpan(new URLSpan(bVar5.b), bVar5.c, bVar5.d, 33);
            }
        }
        return true;
    }

    public static String a(@NonNull String str, @NonNull String[] strArr, Matcher matcher, @Nullable Linkify.TransformFilter transformFilter) {
        boolean z;
        if (transformFilter != null) {
            str = transformFilter.transformUrl(matcher, str);
        }
        int i = 0;
        while (true) {
            z = true;
            if (i >= strArr.length) {
                z = false;
                break;
            }
            if (str.regionMatches(true, 0, strArr[i], 0, strArr[i].length())) {
                if (!str.regionMatches(false, 0, strArr[i], 0, strArr[i].length())) {
                    str = strArr[i] + str.substring(strArr[i].length());
                }
            } else {
                i++;
            }
        }
        if (z || strArr.length <= 0) {
            return str;
        }
        return strArr[0] + str;
    }

    public static void a(ArrayList<b> arrayList, Spannable spannable, Pattern pattern, String[] strArr, Linkify.MatchFilter matchFilter, Linkify.TransformFilter transformFilter) {
        Matcher matcher = pattern.matcher(spannable);
        while (matcher.find()) {
            int iStart = matcher.start();
            int iEnd = matcher.end();
            if (matchFilter == null || matchFilter.acceptMatch(spannable, iStart, iEnd)) {
                b bVar = new b();
                bVar.b = a(matcher.group(0), strArr, matcher, transformFilter);
                bVar.c = iStart;
                bVar.d = iEnd;
                arrayList.add(bVar);
            }
        }
    }

    public static boolean addLinks(@NonNull TextView textView, int i) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Linkify.addLinks(textView, i);
        }
        if (i == 0) {
            return false;
        }
        CharSequence text = textView.getText();
        if (text instanceof Spannable) {
            if (!addLinks((Spannable) text, i)) {
                return false;
            }
            a(textView);
            return true;
        }
        SpannableString spannableStringValueOf = SpannableString.valueOf(text);
        if (!addLinks(spannableStringValueOf, i)) {
            return false;
        }
        a(textView);
        textView.setText(spannableStringValueOf);
        return true;
    }

    public static void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String str) {
        if (Build.VERSION.SDK_INT >= 26) {
            Linkify.addLinks(textView, pattern, str);
        } else {
            addLinks(textView, pattern, str, (String[]) null, (Linkify.MatchFilter) null, (Linkify.TransformFilter) null);
        }
    }

    public static void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String str, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        if (Build.VERSION.SDK_INT >= 26) {
            Linkify.addLinks(textView, pattern, str, matchFilter, transformFilter);
        } else {
            addLinks(textView, pattern, str, (String[]) null, matchFilter, transformFilter);
        }
    }

    public static void addLinks(@NonNull TextView textView, @NonNull Pattern pattern, @Nullable String str, @Nullable String[] strArr, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        if (Build.VERSION.SDK_INT >= 26) {
            Linkify.addLinks(textView, pattern, str, strArr, matchFilter, transformFilter);
            return;
        }
        SpannableString spannableStringValueOf = SpannableString.valueOf(textView.getText());
        if (addLinks(spannableStringValueOf, pattern, str, strArr, matchFilter, transformFilter)) {
            textView.setText(spannableStringValueOf);
            a(textView);
        }
    }

    public static boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String str) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Linkify.addLinks(spannable, pattern, str);
        }
        return addLinks(spannable, pattern, str, (String[]) null, (Linkify.MatchFilter) null, (Linkify.TransformFilter) null);
    }

    public static boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String str, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Linkify.addLinks(spannable, pattern, str, matchFilter, transformFilter);
        }
        return addLinks(spannable, pattern, str, (String[]) null, matchFilter, transformFilter);
    }

    public static boolean addLinks(@NonNull Spannable spannable, @NonNull Pattern pattern, @Nullable String str, @Nullable String[] strArr, @Nullable Linkify.MatchFilter matchFilter, @Nullable Linkify.TransformFilter transformFilter) {
        if (Build.VERSION.SDK_INT >= 26) {
            return Linkify.addLinks(spannable, pattern, str, strArr, matchFilter, transformFilter);
        }
        if (str == null) {
            str = "";
        }
        if (strArr == null || strArr.length < 1) {
            strArr = a;
        }
        String[] strArr2 = new String[strArr.length + 1];
        strArr2[0] = str.toLowerCase(Locale.ROOT);
        int i = 0;
        while (i < strArr.length) {
            String str2 = strArr[i];
            i++;
            strArr2[i] = str2 == null ? "" : str2.toLowerCase(Locale.ROOT);
        }
        Matcher matcher = pattern.matcher(spannable);
        boolean z = false;
        while (matcher.find()) {
            int iStart = matcher.start();
            int iEnd = matcher.end();
            if (matchFilter != null ? matchFilter.acceptMatch(spannable, iStart, iEnd) : true) {
                spannable.setSpan(new URLSpan(a(matcher.group(0), strArr2, matcher, transformFilter)), iStart, iEnd, 33);
                z = true;
            }
        }
        return z;
    }
}
