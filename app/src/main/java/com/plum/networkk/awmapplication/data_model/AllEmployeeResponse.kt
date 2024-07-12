package com.plum.networkk.awmapplication.data_model

// To parse the JSON, install Klaxon and do:
//
//   val employeeCompanyResponse = EmployeeCompanyResponse.fromJson(jsonString)

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.google.gson.annotations.SerializedName

data class AllEmployeeResponse (
    val status: Boolean,
    val message: String,
    val companies: List<Company>,
    val data: List<EmployeeData>
) {}

data class EmployeeData (
    val id: Long,

    @SerializedName("company_id")
    val companyID: Long,

    @SerializedName("user_id")
    val userID: Long,

    val type: String,

    val status: Long,


    @SerializedName( "first_name")
    val firstName: String,

    @SerializedName( "last_name")
    val lastName: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("unlocks_today")
    val unlocksToday: Long,

    @SerializedName("minutesToday")
    val minutesToday: Long,

    @SerializedName("minutesWeekly")
    val minutesWeekly: Long
//    @SerializedName("minutesWeekly")
//    val minutesWeekly: Long
)
