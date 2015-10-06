package io.vokal.hightower

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.graphics.drawable.*
import android.os.Bundle
import android.os.AsyncTask
import android.view.*
import android.widget.TextView
import android.support.wearable.view.*
import android.support.wearable.activity.WearableActivity
import android.util.Log

import java.util.ArrayList

import com.txusballesteros.widgets.*

import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

import kotlin.properties.Delegates
import kotlinx.android.synthetic.activity_main.*

import io.vokal.hightower.fragment.*
import io.vokal.hightower.model.*
import io.vokal.hightower.StatsListenerService

class MainActivity : WearableActivity() {
    companion object {
        public val TAG = "TF2Stats"
    }

    override protected fun onCreate(state: Bundle?) {
        super.onCreate(state)

        setContentView(R.layout.activity_main)
        setAmbientEnabled()

        indicator.setDotColor(getResources().getColor(R.color.trans_black))
        indicator.setDotColorSelected(getResources().getColor(R.color.trans_black))

        var pages = ArrayList<Fragment>()
        pages.add(createKillCountFragment())
        pages.add(createDominationCountFragment())
        pages.add(createWeeklyFragment())

        var adapter = StatsGridPagerAdapter(this, pages, getFragmentManager())
        pager.setAdapter(adapter)

        indicator.setPager(pager)
    }

    override public fun onEnterAmbient(ambientDetails: Bundle?) {
        super.onEnterAmbient(ambientDetails)
    }

    override public fun onExitAmbient() {
        super.onExitAmbient()
    }

    class StatsGridPagerAdapter(var context: Context, var pages: ArrayList<Fragment>, var fm: FragmentManager): FragmentGridPagerAdapter(fm) {

        override fun getFragment(row: Int, col: Int): Fragment {
            return pages.get(col)
        }

        override public fun getBackgroundForPage(row: Int, col: Int): Drawable {
            return ColorDrawable(context.getResources().getColor(R.color.white))
        }

        override fun getRowCount(): Int {
            return 1
        }

        override fun getColumnCount(row: Int): Int {
            return pages.size()
        }
    }
}
