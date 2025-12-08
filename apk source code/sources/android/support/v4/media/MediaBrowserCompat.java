package android.support.v4.media;

import android.content.ComponentName;
import android.content.Context;
import android.media.browse.MediaBrowser;
import android.os.BadParcelableException;
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
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.os.ResultReceiver;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import defpackage.g9;
import defpackage.p4;
import defpackage.q4;
import defpackage.r4;
import defpackage.s4;
import defpackage.t4;
import defpackage.u4;
import defpackage.v4;
import defpackage.w4;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public final class MediaBrowserCompat {
    public static final String CUSTOM_ACTION_DOWNLOAD = "android.support.v4.media.action.DOWNLOAD";
    public static final String CUSTOM_ACTION_REMOVE_DOWNLOADED_FILE = "android.support.v4.media.action.REMOVE_DOWNLOADED_FILE";
    public static final String EXTRA_DOWNLOAD_PROGRESS = "android.media.browse.extra.DOWNLOAD_PROGRESS";
    public static final String EXTRA_MEDIA_ID = "android.media.browse.extra.MEDIA_ID";
    public static final String EXTRA_PAGE = "android.media.browse.extra.PAGE";
    public static final String EXTRA_PAGE_SIZE = "android.media.browse.extra.PAGE_SIZE";
    public static final boolean b = Log.isLoggable("MediaBrowserCompat", 3);
    public final b a;

    public static class ConnectionCallback {
        public final Object a = new q4(new b());
        public a b;

        public interface a {
        }

        public class b implements p4 {
            public b() {
            }
        }

        public void onConnected() {
        }

        public void onConnectionFailed() {
        }

        public void onConnectionSuspended() {
        }
    }

    public static abstract class CustomActionCallback {
        public void onError(String str, Bundle bundle, Bundle bundle2) {
        }

        public void onProgressUpdate(String str, Bundle bundle, Bundle bundle2) {
        }

        public void onResult(String str, Bundle bundle, Bundle bundle2) {
        }
    }

    public static class CustomActionResultReceiver extends ResultReceiver {
        public final String d;
        public final Bundle e;
        public final CustomActionCallback f;

        public CustomActionResultReceiver(String str, Bundle bundle, CustomActionCallback customActionCallback, Handler handler) {
            super(handler);
            this.d = str;
            this.e = bundle;
            this.f = customActionCallback;
        }

        @Override // android.support.v4.os.ResultReceiver
        public void onReceiveResult(int i, Bundle bundle) {
            CustomActionCallback customActionCallback = this.f;
            if (customActionCallback == null) {
                return;
            }
            if (i == -1) {
                customActionCallback.onError(this.d, this.e, bundle);
                return;
            }
            if (i == 0) {
                customActionCallback.onResult(this.d, this.e, bundle);
                return;
            }
            if (i == 1) {
                customActionCallback.onProgressUpdate(this.d, this.e, bundle);
                return;
            }
            StringBuilder sbA = g9.a("Unknown result code: ", i, " (extras=");
            sbA.append(this.e);
            sbA.append(", resultData=");
            sbA.append(bundle);
            sbA.append(")");
            Log.w("MediaBrowserCompat", sbA.toString());
        }
    }

    public static abstract class ItemCallback {
        public final Object a = new u4(new a());

        public class a implements t4 {
            public a() {
            }

            public void a(Parcel parcel) {
                if (parcel == null) {
                    ItemCallback.this.onItemLoaded(null);
                    return;
                }
                parcel.setDataPosition(0);
                MediaItem mediaItemCreateFromParcel = MediaItem.CREATOR.createFromParcel(parcel);
                parcel.recycle();
                ItemCallback.this.onItemLoaded(mediaItemCreateFromParcel);
            }
        }

        public void onError(@NonNull String str) {
        }

        public void onItemLoaded(MediaItem mediaItem) {
        }
    }

    public static class ItemReceiver extends ResultReceiver {
        public final String d;
        public final ItemCallback e;

        public ItemReceiver(String str, ItemCallback itemCallback, Handler handler) {
            super(handler);
            this.d = str;
            this.e = itemCallback;
        }

        @Override // android.support.v4.os.ResultReceiver
        public void onReceiveResult(int i, Bundle bundle) {
            if (bundle != null) {
                bundle.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            }
            if (i != 0 || bundle == null || !bundle.containsKey(MediaBrowserServiceCompat.KEY_MEDIA_ITEM)) {
                this.e.onError(this.d);
                return;
            }
            Parcelable parcelable = bundle.getParcelable(MediaBrowserServiceCompat.KEY_MEDIA_ITEM);
            if (parcelable == null || (parcelable instanceof MediaItem)) {
                this.e.onItemLoaded((MediaItem) parcelable);
            } else {
                this.e.onError(this.d);
            }
        }
    }

    public static abstract class SearchCallback {
        public void onError(@NonNull String str, Bundle bundle) {
        }

        public void onSearchResult(@NonNull String str, Bundle bundle, @NonNull List<MediaItem> list) {
        }
    }

    public static class SearchResultReceiver extends ResultReceiver {
        public final String d;
        public final Bundle e;
        public final SearchCallback f;

        public SearchResultReceiver(String str, Bundle bundle, SearchCallback searchCallback, Handler handler) {
            super(handler);
            this.d = str;
            this.e = bundle;
            this.f = searchCallback;
        }

        @Override // android.support.v4.os.ResultReceiver
        public void onReceiveResult(int i, Bundle bundle) {
            if (bundle != null) {
                bundle.setClassLoader(MediaBrowserCompat.class.getClassLoader());
            }
            if (i != 0 || bundle == null || !bundle.containsKey(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS)) {
                this.f.onError(this.d, this.e);
                return;
            }
            Parcelable[] parcelableArray = bundle.getParcelableArray(MediaBrowserServiceCompat.KEY_SEARCH_RESULTS);
            ArrayList arrayList = null;
            if (parcelableArray != null) {
                arrayList = new ArrayList();
                for (Parcelable parcelable : parcelableArray) {
                    arrayList.add((MediaItem) parcelable);
                }
            }
            this.f.onSearchResult(this.d, this.e, arrayList);
        }
    }

    public static class a extends Handler {
        public final WeakReference<f> a;
        public WeakReference<Messenger> b;

        public a(f fVar) {
            this.a = new WeakReference<>(fVar);
        }

        public void a(Messenger messenger) {
            this.b = new WeakReference<>(messenger);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            WeakReference<Messenger> weakReference = this.b;
            if (weakReference == null || weakReference.get() == null || this.a.get() == null) {
                return;
            }
            Bundle data = message.getData();
            data.setClassLoader(MediaSessionCompat.class.getClassLoader());
            f fVar = this.a.get();
            Messenger messenger = this.b.get();
            try {
                int i = message.what;
                if (i == 1) {
                    fVar.a(messenger, data.getString("data_media_item_id"), (MediaSessionCompat.Token) data.getParcelable("data_media_session_token"), data.getBundle("data_root_hints"));
                } else if (i == 2) {
                    fVar.a(messenger);
                } else if (i != 3) {
                    Log.w("MediaBrowserCompat", "Unhandled message: " + message + "\n  Client version: 1\n  Service version: " + message.arg1);
                } else {
                    fVar.a(messenger, data.getString("data_media_item_id"), data.getParcelableArrayList("data_media_item_list"), data.getBundle("data_options"));
                }
            } catch (BadParcelableException unused) {
                Log.e("MediaBrowserCompat", "Could not unparcel the data.");
                if (message.what == 1) {
                    fVar.a(messenger);
                }
            }
        }
    }

    public interface b {
        @NonNull
        MediaSessionCompat.Token a();

        void a(@NonNull String str, Bundle bundle, @Nullable CustomActionCallback customActionCallback);

        void a(@NonNull String str, Bundle bundle, @NonNull SearchCallback searchCallback);

        void a(@NonNull String str, @Nullable Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback);

        void a(@NonNull String str, @NonNull ItemCallback itemCallback);

        void a(@NonNull String str, SubscriptionCallback subscriptionCallback);

        boolean b();

        ComponentName c();

        void d();

        void e();

        @Nullable
        Bundle getExtras();

        @NonNull
        String getRoot();
    }

    @RequiresApi(23)
    public static class d extends c {
        public d(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            super(context, componentName, connectionCallback, bundle);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c, android.support.v4.media.MediaBrowserCompat.b
        public void a(@NonNull String str, @NonNull ItemCallback itemCallback) {
            if (this.g != null) {
                super.a(str, itemCallback);
            } else {
                ((MediaBrowser) this.b).getItem(str, (MediaBrowser.ItemCallback) itemCallback.a);
            }
        }
    }

    public interface f {
        void a(Messenger messenger);

        void a(Messenger messenger, String str, MediaSessionCompat.Token token, Bundle bundle);

        void a(Messenger messenger, String str, List list, Bundle bundle);
    }

    public MediaBrowserCompat(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 26) {
            this.a = new e(context, componentName, connectionCallback, bundle);
        } else {
            this.a = new d(context, componentName, connectionCallback, bundle);
        }
    }

    public void connect() {
        this.a.e();
    }

    public void disconnect() {
        this.a.d();
    }

    @Nullable
    public Bundle getExtras() {
        return this.a.getExtras();
    }

    public void getItem(@NonNull String str, @NonNull ItemCallback itemCallback) {
        this.a.a(str, itemCallback);
    }

    @NonNull
    public String getRoot() {
        return this.a.getRoot();
    }

    @NonNull
    public ComponentName getServiceComponent() {
        return this.a.c();
    }

    @NonNull
    public MediaSessionCompat.Token getSessionToken() {
        return this.a.a();
    }

    public boolean isConnected() {
        return this.a.b();
    }

    public void search(@NonNull String str, Bundle bundle, @NonNull SearchCallback searchCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("query cannot be empty");
        }
        if (searchCallback == null) {
            throw new IllegalArgumentException("callback cannot be null");
        }
        this.a.a(str, bundle, searchCallback);
    }

    public void sendCustomAction(@NonNull String str, Bundle bundle, @Nullable CustomActionCallback customActionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("action cannot be empty");
        }
        this.a.a(str, bundle, customActionCallback);
    }

    public void subscribe(@NonNull String str, @NonNull SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("parentId is empty");
        }
        if (subscriptionCallback == null) {
            throw new IllegalArgumentException("callback is null");
        }
        this.a.a(str, (Bundle) null, subscriptionCallback);
    }

    public void unsubscribe(@NonNull String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("parentId is empty");
        }
        this.a.a(str, (SubscriptionCallback) null);
    }

    public static abstract class SubscriptionCallback {
        public final Object a;
        public final IBinder b = new Binder();
        public WeakReference<h> c;

        public SubscriptionCallback() {
            if (Build.VERSION.SDK_INT >= 26) {
                this.a = new w4(new b());
            } else {
                this.a = new s4(new a());
            }
        }

        public void onChildrenLoaded(@NonNull String str, @NonNull List<MediaItem> list) {
        }

        public void onChildrenLoaded(@NonNull String str, @NonNull List<MediaItem> list, @NonNull Bundle bundle) {
        }

        public void onError(@NonNull String str) {
        }

        public void onError(@NonNull String str, @NonNull Bundle bundle) {
        }

        public class b extends a implements v4 {
            public b() {
                super();
            }

            @Override // defpackage.v4
            public void a(@NonNull String str, List<?> list, @NonNull Bundle bundle) {
                SubscriptionCallback.this.onChildrenLoaded(str, MediaItem.fromMediaItemList(list), bundle);
            }

            @Override // defpackage.v4
            public void a(@NonNull String str, @NonNull Bundle bundle) {
                SubscriptionCallback.this.onError(str, bundle);
            }
        }

        public class a implements r4 {
            public a() {
            }

            @Override // defpackage.r4
            public void a(@NonNull String str, List<?> list) {
                List<MediaItem> listSubList;
                WeakReference<h> weakReference = SubscriptionCallback.this.c;
                h hVar = weakReference == null ? null : weakReference.get();
                if (hVar == null) {
                    SubscriptionCallback.this.onChildrenLoaded(str, MediaItem.fromMediaItemList(list));
                    return;
                }
                List<MediaItem> listFromMediaItemList = MediaItem.fromMediaItemList(list);
                List<SubscriptionCallback> list2 = hVar.a;
                List<Bundle> list3 = hVar.b;
                for (int i = 0; i < list2.size(); i++) {
                    Bundle bundle = list3.get(i);
                    if (bundle == null) {
                        SubscriptionCallback.this.onChildrenLoaded(str, listFromMediaItemList);
                    } else {
                        SubscriptionCallback subscriptionCallback = SubscriptionCallback.this;
                        if (listFromMediaItemList == null) {
                            listSubList = null;
                        } else {
                            int i2 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
                            int i3 = bundle.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
                            if (i2 == -1 && i3 == -1) {
                                listSubList = listFromMediaItemList;
                            } else {
                                int i4 = i3 * i2;
                                int size = i4 + i3;
                                if (i2 < 0 || i3 < 1 || i4 >= listFromMediaItemList.size()) {
                                    listSubList = Collections.EMPTY_LIST;
                                } else {
                                    if (size > listFromMediaItemList.size()) {
                                        size = listFromMediaItemList.size();
                                    }
                                    listSubList = listFromMediaItemList.subList(i4, size);
                                }
                            }
                        }
                        subscriptionCallback.onChildrenLoaded(str, listSubList, bundle);
                    }
                }
            }

            @Override // defpackage.r4
            public void a(@NonNull String str) {
                SubscriptionCallback.this.onError(str);
            }
        }
    }

    public static class h {
        public final List<SubscriptionCallback> a = new ArrayList();
        public final List<Bundle> b = new ArrayList();

        public SubscriptionCallback a(Context context, Bundle bundle) {
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader());
            }
            for (int i = 0; i < this.b.size(); i++) {
                if (MediaBrowserCompatUtils.areSameOptions(this.b.get(i), bundle)) {
                    return this.a.get(i);
                }
            }
            return null;
        }

        public void a(Context context, Bundle bundle, SubscriptionCallback subscriptionCallback) {
            if (bundle != null) {
                bundle.setClassLoader(context.getClassLoader());
            }
            for (int i = 0; i < this.b.size(); i++) {
                if (MediaBrowserCompatUtils.areSameOptions(this.b.get(i), bundle)) {
                    this.a.set(i, subscriptionCallback);
                    return;
                }
            }
            this.a.add(subscriptionCallback);
            this.b.add(bundle);
        }
    }

    public void unsubscribe(@NonNull String str, @NonNull SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("parentId is empty");
        }
        if (subscriptionCallback != null) {
            this.a.a(str, subscriptionCallback);
            return;
        }
        throw new IllegalArgumentException("callback is null");
    }

    @RequiresApi(21)
    public static class c implements b, f, ConnectionCallback.a {
        public final Context a;
        public final Object b;
        public final Bundle c;
        public final a d = new a(this);
        public final ArrayMap<String, h> e = new ArrayMap<>();
        public int f;
        public g g;
        public Messenger h;
        public MediaSessionCompat.Token i;

        public class a implements Runnable {
            public final /* synthetic */ ItemCallback a;
            public final /* synthetic */ String b;

            public a(c cVar, ItemCallback itemCallback, String str) {
                this.a = itemCallback;
                this.b = str;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.a.onError(this.b);
            }
        }

        public class b implements Runnable {
            public final /* synthetic */ ItemCallback a;
            public final /* synthetic */ String b;

            public b(c cVar, ItemCallback itemCallback, String str) {
                this.a = itemCallback;
                this.b = str;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.a.onError(this.b);
            }
        }

        /* renamed from: android.support.v4.media.MediaBrowserCompat$c$c, reason: collision with other inner class name */
        public class RunnableC0001c implements Runnable {
            public final /* synthetic */ ItemCallback a;
            public final /* synthetic */ String b;

            public RunnableC0001c(c cVar, ItemCallback itemCallback, String str) {
                this.a = itemCallback;
                this.b = str;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.a.onError(this.b);
            }
        }

        public class d implements Runnable {
            public final /* synthetic */ SearchCallback a;
            public final /* synthetic */ String b;
            public final /* synthetic */ Bundle c;

            public d(c cVar, SearchCallback searchCallback, String str, Bundle bundle) {
                this.a = searchCallback;
                this.b = str;
                this.c = bundle;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.a.onError(this.b, this.c);
            }
        }

        public class e implements Runnable {
            public final /* synthetic */ SearchCallback a;
            public final /* synthetic */ String b;
            public final /* synthetic */ Bundle c;

            public e(c cVar, SearchCallback searchCallback, String str, Bundle bundle) {
                this.a = searchCallback;
                this.b = str;
                this.c = bundle;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.a.onError(this.b, this.c);
            }
        }

        public class f implements Runnable {
            public final /* synthetic */ CustomActionCallback a;
            public final /* synthetic */ String b;
            public final /* synthetic */ Bundle c;

            public f(c cVar, CustomActionCallback customActionCallback, String str, Bundle bundle) {
                this.a = customActionCallback;
                this.b = str;
                this.c = bundle;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.a.onError(this.b, this.c, null);
            }
        }

        public class g implements Runnable {
            public final /* synthetic */ CustomActionCallback a;
            public final /* synthetic */ String b;
            public final /* synthetic */ Bundle c;

            public g(c cVar, CustomActionCallback customActionCallback, String str, Bundle bundle) {
                this.a = customActionCallback;
                this.b = str;
                this.c = bundle;
            }

            @Override // java.lang.Runnable
            public void run() {
                this.a.onError(this.b, this.c, null);
            }
        }

        public c(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            this.a = context;
            bundle = bundle == null ? new Bundle() : bundle;
            bundle.putInt("extra_client_version", 1);
            Bundle bundle2 = new Bundle(bundle);
            this.c = bundle2;
            connectionCallback.b = this;
            this.b = new MediaBrowser(context, componentName, (MediaBrowser.ConnectionCallback) connectionCallback.a, bundle2);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b
        @NonNull
        public MediaSessionCompat.Token a() {
            if (this.i == null) {
                this.i = MediaSessionCompat.Token.fromToken(((MediaBrowser) this.b).getSessionToken());
            }
            return this.i;
        }

        @Override // android.support.v4.media.MediaBrowserCompat.f
        public void a(Messenger messenger) {
        }

        @Override // android.support.v4.media.MediaBrowserCompat.f
        public void a(Messenger messenger, String str, MediaSessionCompat.Token token, Bundle bundle) {
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b
        public boolean b() {
            return ((MediaBrowser) this.b).isConnected();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b
        public ComponentName c() {
            return ((MediaBrowser) this.b).getServiceComponent();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b
        public void d() {
            Messenger messenger;
            g gVar = this.g;
            if (gVar != null && (messenger = this.h) != null) {
                try {
                    gVar.a(7, (Bundle) null, messenger);
                } catch (RemoteException unused) {
                    Log.i("MediaBrowserCompat", "Remote error unregistering client messenger.");
                }
            }
            ((MediaBrowser) this.b).disconnect();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b
        public void e() {
            ((MediaBrowser) this.b).connect();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b
        @Nullable
        public Bundle getExtras() {
            return ((MediaBrowser) this.b).getExtras();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b
        @NonNull
        public String getRoot() {
            return ((MediaBrowser) this.b).getRoot();
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b
        public void a(@NonNull String str, Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
            h hVar = this.e.get(str);
            if (hVar == null) {
                hVar = new h();
                this.e.put(str, hVar);
            }
            if (subscriptionCallback != null) {
                subscriptionCallback.c = new WeakReference<>(hVar);
                Bundle bundle2 = bundle != null ? new Bundle(bundle) : null;
                hVar.a(this.a, bundle2, subscriptionCallback);
                g gVar = this.g;
                if (gVar == null) {
                    ((MediaBrowser) this.b).subscribe(str, (MediaBrowser.SubscriptionCallback) subscriptionCallback.a);
                    return;
                }
                try {
                    gVar.a(str, subscriptionCallback.b, bundle2, this.h);
                    return;
                } catch (RemoteException unused) {
                    Log.i("MediaBrowserCompat", "Remote error subscribing media item: " + str);
                    return;
                }
            }
            throw null;
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b
        public void a(@NonNull String str, SubscriptionCallback subscriptionCallback) {
            h hVar = this.e.get(str);
            if (hVar == null) {
                return;
            }
            g gVar = this.g;
            if (gVar != null) {
                try {
                    if (subscriptionCallback == null) {
                        gVar.a(str, (IBinder) null, this.h);
                    } else {
                        List<SubscriptionCallback> list = hVar.a;
                        List<Bundle> list2 = hVar.b;
                        int size = list.size();
                        while (true) {
                            size--;
                            if (size < 0) {
                                break;
                            }
                            if (list.get(size) == subscriptionCallback) {
                                this.g.a(str, subscriptionCallback.b, this.h);
                                list.remove(size);
                                list2.remove(size);
                            }
                        }
                    }
                } catch (RemoteException unused) {
                    Log.d("MediaBrowserCompat", "removeSubscription failed with RemoteException parentId=" + str);
                }
            } else if (subscriptionCallback == null) {
                ((MediaBrowser) this.b).unsubscribe(str);
            } else {
                List<SubscriptionCallback> list3 = hVar.a;
                List<Bundle> list4 = hVar.b;
                int size2 = list3.size();
                while (true) {
                    size2--;
                    if (size2 < 0) {
                        break;
                    } else if (list3.get(size2) == subscriptionCallback) {
                        list3.remove(size2);
                        list4.remove(size2);
                    }
                }
                if (list3.size() == 0) {
                    ((MediaBrowser) this.b).unsubscribe(str);
                }
            }
            if (hVar.a.isEmpty() || subscriptionCallback == null) {
                this.e.remove(str);
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b
        public void a(@NonNull String str, @NonNull ItemCallback itemCallback) {
            if (TextUtils.isEmpty(str)) {
                throw new IllegalArgumentException("mediaId is empty");
            }
            if (itemCallback != null) {
                if (!((MediaBrowser) this.b).isConnected()) {
                    Log.i("MediaBrowserCompat", "Not connected, unable to retrieve the MediaItem.");
                    this.d.post(new a(this, itemCallback, str));
                    return;
                }
                if (this.g == null) {
                    this.d.post(new b(this, itemCallback, str));
                    return;
                }
                ItemReceiver itemReceiver = new ItemReceiver(str, itemCallback, this.d);
                try {
                    g gVar = this.g;
                    Messenger messenger = this.h;
                    if (gVar != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("data_media_item_id", str);
                        bundle.putParcelable("data_result_receiver", itemReceiver);
                        gVar.a(5, bundle, messenger);
                        return;
                    }
                    throw null;
                } catch (RemoteException unused) {
                    Log.i("MediaBrowserCompat", "Remote error getting media item: " + str);
                    this.d.post(new RunnableC0001c(this, itemCallback, str));
                    return;
                }
            }
            throw new IllegalArgumentException("cb is null");
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b
        public void a(@NonNull String str, Bundle bundle, @NonNull SearchCallback searchCallback) {
            if (b()) {
                if (this.g == null) {
                    Log.i("MediaBrowserCompat", "The connected service doesn't support search.");
                    this.d.post(new d(this, searchCallback, str, bundle));
                    return;
                }
                try {
                    this.g.a(str, bundle, new SearchResultReceiver(str, bundle, searchCallback, this.d), this.h);
                    return;
                } catch (RemoteException e2) {
                    Log.i("MediaBrowserCompat", "Remote error searching items with query: " + str, e2);
                    this.d.post(new e(this, searchCallback, str, bundle));
                    return;
                }
            }
            throw new IllegalStateException("search() called while not connected");
        }

        @Override // android.support.v4.media.MediaBrowserCompat.b
        public void a(@NonNull String str, Bundle bundle, @Nullable CustomActionCallback customActionCallback) {
            if (b()) {
                if (this.g == null) {
                    Log.i("MediaBrowserCompat", "The connected service doesn't support sendCustomAction.");
                    if (customActionCallback != null) {
                        this.d.post(new f(this, customActionCallback, str, bundle));
                    }
                }
                try {
                    this.g.b(str, bundle, new CustomActionResultReceiver(str, bundle, customActionCallback, this.d), this.h);
                    return;
                } catch (RemoteException e2) {
                    Log.i("MediaBrowserCompat", "Remote error sending a custom action: action=" + str + ", extras=" + bundle, e2);
                    if (customActionCallback != null) {
                        this.d.post(new g(this, customActionCallback, str, bundle));
                        return;
                    }
                    return;
                }
            }
            throw new IllegalStateException("Cannot send a custom action (" + str + ") with extras " + bundle + " because the browser is not connected to the service.");
        }

        @Override // android.support.v4.media.MediaBrowserCompat.f
        public void a(Messenger messenger, String str, List list, Bundle bundle) {
            if (this.h != messenger) {
                return;
            }
            h hVar = this.e.get(str);
            if (hVar == null) {
                if (MediaBrowserCompat.b) {
                    Log.d("MediaBrowserCompat", "onLoadChildren for id that isn't subscribed id=" + str);
                    return;
                }
                return;
            }
            SubscriptionCallback subscriptionCallbackA = hVar.a(this.a, bundle);
            if (subscriptionCallbackA != null) {
                if (bundle == null) {
                    if (list == null) {
                        subscriptionCallbackA.onError(str);
                        return;
                    } else {
                        subscriptionCallbackA.onChildrenLoaded(str, list);
                        return;
                    }
                }
                if (list == null) {
                    subscriptionCallbackA.onError(str, bundle);
                } else {
                    subscriptionCallbackA.onChildrenLoaded(str, list, bundle);
                }
            }
        }
    }

    public static class g {
        public Messenger a;
        public Bundle b;

        public g(IBinder iBinder, Bundle bundle) {
            this.a = new Messenger(iBinder);
            this.b = bundle;
        }

        public void a(String str, IBinder iBinder, Bundle bundle, Messenger messenger) throws RemoteException {
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_media_item_id", str);
            BundleCompat.putBinder(bundle2, "data_callback_token", iBinder);
            bundle2.putBundle("data_options", bundle);
            a(3, bundle2, messenger);
        }

        public void b(String str, Bundle bundle, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_custom_action", str);
            bundle2.putBundle("data_custom_action_extras", bundle);
            bundle2.putParcelable("data_result_receiver", resultReceiver);
            a(9, bundle2, messenger);
        }

        public void a(String str, IBinder iBinder, Messenger messenger) throws RemoteException {
            Bundle bundle = new Bundle();
            bundle.putString("data_media_item_id", str);
            BundleCompat.putBinder(bundle, "data_callback_token", iBinder);
            a(4, bundle, messenger);
        }

        public void a(String str, Bundle bundle, ResultReceiver resultReceiver, Messenger messenger) throws RemoteException {
            Bundle bundle2 = new Bundle();
            bundle2.putString("data_search_query", str);
            bundle2.putBundle("data_search_extras", bundle);
            bundle2.putParcelable("data_result_receiver", resultReceiver);
            a(8, bundle2, messenger);
        }

        public final void a(int i, Bundle bundle, Messenger messenger) throws RemoteException {
            Message messageObtain = Message.obtain();
            messageObtain.what = i;
            messageObtain.arg1 = 1;
            messageObtain.setData(bundle);
            messageObtain.replyTo = messenger;
            this.a.send(messageObtain);
        }
    }

    public void subscribe(@NonNull String str, @NonNull Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("parentId is empty");
        }
        if (subscriptionCallback == null) {
            throw new IllegalArgumentException("callback is null");
        }
        if (bundle != null) {
            this.a.a(str, bundle, subscriptionCallback);
            return;
        }
        throw new IllegalArgumentException("options are null");
    }

    public static class MediaItem implements Parcelable {
        public static final Parcelable.Creator<MediaItem> CREATOR = new a();
        public static final int FLAG_BROWSABLE = 1;
        public static final int FLAG_PLAYABLE = 2;
        public final int a;
        public final MediaDescriptionCompat b;

        @Retention(RetentionPolicy.SOURCE)
        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public @interface Flags {
        }

        public static class a implements Parcelable.Creator<MediaItem> {
            @Override // android.os.Parcelable.Creator
            public MediaItem createFromParcel(Parcel parcel) {
                return new MediaItem(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public MediaItem[] newArray(int i) {
                return new MediaItem[i];
            }
        }

        public MediaItem(@NonNull MediaDescriptionCompat mediaDescriptionCompat, int i) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("description cannot be null");
            }
            if (TextUtils.isEmpty(mediaDescriptionCompat.getMediaId())) {
                throw new IllegalArgumentException("description must have a non-empty media id");
            }
            this.a = i;
            this.b = mediaDescriptionCompat;
        }

        public static MediaItem fromMediaItem(Object obj) {
            if (obj == null) {
                return null;
            }
            MediaBrowser.MediaItem mediaItem = (MediaBrowser.MediaItem) obj;
            return new MediaItem(MediaDescriptionCompat.fromMediaDescription(mediaItem.getDescription()), mediaItem.getFlags());
        }

        public static List<MediaItem> fromMediaItemList(List<?> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList(list.size());
            Iterator<?> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(fromMediaItem(it.next()));
            }
            return arrayList;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @NonNull
        public MediaDescriptionCompat getDescription() {
            return this.b;
        }

        public int getFlags() {
            return this.a;
        }

        @Nullable
        public String getMediaId() {
            return this.b.getMediaId();
        }

        public boolean isBrowsable() {
            return (this.a & 1) != 0;
        }

        public boolean isPlayable() {
            return (this.a & 2) != 0;
        }

        public String toString() {
            return "MediaItem{mFlags=" + this.a + ", mDescription=" + this.b + '}';
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.a);
            this.b.writeToParcel(parcel, i);
        }

        public MediaItem(Parcel parcel) {
            this.a = parcel.readInt();
            this.b = MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
        }
    }

    @RequiresApi(26)
    public static class e extends d {
        public e(Context context, ComponentName componentName, ConnectionCallback connectionCallback, Bundle bundle) {
            super(context, componentName, connectionCallback, bundle);
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c, android.support.v4.media.MediaBrowserCompat.b
        public void a(@NonNull String str, @Nullable Bundle bundle, @NonNull SubscriptionCallback subscriptionCallback) {
            if (this.g != null && this.f >= 2) {
                super.a(str, bundle, subscriptionCallback);
            } else if (bundle == null) {
                ((MediaBrowser) this.b).subscribe(str, (MediaBrowser.SubscriptionCallback) subscriptionCallback.a);
            } else {
                ((MediaBrowser) this.b).subscribe(str, bundle, (MediaBrowser.SubscriptionCallback) subscriptionCallback.a);
            }
        }

        @Override // android.support.v4.media.MediaBrowserCompat.c, android.support.v4.media.MediaBrowserCompat.b
        public void a(@NonNull String str, SubscriptionCallback subscriptionCallback) {
            if (this.g != null && this.f >= 2) {
                super.a(str, subscriptionCallback);
            } else if (subscriptionCallback == null) {
                ((MediaBrowser) this.b).unsubscribe(str);
            } else {
                ((MediaBrowser) this.b).unsubscribe(str, (MediaBrowser.SubscriptionCallback) subscriptionCallback.a);
            }
        }
    }
}
