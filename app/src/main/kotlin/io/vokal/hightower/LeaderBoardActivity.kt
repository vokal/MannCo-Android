package io.vokal.hightower;

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.OvershootInterpolator
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SimpleAdapter
<<<<<<< Updated upstream
import android.widget.Toast
=======
import android.util.Log
>>>>>>> Stashed changes
import com.trello.rxlifecycle.components.RxActivity
import io.realm.Realm
import io.realm.RealmConfiguration
import io.vokal.hightower.api.Api
import io.vokal.hightower.api.model.Player
import io.vokal.hightower.api.model.PlayerResponse
import io.vokal.hightower.api.model.getKdr
import io.vokal.hightower.api.view.LeaderboardAdapter
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.activity_leader_board.*
import java.util.*
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Func2
import uk.co.chrisjenx.calligraphy.CalligraphyConfig
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper
import java.util.concurrent.TimeUnit

import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.GoogleApiClient.*
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.wearable.Wearable

public class LeaderBoardActivity : RxActivity() {

    companion object {
        public val TAG: String = "LeaderBoardActivity"
        public val killSort = { first : Player, second : Player -> second.KILLS - first.KILLS }
        public val kdrSort = { first : Player, second : Player -> (second.getKdr()  - first.getKdr()*10) as Int}
        public val pointSort = { first : Player, second : Player -> second.POINTS- first.POINTS}
        public var selectedSort = pointSort
    }

    private var adapter: LeaderboardAdapter = LeaderboardAdapter(ArrayList<Player>())

    private var mPlayerList: List<Player> = ArrayList<Player>()

    override protected fun onCreate(state: Bundle?) {
        super.onCreate(state)
        var config : RealmConfiguration= RealmConfiguration.Builder(this).build();
        Realm.setDefaultConfiguration(config);
        CalligraphyConfig.initDefault(CalligraphyConfig.Builder()
                .setFontAttrId(R.attr.fontPath)
                .build()
        )

        setContentView(R.layout.activity_leader_board)

        actionBar.title = "Leaderboard"
        actionBar.setDisplayShowHomeEnabled(true)

        val listener = object : RecyclerView.OnScrollListener() {
            val listsner = object : Rec
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {
                adapter.resetOffset(dy > 0)
            }
        }

        leaderboard.layoutManager = LinearLayoutManager(this)
        leaderboard.setOnScrollListener(listener)

        refresh()

        sorting.adapter = ArrayAdapter.createFromResource(this, R.array.names, R.layout.spinner_item)
        timeframe.adapter = ArrayAdapter.createFromResource(this, R.array.times, R.layout.spinner_item)
        timeframe.visibility = View.INVISIBLE

        val sortingListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
               selectedSort = when (position) {
                    0 ->  killSort
                    1 ->  pointSort
                    else ->  kdrSort
                }
                updateList(Observable.just(mPlayerList))
            }
        }

        sorting.onItemSelectedListener = sortingListener

        swipe.setOnRefreshListener( {
                refresh();
            })

        var apiClient = GoogleApiClient.Builder(this)
            .addConnectionCallbacks(object : ConnectionCallbacks {
                override fun onConnected(hint: Bundle) {
                    Log.i(TAG, "onConnected: " + hint)
                }

                override fun onConnectionSuspended(cause: Int) {
                    Log.d(TAG, "onConnectionSuspended: ", + cause)
                }
            })
            .addOnConnectionFailedListener(object : OnConnectionFailedListener {
                override fun onConnectionFailed(result: ConnectionResult) {
                    Log.d(TAG, "onConnectionFailed: " + result)
                }
            })
            .addApi(Wearable.API)
            .build()
    }

    override
    protected fun attachBaseContext(newBase : Context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    fun refresh() {
        updateList(Api.SERVICE.getAll()
                .compose(bindToLifecycle<PlayerResponse>())
                .observeOn(AndroidSchedulers.mainThread())
                .map( {playerResonse -> playerResonse.results }))
    }

    fun updateList(observable : Observable<List<Player>>)  {
        observable
                .doOnNext({list ->
                    Realm.getDefaultInstance().beginTransaction()
                    Realm.getDefaultInstance().copyToRealm(list)
                    Realm.getDefaultInstance().commitTransaction()
                })
                .onErrorResumeNext({error ->
                    Observable.just(Realm.getDefaultInstance().allObjects(Player::class.java))
                })
                .flatMapIterable({i -> i})
                .toSortedList(pointSort)
                .subscribe(
                        {playerList : List<Player> ->
                            mPlayerList = playerList
                            adapter = LeaderboardAdapter(playerList)
                            leaderboard.adapter = LeaderboardAdapter(playerList)
                        },
                        {error -> error.printStackTrace()
                            Toast.makeText(this, "There's a spy sappin my dispenser!", Toast.LENGTH_LONG).show()
                            swipe.isRefreshing = false
                        }, { swipe.isRefreshing = false }
                );
    }

}
