package mcapi;

import com.google.gson.Gson;
import defpackage.mm;
import helper.SharedPreferencesHelper;
import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import mcapi.NewMcApi;
import mcapi.json.message.CampaignMessage;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/* loaded from: classes.dex */
public class NewMcApi {
    public static final String MESSAGE_BASE_URL = "https://api2.monsieur-cuisine.com/mcc/api/v1/";
    public static NewMcApi b = new NewMcApi();
    public McAPIService a;

    public NewMcApi() {
        APIServiceFactory aPIServiceFactory = APIServiceFactory.getInstance();
        if (aPIServiceFactory == null) {
            throw null;
        }
        this.a = (McAPIService) new Retrofit.Builder().client(aPIServiceFactory.a).addConverterFactory(GsonConverterFactory.create()).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).baseUrl(MESSAGE_BASE_URL).build().create(McAPIService.class);
    }

    public static NewMcApi getInstance() {
        return b;
    }

    public Completable deleteCampaignMessage() {
        return this.a.deleteMessage().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    public Single<String> getDataPrivacyHTML() {
        return this.a.getDataPrivacyHTML(SharedPreferencesHelper.getInstance().getLanguage()).compose(mm.a).map(new Function() { // from class: om
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((ResponseBody) obj).string();
            }
        });
    }

    public Maybe<CampaignMessage> getMessageFromServer() {
        final Gson gson = new Gson();
        return this.a.getMessage().compose(mm.a).toMaybe().map(new Function() { // from class: lm
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return NewMcApi.a(gson, (ResponseBody) obj);
            }
        });
    }

    public Single<String> getTermsOfUseHTML() {
        return this.a.getTermsOfUseHTML(SharedPreferencesHelper.getInstance().getLanguage()).compose(mm.a).map(new Function() { // from class: km
            @Override // io.reactivex.functions.Function
            public final Object apply(Object obj) {
                return ((ResponseBody) obj).string();
            }
        });
    }

    public static /* synthetic */ CampaignMessage a(Gson gson, ResponseBody responseBody) throws Exception {
        return (CampaignMessage) gson.fromJson(responseBody.charStream(), CampaignMessage.class);
    }
}
