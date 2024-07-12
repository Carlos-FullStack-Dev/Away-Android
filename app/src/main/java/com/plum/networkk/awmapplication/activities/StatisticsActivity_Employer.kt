package com.plum.networkk.awmapplication.activities

//import com.plum.networkk.awmapplication.apis.ApisSericesInterface
//import com.plum.networkk.awmapplication.apis.RetrofitServiceSignIn
//import retrofit2.Call
//import retrofit2.Response
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.databinding.ActivityStatisticsBinding
import com.plum.networkk.awmapplication.interfaces.RecyclerItemClick
import com.plum.networkk.awmapplication.models.ChartWeeksModel
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import java.text.SimpleDateFormat
import kotlin.random.Random


class StatisticsActivity_Employer : AppCompatActivity() {

//    var dataSet: ArrayList<ILineDataSet>? = null
//
//    //    private lateinit var weeksItemsList: ArrayList<ChartWeeksModel>
//    var vBinding: ActivityStatisticsBinding? = null
//
//    private var chart: LineChart? = null
//
//    var tvUnlockToday: TextView? = null
//    var tvMinutesToday: TextView? = null
//    var tvMinutesThisWeek: TextView? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_statistics)
//
//        vBinding = ActivityStatisticsBinding.inflate(layoutInflater)
//        val view: View = vBinding!!.getRoot()
//        setContentView(view)
//
//        tvMinutesThisWeek = view.findViewById(R.id.tvMinutesThisWeek) as TextView
//  tvUnlockToday = view.findViewById(R.id.tvUnlockToday) as TextView
////        tvMinutesToday = view.findViewById(R.id.tvMinutesToday) as TextView
////
//        setSupportActionBar(vBinding!!.toolbar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
////        supportActionBar!!.setTitle("JSON'S STATISTICS")
//        vBinding!!.toolbarTitle.text = "STATISTICS".toUpperCase()
////        vBinding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorNearbyPlaces1))
//
//
////        vBinding.tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
////        vBinding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
////        vBinding.tabLayout.tabTextColors =
////            ContextCompat.getColorStateList(this, android.R.color.white)
////        vBinding.tabLayout.isInlineLabel = true
//
//
//        vBinding!!.tabLayout.addTab(
//            vBinding!!.tabLayout.newTab().setText(resources.getText(R.string.txt_today))
//        )
//        vBinding!!.tabLayout.addTab(
//            vBinding!!.tabLayout.newTab().setText(resources.getText(R.string.txt_week))
//        )
//        vBinding!!.tabLayout.addTab(
//            vBinding!!.tabLayout.newTab().setText(resources.getText(R.string.txt_month))
//        )
//
//        vBinding!!.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab) {
//
//                when (tab.position) {
//                    0 -> {
//                        getStatisticData(false)
//                    }
//                    1 -> {
//                        getStatisticData(false)
//                    }
//                    2 -> {
//                        getStatisticData(false)
//                    }
//                }
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab) {}
//            override fun onTabReselected(tab: TabLayout.Tab) {}
//        })
//
//
//        for (i in 0 until vBinding!!.tabLayout.getTabCount()) {
//            val tab = vBinding!!.tabLayout.getTabAt(i)
//            val relativeLayout = LayoutInflater.from(this)
//                .inflate(R.layout.custom_tab_item, vBinding!!.tabLayout, false) as RelativeLayout
//            val tabTextView =
//                relativeLayout.findViewById<View>(R.id.tab_title) as TextView
//            tabTextView.text = tab!!.text
//            tab.customView = relativeLayout
//        }
//
//        vBinding!!.tabLayout.selectTab(vBinding!!.tabLayout.getTabAt(0))
//
////        chart = vBinding!!.verticalbarChart
////        chart!!.drawOrder = arrayOf<CombinedChart.DrawOrder>(
//////            CombinedChart.DrawOrder.BAR
////            CombinedChart.DrawOrder.LINE
////        )
////        var adapter =
////            Employer_Adapter_Statistics(this@StatisticsActivity_Employer, weeksItemsList!!)
////        vBinding!!.weekTopRecyclerView.adapter = adapter
////
////        vBinding!!.weekTopRecyclerView.scrollToPosition(weeksItemsList!!.size)
////        vBinding!!.weekTopRecyclerView.smoothScrollToPosition(weeksItemsList!!.size)
//
//
////        getCurrentDateWithWeekData()
////        printSunToSatAllWeeksOfYear()
////        println("weeks are = "+WEEKS())
//
//        getStatisticData(false)
//    }
//
//    fun getStatisticData(forceLoad: Boolean) {
//        var strMinW: Long = 0
//        var strMinT: Long = 0
//        var strUnlockT: Long = 0
//        AppController.employeeList.forEach {
//            strMinT += it.statistics!!.minutesToday
//            strMinW += it.statistics!!.minutesThisWeek
//            strUnlockT += it.statistics!!.unlocksToday
//        }
//        tvUnlockToday?.text = "$strUnlockT"
//        tvMinutesToday?.text = "$strMinT"
//        tvMinutesThisWeek?.text = "$strMinW"
//        getDataInList(vBinding!!.tabLayout.selectedTabPosition)
//    }
//
//    fun getDataInList(index: Int) {
//        dataSet = ArrayList()
//        var wiList: ArrayList<ChartWeeksModel> = ArrayList()
//        for ( i in 0 until AppController.employeeList.size){
//            var stats = AppController.employeeList[i].statistics
//            var max: Long = 0
//
//            var weeksItemsList = ArrayList<ChartWeeksModel>()
//            when (index) {
//                0 -> {
//                    stats!!.last24Hour.forEach {
//                        if (max < it.value)
//                            max = it.value
//                        weeksItemsList.add(ChartWeeksModel(it.value, it.key, it.key))
//                    }
//                }
//                1 -> {
//
//                    stats!!.monthArray.forEach {
//                        var date = DateTime.parse("${it.key} 01:12:12", DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss"))
//                        var dateFormatted = DateTime.now()
//                        dateFormatted = dateFormatted.minusDays(7)
//                        if (date.isAfter(dateFormatted)) {
//                            if (max < it.value)
//                                max = it.value
//
//                            weeksItemsList.add(ChartWeeksModel(it.value, it.key, it.key))
//                        }
//                    }
//                }
//                2 -> {
//
//                    stats!!.monthArray.forEach {
//                        if (max < it.value)
//                            max = it.value
//                        weeksItemsList.add(ChartWeeksModel(it.value, it.key, it.key))
//                    }
//                }
//            }
//            wiList = weeksItemsList
//            var arr = ArrayList<Entry>()
//            var x = 0f
//            weeksItemsList.forEach {
//                var y = it.weekValue!!.toFloat()
//                var entry = BarEntry(x, y, it)
//                x += 1f
//                arr.add(entry)
//            }
//            var ds = LineDataSet(arr, AppController.employeeList[i].firstName)
//
//            ds.lineWidth = 3f
////        dataSet.fillAlpha = R.color.red
//
//            var color = Color.argb(Random.nextInt(50,253),Random.nextInt(253), Random.nextInt(253), Random.nextInt(253))
//            ds.setCircleColor(color)
//            ds.color = color
////            ds.fillColor = Color.rgb(132, 208, 51)
////            ds.fillAlpha = 100
//
////        dataSet.setDrawValues(true)
////            ds.setDrawFilled(true)
//            dataSet!!.add(ds)
//        }
//        var data = LineData(dataSet)
//        data.setValueTextColor(R.color.app_green_color)
//        chart!!.xAxis.labelRotationAngle = 0.5f
//        chart!!.xAxis.axisMinimum = 0.0f
////        chart!!.xAxis.mAxisMaximum = arr.size.toFloat()
//        chart!!.xAxis.setLabelCount(4, true)
//        chart!!.setGridBackgroundColor(R.color.employee_dashboard_bg_color)
//        chart!!.axisRight.isEnabled = false
//        chart!!.setTouchEnabled(true)
//        chart!!.setPinchZoom(true)
//        chart!!.description.text = "Date Time"
//        chart!!.setNoDataText("No Data yet!")
//        chart!!.xAxis.position = XAxis.XAxisPosition.BOTTOM
//        vBinding!!.verticalbarChart.setTouchEnabled(true)
//        vBinding!!.verticalbarChart.setScaleEnabled(true)
//        vBinding!!.verticalbarChart.setPinchZoom(true)
//        vBinding!!.verticalbarChart.axisLeft.setDrawAxisLine(true)
//        vBinding!!.verticalbarChart.xAxis.setDrawAxisLine(true)
//        vBinding!!.verticalbarChart.xAxis.setDrawGridLines(false)
//
////        vBinding!!.verticalbarChart.getLegend().setEnabled(false)
////        chart!!.zoom(50f, 0.5f, 10f, 0.5f)
//        if (vBinding!!.tabLayout.selectedTabPosition == 0) {
//            chart!!.xAxis.valueFormatter = object : ValueFormatter() {
//                val pattern = "HH:mm"
//                private val mFormat = SimpleDateFormat(pattern)
//
//                private val inputFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ss")
//                override fun getFormattedValue(value: Float): String {
////                Log.i("Date --> ","$value")
//                    return mFormat.format(inputFormat.parse(wiList[value.toInt()].startDate!!)!!)
//
//                }
//            }
//
//        } else {
//            chart!!.xAxis.valueFormatter = object : ValueFormatter() {
//                val pattern = "dd MMM"
//                private val mFormat = SimpleDateFormat(pattern)
//
//                private val inputFormat = SimpleDateFormat("yyyy-MM-d")
//                override fun getFormattedValue(value: Float): String {
////                Log.i("Date --> ","$value")
//                    return mFormat.format(inputFormat.parse(wiList[value.toInt()].startDate!!)!!)
//
//                }
//            }
//
//        }
//        chart!!.animateX(1800, Easing.Linear)
//        chart!!.data = data
//    }
//
//    var progressDialog: Dialog? = null
//    fun progressDialog() {
//        if (progressDialog != null) {
//            if (progressDialog!!.isShowing)
//                progressDialog!!.dismiss()
//        }
//
//        progressDialog = Dialog(this@StatisticsActivity_Employer)
//        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        progressDialog!!.setCanceledOnTouchOutside(false)
//        progressDialog!!.setCancelable(false)
//        progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        val window: Window = progressDialog!!.window!!
////        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        progressDialog!!.setContentView(R.layout.progress_dialog)
//        progressDialog!!.window!!.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//
//        progressDialog!!.show()
//    }
//
//    fun problemInAPIDialog() {
//        val dialog = Dialog(this@StatisticsActivity_Employer/*, R.style.SearchFieldSetterDialog*/)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setCanceledOnTouchOutside(false)
//        dialog.setCancelable(false)
//        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//        val window: Window = dialog.window!!
////        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
//        dialog.setContentView(R.layout.error_dialog)
//        dialog.window!!.setLayout(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            ViewGroup.LayoutParams.WRAP_CONTENT
//        )
//
//        val dialogMessage = dialog.findViewById(R.id.dialogMessage) as TextView
//
//        dialogMessage.text = "" + resources.getString(R.string.txt_error_fetching_data)
//
//        val btnSend = dialog.findViewById(R.id.btnSend) as RelativeLayout
//
//        btnSend.setOnClickListener {
//            dialog.dismiss()
//        }
//
//        dialog.show()
//    }
//
//    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
//        if (menuItem.getItemId() === android.R.id.home) {
//            finish()
//        }
//        return super.onOptionsItemSelected(menuItem)
//    }
//
//    override fun OnRecyclerViewItemClick(position: Int) {
//
//    }

}
