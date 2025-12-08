package android.support.v4.media.session;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.media.MediaMetadata;
import android.media.session.MediaController;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.SupportActivity;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaControllerCompatApi21;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import defpackage.g9;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/* loaded from: classes.dex */
public final class MediaControllerCompat {
    public final b a;
    public final MediaSessionCompat.Token b;
    public final HashSet<Callback> c = new HashSet<>();

    public static final class PlaybackInfo {
        public static final int PLAYBACK_TYPE_LOCAL = 1;
        public static final int PLAYBACK_TYPE_REMOTE = 2;
        public final int a;
        public final int b;
        public final int c;
        public final int d;
        public final int e;

        public PlaybackInfo(int i, int i2, int i3, int i4, int i5) {
            this.a = i;
            this.b = i2;
            this.c = i3;
            this.d = i4;
            this.e = i5;
        }

        public int getAudioStream() {
            return this.b;
        }

        public int getCurrentVolume() {
            return this.e;
        }

        public int getMaxVolume() {
            return this.d;
        }

        public int getPlaybackType() {
            return this.a;
        }

        public int getVolumeControl() {
            return this.c;
        }
    }

    public static abstract class TransportControls {
        public static final String EXTRA_LEGACY_STREAM_TYPE = "android.media.session.extra.LEGACY_STREAM_TYPE";

        public abstract void fastForward();

        public abstract void pause();

        public abstract void play();

        public abstract void playFromMediaId(String str, Bundle bundle);

        public abstract void playFromSearch(String str, Bundle bundle);

        public abstract void playFromUri(Uri uri, Bundle bundle);

        public abstract void prepare();

        public abstract void prepareFromMediaId(String str, Bundle bundle);

        public abstract void prepareFromSearch(String str, Bundle bundle);

        public abstract void prepareFromUri(Uri uri, Bundle bundle);

        public abstract void rewind();

        public abstract void seekTo(long j);

        public abstract void sendCustomAction(PlaybackStateCompat.CustomAction customAction, Bundle bundle);

        public abstract void sendCustomAction(String str, Bundle bundle);

        public abstract void setCaptioningEnabled(boolean z);

        public abstract void setRating(RatingCompat ratingCompat);

        public abstract void setRating(RatingCompat ratingCompat, Bundle bundle);

        public abstract void setRepeatMode(int i);

        public abstract void setShuffleMode(int i);

        public abstract void skipToNext();

        public abstract void skipToPrevious();

        public abstract void skipToQueueItem(long j);

        public abstract void stop();
    }

    public static class a extends SupportActivity.ExtraData {
        public final MediaControllerCompat a;

        public a(MediaControllerCompat mediaControllerCompat) {
            this.a = mediaControllerCompat;
        }
    }

    public interface b {
        PendingIntent a();

        void a(int i, int i2);

        void a(MediaDescriptionCompat mediaDescriptionCompat, int i);

        void a(Callback callback);

        void a(Callback callback, Handler handler);

        void a(String str, Bundle bundle, ResultReceiver resultReceiver);

        boolean a(KeyEvent keyEvent);

        void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat);

        PlaybackInfo b();

        void b(int i, int i2);

        TransportControls c();

        Object d();

        boolean e();

        Bundle getExtras();

        long getFlags();

        MediaMetadataCompat getMetadata();

        String getPackageName();

        PlaybackStateCompat getPlaybackState();

        List<MediaSessionCompat.QueueItem> getQueue();

        CharSequence getQueueTitle();

        int getRatingType();

        int getRepeatMode();

        int getShuffleMode();

        boolean isCaptioningEnabled();

