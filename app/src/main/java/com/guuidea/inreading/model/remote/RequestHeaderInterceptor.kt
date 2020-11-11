package com.guuidea.inreading.model.remote

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @file      RequestHeaderIntercepter
 * @description    添加header
 * @author         江 杰
 * @createDate     2020/11/11 11:24
 */
class RequestHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
                .addHeader("authToken", "123")
                .build()
        return chain.proceed(request)
    }

}