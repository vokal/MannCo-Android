package io.vokal.hightower.api

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
        public val SERVICE: ApiInterface =  Retrofit.Builder().baseUrl("http://10.1.20.178:3000")
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

