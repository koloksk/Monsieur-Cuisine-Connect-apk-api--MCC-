package defpackage;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import defpackage.jd;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/* loaded from: classes.dex */
public class cd {
    public final b a;
    public final Context b;
    public final ExecutorService c;
    public final Downloader d;
    public final Map<String, xc> e;
    public final Map<Object, vc> f;
    public final Map<Object, vc> g;
    public final Set<Object> h;
    public final Handler i;
    public final Handler j;
    public final Cache k;
    public final od l;
    public final List<xc> m;
    public final c n;
    public final boolean o;
    public boolean p;

    public static class a extends Handler {
        public final cd a;

        /* renamed from: cd$a$a, reason: collision with other inner class name */
        public class RunnableC0008a implements Runnable {
            public final /* synthetic */ Message a;

            public RunnableC0008a(a aVar, Message message) {
                this.a = message;
            }

            @Override // java.lang.Runnable
            public void run() {
                StringBuilder sbA = g9.a("Unknown handler message received: ");
                sbA.append(this.a.what);
                throw new AssertionError(sbA.toString());
            }
        }

        public a(Looper looper, cd cdVar) {
            super(looper);
            this.a = cdVar;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            boolean zA;
            Object objB;
            ArrayList arrayList = null;
            switch (message.what) {
                case 1:
                    this.a.a((vc) message.obj, true);
                    return;
                case 2:
                    vc vcVar = (vc) message.obj;
                    cd cdVar = this.a;
                    if (cdVar == null) {
                        throw null;
                    }
                    String str = vcVar.i;
                    xc xcVar = cdVar.e.get(str);
                    if (xcVar != null) {
                        xcVar.a(vcVar);
                        if (xcVar.a()) {
                            cdVar.e.remove(str);
                            if (vcVar.a.n) {
                                qd.a("Dispatcher", "canceled", vcVar.b.a(), "");
                            }
                        }
                    }
                    if (cdVar.h.contains(vcVar.j)) {
                        cdVar.g.remove(vcVar.b());
                        if (vcVar.a.n) {
                            qd.a("Dispatcher", "canceled", vcVar.b.a(), "because paused request got canceled");
                        }
                    }
                    vc vcVarRemove = cdVar.f.remove(vcVar.b());
                    if (vcVarRemove == null || !vcVarRemove.a.n) {
                        return;
                    }
                    qd.a("Dispatcher", "canceled", vcVarRemove.b.a(), "from replaying");
                    return;
                case 3:
                case 8:
                default:
                    Picasso.p.post(new RunnableC0008a(this, message));
                    return;
                case 4:
                    xc xcVar2 = (xc) message.obj;
                    cd cdVar2 = this.a;
                    if (cdVar2 == null) {
                        throw null;
                    }
                    if ((xcVar2.h & MemoryPolicy.NO_STORE.a) == 0) {
                        cdVar2.k.set(xcVar2.f, xcVar2.m);
                    }
                    cdVar2.e.remove(xcVar2.f);
                    cdVar2.a(xcVar2);
                    if (xcVar2.b.n) {
                        qd.a("Dispatcher", "batched", qd.a(xcVar2), "for completion");
                        return;
                    }
                    return;
                case 5:
                    xc xcVar3 = (xc) message.obj;
                    cd cdVar3 = this.a;
                    if (cdVar3 == null) {
                        throw null;
                    }
                    Future<?> future = xcVar3.n;
                    if (future != null && future.isCancelled()) {
                        return;
                    }
                    if (cdVar3.c.isShutdown()) {
                        cdVar3.a(xcVar3, false);
                        return;
                    }
                    NetworkInfo activeNetworkInfo = cdVar3.o ? ((ConnectivityManager) qd.a(cdVar3.b, "connectivity")).getActiveNetworkInfo() : null;
                    boolean z = cdVar3.p;
                    if (xcVar3.r > 0) {
                        xcVar3.r--;
                        zA = xcVar3.j.a(z, activeNetworkInfo);
                    } else {
                        zA = false;
                    }
                    if (zA) {
                        if (xcVar3.b.n) {
                            qd.a("Dispatcher", "retrying", qd.a(xcVar3), "");
                        }
                        if (xcVar3.p instanceof jd.a) {
                            xcVar3.i |= NetworkPolicy.NO_CACHE.a;
                        }
                        xcVar3.n = cdVar3.c.submit(xcVar3);
                        return;
                    }
                    boolean z2 = cdVar3.o && xcVar3.j.b();
                    cdVar3.a(xcVar3, z2);
                    if (z2) {
                        vc vcVar2 = xcVar3.k;
                        if (vcVar2 != null && (objB = vcVar2.b()) != null) {
                            vcVar2.k = true;
                            cdVar3.f.put(objB, vcVar2);
                        }
                        List<vc> list = xcVar3.l;
                        if (list != null) {
                            int size = list.size();
                            for (int i = 0; i < size; i++) {
                                vc vcVar3 = list.get(i);
                                Object objB2 = vcVar3.b();
                                if (objB2 != null) {
                                    vcVar3.k = true;
                                    cdVar3.f.put(objB2, vcVar3);
                                }
                            }
                            return;
                        }
                        return;
                    }
                    return;
                case 6:
                    this.a.a((xc) message.obj, false);
                    return;
                case 7:
                    cd cdVar4 = this.a;
                    if (cdVar4 == null) {
                        throw null;
                    }
                    ArrayList arrayList2 = new ArrayList(cdVar4.m);
                    cdVar4.m.clear();
                    Handler handler = cdVar4.j;
                    handler.sendMessage(handler.obtainMessage(8, arrayList2));
                    if (!arrayList2.isEmpty() && ((xc) arrayList2.get(0)).b.n) {
                        StringBuilder sb = new StringBuilder();
                        Iterator it = arrayList2.iterator();
                        while (it.hasNext()) {
                            xc xcVar4 = (xc) it.next();
                            if (sb.length() > 0) {
                                sb.append(", ");
                            }
                            sb.append(qd.a(xcVar4));
                        }
                        qd.a("Dispatcher", "delivered", sb.toString(), "");
                        return;
                    }
                    return;
                case 9:
                    NetworkInfo networkInfo = (NetworkInfo) message.obj;
                    cd cdVar5 = this.a;
                    ExecutorService executorService = cdVar5.c;
                    if (executorService instanceof ld) {
                        ld ldVar = (ld) executorService;
                        if (ldVar == null) {
                            throw null;
                        }
                        if (networkInfo != null && networkInfo.isConnectedOrConnecting()) {
                            int type = networkInfo.getType();
                            if (type == 0) {
                                int subtype = networkInfo.getSubtype();
                                switch (subtype) {
                                    case 1:
                                    case 2:
                                        ldVar.setCorePoolSize(1);
                                        ldVar.setMaximumPoolSize(1);
                                        break;
                                    default:
                                        switch (subtype) {
                                            case 12:
                                                break;
                                            case 13:
                                            case 14:
                                            case 15:
                                                ldVar.setCorePoolSize(3);
                                                ldVar.setMaximumPoolSize(3);
                                                break;
                                            default:
                                                ldVar.setCorePoolSize(3);
                                                ldVar.setMaximumPoolSize(3);
                                                break;
                                        }
                                    case 3:
                                    case 4:
                                    case 5:
                                    case 6:
                                        ldVar.setCorePoolSize(2);
                                        ldVar.setMaximumPoolSize(2);
                                        break;
                                }
                            } else if (type == 1 || type == 6 || type == 9) {
                                ldVar.setCorePoolSize(4);
                                ldVar.setMaximumPoolSize(4);
                            } else {
                                ldVar.setCorePoolSize(3);
                                ldVar.setMaximumPoolSize(3);
                            }
                        } else {
                            ldVar.setCorePoolSize(3);
                            ldVar.setMaximumPoolSize(3);
                        }
                    }
                    if (networkInfo == null || !networkInfo.isConnected() || cdVar5.f.isEmpty()) {
                        return;
                    }
                    Iterator<vc> it2 = cdVar5.f.values().iterator();
                    while (it2.hasNext()) {
                        vc next = it2.next();
                        it2.remove();
                        if (next.a.n) {
                            qd.a("Dispatcher", "replaying", next.b.a(), "");
                        }
                        cdVar5.a(next, false);
                    }
                    return;
                case 10:
                    this.a.p = message.arg1 == 1;
                    return;
                case 11:
                    Object obj = message.obj;
                    cd cdVar6 = this.a;
                    if (cdVar6.h.add(obj)) {
                        Iterator<xc> it3 = cdVar6.e.values().iterator();
                        while (it3.hasNext()) {
                            xc next2 = it3.next();
                            boolean z3 = next2.b.n;
                            vc vcVar4 = next2.k;
                            List<vc> list2 = next2.l;
                            boolean z4 = (list2 == null || list2.isEmpty()) ? false : true;
                            if (vcVar4 != null || z4) {
                                if (vcVar4 != null && vcVar4.j.equals(obj)) {
                                    next2.a(vcVar4);
                                    cdVar6.g.put(vcVar4.b(), vcVar4);
                                    if (z3) {
                                        qd.a("Dispatcher", "paused", vcVar4.b.a(), "because tag '" + obj + "' was paused");
                                    }
                                }
                                if (z4) {
                                    for (int size2 = list2.size() - 1; size2 >= 0; size2--) {
                                        vc vcVar5 = list2.get(size2);
                                        if (vcVar5.j.equals(obj)) {
                                            next2.a(vcVar5);
                                            cdVar6.g.put(vcVar5.b(), vcVar5);
                                            if (z3) {
                                                qd.a("Dispatcher", "paused", vcVar5.b.a(), "because tag '" + obj + "' was paused");
                                            }
                                        }
                                    }
                                }
                                if (next2.a()) {
                                    it3.remove();
                                    if (z3) {
                                        qd.a("Dispatcher", "canceled", qd.a(next2), "all actions paused");
                                    }
                                }
                            }
                        }
                        return;
                    }
                    return;
                case 12:
                    Object obj2 = message.obj;
                    cd cdVar7 = this.a;
                    if (cdVar7.h.remove(obj2)) {
                        Iterator<vc> it4 = cdVar7.g.values().iterator();
                        while (it4.hasNext()) {
                            vc next3 = it4.next();
                            if (next3.j.equals(obj2)) {
                                if (arrayList == null) {
                                    arrayList = new ArrayList();
                                }
                                arrayList.add(next3);
                                it4.remove();
                            }
                        }
                        if (arrayList != null) {
                            Handler handler2 = cdVar7.j;
                            handler2.sendMessage(handler2.obtainMessage(13, arrayList));
                            return;
                        }
                        return;
                    }
                    return;
            }
        }
    }

