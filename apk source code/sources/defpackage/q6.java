package defpackage;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatCallback;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.app.AppCompatDelegateImplV9;
import android.support.v7.view.SupportActionModeWrapper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ActionMode;
import android.view.View;
import android.view.Window;
import defpackage.o6;
import java.lang.reflect.Field;

@RequiresApi(14)
/* loaded from: classes.dex */
public class q6 extends AppCompatDelegateImplV9 {
    public int R;
    public boolean S;
    public boolean T;
    public b U;

    public class a extends o6.c {
        public a(Window.Callback callback) {
            super(callback);
        }

        public final ActionMode a(ActionMode.Callback callback) {
            SupportActionModeWrapper.CallbackWrapper callbackWrapper = new SupportActionModeWrapper.CallbackWrapper(q6.this.c, callback);
            android.support.v7.view.ActionMode actionModeStartSupportActionMode = q6.this.startSupportActionMode(callbackWrapper);
            if (actionModeStartSupportActionMode != null) {
                return callbackWrapper.getActionModeWrapper(actionModeStartSupportActionMode);
            }
            return null;
        }
    }

    @VisibleForTesting
    public final class b {
        public a7 a;
        public boolean b;
        public BroadcastReceiver c;
        public IntentFilter d;

        public b(@NonNull a7 a7Var) {
            this.a = a7Var;
            this.b = a7Var.a();
        }

        public final void a() {
            BroadcastReceiver broadcastReceiver = this.c;
            if (broadcastReceiver != null) {
                q6.this.c.unregisterReceiver(broadcastReceiver);
                this.c = null;
            }
        }
    }

    public q6(Context context, Window window, AppCompatCallback appCompatCallback) {
        super(context, window, appCompatCallback);
        this.R = -100;
        this.T = true;
    }

