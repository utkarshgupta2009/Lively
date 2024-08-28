package com.example.lively.utils

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("x-rapidapi-key", apiKey)
            .addHeader("x-rapidapi-host", apiHost)
            .build()

        return chain.proceed(request)
    }


}