    public static class b extends HandlerThread {
        public b() {
            super("Picasso-Dispatcher", 10);
        }
    }

    public static class c extends BroadcastReceiver {
        public final cd a;

        public c(cd cdVar) {
            this.a = cdVar;
        }

        @Override // android.content.BroadcastReceiver
        @SuppressLint({"MissingPermission"})
        public void onReceive(Context context, Intent intent) {
            if (intent == null) {
                return;
            }
            String action = intent.getAction();
            if ("android.intent.action.AIRPLANE_MODE".equals(action)) {
                if (intent.hasExtra("state")) {
                    cd cdVar = this.a;
                    boolean booleanExtra = intent.getBooleanExtra("state", false);
                    Handler handler = cdVar.i;
                    handler.sendMessage(handler.obtainMessage(10, booleanExtra ? 1 : 0, 0));
                    return;
                }
                return;
            }
            if ("android.net.conn.CONNECTIVITY_CHANGE".equals(action)) {
                ConnectivityManager connectivityManager = (ConnectivityManager) qd.a(context, "connectivity");
                cd cdVar2 = this.a;
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                Handler handler2 = cdVar2.i;
                handler2.sendMessage(handler2.obtainMessage(9, activeNetworkInfo));
            }
        }
    }

    public cd(Context context, ExecutorService executorService, Handler handler, Downloader downloader, Cache cache, od odVar) {
        b bVar = new b();
        this.a = bVar;
        bVar.start();
        qd.a(this.a.getLooper());
        this.b = context;
        this.c = executorService;
        this.e = new LinkedHashMap();
        this.f = new WeakHashMap();
        this.g = new WeakHashMap();
        this.h = new LinkedHashSet();
        this.i = new a(this.a.getLooper(), this);
        this.d = downloader;
        this.j = handler;
        this.k = cache;
        this.l = odVar;
        this.m = new ArrayList(4);
        boolean z = Settings.Global.getInt(this.b.getContentResolver(), "airplane_mode_on", 0) != 0;
        this.p = z;
        this.o = context.checkCallingOrSelfPermission("android.permission.ACCESS_NETWORK_STATE") == 0;
        c cVar = new c(this);
        this.n = cVar;
        if (cVar == null) {
            throw null;
        }
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        if (cVar.a.o) {
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        }
        cVar.a.b.registerReceiver(cVar, intentFilter);
    }

