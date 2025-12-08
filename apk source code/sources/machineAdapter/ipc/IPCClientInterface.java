package machineAdapter.ipc;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IPCClientInterface extends IInterface {

    public static abstract class Stub extends Binder implements IPCClientInterface {

        public static class a implements IPCClientInterface {
            public IBinder a;

            public a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // machineAdapter.ipc.IPCClientInterface
            public int getPid() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("machineAdapter.ipc.IPCClientInterface");
                    this.a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // machineAdapter.ipc.IPCClientInterface
            public void onBadState(int[] iArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("machineAdapter.ipc.IPCClientInterface");
                    parcelObtain.writeIntArray(iArr);
                    this.a.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // machineAdapter.ipc.IPCClientInterface
            public void onCookingTimeExpired() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("machineAdapter.ipc.IPCClientInterface");
                    this.a.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // machineAdapter.ipc.IPCClientInterface
            public void onLidStateChanged(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("machineAdapter.ipc.IPCClientInterface");
                    parcelObtain.writeInt(i);
                    this.a.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // machineAdapter.ipc.IPCClientInterface
            public void onMotorStateChanged(int i, int i2, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("machineAdapter.ipc.IPCClientInterface");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeLong(j);
                    this.a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // machineAdapter.ipc.IPCClientInterface
            public void onScaleStateChanged(int i, long j, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("machineAdapter.ipc.IPCClientInterface");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i2);
                    this.a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // machineAdapter.ipc.IPCClientInterface
            public void onTemperatureChanged(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("machineAdapter.ipc.IPCClientInterface");
                    parcelObtain.writeLong(j);
                    this.a.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "machineAdapter.ipc.IPCClientInterface");
        }

        public static IPCClientInterface asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("machineAdapter.ipc.IPCClientInterface");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IPCClientInterface)) ? new a(iBinder) : (IPCClientInterface) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString("machineAdapter.ipc.IPCClientInterface");
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface("machineAdapter.ipc.IPCClientInterface");
                    int pid = getPid();
                    parcel2.writeNoException();
                    parcel2.writeInt(pid);
                    return true;
                case 2:
                    parcel.enforceInterface("machineAdapter.ipc.IPCClientInterface");
                    onMotorStateChanged(parcel.readInt(), parcel.readInt(), parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                case 3:
                    parcel.enforceInterface("machineAdapter.ipc.IPCClientInterface");
                    onScaleStateChanged(parcel.readInt(), parcel.readLong(), parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 4:
                    parcel.enforceInterface("machineAdapter.ipc.IPCClientInterface");
                    onTemperatureChanged(parcel.readLong());
                    parcel2.writeNoException();
                    return true;
                case 5:
                    parcel.enforceInterface("machineAdapter.ipc.IPCClientInterface");
                    onLidStateChanged(parcel.readInt());
                    parcel2.writeNoException();
                    return true;
                case 6:
                    parcel.enforceInterface("machineAdapter.ipc.IPCClientInterface");
                    onCookingTimeExpired();
                    parcel2.writeNoException();
                    return true;
                case 7:
                    parcel.enforceInterface("machineAdapter.ipc.IPCClientInterface");
                    onBadState(parcel.createIntArray());
                    parcel2.writeNoException();
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    int getPid() throws RemoteException;

    void onBadState(int[] iArr) throws RemoteException;

    void onCookingTimeExpired() throws RemoteException;

    void onLidStateChanged(int i) throws RemoteException;

    void onMotorStateChanged(int i, int i2, long j) throws RemoteException;

    void onScaleStateChanged(int i, long j, int i2) throws RemoteException;

    void onTemperatureChanged(long j) throws RemoteException;
}
