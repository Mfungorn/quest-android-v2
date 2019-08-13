package com.quest.app.features.auth.data

import com.quest.app.features.auth.domain.model.AuthResponse
import com.quest.app.features.auth.domain.model.LoginRequest
import com.quest.app.features.auth.domain.model.SignUpRequest
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("/auth/login")
    fun signInUserAsync(@Body loginRequest: LoginRequest): Single<AuthResponse>

    @POST("/auth/signup")
    fun signUpAsync(@Body signUpRequest: SignUpRequest): Completable
}