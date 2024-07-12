package com.plum.networkk.awmapplication.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.BusinessSettingAdapter
import com.plum.networkk.awmapplication.databinding.ActivityBusinessSettingBinding
import com.plum.networkk.awmapplication.models.BusinessSettingDataModel

class BusinessSettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_signin)
        var vBinding = ActivityBusinessSettingBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)
        setSupportActionBar(vBinding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        vBinding!!.toolbarTitleTv.text = ""+resources.getString(R.string.txt_business_setting)


        var bSettingDataList = ArrayList<BusinessSettingDataModel>()
        bSettingDataList.add(
            BusinessSettingDataModel(
                resources.getString(R.string.txt_name),
                "ACME computer"
            )
        )
        bSettingDataList.add(
            BusinessSettingDataModel(
                resources.getString(R.string.txt_address),
                "123 Broadway st."
            )
        )
        bSettingDataList.add(
            BusinessSettingDataModel(
                resources.getString(R.string.txt_city),
                "San Francisco"
            )
        )
        bSettingDataList.add(
            BusinessSettingDataModel(
                resources.getString(R.string.txt_state),
                "California"
            )
        )
        bSettingDataList.add(
            BusinessSettingDataModel(
                resources.getString(R.string.txt_zipcode),
                "92830"
            )
        )
        bSettingDataList.add(
            BusinessSettingDataModel(
                resources.getString(R.string.txt_phone),
                "(215) 555-1234"
            )
        )

        var bSettingAdapter = BusinessSettingAdapter(this@BusinessSettingActivity, bSettingDataList)
        vBinding.businesSettingRecyclerView.adapter = bSettingAdapter
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }

}