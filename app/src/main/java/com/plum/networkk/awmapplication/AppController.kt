package com.plum.networkk.awmapplication
import android.app.Activity
import android.app.Application
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.text.TextUtils
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.ListView
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.Gson
import com.plum.networkk.awmapplication.apis.VolleySingleton
import com.plum.networkk.awmapplication.constants.URLs
import com.plum.networkk.awmapplication.data_model.*
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences
import org.json.JSONObject
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by aCodeMechanic on 2019-06-17.
 */
class AppController : Application() {

    companion object {
//        // Selected Company for Employee
//        var selectedCompany: EmployerCompany? = null


        var companies: List<Company>? = null
        var selectedCompany: Company ? = null
        var userRole: Int = 0
        var employee: EmployeeDetail? = null
        var firstName = ""
        var employerCompanyName = ""
        var user: UserResponse? = null
        var statistics: StatisticsResponse? = null
        var statisticsEmployer: StatisticsResponse? = null
        var employeeList: ArrayList<Employee> = ArrayList()
        var TICKET_VIEW = 0
        var BEARER_ACCESS_TOKEN = ""

        var employeeDataList: ArrayList<EmployeeData> = ArrayList()

        var selectedEmployee: EmployeeDetailResponse? = null


        val TAG = AppController::class.java.simpleName

        private var mRequestQueue: RequestQueue? = null

        private var mInstance: AppController? = null

        private var mContext: Context? = null

        private var cookieManager: CookieManager? = null
        internal var appName = ""

        fun getDate(dateString: String, finalFormat: String): String {
            val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
//            formatter.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
            var value: Date? = null
            try {
                value = formatter.parse(dateString)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            val dateFormatter = SimpleDateFormat(finalFormat)
            dateFormatter.timeZone = TimeZone.getDefault()
            return dateFormatter.format(value!!)
        }
        fun getTime(dateString: String, finalFormat: String): String {
            val formatter = SimpleDateFormat("HH:mm:ss")
//            formatter.timeZone = TimeZone.getTimeZone("Asia/Kolkata")
            var value: Date? = null
            try {
                value = formatter.parse(dateString)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            val dateFormatter = SimpleDateFormat(finalFormat)
            dateFormatter.timeZone = TimeZone.getDefault()
            return dateFormatter.format(value as Date)
        }

        fun setListViewHeightBasedOnChildren(listView: ListView) {
            val listAdapter = listView.adapter ?: return

            val desiredWidth =
                View.MeasureSpec.makeMeasureSpec(listView.width, View.MeasureSpec.UNSPECIFIED)
            var totalHeight = 0
            var view: View? = null
            for (i in 0 until listAdapter.count) {
                view = listAdapter.getView(i, view, listView)
                if (i == 0)
                    view!!.layoutParams =
                        ViewGroup.LayoutParams(desiredWidth, ViewGroup.LayoutParams.WRAP_CONTENT)

                view!!.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED)
                totalHeight += view!!.measuredHeight
            }
            val params = listView.layoutParams
            params.height = totalHeight + listView.dividerHeight * (listAdapter.count - 1)
            listView.layoutParams = params
        }

        fun handleEditTextInputType(editText: TextInputEditText) {
            editText.typeface = Typeface.DEFAULT
            editText.transformationMethod = PasswordTransformationMethod()
        }

        fun getTodayDate(dateString: String): String {
            val c = Calendar.getInstance().time
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val s = formatter.format(c)
            if (dateString.contains(s)) {
                val time_formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                var value: Date? = null
                try {
                    value = time_formatter.parse(dateString)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }

                val dateFormatter = SimpleDateFormat("hh:mm a")
                return dateFormatter.format(value)
            } else {
                if (dateString.contains(" ")) {
                    val time_formatter = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                    var value: Date? = null
                    try {
                        value = time_formatter.parse(dateString)

                    } catch (e: ParseException) {
                        e.printStackTrace()
                    }

                    val dateFormatter = SimpleDateFormat("dd MMM hh:mm a")
                    return if (value != null) dateFormatter.format(value) else ""
                } else
                    return ""
            }
        }

        fun isValidEmailAddress(email: String): Boolean {
            val ePattern =
                "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$"
            val p = java.util.regex.Pattern.compile(ePattern)
            val m = p.matcher(email)
            return m.matches()
        }

        fun handleEditTextEventListener(editText: List<TextInputEditText>, activity: Activity) {
            for (e in editText) {
                e.setOnEditorActionListener { v, actionId, event ->
                    if (event != null && event.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                        //Log.i("Scene....", "Enter pressed");
                        hideKeyboard(activity)
                    }
                    true
                }
            }
        }

        fun hideKeyboard(activity: Activity?) {
            if (activity != null && activity.window != null && activity.window.decorView != null) {
                val imm =
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(activity.window.decorView.windowToken, 0)
            }
        }

        fun getCookieManager(): CookieManager? {
            return cookieManager
        }


        @Synchronized
        fun getInstance(): AppController? {
            return mInstance
        }

        fun getContext(): Context? {
            return mContext
        }


        /*** VOLLEY REQUEST MANAGER  */

        fun getRequestQueue(): RequestQueue? {
            if (mRequestQueue == null) {
                //set the server cookies, essential if your server require authentication
                cookieManager = CookieManager(null, CookiePolicy.ACCEPT_ALL)
                CookieHandler.setDefault(cookieManager)
                mRequestQueue = Volley.newRequestQueue(
                    mContext
                )
            }

            return mRequestQueue
        }

        fun <T> addToRequestQueue(req: Request<T>, tag: String) {
            // set the default tag if tag is empty
            req.tag = if (TextUtils.isEmpty(tag)) TAG else tag
            getRequestQueue()?.add(req)
        }

        fun <T> addToRequestQueue(req: Request<T>) {
            req.retryPolicy = DefaultRetryPolicy(
                30 * 1000, 0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
            )
            req.tag = TAG
            getRequestQueue()?.add(req)
        }

        fun cancelPendingRequests(tag: Any) {
            mRequestQueue?.cancelAll(tag)
        }
        fun signIn(context: Context,  myCallback: (result: Boolean?) -> Unit) {

            val stringReq2 = object : StringRequest(Method.POST, URLs.LOGIN, Response.Listener<String> { response ->

                var strResp2 = response.toString()
                Log.i("VolleyResponse", "" + strResp2)
                val jsonObj2: JSONObject = JSONObject(strResp2)
                if (jsonObj2.get("status_code").toString().equals("200")) {
                    var gson = Gson()
                    var user = gson.fromJson(strResp2, UserResponse::class.java)
                    AppController.user = user
                    Log.i("VolleyResponse",user?.user?.email!!)
                    AppController.BEARER_ACCESS_TOKEN = "" + jsonObj2.getString("access_token")
                    MySharedPreferences.editStringPreferences(MySharedPreferences.TOKEN_KEY, AppController.BEARER_ACCESS_TOKEN, context)
                    MySharedPreferences.editBooleanPreferences(MySharedPreferences.SIGNED_IN_KEY, true, context)
                    MySharedPreferences.editStringPreferences(MySharedPreferences.SIGNED_IN_AS_KEY, user.role.toString(), context)
//                    MySharedPreferences.editStringPreferences(MySharedPreferences.USERNAME_KEY, email, context)
//                    MySharedPreferences.editStringPreferences(MySharedPreferences.PASSWORD_KEY, password, context)
                    myCallback.invoke(true)
                } else {
//                    Toast.makeText(
//                        context, context.getString(R.string.txt_error_signingin), Toast.LENGTH_SHORT
//                    ).show()
                    myCallback.invoke(false)


                }
            }, Response.ErrorListener {
                Log.i("VolleyError", it.toString())
                myCallback.invoke(false)
            }) {
                override fun getParams(): MutableMap<String, String> {
                    super.getParams()
                    var map1 = HashMap<String, String>()
                    map1["email"] = MySharedPreferences.getStringPreferences(MySharedPreferences.USERNAME_KEY,context,"").toString()
                    map1["password"] = MySharedPreferences.getStringPreferences(MySharedPreferences.PASSWORD_KEY,context,"").toString()
                    return map1
                }
            }

            VolleySingleton(context).addToRequestQueue(stringReq2)

        }



        var progressDialog: Dialog? = null
        fun progressDialog(mContext: Activity) {

            if (progressDialog != null) {
                if (progressDialog!!.isShowing)
                    progressDialog!!.dismiss()
            }

            progressDialog = Dialog(mContext/*, R.style.SearchFieldSetterDialog*/)
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



    }

    override fun onCreate() {
        super.onCreate()
        mInstance = this //return the singleton
        mContext = applicationContext
        appName = getResources().getString(
            R.string.app_name
        )
        BEARER_ACCESS_TOKEN = getSharedPreferences(URLs.MY_PREFERENCES, Context.MODE_PRIVATE).getString("access_token","").toString()
    }

}
