package com.example.app.features.auth.data

import com.example.app.features.auth.domain.model.AuthResponse
import com.example.app.features.auth.domain.model.LoginRequest
import com.example.app.features.auth.domain.model.SignUpRequest
import io.reactivex.Completable
import io.reactivex.Single

interface TokenRepository {
    val api: AuthApi

    fun signInUserAndGetToken(loginRequest: LoginRequest): Single<AuthResponse>
    fun signUpUser(signUpRequest: SignUpRequest): Completable
}