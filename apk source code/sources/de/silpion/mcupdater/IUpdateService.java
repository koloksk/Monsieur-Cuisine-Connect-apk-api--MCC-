package de.silpion.mcupdater;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import de.silpion.mcupdater.IStateCallback;

/* loaded from: classes.dex */
public interface IUpdateService extends IInterface {

    public static abstract class Stub extends Binder implements IUpdateService {

        public static class a implements IUpdateService {
            public IBinder a;

            public a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // de.silpion.mcupdater.IUpdateService
            public int getUpdaterState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("de.silpion.mcupdater.IUpdateService");
                    this.a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // de.silpion.mcupdater.IUpdateService
            public void registerStateCallback(IStateCallback iStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("de.silpion.mcupdater.IUpdateService");
                    parcelObtain.writeStrongBinder(iStateCallback != null ? iStateCallback.asBinder() : null);
                    this.a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // de.silpion.mcupdater.IUpdateService
            public void startCheck() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("de.silpion.mcupdater.IUpdateService");
                    this.a.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // de.silpion.mcupdater.IUpdateService
            public void unregisterStateCallback(IStateCallback iStateCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("de.silpion.mcupdater.IUpdateService");
                    parcelObtain.writeStrongBinder(iStateCallback != null ? iStateCallback.asBinder() : null);
                    this.a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "de.silpion.mcupdater.IUpdateService");
        }

        public static IUpdateService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("de.silpion.mcupdater.IUpdateService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IUpdateService)) ? new a(iBinder) : (IUpdateService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("de.silpion.mcupdater.IUpdateService");
                registerStateCallback(IStateCallback.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface("de.silpion.mcupdater.IUpdateService");
                unregisterStateCallback(IStateCallback.Stub.asInterface(parcel.readStrongBinder()));
                parcel2.writeNoException();
                return true;
            }
            if (i == 3) {
                parcel.enforceInterface("de.silpion.mcupdater.IUpdateService");
                int updaterState = getUpdaterState();
                parcel2.writeNoException();
                parcel2.writeInt(updaterState);
                return true;
            }
            if (i != 4) {
                if (i != 1598968902) {
                    return super.onTransact(i, parcel, parcel2, i2);
                }
                parcel2.writeString("de.silpion.mcupdater.IUpdateService");
                return true;
            }
            parcel.enforceInterface("de.silpion.mcupdater.IUpdateService");
            startCheck();
            parcel2.writeNoException();
            return true;
        }
    }

    int getUpdaterState() throws RemoteException;

    void registerStateCallback(IStateCallback iStateCallback) throws RemoteException;

    void startCheck() throws RemoteException;

    void unregisterStateCallback(IStateCallback iStateCallback) throws RemoteException;
}
