package io.vokal.hightower.api

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.realm.RealmObject
import io.vokal.hightower.api.model.Player
import io.vokal.hightower.api.model.PlayerResponse
import retrofit.RestAdapter
import retrofit.converter.GsonConverter
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
        public val SERVICE: ApiInterface =  RestAdapter.Builder().setEndpoint("https://tf2stats.vokal.io")
                .setConverter(GsonConverter(gson))
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build().create(ApiInterface::class.java)
    }

    interface ApiInterface {
        @GET("/v1/player/{player}")
        fun getPlayer(@Path("player") aPlayer : String) : Observable<Player>

        @GET("/v1/players")
        fun getAll() : Observable<PlayerResponse>
    }
}

