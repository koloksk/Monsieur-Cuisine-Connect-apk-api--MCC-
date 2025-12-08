package android.databinding;

import android.os.Parcel;
import android.os.Parcelable;
import defpackage.o1;
import java.io.Serializable;

/* loaded from: classes.dex */
public class ObservableFloat extends o1 implements Parcelable, Serializable {
    public static final Parcelable.Creator<ObservableFloat> CREATOR = new a();
    public static final long serialVersionUID = 1;
    public float b;

    public static class a implements Parcelable.Creator<ObservableFloat> {
        @Override // android.os.Parcelable.Creator
        public ObservableFloat createFromParcel(Parcel parcel) {
            return new ObservableFloat(parcel.readFloat());
        }

        @Override // android.os.Parcelable.Creator
        public ObservableFloat[] newArray(int i) {
            return new ObservableFloat[i];
        }
    }

    public ObservableFloat(float f) {
        this.b = f;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public float get() {
        return this.b;
    }

    public void set(float f) {
        if (f != this.b) {
            this.b = f;
            notifyChange();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.b);
    }

    public ObservableFloat() {
    }

    public ObservableFloat(Observable... observableArr) {
        super(observableArr);
    }
}
