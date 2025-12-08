package android.support.v7.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.Xml;
import defpackage.g9;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.CharEncoding;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public class ActivityChooserModel extends DataSetObservable {
    public static final String n = ActivityChooserModel.class.getSimpleName();
    public static final Object o = new Object();
    public static final Map<String, ActivityChooserModel> p = new HashMap();
    public final Context d;
    public final String e;
    public Intent f;
    public OnChooseActivityListener m;
    public final Object a = new Object();
    public final List<ActivityResolveInfo> b = new ArrayList();
    public final List<HistoricalRecord> c = new ArrayList();
    public ActivitySorter g = new a();
    public int h = 50;
    public boolean i = true;
    public boolean j = false;
    public boolean k = true;
    public boolean l = false;

    public interface ActivityChooserModelClient {
        void setActivityChooserModel(ActivityChooserModel activityChooserModel);
    }

    public static final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo resolveInfo) {
            this.resolveInfo = resolveInfo;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && ActivityResolveInfo.class == obj.getClass() && Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo) obj).weight);
        }

        public int hashCode() {
            return Float.floatToIntBits(this.weight) + 31;
        }

        public String toString() {
            StringBuilder sbA = g9.a("[", "resolveInfo:");
            sbA.append(this.resolveInfo.toString());
            sbA.append("; weight:");
            sbA.append(new BigDecimal(this.weight));
            sbA.append("]");
            return sbA.toString();
        }

        @Override // java.lang.Comparable
        public int compareTo(ActivityResolveInfo activityResolveInfo) {
            return Float.floatToIntBits(activityResolveInfo.weight) - Float.floatToIntBits(this.weight);
        }
    }

    public interface ActivitySorter {
        void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2);
    }

    public static final class HistoricalRecord {

        /* renamed from: activity, reason: collision with root package name */
        public final ComponentName f3activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(String str, long j, float f) {
            this(ComponentName.unflattenFromString(str), j, f);
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || HistoricalRecord.class != obj.getClass()) {
                return false;
            }
            HistoricalRecord historicalRecord = (HistoricalRecord) obj;
            ComponentName componentName = this.f3activity;
            if (componentName == null) {
                if (historicalRecord.f3activity != null) {
                    return false;
                }
            } else if (!componentName.equals(historicalRecord.f3activity)) {
                return false;
            }
            return this.time == historicalRecord.time && Float.floatToIntBits(this.weight) == Float.floatToIntBits(historicalRecord.weight);
        }

        public int hashCode() {
            ComponentName componentName = this.f3activity;
            int iHashCode = componentName == null ? 0 : componentName.hashCode();
            long j = this.time;
            return Float.floatToIntBits(this.weight) + ((((iHashCode + 31) * 31) + ((int) (j ^ (j >>> 32)))) * 31);
        }

        public String toString() {
            StringBuilder sbA = g9.a("[", "; activity:");
            sbA.append(this.f3activity);
            sbA.append("; time:");
            sbA.append(this.time);
            sbA.append("; weight:");
            sbA.append(new BigDecimal(this.weight));
            sbA.append("]");
            return sbA.toString();
        }

        public HistoricalRecord(ComponentName componentName, long j, float f) {
            this.f3activity = componentName;
            this.time = j;
            this.weight = f;
        }
    }

    public interface OnChooseActivityListener {
        boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent);
    }

    public static final class a implements ActivitySorter {
        public final Map<ComponentName, ActivityResolveInfo> a = new HashMap();

        @Override // android.support.v7.widget.ActivityChooserModel.ActivitySorter
        public void sort(Intent intent, List<ActivityResolveInfo> list, List<HistoricalRecord> list2) {
            Map<ComponentName, ActivityResolveInfo> map = this.a;
            map.clear();
            int size = list.size();
            for (int i = 0; i < size; i++) {
                ActivityResolveInfo activityResolveInfo = list.get(i);
                activityResolveInfo.weight = 0.0f;
                ActivityInfo activityInfo = activityResolveInfo.resolveInfo.activityInfo;
                map.put(new ComponentName(activityInfo.packageName, activityInfo.name), activityResolveInfo);
            }
            float f = 1.0f;
            for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                HistoricalRecord historicalRecord = list2.get(size2);
                ActivityResolveInfo activityResolveInfo2 = map.get(historicalRecord.f3activity);
                if (activityResolveInfo2 != null) {
                    activityResolveInfo2.weight = (historicalRecord.weight * f) + activityResolveInfo2.weight;
                    f *= 0.95f;
                }
            }
            Collections.sort(list);
        }
    }

    public final class b extends AsyncTask<Object, Void, Void> {
        public b() {
        }

        @Override // android.os.AsyncTask
        public Void doInBackground(Object[] objArr) throws IOException {
            FileOutputStream fileOutputStreamOpenFileOutput;
            XmlSerializer xmlSerializerNewSerializer;
            List list = (List) objArr[0];
            String str = (String) objArr[1];
            try {
                fileOutputStreamOpenFileOutput = ActivityChooserModel.this.d.openFileOutput(str, 0);
                xmlSerializerNewSerializer = Xml.newSerializer();
            } catch (FileNotFoundException e) {
                Log.e(ActivityChooserModel.n, "Error writing historical record file: " + str, e);
            }
            try {
                try {
                    try {
                        xmlSerializerNewSerializer.setOutput(fileOutputStreamOpenFileOutput, null);
                        xmlSerializerNewSerializer.startDocument(CharEncoding.UTF_8, true);
                        xmlSerializerNewSerializer.startTag(null, "historical-records");
                        int size = list.size();
                        for (int i = 0; i < size; i++) {
                            HistoricalRecord historicalRecord = (HistoricalRecord) list.remove(0);
                            xmlSerializerNewSerializer.startTag(null, "historical-record");
                            xmlSerializerNewSerializer.attribute(null, "activity", historicalRecord.f3activity.flattenToString());
                            xmlSerializerNewSerializer.attribute(null, NotificationCompat.MessagingStyle.Message.KEY_TIMESTAMP, String.valueOf(historicalRecord.time));
                            xmlSerializerNewSerializer.attribute(null, "weight", String.valueOf(historicalRecord.weight));
                            xmlSerializerNewSerializer.endTag(null, "historical-record");
                        }
                        xmlSerializerNewSerializer.endTag(null, "historical-records");
                        xmlSerializerNewSerializer.endDocument();
                        ActivityChooserModel.this.i = true;
                    } catch (IllegalArgumentException e2) {
                        Log.e(ActivityChooserModel.n, "Error writing historical record file: " + ActivityChooserModel.this.e, e2);
                        ActivityChooserModel.this.i = true;
                        if (fileOutputStreamOpenFileOutput != null) {
                        }
                    } catch (IllegalStateException e3) {
                        Log.e(ActivityChooserModel.n, "Error writing historical record file: " + ActivityChooserModel.this.e, e3);
                        ActivityChooserModel.this.i = true;
                        if (fileOutputStreamOpenFileOutput != null) {
                        }
                    }
                } catch (IOException e4) {
                    Log.e(ActivityChooserModel.n, "Error writing historical record file: " + ActivityChooserModel.this.e, e4);
                    ActivityChooserModel.this.i = true;
                    if (fileOutputStreamOpenFileOutput != null) {
                    }
                }
                if (fileOutputStreamOpenFileOutput != null) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                    } catch (IOException unused) {
                    }
                }
                return null;
            } catch (Throwable th) {
                ActivityChooserModel.this.i = true;
                if (fileOutputStreamOpenFileOutput != null) {
                    try {
                        fileOutputStreamOpenFileOutput.close();
                    } catch (IOException unused2) {
                    }
                }
                throw th;
            }
        }
    }

    public ActivityChooserModel(Context context, String str) {
        this.d = context.getApplicationContext();
        if (TextUtils.isEmpty(str) || str.endsWith(".xml")) {
            this.e = str;
        } else {
            this.e = g9.b(str, ".xml");
        }
    }

    public static ActivityChooserModel a(Context context, String str) {
        ActivityChooserModel activityChooserModel;
        synchronized (o) {
            activityChooserModel = p.get(str);
            if (activityChooserModel == null) {
                activityChooserModel = new ActivityChooserModel(context, str);
                p.put(str, activityChooserModel);
            }
        }
        return activityChooserModel;
    }

    public int b() {
        int size;
        synchronized (this.a) {
            a();
            size = this.b.size();
        }
        return size;
    }

    public ResolveInfo c() {
        synchronized (this.a) {
            a();
            if (this.b.isEmpty()) {
                return null;
            }
            return this.b.get(0).resolveInfo;
        }
    }

    public int d() {
        int size;
        synchronized (this.a) {
            a();
            size = this.c.size();
        }
        return size;
    }

    public final void e() {
        int size = this.c.size() - this.h;
        if (size <= 0) {
            return;
        }
        this.k = true;
        for (int i = 0; i < size; i++) {
            this.c.remove(0);
        }
    }

    public final boolean f() {
        if (this.g == null || this.f == null || this.b.isEmpty() || this.c.isEmpty()) {
            return false;
        }
        this.g.sort(this.f, this.b, Collections.unmodifiableList(this.c));
        return true;
    }

    public ResolveInfo b(int i) {
        ResolveInfo resolveInfo;
        synchronized (this.a) {
            a();
            resolveInfo = this.b.get(i).resolveInfo;
        }
        return resolveInfo;
    }

    public void c(int i) {
        synchronized (this.a) {
            a();
            ActivityResolveInfo activityResolveInfo = this.b.get(i);
            ActivityResolveInfo activityResolveInfo2 = this.b.get(0);
            a(new HistoricalRecord(new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name), System.currentTimeMillis(), activityResolveInfo2 != null ? (activityResolveInfo2.weight - activityResolveInfo.weight) + 5.0f : 1.0f));
        }
    }

    public void a(Intent intent) {
        synchronized (this.a) {
            if (this.f == intent) {
                return;
            }
            this.f = intent;
            this.l = true;
            a();
        }
    }

    public int a(ResolveInfo resolveInfo) {
        synchronized (this.a) {
            a();
            List<ActivityResolveInfo> list = this.b;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (list.get(i).resolveInfo == resolveInfo) {
                    return i;
                }
            }
            return -1;
        }
    }

    public Intent a(int i) {
        synchronized (this.a) {
            if (this.f == null) {
                return null;
            }
            a();
            ActivityResolveInfo activityResolveInfo = this.b.get(i);
            ComponentName componentName = new ComponentName(activityResolveInfo.resolveInfo.activityInfo.packageName, activityResolveInfo.resolveInfo.activityInfo.name);
            Intent intent = new Intent(this.f);
            intent.setComponent(componentName);
            if (this.m != null) {
                if (this.m.onChooseActivity(this, new Intent(intent))) {
                    return null;
                }
            }
            a(new HistoricalRecord(componentName, System.currentTimeMillis(), 1.0f));
            return intent;
        }
    }

    public void a(OnChooseActivityListener onChooseActivityListener) {
        synchronized (this.a) {
            this.m = onChooseActivityListener;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.io.FileInputStream] */
    /* JADX WARN: Type inference failed for: r4v3, types: [java.io.FileInputStream, java.io.InputStream] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5, types: [int] */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r5v3, types: [org.xmlpull.v1.XmlPullParser] */
    public final void a() throws IOException {
        int i;
        ?? OpenFileInput;
        ?? NewPullParser;
        int next = 0;
        if (!this.l || this.f == null) {
            i = 0;
        } else {
            this.l = false;
            this.b.clear();
            List<ResolveInfo> listQueryIntentActivities = this.d.getPackageManager().queryIntentActivities(this.f, 0);
            int size = listQueryIntentActivities.size();
            OpenFileInput = 0;
            while (OpenFileInput < size) {
                this.b.add(new ActivityResolveInfo(listQueryIntentActivities.get(OpenFileInput)));
                OpenFileInput++;
            }
            i = 1;
        }
        if (this.i && this.k && !TextUtils.isEmpty(this.e)) {
            this.i = false;
            this.j = true;
            try {
                try {
                    OpenFileInput = this.d.openFileInput(this.e);
                    try {
                        NewPullParser = Xml.newPullParser();
                        NewPullParser.setInput(OpenFileInput, CharEncoding.UTF_8);
                        while (next != 1 && next != 2) {
                            next = NewPullParser.next();
                        }
                    } catch (IOException e) {
                        Log.e(n, "Error reading historical recrod file: " + this.e, e);
                        if (OpenFileInput != 0) {
                            OpenFileInput.close();
                        }
                    } catch (XmlPullParserException e2) {
                        Log.e(n, "Error reading historical recrod file: " + this.e, e2);
                        if (OpenFileInput != 0) {
                            OpenFileInput.close();
                        }
                    }
                } catch (Throwable th) {
                    if (OpenFileInput != 0) {
                        try {
                            OpenFileInput.close();
                        } catch (IOException unused) {
                        }
                    }
                    throw th;
                }
            } catch (FileNotFoundException | IOException unused2) {
            }
            if ("historical-records".equals(NewPullParser.getName())) {
                List<HistoricalRecord> list = this.c;
                list.clear();
                while (true) {
                    int next2 = NewPullParser.next();
                    if (next2 == 1) {
                        if (OpenFileInput != 0) {
                        }
                    } else if (next2 != 3 && next2 != 4) {
                        if ("historical-record".equals(NewPullParser.getName())) {
                            list.add(new HistoricalRecord(NewPullParser.getAttributeValue(null, "activity"), Long.parseLong(NewPullParser.getAttributeValue(null, NotificationCompat.MessagingStyle.Message.KEY_TIMESTAMP)), Float.parseFloat(NewPullParser.getAttributeValue(null, "weight"))));
                        } else {
                            throw new XmlPullParserException("Share records file not well-formed.");
                        }
                    }
                }
                next = 1;
            } else {
                throw new XmlPullParserException("Share records file does not start with historical-records tag.");
            }
        }
        int i2 = i | next;
        e();
        if (i2 != 0) {
            f();
            notifyChanged();
        }
    }

    public final boolean a(HistoricalRecord historicalRecord) {
        boolean zAdd = this.c.add(historicalRecord);
        if (zAdd) {
            this.k = true;
            e();
            if (this.j) {
                if (this.k) {
                    this.k = false;
                    if (!TextUtils.isEmpty(this.e)) {
                        new b().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new ArrayList(this.c), this.e);
                    }
                }
                f();
                notifyChanged();
            } else {
                throw new IllegalStateException("No preceding call to #readHistoricalData");
            }
        }
        return zAdd;
    }
}
