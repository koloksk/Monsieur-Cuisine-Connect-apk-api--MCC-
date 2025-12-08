package machineAdapter.impl.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Process;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import machineAdapter.IClientCallbackNotifier;
import machineAdapter.IMachineCallback;
import machineAdapter.impl.BaseMachineAdapter;
import machineAdapter.ipc.IPCClientInterface;

/* loaded from: classes.dex */
public class ClientService extends Service {
    public static IClientCallbackNotifier b;
    public b a;

    public class b extends IPCClientInterface.Stub {
        public /* synthetic */ b(ClientService clientService, a aVar) {
        }

        @Override // machineAdapter.ipc.IPCClientInterface
        public int getPid() {
            return Process.myPid();
        }

        @Override // machineAdapter.ipc.IPCClientInterface
        public void onBadState(final int[] iArr) throws RemoteException {
            IClientCallbackNotifier iClientCallbackNotifier = ClientService.b;
            if (iClientCallbackNotifier == null) {
                return;
            }
            iClientCallbackNotifier.notifyMachineCallbacks(new BaseMachineAdapter.MachineCallbackHandler() { // from class: bm
                @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
                public final void handleCallback(IMachineCallback iMachineCallback) {
                    iMachineCallback.onBadState(iArr);
                }
            });
        }

        @Override // machineAdapter.ipc.IPCClientInterface
        public void onCookingTimeExpired() throws RemoteException {
        }

        @Override // machineAdapter.ipc.IPCClientInterface
        public void onLidStateChanged(int i) throws RemoteException {
            IClientCallbackNotifier iClientCallbackNotifier = ClientService.b;
            if (iClientCallbackNotifier == null) {
                return;
            }
            if (i == 1) {
                iClientCallbackNotifier.notifyMachineCallbacks(new BaseMachineAdapter.MachineCallbackHandler() { // from class: fm
                    @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
                    public final void handleCallback(IMachineCallback iMachineCallback) {
                        iMachineCallback.onLidClosed();
                    }
                });
            } else {
                iClientCallbackNotifier.notifyMachineCallbacks(new BaseMachineAdapter.MachineCallbackHandler() { // from class: em
                    @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
                    public final void handleCallback(IMachineCallback iMachineCallback) {
                        iMachineCallback.onLidOpened();
                    }
                });
            }
        }

        @Override // machineAdapter.ipc.IPCClientInterface
        public void onMotorStateChanged(final int i, final int i2, final long j) throws RemoteException {
            IClientCallbackNotifier iClientCallbackNotifier = ClientService.b;
            if (iClientCallbackNotifier == null) {
                return;
            }
            iClientCallbackNotifier.notifyMachineCallbacks(new BaseMachineAdapter.MachineCallbackHandler() { // from class: xl
                @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
                public final void handleCallback(IMachineCallback iMachineCallback) {
                    iMachineCallback.onMotorStateChanged(i);
                }
            });
            ClientService.b.notifyMachineCallbacks(new BaseMachineAdapter.MachineCallbackHandler() { // from class: am
                @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
                public final void handleCallback(IMachineCallback iMachineCallback) {
                    iMachineCallback.onMotorSpeedChanged(j, i2);
                }
            });
        }

        @Override // machineAdapter.ipc.IPCClientInterface
        public void onScaleStateChanged(final int i, final long j, final int i2) throws RemoteException {
            IClientCallbackNotifier iClientCallbackNotifier = ClientService.b;
            if (iClientCallbackNotifier == null) {
                return;
            }
            iClientCallbackNotifier.notifyMachineCallbacks(new BaseMachineAdapter.MachineCallbackHandler() { // from class: yl
                @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
                public final void handleCallback(IMachineCallback iMachineCallback) {
                    iMachineCallback.onScaleStateChanged(i, j, i2);
                }
            });
        }

        @Override // machineAdapter.ipc.IPCClientInterface
        public void onTemperatureChanged(final long j) throws RemoteException {
            IClientCallbackNotifier iClientCallbackNotifier = ClientService.b;
            if (iClientCallbackNotifier == null) {
                return;
            }
            iClientCallbackNotifier.notifyMachineCallbacks(new BaseMachineAdapter.MachineCallbackHandler() { // from class: zl
                @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
                public final void handleCallback(IMachineCallback iMachineCallback) {
                    iMachineCallback.onTemperatureChanged(j);
                }
            });
        }
    }

    public static void setClientCallbackNotifier(IClientCallbackNotifier iClientCallbackNotifier) {
        b = iClientCallbackNotifier;
    }

    @Override // android.app.Service
    @Nullable
    public IBinder onBind(Intent intent) {
        return this.a;
    }

    @Override // android.app.Service
    public void onCreate() {
        this.a = new b(this, null);
    }
}
