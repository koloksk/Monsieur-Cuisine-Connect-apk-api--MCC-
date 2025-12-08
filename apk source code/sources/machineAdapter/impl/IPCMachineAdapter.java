package machineAdapter.impl;

import activity.MainActivity;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import application.App;
import defpackage.rl;
import java.util.concurrent.Semaphore;
import machineAdapter.ICommandInterface;
import machineAdapter.IMachineCallback;
import machineAdapter.adapter.ActivityLifecycleCallbacksAdapter;
import machineAdapter.impl.BaseMachineAdapter;
import machineAdapter.impl.service.ClientService;
import machineAdapter.ipc.DeviceState;
import machineAdapter.ipc.IPCMachineInterface;

/* loaded from: classes.dex */
public abstract class IPCMachineAdapter extends BaseMachineAdapter {
    public static final String n = IPCMachineAdapter.class.getSimpleName();
    public Semaphore k;
    public IPCMachineInterface l;
    public ServiceConnection m;

    public class a implements ServiceConnection {
        public a() {
        }

        @Override // android.content.ServiceConnection
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d(IPCMachineAdapter.n, "*** >>> Bound to MachineService. *** ");
            IPCMachineAdapter.this.l = IPCMachineInterface.Stub.asInterface(iBinder);
            synchronized (IPCMachineAdapter.this) {
                IPCMachineAdapter.this.k.release();
            }
            IPCMachineAdapter.this.notifyMachineCallbacks(new BaseMachineAdapter.MachineCallbackHandler() { // from class: jl
                @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
                public final void handleCallback(IMachineCallback iMachineCallback) {
                    iMachineCallback.onMachineInterfaceConnected();
                }
            });
        }

