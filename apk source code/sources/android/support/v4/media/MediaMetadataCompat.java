package android.support.v4.media;

import android.graphics.Bitmap;
import android.media.MediaMetadata;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.RestrictTo;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import defpackage.g9;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Set;

/* loaded from: classes.dex */
public final class MediaMetadataCompat implements Parcelable {
    public static final Parcelable.Creator<MediaMetadataCompat> CREATOR;
    public static final String METADATA_KEY_ADVERTISEMENT = "android.media.metadata.ADVERTISEMENT";
    public static final String METADATA_KEY_ALBUM = "android.media.metadata.ALBUM";
    public static final String METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART";
    public static final String METADATA_KEY_ALBUM_ARTIST = "android.media.metadata.ALBUM_ARTIST";
    public static final String METADATA_KEY_ALBUM_ART_URI = "android.media.metadata.ALBUM_ART_URI";
    public static final String METADATA_KEY_ART = "android.media.metadata.ART";
    public static final String METADATA_KEY_ARTIST = "android.media.metadata.ARTIST";
    public static final String METADATA_KEY_ART_URI = "android.media.metadata.ART_URI";
    public static final String METADATA_KEY_AUTHOR = "android.media.metadata.AUTHOR";
    public static final String METADATA_KEY_BT_FOLDER_TYPE = "android.media.metadata.BT_FOLDER_TYPE";
    public static final String METADATA_KEY_COMPILATION = "android.media.metadata.COMPILATION";
    public static final String METADATA_KEY_COMPOSER = "android.media.metadata.COMPOSER";
    public static final String METADATA_KEY_DATE = "android.media.metadata.DATE";
    public static final String METADATA_KEY_DISC_NUMBER = "android.media.metadata.DISC_NUMBER";
    public static final String METADATA_KEY_DISPLAY_DESCRIPTION = "android.media.metadata.DISPLAY_DESCRIPTION";
    public static final String METADATA_KEY_DISPLAY_ICON = "android.media.metadata.DISPLAY_ICON";
    public static final String METADATA_KEY_DISPLAY_ICON_URI = "android.media.metadata.DISPLAY_ICON_URI";
    public static final String METADATA_KEY_DISPLAY_SUBTITLE = "android.media.metadata.DISPLAY_SUBTITLE";
    public static final String METADATA_KEY_DISPLAY_TITLE = "android.media.metadata.DISPLAY_TITLE";
    public static final String METADATA_KEY_DOWNLOAD_STATUS = "android.media.metadata.DOWNLOAD_STATUS";
    public static final String METADATA_KEY_DURATION = "android.media.metadata.DURATION";
    public static final String METADATA_KEY_GENRE = "android.media.metadata.GENRE";
    public static final String METADATA_KEY_MEDIA_ID = "android.media.metadata.MEDIA_ID";
    public static final String METADATA_KEY_MEDIA_URI = "android.media.metadata.MEDIA_URI";
    public static final String METADATA_KEY_NUM_TRACKS = "android.media.metadata.NUM_TRACKS";
    public static final String METADATA_KEY_RATING = "android.media.metadata.RATING";
    public static final String METADATA_KEY_TITLE = "android.media.metadata.TITLE";
    public static final String METADATA_KEY_TRACK_NUMBER = "android.media.metadata.TRACK_NUMBER";
    public static final String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";
    public static final String METADATA_KEY_WRITER = "android.media.metadata.WRITER";
    public static final String METADATA_KEY_YEAR = "android.media.metadata.YEAR";
    public static final ArrayMap<String, Integer> d;
    public static final String[] e;
    public static final String[] f;
    public static final String[] g;
    public final Bundle a;
    public Object b;
    public MediaDescriptionCompat c;

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface BitmapKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface LongKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface RatingKey {
    }

    @Retention(RetentionPolicy.SOURCE)
    @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
    public @interface TextKey {
    }

