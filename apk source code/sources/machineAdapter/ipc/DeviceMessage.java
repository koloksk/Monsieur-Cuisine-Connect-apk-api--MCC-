package machineAdapter.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class DeviceMessage implements Parcelable {
    public static final Parcelable.Creator<DeviceMessage> CREATOR = new a();
    public String changeType;
    public String component;
    public long errorCode;
    public String newState;
    public long newValue;
    public String valueKey;

    public static class a implements Parcelable.Creator<DeviceMessage> {
        @Override // android.os.Parcelable.Creator
        public DeviceMessage createFromParcel(Parcel parcel) {
            return new DeviceMessage(parcel);
        }

        @Override // android.os.Parcelable.Creator
        public DeviceMessage[] newArray(int i) {
            return new DeviceMessage[i];
        }
    }

    public DeviceMessage() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void readFromParcel(Parcel parcel) {
        this.errorCode = parcel.readLong();
        this.component = parcel.readString();
        this.changeType = parcel.readString();
        this.newState = parcel.readString();
        this.valueKey = parcel.readString();
        this.newValue = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.errorCode);
        parcel.writeString(this.component);
        parcel.writeString(this.changeType);
        parcel.writeString(this.newState);
        parcel.writeString(this.valueKey);
        parcel.writeLong(this.newValue);
    }

    public DeviceMessage(Parcel parcel) {
        this();
        readFromParcel(parcel);
    }
}
