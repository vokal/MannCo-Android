package io.vokal.hightower.api.view

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import io.vokal.hightower.LeaderBoardActivity
import io.vokal.hightower.PlayerActivity
import io.vokal.hightower.R
import io.vokal.hightower.api.model.Player
import io.vokal.hightower.api.model.getKdr
import kotlinx.android.synthetic.leaderboard_card.view.*
import java.text.DecimalFormat
import java.util.*

public class LeaderboardAdapter(val mData : List<Player>) : RecyclerView.Adapter<LeaderboardAdapter.ViewHolder>() {

    private var downScroll: Boolean = true
    private var lastPosition: Int = -1;
    private var offset: Long = 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mPoints.text = Integer.toString(mData.get(position).POINTS)
        holder.mName.text = mData.get(position).NAME
        holder.mKills.text = Integer.toString(mData.get(position).KILLS)
        holder.mBackground.tag = mData.get(position).STEAMID
        holder.mKdr.text = DecimalFormat("#.##").format(mData.get(position).getKdr())
        holder.mBackground.setOnClickListener(View.OnClickListener { view ->
            val i = Intent(view.context, PlayerActivity::class.java)
            i.putExtra("steamid", view.tag as String)
            view.context.startActivity(i)
        })
        holder.mRank.text = Integer.toString(position + 1)

        Glide.with(holder.mIcon.context)
                .load(mData.get(position).profile_image_url)
                .into(holder.mIcon);

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
        public var mKills: TextView = v.kills
        public var mName: TextView = v.name
        public var mPoints: TextView = v.points
        public var mKdr: TextView = v.kdr
        public var mBackground: View = v.background
        public var mRank: TextView = v.rank
        public var mIcon: ImageView = v.icon
    }

    fun resetOffset(down : Boolean) {
        downScroll = down
        offset = 0
    }

    fun setAnimation(viewToAnimate : View, position: Int){
        if ((position > lastPosition) && downScroll) {
            var animation :Animation = AnimationUtils.loadAnimation(viewToAnimate.context, R.anim.slide_in)
            animation.interpolator = AccelerateDecelerateInterpolator()
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }
}
