package com.plum.networkk.awmapplication.adapters


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.StatisticsActivity
import com.plum.networkk.awmapplication.apis.VolleySingleton
import com.plum.networkk.awmapplication.constants.URLs
import com.plum.networkk.awmapplication.data_model.EmployeeData
import com.plum.networkk.awmapplication.data_model.StatisticsResponse
import org.json.JSONObject
import java.util.HashMap


class ActiveInActiveEmployeesAdapter(context: Context) :
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

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.item_list_active_employees,
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

        val viewHolderM = holder as ViewHolderM

        viewHolderM.userProfile.visibility = View.GONE
        viewHolderM.tvUserName.text = "${dataList!!.get(position).firstName!!} ${dataList!!.get(position).lastName!!}".trim()

        viewHolderM.iconSwitchTimer.isSelected = false
        viewHolderM.iconSwitchTimer.visibility = View.GONE
        viewHolderM.iconSwitchWifi.visibility = View.GONE
        viewHolderM.iconSwitchPower.visibility = View.GONE
        if(dataList!!.get(position).status != null && dataList!!.get(position).status!!.toInt() == 1) {
            viewHolderM.statusColorView.setBackgroundColor(mContext?.resources!!.getColor(R.color.app_green_color))
        } else {
//            viewHolderM.statusColorView.setBackgroundColor(mContext?.resources!!.getColor(R.color.app_gray_color))
            viewHolderM.statusColorView.setBackgroundColor(mContext?.resources!!.getColor(R.color.dark_red))
        }
//        ViewHolderM.tvStatusAwayTime.text = dataList!!.get(position).statusAwayTime
        var str = dataList!!.get(position).unlocksToday.toString()
        viewHolderM.tvUnlocks.text = "Unlocks: $str"
//        viewHolderM.tvTimeToday.text = "Minutes Today ${(dataList!!.get(position).status.toInt() == 1 ?: "Offline").toString()}"
        viewHolderM.tvTimeToday.text = "Minutes Today: ${(dataList!!.get(position).minutesToday).toString()}"
//        ViewHolderM.tvOverages.text = dataList!!.get(position).overAgaes
        viewHolderM.itemView.setOnClickListener {
            AppController.firstName = dataList!![position].firstName!!
            getStatisticData( dataList!![position].userID.toString(),  dataList!![position].companyID.toString() ,mContext!!)
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
        var statusColorView: View = itemView.findViewById(R.id.statusColorView)
        var iconSwitchTimer: CheckBox = itemView.findViewById(R.id.iconSwitchTimer)
        var iconSwitchWifi: CheckBox = itemView.findViewById(R.id.iconSwitchWifi)
        var iconSwitchPower: CheckBox = itemView.findViewById(R.id.iconSwitchPower)
        var tvStatusAwayTime: TextView = itemView.findViewById(R.id.tvStatusAwayTime)
        var tvUnlocks: TextView = itemView.findViewById(R.id.tvUnlocks)
        var tvTimeToday: TextView = itemView.findViewById(R.id.tvTimeToday)
//        var tvOverages: TextView = itemView.findViewById(R.id.tvOverages)

    }

    /*********************************
     * @author: geniusdev0813@gmail.com
     * @date: 2022.12.15
     * @desc: Get Statistic Data for Employee
     */
    fun getStatisticData(user_id: String, company_id: String,mContext: Activity ) {
        AppController.progressDialog(mContext)
        val stringReq2 = object : StringRequest(Method.POST, URLs.STATISTICS, Response.Listener<String> { response ->

            var strResp2 = response.toString()
            Log.i("APP_LOG", "getStatisticData: ${strResp2}")
            val jsonObj2: JSONObject = JSONObject(strResp2)
            AppController.progressDialog!!.dismiss()
            if (jsonObj2.has("status") && jsonObj2.getBoolean("status")) {

                /******************************
                 * Set  Statistic Data
                 */
                var gson = Gson()
                AppController.statistics = gson.fromJson(strResp2, StatisticsResponse::class.java)

                /******************************
                 * Go to Statistic Page
                 */
                var employeesDetails = Intent(mContext, StatisticsActivity::class.java)
                mContext!!.startActivity(employeesDetails)
            } else {
                Toast.makeText(
                    mContext, "Error in fetching stats", Toast.LENGTH_SHORT
                ).show()
            }
        }, Response.ErrorListener {
            Log.i("VolleyError : Sta", it.toString())
            Toast.makeText(
                mContext, "Error in fetching stats", Toast.LENGTH_SHORT
            ).show()
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


}
