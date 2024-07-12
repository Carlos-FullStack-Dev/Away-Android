package com.plum.networkk.awmapplication.data_model

// To parse the JSON, install Klaxon and do:
//
//   val employeeCompanyResponse = EmployeeCompanyResponse.fromJson(jsonString)

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.google.gson.annotations.SerializedName

data class EmployeeCompanyResponse (
    val status: Boolean,
    val message: String,
    val data: List<Company>
) {

}

data class Company (
    @SerializedName("company_id")
    val companyID: Long,

    @SerializedName("name")
    val name: String,

    @SerializedName("address")
    val address: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("user_id")
    val userID: Long,

    @SerializedName("invite_code")
    val inviteCode: String,

    @SerializedName("employee_count")
    val employeeCount: Long?

)

data class Pivot (
    @SerializedName("user_id")
    val userID: Long,

    @SerializedName("company_id")
    val companyID: Long,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    val type: String



)
