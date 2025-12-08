package android.support.v4.media;

import android.media.AudioAttributes;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RestrictTo;
import android.util.Log;
import android.util.SparseIntArray;
import defpackage.c2;
import defpackage.g9;
import defpackage.o4;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

/* loaded from: classes.dex */
public class AudioAttributesCompat {
    public static final int CONTENT_TYPE_MOVIE = 3;
    public static final int CONTENT_TYPE_MUSIC = 2;
    public static final int CONTENT_TYPE_SONIFICATION = 4;
    public static final int CONTENT_TYPE_SPEECH = 1;
    public static final int CONTENT_TYPE_UNKNOWN = 0;
    public static final int FLAG_AUDIBILITY_ENFORCED = 1;
    public static final int FLAG_HW_AV_SYNC = 16;
    public static final int USAGE_ALARM = 4;
    public static final int USAGE_ASSISTANCE_ACCESSIBILITY = 11;
    public static final int USAGE_ASSISTANCE_NAVIGATION_GUIDANCE = 12;
    public static final int USAGE_ASSISTANCE_SONIFICATION = 13;
    public static final int USAGE_ASSISTANT = 16;
    public static final int USAGE_GAME = 14;
    public static final int USAGE_MEDIA = 1;
    public static final int USAGE_NOTIFICATION = 5;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_DELAYED = 9;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_INSTANT = 8;
    public static final int USAGE_NOTIFICATION_COMMUNICATION_REQUEST = 7;
    public static final int USAGE_NOTIFICATION_EVENT = 10;
    public static final int USAGE_NOTIFICATION_RINGTONE = 6;
    public static final int USAGE_UNKNOWN = 0;
    public static final int USAGE_VOICE_COMMUNICATION = 2;
    public static final int USAGE_VOICE_COMMUNICATION_SIGNALLING = 3;
    public static final SparseIntArray f;
    public static boolean g;
    public int a = 0;
    public int b = 0;
    public int c = 0;
    public Integer d;
    public o4 e;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface AttributeContentType {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface AttributeUsage {
    }

    static {
        SparseIntArray sparseIntArray = new SparseIntArray();
        f = sparseIntArray;
        sparseIntArray.put(5, 1);
        f.put(6, 2);
        f.put(7, 2);
        f.put(8, 1);
        f.put(9, 1);
        f.put(10, 1);
    }

    public AudioAttributesCompat() {
    }

    public static /* synthetic */ int a(int i) {
        switch (i) {
            case 0:
            case 6:
                return 2;
            case 1:
            case 7:
                return 13;
            case 2:
                return 6;
            case 3:
                return 1;
            case 4:
                return 4;
            case 5:
                return 5;
            case 8:
                return 3;
            case 9:
            default:
                return 0;
            case 10:
                return 11;
        }
    }

    public static int a(boolean z, int i, int i2) {
        if ((i & 1) == 1) {
            return z ? 1 : 7;
        }
        if ((i & 4) == 4) {
            return z ? 0 : 6;
        }
        switch (i2) {
            case 0:
                return z ? Integer.MIN_VALUE : 3;
            case 1:
            case 12:
            case 14:
            case 16:
                return 3;
            case 2:
                return 0;
            case 3:
                return z ? 0 : 8;
            case 4:
                return 4;
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
                return 5;
            case 6:
                return 2;
            case 11:
                return 10;
            case 13:
                return 1;
            case 15:
            default:
                if (!z) {
                    return 3;
                }
                throw new IllegalArgumentException("Unknown usage value " + i2 + " in audio attributes");
        }
    }

    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public static void setForceLegacyBehavior(boolean z) {
        g = z;
    }

    @Nullable
    public static AudioAttributesCompat wrap(@NonNull Object obj) {
        if (g) {
            return null;
        }
        AudioAttributesCompat audioAttributesCompat = new AudioAttributesCompat();
        AudioAttributes audioAttributes = (AudioAttributes) obj;
        if (audioAttributes == null) {
            throw new IllegalArgumentException("AudioAttributesApi21.Wrapper cannot wrap null");
        }
        audioAttributesCompat.e = new o4(audioAttributes);
        return audioAttributesCompat;
    }

    public boolean equals(Object obj) {
        o4 o4Var;
        if (this == obj) {
            return true;
        }
        if (obj == null || AudioAttributesCompat.class != obj.getClass()) {
            return false;
        }
        AudioAttributesCompat audioAttributesCompat = (AudioAttributesCompat) obj;
        if (!g && (o4Var = this.e) != null) {
            return o4Var.a.equals(audioAttributesCompat.unwrap());
        }
        if (this.b == audioAttributesCompat.getContentType() && this.c == audioAttributesCompat.getFlags() && this.a == audioAttributesCompat.getUsage()) {
            Integer num = this.d;
            if (num != null) {
                if (num.equals(audioAttributesCompat.d)) {
                    return true;
                }
            } else if (audioAttributesCompat.d == null) {
                return true;
            }
        }
        return false;
    }

    public int getContentType() {
        o4 o4Var;
        return (g || (o4Var = this.e) == null) ? this.b : o4Var.a.getContentType();
    }

    public int getFlags() {
        o4 o4Var;
        if (!g && (o4Var = this.e) != null) {
            return o4Var.a.getFlags();
        }
        int i = this.c;
        int legacyStreamType = getLegacyStreamType();
        if (legacyStreamType == 6) {
            i |= 4;
        } else if (legacyStreamType == 7) {
            i |= 1;
        }
        return i & 273;
    }

    public int getLegacyStreamType() {
        Integer num = this.d;
        if (num != null) {
            return num.intValue();
        }
        if (g) {
            return a(false, this.c, this.a);
        }
        AudioAttributes audioAttributes = this.e.a;
        try {
            if (c2.a == null) {
                c2.a = AudioAttributes.class.getMethod("toLegacyStreamType", AudioAttributes.class);
            }
            return ((Integer) c2.a.invoke(null, audioAttributes)).intValue();
        } catch (ClassCastException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Log.w("AudioAttributesCompat", "getLegacyStreamType() failed on API21+", e);
            return -1;
        }
    }

    public int getUsage() {
        o4 o4Var;
        return (g || (o4Var = this.e) == null) ? this.a : o4Var.a.getUsage();
    }

    public int getVolumeControlStream() {
        return (Build.VERSION.SDK_INT < 26 || g || unwrap() == null) ? a(true, getFlags(), getUsage()) : ((AudioAttributes) unwrap()).getVolumeControlStream();
    }

    public int hashCode() {
        o4 o4Var;
        return (g || (o4Var = this.e) == null) ? Arrays.hashCode(new Object[]{Integer.valueOf(this.b), Integer.valueOf(this.c), Integer.valueOf(this.a), this.d}) : o4Var.a.hashCode();
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder("AudioAttributesCompat:");
        if (unwrap() != null) {
            sb.append(" audioattributes=");
            sb.append(unwrap());
        } else {
            if (this.d != null) {
                sb.append(" stream=");
                sb.append(this.d);
                sb.append(" derived");
            }
            sb.append(" usage=");
            int i = this.a;
            switch (i) {
                case 0:
                    str = new String("USAGE_UNKNOWN");
                    break;
                case 1:
                    str = new String("USAGE_MEDIA");
                    break;
                case 2:
                    str = new String("USAGE_VOICE_COMMUNICATION");
                    break;
                case 3:
                    str = new String("USAGE_VOICE_COMMUNICATION_SIGNALLING");
                    break;
                case 4:
                    str = new String("USAGE_ALARM");
                    break;
                case 5:
                    str = new String("USAGE_NOTIFICATION");
                    break;
                case 6:
                    str = new String("USAGE_NOTIFICATION_RINGTONE");
                    break;
                case 7:
                    str = new String("USAGE_NOTIFICATION_COMMUNICATION_REQUEST");
                    break;
                case 8:
                    str = new String("USAGE_NOTIFICATION_COMMUNICATION_INSTANT");
                    break;
                case 9:
                    str = new String("USAGE_NOTIFICATION_COMMUNICATION_DELAYED");
                    break;
                case 10:
                    str = new String("USAGE_NOTIFICATION_EVENT");
                    break;
                case 11:
                    str = new String("USAGE_ASSISTANCE_ACCESSIBILITY");
                    break;
                case 12:
                    str = new String("USAGE_ASSISTANCE_NAVIGATION_GUIDANCE");
                    break;
                case 13:
                    str = new String("USAGE_ASSISTANCE_SONIFICATION");
                    break;
                case 14:
                    str = new String("USAGE_GAME");
                    break;
                case 15:
                default:
                    str = new String(g9.b("unknown usage ", i));
                    break;
                case 16:
                    str = new String("USAGE_ASSISTANT");
                    break;
            }
            sb.append(str);
            sb.append(" content=");
            sb.append(this.b);
            sb.append(" flags=0x");
            sb.append(Integer.toHexString(this.c).toUpperCase());
        }
        return sb.toString();
    }

    @Nullable
    public Object unwrap() {
        o4 o4Var = this.e;
        if (o4Var != null) {
            return o4Var.a;
        }
        return null;
    }

    public static class Builder {
        public int a;
        public int b;
        public int c;
        public Integer d;
        public Object e;

        public Builder() {
            this.a = 0;
            this.b = 0;
            this.c = 0;
        }

        public AudioAttributesCompat build() {
            if (AudioAttributesCompat.g) {
                AudioAttributesCompat audioAttributesCompat = new AudioAttributesCompat(null);
                audioAttributesCompat.b = this.b;
                audioAttributesCompat.c = this.c;
                audioAttributesCompat.a = this.a;
                audioAttributesCompat.d = this.d;
                audioAttributesCompat.e = null;
                return audioAttributesCompat;
            }
            Object obj = this.e;
            if (obj != null) {
                return AudioAttributesCompat.wrap(obj);
            }
            AudioAttributes.Builder usage = new AudioAttributes.Builder().setContentType(this.b).setFlags(this.c).setUsage(this.a);
            Integer num = this.d;
            if (num != null) {
                usage.setLegacyStreamType(num.intValue());
            }
            return AudioAttributesCompat.wrap(usage.build());
        }

        public Builder setContentType(int i) {
            if (i == 0 || i == 1 || i == 2 || i == 3 || i == 4) {
                this.b = i;
            } else {
                this.a = 0;
            }
            return this;
        }

        public Builder setFlags(int i) {
            this.c = (i & 1023) | this.c;
            return this;
        }

        public Builder setLegacyStreamType(int i) {
            if (i == 10) {
                throw new IllegalArgumentException("STREAM_ACCESSIBILITY is not a legacy stream type that was used for audio playback");
            }
            this.d = Integer.valueOf(i);
            this.a = AudioAttributesCompat.a(i);
            return this;
        }

        public Builder setUsage(int i) {
            switch (i) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                case 13:
                case 14:
                case 15:
                    this.a = i;
                    return this;
                case 16:
                    if (AudioAttributesCompat.g || Build.VERSION.SDK_INT <= 25) {
                        this.a = 12;
                    } else {
                        this.a = i;
                    }
                    return this;
                default:
                    this.a = 0;
                    return this;
            }
        }

        public Builder(AudioAttributesCompat audioAttributesCompat) {
            this.a = 0;
            this.b = 0;
            this.c = 0;
            this.a = audioAttributesCompat.a;
            this.b = audioAttributesCompat.b;
            this.c = audioAttributesCompat.c;
            this.d = audioAttributesCompat.d;
            this.e = audioAttributesCompat.unwrap();
        }
    }

    public /* synthetic */ AudioAttributesCompat(a aVar) {
    }
}
