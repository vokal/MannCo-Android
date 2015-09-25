package io.vokal.hightower.api

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import io.realm.RealmObject
import io.vokal.hightower.api.model.Player
import io.vokal.hightower.api.model.PlayerResponse
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import retrofit.http.GET
import retrofit.http.Path
import rx.Observable

public class Api {

    companion object {

/*         val exclusionStrategy : ExclusionStrategy =  object : ExclusionStrategy {
            override
            fun  shouldSkipField(f : FieldAttributes) : Boolean {
                return f.getDeclaringClass().equals(RealmObject.class);
            }

            override
            fun  shouldSkipClass(Class<*> clazz) : Boolean {
                return false;
            }
        };*/

        public val SERVICE: ApiInterface =  Retrofit.Builder().baseUrl("https://tf2stats.vokal.io")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build().create(ApiInterface::class.java)
    }

    interface ApiInterface {
        @GET("/player/{player}")
        fun getPlayer(@Path("player") aPlayer : String) : Observable<Player>

        @GET("/players")
        fun getAll() : Observable<PlayerResponse>
    }
}

