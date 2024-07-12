package com.plum.networkk.awmapplication.apis
//
////import com.google.gson.GsonBuilder
////import okhttp3.OkHttpClient
////import retrofit2.Retrofit
////import retrofit2.converter.gson.GsonConverterFactory
//import java.util.concurrent.TimeUnit
//
//class RetrofitServiceWithAuth {
//
//    companion object {
//
//        //        var interceptorr: TokenInterceptor? = null
////        var okHttpClient: OkHttpClient? = null
//
//
////        val okHttpClient = OkHttpClient.Builder()
////            .connectTimeout(30, TimeUnit.SECONDS)
////            .addInterceptor(interceptorr)
////            .readTimeout(30, TimeUnit.SECONDS)
////            .build()
//
////        val retrofit = Retrofit.Builder()
////            .baseUrl("https://www.awmdev.xyz")
////            .client(okHttpClient)
////            .addConverterFactory(GsonConverterFactory.create(gson))
////
////            .build()
//
//
////        fun <S> cteateService(serviceClass: Class<S>?): S {
////            return retrofit.create(serviceClass)
////        }
//
//        fun <S> cteateService(
//            serviceClass: Class<S>?,
//            interpolator: TokenInterceptor?
//        ): S {
//
//            var gson = GsonBuilder()
//                .setLenient()
//                .create()
//
//            var okHttpClient = OkHttpClient.Builder()
//                .connectTimeout(30, TimeUnit.SECONDS)
//                .addInterceptor(interpolator)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build()
//
//            val retrofit = Retrofit.Builder()
//                .baseUrl("https://app.awaybusiness.com")//https://app.awaybusiness.com/api//https://www.awmdev.xyz
//                .client(okHttpClient)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .build()
//
////            interceptorr = interpolator
//            return retrofit.create(serviceClass)
//        }
//
//    }
//
//}