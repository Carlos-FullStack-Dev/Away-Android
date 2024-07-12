package com.plum.networkk.awmapplication.activities

//import retrofit2.Call
//import retrofit2.Response
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.animation.Easing

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.ChartWeeksAdapter_Statistics
import com.plum.networkk.awmapplication.databinding.ActivityStatisticsBinding
import com.plum.networkk.awmapplication.interfaces.RecyclerItemClick
import com.plum.networkk.awmapplication.models.ChartWeeksModel
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat


class StatisticsActivity : AppCompatActivity(), RecyclerItemClick {

    private lateinit var weeksItemsList: ArrayList<ChartWeeksModel>
    var vBinding: ActivityStatisticsBinding? = null
    private var chart: BarChart? = null
    var tvUnlockToday: TextView? = null
    var tvMinutesToday: TextView? = null
    var tvMinutesThisWeek: TextView? = null
    // on below line we are creating
    // variables for our bar chart
//    lateinit var barChart: BarChart

    // on below line we are creating
    // a variable for bar data set
    lateinit var barDataSet: BarDataSet

    // on below line we are creating array list for bar data
    lateinit var barEntriesList: ArrayList<BarEntry>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vBinding = ActivityStatisticsBinding.inflate(layoutInflater)
        val view: View = vBinding!!.getRoot()
        setContentView(view)
        tvUnlockToday = findViewById(R.id.tvUnlockToday) as TextView
        tvMinutesToday = findViewById(R.id.tvMinutesToday) as TextView
        tvMinutesThisWeek = findViewById(R.id.tvMinutesThisWeek) as TextView

