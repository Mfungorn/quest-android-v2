package com.quest.app.network

import android.content.SharedPreferences
import com.quest.app.data.PreferencesApi
import com.quest.app.exceptions.UserNotAuthenticatedException
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(private val prefs: SharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return if (request.method() == "POST" &&
            request.url().encodedPath().contains("auth")
        ) {
//            val response = chain.proceed(request)
//            val header = response.header("Authorization")
//            PreferencesApi.setJwt(prefs, header ?: "")
//            response
            chain.proceed(request)
        }
        else {
            val token = PreferencesApi.getJwt(prefs) ?: ""
            if (token.isNotEmpty()) {
                val authenticatedRequest = request.newBuilder()
                    .header("Authorization", token).build()
                chain.proceed(authenticatedRequest)
            } else throw UserNotAuthenticatedException()
        }
    }
}