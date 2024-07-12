package com.plum.networkk.awmapplication.activities

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.plum.networkk.awmapplication.R
import com.plum.networkk.awmapplication.constants.mConstants.Companion.ACCOUNT_TYPE_BUSINESS
import com.plum.networkk.awmapplication.constants.mConstants.Companion.ACCOUNT_TYPE_EMPLOYEE
import com.plum.networkk.awmapplication.databinding.ActivityCreateAccountBinding

class CreateAccountActivity : AppCompatActivity() {

    var accountType: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_create_account)


        var vBinding = ActivityCreateAccountBinding.inflate(layoutInflater)
        val view: View = vBinding.getRoot()
        setContentView(view)

        setSupportActionBar(vBinding.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        accountType = ACCOUNT_TYPE_EMPLOYEE
//        var tv_business = findViewById<TextView>(R.id.tv_business)
//        var tv_employee = findViewById<TextView>(R.id.tv_employee)
//        var tvTitle = findViewById<TextView>(R.id.tvTitle)
//        var tvDescription = findViewById<TextView>(R.id.tvDescription)

        vBinding.tvEmployee.isSelected = true
        vBinding.tvBusiness.isSelected = false

        vBinding.tvTitle.text = resources.getString(R.string.txt_title_employee_create_account)
        vBinding.tvDescription.text =
            resources.getString(R.string.txt_description_employee_create_account)

        vBinding.btnNext.setOnClickListener {
            var singinIntent =
                Intent(this@CreateAccountActivity, CreateAccountActivity2::class.java)
            singinIntent.putExtra("AccountType", accountType)
            startActivity(singinIntent)
        }

        vBinding.tvEmployee.setOnClickListener {
            if (vBinding.tvEmployee!!.isSelected) {
//                tv_employee.isSelected = false
            } else {

                accountType = ACCOUNT_TYPE_EMPLOYEE
                vBinding.tvTitle.text =
                    resources.getString(R.string.txt_title_employee_create_account)
                vBinding.tvDescription.text =
                    resources.getString(R.string.txt_description_employee_create_account)

                vBinding.tvEmployee.isSelected = true
                vBinding.tvBusiness.isSelected = false

            }
        }



        vBinding.tvBusiness.setOnClickListener {
            if (vBinding.tvBusiness!!.isSelected) {
//                tv_business.isSelected = false

            } else {

                accountType = ACCOUNT_TYPE_BUSINESS
                vBinding.tvTitle.text =
                    resources.getString(R.string.txt_title_business_create_account)
                vBinding.tvDescription.text =
                    resources.getString(R.string.txt_description_business_create_account)
                vBinding.tvBusiness.isSelected = true
                vBinding.tvEmployee.isSelected = false
            }
        }
    }

    override fun onOptionsItemSelected(menuItem: MenuItem): Boolean {
        if (menuItem.getItemId() === android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(menuItem)
    }
}