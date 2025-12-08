package defpackage;

import android.media.VolumeProvider;
import android.support.v4.media.VolumeProviderCompatApi21$Delegate;

/* loaded from: classes.dex */
public final class l5 extends VolumeProvider {
    public final /* synthetic */ VolumeProviderCompatApi21$Delegate a;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public l5(int i, int i2, int i3, VolumeProviderCompatApi21$Delegate volumeProviderCompatApi21$Delegate) {
        super(i, i2, i3);
        this.a = volumeProviderCompatApi21$Delegate;
    }

    @Override // android.media.VolumeProvider
    public void onAdjustVolume(int i) {
        this.a.onAdjustVolume(i);
    }

    @Override // android.media.VolumeProvider
    public void onSetVolumeTo(int i) {
        this.a.onSetVolumeTo(i);
    }
}
