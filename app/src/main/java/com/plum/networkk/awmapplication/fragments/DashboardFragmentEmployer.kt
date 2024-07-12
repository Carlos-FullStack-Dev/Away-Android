package com.plum.networkk.awmapplication.fragments

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.MenuActivityEmployer
import com.plum.networkk.awmapplication.activities.StatisticsActivity
import com.plum.networkk.awmapplication.adapters.ActiveInActiveEmployeesAdapter
import com.plum.networkk.awmapplication.adapters.TakeBreakAdapter
import com.plum.networkk.awmapplication.apis.VolleySingleton
import com.plum.networkk.awmapplication.constants.URLs
import com.plum.networkk.awmapplication.data_model.*
import com.plum.networkk.awmapplication.models.TakeBreakDataModel
import com.plum.networkk.awmapplication.utils.doAsync
import org.json.JSONObject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class DashboardFragmentEmployer : Fragment() {


    private lateinit var inActiveEmployeesRecyclerView: RecyclerView
    private lateinit var activeEmployeesRecyclerView: RecyclerView
    private lateinit var activeEmployeesAdapter: ActiveInActiveEmployeesAdapter
    private lateinit var inactiveEmployeesAdapter: ActiveInActiveEmployeesAdapter
    var tvUnlockToday: TextView? = null
    var tvMinutesToday: TextView? = null
    var tvMinutesThisWeek: TextView? = null
    var refreshButton: Button ? = null
    var listActive: ArrayList<EmployeeData> = ArrayList()
    var listInactive: ArrayList<EmployeeData> = ArrayList()

    var progressView: View? = null
    var mainView: View? = null
    var errorView: View? = null
    var totalTodayMins: Long = 0;
    var totalTodayUnlock: Long = 0;
    var totalWeekMins: Long = 0;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard_employer, container, false)

        /*************************************
         * Setup Components
         */
        var viewAllLableBar = root.findViewById(R.id.activeEmployeesBannerLayout) as View

