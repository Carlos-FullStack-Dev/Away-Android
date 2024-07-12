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
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.adapters.FAQsAdapter
import com.plum.networkk.awmapplication.apis.VolleySingleton
import com.plum.networkk.awmapplication.constants.URLs
import com.plum.networkk.awmapplication.data_model.FAQItem
import com.plum.networkk.awmapplication.data_model.FAQResponse
import com.plum.networkk.awmapplication.databinding.ActivityFaqBinding
import com.plum.networkk.awmapplication.interfaces.ExpendableListItemClick
import com.plum.networkk.awmapplication.models.ChildViewFAQs
import com.plum.networkk.awmapplication.models.HeaderViewFAQs
import org.json.JSONObject

//import retrofit2.Call
//import retrofit2.Response

class FAQActivity : AppCompatActivity(), ExpendableListItemClick {

    var faqDataList: ArrayList<FAQItem>? = null
    var faqAdapter: FAQsAdapter? = null
    var vBinding: ActivityFaqBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        vBinding = ActivityFaqBinding.inflate(layoutInflater)
        val view: View = vBinding!!.getRoot()
        setContentView(view)
        setSupportActionBar(vBinding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        vBinding!!.toolbarTitleTv.text = "" + resources.getString(R.string.txt_faqs)

        faqDataList = ArrayList<FAQItem>()
//        faqAdapter = FAQsAdapter(this@FAQActivity, faqDataList!!)
//        vBinding!!.allFaqsRecyclerView.adapter = faqAdapter


//        vBinding!!.expandableAllFaqsRecyclerView.addView(HeaderViewFAQs(this, "FAQ testing"))
//        vBinding!!.expandableAllFaqsRecyclerView.addView(ChildViewFAQs(this, "FAQ description"))
//        vBinding!!.expandableAllFaqsRecyclerView.setOnClickListener {
//            Toast.makeText(applicationContext, "Clixcked", it.id).show()
//        }

        fetAllFAQs()
    }


    fun putDataToList() {
        /* faqDataList!!.add(
             FaqItemModel(
                 3,
                 "Does this app track what I'm doing on my phone",
                 "Does this app track what I'm doing on my phone yes it does",
                 "",
                 ""
             )
         )

         faqDataList!!.add(
             FaqItemModel(
                 4,
                 "Does this app track what I'm doing on my phone",
                 "Does this app track what I'm doing on my phone yes it does",
                 "",
                 ""
             )
         )*/
        for (item in faqDataList!!) {
            vBinding!!.expandableAllFaqsRecyclerView.addView(
                HeaderViewFAQs(this, item.id.toInt(), item.name!!)
            )

            vBinding!!.expandableAllFaqsRecyclerView.addView(
                ChildViewFAQs(
                    this, item.description!!
                )
            )
        }

    }

    fun fetAllFAQs() {


        val stringReq2 = object : StringRequest(Method.GET, URLs.FAQS, Response.Listener<String> { response ->

            var strResp2 = response.toString()
            val jsonObj2: JSONObject = JSONObject(strResp2)
            if (jsonObj2.has("data")) {
                var gson = Gson()
                var emp = gson.fromJson(strResp2, FAQResponse::class.java)
                faqDataList!!.addAll(emp!!.data)
//                faqAdapter!!.notifyDataSetChanged()
                putDataToList()
            } else {
                Toast.makeText(
                    this, getString(R.string.txt_error_signingin), Toast.LENGTH_SHORT
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
//    {
//
////        progressDialog()
////
////        var netWorkApi = RetrofitServiceSignIn.cteateService(ApisSericesInterface::class.java)
////        netWorkApi.getFaq(
////        )!!.enqueue(object : retrofit2.Callback<FaqsResponseModel> {
////
////            override fun onResponse(
////                call: Call<FaqsResponseModel>,
////                response: Response<FaqsResponseModel>
////            ) {
////
////                progressDialog!!.dismiss()
////
////                if (response.isSuccessful) {
////
////                    if (response.body() == null) {
////                        //error
////
////                        problemInAPIDialog()
////                    } else {
////                        //success
////
////                        faqDataList = response.body()!!.data
//////                        faqAdapter!!.setDataToList(faqDataList!!)
//////                        vBinding!!.allFaqsRecyclerView.adapter!!.notifyDataSetChanged()
////
////                        putDataToList()
////
////                    }
////
////                } else {
////                    //error
////                    problemInAPIDialog()
////                }
////
////            }
////
////            override fun onFailure(call: Call<FaqsResponseModel>, t: Throwable) {
//////                onPointsDataResponse.onFailure(call.toString())
////                //error in signing in
////                Log.e("SigninActivity", "error:" + t)
////
////                progressDialog!!.dismiss()
////                problemInAPIDialog()
////            }
////
////        })
//    }


    fun problemInAPIDialog() {
        val dialog = Dialog(this@FAQActivity/*, R.style.SearchFieldSetterDialog*/)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window: Window = dialog.window!!
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setContentView(R.layout.error_dialog)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val dialogMessage = dialog.findViewById(R.id.dialogMessage) as TextView

        dialogMessage.text = "" + resources.getString(R.string.txt_error_fetching_data)

        val btnSend = dialog.findViewById(R.id.btnSend) as RelativeLayout

        btnSend.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    var progressDialog: Dialog? = null
    fun progressDialog() {

        if (progressDialog != null) {
            if (progressDialog!!.isShowing)
                progressDialog!!.dismiss()
        }

        progressDialog = Dialog(this@FAQActivity/*, R.style.SearchFieldSetterDialog*/)
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


    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {

        if (menuItem.getItemId() === android.R.id.home) {
            if (AppController.userRole == 1){
                var FaqIntent = Intent(this@FAQActivity, MenuActivityEmployer::class.java)
                startActivity(FaqIntent)
                finish()
            }else{
                var FaqIntent = Intent(this@FAQActivity, MenuActivityEmployee::class.java)
                startActivity(FaqIntent)
                finish()
            }
            Log.i("checking", "here")

        }
        return super.onOptionsItemSelected(menuItem)
    }

    override fun OnListItemExpand(position: Int) {
//        Toast.makeText(this@FAQActivity, "on Expand: " + position, Toast.LENGTH_SHORT).show()
    }

    override fun OnListItemCollapse(position: Int) {
//        Toast.makeText(this@FAQActivity, "on Collapse: " + position, Toast.LENGTH_SHORT).show()

    }

}