package defpackage;

import android.content.Context;
import android.view.KeyEvent;
import machineAdapter.IMachineCallback;
import machineAdapter.impl.BaseMachineAdapter;
import machineAdapter.impl.IPCMachineAdapter;
import machineAdapter.impl.service.MockMachineService;

/* loaded from: classes.dex */
public final class vl extends IPCMachineAdapter {
    public vl(Class<? extends MockMachineService> cls, Context context) {
        super(cls, context);
    }

    @Override // machineAdapter.impl.BaseMachineAdapter, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != 62) {
            return super.onKeyDown(i, keyEvent);
        }
        notifyMachineCallbacks(new BaseMachineAdapter.MachineCallbackHandler() { // from class: sl
            @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
            public final void handleCallback(IMachineCallback iMachineCallback) {
                iMachineCallback.onJogDialPushed(0);
            }
        });
        return true;
    }

    @Override // machineAdapter.impl.BaseMachineAdapter, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        return keyEvent.getKeyCode() == 62 || super.onKeyLongPress(i, keyEvent);
    }

    @Override // machineAdapter.impl.BaseMachineAdapter, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() != 62) {
            return super.onKeyUp(i, keyEvent);
        }
        notifyMachineCallbacks(new BaseMachineAdapter.MachineCallbackHandler() { // from class: tl
            @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
            public final void handleCallback(IMachineCallback iMachineCallback) {
                iMachineCallback.onJogDialPushed(1);
            }
        });
        return true;
    }
}
