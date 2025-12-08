package de.silpion.mcupdater;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import de.silpion.mc2.BuildConfig;
import de.silpion.mcupdater.IStateCallback;
import de.silpion.mcupdater.IUpdateService;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class UpdateServiceAdapter {
    public static WeakReference<UpdateServiceAdapter> e;
    public boolean a;
    public final List<StateListener> b = new ArrayList();
    public final IStateCallback c = new a();
    public IUpdateService d;

    public interface StateListener {
        void checking();

        void downloadProgress(int i);

        void downloading();

        void idle();

        void installing();

        void unknown();

        void updateAvailable();

        void updateReady();
    }

    public static class StateListenerAdapter implements StateListener {
        @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
        public void checking() {
        }

        @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
        public void downloadProgress(int i) {
        }

        @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
        public void downloading() {
        }

        @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
        public void idle() {
        }

        @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
        public void installing() {
        }

        @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
        public void unknown() {
        }

        @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
        public void updateAvailable() {
        }

        @Override // de.silpion.mcupdater.UpdateServiceAdapter.StateListener
        public void updateReady() {
        }
    }

    public class a extends IStateCallback.Stub {
        public a() {
        }

        @Override // de.silpion.mcupdater.IStateCallback
        public void onDownloadProgress(int i) throws RemoteException {
            Log.i("UpdateServiceAdapter", "UpdateService " + i + "%");
            Iterator<StateListener> it = UpdateServiceAdapter.this.b.iterator();
            while (it.hasNext()) {
                it.next().downloadProgress(i);
            }
        }

        @Override // de.silpion.mcupdater.IStateCallback
        public void onStateChanged(int i, int i2) throws RemoteException {
            synchronized (UpdateServiceAdapter.this.b) {
                Log.i("UpdateServiceAdapter", "UpdateService " + i + " -> " + i2 + ", " + UpdateServiceAdapter.this.b.size() + " listener(s)");
                for (StateListener stateListener : UpdateServiceAdapter.this.b) {
                    if (i2 == 0) {
                        stateListener.idle();
                    } else if (i2 == 1) {
                        stateListener.checking();
                    } else if (i2 == 2) {
                        stateListener.updateAvailable();
                    } else if (i2 == 3) {
                        stateListener.downloading();
                    } else if (i2 == 4) {
                        stateListener.updateReady();
                    } else if (i2 != 5) {
                        stateListener.unknown();
                    } else {
                        stateListener.installing();
                    }
                }
            }
        }
    }

    public class b implements ServiceConnection {
        public b() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i("UpdateServiceAdapter", "onServiceConnected " + componentName);
            UpdateServiceAdapter.this.d = IUpdateService.Stub.asInterface(iBinder);
            try {
                UpdateServiceAdapter.this.d.registerStateCallback(UpdateServiceAdapter.this.c);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            UpdateServiceAdapter.this.a = false;
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i("UpdateServiceAdapter", "onServiceDisconnected " + componentName);
            UpdateServiceAdapter updateServiceAdapter = UpdateServiceAdapter.this;
            updateServiceAdapter.d = null;
            updateServiceAdapter.a = false;
        }
    }

    public static synchronized UpdateServiceAdapter getInstance() {
        if (e == null || e.get() == null) {
            e = new WeakReference<>(new UpdateServiceAdapter());
        }
        return e.get();
    }

    public final synchronized void a(Context context) {
        if (this.a) {
            return;
        }
        this.a = true;
        Intent intent = new Intent();
        intent.setComponent(new ComponentName(BuildConfig.UPDATE_SERVICE_PACKAGE_NAME, BuildConfig.UPDATE_SERVICE_IDENTIFIER));
        this.a = context.bindService(intent, new b(), 1);
    }

    public void addStateListener(@NonNull StateListener stateListener) {
        Log.i("UpdateServiceAdapter", "adding listener " + stateListener);
        this.b.add(stateListener);
    }

    public synchronized IUpdateService getUpdateService(@NonNull Context context) {
        if (this.d == null) {
            if (this.d == null) {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(BuildConfig.UPDATE_SERVICE_PACKAGE_NAME, BuildConfig.UPDATE_SERVICE_IDENTIFIER));
                context.startService(intent);
            }
            a(context);
        }
        return this.d;
    }

    public void removeStateListener(@NonNull StateListener stateListener) {
        Log.i("UpdateServiceAdapter", "remove listener " + stateListener);
        this.b.remove(stateListener);
    }
}
