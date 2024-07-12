package com.plum.networkk.awmapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.plum.networkk.awmapplication.adapters.EmployeesBreakSettingAdapter
import com.plum.networkk.awmapplication.databinding.ActivityEmployeeBreakSettingBinding
import com.plum.networkk.awmapplication.models.EmployeeBreakSettingModel

class EmployeeBreakSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var vBinding = ActivityEmployeeBreakSettingBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)
//        setContentView(R.layout.activity_employee_detail)

        setSupportActionBar(vBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setTitle("EMPLOYEE BREAKS")
        vBinding!!.toolbarTitle.text = "EMPLOYEE BREAKS"

        var itemList = ArrayList<EmployeeBreakSettingModel>()
        itemList.add(EmployeeBreakSettingModel("All breaks", ""))
        itemList.add(EmployeeBreakSettingModel("10 Minutes break", "10 Mins"))
        itemList.add(EmployeeBreakSettingModel("Lunch Breaks", "20 Mins"))
        itemList.add(EmployeeBreakSettingModel("Emergency", "30 Mins"))

        var adapter = EmployeesBreakSettingAdapter(this@EmployeeBreakSettingActivity, itemList)
        vBinding.employeeSettingRecyclerView.adapter = adapter

        vBinding.btnAddNewBreak.setOnClickListener {
            var addBreakIntent =
                Intent(this@EmployeeBreakSettingActivity, AddBreakActivity::class.java)
            startActivity(addBreakIntent)
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }


}