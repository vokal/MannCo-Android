package io.vokal.hightower

import android.os.Bundle
import com.bumptech.glide.Glide
import com.trello.rxlifecycle.components.RxActivity
import io.realm.Realm
import io.vokal.hightower.api.model.Player
import io.vokal.hightower.api.model.getKdr
import io.vokal.hightower.api.model.getTauntKills
import kotlinx.android.synthetic.activity_player.*

public class PlayerActivity : RxActivity() {
    companion object {
        public val TAG: String = "PlayerActivity"
    }

    override protected fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_player)
        var player : Player = Realm.getDefaultInstance().where(Player::class.java).equalTo("STEAMID", (intent.getStringExtra("steamid"))).findFirst()
        var url = player.profile_image_url
        Glide.with(this)
                .load(url)
                .into(image)
        actionBar.title = player.NAME
        headshot.text = Integer.toString(player.HeadshotKill)
        kills.text = Integer.toString(player.KILLS)
        deaths.text = Integer.toString(player.Death)
        kdr.text = player.getKdr().toString()
        points.text = Integer.toString(player.POINTS)
        buildings.text = Integer.toString(player.KO_Dispenser + player.KO_SentryGun + player.KO_TeleporterEntrance + player.KO_TeleporterExit)
        taunt.text = Integer.toString(player.getTauntKills())
        assists.text = Integer.toString(player.KillAssist)
        backstab.text = Integer.toString(player.K_backstab)
        domination.text = Integer.toString(player.DOMINATION)
        revenge.text = Integer.toString(player.Revenge)

    }
}