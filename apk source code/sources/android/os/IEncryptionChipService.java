package android.os;

/* loaded from: classes.dex */
public interface IEncryptionChipService extends IInterface {

    public static abstract class Stub extends Binder implements IEncryptionChipService {

        public static class a implements IEncryptionChipService {
            public IBinder a;

            public a(IBinder iBinder) {
                this.a = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.a;
            }

            @Override // android.os.IEncryptionChipService
            public int close() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.os.IEncryptionChipService");
                    this.a.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IEncryptionChipService
            public byte[] computeEcdsa(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.os.IEncryptionChipService");
                    parcelObtain.writeByteArray(bArr);
                    this.a.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IEncryptionChipService
            public int generateKeypair() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.os.IEncryptionChipService");
                    this.a.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IEncryptionChipService
            public byte[] getId() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.os.IEncryptionChipService");
                    this.a.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IEncryptionChipService
            public byte[] getPublickey() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.os.IEncryptionChipService");
                    this.a.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IEncryptionChipService
            public int open() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.os.IEncryptionChipService");
                    this.a.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IEncryptionChipService
            public byte[] readCertificate() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.os.IEncryptionChipService");
                    this.a.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IEncryptionChipService
            public byte[] readSysPrivKey() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.os.IEncryptionChipService");
                    this.a.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IEncryptionChipService
            public byte[] readSysPubKeyX() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.os.IEncryptionChipService");
                    this.a.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IEncryptionChipService
            public byte[] readSysPubKeyY() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.os.IEncryptionChipService");
                    this.a.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IEncryptionChipService
            public int writeCertificate(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.os.IEncryptionChipService");
                    parcelObtain.writeByteArray(bArr);
                    this.a.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IEncryptionChipService
            public int writeSysPrivKey(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.os.IEncryptionChipService");
                    parcelObtain.writeByteArray(bArr);
                    this.a.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IEncryptionChipService
            public int writeSysPubKeyX(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.os.IEncryptionChipService");
                    parcelObtain.writeByteArray(bArr);
                    this.a.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.os.IEncryptionChipService
            public int writeSysPubKeyY(byte[] bArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain();
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken("android.os.IEncryptionChipService");
                    parcelObtain.writeByteArray(bArr);
                    this.a.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, "android.os.IEncryptionChipService");
        }

        public static IEncryptionChipService asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("android.os.IEncryptionChipService");
            return (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof IEncryptionChipService)) ? new a(iBinder) : (IEncryptionChipService) iInterfaceQueryLocalInterface;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i == 1598968902) {
                parcel2.writeString("android.os.IEncryptionChipService");
                return true;
            }
            switch (i) {
                case 1:
                    parcel.enforceInterface("android.os.IEncryptionChipService");
                    int iOpen = open();
                    parcel2.writeNoException();
                    parcel2.writeInt(iOpen);
                    return true;
                case 2:
                    parcel.enforceInterface("android.os.IEncryptionChipService");
                    int iClose = close();
                    parcel2.writeNoException();
                    parcel2.writeInt(iClose);
                    return true;
                case 3:
                    parcel.enforceInterface("android.os.IEncryptionChipService");
                    byte[] id = getId();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(id);
                    return true;
                case 4:
                    parcel.enforceInterface("android.os.IEncryptionChipService");
                    int iGenerateKeypair = generateKeypair();
                    parcel2.writeNoException();
                    parcel2.writeInt(iGenerateKeypair);
                    return true;
                case 5:
                    parcel.enforceInterface("android.os.IEncryptionChipService");
                    byte[] publickey = getPublickey();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(publickey);
                    return true;
                case 6:
                    parcel.enforceInterface("android.os.IEncryptionChipService");
                    int iWriteCertificate = writeCertificate(parcel.createByteArray());
                    parcel2.writeNoException();
                    parcel2.writeInt(iWriteCertificate);
                    return true;
                case 7:
                    parcel.enforceInterface("android.os.IEncryptionChipService");
                    byte[] certificate = readCertificate();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(certificate);
                    return true;
                case 8:
                    parcel.enforceInterface("android.os.IEncryptionChipService");
                    byte[] bArrComputeEcdsa = computeEcdsa(parcel.createByteArray());
                    parcel2.writeNoException();
                    parcel2.writeByteArray(bArrComputeEcdsa);
                    return true;
                case 9:
                    parcel.enforceInterface("android.os.IEncryptionChipService");
                    int iWriteSysPrivKey = writeSysPrivKey(parcel.createByteArray());
                    parcel2.writeNoException();
                    parcel2.writeInt(iWriteSysPrivKey);
                    return true;
                case 10:
                    parcel.enforceInterface("android.os.IEncryptionChipService");
                    byte[] sysPrivKey = readSysPrivKey();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(sysPrivKey);
                    return true;
                case 11:
                    parcel.enforceInterface("android.os.IEncryptionChipService");
                    int iWriteSysPubKeyX = writeSysPubKeyX(parcel.createByteArray());
                    parcel2.writeNoException();
                    parcel2.writeInt(iWriteSysPubKeyX);
                    return true;
                case 12:
                    parcel.enforceInterface("android.os.IEncryptionChipService");
                    byte[] sysPubKeyX = readSysPubKeyX();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(sysPubKeyX);
                    return true;
                case 13:
                    parcel.enforceInterface("android.os.IEncryptionChipService");
                    int iWriteSysPubKeyY = writeSysPubKeyY(parcel.createByteArray());
                    parcel2.writeNoException();
                    parcel2.writeInt(iWriteSysPubKeyY);
                    return true;
                case 14:
                    parcel.enforceInterface("android.os.IEncryptionChipService");
                    byte[] sysPubKeyY = readSysPubKeyY();
                    parcel2.writeNoException();
                    parcel2.writeByteArray(sysPubKeyY);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    int close() throws RemoteException;

    byte[] computeEcdsa(byte[] bArr) throws RemoteException;

    int generateKeypair() throws RemoteException;

    byte[] getId() throws RemoteException;

    byte[] getPublickey() throws RemoteException;

    int open() throws RemoteException;

    byte[] readCertificate() throws RemoteException;

    byte[] readSysPrivKey() throws RemoteException;

    byte[] readSysPubKeyX() throws RemoteException;

    byte[] readSysPubKeyY() throws RemoteException;

    int writeCertificate(byte[] bArr) throws RemoteException;

    int writeSysPrivKey(byte[] bArr) throws RemoteException;

    int writeSysPubKeyX(byte[] bArr) throws RemoteException;

    int writeSysPubKeyY(byte[] bArr) throws RemoteException;
}
