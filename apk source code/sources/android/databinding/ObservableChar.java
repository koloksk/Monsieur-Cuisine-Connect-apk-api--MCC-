package android.databinding;

import android.os.Parcel;
import android.os.Parcelable;
import defpackage.o1;
import java.io.Serializable;

/* loaded from: classes.dex */
public class ObservableChar extends o1 implements Parcelable, Serializable {
    public static final Parcelable.Creator<ObservableChar> CREATOR = new a();
    public static final long serialVersionUID = 1;
    public char b;

    public static class a implements Parcelable.Creator<ObservableChar> {
        @Override // android.os.Parcelable.Creator
        public ObservableChar createFromParcel(Parcel parcel) {
            return new ObservableChar((char) parcel.readInt());
        }

        @Override // android.os.Parcelable.Creator
        public ObservableChar[] newArray(int i) {
            return new ObservableChar[i];
        }
    }

    public ObservableChar(char c) {
        this.b = c;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public char get() {
        return this.b;
    }

    public void set(char c) {
        if (c != this.b) {
            this.b = c;
            notifyChange();
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.b);
    }

    public ObservableChar() {
    }

    public ObservableChar(Observable... observableArr) {
        super(observableArr);
    }
}
