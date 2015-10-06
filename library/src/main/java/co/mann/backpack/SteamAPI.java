package co.mann.backpack;


import android.content.Context;

import com.squareup.okhttp.*;
import retrofit.*;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import timber.log.Timber;

public class SteamAPI {

    private static final String BASE_URL = "http://api.steampowered.com/";

    interface SteamWebInterface {

        @GET("/ISteamUser/ResolveVanityURL/v0001/")
        public Observable<UserIdResponse> getUserId(@Query("key") String key,
                                                    @Query("vanityurl") String username);
    }

    private static SteamAPI     sInstance;
    private static Retrofit     sRetrofit;
    private static OkHttpClient sOkClient;

    private SteamWebInterface mApi;

    public static SteamAPI instance(Context context) {
        if (sInstance == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl("http://api.steampowered.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getOkClient(context))
                    .build();

            sInstance = new SteamAPI();
            sInstance.mApi = sRetrofit.create(SteamWebInterface.class);
        }
        return sInstance;
    }

    public static OkHttpClient getOkClient(Context context) {
        if (sOkClient == null) {
            sOkClient = new OkHttpClient();
            sOkClient.setCache(new Cache(context.getCacheDir(), 25 * 1024 * 1024));
            sOkClient.interceptors().add(chain -> {
                Request request = chain.request();

                long t1 = System.nanoTime();
                Timber.i("---> %s %n%s", request.url(), request.headers());

                com.squareup.okhttp.Response response = chain.proceed(request);

                long t2 = System.nanoTime();
                Timber.i("<--- %d %s : %s in %.1fms%n%s", response.code(), response.message(),
                         response.request().url(), (t2 - t1) / 1e6d,
                         response.headers());

                return response;
            });
        }
        return sOkClient;
    }

    public Observable<Long> getSteamId(String userName) {
        return mApi.getUserId(BuildConfig.STEAM_KEY, userName)
                .map(r -> Long.parseLong(r.response.steamid));
    }

    public Observable<String> getSteamCommunityId(String userName) {
        return getSteamId(userName).compose(steamIdToCommunityId());
    }

    public static Observable.Transformer<Long, String> steamIdToCommunityId() {
        return steamId -> steamId.map(id -> {
            int x = (int) (id & 0xffffffff) / 2;
            long y = Long.lowestOneBit(id) > 1 ? 0 : 1;
            return String.format("STEAM_0:%d:%d", y, x);
        });
    }
}
