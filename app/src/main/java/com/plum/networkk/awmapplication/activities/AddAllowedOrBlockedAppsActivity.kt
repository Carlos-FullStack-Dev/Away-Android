package com.plum.networkk.awmapplication.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.*
import com.plum.networkk.awmapplication.databinding.ActivityAddAllowedBlockedAppsBinding
import com.plum.networkk.awmapplication.fragments.FragmentPopularApps
import com.plum.networkk.awmapplication.models.*

class AddAllowedOrBlockedAppsActivity : AppCompatActivity() {


    var popularAppsList = ArrayList<AllowedOrBlockedModel>()
    var popularAppsList2 = ArrayList<AllowedOrBlockedModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var vBinding = ActivityAddAllowedBlockedAppsBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)

//        setContentView(R.layout.activity_employee_detail)


        setSupportActionBar(vBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setTitle("" + resources.getString(R.string.txt_select_apps))
        vBinding!!.toolbarTitle.text = "" + resources.getString(
            R.string.txt_select_apps
        )


        vBinding.tabLayout.addTab(
            vBinding.tabLayout.newTab().setText(resources.getText(R.string.txt_popular))
        )
        vBinding.tabLayout.addTab(
            vBinding.tabLayout.newTab().setText(resources.getText(R.string.txt_social))
        )
        vBinding.tabLayout.addTab(
            vBinding.tabLayout.newTab().setText(resources.getText(R.string.txt_games))
        )
        vBinding.tabLayout.addTab(
            vBinding.tabLayout.newTab().setText(resources.getText(R.string.txt_work))
        )
        vBinding.tabLayout.addTab(
            vBinding.tabLayout.newTab().setText(resources.getText(R.string.txt_all_apps))
        )

        for (i in 0 until vBinding.tabLayout.getTabCount()) {
            val tab = vBinding.tabLayout.getTabAt(i)
            val relativeLayout = LayoutInflater.from(this)
                .inflate(
                    R.layout.custom_tab_item_apps,
                    vBinding.tabLayout,
                    false
                ) as RelativeLayout
            val tabTextView =
                relativeLayout.findViewById<View>(R.id.tab_title) as TextView
            tabTextView.text = tab!!.text
            tab.customView = relativeLayout

            if (i == 0)
                tab.select()
        }


//        var popularAppsList = ArrayList<AllowedOrBlockedModel>()

        popularAppsList.add(
            AllowedOrBlockedModel(
                R.drawable.dummy_user,
                resources.getString(R.string.txt_emergency_calls),
                false
            )
        )

        popularAppsList.add(
            AllowedOrBlockedModel(
                R.drawable.dummy_user,
                resources.getString(R.string.txt_work_chat),
                false
            )
        )
        popularAppsList.add(
            AllowedOrBlockedModel(
                R.drawable.dummy_user,
                resources.getString(R.string.txt_timeclock_app),
                false
            )
        )
        popularAppsList.add(
            AllowedOrBlockedModel(
                R.drawable.dummy_user,
                resources.getString(R.string.txt_project_management_app),
                false
            )
        )
        popularAppsList.add(
            AllowedOrBlockedModel(
                R.drawable.dummy_user,
                resources.getString(R.string.txt_internet),
                false
            )
        )
        popularAppsList.add(
            AllowedOrBlockedModel(
                R.drawable.dummy_user,
                resources.getString(R.string.txt_messaging),
                false
            )
        )
        popularAppsList.add(
            AllowedOrBlockedModel(
                R.drawable.dummy_user,
                resources.getString(R.string.txt_facebook),
                false
            )
        )
        popularAppsList.add(
            AllowedOrBlockedModel(
                R.drawable.dummy_user,
                resources.getString(R.string.txt_snapchat),
                false
            )
        )
        popularAppsList.add(
            AllowedOrBlockedModel(
                R.drawable.dummy_user,
                resources.getString(R.string.txt_instagram),
                false
            )
        )

        popularAppsList2.add(
            AllowedOrBlockedModel(
                R.drawable.dummy_user,
                resources.getString(R.string.txt_emergency_calls),
                false
            )
        )

        val adapter = MyAdapter(this, supportFragmentManager, vBinding.tabLayout!!.tabCount)
        vBinding.appsViewPager.adapter = adapter
//        var adapter =
//            AddAllowedOrBlockedAppsAdapter(this@AddAllowedOrBlockedAppsActivity, popularAppsList)
//        vBinding.appsRecyclerView.adapter = adapter

        vBinding.appsViewPager.addOnPageChangeListener(
            TabLayout.TabLayoutOnPageChangeListener(
                vBinding.tabLayout
            )
        )


        vBinding.tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                vBinding.appsViewPager!!.currentItem = tab.position
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {

            }

            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })


    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }


    inner class MyAdapter(
        private val myContext: Context,
        fm: FragmentManager,
        internal var totalTabs: Int
    ) : FragmentPagerAdapter(fm) {

        // this is for fragment tabs
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> {
                    return FragmentPopularApps(popularAppsList)
                }
                1 -> {
                    return FragmentPopularApps(popularAppsList2)
                }
                2 -> {
                    return FragmentPopularApps(popularAppsList2)
                }
                3 -> {
                    return FragmentPopularApps(popularAppsList2)
                }
                4 -> {
                    return FragmentPopularApps(popularAppsList2)
                }
                else -> {
                    return FragmentPopularApps(popularAppsList2)
                }
            }
        }

        // this counts total number of tabs
        override fun getCount(): Int {
            return totalTabs
        }
    }

}