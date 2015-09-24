package io.vokal.hightower.api

import io.vokal.hightower.api.model.Player
import retrofit.Retrofit
import retrofit.http.GET
import retrofit.http.Path
import rx.Observable

public class Api {

    companion object {
        public val SERVICE: ApiInterface =  Retrofit.Builder().baseUrl("url").build().create(ApiInterface::class.java)
    }

    interface ApiInterface {
        @GET("/player/{player}")
        fun getPlayer(@Path("player") aPlayer : String) : Observable<Player>

        @GET("/player/")
        fun getAll() : Observable<List<Player>>
    }
}