        setSupportActionBar(vBinding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        vBinding!!.toolbarTitle.text = "${AppController.firstName}'s STATISTICS".toUpperCase()

        vBinding!!.tabLayout.addTab(
            vBinding!!.tabLayout.newTab().setText(resources.getText(R.string.txt_today))
        )
        vBinding!!.tabLayout.addTab(
            vBinding!!.tabLayout.newTab().setText(resources.getText(R.string.txt_week))
        )
        vBinding!!.tabLayout.addTab(
            vBinding!!.tabLayout.newTab().setText(resources.getText(R.string.txt_month))
        )
        for (i in 0 until vBinding!!.tabLayout.getTabCount()) {
            val tab = vBinding!!.tabLayout.getTabAt(i)
            val relativeLayout = LayoutInflater.from(this)
                .inflate(R.layout.custom_tab_item, vBinding!!.tabLayout, false) as RelativeLayout
            val tabTextView =
                relativeLayout.findViewById<View>(R.id.tab_title) as TextView
            tabTextView.text = tab!!.text
            tab.customView = relativeLayout
        }
        vBinding!!.tabLayout.selectTab(vBinding!!.tabLayout.getTabAt(0))

        vBinding!!.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                when (tab.position) {
                    0 -> {
                        getStatisticData(false)
                    }
                    1 -> {
                        getStatisticData(false)
                    }
                    2 -> {
                        getStatisticData(false)
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
        chart = vBinding!!.verticalbarChart
//        chart!!.drawOrder = arrayOf<CombinedChart.DrawOrder>(
////            CombinedChart.DrawOrder.BAR
//            CombinedChart.DrawOrder.LINE
//        )

        weeksItemsList = ArrayList<ChartWeeksModel>()

        var adapter =
            ChartWeeksAdapter_Statistics(this@StatisticsActivity, weeksItemsList!!)
        vBinding!!.weekTopRecyclerView.adapter = adapter
        getStatisticData(false)

    }
    fun getStatisticData(forceLoad: Boolean) {
//        progressDialog()
        tvUnlockToday?.text = "${AppController.statistics?.unlocksToday}"
        tvMinutesToday?.text = "${AppController.statistics?.minutesToday}"
        tvMinutesThisWeek?.text = "${AppController.statistics?.minutesThisWeek}"
        getDataInList(vBinding!!.tabLayout.selectedTabPosition)
    }
    fun getDataInList(index: Int) {
        var max: Long = 0
        weeksItemsList.clear()
        Log.i("checking Value", "$AppController.statistics")
        when (index) {
            0 -> {
                AppController.statistics!!.last24Hour.forEach {

                    if (max < it.value)
                        max = it.value
                    weeksItemsList.add(ChartWeeksModel(it.value, it.key, it.key))
                }
            }
            1 -> {

                AppController.statistics!!.monthArray.forEach {
                    var dateFormatted = DateTime.now()
                    var date = DateTime.parse("${it.key} 01:12:12", DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss"))
                    dateFormatted = dateFormatted.minusDays(7)
                    if (date.isAfter(dateFormatted)) {
                        if (max < it.value)
                            max = it.value

                        weeksItemsList.add(ChartWeeksModel(it.value, it.key, it.key))
                    }
                }
            }
            2 -> {

                AppController.statistics!!.monthArray.forEach {
                    if (max < it.value)
                        max = it.value
                    weeksItemsList.add(ChartWeeksModel(it.value, it.key, it.key))
                }
            }
        }
        Log.i("weekvalue", "$weeksItemsList")
        var arr = ArrayList<BarEntry>()
        var x = 0f
        weeksItemsList.forEach {
            var y = it.weekValue!!.toFloat()
            var entry = BarEntry(x, y)
            x += 1f
            arr.add(entry)
        }

        // on below line we are initializing
        // our variable with their ids.

        // on below line we are creating a new bar data set
        barDataSet = BarDataSet(arr, AppController.firstName)
        barDataSet.setColor(resources.getColor(R.color.app_green_color))
        barDataSet.valueTextSize = 20f

        // on below line we are adding bar data set to bar data
        var data = BarData(barDataSet)

        // on below line we are setting data to our chart
        chart!!.data = data

        // on below line we are setting description enabled.
        chart!!.description.isEnabled = false

        // on below line setting x axis
        var xAxis = chart!!.xAxis

        // below line is to set value formatter to our x-axis and
        // we are adding our days to our x axis.

        var xValue = arrayOf<String>()
        if (vBinding!!.tabLayout.selectedTabPosition == 0){
            val pattern = "dd/hh a"
             val mFormat = SimpleDateFormat(pattern)
             val inputFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
            weeksItemsList.forEach {
                var timeVal = mFormat.format(inputFormat.parse(it.startDate))
                xValue +=timeVal.toString()
            }
        }else{
            val pattern = "dd MMM"
             val mFormat = SimpleDateFormat(pattern)

             val inputFormat = SimpleDateFormat("yyyy-MM-d")
            weeksItemsList.forEach {
                var timeVal = mFormat.format(inputFormat.parse(it.startDate))
                xValue +=timeVal.toString()
            }
        }
        xAxis.valueFormatter = IndexAxisValueFormatter(xValue)
        xAxis.setCenterAxisLabels(false)


        // below line is to set position
        // to our x-axis to bottom.
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        // below line is to set granularity
        // to our x axis labels.
        xAxis.setGranularity(1f)

        // below line is to enable
        // granularity to our x axis.
        xAxis.setGranularityEnabled(true)

        // below line is to make our
        // bar chart as draggable.
        chart!!.setDragEnabled(true)
        chart!!.setTouchEnabled(true)
        chart!!.setScaleEnabled(true)
        chart!!.setPinchZoom(true)

        // below line is to make visible
        // range for our bar chart.
        chart!!.setVisibleXRangeMinimum(3f)
        chart!!.setVisibleXRangeMaximum(4f)
        // we are setting width of
        // bar in below line.
        data.barWidth = 0.2f

        // below line is to set minimum
        // axis to our chart.
        chart!!.xAxis.labelRotationAngle = 0.5f
        chart!!.xAxis.mAxisMaximum = arr.size.toFloat()
        chart!!.axisRight.axisMinimum = 0f
        chart!!.axisLeft.axisMinimum = 0f
        chart!!.xAxis.setDrawGridLines(false)
        chart!!.axisLeft.setDrawGridLines(false)
        chart!!.axisRight.setDrawGridLines(false)
        chart!!.axisRight.setDrawLabels(false)
        chart!!.setDrawValueAboveBar(false)
//        chart!!.axisRight.textSize = 30f
        chart!!.axisLeft.textSize = 20f
        chart!!.xAxis.textSize = 20f
        chart!!.setFitBars(true)
        chart!!.axisLeft.axisMinimum = 0.1f

        chart!!.animate()

        chart!!.invalidate()
    }
    var progressDialog: Dialog? = null
    fun progressDialog() {
        if (progressDialog != null) {
            if (progressDialog!!.isShowing)
                progressDialog!!.dismiss()
        }

        progressDialog = Dialog(this@StatisticsActivity)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.setCancelable(false)
        progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window: Window = progressDialog!!.window!!
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        progressDialog!!.setContentView(R.layout.progress_dialog)
        progressDialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        progressDialog!!.show()
    }
    fun problemInAPIDialog() {
        val dialog = Dialog(this@StatisticsActivity/*, R.style.SearchFieldSetterDialog*/)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window: Window = dialog.window!!
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setContentView(R.layout.error_dialog)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val dialogMessage = dialog.findViewById(R.id.dialogMessage) as TextView

        dialogMessage.text = "" + resources.getString(R.string.txt_error_fetching_data)

        val btnSend = dialog.findViewById(R.id.btnSend) as RelativeLayout

        btnSend.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
    fun converterTodayTime(){

    }
    inner class MyXAxisFormatter : ValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {

            return "\n\n" + weeksItemsList.get(value.toInt()).toString()
//            return (graphValues.getOrNull(value.toInt()) ?: value.toString())
        }
    }


    override fun OnRecyclerViewItemClick(position: Int) {

//        configureChartAppearance(weeksItemsList!!.get(position).startDate!!)

    }


}