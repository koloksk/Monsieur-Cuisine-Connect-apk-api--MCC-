package services;

import android.os.IBinder;
import android.os.IEncryptionChipService;
import android.os.RemoteException;

/* loaded from: classes.dex */
public class MockEncryptionChipService implements IEncryptionChipService {
    @Override // android.os.IInterface
    public IBinder asBinder() {
        return null;
    }

    @Override // android.os.IEncryptionChipService
    public int close() throws RemoteException {
        return 0;
    }

    @Override // android.os.IEncryptionChipService
    public byte[] computeEcdsa(byte[] bArr) throws RemoteException {
        return new byte[0];
    }

    @Override // android.os.IEncryptionChipService
    public int generateKeypair() throws RemoteException {
        return 0;
    }

    @Override // android.os.IEncryptionChipService
    public byte[] getId() throws RemoteException {
        return new byte[0];
    }

    @Override // android.os.IEncryptionChipService
    public byte[] getPublickey() throws RemoteException {
        return new byte[0];
    }

    @Override // android.os.IEncryptionChipService
    public int open() throws RemoteException {
        return 0;
    }

    @Override // android.os.IEncryptionChipService
    public byte[] readCertificate() throws RemoteException {
        return new byte[0];
    }

    @Override // android.os.IEncryptionChipService
    public byte[] readSysPrivKey() throws RemoteException {
        return new byte[0];
    }

    @Override // android.os.IEncryptionChipService
    public byte[] readSysPubKeyX() throws RemoteException {
        return new byte[0];
    }

    @Override // android.os.IEncryptionChipService
    public byte[] readSysPubKeyY() throws RemoteException {
        return new byte[0];
    }

    @Override // android.os.IEncryptionChipService
    public int writeCertificate(byte[] bArr) throws RemoteException {
        return 0;
    }

    @Override // android.os.IEncryptionChipService
    public int writeSysPrivKey(byte[] bArr) throws RemoteException {
        return 0;
    }

    @Override // android.os.IEncryptionChipService
    public int writeSysPubKeyX(byte[] bArr) throws RemoteException {
        return 0;
    }

    @Override // android.os.IEncryptionChipService
    public int writeSysPubKeyY(byte[] bArr) throws RemoteException {
        return 0;
    }
}
