package com.plum.networkk.awmapplication.data_model

import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.google.gson.annotations.SerializedName

/*
Created By aCodeMechanic, mac on 10/11/22.
*/
// To parse the JSON, install Klaxon and do:
//
//   val employerCompanyRespose = EmployerCompanyRespose.fromJson(jsonString)


data class EmployerCompanyRespose (
    val status: Boolean,
    val message: String,
    val data: List<EmployerCompany>
) {
}

data class EmployerCompany (
    val id: Long,
    val name: String,
    val address: String,

    @SerializedName( "created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName( "user_id")
    val userID: Long,

    @SerializedName("invite_code")
    val inviteCode: String
)
