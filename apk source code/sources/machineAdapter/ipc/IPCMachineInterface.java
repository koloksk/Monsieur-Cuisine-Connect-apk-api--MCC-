package machineAdapter.ipc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IPCMachineInterface extends IInterface {

    public static abstract class Stub extends Binder implements IPCMachineInterface {

        public static class a implements IPCMachineInterface {
            public IBinder a;

            public a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // machineAdapter.ipc.IPCMachineInterface
            public int getFirmwareVersion() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("machineAdapter.ipc.IPCMachineInterface");
                    this.a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // machineAdapter.ipc.IPCMachineInterface
            public int getPid() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("machineAdapter.ipc.IPCMachineInterface");
                    this.a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // machineAdapter.ipc.IPCMachineInterface
            public DeviceState requestDeviceState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("machineAdapter.ipc.IPCMachineInterface");
                    this.a.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt() != 0 ? DeviceState.CREATOR.createFromParcel(parcelObtain2) : null;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // machineAdapter.ipc.IPCMachineInterface
            public void requestScaleCalibrate(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("machineAdapter.ipc.IPCMachineInterface");
                    parcelObtain.writeInt(i);
                    this.a.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // machineAdapter.ipc.IPCMachineInterface
            public void requestScaleTare() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("machineAdapter.ipc.IPCMachineInterface");
                    this.a.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // machineAdapter.ipc.IPCMachineInterface
            public void requestStart(int i, int i2, int i3, int i4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("machineAdapter.ipc.IPCMachineInterface");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeInt(i3);
                    parcelObtain.writeInt(i4);
                    this.a.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // machineAdapter.ipc.IPCMachineInterface
            public void requestStop() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("machineAdapter.ipc.IPCMachineInterface");
                    this.a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "machineAdapter.ipc.IPCMachineInterface");
        }

        public static IPCMachineInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("machineAdapter.ipc.IPCMachineInterface");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IPCMachineInterface)) ? new a(iBinder) : (IPCMachineInterface) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString("machineAdapter.ipc.IPCMachineInterface");
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface("machineAdapter.ipc.IPCMachineInterface");
                    int pid = getPid();
                    parcel2.writeNoException();
                    parcel2.writeInt(pid);
                    return true;
                case 2:
                    parcel.enforceInterface("machineAdapter.ipc.IPCMachineInterface");
                    int firmwareVersion = getFirmwareVersion();
                    parcel2.writeNoException();
                    parcel2.writeInt(firmwareVersion);
                    return true;
                case 3:
                    parcel.enforceInterface("machineAdapter.ipc.IPCMachineInterface");
                    requestStop();
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface("machineAdapter.ipc.IPCMachineInterface");
                    requestStart(parcel.readInt(), parcel.readInt(), parcel.readInt(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface("machineAdapter.ipc.IPCMachineInterface");
                    requestScaleTare();
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface("machineAdapter.ipc.IPCMachineInterface");
                    requestScaleCalibrate(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface("machineAdapter.ipc.IPCMachineInterface");
                    DeviceState deviceStateRequestDeviceState = requestDeviceState();
                    parcel2.writeNoException();
                    if (deviceStateRequestDeviceState != null) {
                        parcel2.writeInt(1);
                        deviceStateRequestDeviceState.writeToParcel(parcel2, 1);
                    } else {
                        parcel2.writeInt(0);
                    }
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    int getFirmwareVersion() throws RemoteException;

    int getPid() throws RemoteException;

    DeviceState requestDeviceState() throws RemoteException;

    void requestScaleCalibrate(int i) throws RemoteException;

    void requestScaleTare() throws RemoteException;

    void requestStart(int i, int i2, int i3, int i4) throws RemoteException;

    void requestStop() throws RemoteException;
}