//        var tvViewMoreLable =
//            root.findViewById(R.id.tvViewMoreLable) as TextView
        var viewQuickStats = root.findViewById(R.id.quikStatesBannerLayout) as View

        tvUnlockToday = root.findViewById(R.id.tvUnlockToday) as TextView
        tvMinutesToday = root.findViewById(R.id.tvMinutesToday) as TextView
        tvMinutesThisWeek = root.findViewById(R.id.tvMinutesThisWeek) as TextView

        //============ Get element instance
        mainView = root.findViewById(R.id.dashboardMainBaord) as View
        progressView = root.findViewById(R.id.dashboardProgressBoard) as View
        errorView = root.findViewById(R.id.dashboardErrorBoard) as View
        refreshButton = root.findViewById(R.id.refreshButton) as Button
        refreshButton!!.setOnClickListener {
            var refreshIntent = Intent(requireActivity(), MenuActivityEmployer::class.java)
            startActivity(refreshIntent)
        }
        /***************************************
         * Quick Stats Bar Click Event Handler
         */
        viewQuickStats.setOnClickListener {
            AppController.firstName = AppController.employerCompanyName
            AppController.statistics = AppController.statisticsEmployer
            var viewMoreIntent =
                Intent(requireActivity(), StatisticsActivity::class.java)
            startActivity(viewMoreIntent)
        }

        /*************************************
         * Setup Active & InActive Employees List
         */
        inActiveEmployeesRecyclerView =
            root.findViewById(R.id.inActiveEmployeesRecyclerView) as RecyclerView
        activeEmployeesRecyclerView =
            root.findViewById(R.id.activeEmployeesRecyclerView) as RecyclerView

        /*******************************************
         * Active Employees Bar Click Event Handler
         */
        viewAllLableBar.setOnClickListener {
            (activity as MenuActivityEmployer).changeFragment(1)
        }

        /***********************************
         * @auth: geniusdev0813@gmail.com
         * @desc: Get All Employees
         */
        getAllEmployees()

        return root
    }


    fun takeBreakDialog() {
        val dialog = Dialog(requireActivity()/*, R.style.SearchFieldSetterDialog*/)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window: Window = dialog.window!!
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setContentView(R.layout.take_break_dialog)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val tvCancel = dialog.findViewById(R.id.tvCancel) as TextView
        val btnGoOnBreak = dialog.findViewById(R.id.btnGoOnBreak) as RelativeLayout
        val breakTypeRecyclerView = dialog.findViewById(R.id.breakTypeRecyclerView) as RecyclerView

        var breakDataList = ArrayList<TakeBreakDataModel>()
        breakDataList.add(
            TakeBreakDataModel(
                "10 Minute break",
                "1 of 2 Remaining",
                "10 Mins"
            )
        )
        breakDataList.add(
            TakeBreakDataModel(
                "Lunch break",
                "1 of 1 Remaining",
                "20 Mins"
            )
        )

        breakDataList.add(
            TakeBreakDataModel(
                "Emergency break",
                "1 of 1 Remaining",
                "30 Mins"
            )
        )

        var takeBreakAdapter = TakeBreakAdapter(requireActivity(), breakDataList)

//        takeBreakAdapter?.setDataToList(breakDataList)
        breakTypeRecyclerView.adapter = takeBreakAdapter



        tvCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnGoOnBreak.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    fun advertisementDialog() {
        val dialog = Dialog(requireActivity()/*, R.style.SearchFieldSetterDialog*/)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window: Window = dialog.window!!
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setContentView(R.layout.employee_dashboard_advertisement_dialog)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )

        val btnCross = dialog.findViewById(R.id.btnCross) as ImageView
        val btnGoBack = dialog.findViewById(R.id.btnGoBack) as RelativeLayout


        btnCross.setOnClickListener {
            dialog.dismiss()
        }

        btnGoBack.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }



    /*************************************
     * Visible Circle Progress
     */
    fun viewBoard(type: Int) {
        if(type == 1) { // MainBoard
            mainView!!.setVisibility(View.VISIBLE)
            progressView!!.setVisibility(View.GONE)
            errorView!!.setVisibility(View.GONE)


        } else if(type == 2){ // Progress Board
            progressView!!.setVisibility(View.VISIBLE)
            mainView!!.setVisibility(View.GONE)
            errorView!!.setVisibility(View.GONE)

        } else { // Error Board
            errorView!!.setVisibility(View.VISIBLE)
            mainView!!.setVisibility(View.GONE)
            progressView!!.setVisibility(View.GONE)
        }
    }


    /***********************************
     * @auth: geniusdev0813@gmail.com
     * @desc: Get All Employees
     */
    fun getAllEmployees() {
        // Show Progress
        viewBoard(2);
        val stringReq2 = object : StringRequest(Method.GET, URLs.GET_ALL_EMPLOYEES, Response.Listener<String> { response ->

            var strResp2 = response.toString()
            Log.i("APP_LOG", "getAllEmployees: ${strResp2}")
            val jsonObj2: JSONObject = JSONObject(strResp2)
            if (jsonObj2.get("result").toString().equals("true")) {

                val gson = Gson()

                var emp = gson.fromJson(strResp2, AllEmployeeResponse::class.java)
//                tvUnlockToday?.text = emp!!.data;
                /****************************
                 * Set All Employee Data
                 */
                AppController.employeeDataList.clear()
                AppController.employeeDataList = emp!!.data as ArrayList<EmployeeData>
                if (emp!!.companies.isNotEmpty()){
                    AppController.companies = emp!!.companies as ArrayList<Company>
                }else{
                    AppController.companies = Arrays.asList(Company(0, "", "", "", "", 0, "", 0))
                }


                AppController.employeeDataList.forEach {
                    totalTodayMins += it.minutesToday
                    totalTodayUnlock +=it.unlocksToday
                    totalWeekMins += it.minutesWeekly
                }
                tvUnlockToday?.text = totalTodayUnlock.toString()
                tvMinutesToday?.text = totalTodayMins.toString()
                tvMinutesThisWeek?.text = totalWeekMins.toString()
                /********************************************
                 * Set data for active & inactive Users List
                 */
                listActive.clear()
                listInactive.clear()
                listActive.addAll(AppController.employeeDataList.filter { it.status != null && it.status.toInt() == 1}.toList())
                listInactive.addAll(AppController.employeeDataList.filter { it.status == null || it.status!!.toInt() == 0 }.toList())
                activeEmployeesAdapter =
                ActiveInActiveEmployeesAdapter(requireActivity(), listActive)
                activeEmployeesRecyclerView.adapter = activeEmployeesAdapter
                inactiveEmployeesAdapter =
                ActiveInActiveEmployeesAdapter(requireActivity(), listInactive)
                inActiveEmployeesRecyclerView.adapter = inactiveEmployeesAdapter

                activeEmployeesAdapter.notifyDataSetChanged()
                inactiveEmployeesAdapter.notifyDataSetChanged()
                getStatisticData()
                // View Main Board
                viewBoard(1)
            } else {
                Toast.makeText(
                    context, getString(R.string.txt_error_fetching_data), Toast.LENGTH_SHORT
                ).show()

                // View Error Board
                viewBoard(3)
            }
        }, Response.ErrorListener {
            Log.i("VolleyError", it.toString())
            Toast.makeText(
                context, getString(R.string.txt_error_fetching_data), Toast.LENGTH_SHORT
            ).show()

            // View Error Board
            viewBoard(3)
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                super.getHeaders()
                var headers = HashMap<String, String>()
                var bearer = "Bearer ${AppController.BEARER_ACCESS_TOKEN}"
                headers["Authorization"] = bearer
                return headers
            }

        }
        VolleySingleton(requireContext()).addToRequestQueue(stringReq2)
    }

    fun getStatisticData() {
        val stringReq2 = object : StringRequest(Method.GET, URLs.COMPANY_STATISTICS, Response.Listener<String> { response ->

            var strResp2 = response.toString()
            Log.i("APP_LOG", "getAllEmployees: ${strResp2}")
            val jsonObj2: JSONObject = JSONObject(strResp2)
            if (jsonObj2.has("status") && jsonObj2.getBoolean("status")) {

                val gson = Gson()

                var emp = gson.fromJson(strResp2, StatisticsResponse::class.java)
                AppController.statistics = emp
                AppController.statisticsEmployer = emp
                AppController.companies!!.forEach {
                    AppController.firstName = it.name.toString()
                    AppController.employerCompanyName = it.name.toString()
                }
                /****************************
                 * Set All Employee Statistics Data
                 */

            } else {
                Toast.makeText(
                    context, getString(R.string.txt_error_fetching_data), Toast.LENGTH_SHORT
                ).show()

                // View Error Board
                viewBoard(3)
            }
        }, Response.ErrorListener {
            Log.i("VolleyError", it.toString())
            Toast.makeText(
                context, getString(R.string.txt_error_fetching_data), Toast.LENGTH_SHORT
            ).show()

        }) {
            override fun getHeaders(): MutableMap<String, String> {
                super.getHeaders()
                var headers = HashMap<String, String>()
                var bearer = "Bearer ${AppController.BEARER_ACCESS_TOKEN}"
                headers["Authorization"] = bearer
                return headers
            }

        }
        VolleySingleton(requireContext()).addToRequestQueue(stringReq2)
//        doAsync {//        progressDialog()
//            for (i in 0 until AppController.employeeList.size) {
//                val stringReq2 = object : StringRequest(Method.POST, URLs.STATISTICS, Response.Listener<String> { response ->
//
////            progressDialog!!.dismiss()
//                    var strResp2 = response.toString()
//                    Log.i("APP_LOG", "getStatisticData: ${strResp2}")
//                    val jsonObj2: JSONObject = JSONObject(strResp2)
//                    if (jsonObj2.has("status") && jsonObj2.getBoolean("status")) {
//                        var gson = Gson()
//                        AppController.employeeList[i].statistics = gson.fromJson(strResp2, StatisticsResponse::class.java)
//
////                        AppController.employeeList[i].statistics = StatisticsResponse.fromJson(strResp2)
////                        tvUnlockToday?.text = "${AppController.statistics?.unlocksToday}"
////                        tvMinutesToday?.text = "${AppController.statistics?.minutesToday}"
////                        tvMinutesThisWeek?.text = "${AppController.statistics?.minutesThisWeek}"
//                        if (i == AppController.employeeList.size - 1) {
//                            var strMinW: Long = 0
//                            var strMinT: Long = 0
//                            var strUnlockT: Long = 0
//                            AppController.employeeList.forEach {
//                                if(it.statistics != null) {
//                                strMinT += it.statistics!!.minutesToday
//                                strMinW += it.statistics!!.minutesThisWeek
//                                strUnlockT += it.statistics!!.unlocksToday
//                                }
//                            }
//                            tvUnlockToday?.text = "$strUnlockT"
//                            tvMinutesToday?.text = "$strMinT"
//                            tvMinutesThisWeek?.text = "$strMinW"
//                        }
//                    } else {
//                        Toast.makeText(
//                            context, getString(R.string.txt_error_stats_logs), Toast.LENGTH_SHORT
//                        ).show()
//                    }
//                }, Response.ErrorListener {
//                    Log.i("VolleyError : Sta", it.toString())
////            progressDialog!!.dismiss()
//                }) {
//                    override fun getParams(): MutableMap<String, String> {
//                        super.getParams()
//                        var map1 = java.util.HashMap<String, String>()
//                        map1["user_id"] = AppController.employeeList[i].id.toString()
//                        return map1
//                    }
//
//                    override fun getHeaders(): MutableMap<String, String> {
//                        super.getHeaders()
//                        var headers = java.util.HashMap<String, String>()
//                        var bearer = "Bearer ${AppController.BEARER_ACCESS_TOKEN}"
//                        Log.i("TAG", "getHeaders: $bearer=> ${URLs.STATISTICS}")
//                        headers["Authorization"] = bearer
//                        return headers
//                    }
//
//                }
//                VolleySingleton(requireContext()).addToRequestQueue(stringReq2)
//            }
//        }
    }

    fun getEmployeeList(companyId: String) {
        doAsync {
            val stringReq2 = object : StringRequest(Method.POST, URLs.GET_EMPLOYEES, Response.Listener<String> { response ->
                var strResp2 = response.toString()
                Log.i("APP_LOG", "getEMP List: ${strResp2}")
                val jsonObj2: JSONObject = JSONObject(strResp2)
                if (jsonObj2.has("data")) {
                    var gson = Gson()
                    var emp = gson.fromJson(strResp2, CompanyEmployeeListResponse::class.java)
                    AppController.employeeList.addAll(emp!!.data)
                } else {
                    requireActivity().runOnUiThread {
                        Toast.makeText(
                            context, getString(R.string.txt_error_fetching_data), Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }, Response.ErrorListener {
                Log.i("VolleyError", it.toString())
            }) {
                override fun getParams(): MutableMap<String, String> {
                    super.getParams()
                    var map1 = HashMap<String, String>()
                    map1["company_id"] = companyId
                    return map1
                }

                override fun getHeaders(): MutableMap<String, String> {
                    super.getHeaders()
                    var headers = HashMap<String, String>()
                    var bearer = "Bearer ${AppController.BEARER_ACCESS_TOKEN}"
                    Log.i("TAG", "getHeaders: $bearer=> ${URLs.EMPLOYEE_COMPANY}")
                    headers["Authorization"] = bearer
                    return headers
                }

            }
            VolleySingleton(requireContext()).addToRequestQueue(stringReq2)

        }

    }

    fun getEmployeeStatus() {
        doAsync {
            for (i in 0 until AppController.employeeList.size) {
                val stringReq2 = object : StringRequest(Method.POST, URLs.USER_STATUS, Response.Listener<String> { response ->

                    var strResp2 = response.toString()
                    Log.i("APP_LOG", "getStatus: ${strResp2}")
                    val jsonObj2: JSONObject = JSONObject(strResp2)
                    if (jsonObj2.has("user")) {
                        AppController.employeeList[i].onlineStatus = jsonObj2.getString("user")
                        listActive.clear()
                        listInactive.clear()
//                        listActive.addAll(AppController.employeeList.filter { it.status != null && it.status.toLowerCase() == "online" }.toList())
//                        listInactive.addAll(AppController.employeeList.filter { it.status == null || it.status.toLowerCase() == "offline" }.toList())
//                        requireActivity().runOnUiThread {
//                            activeEmployeesAdapter.notifyDataSetChanged()
//                            inactiveEmployeesAdapter.notifyDataSetChanged()
//                        }
//                        if(i == AppController.employeeList.size-1) {
//                            getStatisticData()
//                        }
                    }
                }, Response.ErrorListener {
                    Log.i("VolleyError", it.toString())
                }) {
                    override fun getParams(): MutableMap<String, String> {
                        super.getParams()
                        var map1 = HashMap<String, String>()
                        map1["user_id"] = AppController.employeeList[i].id.toString()
                        return map1
                    }

                    override fun getHeaders(): MutableMap<String, String> {
                        super.getHeaders()
                        var headers = HashMap<String, String>()
                        var bearer = "Bearer ${AppController.BEARER_ACCESS_TOKEN}"
                        Log.i("TAG", "getHeaders: $bearer=> ${URLs.EMPLOYEE_COMPANY}")
                        headers["Authorization"] = bearer
                        return headers
                    }

                }
                VolleySingleton(requireContext()).addToRequestQueue(stringReq2)
            }

        }
    }

}