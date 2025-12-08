package android.databinding;

import android.os.Parcel;
import android.os.Parcelable;
import defpackage.o1;
import java.io.Serializable;

/* loaded from: classes.dex */
public class ObservableDouble extends o1 implements Parcelable, Serializable {
    public static final Parcelable.Creator<ObservableDouble> CREATOR = new a();
    public static final long serialVersionUID = 1;
    public double b;

    public static class a implements Parcelable.Creator<ObservableDouble> {
        @Override // android.os.Parcelable.Creator
        public ObservableDouble createFromParcel(Parcel parcel) {
            return new ObservableDouble(parcel.readDouble());
        }

        @Override // android.os.Parcelable.Creator
        public ObservableDouble[] newArray(int i) {
            return new ObservableDouble[i];
        }
    }

    public ObservableDouble(double d) {
        this.b = d;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public double get() {
        return this.b;
    }

    public void set(double d) {
        if (d != this.b) {
            this.b = d;
            notifyChange();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeDouble(this.b);
    }

    public ObservableDouble() {
    }

    public ObservableDouble(Observable... observableArr) {
        super(observableArr);
    }
}
