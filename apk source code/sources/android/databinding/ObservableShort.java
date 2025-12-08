package android.databinding;

import android.os.Parcel;
import android.os.Parcelable;
import defpackage.o1;
import java.io.Serializable;

/* loaded from: classes.dex */
public class ObservableShort extends o1 implements Parcelable, Serializable {
    public static final Parcelable.Creator<ObservableShort> CREATOR = new a();
    public static final long serialVersionUID = 1;
    public short b;

    public static class a implements Parcelable.Creator<ObservableShort> {
        @Override // android.os.Parcelable.Creator
        public ObservableShort createFromParcel(Parcel parcel) {
            return new ObservableShort((short) parcel.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public ObservableShort[] newArray(int i) {
            return new ObservableShort[i];
        }
    }

    public ObservableShort(short s) {
        this.b = s;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public short get() {
        return this.b;
    }

    public void set(short s) {
        if (s != this.b) {
            this.b = s;
            notifyChange();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.b);
    }

    public ObservableShort() {
    }

    public ObservableShort(Observable... observableArr) {
        super(observableArr);
    }
}
