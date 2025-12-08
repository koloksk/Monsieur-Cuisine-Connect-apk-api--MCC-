package defpackage;

import android.net.NetworkInfo;
import android.os.Handler;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;
import java.io.IOException;
import okhttp3.CacheControl;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/* loaded from: classes.dex */
public class jd extends RequestHandler {
    public final Downloader a;
    public final od b;

    public static class a extends IOException {
        public a(String str) {
            super(str);
        }
    }

    public static final class b extends IOException {
        public final int a;
        public final int b;

        public b(int i, int i2) {
            super(g9.b("HTTP ", i));
            this.a = i;
            this.b = i2;
        }
    }

    public jd(Downloader downloader, od odVar) {
        this.a = downloader;
        this.b = odVar;
    }

    @Override // com.squareup.picasso.RequestHandler
    public int a() {
        return 2;
    }

    @Override // com.squareup.picasso.RequestHandler
    public boolean a(boolean z, NetworkInfo networkInfo) {
        return networkInfo == null || networkInfo.isConnected();
    }

    @Override // com.squareup.picasso.RequestHandler
    public boolean b() {
        return true;
    }

    @Override // com.squareup.picasso.RequestHandler
    public boolean canHandleRequest(Request request) {
        String scheme = request.uri.getScheme();
        return "http".equals(scheme) || "https".equals(scheme);
    }

    @Override // com.squareup.picasso.RequestHandler
    public RequestHandler.Result load(Request request, int i) throws IOException {
        CacheControl cacheControlBuild;
        if (i == 0) {
            cacheControlBuild = null;
        } else if (NetworkPolicy.isOfflineOnly(i)) {
            cacheControlBuild = CacheControl.FORCE_CACHE;
        } else {
            CacheControl.Builder builder = new CacheControl.Builder();
            if (!NetworkPolicy.shouldReadFromDiskCache(i)) {
                builder.noCache();
            }
            if (!NetworkPolicy.shouldWriteToDiskCache(i)) {
                builder.noStore();
            }
            cacheControlBuild = builder.build();
        }
        Request.Builder builderUrl = new Request.Builder().url(request.uri.toString());
        if (cacheControlBuild != null) {
            builderUrl.cacheControl(cacheControlBuild);
        }
        Response responseLoad = this.a.load(builderUrl.build());
        ResponseBody responseBodyBody = responseLoad.body();
        if (!responseLoad.isSuccessful()) {
            responseBodyBody.close();
            throw new b(responseLoad.code(), request.c);
        }
        Picasso.LoadedFrom loadedFrom = responseLoad.cacheResponse() == null ? Picasso.LoadedFrom.NETWORK : Picasso.LoadedFrom.DISK;
        if (loadedFrom == Picasso.LoadedFrom.DISK && responseBodyBody.contentLength() == 0) {
            responseBodyBody.close();
            throw new a("Received response with 0 content-length header.");
        }
        if (loadedFrom == Picasso.LoadedFrom.NETWORK && responseBodyBody.contentLength() > 0) {
            od odVar = this.b;
            long jContentLength = responseBodyBody.contentLength();
            Handler handler = odVar.c;
            handler.sendMessage(handler.obtainMessage(4, Long.valueOf(jContentLength)));
        }
        return new RequestHandler.Result(responseBodyBody.source(), loadedFrom);
    }
}
