package helper;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.RawRes;
import android.util.Log;
import application.App;
import de.silpion.mc2.R;
import defpackage.g9;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import model.HelpContentTutorial;

/* loaded from: classes.dex */
public class ResourceHelper {
    public static ResourceHelper a;

    public static class a implements Comparable<a> {
        public final String a;
        public final int b;
        public final boolean c;
        public final String d;

        public a(String str) {
            this.a = str;
            String str2 = str.split("_")[r0.length - 1];
            this.d = str.substring(0, (str.length() - 1) - str2.length());
            boolean zEquals = "bg".equals(str2);
            this.c = zEquals;
            this.b = zEquals ? Integer.MAX_VALUE : Integer.valueOf(str2, 10).intValue();
        }

        @Override // java.lang.Comparable
        public int compareTo(@NonNull a aVar) {
            a aVar2 = aVar;
            int iCompareTo = this.d.compareTo(aVar2.d);
            return iCompareTo != 0 ? iCompareTo : Integer.compare(this.b, aVar2.b);
        }

        public String toString() {
            return this.c ? String.format(Locale.getDefault(), "Tutorial \"%s\" -- Background -- File \"%s\"", this.d, this.a) : String.format(Locale.getDefault(), "Tutorial \"%s\" -- Image #%d -- File \"%s\"", this.d, Integer.valueOf(this.b), this.a);
        }
    }

    public static int getDocumentVersionFromHTML(String str) {
        Matcher matcher = Pattern.compile("<meta\\s+version=((?:'|\")(\\d+)(?:'|\"))\\s*/>").matcher(str);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(2));
        }
        return 0;
    }

    public static ResourceHelper getInstance() {
        if (a == null) {
            a = new ResourceHelper();
        }
        return a;
    }

    public static String getLatestLocalDocument(String str, int i) throws Resources.NotFoundException, IOException {
        String textFromRaw = readTextFromRaw(App.getInstance(), i);
        int documentVersionFromHTML = getDocumentVersionFromHTML(textFromRaw);
        String localDocument = getLocalDocument(str, i);
        return getDocumentVersionFromHTML(localDocument) > documentVersionFromHTML ? localDocument : textFromRaw;
    }

    public static String getLocalDocument(String str, int i) throws Resources.NotFoundException, IOException {
        String str2 = String.format("%s/%s", App.getInstance().getFilesDir().getAbsolutePath(), str);
        File file = new File(str2);
        if (!file.exists()) {
            return readTextFromRaw(App.getInstance(), i);
        }
        try {
            byte[] bArr = new byte[(int) file.length()];
            new FileInputStream(str2).read(bArr);
            return new String(bArr);
        } catch (IOException e) {
            String textFromRaw = readTextFromRaw(App.getInstance(), i);
            StringBuilder sbA = g9.a("File read failed: ");
            sbA.append(e.toString());
            Log.e("ResourceHelper", sbA.toString());
            return textFromRaw;
        }
    }

    public static String getLocalPrivacyTermHTML() {
        return getLatestLocalDocument(String.format("privacyterm.%s.html", SharedPreferencesHelper.getInstance().getLanguage()), R.raw.mcc_privacy);
    }

    public static int getLocalPrivacyTermVersion() {
        return getDocumentVersionFromHTML(getLocalPrivacyTermHTML());
    }

    public static String getLocalTermsOfUseHTML() {
        return getLatestLocalDocument(String.format("terms_of_use.%s.html", SharedPreferencesHelper.getInstance().getLanguage()), R.raw.terms_of_use);
    }

    public static int getLocalTermsOfUseVersion() {
        return getDocumentVersionFromHTML(getLocalTermsOfUseHTML());
    }

    public static String readTextFromRaw(Context context, @RawRes int i) throws Resources.NotFoundException, IOException {
        InputStream inputStreamOpenRawResource = context.getResources().openRawResource(i);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            for (int i2 = inputStreamOpenRawResource.read(); i2 != -1; i2 = inputStreamOpenRawResource.read()) {
                byteArrayOutputStream.write(i2);
            }
            inputStreamOpenRawResource.close();
        } catch (IOException e) {
            Log.e("ResourceHelper", "error read text from raw: " + e);
        }
        return byteArrayOutputStream.toString();
    }

    public static void storeLocalDocument(String str, String str2) throws IOException {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(String.format("%s/%s", App.getInstance().getFilesDir().getAbsolutePath(), str));
            fileOutputStream.write(str2.getBytes());
            fileOutputStream.close();
        } catch (IOException e) {
            StringBuilder sbA = g9.a("File write failed: ");
            sbA.append(e.toString());
            Log.e("ResourceHelper", sbA.toString());
        }
    }

    public static void storeLocalPrivacyTermHTML(String str) throws IOException {
        storeLocalDocument(String.format("privacyterm.%s.html", SharedPreferencesHelper.getInstance().getLanguage()), str);
    }

    public static void storeLocalTermsOfUseHTML(String str) throws IOException {
        storeLocalDocument(String.format("terms_of_use.%s.html", SharedPreferencesHelper.getInstance().getLanguage()), str);
    }

    public ResourceHelper changeFolder(String str) throws ClassNotFoundException {
        try {
            Logger.d("Resource Helper", "changed to " + str + Class.forName("de.silpion.mc2.R." + str).toString());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Logger.d("Resource Helper", "Class not found" + str);
        }
        return this;
    }

    public List<String> getFileListByRegex(String str) throws SecurityException {
        ArrayList arrayList = new ArrayList();
        for (Field field : R.raw.class.getFields()) {
            String name = field.getName();
            if (name.matches(str)) {
                arrayList.add(name);
            }
        }
        return arrayList;
    }

    public Map<String, Integer> getFilesMapByRegex(String str) throws SecurityException {
        HashMap map = new HashMap();
        try {
            for (Field field : R.raw.class.getFields()) {
                String name = field.getName();
                if (name.matches(str)) {
                    map.put(name, Integer.valueOf(field.getInt(field)));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return map;
    }

    public String getStringByName(String str, Context context) {
        Resources resources = context.getResources();
        return resources.getString(resources.getIdentifier(str, "string", context.getPackageName()));
    }

    public List<HelpContentTutorial> getTutorialList(Context context) throws SecurityException {
        a aVar;
        ArrayList arrayList = new ArrayList();
        Map<String, Integer> filesMapByRegex = getInstance().getFilesMapByRegex("(.)+_tutorial_[0-9]+_(bg|[0-9]+)");
        ArrayList arrayList2 = new ArrayList();
        Iterator<String> it = filesMapByRegex.keySet().iterator();
        while (it.hasNext()) {
            arrayList2.add(new a(it.next()));
        }
        Collections.sort(arrayList2);
        ArrayList arrayList3 = new ArrayList();
        Iterator it2 = arrayList2.iterator();
        while (true) {
            String str = null;
            while (it2.hasNext()) {
                aVar = (a) it2.next();
                if (str == null || !aVar.d.equals(str)) {
                    if (arrayList3.size() > 0) {
                        arrayList3 = new ArrayList();
                    }
                    str = aVar.d;
                    arrayList3.add(filesMapByRegex.get(aVar.a));
                } else {
                    if (aVar.c) {
                        break;
                    }
                    arrayList3.add(filesMapByRegex.get(aVar.a));
                }
            }
            return arrayList;
            arrayList.add(new HelpContentTutorial(getStringByName(str, context), filesMapByRegex.get(aVar.a), arrayList3));
            arrayList3 = new ArrayList();
        }
    }
}
