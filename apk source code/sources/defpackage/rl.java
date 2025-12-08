package defpackage;

import android.os.RemoteException;
import machineAdapter.IMachineCallback;
import machineAdapter.MachineException;
import machineAdapter.impl.BaseMachineAdapter;

/* compiled from: lambda */
/* loaded from: classes.dex */
public final /* synthetic */ class rl implements BaseMachineAdapter.MachineCallbackHandler {
    private final /* synthetic */ RemoteException a;

    public /* synthetic */ rl(RemoteException remoteException) {
        this.a = remoteException;
    }

    @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
    public final void handleCallback(IMachineCallback iMachineCallback) {
        iMachineCallback.onMachineException(new MachineException(this.a));
    }
}
