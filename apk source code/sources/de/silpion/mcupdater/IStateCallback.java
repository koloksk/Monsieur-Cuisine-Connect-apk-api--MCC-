package de.silpion.mcupdater;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

/* loaded from: classes.dex */
public interface IStateCallback extends IInterface {

    public static abstract class Stub extends Binder implements IStateCallback {

        public static class a implements IStateCallback {
            public IBinder a;

            public a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // de.silpion.mcupdater.IStateCallback
            public void onDownloadProgress(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("de.silpion.mcupdater.IStateCallback");
                    parcelObtain.writeInt(i);
                    this.a.transact(2, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // de.silpion.mcupdater.IStateCallback
            public void onStateChanged(int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("de.silpion.mcupdater.IStateCallback");
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.a.transact(1, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "de.silpion.mcupdater.IStateCallback");
        }

        public static IStateCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("de.silpion.mcupdater.IStateCallback");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IStateCallback)) ? new a(iBinder) : (IStateCallback) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1) {
                parcel.enforceInterface("de.silpion.mcupdater.IStateCallback");
                onStateChanged(parcel.readInt(), parcel.readInt());
                return true;
            }
            if (i == 2) {
                parcel.enforceInterface("de.silpion.mcupdater.IStateCallback");
                onDownloadProgress(parcel.readInt());
                return true;
            }
            if (i != 1598968902) {
                return super.onTransact(i, parcel, parcel2, i2);
            }
            parcel2.writeString("de.silpion.mcupdater.IStateCallback");
            return true;
        }
    }

    void onDownloadProgress(int i) throws RemoteException;

    void onStateChanged(int i, int i2) throws RemoteException;
}
