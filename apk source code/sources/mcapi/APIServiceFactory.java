package mcapi;

import application.App;
import helper.EncryptionChipServiceAdapter;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes.dex */
public class APIServiceFactory {
    public static APIServiceFactory b = new APIServiceFactory();
    public OkHttpClient a;
    public Retrofit retrofit;

    public APIServiceFactory() {
        Cache cache = new Cache(App.getInstance().getCacheDir(), 10485760);
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        final String sESerial = EncryptionChipServiceAdapter.getInstance().getSESerial();
        this.a = new OkHttpClient.Builder().cache(cache).addInterceptor(httpLoggingInterceptor).addInterceptor(new Interceptor() { // from class: jm
            @Override // okhttp3.Interceptor
            public final Response intercept(Interceptor.Chain chain) {
                return chain.proceed(chain.request().newBuilder().addHeader("x-se-id", sESerial).build());
            }
        }).build();
        this.retrofit = new Retrofit.Builder().client(this.a).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl("https://mc20.monsieur-cuisine.com/mcc/api/v1/").build();
    }

    public static APIServiceFactory getInstance() {
        return b;
    }
}
