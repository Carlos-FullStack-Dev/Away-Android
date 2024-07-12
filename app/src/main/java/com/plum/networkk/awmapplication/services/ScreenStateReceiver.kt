package com.plum.networkk.awmapplication.services

// ScreenStateReceiver.kt
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.apis.VolleySingleton
import com.plum.networkk.awmapplication.constants.URLs
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.HashMap
import java.util.Locale

class ScreenStateReceiver() : BroadcastReceiver() {
    private var startTimer: Long = 0
    private var endTimer: Long = 0
    private var screenOnTimeSingle: Long = 0
    private var i = 0;
    private var TIME_ERROR: Long = 1000
    override fun onReceive(context: Context, intent: Intent?) {

        if (intent?.action == Intent.ACTION_SCREEN_ON) {

            startTimer = System.currentTimeMillis();

        } else if (intent?.action == Intent.ACTION_SCREEN_OFF) {

            i = i+1
            endTimer = System.currentTimeMillis();
            if (i > 1){
                var diffTime = endTimer - startTimer;
                Log.i("****Screen Diff Time**", "${diffTime/1000}")
                updateDeviceLogs("locked",context, (diffTime/1000).toString())
//                updateDeviceLogs("locked", this@ScreenStateReceiver, (diffTime/1000).toString())
            }else{
                if(((endTimer - startTimer)/1000)>60){
                    updateDeviceLogs("locked",context, ((endTimer - startTimer)/1000).toString())
                }
            }

//            val date = Date(endTimer)
//            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
//            val formattedTime = dateFormat.format(date)




        }
    }



    fun timeRegister( startTime:Long ) {
        startTimer = startTime
    }

    fun timeunregister(num: Int) {
        i = 0
    }
    fun getStartTime(): Long {
        return startTimer
    }
    fun getEndTime(endTime:Long, mContext: Context){
        var diffTime = endTime-startTimer
        if ((diffTime/1000)>60){
            updateDeviceLogs("locked",mContext, (diffTime/1000).toString())
        }

    }
    fun updateDeviceLogs(status: String, mContext: Context, diffTime: String) {

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
                    map1["diffTime"] = diffTime
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


}
