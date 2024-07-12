package com.plum.networkk.awmapplication.constants

import android.text.TextUtils
import android.util.Patterns


class mConstants {
    companion object {

        var ROLL_ID_SUPER_ADMIN = "1"
        var ROLL_ID_BUSINESS_MANAGER = "2"
        var ROLL_ID_EMPLOYEE = "3"

        var ACCOUNT_TYPE_EMPLOYEE = "employee"
        var ACCOUNT_TYPE_BUSINESS = "business"

        fun isValidEmail(target: CharSequence?): Boolean {
            return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
        }
//        fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
    }
}