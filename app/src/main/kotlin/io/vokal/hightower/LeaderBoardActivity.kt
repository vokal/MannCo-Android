package io.vokal.hightower;

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
import com.trello.rxlifecycle.components.RxActivity
import io.vokal.hightower.api.Api
import io.vokal.hightower.api.model.Player
import io.vokal.hightower.api.view.LeaderboardAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.activity_leader_board.*
import java.util.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import java.util.concurrent.TimeUnit

public class LeaderBoardActivity : RxActivity() {
        companion object {
            public val TAG: String = "LeaderBoardActivity"
        }

    private var adapter: LeaderboardAdapter = LeaderboardAdapter(ArrayList<Player>())

    override protected fun onCreate(state: Bundle?) {
            super.onCreate(state)
            setContentView(R.layout.activity_leader_board)

            val players = ArrayList<Player>()
            players.add(Player("David" : String, "David" : String, 12 : Int, 5 : Int))
            players.add(Player("David" : String, "David" : String, 12 : Int, 5 : Int))
            players.add(Player("David" : String, "David" : String, 12 : Int, 5 : Int))
            players.add(Player("David" : String, "David" : String, 12 : Int, 5 : Int))
            players.add(Player("David" : String, "David" : String, 12 : Int, 5 : Int))
            players.add(Player("David" : String, "David" : String, 12 : Int, 5 : Int))
            players.add(Player("David" : String, "David" : String, 12 : Int, 5 : Int))
            players.add(Player("David" : String, "David" : String, 12 : Int, 5 : Int))
            players.add(Player("David" : String, "David" : String, 12 : Int, 5 : Int))

            setActionBar(tool)
            actionBar.setDisplayShowTitleEnabled(false)
            actionBar.setDisplayUseLogoEnabled(true)

            leaderboard.layoutManager = LinearLayoutManager(this)

            //leaderboard.setOnScrollChangeListener(View.OnScrollChangeListener { view, i, i1, i2, i3 -> adapter.resetOffset() })

            //Api.SERVICE.getAll()
                    Observable.just(players)
                    .compose(bindToLifecycle<List<Player>>())
                    .subscribe(
                            {playerList : List<Player> ->
                                adapter = LeaderboardAdapter(playerList)
                                leaderboard.adapter = LeaderboardAdapter(playerList)},
                            {error -> error.printStackTrace()}
                    );

            val adapter = ArrayAdapter.createFromResource(this, R.array.names, R.layout.spinner_item)
            sorting.adapter = adapter

            val timeAdapter = ArrayAdapter.createFromResource(this, R.array.times, R.layout.spinner_item)
            timeframe.adapter = timeAdapter


        }
}