package com.plum.networkk.awmapplication.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.EmployeesCompanyAdapter
import com.plum.networkk.awmapplication.apis.VolleySingleton
import com.plum.networkk.awmapplication.constants.URLs
import com.plum.networkk.awmapplication.data_model.Company
import com.plum.networkk.awmapplication.data_model.EmployeeCompanyResponse
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences
import com.plum.networkk.awmapplication.databinding.ActivityCompanyEmployeeBinding
import org.json.JSONObject

class EmployeeCompanyActivity : AppCompatActivity() {

    private lateinit var companyListAdapter: EmployeesCompanyAdapter
    var data: ArrayList<Company> = ArrayList<Company>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var vBinding = ActivityCompanyEmployeeBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)
        setSupportActionBar(vBinding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        vBinding!!.toolbarTitleTv.text = "" + resources.getString(R.string.txt_companies)

        companyListAdapter =
            EmployeesCompanyAdapter(this@EmployeeCompanyActivity, data)
        vBinding.companyRecyclerView.adapter = companyListAdapter


        vBinding.iconAdd.setOnClickListener({
            var signIntent = Intent(this, CreateJoinCompanyActivity::class.java)
            signIntent.putExtra("AccountType", ("employee".takeIf { AppController.user!!.role.toString().toLowerCase().contains("employee") } ?: "business"))
            startActivity(signIntent)
        })

        /// ======= get current selected company ====


        getJoinedCompanies()
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            var menuPage = Intent(this@EmployeeCompanyActivity, MenuActivityEmployee::class.java)
            startActivity(menuPage)
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }

    /*****************************
     * Get Joined Companeds
     */
    fun getJoinedCompanies() {

        val stringReq2 = object : StringRequest(Method.GET, URLs.EMPLOYEE_COMPANY, Response.Listener<String> { response ->

            var strResp2 = response.toString()
            val jsonObj2: JSONObject = JSONObject(strResp2)
            if (jsonObj2.has("data")) {
                var gson = Gson()
                var emp = gson.fromJson(strResp2,  EmployeeCompanyResponse::class.java)

                data.addAll(emp!!.data)
                AppController.companies = emp.data
//                AppController.selectedCompany = emp.data.first()

                // Save Company ID into device
                var companyID = if(emp.data.isNotEmpty()) emp.data.first().companyID.toString()  else ""
                MySharedPreferences.editStringPreferences(MySharedPreferences.COMPANY_ID,  companyID , this@EmployeeCompanyActivity )


                companyListAdapter.notifyDataSetChanged()
            } else {
                Toast.makeText(
                    this, getString(R.string.txt_error_signingin), Toast.LENGTH_SHORT
                ).show()
            }
        }, Response.ErrorListener {
            Log.i("VolleyError", it.toString())
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                super.getHeaders()
                var headers = HashMap<String, String>()
                var bearer = "Bearer ${AppController.BEARER_ACCESS_TOKEN}"
                Log.i("TAG", "getHeaders: $bearer=> ${URLs.EMPLOYEE_COMPANY}")
                headers["Authorization"] = bearer
                return headers
            }

        }
        VolleySingleton(this).addToRequestQueue(stringReq2)

    }


}