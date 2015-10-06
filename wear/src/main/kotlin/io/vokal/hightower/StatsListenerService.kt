package io.vokal.hightower

import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService
import com.google.gson.Gson
import io.realm.Realm
import io.realm.RealmConfiguration
import io.vokal.hightower.model.KillCount
import io.vokal.hightower.model.RKillCount

public class StatsListenerService: WearableListenerService() {
    companion object {
        public val TAG = "StatsListener"
    }

    val realmConfig: RealmConfiguration by lazy {
        RealmConfiguration.Builder(this)
                .name("tf2.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build()
    }

    public val realm: Realm by lazy {
        Realm.getInstance(realmConfig)
    }

    override fun onMessageReceived(messageEvent: MessageEvent) {

        Log.i(TAG, "onMessageReceived()")

        if (messageEvent.getPath().equals("/stats-updated")) {
            val message = String(messageEvent.getData())

            val gson = Gson()
            var count = gson.fromJson(message, RKillCount::class.java)

            realm.beginTransaction()
            realm.clear(KillCount::class.java)
            realm.copyToRealm(count.toKillCount())
            realm.commitTransaction()

            // TODO: Notification
            var notificationId = 1

            // Build intent for notification content
            var viewIntent = Intent(this, MainActivity::class.java)

            val viewPendingIntent =
                PendingIntent.getActivity(this, 0, viewIntent, 0)

            val wearableExtender = NotificationCompat.WearableExtender()
                .setHintAvoidBackgroundClipping(true)
                .setBackground(BitmapFactory.decodeResource(getResources(), R.drawable.pl_hightower))

            var notificationBuilder = NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle("${count.kills} kills")
                .setContentText("Today")
                .setContentIntent(viewPendingIntent)
                .setOngoing(true)
                .extend(wearableExtender)

            val notificationManager = NotificationManagerCompat.from(this)

            // Build the notification and issues it with notification manager.
            notificationManager.notify(notificationId, notificationBuilder.build())
        } else {
            super.onMessageReceived(messageEvent)
        }
    }
}
