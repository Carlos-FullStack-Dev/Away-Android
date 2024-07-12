package com.plum.networkk.awmapplication.activities

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.*
import com.plum.networkk.awmapplication.databinding.ActivityEmployeeAppSettingBinding
import com.plum.networkk.awmapplication.interfaces.RecyclerItemClick
import com.plum.networkk.awmapplication.models.*

class EmployeeAppSettingActivity : AppCompatActivity(), RecyclerItemClick {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var vBinding = ActivityEmployeeAppSettingBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)
//        setContentView(R.layout.activity_employee_detail)


        setSupportActionBar(vBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setTitle("" + resources.getString(R.string.txt_wifi_setting))
        vBinding!!.toolbarTitle.text = ""+ resources.getString(R.string.txt_wifi_setting)

        var itemList = ArrayList<EmployeeAppSettingModel>()
        itemList.add(
            EmployeeAppSettingModel(
                resources.getString(R.string.txt_required_apps),
                false,
                View.GONE,
                View.GONE,
                View.GONE
            )
        )

        itemList.add(
            EmployeeAppSettingModel(
                resources.getString(R.string.txt_emergency_calls),
                true,
                View.GONE,
                View.GONE,
                View.GONE
            )
        )
        itemList.add(
            EmployeeAppSettingModel(
                resources.getString(R.string.txt_allowed_apps),
                false,
                View.GONE,
                View.VISIBLE,
                View.GONE
            )
        )

        itemList.add(
            EmployeeAppSettingModel(
                resources.getString(R.string.txt_work_chat),
                false,
                View.GONE,
                View.GONE,
                View.GONE
            )
        )
        itemList.add(
            EmployeeAppSettingModel(
                resources.getString(R.string.txt_timeclock_app),
                false,
                View.GONE,
                View.GONE,
                View.GONE
            )
        )
        itemList.add(
            EmployeeAppSettingModel(
                resources.getString(R.string.txt_project_management_app),
                false,
                View.GONE,
                View.GONE,
                View.GONE
            )
        )


        itemList.add(
            EmployeeAppSettingModel(
                resources.getString(R.string.txt_blocked_apps),
                false,
                View.GONE,
                View.VISIBLE,
                View.GONE
            )
        )

        itemList.add(
            EmployeeAppSettingModel(
                resources.getString(R.string.txt_blocked_all_except_allowed_apps),
                true,
                View.VISIBLE,
                View.GONE,
                View.GONE
            )
        )

        itemList.add(
            EmployeeAppSettingModel(
                resources.getString(R.string.txt_internet),
                false,
                View.GONE,
                View.GONE,
                View.VISIBLE
            )
        )

        itemList.add(
            EmployeeAppSettingModel(
                resources.getString(R.string.txt_messaging),
                false,
                View.GONE,
                View.GONE,
                View.VISIBLE
            )
        )

        itemList.add(
            EmployeeAppSettingModel(
                resources.getString(R.string.txt_facebook),
                false,
                View.GONE,
                View.GONE,
                View.VISIBLE
            )
        )

        itemList.add(
            EmployeeAppSettingModel(
                resources.getString(R.string.txt_snapchat),
                false,
                View.GONE,
                View.GONE,
                View.VISIBLE
            )
        )


        var adapter =
            EmployeesAppSettingAdapter(this@EmployeeAppSettingActivity, itemList)
        vBinding.employeeSettingRecyclerView.adapter = adapter


    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }


    fun updgradeDialog() {
        val dialog = Dialog(this@EmployeeAppSettingActivity/*, R.style.SearchFieldSetterDialog*/)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window: Window = dialog.window!!
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setContentView(R.layout.app_setting_advance_dialog)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val tvGoBack = dialog.findViewById(R.id.tvGoBack) as TextView
        val btnUpgradeNow = dialog.findViewById(R.id.btnUpgradeNow) as RelativeLayout

        tvGoBack.setOnClickListener {
            dialog.dismiss()
        }

        btnUpgradeNow.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun OnRecyclerViewItemClick(position: Int) {
        updgradeDialog()
    }
}