package defpackage;

import android.content.Context;
import android.net.Uri;
import com.squareup.picasso.Request;

/* loaded from: classes.dex */
public class id extends zc {
    public static final String[] b = {"orientation"};

    public enum a {
        MICRO(3, 96, 96),
        MINI(1, 512, 384),
        FULL(2, -1, -1);

        public final int a;
        public final int b;
        public final int c;

        a(int i, int i2, int i3) {
            this.a = i;
            this.b = i2;
            this.c = i3;
        }
    }

    public id(Context context) {
        super(context);
    }

    @Override // defpackage.zc, com.squareup.picasso.RequestHandler
    public boolean canHandleRequest(Request request) {
        Uri uri = request.uri;
        return "content".equals(uri.getScheme()) && "media".equals(uri.getAuthority());
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x003a A[PHI: r3
  0x003a: PHI (r3v2 android.database.Cursor) = (r3v1 android.database.Cursor), (r3v12 android.database.Cursor) binds: [B:19:0x0038, B:12:0x002d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0051  */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0058  */
    @Override // defpackage.zc, com.squareup.picasso.RequestHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.squareup.picasso.RequestHandler.Result load(com.squareup.picasso.Request r17, int r18) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 229
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: defpackage.id.load(com.squareup.picasso.Request, int):com.squareup.picasso.RequestHandler$Result");
    }
}
