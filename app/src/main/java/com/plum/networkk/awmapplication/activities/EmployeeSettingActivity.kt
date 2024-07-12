package com.plum.networkk.awmapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.EmployeesDetailsAdapter
import com.plum.networkk.awmapplication.adapters.EmployeesSettingAdapter
import com.plum.networkk.awmapplication.databinding.ActivityEmployeeDetailBinding
import com.plum.networkk.awmapplication.databinding.ActivityEmployeeSettingBinding
import com.plum.networkk.awmapplication.databinding.ActivityStatisticsBinding
import com.plum.networkk.awmapplication.models.EmployeeDetailModel
import com.plum.networkk.awmapplication.models.EmployeeSettingModel

class EmployeeSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var vBinding = ActivityEmployeeSettingBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)
//        setContentView(R.layout.activity_employee_detail)


        setSupportActionBar(vBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setTitle("JSON'S SETTINGS")
        vBinding!!.toolbarTitle.text = "JSON'S SETTINGS"

        var itemList = ArrayList<EmployeeSettingModel>()
        itemList.add(EmployeeSettingModel("General settings"))
        itemList.add(EmployeeSettingModel("Away settings"))
        itemList.add(EmployeeSettingModel("Employee Breaks"))
        itemList.add(EmployeeSettingModel("Notification"))

        itemList.add(EmployeeSettingModel("Advance settings"))
        itemList.add(EmployeeSettingModel("Wifi settings"))
        itemList.add(EmployeeSettingModel("App Settings"))

        var adapter = EmployeesSettingAdapter(this@EmployeeSettingActivity, itemList)
        vBinding.employeeSettingRecyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}