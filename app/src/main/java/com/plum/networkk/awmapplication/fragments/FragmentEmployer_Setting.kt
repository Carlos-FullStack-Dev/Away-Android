package com.plum.networkk.awmapplication.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.CreateAccountActivity2
import com.plum.networkk.awmapplication.activities.StatisticsActivity
import com.plum.networkk.awmapplication.adapters.*
import com.plum.networkk.awmapplication.constants.mConstants
import com.plum.networkk.awmapplication.models.*

class FragmentEmployer_Setting : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_employer_setting, container, false)

        var settingRecyclerView =
            root.findViewById(R.id.settingRecyclerView) as RecyclerView

        var itemList = ArrayList<EmployerSettingModel>()
        itemList.add(EmployerSettingModel("General settings"))
        itemList.add(EmployerSettingModel("Business Info"))
        itemList.add(EmployerSettingModel("Away settings"))
        itemList.add(EmployerSettingModel("Employee Breaks"))
        itemList.add(EmployerSettingModel("Notification"))

        itemList.add(EmployerSettingModel("Advance settings"))
        itemList.add(EmployerSettingModel("Wifi settings"))
        itemList.add(EmployerSettingModel("App Settings"))

        itemList.add(EmployerSettingModel("Other Information"))
        itemList.add(EmployerSettingModel("Privacy Policy"))
        itemList.add(EmployerSettingModel("Term of Service"))
        itemList.add(EmployerSettingModel("Billing Details"))

        var adapter = EmployerSettingAdapter(this@FragmentEmployer_Setting, itemList)
        settingRecyclerView.adapter = adapter


        return root
    }

}