    @Override // android.support.v7.app.AppCompatDelegateImplV9
    public View a(View view2, String str, Context context, AttributeSet attributeSet) {
        return null;
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public boolean applyDayNight() throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        Object obj;
        int defaultNightMode = this.R;
        if (defaultNightMode == -100) {
            defaultNightMode = AppCompatDelegate.getDefaultNightMode();
        }
        int iF = f(defaultNightMode);
        boolean z = false;
        if (iF != -1) {
            Resources resources = this.c.getResources();
            Configuration configuration = resources.getConfiguration();
            int i = configuration.uiMode & 48;
            int i2 = iF == 2 ? 32 : 16;
            if (i != i2) {
                if (this.S) {
                    Context context = this.c;
                    if (context instanceof Activity) {
                        try {
                        } catch (PackageManager.NameNotFoundException e) {
                            Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e);
                        }
                        if ((context.getPackageManager().getActivityInfo(new ComponentName(this.c, this.c.getClass()), 0).configChanges & 512) == 0) {
                            z = true;
                        }
                    }
                }
                if (z) {
                    ((Activity) this.c).recreate();
                } else {
                    Configuration configuration2 = new Configuration(configuration);
                    DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                    configuration2.uiMode = i2 | (configuration2.uiMode & (-49));
                    resources.updateConfiguration(configuration2, displayMetrics);
                    int i3 = Build.VERSION.SDK_INT;
                    if (i3 < 26) {
                        Object obj2 = null;
                        if (i3 >= 24) {
                            if (!q5.h) {
                                try {
                                    Field declaredField = Resources.class.getDeclaredField("mResourcesImpl");
                                    q5.g = declaredField;
                                    declaredField.setAccessible(true);
                                } catch (NoSuchFieldException e2) {
                                    Log.e("ResourcesFlusher", "Could not retrieve Resources#mResourcesImpl field", e2);
                                }
                                q5.h = true;
                            }
                            Field field = q5.g;
                            if (field != null) {
                                try {
                                    obj = field.get(resources);
                                } catch (IllegalAccessException e3) {
                                    Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mResourcesImpl", e3);
                                    obj = null;
                                }
                                if (obj != null) {
                                    if (!q5.b) {
                                        try {
                                            Field declaredField2 = obj.getClass().getDeclaredField("mDrawableCache");
                                            q5.a = declaredField2;
                                            declaredField2.setAccessible(true);
                                        } catch (NoSuchFieldException e4) {
                                            Log.e("ResourcesFlusher", "Could not retrieve ResourcesImpl#mDrawableCache field", e4);
                                        }
                                        q5.b = true;
                                    }
                                    Field field2 = q5.a;
                                    if (field2 != null) {
                                        try {
                                            obj2 = field2.get(obj);
                                        } catch (IllegalAccessException e5) {
                                            Log.e("ResourcesFlusher", "Could not retrieve value from ResourcesImpl#mDrawableCache", e5);
                                        }
                                    }
                                    if (obj2 != null) {
                                        q5.a(obj2);
                                    }
                                }
                            }
                        } else {
                            if (!q5.b) {
                                try {
                                    Field declaredField3 = Resources.class.getDeclaredField("mDrawableCache");
                                    q5.a = declaredField3;
                                    declaredField3.setAccessible(true);
                                } catch (NoSuchFieldException e6) {
                                    Log.e("ResourcesFlusher", "Could not retrieve Resources#mDrawableCache field", e6);
                                }
                                q5.b = true;
                            }
                            Field field3 = q5.a;
                            if (field3 != null) {
                                try {
                                    obj2 = field3.get(resources);
                                } catch (IllegalAccessException e7) {
                                    Log.e("ResourcesFlusher", "Could not retrieve value from Resources#mDrawableCache", e7);
                                }
                            }
                            if (obj2 != null) {
                                q5.a(obj2);
                            }
                        }
                    }
                }
                z = true;
            }
        }
        if (defaultNightMode == 0) {
            h();
            b bVar = this.U;
            bVar.a();
            if (bVar.c == null) {
                bVar.c = new r6(bVar);
            }
            if (bVar.d == null) {
                IntentFilter intentFilter = new IntentFilter();
                bVar.d = intentFilter;
                intentFilter.addAction("android.intent.action.TIME_SET");
                bVar.d.addAction("android.intent.action.TIMEZONE_CHANGED");
                bVar.d.addAction("android.intent.action.TIME_TICK");
            }
            q6.this.c.registerReceiver(bVar.c, bVar.d);
        }
        this.S = true;
        return z;
    }

    public int f(int i) {
        if (i == -100) {
            return -1;
        }
        if (i != 0) {
            return i;
        }
        h();
        b bVar = this.U;
        boolean zA = bVar.a.a();
        bVar.b = zA;
        return zA ? 2 : 1;
    }

    public final void h() {
        if (this.U == null) {
            Context context = this.c;
            if (a7.d == null) {
                Context applicationContext = context.getApplicationContext();
                a7.d = new a7(applicationContext, (LocationManager) applicationContext.getSystemService("location"));
            }
            this.U = new b(a7.d);
        }
    }

    @Override // android.support.v7.app.AppCompatDelegateImplV9, android.support.v7.app.AppCompatDelegate
    public boolean hasWindowFeature(int i) {
        int iD = d(i);
        return (iD != 1 ? iD != 2 ? iD != 5 ? iD != 10 ? iD != 108 ? iD != 109 ? false : this.k : this.j : this.l : this.F : this.E : this.n) || this.d.hasFeature(i);
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public boolean isHandleNativeActionModesEnabled() {
        return this.T;
    }

    @Override // android.support.v7.app.AppCompatDelegateImplV9, android.support.v7.app.AppCompatDelegate
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null || this.R != -100) {
            return;
        }
        this.R = bundle.getInt("appcompat:local_night_mode", -100);
    }

    @Override // android.support.v7.app.AppCompatDelegateImplV9, defpackage.o6, android.support.v7.app.AppCompatDelegate
    public void onDestroy() {
        super.onDestroy();
        b bVar = this.U;
        if (bVar != null) {
            bVar.a();
        }
    }

    @Override // defpackage.o6, android.support.v7.app.AppCompatDelegate
    public void onSaveInstanceState(Bundle bundle) {
        int i = this.R;
        if (i != -100) {
            bundle.putInt("appcompat:local_night_mode", i);
        }
    }

    @Override // defpackage.o6, android.support.v7.app.AppCompatDelegate
    public void onStart() throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        applyDayNight();
    }

    @Override // android.support.v7.app.AppCompatDelegateImplV9, android.support.v7.app.AppCompatDelegate
    public void onStop() {
        super.onStop();
        b bVar = this.U;
        if (bVar != null) {
            bVar.a();
        }
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void setHandleNativeActionModesEnabled(boolean z) {
        this.T = z;
    }

    @Override // android.support.v7.app.AppCompatDelegate
    public void setLocalNightMode(int i) throws IllegalAccessException, NoSuchFieldException, IllegalArgumentException {
        if (i != -1 && i != 0 && i != 1 && i != 2) {
            Log.i("AppCompatDelegate", "setLocalNightMode() called with an unknown mode");
        } else if (this.R != i) {
            this.R = i;
            if (this.S) {
                applyDayNight();
            }
        }
    }
}
