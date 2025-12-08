package machineAdapter.impl;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import de.silpion.mc2.BuildConfig;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import machineAdapter.IClientCallbackNotifier;
import machineAdapter.ICommandInterface;
import machineAdapter.IMachineAdapter;
import machineAdapter.IMachineCallback;

/* loaded from: classes.dex */
public abstract class BaseMachineAdapter implements IMachineAdapter, IClientCallbackNotifier {
    public static final long i;
    public static final String j;
    public final Context context;
    public ICommandInterface d;
    public final Thread g;
    public int h;
    public final Set<IMachineCallback> a = new HashSet();
    public final b b = new b();
    public final ExecutorService c = Executors.newFixedThreadPool(2);
    public int e = 0;
    public int f = 2;

    public interface MachineCallbackHandler {
        void handleCallback(IMachineCallback iMachineCallback);
    }

    public class a extends Thread {
        public a() {
        }

        public /* synthetic */ void a(IMachineCallback iMachineCallback) {
            iMachineCallback.onJogDialTurned(BaseMachineAdapter.this.f, r0.e);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() throws InterruptedException {
            long j = BaseMachineAdapter.i / 10;
            long j2 = 0;
            while (true) {
                try {
                    synchronized (this) {
                        wait();
                    }
                    int i = 0;
                    while (i < 10) {
                        Thread.sleep(j);
                        i++;
                        if (j2 != BaseMachineAdapter.this.e) {
                            j2 = BaseMachineAdapter.this.e;
                        } else if (i > BaseMachineAdapter.this.e * 5) {
                            break;
                        }
                    }
                    BaseMachineAdapter.this.notifyMachineCallbacks(new MachineCallbackHandler() { // from class: kl
                        @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
                        public final void handleCallback(IMachineCallback iMachineCallback) {
                            this.a.a(iMachineCallback);
                        }
                    });
                    BaseMachineAdapter.this.e = 0;
                } catch (InterruptedException e) {
                    Log.e(BaseMachineAdapter.j, "timerThread: InterruptedException >> " + e);
                }
            }
        }
    }

    public class b extends Thread {
        public final AtomicBoolean a = new AtomicBoolean(false);

        public b() {
        }

        public /* synthetic */ void a(IMachineCallback iMachineCallback) {
            iMachineCallback.onJogDialPagingRequested(BaseMachineAdapter.this.f);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            this.a.set(true);
            try {
                try {
                    BaseMachineAdapter.this.notifyMachineCallbacks(new MachineCallbackHandler() { // from class: ml
                        @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
                        public final void handleCallback(IMachineCallback iMachineCallback) {
                            this.a.a(iMachineCallback);
                        }
                    });
                    Thread.sleep(300L);
                } catch (InterruptedException e) {
                    Log.e(BaseMachineAdapter.j, "SlideTimerThread: InterruptedException >> " + e);
                }
            } finally {
                this.a.set(false);
            }
        }
    }

    static {
        i = MachineAdapterFactory.getBuildFlavor().equals(BuildConfig.FLAVOR) ? 70L : 200L;
        j = BaseMachineAdapter.class.getSimpleName();
    }

    public BaseMachineAdapter(Class<? extends Service> cls, Context context) {
        a aVar = new a();
        this.g = aVar;
        this.h = 2;
        this.context = context;
        this.c.execute(aVar);
        context.startService(new Intent(context, cls));
    }

    @Override // machineAdapter.IMachineAdapter
    public synchronized ICommandInterface getCommandInterface() {
        return this.d;
    }

    @Override // machineAdapter.IClientCallbackNotifier
    public synchronized void notifyMachineCallbacks(MachineCallbackHandler machineCallbackHandler) {
        Iterator<IMachineCallback> it = this.a.iterator();
        while (it.hasNext()) {
            machineCallbackHandler.handleCallback(it.next());
        }
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyDown(int i2, KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        Log.v(j, "onKeyDown " + keyCode);
        if (keyCode != 136) {
            if (keyCode != 137) {
                switch (keyCode) {
                    case 21:
                        break;
                    case 22:
                        break;
                    case 23:
                        notifyMachineCallbacks(new MachineCallbackHandler() { // from class: ll
                            @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
                            public final void handleCallback(IMachineCallback iMachineCallback) {
                                iMachineCallback.onJogDialPushed(0);
                            }
                        });
                        return true;
                    default:
                        return false;
                }
            }
            this.f = 0;
            a();
            return true;
        }
        this.f = 1;
        a();
        return true;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyLongPress(int i2, KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        Log.v(j, "onKeyLongPress " + keyCode);
        if (keyCode != 136 && keyCode != 137) {
            switch (keyCode) {
                case 21:
                case 22:
                case 23:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyMultiple(int i2, int i3, KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        Log.v(j, "onKeyMultiple " + keyCode);
        if (keyCode != 136 && keyCode != 137) {
            switch (keyCode) {
                case 21:
                case 22:
                case 23:
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    @Override // android.view.KeyEvent.Callback
    public boolean onKeyUp(int i2, KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        Log.v(j, "onKeyUp " + keyCode);
        if (keyCode != 136 && keyCode != 137) {
            switch (keyCode) {
                case 23:
                    notifyMachineCallbacks(new MachineCallbackHandler() { // from class: nl
                        @Override // machineAdapter.impl.BaseMachineAdapter.MachineCallbackHandler
                        public final void handleCallback(IMachineCallback iMachineCallback) {
                            iMachineCallback.onJogDialPushed(1);
                        }
                    });
                case 21:
                case 22:
                    return true;
                default:
                    return false;
            }
        }
        return true;
    }

    @Override // machineAdapter.IMachineAdapter
    public final synchronized void registerMachineCallback(IMachineCallback iMachineCallback) {
        Log.i(j, "registerMachineCallback " + iMachineCallback);
        if (!this.a.contains(iMachineCallback)) {
            this.a.add(iMachineCallback);
            Log.i(j, " .. registered.");
        }
    }

    @Override // machineAdapter.IMachineAdapter
    public void shutDown() {
    }

    @Override // machineAdapter.IMachineAdapter
    public final synchronized void unregisterMachineCallback(IMachineCallback iMachineCallback) {
        Log.i(j, "unregisterMachineCallback " + iMachineCallback);
        if (this.a.contains(iMachineCallback)) {
            this.a.remove(iMachineCallback);
            Log.i(j, " .. removed.");
        }
    }

    public final void a() {
        if (!this.b.a.get()) {
            this.c.execute(this.b);
        }
        int i2 = this.f;
        if (i2 != this.h) {
            this.h = i2;
            this.e = 1;
        } else {
            this.e++;
        }
        synchronized (this.g) {
            this.g.notify();
        }
    }
}
