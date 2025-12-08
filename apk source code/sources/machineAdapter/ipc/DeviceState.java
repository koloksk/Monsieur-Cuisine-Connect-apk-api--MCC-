package machineAdapter.ipc;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes.dex */
public class DeviceState implements Parcelable {
    public static final Parcelable.Creator<DeviceState> CREATOR = new a();
    public long durationLeft;
    public int errorState;
    public int heatingState;
    public int lidState;
    public int motorDirection;
    public long motorSpeedLevel;
    public int motorState;
    public long scaleMeasureValue;
    public int scaleState;
    public long temperature;

    public static class a implements Parcelable.Creator<DeviceState> {
        @Override // android.os.Parcelable.Creator
        public DeviceState createFromParcel(Parcel parcel) {
            return new DeviceState(parcel, null);
        }

        @Override // android.os.Parcelable.Creator
        public DeviceState[] newArray(int i) {
            return new DeviceState[i];
        }
    }

    public DeviceState() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.motorState);
        parcel.writeInt(this.motorDirection);
        parcel.writeLong(this.motorSpeedLevel);
        parcel.writeInt(this.scaleState);
        parcel.writeLong(this.scaleMeasureValue);
        parcel.writeInt(this.heatingState);
        parcel.writeLong(this.temperature);
        parcel.writeInt(this.lidState);
        parcel.writeLong(this.durationLeft);
        parcel.writeInt(this.errorState);
    }

    public /* synthetic */ DeviceState(Parcel parcel, a aVar) {
        this();
        this.motorState = parcel.readInt();
        this.motorDirection = parcel.readInt();
        this.motorSpeedLevel = parcel.readLong();
        this.scaleState = parcel.readInt();
        this.scaleMeasureValue = parcel.readLong();
        this.heatingState = parcel.readInt();
        this.temperature = parcel.readLong();
        this.lidState = parcel.readInt();
        this.durationLeft = parcel.readLong();
        this.errorState = parcel.readInt();
    }
}
