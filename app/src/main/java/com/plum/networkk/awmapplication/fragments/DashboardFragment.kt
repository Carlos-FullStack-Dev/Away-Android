package com.plum.networkk.awmapplication.fragments

//import retrofit2.Call
//import retrofit2.Response
import android.app.ActivityManager
import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
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
import com.plum.networkk.awmapplication.activities.StatisticsActivity
import com.plum.networkk.awmapplication.adapters.TakeBreakAdapter
import com.plum.networkk.awmapplication.apis.VolleySingleton
import com.plum.networkk.awmapplication.constants.URLs
import com.plum.networkk.awmapplication.data_model.*
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences
import com.plum.networkk.awmapplication.models.TakeBreakDataModel
import com.plum.networkk.awmapplication.services.MonitoringLogsServiceKt
//import com.plum.networkk.awmapplication.services.MonitoringLogsServiceKt
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.schedule
import com.plum.networkk.awmapplication.services.ScreenStateReceiver
//import com.plum.networkk.awmapplication.services.ScreenTimeRecordingService

class DashboardFragment : Fragment() {

    var awayModeSwitch: CheckBox? = null
    var takeBreakSwitch: CheckBox? = null
    var tvAwayModeOnOff: TextView? = null
    var tvDateText: TextView? = null
    var tvTimeLastUsedText: TextView? = null
    var logsStatus: String? = null
    var elapsedTimeInSecond = 0.0
    var startTime: Long = 0
    var firstTime: Boolean = true
    var tvUnlockToday: TextView? = null
    var tvMinutesToday: TextView? = null
    var tvMinutesThisWeek: TextView? = null
    var data: List<Company>? = null
    var LockStatusList: ArrayList<String>? = null

    var progressView: View? = null
    var mainView: View? = null
    var errorView: View? = null
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
//    private var recordingService: ScreenTimeRecordingService? = null


//    private lateinit var screenStateReceiver: ScreenStateReceiver

    fun LogsTimer() {
        val mHandler = Handler()
        mHandler.postDelayed(object : Runnable {
            override fun run() {
                Log.e("MonitoringLogsService", "MonitoringService: timer ")
                LogsTimer()
            }

        }, 5 * 1000)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)

//        (activity as MenuActivityEmployee).changeToolbarItem("dashboard")

//        LogsTimer()
        //============ Get element instance
        mainView = root.findViewById(R.id.dashboardMainBaord) as View
        progressView = root.findViewById(R.id.dashboardProgressBoard) as View
        errorView = root.findViewById(R.id.dashboardErrorBoard) as View

        var btnRefresh = root.findViewById(R.id.refreshButton) as Button
        tvUnlockToday = root.findViewById(R.id.tvUnlockToday) as TextView
        tvMinutesToday = root.findViewById(R.id.tvMinutesToday) as TextView
        tvMinutesThisWeek = root.findViewById(R.id.tvMinutesThisWeek) as TextView

        var tvTakeBreak = root.findViewById(R.id.tvTakeBreak) as TextView
        var tvBreakRemaining = root.findViewById(R.id.tvBreakRemaining) as View
        awayModeSwitch = root.findViewById<CheckBox>(R.id.awayModeSwitch)
        takeBreakSwitch = root.findViewById<CheckBox>(R.id.takeBreakSwitch)
        tvAwayModeOnOff = root.findViewById(R.id.tvAwayModeOnOff) as TextView
        var tvViewMoreLable = root.findViewById(R.id.tvViewMoreLable) as TextView
        tvDateText = root.findViewById(R.id.tvDate) as TextView
        tvTimeLastUsedText = root.findViewById(R.id.tvTimeLastUsed) as TextView
        takeBreakSwitch!!.isClickable = false

