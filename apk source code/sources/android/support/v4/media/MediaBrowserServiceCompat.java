package android.support.v4.media;

import android.app.Service;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.media.session.MediaSession;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.service.media.MediaBrowserService;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.media.MediaBrowserServiceCompatApi26;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.util.Log;
import defpackage.a5;
import defpackage.b5;
import defpackage.c5;
import defpackage.d5;
import defpackage.e5;
import defpackage.f5;
import defpackage.g5;
import defpackage.g9;
import defpackage.h5;
import defpackage.j5;
import defpackage.k5;
import defpackage.x4;
import defpackage.y4;
import defpackage.z4;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public abstract class MediaBrowserServiceCompat extends Service {

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final String KEY_MEDIA_ITEM = "media_item";

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static final String KEY_SEARCH_RESULTS = "search_results";
    public static final String SERVICE_INTERFACE = "android.media.browse.MediaBrowserService";
    public static final boolean f = Log.isLoggable("MBServiceCompat", 3);
    public f a;
    public e c;
    public MediaSessionCompat.Token e;
    public final ArrayMap<IBinder, e> b = new ArrayMap<>();
    public final m d = new m();

    public static final class BrowserRoot {
        public static final String EXTRA_OFFLINE = "android.service.media.extra.OFFLINE";
        public static final String EXTRA_RECENT = "android.service.media.extra.RECENT";
        public static final String EXTRA_SUGGESTED = "android.service.media.extra.SUGGESTED";

        @Deprecated
        public static final String EXTRA_SUGGESTION_KEYWORDS = "android.service.media.extra.SUGGESTION_KEYWORDS";
        public final String a;
        public final Bundle b;

        public BrowserRoot(@NonNull String str, @Nullable Bundle bundle) {
            if (str == null) {
                throw new IllegalArgumentException("The root id in BrowserRoot cannot be null. Use null for BrowserRoot instead.");
            }
            this.a = str;
            this.b = bundle;
        }

        public Bundle getExtras() {
            return this.b;
        }

        public String getRootId() {
            return this.a;
        }
    }

    public static class Result<T> {
        public final Object a;
        public boolean b;
        public boolean c;
        public boolean d;
        public int e;

        public Result(Object obj) {
            this.a = obj;
        }

        public void a(T t) {
        }

        public boolean a() {
            return this.b || this.c || this.d;
        }

        public void b(Bundle bundle) {
            StringBuilder sbA = g9.a("It is not supported to send an interim update for ");
            sbA.append(this.a);
            throw new UnsupportedOperationException(sbA.toString());
        }

        public void detach() {
            if (this.b) {
                StringBuilder sbA = g9.a("detach() called when detach() had already been called for: ");
                sbA.append(this.a);
                throw new IllegalStateException(sbA.toString());
            }
            if (this.c) {
                StringBuilder sbA2 = g9.a("detach() called when sendResult() had already been called for: ");
                sbA2.append(this.a);
                throw new IllegalStateException(sbA2.toString());
            }
            if (!this.d) {
                this.b = true;
            } else {
                StringBuilder sbA3 = g9.a("detach() called when sendError() had already been called for: ");
                sbA3.append(this.a);
                throw new IllegalStateException(sbA3.toString());
            }
        }

        public void sendError(Bundle bundle) {
            if (this.c || this.d) {
                StringBuilder sbA = g9.a("sendError() called when either sendResult() or sendError() had already been called for: ");
                sbA.append(this.a);
                throw new IllegalStateException(sbA.toString());
            }
            this.d = true;
            a(bundle);
        }

        public void sendProgressUpdate(Bundle bundle) {
            if (this.c || this.d) {
                StringBuilder sbA = g9.a("sendProgressUpdate() called when either sendResult() or sendError() had already been called for: ");
                sbA.append(this.a);
                throw new IllegalStateException(sbA.toString());
            }
            if (bundle != null && bundle.containsKey(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS)) {
                float f = bundle.getFloat(MediaBrowserCompat.EXTRA_DOWNLOAD_PROGRESS);
                if (f < -1.0E-5f || f > 1.00001f) {
                    throw new IllegalArgumentException("The value of the EXTRA_DOWNLOAD_PROGRESS field must be a float number within [0.0, 1.0].");
                }
            }
            b(bundle);
        }

        public void sendResult(T t) {
            if (this.c || this.d) {
                StringBuilder sbA = g9.a("sendResult() called when either sendResult() or sendError() had already been called for: ");
                sbA.append(this.a);
                throw new IllegalStateException(sbA.toString());
            }
            this.c = true;
            a((Result<T>) t);
        }

        public void a(Bundle bundle) {
            StringBuilder sbA = g9.a("It is not supported to send an error for ");
            sbA.append(this.a);
            throw new UnsupportedOperationException(sbA.toString());
        }
    }

    public class a extends Result<List<MediaBrowserCompat.MediaItem>> {
        public final /* synthetic */ e f;
        public final /* synthetic */ String g;
        public final /* synthetic */ Bundle h;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public a(Object obj, e eVar, String str, Bundle bundle) {
            super(obj);
            this.f = eVar;
            this.g = str;
            this.h = bundle;
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
        public void a(List<MediaBrowserCompat.MediaItem> list) {
            List<MediaBrowserCompat.MediaItem> listA = list;
            if (MediaBrowserServiceCompat.this.b.get(((l) this.f.c).a()) != this.f) {
                if (MediaBrowserServiceCompat.f) {
                    StringBuilder sbA = g9.a("Not sending onLoadChildren result for connection that has been disconnected. pkg=");
                    sbA.append(this.f.a);
                    sbA.append(" id=");
                    sbA.append(this.g);
                    Log.d("MBServiceCompat", sbA.toString());
                    return;
                }
                return;
            }
            if ((this.e & 1) != 0) {
                listA = MediaBrowserServiceCompat.this.a(listA, this.h);
            }
            try {
                k kVar = this.f.c;
                String str = this.g;
                Bundle bundle = this.h;
                l lVar = (l) kVar;
                if (lVar == null) {
                    throw null;
                }
                Bundle bundle2 = new Bundle();
                bundle2.putString("data_media_item_id", str);
                bundle2.putBundle("data_options", bundle);
                if (listA != null) {
                    bundle2.putParcelableArrayList("data_media_item_list", listA instanceof ArrayList ? (ArrayList) listA : new ArrayList<>(listA));
                }
                lVar.a(3, bundle2);
            } catch (RemoteException unused) {
                StringBuilder sbA2 = g9.a("Calling onLoadChildren() failed for id=");
                sbA2.append(this.g);
                sbA2.append(" package=");
                sbA2.append(this.f.a);
                Log.w("MBServiceCompat", sbA2.toString());
            }
        }
    }

    public class b extends Result<MediaBrowserCompat.MediaItem> {
        public final /* synthetic */ ResultReceiver f;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public b(MediaBrowserServiceCompat mediaBrowserServiceCompat, Object obj, ResultReceiver resultReceiver) {
            super(obj);
            this.f = resultReceiver;
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
        public void a(MediaBrowserCompat.MediaItem mediaItem) {
            MediaBrowserCompat.MediaItem mediaItem2 = mediaItem;
            if ((this.e & 2) != 0) {
                this.f.send(-1, null);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM, mediaItem2);
            this.f.send(0, bundle);
        }
    }

    public class c extends Result<List<MediaBrowserCompat.MediaItem>> {
        public final /* synthetic */ ResultReceiver f;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public c(MediaBrowserServiceCompat mediaBrowserServiceCompat, Object obj, ResultReceiver resultReceiver) {
            super(obj);
            this.f = resultReceiver;
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
        public void a(List<MediaBrowserCompat.MediaItem> list) {
            List<MediaBrowserCompat.MediaItem> list2 = list;
            if ((this.e & 4) != 0 || list2 == null) {
                this.f.send(-1, null);
                return;
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArray(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS, (Parcelable[]) list2.toArray(new MediaBrowserCompat.MediaItem[0]));
            this.f.send(0, bundle);
        }
    }

    public class e implements IBinder.DeathRecipient {
        public String a;
        public Bundle b;
        public k c;
        public BrowserRoot d;
        public HashMap<String, List<Pair<IBinder, Bundle>>> e = new HashMap<>();

        public class a implements Runnable {
            public a() {
            }

            @Override // java.lang.Runnable
            public void run() {
                e eVar = e.this;
                MediaBrowserServiceCompat.this.b.remove(((l) eVar.c).a());
            }
        }

        public e() {
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            MediaBrowserServiceCompat.this.d.post(new a());
        }
    }

    public interface f {
        IBinder a(Intent intent);

        void a();

        void a(MediaSessionCompat.Token token);

        void a(String str, Bundle bundle);

        Bundle b();
    }

    @RequiresApi(23)
    public class h extends g implements MediaBrowserServiceCompatApi23$ServiceCompatProxy {

        public class a extends Result<MediaBrowserCompat.MediaItem> {
            public final /* synthetic */ j5 f;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public a(h hVar, Object obj, j5 j5Var) {
                super(obj);
                this.f = j5Var;
            }

            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            public void a(MediaBrowserCompat.MediaItem mediaItem) {
                MediaBrowserCompat.MediaItem mediaItem2 = mediaItem;
                if (mediaItem2 == null) {
                    this.f.a(null);
                    return;
                }
                Parcel parcelObtain = Parcel.obtain();
                mediaItem2.writeToParcel(parcelObtain, 0);
                this.f.a(parcelObtain);
            }

            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            public void detach() {
                this.f.a.detach();
            }
        }

        public h() {
            super();
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.f
        public void a() {
            k5 k5Var = new k5(MediaBrowserServiceCompat.this, this);
            this.b = k5Var;
            k5Var.onCreate();
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompatApi23$ServiceCompatProxy
        public void onLoadItem(String str, j5<Parcel> j5Var) {
            MediaBrowserServiceCompat.this.onLoadItem(str, new a(this, str, j5Var));
        }
    }

    public class j {
        public j() {
        }
    }

    public interface k {
    }

    public static class l implements k {
        public final Messenger a;

        public l(Messenger messenger) {
            this.a = messenger;
        }

        public IBinder a() {
            return this.a.getBinder();
        }

        public void a(String str, MediaSessionCompat.Token token, Bundle bundle) throws RemoteException {
            if (bundle == null) {
                bundle = new Bundle();
            }
            bundle.putInt("extra_service_version", 2);
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_media_item_id", str);
            bundle2.putParcelable("data_media_session_token", token);
            bundle2.putBundle("data_root_hints", bundle);
            a(1, bundle2);
        }

        public final void a(int i, Bundle bundle) throws RemoteException {
            Message messageObtain = Message.obtain();
            messageObtain.what = i;
            messageObtain.arg1 = 2;
            messageObtain.setData(bundle);
            this.a.send(messageObtain);
        }
    }

    public final class m extends Handler {
        public final j a;

        public m() {
            this.a = MediaBrowserServiceCompat.this.new j();
        }

        public void a(Runnable runnable) {
            if (Thread.currentThread() == getLooper().getThread()) {
                runnable.run();
            } else {
                post(runnable);
            }
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            Bundle data = message.getData();
            switch (message.what) {
                case 1:
                    j jVar = this.a;
                    String string = data.getString("data_package_name");
                    int i = data.getInt("data_calling_uid");
                    Bundle bundle = data.getBundle("data_root_hints");
                    l lVar = new l(message.replyTo);
                    if (MediaBrowserServiceCompat.this.a(string, i)) {
                        MediaBrowserServiceCompat.this.d.a(new y4(jVar, lVar, string, bundle, i));
                        return;
                    }
                    throw new IllegalArgumentException("Package/uid mismatch: uid=" + i + " package=" + string);
                case 2:
                    j jVar2 = this.a;
                    MediaBrowserServiceCompat.this.d.a(new z4(jVar2, new l(message.replyTo)));
                    return;
                case 3:
                    j jVar3 = this.a;
                    MediaBrowserServiceCompat.this.d.a(new a5(jVar3, new l(message.replyTo), data.getString("data_media_item_id"), BundleCompat.getBinder(data, "data_callback_token"), data.getBundle("data_options")));
                    return;
                case 4:
                    j jVar4 = this.a;
                    MediaBrowserServiceCompat.this.d.a(new b5(jVar4, new l(message.replyTo), data.getString("data_media_item_id"), BundleCompat.getBinder(data, "data_callback_token")));
                    return;
                case 5:
                    j jVar5 = this.a;
                    String string2 = data.getString("data_media_item_id");
                    ResultReceiver resultReceiver = (ResultReceiver) data.getParcelable("data_result_receiver");
                    l lVar2 = new l(message.replyTo);
                    if (jVar5 == null) {
                        throw null;
                    }
                    if (TextUtils.isEmpty(string2) || resultReceiver == null) {
                        return;
                    }
                    MediaBrowserServiceCompat.this.d.a(new c5(jVar5, lVar2, string2, resultReceiver));
                    return;
                case 6:
                    j jVar6 = this.a;
                    MediaBrowserServiceCompat.this.d.a(new d5(jVar6, new l(message.replyTo), data.getBundle("data_root_hints")));
                    return;
                case 7:
                    j jVar7 = this.a;
                    MediaBrowserServiceCompat.this.d.a(new e5(jVar7, new l(message.replyTo)));
                    return;
                case 8:
                    j jVar8 = this.a;
                    String string3 = data.getString("data_search_query");
                    Bundle bundle2 = data.getBundle("data_search_extras");
                    ResultReceiver resultReceiver2 = (ResultReceiver) data.getParcelable("data_result_receiver");
                    l lVar3 = new l(message.replyTo);
                    if (jVar8 == null) {
                        throw null;
                    }
                    if (TextUtils.isEmpty(string3) || resultReceiver2 == null) {
                        return;
                    }
                    MediaBrowserServiceCompat.this.d.a(new f5(jVar8, lVar3, string3, bundle2, resultReceiver2));
                    return;
                case 9:
                    j jVar9 = this.a;
                    String string4 = data.getString("data_custom_action");
                    Bundle bundle3 = data.getBundle("data_custom_action_extras");
                    ResultReceiver resultReceiver3 = (ResultReceiver) data.getParcelable("data_result_receiver");
                    l lVar4 = new l(message.replyTo);
                    if (jVar9 == null) {
                        throw null;
                    }
                    if (TextUtils.isEmpty(string4) || resultReceiver3 == null) {
                        return;
                    }
                    MediaBrowserServiceCompat.this.d.a(new g5(jVar9, lVar4, string4, bundle3, resultReceiver3));
                    return;
                default:
                    Log.w("MBServiceCompat", "Unhandled message: " + message + "\n  Service version: 2\n  Client version: " + message.arg1);
                    return;
            }
        }

        @Override // android.os.Handler
        public boolean sendMessageAtTime(Message message, long j) {
            Bundle data = message.getData();
            data.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            data.putInt("data_calling_uid", Binder.getCallingUid());
            return super.sendMessageAtTime(message, j);
        }
    }

    public boolean a(String str, int i2) {
        if (str == null) {
            return false;
        }
        for (String str2 : getPackageManager().getPackagesForUid(i2)) {
            if (str2.equals(str)) {
                return true;
            }
        }
        return false;
    }

    public void b(String str, Bundle bundle, e eVar, ResultReceiver resultReceiver) {
        c cVar = new c(this, str, resultReceiver);
        this.c = eVar;
        onSearch(str, bundle, cVar);
        this.c = null;
        if (!cVar.a()) {
            throw new IllegalStateException(g9.b("onSearch must call detach() or sendResult() before returning for query=", str));
        }
    }

    @Override // android.app.Service
    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public final Bundle getBrowserRootHints() {
        return this.a.b();
    }

    @Nullable
    public MediaSessionCompat.Token getSessionToken() {
        return this.e;
    }

    public void notifyChildrenChanged(@NonNull String str) {
        if (str == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        this.a.a(str, null);
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return this.a.a(intent);
    }

    @Override // android.app.Service
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 26) {
            this.a = new i();
        } else {
            this.a = new h();
        }
        this.a.a();
    }

    public void onCustomAction(@NonNull String str, Bundle bundle, @NonNull Result<Bundle> result) {
        result.sendError(null);
    }

    @Nullable
    public abstract BrowserRoot onGetRoot(@NonNull String str, int i2, @Nullable Bundle bundle);

    public abstract void onLoadChildren(@NonNull String str, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result);

    public void onLoadChildren(@NonNull String str, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result, @NonNull Bundle bundle) {
        result.e = 1;
        onLoadChildren(str, result);
    }

    public void onLoadItem(String str, @NonNull Result<MediaBrowserCompat.MediaItem> result) {
        result.e = 2;
        result.sendResult(null);
    }

    public void onSearch(@NonNull String str, Bundle bundle, @NonNull Result<List<MediaBrowserCompat.MediaItem>> result) {
        result.e = 4;
        result.sendResult(null);
    }

    public void setSessionToken(MediaSessionCompat.Token token) {
        if (token == null) {
            throw new IllegalArgumentException("Session token may not be null.");
        }
        if (this.e != null) {
            throw new IllegalStateException("The session token has already been set.");
        }
        this.e = token;
        this.a.a(token);
    }

    public class d extends Result<Bundle> {
        public final /* synthetic */ ResultReceiver f;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public d(MediaBrowserServiceCompat mediaBrowserServiceCompat, Object obj, ResultReceiver resultReceiver) {
            super(obj);
            this.f = resultReceiver;
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
        public void a(Bundle bundle) {
            this.f.send(0, bundle);
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
        public void b(Bundle bundle) {
            this.f.send(1, bundle);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
        public void a(Bundle bundle) {
            this.f.send(-1, bundle);
        }
    }

    @RequiresApi(21)
    public class g implements f, MediaBrowserServiceCompatApi21$ServiceCompatProxy {
        public final List<Bundle> a = new ArrayList();
        public Object b;
        public Messenger c;

        public class a implements Runnable {
            public final /* synthetic */ MediaSessionCompat.Token a;

            public a(MediaSessionCompat.Token token) {
                this.a = token;
            }

            @Override // java.lang.Runnable
            public void run() {
                if (!g.this.a.isEmpty()) {
                    IMediaSession extraBinder = this.a.getExtraBinder();
                    if (extraBinder != null) {
                        Iterator<Bundle> it = g.this.a.iterator();
                        while (it.hasNext()) {
                            BundleCompat.putBinder(it.next(), "extra_session_binder", extraBinder.asBinder());
                        }
                    }
                    g.this.a.clear();
                }
                ((MediaBrowserService) g.this.b).setSessionToken((MediaSession.Token) this.a.getToken());
            }
        }

        public class b extends Result<List<MediaBrowserCompat.MediaItem>> {
            public final /* synthetic */ j5 f;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public b(g gVar, Object obj, j5 j5Var) {
                super(obj);
                this.f = j5Var;
            }

            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            public void a(List<MediaBrowserCompat.MediaItem> list) {
                ArrayList arrayList;
                List<MediaBrowserCompat.MediaItem> list2 = list;
                if (list2 != null) {
                    arrayList = new ArrayList();
                    for (MediaBrowserCompat.MediaItem mediaItem : list2) {
                        Parcel parcelObtain = Parcel.obtain();
                        mediaItem.writeToParcel(parcelObtain, 0);
                        arrayList.add(parcelObtain);
                    }
                } else {
                    arrayList = null;
                }
                this.f.a(arrayList);
            }

            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            public void detach() {
                this.f.a.detach();
            }
        }

        public g() {
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.f
        public IBinder a(Intent intent) {
            return ((MediaBrowserService) this.b).onBind(intent);
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.f
        public Bundle b() {
            if (this.c == null) {
                return null;
            }
            e eVar = MediaBrowserServiceCompat.this.c;
            if (eVar == null) {
                throw new IllegalStateException("This should be called inside of onLoadChildren, onLoadItem or onSearch methods");
            }
            if (eVar.b == null) {
                return null;
            }
            return new Bundle(MediaBrowserServiceCompat.this.c.b);
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompatApi21$ServiceCompatProxy
        public h5 onGetRoot(String str, int i, Bundle bundle) {
            Bundle extras;
            if (bundle == null || bundle.getInt("extra_client_version", 0) == 0) {
                extras = null;
            } else {
                bundle.remove("extra_client_version");
                this.c = new Messenger(MediaBrowserServiceCompat.this.d);
                extras = new Bundle();
                extras.putInt("extra_service_version", 2);
                BundleCompat.putBinder(extras, "extra_messenger", this.c.getBinder());
                MediaSessionCompat.Token token = MediaBrowserServiceCompat.this.e;
                if (token != null) {
                    IMediaSession extraBinder = token.getExtraBinder();
                    BundleCompat.putBinder(extras, "extra_session_binder", extraBinder == null ? null : extraBinder.asBinder());
                } else {
                    this.a.add(extras);
                }
            }
            BrowserRoot browserRootOnGetRoot = MediaBrowserServiceCompat.this.onGetRoot(str, i, bundle);
            if (browserRootOnGetRoot == null) {
                return null;
            }
            if (extras == null) {
                extras = browserRootOnGetRoot.getExtras();
            } else if (browserRootOnGetRoot.getExtras() != null) {
                extras.putAll(browserRootOnGetRoot.getExtras());
            }
            return new h5(browserRootOnGetRoot.getRootId(), extras);
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompatApi21$ServiceCompatProxy
        public void onLoadChildren(String str, j5<List<Parcel>> j5Var) {
            MediaBrowserServiceCompat.this.onLoadChildren(str, new b(this, str, j5Var));
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.f
        public void a(MediaSessionCompat.Token token) {
            MediaBrowserServiceCompat.this.d.a(new a(token));
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.f
        public void a(String str, Bundle bundle) {
            b(str, bundle);
            MediaBrowserServiceCompat.this.d.post(new x4(this, str, bundle));
        }

        public void b(String str, Bundle bundle) {
            ((MediaBrowserService) this.b).notifyChildrenChanged(str);
        }
    }

    @RequiresApi(26)
    public class i extends h implements MediaBrowserServiceCompatApi26.ServiceCompatProxy {

        public class a extends Result<List<MediaBrowserCompat.MediaItem>> {
            public final /* synthetic */ MediaBrowserServiceCompatApi26.b f;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            public a(i iVar, Object obj, MediaBrowserServiceCompatApi26.b bVar) {
                super(obj);
                this.f = bVar;
            }

            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            public void a(List<MediaBrowserCompat.MediaItem> list) throws IllegalAccessException, IllegalArgumentException {
                ArrayList<Parcel> arrayList;
                List<MediaBrowserCompat.MediaItem> list2 = list;
                ArrayList arrayList2 = null;
                if (list2 != null) {
                    arrayList = new ArrayList();
                    for (MediaBrowserCompat.MediaItem mediaItem : list2) {
                        Parcel parcelObtain = Parcel.obtain();
                        mediaItem.writeToParcel(parcelObtain, 0);
                        arrayList.add(parcelObtain);
                    }
                } else {
                    arrayList = null;
                }
                MediaBrowserServiceCompatApi26.b bVar = this.f;
                int i = this.e;
                if (bVar == null) {
                    throw null;
                }
                try {
                    MediaBrowserServiceCompatApi26.a.setInt(bVar.a, i);
                } catch (IllegalAccessException e) {
                    Log.w("MBSCompatApi26", e);
                }
                MediaBrowserService.Result result = bVar.a;
                if (arrayList != null) {
                    arrayList2 = new ArrayList();
                    for (Parcel parcel : arrayList) {
                        parcel.setDataPosition(0);
                        arrayList2.add(MediaBrowser.MediaItem.CREATOR.createFromParcel(parcel));
                        parcel.recycle();
                    }
                }
                result.sendResult(arrayList2);
            }

            @Override // android.support.v4.media.MediaBrowserServiceCompat.Result
            public void detach() {
                this.f.a.detach();
            }
        }

        public i() {
            super();
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.h, android.support.v4.media.MediaBrowserServiceCompat.f
        public void a() {
            Object objA = MediaBrowserServiceCompatApi26.a(MediaBrowserServiceCompat.this, this);
            this.b = objA;
            ((MediaBrowserService) objA).onCreate();
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.g, android.support.v4.media.MediaBrowserServiceCompat.f
        public Bundle b() {
            e eVar = MediaBrowserServiceCompat.this.c;
            if (eVar == null) {
                return MediaBrowserServiceCompatApi26.a(this.b);
            }
            if (eVar.b == null) {
                return null;
            }
            return new Bundle(MediaBrowserServiceCompat.this.c.b);
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompatApi26.ServiceCompatProxy
        public void onLoadChildren(String str, MediaBrowserServiceCompatApi26.b bVar, Bundle bundle) {
            MediaBrowserServiceCompat.this.onLoadChildren(str, new a(this, str, bVar), bundle);
        }

        @Override // android.support.v4.media.MediaBrowserServiceCompat.g
        public void b(String str, Bundle bundle) {
            if (bundle != null) {
                MediaBrowserServiceCompatApi26.a(this.b, str, bundle);
            } else {
                ((MediaBrowserService) this.b).notifyChildrenChanged(str);
            }
        }
    }

    public void notifyChildrenChanged(@NonNull String str, @NonNull Bundle bundle) {
        if (str == null) {
            throw new IllegalArgumentException("parentId cannot be null in notifyChildrenChanged");
        }
        if (bundle != null) {
            this.a.a(str, bundle);
            return;
        }
        throw new IllegalArgumentException("options cannot be null in notifyChildrenChanged");
    }

    public void a(String str, e eVar, IBinder iBinder, Bundle bundle) {
        List<Pair<IBinder, Bundle>> arrayList = eVar.e.get(str);
        if (arrayList == null) {
            arrayList = new ArrayList<>();
        }
        for (Pair<IBinder, Bundle> pair : arrayList) {
            if (iBinder == pair.first && MediaBrowserCompatUtils.areSameOptions(bundle, pair.second)) {
                return;
            }
        }
        arrayList.add(new Pair<>(iBinder, bundle));
        eVar.e.put(str, arrayList);
        a(str, eVar, bundle);
    }

    public boolean a(String str, e eVar, IBinder iBinder) {
        boolean z = false;
        if (iBinder == null) {
            return eVar.e.remove(str) != null;
        }
        List<Pair<IBinder, Bundle>> list = eVar.e.get(str);
        if (list != null) {
            Iterator<Pair<IBinder, Bundle>> it = list.iterator();
            while (it.hasNext()) {
                if (iBinder == it.next().first) {
                    it.remove();
                    z = true;
                }
            }
            if (list.size() == 0) {
                eVar.e.remove(str);
            }
        }
        return z;
    }

    public void a(String str, e eVar, Bundle bundle) {
        a aVar = new a(str, eVar, str, bundle);
        this.c = eVar;
        if (bundle == null) {
            onLoadChildren(str, aVar);
        } else {
            onLoadChildren(str, aVar, bundle);
        }
        this.c = null;
        if (aVar.a()) {
            return;
        }
        StringBuilder sbA = g9.a("onLoadChildren must call detach() or sendResult() before returning for package=");
        sbA.append(eVar.a);
        sbA.append(" id=");
        sbA.append(str);
        throw new IllegalStateException(sbA.toString());
    }

    public List<MediaBrowserCompat.MediaItem> a(List<MediaBrowserCompat.MediaItem> list, Bundle bundle) {
        if (list == null) {
            return null;
        }
        int i2 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int i3 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        if (i2 == -1 && i3 == -1) {
            return list;
        }
        int i4 = i3 * i2;
        int size = i4 + i3;
        if (i2 >= 0 && i3 >= 1 && i4 < list.size()) {
            if (size > list.size()) {
                size = list.size();
            }
            return list.subList(i4, size);
        }
        return Collections.EMPTY_LIST;
    }

    public void a(String str, e eVar, ResultReceiver resultReceiver) {
        b bVar = new b(this, str, resultReceiver);
        this.c = eVar;
        onLoadItem(str, bVar);
        this.c = null;
        if (!bVar.a()) {
            throw new IllegalStateException(g9.b("onLoadItem must call detach() or sendResult() before returning for id=", str));
        }
    }

    public void a(String str, Bundle bundle, e eVar, ResultReceiver resultReceiver) {
        d dVar = new d(this, str, resultReceiver);
        this.c = eVar;
        onCustomAction(str, bundle, dVar);
        this.c = null;
        if (dVar.a()) {
            return;
        }
        throw new IllegalStateException("onCustomAction must call detach() or sendResult() or sendError() before returning for action=" + str + " extras=" + bundle);
    }
}
