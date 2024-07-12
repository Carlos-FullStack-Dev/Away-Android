package com.plum.networkk.awmapplication.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.databinding.ActivityAppUsageBinding
import com.plum.networkk.awmapplication.databinding.ActivityOveragesBinding
import com.plum.networkk.awmapplication.models.*


class AppUsageActivity : AppCompatActivity() {

    var vBinding: ActivityAppUsageBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vBinding = ActivityAppUsageBinding.inflate(layoutInflater)
        val view: View = vBinding!!.getRoot()
        setContentView(view)

        setSupportActionBar(vBinding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setTitle("APP USAGE")
        vBinding!!.toolbarTitle.text = "APP USAGE"





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
//                            this@AppUsageActivity,
//                            "Tab " + vBinding!!.tabLayout.getSelectedTabPosition(),
//                            Toast.LENGTH_LONG
//                        ).show()
                    }
                    1 -> {
//                        Toast.makeText(
//                            this@AppUsageActivity,
//                            "Tab " + vBinding!!.tabLayout.getSelectedTabPosition(),
//                            Toast.LENGTH_LONG
//                        ).show()
                    }
                    2 -> {
//                        Toast.makeText(
//                            this@AppUsageActivity,
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


        vBinding!!.expandablePlaceHolder.setOnClickListener {
//            Toast.makeText(applicationContext, "Clixcked", it.id).show()
        }

        getHeaderAndChild()
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


        var headerList1 = AppUsageDataModel(
            "Json Newberg",
            "2",
            View.VISIBLE,
            resources.getString(R.string.txt_required_apps)
        )
        var headerList2 = AppUsageDataModel(
            "Nathan Smith", "5", View.VISIBLE,
            resources.getString(R.string.txt_allowed_apps)
        )
        var headerList3 = AppUsageDataModel(
            "Smith Nathan ", "8", View.GONE,
            ""
        )

        var headerList4 = AppUsageDataModel(
            "Smith Nathan ", "8", View.VISIBLE,
            resources.getString(R.string.txt_blocked_apps)
        )

        vBinding!!.expandablePlaceHolder.addView(HeaderView_AppUsage(this, headerList1))

        vBinding!!.expandablePlaceHolder.addView(
            ChildView(
                this, OverAgesChildDataModel("March 2, 2017", "32 minutes", "2/3 unlocks")
            )
        )

        vBinding!!.expandablePlaceHolder.addView(
            ChildView(
                this, OverAgesChildDataModel("March 2, 2017", "32 minutes", "2/3 unlocks")
            )
        )

        vBinding!!.expandablePlaceHolder.addView(HeaderView_AppUsage(this, headerList2))
        vBinding!!.expandablePlaceHolder.addView(
            ChildView(
                this,
                OverAgesChildDataModel("Apr 2, 2018", "20 minutes", "4/2 unlocks")
            )
        )

        vBinding!!.expandablePlaceHolder.addView(HeaderView_AppUsage(this, headerList3))
        vBinding!!.expandablePlaceHolder.addView(
            ChildView(
                this,
                OverAgesChildDataModel("Apr 2, 2018", "20 minutes", "4/2 unlocks")
            )
        )


        vBinding!!.expandablePlaceHolder.addView(HeaderView_AppUsage(this, headerList4))
        vBinding!!.expandablePlaceHolder.addView(
            ChildView(
                this,
                OverAgesChildDataModel("Apr 2, 2018", "20 minutes", "4/2 unlocks")
            )
        )


        /*   val movieList1: List<OverAgesDataModel>? = categoryMap[dataList.get(0).employeeName]
           for (dList in dataList) {

               var test = dList.employeeName
               var movieList1: List<OverAgesDataModel>? = categoryMap.get(test)

               if (movieList1 == null) {
                   movieList1 = ArrayList()
               }
               movieList1.add(dList)
               categoryMap.put(dList.employeeName, movieList1)

           }*/

//        var categoryMap  : Map<String, List<OverAgesDataModel>> = HashMap<String, MutableList<OverAgesDataModel>>()

        /*   for (dList in dataList) {

               var movieList1: MutableList<OverAgesDataModel> =
                   categoryMap!!.get(dList.employeeName!!)!!

               if (movieList1 == null) {
                   movieList1 = ArrayList()
               }
               movieList1.add(dList)
               categoryMap.put(dList.employeeName, movieList1)
           }

           val it: MutableIterator<*> = categoryMap.entrySet().iterator()
           while (it.hasNext()) {
               val pair =
                   it.next() as Map.Entry<*, *>

               vBinding!!.expandablePlaceHolder.addView(HeaderView(this, pair.key.toString()))
               val dataList1 =
                   pair.value as List<OverAgesDataModel>
               for (dList1 in dataList1) {
                   vBinding!!.expandablePlaceHolder.addView(ChildView(this, dList1))
               }
               it.remove()
           }*/
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}