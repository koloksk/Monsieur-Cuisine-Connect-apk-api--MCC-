package android.support.v4.media.session;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.MediaDescription;
import android.media.MediaMetadata;
import android.media.VolumeProvider;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.BadParcelableException;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.annotation.RestrictTo;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import defpackage.g9;
import defpackage.m5;
import defpackage.o5;
import defpackage.p5;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class MediaSessionCompat {
    public static final String ACTION_FLAG_AS_INAPPROPRIATE = "android.support.v4.media.session.action.FLAG_AS_INAPPROPRIATE";
    public static final String ACTION_FOLLOW = "android.support.v4.media.session.action.FOLLOW";
    public static final String ACTION_SKIP_AD = "android.support.v4.media.session.action.SKIP_AD";
    public static final String ACTION_UNFOLLOW = "android.support.v4.media.session.action.UNFOLLOW";
    public static final String ARGUMENT_MEDIA_ATTRIBUTE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE";
    public static final String ARGUMENT_MEDIA_ATTRIBUTE_VALUE = "android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE_VALUE";
    public static final int FLAG_HANDLES_MEDIA_BUTTONS = 1;
    public static final int FLAG_HANDLES_QUEUE_COMMANDS = 4;
    public static final int FLAG_HANDLES_TRANSPORT_CONTROLS = 2;
    public static final int MEDIA_ATTRIBUTE_ALBUM = 1;
    public static final int MEDIA_ATTRIBUTE_ARTIST = 0;
    public static final int MEDIA_ATTRIBUTE_PLAYLIST = 2;
    public static int d;
    public final c a;
    public final MediaControllerCompat b;
    public final ArrayList<OnActiveChangeListener> c;

    public interface OnActiveChangeListener {
        void onActiveChanged();
    }

    public static final class QueueItem implements Parcelable {
        public static final Parcelable.Creator<QueueItem> CREATOR = new a();
        public static final int UNKNOWN_ID = -1;
        public final MediaDescriptionCompat a;
        public final long b;
        public Object c;

        public static class a implements Parcelable.Creator<QueueItem> {
            @Override // android.os.Parcelable.Creator
            public QueueItem createFromParcel(Parcel parcel) {
                return new QueueItem(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public QueueItem[] newArray(int i) {
                return new QueueItem[i];
            }
        }

        public QueueItem(MediaDescriptionCompat mediaDescriptionCompat, long j) {
            this(null, mediaDescriptionCompat, j);
        }

        public static QueueItem fromQueueItem(Object obj) {
            if (obj == null) {
                return null;
            }
            MediaSession.QueueItem queueItem = (MediaSession.QueueItem) obj;
            return new QueueItem(obj, MediaDescriptionCompat.fromMediaDescription(queueItem.getDescription()), queueItem.getQueueId());
        }

        public static List<QueueItem> fromQueueItemList(List<?> list) {
            if (list == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            Iterator<?> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(fromQueueItem(it.next()));
            }
            return arrayList;
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public MediaDescriptionCompat getDescription() {
            return this.a;
        }

        public long getQueueId() {
            return this.b;
        }

        public Object getQueueItem() {
            Object obj = this.c;
            if (obj != null) {
                return obj;
            }
            MediaSession.QueueItem queueItem = new MediaSession.QueueItem((MediaDescription) this.a.getMediaDescription(), this.b);
            this.c = queueItem;
            return queueItem;
        }

        public String toString() {
            StringBuilder sbA = g9.a("MediaSession.QueueItem {Description=");
            sbA.append(this.a);
            sbA.append(", Id=");
            sbA.append(this.b);
            sbA.append(" }");
            return sbA.toString();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            this.a.writeToParcel(parcel, i);
            parcel.writeLong(this.b);
        }

        public QueueItem(Object obj, MediaDescriptionCompat mediaDescriptionCompat, long j) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("Description cannot be null.");
            }
            if (j == -1) {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            }
            this.a = mediaDescriptionCompat;
            this.b = j;
            this.c = obj;
        }

        public QueueItem(Parcel parcel) {
            this.a = MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
            this.b = parcel.readLong();
        }
    }

    public static final class ResultReceiverWrapper implements Parcelable {
        public static final Parcelable.Creator<ResultReceiverWrapper> CREATOR = new a();
        public ResultReceiver a;

        public static class a implements Parcelable.Creator<ResultReceiverWrapper> {
            @Override // android.os.Parcelable.Creator
            public ResultReceiverWrapper createFromParcel(Parcel parcel) {
                return new ResultReceiverWrapper(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public ResultReceiverWrapper[] newArray(int i) {
                return new ResultReceiverWrapper[i];
            }
        }

        public ResultReceiverWrapper(Parcel parcel) {
            this.a = (ResultReceiver) ResultReceiver.CREATOR.createFromParcel(parcel);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            this.a.writeToParcel(parcel, i);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface SessionFlags {
    }

    public static final class Token implements Parcelable {
        public static final Parcelable.Creator<Token> CREATOR = new a();
        public final Object a;
        public final IMediaSession b;

        public static class a implements Parcelable.Creator<Token> {
            @Override // android.os.Parcelable.Creator
            public Token createFromParcel(Parcel parcel) {
                return new Token(parcel.readParcelable(null));
            }

            @Override // android.os.Parcelable.Creator
            public Token[] newArray(int i) {
                return new Token[i];
            }
        }

        public Token(Object obj) {
            this.a = obj;
            this.b = null;
        }

        public static Token fromToken(Object obj) {
            return fromToken(obj, null);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Token)) {
                return false;
            }
            Token token = (Token) obj;
            Object obj2 = this.a;
            if (obj2 == null) {
                return token.a == null;
            }
            Object obj3 = token.a;
            if (obj3 == null) {
                return false;
            }
            return obj2.equals(obj3);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public IMediaSession getExtraBinder() {
            return this.b;
        }

        public Object getToken() {
            return this.a;
        }

        public int hashCode() {
            Object obj = this.a;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable((Parcelable) this.a, i);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public static Token fromToken(Object obj, IMediaSession iMediaSession) {
            if (obj == null) {
                return null;
            }
            if (obj instanceof MediaSession.Token) {
                return new Token(obj, iMediaSession);
            }
            throw new IllegalArgumentException("token is not a valid MediaSession.Token object");
        }

        public Token(Object obj, IMediaSession iMediaSession) {
            this.a = obj;
            this.b = iMediaSession;
        }
    }

    public class a extends Callback {
        public a(MediaSessionCompat mediaSessionCompat) {
        }
    }

    public class b extends Callback {
        public b(MediaSessionCompat mediaSessionCompat) {
        }
    }

    public interface c {
        Token a();

        void a(int i);

        void a(PendingIntent pendingIntent);

        void a(MediaMetadataCompat mediaMetadataCompat);

        void a(VolumeProviderCompat volumeProviderCompat);

        void a(Callback callback, Handler handler);

        void a(PlaybackStateCompat playbackStateCompat);

        void a(CharSequence charSequence);

        void a(String str, Bundle bundle);

        void a(List<QueueItem> list);

        void a(boolean z);

        void b();

        void b(int i);

        void b(PendingIntent pendingIntent);

        Object c();

        void c(int i);

        boolean d();

        String e();

        Object f();

        PlaybackStateCompat getPlaybackState();

        void setCaptioningEnabled(boolean z);

        void setExtras(Bundle bundle);

        void setRepeatMode(int i);

        void setShuffleMode(int i);
    }

    public static class e implements c {
        public int a;
        public int b;
        public VolumeProviderCompat c;

        public class a extends VolumeProviderCompat.Callback {
            public final /* synthetic */ e a;

            @Override // android.support.v4.media.VolumeProviderCompat.Callback
            public void onVolumeChanged(VolumeProviderCompat volumeProviderCompat) {
                if (this.a.c != volumeProviderCompat) {
                    return;
                }
                e eVar = this.a;
                this.a.a(new ParcelableVolumeInfo(eVar.a, eVar.b, volumeProviderCompat.getVolumeControl(), volumeProviderCompat.getMaxVolume(), volumeProviderCompat.getCurrentVolume()));
            }
        }

        public void a(ParcelableVolumeInfo parcelableVolumeInfo) {
            throw null;
        }
    }

    public MediaSessionCompat(Context context, String str) {
        this(context, str, null, null);
    }

    public static /* synthetic */ PlaybackStateCompat a(PlaybackStateCompat playbackStateCompat, MediaMetadataCompat mediaMetadataCompat) {
        if (playbackStateCompat == null) {
            return playbackStateCompat;
        }
        long j = -1;
        if (playbackStateCompat.getPosition() == -1) {
            return playbackStateCompat;
        }
        if (playbackStateCompat.getState() != 3 && playbackStateCompat.getState() != 4 && playbackStateCompat.getState() != 5) {
            return playbackStateCompat;
        }
        if (playbackStateCompat.getLastPositionUpdateTime() <= 0) {
            return playbackStateCompat;
        }
        long jElapsedRealtime = SystemClock.elapsedRealtime();
        long position = playbackStateCompat.getPosition() + ((long) (playbackStateCompat.getPlaybackSpeed() * (jElapsedRealtime - r0)));
        if (mediaMetadataCompat != null && mediaMetadataCompat.containsKey(MediaMetadataCompat.METADATA_KEY_DURATION)) {
            j = mediaMetadataCompat.getLong(MediaMetadataCompat.METADATA_KEY_DURATION);
        }
        return new PlaybackStateCompat.Builder(playbackStateCompat).setState(playbackStateCompat.getState(), (j < 0 || position <= j) ? position < 0 ? 0L : position : j, playbackStateCompat.getPlaybackSpeed(), jElapsedRealtime).build();
    }

    public static MediaSessionCompat fromMediaSession(Context context, Object obj) {
        if (context == null || obj == null) {
            return null;
        }
        return new MediaSessionCompat(context, new d(obj));
    }

    public void addOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener == null) {
            throw new IllegalArgumentException("Listener may not be null");
        }
        this.c.add(onActiveChangeListener);
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public String getCallingPackage() {
        return this.a.e();
    }

    public MediaControllerCompat getController() {
        return this.b;
    }

    public Object getMediaSession() {
        return this.a.f();
    }

    public Object getRemoteControlClient() {
        return this.a.c();
    }

    public Token getSessionToken() {
        return this.a.a();
    }

    public boolean isActive() {
        return this.a.d();
    }

    public void release() {
        this.a.b();
    }

    public void removeOnActiveChangeListener(OnActiveChangeListener onActiveChangeListener) {
        if (onActiveChangeListener == null) {
            throw new IllegalArgumentException("Listener may not be null");
        }
        this.c.remove(onActiveChangeListener);
    }

    public void sendSessionEvent(String str, Bundle bundle) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("event cannot be null or empty");
        }
        this.a.a(str, bundle);
    }

    public void setActive(boolean z) {
        this.a.a(z);
        Iterator<OnActiveChangeListener> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().onActiveChanged();
        }
    }

    public void setCallback(Callback callback) {
        setCallback(callback, null);
    }

    public void setCaptioningEnabled(boolean z) {
        this.a.setCaptioningEnabled(z);
    }

    public void setExtras(Bundle bundle) {
        this.a.setExtras(bundle);
    }

    public void setFlags(int i) {
        this.a.c(i);
    }

    public void setMediaButtonReceiver(PendingIntent pendingIntent) {
        this.a.b(pendingIntent);
    }

    public void setMetadata(MediaMetadataCompat mediaMetadataCompat) {
        this.a.a(mediaMetadataCompat);
    }

    public void setPlaybackState(PlaybackStateCompat playbackStateCompat) {
        this.a.a(playbackStateCompat);
    }

    public void setPlaybackToLocal(int i) {
        this.a.a(i);
    }

    public void setPlaybackToRemote(VolumeProviderCompat volumeProviderCompat) {
        if (volumeProviderCompat == null) {
            throw new IllegalArgumentException("volumeProvider may not be null!");
        }
        this.a.a(volumeProviderCompat);
    }

    public void setQueue(List<QueueItem> list) {
        this.a.a(list);
    }

    public void setQueueTitle(CharSequence charSequence) {
        this.a.a(charSequence);
    }

    public void setRatingType(int i) {
        this.a.b(i);
    }

    public void setRepeatMode(int i) {
        this.a.setRepeatMode(i);
    }

    public void setSessionActivity(PendingIntent pendingIntent) {
        this.a.a(pendingIntent);
    }

    public void setShuffleMode(int i) {
        this.a.setShuffleMode(i);
    }

    public MediaSessionCompat(Context context, String str, ComponentName componentName, PendingIntent pendingIntent) {
        this.c = new ArrayList<>();
        if (context == null) {
            throw new IllegalArgumentException("context must not be null");
        }
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("tag must not be null or empty");
        }
        if (componentName == null && (componentName = MediaButtonReceiver.a(context)) == null) {
            Log.w("MediaSessionCompat", "Couldn't find a unique registered media button receiver in the given context.");
        }
        if (componentName != null && pendingIntent == null) {
            Intent intent = new Intent("android.intent.action.MEDIA_BUTTON");
            intent.setComponent(componentName);
            pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        }
        this.a = new d(context, str);
        setCallback(new a(this));
        this.a.b(pendingIntent);
        this.b = new MediaControllerCompat(context, this);
        if (d == 0) {
            d = (int) TypedValue.applyDimension(1, 320.0f, context.getResources().getDisplayMetrics());
        }
    }

    public void setCallback(Callback callback, Handler handler) {
        c cVar = this.a;
        if (handler == null) {
            handler = new Handler();
        }
        cVar.a(callback, handler);
    }

    @RequiresApi(21)
    public static class d implements c {
        public final Object a;
        public final Token b;
        public boolean c = false;
        public final RemoteCallbackList<IMediaControllerCallback> d = new RemoteCallbackList<>();
        public PlaybackStateCompat e;
        public List<QueueItem> f;
        public MediaMetadataCompat g;
        public int h;
        public boolean i;
        public int j;
        public int k;

        public class a extends IMediaSession.Stub {
            public a() {
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, int i) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void adjustVolume(int i, int i2, String str) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void fastForward() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public Bundle getExtras() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public long getFlags() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public PendingIntent getLaunchPendingIntent() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public MediaMetadataCompat getMetadata() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public String getPackageName() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public PlaybackStateCompat getPlaybackState() {
                d dVar = d.this;
                return MediaSessionCompat.a(dVar.e, dVar.g);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public List<QueueItem> getQueue() {
                return null;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public CharSequence getQueueTitle() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public int getRatingType() {
                return d.this.h;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public int getRepeatMode() {
                return d.this.j;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public int getShuffleMode() {
                return d.this.k;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public String getTag() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public ParcelableVolumeInfo getVolumeAttributes() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public boolean isCaptioningEnabled() {
                return d.this.i;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public boolean isShuffleModeEnabledRemoved() {
                return false;
            }

            @Override // android.support.v4.media.session.IMediaSession
            public boolean isTransportControlEnabled() {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void next() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void pause() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void play() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void playFromMediaId(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void playFromSearch(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void prepare() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void prepareFromMediaId(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void prepareFromSearch(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void previous() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void rate(RatingCompat ratingCompat) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void rateWithExtras(RatingCompat ratingCompat, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                d dVar = d.this;
                if (dVar.c) {
                    return;
                }
                dVar.d.register(iMediaControllerCallback);
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void removeQueueItemAt(int i) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void rewind() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void seekTo(long j) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void sendCommand(String str, Bundle bundle, ResultReceiverWrapper resultReceiverWrapper) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void sendCustomAction(String str, Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public boolean sendMediaButton(KeyEvent keyEvent) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setCaptioningEnabled(boolean z) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setRepeatMode(int i) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setShuffleMode(int i) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setShuffleModeEnabledRemoved(boolean z) throws RemoteException {
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void setVolumeTo(int i, int i2, String str) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void skipToQueueItem(long j) {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void stop() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaSession
            public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) {
                d.this.d.unregister(iMediaControllerCallback);
            }
        }

        public d(Context context, String str) {
            this.a = new MediaSession(context, str);
            this.b = new Token(((MediaSession) this.a).getSessionToken(), new a());
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void a(Callback callback, Handler handler) {
            ((MediaSession) this.a).setCallback((MediaSession.Callback) (callback == null ? null : callback.a), handler);
            if (callback != null) {
                Callback.a(callback, this, handler);
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void b() {
            this.c = true;
            ((MediaSession) this.a).release();
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public Object c() {
            return null;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void c(int i) {
            ((MediaSession) this.a).setFlags(i);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public boolean d() {
            return ((MediaSession) this.a).isActive();
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public String e() {
            if (Build.VERSION.SDK_INT < 24) {
                return null;
            }
            MediaSession mediaSession = (MediaSession) this.a;
            try {
                return (String) mediaSession.getClass().getMethod("getCallingPackage", new Class[0]).invoke(mediaSession, new Object[0]);
            } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                Log.e("MediaSessionCompatApi24", "Cannot execute MediaSession.getCallingPackage()", e);
                return null;
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public Object f() {
            return this.a;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public PlaybackStateCompat getPlaybackState() {
            return this.e;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void setCaptioningEnabled(boolean z) {
            if (this.i != z) {
                this.i = z;
                for (int iBeginBroadcast = this.d.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                    try {
                        ((IMediaControllerCallback) this.d.getBroadcastItem(iBeginBroadcast)).onCaptioningEnabledChanged(z);
                    } catch (RemoteException unused) {
                    }
                }
                this.d.finishBroadcast();
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void setExtras(Bundle bundle) {
            ((MediaSession) this.a).setExtras(bundle);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void setRepeatMode(int i) {
            if (this.j != i) {
                this.j = i;
                for (int iBeginBroadcast = this.d.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                    try {
                        ((IMediaControllerCallback) this.d.getBroadcastItem(iBeginBroadcast)).onRepeatModeChanged(i);
                    } catch (RemoteException unused) {
                    }
                }
                this.d.finishBroadcast();
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void setShuffleMode(int i) {
            if (this.k != i) {
                this.k = i;
                for (int iBeginBroadcast = this.d.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                    try {
                        ((IMediaControllerCallback) this.d.getBroadcastItem(iBeginBroadcast)).onShuffleModeChanged(i);
                    } catch (RemoteException unused) {
                    }
                }
                this.d.finishBroadcast();
            }
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void a(int i) {
            Object obj = this.a;
            AudioAttributes.Builder builder = new AudioAttributes.Builder();
            builder.setLegacyStreamType(i);
            ((MediaSession) obj).setPlaybackToLocal(builder.build());
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void b(PendingIntent pendingIntent) {
            ((MediaSession) this.a).setMediaButtonReceiver(pendingIntent);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void b(int i) {
            ((MediaSession) this.a).setRatingType(i);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void a(VolumeProviderCompat volumeProviderCompat) {
            ((MediaSession) this.a).setPlaybackToRemote((VolumeProvider) volumeProviderCompat.getVolumeProvider());
        }

        public d(Object obj) {
            if (obj instanceof MediaSession) {
                this.a = obj;
                this.b = new Token(((MediaSession) this.a).getSessionToken(), new a());
                return;
            }
            throw new IllegalArgumentException("mediaSession is not a valid MediaSession object");
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void a(boolean z) {
            ((MediaSession) this.a).setActive(z);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void a(String str, Bundle bundle) {
            ((MediaSession) this.a).sendSessionEvent(str, bundle);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public Token a() {
            return this.b;
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void a(PlaybackStateCompat playbackStateCompat) {
            this.e = playbackStateCompat;
            for (int iBeginBroadcast = this.d.beginBroadcast() - 1; iBeginBroadcast >= 0; iBeginBroadcast--) {
                try {
                    ((IMediaControllerCallback) this.d.getBroadcastItem(iBeginBroadcast)).onPlaybackStateChanged(playbackStateCompat);
                } catch (RemoteException unused) {
                }
            }
            this.d.finishBroadcast();
            ((MediaSession) this.a).setPlaybackState((PlaybackState) (playbackStateCompat == null ? null : playbackStateCompat.getPlaybackState()));
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void a(MediaMetadataCompat mediaMetadataCompat) {
            this.g = mediaMetadataCompat;
            ((MediaSession) this.a).setMetadata((MediaMetadata) (mediaMetadataCompat == null ? null : mediaMetadataCompat.getMediaMetadata()));
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void a(PendingIntent pendingIntent) {
            ((MediaSession) this.a).setSessionActivity(pendingIntent);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void a(List<QueueItem> list) {
            ArrayList arrayList;
            this.f = list;
            if (list != null) {
                arrayList = new ArrayList();
                Iterator<QueueItem> it = list.iterator();
                while (it.hasNext()) {
                    arrayList.add(it.next().getQueueItem());
                }
            } else {
                arrayList = null;
            }
            Object obj = this.a;
            if (arrayList == null) {
                ((MediaSession) obj).setQueue(null);
                return;
            }
            ArrayList arrayList2 = new ArrayList();
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                arrayList2.add((MediaSession.QueueItem) it2.next());
            }
            ((MediaSession) obj).setQueue(arrayList2);
        }

        @Override // android.support.v4.media.session.MediaSessionCompat.c
        public void a(CharSequence charSequence) {
            ((MediaSession) this.a).setQueueTitle(charSequence);
        }
    }

    public static abstract class Callback {
        public final Object a;
        public WeakReference<c> b;
        public a c = null;
        public boolean d;

        public class a extends Handler {
            public a(Looper looper) {
                super(looper);
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (message.what == 1) {
                    Callback.this.a();
                }
            }
        }

        @RequiresApi(21)
        public class b implements m5 {
            public b() {
            }

            @Override // defpackage.m5
            public void a(String str, Bundle bundle, ResultReceiver resultReceiver) {
                try {
                    QueueItem queueItem = null;
                    IBinder iBinderAsBinder = null;
                    queueItem = null;
                    if (str.equals("android.support.v4.media.session.command.GET_EXTRA_BINDER")) {
                        d dVar = (d) Callback.this.b.get();
                        if (dVar != null) {
                            Bundle bundle2 = new Bundle();
                            IMediaSession extraBinder = dVar.b.getExtraBinder();
                            if (extraBinder != null) {
                                iBinderAsBinder = extraBinder.asBinder();
                            }
                            BundleCompat.putBinder(bundle2, "android.support.v4.media.session.EXTRA_BINDER", iBinderAsBinder);
                            resultReceiver.send(0, bundle2);
                            return;
                        }
                        return;
                    }
                    if (str.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM")) {
                        bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
                        Callback.this.onAddQueueItem((MediaDescriptionCompat) bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"));
                        return;
                    }
                    if (str.equals("android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT")) {
                        bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
                        Callback.this.onAddQueueItem((MediaDescriptionCompat) bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"), bundle.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX"));
                        return;
                    }
                    if (str.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM")) {
                        bundle.setClassLoader(MediaDescriptionCompat.class.getClassLoader());
                        Callback.this.onRemoveQueueItem((MediaDescriptionCompat) bundle.getParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION"));
                        return;
                    }
                    if (!str.equals("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM_AT")) {
                        Callback.this.onCommand(str, bundle, resultReceiver);
                        return;
                    }
                    d dVar2 = (d) Callback.this.b.get();
                    if (dVar2 == null || dVar2.f == null) {
                        return;
                    }
                    int i = bundle.getInt("android.support.v4.media.session.command.ARGUMENT_INDEX", -1);
                    if (i >= 0 && i < dVar2.f.size()) {
                        queueItem = dVar2.f.get(i);
                    }
                    if (queueItem != null) {
                        Callback.this.onRemoveQueueItem(queueItem.getDescription());
                    }
                } catch (BadParcelableException unused) {
                    Log.e("MediaSessionCompat", "Could not unparcel the extra data.");
                }
            }

            @Override // defpackage.m5
            public void b(String str, Bundle bundle) {
                Callback.this.onPlayFromMediaId(str, bundle);
            }

            @Override // defpackage.m5
            public void c() {
                Callback.this.onSkipToPrevious();
            }

            @Override // defpackage.m5
            public void d() {
                Callback.this.onSkipToNext();
            }

            @Override // defpackage.m5
            public void e() {
                Callback.this.onRewind();
            }

            @Override // defpackage.m5
            public void f() {
                Callback.this.onPause();
            }

            @Override // defpackage.m5
            public void g() {
                Callback.this.onFastForward();
            }

            @Override // defpackage.m5
            public void b(long j) {
                Callback.this.onSkipToQueueItem(j);
            }

            @Override // defpackage.m5
            public void c(String str, Bundle bundle) {
                if (str.equals("android.support.v4.media.session.action.PLAY_FROM_URI")) {
                    Callback.this.onPlayFromUri((Uri) bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_URI"), (Bundle) bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                    return;
                }
                if (str.equals("android.support.v4.media.session.action.PREPARE")) {
                    Callback.this.onPrepare();
                    return;
                }
                if (str.equals("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID")) {
                    Callback.this.onPrepareFromMediaId(bundle.getString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID"), bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                    return;
                }
                if (str.equals("android.support.v4.media.session.action.PREPARE_FROM_SEARCH")) {
                    Callback.this.onPrepareFromSearch(bundle.getString("android.support.v4.media.session.action.ARGUMENT_QUERY"), bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                    return;
                }
                if (str.equals("android.support.v4.media.session.action.PREPARE_FROM_URI")) {
                    Callback.this.onPrepareFromUri((Uri) bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_URI"), bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                    return;
                }
                if (str.equals("android.support.v4.media.session.action.SET_CAPTIONING_ENABLED")) {
                    Callback.this.onSetCaptioningEnabled(bundle.getBoolean("android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED"));
                    return;
                }
                if (str.equals("android.support.v4.media.session.action.SET_REPEAT_MODE")) {
                    Callback.this.onSetRepeatMode(bundle.getInt("android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE"));
                    return;
                }
                if (str.equals("android.support.v4.media.session.action.SET_SHUFFLE_MODE")) {
                    Callback.this.onSetShuffleMode(bundle.getInt("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE"));
                } else {
                    if (!str.equals("android.support.v4.media.session.action.SET_RATING")) {
                        Callback.this.onCustomAction(str, bundle);
                        return;
                    }
                    bundle.setClassLoader(RatingCompat.class.getClassLoader());
                    Callback.this.onSetRating((RatingCompat) bundle.getParcelable("android.support.v4.media.session.action.ARGUMENT_RATING"), bundle.getBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS"));
                }
            }

            @Override // defpackage.m5
            public void b() {
                Callback.this.onStop();
            }

            @Override // defpackage.m5
            public boolean a(Intent intent) {
                return Callback.this.onMediaButtonEvent(intent);
            }

            @Override // defpackage.m5
            public void a() {
                Callback.this.onPlay();
            }

            @Override // defpackage.m5
            public void a(String str, Bundle bundle) {
                Callback.this.onPlayFromSearch(str, bundle);
            }

            @Override // defpackage.m5
            public void a(long j) {
                Callback.this.onSeekTo(j);
            }

            @Override // defpackage.m5
            public void a(Object obj) {
                Callback.this.onSetRating(RatingCompat.fromRating(obj));
            }
        }

        @RequiresApi(23)
        public class c extends b implements MediaSessionCompatApi23$Callback {
            public c() {
                super();
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi23$Callback
            public void onPlayFromUri(Uri uri, Bundle bundle) {
                Callback.this.onPlayFromUri(uri, bundle);
            }
        }

        @RequiresApi(24)
        public class d extends c implements MediaSessionCompatApi24$Callback {
            public d() {
                super();
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi24$Callback
            public void onPrepare() {
                Callback.this.onPrepare();
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi24$Callback
            public void onPrepareFromMediaId(String str, Bundle bundle) {
                Callback.this.onPrepareFromMediaId(str, bundle);
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi24$Callback
            public void onPrepareFromSearch(String str, Bundle bundle) {
                Callback.this.onPrepareFromSearch(str, bundle);
            }

            @Override // android.support.v4.media.session.MediaSessionCompatApi24$Callback
            public void onPrepareFromUri(Uri uri, Bundle bundle) {
                Callback.this.onPrepareFromUri(uri, bundle);
            }
        }

        public Callback() {
            if (Build.VERSION.SDK_INT >= 24) {
                this.a = new p5(new d());
            } else {
                this.a = new o5(new c());
            }
        }

        public static /* synthetic */ void a(Callback callback, c cVar, Handler handler) {
            if (callback == null) {
                throw null;
            }
            callback.b = new WeakReference<>(cVar);
            a aVar = callback.c;
            if (aVar != null) {
                aVar.removeCallbacksAndMessages(null);
            }
            callback.c = callback.new a(handler.getLooper());
        }

        public void onAddQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        }

        public void onAddQueueItem(MediaDescriptionCompat mediaDescriptionCompat, int i) {
        }

        public void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
        }

        public void onCustomAction(String str, Bundle bundle) {
        }

        public void onFastForward() {
        }

        public boolean onMediaButtonEvent(Intent intent) {
            KeyEvent keyEvent;
            c cVar = this.b.get();
            if (cVar == null || this.c == null || (keyEvent = (KeyEvent) intent.getParcelableExtra("android.intent.extra.KEY_EVENT")) == null || keyEvent.getAction() != 0) {
                return false;
            }
            int keyCode = keyEvent.getKeyCode();
            if (keyCode != 79 && keyCode != 85) {
                a();
                return false;
            }
            if (keyEvent.getRepeatCount() > 0) {
                a();
            } else if (this.d) {
                this.c.removeMessages(1);
                this.d = false;
                PlaybackStateCompat playbackState = cVar.getPlaybackState();
                if (((playbackState == null ? 0L : playbackState.getActions()) & 32) != 0) {
                    onSkipToNext();
                }
            } else {
                this.d = true;
                this.c.sendEmptyMessageDelayed(1, ViewConfiguration.getDoubleTapTimeout());
            }
            return true;
        }

        public void onPause() {
        }

        public void onPlay() {
        }

        public void onPlayFromMediaId(String str, Bundle bundle) {
        }

        public void onPlayFromSearch(String str, Bundle bundle) {
        }

        public void onPlayFromUri(Uri uri, Bundle bundle) {
        }

        public void onPrepare() {
        }

        public void onPrepareFromMediaId(String str, Bundle bundle) {
        }

        public void onPrepareFromSearch(String str, Bundle bundle) {
        }

        public void onPrepareFromUri(Uri uri, Bundle bundle) {
        }

        public void onRemoveQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        }

        @Deprecated
        public void onRemoveQueueItemAt(int i) {
        }

        public void onRewind() {
        }

        public void onSeekTo(long j) {
        }

        public void onSetCaptioningEnabled(boolean z) {
        }

        public void onSetRating(RatingCompat ratingCompat) {
        }

        public void onSetRating(RatingCompat ratingCompat, Bundle bundle) {
        }

        public void onSetRepeatMode(int i) {
        }

        public void onSetShuffleMode(int i) {
        }

        public void onSkipToNext() {
        }

        public void onSkipToPrevious() {
        }

        public void onSkipToQueueItem(long j) {
        }

        public void onStop() {
        }

        public final void a() {
            if (this.d) {
                this.d = false;
                this.c.removeMessages(1);
                c cVar = this.b.get();
                if (cVar == null) {
                    return;
                }
                PlaybackStateCompat playbackState = cVar.getPlaybackState();
                long actions = playbackState == null ? 0L : playbackState.getActions();
                boolean z = playbackState != null && playbackState.getState() == 3;
                boolean z2 = (516 & actions) != 0;
                boolean z3 = (actions & 514) != 0;
                if (z && z3) {
                    onPause();
                } else {
                    if (z || !z2) {
                        return;
                    }
                    onPlay();
                }
            }
        }
    }

    public MediaSessionCompat(Context context, c cVar) throws NoSuchFieldException {
        this.c = new ArrayList<>();
        this.a = cVar;
        Object objF = cVar.f();
        boolean z = false;
        try {
            Field declaredField = objF.getClass().getDeclaredField("mCallback");
            if (declaredField != null) {
                declaredField.setAccessible(true);
                if (declaredField.get(objF) != null) {
                    z = true;
                }
            }
        } catch (IllegalAccessException | NoSuchFieldException unused) {
            Log.w("MediaSessionCompatApi21", "Failed to get mCallback object.");
        }
        if (!z) {
            setCallback(new b(this));
        }
        this.b = new MediaControllerCompat(context, this);
    }
}
