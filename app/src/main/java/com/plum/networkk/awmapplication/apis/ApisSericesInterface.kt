package com.plum.networkk.awmapplication.apis
//
//import retrofit2.Call
//import retrofit2.http.*
//
//
//interface ApisSericesInterface {
//
//    /*@Header("x-api-key") api_key: String,*/
//
//    @POST("/api/auth/login")
//    fun SignIn(
//        @Body msignInModel: SignInModel
//    ): Call<SigninResponseModelM>
//
//
//    /*@FormUrlEncoded
//    @GET("/products?page=1")
//    fun getProducts(
//        @Field("parameter") parameter: String?,
//        @Field("value") value: String?
//    ): Call<SignInDataCredential?>?*/
//
//
//    /* @FormUrlEncoded
//     @POST("/api/auth/login")
//     fun SignIn(
//         @Field("email") email: String?, @Field("password") password: String?
//     ): Call<SignInDataCredential>*/
//
//
//    /*@FormUrlEncoded
//    @POST("/api/auth/register")
//    fun UserRegisteration(
//        @Body msignupModel: SignUpModel
//    ): Call<SignInDataCredential>*/
//
//
//    //@FormUrlEncoded
//    @POST("/api/auth/register")
//    fun UserRegisteration(
//        @Body siginup: SignUpModel
//    ): Call<RegistrationResponseModel>
//
//
//    /* @FormUrlEncoded
//     @POST("/api/device-logs")
//     fun deviceLogs(
//         @Header("Authorization") auth: String,
//         @Field("status") status: String?
//     ): Call<deviceLogsModel>*/
//
//    //    @FormUrlEncoded
//    @POST("/api/device-logs")
//    fun deviceLogs(
//        @Body logsModel: GetDeviceLogsModel
//    ): Call<deviceLogsModel>
//
//    /* @Header("Authorization") auth: String,*/
//    /*@Body logsModel: GetDeviceLogsModel*/
//    /*@FormUrlEncoded
//    @POST("/api/auth/register")
//    fun UserRegisteration(
//        @Field("first_name") first_name: String?,
//        @Field("last_name") last_name: String?,
//        @Field("email") email: String?,
//        @Field("password") password: String?
//    ): Call<Data>*/
//
//
//    @GET("/api/faq")
//    fun getFaq(): Call<FaqsResponseModel>
//
//    @GET("/api/statistics")
//    fun getStatistics(): Call<StatisticsModel>
//
//}