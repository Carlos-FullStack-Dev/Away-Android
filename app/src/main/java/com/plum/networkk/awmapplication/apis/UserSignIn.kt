//package com.plum.networkk.awmapplication.apis
//// To parse the JSON, install Klaxon and do:
////
////   val userResponse = UserResponse.fromJson(jsonString)
//
//
//private val klaxon = Klaxon()
//
//data class UserResponse (
//    @Json(name = "status_code")
//    val statusCode: Long,
//
//    @Json(name = "access_token")
//    val accessToken: String,
//
//    @Json(name = "token_type")
//    val tokenType: String,
//
//    val role: String,
//    val user: User
//) {
//    public fun toJson() = klaxon.toJsonString(this)
//
//    companion object {
//        public fun fromJson(json: String) = klaxon.parse<UserResponse>(json)
//    }
//}
//
//data class User (
//    val id: Long,
//
//    @Json(name = "first_name")
//    val firstName: String,
//
//    @Json(name = "last_name")
//    val lastName: String,
//
//    val email: String,
//    val location: Any? = null,
//    val team: Any? = null,
//    val phone: Any? = null,
//    val title: Any? = null,
//    val address: Any? = null,
//    val city: String,
//
//    @Json(name = "created_at")
//    val createdAt: String,
//
//    @Json(name = "updated_at")
//    val updatedAt: String,
//
//    @Json(name = "lock_logs")
//    val lockLogs: List<LockLog>
//)
//
//data class LockLog (
//    val id: Long,
//
//    @Json(name = "user_id")
//    val userID: Long,
//
//    val status: String,
//
//    @Json(name = "created_at")
//    val createdAt: String,
//
//    @Json(name = "updated_at")
//    val updatedAt: String
//)
