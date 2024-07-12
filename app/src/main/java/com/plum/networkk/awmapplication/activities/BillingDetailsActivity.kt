package com.plum.networkk.awmapplication.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.BillingDetailsAdapter
import com.plum.networkk.awmapplication.adapters.BusinessSettingAdapter
import com.plum.networkk.awmapplication.adapters.PrivacyPolicyAdapter
import com.plum.networkk.awmapplication.databinding.ActivityBillingDetailsBinding
import com.plum.networkk.awmapplication.databinding.ActivityBusinessSettingBinding
import com.plum.networkk.awmapplication.databinding.ActivityPrivacyPolicyBinding
import com.plum.networkk.awmapplication.models.BillingDataModel
import com.plum.networkk.awmapplication.models.BusinessSettingDataModel
import com.plum.networkk.awmapplication.models.PrivacyPolicyDataModel

class BillingDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var vBinding = ActivityBillingDetailsBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)
        setSupportActionBar(vBinding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        vBinding!!.toolbarTitleTv.text = "" + resources.getString(R.string.txt_billing_details)

        var billingDataList = ArrayList<BillingDataModel>()
        billingDataList.add(
            BillingDataModel(
                resources.getString(R.string.txt_dummy_title),
                resources.getString(R.string.txt_dummy_description)
            )
        )
        billingDataList.add(
            BillingDataModel(
                resources.getString(R.string.txt_dummy_title),
                resources.getString(R.string.txt_dummy_description)
            )
        )


        var billingAdapter = BillingDetailsAdapter(this@BillingDetailsActivity, billingDataList)
        vBinding.billingStatementRecyclerView.adapter = billingAdapter
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }

}