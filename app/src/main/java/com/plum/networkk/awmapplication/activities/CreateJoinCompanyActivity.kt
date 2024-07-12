package com.plum.networkk.awmapplication.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.apis.VolleySingleton
import com.plum.networkk.awmapplication.constants.URLs
import com.plum.networkk.awmapplication.constants.mConstants
import com.plum.networkk.awmapplication.databinding.ActivityCreateJoinCompanyBinding
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject

class CreateJoinCompanyActivity : AppCompatActivity() {
    lateinit var accountType: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var vBinding = ActivityCreateJoinCompanyBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()

        accountType = intent.getStringExtra("AccountType").toString()
        setContentView(view)
        setSupportActionBar(vBinding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        /****** IF Employeer ***********/
        if (accountType.equals(mConstants.ACCOUNT_TYPE_BUSINESS)) {
            vBinding.toolbarTitleTv!!.text = "Create Company"

            vBinding.viewCreateCompany.visibility = View.VISIBLE
            vBinding.viewJoinCompany.visibility = View.GONE
            vBinding.btnJoinSaveCompany.setOnClickListener { v1 ->
                if(! (vBinding.etCompanyName.text.toString().isEmpty() && vBinding.etCompanyName.text.toString().isEmpty())){
                    createCompany(companyName = vBinding.etCompanyName.text.toString(), companyAddress = vBinding.etCompanyAddress.text.toString())
                }
            }

        }

        /****** IF Employee ***********/
        else if (accountType.equals(mConstants.ACCOUNT_TYPE_EMPLOYEE)) {
            vBinding.toolbarTitleTv!!.text = "Join Company"
            vBinding.viewCreateCompany.visibility = View.GONE
            vBinding.viewJoinCompany.visibility = View.VISIBLE
            vBinding.btnJoinSaveCompany.setOnClickListener { v1 ->
                if(! vBinding.etInviteCode.text.toString().isEmpty()){
                    joinCompany(vBinding.etInviteCode.text.toString())
                }
            }
        }

    }

    /*****************************
     * Progress Dialog
     */
    var progressDialog: Dialog? = null
    fun progressDialog() {

        if (progressDialog != null) {
            if (progressDialog!!.isShowing)
                progressDialog!!.dismiss()
        }

        progressDialog = Dialog(this/*, R.style.SearchFieldSetterDialog*/)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.setCancelable(false)
        progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window: Window = progressDialog!!.window!!
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        progressDialog!!.setContentView(R.layout.progress_dialog)
        progressDialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        progressDialog!!.show()
    }

    /****************************
     * Create Company Function for Employeer
     */
    private fun createCompany(companyName: String,companyAddress: String) {
//        Log.i("VolleyResponse", "Inside Company API")
        progressDialog()
        val stringReq1 = object : StringRequest(Method.POST, URLs.CREATE_COMPANY, Response.Listener<String> { response ->
            var strResp1 = response.toString()
            Log.i("VolleyResponse", "" + strResp1)
            val jsonObj2: JSONObject = JSONObject(strResp1)
            progressDialog!!.dismiss()
            if (jsonObj2.get("status").toString().equals("true")) {
                var signIntent = Intent(this, MenuActivityEmployer::class.java)
                signIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signIntent)
            } else {
                progressDialog!!.dismiss()
                Toast.makeText(
                    this, resources.getString(R.string.txt_error_fetching_data), Toast.LENGTH_SHORT
                ).show()
            }
        }, Response.ErrorListener {
            progressDialog!!.dismiss()
            Log.i("VolleyError", it.toString())
            Toast.makeText(this,"Could not create company", Toast.LENGTH_SHORT).show()
        }) {
            override fun getParams(): MutableMap<String, String> {
                super.getParams()
                var map1 = HashMap<String, String>()
                map1["name"] = companyName
                map1["address"] = companyAddress
                return map1
            }
            override fun getHeaders(): MutableMap<String, String> {
                super.getHeaders()
                var headers = HashMap<String, String>()
                var bearer = "Bearer " + AppController.BEARER_ACCESS_TOKEN
                headers["Authorization"] = bearer
                return headers
            }
        }
        VolleySingleton(this).addToRequestQueue(stringReq1)

    }

    /****************************
     * Create Company Function for Employee
     */
    private fun joinCompany(inviteCode: String) {
        progressDialog()
        val stringReq1 = object : StringRequest(Method.POST, URLs.JOIN_COMPANY, Response.Listener<String> { response ->
            progressDialog!!.dismiss()

            var strResp1 = response.toString()
            Log.i("VolleyResponse", "" + strResp1)
            val jsonObj2: JSONObject = JSONObject(strResp1)
            if (jsonObj2.get("status").toString().equals("true")) {
                var signIntent = Intent(this, MenuActivityEmployee::class.java)
                signIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signIntent)
            } else {

                var msg = if(!jsonObj2.get("message").toString().equals("")) jsonObj2.get("message").toString() else resources.getString(R.string.txt_failed_try_again)
                FancyToast.makeText(
                    this, msg, FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show()
            }
        }, Response.ErrorListener {
            progressDialog!!.dismiss()
            Log.i("VolleyError", it.toString())
            Toast.makeText(this,"Could not Join the invite.", Toast.LENGTH_SHORT).show()
        }) {
            override fun getParams(): MutableMap<String, String> {
                super.getParams()
                var map1 = HashMap<String, String>()
                map1["coupons"] = inviteCode
                return map1
            }
            override fun getHeaders(): MutableMap<String, String> {
                super.getHeaders()
                var headers = HashMap<String, String>()
                var bearer = "Bearer " + AppController.BEARER_ACCESS_TOKEN
                headers["Authorization"] = bearer
                return headers
            }
        }
        VolleySingleton(this).addToRequestQueue(stringReq1)
    }
    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }

}