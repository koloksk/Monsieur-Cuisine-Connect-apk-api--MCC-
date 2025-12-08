package helper;

import android.util.Log;
import application.App;
import com.google.gson.Gson;
import cooking.Limits;
import db.DbHelper;
import db.RecipeType;
import defpackage.g9;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import machineAdapter.adapter.MachineCallbackAdapter;
import machineAdapter.ipc.WorkModeNames;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class UsageLogger implements Serializable {
    public static final String u = UsageLogger.class.getSimpleName();
    public static final transient UsageLogger v = new UsageLogger();
    public TimerTask b;
    public long c;
    public int f;
    public transient int h;
    public transient long i;
    public transient int j;
    public transient int k;
    public long l;
    public long m;
    public long n;
    public long p;
    public long q;
    public transient boolean r;
    public long s;
    public final transient Timer a = new Timer();
    public HashMap<String, Long> d = new HashMap<>();
    public HashMap<String, Long> e = new HashMap<>();
    public final MachineCallbackAdapter g = new a();
    public HashMap<String, Long> o = new HashMap<>();
    public HashMap<String, Long> t = new HashMap<>();

    public class a extends MachineCallbackAdapter {
        public a() {
        }

        @Override // machineAdapter.adapter.MachineCallbackAdapter, machineAdapter.IMachineCallback
        public void onBadState(int[] iArr) {
            super.onBadState(iArr);
            if (iArr.length != 0) {
                if (iArr.length == 1 && iArr[0] == 0) {
                    return;
                }
                for (int i : iArr) {
                    UsageLogger usageLogger = UsageLogger.this;
                    usageLogger.a(usageLogger.d, i, 1L);
                    usageLogger.f = i;
                    String str = UsageLogger.u;
                    StringBuilder sbA = g9.a("error log errorState ", i, StringUtils.SPACE);
                    sbA.append(usageLogger.d.get(String.valueOf(i)));
                    Log.d(str, sbA.toString());
                }
            }
        }
    }

    public class b extends TimerTask {
        public b() {
        }

        @Override // java.util.TimerTask, java.lang.Runnable
        public void run() throws IOException {
            UsageLogger.a(UsageLogger.this);
        }
    }

    public UsageLogger() {
        Log.w(u, "created");
    }

    public static String a(long j) {
        StringBuilder sb;
        String str;
        long j2 = j / 60;
        long j3 = j % 60;
        String str2 = j3 == 1 ? String.format(Locale.GERMAN, ", %d Sekunde", Long.valueOf(j3)) : String.format(Locale.GERMAN, ", %d Sekunden", Long.valueOf(j3));
        StringBuilder sb2 = new StringBuilder();
        long j4 = j2 / 60;
        long j5 = j2 % 60;
        String string = j5 == 1 ? String.format(Locale.GERMAN, "%d Minute", Long.valueOf(j5)) : String.format(Locale.GERMAN, "%d Minuten", Long.valueOf(j5));
        if (j4 > 0) {
            if (j4 == 1) {
                sb = new StringBuilder();
                str = String.format(Locale.GERMAN, "%d Stunde, ", Long.valueOf(j4));
            } else {
                sb = new StringBuilder();
                str = String.format(Locale.GERMAN, "%d Stunden, ", Long.valueOf(j4));
            }
            sb.append(str);
            sb.append(string);
            string = sb.toString();
        }
        sb2.append(string);
        sb2.append(str2);
        return sb2.toString();
    }

    public static UsageLogger getInstance() {
        return v;
    }

    public final void b() throws IOException {
        String json = new Gson().toJson(v);
        try {
            FileOutputStream fileOutputStreamOpenFileOutput = App.getInstance().openFileOutput("usage_log.json", 0);
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStreamOpenFileOutput));
                bufferedWriter.write(json);
                bufferedWriter.flush();
                bufferedWriter.close();
                fileOutputStreamOpenFileOutput.flush();
                fileOutputStreamOpenFileOutput.close();
            } catch (Throwable th) {
                fileOutputStreamOpenFileOutput.flush();
                fileOutputStreamOpenFileOutput.close();
                throw th;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearUsageLog() {
        new File(App.getInstance().getFilesDir(), "usage_log.json").delete();
    }

    public Map<String, Object> forServer() {
        Iterator<String> it = this.d.keySet().iterator();
        int iLongValue = 0;
        while (it.hasNext()) {
            iLongValue = (int) (this.d.get(it.next()).longValue() + iLongValue);
        }
        HashMap map = new HashMap();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.GERMANY);
        HashMap map2 = new HashMap();
        String language = SharedPreferencesHelper.getInstance().getLanguage();
        long jCountRecipesByType = DbHelper.getInstance().countRecipesByType(RecipeType.DEFAULT, language, false);
        long jCountRecipesByType2 = DbHelper.getInstance().countRecipesByType(RecipeType.LIVE, language, false);
        long jCountRecipesByType3 = DbHelper.getInstance().countRecipesByType(RecipeType.BETA, language, false);
        map2.put(RecipeType.DEFAULT, Long.valueOf(jCountRecipesByType));
        map2.put(RecipeType.LIVE, Long.valueOf(jCountRecipesByType2));
        map2.put(RecipeType.BETA, Long.valueOf(jCountRecipesByType3));
        map.put("sysTime", simpleDateFormat.format(date));
        map.put("onTime", Long.valueOf(this.m));
        map.put("turboTime", Long.valueOf(this.s));
        map.put("longestTurboTime", Long.valueOf(this.l));
        map.put("lidOpened", Long.valueOf(this.q));
        map.put("motorBraked", Long.valueOf(this.n));
        map.put("scaleCalibrated", Long.valueOf(this.p));
        map.put("errors", Integer.valueOf(iLongValue));
        map.put("lastError", Integer.valueOf(this.f));
        map.put("motor", a(this.o, 10));
        map.put("temperature", a(this.e, Limits.TEMPERATURE_LEVEL_MAX));
        map.put("modes", this.t);
        map.put("recipes", map2);
        return map;
    }

    public void logMotorBraked() {
        this.n++;
    }

    public void logScaleCalibration() {
        this.p++;
    }

    public void logSealOpened() {
        this.q++;
    }

    public void logStart(int i, int i2, int i3) {
        logStop();
        this.i = System.currentTimeMillis();
        this.k = i;
        this.h = i2;
        this.j = i3;
    }

    public void logStop() {
        if (this.i > 0) {
            long jCurrentTimeMillis = (System.currentTimeMillis() - this.i) / 1000;
            if (jCurrentTimeMillis > 0) {
                int i = this.h;
                if (i > 0) {
                    Log.w(u, String.format("logMotor >> level %d %ds", Integer.valueOf(i), Long.valueOf(jCurrentTimeMillis)));
                    a(this.o, this.h, jCurrentTimeMillis);
                    this.h = 0;
                }
                int i2 = this.j;
                if (i2 > 0) {
                    Log.w(u, String.format("logTemperature >> level %d %ds", Integer.valueOf(i2), Long.valueOf(jCurrentTimeMillis)));
                    a(this.e, this.j, jCurrentTimeMillis);
                    this.j = 0;
                }
                int i3 = this.k;
                if (i3 != 0 && i3 != 6) {
                    Log.w(u, String.format("logWorkMode >> mode %d %ds", Integer.valueOf(i3), Long.valueOf(jCurrentTimeMillis)));
                    if (this.k == 2) {
                        this.s += jCurrentTimeMillis;
                        if (jCurrentTimeMillis > this.l) {
                            this.l = jCurrentTimeMillis;
                        }
                    }
                    a(this.t, WorkModeNames.fromMode(this.k), jCurrentTimeMillis);
                    this.k = 0;
                }
            }
        }
        this.i = 0L;
    }

    public void start() {
        Log.i(u, this.r ? "start again" : "start");
        if (this.r) {
            return;
        }
        this.r = true;
        try {
            String strA = a();
            if (strA != null) {
                UsageLogger usageLogger = (UsageLogger) new Gson().fromJson(strA, UsageLogger.class);
                if (usageLogger != null) {
                    this.c = usageLogger.c;
                    this.d = usageLogger.d;
                    this.e = usageLogger.e;
                    this.f = usageLogger.f;
                    this.l = usageLogger.l;
                    this.m = usageLogger.m;
                    this.n = usageLogger.n;
                    this.o = usageLogger.o;
                    this.p = usageLogger.p;
                    this.q = usageLogger.q;
                    this.s = usageLogger.s;
                    this.t = usageLogger.t;
                } else {
                    Log.w(u, "null from json " + strA);
                }
            } else {
                Log.w(u, "no usage data stored, yet.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (!SharedPreferencesHelper.getInstance().isMachineTimeReset()) {
            this.m = 0L;
            SharedPreferencesHelper.getInstance().markMachineTimeAsReset();
        }
        TimerTask timerTask = this.b;
        if (timerTask != null) {
            timerTask.cancel();
        }
        b bVar = new b();
        this.b = bVar;
        this.a.scheduleAtFixedRate(bVar, 60000L, 60000L);
        App.getInstance().getMachineAdapter().registerMachineCallback(this.g);
    }

    public void stop() throws IOException {
        if (this.r) {
            this.a.cancel();
            this.a.purge();
            App.getInstance().getMachineAdapter().unregisterMachineCallback(this.g);
            b();
            this.r = false;
        }
    }

    public String toString() {
        Iterator<String> it = this.d.keySet().iterator();
        int iLongValue = 0;
        while (it.hasNext()) {
            iLongValue = (int) (this.d.get(it.next()).longValue() + iLongValue);
        }
        StringBuilder sbA = g9.a("Turbo: ");
        sbA.append(a(this.s));
        sbA.append("<br>");
        sbA.append("Längste Turbozeit: ");
        sbA.append(a(this.l));
        sbA.append("<br>");
        sbA.append("Deckel geöffnet: ");
        sbA.append(this.q);
        sbA.append("<br>");
        sbA.append("Motor gebremst: ");
        sbA.append(this.n);
        sbA.append("<br>");
        sbA.append("Waage kalibriert: ");
        sbA.append(this.p);
        sbA.append("<br>");
        sbA.append("Fehleranzahl: ");
        sbA.append(iLongValue);
        sbA.append("<br>");
        sbA.append("Letzter Fehler: ");
        sbA.append(this.f);
        sbA.append("<br>");
        sbA.append("<br><b>Motor:</b><br>");
        for (int i = 0; i <= 10; i++) {
            String strValueOf = String.valueOf(i);
            if (this.o.containsKey(strValueOf)) {
                sbA.append(String.format(Locale.GERMAN, "Stufe %d: %s<br>", Integer.valueOf(i), a(this.o.get(strValueOf).longValue())));
            }
        }
        sbA.append("<br><b>Heizelement:</b><br>");
        for (int i2 = 0; i2 <= Limits.TEMPERATURE_LEVEL_MAX; i2++) {
            String strValueOf2 = String.valueOf(i2);
            if (this.e.containsKey(strValueOf2)) {
                sbA.append(String.format(Locale.GERMAN, "Stufe %d: %s<br>", Integer.valueOf(i2), a(this.e.get(strValueOf2).longValue())));
            }
        }
        sbA.append("<br><b>Betriebsmodus:</b><br>");
        for (String str : this.t.keySet()) {
            sbA.append(String.format(Locale.GERMAN, "\"%s\": %s<br>", str, a(this.t.get(str).longValue())));
        }
        return sbA.toString();
    }

    public final List<Long> a(Map<String, Long> map, int i) {
        ArrayList arrayList = new ArrayList();
        for (int i2 = 0; i2 < i; i2++) {
            String strValueOf = String.valueOf(i2);
            if (map.containsKey(strValueOf)) {
                arrayList.add(map.get(strValueOf));
            } else {
                arrayList.add(0L);
            }
        }
        return arrayList;
    }

    public final void a(Map<String, Long> map, int i, long j) {
        a(map, String.valueOf(i), j);
    }

    public final void a(Map<String, Long> map, String str, long j) {
        if (map.containsKey(str)) {
            map.put(str, Long.valueOf(map.get(str).longValue() + j));
        } else {
            map.put(str, Long.valueOf(j));
        }
    }

    public final String a() throws IOException {
        try {
            FileInputStream fileInputStreamOpenFileInput = App.getInstance().openFileInput("usage_log.json");
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStreamOpenFileInput));
                StringBuilder sb = new StringBuilder();
                while (true) {
                    String line = bufferedReader.readLine();
                    if (line == null) {
                        break;
                    }
                    sb.append(line);
                }
                String string = sb.toString();
                if (fileInputStreamOpenFileInput != null) {
                    fileInputStreamOpenFileInput.close();
                }
                return string;
            } finally {
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static /* synthetic */ void a(UsageLogger usageLogger) throws IOException {
        if (usageLogger != null) {
            boolean zIsMachineSleeping = App.getInstance().isMachineSleeping();
            String str = u;
            StringBuilder sbA = g9.a("usageMinuteElapsed @");
            sbA.append(System.currentTimeMillis());
            sbA.append(" >> sleeping ");
            sbA.append(zIsMachineSleeping);
            Log.i(str, sbA.toString());
            usageLogger.m++;
            if (!zIsMachineSleeping) {
                usageLogger.c++;
            }
            usageLogger.b();
            return;
        }
        throw null;
    }
}
