package defpackage;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import application.App;
import de.silpion.mc2.R;
import helper.SharedPreferencesHelper;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import mcapi.HttpResponseListener;
import mcapi.UnauthorizedException;

/* loaded from: classes.dex */
public abstract class pm {
    public ConnectivityManager a;

    public static class b extends d {
        public final String e;
        public final HttpResponseListener f;
        public final String g;

        public b(String str, String str2, String str3, @NonNull HttpResponseListener httpResponseListener) {
            super(null);
            this.e = str;
            this.f = httpResponseListener;
            this.g = str2;
            b("X-Auth-Key", str3);
        }

        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void[] voidArr) {
            InputStream inputStream;
            HttpsURLConnection httpsURLConnectionA = a(this.e, this.g);
            boolean z = false;
            try {
                if (httpsURLConnectionA != null) {
                    try {
                        httpsURLConnectionA.setRequestMethod("DELETE");
                        httpsURLConnectionA.setDoInput(true);
                        Log.i("pm", "DELETE >> " + this.g);
                        httpsURLConnectionA.connect();
                        Log.i("pm", "DELETE <<< " + httpsURLConnectionA.getResponseMessage() + " [" + httpsURLConnectionA.getResponseCode() + "]");
                        inputStream = null;
                    } catch (IOException e) {
                        Log.e("pm", "Failed to DELETE", e);
                        SharedPreferencesHelper.getInstance().incrementFailedRequestCounter();
                        this.f.failure(e);
                    }
                    try {
                        inputStream = httpsURLConnectionA.getInputStream();
                        this.f.receivedResponse(httpsURLConnectionA.getResponseCode(), httpsURLConnectionA.getResponseMessage(), new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)));
                        z = true;
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                }
                return z;
            } finally {
                httpsURLConnectionA.disconnect();
            }
        }
    }

    public static class c extends d {
        public final String e;
        public final HttpResponseListener f;
        public final String g;

        public c(String str, String str2, String str3, Date date, Map<String, String> map, Integer num, @NonNull HttpResponseListener httpResponseListener) {
            super(0 == true ? 1 : 0);
            this.e = str;
            this.f = httpResponseListener;
            this.g = str2;
            b("X-Auth-Key", str3);
            this.b = date != null ? Long.valueOf(date.getTime()) : null;
            a(map);
            this.c = num;
        }

        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void[] voidArr) {
            InputStream inputStream;
            HttpsURLConnection httpsURLConnectionA = a(this.e, this.g);
            boolean z = false;
            try {
                if (httpsURLConnectionA != null) {
                    try {
                        httpsURLConnectionA.setRequestMethod("GET");
                        httpsURLConnectionA.setDoInput(true);
                        Log.i("pm", "GET >> " + this.g);
                        httpsURLConnectionA.connect();
                        Log.i("pm", "GET <<< " + httpsURLConnectionA.getResponseMessage() + " [" + httpsURLConnectionA.getResponseCode() + "]");
                        inputStream = null;
                    } catch (IOException e) {
                        Log.e("pm", "Failed to GET", e);
                        SharedPreferencesHelper.getInstance().incrementFailedRequestCounter();
                        this.f.failure(e);
                    }
                    try {
                        inputStream = httpsURLConnectionA.getInputStream();
                        this.f.receivedResponse(httpsURLConnectionA.getResponseCode(), httpsURLConnectionA.getResponseMessage(), new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8)));
                        z = true;
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                }
                return z;
            } finally {
                httpsURLConnectionA.disconnect();
            }
        }
    }

    public static class e extends d {
        public final String e;
        public final String f;
        public final HttpResponseListener g;
        public final String h;

        public e(String str, String str2, String str3, String str4, Map<String, String> map, @NonNull HttpResponseListener httpResponseListener) {
            super(null);
            this.e = str;
            this.f = str4;
            this.g = httpResponseListener;
            this.h = str2;
            b("X-Auth-Key", str3);
            a(map);
        }

        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void[] voidArr) {
            InputStream inputStream;
            BufferedReader bufferedReader;
            HttpsURLConnection httpsURLConnectionA = a(this.e, this.h);
            boolean z = false;
            try {
                if (httpsURLConnectionA != null) {
                    try {
                        httpsURLConnectionA.setRequestMethod("POST");
                        httpsURLConnectionA.setDoOutput(true);
                        httpsURLConnectionA.setDoInput(true);
                        httpsURLConnectionA.setUseCaches(false);
                        httpsURLConnectionA.setRequestProperty("Connection", "Keep-Alive");
                        httpsURLConnectionA.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                        httpsURLConnectionA.setRequestProperty("Accept", "application/json");
                        Log.i("pm", "POSTJSON >> " + this.h + " >> data " + this.f);
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpsURLConnectionA.getOutputStream(), StandardCharsets.UTF_8);
                        outputStreamWriter.write(this.f);
                        outputStreamWriter.flush();
                        outputStreamWriter.close();
                        httpsURLConnectionA.connect();
                        Log.i("pm", "POSTJSON << " + httpsURLConnectionA.getResponseMessage() + " [" + httpsURLConnectionA.getResponseCode() + "]");
                        InputStream inputStream2 = null;
                        try {
                        } catch (Throwable th) {
                            if (0 != 0) {
                                inputStream2.close();
                            }
                            throw th;
                        }
                    } catch (IOException e) {
                        Log.e("pm", "Failed to POSTJSON", e);
                        e.printStackTrace();
                        SharedPreferencesHelper.getInstance().incrementFailedRequestCounter();
                        this.g.failure(e);
                    }
                    if (httpsURLConnectionA.getResponseCode() == 401) {
                        throw new UnauthorizedException();
                    }
                    if (httpsURLConnectionA.getResponseCode() == 409) {
                        inputStream = httpsURLConnectionA.getErrorStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    } else {
                        inputStream = httpsURLConnectionA.getInputStream();
                        bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    }
                    this.g.receivedResponse(httpsURLConnectionA.getResponseCode(), httpsURLConnectionA.getResponseMessage(), bufferedReader);
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    z = true;
                }
                return z;
            } finally {
                httpsURLConnectionA.disconnect();
            }
        }
    }

    public static class f extends d {
        public final String e;
        public final String f;
        public final HttpResponseListener g;
        public final String h;

        public f(String str, String str2, String str3, String str4, @NonNull HttpResponseListener httpResponseListener) {
            super(null);
            this.e = str;
            this.f = str4;
            this.g = httpResponseListener;
            this.h = str2;
            b("X-Auth-Key", str3);
        }

        @Override // android.os.AsyncTask
        public Boolean doInBackground(Void[] voidArr) {
            InputStream inputStream;
            HttpsURLConnection httpsURLConnectionA = a(this.e, this.h);
            boolean z = false;
            try {
                if (httpsURLConnectionA != null) {
                    try {
                        httpsURLConnectionA.setRequestMethod("PUT");
                        httpsURLConnectionA.setDoOutput(true);
                        httpsURLConnectionA.setDoInput(true);
                        httpsURLConnectionA.setUseCaches(false);
                        httpsURLConnectionA.setRequestProperty("Connection", "Keep-Alive");
                        httpsURLConnectionA.setRequestProperty("ENCTYPE", "multipart/form-data");
                        Log.i("pm", "PUT >> " + this.h + " >> data " + this.f);
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpsURLConnectionA.getOutputStream(), StandardCharsets.UTF_8);
                        outputStreamWriter.write(this.f);
                        outputStreamWriter.flush();
                        outputStreamWriter.close();
                        httpsURLConnectionA.connect();
                        Log.i("pm", "PUT << " + httpsURLConnectionA.getResponseMessage() + " [" + httpsURLConnectionA.getResponseCode() + "]");
                        inputStream = null;
                    } catch (IOException e) {
                        Log.e("pm", "Failed to PUT", e);
                        this.g.failure(e);
                    }
                    try {
                        inputStream = httpsURLConnectionA.getInputStream();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                        SharedPreferencesHelper.getInstance().incrementFailedRequestCounter();
                        this.g.receivedResponse(httpsURLConnectionA.getResponseCode(), httpsURLConnectionA.getResponseMessage(), bufferedReader);
                        z = true;
                    } finally {
                        if (inputStream != null) {
                            inputStream.close();
                        }
                    }
                }
                return z;
            } finally {
                httpsURLConnectionA.disconnect();
            }
        }
    }

    public static /* synthetic */ String a(String str, String str2) {
        StringBuilder sbA = g9.a(str);
        sbA.append(str2.trim().replaceFirst("^[/.]+", ""));
        return sbA.toString();
    }

    public static /* synthetic */ String b() {
        return "pm";
    }

    public static String join(int[] iArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < iArr.length - 1; i++) {
            sb.append(iArr[i]);
            sb.append(",");
        }
        if (iArr.length > 0) {
            sb.append(iArr[iArr.length - 1]);
        }
        return sb.toString();
    }

    public static String a(BufferedReader bufferedReader) throws IOException {
        StringBuilder sb = new StringBuilder();
        while (true) {
            String line = bufferedReader.readLine();
            if (line == null) {
                return sb.toString();
            }
            sb.append(line);
        }
    }

    public void a(String str, String str2, String str3, Date date, Map<String, String> map, Integer num, @NonNull HttpResponseListener httpResponseListener) {
        new c(str, str2, str3, date, map, num, httpResponseListener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }

    public boolean a() {
        if (this.a == null) {
            this.a = (ConnectivityManager) App.getInstance().getSystemService("connectivity");
        }
        ConnectivityManager connectivityManager = this.a;
        if (connectivityManager == null) {
            return true;
        }
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        boolean z = activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected();
        if (!z) {
            Log.w("pm", "No active network or not connected");
        }
        return !z;
    }

    public static abstract class d extends AsyncTask<Void, Void, Boolean> {
        public static final SSLContext d;
        public final Map<String, String> a = new HashMap();
        public Long b;
        public Integer c;

        static {
            CertificateFactory certificateFactory;
            KeyStore keyStore;
            BufferedInputStream bufferedInputStream;
            String strB = pm.b();
            SSLContext sSLContext = null;
            try {
                certificateFactory = CertificateFactory.getInstance("X.509");
                keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(null, null);
                bufferedInputStream = new BufferedInputStream(App.getInstance().getResources().openRawResource(R.raw.isrgrootx1));
            } catch (IOException e) {
                e = e;
            } catch (KeyManagementException e2) {
                e = e2;
            } catch (KeyStoreException e3) {
                e = e3;
            } catch (NoSuchAlgorithmException e4) {
                e = e4;
            } catch (CertificateException e5) {
                e = e5;
            }
            try {
                Certificate certificateGenerateCertificate = certificateFactory.generateCertificate(bufferedInputStream);
                Log.d(strB, "read certificate from RAW resource " + certificateGenerateCertificate.toString());
                bufferedInputStream.close();
                keyStore.setCertificateEntry("active", certificateGenerateCertificate);
                BufferedInputStream bufferedInputStream2 = new BufferedInputStream(App.getInstance().getResources().openRawResource(R.raw.starfield_secure_certificate_authority_g2));
                try {
                    Certificate certificateGenerateCertificate2 = certificateFactory.generateCertificate(bufferedInputStream2);
                    Log.d(strB, "read certificate from RAW resource " + certificateGenerateCertificate2.toString());
                    bufferedInputStream2.close();
                    keyStore.setCertificateEntry("backup", certificateGenerateCertificate2);
                    BufferedInputStream bufferedInputStream3 = new BufferedInputStream(App.getInstance().getResources().openRawResource(R.raw.globalsign_root_ca));
                    try {
                        Certificate certificateGenerateCertificate3 = certificateFactory.generateCertificate(bufferedInputStream3);
                        Log.d(strB, "read certificate from RAW resource " + certificateGenerateCertificate3.toString());
                        bufferedInputStream3.close();
                        keyStore.setCertificateEntry("ecc", certificateGenerateCertificate3);
                        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                        trustManagerFactory.init(keyStore);
                        SSLContext sSLContext2 = SSLContext.getInstance("TLS");
                        try {
                            sSLContext2.init(null, trustManagerFactory.getTrustManagers(), null);
                            Log.v(strB, "created SSLContext " + sSLContext2);
                        } catch (IOException | KeyManagementException | KeyStoreException | NoSuchAlgorithmException | CertificateException e6) {
                            e = e6;
                            sSLContext = sSLContext2;
                            Log.w(strB, e.getClass().getSimpleName());
                            e.printStackTrace();
                            sSLContext2 = sSLContext;
                            d = sSLContext2;
                        }
                        d = sSLContext2;
                    } finally {
                    }
                } catch (Throwable th) {
                    try {
                        throw th;
                    } catch (Throwable th2) {
                        try {
                            bufferedInputStream2.close();
                        } catch (Throwable th3) {
                            th.addSuppressed(th3);
                        }
                        throw th2;
                    }
                }
            } catch (Throwable th4) {
                try {
                    throw th4;
                } catch (Throwable th5) {
                    try {
                        bufferedInputStream.close();
                    } catch (Throwable th6) {
                        th4.addSuppressed(th6);
                    }
                    throw th5;
                }
            }
        }

        public /* synthetic */ d(a aVar) {
        }

        public HttpsURLConnection a(String str, String str2) {
            try {
                String strA = pm.a(str, str2);
                URL url = new URL(strA);
                Log.i("pm", "open(\"" + str2 + "\") connecting to \"" + strA + "\"");
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                if (d != null) {
                    httpsURLConnection.setSSLSocketFactory(d.getSocketFactory());
                }
                if (this.c != null) {
                    httpsURLConnection.setConnectTimeout(this.c.intValue());
                    httpsURLConnection.setReadTimeout(this.c.intValue());
                }
                for (Map.Entry<String, String> entry : this.a.entrySet()) {
                    httpsURLConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
                if (this.b != null) {
                    httpsURLConnection.setIfModifiedSince(this.b.longValue());
                }
                return httpsURLConnection;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        public void b(String str, String str2) {
            if (!TextUtils.isEmpty(str2)) {
                this.a.put(str, str2);
            } else if (this.a.containsKey(str)) {
                this.a.remove(str);
            }
        }

        public void a(Map<String, String> map) {
            if (map != null) {
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    b(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    public void a(String str, String str2, String str3, Map<String, String> map, @NonNull HttpResponseListener httpResponseListener) {
        a(str, str2, null, str3, map, httpResponseListener);
    }

    public void a(String str, String str2, String str3, String str4, Map<String, String> map, @NonNull HttpResponseListener httpResponseListener) {
        new e(str, str2, str3, str4, map, httpResponseListener).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
    }
}
