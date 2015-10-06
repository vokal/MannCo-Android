package io.vokal.hightower.api

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.realm.RealmObject
import io.vokal.hightower.api.model.PlayerResponse
import io.vokal.hightower.model.KillCount
import retrofit.GsonConverterFactory
import retrofit.Retrofit
import retrofit.RxJavaCallAdapterFactory
import retrofit.http.GET
import retrofit.http.Path
import rx.Observable

public class Api {

    companion object {
        var gson : Gson = GsonBuilder().setExclusionStrategies(object : ExclusionStrategy {
            override
            fun shouldSkipField(f: FieldAttributes ) : Boolean {
                return f.declaringClass.equals(RealmObject::class.java)
            }

            override
            fun  shouldSkipClass( clazz: Class<*>) : Boolean{
                return false;
            }

        })
        .create();
        public val SERVICE: ApiInterface =  Retrofit.Builder().baseUrl("https://tf2stats.vokal.io")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .build().create(ApiInterface::class.java)
    }

    interface ApiInterface {
        @GET("/v1/player/{player}")
        fun getPlayer(@Path("player") aPlayer : String) : Observable<KillCount>

        @GET("/v1/players")
        fun getAll() : Observable<PlayerResponse>
    }
}

