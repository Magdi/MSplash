package com.magdi.msplash.network.interceptor

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class HttpDebugInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        Log.d("HttpDebugInterceptor", "request: ${chain.request()}")
        Log.d("HttpDebugInterceptor", "headers: ${chain.request().headers()}")
        val result = chain.proceed(chain.request())
        Log.d("HttpDebugInterceptor", "results: $result")
        return result
    }
}