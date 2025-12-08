package defpackage;

import android.content.Context;
import android.content.res.AssetManager;
import android.net.Uri;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;
import java.io.IOException;
import okio.Okio;

/* loaded from: classes.dex */
public class wc extends RequestHandler {
    public final Context a;
    public final Object b = new Object();
    public AssetManager c;

    public wc(Context context) {
        this.a = context;
    }

    @Override // com.squareup.picasso.RequestHandler
    public boolean canHandleRequest(Request request) {
        Uri uri = request.uri;
        return "file".equals(uri.getScheme()) && !uri.getPathSegments().isEmpty() && "android_asset".equals(uri.getPathSegments().get(0));
    }

    @Override // com.squareup.picasso.RequestHandler
    public RequestHandler.Result load(Request request, int i) throws IOException {
        if (this.c == null) {
            synchronized (this.b) {
                if (this.c == null) {
                    this.c = this.a.getAssets();
                }
            }
        }
        return new RequestHandler.Result(Okio.source(this.c.open(request.uri.toString().substring(22))), Picasso.LoadedFrom.DISK);
    }
}
