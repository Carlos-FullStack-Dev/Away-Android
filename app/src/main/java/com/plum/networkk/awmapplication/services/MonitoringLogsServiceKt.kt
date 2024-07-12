package com.plum.networkk.awmapplication.services

//import retrofit2.Call
//import retrofit2.Response

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.activities.SplashActivity
import com.plum.networkk.awmapplication.apis.VolleySingleton
import com.plum.networkk.awmapplication.constants.URLs
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences
import org.json.JSONObject


class MonitoringLogsServiceKt : Service() {
//    private val mInterval = 1 * 2 * 1000 // 1 minute by default, can be changed later
//    private val mInterval = 1 * 60 * 1000
//    private var mHandler: Handler? = null
//    public  var i = 0;
    private val receiver = ScreenStateReceiver()
    override fun onBind(intent: Intent): IBinder? {
        Log.e("MonitoringLogsService", "MonitoringService: OnBind")
//        LogsTimer()
        return null
    }


    override fun onCreate() {
        Log.e("MonitoringLogsService", "MonitoringService: onCreate")

        super.onCreate()
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.e("MonitoringLogsService", "MonitoringService: onStartCommand")
        val input = intent.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, SplashActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Logs Monitoring Service")
            .setContentText(input)
            .setSmallIcon(R.drawable.app_icon_a)
            .setContentIntent(pendingIntent)
            .build()
        startForeground(1, notification)

        registerReceiver(receiver, IntentFilter(Intent.ACTION_SCREEN_ON))
        registerReceiver(receiver, IntentFilter(Intent.ACTION_SCREEN_OFF))
        receiver.timeRegister(System.currentTimeMillis())
        return START_STICKY
    }

    override fun stopService(name: Intent?): Boolean {
        Log.e("MonitoringLogsService", "STOPPPED!!! MonitoringService: onDestroy")
        super.onDestroy()
        return null == true
    }

    override fun onDestroy() {
        Log.e("MonitoringLogsService", "MonitoringService: onDestroy")
        super.onDestroy()
        receiver.timeunregister(0)
        setEmployeeStatus("0", this@MonitoringLogsServiceKt)
        receiver.getEndTime(System.currentTimeMillis(), this@MonitoringLogsServiceKt)
        updateDeviceLogs("unlocked", this@MonitoringLogsServiceKt)
        unregisterReceiver(receiver)
//        stopLogsTimer()
//        updateDeviceLogs("unlocked", this@MonitoringLogsServiceKt)
    }

//    override fun onTaskRemoved(rootIntent: Intent) {
//        Log.e("MonitoringLogsService", "MonitoringService: onTaskRemoved")
//        super.onTaskRemoved(rootIntent)
////        stopLogsTimer()
//    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Logs Monitoring Service Channel",
                NotificationManager.IMPORTANCE_HIGH
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
    }

    companion object {
        const val CHANNEL_ID = "MonitoringLogsServiceChanel"
    }

    /********************************
     * Start Log Timer
     */
//    fun LogsTimer() {
//        mStatusChecker.run();
//        Log.e("MonitoringLogsService", "MonitoringService: timer Starts")
//        setEmployeeStatus("1", this@MonitoringLogsServiceKt)
//    }

    /*****************************
     * Stop Log Task
     */
//    fun stopLogsTimer() {
//        mHandler!!.removeCallbacks(mStatusChecker)
//        setEmployeeStatus("0", this@MonitoringLogsServiceKt)
//    }

    /*****************************
     * Process Service
     */
//    var mStatusChecker: Runnable = object : Runnable {
//        override fun run() {
//
//            try {
//
//                /**
//                 * Save Log into BackendDatabase via HTTP Call
//                 */
//                if (i ==0){
//
//                }else{
//                    updateDeviceLogs("locked", this@MonitoringLogsServiceKt)
//                }
//                i = i+1
//                /**
//                 * Stop other all background services
//                 */
//                //val am = getSystemService(Activity.ACTIVITY_SERVICE) as ActivityManager
//                //am.killBackgroundProcesses(packageName)
//            } finally {
//                // 100% guarantee that this always happens, even if
//                // your update method throws an exception
//                mHandler!!.postDelayed(this, mInterval.toLong())
//            }
//        }
//    }

    /***************************************
     * @auth: geniusdev0813@gmail.com
     * @Date: 2022.10.25
     * @Desc: Save Log into Backend Database
     */
    fun updateDeviceLogs(status: String, mContext: Context) {

        var authorizationToken =
            MySharedPreferences.getStringPreferences(
                MySharedPreferences.TOKEN_KEY,
                mContext,
                "empty"
            )
        var companyID =  MySharedPreferences.getStringPreferences(
            MySharedPreferences.COMPANY_ID,
            mContext,
            ""
        )
//        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
//        val currentTime = LocalDateTime.now().format(formatter)
//        Log.e("current time", "${currentTime}")
//        var formatter = DateTimeFormat.forPattern("yyyy-MM-dd hh:mm:ss")
//        var currentTime = formatter.parseDateTime()
        if (!authorizationToken.equals("empty") && !companyID.equals("")) {

            val stringReq2 = object : StringRequest(Method.POST, URLs.UPDATE_DEVICE_LOGS, Response.Listener<String> { response ->

                var strResp2 = response.toString()
                Log.i("VolleyResponse", "" + strResp2)
                val jsonObj2: JSONObject = JSONObject(strResp2)
                if (jsonObj2.get("result") == true) {
                    Toast.makeText(
                        mContext, "Status Updated Successfully", Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        mContext, mContext.getString(R.string.txt_error_stats_logs), Toast.LENGTH_SHORT
                    ).show()
                }
            }, Response.ErrorListener {
                Log.i("VolleyError", it.toString())
                Toast.makeText(
                    mContext, mContext.getString(R.string.txt_error_stats_logs), Toast.LENGTH_SHORT
                ).show()
            }) {
                override fun getParams(): MutableMap<String, String> {
                    super.getParams()
                    var map1 = HashMap<String, String>()
//                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
//                    val current = LocalDateTime.now()
                    map1["status"] = status
                    map1["company_id"] = companyID!!
//                    map1["time_log"] = currentTime.toString()
                    return map1
                }
                override fun getHeaders(): MutableMap<String, String> {
                    super.getHeaders()
                    var headers = HashMap<String, String>()
                    var bearer = "Bearer $authorizationToken"
                    headers["Authorization"] = bearer
                    return headers
                }

            }
            VolleySingleton(mContext).addToRequestQueue(stringReq2)
        }
    }


    /***************************************
     * @auth: geniusdev0813@gmail.com
     * @date: 2022.11.25
     * @desc: Set Users Status( Online or Offline)
     */
    fun setEmployeeStatus(status:String,  mContext: Context) {

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
                    mContext, jsonObj2.get ("message").toString(), Toast.LENGTH_SHORT
                ).show()
            }
        }, Response.ErrorListener {
            Log.i("VolleyError", it.toString())
            Toast.makeText(
                mContext, getString(R.string.txt_error_in_updating_status), Toast.LENGTH_SHORT
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
        VolleySingleton(mContext).addToRequestQueue(stringReq2)
    }

}