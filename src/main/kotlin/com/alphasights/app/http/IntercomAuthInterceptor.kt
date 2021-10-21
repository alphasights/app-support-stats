package com.alphasights.app.http

import com.alphasights.app.service.AuthenticationService
import okhttp3.Interceptor
import okhttp3.Response

class IntercomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authenticationService = AuthenticationService()
        val accessToken = authenticationService.getAccessToken()

        // TODO: Implement proper header to include the accessToken
        val basicAuthRequest = request.newBuilder()
            .addHeader("Authorization", accessToken) // this should be modified to make sure it matches what intercom expects
            .addHeader("Content-Type", "application/json")
            .build()

        return chain.proceed(basicAuthRequest)
    }
}
