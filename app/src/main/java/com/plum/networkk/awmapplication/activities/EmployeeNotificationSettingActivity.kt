package com.plum.networkk.awmapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.EmployeesBreakSettingAdapter
import com.plum.networkk.awmapplication.adapters.EmployeesDetailsAdapter
import com.plum.networkk.awmapplication.adapters.EmployeesNotificationSettingAdapter
import com.plum.networkk.awmapplication.adapters.EmployeesSettingAdapter
import com.plum.networkk.awmapplication.databinding.*
import com.plum.networkk.awmapplication.models.EmployeeBreakSettingModel
import com.plum.networkk.awmapplication.models.EmployeeDetailModel
import com.plum.networkk.awmapplication.models.EmployeeNotificationSettingModel
import com.plum.networkk.awmapplication.models.EmployeeSettingModel

class EmployeeNotificationSettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var vBinding = ActivityEmployeeNotificationSettingBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)
//        setContentView(R.layout.activity_employee_detail)


        setSupportActionBar(vBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setTitle("NOTIFICATION")
        vBinding!!.toolbarTitle.text = "NOTIFICATION"

        var itemList = ArrayList<EmployeeNotificationSettingModel>()
        itemList.add(EmployeeNotificationSettingModel("Phone unlocks", false))
        itemList.add(EmployeeNotificationSettingModel("Overages",true))
        itemList.add(EmployeeNotificationSettingModel("New registration",false))
        itemList.add(EmployeeNotificationSettingModel("Away mode on/off",false))

        var adapter = EmployeesNotificationSettingAdapter(this@EmployeeNotificationSettingActivity, itemList)
        vBinding.employeeSettingRecyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}