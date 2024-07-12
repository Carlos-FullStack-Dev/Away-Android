package com.plum.networkk.awmapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.EmployeesDetailsAdapter
import com.plum.networkk.awmapplication.adapters.EmployeesSettingAdapter
import com.plum.networkk.awmapplication.adapters.EmployeesWifiSettingAdapter
import com.plum.networkk.awmapplication.databinding.ActivityEmployeeDetailBinding
import com.plum.networkk.awmapplication.databinding.ActivityEmployeeSettingBinding
import com.plum.networkk.awmapplication.databinding.ActivityStatisticsBinding
import com.plum.networkk.awmapplication.models.EmployeeDetailModel
import com.plum.networkk.awmapplication.models.EmployeeSettingModel
import com.plum.networkk.awmapplication.models.EmployeeWifiSettingModel

class EmployeeWifiSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var vBinding = ActivityEmployeeSettingBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)
//        setContentView(R.layout.activity_employee_detail)


        setSupportActionBar(vBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setTitle("WIFI SETTINGS")
        vBinding!!.toolbarTitle.text = "WIFI SETTINGS"

        var itemList = ArrayList<EmployeeWifiSettingModel>()
        itemList.add(EmployeeWifiSettingModel("Global settings", false, View.VISIBLE, View.GONE))
        itemList.add(
            EmployeeWifiSettingModel(
                "Turn off wifi completely",
                false,
                View.VISIBLE,
                View.GONE
            )
        )
        itemList.add(
            EmployeeWifiSettingModel(
                "Wifi for allowed apps only",
                true,
                View.VISIBLE,
                View.GONE
            )
        )
        itemList.add(
            EmployeeWifiSettingModel(
                "Turn on wifi firewall",
                false,
                View.VISIBLE,
                View.GONE
            )
        )

        itemList.add(EmployeeWifiSettingModel("Allowed websites", false, View.GONE, View.VISIBLE))
        itemList.add(
            EmployeeWifiSettingModel(
                "https://www.acmecomputers.com",
                false,
                View.GONE,
                View.GONE
            )
        )
        itemList.add(
            EmployeeWifiSettingModel(
                "https://www.a-wayapp.com",
                false,
                View.GONE,
                View.GONE
            )
        )
        itemList.add(
            EmployeeWifiSettingModel(
                "https://www.calendar.com",
                false,
                View.GONE,
                View.GONE
            )
        )

        var adapter = EmployeesWifiSettingAdapter(this@EmployeeWifiSettingActivity, itemList)
        vBinding.employeeSettingRecyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}