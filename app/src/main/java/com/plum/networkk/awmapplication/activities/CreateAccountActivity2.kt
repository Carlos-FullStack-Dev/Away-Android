package com.plum.networkk.awmapplication.activities

//import com.plum.networkk.awmapplication.apis.ApisSericesInterface
//import com.plum.networkk.awmapplication.apis.RetrofitServiceSignIn
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
import com.plum.networkk.awmapplication.apis.VolleySingleton
import com.plum.networkk.awmapplication.constants.URLs
import com.plum.networkk.awmapplication.constants.mConstants
import com.plum.networkk.awmapplication.constants.mConstants.Companion.ACCOUNT_TYPE_BUSINESS
import com.plum.networkk.awmapplication.constants.mConstants.Companion.ACCOUNT_TYPE_EMPLOYEE
import com.plum.networkk.awmapplication.data_model.UserResponse
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences
import com.plum.networkk.awmapplication.databinding.ActivityCreateAccount2Binding
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject

//import retrofit2.CallN
//import retrofit2.Response

class CreateAccountActivity2 : AppCompatActivity() {
    var inviteCode = ""
    var accountType: String? = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_create_account)


        var vBinding = ActivityCreateAccount2Binding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)
        setSupportActionBar(vBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

//        accountType = intent.getStringExtra("AccountType")
        vBinding.tvEmployee.setOnClickListener {
            if (vBinding.tvEmployee!!.isSelected) {
//                tv_employee.isSelected = false
            } else {
                accountType = ACCOUNT_TYPE_EMPLOYEE
                vBinding.tvEmployee.isSelected = true
                vBinding.tvBusiness.isSelected = false

            }
        }



        vBinding.tvBusiness.setOnClickListener {
            if (vBinding.tvBusiness!!.isSelected) {
//                tv_business.isSelected = false

            } else {

                accountType = ACCOUNT_TYPE_BUSINESS
                vBinding.tvBusiness.isSelected = true
                vBinding.tvEmployee.isSelected = false
            }
        }
//        if (accountType.equals(ACCOUNT_TYPE_BUSINESS)) {
//
//            vBinding.tvAccountTypeName.text = resources.getString(R.string.txt_bussiness_account)
//            vBinding.topBannerLayout.setBackgroundResource(R.drawable.inspector_with_notebook)
//
////            vBinding.etCompanyName.visibility = View.VISIBLE
////            vBinding.etInviteCode.visibility = View.GONE
////
//        }
//        if (accountType.equals(ACCOUNT_TYPE_EMPLOYEE)) {
//
//            vBinding.tvAccountTypeName.text = resources.getString(R.string.txt_employee_account)
//            vBinding.topBannerLayout.setBackgroundResource(R.drawable.barista_on_phone)
//
////            vBinding.etCompanyName.visibility = View.GONE
////            vBinding.etInviteCode.visibility = View.VISIBLE
//        }


        vBinding.btnNext.setOnClickListener {

//            if (accountType.equals(ACCOUNT_TYPE_EMPLOYEE)) {


            if (
                (vBinding.etFname.text.length > 0) &&
                (vBinding.etlname.text.length > 0) &&
                (vBinding.etEmail.text.length > 0) &&
                (vBinding.etPassword.text.length > 0) &&
                (vBinding.etConfirmPassword.text.length > 0  && accountType.toString().length>0)
            ) {

                if (!(mConstants.isValidEmail(vBinding.etEmail.text))) {
                    vBinding.etEmail.setError("Email not valid")
                } else if (vBinding.etPassword.text.equals("")) {
                    vBinding.etPassword.setError("Password can't be empty")
                } else if (vBinding.etConfirmPassword.text.equals("")) {
                    vBinding.etConfirmPassword.setError("Password can't be empty")
                }
//                else if(accountType.equals(ACCOUNT_TYPE_EMPLOYEE) && vBinding.etInviteCode.text.equals("")){
//                    vBinding.etInviteCode.setError("Invite Code can't be empty")
//                }
//                else if(accountType.equals(ACCOUNT_TYPE_BUSINESS) && vBinding.etCompanyName.text.equals("")){
//                    vBinding.etCompanyName.setError("Company Name can't be empty")
//                }

                else if (!(vBinding.etPassword.text.toString() == vBinding.etConfirmPassword.text.toString())) {
                    vBinding.etConfirmPassword.setError("Password doesn't match")
                } else {

                    var userRoleId = "3"
                    if (accountType.equals(ACCOUNT_TYPE_EMPLOYEE)) {
                        userRoleId = mConstants.ROLL_ID_EMPLOYEE

                    } else if (accountType.equals(ACCOUNT_TYPE_BUSINESS)) {

                        userRoleId = mConstants.ROLL_ID_BUSINESS_MANAGER
                        Log.i("role value", "${userRoleId}")
                    }

                    // go to registeration apis
                    progressDialog()
//                    inviteCode = vBinding.etInviteCode.text.toString()
                    siginup(
                        vBinding.etFname.text.toString(),
                        vBinding.etlname.text.toString(),
                        vBinding.etEmail.text.toString(),
                        vBinding.etPassword.text.toString(),
                        userRoleId
                    )
//                        siginup("arshad", "iqbal", "arshadiqbal268@gmail.com", "test")

                }

            } else {

                if (vBinding.etFname.text.length <= 0) {
                    vBinding.etFname.setError("" + resources.getString(R.string.txt_insert_first_name))
                } else if (vBinding.etlname.text.length <= 0) {
                    vBinding.etlname.setError("" + resources.getString(R.string.txt_insert_last_name))
                } else if (vBinding.etEmail.text.length <= 0) {
                    vBinding.etEmail.setError("" + resources.getString(R.string.txt_insert_email))
                } else if (vBinding.etPassword.text.length <= 0) {
                    vBinding.etPassword.setError("" + resources.getString(R.string.txt_insert_password))
                } else if (vBinding.etConfirmPassword.text.length <= 0) {
                    vBinding.etConfirmPassword.setError("" + resources.getString(R.string.txt_insert_password))
                } else if(accountType.toString().length<=0){
                    Log.i("account Type", "no!!!")
                    FancyToast.makeText(this,"Please select the role for account!",
                        FancyToast.LENGTH_LONG,
                        FancyToast.ERROR,true).show();
                }
//                else if (vBinding.etInviteCode.text.length<=0){
//                    vBinding.etInviteCode.setError("" + resources.getString(R.string.txt_insert_invite_code))
//                }

            }

            /*} else if (accountType.equals(ACCOUNT_TYPE_BUSINESS)) {
                var menuIntent =
                    Intent(this@CreateAccountActivity2, MenuActivityEmployer::class.java)
                startActivity(menuIntent)
            }*/

        }
    }


    var progressDialog: Dialog? = null
    fun progressDialog() {

        if (progressDialog != null) {
            if (progressDialog!!.isShowing)
                progressDialog!!.dismiss()
        }

        progressDialog = Dialog(this@CreateAccountActivity2/*, R.style.SearchFieldSetterDialog*/)
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

    fun siginup(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        roleId: String
    ) {
        val stringReq1 = object : StringRequest(Method.POST, URLs.REGISTRATION, Response.Listener<String> { response ->

            var strResp1 = response.toString()
            Log.i("VolleyResponse", "" + strResp1)
            val jsonObj2: JSONObject = JSONObject(strResp1)
            progressDialog!!.dismiss()
            if (jsonObj2.has("data")) {
                progressDialog()
                login(email, password)
            } else {
                progressDialog!!.dismiss()
                Toast.makeText(
                    this, resources.getString(R.string.txt_error_signingin), Toast.LENGTH_SHORT
                ).show()
            }
        }, Response.ErrorListener {
            progressDialog!!.dismiss()
            Log.i("VolleyError", it.toString())
        }) {
            override fun getParams(): MutableMap<String, String> {
                super.getParams()
                var map1 = HashMap<String, String>()
                map1["email"] = email
                map1["password"] = password
                map1["first_name"] = firstName
                map1["last_name"] = lastName
                map1["role_id"] = roleId
                return map1
            }
        }

        VolleySingleton(this).addToRequestQueue(stringReq1)


//        var signup = SignUpModel(email, password, firstName, lastName, roleId)
//        var netWorkApi = RetrofitServiceSignIn.cteateService(ApisSericesInterface::class.java)
//
//        netWorkApi.UserRegisteration(
//            signup
//        )!!.enqueue(object : retrofit2.Callback<RegistrationResponseModel> {
//
//            override fun onResponse(
//                call: Call<RegistrationResponseModel>,
//                response: Response<RegistrationResponseModel>
//            ) {
//
//                progressDialog!!.dismiss()
////                Log.e("SigininActivity", "" + call.request().url())
//                if (response.isSuccessful) {
//                    if (response.body() == null) {
//                        //error in signing up
//                        problemRegisteringDialog()
//                    } else {
//                        //successful signup
//
//                        MySharedPreferences.editStringPreferences(
//                            MySharedPreferences.TokenSinginIn,
//                            "" + response.body()!!.data!!.access_token,
//                            this@CreateAccountActivity2
//                        )
//
//                        MySharedPreferences.editBooleanPreferences(
//                            MySharedPreferences.SignedIn,
//                            true,
//                            this@CreateAccountActivity2
//                        )
//
//
//                        if (accountType.equals(ACCOUNT_TYPE_EMPLOYEE)) {
//
//                            MySharedPreferences.editStringPreferences(
//                                MySharedPreferences.SignedInAs,
//                                "Employee",
//                                this@CreateAccountActivity2
//                            )
//                            var menuIntent =
//                                Intent(
//                                    this@CreateAccountActivity2,
//                                    MenuActivityEmployee::class.java
//                                )
//                            startActivity(menuIntent)
//                        } else {
//
//                            MySharedPreferences.editStringPreferences(
//                                MySharedPreferences.SignedInAs,
//                                "Business Manager",
//                                this@CreateAccountActivity2
//                            )
//
//                            var menuIntent =
//                                Intent(
//                                    this@CreateAccountActivity2,
//                                    MenuActivityEmployer::class.java
//                                )
//                            startActivity(menuIntent)
//                        }
//                    }
//
//                } else {
//                    //error in signing up
//                    problemRegisteringDialog()
//                }
//            }
//
//            override fun onFailure(call: Call<RegistrationResponseModel>, t: Throwable) {
//                Log.e("SigininActivity", "" + call.request().url())
//                //error in signing in
//                Log.e("SigninActivity", "error:" + t)
//                progressDialog!!.dismiss()
//                problemRegisteringDialog()
//            }
//
//        })
    }

    private fun login(email: String, password: String) {
        Log.i("VolleyResponse", "Inside Login API")
        AppController.user = null
        val stringReq1 = object : StringRequest(Method.POST, URLs.LOGIN, Response.Listener<String> { response ->

            var strResp1 = response.toString()
            Log.i("VolleyResponse", "" + strResp1)
            val jsonObj2: JSONObject = JSONObject(strResp1)
            progressDialog!!.dismiss()
            if (jsonObj2.get("status_code").toString().equals("200")) {
                var gson = Gson()
                var user = gson.fromJson(strResp1, UserResponse::class.java)
                AppController.user = user
                Log.i("VolleyResponse", user?.user?.email!!)
                AppController.BEARER_ACCESS_TOKEN = "" + user.accessToken
                MySharedPreferences.editStringPreferences(MySharedPreferences.TOKEN_KEY, AppController.BEARER_ACCESS_TOKEN, this@CreateAccountActivity2)
                MySharedPreferences.editBooleanPreferences(MySharedPreferences.SIGNED_IN_KEY, true, this@CreateAccountActivity2)
                MySharedPreferences.editStringPreferences(MySharedPreferences.SIGNED_IN_AS_KEY, "Employee", this@CreateAccountActivity2)
                MySharedPreferences.editStringPreferences(MySharedPreferences.USERNAME_KEY, email, this@CreateAccountActivity2)
                MySharedPreferences.editStringPreferences(MySharedPreferences.PASSWORD_KEY, password, this@CreateAccountActivity2)
                var signIntent = Intent(this, CreateJoinCompanyActivity::class.java)
                signIntent.putExtra("AccountType", accountType)
                signIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(signIntent)
            } else {
                progressDialog!!.dismiss()
                Toast.makeText(
                    this, resources.getString(R.string.txt_error_signingin), Toast.LENGTH_SHORT
                ).show()
            }
        }, Response.ErrorListener {
            progressDialog!!.dismiss()
            Log.i("VolleyError", it.toString())
        }) {
            override fun getParams(): MutableMap<String, String> {
                super.getParams()
                var map1 = HashMap<String, String>()
                map1["email"] = email
                map1["password"] = password
                return map1
            }
        }
        VolleySingleton(this).addToRequestQueue(stringReq1)

    }


    fun problemRegisteringDialog() {
        val dialog = Dialog(this@CreateAccountActivity2/*, R.style.SearchFieldSetterDialog*/)
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

        dialogMessage.text = "" + resources.getString(R.string.txt_error_registeration)
        val btnSend = dialog.findViewById(R.id.btnSend) as RelativeLayout

        btnSend.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}