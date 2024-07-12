package com.plum.networkk.awmapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.AddOrEditBreakAdapter
import com.plum.networkk.awmapplication.databinding.*
import com.plum.networkk.awmapplication.models.AddorEditBreakModel

class AddBreakActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var vBinding = ActivityAddBreakBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)
//        setContentView(R.layout.activity_employee_detail)

        setSupportActionBar(vBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setTitle("" +resources.getString(R.string.txt_add_new_break))

        var itemList = ArrayList<AddorEditBreakModel>()
        itemList.add(AddorEditBreakModel("Details", View.GONE,"0",0))
        itemList.add(AddorEditBreakModel("Title", View.GONE,"15 Minutes Break",0))
        itemList.add(AddorEditBreakModel("Duration", View.VISIBLE,"15 Mins",15))
        itemList.add(AddorEditBreakModel("#Allowed", View.GONE,"1",0))
        itemList.add(AddorEditBreakModel("Frequency", View.GONE,"Hourly",0))
        itemList.add(AddorEditBreakModel("Time Reset", View.GONE,"4",0))

        var adapter = AddOrEditBreakAdapter(this@AddBreakActivity, itemList)
        vBinding.employeeSettingRecyclerView.adapter = adapter

        vBinding.tvCancel.setOnClickListener{
            finish()
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }


}