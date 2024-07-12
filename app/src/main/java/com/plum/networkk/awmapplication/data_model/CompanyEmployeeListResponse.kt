package com.plum.networkk.awmapplication.data_model


/*
Created By aCodeMechanic, mac on 10/11/22.
*/

// To parse the JSON, install Klaxon and do:
//
//   val companyEmployeeResponse = CompanyEmployeeResponse.fromJson(jsonString)


import com.beust.klaxon.*
import com.google.gson.annotations.SerializedName


data class CompanyEmployeeListResponse (
    val status: Boolean,
    val message: String,
    val data: List<Employee>
) {
}

data class Employee (
    val id: Long,

   @SerializedName("first_name")
    val firstName: String,

   @SerializedName("last_name")
    val lastName: String,

    val email: String,

   @SerializedName("email_verified_at")
    val emailVerifiedAt: Any? = null,

    val location: Any? = null,
    val team: Any? = null,
    val phone: Any? = null,
    val title: Any? = null,
    val address: Any? = null,

   @SerializedName("city_id")
    val cityID: Long,

   @SerializedName("company_id")
    val companyID: Any? = null,

   @SerializedName("state_id")
    val stateID: Any? = null,

   @SerializedName("country_id")
    val countryID: Any? = null,

   @SerializedName("created_at")
    val createdAt: String,

   @SerializedName("updated_at")
    val updatedAt: String,

   @SerializedName("business_manager_id")
    val businessManagerID: Any? = null,

   @SerializedName("email_verify")
    val emailVerify: Any? = null,

    val status: String? = null,

   @SerializedName("stripe_id")
    val stripeID: Any? = null,

   @SerializedName("pm_type")
    val pmType: Any? = null,

   @SerializedName("pm_last_four")
    val pmLastFour: Any? = null,

   @SerializedName("trial_ends_at")
    val trialEndsAt: Any? = null,

    val pivot: Pivot,

   @SerializedName("mobile_logs")
    val mobileLogs: List<MobileLog>,

    var onlineStatus: String = "Offline",
    var statistics: StatisticsResponse? = null

)

data class MobileLog (
    val id: Long,

   @SerializedName("user_id")
    val userID: Long,

    val status: Status,

   @SerializedName("created_at")
    val createdAt: String,

   @SerializedName("updated_at")
    val updatedAt: String
)

enum class Status(val value: String) {
    Locked("locked"),
    Unlocked("unlocked");

    companion object {
        public fun fromValue(value: String): Status = when (value) {
            "locked"   -> Locked
            "unlocked" -> Unlocked
            else       -> throw IllegalArgumentException()
        }
    }
}
