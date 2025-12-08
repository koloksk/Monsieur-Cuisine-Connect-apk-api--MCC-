package okhttp3.internal.http;

import defpackage.g9;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;
import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.Util;
import okhttp3.internal.connection.Exchange;
import okhttp3.internal.connection.Transmitter;

/* loaded from: classes.dex */
public final class RealInterceptorChain implements Interceptor.Chain {
    public final List<Interceptor> a;
    public final Transmitter b;

    @Nullable
    public final Exchange c;
    public final int d;
    public final Request e;
    public final Call f;
    public final int g;
    public final int h;
    public final int i;
    public int j;

    public RealInterceptorChain(List<Interceptor> list, Transmitter transmitter, @Nullable Exchange exchange, int i, Request request, Call call, int i2, int i3, int i4) {
        this.a = list;
        this.b = transmitter;
        this.c = exchange;
        this.d = i;
        this.e = request;
        this.f = call;
        this.g = i2;
        this.h = i3;
        this.i = i4;
    }

    @Override // okhttp3.Interceptor.Chain
    public Call call() {
        return this.f;
    }

    @Override // okhttp3.Interceptor.Chain
    public int connectTimeoutMillis() {
        return this.g;
    }

    @Override // okhttp3.Interceptor.Chain
    @Nullable
    public Connection connection() {
        Exchange exchange = this.c;
        if (exchange != null) {
            return exchange.connection();
        }
        return null;
    }

    public Exchange exchange() {
        Exchange exchange = this.c;
        if (exchange != null) {
            return exchange;
        }
        throw new IllegalStateException();
    }

    @Override // okhttp3.Interceptor.Chain
    public Response proceed(Request request) throws IOException {
        return proceed(request, this.b, this.c);
    }

    @Override // okhttp3.Interceptor.Chain
    public int readTimeoutMillis() {
        return this.h;
    }

    @Override // okhttp3.Interceptor.Chain
    public Request request() {
        return this.e;
    }

    public Transmitter transmitter() {
        return this.b;
    }

    @Override // okhttp3.Interceptor.Chain
    public Interceptor.Chain withConnectTimeout(int i, TimeUnit timeUnit) {
        return new RealInterceptorChain(this.a, this.b, this.c, this.d, this.e, this.f, Util.checkDuration("timeout", i, timeUnit), this.h, this.i);
    }

    @Override // okhttp3.Interceptor.Chain
    public Interceptor.Chain withReadTimeout(int i, TimeUnit timeUnit) {
        return new RealInterceptorChain(this.a, this.b, this.c, this.d, this.e, this.f, this.g, Util.checkDuration("timeout", i, timeUnit), this.i);
    }

    @Override // okhttp3.Interceptor.Chain
    public Interceptor.Chain withWriteTimeout(int i, TimeUnit timeUnit) {
        return new RealInterceptorChain(this.a, this.b, this.c, this.d, this.e, this.f, this.g, this.h, Util.checkDuration("timeout", i, timeUnit));
    }

    @Override // okhttp3.Interceptor.Chain
    public int writeTimeoutMillis() {
        return this.i;
    }

    public Response proceed(Request request, Transmitter transmitter, @Nullable Exchange exchange) throws IOException {
        if (this.d >= this.a.size()) {
            throw new AssertionError();
        }
        this.j++;
        Exchange exchange2 = this.c;
        if (exchange2 != null && !exchange2.connection().supportsUrl(request.url())) {
            StringBuilder sbA = g9.a("network interceptor ");
            sbA.append(this.a.get(this.d - 1));
            sbA.append(" must retain the same host and port");
            throw new IllegalStateException(sbA.toString());
        }
        if (this.c != null && this.j > 1) {
            StringBuilder sbA2 = g9.a("network interceptor ");
            sbA2.append(this.a.get(this.d - 1));
            sbA2.append(" must call proceed() exactly once");
            throw new IllegalStateException(sbA2.toString());
        }
        RealInterceptorChain realInterceptorChain = new RealInterceptorChain(this.a, transmitter, exchange, this.d + 1, request, this.f, this.g, this.h, this.i);
        Interceptor interceptor = this.a.get(this.d);
        Response responseIntercept = interceptor.intercept(realInterceptorChain);
        if (exchange != null && this.d + 1 < this.a.size() && realInterceptorChain.j != 1) {
            throw new IllegalStateException("network interceptor " + interceptor + " must call proceed() exactly once");
        }
        if (responseIntercept == null) {
            throw new NullPointerException("interceptor " + interceptor + " returned null");
        }
        if (responseIntercept.body() != null) {
            return responseIntercept;
        }
        throw new IllegalStateException("interceptor " + interceptor + " returned a response with no body");
    }
}
