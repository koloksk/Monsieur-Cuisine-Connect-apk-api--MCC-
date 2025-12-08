package android.support.v4.os;

import android.os.Build;
import android.os.LocaleList;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.Size;
import android.support.v4.media.MediaDescriptionCompat;
import defpackage.q5;
import defpackage.s5;
import defpackage.t5;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* loaded from: classes.dex */
public final class LocaleListCompat {
    public static final t5 a;
    public static final LocaleListCompat b = new LocaleListCompat();

    @RequiresApi(24)
    public static class a implements t5 {
        public LocaleList a = new LocaleList(new Locale[0]);

        @Override // defpackage.t5
        public void a(@NonNull Locale... localeArr) {
            this.a = new LocaleList(localeArr);
        }

        @Override // defpackage.t5
        public Object b() {
            return this.a;
        }

        @Override // defpackage.t5
        public boolean equals(Object obj) {
            return this.a.equals(((LocaleListCompat) obj).unwrap());
        }

        @Override // defpackage.t5
        public Locale get(int i) {
            return this.a.get(i);
        }

        @Override // defpackage.t5
        public int hashCode() {
            return this.a.hashCode();
        }

        @Override // defpackage.t5
        public boolean isEmpty() {
            return this.a.isEmpty();
        }

        @Override // defpackage.t5
        @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
        public int size() {
            return this.a.size();
        }

        @Override // defpackage.t5
        public String toString() {
            return this.a.toString();
        }

        @Override // defpackage.t5
        @IntRange(from = -1)
        public int a(Locale locale) {
            return this.a.indexOf(locale);
        }

        @Override // defpackage.t5
        public String a() {
            return this.a.toLanguageTags();
        }

        @Override // defpackage.t5
        @Nullable
        public Locale a(String[] strArr) {
            LocaleList localeList = this.a;
            if (localeList != null) {
                return localeList.getFirstMatch(strArr);
            }
            return null;
        }
    }

    public static class b implements t5 {
        public s5 a = new s5(new Locale[0]);

        @Override // defpackage.t5
        public void a(@NonNull Locale... localeArr) {
            this.a = new s5(localeArr);
        }

        @Override // defpackage.t5
        public Object b() {
            return this.a;
        }

        @Override // defpackage.t5
        public boolean equals(Object obj) {
            return this.a.equals(((LocaleListCompat) obj).unwrap());
        }

        @Override // defpackage.t5
        public Locale get(int i) {
            s5 s5Var = this.a;
            if (s5Var == null) {
                throw null;
            }
            if (i < 0) {
                return null;
            }
            Locale[] localeArr = s5Var.a;
            if (i < localeArr.length) {
                return localeArr[i];
            }
            return null;
        }

        @Override // defpackage.t5
        public int hashCode() {
            return this.a.hashCode();
        }

        @Override // defpackage.t5
        public boolean isEmpty() {
            return this.a.a.length == 0;
        }

        @Override // defpackage.t5
        @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
        public int size() {
            return this.a.a.length;
        }

        @Override // defpackage.t5
        public String toString() {
            return this.a.toString();
        }

        @Override // defpackage.t5
        @IntRange(from = -1)
        public int a(Locale locale) {
            s5 s5Var = this.a;
            int i = 0;
            while (true) {
                Locale[] localeArr = s5Var.a;
                if (i >= localeArr.length) {
                    return -1;
                }
                if (localeArr[i].equals(locale)) {
                    return i;
                }
                i++;
            }
        }

        @Override // defpackage.t5
        public String a() {
            return this.a.b;
        }

        @Override // defpackage.t5
        @Nullable
        public Locale a(String[] strArr) {
            s5 s5Var = this.a;
            if (s5Var == null) {
                return null;
            }
            if (s5Var != null) {
                List listAsList = Arrays.asList(strArr);
                Locale[] localeArr = s5Var.a;
                int i = 0;
                if (localeArr.length != 1) {
                    if (localeArr.length == 0) {
                        i = -1;
                    } else {
                        Iterator it = listAsList.iterator();
                        int i2 = Integer.MAX_VALUE;
                        while (true) {
                            if (it.hasNext()) {
                                int iA = s5Var.a(q5.a((String) it.next()));
                                if (iA == 0) {
                                    break;
                                }
                                if (iA < i2) {
                                    i2 = iA;
                                }
                            } else if (i2 != Integer.MAX_VALUE) {
                                i = i2;
                            }
                        }
                    }
                }
                if (i == -1) {
                    return null;
                }
                return s5Var.a[i];
            }
            throw null;
        }
    }

    static {
        if (Build.VERSION.SDK_INT >= 24) {
            a = new a();
        } else {
            a = new b();
        }
    }

    public static LocaleListCompat create(@NonNull Locale... localeArr) {
        LocaleListCompat localeListCompat = new LocaleListCompat();
        a.a(localeArr);
        return localeListCompat;
    }

    @NonNull
    public static LocaleListCompat forLanguageTags(@Nullable String str) {
        if (str == null || str.isEmpty()) {
            return getEmptyLocaleList();
        }
        String[] strArrSplit = str.split(",");
        int length = strArrSplit.length;
        Locale[] localeArr = new Locale[length];
        for (int i = 0; i < length; i++) {
            localeArr[i] = Locale.forLanguageTag(strArrSplit[i]);
        }
        LocaleListCompat localeListCompat = new LocaleListCompat();
        a.a(localeArr);
        return localeListCompat;
    }

    @Size(min = 1)
    @NonNull
    public static LocaleListCompat getAdjustedDefault() {
        return Build.VERSION.SDK_INT >= 24 ? wrap(LocaleList.getAdjustedDefault()) : create(Locale.getDefault());
    }

    @Size(min = 1)
    @NonNull
    public static LocaleListCompat getDefault() {
        return Build.VERSION.SDK_INT >= 24 ? wrap(LocaleList.getDefault()) : create(Locale.getDefault());
    }

    @NonNull
    public static LocaleListCompat getEmptyLocaleList() {
        return b;
    }

    @RequiresApi(24)
    public static LocaleListCompat wrap(Object obj) {
        LocaleList localeList;
        int size;
        LocaleListCompat localeListCompat = new LocaleListCompat();
        if ((obj instanceof LocaleList) && (size = (localeList = (LocaleList) obj).size()) > 0) {
            Locale[] localeArr = new Locale[size];
            for (int i = 0; i < size; i++) {
                localeArr[i] = localeList.get(i);
            }
            a.a(localeArr);
        }
        return localeListCompat;
    }

    public boolean equals(Object obj) {
        return a.equals(obj);
    }

    public Locale get(int i) {
        return a.get(i);
    }

    public Locale getFirstMatch(String[] strArr) {
        return a.a(strArr);
    }

    public int hashCode() {
        return a.hashCode();
    }

    @IntRange(from = -1)
    public int indexOf(Locale locale) {
        return a.a(locale);
    }

    public boolean isEmpty() {
        return a.isEmpty();
    }

    @IntRange(from = MediaDescriptionCompat.BT_FOLDER_TYPE_MIXED)
    public int size() {
        return a.size();
    }

    @NonNull
    public String toLanguageTags() {
        return a.a();
    }

    public String toString() {
        return a.toString();
    }

    @Nullable
    public Object unwrap() {
        return a.b();
    }
}