        @Override // android.content.ServiceConnection
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(IPCMachineAdapter.n, "*** <<< Unbound from MachineService. *** ");
            synchronized (IPCMachineAdapter.this) {
                IPCMachineAdapter.this.k.drainPermits();
                IPCMachineAdapter.this.l = null;
            }
            IPCMachineAdapter.this.notifyMachineCallbacks(new BaseMachineAdapter.MachineCallbackHandler() { // from class: ol
                @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
                public final void handleCallback(IMachineCallback iMachineCallback) {
                    iMachineCallback.onMachineInterfaceDisconnected();
                }
            });
        }
    }

    public class b extends ActivityLifecycleCallbacksAdapter {
        public b() {
        }

        @Override // machineAdapter.adapter.ActivityLifecycleCallbacksAdapter, android.app.Application.ActivityLifecycleCallbacks
        public void onActivityDestroyed(Activity activity2) {
            if (activity2.getClass() != MainActivity.class) {
                App.getInstance().unbindService(IPCMachineAdapter.this.m);
            }
        }

        @Override // machineAdapter.adapter.ActivityLifecycleCallbacksAdapter, android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStarted(Activity activity2) {
            activity2.getClass();
        }

        @Override // machineAdapter.adapter.ActivityLifecycleCallbacksAdapter, android.app.Application.ActivityLifecycleCallbacks
        public void onActivityStopped(Activity activity2) {
            if (activity2.getClass() != MainActivity.class) {
                App.getInstance().unbindService(IPCMachineAdapter.this.m);
            }
        }
    }

    public class c implements ICommandInterface {
        public /* synthetic */ c(a aVar) {
        }

        @Override // machineAdapter.ICommandInterface
        public ICommandInterface.HeatingElementState getCurrentHeatingElementState() {
            IPCMachineInterface iPCMachineInterface = IPCMachineAdapter.this.l;
            if (iPCMachineInterface == null) {
                return null;
            }
            try {
                DeviceState deviceStateRequestDeviceState = iPCMachineInterface.requestDeviceState();
                ICommandInterface.HeatingElementState heatingElementState = new ICommandInterface.HeatingElementState();
                heatingElementState.enableState = deviceStateRequestDeviceState.heatingState;
                heatingElementState.temperature = deviceStateRequestDeviceState.temperature;
                heatingElementState.durationLeft = deviceStateRequestDeviceState.durationLeft;
                return heatingElementState;
            } catch (RemoteException e) {
                IPCMachineAdapter.this.notifyMachineCallbacks(new rl(e));
                return null;
            }
        }

        @Override // machineAdapter.ICommandInterface
        public int getCurrentLidState() {
            IPCMachineInterface iPCMachineInterface = IPCMachineAdapter.this.l;
            if (iPCMachineInterface == null) {
                return 2;
            }
            try {
                return iPCMachineInterface.requestDeviceState().lidState;
            } catch (RemoteException e) {
                IPCMachineAdapter.this.notifyMachineCallbacks(new rl(e));
                return 2;
            }
        }

        @Override // machineAdapter.ICommandInterface
        public ICommandInterface.MotorState getCurrentMotorState() {
            IPCMachineInterface iPCMachineInterface = IPCMachineAdapter.this.l;
            if (iPCMachineInterface == null) {
                return null;
            }
            try {
                DeviceState deviceStateRequestDeviceState = iPCMachineInterface.requestDeviceState();
                ICommandInterface.MotorState motorState = new ICommandInterface.MotorState();
                motorState.enableState = deviceStateRequestDeviceState.motorState;
                motorState.direction = deviceStateRequestDeviceState.motorDirection;
                motorState.speed = deviceStateRequestDeviceState.motorSpeedLevel;
                motorState.durationLeft = deviceStateRequestDeviceState.durationLeft;
                return motorState;
            } catch (RemoteException e) {
                IPCMachineAdapter.this.notifyMachineCallbacks(new rl(e));
                return null;
            }
        }

        @Override // machineAdapter.ICommandInterface
        public ICommandInterface.ScaleState getCurrentScaleState() {
            IPCMachineInterface iPCMachineInterface = IPCMachineAdapter.this.l;
            if (iPCMachineInterface == null) {
                return null;
            }
            try {
                DeviceState deviceStateRequestDeviceState = iPCMachineInterface.requestDeviceState();
                ICommandInterface.ScaleState scaleState = new ICommandInterface.ScaleState();
                scaleState.scaleState = deviceStateRequestDeviceState.scaleState;
                scaleState.scaleMeasureValue = deviceStateRequestDeviceState.scaleMeasureValue;
                return scaleState;
            } catch (RemoteException e) {
                IPCMachineAdapter.this.notifyMachineCallbacks(new rl(e));
                return null;
            }
        }

        @Override // machineAdapter.ICommandInterface
        public int getErrorState() {
            IPCMachineInterface iPCMachineInterface = IPCMachineAdapter.this.l;
            if (iPCMachineInterface == null) {
                return 6;
            }
            try {
                return iPCMachineInterface.requestDeviceState().errorState;
            } catch (RemoteException e) {
                IPCMachineAdapter.this.notifyMachineCallbacks(new rl(e));
                return 6;
            }
        }

        @Override // machineAdapter.ICommandInterface
        public int getFirmwareVersion() {
            IPCMachineInterface iPCMachineInterface = IPCMachineAdapter.this.l;
            if (iPCMachineInterface == null) {
                return 0;
            }
            try {
                return iPCMachineInterface.getFirmwareVersion();
            } catch (RemoteException e) {
                IPCMachineAdapter.this.notifyMachineCallbacks(new rl(e));
                return 0;
            }
        }

        @Override // machineAdapter.ICommandInterface
        public boolean setScaleCalibration(int i) {
            IPCMachineInterface iPCMachineInterface = IPCMachineAdapter.this.l;
            if (iPCMachineInterface == null) {
                return false;
            }
            try {
                iPCMachineInterface.requestScaleCalibrate(i);
                return true;
            } catch (RemoteException e) {
                IPCMachineAdapter.this.notifyMachineCallbacks(new rl(e));
                return false;
            }
        }

        @Override // machineAdapter.ICommandInterface
        public boolean setScaleTare() {
            IPCMachineInterface iPCMachineInterface = IPCMachineAdapter.this.l;
            if (iPCMachineInterface == null) {
                return false;
            }
            try {
                iPCMachineInterface.requestScaleTare();
                return true;
            } catch (RemoteException e) {
                IPCMachineAdapter.this.notifyMachineCallbacks(new rl(e));
                return false;
            }
        }

        @Override // machineAdapter.ICommandInterface
        public boolean start(int i, int i2, int i3, int i4) {
            IPCMachineInterface iPCMachineInterface = IPCMachineAdapter.this.l;
            if (iPCMachineInterface == null) {
                return false;
            }
            try {
                iPCMachineInterface.requestStart(i, i2, i3, i4);
                return true;
            } catch (RemoteException e) {
                IPCMachineAdapter.this.notifyMachineCallbacks(new rl(e));
                return false;
            }
        }

        @Override // machineAdapter.ICommandInterface
        public boolean stop() {
            IPCMachineInterface iPCMachineInterface = IPCMachineAdapter.this.l;
            if (iPCMachineInterface == null) {
                return false;
            }
            try {
                iPCMachineInterface.requestStop();
                return true;
            } catch (RemoteException e) {
                IPCMachineAdapter.this.notifyMachineCallbacks(new rl(e));
                return false;
            }
        }
    }

    public IPCMachineAdapter(Class<? extends Service> cls, Context context) {
        super(cls, context);
        this.m = new a();
        this.k = new Semaphore(0);
        this.d = new c(null);
        ClientService.setClientCallbackNotifier(this);
        Intent intent = new Intent(context, cls);
        intent.setAction(cls.getName());
        context.bindService(intent, this.m, 0);
        Log.d(n, "--- Registering new lifecycle callbacks. ---");
        App.getInstance().registerActivityLifecycleCallbacks(new b());
    }

    @Override // machineAdapter.impl.BaseMachineAdapter, machineAdapter.IMachineAdapter
    public synchronized ICommandInterface getCommandInterface() {
        if (this.d == null) {
            try {
                this.k.acquire();
            } catch (InterruptedException unused) {
                return null;
            }
        }
        return this.d;
    }

    @Override // machineAdapter.impl.BaseMachineAdapter, machineAdapter.IMachineAdapter
    public void shutDown() {
        super.shutDown();
        this.context.unbindService(this.m);
        this.context.stopService(new Intent(this.context, (Class<?>) ClientService.class));
    }
}
