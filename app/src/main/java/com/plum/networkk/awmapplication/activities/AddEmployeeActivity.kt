package com.plum.networkk.awmapplication.activities

import android.R.attr.label
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.getSystemService
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.databinding.ActivityAddEmployeeBinding
import gen._base._base_java__rjava_resources.srcjar.R.id.text


class AddEmployeeActivity : AppCompatActivity() {

    var tvCompanyName : TextView? = null
    var tvCount : TextView? = null
    var tvInviteCode : TextView? = null
    var copyCodeBtn : View? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var vBinding = ActivityAddEmployeeBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)
        setSupportActionBar(vBinding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        vBinding!!.toolbarTitleTv.text = "" + resources.getString(R.string.txt_add_employee)

        /*************************************
         * Setup Elements
         */
        tvCompanyName = view.findViewById(R.id.tv1) as TextView
        tvCount = view.findViewById(R.id.tv2TotalEmployees) as TextView
        tvInviteCode = view.findViewById(R.id.tv3LableInviteCode) as TextView
        copyCodeBtn = view.findViewById(R.id.tvAwayModeStatusOrAddEmployee) as View

        /****************************
         * Set Value to Elements
         */
        if(AppController.companies != null && AppController.companies!!.isNotEmpty()) {
            tvCompanyName!!.text = AppController.companies!![0].name
            tvCount!!.text = AppController.companies!![0].employeeCount.toString() + " Total Employees"
            tvInviteCode!!.text = "Invite Code: " +  AppController.companies!![0].inviteCode.toString()
        }

        /*************************************
         * Copy Code Click Event Handler
         */
        copyCodeBtn!!.setOnClickListener{
            copyToClipboard(AppController.companies!![0].inviteCode)
            Toast.makeText(
                this@AddEmployeeActivity, "Copied invite code into clipboard. \r\nPlease share it with your employee!", Toast.LENGTH_LONG
            ).show()
        }

    }

    fun copyToClipboard(text: CharSequence){
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label",text)
        clipboard.setPrimaryClip(clip)
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }

}