package com.plum.networkk.awmapplication.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.BusinessSettingAdapter
import com.plum.networkk.awmapplication.adapters.PrivacyPolicyAdapter
import com.plum.networkk.awmapplication.databinding.ActivityBusinessSettingBinding
import com.plum.networkk.awmapplication.databinding.ActivityPrivacyPolicyBinding
import com.plum.networkk.awmapplication.models.BusinessSettingDataModel
import com.plum.networkk.awmapplication.models.PrivacyPolicyDataModel

class TermOfServiceActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var vBinding = ActivityPrivacyPolicyBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)
        setSupportActionBar(vBinding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        vBinding!!.toolbarTitleTv.text = "" + resources.getString(R.string.txt_term_of_reference)


        var termOfReferenceDataList = ArrayList<PrivacyPolicyDataModel>()
        termOfReferenceDataList.add(
            PrivacyPolicyDataModel(
                resources.getString(R.string.txt_dummy_title),
                resources.getString(R.string.txt_dummy_description)
            )
        )

        termOfReferenceDataList.add(
            PrivacyPolicyDataModel(
                resources.getString(R.string.txt_dummy_title),
                resources.getString(R.string.txt_dummy_description)
            )
        )

        var pPolicyAdapter = PrivacyPolicyAdapter(this@TermOfServiceActivity, termOfReferenceDataList)
        vBinding.privacyPolicyRecyclerView.adapter = pPolicyAdapter
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }

}