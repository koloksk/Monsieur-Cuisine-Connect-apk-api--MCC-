package defpackage;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import machineAdapter.impl.service.ClientService;
import machineAdapter.impl.service.IPCMachineBinder;
import machineAdapter.ipc.DeviceState;
import machineAdapter.ipc.IPCClientInterface;

/* loaded from: classes.dex */
public abstract class gm extends Service implements hm {
    public static final String k = gm.class.getSimpleName();
    public im b;
    public im c;
    public IPCClientInterface clientInterface;
    public ReentrantReadWriteLock d;
    public ExecutorService e;
    public IPCMachineBinder f;
    public Lock g;
    public Handler h;
    public HandlerThread i;
    public final ServiceConnection a = new a();
    public Thread j = new b();

    public class a implements ServiceConnection {
        public a() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.i(gm.k, "onServiceConnected *** >>> Machine service bound to client. *** ");
            gm.this.clientInterface = IPCClientInterface.Stub.asInterface(iBinder);
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.i(gm.k, "onServiceDisconnected *** <<< Machine Service unbound from client. *** ");
            gm.this.clientInterface = null;
        }
    }

    public class b extends Thread {
        public b() {
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() throws InterruptedException {
            while (true) {
                try {
                    Thread.sleep(100L);
                    gm.this.g.lock();
                    gm.this.d.writeLock().lockInterruptibly();
                    gm gmVar = gm.this;
                    im imVar = gmVar.b;
                    gmVar.b = gmVar.c;
                    gmVar.c = imVar;
                    gm.this.onProcessSensorData(gm.this.c);
                    final gm gmVar2 = gm.this;
                    if (gmVar2.clientInterface != null) {
                        final im imVarSnapshotSensorValues = gmVar2.snapshotSensorValues(gmVar2.c);
                        final im imVarSnapshotSensorValues2 = gmVar2.snapshotSensorValues(gmVar2.b);
                        gmVar2.h.post(new Runnable() { // from class: wl
                            @Override // java.lang.Runnable
                            public final void run() {
                                gmVar2.a(imVarSnapshotSensorValues, imVarSnapshotSensorValues2);
                            }
                        });
                    }
                    gm.this.d.writeLock().unlock();
                    gm.this.onPerformPeriodicHardwareAction();
                    gm.this.g.unlock();
                } catch (InterruptedException unused) {
                    return;
                }
            }
        }
    }

    public abstract void RESA(Exception exc);

    public /* synthetic */ void a(im imVar, im imVar2) {
        try {
            if (imVar == null || imVar2 == null) {
                Log.w(k, "handleSensorValueChanges -- something is (null)");
                return;
            }
            long jAbs = Math.abs(imVar.e - imVar2.e);
            if (imVar.f != imVar2.f || imVar.d != imVar2.d || jAbs > 0) {
                this.clientInterface.onMotorStateChanged(imVar.f, imVar.d, imVar.e);
            }
            if (imVar.g != imVar2.g) {
                Log.w(k, "Calibration " + imVar2.g + " -> " + imVar.g);
            }
            long jAbs2 = Math.abs(imVar.h - imVar2.h);
            if (imVar.i != imVar2.i || jAbs2 > 0 || imVar.g != imVar2.g) {
                this.clientInterface.onScaleStateChanged(imVar.i, imVar.h, imVar.g);
            }
            long jAbs3 = Math.abs(imVar.b - imVar2.b);
            if (imVar.a != imVar2.a || jAbs3 > 0) {
                this.clientInterface.onTemperatureChanged(imVar.b);
            }
            if (imVar.c != imVar2.c) {
                this.clientInterface.onLidStateChanged(imVar.c);
            }
            if ((imVar.j == imVar2.j || imVar.j == 0) && (imVar.i == imVar2.i || imVar.i != 1)) {
                return;
            }
            this.clientInterface.onBadState(collectWarnings(imVar));
        } catch (RemoteException e) {
            String str = k;
            StringBuilder sbA = g9.a("Could not communicate with client: ");
            sbA.append(e.getMessage());
            Log.e(str, sbA.toString(), e);
            RESA(e);
        }
    }

    public final void bindToClientService() {
        Intent intent = new Intent(this, (Class<?>) ClientService.class);
        intent.setAction(ClientService.class.getName());
        bindService(intent, this.a, 1);
    }

    public int[] collectWarnings(im imVar) {
        int i = imVar.j;
        return i == 0 ? imVar.i == 1 ? new int[]{7} : new int[0] : imVar.i == 1 ? new int[]{7, i} : new int[]{i};
    }

    @Override // android.app.Service
    @Nullable
    public final synchronized IBinder onBind(Intent intent) {
        Log.w(k, "onBind");
        bindToClientService();
        this.g.unlock();
        return this.f;
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        this.e = Executors.newFixedThreadPool(2);
        this.d = new ReentrantReadWriteLock();
        HandlerThread handlerThread = new HandlerThread("SensorValueHandlerThread");
        this.i = handlerThread;
        handlerThread.start();
        this.h = new Handler(this.i.getLooper());
        this.b = new im();
        this.c = new im();
        this.f = new IPCMachineBinder(this);
        ReentrantLock reentrantLock = new ReentrantLock();
        this.g = reentrantLock;
        reentrantLock.lock();
        this.e.execute(this.j);
    }

    @Override // android.app.Service
    public void onDestroy() {
        this.e.shutdown();
        unbindFromClientService();
        super.onDestroy();
    }

    @Override // defpackage.hm
    public DeviceState onDeviceStateRequested() {
        DeviceState deviceState = new DeviceState();
        im imVarSnapshotSensorValues = snapshotSensorValues(this.c);
        if (imVarSnapshotSensorValues == null) {
            return null;
        }
        deviceState.motorState = imVarSnapshotSensorValues.f;
        deviceState.motorDirection = imVarSnapshotSensorValues.d;
        deviceState.motorSpeedLevel = imVarSnapshotSensorValues.e;
        deviceState.scaleState = imVarSnapshotSensorValues.i;
        deviceState.scaleMeasureValue = imVarSnapshotSensorValues.h;
        deviceState.heatingState = imVarSnapshotSensorValues.a;
        deviceState.temperature = imVarSnapshotSensorValues.b;
        deviceState.lidState = imVarSnapshotSensorValues.c;
        deviceState.errorState = imVarSnapshotSensorValues.j;
        return deviceState;
    }

    public abstract void onPerformPeriodicHardwareAction();

    public abstract void onProcessSensorData(im imVar);

    @Override // android.app.Service
    public int onStartCommand(Intent intent, int i, int i2) {
        Log.d(k, "onStartCommand *** Received start command!");
        return 1;
    }

    @Override // android.app.Service
    public final boolean onUnbind(Intent intent) {
        Log.w(k, "onUnbind");
        RESA(null);
        unbindFromClientService();
        this.g.lock();
        return super.onUnbind(intent);
    }

    public final im snapshotSensorValues(im imVar) {
        try {
            try {
                this.d.readLock().lock();
                return (im) imVar.clone();
            } catch (CloneNotSupportedException e) {
                Log.e(k, "snapshotSensorValues *** Could not clone sensor values.", e);
                this.d.readLock().unlock();
                return null;
            }
        } finally {
            this.d.readLock().unlock();
        }
    }

    public final void unbindFromClientService() {
        unbindService(this.a);
    }
}
