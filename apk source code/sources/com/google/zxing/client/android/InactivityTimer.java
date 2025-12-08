package com.google.zxing.client.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

/* loaded from: classes.dex */
public final class InactivityTimer {
    public final Context a;
    public Runnable e;
    public boolean f;
    public boolean c = false;
    public final BroadcastReceiver b = new b(null);
    public Handler d = new Handler();

    public final class b extends BroadcastReceiver {

        public class a implements Runnable {
            public final /* synthetic */ boolean a;

            public a(boolean z) {
                this.a = z;
            }

            @Override // java.lang.Runnable
            public void run() {
                InactivityTimer inactivityTimer = InactivityTimer.this;
                inactivityTimer.f = this.a;
                if (inactivityTimer.c) {
                    inactivityTimer.activity();
                }
            }
        }

        public /* synthetic */ b(a aVar) {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if ("android.intent.action.BATTERY_CHANGED".equals(intent.getAction())) {
                InactivityTimer.this.d.post(new a(intent.getIntExtra("plugged", -1) <= 0));
            }
        }
    }

    public InactivityTimer(Context context, Runnable runnable) {
        this.a = context;
        this.e = runnable;
    }

    public void activity() {
        this.d.removeCallbacksAndMessages(null);
        if (this.f) {
            this.d.postDelayed(this.e, 300000L);
        }
    }

    public void cancel() {
        this.d.removeCallbacksAndMessages(null);
        if (this.c) {
            this.a.unregisterReceiver(this.b);
            this.c = false;
        }
    }

    public void start() {
        if (!this.c) {
            this.a.registerReceiver(this.b, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            this.c = true;
        }
        activity();
    }
}
