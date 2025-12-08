package defpackage;

import android.content.Context;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;
import java.io.IOException;
import okio.Okio;

/* loaded from: classes.dex */
public class zc extends RequestHandler {
    public final Context a;

    public zc(Context context) {
        this.a = context;
    }

    @Override // com.squareup.picasso.RequestHandler
    public boolean canHandleRequest(Request request) {
        return "content".equals(request.uri.getScheme());
    }

    @Override // com.squareup.picasso.RequestHandler
    public RequestHandler.Result load(Request request, int i) throws IOException {
        return new RequestHandler.Result(Okio.source(this.a.getContentResolver().openInputStream(request.uri)), Picasso.LoadedFrom.DISK);
    }
}
