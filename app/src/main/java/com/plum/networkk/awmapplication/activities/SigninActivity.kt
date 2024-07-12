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
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.apis.VolleySingleton
import com.plum.networkk.awmapplication.constants.URLs
import com.plum.networkk.awmapplication.constants.mConstants
import com.plum.networkk.awmapplication.data_model.UserResponse
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences.PASSWORD_KEY
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences.SIGNED_IN_AS_KEY
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences.SIGNED_IN_KEY
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences.TOKEN_KEY
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences.USERNAME_KEY
import com.plum.networkk.awmapplication.databinding.ActivitySigninBinding
import com.plum.networkk.awmapplication.utils.makeStatusBarTransparent
import com.shashank.sony.fancytoastlib.FancyToast
import org.json.JSONObject

//import retrofit2.Call
//import retrofit2.Response

class SigninActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_signin)
        var vBinding = ActivitySigninBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)

        /****************************************
         * Hide Appbar & Transparent Status Bar
         */
        makeStatusBarTransparent()
        setSupportActionBar(vBinding!!.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.appbar_arrow_back_black);

        /********************************************
         * SignIn Button Click Event Handler
         */
        vBinding.btnNext.setOnClickListener {

            if (vBinding.etEmail.text.length > 0) {
                if (!(mConstants.isValidEmail(vBinding.etEmail.text))) {
                    vBinding.etEmail.setError("Email not valid");
                }

                if (vBinding.etPassword.text.length > 0) {

                    //go to sigin function
                    siginInFun(
                        vBinding.etEmail.text.toString(), vBinding.etPassword.text.toString()
                    )
                } else {
                    vBinding.etPassword.setError("password is required");
                }
            } else {
                vBinding.etEmail.setError("Email Required")

            }


        }

        /********************************************
         * Forget Password Button Click Event Handler
         */
        vBinding.tvForgotPassword.setOnClickListener {
            forgotPasswordDialog()
        }
    }

    /***********************************************
     * @author: geniusdev0813@gmail.com
     * @desc: SignIn function
     * @param: email, password
     */
    fun siginInFun(email: String, password: String) {

        progressDialog()
        val stringReq2 = object : StringRequest(Method.POST, URLs.LOGIN, Response.Listener<String> { response ->

            var strResp2 = response.toString()
            Log.i("VolleyResponse", "" + strResp2)
            val jsonObj2: JSONObject = JSONObject(strResp2)

            if (jsonObj2.get("status_code").toString().equals("200")) {
                var gson = Gson()
                var user = gson.fromJson(strResp2, UserResponse::class.java)
                AppController.user = user
                Log.i("VolleyResponse",user?.user?.email!!)
                AppController.BEARER_ACCESS_TOKEN = jsonObj2.getString("access_token")
                MySharedPreferences.editStringPreferences(TOKEN_KEY, AppController.BEARER_ACCESS_TOKEN, this@SigninActivity)
                MySharedPreferences.editBooleanPreferences(SIGNED_IN_KEY, true, this@SigninActivity)
                MySharedPreferences.editStringPreferences(SIGNED_IN_AS_KEY, user.role.toString(), this@SigninActivity)
                MySharedPreferences.editStringPreferences(USERNAME_KEY, email, this@SigninActivity)
                MySharedPreferences.editStringPreferences(PASSWORD_KEY, password, this@SigninActivity)

                progressDialog!!.dismiss()

                if(user.role.equals("Employee")){
                    var signIntent = Intent(this@SigninActivity, MenuActivityEmployee::class.java)
                    signIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(signIntent)
                    finish()
                } else {
                    var signIntent = Intent(this@SigninActivity, MenuActivityEmployer::class.java)
                    signIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(signIntent)
                    finish()
                }
            } else {
                progressDialog!!.dismiss()
                FancyToast.makeText(this,resources.getString(R.string.txt_error_signingin),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//                Toast.makeText(
//                    this, resources.getString(R.string.txt_error_signingin), Toast.LENGTH_LONG
//                ).show()
            }
        }, Response.ErrorListener {
            progressDialog!!.dismiss()
            Log.i("VolleyError", it.toString())
            FancyToast.makeText(this,it.toString(),FancyToast.LENGTH_LONG,FancyToast.ERROR,true).show();
//            Toast.makeText(
//                this, resources.getString(R.string.txt_error_signingin), Toast.LENGTH_LONG
//            ).show()
        }) {
            override fun getParams(): MutableMap<String, String> {
                super.getParams()
                var map1 = HashMap<String, String>()
                map1["email"] = email
                map1["password"] = password
                return map1
            }
        }

        VolleySingleton(this).addToRequestQueue(stringReq2)

    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    var progressDialog: Dialog? = null
    fun progressDialog() {

        if (progressDialog != null) {
            if (progressDialog!!.isShowing) progressDialog!!.dismiss()
        }

        progressDialog = Dialog(this@SigninActivity/*, R.style.SearchFieldSetterDialog*/)
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.setCanceledOnTouchOutside(false)
        progressDialog!!.setCancelable(false)
        progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window: Window = progressDialog!!.window!!
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        progressDialog!!.setContentView(R.layout.progress_dialog)
        progressDialog!!.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        progressDialog!!.show()
    }


    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }


    fun forgotPasswordDialog() {
        val dialog = Dialog(this@SigninActivity/*, R.style.SearchFieldSetterDialog*/)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window: Window = dialog.window!!
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setContentView(R.layout.forgot_password_dialog)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val et_email = dialog.findViewById(R.id.et_email) as EditText
        val cancelDialog = dialog.findViewById(R.id.cancelDialog) as ImageView
        val btnSend = dialog.findViewById(R.id.btnSend) as RelativeLayout

        btnSend.setOnClickListener {
            dialog.dismiss()
        }


        cancelDialog.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


    fun problemSigingInDialog() {
        val dialog = Dialog(this@SigninActivity/*, R.style.SearchFieldSetterDialog*/)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val window: Window = dialog.window!!
//        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog.setContentView(R.layout.error_dialog)
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val dialogMessage = dialog.findViewById(R.id.dialogMessage) as TextView

        dialogMessage.text = "" + resources.getString(R.string.txt_error_signingin)

        val btnSend = dialog.findViewById(R.id.btnSend) as RelativeLayout

        btnSend.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }


}