        LockStatusList = ArrayList<String>()
        tvDateText!!.text = SimpleDateFormat("EEEE, MMM dd hh:mm a").format(Date())



//        recordingService = ScreenTimeRecordingService()

//        screenStateReceiver = ScreenStateReceiver(recordingService!!)


//        screenStateReceiver.register(requireActivity())

//        alarmManager = requireActivity().getSystemService(Context.ALARM_SERVICE) as AlarmManager
//
        /***************************
         * Update Users Status (Online or Offline
         */
//        setEmployeeStatus(true.toString());

        /*************************
         * Click Lock Button
         */
        awayModeSwitch!!.setOnClickListener {
            Log.i("jgj", "checking")
            var  checkVal: Boolean = awayModeSwitch!!.isChecked()
            if (checkVal) {
                MySharedPreferences.editBooleanPreferences(
                    MySharedPreferences.LockMode,
                    true,
                    this@DashboardFragment.requireContext()
                )


            } else {
                MySharedPreferences.editBooleanPreferences(
                    MySharedPreferences.LockMode,
                    false,
                    this@DashboardFragment.requireContext()
                )

            }
            var isLocked = MySharedPreferences.getBooleanPreferences(
                MySharedPreferences.LockMode,
                this@DashboardFragment.requireContext())
            if (isLocked) {
//                MySharedPreferences.editBooleanPreferences(
//                    MySharedPreferences.LockMode,
//                    true,
//                    this@DashboardFragment.requireContext()
//                )
                startServiceFun()
                tvAwayModeOnOff!!.text =
                    resources.getString(R.string.txt_away_mode_turned_on)
                logsStatus = "locked"
//                updateDeviceLogs("locked")

            } else {
//                MySharedPreferences.editBooleanPreferences(
//                    MySharedPreferences.LockMode,
//                    false,
//                    this@DashboardFragment.requireContext()
//                )
                stopLogsService()
                logsStatus = "unlocked"
                tvAwayModeOnOff!!.text =
                    resources.getString(R.string.txt_away_mode_turned_off)
//                updateDeviceLogs("unlocked")
                Timer("SettingUp", false).schedule(2000) {

                    getStatisticData() {
                        if(!it) {
                            Toast.makeText(
                                context, getString(R.string.txt_error_stats_logs), Toast.LENGTH_SHORT
                            ).show()
                        }else{
                            advertisementDialog()
                        }
                    }
                }


            }
        }

