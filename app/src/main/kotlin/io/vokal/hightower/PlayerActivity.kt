package io.vokal.hightower

import android.os.Bundle
import com.trello.rxlifecycle.components.RxActivity

public class PlayerActivity : RxActivity() {
    companion object {
        public val TAG: String = "PlayerActivity"
    }

    override protected fun onCreate(state: Bundle?) {
        super.onCreate(state)
        setContentView(R.layout.activity_player)
    }
}