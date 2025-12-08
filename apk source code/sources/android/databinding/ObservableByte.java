package android.databinding;

import android.os.Parcel;
import android.os.Parcelable;
import defpackage.o1;
import java.io.Serializable;

/* loaded from: classes.dex */
public class ObservableByte extends o1 implements Parcelable, Serializable {
    public static final Parcelable.Creator<ObservableByte> CREATOR = new a();
    public static final long serialVersionUID = 1;
    public byte b;

    public static class a implements Parcelable.Creator<ObservableByte> {
        @Override // android.os.Parcelable.Creator
        public ObservableByte createFromParcel(Parcel parcel) {
            return new ObservableByte(parcel.readByte());
        }

        @Override // android.os.Parcelable.Creator
        public ObservableByte[] newArray(int i) {
            return new ObservableByte[i];
        }
    }

    public ObservableByte(byte b) {
        this.b = b;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public byte get() {
        return this.b;
    }

    public void set(byte b) {
        if (b != this.b) {
            this.b = b;
            notifyChange();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.b);
    }

    public ObservableByte() {
    }

    public ObservableByte(Observable... observableArr) {
        super(observableArr);
    }
}
