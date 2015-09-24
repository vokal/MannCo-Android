package io.vokal.hightower.api.view

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.TextView
import io.vokal.hightower.LeaderBoardActivity
import io.vokal.hightower.PlayerActivity
import io.vokal.hightower.R
import io.vokal.hightower.api.model.Player
import kotlinx.android.synthetic.leaderboard_card.view.*
import java.util.*

public class LeaderboardAdapter(val mData : List<Player>) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mDeaths.text = Integer.toString(mData.get(position).totalDeaths)
        holder.mName.text = mData.get(position).name
        holder.mKills.text = Integer.toString(mData.get(position).totalKills)
        holder.mBackground.tag = position
        holder.mBackground.setOnClickListener(View.OnClickListener { view ->
            val i = Intent(view.context, PlayerActivity::class.java)
            i.putExtra("steamid", view.tag as Int)
            view.context.startActivity(i)
        })

        setAnimation(holder.mBackground, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        var v = LayoutInflater.from(parent.context).inflate(R.layout.leaderboard_card, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount() : Int {
        return mData.size()
    }

    public class ViewHolder(v: View) : RecyclerView.ViewHolder(v : View) {
        public var mKills:      TextView = v.kills
        public var mName:       TextView = v.name
        public var mDeaths:     TextView = v.points
        public var mBackground: View = v.background
    }

    fun resetOffset() {
        offset = 0
    }

    private var lastPosition: Int = -1;

    private var offset: Long = 0

    fun setAnimation(viewToAnimate : View, position: Int){
        // If the bound view wasn't previously displayed on screen, it's animated
        if (position > lastPosition || position == 0) {
            var animation :Animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.slide_in)
            animation.interpolator = AccelerateDecelerateInterpolator()
           // animation.startOffset = (200 * offset++) as Long
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}