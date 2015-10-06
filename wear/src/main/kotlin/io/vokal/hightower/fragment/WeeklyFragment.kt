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

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.components.XAxis.XAxisPosition

import io.vokal.hightower.R
import io.vokal.hightower.model.*

class WeeklyFragment: Fragment() {

    private var chart: BarChart by Delegates.notNull()
    private var label: TextView by Delegates.notNull()

    override public fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?  {
        var v = inflater.inflate(R.layout.weekly_stats, container, false);

        chart = v.findViewById(R.id.chart) as BarChart
        label = v.findViewById(R.id.total_kills) as TextView

        label.setText("${25} kills today")

        var days = ArrayList<String>()
        days.add("S")
        days.add("M")
        days.add("T")
        days.add("W")
        days.add("T")
        days.add("F")
        days.add("S")

        var values = ArrayList<BarEntry>()
        values.add(BarEntry(10f, 0))
        values.add(BarEntry(20f, 1))
        values.add(BarEntry(30f, 2))
        values.add(BarEntry(20f, 3))
        values.add(BarEntry(40f, 4))
        values.add(BarEntry(10f, 5))
        values.add(BarEntry(30f, 6))

        var dataset = BarDataSet(values, "kills")
        dataset.setColors(intArrayOf(R.color.primary_dark, 
            R.color.accent, 
            R.color.primary, 
            R.color.primary_dark, 
            R.color.accent, 
            R.color.primary), getActivity())
        dataset.setDrawValues(false)
        dataset.setBarSpacePercent(50f)

        var yAxis = chart.getAxisLeft()
        yAxis.setEnabled(false)
        yAxis.setDrawGridLines(false)
        yAxis.setDrawAxisLine(false)
        yAxis.setDrawLabels(false)

        yAxis = chart.getAxisRight()
        yAxis.setEnabled(false)
        yAxis.setDrawGridLines(false)
        yAxis.setDrawAxisLine(false)
        yAxis.setDrawLabels(false)

        var xAxis = chart.getXAxis()
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.setPosition(XAxisPosition.BOTTOM)
        xAxis.setLabelsToSkip(0)
        xAxis.setTextSize(6f)

        var legend = chart.getLegend()
        legend.setEnabled(false)

        chart.setTouchEnabled(false)
        chart.setBackgroundColor(getActivity().getResources().getColor(R.color.white))
        chart.setDrawGridBackground(false)
        chart.setDrawBorders(false)
        chart.setScaleEnabled(false)
        chart.setHighlightEnabled(false)
        chart.setData(BarData(days, dataset))
        chart.setDescription("")
        chart.animateY(1000)

        return v
    }
}

public fun createWeeklyFragment(): WeeklyFragment {
    return WeeklyFragment()
}