        takeBreakSwitch!!.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            tvTakeBreak.isSelected = b
            if (b) {

                takeBreakDialog()
                tvTakeBreak.text = "Lunch Break"
                tvBreakRemaining.visibility = View.VISIBLE
            } else {
                tvTakeBreak.text = resources.getString(R.string.txt_take_a_break)
                tvBreakRemaining.visibility = View.GONE
            }
        }

        tvViewMoreLable.setOnClickListener {
            var viewMoreIntent =
                Intent(requireActivity(), StatisticsActivity::class.java)

            startActivity(viewMoreIntent)
        }

        /*************************
         * Click Refresh Button
         */
        btnRefresh.setOnClickListener {
            loadData()
        }

        /********************
         * Get Init Data
         */
        loadData()

        return root
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




    /***************************************
     * @auth: geniusdev0813@gmail.com
     * @date: 2022.11.25
     * @desc: Update Device Log ( Locked or Unlocked)
     */
    fun updateDeviceLogs(status: String) {


        val stringReq2 = object : StringRequest(Method.POST, URLs.UPDATE_DEVICE_LOGS, Response.Listener<String> { response ->

            var strResp2 = response.toString()
            Log.i("VolleyResponse", "$status->" + strResp2)
            val jsonObj2: JSONObject = JSONObject(strResp2)

            if (jsonObj2.get ("result").toString().equals("true")) {
                if (logsStatus.equals("unlocked")) {
                    takeBreakSwitch!!.isClickable = false
                    takeBreakSwitch!!.isChecked = false

                    tvAwayModeOnOff!!.text =
                        resources.getString(R.string.txt_away_mode_turned_off)

                } else if (logsStatus.equals("locked")) {
                    takeBreakSwitch!!.isClickable = true
                    tvAwayModeOnOff!!.text =
                        resources.getString(R.string.txt_away_mode_turned_on)
                    tvUnlockToday!!.text = jsonObj2.get("unlock_today").toString()
                }

            } else {
                Toast.makeText(
                    context, getString(R.string.txt_error_stats_logs), Toast.LENGTH_SHORT
                ).show()
            }
        }, Response.ErrorListener {
            Log.i("VolleyError", it.toString())
            Toast.makeText(
                context, getString(R.string.txt_error_stats_logs), Toast.LENGTH_SHORT
            ).show()
        }) {
            override fun getParams(): MutableMap<String, String> {
                super.getParams()
                var map1 = HashMap<String, String>()
                map1["status"] = status
                map1["company_id"] = if (AppController.selectedCompany!= null) AppController.selectedCompany!!.companyID.toString() else ""
                return map1
            }
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

    /**************************************
     * @auth: geniusdev0813@gmail.com
     * @date: 2022.11.25
     * @desc: Get Init Data
     */
    fun loadData() {

        // Show Progress
//        viewBoard(2);
        Log.i("jgj_status", "value")
//        AppController.selectedCompany = null;
        var selectedCompany = AppController.selectedCompany
        Log.i("company", selectedCompany.toString())
        if(selectedCompany == null) {
            // Get Companies
            getJoinedCompanies(){result ->
                if(result) {

                    /// Get StatisticData
                    getStatisticData(){result ->
                        if (result) {
                            // View Main Board
                            viewBoard(1)

                            var isLocked = MySharedPreferences.getBooleanPreferences(
                                MySharedPreferences.LockMode,
                                this@DashboardFragment.requireContext()
                            )
                            Log.i("jgj status", "here********")
//                            awayModeSwitch!!.isChecked = isLocked
                            if (!isLocked){
//                                stopLogsService()
                                tvAwayModeOnOff!!.text =
                                    resources.getString(R.string.txt_away_mode_turned_off)

                            } else {
//                                startServiceFun()
                                tvAwayModeOnOff!!.text =
                                    resources.getString(R.string.txt_away_mode_turned_on)
                            }
                        } else {
                            Toast.makeText(
                                context, getString(R.string.txt_error_stats_logs), Toast.LENGTH_SHORT
                            ).show()

                            // View Error Board
                            viewBoard(3)
                        }
                    }
                } else {
                    Toast.makeText(
                        context, getString(R.string.txt_error_fetching_data), Toast.LENGTH_SHORT
                    ).show()

                    // View Error Board
                    viewBoard(3)
                }
            }

        } else {
            // Update UI
            tvTimeLastUsedText!!.text = selectedCompany!!.name
            getStatisticData { result ->
                if (result) {
                    // View Main Board
                    viewBoard(1)
                    var isLocked = MySharedPreferences.getBooleanPreferences(
                        MySharedPreferences.LockMode,
                        this@DashboardFragment.requireContext()
                    )
                    Log.i("jgj status", "here********")
                    awayModeSwitch!!.isChecked = isLocked
                } else {
                    Toast.makeText(
                        context, getString(R.string.txt_error_stats_logs), Toast.LENGTH_SHORT
                    ).show()

                    // View Error Board
                    viewBoard(3)
                }
            }
        }



    }

    /***************************************
     * @auth: geniusdev0813@gmail.com
     * @date: 2022.11.25
     * @desc: Set Users Status( Online or Offline)
     */
    fun setEmployeeStatus(status: String) {

        val stringReq2 = object : StringRequest(Method.POST, URLs.SEND_STATUS, Response.Listener<String> { response ->
            var strResp2 = response.toString()
            Log.i("VolleyResponse", "$status->" + strResp2)
            val jsonObj2: JSONObject = JSONObject(strResp2)

            if (jsonObj2.get ("result").toString().equals("true")) {
//                    Toast.makeText(
//                        context, getString(R.string.txt_error_in_updating_status), Toast.LENGTH_SHORT
//                    ).show()

            } else {
                Toast.makeText(
                    context, getString(R.string.txt_error_in_updating_status), Toast.LENGTH_SHORT
                ).show()
            }
        }, Response.ErrorListener {
            Log.i("VolleyError", it.toString())
            Toast.makeText(
                context, getString(R.string.txt_error_in_updating_status), Toast.LENGTH_SHORT
            ).show()
        }) {
            override fun getParams(): MutableMap<String, String> {
                super.getParams()
                var map1 = HashMap<String, String>()
                map1["status"] = status
                map1["company_id"] = if (AppController.selectedCompany!= null) AppController.selectedCompany!!.companyID.toString() else ""
                return map1
            }
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

    /***************************************
     * @auth: geniusdev0813@gmail.com
     * @date: 2022.11.25
     * @desc: Get Joined Companies for employee
     */
    fun getJoinedCompanies( myCallback: (result: Boolean) -> Unit) {
        AppController.selectedCompany = null;
        AppController.companies = null;
        val stringReq2 = object : StringRequest(Method.GET, URLs.EMPLOYEE_COMPANY, Response.Listener<String> { response ->
            var strResp2 = response.toString()
            Log.i("APP_LOG", "getStatisticData: ${strResp2}")
            val jsonObj2: JSONObject = JSONObject(strResp2)

            if (jsonObj2.has("data")) {

                var gson = Gson()
                var emp = gson.fromJson(strResp2, EmployeeCompanyResponse::class.java)
                data = emp!!.data

                // Set Employee's companies
                AppController.companies = data;


                if(data!!.isNotEmpty()){
                    AppController.selectedCompany = data!!.first();

                }else{
                    AppController.selectedCompany = Company(0, "", "", "", "", 0, "", 0)
                }

                // Save Company ID into device
                var companyID = if(data!!.isNotEmpty()) data!!.first().companyID.toString()  else ""
                MySharedPreferences.editStringPreferences(MySharedPreferences.COMPANY_ID,  companyID ,requireActivity() )

                // Update UI
                tvTimeLastUsedText!!.text = if ( data!!.isNotEmpty()) data!!.first().name else ""

                myCallback.invoke(true)
            } else {


                myCallback.invoke(false)
            }
        }, Response.ErrorListener {
            Log.i("VolleyError", it.toString())

            myCallback.invoke(false)
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
        VolleySingleton(requireContext()).addToRequestQueue(stringReq2)
    }

    fun updateDeviceLogsAsArray(lockStatusList: ArrayList<String>) {
    }


    fun getStatisticData( myCallback: (result: Boolean) -> Unit) {
        val stringReq2 = object : StringRequest(Method.POST, URLs.STATISTICS, Response.Listener<String> { response ->

            var strResp2 = response.toString()
            Log.i("APP_LOG", "getStatisticData: ${strResp2}")
            val jsonObj2: JSONObject = JSONObject(strResp2)
            if (jsonObj2.has("status") && jsonObj2.getBoolean("status")) {
                AppController.firstName = AppController.user!!.user.firstName
                var gson = Gson()
                AppController.statistics = gson.fromJson(strResp2, StatisticsResponse::class.java)
                tvUnlockToday?.text = "${AppController.statistics?.unlocksToday}"
                tvMinutesToday?.text = "${AppController.statistics?.minutesToday}"
                tvMinutesThisWeek?.text = "${AppController.statistics?.minutesThisWeek}"

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
                map1["user_id"] = if (AppController.user!= null) AppController.user!!.user.id.toString() else ""
                map1["company_id"] = if (AppController.selectedCompany!= null) AppController.selectedCompany!!.companyID.toString() else ""
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
        VolleySingleton(requireContext()).addToRequestQueue(stringReq2)
    }

    fun problemInAPIDialog() {
        val dialog = Dialog(requireActivity()/*, R.style.SearchFieldSetterDialog*/)
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

    fun problemInAPIsDialog() {
        val dialog = Dialog(requireActivity()/*, R.style.SearchFieldSetterDialog*/)
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

        dialogMessage.text = "" + resources.getString(R.string.txt_error_in_updating_status)
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

        progressDialog = Dialog(requireActivity()/*, R.style.SearchFieldSetterDialog*/)
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
            takeBreakSwitch!!.isChecked = false
        }

        btnGoOnBreak.setOnClickListener {
            dialog.dismiss()
            takeBreakSwitch!!.isChecked = true
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
            loadData()
            dialog.dismiss()
        }

        btnGoBack.setOnClickListener {
            loadData()
            dialog.dismiss()
        }

        dialog.show()
    }


    fun startServiceFun() {
        setEmployeeStatusLast("1")
        val intent = Intent(requireActivity(), MonitoringLogsServiceKt::class.java)
        requireActivity().startService(intent)
    }

    /*********************************************
     * @author: Onysiia Babak
     * @date:   2023.6.17
     * @desc:   Request Permission
     */
//    private fun checkPermission() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Log.i("VERSION", "Android Version is suitable for this app")
//            val permission1 = Manifest.permission.SCHEDULE_EXACT_ALARM
//            val permission2 = Manifest.permission.SET_ALARM
//            val permission3 = Manifest.permission.USE_EXACT_ALARM
//            val permission4 = Manifest.permission.RECEIVE_BOOT_COMPLETED
//
//            val permissionResult1 = ContextCompat.checkSelfPermission(requireContext() , permission1)
//            val permissionResult2 = ContextCompat.checkSelfPermission(requireContext() , permission2)
//            val permissionResult3 = ContextCompat.checkSelfPermission(requireContext() , permission3)
//            val permissionResult4 = ContextCompat.checkSelfPermission(requireContext() , permission4)
//
//            if (permissionResult2 != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission2), 2)
//                // Handle the permission request result in onRequestPermissionsResult()
//
//                Log.e("Handle Permission", "SET_ALARM")
//            } else if (permissionResult2 != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission2), 1)
//                // Handle the permission request result in onRequestPermissionsResult()
//
//                Log.e("Handle Permission", "SCHEDULE_ALARM")
//                if (permissionResult3 != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission3), 3)
//                // Handle the permission request result in onRequestPermissionsResult()
//
//                    Log.e("Handle Permission", "EXACT_ALARM")
//
//                }
//            }
////           else
//           else  if (permissionResult4 != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission4), 4)
//                // Handle the permission request result in onRequestPermissionsResult()
//                    Log.e("Handle Permission", "BOOT_COMPLETED")
//
//                }
//            else {
//
//                // Permission is already granted, proceed with setting the alarm
//                Log.i("****permission*****", "****approve***")
//                setAlarm()
//                setEmployeeStatusLast("1")
//            }
//
//        } else {
//            Log.e("PERMISSOIN ERROR", "ANDROID VERSION NUMBER IS TOO LOW")
//            Toast.makeText(requireActivity(), "Android version is too low. Please retry with higher android!", Toast.LENGTH_LONG).show()
//        }
//    }

//    override  fun onRequestPermissionsResult(requestCode: Int,
//                                   permissions: Array<String>,
//                                   grantResults: IntArray) {
//
//        var grant1 = false
//        var grant2 =  false
//        var grant3 = false
//        var grant4 = false
//        when (requestCode) {
//            1 -> {
//                // If request is cancelled, the result arrays are empty
//                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    // Permission is granted, proceed with setting the alarm
//                    Log.i(" SCHEDULE_ALARM ", "****approve***")
////                    setAlarm()
//
//                    grant1 = true;
//                } else {
//                    // Permission is denied, show a message to the user
////                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
//                    grant1 = false;
//                    Log.e("SCHEDULE_ALARM", "SET_ALARM PERMISSION DENIED")
//
//                }
//                return
//            }
//            2 -> {
//                // If request is cancelled, the result arrays are empty
//                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    // Permission is granted, proceed with setting the alarm
//                    Log.i("**SET ALARM*********", "****approve***")
////                    setAlarm()
//                    grant2 = true;
//                } else {
//                    // Permission is denied, show a message to the user
//                    Log.e("SET ALARM PERMISSION", "SET_ALARM PERMISSION DENIED")
////                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
//                    grant2 = false;
//                }
//                return
//            }
//
//            3 -> {
//                // If request is cancelled, the result arrays are empty
//                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    // Permission is granted, proceed with setting the alarm
//                    Log.i("**request*********", "****approve***")
//                    grant3=  true;
////                    setAlarm()
//                } else {
//                    // Permission is denied, show a message to the user
//                    Log.i("**ERROR*********", "****EXACT_ALARM PERMISSION DENIED***")
//
////                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
//                    grant3 = false;
//                }
//                return
//            }
//            4 -> {
//                // If request is cancelled, the result arrays are empty
//                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
//                    // Permission is granted, proceed with setting the alarm
//                    Log.i("**request*********", "****approve***")
////                    setAlarm()
//                    grant4 = true;
//                } else {
//                    // Permission is denied, show a message to the user
////                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
//                    grant3 = false;
//                }
//                return
//            }
//
//            // Add more cases if needed
//
//            else -> {
//                // Ignore all other requests
//            }
//        }
//
//        try {
//            // Code that might throw an exception
//            Log.i("TRY ALARM", "TRY ALARM AFTER PERMISSION REQUEST")
//            setAlarm()
//            setEmployeeStatusLast("1")
//        } catch (exception: Exception) {
//            // Handle the exception
//            Log.i("ERROR", "**** PERMISSION REQUEST FAILED***")
//
//        }
//
//    }
    fun stopLogsService() {

//        setEmployeeStatusLast("0")
//        updateDeviceLogs("unlocked")

        val serviceIntent = Intent(requireActivity(), MonitoringLogsServiceKt::class.java)
        requireActivity().stopService(serviceIntent)

//        screenStateReceiver.unregister(requireActivity())

    }


    fun Context.isMyServiceRunning(serviceClass: Class<*>): Boolean {
        val manager = this.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return manager.getRunningServices(Int.MAX_VALUE)
            .any { it.service.className == serviceClass.name }
    }

    fun setEmployeeStatusLast(status:String) {

        val stringReq2 = object : StringRequest(Method.POST, URLs.SEND_STATUS, Response.Listener<String> { response ->
            var strResp2 = response.toString()
            Log.i("VolleyResponse", "->$status" + strResp2)
            val jsonObj2: JSONObject = JSONObject(strResp2)

            if (jsonObj2.get ("result").toString().equals("true")) {
//                    Toast.makeText(
//                        context, getString(R.string.txt_error_in_updating_status), Toast.LENGTH_SHORT
//                    ).show()
                Log.w("Monitoring Log Service", "Updated Users Status as $status")
            } else {
                Toast.makeText(
                    context, jsonObj2.get ("message").toString(), Toast.LENGTH_SHORT
                ).show()
            }
        }, Response.ErrorListener {
            Log.i("VolleyError", it.toString())
            Toast.makeText(
                context, getString(R.string.txt_error_in_updating_status), Toast.LENGTH_SHORT
            ).show()
        }) {
            override fun getParams(): MutableMap<String, String> {
                super.getParams()
                var map1 = java.util.HashMap<String, String>()
                map1["status"] = status
                map1["company_id"] = if (AppController.selectedCompany!= null) AppController.selectedCompany!!.companyID.toString() else ""
                return map1
            }
            override fun getHeaders(): MutableMap<String, String> {
                super.getHeaders()
                var headers = java.util.HashMap<String, String>()
                var bearer = "Bearer ${AppController.BEARER_ACCESS_TOKEN}"
                headers["Authorization"] = bearer
                return headers
            }

        }
        VolleySingleton(requireContext()).addToRequestQueue(stringReq2)
    }

}