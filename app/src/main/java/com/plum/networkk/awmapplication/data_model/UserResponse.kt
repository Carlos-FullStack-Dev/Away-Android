package com.plum.networkk.awmapplication.data_model

import com.beust.klaxon.*
import com.google.gson.annotations.SerializedName

// To parse the JSON, install Klaxon and do:
//
//   val userResponse = UserResponse.fromJson(jsonString)

data class UserResponse (
    @SerializedName("status_code")
    val statusCode: Long,

    @SerializedName("access_token")
    val accessToken: String,

    @SerializedName( "token_type")
    val tokeType: String,

    val role: String,
    val user: User
) {
}

data class User (
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
    val updatedAt: String,

    @SerializedName("lock_logs")
    val lockLogs: List<LockLog>
)

data class LockLog (
    val id: Long,

    @SerializedName("user_id")
    val userID: Long,

    val status: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String
)
