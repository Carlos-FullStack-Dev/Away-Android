package com.plum.networkk.awmapplication.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.ChartWeeksAdapter
import com.plum.networkk.awmapplication.databinding.ActivityAwayHoursBinding
import com.plum.networkk.awmapplication.interfaces.RecyclerItemClick
import com.plum.networkk.awmapplication.models.*
import com.plum.networkk.awmapplication.utils.DoubleXLabelAxisRenderer2
import org.joda.time.DateTime
import org.joda.time.LocalDate
import org.joda.time.Weeks
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class HoursAwayActivity : AppCompatActivity(), RecyclerItemClick {

    var weeksItemsList: ArrayList<ChartWeeksModel>? = null
    var vBinding: ActivityAwayHoursBinding? = null
    private val MAX_X_VALUE = 7
    private val MAX_Y_VALUE = 50
    private val MIN_Y_VALUE = 5
    private val SET_LABEL = ""
    private val DAYS =
        arrayOf("SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN", "MON", "TUE", "WED")


    private val graphValues =
        arrayOf(240, 320, 301, 130, 112, 219, 140)

    private var chart: BarChart? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_statistics)

        vBinding = ActivityAwayHoursBinding.inflate(layoutInflater)
        val view: View = vBinding!!.getRoot()
        setContentView(view)

        setSupportActionBar(vBinding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setTitle("AWAY HOURS")
        vBinding!!.toolbarTitle.text = "AWAY HOURS"
//        vBinding.toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorNearbyPlaces1))


//        vBinding.tabLayout.setSelectedTabIndicatorColor(Color.WHITE)
//        vBinding.tabLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.white))
//        vBinding.tabLayout.tabTextColors =
//            ContextCompat.getColorStateList(this, android.R.color.white)
//        vBinding.tabLayout.isInlineLabel = true


        vBinding!!.tabLayout.addTab(
            vBinding!!.tabLayout.newTab().setText(resources.getText(R.string.txt_today))
        )
        vBinding!!.tabLayout.addTab(
            vBinding!!.tabLayout.newTab().setText(resources.getText(R.string.txt_week))
        )
        vBinding!!.tabLayout.addTab(
            vBinding!!.tabLayout.newTab().setText(resources.getText(R.string.txt_month))
        )

//        val adapter = TabsPagerAdapter(supportFragmentManager, lifecycle, numberOfTabs)
//        tabs_viewpager.adapter = adapter


        vBinding!!.tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {

                when (tab.position) {
                    0 -> {
//                        Toast.makeText(
//                            this@HoursAwayActivity,
//                            "Tab " + vBinding!!.tabLayout.getSelectedTabPosition(),
//                            Toast.LENGTH_LONG
//                        ).show()
                    }
                    1 -> {
//                        Toast.makeText(
//                            this@HoursAwayActivity,
//                            "Tab " + vBinding!!.tabLayout.getSelectedTabPosition(),
//                            Toast.LENGTH_LONG
//                        ).show()
                    }
                    2 -> {
//                        Toast.makeText(
//                            this@HoursAwayActivity,
//                            "Tab " + vBinding!!.tabLayout.getSelectedTabPosition(),
//                            Toast.LENGTH_LONG
//                        ).show()
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })


        for (i in 0 until vBinding!!.tabLayout.getTabCount()) {
            val tab = vBinding!!.tabLayout.getTabAt(i)
            val relativeLayout = LayoutInflater.from(this)
                .inflate(R.layout.custom_tab_item, vBinding!!.tabLayout, false) as RelativeLayout
            val tabTextView =
                relativeLayout.findViewById<View>(R.id.tab_title) as TextView
            tabTextView.text = tab!!.text
            tab.customView = relativeLayout
            tab.select()
        }

        var currentFirstDAYofWeek = getFirstDayOfWeekDate()

        chart = vBinding!!.verticalbarChart
        val data: BarData = createChartData()!!
        configureChartAppearance(currentFirstDAYofWeek!!)
        prepareChartData(data)


        vBinding!!.verticalbarChart!!.data = data.apply {
            barWidth = 0.5f
        }


        getHeaderAndChild()



        weeksItemsList = ArrayList<ChartWeeksModel>()

        var adapter =
            ChartWeeksAdapter(this@HoursAwayActivity, weeksItemsList!!)
        vBinding!!.weekTopRecyclerView.adapter = adapter

        vBinding!!.weekTopRecyclerView.scrollToPosition(weeksItemsList!!.size)
        vBinding!!.weekTopRecyclerView.smoothScrollToPosition(weeksItemsList!!.size)

//        getCurrentDateWithWeekData()
//        printSunToSatAllWeeksOfYear()
//        println("weeks are = "+WEEKS())


    }


    //    var categoryMap: Map<String, List<OverAgesDataModel>>?=null
    private fun getHeaderAndChild() {

        /* var overAgesDataModel = ArrayList<OverAgesDataModel>()
         overAgesDataModel.add(OverAgesDataModel("Heading1"))
         overAgesDataModel.add(OverAgesDataModel("Heading2"))

         var childDataModel = OverAgesChildDataModel("March 2, 2017", "32 minutes", "2/3 unlocks")
         var childDataList = ArrayList<OverAgesChildDataModel>()
         childDataList.add(childDataModel)*/

//        var categoryMap: HashMap<String, List<String>> = HashMap<String, List<String>>()
//        categoryMap.put("Heading1", subArrayList)
//        categoryMap.put("Heading2", subArrayList)
//        categoryMap.put("Heading3", subArrayList)


        var headerList1 = OverAgesDataModel("Json Newberg", "45.42 Hrs")
        var headerList2 = OverAgesDataModel("Nathan Smith", "45.42 Hrs")

        vBinding!!.expandablePlaceHolder.addView(HeaderView(this, headerList1))
        vBinding!!.expandablePlaceHolder.addView(
            ChildView_Away_Hours(
                this,
                AwayHoursChildDataModel(
                    "Away off",
                    "02/12/7",
                    "10:00 AM",
                    "123 villanueva st, chico ca,  95973"
                )
            )
        )

        vBinding!!.expandablePlaceHolder.addView(
            ChildView_Away_Hours(
                this,
                AwayHoursChildDataModel(
                    "Away off",
                    "02/12/7",
                    "10:00 AM",
                    "123 villanueva st, chico ca,  95973"
                )
            )
        )

        vBinding!!.expandablePlaceHolder.addView(HeaderView(this, headerList2))
        vBinding!!.expandablePlaceHolder.addView(
            ChildView_Away_Hours(
                this,
                AwayHoursChildDataModel(
                    "Away off",
                    "02/12/7",
                    "10:00 AM",
                    "123 villanueva st, chico ca,  95973"
                )
            )
        )
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }


    private fun configureChartAppearance(givenDate: String) {
        chart!!.description.isEnabled = false
        chart!!.setDrawValueAboveBar(false)
        val xAxis = chart!!.xAxis
        val yAxis = chart!!.axisLeft
        xAxis.setAxisLineWidth(1f)
//        xAxis.axisLineWidth=6f

        // XAxis
        xAxis.setPosition(XAxis.XAxisPosition.BOTH_SIDED)
        xAxis.setGranularity(1f)
        val xAxiss: XAxis = chart!!.getXAxis()

        val axisLeft = chart!!.axisLeft
        axisLeft.granularity = 10f
        axisLeft.axisMinimum = 0f

        val axisRight = chart!!.axisRight
        axisRight.granularity = 10f
        axisRight.axisMinimum = 0f
//        axisRight.setDrawLabels(false)
//        xAxis.setCenterAxisLabels(false);
        chart!!.setDrawGridBackground(false)

        chart!!.axisLeft.isEnabled = false
        chart!!.axisRight.isEnabled = false
        chart!!.description.isEnabled = false
//        val leftAxis = chart!!.axisLeft


        vBinding!!.verticalbarChart.setTouchEnabled(false)
        vBinding!!.verticalbarChart.setScaleEnabled(false)
        vBinding!!.verticalbarChart.setPinchZoom(false)
        vBinding!!.verticalbarChart.isDoubleTapToZoomEnabled = false
        vBinding!!.verticalbarChart.isDragEnabled = false
        vBinding!!.verticalbarChart.axisLeft.setDrawAxisLine(false)
        vBinding!!.verticalbarChart.xAxis.setDrawAxisLine(false)
        vBinding!!.verticalbarChart.xAxis.setDrawGridLines(false)

        vBinding!!.verticalbarChart.getLegend().setEnabled(false)

        vBinding!!.expandablePlaceHolder.setOnClickListener {
//            Toast.makeText(applicationContext, "Clixcked", it.id).show()
        }

        chart!!.setExtraBottomOffset(15f);
        chart!!.setExtraTopOffset(15f);



        chart!!.xAxis.valueFormatter = MyXAxisFormatter()

        val days = arrayOfNulls<String>(7)
        val dfNormalDate: DateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
//        val newDate = dfNormalDate.parse(CurrentWeekStart_normalDate!!)
        val newDate = dfNormalDate.parse(givenDate!!)

        var calendar = Calendar.getInstance()
        calendar.time = newDate


        chart!!.setXAxisRenderer(
            DoubleXLabelAxisRenderer2(
                chart!!.getViewPortHandler(),
                chart!!.getXAxis(),
                chart!!.getTransformer(YAxis.AxisDependency.LEFT),
                object : IAxisValueFormatter {


                    override fun getFormattedValue(value: Float, axis: AxisBase?): String {

                        val format = SimpleDateFormat("EEE\n\ndd")
                        days[value.toInt()] = format.format(calendar.getTime())
                        calendar.add(Calendar.DATE, 1)

                        return "" + days!![value.toInt()]

                    }
                })
        )

        chart!!.invalidate()
    }


    private fun createChartData(): BarData? {

        val values: ArrayList<BarEntry> = ArrayList()
        for (i in 0 until MAX_X_VALUE) {
            val x = i.toFloat()
//            val y: Float = 10.0f//Util().randomFloatBetween(MIN_Y_VALUE, MAX_Y_VALUE)
            val y: Double = Math.random() * (MAX_Y_VALUE - MIN_Y_VALUE)
//            val f = y as Float
            val f = (y as Double).toFloat()
//            values.add(BarEntry(x, 30f))
            val yValue: Float = graphValues.get(i).toFloat()
            values.add(BarEntry(x, yValue))
        }


        val set1 = BarDataSet(values, SET_LABEL)
        val dataSets: ArrayList<IBarDataSet> = ArrayList()
        dataSets.add(set1)

        set1.setDrawValues(false);

        val colorsList = mutableListOf(
            ContextCompat.getColor(
                vBinding!!.verticalbarChart.getContext(),
                R.color.char_bar_color_yellow
            ),
            ContextCompat.getColor(
                vBinding!!.verticalbarChart.getContext(),
                R.color.char_bar_color_red
            ),
            ContextCompat.getColor(
                vBinding!!.verticalbarChart.getContext(),
                R.color.char_bar_color_red
            ),
            ContextCompat.getColor(
                vBinding!!.verticalbarChart.getContext(),
                R.color.char_bar_color_green
            ),
            ContextCompat.getColor(
                vBinding!!.verticalbarChart.getContext(),
                R.color.char_bar_color_green
            ),
            ContextCompat.getColor(
                vBinding!!.verticalbarChart.getContext(),
                R.color.char_bar_color_yellow
            ),
            ContextCompat.getColor(
                vBinding!!.verticalbarChart.getContext(),
                R.color.char_bar_color_red
            ),
            ContextCompat.getColor(
                vBinding!!.verticalbarChart.getContext(),
                R.color.char_bar_color_red
            ),
            ContextCompat.getColor(
                vBinding!!.verticalbarChart.getContext(),
                R.color.char_bar_color_red
            ),
            ContextCompat.getColor(
                vBinding!!.verticalbarChart.getContext(),
                R.color.char_bar_color_red
            ),
            ContextCompat.getColor(
                vBinding!!.verticalbarChart.getContext(),
                R.color.char_bar_color_red
            )
        )


        vBinding!!.verticalbarChart!!.data = BarData(set1).apply {
            barWidth = 0.5f
        }

        set1.setColors(colorsList)


        return BarData(dataSets)
    }

    private fun prepareChartData(data: BarData) {
        data.setValueTextSize(12f)
        chart!!.data = data
        chart!!.invalidate()
    }


    fun getFirstDayOfWeekDate(): String {
        // get Current Week of the year
        // get Current Week of the year
        var calendar = Calendar.getInstance()
//        Log.v("Current Week", java.lang.String.valueOf(calendar.get(Calendar.WEEK_OF_YEAR)))
        val current_week: Int = calendar.get(Calendar.WEEK_OF_YEAR)
        val week_start_day: Int =
            calendar.getFirstDayOfWeek() // this will get the starting day os week in integer format i-e 1 if monday

        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
        System.out.println("Current week = " + Calendar.DAY_OF_WEEK)

        // Print dates of the current week starting on Sunday
        val dfOnlyDay: DateFormat = SimpleDateFormat("-dd", Locale.getDefault())
        val dfMonthDate: DateFormat = SimpleDateFormat("MMMM dd", Locale.getDefault())
        val dfNormalDate: DateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
        var startDate = ""
        var endDate = ""

        var CurrentWeekStart_normalDate = dfNormalDate.format(calendar.getTime())
        startDate = dfMonthDate.format(calendar.getTime())
        calendar.add(Calendar.DATE, 6)
        endDate = dfOnlyDay.format(calendar.getTime())


        println("Normal Start Date = $CurrentWeekStart_normalDate")
        println("Start Date = $startDate$endDate")

        return CurrentWeekStart_normalDate!!
    }


   /* fun getWHoleWeekDates(): String {

        val dfNormalDate: DateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())

        val newDate = dfNormalDate.parse(CurrentWeekStart_normalDate!!)

        val df = SimpleDateFormat("EEE\n\ndd")
        val date: String = df.format(newDate)

        return date

    }*/

    fun getWeekDateListForChart(dateOfWeek: String): Array<String?>? {
        val dfNormalDate: DateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())

//        val newDate = dfNormalDate.parse(CurrentWeekStart_normalDate!!)
        val newDate = dfNormalDate.parse(dateOfWeek!!)

        var calendar = Calendar.getInstance()
        calendar.time = newDate

        val format = SimpleDateFormat("EEE\n\ndd")
        val days = arrayOfNulls<String>(7)
        for (i in 0..6) {
            days[i] = format.format(calendar.getTime())
            calendar.add(Calendar.DATE, 1)
        }
        return days
    }


