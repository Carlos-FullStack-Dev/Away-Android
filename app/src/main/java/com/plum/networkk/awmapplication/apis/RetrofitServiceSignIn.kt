package com.plum.networkk.awmapplication.apis
//
//import com.google.gson.GsonBuilder
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//
//class RetrofitServiceSignIn {
//
//    companion object {
//
//
//        var gson = GsonBuilder()
//            .setLenient()
//            .create()
//
//        val okHttpClient = OkHttpClient.Builder()
//            .connectTimeout(30, TimeUnit.SECONDS)
//            .readTimeout(30, TimeUnit.SECONDS)
//            .build()
//
//        val retrofit = Retrofit.Builder()
////            .baseUrl("https://www.awmdev.xyz")
//            .baseUrl("https://app.awaybusiness.com")//https://app.awaybusiness.com/api
//            .client(okHttpClient)
//            .addConverterFactory(GsonConverterFactory.create(gson))
//
//            .build()
//
//
//        fun <S> cteateService(serviceClass: Class<S>?): S {
//            return retrofit.create(serviceClass)
//        }
//
//    }
//
//}