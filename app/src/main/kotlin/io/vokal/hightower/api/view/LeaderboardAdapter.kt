package io.vokal.hightower.api.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.vokal.hightower.R
import io.vokal.hightower.api.model.Player
import kotlinx.android.synthetic.leaderboard_card.view.*
import java.util.*

public class LeaderboardAdapter(val mData : List<Player>) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mDeaths.text = Integer.toString(mData.get(position).totalDeaths)
        holder.mName.text = mData.get(position).name
        holder.mKills.text = Integer.toString(mData.get(position).totalKills)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        var v = LayoutInflater.from(parent.getContext()).inflate(R.layout.leaderboard_card, parent, false)
        return ViewHolder(v);
    }

    override fun getItemCount() : Int {
        return mData.size()
    }

    public class ViewHolder(v: View) : RecyclerView.ViewHolder(v : View) {
        public var mKills:  TextView = v.kills
        public var mName:   TextView = v.name
        public var mDeaths: TextView = v.deaths
    }
}