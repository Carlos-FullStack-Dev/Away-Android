package com.plum.networkk.awmapplication.apis;
//
//import java.io.IOException;
//
//import okhttp3.Interceptor;
//import okhttp3.Request;
//import okhttp3.Response;
//
//public class TokenInterceptor implements Interceptor {
//
//    String token;
//
//    public TokenInterceptor(String token) {
//        this.token = token;
//    }
//
//    public TokenInterceptor() {
//
//    }
//
//    @Override
//    public Response intercept(Chain chain) throws IOException {
//
//        //rewrite the request to add bearer token
//
//        Request newRequest = chain.request().newBuilder()
//                .header("Authorization", "Bearer " + token)
//                .build();
//
////        Request newRequest = chain.request().newBuilder()
////                .header("Authorization", "Bearer " + "88|UidggzYKWeENXzSFztkXGhPL3KvcMwDCK0pMbGX4")
////                .build();
//
//        return chain.proceed(newRequest);
//    }
//}
