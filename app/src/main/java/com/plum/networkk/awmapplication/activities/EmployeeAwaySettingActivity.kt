package com.plum.networkk.awmapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.EmployeesAwaySettingAdapter
import com.plum.networkk.awmapplication.adapters.EmployeesDetailsAdapter
import com.plum.networkk.awmapplication.adapters.EmployeesSettingAdapter
import com.plum.networkk.awmapplication.databinding.ActivityEmployeeAwaySettingBinding
import com.plum.networkk.awmapplication.databinding.ActivityEmployeeDetailBinding
import com.plum.networkk.awmapplication.databinding.ActivityEmployeeSettingBinding
import com.plum.networkk.awmapplication.databinding.ActivityStatisticsBinding
import com.plum.networkk.awmapplication.models.EmployeeAwaySettingModel
import com.plum.networkk.awmapplication.models.EmployeeDetailModel
import com.plum.networkk.awmapplication.models.EmployeeSettingModel

class EmployeeAwaySettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var vBinding = ActivityEmployeeAwaySettingBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)
//        setContentView(R.layout.activity_employee_detail)


        setSupportActionBar(vBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setTitle("" + resources.getString(R.string.txt_away_setting))
        vBinding!!.toolbarTitle.text = "" + resources.getString(R.string.txt_away_setting)

        var itemList = ArrayList<EmployeeAwaySettingModel>()
        itemList.add(
            EmployeeAwaySettingModel(
                "Global Settings",
                "",
                false,
                View.GONE,
                View.GONE,
                View.GONE
            )
        )

        itemList.add(
            EmployeeAwaySettingModel(
                "Manual On/Off",
                "",
                false,
                View.VISIBLE,
                View.GONE,
                View.GONE
            )
        )

        itemList.add(
            EmployeeAwaySettingModel(
                "GPS enabled On/Off",
                "",
                false,
                View.VISIBLE,
                View.GONE,
                View.GONE
            )
        )

        itemList.add(
            EmployeeAwaySettingModel(
                "GPS Radius",
                "5 Miles",
                false,
                View.GONE,
                View.VISIBLE,
                View.VISIBLE
            )
        )


        itemList.add(
            EmployeeAwaySettingModel(
                "GPS Location",
                "Add New",
                false,
                View.GONE,
                View.VISIBLE,
                View.VISIBLE
            )
        )


        itemList.add(
            EmployeeAwaySettingModel(
                "123 broadway st, SAN Francisco, CA",
                "",
                false,
                View.GONE,
                View.GONE,
                View.GONE
            )
        )

        itemList.add(
            EmployeeAwaySettingModel(
                "555 broadway st, SAN Francisco, CA",
                "",
                false,
                View.GONE,
                View.GONE,
                View.GONE
            )
        )

        var adapter = EmployeesAwaySettingAdapter(this@EmployeeAwaySettingActivity, itemList)
        vBinding.employeeSettingRecyclerView.adapter = adapter
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}