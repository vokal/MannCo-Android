package io.vokal.hightower

import android.os.Bundle
import com.bumptech.glide.Glide
import com.trello.rxlifecycle.components.RxActivity
import io.realm.Realm
import io.vokal.hightower.api.model.Player
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
                .into(image);
        headshot.text = Integer.toString(player.HeadshotKill)
        actionBar.title = player.NAME

    }
}