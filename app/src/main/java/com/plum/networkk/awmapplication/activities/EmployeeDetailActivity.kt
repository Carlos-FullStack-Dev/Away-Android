package com.plum.networkk.awmapplication.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.EmployeesDetailsAdapter
import com.plum.networkk.awmapplication.apis.VolleySingleton
import com.plum.networkk.awmapplication.constants.URLs
import com.plum.networkk.awmapplication.databinding.ActivityEmployeeDetailBinding
import com.plum.networkk.awmapplication.models.EmployeeDetailModel
import org.json.JSONObject

class EmployeeDetailActivity : AppCompatActivity() {

    var tvUnlockToday: TextView? = null
    var tvMinutesToday: TextView? = null
    var tvMinutesThisWeek: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var vBinding = ActivityEmployeeDetailBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)
//        setContentView(R.layout.activity_employee_detail)


        setSupportActionBar(vBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setTitle("JSON'S NEWBURG")
        vBinding!!.toolbarTitle.text = "${AppController.employee?.firstName} ${AppController.employee?.lastName}".trim()

        tvUnlockToday = view.findViewById(R.id.tvUnlockToday) as TextView
        tvMinutesToday = view.findViewById(R.id.tvMinutesToday) as TextView
        tvMinutesThisWeek = view.findViewById(R.id.tvMinutesThisWeek) as TextView

        var itemList = ArrayList<EmployeeDetailModel>()
        itemList.add(EmployeeDetailModel("First Name", "${AppController.employee?.firstName}", View.GONE, View.VISIBLE))
        itemList.add(EmployeeDetailModel("Last Name", "${AppController.employee?.lastName}", View.GONE, View.VISIBLE))
        itemList.add(EmployeeDetailModel("Email", "${AppController.employee?.email}", View.GONE, View.VISIBLE))
        itemList.add(EmployeeDetailModel("Phone", "${AppController.employee?.phone?: "NA"}", View.GONE, View.VISIBLE))
//        itemList.add(EmployeeDetailModel("Settings", "Default", View.VISIBLE, View.VISIBLE))

        var adapter = EmployeesDetailsAdapter(this@EmployeeDetailActivity, itemList)
        vBinding.employeesDetailsRecyclerView.adapter = adapter


        vBinding.tvViewMoreLable.setOnClickListener {
            var viewMoreIntent =
                Intent(this@EmployeeDetailActivity, StatisticsActivity::class.java)
            startActivity(viewMoreIntent)
        }
        tvUnlockToday?.text = "${AppController.statistics?.unlocksToday}"
        tvMinutesToday?.text = "${AppController.statistics?.minutesToday}"
        tvMinutesThisWeek?.text = "${AppController.statistics?.minutesThisWeek}"
        vBinding.btnRemoveEmployees.setOnClickListener{
            val builder = AlertDialog.Builder(this@EmployeeDetailActivity)
            builder.setMessage("Are you sure you want to remove this person from company?")
                .setCancelable(true)
                .setPositiveButton("Yes") { dialog, id ->
                    // Delete selected note from database
                    confirmRemove()
                    dialog.dismiss()
                }
                .setNegativeButton("No") { dialog, id ->
                    // Dismiss the dialog
                    dialog.dismiss()
                }
            val alert = builder.create()
            alert.show()
        }
    }

    private fun confirmRemove() {

        val stringReq2 = object : StringRequest(Method.GET, URLs.REMOVE_EMPLOYEE+"${AppController.employee!!.id}", Response.Listener<String> { response ->

            var strResp2 = response.toString()
            Log.i("APP_LOG", "getCompanies: ${strResp2}")
            val jsonObj2: JSONObject = JSONObject(strResp2)
            if (strResp2.contains("success",true)) {
//                AppController.employeeList.remove(AppController.employee!! )
                finish()
            } else {
                Toast.makeText(
                    this , getString(R.string.txt_error_fetching_data), Toast.LENGTH_SHORT
                ).show()
            }
        }, Response.ErrorListener {
            Log.i("VolleyError", it.toString())
        }) {
//                override fun getParams(): MutableMap<String, String> {
//                    super.getParams()
//                    var map1 = HashMap<String, String>()
//                    return map1
//                }

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

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}