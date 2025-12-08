package defpackage;

import android.content.Context;
import android.support.media.ExifInterface;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;
import java.io.IOException;
import okio.Okio;

/* loaded from: classes.dex */
public class ed extends zc {
    public ed(Context context) {
        super(context);
    }

    @Override // defpackage.zc, com.squareup.picasso.RequestHandler
    public boolean canHandleRequest(Request request) {
        return "file".equals(request.uri.getScheme());
    }

    @Override // defpackage.zc, com.squareup.picasso.RequestHandler
    public RequestHandler.Result load(Request request, int i) throws IOException {
        return new RequestHandler.Result(null, Okio.source(this.a.getContentResolver().openInputStream(request.uri)), Picasso.LoadedFrom.DISK, new ExifInterface(request.uri.getPath()).getAttributeInt(ExifInterface.TAG_ORIENTATION, 1));
    }
}
