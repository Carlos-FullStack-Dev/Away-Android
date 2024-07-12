package com.plum.networkk.awmapplication.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.Window
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.Snackbar
import com.plum.networkk.awmapplication.AppController
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.database.msharedpreference.MySharedPreferences
import com.plum.networkk.awmapplication.utils.makeStatusBarTransparent
import com.shashank.sony.fancytoastlib.FancyToast

class SplashActivity : AppCompatActivity() {

    var accountType: String?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        this.getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash)

        makeStatusBarTransparent()
        if (getSupportActionBar() != null) {
            getSupportActionBar()!!.hide();
        }

        var progressBar = findViewById<ProgressBar>(R.id.progressBar)
        var btnLayout = findViewById<ConstraintLayout>(R.id.btnLayout)
        var txt_create_account = findViewById<TextView>(R.id.txt_create_account)
        var tv_sign_in = findViewById<TextView>(R.id.tv_sign_in)
        var tv_take_tour = findViewById<TextView>(R.id.tv_take_tour)

        tv_sign_in.setOnClickListener {
            var singinIntent = Intent(this@SplashActivity, SigninActivity::class.java)
//            var singinIntent = Intent(this@SplashActivity, MenuActivityEmployee::class.java)
            startActivity(singinIntent)
        }

        tv_take_tour.setOnClickListener {
            var singinIntent = Intent(this@SplashActivity, TourActivity::class.java)
            startActivity(singinIntent)
        }

        txt_create_account.setOnClickListener {
//            var singinIntent = Intent(this@SplashActivity, CreateAccountActivity::class.java)
//            startActivity(singinIntent)
            var singinIntent =
                Intent(this@SplashActivity, CreateAccountActivity2::class.java)
            singinIntent.putExtra("AccountType", accountType)
            startActivity(singinIntent)
        }

        var token = MySharedPreferences.getStringPreferences(
            MySharedPreferences.TOKEN_KEY,
            this@SplashActivity, ""
        )

        var isSignedIn = MySharedPreferences.getBooleanPreferences(
            MySharedPreferences.SIGNED_IN_KEY,
            this@SplashActivity
        )

//        Handler().postDelayed({

        if (isSignedIn) {
            AppController.signIn(this@SplashActivity){ result ->
                if(result == null) {
                    progressBar.visibility = View.GONE
                    btnLayout.visibility = View.VISIBLE
                } else if(!result){
                    progressBar.visibility = View.GONE
                    btnLayout.visibility = View.VISIBLE
                } else {
                    var signedInAs = MySharedPreferences.getStringPreferences(
                        MySharedPreferences.SIGNED_IN_AS_KEY,
                        this@SplashActivity, ""
                    )

                    if (signedInAs.equals("Employee")) {

                        var signIntent =
                            Intent(this@SplashActivity, MenuActivityEmployee::class.java)
                        startActivity(signIntent)
                        finish()

                    } else if (signedInAs.equals("Business Manager")) {

                        var signIntent =
                            Intent(this@SplashActivity, MenuActivityEmployer::class.java)
                        startActivity(signIntent)
                        finish()
                    }
                }
            };
        } else {
            progressBar.visibility = View.GONE
            btnLayout.visibility = View.VISIBLE
        }
//        }, 3000)
    }
}