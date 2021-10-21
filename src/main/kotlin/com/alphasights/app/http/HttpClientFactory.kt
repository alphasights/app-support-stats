package com.alphasights.app.http

import okhttp3.OkHttpClient

class HttpClientFactory {
    private var okHttpClient: OkHttpClient? = null

    fun getIntercomOkHttpClient(): OkHttpClient {
        if(okHttpClient != null)
            return okHttpClient!!

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(IntercomInterceptor())
            .build()

        return okHttpClient!!
    }
}
