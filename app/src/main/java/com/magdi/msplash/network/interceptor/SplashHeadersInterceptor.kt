package com.magdi.msplash.network.interceptor

import com.magdi.msplash.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class SplashHeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()

        return chain.proceed(
            requestBuilder
                .header(AUTHORIZATION_KEY, BuildConfig.PRIVATE_KEY)
                .header(CONTENT_TYPE_KEY, JSON_CONTENT_TYPE)
                .build()
        )
    }

    companion object {
        const val AUTHORIZATION_KEY = "Authorization"
        const val CONTENT_TYPE_KEY = "content-type"
        const val JSON_CONTENT_TYPE = "application/json"
    }
}