//    fun printSunToSatAllWeeksOfYear() {
//
//        var calendar = Calendar.getInstance()
//        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
//        calendar.set(Calendar.YEAR, 2021)
//        calendar.set(Calendar.MONTH, 0)
////        calendar.set(2021, 0, 1);
//
//        val customeStartDate = DateTime(calendar)
//
//
//        var calendar2 = Calendar.getInstance()
//        calendar2.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
////        calendar2.set(Calendar.YEAR, 2021)
////        calendar2.set(Calendar.MONTH, 6)
////        calendar2.set(Calendar.DAY_OF_MONTH, 21)
//        calendar2.set(2021, 7, 0);
//        val customeEndDate = DateTime(calendar2)
//
//
//        val df = SimpleDateFormat("MMMM dd")
//        val dfEndDate = SimpleDateFormat("-dd")
//        val dfNormalDate: DateFormat = SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault())
////        val weekPeriod = Period().withWeeks(1)
//        val weekPeriod = Period().withDays(7)
//
////        val startDate = DateTime(2021, 1, 1, 0, 0, 0, 0)
//
//
////        val endDate = DateTime(2021, 7, 30, 0, 0, 0, 0)
//        var i = Interval(customeStartDate, weekPeriod)
//        while (i.end.isBefore(customeEndDate)) {
//
//            weeksItemsList!!.add(
//                ChartWeeksModel(
//                    "" + (df.format(i.start.toDate()) + "" + dfEndDate.format(
//                        i.end.minusDays(1).toDate()
//                    )),
//                    dfNormalDate.format(i.start.toDate()),
//                    ""
//
//                )
//            )
//
//            println(
//                "All weeks are: " + df.format(i.start.toDate()) +
//                        "" + dfEndDate.format(
//                    i.end.minusDays(1).toDate()
//                    /*i.end.minusMillis(1).toDate()*/
//                )
//            )
//
//            i = Interval(
//                i.start.plus(weekPeriod), weekPeriod
//            )
//        }
//
//        weeksItemsList!!.add(
//            ChartWeeksModel(
//                "",
//                "",
//                ""
//            )
//        )
//
//        vBinding!!.weekTopRecyclerView.adapter!!.notifyDataSetChanged()
//        vBinding!!.weekTopRecyclerView.scrollToPosition(weeksItemsList!!.size)
//        vBinding!!.weekTopRecyclerView.smoothScrollToPosition(weeksItemsList!!.size)
//
//    }


    fun WEEKS(): Int? {
//        val startDateObj: Date = convertDateObject(startDate)
        val startDateObj = DateTime(2021, 1, 1, 0, 0, 0, 0)
        if (startDateObj == null) {
            return null
        }

        val endDateObj = DateTime(2021, 1, 7, 0, 0, 0, 0)
        return if (endDateObj == null) {
            null
        } else {
            val dt1 = LocalDate(startDateObj)
            val dt2 = LocalDate(endDateObj)
            Weeks.weeksBetween(dt1, dt2).getWeeks()
        }
    }

    inner class MyXAxisFormatter : ValueFormatter() {

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {

            return "\n\n" + graphValues.get(value.toInt()).toString()
//            return (graphValues.getOrNull(value.toInt()) ?: value.toString())
        }
    }

    class MyValueFormatter : ValueFormatter() {
        private val format = DecimalFormat("###,##0.0")

        // override this for e.g. LineChart or ScatterChart
        override fun getPointLabel(entry: Entry?): String {
            return format.format(entry?.y)
        }

        // override this for BarChart
        override fun getBarLabel(barEntry: BarEntry?): String {
            return format.format(barEntry?.y)
        }

        // override this for custom formatting of XAxis or YAxis labels
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return format.format(value)
        }
        // ... override other methods for the other chart types
    }

    override fun OnRecyclerViewItemClick(position: Int) {


        configureChartAppearance(weeksItemsList!!.get(position).startDate!!)
    }
}