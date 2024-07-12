package com.plum.networkk.awmapplication.data_model

import com.beust.klaxon.*
import com.google.gson.annotations.SerializedName

// To parse the JSON, install Klaxon and do:
//
//   val userResponse = UserResponse.fromJson(jsonString)

data class EmployeeDetailResponse (
    val result: Boolean,

    val message: String,
    val data: EmployeeDetail

    ) {
}

data class EmployeeDetail (
    val id: Long,

    @SerializedName("first_name")
    val firstName: String,

    @SerializedName( "last_name")
    val lastName: String,

    val email: String,
    val location: Any? = null,
    val team: Any? = null,
    val phone: Any? = null,
    val title: Any? = null,
    val address: Any? = null,
    val city: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String

)

