package io.vokal.hightower

import android.app.job.JobParameters
import android.app.job.JobService
import android.os.Bundle
import android.support.v7.preference.PreferenceManager
import android.util.Log
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener
import com.google.android.gms.wearable.Wearable
import com.google.gson.Gson
import io.vokal.hightower.api.Api
import rx.schedulers.Schedulers
import timber.log.Timber
import kotlin.properties.Delegates

public class StatsSyncService: JobService() {

    companion object {
        public val TAG: String = "StatsSyncService"
    }

    private var apiClient: GoogleApiClient by Delegates.notNull()
 
    override fun onStartJob(params: JobParameters): Boolean {

        apiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(object : ConnectionCallbacks {
                override fun onConnected(hint: Bundle?) {
                    Timber.i("onConnected: %s", hint);
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
        var username = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            .getString(getString(R.string.settings_key_username), null)

        if (username != null) {
            Api.SERVICE.getPlayer(username)
                    .subscribeOn(Schedulers.io())
                    .subscribe({ stats ->
                        Log.d(TAG, "KillCount: " + stats)

                        var nodes = Wearable.NodeApi.getConnectedNodes(apiClient).await()

                        for (node in nodes.nodes) {
                            var result = Wearable.MessageApi.sendMessage(apiClient, node.id,
                                    "/stats-updated", Gson().toJson(stats).getBytes()).await()

                            if (!result.status.isSuccess) {
                                Log.e(TAG, "error sending to: " + node.displayName)
                            }
                        }
                    }, { e -> e.printStackTrace() })
        }
    }
}
