package io.vokal.hightower;

import android.app.Activity
import android.os.Bundle
import com.trello.rxlifecycle.components.RxActivity
import io.vokal.hightower.api.Api
import io.vokal.hightower.api.model.Player
import io.vokal.hightower.api.view.LeaderboardAdapter
import kotlinx.android.synthetic.activity_leader_board.*

public class LeaderBoardActivity : RxActivity() {
        companion object {
            public val TAG: String = "LeaderBoardActivity"
        }

        override protected fun onCreate(state: Bundle?) {
            super.onCreate(state)
            setContentView(R.layout.activity_leader_board)
            Api.SERVICE.getAll()
                    .compose(bindToLifecycle<List<Player>>())
                    .subscribe(
                            {playerList : List<Player> -> leaderboard.adapter = LeaderboardAdapter(playerList)},
                            {error -> error.printStackTrace()}
                    );
        }
}