    public void a(vc vcVar, boolean z) {
        if (this.h.contains(vcVar.j)) {
            this.g.put(vcVar.b(), vcVar);
            if (vcVar.a.n) {
                String strA = vcVar.b.a();
                StringBuilder sbA = g9.a("because tag '");
                sbA.append(vcVar.j);
                sbA.append("' is paused");
                qd.a("Dispatcher", "paused", strA, sbA.toString());
                return;
            }
            return;
        }
        xc xcVar = this.e.get(vcVar.i);
        if (xcVar == null) {
            if (this.c.isShutdown()) {
                if (vcVar.a.n) {
                    qd.a("Dispatcher", "ignored", vcVar.b.a(), "because shut down");
                    return;
                }
                return;
            }
            xc xcVarA = xc.a(vcVar.a, this, this.k, this.l, vcVar);
            xcVarA.n = this.c.submit(xcVarA);
            this.e.put(vcVar.i, xcVarA);
            if (z) {
                this.f.remove(vcVar.b());
            }
            if (vcVar.a.n) {
                qd.a("Dispatcher", "enqueued", vcVar.b.a(), "");
                return;
            }
            return;
        }
        boolean z2 = xcVar.b.n;
        Request request = vcVar.b;
        if (xcVar.k == null) {
            xcVar.k = vcVar;
            if (z2) {
                List<vc> list = xcVar.l;
                if (list == null || list.isEmpty()) {
                    qd.a("Hunter", "joined", request.a(), "to empty hunter");
                    return;
                } else {
                    qd.a("Hunter", "joined", request.a(), qd.a(xcVar, "to "));
                    return;
                }
            }
            return;
        }
        if (xcVar.l == null) {
            xcVar.l = new ArrayList(3);
        }
        xcVar.l.add(vcVar);
        if (z2) {
            qd.a("Hunter", "joined", request.a(), qd.a(xcVar, "to "));
        }
        Picasso.Priority priority = vcVar.b.priority;
        if (priority.ordinal() > xcVar.s.ordinal()) {
            xcVar.s = priority;
        }
    }

    public void b(xc xcVar) {
        Handler handler = this.i;
        handler.sendMessage(handler.obtainMessage(4, xcVar));
    }

    public void c(xc xcVar) {
        Handler handler = this.i;
        handler.sendMessage(handler.obtainMessage(6, xcVar));
    }

    public final void a(xc xcVar) {
        Future<?> future = xcVar.n;
        if (future != null && future.isCancelled()) {
            return;
        }
        Bitmap bitmap = xcVar.m;
        if (bitmap != null) {
            bitmap.prepareToDraw();
        }
        this.m.add(xcVar);
        if (this.i.hasMessages(7)) {
            return;
        }
        this.i.sendEmptyMessageDelayed(7, 200L);
    }

    public void a(xc xcVar, boolean z) {
        if (xcVar.b.n) {
            String strA = qd.a(xcVar);
            StringBuilder sbA = g9.a("for error");
            sbA.append(z ? " (will replay)" : "");
            qd.a("Dispatcher", "batched", strA, sbA.toString());
        }
        this.e.remove(xcVar.f);
        a(xcVar);
    }
}
