package application;

import activity.MainActivity;
import android.app.Activity;
import android.app.Application;
import android.app.backup.BackupManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import application.App;
import db.DbHelper;
import defpackage.g9;
import helper.PowerManagerWrapper;
import helper.SharedPreferencesHelper;
import helper.UsageLogger;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import machineAdapter.ICommandInterface;
import machineAdapter.IMachineAdapter;
import machineAdapter.adapter.ActivityLifecycleCallbacksAdapter;
import machineAdapter.impl.MachineAdapterFactory;
import mcapi.McUsageApi;
import sound.SoundManager;

/* loaded from: classes.dex */
public class App extends Application {
    public static final String h = App.class.getSimpleName();
    public static App i;
    public final Handler a;
    public boolean b;
    public IMachineAdapter c;
    public MainActivity d;
    public final Application.ActivityLifecycleCallbacks e = new a();
    public PowerManagerWrapper f;
    public SoundManager g;

    public class a extends ActivityLifecycleCallbacksAdapter {
        public a() {
        }

        public static /* synthetic */ void a() throws InterruptedException {
            try {
                Thread.sleep(5000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            McUsageApi.getInstance().postAllMachineUsageLog();
        }

        @Override // machineAdapter.adapter.ActivityLifecycleCallbacksAdapter, android.app.Application.ActivityLifecycleCallbacks
        public void onActivityCreated(Activity activity2, Bundle bundle) {
            if (activity2 instanceof MainActivity) {
                App.this.d = (MainActivity) activity2;
            }
        }

        @Override // machineAdapter.adapter.ActivityLifecycleCallbacksAdapter, android.app.Application.ActivityLifecycleCallbacks
        public void onActivityPaused(Activity activity2) {
            Log.i(App.h, "Activity paused " + activity2);
            App.this.b = false;
        }

        @Override // machineAdapter.adapter.ActivityLifecycleCallbacksAdapter, android.app.Application.ActivityLifecycleCallbacks
        public void onActivityResumed(Activity activity2) {
            Log.i(App.h, "Activity resumed " + activity2);
            UsageLogger.getInstance().start();
            new Thread(new Runnable() { // from class: d9
                @Override // java.lang.Runnable
                public final void run() throws InterruptedException {
                    App.a.a();
                }
            }).start();
            App.this.b = true;
        }

        @Override // machineAdapter.adapter.ActivityLifecycleCallbacksAdapter, android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity2) {
        }
    }

    public App() {
        Log.w(h, "new instance");
        this.a = new Handler();
    }

    public static App getInstance() {
        return i;
    }

    public /* synthetic */ void a(boolean z) {
        Log.i(h, "display-app entering sleep");
        if (z) {
            a().sleepNow();
        } else {
            a().napNow();
        }
    }

    public final SoundManager b() {
        if (this.g == null) {
            this.g = new SoundManager();
        }
        return this.g;
    }

    public void changeLocale(Locale locale) throws IllegalAccessException, ClassNotFoundException, IllegalArgumentException, InvocationTargetException {
        if (SharedPreferencesHelper.getInstance().isLocaleChosen() && SharedPreferencesHelper.getInstance().getLanguage().equals(locale.getLanguage())) {
            String str = h;
            StringBuilder sbA = g9.a("Won't apply locale ");
            sbA.append(locale.getLanguage());
            sbA.append(" - already selected.");
            Log.w(str, sbA.toString());
            return;
        }
        SharedPreferencesHelper.getInstance().setLanguage(locale.getLanguage());
        SharedPreferencesHelper.getInstance().setLocaleChosen();
        SharedPreferencesHelper.getInstance().flush();
        try {
            Class<?> cls = Class.forName("android.app.IActivityManager");
            Class<?> cls2 = Class.forName("android.app.ActivityManagerNative");
            Object objInvoke = cls2.getDeclaredMethod("getDefault", new Class[0]).invoke(cls2, new Object[0]);
            android.content.res.Configuration configuration = (android.content.res.Configuration) cls.getDeclaredMethod("getConfiguration", new Class[0]).invoke(objInvoke, new Object[0]);
            configuration.locale = locale;
            Class.forName("android.content.res.Configuration").getField("userSetLocale").set(configuration, true);
            cls.getDeclaredMethod("updateConfiguration", android.content.res.Configuration.class).invoke(objInvoke, configuration);
            BackupManager.dataChanged("com.android.providers.settings");
        } catch (Exception e) {
            Log.e(h, "error while setting up keyboard language");
            e.printStackTrace();
        }
        System.exit(0);
    }

    public IMachineAdapter getMachineAdapter() {
        return this.c;
    }

    public MainActivity getMainActivity() {
        return this.d;
    }

    public boolean isAppInForeground() {
        return this.b;
    }

    public boolean isMachineSleeping() {
        return a().isSleeping();
    }

    @Override // android.app.Application
    public void onCreate() {
        Log.i(h, "onCreate");
        i = this;
        super.onCreate();
        registerActivityLifecycleCallbacks(this.e);
        this.c = new MachineAdapterFactory().createMachineAdapter(this);
        DbHelper.getInstance().createSession(this);
    }

    public void playSound(int i2, int i3) {
        b().playSound(i2, i3);
    }

    public void setSoundVolume(int i2) {
        b().setSoundVolume(i2);
    }

    public void sleep(final boolean z) {
        ICommandInterface commandInterface = getMachineAdapter().getCommandInterface();
        if (commandInterface != null) {
            commandInterface.start(6, 0, 0, 0);
        }
        this.a.postDelayed(new Runnable() { // from class: e9
            @Override // java.lang.Runnable
            public final void run() {
                this.a.a(z);
            }
        }, 2000L);
    }

    public void startSoundManager() {
        b().start(this);
    }

    public void wakeUpMachine() {
        a().wakeUpNow();
        ICommandInterface commandInterface = getMachineAdapter().getCommandInterface();
        if (commandInterface != null) {
            commandInterface.start(0, 0, 0, 0);
        }
    }

    public final PowerManagerWrapper a() {
        if (this.f == null) {
            this.f = new PowerManagerWrapper(this);
        }
        return this.f;
    }
}
