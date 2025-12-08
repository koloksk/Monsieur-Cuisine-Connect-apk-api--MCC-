package helper;

import android.os.IBinder;
import android.os.IEncryptionChipService;
import android.os.RemoteException;
import android.util.Base64;
import android.util.Log;
import defpackage.g9;
import helper.WifiHelper;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/* loaded from: classes.dex */
public class EncryptionChipServiceAdapter {
    public static EncryptionChipServiceAdapter c;
    public final Method a;
    public IEncryptionChipService b;

    public class a implements WifiHelper.NetworkStateListener {
        public a(EncryptionChipServiceAdapter encryptionChipServiceAdapter) {
        }

        @Override // helper.WifiHelper.NetworkStateListener
        public void networkStateChanged(String str, String str2) {
        }
    }

    public class b implements WifiHelper.WifiConnectionStateListener {
        public b(EncryptionChipServiceAdapter encryptionChipServiceAdapter) {
        }

        @Override // helper.WifiHelper.WifiConnectionStateListener
        public void connected() {
        }

        @Override // helper.WifiHelper.WifiConnectionStateListener
        public void establishingConnection() {
        }

        @Override // helper.WifiHelper.WifiConnectionStateListener
        public void failed() {
        }
    }

    public class c {
        public final int a;
        public final int b;

        public c(EncryptionChipServiceAdapter encryptionChipServiceAdapter, int i, int i2) {
            this.b = i;
            this.a = i2;
        }
    }

    public EncryptionChipServiceAdapter() throws NoSuchMethodException, SecurityException {
        Method method;
        try {
            method = Class.forName("android.os.ServiceManager").getMethod("getService", String.class);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
            method = null;
        }
        this.a = method;
    }

    public static EncryptionChipServiceAdapter getInstance() {
        if (c == null) {
            c = new EncryptionChipServiceAdapter();
        }
        return c;
    }

    public final String a(String str) {
        WifiHelper wifiHelper = WifiHelper.getInstance();
        if (wifiHelper == null) {
            wifiHelper = WifiHelper.getInstance(new a(this), new b(this));
        }
        String upperCase = wifiHelper.getMACAddress().toUpperCase();
        return upperCase.replace(":", "") + g9.a("0000", str, "0BAD");
    }

    public final synchronized IEncryptionChipService b() {
        if (this.b == null) {
            a();
        }
        return this.b;
    }

    public String[] getPublicKeyBase64() {
        IEncryptionChipService iEncryptionChipServiceB = b();
        if (iEncryptionChipServiceB == null) {
            return null;
        }
        try {
            if (iEncryptionChipServiceB.open() != 1) {
                return null;
            }
            byte[] publickey = iEncryptionChipServiceB.getPublickey();
            iEncryptionChipServiceB.close();
            return new String[]{Base64.encodeToString(publickey, 0, 32, 2), Base64.encodeToString(publickey, 32, 32, 2)};
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getPublicKeyHex() {
        IEncryptionChipService iEncryptionChipServiceB = b();
        if (iEncryptionChipServiceB == null) {
            return null;
        }
        try {
            if (iEncryptionChipServiceB.open() != 1) {
                return null;
            }
            byte[] publickey = iEncryptionChipServiceB.getPublickey();
            iEncryptionChipServiceB.close();
            return new String[]{a(publickey, new c(this, 0, 32)), a(publickey, new c(this, 32, 32))};
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getSESerial() {
        return getSESerial("-");
    }

    public String getSESerial(String str) {
        IEncryptionChipService iEncryptionChipServiceB = b();
        if (iEncryptionChipServiceB == null) {
            return "4C303B1700000065-0000";
        }
        try {
            int iOpen = iEncryptionChipServiceB.open();
            Log.i("EncryptionChipServiceAdapter", "open >> " + iOpen);
            if (iOpen != 1) {
                return "4C303B1700000065-0000";
            }
            byte[] id = iEncryptionChipServiceB.getId();
            Log.i("EncryptionChipServiceAdapter", "close >> " + iEncryptionChipServiceB.close());
            String strA = a(id, new c(this, 16, 8));
            String strA2 = a(id, new c(this, 24, 2));
            String str2 = strA + str + strA2;
            return (strA.equals("0000000000000000") && strA2.equals("0000")) ? a(str) : str2;
        } catch (Exception e) {
            e.printStackTrace();
            return a(str);
        }
    }

    public final synchronized void a() {
        Log.i("EncryptionChipServiceAdapter", "bindEncryptionChipService");
        try {
            Log.e("EncryptionChipServiceAdapter", "bind EncryptionChipService");
            IBinder iBinder = (IBinder) this.a.invoke(null, "EncryptionChipService");
            if (iBinder != null) {
                this.b = IEncryptionChipService.Stub.asInterface(iBinder);
            }
        } catch (IllegalAccessException | InvocationTargetException e) {
            Log.e("EncryptionChipServiceAdapter", "error occurred while binding EncryptionChipService", e);
        }
    }

    public final String a(byte[] bArr, c cVar) {
        if (bArr.length < cVar.b + cVar.a) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < cVar.a; i++) {
            sb.append(String.format("%02X", Byte.valueOf(bArr[cVar.b + i])));
        }
        StringBuilder sbA = g9.a("### (");
        sbA.append(cVar.b);
        sbA.append(" to ");
        sbA.append((cVar.b + cVar.a) - 1);
        sbA.append(") ");
        sbA.append(sb.toString());
        Log.v("EncryptionChipServiceAdapter", sbA.toString());
        return sb.toString();
    }
}
