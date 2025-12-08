package defpackage;

import android.app.Service;
import android.content.Context;
import android.util.Log;
import android.view.KeyEvent;
import machineAdapter.IMachineCallback;
import machineAdapter.impl.BaseMachineAdapter;
import machineAdapter.impl.IPCMachineAdapter;

/* loaded from: classes.dex */
public final class ul extends IPCMachineAdapter {
    public static final String o = ul.class.getSimpleName();

    public ul(Class<? extends Service> cls, Context context) {
        super(cls, context);
    }

    @Override // machineAdapter.impl.BaseMachineAdapter, android.view.KeyEvent.Callback
    public boolean onKeyDown(int i, KeyEvent keyEvent) {
        String str = o;
        StringBuilder sbA = g9.a("onKeyDown ");
        sbA.append(keyEvent.getKeyCode());
        Log.v(str, sbA.toString());
        if (keyEvent.getKeyCode() != 135) {
            return super.onKeyDown(i, keyEvent);
        }
        notifyMachineCallbacks(new BaseMachineAdapter.MachineCallbackHandler() { // from class: pl
            @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
            public final void handleCallback(IMachineCallback iMachineCallback) {
                iMachineCallback.onJogDialPushed(0);
            }
        });
        return true;
    }

    @Override // machineAdapter.impl.BaseMachineAdapter, android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i, KeyEvent keyEvent) {
        String str = o;
        StringBuilder sbA = g9.a("onKeyLongPress ");
        sbA.append(keyEvent.getKeyCode());
        Log.v(str, sbA.toString());
        return keyEvent.getKeyCode() == 135 || super.onKeyLongPress(i, keyEvent);
    }

    @Override // machineAdapter.impl.BaseMachineAdapter, android.view.KeyEvent.Callback
    public boolean onKeyUp(int i, KeyEvent keyEvent) {
        String str = o;
        StringBuilder sbA = g9.a("onKeyUp ");
        sbA.append(keyEvent.getKeyCode());
        Log.v(str, sbA.toString());
        if (keyEvent.getKeyCode() != 135) {
            return super.onKeyUp(i, keyEvent);
        }
        notifyMachineCallbacks(new BaseMachineAdapter.MachineCallbackHandler() { // from class: ql
            @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
            public final void handleCallback(IMachineCallback iMachineCallback) {
                iMachineCallback.onJogDialPushed(1);
            }
        });
        return true;
    }
}
