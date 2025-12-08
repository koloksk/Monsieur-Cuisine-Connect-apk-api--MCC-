package mcapi;

import io.reactivex.Completable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

/* loaded from: classes.dex */
public interface McAPIService {
    @DELETE("message")
    Completable deleteMessage();

    @GET("https://mc20.monsieur-cuisine.com/mcc_privacy.{language}.html")
    Single<ResponseBody> getDataPrivacyHTML(@Path("language") String str);

    @GET("message")
    Single<ResponseBody> getMessage();

    @GET("https://mc20.monsieur-cuisine.com/gen_terms.{language}.html")
    Single<ResponseBody> getTermsOfUseHTML(@Path("language") String str);
}
