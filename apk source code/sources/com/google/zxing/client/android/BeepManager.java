package com.google.zxing.client.android;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.util.Log;
import java.io.IOException;

/* loaded from: classes.dex */
public final class BeepManager {
    public final Context a;
    public boolean b = true;
    public boolean c = false;

    public class a implements MediaPlayer.OnCompletionListener {
        public a(BeepManager beepManager) {
        }

        @Override // android.media.MediaPlayer.OnCompletionListener
        public void onCompletion(MediaPlayer mediaPlayer) throws IllegalStateException {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    public class b implements MediaPlayer.OnErrorListener {
        public b(BeepManager beepManager) {
        }

        @Override // android.media.MediaPlayer.OnErrorListener
        public boolean onError(MediaPlayer mediaPlayer, int i, int i2) throws IllegalStateException {
            Log.w("BeepManager", "Failed to beep " + i + ", " + i2);
            mediaPlayer.stop();
            mediaPlayer.release();
            return true;
        }
    }

    public BeepManager(Activity activity2) {
        activity2.setVolumeControlStream(3);
        this.a = activity2.getApplicationContext();
    }

    public boolean isBeepEnabled() {
        return this.b;
    }

    public boolean isVibrateEnabled() {
        return this.c;
    }

    public MediaPlayer playBeepSound() throws IllegalStateException, Resources.NotFoundException, IOException {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(3);
        mediaPlayer.setOnCompletionListener(new a(this));
        mediaPlayer.setOnErrorListener(new b(this));
        try {
            AssetFileDescriptor assetFileDescriptorOpenRawResourceFd = this.a.getResources().openRawResourceFd(R.raw.zxing_beep);
            try {
                mediaPlayer.setDataSource(assetFileDescriptorOpenRawResourceFd.getFileDescriptor(), assetFileDescriptorOpenRawResourceFd.getStartOffset(), assetFileDescriptorOpenRawResourceFd.getLength());
                assetFileDescriptorOpenRawResourceFd.close();
                mediaPlayer.setVolume(0.1f, 0.1f);
                mediaPlayer.prepare();
                mediaPlayer.start();
                return mediaPlayer;
            } catch (Throwable th) {
                assetFileDescriptorOpenRawResourceFd.close();
                throw th;
            }
        } catch (IOException e) {
            Log.w("BeepManager", e);
            mediaPlayer.release();
            return null;
        }
    }

    public synchronized void playBeepSoundAndVibrate() {
        if (this.b) {
            playBeepSound();
        }
        if (this.c) {
            ((Vibrator) this.a.getSystemService("vibrator")).vibrate(200L);
        }
    }

    public void setBeepEnabled(boolean z) {
        this.b = z;
    }

    public void setVibrateEnabled(boolean z) {
        this.c = z;
    }
}