    public static class a implements Parcelable.Creator<MediaMetadataCompat> {
        @Override // android.os.Parcelable.Creator
        public MediaMetadataCompat createFromParcel(Parcel parcel) {
            return new MediaMetadataCompat(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public MediaMetadataCompat[] newArray(int i) {
            return new MediaMetadataCompat[i];
        }
    }

    static {
        ArrayMap<String, Integer> arrayMap = new ArrayMap<>();
        d = arrayMap;
        arrayMap.put(METADATA_KEY_TITLE, 1);
        d.put(METADATA_KEY_ARTIST, 1);
        d.put(METADATA_KEY_DURATION, 0);
        d.put(METADATA_KEY_ALBUM, 1);
        d.put(METADATA_KEY_AUTHOR, 1);
        d.put(METADATA_KEY_WRITER, 1);
        d.put(METADATA_KEY_COMPOSER, 1);
        d.put(METADATA_KEY_COMPILATION, 1);
        d.put(METADATA_KEY_DATE, 1);
        d.put(METADATA_KEY_YEAR, 0);
        d.put(METADATA_KEY_GENRE, 1);
        d.put(METADATA_KEY_TRACK_NUMBER, 0);
        d.put(METADATA_KEY_NUM_TRACKS, 0);
        d.put(METADATA_KEY_DISC_NUMBER, 0);
        d.put(METADATA_KEY_ALBUM_ARTIST, 1);
        d.put(METADATA_KEY_ART, 2);
        d.put(METADATA_KEY_ART_URI, 1);
        d.put(METADATA_KEY_ALBUM_ART, 2);
        d.put(METADATA_KEY_ALBUM_ART_URI, 1);
        d.put(METADATA_KEY_USER_RATING, 3);
        d.put(METADATA_KEY_RATING, 3);
        d.put(METADATA_KEY_DISPLAY_TITLE, 1);
        d.put(METADATA_KEY_DISPLAY_SUBTITLE, 1);
        d.put(METADATA_KEY_DISPLAY_DESCRIPTION, 1);
        d.put(METADATA_KEY_DISPLAY_ICON, 2);
        d.put(METADATA_KEY_DISPLAY_ICON_URI, 1);
        d.put(METADATA_KEY_MEDIA_ID, 1);
        d.put(METADATA_KEY_BT_FOLDER_TYPE, 0);
        d.put(METADATA_KEY_MEDIA_URI, 1);
        d.put(METADATA_KEY_ADVERTISEMENT, 0);
        d.put(METADATA_KEY_DOWNLOAD_STATUS, 0);
        e = new String[]{METADATA_KEY_TITLE, METADATA_KEY_ARTIST, METADATA_KEY_ALBUM, METADATA_KEY_ALBUM_ARTIST, METADATA_KEY_WRITER, METADATA_KEY_AUTHOR, METADATA_KEY_COMPOSER};
        f = new String[]{METADATA_KEY_DISPLAY_ICON, METADATA_KEY_ART, METADATA_KEY_ALBUM_ART};
        g = new String[]{METADATA_KEY_DISPLAY_ICON_URI, METADATA_KEY_ART_URI, METADATA_KEY_ALBUM_ART_URI};
        CREATOR = new a();
    }

    public MediaMetadataCompat(Bundle bundle) {
        Bundle bundle2 = new Bundle(bundle);
        this.a = bundle2;
        bundle2.setClassLoader(MediaMetadataCompat.class.getClassLoader());
    }

    public static MediaMetadataCompat fromMediaMetadata(Object obj) {
        if (obj == null) {
            return null;
        }
        Parcel parcelObtain = Parcel.obtain();
        ((MediaMetadata) obj).writeToParcel(parcelObtain, 0);
        parcelObtain.setDataPosition(0);
        MediaMetadataCompat mediaMetadataCompatCreateFromParcel = CREATOR.createFromParcel(parcelObtain);
        parcelObtain.recycle();
        mediaMetadataCompatCreateFromParcel.b = obj;
        return mediaMetadataCompatCreateFromParcel;
    }

    public boolean containsKey(String str) {
        return this.a.containsKey(str);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public Bitmap getBitmap(String str) {
        try {
            return (Bitmap) this.a.getParcelable(str);
        } catch (Exception e2) {
            Log.w("MediaMetadata", "Failed to retrieve a key as Bitmap.", e2);
            return null;
        }
    }

    public Bundle getBundle() {
        return this.a;
    }

    public MediaDescriptionCompat getDescription() {
        Bitmap bitmap;
        Uri uri;
        MediaDescriptionCompat mediaDescriptionCompat = this.c;
        if (mediaDescriptionCompat != null) {
            return mediaDescriptionCompat;
        }
        String string = getString(METADATA_KEY_MEDIA_ID);
        CharSequence[] charSequenceArr = new CharSequence[3];
        CharSequence text = getText(METADATA_KEY_DISPLAY_TITLE);
        if (TextUtils.isEmpty(text)) {
            int i = 0;
            int i2 = 0;
            while (i < 3) {
                String[] strArr = e;
                if (i2 >= strArr.length) {
                    break;
                }
                int i3 = i2 + 1;
                CharSequence text2 = getText(strArr[i2]);
                if (!TextUtils.isEmpty(text2)) {
                    charSequenceArr[i] = text2;
                    i++;
                }
                i2 = i3;
            }
        } else {
            charSequenceArr[0] = text;
            charSequenceArr[1] = getText(METADATA_KEY_DISPLAY_SUBTITLE);
            charSequenceArr[2] = getText(METADATA_KEY_DISPLAY_DESCRIPTION);
        }
        int i4 = 0;
        while (true) {
            String[] strArr2 = f;
            if (i4 >= strArr2.length) {
                bitmap = null;
                break;
            }
            bitmap = getBitmap(strArr2[i4]);
            if (bitmap != null) {
                break;
            }
            i4++;
        }
        int i5 = 0;
        while (true) {
            String[] strArr3 = g;
            if (i5 >= strArr3.length) {
                uri = null;
                break;
            }
            String string2 = getString(strArr3[i5]);
            if (!TextUtils.isEmpty(string2)) {
                uri = Uri.parse(string2);
                break;
            }
            i5++;
        }
        String string3 = getString(METADATA_KEY_MEDIA_URI);
        Uri uri2 = TextUtils.isEmpty(string3) ? null : Uri.parse(string3);
        MediaDescriptionCompat.Builder builder = new MediaDescriptionCompat.Builder();
        builder.setMediaId(string);
        builder.setTitle(charSequenceArr[0]);
        builder.setSubtitle(charSequenceArr[1]);
        builder.setDescription(charSequenceArr[2]);
        builder.setIconBitmap(bitmap);
        builder.setIconUri(uri);
        builder.setMediaUri(uri2);
        Bundle bundle = new Bundle();
        if (this.a.containsKey(METADATA_KEY_BT_FOLDER_TYPE)) {
            bundle.putLong(MediaDescriptionCompat.EXTRA_BT_FOLDER_TYPE, getLong(METADATA_KEY_BT_FOLDER_TYPE));
        }
        if (this.a.containsKey(METADATA_KEY_DOWNLOAD_STATUS)) {
            bundle.putLong(MediaDescriptionCompat.EXTRA_DOWNLOAD_STATUS, getLong(METADATA_KEY_DOWNLOAD_STATUS));
        }
        if (!bundle.isEmpty()) {
            builder.setExtras(bundle);
        }
        MediaDescriptionCompat mediaDescriptionCompatBuild = builder.build();
        this.c = mediaDescriptionCompatBuild;
        return mediaDescriptionCompatBuild;
    }

    public long getLong(String str) {
        return this.a.getLong(str, 0L);
    }

    public Object getMediaMetadata() {
        if (this.b == null) {
            Parcel parcelObtain = Parcel.obtain();
            writeToParcel(parcelObtain, 0);
            parcelObtain.setDataPosition(0);
            this.b = MediaMetadata.CREATOR.createFromParcel(parcelObtain);
            parcelObtain.recycle();
        }
        return this.b;
    }

    public RatingCompat getRating(String str) {
        try {
            return RatingCompat.fromRating(this.a.getParcelable(str));
        } catch (Exception e2) {
            Log.w("MediaMetadata", "Failed to retrieve a key as Rating.", e2);
            return null;
        }
    }

    public String getString(String str) {
        CharSequence charSequence = this.a.getCharSequence(str);
        if (charSequence != null) {
            return charSequence.toString();
        }
        return null;
    }

    public CharSequence getText(String str) {
        return this.a.getCharSequence(str);
    }

    public Set<String> keySet() {
        return this.a.keySet();
    }

    public int size() {
        return this.a.size();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.a);
    }

    public static final class Builder {
        public final Bundle a;

        public Builder() {
            this.a = new Bundle();
        }

        public MediaMetadataCompat build() {
            return new MediaMetadataCompat(this.a);
        }

        public Builder putBitmap(String str, Bitmap bitmap) {
            if (MediaMetadataCompat.d.containsKey(str) && MediaMetadataCompat.d.get(str).intValue() != 2) {
                throw new IllegalArgumentException(g9.a("The ", str, " key cannot be used to put a Bitmap"));
            }
            this.a.putParcelable(str, bitmap);
            return this;
        }

        public Builder putLong(String str, long j) {
            if (MediaMetadataCompat.d.containsKey(str) && MediaMetadataCompat.d.get(str).intValue() != 0) {
                throw new IllegalArgumentException(g9.a("The ", str, " key cannot be used to put a long"));
            }
            this.a.putLong(str, j);
            return this;
        }

        public Builder putRating(String str, RatingCompat ratingCompat) {
            if (MediaMetadataCompat.d.containsKey(str) && MediaMetadataCompat.d.get(str).intValue() != 3) {
                throw new IllegalArgumentException(g9.a("The ", str, " key cannot be used to put a Rating"));
            }
            this.a.putParcelable(str, (Parcelable) ratingCompat.getRating());
            return this;
        }

        public Builder putString(String str, String str2) {
            if (MediaMetadataCompat.d.containsKey(str) && MediaMetadataCompat.d.get(str).intValue() != 1) {
                throw new IllegalArgumentException(g9.a("The ", str, " key cannot be used to put a String"));
            }
            this.a.putCharSequence(str, str2);
            return this;
        }

        public Builder putText(String str, CharSequence charSequence) {
            if (MediaMetadataCompat.d.containsKey(str) && MediaMetadataCompat.d.get(str).intValue() != 1) {
                throw new IllegalArgumentException(g9.a("The ", str, " key cannot be used to put a CharSequence"));
            }
            this.a.putCharSequence(str, charSequence);
            return this;
        }

        public Builder(MediaMetadataCompat mediaMetadataCompat) {
            this.a = new Bundle(mediaMetadataCompat.a);
        }

        @RestrictTo({RestrictTo.Scope.LIBRARY_GROUP})
        public Builder(MediaMetadataCompat mediaMetadataCompat, int i) {
            this(mediaMetadataCompat);
            for (String str : this.a.keySet()) {
                Object obj = this.a.get(str);
                if (obj instanceof Bitmap) {
                    Bitmap bitmap = (Bitmap) obj;
                    if (bitmap.getHeight() > i || bitmap.getWidth() > i) {
                        float f = i;
                        float fMin = Math.min(f / bitmap.getWidth(), f / bitmap.getHeight());
                        putBitmap(str, Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * fMin), (int) (bitmap.getHeight() * fMin), true));
                    }
                }
            }
        }
    }

    public MediaMetadataCompat(Parcel parcel) {
        Bundle bundle = parcel.readBundle();
        this.a = bundle;
        bundle.setClassLoader(MediaMetadataCompat.class.getClassLoader());
    }
}
