package io.vokal.hightower

import android.app.job.*
import android.content.Context
import android.content.ComponentName
import android.os.Bundle
import android.util.Log

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.wearable.DataApi
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable

import com.google.gson.*

import kotlin.properties.Delegates

import io.vokal.hightower.model.KillCount
import io.vokal.hightower.api.Api

public class StatsSyncService: JobService() {

    companion object {
        public val TAG: String = "StatsSyncService"
    }

    private var apiClient: GoogleApiClient by Delegates.notNull()
 
    override fun onStartJob(params: JobParameters): Boolean {

        apiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(object : ConnectionCallbacks {
                override fun onConnected(hint: Bundle?) {
                    Log.i(TAG, "onConnected: " + hint)

                    sendMessage()
                }

                override fun onConnectionSuspended(cause: Int) {
                    Log.d(TAG, "onConnectionSuspended: " + cause)
                }
            })
            .addOnConnectionFailedListener(object : OnConnectionFailedListener {
                override fun onConnectionFailed(result: ConnectionResult) {
                    Log.d(TAG, "onConnectionFailed: " + result)
                }
            })
            .addApi(Wearable.API)
            .build()

        apiClient.connect()
 
        return false
    }
 
    override fun onStopJob(params: JobParameters): Boolean {
         
        return false
    }

    fun sendMessage() {
        Api.SERVICE.getPlayer("STEAM_0:1:2280452")
            .map { stats -> 
                var nodes = Wearable.NodeApi.getConnectedNodes(apiClient).await()

                val gson = Gson()

                for (node in nodes.getNodes()) {
                    var result = Wearable.MessageApi.sendMessage(apiClient, node.getId(), 
                        "/stats-updated", gson.toJson(stats).getBytes()).await()

                    if (!result.getStatus().isSuccess()) {
                        Log.e(TAG, "error sending to: " + node.getDisplayName())
                    }
                }
            }
    }
}
