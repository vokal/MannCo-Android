package io.vokal.hightower.fragment

import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import android.widget.TextView

import kotlin.properties.Delegates

import com.txusballesteros.widgets.*

import java.util.ArrayList

import io.vokal.hightower.R
import io.vokal.hightower.model.*

import io.realm.*

class KillCountFragment: Fragment() {

    private var graph: FitChart by Delegates.notNull()

    val realmConfig: RealmConfiguration by lazy {
        RealmConfiguration.Builder(activity)
                .name("tf2.realm")
                .schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build()
    }

    public val realm: Realm by lazy {
        Realm.getInstance(realmConfig)
    }

    override public fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        var v = inflater.inflate(R.layout.stats_grid, container, false);

        graph = v.findViewById(R.id.graph) as FitChart

        var resources = getActivity().getResources()
        val count = realm.where(KillCount::class.java).findFirst();

        var values = ArrayList<FitChartValue>()
        values.add(FitChartValue(count.kills.toFloat(), resources.getColor(R.color.primary)))
        values.add(FitChartValue(count.assists.toFloat(), resources.getColor(R.color.primary_dark)))
        values.add(FitChartValue(count.deaths.toFloat(), resources.getColor(R.color.accent)))
        
        graph.setValues(values)

        var main_text = v.findViewById(R.id.main_text) as TextView
        var sub_text = v.findViewById(R.id.sub_text) as TextView
        var opt_text = v.findViewById(R.id.opt_text) as TextView
        var empty_text = v.findViewById(R.id.empty) as TextView

        if (count.kills == 0 && count.assists == 0 && count.deaths == 0) {
            main_text.visibility = View.GONE
            sub_text.visibility = View.GONE
            opt_text.visibility = View.GONE
            empty_text.visibility = View.VISIBLE
        } else {
            main_text.text = "${count.kills} kills today"
            sub_text.text = "${count.assists} assists"
            opt_text.visibility = View.VISIBLE
            opt_text.text = "${count.deaths} deaths"
        }

        return v
    }
}

public fun createKillCountFragment(): KillCountFragment {
    return KillCountFragment()
}
