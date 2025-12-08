package defpackage;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.squareup.picasso.Cache;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.StatsSnapshot;

/* loaded from: classes.dex */
public class od {
    public final HandlerThread a;
    public final Cache b;
    public final Handler c;
    public long d;
    public long e;
    public long f;
    public long g;
    public long h;
    public long i;
    public long j;
    public long k;
    public int l;
    public int m;
    public int n;

    public static class a extends Handler {
        public final od a;

        /* renamed from: od$a$a, reason: collision with other inner class name */
        public class RunnableC0076a implements Runnable {
            public final /* synthetic */ Message a;

            public RunnableC0076a(a aVar, Message message) {
                this.a = message;
            }

            @Override // java.lang.Runnable
            public void run() {
                StringBuilder sbA = g9.a("Unhandled stats message.");
                sbA.append(this.a.what);
                throw new AssertionError(sbA.toString());
            }
        }

        public a(Looper looper, od odVar) {
            super(looper);
            this.a = odVar;
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
            if (i == 0) {
                this.a.d++;
                return;
            }
            if (i == 1) {
                this.a.e++;
                return;
            }
            if (i == 2) {
                od odVar = this.a;
                long j = message.arg1;
                int i2 = odVar.m + 1;
                odVar.m = i2;
                long j2 = odVar.g + j;
                odVar.g = j2;
                odVar.j = j2 / i2;
                return;
            }
            if (i == 3) {
                od odVar2 = this.a;
                long j3 = message.arg1;
                odVar2.n++;
                long j4 = odVar2.h + j3;
                odVar2.h = j4;
                odVar2.k = j4 / odVar2.m;
                return;
            }
            if (i != 4) {
                Picasso.p.post(new RunnableC0076a(this, message));
                return;
            }
            od odVar3 = this.a;
            Long l = (Long) message.obj;
            odVar3.l++;
            long jLongValue = l.longValue() + odVar3.f;
            odVar3.f = jLongValue;
            odVar3.i = jLongValue / odVar3.l;
        }
    }

    public od(Cache cache) {
        this.b = cache;
        HandlerThread handlerThread = new HandlerThread("Picasso-Stats", 10);
        this.a = handlerThread;
        handlerThread.start();
        qd.a(this.a.getLooper());
        this.c = new a(this.a.getLooper(), this);
    }

    public StatsSnapshot a() {
        return new StatsSnapshot(this.b.maxSize(), this.b.size(), this.d, this.e, this.f, this.g, this.h, this.i, this.j, this.k, this.l, this.m, this.n, System.currentTimeMillis());
    }
}