        void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat);
    }

    @RequiresApi(23)
    public static class c extends MediaControllerImplApi21 {
        public c(Context context, MediaSessionCompat mediaSessionCompat) {
            super(context, mediaSessionCompat);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.MediaControllerImplApi21, android.support.v4.media.session.MediaControllerCompat.b
        public TransportControls c() {
            Object objA = MediaControllerCompatApi21.a(this.a);
            if (objA != null) {
                return new f(objA);
            }
            return null;
        }

        public c(Context context, MediaSessionCompat.Token token) throws RemoteException {
            super(context, token);
        }
    }

    @RequiresApi(24)
    public static class d extends c {
        public d(Context context, MediaSessionCompat mediaSessionCompat) {
            super(context, mediaSessionCompat);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.c, android.support.v4.media.session.MediaControllerCompat.MediaControllerImplApi21, android.support.v4.media.session.MediaControllerCompat.b
        public TransportControls c() {
            Object objA = MediaControllerCompatApi21.a(this.a);
            if (objA != null) {
                return new g(objA);
            }
            return null;
        }

        public d(Context context, MediaSessionCompat.Token token) throws RemoteException {
            super(context, token);
        }
    }

    @RequiresApi(23)
    public static class f extends e {
        public f(Object obj) {
            super(obj);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.e, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromUri(Uri uri, Bundle bundle) {
            MediaControllerCompatApi23$TransportControls.playFromUri(this.a, uri, bundle);
        }
    }

    @RequiresApi(24)
    public static class g extends f {
        public g(Object obj) {
            super(obj);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.e, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepare() {
            MediaControllerCompatApi24$TransportControls.prepare(this.a);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.e, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromMediaId(String str, Bundle bundle) {
            MediaControllerCompatApi24$TransportControls.prepareFromMediaId(this.a, str, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.e, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromSearch(String str, Bundle bundle) {
            MediaControllerCompatApi24$TransportControls.prepareFromSearch(this.a, str, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.e, android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromUri(Uri uri, Bundle bundle) {
            MediaControllerCompatApi24$TransportControls.prepareFromUri(this.a, uri, bundle);
        }
    }

    public MediaControllerCompat(Context context, @NonNull MediaSessionCompat mediaSessionCompat) {
        if (mediaSessionCompat == null) {
            throw new IllegalArgumentException("session must not be null");
        }
        this.b = mediaSessionCompat.getSessionToken();
        if (Build.VERSION.SDK_INT >= 24) {
            this.a = new d(context, mediaSessionCompat);
        } else {
            this.a = new c(context, mediaSessionCompat);
        }
    }

    public static /* synthetic */ void a(String str, Bundle bundle) {
        if (str == null) {
            return;
        }
        char c2 = 65535;
        int iHashCode = str.hashCode();
        if (iHashCode != -1348483723) {
            if (iHashCode == 503011406 && str.equals(MediaSessionCompat.ACTION_UNFOLLOW)) {
                c2 = 1;
            }
        } else if (str.equals(MediaSessionCompat.ACTION_FOLLOW)) {
            c2 = 0;
        }
        if (c2 == 0 || c2 == 1) {
            if (bundle == null || !bundle.containsKey(MediaSessionCompat.ARGUMENT_MEDIA_ATTRIBUTE)) {
                throw new IllegalArgumentException(g9.a("An extra field android.support.v4.media.session.ARGUMENT_MEDIA_ATTRIBUTE is required for this action ", str, "."));
            }
        }
    }

    public static MediaControllerCompat getMediaController(@NonNull Activity activity2) {
        if (activity2 instanceof SupportActivity) {
            a aVar = (a) ((SupportActivity) activity2).getExtraData(a.class);
            if (aVar != null) {
                return aVar.a;
            }
            return null;
        }
        MediaController mediaController = activity2.getMediaController();
        if (mediaController == null) {
            return null;
        }
        try {
            return new MediaControllerCompat(activity2, MediaSessionCompat.Token.fromToken(mediaController.getSessionToken()));
        } catch (RemoteException e2) {
            Log.e("MediaControllerCompat", "Dead object in getMediaController.", e2);
            return null;
        }
    }

    public static void setMediaController(@NonNull Activity activity2, MediaControllerCompat mediaControllerCompat) {
        if (activity2 instanceof SupportActivity) {
            ((SupportActivity) activity2).putExtraData(new a(mediaControllerCompat));
        }
        activity2.setMediaController((MediaController) (mediaControllerCompat != null ? MediaControllerCompatApi21.a(activity2, mediaControllerCompat.getSessionToken().getToken()) : null));
    }

    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        this.a.addQueueItem(mediaDescriptionCompat);
    }

    public void adjustVolume(int i, int i2) {
        this.a.a(i, i2);
    }

    public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
        if (keyEvent != null) {
            return this.a.a(keyEvent);
        }
        throw new IllegalArgumentException("KeyEvent may not be null");
    }

    public Bundle getExtras() {
        return this.a.getExtras();
    }

    public long getFlags() {
        return this.a.getFlags();
    }

    public MediaMetadataCompat getMetadata() {
        return this.a.getMetadata();
    }

    public String getPackageName() {
        return this.a.getPackageName();
    }

    public PlaybackInfo getPlaybackInfo() {
        return this.a.b();
    }

    public PlaybackStateCompat getPlaybackState() {
        return this.a.getPlaybackState();
    }

    public List<MediaSessionCompat.QueueItem> getQueue() {
        return this.a.getQueue();
    }

    public CharSequence getQueueTitle() {
        return this.a.getQueueTitle();
    }

    public int getRatingType() {
        return this.a.getRatingType();
    }

    public int getRepeatMode() {
        return this.a.getRepeatMode();
    }

    public PendingIntent getSessionActivity() {
        return this.a.a();
    }

    public MediaSessionCompat.Token getSessionToken() {
        return this.b;
    }

    public int getShuffleMode() {
        return this.a.getShuffleMode();
    }

    public TransportControls getTransportControls() {
        return this.a.c();
    }

    public boolean isCaptioningEnabled() {
        return this.a.isCaptioningEnabled();
    }

    public boolean isSessionReady() {
        return this.a.e();
    }

    public void registerCallback(@NonNull Callback callback) {
        registerCallback(callback, null);
    }

    public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
        this.a.removeQueueItem(mediaDescriptionCompat);
    }

    @Deprecated
    public void removeQueueItemAt(int i) {
        MediaSessionCompat.QueueItem queueItem;
        List<MediaSessionCompat.QueueItem> queue = getQueue();
        if (queue == null || i < 0 || i >= queue.size() || (queueItem = queue.get(i)) == null) {
            return;
        }
        removeQueueItem(queueItem.getDescription());
    }

    public void sendCommand(@NonNull String str, Bundle bundle, ResultReceiver resultReceiver) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("command must neither be null nor empty");
        }
        this.a.a(str, bundle, resultReceiver);
    }

    public void setVolumeTo(int i, int i2) {
        this.a.b(i, i2);
    }

    public void unregisterCallback(@NonNull Callback callback) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        try {
            this.c.remove(callback);
            this.a.a(callback);
        } finally {
            callback.a(null);
        }
    }

    public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat, int i) {
        this.a.a(mediaDescriptionCompat, i);
    }

    public void registerCallback(@NonNull Callback callback, Handler handler) {
        if (callback == null) {
            throw new IllegalArgumentException("callback must not be null");
        }
        if (handler == null) {
            handler = new Handler();
        }
        callback.a(handler);
        this.a.a(callback, handler);
        this.c.add(callback);
    }

    public static class e extends TransportControls {
        public final Object a;

        public e(Object obj) {
            this.a = obj;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void fastForward() {
            MediaControllerCompatApi21.TransportControls.fastForward(this.a);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void pause() {
            MediaControllerCompatApi21.TransportControls.pause(this.a);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void play() {
            MediaControllerCompatApi21.TransportControls.play(this.a);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromMediaId(String str, Bundle bundle) {
            MediaControllerCompatApi21.TransportControls.playFromMediaId(this.a, str, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromSearch(String str, Bundle bundle) {
            MediaControllerCompatApi21.TransportControls.playFromSearch(this.a, str, bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void playFromUri(Uri uri, Bundle bundle) {
            if (uri == null || Uri.EMPTY.equals(uri)) {
                throw new IllegalArgumentException("You must specify a non-empty Uri for playFromUri.");
            }
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", uri);
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle);
            sendCustomAction("android.support.v4.media.session.action.PLAY_FROM_URI", bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepare() {
            sendCustomAction("android.support.v4.media.session.action.PREPARE", (Bundle) null);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromMediaId(String str, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("android.support.v4.media.session.action.ARGUMENT_MEDIA_ID", str);
            bundle2.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle);
            sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_MEDIA_ID", bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromSearch(String str, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putString("android.support.v4.media.session.action.ARGUMENT_QUERY", str);
            bundle2.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle);
            sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_SEARCH", bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void prepareFromUri(Uri uri, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_URI", uri);
            bundle2.putBundle("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle);
            sendCustomAction("android.support.v4.media.session.action.PREPARE_FROM_URI", bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void rewind() {
            MediaControllerCompatApi21.TransportControls.rewind(this.a);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void seekTo(long j) {
            MediaControllerCompatApi21.TransportControls.seekTo(this.a, j);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void sendCustomAction(PlaybackStateCompat.CustomAction customAction, Bundle bundle) {
            MediaControllerCompat.a(customAction.getAction(), bundle);
            MediaControllerCompatApi21.TransportControls.sendCustomAction(this.a, customAction.getAction(), bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setCaptioningEnabled(boolean z) {
            Bundle bundle = new Bundle();
            bundle.putBoolean("android.support.v4.media.session.action.ARGUMENT_CAPTIONING_ENABLED", z);
            sendCustomAction("android.support.v4.media.session.action.SET_CAPTIONING_ENABLED", bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRating(RatingCompat ratingCompat) {
            MediaControllerCompatApi21.TransportControls.setRating(this.a, ratingCompat != null ? ratingCompat.getRating() : null);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRepeatMode(int i) {
            Bundle bundle = new Bundle();
            bundle.putInt("android.support.v4.media.session.action.ARGUMENT_REPEAT_MODE", i);
            sendCustomAction("android.support.v4.media.session.action.SET_REPEAT_MODE", bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setShuffleMode(int i) {
            Bundle bundle = new Bundle();
            bundle.putInt("android.support.v4.media.session.action.ARGUMENT_SHUFFLE_MODE", i);
            sendCustomAction("android.support.v4.media.session.action.SET_SHUFFLE_MODE", bundle);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToNext() {
            MediaControllerCompatApi21.TransportControls.skipToNext(this.a);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToPrevious() {
            MediaControllerCompatApi21.TransportControls.skipToPrevious(this.a);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void skipToQueueItem(long j) {
            MediaControllerCompatApi21.TransportControls.skipToQueueItem(this.a, j);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void stop() {
            MediaControllerCompatApi21.TransportControls.stop(this.a);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void setRating(RatingCompat ratingCompat, Bundle bundle) {
            Bundle bundle2 = new Bundle();
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_RATING", ratingCompat);
            bundle2.putParcelable("android.support.v4.media.session.action.ARGUMENT_EXTRAS", bundle);
            sendCustomAction("android.support.v4.media.session.action.SET_RATING", bundle2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.TransportControls
        public void sendCustomAction(String str, Bundle bundle) {
            MediaControllerCompat.a(str, bundle);
            MediaControllerCompatApi21.TransportControls.sendCustomAction(this.a, str, bundle);
        }
    }

    public static abstract class Callback implements IBinder.DeathRecipient {
        public final Object a = new MediaControllerCompatApi21.a(new b(this));
        public a b;
        public boolean c;

        public class a extends Handler {
            public boolean a;

            public a(Looper looper) {
                super(looper);
                this.a = false;
            }

            @Override // android.os.Handler
            public void handleMessage(Message message) {
                if (this.a) {
                    switch (message.what) {
                        case 1:
                            Callback.this.onSessionEvent((String) message.obj, message.getData());
                            break;
                        case 2:
                            Callback.this.onPlaybackStateChanged((PlaybackStateCompat) message.obj);
                            break;
                        case 3:
                            Callback.this.onMetadataChanged((MediaMetadataCompat) message.obj);
                            break;
                        case 4:
                            Callback.this.onAudioInfoChanged((PlaybackInfo) message.obj);
                            break;
                        case 5:
                            Callback.this.onQueueChanged((List) message.obj);
                            break;
                        case 6:
                            Callback.this.onQueueTitleChanged((CharSequence) message.obj);
                            break;
                        case 7:
                            Callback.this.onExtrasChanged((Bundle) message.obj);
                            break;
                        case 8:
                            Callback.this.onSessionDestroyed();
                            break;
                        case 9:
                            Callback.this.onRepeatModeChanged(((Integer) message.obj).intValue());
                            break;
                        case 11:
                            Callback.this.onCaptioningEnabledChanged(((Boolean) message.obj).booleanValue());
                            break;
                        case 12:
                            Callback.this.onShuffleModeChanged(((Integer) message.obj).intValue());
                            break;
                        case 13:
                            Callback.this.onSessionReady();
                            break;
                    }
                }
            }
        }

        public static class b implements MediaControllerCompatApi21.Callback {
            public final WeakReference<Callback> a;

            public b(Callback callback) {
                this.a = new WeakReference<>(callback);
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onAudioInfoChanged(int i, int i2, int i3, int i4, int i5) {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.onAudioInfoChanged(new PlaybackInfo(i, i2, i3, i4, i5));
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onExtrasChanged(Bundle bundle) {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.onExtrasChanged(bundle);
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onMetadataChanged(Object obj) {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(obj));
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onPlaybackStateChanged(Object obj) {
                Callback callback = this.a.get();
                if (callback == null || callback.c) {
                    return;
                }
                callback.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(obj));
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onQueueChanged(List<?> list) {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.onQueueChanged(MediaSessionCompat.QueueItem.fromQueueItemList(list));
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onQueueTitleChanged(CharSequence charSequence) {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.onQueueTitleChanged(charSequence);
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onSessionDestroyed() {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.onSessionDestroyed();
                }
            }

            @Override // android.support.v4.media.session.MediaControllerCompatApi21.Callback
            public void onSessionEvent(String str, Bundle bundle) {
                Callback callback = this.a.get();
                if (callback != null) {
                    boolean z = callback.c;
                    callback.onSessionEvent(str, bundle);
                }
            }
        }

        public static class c extends IMediaControllerCallback.Stub {
            public final WeakReference<Callback> a;

            public c(Callback callback) {
                this.a = new WeakReference<>(callback);
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onCaptioningEnabledChanged(boolean z) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(11, Boolean.valueOf(z), null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onEvent(String str, Bundle bundle) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(1, str, bundle);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(2, playbackStateCompat, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onRepeatModeChanged(int i) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(9, Integer.valueOf(i), null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onSessionReady() throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(13, null, null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onShuffleModeChanged(int i) throws RemoteException {
                Callback callback = this.a.get();
                if (callback != null) {
                    callback.a(12, Integer.valueOf(i), null);
                }
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onShuffleModeChangedRemoved(boolean z) throws RemoteException {
            }
        }

        public void a(Handler handler) {
            if (handler != null) {
                a aVar = new a(handler.getLooper());
                this.b = aVar;
                aVar.a = true;
            } else {
                a aVar2 = this.b;
                if (aVar2 != null) {
                    aVar2.a = false;
                    aVar2.removeCallbacksAndMessages(null);
                    this.b = null;
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            onSessionDestroyed();
        }

        public void onAudioInfoChanged(PlaybackInfo playbackInfo) {
        }

        public void onCaptioningEnabledChanged(boolean z) {
        }

        public void onExtrasChanged(Bundle bundle) {
        }

        public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
        }

        public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
        }

        public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) {
        }

        public void onQueueTitleChanged(CharSequence charSequence) {
        }

        public void onRepeatModeChanged(int i) {
        }

        public void onSessionDestroyed() {
        }

        public void onSessionEvent(String str, Bundle bundle) {
        }

        public void onSessionReady() {
        }

        public void onShuffleModeChanged(int i) {
        }

        public void a(int i, Object obj, Bundle bundle) {
            a aVar = this.b;
            if (aVar != null) {
                Message messageObtainMessage = aVar.obtainMessage(i, obj);
                messageObtainMessage.setData(bundle);
                messageObtainMessage.sendToTarget();
            }
        }
    }

    @RequiresApi(21)
    public static class MediaControllerImplApi21 implements b {
        public final Object a;
        public IMediaSession c;
        public final List<Callback> b = new ArrayList();
        public HashMap<Callback, a> d = new HashMap<>();

        public static class ExtraBinderRequestResultReceiver extends ResultReceiver {
            public WeakReference<MediaControllerImplApi21> a;

            public ExtraBinderRequestResultReceiver(MediaControllerImplApi21 mediaControllerImplApi21, Handler handler) {
                super(handler);
                this.a = new WeakReference<>(mediaControllerImplApi21);
            }

            @Override // android.os.ResultReceiver
            public void onReceiveResult(int i, Bundle bundle) {
                MediaControllerImplApi21 mediaControllerImplApi21 = this.a.get();
                if (mediaControllerImplApi21 == null || bundle == null) {
                    return;
                }
                mediaControllerImplApi21.c = IMediaSession.Stub.asInterface(BundleCompat.getBinder(bundle, "android.support.v4.media.session.EXTRA_BINDER"));
                mediaControllerImplApi21.f();
            }
        }

        public static class a extends Callback.c {
            public a(Callback callback) {
                super(callback);
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onExtrasChanged(Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onSessionDestroyed() throws RemoteException {
                throw new AssertionError();
            }

            @Override // android.support.v4.media.session.IMediaControllerCallback
            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
                throw new AssertionError();
            }
        }

        public MediaControllerImplApi21(Context context, MediaSessionCompat mediaSessionCompat) {
            this.a = MediaControllerCompatApi21.a(context, mediaSessionCompat.getSessionToken().getToken());
            IMediaSession extraBinder = mediaSessionCompat.getSessionToken().getExtraBinder();
            this.c = extraBinder;
            if (extraBinder == null) {
                ((MediaController) this.a).sendCommand("android.support.v4.media.session.command.GET_EXTRA_BINDER", null, new ExtraBinderRequestResultReceiver(this, new Handler()));
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public final void a(Callback callback, Handler handler) {
            ((MediaController) this.a).registerCallback((MediaController.Callback) callback.a, handler);
            if (this.c == null) {
                synchronized (this.b) {
                    callback.c = false;
                    this.b.add(callback);
                }
                return;
            }
            a aVar = new a(callback);
            this.d.put(callback, aVar);
            callback.c = true;
            try {
                this.c.registerCallbackListener(aVar);
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
            if ((getFlags() & 4) == 0) {
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", mediaDescriptionCompat);
            ((MediaController) this.a).sendCommand("android.support.v4.media.session.command.ADD_QUEUE_ITEM", bundle, null);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public PlaybackInfo b() {
            MediaController.PlaybackInfo playbackInfo = ((MediaController) this.a).getPlaybackInfo();
            if (playbackInfo != null) {
                return new PlaybackInfo(MediaControllerCompatApi21.PlaybackInfo.getPlaybackType(playbackInfo), MediaControllerCompatApi21.PlaybackInfo.getLegacyAudioStream(playbackInfo), MediaControllerCompatApi21.PlaybackInfo.getVolumeControl(playbackInfo), MediaControllerCompatApi21.PlaybackInfo.getMaxVolume(playbackInfo), MediaControllerCompatApi21.PlaybackInfo.getCurrentVolume(playbackInfo));
            }
            return null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public TransportControls c() {
            Object objA = MediaControllerCompatApi21.a(this.a);
            if (objA != null) {
                return new e(objA);
            }
            return null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public Object d() {
            return this.a;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public boolean e() {
            return this.c != null;
        }

        public final void f() {
            if (this.c == null) {
                return;
            }
            synchronized (this.b) {
                for (Callback callback : this.b) {
                    a aVar = new a(callback);
                    this.d.put(callback, aVar);
                    callback.c = true;
                    try {
                        this.c.registerCallbackListener(aVar);
                        callback.onSessionReady();
                    } catch (RemoteException e) {
                        Log.e("MediaControllerCompat", "Dead object in registerCallback.", e);
                    }
                }
                this.b.clear();
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public Bundle getExtras() {
            return ((MediaController) this.a).getExtras();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public long getFlags() {
            return ((MediaController) this.a).getFlags();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public MediaMetadataCompat getMetadata() {
            MediaMetadata metadata = ((MediaController) this.a).getMetadata();
            if (metadata != null) {
                return MediaMetadataCompat.fromMediaMetadata(metadata);
            }
            return null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public String getPackageName() {
            return ((MediaController) this.a).getPackageName();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public PlaybackStateCompat getPlaybackState() {
            IMediaSession iMediaSession = this.c;
            if (iMediaSession != null) {
                try {
                    return iMediaSession.getPlaybackState();
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in getPlaybackState.", e);
                }
            }
            PlaybackState playbackState = ((MediaController) this.a).getPlaybackState();
            if (playbackState != null) {
                return PlaybackStateCompat.fromPlaybackState(playbackState);
            }
            return null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public List<MediaSessionCompat.QueueItem> getQueue() {
            List<MediaSession.QueueItem> queue = ((MediaController) this.a).getQueue();
            ArrayList arrayList = queue == null ? null : new ArrayList(queue);
            if (arrayList != null) {
                return MediaSessionCompat.QueueItem.fromQueueItemList(arrayList);
            }
            return null;
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public CharSequence getQueueTitle() {
            return ((MediaController) this.a).getQueueTitle();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public int getRatingType() {
            return ((MediaController) this.a).getRatingType();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public int getRepeatMode() {
            IMediaSession iMediaSession = this.c;
            if (iMediaSession == null) {
                return -1;
            }
            try {
                return iMediaSession.getRepeatMode();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getRepeatMode.", e);
                return -1;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public int getShuffleMode() {
            IMediaSession iMediaSession = this.c;
            if (iMediaSession == null) {
                return -1;
            }
            try {
                return iMediaSession.getShuffleMode();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in getShuffleMode.", e);
                return -1;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public boolean isCaptioningEnabled() {
            IMediaSession iMediaSession = this.c;
            if (iMediaSession == null) {
                return false;
            }
            try {
                return iMediaSession.isCaptioningEnabled();
            } catch (RemoteException e) {
                Log.e("MediaControllerCompat", "Dead object in isCaptioningEnabled.", e);
                return false;
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) {
            if ((getFlags() & 4) == 0) {
                throw new UnsupportedOperationException("This session doesn't support queue management operations");
            }
            Bundle bundle = new Bundle();
            bundle.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", mediaDescriptionCompat);
            ((MediaController) this.a).sendCommand("android.support.v4.media.session.command.REMOVE_QUEUE_ITEM", bundle, null);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public void b(int i, int i2) {
            ((MediaController) this.a).setVolumeTo(i, i2);
        }

        public MediaControllerImplApi21(Context context, MediaSessionCompat.Token token) throws RemoteException {
            this.a = MediaControllerCompatApi21.a(context, token.getToken());
            IMediaSession extraBinder = token.getExtraBinder();
            this.c = extraBinder;
            if (extraBinder == null) {
                ((MediaController) this.a).sendCommand("android.support.v4.media.session.command.GET_EXTRA_BINDER", null, new ExtraBinderRequestResultReceiver(this, new Handler()));
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public final void a(Callback callback) {
            ((MediaController) this.a).unregisterCallback((MediaController.Callback) callback.a);
            if (this.c != null) {
                try {
                    a aVarRemove = this.d.remove(callback);
                    if (aVarRemove != null) {
                        callback.c = false;
                        this.c.unregisterCallbackListener(aVarRemove);
                        return;
                    }
                    return;
                } catch (RemoteException e) {
                    Log.e("MediaControllerCompat", "Dead object in unregisterCallback.", e);
                    return;
                }
            }
            synchronized (this.b) {
                this.b.remove(callback);
            }
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public boolean a(KeyEvent keyEvent) {
            return ((MediaController) this.a).dispatchMediaButtonEvent(keyEvent);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public void a(MediaDescriptionCompat mediaDescriptionCompat, int i) {
            if ((getFlags() & 4) != 0) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("android.support.v4.media.session.command.ARGUMENT_MEDIA_DESCRIPTION", mediaDescriptionCompat);
                bundle.putInt("android.support.v4.media.session.command.ARGUMENT_INDEX", i);
                ((MediaController) this.a).sendCommand("android.support.v4.media.session.command.ADD_QUEUE_ITEM_AT", bundle, null);
                return;
            }
            throw new UnsupportedOperationException("This session doesn't support queue management operations");
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public PendingIntent a() {
            return ((MediaController) this.a).getSessionActivity();
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public void a(int i, int i2) {
            ((MediaController) this.a).adjustVolume(i, i2);
        }

        @Override // android.support.v4.media.session.MediaControllerCompat.b
        public void a(String str, Bundle bundle, ResultReceiver resultReceiver) {
            ((MediaController) this.a).sendCommand(str, bundle, resultReceiver);
        }
    }

    public MediaControllerCompat(Context context, @NonNull MediaSessionCompat.Token token) throws RemoteException {
        if (token != null) {
            this.b = token;
            if (Build.VERSION.SDK_INT >= 24) {
                this.a = new d(context, token);
                return;
            } else {
                this.a = new c(context, token);
                return;
            }
        }
        throw new IllegalArgumentException("sessionToken must not be null");
    }

    public Object getMediaController() {
        return this.a.d();
    }
}
