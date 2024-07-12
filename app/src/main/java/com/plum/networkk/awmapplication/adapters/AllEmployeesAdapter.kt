package com.plum.networkk.awmapplication.adapters


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.EmployeeDetailActivity
import com.plum.networkk.awmapplication.apis.VolleySingleton
import com.plum.networkk.awmapplication.constants.URLs
import com.plum.networkk.awmapplication.data_model.*
import org.json.JSONObject
import java.util.HashMap


class AllEmployeesAdapter(context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var dataList: ArrayList<EmployeeData>? = null
    var mContext: Activity? = null

    constructor(mContext: Activity, breakDataList: ArrayList<EmployeeData>) : this(
        mContext
    ) {
//        super(mContext,breakDataList)
        this.mContext = mContext
        dataList = breakDataList
    }


//    var onGridItemClick: GridItemClick? = null

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_employer_employee,
            parent, false
        )

        var viewHolder = ViewHolderM(v)

        return viewHolder

    }

    override fun getItemCount(): Int {
        if (dataList != null && dataList!!.size > 0)
            return dataList!!.size
        else
            return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val ViewHolderM = holder as ViewHolderM

//        ViewHolderM.userProfile.setBackgroundResource(dataList!!.get(position).employeeProfileImage!!)
        ViewHolderM.tvUserName.text =  "${dataList!!.get(position).firstName} ${dataList!!.get(position).lastName}".trim()
        ViewHolderM.itemView.setOnClickListener {
            AppController.firstName = dataList!![position].firstName
            // AppController.statistics = dataList!![position].statistics
            //AppController.employee = dataList!![position]

            /******************************
             * Get Employee Data
             */
            loadEmployeeData(dataList!!.get(position).userID.toString(), dataList!!.get(position).companyID.toString(), mContext!!)

        }
    }

    fun setDataToList(mList: ArrayList<EmployeeData>) {
        this.dataList = mList
//        notifyDataSetChanged()
    }

    inner class ViewHolderM(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var userProfile: ImageView = itemView.findViewById(R.id.userProfile)
        var tvUserName: TextView = itemView.findViewById(R.id.tvUserName)

    }

    fun loadEmployeeData(user_id: String, company_id: String,mContext: Activity) {
        // Show Progress Dialog
        AppController.progressDialog(mContext)

        getStatisticData(user_id, company_id, mContext){
            if(it) {
                getEmployeeDetail(user_id, mContext) {
                    //Dismiss Progress Dialog
                    AppController.progressDialog!!.dismiss()

                    if(it) {
                        /******************************
                         * Go to Employee Detail Page
                         */
                        var employeesDetails = Intent(mContext, EmployeeDetailActivity::class.java)
                        mContext!!.startActivity(employeesDetails)
                    } else {
                        Toast.makeText(
                            mContext, "Error in Employee Data. Please retry!", Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                AppController.progressDialog!!.dismiss()

                Toast.makeText(
                    mContext, "Error in Employee Data. Please retry!", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    /*********************************
     * @author: geniusdev0813@gmail.com
     * @date: 2022.12.15
     * @desc: Get Statistic Data for Employee
     */
    fun getStatisticData(user_id: String, company_id: String,mContext: Activity, myCallback: (result: Boolean) -> Unit ) {
        val stringReq2 = object : StringRequest(Method.POST, URLs.STATISTICS, Response.Listener<String> { response ->

            var strResp2 = response.toString()
            Log.i("APP_LOG", "getStatisticData: ${strResp2}")
            val jsonObj2: JSONObject = JSONObject(strResp2)
            if (jsonObj2.has("status") && jsonObj2.getBoolean("status")) {

                /******************************
                 * Set  Statistic Data
                 */
                var gson = Gson()
                AppController.statistics = gson.fromJson(strResp2, StatisticsResponse::class.java)

                myCallback.invoke(true)
            } else {
                myCallback.invoke(false)
            }
        }, Response.ErrorListener {
            Log.i("VolleyError : Sta", it.toString())
            myCallback.invoke(false)
        }) {
            override fun getParams(): MutableMap<String, String> {
                super.getParams()
                var map1 = HashMap<String, String>()
                map1["user_id"] = user_id
                map1["company_id"] = company_id
                return map1
            }

            override fun getHeaders(): MutableMap<String, String> {
                super.getHeaders()
                var headers = HashMap<String, String>()
                var bearer = "Bearer ${AppController.BEARER_ACCESS_TOKEN}"
                Log.i("TAG", "getHeaders: $bearer=> ${URLs.STATISTICS}")
                headers["Authorization"] = bearer
                return headers
            }

        }
        VolleySingleton(mContext).addToRequestQueue(stringReq2)
    }


    /*********************************
     * @author: geniusdev0813@gmail.com
     * @date: 2022.12.15
     * @desc: Get EmployeeDetail
     */
    fun getEmployeeDetail(user_id: String, mContext: Activity, myCallback: (result: Boolean) -> Unit ) {
        val stringReq2 = object : StringRequest(Method.POST, URLs.GET_EMPLOYEE_DETAIL, Response.Listener<String> { response ->

            var strResp2 = response.toString()
            Log.i("APP_LOG", "getStatisticData: ${strResp2}")
            val jsonObj2: JSONObject = JSONObject(strResp2)
            if (jsonObj2.has("result") && jsonObj2.getBoolean("result")) {

                /******************************
                 * Set  Statistic Data
                 */
                var gson = Gson()
                var resData = gson.fromJson(strResp2, EmployeeDetailResponse::class.java)
                AppController.employee = resData.data
                myCallback.invoke(true)

            } else {
                myCallback.invoke(false)
            }
        }, Response.ErrorListener {
            Log.i("VolleyError : Sta", it.toString())
            myCallback.invoke(false)

        }) {
            override fun getParams(): MutableMap<String, String> {
                super.getParams()
                var map1 = HashMap<String, String>()
                map1["user_id"] = user_id
                return map1
            }

            override fun getHeaders(): MutableMap<String, String> {
                super.getHeaders()
                var headers = HashMap<String, String>()
                var bearer = "Bearer ${AppController.BEARER_ACCESS_TOKEN}"
                Log.i("TAG", "getHeaders: $bearer=> ${URLs.STATISTICS}")
                headers["Authorization"] = bearer
                return headers
            }

        }
        VolleySingleton(mContext).addToRequestQueue(stringReq2)
    }

}
