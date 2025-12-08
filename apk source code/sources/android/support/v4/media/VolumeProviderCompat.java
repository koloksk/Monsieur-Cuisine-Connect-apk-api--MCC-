package android.support.v4.media;

import android.media.VolumeProvider;
import android.support.annotation.RestrictTo;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;
import defpackage.l5;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes.dex */
public abstract class VolumeProviderCompat {
    public static final int VOLUME_CONTROL_ABSOLUTE = 2;
    public static final int VOLUME_CONTROL_FIXED = 0;
    public static final int VOLUME_CONTROL_RELATIVE = 1;
    public final int a;
    public final int b;
    public int c;
    public Callback d;
    public Object e;

    public static abstract class Callback {
        public abstract void onVolumeChanged(VolumeProviderCompat volumeProviderCompat);
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface ControlType {
    }

    public class a implements VolumeProviderCompatApi21$Delegate {
        public a() {
        }

        @Override // android.support.v4.media.VolumeProviderCompatApi21$Delegate
        public void onAdjustVolume(int i) {
            VolumeProviderCompat.this.onAdjustVolume(i);
        }

        @Override // android.support.v4.media.VolumeProviderCompatApi21$Delegate
        public void onSetVolumeTo(int i) {
            VolumeProviderCompat.this.onSetVolumeTo(i);
        }
    }

    public VolumeProviderCompat(int i, int i2, int i3) {
        this.a = i;
        this.b = i2;
        this.c = i3;
    }

    public final int getCurrentVolume() {
        return this.c;
    }

    public final int getMaxVolume() {
        return this.b;
    }

    public final int getVolumeControl() {
        return this.a;
    }

    public Object getVolumeProvider() {
        if (this.e == null) {
            this.e = new l5(this.a, this.b, this.c, new a());
        }
        return this.e;
    }

    public void onAdjustVolume(int i) {
    }

    public void onSetVolumeTo(int i) {
    }

    public void setCallback(Callback callback) {
        this.d = callback;
    }

    public final void setCurrentVolume(int i) {
        this.c = i;
        Object volumeProvider = getVolumeProvider();
        if (volumeProvider != null) {
            ((VolumeProvider) volumeProvider).setCurrentVolume(i);
        }
        Callback callback = this.d;
        if (callback != null) {
            MediaSessionCompat.e.a aVar = (MediaSessionCompat.e.a) callback;
            if (aVar.a.c != this) {
                return;
            }
            MediaSessionCompat.e eVar = aVar.a;
            aVar.a.a(new ParcelableVolumeInfo(eVar.a, eVar.b, getVolumeControl(), getMaxVolume(), getCurrentVolume()));
        }
    }
}
