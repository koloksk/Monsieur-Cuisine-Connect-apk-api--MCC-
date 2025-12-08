package defpackage;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.net.Uri;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Request;
import com.squareup.picasso.RequestHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/* loaded from: classes.dex */
public class nd extends RequestHandler {
    public final Context a;

    public nd(Context context) {
        this.a = context;
    }

    @Override // com.squareup.picasso.RequestHandler
    public boolean canHandleRequest(Request request) {
        if (request.resourceId != 0) {
            return true;
        }
        return "android.resource".equals(request.uri.getScheme());
    }

    @Override // com.squareup.picasso.RequestHandler
    public RequestHandler.Result load(Request request, int i) throws NumberFormatException, IOException {
        int identifier;
        Uri uri;
        Resources resourcesA = qd.a(this.a, request);
        if (request.resourceId != 0 || (uri = request.uri) == null) {
            identifier = request.resourceId;
        } else {
            String authority = uri.getAuthority();
            if (authority == null) {
                StringBuilder sbA = g9.a("No package provided: ");
                sbA.append(request.uri);
                throw new FileNotFoundException(sbA.toString());
            }
            List<String> pathSegments = request.uri.getPathSegments();
            if (pathSegments == null || pathSegments.isEmpty()) {
                StringBuilder sbA2 = g9.a("No path segments: ");
                sbA2.append(request.uri);
                throw new FileNotFoundException(sbA2.toString());
            }
            if (pathSegments.size() == 1) {
                try {
                    identifier = Integer.parseInt(pathSegments.get(0));
                } catch (NumberFormatException unused) {
                    StringBuilder sbA3 = g9.a("Last path segment is not a resource ID: ");
                    sbA3.append(request.uri);
                    throw new FileNotFoundException(sbA3.toString());
                }
            } else {
                if (pathSegments.size() != 2) {
                    StringBuilder sbA4 = g9.a("More than two path segments: ");
                    sbA4.append(request.uri);
                    throw new FileNotFoundException(sbA4.toString());
                }
                identifier = resourcesA.getIdentifier(pathSegments.get(1), pathSegments.get(0), authority);
            }
        }
        BitmapFactory.Options optionsA = RequestHandler.a(request);
        if (optionsA != null && optionsA.inJustDecodeBounds) {
            BitmapFactory.decodeResource(resourcesA, identifier, optionsA);
            RequestHandler.a(request.targetWidth, request.targetHeight, optionsA, request);
        }
        return new RequestHandler.Result(BitmapFactory.decodeResource(resourcesA, identifier, optionsA), Picasso.LoadedFrom.DISK);
    }
}
