package defpackage;

import android.content.Intent;
import android.media.Rating;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.os.ResultReceiver;
import defpackage.m5;

/* loaded from: classes.dex */
public class n5<T extends m5> extends MediaSession.Callback {
    public final T a;

    public n5(T t) {
        this.a = t;
    }

    @Override // android.media.session.MediaSession.Callback
    public void onCommand(String str, Bundle bundle, ResultReceiver resultReceiver) {
        this.a.a(str, bundle, resultReceiver);
    }

    @Override // android.media.session.MediaSession.Callback
    public void onCustomAction(String str, Bundle bundle) {
        this.a.c(str, bundle);
    }

    @Override // android.media.session.MediaSession.Callback
    public void onFastForward() {
        this.a.g();
    }

    @Override // android.media.session.MediaSession.Callback
    public boolean onMediaButtonEvent(Intent intent) {
        return this.a.a(intent) || super.onMediaButtonEvent(intent);
    }

    @Override // android.media.session.MediaSession.Callback
    public void onPause() {
        this.a.f();
    }

    @Override // android.media.session.MediaSession.Callback
    public void onPlay() {
        this.a.a();
    }

    @Override // android.media.session.MediaSession.Callback
    public void onPlayFromMediaId(String str, Bundle bundle) {
        this.a.b(str, bundle);
    }

    @Override // android.media.session.MediaSession.Callback
    public void onPlayFromSearch(String str, Bundle bundle) {
        this.a.a(str, bundle);
    }

    @Override // android.media.session.MediaSession.Callback
    public void onRewind() {
        this.a.e();
    }

    @Override // android.media.session.MediaSession.Callback
    public void onSeekTo(long j) {
        this.a.a(j);
    }

    @Override // android.media.session.MediaSession.Callback
    public void onSetRating(Rating rating) {
        this.a.a(rating);
    }

    @Override // android.media.session.MediaSession.Callback
    public void onSkipToNext() {
        this.a.d();
    }

    @Override // android.media.session.MediaSession.Callback
    public void onSkipToPrevious() {
        this.a.c();
    }

    @Override // android.media.session.MediaSession.Callback
    public void onSkipToQueueItem(long j) {
        this.a.b(j);
    }

    @Override // android.media.session.MediaSession.Callback
    public void onStop() {
        this.a.b();
    }
}
