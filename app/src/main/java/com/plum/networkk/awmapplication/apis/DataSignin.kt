package com.plum.networkk.awmapplication.apis

data class DataSignin(

	val status_code: Int? = null,
	val access_token: String? = null,
	val token_type: String? = null,
	val role: String? = null
//	val user: UserSignIn? = null
)

/*data class User(
	val address: Any? = null,
	val updatedAt: String? = null,
	val phone: Any? = null,
	val city: String? = null,
	val lastName: String? = null,
	val createdAt: String? = null,
	val location: Any? = null,
	val team: Any? = null,
	val title: Any? = null,
	val firstName: String? = null,
	val lockLogs: List<LockLogsItem?>? = null,
	val email: String? = null
)*/

/*data class LockLogsItem(
	val updatedAt: String? = null,
	val userId: String? = null,
	val createdAt: String? = null,
	val id: Int? = null,
	val status: String? = null
)*/

