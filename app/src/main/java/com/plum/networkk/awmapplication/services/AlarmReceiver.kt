//package com.plum.networkk.awmapplication.services
//
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.util.Log
//import android.widget.Toast
//import com.android.volley.Response
//import com.android.volley.toolbox.StringRequest
//import com.plum.networkk.awmapplication.R
//import com.plum.networkk.awmapplication.apis.VolleySingleton
//import com.plum.networkk.awmapplication.constants.URLs
//import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences
//import org.json.JSONObject
//import java.time.LocalDateTime
//import java.time.format.DateTimeFormatter
//import android.app.*
//import android.content.IntentFilter
//import android.os.IBinder
//import com.plum.networkk.awmapplication.AppController
//import com.plum.networkk.awmapplication.activities.SplashActivity
//import org.joda.time.DateTime
//import org.joda.time.format.DateTimeFormat
//import java.util.HashMap
//
//
//class AlarmReceiver : Service() {
//    private val receiver = ScreenStateReceiver()
//
//    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        Log.i("****jgj service****", "****testing*********")
//        registerReceiver(receiver, IntentFilter(Intent.ACTION_SCREEN_ON))
//        registerReceiver(receiver, IntentFilter(Intent.ACTION_SCREEN_OFF))
//        receiver.timeRegister(System.currentTimeMillis())
//        return START_STICKY
//    }
//
//    override fun onDestroy() {
//        Log.e("MonitoringLogsService", "MonitoringService: onDestroy")
//        super.onDestroy()
//        receiver.timeunregister(0)
//        var startTime = receiver.getStartTime()
//        var endTime = receiver.getEndTime()
//        setEmployeeStatusLast("0")
//        updateDeviceLogs("unlocked")
//        unregisterReceiver(receiver)
//
//    }
//    override fun stopService(name: Intent?): Boolean {
//        Log.e("MonitoringLogsService", "STOPPPED!!! MonitoringService: onDestroy")
//        super.onDestroy()
//        return null == true
//    }
//    fun alarmStopService(name: Intent?): Boolean {
//        super.onDestroy()
//        receiver.timeunregister(0)
//        unregisterReceiver(receiver)
//        return true
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//    fun setEmployeeStatusLast(status:String) {
//
//        val stringReq2 = object : StringRequest(Method.POST, URLs.SEND_STATUS, Response.Listener<String> { response ->
//            var strResp2 = response.toString()
//            Log.i("VolleyResponse", "->$status" + strResp2)
//            val jsonObj2: JSONObject = JSONObject(strResp2)
//
//            if (jsonObj2.get ("result").toString().equals("true")) {
////                    Toast.makeText(
////                        context, getString(R.string.txt_error_in_updating_status), Toast.LENGTH_SHORT
////                    ).show()
//                Log.w("Monitoring Log Service", "Updated Users Status as $status")
//            } else {
//                Toast.makeText(
//                    this, jsonObj2.get ("message").toString(), Toast.LENGTH_SHORT
//                ).show()
//            }
//        }, Response.ErrorListener {
//            Log.i("VolleyError", it.toString())
//            Toast.makeText(
//                this, getString(R.string.txt_error_in_updating_status), Toast.LENGTH_SHORT
//            ).show()
//        }) {
//            override fun getParams(): MutableMap<String, String> {
//                super.getParams()
//                var map1 = java.util.HashMap<String, String>()
//                map1["status"] = status
//                map1["company_id"] = if (AppController.selectedCompany!= null) AppController.selectedCompany!!.companyID.toString() else ""
//                return map1
//            }
//            override fun getHeaders(): MutableMap<String, String> {
//                super.getHeaders()
//                var headers = java.util.HashMap<String, String>()
//                var bearer = "Bearer ${AppController.BEARER_ACCESS_TOKEN}"
//                headers["Authorization"] = bearer
//                return headers
//            }
//
//        }
//        VolleySingleton(this).addToRequestQueue(stringReq2)
//    }
//
//    fun updateDeviceLogs(status: String) {
//
//
//        val stringReq2 = object : StringRequest(Method.POST, URLs.UPDATE_DEVICE_LOGS, Response.Listener<String> { response ->
//
//            var strResp2 = response.toString()
//            Log.i("VolleyResponse", "$status->" + strResp2)
//            val jsonObj2: JSONObject = JSONObject(strResp2)
//
//            if (jsonObj2.get ("result").toString().equals("true")) {
//
//
//            } else {
//                Toast.makeText(
//                    this, getString(R.string.txt_error_stats_logs), Toast.LENGTH_SHORT
//                ).show()
//            }
//        }, Response.ErrorListener {
//            Log.i("VolleyError", it.toString())
//            Toast.makeText(
//                this, getString(R.string.txt_error_stats_logs), Toast.LENGTH_SHORT
//            ).show()
//        }) {
//            override fun getParams(): MutableMap<String, String> {
//                super.getParams()
//                var map1 = HashMap<String, String>()
//                map1["status"] = status
//                map1["company_id"] = if (AppController.selectedCompany!= null) AppController.selectedCompany!!.companyID.toString() else ""
//                return map1
//            }
//            override fun getHeaders(): MutableMap<String, String> {
//                super.getHeaders()
//                var headers = HashMap<String, String>()
//                var bearer = "Bearer ${AppController.BEARER_ACCESS_TOKEN}"
//                headers["Authorization"] = bearer
//                return headers
//            }
//
//        }
//        VolleySingleton(this).addToRequestQueue(stringReq2)
//    }
//}
